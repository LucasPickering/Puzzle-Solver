package puzzlesolver.ui.fx_2d;

import javafx.concurrent.Task;
import puzzlesolver.solver.Solver;

public class PuzzleStepperTask extends Task<Void> {

  private final Solver solver;

  public PuzzleStepperTask(Solver solver) {
    this.solver = solver;
  }

  @Override
  public Void call() throws Exception {
    while (!isCancelled() && !solver.done()) {
      solver.nextStep();
      updateProgress(solver.getPiecesPlaced(), solver.getTotalPieces());
    }
    return null;
  }
}
