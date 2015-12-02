package puzzlesolver.enums;

public enum ExitCode {

  GOOD(0, "Good");

  public final int number;
  public final String meaning;

  ExitCode(int number, String meaning) {
    this.number = number;
    this.meaning = meaning;
  }
}
