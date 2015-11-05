package puzzlesolver.ui.views;

import org.jetbrains.annotations.NotNull;

import java.util.Objects;

import puzzlesolver.Constants;
import puzzlesolver.Piece;

public class TextView {

  private final Piece[] pieces;

  public TextView(@NotNull Piece[] pieces) {
    Objects.requireNonNull(pieces);
    this.pieces = pieces;
  }

  /**
   * Generate a console view of the puzzle.
   *
   * Each line of the console should go in its own array index. (draw()[0] should be the first
   * line, and so on). Do not append newline characters or breaks to the output.
   *
   * Each piece should be {@link Constants#PIECE_SIZE_CHARS} characters wide & tall.
   *
   * Overall view should be as big as you need it to be given the
   * {@link Constants#PIECE_SIZE_CHARS} and the number of {@link #pieces}.
   *
   * @return a text representation of the current puzzle view.
   */
  public String[] draw() {
    return null;
  }
}
