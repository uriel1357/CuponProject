package dbdao;

import beans.Category;
import beans.Coupon;
import dao.CuponsDAO;
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
                ("INSERT INTO  coupons.COUPON (id,company_id,category_id,title,description,start_date,end_date,amount, price, image ) " +
                        "VALUES (?,?,?,?,?,?,?,?,?,?)")) {
            preparedStatement.setInt(1, coupon.getId());
            preparedStatement.setInt(2, coupon.getCompanyID());
            preparedStatement.setInt(3, coupon.getCategory().ordinal());
            preparedStatement.setString(4, coupon.getTitle());
            preparedStatement.setString(5, coupon.getDescription());
            preparedStatement.setDate(6, coupon.getStartDate());
            preparedStatement.setDate(7, coupon.getEndDate());
            preparedStatement.setInt(8, coupon.getAmount());
            preparedStatement.setDouble(9, coupon.getPrice());
            preparedStatement.setString(10, coupon.getImage());

            preparedStatement.execute();

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            this.connetionPool.returnConnection(connection);
        }
    }

    @Override
    public void updateCoupon(Coupon coupon) {
        ConnetionPool pool = connetionPool.getInstance();

        Connection connection = pool.getConnection();

        try (PreparedStatement preparedStatement = connection.prepareStatement
                ("UPDATE   coupons.COUPON set category_id =? , title = ?,description = ? , start_date= ?, end_date= ?, amount=?, price=?, image=? ")) {
            preparedStatement.setInt(1, coupon.getCompanyID());
            preparedStatement.setString(2, coupon.getTitle());
            preparedStatement.setString(3, coupon.getDescription());
            preparedStatement.setDate(4, coupon.getStartDate());
            preparedStatement.setDate(5, coupon.getEndDate());
            preparedStatement.setInt(6, coupon.getAmount());
            preparedStatement.setDouble(7, coupon.getPrice());
            preparedStatement.setString(8, coupon.getImage());
            preparedStatement.execute();

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            this.connetionPool.returnConnection(connection);
        }
    }

    @Override
    public void deleteCoupon(int couponID) {
        ConnetionPool pool = connetionPool.getInstance();

        Connection connection = pool.getConnection();

        try (PreparedStatement preparedStatement = connection.prepareStatement
                ("DELETE FROM  coupons.COUPON where id = ?")) {
            preparedStatement.setInt(1, couponID);

            preparedStatement.execute();

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            this.connetionPool.returnConnection(connection);
        }
    }


    @Override
    public ArrayList<Coupon> getAllCoupons() {
        ConnetionPool pool = connetionPool.getInstance();

        Connection connection = pool.getConnection();
        ArrayList<Coupon> coupons = new ArrayList<>();

        try (PreparedStatement preparedStatement = connection.prepareStatement
                ("SELECT coupon.* from  coupons.COUPON coupon ")) {


            ResultSet resultSet = preparedStatement.executeQuery();

            buildCoupon(coupons, resultSet);

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            this.connetionPool.returnConnection(connection);
        }
        return coupons;
    }

    @Override
    public Coupon getOneCoupon(int couponID) {
        ConnetionPool pool = connetionPool.getInstance();

        Connection connection = pool.getConnection();

        Coupon coupon = null;
        try (PreparedStatement preparedStatement = connection.prepareStatement
                ("SELECT * from  coupons.COUPON where id = ?")) {
            preparedStatement.setInt(1, couponID);

            ResultSet resultSet = preparedStatement.executeQuery();


            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                int company_id = resultSet.getInt("company_id");
                int category_id = resultSet.getInt("category_id");
                String title = resultSet.getString("title");
                String description = resultSet.getString("description");
                Date startDate = resultSet.getDate("start_date");
                Date endDate = resultSet.getDate("end_date");
                int amount = resultSet.getInt("amount");
                double price = resultSet.getDouble("price");
                String image = resultSet.getString("image");
                coupon = new Coupon(id, company_id, Category.values()[category_id], title, description, startDate, endDate, amount, price, image);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            this.connetionPool.returnConnection(connection);
        }
        return coupon;
    }

    @Override
    public void addCouponPurchace(int customerID, int couponID) {
        ConnetionPool pool = connetionPool.getInstance();

        Connection connection = pool.getConnection();

        try (PreparedStatement preparedStatement = connection.prepareStatement
                ("INSERT INTO  coupons.CUSTOMERS_VS_CUPONS (customer_id,cupon_id ) " +
                        "VALUES (?,?)")) {
            preparedStatement.setInt(1, customerID);
            preparedStatement.setInt(2, couponID);

            preparedStatement.execute();

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            this.connetionPool.returnConnection(connection);
        }
    }

    @Override
    public void deleteCouponPurchace(int customerID, int couponID) {
        ConnetionPool pool = connetionPool.getInstance();

        Connection connection = pool.getConnection();

        try (PreparedStatement preparedStatement = connection.prepareStatement
                ("DELETE FROM  coupons.CUSTOMERS_VS_CUPONS where customer_id = ? and cupon_id = ? ")) {
            preparedStatement.setInt(1, customerID);
            preparedStatement.setInt(2, couponID);


            preparedStatement.execute();

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            this.connetionPool.returnConnection(connection);
        }
    }

    @Override
    public ArrayList<Coupon> getCompanyCoupons(int companyID) {
        ConnetionPool pool = connetionPool.getInstance();

        Connection connection = pool.getConnection();
        ArrayList<Coupon> companyCoupons = new ArrayList<>();

        try (PreparedStatement preparedStatement = connection.prepareStatement
                ("SELECT * from  coupons.COUPON where company_id = ?")) {
            preparedStatement.setInt(1, companyID);

            ResultSet resultSet = preparedStatement.executeQuery();
            Coupon coupon = null;

            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                int category_id = resultSet.getInt("category_id");
                String title = resultSet.getString("title");
                String description = resultSet.getString("description");
                Date startDate = resultSet.getDate("start_date");
                Date endDate = resultSet.getDate("end_date");
                int amount = resultSet.getInt("amount");
                double price = resultSet.getDouble("price");
                String image = resultSet.getString("image");
                Coupon coupon1 = new Coupon(id, companyID, Category.values()[category_id], title, description, startDate, endDate, amount, price, image);
                companyCoupons.add(coupon1);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            this.connetionPool.returnConnection(connection);
        }
        return companyCoupons;
    }


    @Override
    public void deleteCustomerCouponPurchaces(int customerID) {
        ConnetionPool pool = connetionPool.getInstance();

        Connection connection = pool.getConnection();

        try (PreparedStatement preparedStatement = connection.prepareStatement
                ("DELETE FROM  coupon.CUSTOMERS_VS_CUPONS where customer_id = ?")) {
            preparedStatement.setInt(1, customerID);

            preparedStatement.execute();

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            this.connetionPool.returnConnection(connection);
        }
    }

    @Override
    public boolean isCouponPurchaceExists(int customerID, int couponID) {
        boolean isCouponPurchaceExists = false;
        ConnetionPool pool = connetionPool.getInstance();

        Connection connection = pool.getConnection();

        try (PreparedStatement preparedStatement = connection.prepareStatement("select count(*) from coupons.CUSTOMERS_VS_CUPONS where customer_id = ? and cupon_id = ?")) {
            preparedStatement.setInt(1, customerID);
            preparedStatement.setInt(2, couponID);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                int id = resultSet.getInt(1);
                isCouponPurchaceExists = id > 0;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            this.connetionPool.returnConnection(connection);
        }
        return isCouponPurchaceExists;
    }

    @Override
    public ArrayList<Coupon> getCompanyCouponsByCategory(int companyID, Category category) {
        ConnetionPool pool = connetionPool.getInstance();

        Connection connection = pool.getConnection();
        ArrayList<Coupon> companyCoupons = new ArrayList<>();

        try (PreparedStatement preparedStatement = connection.prepareStatement
                ("SELECT coupon.* from  coupons.COUPON coupon  where coupon.company_id = ? and category_id = ?")) {
            preparedStatement.setInt(1, companyID);
            preparedStatement.setInt(2, category.ordinal());


            ResultSet resultSet = preparedStatement.executeQuery();

            buildCoupon(companyCoupons, resultSet);

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            this.connetionPool.returnConnection(connection);
        }
        return companyCoupons;
    }

    @Override
    public ArrayList<Coupon> getCompanyCouponsByMaxPrice(int companyID, double price) {
        ConnetionPool pool = connetionPool.getInstance();

        Connection connection = pool.getConnection();
        ArrayList<Coupon> companyCoupons = new ArrayList<>();

        try (PreparedStatement preparedStatement = connection.prepareStatement
                ("SELECT coupon.* from  coupons.COUPON coupon  where coupon.company_id = ? and price <= ?")) {
            preparedStatement.setInt(1, companyID);
            preparedStatement.setDouble(2, price);


            ResultSet resultSet = preparedStatement.executeQuery();

            buildCoupon(companyCoupons, resultSet);

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            this.connetionPool.returnConnection(connection);
        }
        return companyCoupons;
    }

    @Override
    public void deleteCouponPurchaces(int couponID) {
        ConnetionPool pool = connetionPool.getInstance();

        Connection connection = pool.getConnection();

        try (PreparedStatement preparedStatement = connection.prepareStatement
                ("DELETE FROM  coupons.CUSTOMERS_VS_CUPONS where cupon_id = ?")) {
            preparedStatement.setInt(1, couponID);

            preparedStatement.execute();

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            this.connetionPool.returnConnection(connection);
        }
    }

    @Override
    public ArrayList<Coupon> getCustmerCoupons(int customerID) {
        ConnetionPool pool = connetionPool.getInstance();

        Connection connection = pool.getConnection();
        ArrayList<Coupon> custmerCoupons = new ArrayList<>();

        try (PreparedStatement preparedStatement = connection.prepareStatement
                ("SELECT * from  coupons.COUPON coupon join CUSTOMERS_VS_CUPONS cvc on coupon.id = cvc.customer_id where cvc.customer_id = ?")) {
            preparedStatement.setInt(1, customerID);

            ResultSet resultSet = preparedStatement.executeQuery();
            Coupon coupon = null;

            buildCoupon(custmerCoupons, resultSet);

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            this.connetionPool.returnConnection(connection);
        }
        return custmerCoupons;
    }

    private void buildCoupon(ArrayList<Coupon> custmerCoupons, ResultSet resultSet) throws SQLException {
        while (resultSet.next()) {
            int id = resultSet.getInt("id");
            int company_id = resultSet.getInt("company_id");
            int amount = resultSet.getInt("amount");
            double price = resultSet.getDouble("amount");


            int category_id = resultSet.getInt("category_id");
            String title = resultSet.getString("title");
            String image = resultSet.getString("image");
            String description = resultSet.getString("description");
            Date start_date = resultSet.getDate("start_date");
            Date end_date = resultSet.getDate("end_date");

            Coupon coupon1 = new Coupon(id, company_id, Category.values()[category_id], title, description, start_date, end_date, amount, price, image);
            custmerCoupons.add(coupon1);
        }
    }

    @Override
    public ArrayList<Coupon> getCustomerCouponsByCategory(int customerID, Category category) {
        ConnetionPool pool = connetionPool.getInstance();

        Connection connection = pool.getConnection();
        ArrayList<Coupon> custmerCoupons = new ArrayList<>();

        try (PreparedStatement preparedStatement = connection.prepareStatement
                ("SELECT coupon.* from  coupons.COUPON coupon join CUSTOMERS_VS_CUPONS cvc on coupon.id = cvc.customer_id where cvc.customer_id = ? and coupon.category_id = ?")) {
            preparedStatement.setInt(1, customerID);
            preparedStatement.setInt(2, category.ordinal());


            ResultSet resultSet = preparedStatement.executeQuery();
            Coupon coupon = null;

            buildCoupon(custmerCoupons, resultSet);

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            this.connetionPool.returnConnection(connection);
        }
        return custmerCoupons;
    }

    @Override
    public ArrayList<Coupon> getCustomerCouponsByMaxPrice(int customerID, double price) {
        ConnetionPool pool = connetionPool.getInstance();

        Connection connection = pool.getConnection();
        ArrayList<Coupon> custmerCoupons = new ArrayList<>();

        try (PreparedStatement preparedStatement = connection.prepareStatement
                ("SELECT coupon.* from  coupons.COUPON coupon join CUSTOMERS_VS_CUPONS cvc on coupon.id = cvc.customer_id where cvc.customer_id = ? and coupon.price < ?")) {
            preparedStatement.setInt(1, customerID);
            preparedStatement.setDouble(2, price);


            ResultSet resultSet = preparedStatement.executeQuery();
            Coupon coupon = null;

            buildCoupon(custmerCoupons, resultSet);

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            this.connetionPool.returnConnection(connection);
        }
        return custmerCoupons;
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
        } finally {
            this.connetionPool.returnConnection(connection);
        }
        return expiredCoupons;
    }
}
