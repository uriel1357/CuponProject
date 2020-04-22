package dao;

import beans.Category;
import beans.Coupon;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public interface CuponsDAO {

    void addCupon(Coupon coupon);

    void updateCoupon(Coupon coupon);

    void deleteCoupon(int couponID);

    ArrayList<Coupon> getAllCoupons();

    ArrayList<Coupon> getCompanyCoupons(int companyID);

    ArrayList<Coupon> getCustmerCoupons(int customerID);

    ArrayList<Coupon> getCompanyCouponsByCategory(int companyID, Category category);

    ArrayList<Coupon> getCustomerCouponsByCategory(int customerID, Category category);

    ArrayList<Coupon> getCompanyCouponsByMaxPrice(int companyID, double price);

    ArrayList<Coupon> getCustomerCouponsByMaxPrice(int customerID, double price);

    ArrayList<Coupon> getExpiredCoupons(Date expiredDate);

    Coupon getOneCoupon(int couponID);

    boolean isCouponPurchaceExists(int customerID, int couponID);

    void addCouponPurchace(int customerID, int couponID);

    void deleteCouponPurchace(int customerID, int couponID);

    void deleteCustomerCouponPurchaces(int customerID);

    void deleteCouponPurchaces(int couponID);

}
