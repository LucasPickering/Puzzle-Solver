package puzzlesolver.constants;

// UIConstants
public final class UIConstants {

  public static final String TEXT_SIMPLE = "Text (Simple)", TEXT_FANCY = "Text (Fancy)",
      VISUAL_SIMPLE = "Visual (Simple)", VISUAL_FANCY = "Visual (Fancy)";

  public static final String BUTTON_SOLVE = "Solve", BUTTON_STOP = "Stop",
      BUTTON_SHOW = "Show";

  @SuppressWarnings("SuspiciousNameCombination")
  public static final double VISUAL_PIECE_WIDTH = Constants.SIDE_LENGTH,
      VISUAL_PIECE_HEIGHT = VISUAL_PIECE_WIDTH,
      WINDOW_MIN_WIDTH = 400d, WINDOW_MIN_HEIGHT = 400d,
      VISUAL_PIECE_PADDING = Constants.SIDE_LENGTH / 2d;
}
