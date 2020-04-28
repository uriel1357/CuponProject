package run;

import beans.Category;
import beans.Company;
import beans.Coupon;
import beans.Customer;
import client.AdminFacade;
import client.CompanyFacade;
import client.CustomerFacade;
import dbdao.SchemaDBDAO;
import manager.ClientType;
import manager.LoginManager;
import pool.ConnetionPool;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

public class Test {
    private static SchemaDBDAO schemaDBDAO = new SchemaDBDAO();

    public static void testAll() {

        CouponsExpirationDailyJob job = new CouponsExpirationDailyJob(300);
        try {
            Coupon expiredCoupon = new Coupon(1, 1, Category.FOOD, "title", "description", new Date(1), new Date(2), 1, 1d, "image");
            List<Coupon> expiredCouponList = new ArrayList<>();
            expiredCouponList.add(expiredCoupon);

            // admin facade actions
            AdminFacade adminFacade = (AdminFacade) LoginManager.getInstance().login("admin", "admin", ClientType.ADMINISTRATOR);
            Coupon coupon = new Coupon(1, 1, Category.ELECTRICITY, "title", "description", new Date(System.currentTimeMillis()), new Date(System.currentTimeMillis()), 1, 1d, "image");
            List<Coupon> couponList = new ArrayList<>();
            couponList.add(coupon);
            Customer customer = new Customer(1, "moshe", "cohen", "e@mail.com", "pass", couponList);
            adminFacade.addCustomer(customer);
            ArrayList<Customer> allCustomers = adminFacade.getAllCustomers();

            adminFacade.addCompany(new Company(1, "comp", "e@mail.com", "pass", couponList));
            Company company = adminFacade.getOneCompany(1);
            System.out.println("Retrieved company from DB details - " + company.toString());


            ArrayList<Company> allConmpanies = adminFacade.getAllConmpanies();
            System.out.println("All Companies from DB details - " + company.toString());

            Customer oneCustomer = adminFacade.getOneCustomer(1);
            company.setEmail("email@gmail.com");
            adminFacade.updateCompany(company);
            customer.setEmail("email@gmail.com");
            adminFacade.updateCustomer(customer);
            adminFacade.deleteCompany(1);

            //CustomerFacade actions
            CustomerFacade customerFacade = (CustomerFacade) LoginManager.getInstance().login("email@gmail.com", "pass", ClientType.CUSTOMER);
            System.out.println("customer details - " + customerFacade.getCustomerDetails());
            adminFacade.addCompany(company);
            customerFacade.purchaceCoupon(coupon);
            ArrayList<Coupon> customerMaxPriceCoupons = customerFacade.getCustomerCoupons(1d);
            System.out.println("customer by Max Price Coupons - " + customerMaxPriceCoupons);

            ArrayList<Coupon> customerCategoryCoupons = customerFacade.getCustomerCoupons(Category.ELECTRICITY);
            System.out.println("customer by Category Coupons - " + customerCategoryCoupons);

            ArrayList<Coupon> customerCoupons = customerFacade.getCustomerCoupons();
            System.out.println("customer  Coupons - " + customerCoupons);



            //CompanyFacade actions
            adminFacade.addCompany(new Company(1, "comp", "e@mail.com", "pass", couponList));
            CompanyFacade companyFacade = (CompanyFacade) LoginManager.getInstance().login("e@mail.com", "pass", ClientType.COMPANY);
            System.out.println("comapny detalis - " + companyFacade.getCompanyDetails());
            companyFacade.getCompanyCoupons(1d);
            companyFacade.getCompanyCoupons(Category.ELECTRICITY);
            companyFacade.getCompanyCoupons();
            Company company1 = companyFacade.getCompanyDetails();
            Coupon companyCoupon = new Coupon(1, company1.getId(), Category.FOOD, "title1", "description", new Date(1), new Date(2), 1, 1d, "image");

            companyFacade.addCoupon(companyCoupon);
            companyFacade.updateCoupon(companyCoupon);
            companyFacade.deleteCoupon(companyCoupon.getId());


        } catch (Exception e) {
            System.out.println(e.getMessage());

        }
        finally {
            job.stop();
            ConnetionPool.getInstance().closeConnections();

        }
    }
}
