package puzzlesolver.piecelist;

import com.sun.istack.internal.NotNull;

import puzzlesolver.Piece;
import puzzlesolver.enums.Direction;
import puzzlesolver.enums.PieceType;

/**
 * A list of pieces. All pieces stored in this list are "concrete" pieces, meaning they have no
 * {@code null} sides, and are actual pieces in the puzzle being solved. The list can only be
 * accessed by specifying a {@link Direction}. Whenever accessed, the list can be considered to be
 * sorted by the specified Direction.
 */
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
     * @return true if something was removed, false otherwise
     */
    boolean remove(Piece p);

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
     * Is this list empty?
     *
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
     * Finds a piece that matches the given piece. There should be at most one piece that matches
     * the given one, since pieces have at least one defined side and all sides are unique.
     *
     * @param p the piece to be found (non-null)
     * @return the matching piece, or {@code null} if it isn't found
     * @throws NullPointerException if p is {@code null}
     */
    Piece find(@NotNull Piece p);

    /**
     * Get a sorted list of all pieces in this list that are of the given type.
     *
     * @param pieceType the type to filter by
     * @return a sublist containing all (and only) the pieces of the given type
     */
    PieceList sublistByType(PieceType pieceType);
}
