package puzzlesolver.constants;

public class ExitCode {

  public ExitCode(int number, String meaning) {
    this.number = number;
    this.meaning = meaning;
  }

  public int getNumber() {
    return number;
  }

  public String getMeaning() {
    return meaning;
  }

  public void setMeaning(String meaning) {
    this.meaning = meaning;
  }

  int number;
  String meaning;
}
