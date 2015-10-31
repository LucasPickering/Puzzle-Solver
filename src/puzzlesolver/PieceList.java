package puzzlesolver;

import com.sun.istack.internal.NotNull;


import puzzlesolver.enums.Direction;

public interface PieceList {

  /**
   * Adds a piece to this list, while keeping all internal lists sorted.
   *
   * @param p the piece to be added
   */
  void add(Piece p);

  /**
   * Adds all pieces in the given array
   *
   * @param pieces the pieces to be added (non-null, does not contain null)
   */
  void addAll(@NotNull Piece[] pieces);

  /**
   * Removes the given piece from this list, if it exists in the list.
   *
   * @param p the piece to be removed
   */
  void remove(Piece p);

  /**
   * Clears this list.
   */
  void clear();

  /**
   * Gets the piece at the given index if the list is sorted by the given direction.
   *
   * @param dir the direction of the internal list to be indexed (non-null)
   * @param i   the index of the item to be gotten
   * @return the piece at the given index in the list of the given direction
   * @throws IndexOutOfBoundsException if {@code i} is out of the bounds of this list
   */
  Piece get(@NotNull Direction dir, int i);

  /**
   * Gets the first item in this list when sorted by the given direction.
   *
   * @param dir the direction to sort by (non-null)
   * @return the first item in the list
   * @throws IndexOutOfBoundsException if this list is empty
   */
  Piece first(@NotNull Direction dir);

  /**
   * Is this list empty?
   * @return true if this list has no elements, i.e. size() == 0, false otherwise
   */
  boolean isEmpty();

  /**
   * Gets the size of this list.
   *
   * @return the amount of elements of this list
   */
  int size();

  /**
   * Searches all sides of the given direction of each piece for the given side.
   *
   * @param dir the direction to sort this list by (non-null)
   * @param s   the side to be found
   * @return the index of the search key, if it is contained in the list; otherwise, (-(insertion
   * point) - 1). The insertion point is defined as the point at which the key would be inserted
   * into the list: the index of the first element greater than the key, or list.size() if all
   * elements in the list are less than the specified key. Note that this guarantees that the return
   * value will be >= 0 if and only if the key is found.
   */
  int binarySearch(@NotNull Direction dir, Side s);

  /**
   * Does any piece in this list contain the given side?
   *
   * @param s the side to be found
   * @return true if any piece in this list contains s, false otherwise
   */
  boolean containsSide(Side s);
}
