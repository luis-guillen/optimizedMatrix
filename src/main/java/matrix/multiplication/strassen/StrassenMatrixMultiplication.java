package matrix.multiplication.strassen;

public class StrassenMatrixMultiplication {

    public static double[][] strassenMultiplication(double[][] A, double[][] B) {
        int n = A.length;
        double[][] C = new double[n][n];

        // Si el tamaño es pequeño, realiza la multiplicación convencional
        if (n <= 2) {
            return conventionalMultiplication(A, B);
        }

        int newSize = n / 2;
        double[][] a11 = new double[newSize][newSize];
        double[][] a12 = new double[newSize][newSize];
        double[][] a21 = new double[newSize][newSize];
        double[][] a22 = new double[newSize][newSize];

        double[][] b11 = new double[newSize][newSize];
        double[][] b12 = new double[newSize][newSize];
        double[][] b21 = new double[newSize][newSize];
        double[][] b22 = new double[newSize][newSize];

        // Dividir las matrices A y B en submatrices
        split(A, a11, 0, 0);
        split(A, a12, 0, newSize);
        split(A, a21, newSize, 0);
        split(A, a22, newSize, newSize);

        split(B, b11, 0, 0);
        split(B, b12, 0, newSize);
        split(B, b21, newSize, 0);
        split(B, b22, newSize, newSize);

        // Calcular las matrices intermedias
        double[][] m1 = strassenMultiplication(add(a11, a22), add(b11, b22));
        double[][] m2 = strassenMultiplication(add(a21, a22), b11);
        double[][] m3 = strassenMultiplication(a11, subtract(b12, b22));
        double[][] m4 = strassenMultiplication(a22, subtract(b21, b11));
        double[][] m5 = strassenMultiplication(add(a11, a12), b22);
        double[][] m6 = strassenMultiplication(subtract(a21, a11), add(b11, b12));
        double[][] m7 = strassenMultiplication(subtract(a12, a22), add(b21, b22));

        // Combinar las submatrices para obtener el resultado
        double[][] c11 = add(subtract(add(m1, m4), m5), m7);
        double[][] c12 = add(m3, m5);
        double[][] c21 = add(m2, m4);
        double[][] c22 = add(subtract(add(m1, m3), m2), m6);

        // Combina las submatrices en la matriz resultado C
        join(c11, C, 0, 0);
        join(c12, C, 0, newSize);
        join(c21, C, newSize, 0);
        join(c22, C, newSize, newSize);

        return C;
    }

    private static double[][] conventionalMultiplication(double[][] A, double[][] B) {
        int n = A.length;
        double[][] C = new double[n][n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                C[i][j] = 0;
                for (int k = 0; k < n; k++) {
                    C[i][j] += A[i][k] * B[k][j];
                }
            }
        }
        return C;
    }

    private static double[][] add(double[][] A, double[][] B) {
        int n = A.length;
        double[][] result = new double[n][n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                result[i][j] = A[i][j] + B[i][j];
            }
        }
        return result;
    }

    private static double[][] subtract(double[][] A, double[][] B) {
        int n = A.length;
        double[][] result = new double[n][n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                result[i][j] = A[i][j] - B[i][j];
            }
        }
        return result;
    }

    private static void split(double[][] P, double[][] C, int iB, int jB) {
        for (int i1 = 0, i2 = iB; i1 < C.length; i1++, i2++) {
            for (int j1 = 0, j2 = jB; j1 < C.length; j1++, j2++) {
                C[i1][j1] = P[i2][j2];
            }
        }
    }

    private static void join(double[][] C, double[][] P, int iB, int jB) {
        for (int i1 = 0, i2 = iB; i1 < C.length; i1++, i2++) {
            for (int j1 = 0, j2 = jB; j1 < C.length; j1++, j2++) {
                P[i2][j2] = C[i1][j1];
            }
        }
    }
}
