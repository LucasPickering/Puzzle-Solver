package puzzlesolver.test.solver;

import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.BenchmarkMode;
import org.openjdk.jmh.annotations.Measurement;
import org.openjdk.jmh.annotations.Mode;
import org.openjdk.jmh.annotations.Param;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.Setup;
import org.openjdk.jmh.annotations.State;
import org.openjdk.jmh.annotations.Threads;
import org.openjdk.jmh.annotations.Warmup;

import java.util.concurrent.TimeUnit;

import puzzlesolver.Piece;
import puzzlesolver.PieceNotFoundException;
import puzzlesolver.generator.Generator;
import puzzlesolver.generator.SimpleGenerator;
import puzzlesolver.solver.SimpleSolver;

@State(Scope.Benchmark)
@Threads(1)
@Warmup(iterations = 100, time = 10000, timeUnit = TimeUnit.MILLISECONDS)
@Measurement(iterations = 100, time = 10000, timeUnit = TimeUnit.MILLISECONDS)
@BenchmarkMode(Mode.AverageTime)
public class SimpleSolverBenchmark {

  @Param({"50", "100", "200", "300"})
  private int size;
  private Piece[] puzzle;

  @Setup
  public void setUp() {
    final Generator generator = new SimpleGenerator();
    puzzle = generator.generate(size, size);
  }

  @Benchmark
  public void measureFull() {
    SimpleSolver solver = new SimpleSolver();
    solver.init(puzzle);

    try {
      //noinspection StatementWithEmptyBody
      while (solver.nextStep());
    } catch (PieceNotFoundException e) {
      e.printStackTrace();
    }
  }
}
