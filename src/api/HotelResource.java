package api;

import model.Customer;
import model.IRoom;
import model.Reservation;
import service.CustomerService;
import service.ReservationService;

import java.util.*;

public class HotelResource {

    private static HotelResource instance = new HotelResource();

    private HotelResource(){};

    public static HotelResource getInstance(){
        return instance;
    }

    private CustomerService customerService = CustomerService.getInstance();

    private ReservationService reservationService = ReservationService.getInstance();

    public Customer getCustomer(String email){
        if(email == null){
            throw new IllegalArgumentException("Fields can't be empty.");
        }
        else{
            return customerService.getCustomer(email);
        }
    }

    public IRoom getRoom(String roomNumber){
        if(roomNumber == null){
            throw new IllegalArgumentException("Room number can't be empty.");
        }
        else{
            return reservationService.getARoom(roomNumber);
        }
    }

    public void createACustomer(String email, String firstName, String lastName){
        customerService.addCustomer(firstName, lastName, email);

    }

    public Reservation bookARoom(String customerEmail, IRoom room, Date checkInDate, Date checkOutDate){
        Customer customer = getCustomer(customerEmail);
        return reservationService.reserveARoom(customer, room, checkInDate, checkOutDate);
    }

    public Collection<Reservation> getCustomerReservations(String customerEmail){
        Customer customer = getCustomer(customerEmail);
        return reservationService.getCustomersReservations(customer);
    }

    public Collection<IRoom> findARoom(Date checkInDate, Date checkOutDate){
       return ReservationService.getInstance().findRooms(checkInDate, checkOutDate);
    }

}
