package com.springcloud.service;

import cn.hutool.core.util.IdUtil;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.concurrent.TimeUnit;

@Service
public class PaymentService {
    public String PaymentInfo_OK(Integer id){
        return"线程池： "+ Thread.currentThread().getName()+" paymentInfo_ok,id"+id+"\t"+"O(∩_∩)O";
    }

    @HystrixCommand(fallbackMethod = "paymentInfo_TimeoutHandler",commandProperties = {
            @HystrixProperty(name="execution.isolation.thread.timeoutInMilliseconds",value = "3000")})
    public String PaymentInfo_Timeout(Integer id){
        int time=5;
        try {
            TimeUnit.SECONDS.sleep(time);
        } catch (InterruptedException e) {
            System.out.println("模拟超时2秒");
        }
        return"线程池： "+ Thread.currentThread().getName()+" paymentInfo_Timeout,id"+id+"\t"+"O(∩_∩)O";
    }
    public String paymentInfo_TimeoutHandler(Integer id){
        return "线程池:  "+Thread.currentThread().getName()+" PaymentInfo_TimeoutHandler,id"+id+"\t"+"/(ㄒoㄒ)/~~";
    }

    //服务熔断
    @HystrixCommand(fallbackMethod = "paymentCircuitBreaker_fallback",commandProperties = {
            //是否开启断路器
            @HystrixProperty(name = "circuitBreaker.enabled",value = "true"),
            //请求次数
            @HystrixProperty(name = "circuitBreaker.requestVolumeThreshold",value = "10"),
            //时间窗口器
            @HystrixProperty(name = "circuitBreaker.sleepWindowInMilliseconds",value = "10000"),
            //失败率达到多少后跳闸
            @HystrixProperty(name = "circuitBreaker.errorThresholdPercentage",value="60")
    })
    public String paymentCircuitBreaker(@PathVariable("id")Integer id){
        if(id<0){
            throw new RuntimeException("id 为负数");
        }
        String serialNumber= IdUtil.simpleUUID();
        return Thread.currentThread().getName()+"\t"+"调用成功，流水号"+serialNumber;
    }
    public String paymentCircuitBreaker_fallback(@PathVariable("id") Integer id){
        return  "id 不能为负数"+id;
    }

}
