package utils;

import guest.FoodGuest;
import guest.Guest;
import guest.TableGuest;

import java.util.ArrayList;
import java.util.List;

public class GuestUtil {

    public static List<Guest> getTableGuests(int amount) {
        List<Guest> guests = new ArrayList<>();
        for (int i = 0; i < amount; i++) {
            guests.add(new TableGuest());
        }
        return guests;
    }

    public static List<Guest> getFoodGuests(int amount) {
        List<Guest> guests = new ArrayList<>();
        for (int i = 0; i < amount; i++) {
            guests.add(new FoodGuest());
        }
        return guests;
    }
}
