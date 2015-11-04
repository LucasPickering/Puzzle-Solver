package puzzlesolver.ui;

import javafx.event.ActionEvent;
import javafx.scene.control.Label;

public class Controller {

  public Label helloWorld;

  public void sayHelloWorld(ActionEvent actionEvent) {
    helloWorld.setText("Hello, world!");
  }
}
