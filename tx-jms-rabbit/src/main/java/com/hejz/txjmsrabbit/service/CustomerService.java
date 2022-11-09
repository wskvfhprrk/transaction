package com.hejz.txjmsrabbit.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author:hejz 75412985@qq.com
 * @create: 2022-11-04 18:11
 * @Description: jms事务测试
 */
@Service
@Slf4j
public class CustomerService {
    @Autowired
    private AmqpTemplate amqpTemplate;

    @Transactional
    public void handle(String msg){
        amqpTemplate.convertAndSend("rabbitMsg.topic","rabbitMsg",msg);
        if(msg.contains("error")){
            error();
        }
    }

//    @RabbitListener(queues = "rabbitMsg")
//    public void Listener(@Header Object o){
//        log.info("接收到消息：{}",o);
//    }

    private void error() {
        throw new RuntimeException("some error");
    }
}
