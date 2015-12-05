package puzzlesolver.ui.fx_2d;

import javafx.animation.AnimationTimer;
import javafx.scene.canvas.GraphicsContext;
import puzzlesolver.Solver;

public class SteppableAnimationTimer extends AnimationTimer {

  private final Solver solver;
  private final GraphicsContext gc;
  private final PuzzleRenderer puzzleRenderer;

  public SteppableAnimationTimer(Solver solver, GraphicsContext gc, PuzzleRenderer puzzleRenderer) {
    this.solver = solver;
    this.gc = gc;
    this.puzzleRenderer = puzzleRenderer;
  }

  public boolean nextStep() {
    return solver.nextStep();
  }

  @Override
  public void handle(long now) {
    puzzleRenderer.update(gc, solver);
  }
}
