package com.isuwang.soa.hello.service;

import com.isuwang.soa.core.SoaException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Future;

/**
 * Created by tangliu on 2016/6/3.
 */
public class HelloServiceImpl implements HelloService {

    Logger logger = LoggerFactory.getLogger(HelloServiceImpl.class);

    @Override
    public Future<String> sayHello(String name) throws SoaException {


        CompletableFuture<String> result = new CompletableFuture<>();

        new Thread(() -> {
            try {
                Thread.sleep(10000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            logger.info("---------result completed");
//            result.complete("hello, " + name);
            result.completeExceptionally(new SoaException("Test", "Test Complete With Exception"));

        }).start();

        logger.info("--------- function returned.");
        return result;
    }
}