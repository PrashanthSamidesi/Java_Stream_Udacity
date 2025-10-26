package service;

import model.Customer;
import model.IRoom;
import model.Reservation;

import java.util.*;

public class ReservationService {

    private static ReservationService instance = new ReservationService();

    private ReservationService() {}

    public static ReservationService getInstance() {
        return instance;
    }

    private Map<String, IRoom> rooms = new HashMap<>();
    private Collection<Reservation> reservations = new ArrayList<>();

    // Add room
    public void addRoom(IRoom room) {
        if (room == null) {
            throw new IllegalArgumentException("Room cannot be null.");
        }
        rooms.put(room.getRoomNumber(), room);
    }

    // Get a room by ID
    public IRoom getARoom(String roomID) {
        return rooms.get(roomID);
    }

    // Reserve a room
    public Reservation reserveARoom(Customer customer, IRoom room, Date checkInDate, Date checkOutDate) {
        if (customer == null || room == null || checkInDate == null || checkOutDate == null) {
            throw new IllegalArgumentException("Inputs cannot be null.");
        }

        if (!rooms.containsKey(room.getRoomNumber())) {
            throw new IllegalArgumentException("Room not found: " + room.getRoomNumber());
        }


        for (Reservation existing : reservations) {
            boolean sameCustomer = existing.getCustomer().equals(customer);
            boolean sameRoom = existing.getRoom().equals(room);
            boolean overlap = datesOverlap(checkInDate, checkOutDate, existing.getCheckInDate(), existing.getCheckOutDate());

            if (sameCustomer && sameRoom && overlap) {
                System.out.println("You have already made a reservation. Duplication is not possible");
                return existing; // Don't add again
            }
        }

        if (!isRoomAvailable(room, checkInDate, checkOutDate)) {
            System.out.println("This room is already booked for the selected date range.");
            return null;
        }

        Reservation reservation = new Reservation(customer, room, checkInDate, checkOutDate);
        reservations.add(reservation);
        return reservation;
    }

    public Collection<IRoom> findRooms(Date checkInDate, Date checkOutDate) {
        if (checkInDate == null || checkOutDate == null) {
            throw new IllegalArgumentException("Dates cannot be null.");
        }

        Collection<IRoom> available = new ArrayList<>();
        for (IRoom room : rooms.values()) {
            if (isRoomAvailable(room, checkInDate, checkOutDate)) {
                available.add(room);
            }
        }
        return available;
    }

    public Collection<IRoom> aRecommendRooms(Date checkInDate, Date checkOutDate) {
        Calendar calendar = Calendar.getInstance();

        calendar.setTime(checkInDate);
        calendar.add(Calendar.DATE, 7);
        Date altCheckIn = calendar.getTime();

        calendar.setTime(checkOutDate);
        calendar.add(Calendar.DATE, 7);
        Date altCheckOut = calendar.getTime();

        Collection<IRoom> recommendedRooms = findRooms(altCheckIn, altCheckOut);

        if (!recommendedRooms.isEmpty()) {
            System.out.println("Sorry, no rooms available on the selected dates.");
            System.out.println("Recommended rooms for alternative dates:");
            System.out.println("Alternative Check-In: " + altCheckIn);
            System.out.println("Alternative Check-Out: " + altCheckOut);
        }

        return recommendedRooms;
    }

    public Collection<Reservation> getCustomersReservations(Customer customer) {
        Collection<Reservation> result = new ArrayList<>();
        if (customer == null) return result;

        for (Reservation r : reservations) {
            if (r.getCustomer().equals(customer)) {
                result.add(r);
            }
        }
        return result;
    }

    // Print all reservations
    public void printAllReservations() {
        if (reservations.isEmpty()) {
            System.out.println("There are no reservations.");
        } else {
            for (Reservation r : reservations) {
                System.out.println(r);
            }
        }
    }

    // Get all rooms
    public Collection<IRoom> getAllRooms() {
        return rooms.values();
    }

    private boolean isRoomAvailable(IRoom room, Date newCheckIn, Date newCheckOut) {
        for (Reservation existing : reservations) {
            if (existing.getRoom().getRoomNumber().equals(room.getRoomNumber())) {
                if (datesOverlap(newCheckIn, newCheckOut, existing.getCheckInDate(), existing.getCheckOutDate())) {
                    return false;
                }
            }
        }
        return true;
    }

    private boolean datesOverlap(Date start1, Date end1, Date start2, Date end2) {
        return start1.before(end2) && end1.after(start2);
    }
}
