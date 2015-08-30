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

package burstcoin.address.generator.gui;

import akka.actor.ActorSystem;
import akka.actor.Props;
import burstcoin.address.generator.core.GenerateActor;
import burstcoin.address.generator.core.GeneratorConfig;
import burstcoin.address.generator.gui.result.ResultController;
import burstcoin.address.generator.gui.tabs.GeneratorConfigPresenter;
import burstcoin.address.generator.gui.tabs.GeneratorConfigView;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.stage.Stage;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.Properties;


/**
 * The type Generator app.
 */
public class GeneratorApp
  extends Application
{
  /**
   * The constant KEYS.
   */
  public static final String KEYS = "keys";
  /**
   * The constant THREADS.
   */
  public static final String THREADS = "threads";
  /**
   * The constant TRIES_PER_THREAD.
   */
  public static final String TRIES_PER_THREAD = "triesPerThread";
  /**
   * The constant PASSWORD_LENGTH.
   */
  public static final String PASSWORD_LENGTH = "passwordLength";
  /**
   * The constant PREFIX.
   */
  public static final String PREFIX = "prefix";
  /**
   * The constant POSTFIX.
   */
  public static final String POSTFIX = "postfix";
  /**
   * The constant DELIMITER.
   */
  public static final String DELIMITER = ",";

  private GeneratorAppView view;
  private GeneratorConfigPresenter generatorConfigPresenter;
  private ActorSystem actorSystem;

  @Override
  public void start(Stage primaryStage)
    throws Exception
  {
    actorSystem = ActorSystem.create();

    view = new GeneratorAppView(new GeneratorAppView.Action()
    {
      @Override
      public void onTabSelect(String name)
      {
         // todo
      }
    });

    generatorConfigPresenter = new GeneratorConfigPresenter(getConfig(), new GeneratorConfigView.Callback()
    {
      @Override
      public void onStart(GeneratorConfig generatorConfig)
      {
        try
        {
          addResult(generatorConfig);
        }
        catch(IOException e)
        {
          e.printStackTrace();
        }
      }
    });
    view.addContent("config", generatorConfigPresenter.getNode());

    view.addTo(primaryStage);
  }

  @Override
  public void stop()
    throws Exception
  {
    super.stop();
    System.exit(0);
  }


  private void addResult(GeneratorConfig generatorConfig)
    throws IOException
  {
    FXMLLoader loader = new FXMLLoader();
    // set fxml location
    loader.setLocation(getClass().getResource("result/Result.fxml"));
    // load and add
    Node node = loader.load();
    String uuid = view.addContent("result", node);
    // get controller from loader
    ResultController resultController = loader.getController();

    if(generatorConfig != null)
    {
      actorSystem.actorOf(Props.create(GenerateActor.class, generatorConfig, resultController));
    }

    view.showTab(uuid);
  }

  /**
   * Gets config.
   *
   * @return GeneratorConfig or null if something went wrong
   */
  public static GeneratorConfig getConfig()
  {
    Properties properties = new Properties();
    try
    {
      properties.load(new FileInputStream(System.getProperty("user.dir") + "/config.properties"));

      return new GeneratorConfig(
        Arrays.asList(properties.getProperty(KEYS).trim().split(DELIMITER)),
        Integer.valueOf(properties.getProperty(THREADS)),
        Long.valueOf(properties.getProperty(TRIES_PER_THREAD)),
        Integer.valueOf(properties.getProperty(PASSWORD_LENGTH)),
        properties.getProperty(PREFIX),
        properties.getProperty(POSTFIX)
      );
    }
    catch(IOException e)
    {
      e.printStackTrace();
    }
    return null;
  }
}
