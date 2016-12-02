package puzzlesolver.solver;

import puzzlesolver.Piece;
import puzzlesolver.PieceNotFoundException;
import puzzlesolver.piecelist.PieceList;

/**
 * A Solver solves a puzzle. A Solver must first be initialized using the {@link #init} method to
 * give it a set of pieces to work with. Then, you can call {@link #nextStep} until
 * {@link #done} returns true. Solvers support multiple solutions over multiple different
 * puzzles! Just call {@link #init} again to solve another puzzle.
 */
public interface Solver {

    /**
     * Initializes this solver to use the given array of pieces.
     *
     * @param pieces the pieces that make up the puzzle, in any order
     */
    void init(Piece[] pieces);

    /**
     * Executes the next step in the solver, placing exactly one piece. If {@link #done} returns
     * true, this does nothing.
     */
    void nextStep() throws PieceNotFoundException;

    /**
     * Are we done solving the puzzle?
     *
     * @return true if the puzzle is complete, false otherwise
     */
    boolean done();

    /**
     * Gets the list of pieces that have not yet been placed into the solution. No copying is done,
     * so <i>please</i> don't screw with this object! Read only!
     *
     * @return all unplaced pieces
     */
    PieceList getUnplacedPieces();

    /**
     * Gets this solver's solution in its current place. All pieces in the solution will be in their
     * correct spot, with null spots for unplaced pieces. No copying is done, so <i>please</i> don't
     * screw with this object! Read only!
     *
     * @return a 2-D array representing all pieces that have been placed, in their final spots
     */
    Piece[][] getSolution();

    /**
     * Gets the total amount of pieces (both placed and unplaced).
     *
     * @return the total number of pieces in this solver's puzzle
     */
    int getTotalPieces();

    /**
     * Gets the amount of pieces that have been placed in the puzzle so far.
     *
     * @return the total number of pieces placed
     */
    int getPiecesPlaced();

}
