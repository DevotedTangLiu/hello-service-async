package com.isuwang.soa.hello;

import com.isuwang.org.apache.thrift.TException;
import com.isuwang.soa.core.SoaException;
import com.isuwang.soa.hello.HelloServiceCodec.*;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Future;

public class HelloServiceClient extends com.isuwang.soa.remoting.BaseServiceClient {

    public HelloServiceClient() {
        super("com.isuwang.soa.hello.service.HelloService", "1.0.0");
    }

    @Override
    protected boolean isSoaTransactionalProcess() {
        return false;
    }

    /**
     * @SoaAsyncFunction
     **/
    public Future<String> sayHello(String name) throws SoaException {
        initContext("sayHello");

        try {
            sayHello_args sayHello_args = new sayHello_args();
            sayHello_args.setName(name);

            CompletableFuture<sayHello_result> resultFuture = (CompletableFuture) sendBaseAsync(sayHello_args, new sayHello_result(), new SayHello_argsSerializer(), new SayHello_resultSerializer());
            CompletableFuture<String> response = new CompletableFuture<>();
            resultFuture.whenComplete((sayHello_result, ex) -> {
                if (sayHello_result != null) {
                    response.complete(sayHello_result.getSuccess());
                } else {
                    response.completeExceptionally(ex);
                }
            });
            return response;

        } catch (SoaException e) {
            throw e;
        } catch (TException e) {
            throw new SoaException(e);
        } finally {
            destoryContext();
        }
    }


    /**
     * getServiceMetadata
     **/
    public String getServiceMetadata() throws SoaException {
        initContext("getServiceMetadata");
        try {
            getServiceMetadata_args getServiceMetadata_args = new getServiceMetadata_args();
            getServiceMetadata_result response = sendBase(getServiceMetadata_args, new getServiceMetadata_result(), new GetServiceMetadata_argsSerializer(), new GetServiceMetadata_resultSerializer());
            return response.getSuccess();
        } catch (SoaException e) {
            throw e;
        } catch (TException e) {
            throw new SoaException(e);
        } finally {
            destoryContext();
        }
    }

}
      