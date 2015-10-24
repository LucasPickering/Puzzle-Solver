package puzzlesolver.generation;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

import puzzlesolver.Piece;
import puzzlesolver.Point;
import puzzlesolver.enums.Direction;
import puzzlesolver.side.SimpleSide;

/**
 * A class to generate a puzzle with pieces composed of {@link SimpleSide}s.
 */
public final class SimpleGenerator implements Generator {

  private static final double SIDE_LENGTH = 10.0d;
  private static final double MIN_X_DEVIATION = 1.0f;
  private static final double MAX_X_DEVIATION = 3.0f;
  private static final double MIN_Y_DEVIATION = 0.1f;
  private static final double MAX_Y_DEVIATION = 1.0f;

  private final Random random = new Random();

  @Override
  public Piece[] generate(int width, int height) {
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

    final int numHorizontals = width * height + width;
    final int numVerticals = width * height + height;
    // Horizontal sides are listed first, then vertical sides, top-to-bottom, left-to-right.
    SimpleSide[] sides = new SimpleSide[numHorizontals + numVerticals];
    for (int i = 0; i < sides.length; i++) {
      if (i < numHorizontals) {
        final int y = i / width; // [0, height]
        sides[i] = generateSide(y == 0 || y == height);
      } else {
        final int x = i - numHorizontals % width; // [0, width]
        sides[i] = generateSide(x == 0 || x == width);
      }
    }

    for (int x = 0; x < width; x++) {
      for (int y = 0; y < height; y++) {
        final Piece.Builder builder = new Piece.Builder();

        builder.setSide(x == 0 ? generateSide(true) :
            pieces[x - 1][y].getSide(Direction.EAST), Direction.WEST);
        builder.setSide(generateSide(x == width - 1), Direction.EAST);

        builder.setSide(y == 0 ? generateSide(true) :
            pieces[x][y - 1].getSide(Direction.SOUTH), Direction.NORTH);
        builder.setSide(generateSide(y == height - 1), Direction.SOUTH);

        pieces[x][y] = builder.build();
      }
    }

    final ArrayList<Piece> toReturn = new ArrayList<>(width * height);
    for (int i = 0; i < width; i++) {
      for (int j = 0; j < height; j++) {
        toReturn.add(pieces[i][j]);
      }
    }
    Collections.shuffle(toReturn);
    return toReturn.toArray(new Piece[width * height]);
  }

  private SimpleSide generateSide(boolean flat) {
    final Point corner1 = new Point(0d, 0d);
    final Point corner2 = new Point(SIDE_LENGTH, 0d);
    if (flat) {
      return new SimpleSide(corner1, corner2);
    }
    final double midX = randomInRange(MIN_X_DEVIATION, MAX_X_DEVIATION);
    final double midY = randomInRange(MIN_Y_DEVIATION, MAX_Y_DEVIATION);
    return new SimpleSide(corner1, new Point(midX, midY), corner2);
  }

  private double randomInRange(double min, double max) {
    return (random.nextDouble() * (max - min) + min) * random.nextInt(2) * 2 - 1;
  }
}
