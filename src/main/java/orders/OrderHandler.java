package orders;

import guest.Guest;
import utils.SimpleTask;
import utils.ThreadTask;

public interface OrderHandler {

    void handleOrder(Guest guest);

    void onTableThreadCreated(ThreadTask task);
    void onFoodThreadCreated(ThreadTask task);
}
