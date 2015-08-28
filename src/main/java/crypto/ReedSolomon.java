/*
    Reed Solomon Encoding and Decoding for Nxt

    Version: 1.0, license: Public Domain, coder: NxtChg (admin@nxtchg.com)
    Java Version: ChuckOne (ChuckOne@mail.de).
*/
package crypto;

import java.math.BigInteger;

public class ReedSolomon
{

  public static final BigInteger two64 = new BigInteger("18446744073709551616");

  private static final int[] initial_codeword = {1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
  private static final int[] gexp = {1, 2, 4, 8, 16, 5, 10, 20, 13, 26, 17, 7, 14, 28, 29, 31, 27, 19, 3, 6, 12, 24, 21, 15, 30, 25, 23, 11, 22, 9, 18, 1};
  private static final int[] glog = {0, 0, 1, 18, 2, 5, 19, 11, 3, 29, 6, 27, 20, 8, 12, 23, 4, 10, 30, 17, 7, 22, 28, 26, 21, 25, 9, 16, 13, 14, 24, 15};
  private static final int[] codeword_map = {3, 2, 1, 0, 7, 6, 5, 4, 13, 14, 15, 16, 12, 8, 9, 10, 11};
  private static final String alphabet = "23456789ABCDEFGHJKLMNPQRSTUVWXYZ";

  private static final int base_32_length = 13;
  private static final int base_10_length = 20;

  public static String encode(long plain)
  {

    String plain_string = toUnsignedLong(plain);
    int length = plain_string.length();
    int[] plain_string_10 = new int[ReedSolomon.base_10_length];
    for(int i = 0; i < length; i++)
    {
      plain_string_10[i] = (int) plain_string.charAt(i) - (int) '0';
    }

    int codeword_length = 0;
    int[] codeword = new int[ReedSolomon.initial_codeword.length];

    do
    {  // base 10 to base 32 conversion
      int new_length = 0;
      int digit_32 = 0;
      for(int i = 0; i < length; i++)
      {
        digit_32 = digit_32 * 10 + plain_string_10[i];
        if(digit_32 >= 32)
        {
          plain_string_10[new_length] = digit_32 >> 5;
          digit_32 &= 31;
          new_length += 1;
        }
        else if(new_length > 0)
        {
          plain_string_10[new_length] = 0;
          new_length += 1;
        }
      }
      length = new_length;
      codeword[codeword_length] = digit_32;
      codeword_length += 1;
    }
    while(length > 0);

    int[] p = {0, 0, 0, 0};
    for(int i = ReedSolomon.base_32_length - 1; i >= 0; i--)
    {
      final int fb = codeword[i] ^ p[3];
      p[3] = p[2] ^ ReedSolomon.gmult(30, fb);
      p[2] = p[1] ^ ReedSolomon.gmult(6, fb);
      p[1] = p[0] ^ ReedSolomon.gmult(9, fb);
      p[0] = ReedSolomon.gmult(17, fb);
    }

    System.arraycopy(p, 0, codeword, ReedSolomon.base_32_length, ReedSolomon.initial_codeword.length - ReedSolomon.base_32_length);

    StringBuilder cypher_string_builder = new StringBuilder();
    for(int i = 0; i < 17; i++)
    {
      final int codework_index = ReedSolomon.codeword_map[i];
      final int alphabet_index = codeword[codework_index];
      cypher_string_builder.append(ReedSolomon.alphabet.charAt(alphabet_index));

      if((i & 3) == 3 && i < 13)
      {
        cypher_string_builder.append('-');
      }
    }
    return cypher_string_builder.toString();
  }

  public static String toUnsignedLong(long objectId)
  {
    if(objectId >= 0)
    {
      return String.valueOf(objectId);
    }
    BigInteger id = BigInteger.valueOf(objectId).add(two64);
    return id.toString();
  }

  private static int gmult(int a, int b)
  {
    if(a == 0 || b == 0)
    {
      return 0;
    }

    int idx = (ReedSolomon.glog[a] + ReedSolomon.glog[b]) % 31;

    return ReedSolomon.gexp[idx];
  }
}


