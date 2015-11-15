package puzzlesolver;

/**
 * A class to represent immutable points, with x and y values.
 *
 * Origin is at the top-left.
 */
public class Point implements Comparable<Point>, Cloneable {

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
  public Point clone() {
    try {
      super.clone();
    } catch (CloneNotSupportedException e) {
      e.printStackTrace();
    }

    return new Point(x, y);
  }

  @Override
  public String toString() {
    return "(" + x + ", " + y + ")";
  }

  @Override
  public int compareTo(Point p) {
    if (y != p.y) {
      return Double.compare(y, p.y);
    }
    return Double.compare(x, p.x);
  }
}
