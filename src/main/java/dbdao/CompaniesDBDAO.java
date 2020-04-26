package dbdao;


import beans.Company;
import dao.CompaniesDAO;
import pool.ConnetionPool;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;


public class CompaniesDBDAO implements CompaniesDAO {

    private ConnetionPool connetionPool;

    public CompaniesDBDAO() {
        connetionPool = ConnetionPool.getInstance();
    }


    @Override
    public Boolean isCompanyExists(String email, String password) {
        boolean isCompanyExists = false;
        ConnetionPool pool = connetionPool.getInstance();

        Connection connection = pool.getConnection();

        try (PreparedStatement preparedStatement = connection.prepareStatement("select count(*) from COMPANIES where email = ? and password = ?")) {
            preparedStatement.setString(1 , email);
            preparedStatement.setString(2 , password);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                int id = resultSet.getInt(1);
                isCompanyExists = id > 0;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return isCompanyExists;
    }

    @Override
    public Company addCompany(Company company) {
        if (company == null) {
            return null;
        }

        String sql = "INSERT INTO COMPANIES(NAME, EMAIL, PASSWORD) VALUES(?, ?, ?)";
//        Connection connection = connetionPool;
        return company;
    }

    @Override
    public Company updateCompany(Company company) {
        return null;
    }

    @Override
    public Company deleteCompany(int companyID) {
        return null;
    }

    @Override
    public ArrayList<Company> getAllCompanies() {
        return null;
    }

    @Override
    public Company getOneCompany(int companyID) {
        return null;
    }

    @Override
    public Company getOneCompany(String email, String password) {
        return null;
    }
}
