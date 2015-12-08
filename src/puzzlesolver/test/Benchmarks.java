package puzzlesolver.test;

import org.openjdk.jmh.annotations.Mode;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.ChainedOptionsBuilder;
import org.openjdk.jmh.runner.options.OptionsBuilder;

import java.util.concurrent.TimeUnit;

public class Benchmarks {

  public static void main(String[] args) throws RunnerException {
    ChainedOptionsBuilder builder = new OptionsBuilder()
        .forks(1)
        .mode(Mode.AverageTime)
        .timeUnit(TimeUnit.MILLISECONDS);

    for (String arg : args) {
      builder.include(arg);
    }

    new Runner(builder.build()).run();
  }
}
