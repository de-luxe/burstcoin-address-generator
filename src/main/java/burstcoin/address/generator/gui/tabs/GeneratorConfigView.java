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

package burstcoin.address.generator.gui.tabs;

import burstcoin.address.generator.GeneratorConfig;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Font;

import java.util.Arrays;

/**
 * The type Generator config view.
 */
public class GeneratorConfigView
  extends AnchorPane
{

  /**
   * The interface Callback.
   */
  public interface Callback
  {
    /**
     * On start.
     *
     * @param generatorConfig the generator config
     */
    void onStart(GeneratorConfig generatorConfig);
  }

  /**
   * Instantiates a new Generator config view.
   *
   * @param generatorConfig the generator config
   * @param callback the callback
   */
  public GeneratorConfigView(GeneratorConfig generatorConfig, Callback callback)
  {

    //  <Label layoutX="28.0" layoutY="14.0" text="search config:">
    //  <font>
    //  <Font size="14.0" />
    //  </font>
    //  </Label>
    Label searchConfigLabel = new Label("search config: (do NOT use: 1, I or O)");
    searchConfigLabel.setLayoutX(28.0);
    searchConfigLabel.setLayoutY(14.0);
    searchConfigLabel.setFont(new Font(14.0));
    getChildren().add(searchConfigLabel);

    // <Label layoutX="96.0" layoutY="51.0" text="keys" />
    Label keysLabel = new Label("keys");
    keysLabel.setLayoutX(96.0);
    keysLabel.setLayoutY(51.0);
    getChildren().add(keysLabel);

    String keys = "";
    for(String key : generatorConfig.getKeys())
    {
      keys += key + ",";
    }
    keys = keys.substring(0, keys.length() - 1);

    // <TextField layoutX="133.0" layoutY="47.0" prefHeight="25.0" prefWidth="439.0" />
    TextField keysTextField = new TextField(keys);
    keysTextField.setLayoutX(133.0);
    keysTextField.setLayoutY(47.0);
    keysTextField.setPrefHeight(25.0);
    keysTextField.setPrefWidth(439.0);
    getChildren().add(keysTextField);

    // <Label layoutX="28.0" layoutY="109.0" text="system config:">
    // <font>
    // <Font size="14.0" />
    // </font>
    // </Label>
    Label systemConfigLabel = new Label("system config:");
    systemConfigLabel.setLayoutX(28.0);
    systemConfigLabel.setLayoutY(109.0);
    systemConfigLabel.setFont(new Font(14.0));
    getChildren().add(systemConfigLabel);

//        <Label layoutX="84.0" layoutY="141.0" text="threads" />
    Label threadsLabel = new Label("threads");
    threadsLabel.setLayoutX(84.0);
    threadsLabel.setLayoutY(141.0);
    getChildren().add(threadsLabel);

//        <TextField layoutX="133.0" layoutY="137.0" prefHeight="25.0" prefWidth="133.0" />
    TextField threadsTextField = new TextField(String.valueOf(generatorConfig.getThreads()));
    threadsTextField.setLayoutX(133.0);
    threadsTextField.setLayoutY(137.0);
    threadsTextField.setPrefHeight(25.0);
    threadsTextField.setPrefWidth(133.0);
    getChildren().add(threadsTextField);

//        <Label layoutX="349.0" layoutY="141.0" text="triesPerThread" />
    Label triesPerThreadLabel = new Label("triesPerThread");
    triesPerThreadLabel.setLayoutX(349.0);
    triesPerThreadLabel.setLayoutY(141.0);
    getChildren().add(triesPerThreadLabel);

//        <TextField layoutX="439.0" layoutY="137.0" prefHeight="25.0" prefWidth="133.0" />
    TextField triesPerThreadTextField = new TextField("");
    triesPerThreadTextField.setLayoutX(439.0);
    triesPerThreadTextField.setLayoutY(137.0);
    triesPerThreadTextField.setText(String.valueOf(generatorConfig.getTriesPerThread()));
    triesPerThreadTextField.setPrefHeight(25.0);
    triesPerThreadTextField.setPrefWidth(133.0);
    getChildren().add(triesPerThreadTextField);


//        <Label layoutX="28.0" layoutY="197.0" text="password config:">
//        <font>
//        <Font size="14.0" />
//        </font>
//        </Label>
    Label passwordConfigLabel = new Label("password config:");
    passwordConfigLabel.setLayoutX(28.0);
    passwordConfigLabel.setLayoutY(197.0);
    passwordConfigLabel.setFont(new Font(14.0));
    getChildren().add(passwordConfigLabel);

//        <Label layoutX="51.0" layoutY="234.0" text="passwordSize" />
    Label passwordSizeLabel = new Label("passwordSize");
    passwordSizeLabel.setLayoutX(51.0);
    passwordSizeLabel.setLayoutY(234.0);
    getChildren().add(passwordSizeLabel);

//        <TextField layoutX="133.0" layoutY="230.0" prefHeight="25.0" prefWidth="133.0" />
    TextField passwordSizeTextField = new TextField(String.valueOf(generatorConfig.getPasswordLength()));
    passwordSizeTextField.setLayoutX(133.0);
    passwordSizeTextField.setLayoutY(230.0);
    passwordSizeTextField.setPrefHeight(25.0);
    passwordSizeTextField.setPrefWidth(133.0);
    getChildren().add(passwordSizeTextField);

//        <Label layoutX="43.0" layoutY="275.0" text="passwordPrefix" />
    Label passwordPrefixLabel = new Label("passwordPrefix");
    passwordPrefixLabel.setLayoutX(43.0);
    passwordPrefixLabel.setLayoutY(275.0);
    getChildren().add(passwordPrefixLabel);


//        <TextField layoutX="133.0" layoutY="271.0" prefHeight="25.0" prefWidth="439.0" />
    TextField passwordPrefixTextField = new TextField(generatorConfig.getPrefix());
    passwordPrefixTextField.setLayoutX(133.0);
    passwordPrefixTextField.setLayoutY(271.0);
    passwordPrefixTextField.setPrefHeight(25.0);
    passwordPrefixTextField.setPrefWidth(439.0);
    getChildren().add(passwordPrefixTextField);

//        <Label layoutX="37.0" layoutY="314.0" text="passwordPostfix" />
    Label passwordPostfixLabel = new Label("passwordPostfix");
    passwordPostfixLabel.setLayoutX(37.0);
    passwordPostfixLabel.setLayoutY(314.0);
    getChildren().add(passwordPostfixLabel);

//        <TextField layoutX="133.0" layoutY="310.0" prefHeight="25.0" prefWidth="439.0" />
    TextField passwordPostfixTextField = new TextField(generatorConfig.getPostfix());
    passwordPostfixTextField.setLayoutX(133.0);
    passwordPostfixTextField.setLayoutY(310.0);
    passwordPostfixTextField.setPrefHeight(25.0);
    passwordPostfixTextField.setPrefWidth(439.0);
    getChildren().add(passwordPostfixTextField);


//        <Button mnemonicParsing="false" text="Start" GridPane.columnIndex="1" GridPane.rowIndex="5" />
    Button startButton = new Button(">> Start search! <<");
    startButton.setLayoutX(133.0);
    startButton.setLayoutY(360.0);
    startButton.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>()
    {
      @Override
      public void handle(MouseEvent event)
      {
        callback.onStart(new GeneratorConfig(Arrays.asList(keysTextField.getText().trim().split(",")),
                                             Integer.valueOf(threadsTextField.getText()),
                                             Long.valueOf(triesPerThreadTextField.getText()),
                                             Integer.valueOf(passwordSizeTextField.getText()),
                                             passwordPrefixTextField.getText(),
                                             passwordPostfixTextField.getText()));
      }
    });
    getChildren().add(startButton);
  }
}
