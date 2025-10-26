
import api.AdminResource;
import model.Customer;
import model.IRoom;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Scanner;

public class AdminMenu {

    private static final AdminResource adminResource = AdminResource.getInstance();
    private static final Scanner sc = new Scanner(System.in);

    public static void displayAdminMenu() {
        while (true) {
            System.out.println("\nAdmin Menu");
            System.out.println("-------------------------------------------");
            System.out.println("1. See all Customers");
            System.out.println("2. See all Rooms");
            System.out.println("3. See all Reservations");
            System.out.println("4. Add a Room");
            System.out.println("5. Back to Main Menu");
            System.out.println("-------------------------------------------");
            System.out.print("Enter your option: ");

            try{

                int option = Integer.parseInt(sc.nextLine());

                if(option > 0 && option < 6){

                    switch (option) {
                        case 1 -> seeAllCustomers();
                        case 2 -> seeAllRooms();
                        case 3 -> adminResource.displayAllReservations();
                        case 4 -> addRoom();
                        case 5 -> {
                            return; // go back to Main Menu
                        }
                        default -> System.out.println("Invalid choice. Please try again.");
                    }

                }
                else{
                    throw new IllegalArgumentException("Enter the correct options given above.");
                }

            }
            catch(IllegalArgumentException ex){
                System.out.println(ex.getLocalizedMessage());
            }



        }
    }

    private static void seeAllCustomers() {
        Collection<Customer> customers = adminResource.getAllCustomers();
        if (customers.isEmpty()) {
            System.out.println("No customers found.");
        } else {
            customers.forEach(System.out::println);
        }
    }

    private static void seeAllRooms() {
        Collection<IRoom> rooms = adminResource.getAllRooms();
        if (rooms.isEmpty()) {
            System.out.println("No rooms added yet.");
        } else {
            rooms.forEach(System.out::println);
        }
    }

    private static void addRoom() {
        List<IRoom> newRooms = new ArrayList<>();

        while (true) {
            System.out.print("Enter room number: ");
            String roomNumber = sc.nextLine();

            System.out.print("Enter room price: ");
            double price = sc.nextDouble();

            System.out.print("Enter room type (SINGLE/DOUBLE): ");
            String typeInput = sc.next().toUpperCase();
            sc.nextLine(); // clear buffer

            model.RoomType roomType = model.RoomType.valueOf(typeInput);

            IRoom room = new model.Room(roomNumber, price, roomType);
            newRooms.add(room);

            System.out.print("Add another room? (y/n): ");
            String another = sc.nextLine().toLowerCase();
            if (!another.equals("y")) break;
        }

        adminResource.addRoom(newRooms);
        System.out.println("Rooms added successfully!");
    }
}
