package com.example.jms.controller;

import com.example.jms.service.CustomerService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api")
@Slf4j
public class CustomerController {
    @Autowired
    JmsTemplate jmsTemplate;
    @Autowired
    CustomerService customerService;

    @GetMapping("customer/msg")
    public void customerMsg(@RequestParam String msg){
        log.info("获取页面数据msg:{}",msg);
        jmsTemplate.convertAndSend("customer:msg1:new",msg);
    }

    @GetMapping("customer/handle")
    public void handle(@RequestParam String msg){
        log.info("获取页面数据msg:{}",msg);
        customerService.handle(msg);

    }

    @GetMapping("getCustomer")
    public String getCustomer(){
        jmsTemplate.setReceiveTimeout(2000L); //设置超时时间
        Object convert = jmsTemplate.receiveAndConvert("customer:msg:reply");
        return convert.toString();
    }

}
