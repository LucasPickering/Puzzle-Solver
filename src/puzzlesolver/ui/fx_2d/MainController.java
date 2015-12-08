package puzzlesolver.ui.fx_2d;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.Timer;
import java.util.TimerTask;

import javafx.application.Application;
import javafx.beans.value.ObservableValue;
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
import puzzlesolver.generator.Generator;
import puzzlesolver.Piece;
import puzzlesolver.solver.Solver;
import puzzlesolver.constants.Constants;
import puzzlesolver.constants.UIConstants;
import puzzlesolver.generator.PolypointGenerator;
import puzzlesolver.solver.RotationSolver;

public class MainController extends Application implements Initializable {

  Timer timer = null;
  @FXML
  private TextField seedField;
  @FXML
  private Button generateButton;
  @FXML
  private Button solveButton = new Button(UIConstants.BUTTON_SHOW);
  @FXML
  private Button showButton = new Button(UIConstants.BUTTON_SOLVE);
  @FXML
  private TextField heightField;
  @FXML
  private TextField widthField;
  @FXML
  private ChoiceBox<String> renderTypeChoiceBox;
  @FXML
  private Slider rateSlider = new Slider();
  @FXML
  private Solver solver;
  private PuzzleController puzzleController;
  private Piece[] puzzle;
  private boolean stopSolve;

  public static void main(String[] args) {
    launch(args);
  }

  @Override
  public void start(Stage primaryStage) throws Exception {
    getParameters();
    // TODO check for seed
    Parent root = FXMLLoader.load(getClass().getResource("main_menu.fxml"));
    primaryStage.setTitle("Puzzle-O-Matic!");
    primaryStage.setResizable(false);
    Scene scene = new Scene(root, root.prefWidth(400), root.prefHeight(300));
    primaryStage.setScene(scene);
    primaryStage.setAlwaysOnTop(true);
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
    solveButton.setDisable(true);
    showButton.setDisable(true);

    switch (generateButton.getText()) {
      case UIConstants.BUTTON_REGENERATE:
        if (timer != null) {
          timer.cancel();
        }
        // Falls through to the next case
      case UIConstants.BUTTON_GENERATE:
        Constants.LOGGER.println(1, "Generating new puzzle.");

        Generator generator = new PolypointGenerator();
        if (puzzle == null) {
          if (seedField.getText() != null && !seedField.getText().equals("")) {
            try {
              generator.setSeed(Long.parseLong(seedField.getText()));
            } catch (NumberFormatException e) {
              generator.setSeed(seedField.getText().hashCode());
            }
          }
        }

        try {
          puzzle = generator.generate(Integer.parseInt(widthField.getText()),
                                      Integer.parseInt(heightField.getText()));
          solver.init(puzzle);
          puzzleController.init(solver);
          puzzleController.openPuzzleWindow(event);
          generateButton.setText(UIConstants.BUTTON_REGENERATE);
        } catch (NumberFormatException e) {
          throw new IllegalArgumentException("Bad number input");
        } finally {
          solveButton.setDisable(false);
          generateButton.setDisable(false);
          showButton.setDisable(false);
        }
        break;
    }
  }

  /**
   * Solves the puzzle stepwise, at a speed specified by {@link #rateSlider}.
   */
  @FXML
  private void solve() {
    switch (solveButton.getText()) {
      case UIConstants.BUTTON_SOLVE:
        if (stopSolve) {
          stopSolve = false;
        }
        solveButton.setText(UIConstants.BUTTON_STOP);

        if (timer != null) {
          timer.cancel();
        }
        timer = new Timer("Solve and Render", true);
        timer.scheduleAtFixedRate(new TimerTask() {
          @Override
          public void run() {
            try {
              if (stopSolve) {
                this.cancel();
              } else {
                if (puzzleController.nextStep()) {
                  this.cancel();
                }
              }
            } catch (Exception e) {
              e.printStackTrace();
            }
          }
        }, 0, (long) rateSlider.getValue());

        break;
      case UIConstants.BUTTON_STOP:
        stopSolve = true;
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
    if (solver == null) {
      solver = new RotationSolver();
    }
    puzzleController.init(solver);

    // Set up renderTypeChoiceBox
    renderTypeChoiceBox.getSelectionModel()
                       .selectedItemProperty()
                       .addListener(this::changeRenderMode);
    renderTypeChoiceBox.getSelectionModel().select(UIConstants.RENDER_VISUAL_FANCY);
    renderTypeChoiceBox.show();

    showButton.setOnAction(puzzleController::openPuzzleWindow);

    rateSlider.valueProperty().addListener((observable, oldValue, newValue) -> {
      if (!newValue.equals(oldValue) && timer != null) {
        stopSolve = true;
      }
    });

    seedField.setText(Long.toString(Constants.RANDOM_SEED));
  }
}
