package com.isuwang.soa.hello.test;

import com.isuwang.soa.core.SoaException;
import com.isuwang.soa.hello.HelloServiceClient;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CountDownLatch;

/**
 * Created by tangliu on 2016/6/1.
 */
public class TestClient {


    public static void main(String[] args) {
        test();
    }

    public static void test() {

        CountDownLatch latch = new CountDownLatch(2);

        HelloServiceClient client = new HelloServiceClient();

        CompletableFuture<String> normalRequest = null;
        try {
            normalRequest = (CompletableFuture<String>) client.sayHello("Tomy", 15000l);
            normalRequest.whenComplete((str, ex) -> {
                if (str != null) {
                    System.out.println(str);
                } else {
                    ex.printStackTrace();
                }
                latch.countDown();
            });
        } catch (SoaException e) {
            e.printStackTrace();
        }

        CompletableFuture<String> timeoutRequest = null;
        try {
            timeoutRequest = (CompletableFuture<String>) client.sayHello("kitty", 5000l);
            timeoutRequest.whenComplete((str, ex) -> {
                if (str != null) {
                    System.out.println(str);
                } else {
                    System.out.println(ex instanceof SoaException);
                    ex.printStackTrace();
                }
                latch.countDown();
            });
        } catch (SoaException e) {
            e.printStackTrace();
        }

        int i = 0;
        while (latch.getCount() > 0) {

            System.out.println(i++);
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        System.exit(0);
    }
}
