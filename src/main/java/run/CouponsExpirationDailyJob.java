package run;

import beans.Coupon;
import dao.CuponsDAO;

import java.util.ArrayList;
import java.util.Date;
import java.util.concurrent.atomic.AtomicBoolean;

public class CouponsExpirationDailyJob implements Runnable {

    private Thread worker;
    private final AtomicBoolean running = new AtomicBoolean(false);
    private int interval;

    private CuponsDAO cuponsDAO;

    private boolean quit;

    public CouponsExpirationDailyJob(int interval) {
        this.interval = interval;
        worker = new Thread(this);
        worker.start();
    }

    public void run() {
        running.set(true);
        while (running.get()) {
            try {
                ArrayList<Coupon> expiredCoupons = this.cuponsDAO.getExpiredCoupons(new Date());
                for (Coupon coupon : expiredCoupons){
                    this.cuponsDAO.deleteCouponPurchaces(coupon.getId());
                    this.cuponsDAO.deleteCoupon(coupon.getId());
                }
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
