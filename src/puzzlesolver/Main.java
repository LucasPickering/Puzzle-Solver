package puzzlesolver;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;

import puzzlesolver.ui.console.ConsoleController;
import puzzlesolver.ui.fx.MainController;

import static puzzlesolver.constants.ConsoleConstants.CLI;
import static puzzlesolver.constants.ConsoleConstants.CLI_FANCY;
import static puzzlesolver.constants.ConsoleConstants.CLI_FANCY_LONG;
import static puzzlesolver.constants.ConsoleConstants.CLI_LONG;
import static puzzlesolver.constants.ConsoleConstants.CLI_SIMPLE;
import static puzzlesolver.constants.ConsoleConstants.CLI_SIMPLE_LONG;
import static puzzlesolver.constants.ConsoleConstants.HELP;
import static puzzlesolver.constants.ConsoleConstants.HELP_LONG;

public class Main {


  public static void main(String[] args) {
    Options options = getOptions();

    CommandLineParser parser = new DefaultParser();
    CommandLine line;
    try {
      // parse the command line arguments
      line = parser.parse(options, args);

    } catch (ParseException exp) {
      // oops, something went wrong
      System.err.println("Parsing failed.  Reason: " + exp.getMessage());
      System.exit(2);
      return;
    }

    // TODO add behaviour for the rest of the arguments
    if (line.hasOption(HELP)) {
      HelpFormatter formatter = new HelpFormatter();
      formatter.printHelp("puzzlesolver", options, true);
    } else if (line.hasOption(CLI)) {
      ConsoleController.main(line.hasOption(CLI_FANCY));
    } else {
      MainController.main(args);
    }
  }

  private static Options getOptions() {
    Options options = new Options();

    options.addOption(HELP, HELP_LONG, false, "display this message");
    options.addOption(CLI, CLI_LONG, false, "run in console output mode (default: Simple)");
    options.addOption(CLI_SIMPLE, CLI_SIMPLE_LONG, false, "with -" + CLI + ": simple output");
    options.addOption(CLI_FANCY, CLI_FANCY_LONG, false, "with -" + CLI + ": fancy output");

    return options;
  }
}
