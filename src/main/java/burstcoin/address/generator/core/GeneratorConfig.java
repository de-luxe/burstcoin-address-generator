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

import java.util.Collection;
import java.util.HashSet;

/**
 * The type Generator config.
 */
public class GeneratorConfig
{
  // query
  private Collection<String> keys;

  // environment
  private int threads;
  private long triesPerThread;

  // password
  private int passwordLength;
  private String prefix;
  private String postfix;

  /**
   * Instantiates a new Generator config.
   *
   * @param keys the keys
   * @param threads the threads
   * @param triesPerThread the tries per thread
   * @param passwordLength the password length
   * @param prefix the prefix
   * @param postfix the postfix
   */
  public GeneratorConfig(Collection<String> keys, int threads, long triesPerThread, int passwordLength,
                         String prefix, String postfix)
  {
    this.keys = keys;
    this.threads = threads;
    this.triesPerThread = triesPerThread;
    this.passwordLength = passwordLength;
    this.prefix = prefix;
    this.postfix = postfix;
  }

  /**
   * Gets keys.
   *
   * @return the keys
   */
  public Collection<String> getKeys()
  {
    return keys;
  }

  /**
   * Gets threads.
   *
   * @return the threads
   */
  public int getThreads()
  {
    return threads;
  }

  /**
   * Gets tries per thread.
   *
   * @return the tries per thread
   */
  public long getTriesPerThread()
  {
    return triesPerThread;
  }

  /**
   * Gets password length.
   *
   * @return the password length
   */
  public int getPasswordLength()
  {
    return passwordLength;
  }

  /**
   * Gets prefix.
   *
   * @return the prefix
   */
  public String getPrefix()
  {
    return prefix;
  }

  /**
   * Gets postfix.
   *
   * @return the postfix
   */
  public String getPostfix()
  {
    return postfix;
  }

  /**
   * Copy generator config.
   *
   * @return the generator config
   */
  public GeneratorConfig copy()
  {
    return new GeneratorConfig(new HashSet<String>(keys), threads, triesPerThread, passwordLength, prefix, postfix);
  }
}
