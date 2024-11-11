package benchmark.blocks;

import org.openjdk.jmh.annotations.*;
import java.util.concurrent.TimeUnit;

public class BlocksMatrixMultiplicationBenchmark {

    @State(Scope.Thread)
    public static class MatrixState {
        @Param({"128","256", "512", "1024"})
        private int matrixSize;

        private double[][] A;
        private double[][] B;
        private double[][] C;

        private static final int BLOCK_SIZE = 2;

        @Setup(Level.Trial)
        public void setup() {
            A = generateMatrix(matrixSize, matrixSize);
            B = generateMatrix(matrixSize, matrixSize);
            C = new double[matrixSize][matrixSize];
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
    }

    @Benchmark
    @BenchmarkMode(Mode.AverageTime)
    @OutputTimeUnit(TimeUnit.MILLISECONDS)
    public void testBlockMatrixMultiplication(MatrixState state) {
        blockMatrixMultiplication(state.A, state.B, state.C, state.matrixSize);
    }

    private static void blockMatrixMultiplication(double[][] A, double[][] B, double[][] C, int N) {
        for (int i = 0; i < N; i += MatrixState.BLOCK_SIZE) {
            for (int j = 0; j < N; j += MatrixState.BLOCK_SIZE) {
                for (int k = 0; k < N; k += MatrixState.BLOCK_SIZE) {
                    multiplyBlock(A, B, C, i, j, k, N);
                }
            }
        }
    }

    private static void multiplyBlock(double[][] A, double[][] B, double[][] C, int rowBlock, int colBlock, int kBlock, int N) {
        for (int i = rowBlock; i < Math.min(rowBlock + MatrixState.BLOCK_SIZE, N); i++) {
            for (int j = colBlock; j < Math.min(colBlock + MatrixState.BLOCK_SIZE, N); j++) {
                double sum = 0;
                for (int k = kBlock; k < Math.min(kBlock + MatrixState.BLOCK_SIZE, N); k++) {
                    sum += A[i][k] * B[k][j];
                }
                C[i][j] += sum;
            }
        }
    }
}
