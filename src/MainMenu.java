import api.HotelResource;
import model.Customer;
import model.IRoom;
import model.Reservation;
import service.ReservationService;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

import static java.lang.System.exit;

public class MainMenu {

    private static final HotelResource hotelResource = HotelResource.getInstance();
    private static final Scanner sc = new Scanner(System.in);

    public static void main(String[] args) {
        displayMainMenu();
    }

    public static void displayMainMenu() {
        while (true) {
            System.out.println("\nWelcome to the Hotel Reservation Application");
            System.out.println("------------------------------------------------------");
            System.out.println("1. Find and reserve a room");
            System.out.println("2. See my reservations");
            System.out.println("3. Create an account");
            System.out.println("4. Admin");
            System.out.println("5. Exit");
            System.out.println("------------------------------------------------------");

            int option = 0;
            boolean validInput = false;

            while (!validInput) {
                System.out.print("Please select a number from the above options: ");
                String input = sc.nextLine();

                try {
                    option = Integer.parseInt(input);
                    if (option >= 1 && option <= 5) {
                        validInput = true;
                    } else {
                        System.out.println("Invalid option. Please enter a number between 1 and 5.");
                    }
                } catch (NumberFormatException e) {
                    System.out.println("Please enter a valid numeric option (1–5).");
                }
            }

            switch (option) {
                case 1 -> findAndReserveRoom();
                case 2 -> seeMyReservations();
                case 3 -> createAccount();
                case 4 -> AdminMenu.displayAdminMenu();
                case 5 -> exit(0);
            }
        }
    }

    private static void findAndReserveRoom() {
        System.out.print("Enter your email: ");
        String email = sc.nextLine();

        Customer customer = hotelResource.getCustomer(email);
        if (customer == null) {
            System.out.println("No account found. Please create one first.");
            return;
        }

        Date checkIn = null;
        Date checkOut = null;
        boolean isValid = false;

        while (!isValid) {
            try {
                System.out.print("Enter check-in date (MM/dd/yyyy): ");
                checkIn = new SimpleDateFormat("MM/dd/yyyy").parse(sc.nextLine());

                System.out.print("Enter check-out date (MM/dd/yyyy): ");
                checkOut = new SimpleDateFormat("MM/dd/yyyy").parse(sc.nextLine());

                Collection<IRoom> availableRooms = hotelResource.findARoom(checkIn, checkOut);

                if (availableRooms.isEmpty()) {
                    System.out.println("No rooms available for those dates.");

                    Collection<IRoom> recommendedRooms = ReservationService.getInstance().aRecommendRooms(checkIn, checkOut);
                    if (recommendedRooms.isEmpty()) {
                        System.out.println("No rooms available for alternate dates either.");
                        return;
                    }

                    System.out.println("Recommended rooms for alternate dates (+7 days):");
                    List<IRoom> recommendedList = new ArrayList<>(recommendedRooms);
                    for (int i = 0; i < recommendedList.size(); i++) {
                        System.out.println((i + 1) + ". " + recommendedList.get(i));
                    }

                    System.out.print("Would you like to book one of these recommended rooms? (Y/N): ");
                    String option = sc.nextLine().trim();

                    if (option.equalsIgnoreCase("Y")) {
                        System.out.print("Enter the number of the room to book: ");
                        try {
                            int roomChoice = Integer.parseInt(sc.nextLine());
                            if (roomChoice < 1 || roomChoice > recommendedList.size()) {
                                System.out.println("Invalid selection. Returning to main menu.");
                                return;
                            }

                            IRoom selectedRoom = recommendedList.get(roomChoice - 1);

                            Date altCheckIn = new Date(checkIn.getTime() + 7L * 24 * 60 * 60 * 1000);
                            Date altCheckOut = new Date(checkOut.getTime() + 7L * 24 * 60 * 60 * 1000);

                            Reservation reservation = hotelResource.bookARoom(email, selectedRoom, altCheckIn, altCheckOut);
                            System.out.println("Room booked successfully!");
                            System.out.println(reservation);
                            return;

                        } catch (NumberFormatException e) {
                            System.out.println("Invalid input. Returning to main menu.");
                            return;
                        }
                    } else {
                        System.out.println("No rooms booked from recommended dates.");
                        return;
                    }
                }

                System.out.println("Available rooms:");
                for (IRoom room : availableRooms) {
                    System.out.println(room);
                }

                System.out.print("Enter room number to reserve: ");
                String roomNumber = sc.nextLine();

                IRoom room = hotelResource.getRoom(roomNumber);
                if (room == null) {
                    System.out.println("Invalid room number. Returning to main menu.");
                    return;
                }

                Reservation reservation = hotelResource.bookARoom(email, room, checkIn, checkOut);
                System.out.println("Reservation confirmed:\n" + reservation);
                isValid = true;

            } catch (ParseException e) {
                System.out.println("Invalid date format. Please use MM/dd/yyyy.");
            }
        }
    }

    private static void seeMyReservations() {
        System.out.print("Enter your email: ");
        String email = sc.nextLine();

        Collection<Reservation> reservations = hotelResource.getCustomerReservations(email);
        if (reservations.isEmpty()) {
            System.out.println("No reservations found for this customer.");
        } else {
            reservations.forEach(System.out::println);
        }
    }

    private static void createAccount() {
        System.out.print("Enter your first name: ");
        String firstName = sc.nextLine();

        System.out.print("Enter your last name: ");
        String lastName = sc.nextLine();

        boolean isValid = false;
        String email = null;

        while (!isValid) {
            try {
                System.out.print("Enter your email: ");
                email = sc.nextLine();
                new Customer(firstName, lastName, email);
                isValid = true;
            } catch (IllegalArgumentException ex) {
                System.out.println(ex.getLocalizedMessage());
            }
        }

        hotelResource.createACustomer(email, firstName, lastName);
        System.out.println("Account created successfully for: " + firstName + " " + lastName);
    }
}
