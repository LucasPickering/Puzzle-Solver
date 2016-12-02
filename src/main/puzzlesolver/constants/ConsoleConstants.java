package puzzlesolver.constants;

import org.apache.commons.cli.Options;

import java.util.HashMap;
import java.util.Map;

public class ConsoleConstants {

    public static final String
        HELP = "h", HELP_LONG = "help",
        CLI = "c", CLI_LONG = "cli",
        CLI_SIMPLE = "s", CLI_SIMPLE_LONG = "cli-simple",
        CLI_FANCY = "f", CLI_FANCY_LONG = "cli-fancy",
        VERBOSE = "v",
        EXIT_CODES = "e", EXIT_CODES_LONG = "exit-codes",
        RANDOM_SEED = "r", RANDOM_SEED_LONG = "random-seed";

    public static final Options options = new Options() {
        {
            this.addOption(HELP, HELP_LONG, false, "display this message");
            this.addOption(CLI, CLI_LONG, false, "run in console output mode (default: Simple)");
            this.addOption(CLI_SIMPLE, CLI_SIMPLE_LONG, false, "with -" + CLI + ": simple output");
            this.addOption(CLI_FANCY, CLI_FANCY_LONG, false, "with -" + CLI + ": fancy output");
            this.addOption(VERBOSE, "verbosity level (more v's in more places)");
            this.addOption(EXIT_CODES, EXIT_CODES_LONG, false, "print meanings of exit codes");
            this.addOption(RANDOM_SEED, RANDOM_SEED_LONG, true,
                           "specify the random seed to be used");
        }
    };

    private static final Map<Integer, String> impl_exitCodes = new HashMap<Integer, String>() {{
        this.put(1, "error in parsing program arguments");
    }};

}
