package puzzlesolver;

import java.util.Arrays;

import puzzlesolver.ui.console.ConsoleController;
import puzzlesolver.ui.fx.MainController;

public class Main {

  public static void main(String[] args) {
    if (Arrays.asList(args).contains("-c") || Constants.USE_CONSOLE) {
      ConsoleController.main(args);
    } else {
      MainController.main(args);
    }
  }
}
