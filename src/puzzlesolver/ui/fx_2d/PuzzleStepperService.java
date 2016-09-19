package puzzlesolver.ui.fx_2d;

import javafx.concurrent.Service;
import javafx.concurrent.Task;
import puzzlesolver.PieceNotFoundException;
import puzzlesolver.solver.Solver;

/**
 * A service that is called once to start solving the puzzle. This can be started with
 * {@link #start} and stopped with {@link #cancel}. Note that after cancelling you have to call
 * {@link #reset} before starting again.
 */
public class PuzzleStepperService extends Service<Void> {

  private final Solver solver;
  private volatile double loopDelay;

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
            Thread.sleep((int) loopDelay);
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

  public void setLoopDelay(double loopDelay) {
    this.loopDelay = loopDelay;
  }
}
