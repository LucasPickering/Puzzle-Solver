package puzzlesolver.side;

/**
 * An interface to represent one side of a puzzle piece.
 *
 * Sides have a type which MUST be calculated and NOT provided to a constructor. Sub-classes MUST
 * override {@link Object#equals} and {@link Object#hashCode}
 *
 * Note: sub-classes may have a natural ordering that is inconsistent with {@link Object#equals}.
 * {@link Object#equals} MUST be no less specific about equality than {@link Comparable#compareTo},
 * and will typically be more specific.
 */
public interface Side extends Comparable<Side>, Cloneable {

  enum SideType {
    FLAT, IN, OUT
  }

  /**
   * Gets the straight-line distance from one corner of this side to another.
   *
   * @return the straight-line corner distance.
   */
  double getCornerDistance();

  /**
   * Gets the {@link SideType} of this side.
   *
   * @return the {@link SideType}
   */
  SideType getSideType();

  /**
   * Creates a deep copy of this side and returns it.
   *
   * @return a deep copy of this side, with no shared variables
   */
  Side copy();
}
