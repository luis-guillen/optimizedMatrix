package matrix.multiplication.blocks;

public class BlocksMatrixMultiplication {

    private static final int TILE_SIZE = 2;

    public static void main(String[] args) {
        int matrixSize = 4;
        double[][] matrixA = initializeMatrix(matrixSize);
        double[][] matrixB = initializeMatrix(matrixSize);
        double[][] resultMatrix = new double[matrixSize][matrixSize];

        performBlockMultiplication(matrixA, matrixB, resultMatrix, matrixSize);
    }

    // Initializes a matrix with random values for benchmarking purposes
    private static double[][] initializeMatrix(int size) {
        double[][] matrix = new double[size][size];
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                matrix[i][j] = Math.random();
            }
        }
        return matrix;
    }

    // Perform block-based matrix multiplication
    private static void performBlockMultiplication(double[][] matrixA, double[][] matrixB, double[][] resultMatrix, int matrixSize) {
        for (int i = 0; i < matrixSize; i += TILE_SIZE) {
            for (int j = 0; j < matrixSize; j += TILE_SIZE) {
                for (int k = 0; k < matrixSize; k += TILE_SIZE) {
                    multiplyTile(matrixA, matrixB, resultMatrix, i, j, k, matrixSize);
                }
            }
        }
    }

    // Multiply individual tiles within the matrices
    private static void multiplyTile(double[][] matrixA, double[][] matrixB, double[][] resultMatrix, int row, int col, int inner, int size) {
        for (int i = row; i < Math.min(row + TILE_SIZE, size); i++) {
            for (int j = col; j < Math.min(col + TILE_SIZE, size); j++) {
                double sum = 0;
                for (int k = inner; k < Math.min(inner + TILE_SIZE, size); k++) {
                    sum += matrixA[i][k] * matrixB[k][j];
                }
                resultMatrix[i][j] += sum;
            }
        }
    }
}
