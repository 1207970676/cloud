package com.springcloud.controller;

import com.netflix.hystrix.contrib.javanica.annotation.DefaultProperties;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.springcloud.service.PaymentHystrixService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@Slf4j
@DefaultProperties(defaultFallback = "payment_Global_FallbackMethod")
public class OrderHystrixController {
    @Resource
    private PaymentHystrixService service;

    @GetMapping("/consumer/payment/hystrix/ok/{id}")
    String paymentInfo_OK(@PathVariable("id") Integer id){
        return  service.paymentInfo_OK(id);
    }

    @GetMapping("/consumer/payment/hystrix/timeout/{id}")
//    @HystrixCommand(fallbackMethod = "payoutTimeoutFallbackMethod",commandProperties = {
//            @HystrixProperty(name="execution.isolation.thread.timeoutInMilliseconds",value = "1500")
//    })
    @HystrixCommand
    String paymentInfo_TimeOut(@PathVariable("id") Integer id){
        int age=10/0;
        return  service.paymentInfo_TimeOut(id);
    }
    public  String payoutTimeoutFallbackMethod(Integer id){
        return  "我是消费者80，对方支付系统繁忙，/(ㄒoㄒ)/~~";
    }
    //全局fallback
    public  String payment_Global_FallbackMethod(){
        return  "global异常处理 请稍后再试 /(ㄒoㄒ)/~~";
    }

}
