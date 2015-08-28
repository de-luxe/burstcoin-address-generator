/*
 * The MIT License (MIT)
 *
 * Copyright (c) 2015 by luxe - https://github.com/de-luxe - BURST-LUXE-ZDVD-CX3E-3SM58
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software
 * and associated documentation files (the "Software"), to deal in the Software without restriction,
 * including without limitation the rights to use, copy, modify, merge, publish, distribute,
 * sublicense, and/or sell copies of the Software, and to permit persons to whom the Software
 * is furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies
 * or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING
 * BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND
 * NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM,
 * DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING
 * FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 *
 */

package burstcoin.address.generator.core;

import akka.actor.UntypedActor;
import crypto.Curve25519;
import crypto.ReedSolomon;
import org.apache.commons.lang3.RandomStringUtils;

import java.math.BigInteger;
import java.nio.charset.Charset;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.List;

/**
 * The type Generate work actor.
 */
public class GenerateWorkActor
  extends UntypedActor
{
  private String jobKey;
  private long count;

  @Override
  public void onReceive(Object message)
    throws Exception
  {
    if(message instanceof GenerateWorkMsg)
    {
      // reset statistic
      GenerateWorkMsg workMessage = (GenerateWorkMsg) message;
      startWorking(workMessage);
    }
  }

  private void startWorking(GenerateWorkMsg workMessage)
  {
    count = 0;
    jobKey = workMessage.getJobKey();

    for(int idx = 0; idx < workMessage.getConfig().getTriesPerThread(); idx++)
    {
      GenerateResultMsg result = generate(workMessage.getConfig());
      if(result != null)
      {
        getSender().tell(result, getSelf());
      }

      if(count % 3500 == 0)
      {
        getSender().tell(new GeneratorStatusMsg(jobKey, count), getSelf());
      }
    }
    getSender().tell(new GeneratorStatusMsg(jobKey, count), getSelf());
    count = 0;
  }

  private GenerateResultMsg generate(GeneratorConfig config)
  {
    count++;

    String random = RandomStringUtils.random(config.getPasswordLength(), true, true);
    String password = config.getPrefix() + random + config.getPostfix();
    GenerateResultMsg result = null;
    String addressRS = getAddressRS(password.getBytes(Charset.forName("UTF-8")));
    for(String key : config.getKeys())
    {
      if(key.contains("&"))
      {
        List<String> and = Arrays.asList(key.split("&"));
        if(check(addressRS, and))
        {
          result = new GenerateResultMsg(jobKey, key, password, addressRS, count);
        }
      }
      else if(addressRS.contains(key))
      {
        result = new GenerateResultMsg(jobKey, key, password, addressRS, count);
      }
    }
    return result;
  }

  private boolean check(String addressRS, List<String> and)
  {
    for(String s : and)
    {
      if(!addressRS.contains(s))
      {
        return false;
      }
    }
    return true;
  }

  private String getAddressRS(byte[] secretPhraseBytes)
  {
    byte[] publicKeyHash = getMessageDigest().digest(getPublicKey(secretPhraseBytes));
    Long accountId = fullHashToId(publicKeyHash);
    return "BURST-" + ReedSolomon.encode(nullToZero(accountId));
  }

  /**
   * Null to zero.
   *
   * @param l the l
   * @return the long
   */
  public long nullToZero(Long l)
  {
    return l == null ? 0 : l;
  }

  /**
   * Gets message digest.
   *
   * @return the message digest
   */
  public MessageDigest getMessageDigest()
  {
    try
    {
      return MessageDigest.getInstance("SHA-256");
    }
    catch(NoSuchAlgorithmException e)
    {
      throw new RuntimeException(e.getMessage(), e);
    }
  }

  /**
   * Get public key.
   *
   * @param secretPhraseBytes the secret phrase bytes
   * @return the byte [ ]
   */
  public byte[] getPublicKey(byte[] secretPhraseBytes)
  {
    byte[] publicKey = new byte[32];
    Curve25519.keygen(publicKey, null, getMessageDigest().digest(secretPhraseBytes));
    return publicKey;
  }

  /**
   * Full hash to id.
   *
   * @param hash the hash
   * @return the long
   */
  public Long fullHashToId(byte[] hash)
  {
    if(hash == null || hash.length < 8)
    {
      throw new IllegalArgumentException("Invalid hash: " + Arrays.toString(hash));
    }
    BigInteger bigInteger = new BigInteger(1, new byte[]{hash[7], hash[6], hash[5], hash[4], hash[3], hash[2], hash[1], hash[0]});
    return bigInteger.longValue();
  }
}

