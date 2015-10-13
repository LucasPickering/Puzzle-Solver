package puzzlesolver;

/**
 * A class to represent immutable points, with integer x and y values.
 */
public class Point {

  public final int x;
  public final int y;

  /**
   * Constructs a new {@code Point} with the given x and y values.
   * @param x the x value of the point
   * @param y the y value of the point
   */
  public Point(int x, int y){
    this.x = x;
    this.y = y;
  }
}
