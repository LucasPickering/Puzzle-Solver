package puzzlesolver;

import java.util.Arrays;

import puzzlesolver.ui.console.ConsoleMain;
import puzzlesolver.ui.fx.FXMain;

public class Main {

  public static void main(String[] args) {
    if (Arrays.asList(args).contains("-c") || Constants.USE_CONSOLE) {
      ConsoleMain.main(args);
    } else {
      FXMain.main(args);
    }
  }
}
