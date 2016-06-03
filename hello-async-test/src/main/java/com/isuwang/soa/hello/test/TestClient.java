package com.isuwang.soa.hello.test;

import com.isuwang.soa.core.SoaException;
import com.isuwang.soa.hello.HelloServiceClient;

import java.util.concurrent.CompletableFuture;

/**
 * Created by tangliu on 2016/6/1.
 */
public class TestClient {


    public static void main(String[] args) {
        test();
    }

    public static void test() {

        HelloServiceClient client = new HelloServiceClient();

        CompletableFuture<String> result = null;
        try {
            result = (CompletableFuture<String>) client.sayHello("kitty");
            result.whenComplete((str, ex) -> {

                if (str != null) {

                    System.out.println(str);

                } else {
                    System.out.println(ex instanceof SoaException);
                    ex.printStackTrace();
                }
                System.exit(0);
            });
        } catch (SoaException e) {
            e.printStackTrace();
        }

        int i = 0;
        while (true) {

            System.out.println(i++);
            System.out.println(result);
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
