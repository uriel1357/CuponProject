package run;

import beans.Coupon;
import dao.CuponsDAO;
import dbdao.CuponsDBDAO;

import java.sql.Date;
import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicBoolean;

public class CouponsExpirationDailyJob implements Runnable {

    private final AtomicBoolean running = new AtomicBoolean(false);
    private Thread worker;
    private int interval;

    private CuponsDAO cuponsDAO;

    private boolean quit;

    public CouponsExpirationDailyJob(int interval) {
        this.cuponsDAO = new CuponsDBDAO();
        this.interval = interval;
        worker = new Thread(this);
        worker.start();


    }

    public void run() {

        running.set(true);
        while (running.get()) {
            try {
                ArrayList<Coupon> expiredCoupons = this.cuponsDAO.getExpiredCoupons(new Date(System.currentTimeMillis()));
                for (Coupon coupon : expiredCoupons) {
                    this.cuponsDAO.deleteCouponPurchaces(coupon.getId());
                    this.cuponsDAO.deleteCoupon(coupon.getId());

                }
                System.out.println("STILL GOING");
                Thread.sleep(interval);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                System.out.println(
                        "CouponsExpirationDailyJob was interrupted, time to end job");
            }
        }
    }

    public void stop() {
        running.set(false);
    }

}
