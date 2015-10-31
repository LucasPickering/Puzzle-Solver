package puzzlesolver.simple;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import puzzlesolver.Piece;
import puzzlesolver.PieceList;
import puzzlesolver.enums.Direction;
import puzzlesolver.enums.PieceType;
import puzzlesolver.Solver;
import puzzlesolver.enums.SideType;

public final class SimpleSolver implements Solver {

  private int width;
  private int height;
  private Piece[][] solution;

  @Override
  public Piece[][] solve(Piece[] pieces) {
    final PieceList pieceList = new SimplePieceList(pieces.length);
    int edges = 0;
    for (Piece piece : pieces) {
      if (piece.getPieceType() == PieceType.EDGE) {
        edges++;
      }
      pieceList.add(piece);
    }

    width = getWidth(edges + 4, pieces.length);
    height = getHeight(width, pieces.length);
    solution = new Piece[width][height];

    placeCorners(pieceList);
    placeEdges(pieceList);
    placeInternals(pieceList);

    return solution;
  }

  private void placeCorners(PieceList pieces) {
    Piece piece;
    while ((piece = pieces.first(Direction.NORTH)).getPieceType() == PieceType.CORNER) {
      final int x = piece.getSide(Direction.WEST).getSideType().isFlat() ? 0 : width - 1;
      final int y = piece.getSide(Direction.NORTH).getSideType().isFlat() ? 0 : height - 1;
      solution[x][y] = piece;
      pieces.remove(piece);
    }
  }

  private void placeEdges(PieceList pieces) {
    for (int x = 1; x < width - 1; x++) {
      for (int y = 0; y < height; y += height - 1) {
        final Piece piece = pieces
            .search(Direction.WEST, solution[x - 1][y].getSide(Direction.EAST), PieceType.EDGE);
        solution[x][y] = piece;
        pieces.remove(piece);
      }
    }
    for (int x = 0; x < height; x += height - 1) {
      for (int y = 1; y < width - 1; y++) {
        final Piece piece = pieces
            .search(Direction.NORTH, solution[x][y - 1].getSide(Direction.SOUTH), PieceType.EDGE);
        solution[x][y] = piece;
        pieces.remove(piece);
      }
    }
  }

  private void placeInternals(PieceList pieces) {
    for (int x = 1; x < width - 1; x++) {
      for (int y = 1; y < height - 1; y++) {
        final List<PieceType> pieceTypes = new ArrayList<>(6); // 6 possible types (efficiency!)
        for (int i = 2; i < PieceType.values().length; i++) {
          pieceTypes.add(PieceType.values()[i]);
        }

        final SideType[] adjacentTypes = new SideType[4];
        for (Direction dir : Direction.values()) {
          adjacentTypes[dir.ordinal()] = solution[x + dir.x][y + dir.y].getSide(dir).getSideType();
        }


      }
    }
  }
}
