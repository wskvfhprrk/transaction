package com.hejz.txjmsrabbit.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.transaction.RabbitTransactionManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionCallback;
import org.springframework.transaction.support.TransactionTemplate;
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
    private RabbitTemplate rabbitTemplate;
    @Autowired
    private ConnectionFactory connectionFactory;

    /**
     * 直接通过
     * @param msg
     */
    @GetMapping("send")
//    @Transactional
    public void send(@RequestParam String msg){
        log.info("发送消息：{}",msg);
//        rabbitTemplate.convertAndSend("rabbitmqMsg.topic","rabbitmqMsg.bindKey",msg);
        TransactionTemplate transactionTemplate = new TransactionTemplate(new RabbitTransactionManager(connectionFactory));
        transactionTemplate.execute((TransactionCallback<String>) transactionStatus -> {
            RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);  //AmqpTemplate目前只有RabbitTemplate实现
            rabbitTemplate.setChannelTransacted(true);  //启用事务
            try {
                rabbitTemplate.convertAndSend("rabbitmqMsg.topic","rabbitmqMsg.bindKey",msg);
                if(msg.contains("error")){
                    error();
                }
            } catch (Exception e) {
                transactionStatus.setRollbackOnly();    //设置回滚标识
            }
            return null;
        });
    }

    private void error() {
        throw new RuntimeException("some error");
    }

    @GetMapping("getmsg")
    public String getMsg(){
        //超时2秒给结果——否则一直等下去
        rabbitTemplate.receive(2000L);
        Object o = rabbitTemplate.receiveAndConvert("rabbitmqMsg");
        log.info("前端猎取消息：{}",o);
        return String.valueOf(o);
    }

//    @RabbitListener(queues = "rabbitmqMsg")
//    public void Listener(Object o){
//        log.info("监听到消息：{}",o);
//    }
}
