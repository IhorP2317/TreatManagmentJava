import guest.Guest;
import orders.Waiters;
import java.util.List;

public class Restaurant {

    public int freeTables;
    public Waiters waiters;

    public List<Thread> tableThreads;
    public List<Thread> foodThreads;

    public Restaurant(int maxFreeTables, int totalWaiters, List<Thread> tableThreadsRef, List<Thread> foodThreadsRef) {
        freeTables = maxFreeTables;

        waiters = new Waiters(totalWaiters);
        waiters.onTableThreadCreated(thread -> {
            if (freeTables > 0) {
                tableThreads.add(thread);
                synchronized (this) {
                    freeTables--;
                }
            }
        });
        waiters.onFoodThreadCreated(thread -> {
            foodThreads.add(thread);
        });

        tableThreads = tableThreadsRef;
        foodThreads = foodThreadsRef;
    }

    public void reserveTable(Guest guest) {
        guest.onOrderStart(() -> {
//            System.out.println(freeTables);

            synchronized(this) {
                if (!tableThreads.contains(Thread.currentThread())) tableThreads.add(Thread.currentThread());
                foodThreads.remove(Thread.currentThread());
//                System.out.println("(S) Free tables: " + freeTables);
            }
        });

        guest.onOrderFinish(() -> {
//            System.out.println(freeTables);

            synchronized(this) {
//                tableThreads.remove(Thread.currentThread());
                freeTables++;
//                System.out.println("(F) Free tables: " + freeTables);
            }
        });

        new Thread(() -> {
            while (freeTables < 1) {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException ignored) { }
            }
            waiters.handleOrder(guest);
        }).start();
    }

    public void orderFood(Guest guest) {
        guest.onOrderStart(() -> {
            synchronized(this) {
                if (!foodThreads.contains(Thread.currentThread())) foodThreads.add(Thread.currentThread());
                tableThreads.remove(Thread.currentThread());
            }
        });

        guest.onOrderFinish(() -> {
            synchronized(this) {
//                foodThreads.remove(Thread.currentThread());
            }
        });

        new Thread(() -> {
            waiters.handleOrder(guest);
        }).start();
    }

    public void killAllThreads() {
        waiters.killExecutor();

        waiters = new Waiters(waiters.amount);
        waiters.onTableThreadCreated(thread -> tableThreads.add(thread));
        waiters.onFoodThreadCreated(thread -> foodThreads.add(thread));
    }
}
