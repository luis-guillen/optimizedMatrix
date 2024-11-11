package benchmark.strassen;
import matrix.multiplication.strassen.StrassenMatrixMultiplication;
import org.openjdk.jmh.annotations.*;

import java.util.concurrent.TimeUnit;

@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.MILLISECONDS)
@State(Scope.Thread)
public class StrassenMatrixMultiplicationBenchmark {

    @Param({"128", "256", "512", "1024"})
    private int matrixSize;

    private double[][] A;
    private double[][] B;

    @Setup(Level.Trial)
    public void setup() {
        A = generateMatrix(matrixSize, matrixSize);
        B = generateMatrix(matrixSize, matrixSize);
    }

    private double[][] generateMatrix(int rows, int cols) {
        double[][] matrix = new double[rows][cols];
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                matrix[i][j] = Math.random();
            }
        }
        return matrix;
    }

    @Benchmark
    @Fork(1)
    @Warmup(iterations = 3, time = 1, timeUnit = TimeUnit.SECONDS)
    @Measurement(iterations = 5, time = 1, timeUnit = TimeUnit.SECONDS)
    public double[][] benchmarkStrassenMultiplication() {
        return StrassenMatrixMultiplication.strassenMultiplication(A, B);
    }
}
