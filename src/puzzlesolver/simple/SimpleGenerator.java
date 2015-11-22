package puzzlesolver.simple;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import puzzlesolver.Generator;
import puzzlesolver.Piece;
import puzzlesolver.Point;
import puzzlesolver.constants.Constants;
import puzzlesolver.enums.Direction;

/**
 * A class to generate a puzzle with pieces composed of {@link SimpleSide}s.
 */
public class SimpleGenerator implements Generator {

  private static final double MIN_X_DEVIATION = 1.0f;
  private static final double MAX_X_DEVIATION = 3.0f;
  private static final double MIN_Y_DEVIATION = 0.1f;
  private static final double MAX_Y_DEVIATION = 1.0f;

  private Random random;

  @Override
  public Piece[] generate(long seed, int width, int height) {
    random = new Random(seed);
    final Piece[][] pieces = new Piece[width][height];

    /* Generate 4wh sides.
     * w + 1 columns of unique vertical sides. Each column has height sides.
     * h(w + 1) unique vertical sides.
     *
     * h + 1 rows of unique horizontal sides. Each column has width sides.
     * w(h + 1) unique horizontal sides.
     *
     * h(w + 1) + w(h + 1) total unique sides.
     * wh + h + wh + w total unique sides.
     * 2wh + w + h total unique sides.
     */

    for (int x = 0; x < width; x++) {
      for (int y = 0; y < height; y++) {
        final Piece.Builder builder = new Piece.Builder();

        // Set the west side. If this is the first column, generate a flat side. Otherwise, use the
        // INVERSE of the EAST side from the piece to the left of this one.
        builder.setSide(x == 0 ? generateSide(true) :
                        pieces[x - 1][y].getSide(Direction.EAST).inverse(), Direction.WEST);

        // Set the east side to a newly-generated side.
        builder.setSide(generateSide(x == width - 1), Direction.EAST);

        // Set the north side. If this is the first row, generate a flat side. Otherwise, use the
        // INVERSE of the SOUTH side from the piece above this one.
        builder.setSide(y == 0 ? generateSide(true) :
                        pieces[x][y - 1].getSide(Direction.SOUTH).inverse(), Direction.NORTH);

        // Set the south side to a newly-generated side.
        builder.setSide(generateSide(y == height - 1), Direction.SOUTH);

        pieces[x][y] = builder.build(); // Build the piece and put it in the final array
      }
    }

    // Flatten the 2-D array into a list, shuffle it, and return it
    final List<Piece> toReturn = new ArrayList<>(width * height);
    for (int i = 0; i < width; i++) {
      for (int j = 0; j < height; j++) {
        toReturn.add(pieces[i][j]);
      }
    }
    Collections.shuffle(toReturn, random); // Shuffle the list to "unsolve" it
    return toReturn.toArray(new Piece[width * height]);
  }

  @Override
  public Piece[] generate(int width, int height) {
    return generate(new Random().nextLong(), width, height);
  }

  private SimpleSide generateSide(boolean flat) {
    final Point corner1 = new Point(0d, 0d);
    final Point corner2 = new Point(Constants.SIDE_LENGTH, 0d);
    if (flat) {
      return new SimpleSide(corner1, corner2);
    }
    final double midX = randomInRange(MIN_X_DEVIATION, MAX_X_DEVIATION);
    final double midY = randomInRange(MIN_Y_DEVIATION, MAX_Y_DEVIATION);
    return new SimpleSide(corner1, new Point(midX, midY), corner2);
  }

  private double randomInRange(double min, double max) {
    return (random.nextDouble() * (max - min) + min) * (random.nextInt(2) == 0 ? -1 : 1);
  }
}
