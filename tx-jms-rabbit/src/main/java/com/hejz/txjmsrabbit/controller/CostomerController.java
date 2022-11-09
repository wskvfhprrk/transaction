package com.hejz.txjmsrabbit.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
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


    /**
     * 直接通过
     * @param msg
     */
    @GetMapping("dirtect")
    public String dirtect(@RequestParam String msg){
        log.info("发送消息：{}",msg);
        amqpTemplate.convertAndSend("rabbitmqMsg.topic","rabbitmqMsg.bindKey",msg);
        return "发送消息成功";
    }
    @GetMapping("getmsg")
    public String getMsg(){
        //超时2秒给结果——否则一直等下去
        amqpTemplate.receive(2000L);
        Object o = amqpTemplate.receiveAndConvert("rabbitmqMsg");
        log.info("前端猎取消息：{}",o);
        return String.valueOf(o);
    }

    @RabbitListener(queues = "rabbitmqMsg")
    public void Listener(Object o){
        log.info("监听到消息：{}",o);
    }
}
