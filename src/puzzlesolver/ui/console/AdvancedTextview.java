package puzzlesolver.ui.console;

import com.sun.istack.internal.NotNull;

import java.util.Arrays;
import java.util.Objects;

import puzzlesolver.Piece;
import puzzlesolver.Side;
import puzzlesolver.Solver;
import puzzlesolver.enums.Direction;

public class AdvancedTextView implements TextView {

  private static final int CHAR_WIDTH = 3;

  private static final char EMPTY_CHAR = ' ';
  private static final char MIDDLE_CHAR = '+';
  private static final char IN_CHAR = 'I';
  private static final char OUT_CHAR = 'O';
  private static final char FLAT_CHAR = 'F';

  private final Solver solver;

  public AdvancedTextView(@NotNull Solver solver) {
    Objects.requireNonNull(solver);
    this.solver = solver;
  }

  @Override
  public String[] draw() {
    Piece[][] pieces = solver.getSolution();
    final int height = pieces[0].length * CHAR_WIDTH;
    StringBuilder[] builders = new StringBuilder[height];
    for (Piece[] pieceRow : pieces) {
      for (int pieceY = 0; pieceY < pieceRow.length; pieceY++) {
        final StringBuilder[] drawnPiece = drawPiece(pieceRow[pieceY]);
        for (int i = 0; i < CHAR_WIDTH; i++) {
          final int builderNum = pieceY * CHAR_WIDTH + i;
          if (builders[builderNum] == null) {
            builders[builderNum] = new StringBuilder();
          }
          builders[builderNum].append(drawnPiece[i]);
        }
      }
    }

    return Arrays.asList(builders).stream().map(StringBuilder::toString).toArray(String[]::new);
  }

  private StringBuilder[] drawPiece(Piece p) {
    final StringBuilder[] builders = new StringBuilder[CHAR_WIDTH];

    builders[0] = new StringBuilder();
    builders[0].append(EMPTY_CHAR);
    builders[0].append(getCharForSide(p, Direction.NORTH));
    builders[0].append(EMPTY_CHAR);

    builders[1] = new StringBuilder();
    builders[1].append(getCharForSide(p, Direction.WEST));
    builders[1].append(MIDDLE_CHAR);
    builders[1].append(getCharForSide(p, Direction.EAST));

    builders[2] = new StringBuilder();
    builders[2].append(EMPTY_CHAR);
    builders[2].append(getCharForSide(p, Direction.SOUTH));
    builders[2].append(EMPTY_CHAR);

    return builders;
  }

  private char getCharForSide(Piece p, Direction dir) {
    final Side s;
    if (p != null && (s = p.getSide(dir)) != null) {
      switch (s.getSideType()) {
        case IN:
          return IN_CHAR;
        case OUT:
          return OUT_CHAR;
        case FLAT:
          return FLAT_CHAR;
      }
    }
    return '?';
  }
}
