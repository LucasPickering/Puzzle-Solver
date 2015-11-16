package puzzlesolver;

public final class Constants {

  /**
   * Number of sides to a {@link Piece}.
   */
  public static final int NUM_SIDES = 4;

  /**
   * Length of {@link Piece} sides.
   */
  public static final double SIDE_LENGTH = 10d;

  // UI
  public static final class UI {

    public static final String TEXT_SIMPLE = "Text", TEXT_FANCY = "Text (Fancy)",
        VISUAL = "Visual", VISUAL_FANCY = "Visual Fancy";

    public static final String BUTTON_SOLVE = "Solve", BUTTON_CANCEL = "Cancel";

    public static final int VISUAL_PIECE_WIDTH = 20, VISUAL_PIECE_HEIGHT = VISUAL_PIECE_WIDTH,
    WINDOW_MIN_WIDTH = 400, WINDOW_MIN_HEIGHT = 300, VISUAL_PIECE_PADDING = 10;
  }

  public static final boolean USE_CONSOLE = true;
}
