package puzzlesolver;

import java.util.Objects;

/**
 * A class to represent immutable coordinates, with integer x and y values. Similar to
 * {@link Point}, except Point holds doubles and this holds ints. Also, {@link Point#compareTo}
 * compares y before x, while {@link #compareTo} compares x before y.
 *
 * Origin is at the top-left.
 */
public class Coord implements Comparable<Coord>, Cloneable {

    /**
     * X-coordinate of the point (distance from left).
     */
    public final int x;

    /**
     * Y-coordinate of the point (distance from top).
     */
    public final int y;

    /**
     * Constructs a new {@code Point} with the given x and y values.
     *
     * @param x the x value of the point
     * @param y the y value of the point
     */
    public Coord(int x, int y) {
        this.x = x;
        this.y = y;
    }

    @Override
    public Coord clone() {
        try {
            super.clone();
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }

        return new Coord(x, y);
    }

    @Override
    public String toString() {
        return String.format("(%d, %d)", x, y);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Coord coord = (Coord) o;
        return x == coord.x && y == coord.y;
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }

    @Override
    public int compareTo(Coord p) {
        if (this == p) {
            return 0;
        }

        int compareResult = Integer.compare(y, p.y);
        if (compareResult != 0) {
            return compareResult;
        }
        return Integer.compare(x, p.x);
    }
}
