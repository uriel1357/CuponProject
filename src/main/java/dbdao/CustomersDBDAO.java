package dbdao;

import beans.Coupon;
import beans.Customer;
import dao.CustomersDAO;
import pool.ConnetionPool;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CustomersDBDAO implements CustomersDAO {

    private ConnetionPool connetionPool;

    public CustomersDBDAO() {
        connetionPool = ConnetionPool.getInstance();
    }


    @Override
    public ArrayList<Customer> getAllCustomers() {
        ConnetionPool pool = connetionPool.getInstance();

        Connection connection = pool.getConnection();

        Customer customer = null;

        ArrayList<Customer> customers = new ArrayList<>();

        try (PreparedStatement preparedStatement = connection.prepareStatement("select * from coupons.CUSTOMERS")) {
            ResultSet resultSet = preparedStatement.executeQuery();


            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String firstName = resultSet.getString("firstName");
                String lastName = resultSet.getString("lastName");
                String email = resultSet.getString("email");
                String password = resultSet.getString("password");
                customer =  new Customer(id, firstName,lastName,email,password,null);
                customers.add(customer);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return customers;
    }

    private int id;

    private String firstName;

    private String lastName;

    private String email;

    private String password;

    private List<Coupon> coupons;


    @Override
    public Customer getOneCustomer(int customerID) {
        ConnetionPool pool = connetionPool.getInstance();

        Connection connection = pool.getConnection();

        Customer customer = null;

        try (PreparedStatement preparedStatement = connection.prepareStatement("select * from coupons.CUSTOMERS where id = ?")) {
            preparedStatement.setInt(1 , customerID);
            ResultSet resultSet = preparedStatement.executeQuery();


            while (resultSet.next()) {

                int id= resultSet.getInt("id");
                String firstName= resultSet.getString("firstName");
                String lastName= resultSet.getString("lastName");
                String password = resultSet.getString("password");
                customer =  new Customer(id,firstName,lastName,email,password,null);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return customer;
    }

    @Override
    public void deleteCustomer(int customerID) {

    }

    @Override
    public void addCustomer(Customer customer) {
        ConnetionPool pool = connetionPool.getInstance();

        Connection connection = pool.getConnection();

        try (PreparedStatement preparedStatement = connection.prepareStatement
                ("INSERT INTO  coupons.CUSTOMERS (id,firstName,lastName,email,password ) " +
                        "VALUES (?,?,?,?,?)")) {
            preparedStatement.setInt(1, customer.getId());
            preparedStatement.setString(2, customer.getFirstName());
            preparedStatement.setString(3, customer.getLastName());
            preparedStatement.setString(4, customer.getEmail());
            preparedStatement.setString(5, customer.getPassword());

            preparedStatement.execute();

        } catch (SQLException e) {
            e.printStackTrace();
        }

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
