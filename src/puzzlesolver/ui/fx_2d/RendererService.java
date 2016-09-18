package puzzlesolver.ui.fx_2d;

import javafx.concurrent.ScheduledService;
import javafx.concurrent.Task;

/**
 * A service that periodically renders the puzzle. This only needs to be called once, after
 * initializing it with timing settings.
 */
public class RendererService extends ScheduledService<Void> {

  private final PuzzleController puzzleController;

  public RendererService(PuzzleController puzzleController) {
    this.puzzleController = puzzleController;
  }

  @Override
  protected Task<Void> createTask() {
    return new Task<Void>() {
      @Override
      protected Void call() throws Exception {
        puzzleController.update();
        return null;
      }
    };
  }
}
