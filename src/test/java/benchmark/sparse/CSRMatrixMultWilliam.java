package benchmark.sparse;

import matrix.multiplication.sparse.CSRMatrixMultiplication.CSRMatrix;
import matrix.multiplication.utils.MatrixReader;
import org.openjdk.jmh.annotations.*;

import java.io.IOException;
import java.util.concurrent.TimeUnit;
@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.MILLISECONDS)
@State(Scope.Thread)
@Warmup(iterations = 0)
@Measurement(iterations = 1)
@Fork(1)
public class CSRMatrixMultWilliam {

    private static CSRMatrix williamsMatrix;

    @Setup(Level.Trial)
    public static void setup() throws IOException {
        String matrixFilePath = "mc2Matrix/mc2depi.mtx";
        williamsMatrix = MatrixReader.loadMatrixFromMTX(matrixFilePath);
    }

    @Benchmark
    public void benchmarkMatrixMultiplication() {
        CSRMatrix resultMatrix = williamsMatrix.multiply(williamsMatrix);
    }
}