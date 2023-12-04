package guest;

import utils.SimpleTask;

public class FoodGuest implements Guest {

    private SimpleTask onStart;
    private SimpleTask onFinish;

    @Override
    public void makeOrder() {
        try {
//            System.out.println("Ordering food on " + Thread.currentThread().getName());
            Thread.sleep(5000);

//            System.out.println("Food ordered on " + Thread.currentThread().getName());
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
