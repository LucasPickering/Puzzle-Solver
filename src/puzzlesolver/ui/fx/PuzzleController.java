package puzzlesolver.ui.fx;

import javafx.scene.canvas.Canvas;
import puzzlesolver.Point;
import puzzlesolver.Solver;
import puzzlesolver.constants.UIConstants;
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
