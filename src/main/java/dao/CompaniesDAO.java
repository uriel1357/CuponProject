package dao;

import beans.Company;
import exceptions.AlreadyExistsException;
import exceptions.NotExistsException;

import java.util.ArrayList;
import java.util.List;

public interface CompaniesDAO {

    Boolean isCompanyExists (String email, String password);

    Company addCompany(Company company);

    Company updateCompany(Company company);

    void deleteCompany(int companyID);

    ArrayList<Company> getAllCompanies();

    Company getOneCompany(int companyID);

    Company getOneCompany(String email, String password);

}
