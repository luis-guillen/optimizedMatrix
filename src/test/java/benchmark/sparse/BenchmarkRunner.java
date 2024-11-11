package benchmark.sparse;
import matrix.multiplication.sparse.CSRMatrixMultiplication;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;

public class BenchmarkRunner {
    public static void main(String[] args) throws Exception {
        Options opt = new OptionsBuilder()
                .include(CSRMatrixMultWilliam.class.getSimpleName())    // Incluye CSRBenchmarkWilliam
                .include(CSCMatrixMultBenchmark.class.getSimpleName()) // Incluye CSCMatrixMulBenchmark
                .include(CSRMatrixMultiplication.class.getSimpleName()) // Incluye CSRMatrixMultiplication
                .forks(1) // Número de forks
                .build();

        new Runner(opt).run();
    }
}
