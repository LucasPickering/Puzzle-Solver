package puzzlesolver.simple;

import com.sun.istack.internal.NotNull;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import puzzlesolver.Point;
import puzzlesolver.Side;
import puzzlesolver.enums.SideType;

/**
 * An implementation of {@code Side}, where each side is represented by a list of {@code Point}s.
 *
 * The coordinates of the {@code Point}s are on an axis relative to the side. The x-axis is the line
 * between the two endpoints of the side, and the y-axis is perpendicular to that, going away from the
 * center of the piece.
 *
 * <h4>Assertions:</h4> <ul> <li><code>points.length > 1</code></li> </ul>
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
   * Cached distance between the corners.
   */
  private Double cornerDistance;

  /**
   * Constructs a new {@code SimpleSide} from the given points.
   *
   * @param points the series of points making up the side, relative to the piece.
   *
   *               These must be in the desired order along the side.
   */
  public SimpleSide(@NotNull Point... points) throws IllegalArgumentException {
    Objects.requireNonNull(points);
    if (points.length < 2) {
      throw new IllegalArgumentException(String.format("Must have at least 2 points: %d found.",
                                                       points.length));
    }

    this.points = points.clone();
  }

  public SimpleSide(List<Point> points) {
    Objects.requireNonNull(points);
    if (points.size() < 2) {
      throw new IllegalArgumentException(String.format("Must have at least 2 points: %d found.",
                                                       points.size()));
    }
    this.points = new Point[points.size()];
    points.toArray(this.points);
  }

  /**
   * Calculate the {@link SideType} based on the points making up this {@link SimpleSide}.
   *
   * Should only be called by {@link #getSideType()}. All other functions should call {@link
   * this.getSideType()}.
   *
   * Assuming {@link #sideType} is currently {@code null}, although nothing bad will happen if it's
   * not.
   *
   * @return the type of the side.
   */
  private SideType findSideType() {
    if (points.length == 2) {
      return sideType = SideType.FLAT;
    }

    double averageHeight = points[0].y;

    for (int i = 1; i < points.length; i++) {
      averageHeight += points[i].y;
    }

    averageHeight /= points.length;
    double baseline = (points[0].y + points[points.length - 1].y) / 2;

    if (averageHeight > baseline) {
      return SideType.OUT;
    } else if (averageHeight == baseline) {
      return SideType.FLAT;
    } else {
      return SideType.IN;
    }
  }

  @Override
  public String toString() {
    final StringBuilder sb = new StringBuilder("SimpleSide{");
    for (int i = 0; i < points.length; i++) {
      sb.append("point").append(i).append("=").append(points[i]).append(", ");
    }
    sb.append("sideType=").append(getSideType());
    sb.append('}');
    return sb.toString();
  }

  /**
   * Checks fit compatibility between this {@link SimpleSide} to the given {@param other} {@link
   * SimpleSide}.
   *
   * @param other the {@link Side} to compare this side to.
   * @return 0 if they are equivalent.
   * @throws NullPointerException if the {@param other} {@link Side} is {@code null}.
   * @throws ClassCastException   if the {@link Side} given is not a {@link SimpleSide}.
   */
  @Override
  public int compareTo(@NotNull Side other) {
    Objects.requireNonNull(other);
    if (!SimpleSide.class.isInstance(other)) {
      throw new ClassCastException(String.format("Cannot compare %s to %s.", getClass().toString(),
                                                 other.getClass().toString()));
    }

    SimpleSide simpleOther = (SimpleSide) other;

    int compatibility = getSideType().compareTo(simpleOther.getSideType());
    if (compatibility != 0) {
      return compatibility;
    }

    int compareResult = Double.compare(getCornerDistance(), other.getCornerDistance());
    if (compareResult != 0) {
      return compareResult;
    }

    Point[] otherPoints = simpleOther.inverse().getPoints();

    Objects.requireNonNull(points);
    Objects.requireNonNull(otherPoints);

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
   * Get a copy of the {@link Point}s, not the original {@link Point}s.
   *
   * @return a copy of the array of {@link Point}s representing this side
   */
  public Point[] getPoints() {
    Point[] copy = new Point[points.length];
    for (int i = 0; i < points.length; i++) {
      copy[i] = points[i].clone();
    }
    return copy;
  }

  @Override
  public double getCornerDistance() {
    return (cornerDistance == null)
           ? cornerDistance = Math.abs(points[points.length - 1].x - points[0].x)
           : cornerDistance;
  }

  @Override
  public SideType getSideType() {
    return (sideType == null) ? sideType = findSideType() : sideType;
  }

  @Override
  public Side copy() {
    return new SimpleSide(getPoints());
  }

  @Override
  public SimpleSide inverse() {
    return new SimpleSide(Arrays.stream(points)
                              .map(point -> new Point(point.x, -point.y))
                              .collect(Collectors.toList()));
  }
}
