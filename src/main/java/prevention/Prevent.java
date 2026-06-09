package prevention;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Prevent {

    public static class Booking {
        public String carPlate;
        public LocalDate from;
        public LocalDate to;

        public Booking(String carPlate, LocalDate from, LocalDate to) {
            this.carPlate = carPlate;
            this.from = from;
            this.to = to;
        }
    }

    public static List<Booking> bookings = new ArrayList<>();

    public static boolean hasConflict(String plate, LocalDate newFrom, LocalDate newTo) {
        for (Booking b : bookings) {
            if (b.carPlate.equals(plate)) {
                boolean overlap = !(newTo.isBefore(b.from) || newFrom.isAfter(b.to));
                if (overlap) return true;
            }
        }
        return false;
    }

    public static void addBooking(String plate, LocalDate from, LocalDate to) {
        bookings.add(new Booking(plate, from, to));
    }
}