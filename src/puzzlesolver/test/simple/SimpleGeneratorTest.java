package puzzlesolver.test.simple;

import org.junit.Test;

import puzzlesolver.Generator;
import puzzlesolver.Piece;
import puzzlesolver.Point;
import puzzlesolver.enums.Direction;
import puzzlesolver.simple.SimpleGenerator;
import puzzlesolver.simple.SimpleSide;

import static org.junit.Assert.assertTrue;

public class SimpleGeneratorTest {
  Generator generator = new SimpleGenerator();

  @Test
  public void testGenerate() throws Exception {
    testInRange(generator.generate(4, 4));
    testInRange(generator.generate(6, 6));
  }

  private void testInRange(Piece[] generated) {
    for (Piece piece : generated) {
      for (Direction d : Direction.values()) {
        Point[] points = ((SimpleSide)piece.getSide(d)).getPoints();
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
