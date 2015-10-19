package puzzlesolver;

/**
 * A class to represent immutable points, with x and y values.
 *
 * Origin is at the top-left.
 */
public class Point implements Comparable<Point> {

  /**
   * X-coordinate of the point (distance from left).
   */
  public final double x;

  /**
   * Y-coordinate of the point (distance from top).
   */
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

  @Override
  public Point clone() throws CloneNotSupportedException {
    super.clone();
    return new Point(x, y);
  }

  @Override
  public int compareTo(Point p) {
    return (x == p.x) ? Double.compare(y, p.y) : Double.compare(x, p.x);
  }
}
