package client;

import beans.Category;
import beans.Company;
import beans.Coupon;
import dbdao.CompaniesDBDAO;
import dbdao.CuponsDBDAO;
import exceptions.CouponActionllegalException;

import java.util.ArrayList;

public class CompanyFacade extends ClientFacade {

    private int companyID;


    public CompanyFacade() {
        this.companiesDAO = new CompaniesDBDAO();
        this.cuponsDAO = new CuponsDBDAO();
    }

    @Override
    public boolean login(String email, String password) {
        boolean isCompanyExists = this.companiesDAO.isCompanyExists(email, password);
        if (isCompanyExists) {
            this.companyID = this.companiesDAO.getOneCompany(email, password).getId();
        }
        return isCompanyExists;
    }

    public void addCoupon(Coupon coupon) throws CouponActionllegalException {
        ArrayList<Coupon> companyCoupons = this.cuponsDAO.getCompanyCoupons(coupon.getCompanyID());
        for (Coupon coupon1 : companyCoupons) {
            if (coupon1.getTitle().equals(coupon.getTitle())) {
                throw new CouponActionllegalException("Cant add coupon with the same title to the same company");
            }
        }
        this.cuponsDAO.addCupon(coupon);

    }

    public void updateCoupon(Coupon coupon) throws CouponActionllegalException {
        Coupon coupon1 = this.cuponsDAO.getOneCoupon(coupon.getId());
        if (coupon1 == null || coupon1.getCompanyID() != coupon.getCompanyID()) {
            throw new CouponActionllegalException("Cant update the id or company id of the coupon");
        }
        this.cuponsDAO.updateCoupon(coupon);
    }

    public void deleteCoupon(int couponID) {
        this.cuponsDAO.deleteCoupon(couponID);
        this.cuponsDAO.deleteCouponPurchaces(couponID);
    }

    public ArrayList<Coupon> getCompanyCoupons() {
        return this.cuponsDAO.getCompanyCoupons(this.companyID);
    }

    public ArrayList<Coupon> getCompanyCoupons(Category category) {
        return this.cuponsDAO.getCompanyCouponsByCategory(companyID, category);
    }

    public ArrayList<Coupon> getCompanyCoupons(double maxPrice) {
        return this.cuponsDAO.getCompanyCouponsByMaxPrice(companyID, maxPrice);
    }

    public Company getCompanyDetails() {
        return this.companiesDAO.getOneCompany(companyID);
    }


}
