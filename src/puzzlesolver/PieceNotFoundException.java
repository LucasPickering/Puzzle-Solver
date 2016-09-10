package puzzlesolver;

public class PieceNotFoundException extends Exception {

  public PieceNotFoundException(int x, int y) {
    super(String.format("No piece found to go at (%d, %d)", x, y));
  }
}
