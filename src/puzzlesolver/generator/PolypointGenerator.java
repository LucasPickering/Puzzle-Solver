package puzzlesolver.generator;

import java.util.LinkedList;
import java.util.List;

import puzzlesolver.Funcs;
import puzzlesolver.Point;
import puzzlesolver.constants.Constants;
import puzzlesolver.side.Side;
import puzzlesolver.side.SimpleSide;

public class PolypointGenerator extends RotationGenerator {

  private final double minXDiff;
  private final double maxXDiff;

  private static final double MIN_Y_DEVIATION = Constants.SIDE_LENGTH * 0.02d;
  private static final double MAX_Y_DEVIATION = Constants.SIDE_LENGTH * 0.1d;

  /**
   * Constructs a new PolypointGenerator with default values for x diff factors. Default values are
   * 0.1 for minimum diff and 0.2 for maximum diff.
   */
  public PolypointGenerator() {
    this(0.1d, 0.2d);
  }

  /**
   * Constructs a new PolypointGenerator with the given factors for x point spacing.
   *
   * @param minXDiffFactor the factor to multiply {@link Constants#SIDE_LENGTH} by to create the
   *                       minimum x spacing between points, (0, 1], <= {@param maxXDiffFactor}
   * @param maxXDiffFactor the factor to multiply {@link Constants#SIDE_LENGTH} by to create the
   *                       maximum x spacing between points,  (0, 1], >= {@param minXDiffFactor}
   * @throws IllegalArgumentException if either parameter is out of bounds, or {@code minXDiffFactor
   *                                  > maxXDiffFactor}
   */
  public PolypointGenerator(double minXDiffFactor, double maxXDiffFactor) {
    if (minXDiffFactor <= 0 || minXDiffFactor > 1 || maxXDiffFactor <= 0 || maxXDiffFactor > 1) {
      throw new IllegalArgumentException(String.format(
          "Diff factors must be (0, 1], were min: %f, max: %f", minXDiffFactor, maxXDiffFactor));
    }
    if (minXDiffFactor > maxXDiffFactor) {
      throw new IllegalArgumentException("min X factor must be <= max X factor");
    }
    minXDiff = Constants.SIDE_LENGTH * minXDiffFactor;
    maxXDiff = Constants.SIDE_LENGTH * maxXDiffFactor;
  }

  @Override
  public Side generateSide(boolean flat) {
    final List<Point> points = new LinkedList<>();
    points.add(new Point(0d, 0d)); // Add first corner

    if (!flat) {
      // Add midpoints
      for (double x = Funcs.randomInRange(random(), minXDiff, maxXDiff);
           x < Constants.SIDE_LENGTH - minXDiff;
           x += Funcs.randomInRange(random(), maxXDiff, maxXDiff)) {
        points.add(new Point(x, Funcs.randomInRangeNegate(random(),
                                                          MIN_Y_DEVIATION, MAX_Y_DEVIATION)));
      }
    }

    points.add(new Point(Constants.SIDE_LENGTH, 0d)); // Add second corner
    return new SimpleSide(points);
  }
}
