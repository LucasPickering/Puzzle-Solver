package puzzlesolver.ui.fx;

import java.io.IOException;
import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import puzzlesolver.Point;
import puzzlesolver.Solver;
import puzzlesolver.constants.Constants;
import puzzlesolver.constants.UIConstants;
import puzzlesolver.enums.Direction;
import puzzlesolver.simple.SimpleSolver;
import puzzlesolver.ui.fx.renderers.PuzzleRenderer;
import puzzlesolver.ui.fx.renderers.SimpleVisualPuzzleRenderer;

public class PuzzleController implements Initializable {

  @FXML
  private AnchorPane puzzlePane;
  private PuzzleRenderer<Solver> puzzleRenderer = new SimpleVisualPuzzleRenderer();
  @FXML
  private Canvas puzzleCanvas = new Canvas(getRequiredWidth(), getRequiredHeight());
  private Solver solver = new SimpleSolver();
  private Stage stage;

  public static Point globalPointFromLocalPoint(Point point, Direction direction, int pieceX,
                                                int pieceY) {
    return SimpleVisualPuzzleRenderer.globalPointFromLocalPoint(point, direction, pieceX, pieceY);
  }

  @Override
  public void initialize(URL location, ResourceBundle resources) {
    if (solver == null) {
      solver = new SimpleSolver();
    }
    if (puzzleRenderer == null) {
      puzzleRenderer = new SimpleVisualPuzzleRenderer(solver);
    }
    if (puzzleCanvas == null) {
      puzzleCanvas = new Canvas(getRequiredWidth(), getRequiredHeight());
    }
  }

  public void init(Solver solver) {
    Objects.requireNonNull(solver);
    this.solver = solver;
    if (puzzleRenderer == null) {
      puzzleRenderer = new SimpleVisualPuzzleRenderer(solver);
    } else {
      puzzleRenderer.init(solver);
    }
  }

  public void openPuzzleWindow(ActionEvent event) {
    if (stage == null) {
      try {
        Parent root = FXMLLoader
            .load(getClass().getResource("puzzle.fxml"));
        stage = new Stage();
        stage.setResizable(false);
        stage.setTitle("Puzzle!");
        stage.setScene(new Scene(root, getRequiredWidth(),
                                 getRequiredHeight()));
        stage.setOnCloseRequest(event1 -> stage.hide());
      } catch (IOException e) {
        e.printStackTrace();
      }
    }
    stage.show();
  }

  public void draw() {
    try {
      puzzleRenderer.update(puzzleCanvas);
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  public void setRenderMethod(String renderMethod) {
    switch (renderMethod) {
      case UIConstants.TEXT_SIMPLE:
        // TODO
        break;
      case UIConstants.TEXT_FANCY:
        // TODO
        break;
      case UIConstants.VISUAL_SIMPLE:
        puzzleRenderer = new SimpleVisualPuzzleRenderer(solver);
        Constants.LOGGER.printf(1, "Changed render method to %s", renderMethod);
        break;
      case UIConstants.VISUAL_FANCY:
        puzzleRenderer = new SimpleVisualPuzzleRenderer(solver);
        Constants.LOGGER.printf(1, "Changed render method to %s", renderMethod);
        break;
      default:
        throw new IllegalArgumentException(renderMethod + " is not a valid render method");
    }
  }

  public double getRequiredWidth() {
    return puzzleRenderer.getRequiredWidth();
  }

  public double getRequiredHeight() {
    return puzzleRenderer.getRequiredHeight();
  }

}
