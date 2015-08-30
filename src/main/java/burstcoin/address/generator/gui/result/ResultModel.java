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

package burstcoin.address.generator.gui.result;

import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleStringProperty;

import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * The type Result model.
 */
public class ResultModel
  implements Serializable
{

  private SimpleStringProperty time;
  private SimpleStringProperty key;

  private SimpleStringProperty address;
  private SimpleStringProperty password;

  private SimpleLongProperty tries;

  /**
   * Instantiates a new Result model.
   *
   * @param key the key
   * @param password the password
   * @param address the address
   * @param tries the tries
   */
  public ResultModel(String key, String password, String address, long tries)
  {

    DateFormat formatter = new SimpleDateFormat();
    this.time = new SimpleStringProperty(formatter.format(new Date().getTime()));
    this.key = new SimpleStringProperty(key);
    this.address = new SimpleStringProperty(address);
    this.password = new SimpleStringProperty(password);
    this.tries = new SimpleLongProperty(tries);
  }

  /**
   * Gets time.
   *
   * @return the time
   */
  public String getTime()
  {
    return time.get();
  }

  /**
   * Time property.
   *
   * @return the simple string property
   */
  public SimpleStringProperty timeProperty()
  {
    return time;
  }

  /**
   * Sets time.
   *
   * @param time the time
   */
  public void setTime(String time)
  {
    this.time.set(time);
  }

  /**
   * Gets key.
   *
   * @return the key
   */
  public String getKey()
  {
    return key.get();
  }

  /**
   * Key property.
   *
   * @return the simple string property
   */
  public SimpleStringProperty keyProperty()
  {
    return key;
  }

  /**
   * Sets key.
   *
   * @param key the key
   */
  public void setKey(String key)
  {
    this.key.set(key);
  }

  /**
   * Gets address.
   *
   * @return the address
   */
  public String getAddress()
  {
    return address.get();
  }

  /**
   * Address property.
   *
   * @return the simple string property
   */
  public SimpleStringProperty addressProperty()
  {
    return address;
  }

  /**
   * Sets address.
   *
   * @param address the address
   */
  public void setAddress(String address)
  {
    this.address.set(address);
  }

  /**
   * Gets password.
   *
   * @return the password
   */
  public String getPassword()
  {
    return password.get();
  }

  /**
   * Password property.
   *
   * @return the simple string property
   */
  public SimpleStringProperty passwordProperty()
  {
    return password;
  }

  /**
   * Sets password.
   *
   * @param password the password
   */
  public void setPassword(String password)
  {
    this.password.set(password);
  }

  /**
   * Gets tries.
   *
   * @return the tries
   */
  public long getTries()
  {
    return tries.get();
  }

  /**
   * Tries property.
   *
   * @return the simple long property
   */
  public SimpleLongProperty triesProperty()
  {
    return tries;
  }

  /**
   * Sets tries.
   *
   * @param tries the tries
   */
  public void setTries(long tries)
  {
    this.tries.set(tries);
  }
}
