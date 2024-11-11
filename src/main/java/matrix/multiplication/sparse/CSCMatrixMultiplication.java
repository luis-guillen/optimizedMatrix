package matrix.multiplication.sparse;

import java.util.ArrayList;
import java.util.List;

public class CSCMatrixMultiplication {

    // Representación de matriz dispersa en formato CSC
    public static class CSCMatrix {
        double[] values;
        int[] rowIndices;
        int[] colPointers;
        int rows, cols;

        CSCMatrix(double[] values, int[] rowIndices, int[] colPointers, int rows, int cols) {
            this.values = values;
            this.rowIndices = rowIndices;
            this.colPointers = colPointers;
            this.rows = rows;
            this.cols = cols;
        }

        // Multiplicación de matrices en formato CSC
        public CSCMatrix multiply(CSCMatrix other) {
            if (this.cols != other.rows) {
                throw new IllegalArgumentException("Dimensiones de matrices no compatibles para multiplicación.");
            }

            List<Double> resultValues = new ArrayList<>();
            List<Integer> resultRowIndices = new ArrayList<>();
            List<Integer> resultColPointers = new ArrayList<>();
            resultColPointers.add(0);

            double[] tempResult = new double[this.rows];

            for (int jB = 0; jB < other.cols; jB++) {
                // Limpiar el vector temporal
                for (int i = 0; i < this.rows; i++) tempResult[i] = 0;

                for (int k = other.colPointers[jB]; k < other.colPointers[jB + 1]; k++) {
                    int rowB = other.rowIndices[k];
                    double valB = other.values[k];

                    for (int i = this.colPointers[rowB]; i < this.colPointers[rowB + 1]; i++) {
                        int rowA = this.rowIndices[i];
                        tempResult[rowA] += this.values[i] * valB;
                    }
                }

                int nonZeroCount = 0;
                for (int i = 0; i < this.rows; i++) {
                    if (tempResult[i] != 0.0) {
                        resultValues.add(tempResult[i]);
                        resultRowIndices.add(i);
                        nonZeroCount++;
                    }
                }
                resultColPointers.add(resultColPointers.get(resultColPointers.size() - 1) + nonZeroCount);
            }

            return new CSCMatrix(
                    resultValues.stream().mapToDouble(Double::doubleValue).toArray(),
                    resultRowIndices.stream().mapToInt(Integer::intValue).toArray(),
                    resultColPointers.stream().mapToInt(Integer::intValue).toArray(),
                    this.rows, other.cols
            );
        }
    }

    // Método para convertir una matriz dispersa a formato CSC
    public static CSCMatrix convertToCSC(double[][] matrix) {
        List<Double> valuesList = new ArrayList<>();
        List<Integer> rowIndicesList = new ArrayList<>();
        List<Integer> colPointersList = new ArrayList<>();
        colPointersList.add(0);

        int rows = matrix.length;
        int cols = matrix[0].length;

        for (int j = 0; j < cols; j++) {
            int nonZeroCount = 0;
            for (int i = 0; i < rows; i++) {
                if (matrix[i][j] != 0) {
                    valuesList.add(matrix[i][j]);
                    rowIndicesList.add(i);
                    nonZeroCount++;
                }
            }
            colPointersList.add(colPointersList.get(colPointersList.size() - 1) + nonZeroCount);
        }

        return new CSCMatrix(
                valuesList.stream().mapToDouble(Double::doubleValue).toArray(),
                rowIndicesList.stream().mapToInt(Integer::intValue).toArray(),
                colPointersList.stream().mapToInt(Integer::intValue).toArray(),
                rows, cols
        );
    }
}
