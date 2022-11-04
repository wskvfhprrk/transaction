package com.hejz.txjms.controller;

import com.hejz.txjms.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.web.bind.annotation.*;

/**
 * @author:hejz 75412985@qq.com
 * @create: 2022-11-04 18:18
 * @Description: 代码事务控制器
 */
@RestController
@RequestMapping("api/msg2")
public class CostomerController2 {
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
        jmsTemplate.convertAndSend("customer:msg2:new",msg);
    }

    /**
     * 直接通过
     * @param msg
     */
    @PostMapping("dirtect")
    public void dirtect(@RequestParam String msg){
        customerService.handleByCode(msg);
    }
}
