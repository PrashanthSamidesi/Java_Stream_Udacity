package model;

import java.util.Date;
import java.util.Objects;

public class Reservation {
    private final Customer customer;
    private final IRoom room;
    private final Date checkInDate;
    private final Date checkOutDate;

    public Reservation(Customer customer, IRoom room, Date checkInDate, Date checkOutDate){
        if(customer == null || room == null){
            throw new IllegalArgumentException("Customer and Room cannot be empty.\n");
        }
        if(checkOutDate.before(checkInDate)){
            throw new IllegalArgumentException("Check out date can't be before Check in date.\n");
        }
        this.customer = customer;
        this.room = room;
        this.checkInDate = checkInDate;
        this.checkOutDate = checkOutDate;
    }

    public Customer getCustomer(){
        return customer;
    }

    public IRoom getRoom(){
        return room;
    }

    public Date getCheckInDate(){
        return checkInDate;
    }

    public Date getCheckOutDate(){
        return checkOutDate;
    }

    @Override
    public String toString(){
        return "Reservation details: \n"+
                "Customer: "+customer+"\n"+
                "Room: "+room+"\n"+
                "Check In Date: "+checkInDate+"\n"+
                "Check out Date: "+checkOutDate;
    }

    @Override
    public boolean equals(Object o){
        if(this == o){
            return true;
        }
        if(!(o instanceof Reservation)){
            return false;
        }
        Reservation other = (Reservation) o;
        return customer.equals(other.customer) &&
                room.equals(other.room) &&
                checkInDate.equals(other.checkInDate) &&
                checkOutDate.equals(other.checkOutDate);
    }

    @Override
    public int hashCode(){
        return Objects.hash(customer, room, checkInDate, checkOutDate);
    }

}
