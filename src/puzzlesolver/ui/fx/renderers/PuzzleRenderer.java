package puzzlesolver.ui.fx.renderers;

import javax.naming.OperationNotSupportedException;

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
   * Re-draw the scene
   */
  void update() throws Exception;
}
