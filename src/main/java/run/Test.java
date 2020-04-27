package run;

import beans.Category;
import beans.Company;
import beans.Coupon;
import beans.Customer;
import client.AdminFacade;
import client.ClientFacade;
import client.CustomerFacade;
import dao.SchemaDAO;
import dbdao.SchemaDBDAO;
import manager.ClientType;
import manager.LoginManager;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.Reader;
import java.util.ArrayList;
import java.sql.Date;
import java.util.List;

public class Test {
    private static SchemaDBDAO schemaDBDAO = new SchemaDBDAO();

    public static void  testAll(){

//        schemaDBDAO.rebuild();
        CouponsExpirationDailyJob job = new CouponsExpirationDailyJob(300);

        try {

            Coupon expiredCoupon = new Coupon(1, 1, Category.FOOD, "title", "description", new Date(1), new Date(2),1,1d,"image");
            List<Coupon> expiredCouponList = new ArrayList<>();
            expiredCouponList.add(expiredCoupon);

//            job.run();

            // admin
            AdminFacade adminFacade = (AdminFacade)LoginManager.getInstance().login("admin", "admin", ClientType.ADMINISTRATOR);
            Coupon coupon = new Coupon(1, 1, Category.ELECTRICITY, "title", "description", new Date(System.currentTimeMillis()), new Date(System.currentTimeMillis()),1,1d,"image");
            List<Coupon> couponList = new ArrayList<>();
            couponList.add(coupon);
            Customer customer = new Customer(1, "moshe", "cohen","e@mail.com", "pass", couponList);
            adminFacade.addCustomer(customer);
            ArrayList<Customer> allCustomers = adminFacade.getAllCustomers();

            adminFacade.addCompany(new Company(1,"comp", "e@mail.com", "pass",couponList));
            Company company = adminFacade.getOneCompany(1);

            ArrayList<Company> allConmpanies = adminFacade.getAllConmpanies();
            Customer oneCustomer = adminFacade.getOneCustomer(1);
            company.setEmail("email@gmail.com");
            adminFacade.updateCompany(company);
            customer.setEmail("email@gmail.com");
            adminFacade.updateCustomer(customer);
            adminFacade.deleteCompany(1);

            //
            CustomerFacade customerFacade = (CustomerFacade)LoginManager.getInstance().login("email@gmail.com", "pass", ClientType.CUSTOMER);
            customerFacade.getCustomerDetails();
            ArrayList<Coupon> customerMaxPriceCoupons = customerFacade.getCustomerCoupons(1d);
            ArrayList<Coupon> customerCategoryCoupons = customerFacade.getCustomerCoupons(Category.VACATION);
            ArrayList<Coupon> customerCoupons = customerFacade.getCustomerCoupons();



            job.stop();
        }
        catch (Exception e){
            job.stop();

        }
    }
}
