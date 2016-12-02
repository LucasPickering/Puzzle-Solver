package puzzlesolver.test;

import org.junit.Before;
import org.junit.Test;

import puzzlesolver.Piece;
import puzzlesolver.Point;
import puzzlesolver.constants.Constants;
import puzzlesolver.enums.Direction;
import puzzlesolver.enums.PieceType;
import puzzlesolver.side.Side;
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

    private Piece oneInPiece;
    private Piece oneOutPiece;

    @Before
    public void setUp() {
        final Side inSide = new SimpleSide(new Point(0, 0), new Point(5d, -0.5d),
                                           new Point(Constants.SIDE_LENGTH, 0));
        final Side outSide = new SimpleSide(new Point(0, 0), new Point(5d, 0.5d),
                                            new Point(Constants.SIDE_LENGTH, 0));
        final Side flatSide = new SimpleSide(new Point(0, 0), new Point(Constants.SIDE_LENGTH, 0));
        Piece.Builder builder;

        builder = new Piece.Builder();
        builder.setSide(flatSide, Direction.NORTH);
        builder.setSide(flatSide, Direction.EAST);
        builder.setSide(inSide, Direction.SOUTH);
        builder.setSide(inSide, Direction.WEST);
        cornerPiece = builder.build();

        builder = new Piece.Builder();
        builder.setSide(flatSide, Direction.NORTH);
        builder.setSide(inSide, Direction.EAST);
        builder.setSide(inSide, Direction.SOUTH);
        builder.setSide(inSide, Direction.WEST);
        edgePiece = builder.build();

        builder = new Piece.Builder();
        builder.setSide(inSide, Direction.NORTH);
        builder.setSide(inSide, Direction.EAST);
        builder.setSide(inSide, Direction.SOUTH);
        builder.setSide(inSide, Direction.WEST);
        allInPiece = builder.build();

        builder = new Piece.Builder();
        builder.setSide(outSide, Direction.NORTH);
        builder.setSide(outSide, Direction.EAST);
        builder.setSide(outSide, Direction.SOUTH);
        builder.setSide(outSide, Direction.WEST);
        allOutPiece = builder.build();

        builder = new Piece.Builder();
        builder.setSide(outSide, Direction.NORTH);
        builder.setSide(inSide, Direction.EAST);
        builder.setSide(inSide, Direction.SOUTH);
        builder.setSide(inSide, Direction.WEST);
        threeInPiece = builder.build();

        builder = new Piece.Builder();
        builder.setSide(outSide, Direction.NORTH);
        builder.setSide(outSide, Direction.EAST);
        builder.setSide(outSide, Direction.SOUTH);
        builder.setSide(inSide, Direction.WEST);
        threeOutPiece = builder.build();

        builder = new Piece.Builder();
        builder.setSide(outSide, Direction.NORTH);
        builder.setSide(inSide, Direction.EAST);
        builder.setSide(outSide, Direction.SOUTH);
        builder.setSide(inSide, Direction.WEST);
        oppositesPiece = builder.build();

        builder = new Piece.Builder();
        builder.setSide(outSide, Direction.NORTH);
        builder.setSide(outSide, Direction.EAST);
        builder.setSide(inSide, Direction.SOUTH);
        builder.setSide(inSide, Direction.WEST);
        adjacentsPiece = builder.build();

        builder = new Piece.Builder();
        builder.setSide(inSide, Direction.NORTH);
        oneInPiece = builder.build();

        builder = new Piece.Builder();
        builder.setSide(outSide, Direction.NORTH);
        oneOutPiece = builder.build();
    }

    @Test
    public void testCanBeTypeCorner() {
        assertTrue(PieceType.CORNER.canBeType(cornerPiece));
        assertFalse(PieceType.CORNER.canBeType(edgePiece));
        assertFalse(PieceType.CORNER.canBeType(allInPiece));
        assertFalse(PieceType.CORNER.canBeType(allOutPiece));
        assertFalse(PieceType.CORNER.canBeType(threeInPiece));
        assertFalse(PieceType.CORNER.canBeType(threeOutPiece));
        assertFalse(PieceType.CORNER.canBeType(oppositesPiece));
        assertFalse(PieceType.CORNER.canBeType(adjacentsPiece));

        assertTrue(PieceType.CORNER.canBeType(oneInPiece));
        assertTrue(PieceType.CORNER.canBeType(oneOutPiece));
    }

    @Test
    public void testCanBeTypeEdge() {
        assertFalse(PieceType.EDGE.canBeType(cornerPiece));
        assertTrue(PieceType.EDGE.canBeType(edgePiece));
        assertFalse(PieceType.EDGE.canBeType(allInPiece));
        assertFalse(PieceType.EDGE.canBeType(allOutPiece));
        assertFalse(PieceType.EDGE.canBeType(threeInPiece));
        assertFalse(PieceType.EDGE.canBeType(threeOutPiece));
        assertFalse(PieceType.EDGE.canBeType(oppositesPiece));
        assertFalse(PieceType.EDGE.canBeType(adjacentsPiece));

        assertTrue(PieceType.EDGE.canBeType(oneInPiece));
        assertTrue(PieceType.EDGE.canBeType(oneOutPiece));
    }

    @Test
    public void testCanBeTypeAllIn() {
        assertFalse(PieceType.ALL_IN.canBeType(cornerPiece));
        assertFalse(PieceType.ALL_IN.canBeType(edgePiece));
        assertTrue(PieceType.ALL_IN.canBeType(allInPiece));
        assertFalse(PieceType.ALL_IN.canBeType(allOutPiece));
        assertFalse(PieceType.ALL_IN.canBeType(threeInPiece));
        assertFalse(PieceType.ALL_IN.canBeType(threeOutPiece));
        assertFalse(PieceType.ALL_IN.canBeType(oppositesPiece));
        assertFalse(PieceType.ALL_IN.canBeType(adjacentsPiece));

        assertTrue(PieceType.ALL_IN.canBeType(oneInPiece));
        assertFalse(PieceType.ALL_IN.canBeType(oneOutPiece));
    }

    @Test
    public void testCanBeTypeAllOut() {
        assertFalse(PieceType.ALL_OUT.canBeType(cornerPiece));
        assertFalse(PieceType.ALL_OUT.canBeType(edgePiece));
        assertFalse(PieceType.ALL_OUT.canBeType(allInPiece));
        assertTrue(PieceType.ALL_OUT.canBeType(allOutPiece));
        assertFalse(PieceType.ALL_OUT.canBeType(threeInPiece));
        assertFalse(PieceType.ALL_OUT.canBeType(threeOutPiece));
        assertFalse(PieceType.ALL_OUT.canBeType(oppositesPiece));
        assertFalse(PieceType.ALL_OUT.canBeType(adjacentsPiece));

        assertFalse(PieceType.ALL_OUT.canBeType(oneInPiece));
        assertTrue(PieceType.ALL_OUT.canBeType(oneOutPiece));
    }

    @Test
    public void testCanBeTypeThreeIn() {
        assertFalse(PieceType.THREE_IN.canBeType(cornerPiece));
        assertFalse(PieceType.THREE_IN.canBeType(edgePiece));
        assertFalse(PieceType.THREE_IN.canBeType(allInPiece));
        assertFalse(PieceType.THREE_IN.canBeType(allOutPiece));
        assertTrue(PieceType.THREE_IN.canBeType(threeInPiece));
        assertFalse(PieceType.THREE_IN.canBeType(threeOutPiece));
        assertFalse(PieceType.THREE_IN.canBeType(oppositesPiece));
        assertFalse(PieceType.THREE_IN.canBeType(adjacentsPiece));

        assertTrue(PieceType.THREE_IN.canBeType(oneInPiece));
        assertTrue(PieceType.THREE_IN.canBeType(oneOutPiece));
    }

    @Test
    public void testCanBeTypeThreeOut() {
        assertFalse(PieceType.THREE_OUT.canBeType(cornerPiece));
        assertFalse(PieceType.THREE_OUT.canBeType(edgePiece));
        assertFalse(PieceType.THREE_OUT.canBeType(allInPiece));
        assertFalse(PieceType.THREE_OUT.canBeType(allOutPiece));
        assertFalse(PieceType.THREE_OUT.canBeType(threeInPiece));
        assertTrue(PieceType.THREE_OUT.canBeType(threeOutPiece));
        assertFalse(PieceType.THREE_OUT.canBeType(oppositesPiece));
        assertFalse(PieceType.THREE_OUT.canBeType(adjacentsPiece));

        assertTrue(PieceType.THREE_OUT.canBeType(oneInPiece));
        assertTrue(PieceType.THREE_OUT.canBeType(oneOutPiece));
    }

    @Test
    public void testCanBeTypeOpposites() {
        assertFalse(PieceType.OPPOSITES.canBeType(cornerPiece));
        assertFalse(PieceType.OPPOSITES.canBeType(edgePiece));
        assertFalse(PieceType.OPPOSITES.canBeType(allInPiece));
        assertFalse(PieceType.OPPOSITES.canBeType(allOutPiece));
        assertFalse(PieceType.OPPOSITES.canBeType(threeInPiece));
        assertFalse(PieceType.OPPOSITES.canBeType(threeOutPiece));
        assertTrue(PieceType.OPPOSITES.canBeType(oppositesPiece));
        assertFalse(PieceType.OPPOSITES.canBeType(adjacentsPiece));

        assertTrue(PieceType.OPPOSITES.canBeType(oneInPiece));
        assertTrue(PieceType.OPPOSITES.canBeType(oneOutPiece));
    }

    @Test
    public void testCanBeTypeAdjacents() {
        assertFalse(PieceType.ADJACENTS.canBeType(cornerPiece));
        assertFalse(PieceType.ADJACENTS.canBeType(edgePiece));
        assertFalse(PieceType.ADJACENTS.canBeType(allInPiece));
        assertFalse(PieceType.ADJACENTS.canBeType(allOutPiece));
        assertFalse(PieceType.ADJACENTS.canBeType(threeInPiece));
        assertFalse(PieceType.ADJACENTS.canBeType(threeOutPiece));
        assertFalse(PieceType.ADJACENTS.canBeType(oppositesPiece));
        assertTrue(PieceType.ADJACENTS.canBeType(adjacentsPiece));

        assertTrue(PieceType.ADJACENTS.canBeType(oneInPiece));
        assertTrue(PieceType.ADJACENTS.canBeType(oneOutPiece));
    }
}
