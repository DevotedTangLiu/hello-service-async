namespace java com.isuwang.soa.hello.service

/**
* Hello Service
**/
service HelloService {

    /**
    * @SoaAsyncFunction
    **/
    string sayHello(1:string name)
}