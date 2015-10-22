package puzzlesolver.solver;

import puzzlesolver.Piece;

public class SimpleSolver implements Solver {

  @Override
  public Piece[][] solve(Piece[] pieces) {
    final int width = getWidth(0, pieces.length);
    final int length = getLength(width, pieces.length);
    Piece[][] solution = new Piece[width][length];

    return solution;
  }

  private int getWidth(int perimeter, int area) {
    final int helper = (perimeter + 4) / 2;
    final double width = (helper + Math.sqrt(helper * helper - 4 * area)) / 2;
    final int roundedWidth = (int) (width + 0.5D);
    final double error = Math.abs(width - roundedWidth);
    if (error > 0.1D) {
      throw new IllegalArgumentException(String.format("Error is %f for perimeter %d and area %d",
          error, perimeter, area));
    }
    return roundedWidth;
  }

  private int getLength(int width, int area) {
    if (area % width != 0) {
      throw new IllegalArgumentException(String.format("Area (%d) is not divisible by width (%d)",
          area, width));
    }
    return area / width;
  }
}
