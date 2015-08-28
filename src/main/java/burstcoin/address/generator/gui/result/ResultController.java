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

package burstcoin.address.generator.gui.result;

import burstcoin.address.generator.core.GenerateActor;
import burstcoin.address.generator.core.GeneratorConfig;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.input.MouseEvent;
import javafx.util.Duration;

import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.net.URL;
import java.util.ResourceBundle;


/**
 * The type Result controller.
 */
public class ResultController
  implements Initializable, GenerateActor.Callback
{
  /**
   * The Result table view.
   */
  @FXML
  TableView<ResultModel> resultTableView;

  /**
   * The Progress.
   */
  public ProgressBar progress;
  /**
   * The Password text area.
   */
  public TextArea passwordTextArea;
  /**
   * The Label.
   */
  public Label label;
  /**
   * The Button.
   */
  public Button button;

  // data

  private double progressValue;
  private long targetCount;
  private long currentCount;
  private String speed;
  private int threads;

  private long dataUpdateCount = 0;
  private long updateCount = 0;

  @Override
  public void initialize(URL location, ResourceBundle resources)
  {
    button.setText("Copy passphrase to Clipboard");
    button.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>()
    {
      @Override
      public void handle(MouseEvent event)
      {
        Toolkit defaultToolkit = Toolkit.getDefaultToolkit();
        Clipboard clipboard = defaultToolkit.getSystemClipboard();
        clipboard.setContents(new StringSelection(passwordTextArea.getText()), null);

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Success");
        alert.setHeaderText("Passphrase in clipboard!");
        alert.setContentText(passwordTextArea.getText());
        alert.showAndWait();
      }
    });

    resultTableView.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<ResultModel>()
    {
      @Override
      public void changed(ObservableValue<? extends ResultModel> observable, ResultModel oldValue, ResultModel newValue)
      {
        onSelectionChange(newValue);
      }
    });

    Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(0.5), ev -> {
      onViewUpdate();
    }));
    timeline.setCycleCount(Animation.INDEFINITE);
    timeline.play();
  }

  private void onViewUpdate()
  {
    updateCount++;
    int percentage = (int) Math.ceil(progressValue * 100);
    label.setText(threads + " threads @ " + speed + " ... " + String.valueOf(percentage) + "% done [" + currentCount + "/" + targetCount + " tries]");

    if(percentage >= 100)
    {
      label.setText(String.valueOf(percentage + "% done ... " + targetCount + " tries finished!"));
    }
  }

  private void onSelectionChange(ResultModel selected)
  {
    if(selected != null)
    {
      passwordTextArea.setText(selected.getPassword());
      passwordTextArea.selectAll();
    }
    else
    {
      passwordTextArea.clear();
    }
  }

  @Override
  public void updateProgress(double progressValue, long targetCount, long currentCount, GeneratorConfig config, String speed)
  {
    this.progressValue = progressValue;
    this.targetCount = targetCount;
    this.currentCount = currentCount;
    this.speed = speed;
    threads = config.getThreads();
    progress.setProgress(progressValue);
    dataUpdateCount++;
  }

  @Override
  public void onResult(ResultModel resultModel)
  {
    resultTableView.getItems().add(resultModel);
  }
}
