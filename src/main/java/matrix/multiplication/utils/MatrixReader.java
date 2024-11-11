package matrix.multiplication.utils;

import java.io.*;
import java.util.*;
import matrix.multiplication.sparse.CSRMatrixMultiplication.CSRMatrix;

public class MatrixReader {
    public static CSRMatrix loadMatrixFromMTX(String matrixFilePath) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(matrixFilePath));

        String line;
        int nRows = 0, nCols = 0, nNonZeros = 0;
        List<int[]> rowIndices = new ArrayList<>();
        List<int[]> colIndices = new ArrayList<>();
        List<Double> values = new ArrayList<>();

        while ((line = reader.readLine()) != null) {
            if (line.startsWith("%")) {
                continue; // Ignorar comentarios
            }
            String[] parts = line.split("\\s+");
            if (parts.length == 3) {
                // El encabezado contiene las dimensiones
                nRows = Integer.parseInt(parts[0]);
                nCols = Integer.parseInt(parts[1]);
                nNonZeros = Integer.parseInt(parts[2]);
                break;
            }
        }

        // Leer los elementos de la matriz
        while ((line = reader.readLine()) != null) {
            String[] parts = line.split("\\s+");
            int rowIndex = Integer.parseInt(parts[0]) - 1; // Índice basado en 0
            int colIndex = Integer.parseInt(parts[1]) - 1; // Índice basado en 0
            double value = Double.parseDouble(parts[2]);

            rowIndices.add(new int[] { rowIndex });
            colIndices.add(new int[] { colIndex });
            values.add(value);
        }

        reader.close();

        // Convertir listas a arreglos
        int[] rows = new int[nNonZeros];
        int[] cols = new int[nNonZeros];
        double[] vals = new double[nNonZeros];

        for (int i = 0; i < nNonZeros; i++) {
            rows[i] = rowIndices.get(i)[0];
            cols[i] = colIndices.get(i)[0];
            vals[i] = values.get(i);
        }

        // Aquí puedes crear la representación CSR
        int[] rowPtr = new int[nRows + 1];
        for (int i = 0; i < nNonZeros; i++) {
            rowPtr[rows[i] + 1]++;
        }
        for (int i = 1; i <= nRows; i++) {
            rowPtr[i] += rowPtr[i - 1];
        }

        return new CSRMatrix(vals, cols, rowPtr, nRows, nCols); // Devuelve el CSRMatrix correcto
    }
}
