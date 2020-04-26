package dao;

import beans.Category;
import beans.Coupon;
import pool.ConnetionPool;

import java.sql.*;
import java.util.ArrayList;

public class CuponsDBDAO implements CuponsDAO {

    private ConnetionPool connetionPool;

    public CuponsDBDAO() {
        connetionPool = ConnetionPool.getInstance();
    }

    @Override
    public void addCupon(Coupon coupon) {
        ConnetionPool pool = connetionPool.getInstance();

        Connection connection = pool.getConnection();

        try (PreparedStatement preparedStatement = connection.prepareStatement
                ("INSERT INTO  coupons.COUPON (id,companyID,category,title,description,startDate,endDate,amount, price, image ) " +
                        "VALUES (?,?,?,?,?,?,?,?,?,?)")) {
            preparedStatement.setInt(1, coupon.getId());
            preparedStatement.setInt(2, coupon.getCompanyID());
            preparedStatement.setString(3, coupon.getCategory().name());
            preparedStatement.setString(4, coupon.getTitle());
            preparedStatement.setString(5, coupon.getDescription());
            preparedStatement.setDate(6, coupon.getStartDate());
            preparedStatement.setDate(7, coupon.getEndDate());
            preparedStatement.setInt(8, coupon.getAmount());
            preparedStatement.setDouble(9, coupon.getPrice());
            preparedStatement.setString(10, coupon.getImage());

            ResultSet resultSet = preparedStatement.executeQuery();

        } catch (SQLException e) {
            e.printStackTrace();
        }
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

    @Override
    public ArrayList<Coupon> getExpiredCoupons(Date expiredDate) {
        ConnetionPool pool = connetionPool.getInstance();

        Connection connection = pool.getConnection();
        ArrayList<Coupon> expiredCoupons = new ArrayList<>();

        try (PreparedStatement preparedStatement = connection.prepareStatement
                ("SELECT id from  coupons.COUPON where end_date > ?")) {
            preparedStatement.setDate(1, expiredDate);

            ResultSet resultSet = preparedStatement.executeQuery();
            Coupon coupon = null;

            while (resultSet.next()) {
                int id = resultSet.getInt(1);
                Coupon coupon1 = new Coupon(id);
                expiredCoupons.add(coupon1);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return expiredCoupons;
    }
}
