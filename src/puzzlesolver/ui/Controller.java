package puzzlesolver.ui;

import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

public class Controller {

  public Label helloWorldLabel;
  public Button generateButton;
  public Button solveButton;

  public void sayHelloWorld(ActionEvent actionEvent) {
    helloWorldLabel.setText("Hello, world!");
  }

  public void generate(ActionEvent actionEvent) {
    generateButton.setDisable(true);
    generateButton.setText("Generating...");

    // TODO
    generateButton.setDisable(false);
    generateButton.setText("Regenerate");
    solveButton.setDisable(false);
  }

  public void solve(ActionEvent actionEvent) {
    solveButton.setDisable(true);
    solveButton.setText("Solving...");
    // TODO
  }
}
