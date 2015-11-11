package puzzlesolver.ui;

import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import puzzlesolver.Constants;
import puzzlesolver.Generator;
import puzzlesolver.Piece;
import puzzlesolver.simple.SimpleGenerator;

public class Controller {

  public Button generateButton;
  public Button solveButton;
  public TextField heightField;
  public TextField widthField;
  public ObservableList<String> renderTypes =
      FXCollections.observableArrayList(Constants.UI.TEXT,
                                        Constants.UI.VISUAL,
                                        Constants.UI.VISUAL_FANCY);
  public ChoiceBox<String> renderTypeChoiceBox = new ChoiceBox<>(renderTypes);

  public void setupChoiceBox() {
    renderTypeChoiceBox.getSelectionModel()
        .selectedItemProperty()
        .addListener(this::changeRenderMode);
    System.out.print(renderTypeChoiceBox.getItems());
    renderTypeChoiceBox.getSelectionModel().select(Constants.UI.TEXT);
    System.out.println(renderTypeChoiceBox.getSelectionModel().selectedItemProperty().getValue());
  }

  private Piece[][] puzzle;

  public void generate() {
    generateButton.setDisable(true);
    generateButton.setText("Generating...");

    Generator generator = new SimpleGenerator();

    try {
      generator.generate(Integer.parseInt(widthField.getText()),
      Integer.parseInt(heightField.getText()));
    } catch (NumberFormatException e) {
      // TODO some kind of pop-up box or notifier for bad data
      return;
    }

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
