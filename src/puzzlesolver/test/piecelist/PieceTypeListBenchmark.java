package puzzlesolver.test.piecelist;

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
import puzzlesolver.generator.Generator;
import puzzlesolver.generator.PolypointGenerator;
import puzzlesolver.piecelist.PieceList;
import puzzlesolver.piecelist.PieceTypePieceList;
import puzzlesolver.piecelist.SimplePieceList;

@State(Scope.Benchmark)
@Warmup(iterations = 10, time = 1000, timeUnit = TimeUnit.MILLISECONDS)
@Measurement(iterations = 10, time = 1000, timeUnit = TimeUnit.MILLISECONDS)
@BenchmarkMode(Mode.AverageTime)
public class PieceTypeListBenchmark {

    @Param({"10", "50", "100", "200"})
    private int size;
    private Piece[] puzzle;
    private PieceList simpleList;
    private PieceList pieceTypeList;
    private PieceList pieceTypeListOpt;

    @Setup
    public void setUp() {
        final Generator generator = new PolypointGenerator();
        puzzle = generator.generate(size, size);
        simpleList = new SimplePieceList(size * size);
        pieceTypeList = new PieceTypePieceList();
        pieceTypeListOpt = new PieceTypePieceList(size * size);
    }

    @Benchmark
    public void measureAddSimple() {
        simpleList.addAll(puzzle);
        simpleList.clear();
    }

    @Benchmark
    public void measureAddPieceType() {
        pieceTypeList.addAll(puzzle);
        pieceTypeList.clear();
    }

    @Benchmark
    public void measureAddPieceTypeOpt() {
        pieceTypeListOpt.addAll(puzzle);
        pieceTypeListOpt.clear();
    }
}
