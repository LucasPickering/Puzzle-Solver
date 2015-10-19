package puzzlesolver;

/**
 * A class to represent immutable points, with x and y values.
 */
public class Point {

  public final double x;
  public final double y;

  /**
   * Constructs a new {@code Point} with the given x and y values.
   * @param x the x value of the point
   * @param y the y value of the point
   */
  public Point(double x, double y){
    this.x = x;
    this.y = y;
  }
}
