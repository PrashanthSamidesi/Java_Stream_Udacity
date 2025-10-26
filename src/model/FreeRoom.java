package model;

public class FreeRoom extends Room{
    public FreeRoom(String roomNumber, Double price, RoomType type) {
        super(roomNumber, 0.0, type);
    }

    @Override
    public String toString(){
        return "Here is the room number: " + getRoomNumber() + ". The price of the room is: " + getRoomPrice() + " and it is a: " + getRoomType();
    }
}
