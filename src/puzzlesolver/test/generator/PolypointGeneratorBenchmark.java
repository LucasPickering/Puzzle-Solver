package puzzlesolver.test.generator;

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
import puzzlesolver.test.Benchmarks;


@State(Scope.Benchmark)
@Threads(1)
@Warmup(iterations = 10, time = 1000, timeUnit = TimeUnit.MILLISECONDS)
@Measurement(iterations = 10, time = 1000, timeUnit = TimeUnit.MILLISECONDS)
@BenchmarkMode(Mode.AverageTime)
public class PolypointGeneratorBenchmark {

    @Param({"50", "100", "200", "300"})
    private int sideLength;
    @Param({"0.2d", "0.1d", "0.05d", "0.01d"})
    private double complexity;

    @Benchmark
    public void measureGenerate() {
        Generator generator = new PolypointGenerator(complexity, complexity);
        generator.generate(sideLength, sideLength);
    }
}
