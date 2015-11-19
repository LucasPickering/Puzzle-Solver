package puzzlesolver.test.ui;

import org.junit.Test;

import puzzlesolver.Point;
import puzzlesolver.constants.UI;
import puzzlesolver.enums.Direction;
import puzzlesolver.ui.fx.PuzzleController;

import static org.junit.Assert.assertEquals;

public class PuzzleControllerTest {

  Point p0_0 = new Point(0, 0);

  @Test
  public void testGetRequiredWidth() throws Exception {

  }

  @Test
  public void testGetRequiredHeight() throws Exception {

  }

  @Test
  public void testGlobalPointFromLocalPoint() throws Exception {
    assertEquals(
        new Point(UI.VISUAL_PIECE_PADDING, UI.VISUAL_PIECE_PADDING - (UI.VISUAL_PIECE_HEIGHT / 2)),
        PuzzleController.globalPointFromLocalPoint(p0_0, Direction.NORTH, 0, 0));
    assertEquals(
        new Point(UI.VISUAL_PIECE_PADDING, UI.VISUAL_PIECE_PADDING + (UI.VISUAL_PIECE_HEIGHT / 2)),
        PuzzleController.globalPointFromLocalPoint(p0_0, Direction.SOUTH, 0, 0));
    assertEquals(
        new Point(UI.VISUAL_PIECE_PADDING + (UI.VISUAL_PIECE_WIDTH / 2), UI.VISUAL_PIECE_PADDING),
        PuzzleController.globalPointFromLocalPoint(p0_0, Direction.EAST, 0, 0));
    assertEquals(
        new Point(UI.VISUAL_PIECE_PADDING - (UI.VISUAL_PIECE_WIDTH / 2), UI.VISUAL_PIECE_PADDING),
        PuzzleController.globalPointFromLocalPoint(p0_0, Direction.WEST, 0, 0));

    assertEquals(UI.VISUAL_PIECE_WIDTH,
                 PuzzleController.globalPointFromLocalPoint(p0_0, Direction.NORTH, 1, 0).x
                 - PuzzleController.globalPointFromLocalPoint(p0_0, Direction.NORTH, 0, 0).x,
                 0.01);

  }
}
