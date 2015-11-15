package puzzlesolver.ui.console;

import com.sun.istack.internal.NotNull;

import java.util.Objects;

import puzzlesolver.Solver;

public class FancyTextView implements TextView {

  private static final String
      SIDE_FLAT = "F",
      SIDE_OUT = "O",
      SIDE_IN = "I",
      PIECE_CENTER = "P";

  private static final String
      BOX_SIDE = "│",
      BOX_TOP = "─",
      BOX_BOTTOM = BOX_TOP,
      CORNER_TOP_LEFT = "╭",
      CORNER_TOP_RIGHT = "╮",
      CORNER_BOTTOM_LEFT = "╰",
      CORNER_BOTTOM_RIGHT = "╯",
      STATUS_DIVIDER = BOX_SIDE,
      STATUS_DIVIDER_TOP = "┬",
      STATUS_DIVIDER_BOTTOM = "┴";

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
    // TODO
    return new String[0];
  }
}
