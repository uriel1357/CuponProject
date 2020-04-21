package dao;

import beans.Category;
import beans.Coupon;

import java.util.ArrayList;

public class CuponsDBDAO implements CuponsDAO{

    @Override
    public void addCupon(Coupon coupon) {

    }

    @Override
    public void updateCoupon(Coupon coupon) {

    }

    @Override
    public void deleteCoupon(int couponID) {

    }

    @Override
    public ArrayList<Coupon> getAllCoupons() {
        return null;
    }

    @Override
    public Coupon getOneCoupon(int couponID) {
        return null;
    }

    @Override
    public void addCouponPurchace(int customerID, int couponID) {

    }

    @Override
    public void deleteCouponPurchace(int customerID, int couponID) {

    }

    @Override
    public ArrayList<Coupon> getCompanyCoupons(int companyID) {
        return null;
    }


    @Override
    public void deleteCustomerCouponPurchaces(int customerID) {

    }

    @Override
    public boolean isCouponPurchaceExists(int customerID, int couponID) {
        return false;
    }

    @Override
    public ArrayList<Coupon> getCompanyCouponsByCategory(int companyID, Category category) {
        return null;
    }

    @Override
    public ArrayList<Coupon> getCompanyCouponsByMaxPrice(int companyID, double price) {
        return null;
    }

    @Override
    public void deleteCouponPurchaces(int couponID) {

    }

    @Override
    public ArrayList<Coupon> getCustmerCoupons(int customerID) {
        return null;
    }

    @Override
    public ArrayList<Coupon> getCustomerCouponsByCategory(int companyID, Category category) {
        return null;
    }

    @Override
    public ArrayList<Coupon> getCustomerCouponsByMaxPrice(int companyID, double price) {
        return null;
    }
}
