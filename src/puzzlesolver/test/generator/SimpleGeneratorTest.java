package puzzlesolver.test.generator;

import org.junit.Test;

import puzzlesolver.generator.Generator;
import puzzlesolver.Piece;
import puzzlesolver.Point;
import puzzlesolver.side.Side;
import puzzlesolver.enums.Direction;
import puzzlesolver.generator.SimpleGenerator;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class SimpleGeneratorTest {

  Generator generator = new SimpleGenerator();

  @Test
  public void testGenerate() {
    testInRange(generator.generate(4, 4));
    testInRange(generator.generate(6, 6));
  }

  @Test
  public void testFlat() {
    Side side = generator.generateSide(true);
    assertTrue(side.isFlat());
    assertEquals(2, side.getPoints().length);
  }

  @Test
  public void testNotFlat() {
    Side side = generator.generateSide(false);
    assertFalse(side.isFlat());
    assertEquals(3, side.getPoints().length);
  }

  private void testInRange(Piece[] generated) {
    for (Piece piece : generated) {
      for (Direction d : Direction.values()) {
        Point[] points = piece.getSide(d).getPoints();
        if (points.length != 2) {
          Point start = points[0];
          Point end = points[points.length - 1];
          for (int i = 1; i < points.length - 1; i++) {
            Point point = points[i];
            assertTrue(point.x >= start.x);
            assertTrue(point.x <= end.x);
          }
        }
      }
    }
  }
}
