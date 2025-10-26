package service;

import model.Customer;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class CustomerService {

    private static CustomerService instance = new CustomerService(); // Creating a static reference.

    private CustomerService(){}; // Private constructor - No multiple instance creation.

    public static CustomerService getInstance(){ // This method is to access the instance.
        return instance;
    };

    private Map<String, Customer> customers = new HashMap<>();

    public void addCustomer(String firstName, String lastName, String email){
        Customer customer = new Customer(firstName, lastName, email);
        customers.put(email, customer);
    }

    public Customer getCustomer(String customerEmail){
        return customers.get(customerEmail);

    }

    public Collection <Customer> getAllCustomers(){
        return customers.values();
    }

}
