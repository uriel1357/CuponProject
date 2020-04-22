package dbdao;


import beans.Company;
import dao.CompaniesDAO;
import exceptions.AlreadyExistsException;
import exceptions.NotExistsException;
import pool.ConnetionPool;

import java.util.ArrayList;
import java.util.List;


public class CompaniesDBDAO implements CompaniesDAO {

    private ConnetionPool connetionPool;

    public CompaniesDBDAO(){
        connetionPool=ConnetionPool.getInstance();
    }


    @Override
    public Boolean isCompanyExists(String email, String password) {
        return null;
    }

    @Override
    public Company addCompany(Company company){
        if(company==null){
            return null;
        }

        String sql = "INSERT INTO COMPANIES(NAME, EMAIL, PASSWORD) VALUES(?, ?, ?)";
//        Connection connection = connetionPool;
        return  company;
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
    public ArrayList<Company>    getAllCompanies() {
        return null;
    }

    @Override
    public Company getOneCompany(int companyID)  {
        return null;
    }

    @Override
    public Company getOneCompany(String email, String password) {
        return null;
    }
}
