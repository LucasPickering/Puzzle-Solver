package puzzlesolver.ui;

import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import puzzlesolver.Constants;

public class Controller {

  public Label helloWorldLabel;
  public Button generateButton;
  public Button solveButton;
  public TextField rowsField;
  public TextField columnsField;
  public ObservableList<String> renderTypes =
      FXCollections.observableArrayList(Constants.UI.TEXT,
                                        Constants.UI.VISUAL,
                                        Constants.UI.VISUAL_FANCY);
  public ChoiceBox<String> renderTypeChoiceBox = new ChoiceBox<>(renderTypes);

  Controller() {
    renderTypeChoiceBox.getItems().addAll();
    renderTypeChoiceBox.getSelectionModel()
        .selectedIndexProperty()
        .addListener(this::changeRenderMode);
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

  public void changeRenderMode(ObservableValue ov, Number oldValue, Number newValue) {
    if (!newValue.equals(oldValue)) {
      switch ((String)ov.getValue()) {
        case Constants.UI.TEXT:
          // TODO
          break;
        case Constants.UI.VISUAL:
          // TODO
          break;
        case Constants.UI.VISUAL_FANCY:
          // TODO
          break;
      }
    }
  }
}
