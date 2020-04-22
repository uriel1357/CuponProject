package dbdao;

import beans.Customer;
import dao.CustomersDAO;

import java.util.ArrayList;

public class CustomersDBDAO implements CustomersDAO {


    @Override
    public ArrayList<Customer> getAllCustomers() {
        return null;
    }

    @Override
    public Customer getOneCustomer(int customerID) {
        return null;
    }

    @Override
    public void deleteCustomer(int customerID) {

    }

    @Override
    public void addCustomer(Customer customer) {

    }

    @Override
    public boolean isCustomerExists(String email, String password) {
        return false;
    }

    @Override
    public void updateCustomer(Customer customer) {

    }

    @Override
    public Customer getOneCustomer(String email) {
        return null;
    }
}
