package puzzlesolver.ui.fx_2d;

import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;

public class MouseEventHandler implements EventHandler<MouseEvent> {

  private final SteppableAnimationTimer timer;

  public MouseEventHandler(SteppableAnimationTimer timer) {
    this.timer = timer;
  }

  @Override
  public void handle(MouseEvent event) {
    timer.nextStep();
  }
}
