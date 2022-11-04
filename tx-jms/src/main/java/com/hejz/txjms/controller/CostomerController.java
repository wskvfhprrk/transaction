package com.hejz.txjms.controller;

import com.hejz.txjms.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.web.bind.annotation.*;

/**
 * @author:hejz 75412985@qq.com
 * @create: 2022-11-04 18:18
 * @Description: 控制器
 */
@RestController
@RequestMapping("api/msg")
public class CostomerController {
    @Autowired
    private JmsTemplate jmsTemplate;
    @Autowired
    private CustomerService customerService;

    /**
     * 通过监听方式
     * @param msg
     */
    @PostMapping("listen")
    public void listen(@RequestParam String msg){
        jmsTemplate.convertAndSend("customer:msg1:new",msg);
    }

    /**
     * 直接通过
     * @param msg
     */
    @PostMapping("dirtect")
    public void dirtect(@RequestParam String msg){
        customerService.handle(msg);
    }
    @GetMapping("all")
    public String getMsg(){
        //超时2秒给结果——否则一直等下去
        jmsTemplate.setReceiveTimeout(2000L);
        Object o = jmsTemplate.receiveAndConvert("customer:msg:reply");
        return String.valueOf(o);
    }
}
