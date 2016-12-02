package puzzlesolver.test;

import org.openjdk.jmh.annotations.Mode;
import org.openjdk.jmh.results.format.ResultFormatType;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.ChainedOptionsBuilder;
import org.openjdk.jmh.runner.options.OptionsBuilder;

import java.util.Date;
import java.util.concurrent.TimeUnit;

import puzzlesolver.constants.Constants;

public class Benchmarks {

    public static final long GENERATOR_SEED = "LOGAN PROBABLY DOESN'T CARE".hashCode();

    public static void main(String[] args) throws RunnerException {
        Date date = new Date();
        ChainedOptionsBuilder builder = new OptionsBuilder().forks(1).mode(Mode.AverageTime)
            .timeUnit(TimeUnit.MILLISECONDS).resultFormat(ResultFormatType.TEXT)
            .result(String.format(Constants.BENCHMARK_LOCATION, date, date, date, date));

        for (String arg : args) {
            builder.include(arg);
        }

        Constants.RANDOM.setSeed(GENERATOR_SEED);
        new Runner(builder.build()).run();
    }
}
