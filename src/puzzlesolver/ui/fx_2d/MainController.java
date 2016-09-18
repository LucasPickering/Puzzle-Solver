package puzzlesolver.ui.fx_2d;

import java.io.FileNotFoundException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.application.Application;
import javafx.beans.value.ObservableValue;
import javafx.concurrent.ScheduledService;
import javafx.concurrent.Task;
import javafx.concurrent.Worker;
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
import javafx.util.Duration;
import puzzlesolver.Logger;
import puzzlesolver.Piece;
import puzzlesolver.constants.Constants;
import puzzlesolver.constants.UIConstants;
import puzzlesolver.generator.Generator;
import puzzlesolver.generator.PolypointGenerator;
import puzzlesolver.solver.GreedySolver;
import puzzlesolver.solver.Solver;

public class MainController extends Application implements Initializable {

  @FXML
  private Button generateButton;
  @FXML
  private Button resetButton;
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
  private Solver solver = new GreedySolver();
  private Piece[] lastPieces;
  private PuzzleController puzzleController;
  private PuzzleStepperService puzzleStepperService;
  private ScheduledService<Void> rendererService;

  public static void main(String[] args) {
    launch(args);
  }

  @Override
  public void start(Stage primaryStage) throws Exception {
    getParameters();
    URL thing = Thread.currentThread().getContextClassLoader().getResource("main_menu.fxml");
    if (thing == null) {
      throw new FileNotFoundException("Could not load resource main_menu.fxml");
    }

    Parent root = FXMLLoader.load(thing);
    primaryStage.setTitle("Puzzleopolis!");
    primaryStage.setResizable(false);
    Scene scene = new Scene(root, root.prefWidth(400), root.prefHeight(300));
    primaryStage.setScene(scene);
    primaryStage.setAlwaysOnTop(true);
    primaryStage.show();
  }

  /**
   * Responds to {@link #generateButton} being pressed by generating a new puzzle.
   *
   * Uses {@link Constants#RANDOM} to generate a new puzzle.
   */
  @FXML
  private void generate() {
    generateButton.setDisable(true);
    resetButton.setDisable(true);
    solveButton.setDisable(true);
    showButton.setDisable(true);

    Constants.LOGGER.println(Logger.INFO, "Generating new puzzle. . .");

    Generator generator = new PolypointGenerator();
    try {
      final int width = Integer.parseInt(widthField.getText());
      final int height = Integer.parseInt(heightField.getText());
      initSolver(generator.generate(width, height));
    } catch (NumberFormatException e) {
      throw new IllegalArgumentException("Bad number input");
    } finally {
      generateButton.setDisable(false);
      resetButton.setDisable(false);
      solveButton.setDisable(false);
      showButton.setDisable(false);
    }
  }

  @FXML
  private void reset() {
    initSolver(lastPieces); // Re-init the solver

    // Enable all buttons
    generateButton.setDisable(false);
    resetButton.setDisable(false);
    solveButton.setDisable(false);
    showButton.setDisable(false);
  }

  private void initSolver(Piece[] pieces) {
    lastPieces = pieces;
    puzzleController.init(solver);
    puzzleStepperService.reset();
    solver.init(pieces);
    puzzleController.openPuzzleWindow();
  }

  /**
   * Starts/stops the solving process.
   */
  @FXML
  private void solve() {
    switch (solveButton.getText()) {
      case UIConstants.BUTTON_SOLVE:
        puzzleStepperService.start(); // Start the solver service
        break;
      case UIConstants.BUTTON_STOP:
        puzzleStepperService.cancel(); // Stop the solver (it will do it gracefully I promise)
        puzzleStepperService.reset(); // Reset the service we can start it again'
        break;
    }
  }

  private void onSolveStarted() {
    // Set all relevant button text/disability
    solveButton.setText(UIConstants.BUTTON_STOP);
    generateButton.setDisable(true);
    resetButton.setDisable(true);
  }

  private void onSolveStopped() {
    // Set all relevant button text/disability
    solveButton.setText(UIConstants.BUTTON_SOLVE); // Set text back to solve
    generateButton.setDisable(false);
    resetButton.setDisable(false);

    // If the solver finished, disable the solve button
    if (puzzleStepperService.getState() == Worker.State.SUCCEEDED) {
      solveButton.setDisable(true);
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

    // Initialize the service that actually runs the solver
    puzzleStepperService = new PuzzleStepperService(solver);
    puzzleStepperService.setOnRunning(event -> onSolveStarted()); // Called when the solve starts
    puzzleStepperService.setOnCancelled(event -> onSolveStopped()); // Called when the solve pauses
    puzzleStepperService.setOnSucceeded(event -> onSolveStopped()); // Called when the solve ends

    rendererService = new ScheduledService<Void>() {
      @Override
      protected Task<Void> createTask() {
        return new Task<Void>() {
          @Override
          protected Void call() throws Exception {
            puzzleController.update();
            return null;
          }
        };
      }
    };
    rendererService.setPeriod(Duration.millis(10));
    rendererService.setDelay(Duration.millis(10));
    rendererService.start();

    // Set up renderTypeChoiceBox
    renderTypeChoiceBox.getSelectionModel()
        .selectedItemProperty()
        .addListener(this::changeRenderMode);
    renderTypeChoiceBox.getSelectionModel().select(UIConstants.RENDER_VISUAL_FANCY);
    renderTypeChoiceBox.show();

    showButton.setOnAction(puzzleController::openPuzzleWindow);

    rateSlider.valueProperty().addListener((observable, oldValue, newValue) ->
                                               solveButton.setText(UIConstants.BUTTON_SOLVE));
  }
}
