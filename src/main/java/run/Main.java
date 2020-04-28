package run;

public class Main {

    public static void main(String[] args) {
        CouponsExpirationDailyJob job = new CouponsExpirationDailyJob(300);
        Test.testAll();
        job.stop();
    }
}