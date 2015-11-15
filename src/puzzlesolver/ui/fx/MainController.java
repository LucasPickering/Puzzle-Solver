package puzzlesolver.ui.fx;

import javafx.application.Application;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import puzzlesolver.Constants;
import puzzlesolver.Generator;
import puzzlesolver.Piece;
import puzzlesolver.Solver;
import puzzlesolver.simple.SimpleGenerator;
import puzzlesolver.simple.SimpleSolver;

public class MainController extends Application {

  public static void main(String[] args) {
    launch(args);
  }

  @Override
  public void start(Stage primaryStage) throws Exception {
    Parent root = FXMLLoader.load(getClass().getResource("main_menu.fxml"));
    this.primaryStage = primaryStage;
    primaryStage.setTitle("Puzzle-O-Matic!");
    primaryStage.setResizable(false);
    scene = new Scene(root, root.prefWidth(400), root.prefHeight(300));
    primaryStage.setScene(scene);
    primaryStage.show();

    // Set up renderTypeChoiceBox
    renderTypeChoiceBox.getSelectionModel()
        .selectedItemProperty()
        .addListener(this::changeRenderMode);
    renderTypeChoiceBox.show();
  }

  private Stage primaryStage;
  private Scene scene;

  public Button generateButton;
  public Button solveButton;
  public TextField heightField;
  public TextField widthField;
  public final ObservableList<String> renderTypes =
      FXCollections.observableArrayList(Constants.UI.TEXT_SIMPLE,
                                        Constants.UI.TEXT_FANCY,
                                        Constants.UI.VISUAL,
                                        Constants.UI.VISUAL_FANCY);
  public final ChoiceBox<String> renderTypeChoiceBox = new ChoiceBox<>(renderTypes);

  private Piece[] puzzle;

  public void generate() {
    generateButton.setDisable(true);
    generateButton.setText("Generating...");

    Generator generator = new SimpleGenerator();

    try {
      puzzle = generator.generate(Integer.parseInt(widthField.getText()),
                                  Integer.parseInt(heightField.getText()));
    } catch (NumberFormatException e) {
      // TODO some kind of pop-up box or notifier for bad data
    } finally {
      generateButton.setDisable(false);
      generateButton.setText("Regenerate");
      solveButton.setDisable(false);
    }
  }

  public void solve() {
    solveButton.setDisable(true);
    solveButton.setText("Solving...");
    Solver solver = new SimpleSolver();
    solver.init(puzzle);

    // TODO
  }

  public void changeRenderMode(ObservableValue ov, String oldValue, String newValue) {
    if (!newValue.equals(oldValue)) {
      switch (newValue) {
        case Constants.UI.TEXT_SIMPLE:
          // TODO
          break;
        case Constants.UI.TEXT_FANCY:
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
