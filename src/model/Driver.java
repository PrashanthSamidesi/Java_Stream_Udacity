package model;

import java.util.Scanner;

public class Driver {
    public static void main(String[] args){
       String firstName;
       String lastName;
       Scanner sc = new Scanner(System.in);
        System.out.println("Enter your first name: ");
        firstName = sc.nextLine();

        System.out.println("Enter your last name: ");
        lastName = sc.nextLine();

       boolean isValid = false;
       Customer customer = null;

       while(!isValid){
           try{
               System.out.println("Enter your email: ");
               String email = sc.nextLine();
               customer = new Customer(firstName, lastName, email);
               isValid = true;
           }
           catch (IllegalArgumentException ex){
               System.out.println(ex.getMessage());
           }
       }
       System.out.println(customer);
    }
}
