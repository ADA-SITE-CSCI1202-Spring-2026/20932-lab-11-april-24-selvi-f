class SharedResource {
    private int value;
    private boolean bChanged = false;

    public synchronized void set(int value) {
        this.value = value;
        bChanged = true;
        notify();
    }

    public synchronized int get() {
        while (!bChanged) {
            try {
                wait();
            } catch (InterruptedException e) {
                System.out.println("Thread interrupted");
            }
        }

        bChanged = false;
        return value;
    }
}

class Producer implements Runnable {
    private SharedResource resource;

    Producer(SharedResource resource) {
        this.resource = resource;
    }

    public void run() {
        resource.set(100);
    }
}

class Consumer implements Runnable {
    private SharedResource resource;

    Consumer(SharedResource resource) {
        this.resource = resource;
    }

    public void run() {
        int result = resource.get();
        System.out.println("Consumer got: " + result);
    }
}

public class Coordination {
    public static void main(String[] args) {
        SharedResource resource = new SharedResource();

        Thread consumer = new Thread(new Consumer(resource));
        Thread producer = new Thread(new Producer(resource));

        consumer.start();
        producer.start();
    }
}