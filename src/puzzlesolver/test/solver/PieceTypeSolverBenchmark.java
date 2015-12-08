package puzzlesolver.test.solver;

import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.BenchmarkMode;
import org.openjdk.jmh.annotations.Measurement;
import org.openjdk.jmh.annotations.Mode;
import org.openjdk.jmh.annotations.Param;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.Setup;
import org.openjdk.jmh.annotations.State;
import org.openjdk.jmh.annotations.Warmup;

import java.util.concurrent.TimeUnit;

import puzzlesolver.Piece;
import puzzlesolver.generator.PolypointGenerator;
import puzzlesolver.solver.PieceTypeRotationSolver;
import puzzlesolver.solver.SimpleSolver;
import puzzlesolver.solver.Solver;

@State(Scope.Benchmark)
@Warmup(iterations = 10, time = 500, timeUnit = TimeUnit.MILLISECONDS)
@Measurement(iterations = 10, time = 500, timeUnit = TimeUnit.MILLISECONDS)
@BenchmarkMode(Mode.AverageTime)
public class PieceTypeSolverBenchmark {

  @Param({"10", "50", "100", "200"})
  private int size;
  private Piece[] puzzle;
  private Solver simpleSolver;
  private Solver pieceTypeSolver;

  @Setup
  public void setUp() {
    puzzle = new PolypointGenerator().generate(size, size);
  }

  @Benchmark
  public void measureSolveSimple() {
    simpleSolver = new SimpleSolver();
    simpleSolver.init(puzzle);
    while(simpleSolver.nextStep());
  }

  @Benchmark
  public void measureSolvePieceType() {
    pieceTypeSolver = new PieceTypeRotationSolver();
    pieceTypeSolver.init(puzzle);
    while(pieceTypeSolver.nextStep());
  }
}
