package puzzlesolver.solver;

import puzzlesolver.Piece;
import puzzlesolver.enums.Direction;
import puzzlesolver.enums.PieceType;

public class RotationSolver extends SimpleSolver {

	/**
	 * Keeps track of whether the puzzle has been rotated once.
	 */
	boolean rotated;

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected void placeCorner(State state) {
		for (int i = 0; i < state.unplacedPieces.size(); i++) {
			Piece piece = state.unplacedPieces.get(Direction.NORTH, i);

			// For the first corner piece found
			if (piece.definitelyType(PieceType.CORNER)) {
				// Rotate it until it fits in the top-left corner
				while (!piece.getSide(Direction.NORTH).isFlat()
							 || !piece.getSide(Direction.WEST).isFlat()) {
					piece.rotate(Direction.NORTH, Direction.EAST);
				}
				state.placePiece(piece);
				state.unplacedPieces.remove(piece);
				return; // We placed it, done!
			}
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected void placeNextPiece(State state) {
		final Piece madePiece = makePiece(state);
		Piece foundPiece = state.unplacedPieces.find(madePiece);
		int rotations;
		// Look for matches, and rotates the piece up to 3 times if no matches are found
		for (rotations = 0; foundPiece == null && rotations < Direction.values().length - 1;
				 rotations++, foundPiece = state.unplacedPieces.find(madePiece)) {
			madePiece.rotate(Direction.NORTH, Direction.EAST); // If no matches were found, rotate once
		}

		/*
		 * There's a chance that the "wrong" first corner was placed, and the puzzles is rotated 90
		 * degrees from what it should be. If the puzzle is non-square, it won't fit. We assume that
		 * when we can't find a specific piece, that it's because we're looking for an edge piece
		 * that shouldn't actually be an edge piece. This means we've reached the end of the array
		 * before we should've, and that the puzzle is rotated 90 degrees. Re-allocate the array with
		 * reversed dimensions, and we're good to go.
		 */
		if (foundPiece == null) { // If it didn't find a piece
			// If the puzzle hasn't been rotated yet
			if (!rotated) {
				rotated = true;
				rotateSolution(state);
				placeNextPiece(state); // Call again with the newly-rotated solution
				return;
			}
			throw new IllegalStateException(String.format("No piece found to go at (%d, %d)",
																										state.x, state.y));
		}

		foundPiece.rotate(Direction.values()[rotations], Direction.NORTH); // Rotate it back
		state.placePiece(foundPiece); // Put it in the solution
		state.unplacedPieces.remove(foundPiece); // Remove it from the list of unplaced pieces
	}

	/**
	 * Re-allocates the solution array with reversed dimensions. The old width is the new height, and
	 * vice versa. Puts all the old pieces in the new array without changing their positions at all.
	 */
	protected void rotateSolution(State state) {
		// Swap width and height
		final int newWidth = state.height();
		final int newHeight = state.width();

		Piece[][] newSolution = new Piece[newWidth][newHeight]; // Make a new solution with w/h swapped
		final int minDimension = Math.min(newWidth, newHeight); // We'll need this soon
		for (int x = 0; x < newWidth && x < newHeight; x++) { // Copy the old data into the new solution
			newSolution[x] = new Piece[newHeight];
			System.arraycopy(state.solution[x], 0, newSolution[x], 0, minDimension);
		}
		state.solution = newSolution;

		// If x is now out of bounds, fix it
		if (state.x >= newWidth) {
			state.x = 0;
			state.y++;
		}
	}
}
