package puzzlesolver.test;

import org.junit.Before;
import org.junit.Test;

import puzzlesolver.Generator;
import puzzlesolver.Piece;
import puzzlesolver.enums.Direction;
import puzzlesolver.simple.SimpleGenerator;

import static org.junit.Assert.*;

public class PieceTest {

  Piece p1;

  @Before
  public void setUp() {
    Generator gen = new SimpleGenerator();
    gen.setSeed("test1".hashCode());
    p1 = gen.generate(1, 1)[0];
  }

  @Test
  public void testRotateMutation() {
    assertEquals(p1, p1.rotate(Direction.NORTH, Direction.NORTH));
    assertEquals(p1, p1.rotate(Direction.EAST, Direction.EAST));
    assertEquals(p1, p1.rotate(Direction.SOUTH, Direction.SOUTH));
    assertEquals(p1, p1.rotate(Direction.WEST, Direction.WEST));

    assertNotEquals(p1, p1.rotate(Direction.NORTH, Direction.EAST));
  }

  @Test
  public void testRotate() {
    Piece p1Rot1 = p1.copyRotate(Direction.NORTH, Direction.EAST);
    assertEquals(p1.getSide(Direction.NORTH), p1Rot1.getSide(Direction.EAST));
  }
}
