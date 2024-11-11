package matrix.multiplication.sparse;

import java.util.ArrayList;
import java.util.List;

public class CSRMatrixMultiplication {

    // Representación de matriz dispersa en formato CSR
    public static class CSRMatrix {
        double[] values;
        int[] columnIndices;
        int[] rowPointers;
        int rows, cols;

        public CSRMatrix(double[] values, int[] columnIndices, int[] rowPointers, int rows, int cols) {
            this.values = values;
            this.columnIndices = columnIndices;
            this.rowPointers = rowPointers;
            this.rows = rows;
            this.cols = cols;
        }

        // Método para multiplicar matrices en formato CSR
        public CSRMatrix multiply(CSRMatrix other) {
            if (this.cols != other.rows) {
                throw new IllegalArgumentException("Dimensiones de matrices no compatibles para multiplicación.");
            }

            List<Double> resultValues = new ArrayList<>();
            List<Integer> resultColumnIndices = new ArrayList<>();
            List<Integer> resultRowPointers = new ArrayList<>();
            resultRowPointers.add(0);

            double[] rowResult = new double[other.cols];

            for (int i = 0; i < this.rows; i++) {
                for (int j = 0; j < other.cols; j++) rowResult[j] = 0.0;

                for (int j = this.rowPointers[i]; j < this.rowPointers[i + 1]; j++) {
                    int colA = this.columnIndices[j];
                    double valA = this.values[j];

                    for (int k = other.rowPointers[colA]; k < other.rowPointers[colA + 1]; k++) {
                        int colB = other.columnIndices[k];
                        rowResult[colB] += valA * other.values[k];
                    }
                }

                int nonZeroCount = 0;
                for (int j = 0; j < other.cols; j++) {
                    if (rowResult[j] != 0.0) {
                        resultValues.add(rowResult[j]);
                        resultColumnIndices.add(j);
                        nonZeroCount++;
                    }
                }

                resultRowPointers.add(resultRowPointers.get(resultRowPointers.size() - 1) + nonZeroCount);
            }

            return new CSRMatrix(
                    resultValues.stream().mapToDouble(Double::doubleValue).toArray(),
                    resultColumnIndices.stream().mapToInt(Integer::intValue).toArray(),
                    resultRowPointers.stream().mapToInt(Integer::intValue).toArray(),
                    this.rows, other.cols
            );
        }
    }

    // Método para convertir una matriz dispersa a formato CSR
    public static CSRMatrix convertToCSR(double[][] matrix) {
        List<Double> valuesList = new ArrayList<>();
        List<Integer> columnIndicesList = new ArrayList<>();
        List<Integer> rowPointersList = new ArrayList<>();
        rowPointersList.add(0);

        int rows = matrix.length;
        int cols = matrix[0].length;

        for (int i = 0; i < rows; i++) {
            int nonZeroCount = 0;
            for (int j = 0; j < cols; j++) {
                if (matrix[i][j] != 0) {
                    valuesList.add(matrix[i][j]);
                    columnIndicesList.add(j);
                    nonZeroCount++;
                }
            }
            rowPointersList.add(rowPointersList.get(rowPointersList.size() - 1) + nonZeroCount);
        }

        return new CSRMatrix(
                valuesList.stream().mapToDouble(Double::doubleValue).toArray(),
                columnIndicesList.stream().mapToInt(Integer::intValue).toArray(),
                rowPointersList.stream().mapToInt(Integer::intValue).toArray(),
                rows, cols
        );
    }
}
