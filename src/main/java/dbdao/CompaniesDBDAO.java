package dbdao;


import beans.Company;
import dao.CompaniesDAO;
import pool.ConnetionPool;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;


public class CompaniesDBDAO implements CompaniesDAO {

    private ConnetionPool connetionPool;
    private CuponsDBDAO cuponsDBDAO;

    public CompaniesDBDAO() {
        connetionPool = ConnetionPool.getInstance();
        this.cuponsDBDAO = new CuponsDBDAO();
    }


    @Override
    public Boolean isCompanyExists(String email, String password) {
        boolean isCompanyExists = false;
        ConnetionPool pool = connetionPool.getInstance();

        Connection connection = pool.getConnection();

        try (PreparedStatement preparedStatement = connection.prepareStatement("select count(*) from COMPANIES where email = ? and password = ?")) {
            preparedStatement.setString(1, email);
            preparedStatement.setString(2, password);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                int id = resultSet.getInt(1);
                isCompanyExists = id > 0;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            this.connetionPool.returnConnection(connection);
        }
        return isCompanyExists;
    }

    @Override
    public Company addCompany(Company company) {
        ConnetionPool pool = connetionPool.getInstance();

        Connection connection = pool.getConnection();

        try (PreparedStatement preparedStatement = connection.prepareStatement
                ("INSERT INTO  coupons.COMPANIES (id,name,email,password ) " +
                        "VALUES (?,?,?,?)")) {
            preparedStatement.setInt(1, company.getId());
            preparedStatement.setString(2, company.getName());
            preparedStatement.setString(3, company.getEmail());
            preparedStatement.setString(4, company.getPassword());

            preparedStatement.execute();

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            this.connetionPool.returnConnection(connection);
        }

        return company;
    }

    @Override
    public Company updateCompany(Company company) {
        ConnetionPool pool = connetionPool.getInstance();

        Connection connection = pool.getConnection();

        try (PreparedStatement preparedStatement = connection.prepareStatement
                ("UPDATE   coupons.COMPANIES set email =? , password = ?")) {
            preparedStatement.setString(1, company.getEmail());
            preparedStatement.setString(2, company.getPassword());

            preparedStatement.execute();

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            this.connetionPool.returnConnection(connection);
        }
        return company;
    }

    @Override
    public void deleteCompany(int companyID) {
        ConnetionPool pool = connetionPool.getInstance();

        Connection connection = pool.getConnection();

        try (PreparedStatement preparedStatement = connection.prepareStatement
                ("DELETE FROM  coupons.COMPANIES where id = ?")) {
            preparedStatement.setInt(1, companyID);

            preparedStatement.execute();

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            this.connetionPool.returnConnection(connection);
        }
    }

    @Override
    public ArrayList<Company> getAllCompanies() {
        ConnetionPool pool = connetionPool.getInstance();

        Connection connection = pool.getConnection();

        ArrayList<Company> companies = new ArrayList<>();

        try (PreparedStatement preparedStatement = connection.prepareStatement("select * from coupons.COMPANIES")) {
            ResultSet resultSet = preparedStatement.executeQuery();
            Company company = null;

            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String name = resultSet.getString("name");
                String email = resultSet.getString("email");
                String password = resultSet.getString("password");
                company = new Company(id, name, email, password, null);
                companies.add(company);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            this.connetionPool.returnConnection(connection);
        }
        return companies;
    }

    @Override
    public Company getOneCompany(int companyID) {
        ConnetionPool pool = connetionPool.getInstance();

        Connection connection = pool.getConnection();
        Company company = null;

        try (PreparedStatement preparedStatement = connection.prepareStatement("select * from coupons.COMPANIES where id =?")) {
            preparedStatement.setInt(1, companyID);

            ResultSet resultSet = preparedStatement.executeQuery();


            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String name = resultSet.getString("name");
                String email = resultSet.getString("email");
                String password = resultSet.getString("password");
                company = new Company(id, name, email, password, null);
            }
            company.setCoupons(this.cuponsDBDAO.getCompanyCoupons(companyID));

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            this.connetionPool.returnConnection(connection);
        }
        return company;

    }

    @Override
    public Company getOneCompany(String email, String password) {
        ConnetionPool pool = connetionPool.getInstance();

        Connection connection = pool.getConnection();
        Company company = null;

        try (PreparedStatement preparedStatement = connection.prepareStatement("select * from coupons.COMPANIES where email =? and password = ?")) {
            preparedStatement.setString(1, email);
            preparedStatement.setString(2, password);


            ResultSet resultSet = preparedStatement.executeQuery();


            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String name = resultSet.getString("name");
                String email1 = resultSet.getString("email");
                String password1 = resultSet.getString("password");
                company = new Company(id, name, email1, password1, null);
            }
            company.setCoupons(this.cuponsDBDAO.getCompanyCoupons(company.getId()));


        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            this.connetionPool.returnConnection(connection);
        }
        return company;
    }
}
