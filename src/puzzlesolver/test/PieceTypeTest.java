package puzzlesolver.test;

import org.junit.Before;
import org.junit.Test;

import puzzlesolver.Piece;
import puzzlesolver.Point;
import puzzlesolver.side.Side;
import puzzlesolver.constants.Constants;
import puzzlesolver.enums.Direction;
import puzzlesolver.enums.PieceType;
import puzzlesolver.side.SimpleSide;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class PieceTypeTest {

  private Piece cornerPiece;
  private Piece edgePiece;
  private Piece allInPiece;
  private Piece allOutPiece;
  private Piece threeInPiece;
  private Piece threeOutPiece;
  private Piece oppositesPiece;
  private Piece adjacentsPiece;

  @Before
  public void setUp() {
    final Side inSide = new SimpleSide(new Point(0, 0), new Point(5d, -0.5d),
                                       new Point(Constants.SIDE_LENGTH, 0));
    final Side outSide = new SimpleSide(new Point(0, 0), new Point(5d, 0.5d),
                                        new Point(Constants.SIDE_LENGTH, 0));
    final Side flatSide = new SimpleSide(new Point(0, 0), new Point(Constants.SIDE_LENGTH, 0));

    Piece.Builder cornerBuilder = new Piece.Builder();
    cornerBuilder.setSide(flatSide, Direction.NORTH);
    cornerBuilder.setSide(flatSide, Direction.EAST);
    cornerBuilder.setSide(inSide, Direction.SOUTH);
    cornerBuilder.setSide(inSide, Direction.WEST);
    cornerPiece = cornerBuilder.build();

    Piece.Builder edgeBuilder = new Piece.Builder();
    edgeBuilder.setSide(flatSide, Direction.NORTH);
    edgeBuilder.setSide(inSide, Direction.EAST);
    edgeBuilder.setSide(inSide, Direction.SOUTH);
    edgeBuilder.setSide(inSide, Direction.WEST);
    edgePiece = edgeBuilder.build();

    Piece.Builder allInBuilder = new Piece.Builder();
    allInBuilder.setSide(inSide, Direction.NORTH);
    allInBuilder.setSide(inSide, Direction.EAST);
    allInBuilder.setSide(inSide, Direction.SOUTH);
    allInBuilder.setSide(inSide, Direction.WEST);
    allInPiece = allInBuilder.build();

    Piece.Builder allOutBuilder = new Piece.Builder();
    allOutBuilder.setSide(outSide, Direction.NORTH);
    allOutBuilder.setSide(outSide, Direction.EAST);
    allOutBuilder.setSide(outSide, Direction.SOUTH);
    allOutBuilder.setSide(outSide, Direction.WEST);
    allOutPiece = allOutBuilder.build();

    Piece.Builder threeInBuilder = new Piece.Builder();
    threeInBuilder.setSide(outSide, Direction.NORTH);
    threeInBuilder.setSide(inSide, Direction.EAST);
    threeInBuilder.setSide(inSide, Direction.SOUTH);
    threeInBuilder.setSide(inSide, Direction.WEST);
    threeInPiece = threeInBuilder.build();

    Piece.Builder threeOutBuilder = new Piece.Builder();
    threeOutBuilder.setSide(outSide, Direction.NORTH);
    threeOutBuilder.setSide(outSide, Direction.EAST);
    threeOutBuilder.setSide(outSide, Direction.SOUTH);
    threeOutBuilder.setSide(inSide, Direction.WEST);
    threeOutPiece = threeOutBuilder.build();

    Piece.Builder oppositesBuilder = new Piece.Builder();
    oppositesBuilder.setSide(outSide, Direction.NORTH);
    oppositesBuilder.setSide(inSide, Direction.EAST);
    oppositesBuilder.setSide(outSide, Direction.SOUTH);
    oppositesBuilder.setSide(inSide, Direction.WEST);
    oppositesPiece = oppositesBuilder.build();

    Piece.Builder adjacentsBuilder = new Piece.Builder();
    adjacentsBuilder.setSide(outSide, Direction.NORTH);
    adjacentsBuilder.setSide(outSide, Direction.EAST);
    adjacentsBuilder.setSide(inSide, Direction.SOUTH);
    adjacentsBuilder.setSide(inSide, Direction.WEST);
    adjacentsPiece = adjacentsBuilder.build();
  }

  @Test
  public void testCanBeTypeCorner() {
    assertTrue(PieceType.CORNER.canBeType(cornerPiece));
    assertFalse(PieceType.EDGE.canBeType(cornerPiece));
    assertFalse(PieceType.ALL_IN.canBeType(cornerPiece));
    assertFalse(PieceType.ALL_OUT.canBeType(cornerPiece));
    assertFalse(PieceType.THREE_IN.canBeType(cornerPiece));
    assertFalse(PieceType.THREE_OUT.canBeType(cornerPiece));
    assertFalse(PieceType.OPPOSITES.canBeType(cornerPiece));
    assertFalse(PieceType.ADJACENTS.canBeType(cornerPiece));
  }

  @Test
  public void testCanBeTypeEdge() {
    assertFalse(PieceType.CORNER.canBeType(edgePiece));
    assertTrue(PieceType.EDGE.canBeType(edgePiece));
    assertFalse(PieceType.ALL_IN.canBeType(edgePiece));
    assertFalse(PieceType.ALL_OUT.canBeType(edgePiece));
    assertFalse(PieceType.THREE_IN.canBeType(edgePiece));
    assertFalse(PieceType.THREE_OUT.canBeType(edgePiece));
    assertFalse(PieceType.OPPOSITES.canBeType(edgePiece));
    assertFalse(PieceType.ADJACENTS.canBeType(edgePiece));
  }

  @Test
  public void testCanBeTypeAllIn() {
    assertFalse(PieceType.CORNER.canBeType(allInPiece));
    assertFalse(PieceType.EDGE.canBeType(allInPiece));
    assertTrue(PieceType.ALL_IN.canBeType(allInPiece));
    assertFalse(PieceType.ALL_OUT.canBeType(allInPiece));
    assertFalse(PieceType.THREE_IN.canBeType(allInPiece));
    assertFalse(PieceType.THREE_OUT.canBeType(allInPiece));
    assertFalse(PieceType.OPPOSITES.canBeType(allInPiece));
    assertFalse(PieceType.ADJACENTS.canBeType(allInPiece));
  }

  @Test
  public void testCanBeTypeAllOut() {
    assertFalse(PieceType.CORNER.canBeType(allOutPiece));
    assertFalse(PieceType.EDGE.canBeType(allOutPiece));
    assertFalse(PieceType.ALL_IN.canBeType(allOutPiece));
    assertTrue(PieceType.ALL_OUT.canBeType(allOutPiece));
    assertFalse(PieceType.THREE_IN.canBeType(allOutPiece));
    assertFalse(PieceType.THREE_OUT.canBeType(allOutPiece));
    assertFalse(PieceType.OPPOSITES.canBeType(allOutPiece));
    assertFalse(PieceType.ADJACENTS.canBeType(allOutPiece));
  }

  @Test
  public void testCanBeTypeThreeIn() {
    assertFalse(PieceType.CORNER.canBeType(threeInPiece));
    assertFalse(PieceType.EDGE.canBeType(threeInPiece));
    assertFalse(PieceType.ALL_IN.canBeType(threeInPiece));
    assertFalse(PieceType.ALL_OUT.canBeType(threeInPiece));
    assertTrue(PieceType.THREE_IN.canBeType(threeInPiece));
    assertFalse(PieceType.THREE_OUT.canBeType(threeInPiece));
    assertFalse(PieceType.OPPOSITES.canBeType(threeInPiece));
    assertFalse(PieceType.ADJACENTS.canBeType(threeInPiece));
  }

  @Test
  public void testCanBeTypeThreeOut() {
    assertFalse(PieceType.CORNER.canBeType(threeOutPiece));
    assertFalse(PieceType.EDGE.canBeType(threeOutPiece));
    assertFalse(PieceType.ALL_IN.canBeType(threeOutPiece));
    assertFalse(PieceType.ALL_OUT.canBeType(threeOutPiece));
    assertFalse(PieceType.THREE_IN.canBeType(threeOutPiece));
    assertTrue(PieceType.THREE_OUT.canBeType(threeOutPiece));
    assertFalse(PieceType.OPPOSITES.canBeType(threeOutPiece));
    assertFalse(PieceType.ADJACENTS.canBeType(threeOutPiece));
  }

  @Test
  public void testCanBeTypeOpposites() {
    assertFalse(PieceType.CORNER.canBeType(oppositesPiece));
    assertFalse(PieceType.EDGE.canBeType(oppositesPiece));
    assertFalse(PieceType.ALL_IN.canBeType(oppositesPiece));
    assertFalse(PieceType.ALL_OUT.canBeType(oppositesPiece));
    assertFalse(PieceType.THREE_IN.canBeType(oppositesPiece));
    assertFalse(PieceType.THREE_OUT.canBeType(oppositesPiece));
    assertTrue(PieceType.OPPOSITES.canBeType(oppositesPiece));
    assertFalse(PieceType.ADJACENTS.canBeType(oppositesPiece));
  }

  @Test
  public void testCanBeTypeAdjacents() {
    assertFalse(PieceType.CORNER.canBeType(adjacentsPiece));
    assertFalse(PieceType.EDGE.canBeType(adjacentsPiece));
    assertFalse(PieceType.ALL_IN.canBeType(adjacentsPiece));
    assertFalse(PieceType.ALL_OUT.canBeType(adjacentsPiece));
    assertFalse(PieceType.THREE_IN.canBeType(adjacentsPiece));
    assertFalse(PieceType.THREE_OUT.canBeType(adjacentsPiece));
    assertFalse(PieceType.OPPOSITES.canBeType(adjacentsPiece));
    assertTrue(PieceType.ADJACENTS.canBeType(adjacentsPiece));
  }
}
