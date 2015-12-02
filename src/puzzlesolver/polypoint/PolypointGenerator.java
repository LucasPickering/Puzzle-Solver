package puzzlesolver.polypoint;

import puzzlesolver.Point;
import puzzlesolver.Side;
import puzzlesolver.constants.Constants;
import puzzlesolver.rotation.RotationGenerator;
import puzzlesolver.simple.SimpleSide;

public class PolypointGenerator extends RotationGenerator {

  @Override
  public Side generateSide(boolean flat) {
    if (flat) {
      return super.generateSide(true);
    }
    final Point corner1 = new Point(0d, 0d);
    final Point corner2 = new Point(Constants.SIDE_LENGTH, 0d);

    // TODO: Make polypoint generation

    return new SimpleSide(corner1, corner2);
  }
}
