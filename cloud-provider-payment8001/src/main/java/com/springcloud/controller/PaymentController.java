package com.springcloud.controller;

import com.springcloud.entities.CommonResult;
import com.springcloud.entities.Payment;
import com.springcloud.service.PaymentService;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;
import java.util.concurrent.TimeUnit;

@RestController
@Slf4j
public class PaymentController {
    @Resource
    PaymentService paymentService;
    @Value("${server.port}")
    private  String serverPort;

    @Resource
    DiscoveryClient discoveryClient;
    @PostMapping("/payment/create")
    public CommonResult<Integer> create(@RequestBody  Payment payment){
        int result = paymentService.create(payment);
        log.info("插入结果"+result);
        if(result>0){
            return  new CommonResult<>(200,"插入数据成功",result);
        }else{
            return  new CommonResult<>(500,"插入数据失败",result);
        }
    }
    @GetMapping("/payment/get/{id}")
    public CommonResult<Payment> getPaymentById(@PathVariable("id") long id){
         Payment result =paymentService.getPaymentById(id);
         log.info("查询结果"+ result);

         if(result!=null){
             return  new CommonResult<>(200,"查询成功"+serverPort,result);
         }else {
             return  new CommonResult<>(500,"未查询到", null);
         }

    }

    @GetMapping(value = "/payment/discovery")
    public Object discovery (){
        List<String> services = discoveryClient.getServices();
        for (String s:services
        ) {
            log.info("element*****"+s);
        }
        List<ServiceInstance> instances = discoveryClient.getInstances("CLOUD-PAYMENT-SERVICE");
        for (ServiceInstance instance: instances
        ) {
            log.info(instance.getServiceId()+"\t"+instance.getHost()+"\t"+instance.getPort()
                    +"\t"+instance.getUri());
        }
        return discoveryClient;
    }

    @GetMapping("/payment/lb")
    public String getPaymentLB(){
        return serverPort;
    }
    @GetMapping(value = "/payment/feign/timeout")
    public String paymentFeignTimeout(){
        try {
            TimeUnit.SECONDS.sleep(3);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return serverPort;
    }
}
