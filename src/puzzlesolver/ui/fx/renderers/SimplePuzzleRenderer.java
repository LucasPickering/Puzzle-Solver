package puzzlesolver.ui.fx.renderers;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

public class SimplePuzzleRenderer<Solver> implements Renderer<Solver> {

  Solver toDraw;
  private Canvas canvas;
  private GraphicsContext gc;
  private Image img = null;

  @Override
  public void init(Solver toDraw, Canvas canvas) {
    this.toDraw = toDraw;
    this.canvas = canvas;
    gc = canvas.getGraphicsContext2D();
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
