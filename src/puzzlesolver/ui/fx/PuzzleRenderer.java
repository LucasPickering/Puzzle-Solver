package puzzlesolver.ui.fx;

import javafx.scene.canvas.Canvas;
import puzzlesolver.Constants;
import puzzlesolver.Solver;

public class PuzzleRenderer {

  private final Solver solver;
  private final Canvas puzzleCanvas;

  public PuzzleRenderer(Solver solver, Canvas puzzleCanvas) {
    this.solver = solver;
    this.puzzleCanvas = puzzleCanvas;
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

  public int getRequiredWidth() {
    return Math.max(solver.getSolution().length * Constants.UI.VISUAL_PIECE_WIDTH
                    + Constants.UI.VISUAL_PIECE_PADDING * 2,
                    Constants.UI.WINDOW_MIN_WIDTH);
  }

  public int getRequiredHeight() {
    return Math.max(solver.getSolution()[0].length * Constants.UI.VISUAL_PIECE_HEIGHT
                    + Constants.UI.VISUAL_PIECE_PADDING * 2,
                    Constants.UI.WINDOW_MIN_HEIGHT);
  }

  public void draw() {
    // TODO
  }
}
