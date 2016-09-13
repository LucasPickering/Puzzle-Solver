package puzzlesolver.constants;

import java.util.Random;

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

  public static double COMP_DELTA = 0.00001d;

  public static final Logger LOGGER = new Logger(System.out);

  public static final Random RANDOM = new Random();

  public static String BENCHMARK_LOCATION = "benchmarks/%tF_%tH.%tM.%tS.txt";

}
