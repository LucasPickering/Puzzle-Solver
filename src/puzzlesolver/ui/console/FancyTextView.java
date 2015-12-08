package puzzlesolver.ui.console;

import com.sun.istack.internal.NotNull;

import java.util.Arrays;
import java.util.Objects;

import puzzlesolver.Piece;
import puzzlesolver.solver.Solver;
import puzzlesolver.enums.Direction;
import puzzlesolver.enums.SideType;

public class FancyTextView implements TextView {

  private static final char
      SIDE_FLAT = 'F',
      SIDE_OUT = 'O',
      SIDE_IN = 'I',
      PIECE_CENTER = 'P';

  private static final char
      BOX_SIDE = '│',
      BOX_TOP = '-',
      BOX_BOTTOM = BOX_TOP,
      CORNER_TOP_LEFT = '╭',
      CORNER_TOP_RIGHT = '╮',
      CORNER_BOTTOM_LEFT = '╰',
      CORNER_BOTTOM_RIGHT = '╯',
      STATUS_DIVIDER_LEFT = '├',
      STATUS_DIVIDER_RIGHT = '┤',
      STATUS_DIVIDER_TOP = '┬',
      STATUS_DIVIDER_BOTTOM = '┴',
      EMPTY = ' ';


  private static final String
      STATUS_LOWER_BOUND = "[",
      STATUS_UPPER_BOUND = "]",
      STATUS_BAR = "▮";

  private final Solver solver;

  public FancyTextView(@NotNull Solver solver) {
    Objects.requireNonNull(solver);
    this.solver = solver;
  }

  @Override
  public String[] draw() {
    // TODO: make sure I don't have off by 1 errors
    Piece[][] pieces = solver.getSolution();
    int height = pieces.length;
    int width = pieces[0].length;
    char[][] board = new char[height * 4][width * 4 + 1];
    int totalPieces = pieces.length * pieces[0].length;
    int countPieces = 0;
    // run through each piece and add it to the temp board
    for (int i = 0; i < height; i++) {
      for (int j = 0; j < width; j++) {
        countPieces++;
        Piece curPiece = pieces[i][j];
        int tempHeight = i * 4 + 1;
        int tempWidth = j * 4 + 2;
        if (curPiece == null) {
          board[tempHeight][tempWidth] = EMPTY;
          board[tempHeight][tempWidth + 1] = EMPTY;
          board[tempHeight][tempWidth + 2] = EMPTY;
          board[tempHeight + 1][tempWidth] = EMPTY;
          board[tempHeight + 1][tempWidth + 1] = EMPTY;
          board[tempHeight + 1][tempWidth + 2] = EMPTY;
          board[tempHeight + 2][tempWidth] = EMPTY;
          board[tempHeight + 2][tempWidth + 1] = EMPTY;
          board[tempHeight + 2][tempWidth + 2] = EMPTY;
          Arrays.fill(board[tempHeight + 3], EMPTY);
        } else {
          char[][] displayPiece = pieceToDisplay(curPiece);

          board[tempHeight][tempWidth] = displayPiece[0][0];
          board[tempHeight][tempWidth + 1] = displayPiece[0][1];
          board[tempHeight][tempWidth + 2] = displayPiece[0][2];
          board[tempHeight + 1][tempWidth] = displayPiece[1][0];
          board[tempHeight + 1][tempWidth + 1] = displayPiece[1][1];
          board[tempHeight + 1][tempWidth + 2] = displayPiece[1][2];
          board[tempHeight + 2][tempWidth] = displayPiece[2][0];
          board[tempHeight + 2][tempWidth + 1] = displayPiece[2][1];
          board[tempHeight + 2][tempWidth + 2] = displayPiece[2][2];
          Arrays.fill(board[tempHeight + 3], EMPTY);
        }
      }
    }

    // convert the completed char[][] to the final string[]
    return finalBoard(board, totalPieces, totalPieces - countPieces);
  }

  // called for each piece to convert to the char representation
  char[][] pieceToDisplay(Piece p) {
    char[][] display = new char[2][2];
    display[0][0] = EMPTY;
    display[0][2] = EMPTY;
    display[2][0] = EMPTY;
    display[2][2] = EMPTY;
    display[1][1] = PIECE_CENTER;

    display[0][1] = displayHelp(p.getSide(Direction.NORTH).getSideType());
    display[1][2] = displayHelp(p.getSide(Direction.SOUTH).getSideType());
    display[1][0] = displayHelp(p.getSide(Direction.EAST).getSideType());
    display[1][2] = displayHelp(p.getSide(Direction.WEST).getSideType());
    return display;
  }

  // called on each side to convert to the char representation
  char displayHelp(SideType s) {
    switch (s) {
      case IN:
        return SIDE_IN;
      case OUT:
        return SIDE_OUT;
      case FLAT:
        return SIDE_FLAT;
    }
    // in case of a problem
    return '*';
  }

  String statusBar(int total, int count) {
    char[] charBars = new char[10];
    int bar = total / 10;
    int showBars = count / bar;
    Arrays.fill(charBars, 0, showBars, '▮');
    String bars = new String(charBars);
    return bars;
  }

  String[] finalBoard(char[][] board, int total, int count) {
    int height = board.length;
    int width = board[0].length;
    String[] strBoard = new String[height + 1];

    // filling in the pretty stuff

    // top row
    char[] topRow = new char[width - 1];
    Arrays.fill(topRow, BOX_TOP);
    topRow[0] = CORNER_TOP_LEFT;
    topRow[width - 1] = CORNER_TOP_RIGHT;
    board[0] = topRow;
    // row above status bar
    char[] midRow = new char[width - 1];
    Arrays.fill(midRow, BOX_BOTTOM);
    midRow[0] = STATUS_DIVIDER_LEFT;
    midRow[width - 1] = STATUS_DIVIDER_RIGHT;
    board[height - 1] = midRow;

    // border edges
    for (int i = 0; i < height; i++) {
      board[i][0] = BOX_SIDE;
      board[i][width - 1] = BOX_SIDE;
    }

    for (int i = 0; i < board.length; i++) {
      strBoard[i] = new String(board[i]);
    }
    String status = "│ remain: " + (total - count) + "            │ [" + statusBar(total, count) + "] │";
    strBoard[height] = status;
    strBoard[height + 1] = "╰-----------------------------------------------------╯";
    return strBoard;
  }
}
