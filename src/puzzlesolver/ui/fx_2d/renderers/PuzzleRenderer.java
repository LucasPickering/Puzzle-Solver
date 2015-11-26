package puzzlesolver.ui.fx_2d.renderers;

import javax.naming.OperationNotSupportedException;

import javafx.scene.canvas.Canvas;
import javafx.scene.image.Image;

public interface PuzzleRenderer<T> {

  /**
   * Initialize the renderer with the object it will draw.
   *
   * {@param toDraw} will not be copied.
   *
   * @param toDraw the object to be drawn
   */
  void init(T toDraw);

  /**
   * Set the image to draw on the puzzle pieces
   *
   * @param img the image to be drawn on the puzzle pieces
   */
  default void setImage(Image img) throws OperationNotSupportedException {
    throw new OperationNotSupportedException("setImage not supported in this PuzzleRenderer");
  }

  /**
   * Get the required width of the renderer window (in pixels).
   *
   * @return required width of the renderer window (in pixels)
   */
  double getRequiredWidth();

  /**
   * Get the required height of the renderer window (in pixels).
   *
   * @return required height of the renderer window (in pixels)
   */
  double getRequiredHeight();

  /**
   * Re-draw the scene
   * @param canvas {@link Canvas} to draw on
   */
  void update(Canvas canvas) throws Exception;
}
