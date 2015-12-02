package puzzlesolver.polypoint;

import java.util.ArrayList;
import java.util.List;

import puzzlesolver.Funcs;
import puzzlesolver.Point;
import puzzlesolver.Side;
import puzzlesolver.constants.Constants;
import puzzlesolver.rotation.RotationGenerator;
import puzzlesolver.simple.SimpleSide;

public class PolypointGenerator extends RotationGenerator {

  private static final double MIN_X_DIFF = 0.1d * Constants.SIDE_LENGTH;
  private static final double MAX_X_DIFF = 0.2d * Constants.SIDE_LENGTH;
  private static final double MIN_Y_DEVIATION = Constants.SIDE_LENGTH * 0.02d;
  private static final double MAX_Y_DEVIATION = Constants.SIDE_LENGTH * 0.2d;

  @Override
  public Side generateSide(boolean flat) {
    if (flat) {
      return super.generateSide(true);
    }

    final List<Point> points = new ArrayList<>();
    points.add(new Point(0d, 0d)); // Add first corner

    // Add midpoints
    for (double x = Funcs.randomInRange(random, MIN_X_DIFF, MAX_X_DIFF);
         x < Constants.SIDE_LENGTH;
         x += Funcs.randomInRange(random, MIN_X_DIFF, MAX_X_DIFF)) {
      points.add(new Point(x, Funcs.randomInRangeNegate(random, MIN_Y_DEVIATION, MAX_Y_DEVIATION)));
    }

    points.add(new Point(Constants.SIDE_LENGTH, 0d)); // Add second corner

    return new SimpleSide(points);
  }
}
