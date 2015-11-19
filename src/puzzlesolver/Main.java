package puzzlesolver;

import java.util.Arrays;
import java.util.List;

import puzzlesolver.constants.Constants;
import puzzlesolver.ui.console.ConsoleController;
import puzzlesolver.ui.fx.MainController;

public class Main {

  public static void main(String[] args) {
    List<String> argList = Arrays.asList(args);

    // this throws a compiler error if not explicitly cast
    // see: http://stackoverflow.com/q/32891632
    //noinspection RedundantCast
    Constants.VERBOSE_LEVEL =
        (int) argList.stream().reduce(0, (integer, s) -> (s.matches("-(v)+"))
                                                         ? integer + s.length() - 1
                                                         : integer,
                                      (i1, i2) -> i1 + i2);

    if (argList.contains("-c"))
    {
      ConsoleController.main(args);
    } else
    {
      MainController.main(args);
    }
  }
}
