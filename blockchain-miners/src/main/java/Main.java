import model.Miner;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.IntStream;

public class Main {
    public static void main (String[] args) {
        ExecutorService executor = Executors.newFixedThreadPool(6);
        IntStream.range(0, 6).forEach(
                x-> executor.submit(new Miner("Worker nr " + x))
        );
    }
}
