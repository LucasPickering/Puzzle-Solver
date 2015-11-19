package puzzlesolver.ui.fx.renderers;

import javafx.scene.canvas.Canvas;
import javafx.scene.image.Image;

public interface PuzzleRenderer<T> {

  /**
   * Initialize the renderer with the object it will draw.
   *
   * {@param toDraw} will not be copied.
   *
   * @param toDraw the object to be drawn
   * @param canvas the canvas to be drawn to
   */
  void init(T toDraw, Canvas canvas);

  /**
   * Set the image to draw on the puzzle pieces
   *
   * @param img the image to be drawn on the puzzle pieces
   */
  void setImage(Image img);

  /**
   * Re-draw the scene
   */
  void update() throws Exception;
}
