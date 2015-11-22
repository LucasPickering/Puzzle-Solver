package puzzlesolver;

/**
 * An interface to represent puzzle generators. These puzzles have the following properties: <ul>
 * <li>Square grid structure</li> <li>Each piece is unique from each other piece</li> </ul>
 */
public interface Generator {

  /**
   * Generates a puzzle of entirely unique pieces of the given grid size. Generation is done from
   * the given seed, meaning two puzzles generated from identical calls to this method (same seed
   * and size) will produce identical puzzles.
   * @param seed the seed for the random generator
   * @param width the width of the grid
   * @param height the height of the grid
   * @return an array of size {@code width * height} of all pieces generated, in random order
   */
  Piece[] generate(long seed, int width, int height);

  /**
   * Generates a puzzle of entirely unique pieces of the given grid size.
   *
   * @param width  the width of the grid
   * @param height the height of the grid
   * @return an array of size {@code width * height} of all pieces generated, in random order
   */
  Piece[] generate(int width, int height);

}
