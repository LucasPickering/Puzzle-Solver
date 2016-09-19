package puzzlesolver.constants;

// UIConstants
public final class UIConstants {

  public static final String
      RENDER_TEXT_SIMPLE = "Text (Simple)", RENDER_TEXT_ADVANCED = "Text (Fancy)",
      RENDER_VISUAL_SIMPLE = "Visual (Simple)", RENDER_VISUAL_FANCY = "Visual (Fancy)";

  public static final String SOLVER_SIMPLE = "Simple", SOLVER_ROTATION = "Rotation";
  public static final String GENERATOR_SIMPLE = "Simple", GENERATOR_ROTATION = "Rotation";

  public static final String WIDTH_FIELD = "Width", HEIGHT_FIELD = "Height";

  public static final String BUTTON_SOLVE = "Solve", BUTTON_STOP = "Stop",
      BUTTON_SHOW = "Show", BUTTON_GENERATE = "Generate New", BUTTON_RESET = "Reset";

  @SuppressWarnings("SuspiciousNameCombination")
  public static final double
      VISUAL_PIECE_WIDTH = Constants.SIDE_LENGTH, VISUAL_PIECE_HEIGHT = Constants.SIDE_LENGTH,
      WINDOW_MIN_WIDTH = 400d, WINDOW_MIN_HEIGHT = 400d,
      WINDOW_PREF_WIDTH = 800d, WINDOW_PREF_HEIGHT = 800d,
      VISUAL_PIECE_PADDING = Constants.SIDE_LENGTH;

  public static final int DEFAULT_PUZZLE_WIDTH = 16, DEFAULT_PUZZLE_HEIGHT = 32;

  /**
   * The minimum time between refreshes on the renderer. Can't let it go too fast (like Sanic).
   */
  public static final int MIN_RENDER_DELAY = 10;
}
