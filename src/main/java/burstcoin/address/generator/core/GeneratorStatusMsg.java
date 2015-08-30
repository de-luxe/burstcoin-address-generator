/*
 * The MIT License (MIT)
 *
 * Copyright (c) 2015 by luxe - https://github.com/de-luxe - BURST-LUXE-RED2-G6JW-H4HG5
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

import java.io.Serializable;

/**
 * The type Generator status msg.
 */
public class GeneratorStatusMsg
  implements Serializable
{
  private String jobKey;
  private long count;

  /**
   * Instantiates a new Generator status msg.
   *
   * @param jobKey the job key
   * @param count the count
   */
  public GeneratorStatusMsg(String jobKey, long count)
  {
    this.jobKey = jobKey;
    this.count = count;
  }

  /**
   * Gets job key.
   *
   * @return the job key
   */
  public String getJobKey()
  {
    return jobKey;
  }

  /**
   * Gets count.
   *
   * @return the count
   */
  public long getCount()
  {
    return count;
  }
}
