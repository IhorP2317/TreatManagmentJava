package guest;

import utils.SimpleTask;

public interface Guest extends Runnable {

    void makeOrder();

    void onOrderStart(SimpleTask task);
    void onOrderFinish(SimpleTask task);
}
