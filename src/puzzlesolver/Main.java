package puzzlesolver;

import java.util.Arrays;
import java.util.List;

import puzzlesolver.ui.console.ConsoleController;
import puzzlesolver.ui.fx.MainController;

public class Main {

  public static void main(String[] args) {
    List<String> argList = Arrays.asList(args);
    /*Constants.VERBOSE_LEVEL = argList.stream().reduce(0, (Integer integer, String s)
                                                     -> (s.matches("-(v)+"))
                                                        ? integer + s.length() - 1
                                                        : integer,
                                                 (u, u2) -> (u + u2));*/

    if (argList.contains("-c")) {
      ConsoleController.main(args);
    } else {
      MainController.main(args);
    }
  }
}
