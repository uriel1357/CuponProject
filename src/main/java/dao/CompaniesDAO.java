package dao;

import beans.Company;

import java.util.ArrayList;

public interface CompaniesDAO {

    Boolean isCompanyExists(String email, String password);

    Company addCompany(Company company);

    Company updateCompany(Company company);

    void deleteCompany(int companyID);

    ArrayList<Company> getAllCompanies();

    Company getOneCompany(int companyID);

    Company getOneCompany(String email, String password);

}
