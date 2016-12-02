package puzzlesolver;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.ParseException;

import java.util.Arrays;
import java.util.Random;

import puzzlesolver.constants.ConsoleConstants;
import puzzlesolver.constants.Constants;
import puzzlesolver.enums.ExitCode;
import puzzlesolver.ui.console.ConsoleController;
import puzzlesolver.ui.fx_2d.MainController;

public class Main {

    /**
     * vroom vroom
     */
    public static void main(String[] args) {

        // Enable hardware acceleration
        System.setProperty("sun.java2d.opengl", "true");

        // Find how many v's are in a -v[v[v[...]]] argument if one exists
        Constants.LOGGER.setVerbosity(Arrays.stream(args)
                                          .reduce(0, (integer, s) -> (s.matches("^-(v)+"))
                                                                     ? integer + s.length() - 1
                                                                     : integer,
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
        long seed = new Random().nextLong();
        if (line.hasOption(ConsoleConstants.RANDOM_SEED)) {
            final String seedString = line.getOptionValue(ConsoleConstants.RANDOM_SEED);
            try {
                seed = new Long(seedString);
            } catch (NumberFormatException e) {
                seed = seedString.hashCode();
            }
        }
        Constants.RANDOM.setSeed(seed);

        Constants.LOGGER
            .printf(Logger.INFO, "Verbosity: %d%n", Constants.LOGGER.getGlobalVerbosity());
        Constants.LOGGER.printf(Logger.INFO, "Random Seed: %s%n", seed);

        if (line.hasOption(ConsoleConstants.HELP)) {
            new HelpFormatter().printHelp("puzzlesolver", ConsoleConstants.options, true);
        } else if (line.hasOption(ConsoleConstants.EXIT_CODES)) {
            ExitCode.printAll(System.out);
        } else if (line.hasOption(ConsoleConstants.CLI)) {
            ConsoleController.start(line.hasOption(ConsoleConstants.CLI_FANCY));
            ConsoleController.main(args);
        } else {
            MainController.main(args);
        }
    }
}
