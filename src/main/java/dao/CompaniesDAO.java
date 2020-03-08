package dao;

import beans.Company;
import exceptions.AlreadyExistsException;
import exceptions.NotExistsException;

import java.util.List;

public interface CompaniesDAO {

    Boolean isCompanyExists (String email, String password);

    Company addCompany(Company company)throws AlreadyExistsException;

    Company updateCompany(Company company) throws NotExistsException;

    Company deleteCompany(int companyID) throws NotExistsException;

    List<Company> getAllCompanies();

    Company getOneCompany(int companyID) throws NotExistsException;


}
