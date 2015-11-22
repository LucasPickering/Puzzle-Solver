package puzzlesolver.constants;

import puzzlesolver.Logger;
import puzzlesolver.Piece;

public class Constants {

  /**
   * Number of sides to a {@link Piece}.
   */
  public static final int NUM_SIDES = 4;

  /**
   * Length of {@link Piece} sides.
   */
  public static final double SIDE_LENGTH = 10d;

  public static int VERBOSE_LEVEL = 0;

  public static double COMP_DELTA = 0.001d;

  public static final Logger LOGGER = new Logger(VERBOSE_LEVEL, System.out);
}
