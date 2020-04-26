package client;

import beans.Company;
import beans.Coupon;
import beans.Customer;
import dao.CuponsDBDAO;
import dbdao.CompaniesDBDAO;
import dbdao.CustomersDBDAO;
import exceptions.*;

import java.util.ArrayList;

public class AdminFacade extends ClientFacade{

    public AdminFacade(){
        this.companiesDAO = new CompaniesDBDAO();
        this.cuponsDAO = new CuponsDBDAO();
        this.customersDAO = new CustomersDBDAO();

    }

    @Override
    public boolean login(String email, String password) {
        if(email != null && password !=null && email.equals("admin") && password.equals("admin")){
            return true;
        }
        return false;
    }

    public void addCompany(Company company) throws CompanyAlreadyExistsException {
        boolean isExistsComapny = this.companiesDAO.isCompanyExists(company.getEmail(), company.getPassword());
        if(isExistsComapny){
            throw new CompanyAlreadyExistsException("Company with email and password already Exists" + company.getEmail()+ ","+ company.getPassword() );
        }
        this.companiesDAO.addCompany(company);
    }

    public void updateCompany(Company company) throws CompanyNotExistsException, CompanyUpdateIllegalException{
        Company companyDB = this.companiesDAO.getOneCompany(company.getId());
        if (companyDB == null ) {
            throw new CompanyUpdateIllegalException("Must not update company code or name , id -" + company.getId());
        }
        if(!company.getName().equals(companyDB.getName())){
            throw new CompanyUpdateIllegalException("Must not update company code or name");
        }

        this.companiesDAO.updateCompany(company);
    }

    public void deleteCompany(int companyID) throws CompanyNotExistsException{
        if(this.companiesDAO.getOneCompany(companyID) == null){
            throw new CompanyNotExistsException("Company with this id not exists , id -" + companyID);
        }
        ArrayList<Coupon> companyCoupons = this.cuponsDAO.getCompanyCoupons(companyID);
        for(Coupon coupon : companyCoupons){
            this.cuponsDAO.deleteCoupon(coupon.getId());
            this.cuponsDAO.deleteCouponPurchaces(coupon.getId());
        }
        this.companiesDAO.deleteCompany(companyID);

    }

    public ArrayList<Company> getAllConmpanies(){
        return this.companiesDAO.getAllCompanies();
    }

    public Company getOneCompany(int companyID){
        Company company = this.companiesDAO.getOneCompany(companyID);
        return company;
    }

    public void addCustomer(Customer customer) throws CustomerAlreadyExistsException{
        if(this.customersDAO.getOneCustomer(customer.getEmail()) != null){
            throw new CustomerAlreadyExistsException("customer with this email already exists, email  = " + customer.getEmail());
        }
        this.customersDAO.addCustomer(customer);

    }

    public void updateCustomer(Customer customer) throws CustomerUpdateIllegalException{
        Customer customer1 = this.customersDAO.getOneCustomer(customer.getEmail());
        if(customer.getId() != customer1.getId()){
            throw new CustomerUpdateIllegalException("Illegal to update customer ID");
        }
        this.customersDAO.updateCustomer(customer);
    }

    public void deleteCustomer(int customerID){
        this.customersDAO.deleteCustomer(customerID);
        this.cuponsDAO.deleteCustomerCouponPurchaces(customerID);
    }

    public ArrayList<Customer> getAllCustomers(){
        ArrayList<Customer> allCustomers = this.customersDAO.getAllCustomers();
        return allCustomers;
    }

    public Customer getOneCustomer(int customerID){
        return this.customersDAO.getOneCustomer(customerID);
    }
}
