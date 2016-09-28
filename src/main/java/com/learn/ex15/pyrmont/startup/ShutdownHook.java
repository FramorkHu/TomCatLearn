package com.learn.ex15.pyrmont.startup;

/**
 * Created by huyan on 2016/9/28.
 */
public class ShutdownHook {

    public static void main(String[] args) {

        Runtime.getRuntime().addShutdownHook(new ShutDownThread());

        while (true);
    }


    static class ShutDownThread extends Thread{

        @Override
        public void run() {
            System.out.println("exit");
        }
    }
}
