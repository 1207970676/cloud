package com.springcloud.service;

import org.springframework.stereotype.Component;

@Component
public class PaymentFallbackService implements  PaymentHystrixService{
    @Override
    public String paymentInfo_OK(Integer id) {
        return "fallBack  paymentInfo_OK /(ㄒoㄒ)/~~ ";
    }

    @Override
    public String paymentInfo_TimeOut(Integer id) {
        return "fallBack  paymentInfo_TimeOut /(ㄒoㄒ)/~~ ";
    }
}
