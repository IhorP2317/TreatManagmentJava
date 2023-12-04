package guest;

import utils.SimpleTask;

public class TableGuest implements Guest {

    private SimpleTask onStart;
    private SimpleTask onFinish;

    private static long counter = 0;
    private static final Object lock = new Object();

    @Override
    public void makeOrder() {
        try {
//            System.out.println("Ordering table on " + Thread.currentThread().getName());

            for (long i = 0; i < 3L; i++) {
                synchronized (lock) {
                    counter++;
                    Thread.sleep(1000);
                }
            }
            Thread.sleep(5000);

            System.out.println("Counter: " + counter);
//            System.out.println("Table ordered on " + Thread.currentThread().getName());
        } catch (Exception ignored) { }
    }

    @Override
    public void onOrderStart(SimpleTask task) {
        this.onStart = task;
    }

    @Override
    public void onOrderFinish(SimpleTask task) {
        this.onFinish = task;
    }

    @Override
    public void run() {
        if (onStart != null) onStart.doTask();
        makeOrder();
        if (onFinish != null) onFinish.doTask();
    }
}
