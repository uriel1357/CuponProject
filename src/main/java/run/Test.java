package run;

import beans.Category;
import beans.Company;
import beans.Coupon;
import beans.Customer;
import client.AdminFacade;
import client.ClientFacade;
import client.CustomerFacade;
import manager.ClientType;
import manager.LoginManager;

import java.util.ArrayList;
import java.sql.Date;
import java.util.List;

public class Test {

    public static void  testAll(){
        try {

            CouponsExpirationDailyJob job = new CouponsExpirationDailyJob(300);
            job.run();

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



            job.stop();
        }
        catch (Exception e){

        }
    }
}
