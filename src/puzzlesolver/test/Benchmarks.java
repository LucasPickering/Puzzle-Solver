package puzzlesolver.test;

import org.openjdk.jmh.annotations.Mode;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;

import java.util.concurrent.TimeUnit;

import puzzlesolver.test.polypoint.PolypointGeneratorTest;

public class Benchmarks {

  public static void main(String[] args) throws RunnerException {
    Options opt = new OptionsBuilder()
      .include(PolypointGeneratorTest.class.getSimpleName())
      .forks(1)
      .mode(Mode.AverageTime)
      .timeUnit(TimeUnit.MILLISECONDS)
      .build();

    new Runner(opt).run();
  }
}
