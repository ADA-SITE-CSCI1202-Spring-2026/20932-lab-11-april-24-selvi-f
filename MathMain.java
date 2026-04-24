class MathTask implements Runnable {
    public void run() {
        long sum = 0;

        for (long i = 0; i < 10_000_000; i++) {
            sum += i * i * i + i;
        }
    }
}

public class MathMain {
    public static void main(String[] args) throws InterruptedException {
        int cores = Runtime.getRuntime().availableProcessors();

        System.out.println("Available logical processors: " + cores);

        runTest(1);
        runTest(cores);
    }

    public static void runTest(int threadCount) throws InterruptedException {
        Thread[] threads = new Thread[threadCount];

        long start = System.currentTimeMillis();

        for (int i = 0; i < threadCount; i++) {
            threads[i] = new Thread(new MathTask());
            threads[i].start();
        }

        for (int i = 0; i < threadCount; i++) {
            threads[i].join();
        }

        long end = System.currentTimeMillis();

        System.out.println(threadCount + " thread(s) time: " + (end - start) + " ms");
    }
}