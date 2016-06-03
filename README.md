# hello-service-async

一个如何使用isuwang-soa的异步特性的demo

## 异步特性设计稿

### 思路

使用Java1.8的CompletableFuture特性，给soa框架的前后端添加异步特性。
* 对于后端服务开发者（Porvider），要做的是构造一个包装了预期返回结构体的Future,并返回，在其他线程中完成数据的准备，并调用CompletableFuture.complete方法。
* 对于前端的方法调用者(Consumer)，发起了一个请求，并返回了一个CompletableFuture<Object>的结果，在需要使用该结果时调用Future.get()方法。
* 对于框架的层面，针对后端的异步，需要做的是维持数据通道，在Provider确定数据准备好之后，将数据取出，并返回给客户端（Consumer）;对于前端的异步，要做的是，接收到对应请求的返回结果后，将结果封装到该请求对应的Future中。

### 代码生成

#### Service
````
public interface HelloService {
    Future<String> sayHello(String name) throws com.isuwang.soa.core.SoaException;
}
````

#### Client
```
 public Future<String> sayHello(String name) throws SoaException {
       initContext("sayHello");
        try {
            sayHello_args sayHello_args = new sayHello_args();
            sayHello_args.setName(name);

            CompletableFuture<sayHello_result> resultFuture = (CompletableFuture) sendBaseAsync(sayHello_args, new sayHello_result(), new SayHello_argsSerializer(), new SayHello_resultSerializer());
            Future<String> response = resultFuture.thenApply(sayHello_result -> sayHello_result.getSuccess());

            return response;
            ...
```

#### Codec
打解包生成与同步的代码生成结果一致，在发送和接收到数据前后进行Future的封装和获取。

### 使用

#### 服务端
```
 @Override
public Future<String> sayHello(String name) throws SoaException {

    CompletableFuture<String> result = new CompletableFuture<>();

    new Thread(() -> {
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        result.complete("result");
    }).start();

    return result;
}
```
简单来说，即构造一个封装了返回结果结构体的Future，并返回这个Future,此时工作线程已经返回，而组装数据、通知完成的过程，则交给另一个线程处理，工作线程不需要在此阻塞，得到结果才返回。

#### 客户端
```
HelloServiceClient client = new HelloServiceClient();
Future<String> result = client.sayHello("LiLei");

//do other job
System.out.println(result.get());
```
* 客户端如常请求服务，并立即得到一个Future封装的结果，该结果可能还没有被写入值，但此时线程可以执行其他后续任务，直到需要使用结果时调用Future.get()方法，此时如果结果已经从服务器返回，则立即获得，否则阻塞至结果返回或超时；
* 或者给Future添加一个回调方法，complete该Future的线程（或线程池）会执行该回调方法，客户端不需要在此阻塞。



> 参考isuwang-soa使用：[isuwang-soa](https://github.com/isuwang/isuwang-soa)


