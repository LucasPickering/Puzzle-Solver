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
        int xSize = solution.length;
        int ySize = solution[0].length;

        if (reset) {
            reset = false;
            return all(xSize, ySize, true);
        }

        boolean[][] result = new boolean[xSize][ySize];

        for (int x = 0; x < xSize; x++) {
            for (int y = 0; y < ySize; y++) {
                if (lastSolution[x][y] == null && solution[x][y] != null) {
                    result[x][y] = true;
                }
            }
        }

        return result;
    }

    private boolean[][] all(int xSize, int ySize, boolean state) {
        boolean[][] result = new boolean[xSize][ySize];
        if (!state) {
            return result;
        }
        for (int x = 0; x < xSize; x++) {
            for (int y = 0; y < ySize; y++) {
                result[x][y] = true;
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

        Piece[][] solution = solver.getSolution();
        int xSize = solution.length;
        int ySize = solution[0].length;
        boolean[][] needsRedraw = (lastSolution == null)
                || lastSolution.length != solution.length
                || lastSolution[0].length != solution[0].length
                ? all(xSize, ySize, true)
                : needsRedraw(solution);

        for (int x = 0; x < xSize; x++) {
            for (int y = 0; y < ySize; y++) {
                if (needsRedraw[x][y]) {
                    drawPiece(gc, solution[x][y], x, y, xSize, ySize);
                }
            }
        }

        lastSolution = solution.clone();
    }
}
