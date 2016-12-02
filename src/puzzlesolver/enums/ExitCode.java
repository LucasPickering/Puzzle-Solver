package puzzlesolver.enums;

import java.io.PrintStream;

public enum ExitCode {

    NORMAL(0, "Normal"),
    CLI_ARG_PARSE_ERROR(1, "error in parsing program arguments");

    public final int number;
    public final String meaning;

    ExitCode(int number, String meaning) {
        this.number = number;
        this.meaning = meaning;
    }

    @Override
    public String toString() {
        return String.format("%d\t%s", number, meaning);
    }

    public static void printAll(PrintStream out) {
        for (ExitCode c : values()) {
            out.println(c);
        }
    }
}
