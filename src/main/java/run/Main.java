package run;

import pool.ConnetionPool;

public class Main {

    public static void main(String[] args) {
        ConnetionPool pool = ConnetionPool.getInstance();

        System.out.println(Thread.currentThread().getName());
    }
}
