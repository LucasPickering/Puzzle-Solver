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
    p1 = gen.generate(3, 3)[4];
  }

  @Test
  public void testRotateMutation() {
    assertEquals(p1, p1.rotate(Direction.NORTH, Direction.NORTH));
    assertEquals(p1, p1.rotate(Direction.EAST, Direction.EAST));
    assertEquals(p1, p1.rotate(Direction.SOUTH, Direction.SOUTH));
    assertEquals(p1, p1.rotate(Direction.WEST, Direction.WEST));

    assertNotEquals(p1, p1.copyRotate(Direction.NORTH, Direction.EAST));
  }

  @Test
  public void testRotate() {
    Piece p1Rot1 = p1.copyRotate(Direction.NORTH, Direction.EAST);
    assertEquals(p1.getSide(Direction.NORTH), p1Rot1.getSide(Direction.EAST));
    assertEquals(p1.getSide(Direction.EAST), p1Rot1.getSide(Direction.SOUTH));
    assertEquals(p1.getSide(Direction.SOUTH), p1Rot1.getSide(Direction.WEST));
    assertEquals(p1.getSide(Direction.WEST), p1Rot1.getSide(Direction.NORTH));

    Piece p1Rot2 = p1.copyRotate(Direction.NORTH, Direction.SOUTH);
    assertEquals(p1.getSide(Direction.NORTH), p1Rot2.getSide(Direction.SOUTH));
    assertEquals(p1.getSide(Direction.EAST), p1Rot2.getSide(Direction.WEST));
    assertEquals(p1.getSide(Direction.SOUTH), p1Rot2.getSide(Direction.NORTH));
    assertEquals(p1.getSide(Direction.WEST), p1Rot2.getSide(Direction.EAST));

    Piece p1Rot3 = p1.copyRotate(Direction.NORTH, Direction.WEST);
    assertEquals(p1.getSide(Direction.NORTH), p1Rot3.getSide(Direction.WEST));
    assertEquals(p1.getSide(Direction.EAST), p1Rot3.getSide(Direction.NORTH));
    assertEquals(p1.getSide(Direction.SOUTH), p1Rot3.getSide(Direction.EAST));
    assertEquals(p1.getSide(Direction.WEST), p1Rot3.getSide(Direction.SOUTH));

    Piece p1Rot4 = p1.copyRotate(Direction.WEST, Direction.NORTH);
    assertEquals(p1.getSide(Direction.NORTH), p1Rot4.getSide(Direction.EAST));
    assertEquals(p1.getSide(Direction.EAST), p1Rot4.getSide(Direction.SOUTH));
    assertEquals(p1.getSide(Direction.SOUTH), p1Rot4.getSide(Direction.WEST));
    assertEquals(p1.getSide(Direction.WEST), p1Rot4.getSide(Direction.NORTH));
  }
}
