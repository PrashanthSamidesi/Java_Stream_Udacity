package model;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Customer {
    private final String firstName;
    private final String lastName;
    private final String email;
    boolean isValid = false;

    public Customer(String firstName, String lastName, String email) {
        this.firstName = firstName;
        this.lastName = lastName;

        String RegEx = "^(.+)@(.+)\\.(com|org|net|co)$";
        Pattern pattern = Pattern.compile(RegEx);
        Matcher matcher = pattern.matcher(email);

        if (matcher.matches()) {
            this.email = email;
            isValid = true;
        } else {
            throw new IllegalArgumentException("Please enter a valid email address.");
        }
    }

    public String getFirstName(){
        return firstName;
    }

    public String getLastName(){
        return lastName;
    }

    public String getEmail(){
        return email;
    }

    @Override
    public String toString() {
        return "First Name: " + firstName + " last Name: " + lastName + " email address: " + email;
    }

}
