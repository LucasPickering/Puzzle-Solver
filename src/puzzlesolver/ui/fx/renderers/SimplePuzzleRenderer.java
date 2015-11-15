package puzzlesolver.ui.fx.renderers;

import javafx.scene.canvas.Canvas;
import javafx.scene.image.Image;

public class SimplePuzzleRenderer<Solver> implements Renderer<Solver> {

  Solver toDraw;
  private Canvas canvas;
  private Image img = null;

  @Override
  public void init(Solver toDraw, Canvas canvas) {
    this.toDraw = toDraw;
    this.canvas = canvas;
  }

  @Override
  public boolean setImage(Image img) {
    boolean wasNull = img == null;
    this.img = img;
    return wasNull;
  }

  @Override
  public void update() {
  }
}
