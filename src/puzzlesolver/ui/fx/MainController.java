package puzzlesolver.ui.fx;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.Timer;
import java.util.TimerTask;

import javafx.application.Application;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import puzzlesolver.Generator;
import puzzlesolver.Piece;
import puzzlesolver.Solver;
import puzzlesolver.constants.Constants;
import puzzlesolver.constants.UIConstants;
import puzzlesolver.simple.SimpleGenerator;

public class MainController extends Application implements Initializable {

  @FXML private Button generateButton;
  @FXML private Button solveButton = new Button(UIConstants.BUTTON_SHOW);
  @FXML private Button showButton = new Button(UIConstants.BUTTON_SOLVE);
  @FXML private TextField heightField;
  @FXML private TextField widthField;
  @FXML private ChoiceBox<String> renderTypeChoiceBox;
  @FXML private Slider rateSlider = new Slider();
  private ObservableList<String> renderTypes;
  Timer timer = null;
  @FXML private Solver solver;
  private PuzzleController puzzleController;
  private Piece[] puzzle;

  public static void main(String[] args) {
    launch(args);
  }

  @Override
  public void start(Stage primaryStage) throws Exception {
    Parent root = FXMLLoader.load(getClass().getResource("main_menu.fxml"));
    primaryStage.setTitle("Puzzle-O-Matic!");
    primaryStage.setResizable(false);
    Scene scene = new Scene(root, root.prefWidth(400), root.prefHeight(300));
    primaryStage.setScene(scene);
    primaryStage.show();
  }

  /**
   * Responds to {@link #generateButton} being pressed by generating a new puzzle.
   *
   * Uses {@link Constants#RANDOM_SEED} to generate a new puzzle.
   */
  @FXML
  private void generate(ActionEvent event) {
    generateButton.setDisable(true);
    generateButton.setText("Generating...");
    Constants.LOGGER.println(1, "Generating new puzzle.");

    Generator generator = new SimpleGenerator();
    generator.setSeed(Constants.RANDOM_SEED);

    try {
      puzzle = generator.generate(Integer.parseInt(widthField.getText()),
                                  Integer.parseInt(heightField.getText()));
    } catch (NumberFormatException e) {
      throw new IllegalArgumentException("Bad number input");
    } finally {
      generateButton.setDisable(false);
      generateButton.setText("Regenerate");
      solveButton.setDisable(false);
      puzzleController.openPuzzleWindow(event);
      showButton.setDisable(false);
    }
  }

  /**
   * Solves the puzzle stepwise, at a speed specified by {@link #rateSlider}.
   */
  @FXML
  private void solve() {
    if (timer != null) {
      timer.cancel();
    }
    switch (solveButton.getText()) {
      case UIConstants.BUTTON_SOLVE:
        solveButton.setText(UIConstants.BUTTON_CANCEL);
        solver.init(puzzle);

        timer = new Timer("solver", true);
        timer.scheduleAtFixedRate(new TimerTask() {
          @Override
          public void run() {
            try {
              puzzleController.draw();
            } catch (Exception e) {
              e.printStackTrace();
            }
            solver.nextStep();
          }
        }, 0, (long) rateSlider.getValue());
        break;
      case UIConstants.BUTTON_CANCEL:
        solveButton.setText(UIConstants.BUTTON_SOLVE);
    }
  }

  /**
   * Respond to a change in {@link #renderTypeChoiceBox} by changing the render mode in {@link
   * #puzzleController}.
   */
  @FXML
  private void changeRenderMode(ObservableValue ov, String oldValue, String newValue) {
    if (!newValue.equals(oldValue)) {
      puzzleController.setRenderMethod(newValue);
    }
  }

  @FXML
  @Override
  public void initialize(URL location, ResourceBundle resources) {
    puzzleController = new PuzzleController();
    puzzleController.init(solver);

    // Set up renderTypeChoiceBox
    renderTypeChoiceBox.getSelectionModel()
        .selectedItemProperty()
        .addListener(this::changeRenderMode);
    renderTypeChoiceBox.show();

    showButton.setOnAction(puzzleController::openPuzzleWindow);

    rateSlider.valueProperty().addListener((observable, oldValue, newValue) -> {
      if (!newValue.equals(oldValue) && timer != null) {
        solve();
      }
    });
  }
}
