package puzzlesolver.side;

import java.util.Arrays;
import java.util.Comparator;

import puzzlesolver.Point;

/**
 * An implementation of {@code Side}, where each side is represented by a list of {@code Point}s.
 *
 * The coordinates of the {@code Point}s are on an axis relative to the side. The x-axis is the
 * line between the two endpoints of the side, and the y-axis is perpendicular to that, going away
 * from the center of the piece.
 */
public final class SimpleSide implements Side {

  /**
   * List of points on the side.
   *
   * points[i].x = distance from left side points[i].y = height from side base
   */
  private final Point[] points;

  /**
   * The {@link SideType} of this {@link SimpleSide}.
   */
  private SideType sideType;

  /**
   * Constructs a new {@code SimpleSide} from the given points.
   *
   * @param points the series of points making up the side, relative to the piece.
   *
   *               These must be in the desired order along the side.
   */
  public SimpleSide(Point... points) {
    this.points = points.clone();
    Arrays.sort(this.points, 0, this.points.length, Comparator.<Point>naturalOrder());
    sideType = findSideType();
  }

  /**
   * Calculate the {@link SideType} based on the points making up this {@link SimpleSide}.
   *
   * Should only be called by {@link #getSideType()}. All other functions should call
   * {@link this.getSideType()}.
   *
   * @return the type of the side.
   */
  private SideType findSideType() {
    final double maxHeight = points[0].y;

    for (int i = 1; i < points.length; i++) {
      if (points[i].y < maxHeight) {
        return sideType = SideType.IN;
      } else if (points[i].y > maxHeight) {
        return sideType = SideType.OUT;
      }
    }

    return sideType = SideType.FLAT;
  }

  /**
   * Compares this {@code SimpleSide} to the given {@code other} {@code SimpleSide}
   *
   * @param other the {@link Side} to compare this side to.
   * @return 0 if they are equivalent.
   * @throws ClassCastException if the {@link Side} given is not a {@link SimpleSide}.
   */
  @Override
  public int compareTo(Side other) {
    if (!SimpleSide.class.isInstance(other)) {
      throw new ClassCastException(String.format("Cannot compare %s to %s.", getClass().toString(),
                                                 other.getClass().toString()));
    }

    SimpleSide simpleOther = (SimpleSide) other;

    if (!getSideType().equals(other.getSideType())) {
      return getSideType().compareTo(other.getSideType());
    }

    if (getCornerDistance() == other.getCornerDistance()) {
      return Double.compare(getCornerDistance(), other.getCornerDistance());
    }

    Point[] otherPoints = simpleOther.getPoints();

    assert points != null && otherPoints != null;
    if (points.length != otherPoints.length) {
      return Integer.compare(points.length, otherPoints.length);
    }

    for (int point = 0; point < points.length; point++) {
      if (!points[point].equals(otherPoints[point])) {
        return points[point].compareTo(otherPoints[point]);
      }
    }

    return 0;
  }

  /**
   * Get the array of points representing this side.
   *
   * @return the array of points representing this side.
   */
  public Point[] getPoints() {
    Point[] copy = new Point[points.length];
    for (int i = 0; i < points.length; i++) {
      try {
        copy[i] = points[i].clone();
      } catch (CloneNotSupportedException e) {
        return null;
      }
    }
    return copy;
  }

  @Override
  public double getCornerDistance() {
    return points[points.length - 1].x;
  }

  @Override
  public SideType getSideType() {
    return (sideType == null) ? findSideType() : sideType;
  }
}
