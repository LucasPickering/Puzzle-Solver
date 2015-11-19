package puzzlesolver.ui.fx;

import javafx.scene.canvas.Canvas;
import puzzlesolver.Constants;
import puzzlesolver.Point;
import puzzlesolver.Solver;
import puzzlesolver.enums.Direction;
import puzzlesolver.ui.fx.renderers.SimplePuzzleRenderer;

public class PuzzleController {

  private final Solver solver;
  private final Canvas puzzleCanvas;
  private final SimplePuzzleRenderer puzzleRenderer;

  public PuzzleController(Solver solver, Canvas puzzleCanvas) {
    this.solver = solver;
    this.puzzleCanvas = puzzleCanvas;
    puzzleRenderer = new SimplePuzzleRenderer();
    puzzleRenderer.init(this.solver, this.puzzleCanvas);
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

  public static Point globalPointFromLocalPoint(Point point, Direction direction, int pieceX, int pieceY) {
    return SimplePuzzleRenderer.globalPointFromLocalPoint(point, direction, pieceX, pieceY);
  }
}
