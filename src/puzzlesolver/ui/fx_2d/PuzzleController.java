package puzzlesolver.ui.fx_2d;

import java.util.Objects;
import java.util.Random;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import puzzlesolver.Point;
import puzzlesolver.solver.Solver;
import puzzlesolver.constants.Constants;
import puzzlesolver.constants.UIConstants;
import puzzlesolver.enums.Direction;
import puzzlesolver.solver.SimpleSolver;

public class PuzzleController {

  @FXML
  private AnchorPane puzzlePane;
  private Solver solver = new SimpleSolver();
  private PuzzleRenderer puzzleRenderer = new PuzzleRenderer();
  @FXML
  private Canvas puzzleCanvas;
  private Stage stage;
  private SteppableAnimationTimer animationTimer;
  private Random random = new Random(Constants.RANDOM_SEED);


  public static Point getGlobalPoint(Point localPoint, Direction orientation, int pieceX,
                                     int pieceY, int puzzleWidth, int puzzleHeight,
                                     double windowWidth, double windowHeight) {
    return PuzzleRenderer.getGlobalPoint(localPoint, orientation, pieceX, pieceY,
                                         puzzleWidth, puzzleHeight, windowWidth, windowHeight);
  }

  public void init(Solver solver) {
    Objects.requireNonNull(solver);
    deinitPuzzleWindow();
    this.solver = solver;
    stage = null;
    if (puzzleRenderer == null) {
      puzzleRenderer = new PuzzleRenderer();
    }
  }

  public void openPuzzleWindow(ActionEvent event) {
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

    GraphicsContext gc = puzzleCanvas.getGraphicsContext2D();
    root.getChildren().add(puzzleCanvas);
    stage.setScene(new Scene(root));
    puzzleCanvas.widthProperty().bind(stage.getScene().widthProperty());
    puzzleCanvas.heightProperty().bind(stage.getScene().heightProperty());
    stage.setTitle("Puzzle!");
    animationTimer = new SteppableAnimationTimer(solver, gc, puzzleRenderer);
    stage.setOnCloseRequest(event -> animationTimer.stop());
    stage.setOnShowing(event -> animationTimer.start());
    stage.getScene().setOnMouseClicked(new MouseEventHandler(animationTimer));
    stage.getScene().setFill(new Color(0.3 * random.nextDouble(),
                                       0.3 * random.nextDouble(),
                                       0.3 * random.nextDouble(), 1.0));
    stage.getScene().widthProperty().addListener(
        (observable, oldValue, newValue) -> puzzleRenderer.draw(gc, solver));
    stage.getScene().heightProperty().addListener(
        (observable, oldValue, newValue) -> puzzleRenderer.draw(gc, solver));

    if (puzzleRenderer == null) {
      puzzleRenderer = new PuzzleRenderer();
    } else {
      puzzleRenderer.reset(puzzleCanvas.getGraphicsContext2D());
    }
  }

  public void update() {
    puzzleRenderer.update(puzzleCanvas.getGraphicsContext2D(), solver);
  }

  public void draw() {
    puzzleRenderer.draw(puzzleCanvas.getGraphicsContext2D(), solver);
  }

  public boolean nextStep() {
    return animationTimer.nextStep();
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
        Constants.LOGGER.printf(1, "Changed render method to %s\n", renderMethod);
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
}
