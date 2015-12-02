package puzzlesolver;

import puzzlesolver.enums.SideType;

/**
 * An interface to represent one side of a puzzle piece.
 *
 * Sides have a type which MUST be calculated during solution and CANNOT provided to a constructor or
 * determined during generation. Sub-classes MUST override {@link Object#equals} and {@link
 * Object#hashCode}.
 *
 * Note: sub-classes may have a natural ordering that is inconsistent with {@link Object#equals}.
 * {@link Object#equals} MUST be no less specific about equality than {@link Comparable#compareTo},
 * and will typically be more specific.
 */
public interface Side extends Comparable<Side>, Cloneable {

  /**
   * Gets the straight-line distance from one corner of this side to another.
   *
   * Must return a positive (> 0) value.
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

  /**
   * Creates a deep copy of the inverse of this side.
   *
   * @return a deep copy of the inverse of this side, with no shared variables
   */
  Side inverse();

  /**
   * Get a <i>shallow</i> copy of the {@link Point}s, not the original {@link Point}s. The array
   * itself is copied, but each {@link Point} is not.
   *
   * @return a copy of the array of {@link Point}s representing this side.
   */
  Point[] getPoints();

  /**
   * Is this side flat or not?
   *
   * @return true if {@link #getSideType} == {@link SideType#FLAT}, false otherwise
   */
  default boolean isFlat() {
    return getSideType() == SideType.FLAT;
  }
}
