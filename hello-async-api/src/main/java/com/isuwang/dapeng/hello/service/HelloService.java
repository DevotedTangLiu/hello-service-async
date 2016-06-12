
package com.isuwang.dapeng.hello.service;


import com.isuwang.dapeng.core.Processor;
import com.isuwang.dapeng.core.Service;

import java.util.concurrent.Future;

/**
 * Hello Service
 **/
@Service(version = "1.0.0")
@Processor(className = "com.isuwang.dapeng.hello.HelloServiceCodec$Processor")
public interface HelloService {

    /**
     * @SoaAsyncFunction
     **/

    Future<String> sayHello(String name) throws com.isuwang.dapeng.core.SoaException;
}
        