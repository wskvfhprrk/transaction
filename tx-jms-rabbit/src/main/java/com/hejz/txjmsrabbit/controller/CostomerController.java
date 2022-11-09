package com.hejz.txjmsrabbit.controller;

import com.hejz.txjmsrabbit.service.CustomerService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author:hejz 75412985@qq.com
 * @create: 2022-11-04 18:18
 * @Description: 控制器
 */
@RestController
@RequestMapping("api/msg")
@Slf4j
public class CostomerController {
    @Autowired
    private AmqpTemplate amqpTemplate;
    @Autowired
    private CustomerService customerService;

    /**
     * 通过监听方式
     * @param msg
     */
//    @PostMapping("listen")
//    public void listen(@RequestParam String msg){
//        rabbitTemplate.convertAndSend("customer:msg1:new",msg);
//    }

    /**
     * 直接通过
     * @param msg
     */
    @GetMapping("dirtect")
    public void dirtect(@RequestParam String msg){
        customerService.handle(msg);
    }
    @GetMapping("all")
    public String getMsg(){
        //超时2秒给结果——否则一直等下去
//        rabbitTemplate.setReceiveTimeout(2000L);
        Object o = amqpTemplate.receiveAndConvert("rabbitMsg");
        log.info("前端猎取消息：{}",o);
        return String.valueOf(o);
    }
}
