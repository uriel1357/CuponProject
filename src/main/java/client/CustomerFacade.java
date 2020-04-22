package client;

import beans.Category;
import beans.Coupon;
import beans.Customer;
import exceptions.CouponActionllegalException;

import java.util.ArrayList;
import java.util.Date;

public class CustomerFacade extends ClientFacade{

    private int customerID;

    public CustomerFacade (){
    }

    @Override
    public boolean login(String email, String password)  {
        boolean isCustomerExists =  this.customersDAO.isCustomerExists(email, password);
        if(isCustomerExists){
            this.customerID = this.customersDAO.getOneCustomer(email).getId();
        }
        return isCustomerExists;
    }

    public void purchaceCoupon(Coupon coupon) throws CouponActionllegalException{
        if(this.cuponsDAO.isCouponPurchaceExists(this.customerID, coupon.getId())){
            throw new CouponActionllegalException("Cant purchase this coupon again");
        }
        else if(coupon.getPrice() == 0){
            throw new CouponActionllegalException("Cant purchase coupon with amount 0");
        }
        else if(coupon.getEndDate().compareTo(new Date()) == 1){
            throw new CouponActionllegalException("Cant purchase expired coupon ");
        }
        this.cuponsDAO.addCouponPurchace(this.customerID, coupon.getId());
        int couponAmount = coupon.getAmount() - 1;
        coupon.setAmount(couponAmount);
        this.cuponsDAO.updateCoupon(coupon);
    }

    public ArrayList<Coupon> getCustomerCoupons(){

        return this.cuponsDAO.getCustmerCoupons(this.customerID);
    }

    public ArrayList<Coupon> getCustomerCoupons(Category category){
        return this.cuponsDAO.getCompanyCouponsByCategory(this.customerID, category);
    }

    public ArrayList<Coupon> getCustomerCoupons(double maxPrice){
        return this.cuponsDAO.getCompanyCouponsByMaxPrice(this.customerID, maxPrice);
    }

    public Customer getCustomerDetails(){
        return this.customersDAO.getOneCustomer(this.customerID);
    }
}
