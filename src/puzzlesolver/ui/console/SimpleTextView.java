package puzzlesolver.ui.console;

import com.sun.istack.internal.NotNull;

import java.util.Objects;

import puzzlesolver.Piece;
import puzzlesolver.Solver;

public class SimpleTextView implements TextView {

  private final Solver solver;

  public SimpleTextView(@NotNull Solver solver) {
    Objects.requireNonNull(solver);
    this.solver = solver;
  }

  @Override
  public String[] draw() {
    Piece[][] pieces = solver.getSolution();
    String[] finalBoard = new String[pieces.length];
    char[][] board = new char[pieces.length][pieces[0].length];
    for (int i = 0; i < pieces.length; i++) {
      for (int j = 0; j < pieces[i].length; j++) {
        Piece piece = pieces[i][j];
        if (piece == null) {
          board[i][j] = (char) 177;
        } else {
          switch (piece.getPieceType()) {
            case CORNER:
              board[i][j] = (char) 192;
              break;
            case EDGE:
              board[i][j] = (char) 179;
              break;
            case ALL_IN:
              board[i][j] = 'X';
              break;
            case ALL_OUT:
              board[i][j] = 'O';
              break;
            case THREE_IN:
              board[i][j] = 'K';
              break;
            case THREE_OUT:
              board[i][j] = '3';
              break;
            case OPPOSITES:
              board[i][j] = 'O';
              break;
            case ADJACENTS:
              board[i][j] = 'A';
          }
        }
      }
    }
    for (int i = 0; i < board.length; i++) {
      finalBoard[i] = new String(board[i]);
    }

    return finalBoard;
  }
}
