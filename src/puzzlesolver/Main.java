package puzzlesolver;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.ParseException;

import java.util.Arrays;

import puzzlesolver.constants.ConsoleConstants;
import puzzlesolver.constants.Constants;
import puzzlesolver.ui.console.ConsoleController;
import puzzlesolver.ui.fx.MainController;

import static puzzlesolver.constants.ConsoleConstants.CLI;
import static puzzlesolver.constants.ConsoleConstants.CLI_FANCY;
import static puzzlesolver.constants.ConsoleConstants.HELP;
import static puzzlesolver.constants.ConsoleConstants.RANDOM_SEED;

public class Main {

  /**
   * vroom vroom
   */
  public static void main(String[] args) {

    // Find how many v's are in a -v[v[v[...]]] argument if one exists
    // I apologize sincerely for how disgusting this is, but I wanted to do it in one line.

    // This throws a compiler error if not explicitly cast (see: http://stackoverflow.com/q/32891632)
    // noinspection RedundantCast
    Constants.LOGGER.setVerbosity((int) Arrays.asList(args)
        .stream()
        .reduce(0, (integer, s) -> (s.matches("-(v)+"))
                                   ? integer + s.length() - 1
                                   : integer,
                (i1, i2) -> i1 + i2));

    Constants.LOGGER.printf(1, "Verbose Level: %d%n", Constants.LOGGER.getGlobalVerbosity());
    Constants.LOGGER.printf(1, "Random Seed: %d%n", Constants.RANDOM_SEED);

    CommandLineParser parser = new DefaultParser();
    CommandLine line;
    try {
      // parse the command line arguments
      line = parser.parse(ConsoleConstants.options, args);

    } catch (ParseException exp) {
      System.err.println("Parsing failed.  Reason: " + exp.getMessage());
      System.exit(1);
      return;
    }

    // TODO add behaviour for the rest of the arguments
    if (line.hasOption(RANDOM_SEED)) {
      Constants.RANDOM_SEED = line.getOptionValue(RANDOM_SEED).hashCode();
    }

    if (line.hasOption(HELP)) {
      HelpFormatter formatter = new HelpFormatter();
      formatter.printHelp("puzzlesolver", ConsoleConstants.options, true);
    } else if (line.hasOption(CLI)) {
      ConsoleController.start(line.hasOption(CLI_FANCY));
    } else {
      MainController.main(args);
    }
  }
}
