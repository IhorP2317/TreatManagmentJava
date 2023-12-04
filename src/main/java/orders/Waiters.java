package orders;

import guest.Guest;
import guest.TableGuest;
import org.jetbrains.annotations.NotNull;
import utils.SimpleTask;
import utils.ThreadTask;

import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;

public class Waiters implements OrderHandler {

    public int amount;

    ExecutorService threadExecutor;
    ThreadTask tableCreationTask;
    ThreadTask foodCreationTask;
    Guest handledGuest;

    public Waiters(int amount) {
        this.amount = amount;
        this.threadExecutor = Executors.newFixedThreadPool(amount, runnable -> {
            Random random = new Random(System.currentTimeMillis());
            Thread thread = new Thread(runnable);

            thread.setPriority(random.nextInt(10) + 1);
            if (handledGuest instanceof TableGuest) {
                if (tableCreationTask != null) tableCreationTask.doTask(thread);
            } else {
                if (foodCreationTask != null) foodCreationTask.doTask(thread);
            }
            handledGuest = null;

            try {
                Thread.sleep(1000);
            } catch (InterruptedException ignored) { }

            return thread;
        });
    }

    @Override
    public void handleOrder(Guest guest) {
        handledGuest = guest;
        threadExecutor.execute(guest);
    }

    @Override
    public void onTableThreadCreated(ThreadTask task) {
        tableCreationTask = task;
    }

    @Override
    public void onFoodThreadCreated(ThreadTask task) {
        foodCreationTask = task;
    }

    public void killExecutor() {
        threadExecutor.shutdown();
    }
}
