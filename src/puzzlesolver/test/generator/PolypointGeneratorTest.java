package puzzlesolver.test.generator;

import org.junit.Test;
import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.BenchmarkMode;
import org.openjdk.jmh.annotations.Measurement;
import org.openjdk.jmh.annotations.Mode;
import org.openjdk.jmh.annotations.Param;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.State;
import org.openjdk.jmh.annotations.Threads;
import org.openjdk.jmh.annotations.Warmup;

import java.util.concurrent.TimeUnit;

import puzzlesolver.generator.Generator;
import puzzlesolver.generator.PolypointGenerator;

public class PolypointGeneratorTest {

  @Test
  public void testGenerate5() {
    Generator generator = new PolypointGenerator(0.2d, 0.2d);
    generator.generate(100, 100);
  }

  @Test
  public void testGenerate10() {
    Generator generator = new PolypointGenerator(0.1d, 0.1d);
    generator.generate(100, 100);
  }

  @Test
  public void testGenerate20() {
    Generator generator = new PolypointGenerator(0.05d, 0.05d);
    generator.generate(100, 100);
  }

  @Test
  public void testGenerate100() {
    Generator generator = new PolypointGenerator(0.01d, 0.01d);
    generator.generate(100, 100);
  }

  @Test
  public void testGenerate500() {
    Generator generator = new PolypointGenerator(0.002d, 0.002d);
    generator.generate(100, 100);
  }
}
