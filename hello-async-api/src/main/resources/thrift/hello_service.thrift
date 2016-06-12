namespace java com.isuwang.dapeng.hello.service

/**
* Hello Service
**/
service HelloService {

    /**
    * @SoaAsyncFunction
    **/
    string sayHello(1:string name)
}