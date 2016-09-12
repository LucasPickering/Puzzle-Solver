package puzzlesolver.ui.fx_2d;

import javafx.scene.canvas.GraphicsContext;
import puzzlesolver.Piece;
import puzzlesolver.solver.Solver;

/**
 * A puzzle renderer that detects differences between results in order to render. This has the
 * advantage of not requiring that the puzzle be solved linearly.
 */
public class DeltaPuzzleRenderer extends PuzzleRenderer {

  private boolean reset = true;
  private Piece[][] lastSolution = null;

  private boolean[][] needsRedraw(Piece[][] solution) {
    final int width = solution.length;
    final int height = solution[0].length;
    final boolean[][] result = new boolean[width][height];

    for (int x = 0; x < width; x++) {
      for (int y = 0; y < height; y++) {
        // Re-draw a piece if it's not null and either it was null before, or we're
        // force re-drawing everything. Maybe we should compare hashcodes between each
        // piece? Slower, but if in the future a piece will every change after being
        // placed, it will support that.
        if (solution[x][y] != null && (reset || lastSolution[x][y] == null)) {
          result[x][y] = true;
        }
      }
    }

    return result;
  }

  protected void reset(GraphicsContext gc) {
    super.reset(gc);
    reset = true;
  }

  @Override
  protected void drawPuzzle(GraphicsContext gc, Solver solver) {
    if (done) {
      return;
    }

    final Piece[][] solution = solver.getSolution();
    final int width = solution.length;
    final int height = solution[0].length;

    // If this is the first draw, or the puzzle was rotated, re-draw everything.
    if (lastSolution == null || lastSolution.length != solution.length) {
      reset = true;
    }
    final boolean[][] needsRedraw = needsRedraw(solution);

    for (int x = 0; x < width; x++) {
      for (int y = 0; y < height; y++) {
        if (needsRedraw[x][y]) {
          drawPiece(gc, solution[x][y], x, y, width, height);
        }
      }
    }

    lastSolution = solution.clone();
  }
}
