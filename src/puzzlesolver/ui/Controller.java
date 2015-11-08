package puzzlesolver.ui;

import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import puzzlesolver.Constants;

public class Controller {

  public Button generateButton;
  public Button solveButton;
  public TextField rowsField;
  public TextField columnsField;
  public ObservableList<String> renderTypes =
      FXCollections.observableArrayList(Constants.UI.TEXT,
                                        Constants.UI.VISUAL,
                                        Constants.UI.VISUAL_FANCY);
  public ChoiceBox<String> renderTypeChoiceBox = new ChoiceBox<>(renderTypes);

  {
    renderTypeChoiceBox.getSelectionModel()
        .selectedItemProperty()
        .addListener(this::changeRenderMode);
    System.out.print(renderTypeChoiceBox.getItems());
    renderTypeChoiceBox.getSelectionModel().select(Constants.UI.TEXT);
  }

  public void generate() {
    generateButton.setDisable(true);
    generateButton.setText("Generating...");

    // TODO
    generateButton.setDisable(false);
    generateButton.setText("Regenerate");
    solveButton.setDisable(false);
  }

  public void solve() {
    solveButton.setDisable(true);
    solveButton.setText("Solving...");
    // TODO
  }

  public void changeRenderMode(ObservableValue ov, String oldValue, String newValue) {
    if (!newValue.equals(oldValue)) {
      switch (newValue) {
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
