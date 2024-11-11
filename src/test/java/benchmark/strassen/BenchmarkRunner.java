package benchmark.strassen;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;

public class BenchmarkRunner {
    public static void main(String[] args) throws Exception {
        Options opt = new OptionsBuilder()
                .include(StrassenMatrixMultiplicationBenchmark.class.getSimpleName()) // Nombre de la clase de benchmark
                .forks(1)
                .build();

        new Runner(opt).run();
    }
}
