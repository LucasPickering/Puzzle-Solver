package puzzlesolver;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.ParseException;

import java.util.Arrays;

import puzzlesolver.constants.ConsoleConstants;
import puzzlesolver.constants.Constants;
import puzzlesolver.enums.ExitCode;
import puzzlesolver.ui.console.ConsoleController;
import puzzlesolver.ui.fx_2d.MainController;

import static puzzlesolver.constants.ConsoleConstants.CLI;
import static puzzlesolver.constants.ConsoleConstants.CLI_FANCY;
import static puzzlesolver.constants.ConsoleConstants.EXIT_CODES;
import static puzzlesolver.constants.ConsoleConstants.HELP;
import static puzzlesolver.constants.ConsoleConstants.RANDOM_SEED;

public class Main {

  /**
   * vroom vroom
   */
  public static void main(String[] args) {

    // Enable hardware acceleration
    System.setProperty("sun.java2d.opengl", "true");

    // Find how many v's are in a -v[v[v[...]]] argument if one exists
    // I apologize sincerely for how disgusting this is, but I wanted to do it in one line.
    // "One line" is a bit of a stretch but w/e - very serious code reviewer
    Constants.LOGGER.setVerbosity(Arrays.stream(args)
                                      .reduce(0, (verbLevel, s) -> (s.matches("-(v)+"))
                                                                   ? verbLevel + s.length() - 1
                                                                   : verbLevel,
                                              (i1, i2) -> i1 + i2));

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

    Constants.LOGGER.printf(Logger.INFO, "Verbose Level: %d%n",
                            Constants.LOGGER.getGlobalVerbosity());
    Constants.LOGGER.printf(Logger.INFO, "Random Seed: %d%n", Constants.RANDOM_SEED);

    if (line.hasOption(HELP)) {
      HelpFormatter formatter = new HelpFormatter();
      formatter.printHelp("puzzlesolver", ConsoleConstants.options, true);
    } else if (line.hasOption(EXIT_CODES)) {
      ExitCode.printAll(System.out);
    } else if (line.hasOption(CLI)) {
      ConsoleController.start(line.hasOption(CLI_FANCY));
      ConsoleController.main(args);
    } else {
      MainController.main(args);
    }
  }
}
