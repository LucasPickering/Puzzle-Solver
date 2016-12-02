package puzzlesolver;

import java.util.Comparator;
import java.util.Objects;

/**
 * A class to represent immutable points, with non-integer x and y values. Similar to
 * {@link Coord}, except Coord holds doubles and this holds ints.
 *
 * Origin is at the top-left.
 */
public class Point implements Comparable<Point>, Cloneable {

    private static final Comparator<Double> comp = new DoubleDeltaComparator();

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
     *
     * @param x the x value of the point
     * @param y the y value of the point
     */
    public Point(double x, double y) {
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
        return String.format("(%f, %f)", x, y);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Point point = (Point) o;
        return Double.compare(point.x, x) == 0 &&
               Double.compare(point.y, y) == 0;
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }

    @Override
    public int compareTo(Point p) {
        if (this == p) {
            return 0;
        }

        int compareResult = comp.compare(y, p.y);
        if (compareResult != 0) {
            return compareResult;
        }
        return comp.compare(x, p.x);
    }
}
