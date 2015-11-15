package puzzlesolver.ui.fx.renderers;

import javafx.scene.canvas.Canvas;
import javafx.scene.image.Image;

public interface Renderer<T> {

  /**
   * Initialize the renderer with the object it will draw.
   *
   * {@param toDraw} will not be copied.
   *
   * @param toDraw the object to be drawn
   * @param canvas the canvas to be drawn to
   */
  void init(T toDraw, Canvas canvas);

  boolean setImage(Image img);

  /**
   * Re-draw the scene
   */
  void update();
}
