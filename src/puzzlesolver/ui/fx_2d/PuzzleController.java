package puzzlesolver.ui.fx_2d;

import java.util.Objects;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import puzzlesolver.Logger;
import puzzlesolver.constants.Constants;
import puzzlesolver.constants.UIConstants;
import puzzlesolver.solver.SimpleSolver;
import puzzlesolver.solver.Solver;

public class PuzzleController {

  private Solver solver = new SimpleSolver();
  private GraphicsContext gc;
  private PuzzleRenderer puzzleRenderer;
  @FXML
  private Canvas puzzleCanvas;
  private Stage stage;

  public void init(Solver solver) {
    Objects.requireNonNull(solver);
    deinitPuzzleWindow();
    this.solver = solver;
    stage = null;
    if (puzzleRenderer == null) {
      puzzleRenderer = new PuzzleRenderer();
    }
  }

  void openPuzzleWindow(ActionEvent event) {
    openPuzzleWindow();
  }

  void openPuzzleWindow() {
    if (stage == null) {
      setupPuzzleWindow();
    }
    stage.show();
  }

  private void setupPuzzleWindow() {
    puzzleCanvas = new Canvas(UIConstants.WINDOW_MIN_WIDTH,
                              UIConstants.WINDOW_MIN_HEIGHT);

    stage = new Stage();
    Group root = new Group();

    stage.setMinWidth(UIConstants.WINDOW_MIN_WIDTH);
    stage.setMinHeight(UIConstants.WINDOW_MIN_HEIGHT);

    gc = puzzleCanvas.getGraphicsContext2D();
    root.getChildren().add(puzzleCanvas);

    stage.setScene(new Scene(root));

    // Keep dimensions synchronised between canvas and stage
    puzzleCanvas.widthProperty().bind(stage.getScene().widthProperty());
    puzzleCanvas.heightProperty().bind(stage.getScene().heightProperty());

    stage.setTitle("Puzzle!");
    stage.getScene().setFill(new Color(0.3 * Constants.RANDOM.nextDouble(),
                                       0.3 * Constants.RANDOM.nextDouble(),
                                       0.3 * Constants.RANDOM.nextDouble(), 1.0));
    stage.getScene().widthProperty().addListener(
        (observable, oldValue, newValue) -> puzzleRenderer.update(gc, solver));
    stage.getScene().heightProperty().addListener(
        (observable, oldValue, newValue) -> puzzleRenderer.update(gc, solver));

    if (puzzleRenderer == null) {
      puzzleRenderer = new PuzzleRenderer();
    } else {
      puzzleRenderer.reset(puzzleCanvas.getGraphicsContext2D());
    }
  }

  public void setRenderMethod(String renderMethod) {
    switch (renderMethod) {
      case UIConstants.RENDER_TEXT_SIMPLE:
        // TODO
        break;
      case UIConstants.RENDER_TEXT_ADVANCED:
        // TODO
        break;
      case UIConstants.RENDER_VISUAL_SIMPLE:
        puzzleRenderer = new PuzzleRenderer();
        Constants.LOGGER.printf(Logger.INFO, "Changed render method to %s\n", renderMethod);
        break;
      case UIConstants.RENDER_VISUAL_FANCY:
        puzzleRenderer = new PuzzleRenderer();
        Constants.LOGGER.printf(1, "Changed render method to %s\n", renderMethod);
        break;
      default:
        throw new IllegalArgumentException(renderMethod + " is not a valid render method");
    }
  }

  public void closePuzzleWindow() {
    if (stage != null) {
      stage.close();
    }
  }

  public void deinitPuzzleWindow() {
    closePuzzleWindow();
    stage = null;
    puzzleCanvas = null;
  }

  public void update() {
    puzzleRenderer.update(gc, solver);
  }
}
