package puzzlesolver;

/**
 * An interface to represent puzzle generators. These puzzles have the following properties: <ul>
 * <li>Square grid structure</li> <li>Each piece is unique from each other piece</li> </ul>
 */
public interface Generator {

  /**
   * Sets the seed to be used for the {@link java.util.Random Random} that the puzzle is generated
   * with. Two puzzles generated with the same seed are guaranteed to be identical, including the
   * order of the pieces after they have been shuffled. This should be called before {@link
   * #generate}.
   *
   * @param seed the seed for the random generator
   */
  void setSeed(long seed);

  /**
   * Sets the seed to be used for the {@link java.util.Random Random} that the puzzle is generated
   * with. Uses the {@link Object#hashCode} of the object as the seed. Two puzzles generated with the
   * game seed are guaranteed to be identical, including the order of the pieces after they have been
   * shuffled. This should be calle before {@link #generate}.
   */
  void setSeed(Object o);

  /**
   * Generates a puzzle of entirely unique pieces of the given grid size.
   *
   * @param width  the width of the grid
   * @param height the height of the grid
   * @return an array of size {@code width * height} of all pieces generated, in random order
   */
  Piece[] generate(int width, int height);

  /**
   * Generates a new {@link Side} according to this generator's rules.
   * @param flat if true, the side will be flat (only have two points)
   * @return the new side
   */
  Side generateSide(boolean flat);

}
