package puzzlesolver.ui.fx_2d;

import javafx.concurrent.Service;
import javafx.concurrent.Task;
import puzzlesolver.PieceNotFoundException;
import puzzlesolver.solver.Solver;

public class PuzzleStepperService extends Service<Void> {

  private final Solver solver;

  public PuzzleStepperService(Solver solver) {
    this.solver = solver;
  }

  @Override
  protected Task<Void> createTask() {
    return new Task<Void>() {
      protected Void call() {
        try {
          while (!isCancelled() && !solver.done()) {
            solver.nextStep();
            updateProgress(solver.getPiecesPlaced(), solver.getTotalPieces());
            Thread.sleep(10);
          }
        } catch (PieceNotFoundException e) {
          e.printStackTrace();
        } catch (InterruptedException e) {
          // Do nothing. We tried to cancel while sleeping, just break out.
        }
        return null;
      }
    };
  }
}
