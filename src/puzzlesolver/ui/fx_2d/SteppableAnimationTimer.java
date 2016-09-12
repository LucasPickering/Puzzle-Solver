package puzzlesolver.ui.fx_2d;

import javafx.animation.AnimationTimer;
import javafx.scene.canvas.GraphicsContext;
import puzzlesolver.PieceNotFoundException;
import puzzlesolver.solver.Solver;

class SteppableAnimationTimer extends AnimationTimer {

  private final Solver solver;
  private final GraphicsContext gc;
  private final PuzzleRenderer puzzleRenderer;

  SteppableAnimationTimer(Solver solver, GraphicsContext gc, PuzzleRenderer puzzleRenderer) {
    this.solver = solver;
    this.gc = gc;
    this.puzzleRenderer = puzzleRenderer;
  }

  public boolean nextStep() {
    try {
      return solver.nextStep();
    } catch (PieceNotFoundException e) {
      e.printStackTrace();
      return true;
    }
  }

  @Override
  public void handle(long now) {
    puzzleRenderer.update(gc, solver);
  }
}
