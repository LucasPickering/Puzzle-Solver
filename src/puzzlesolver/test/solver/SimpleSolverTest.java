package puzzlesolver.test.solver;

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

import puzzlesolver.Piece;
import puzzlesolver.generator.PolypointGenerator;
import puzzlesolver.solver.SimpleSolver;

@State(Scope.Benchmark)
@Threads(1)
@Warmup(iterations = 100, time = 10000, timeUnit = TimeUnit.MILLISECONDS)
@Measurement(iterations = 100, time = 10000, timeUnit = TimeUnit.MILLISECONDS)
@BenchmarkMode(Mode.AverageTime)
public class SimpleSolverTest {

  @Param({"50", "100", "200", "300"})
  private int sideLength;
  @Param({"0.2d", "0.1d", "0.05d", "0.01d"})
  private double complexity;

  @Benchmark
  public void measureFull() {
    Piece[] puzzle = new PolypointGenerator(complexity, complexity).generate(sideLength, sideLength);
    SimpleSolver solver = new SimpleSolver();
    solver.init(puzzle);

    //noinspection StatementWithEmptyBody
    while (solver.nextStep()) {
    }
  }
}
