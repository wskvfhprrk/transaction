package com.hejz.txjmsrabbit.service;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import static com.hejz.txjmsrabbit.config.RabbitMQConfig.BUSINESS_EXCHANGE_NAME;

/**
 * @author:hejz 75412985@qq.com
 * @create: 2022-11-04 18:18
 * @Description: 死信测试service
 */
@Component
public class BusinessMessageSender {
 
    @Autowired
    private RabbitTemplate rabbitTemplate;
 
    public void sendMsg(String msg){
        rabbitTemplate.convertSendAndReceive(BUSINESS_EXCHANGE_NAME, "", msg);
    }
}
 
