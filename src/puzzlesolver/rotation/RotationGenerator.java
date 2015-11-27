package puzzlesolver.rotation;

import puzzlesolver.Piece;
import puzzlesolver.constants.Constants;
import puzzlesolver.enums.Direction;
import puzzlesolver.simple.SimpleGenerator;

public class RotationGenerator extends SimpleGenerator {

  @Override
  public Piece[] generate(int width, int height) {
    Piece[] pieces = super.generate(width, height);
    for (Piece piece : pieces) {
      piece.rotate(Direction.NORTH, Direction.values()[random.nextInt(Constants.NUM_SIDES)]);
    }
    return pieces;
  }
}
