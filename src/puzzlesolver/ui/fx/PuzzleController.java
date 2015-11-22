package puzzlesolver.ui.fx;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import puzzlesolver.Point;
import puzzlesolver.Solver;
import puzzlesolver.constants.UIConstants;
import puzzlesolver.enums.Direction;
import puzzlesolver.ui.fx.renderers.SimplePuzzleRenderer;

public class PuzzleController {

  private final Solver solver;
  private final SimplePuzzleRenderer puzzleRenderer;

  public PuzzleController(Solver solver) {
    this.solver = solver;
    puzzleRenderer = new SimplePuzzleRenderer(solver);
  }

  public void openPuzzleWindow(ActionEvent event) {
    Parent root;
    try {
      root = FXMLLoader.load(getClass().getResource("puzzle.fxml"));
      Stage stage = new Stage();
      stage.setTitle("Puzzle!");
      stage.setScene(new Scene(root, getRequiredWidth(),
                               getRequiredHeight()));
      stage.show();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  public static Point globalPointFromLocalPoint(Point point, Direction direction, int pieceX,
                                                int pieceY) {
    return SimplePuzzleRenderer.globalPointFromLocalPoint(point, direction, pieceX, pieceY);
  }

  public void draw() {
    try {
      puzzleRenderer.update();
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
        // TODO
        break;
      case UIConstants.VISUAL_FANCY:
        // TODO
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
