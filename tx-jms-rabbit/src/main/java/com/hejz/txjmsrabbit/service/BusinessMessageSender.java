package com.hejz.txjmsrabbit.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import static com.hejz.txjmsrabbit.config.DeadRabbitMQConfig.BUSINESS_EXCHANGE_NAME;

/**
 * @author:hejz 75412985@qq.com
 * @create: 2022-11-04 18:18
 * @Description: 死信测试service
 */
@Component
@Slf4j
public class BusinessMessageSender {
 
    @Autowired
    private RabbitTemplate rabbitTemplate;

    public void sendMsg(String msg){
        log.info("发送消息：{}",msg);
        rabbitTemplate.convertSendAndReceive(BUSINESS_EXCHANGE_NAME, "", msg);
    }
}
 
