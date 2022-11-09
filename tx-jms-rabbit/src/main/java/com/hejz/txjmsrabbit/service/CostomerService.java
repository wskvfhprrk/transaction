package com.hejz.txjmsrabbit.service;

import com.rabbitmq.client.Channel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;

/**
 * @author:hejz 75412985@qq.com
 * @create: 2022-11-09 21:24
 * @Description: service
 */
@Service
@Slf4j
public class CostomerService {
    @Autowired
    private RabbitTemplate rabbitTemplate;
    //使用自定义的transactionManager管理器
    @Transactional(transactionManager = "rabbitTransactionManager")
    public void send(String msg){
        log.info("发送消息：{}",msg);
        rabbitTemplate.convertAndSend("rabbitmqMsg.topic","rabbitmqMsg.bindKey",msg);
        if(msg.contains("error")){
            error();
        }
    }

    private void error() {
        throw new RuntimeException("some Error");
    }

    @RabbitListener(queues = "rabbitmqMsg")
//    @RabbitHandler
    public void listener(Message message, Channel channel) throws IOException {
        log.info("接收到的数据为 ====> {}",message);
        channel.basicAck(message.getMessageProperties().getDeliveryTag(),false);
        /**
         *
         * channel.basicAck(msg.getMessageProperties().getDeliveryTag(),false);
         * ack表示确认消息。multiple：false只确认该delivery_tag的消息，true确认该delivery_tag的所有消息
         *
         * channel.basicReject(msg.getMessageProperties().getDeliveryTag(),false);
         * Reject表示拒绝消息。requeue：false表示被拒绝的消息是丢弃；true表示重回队列
         *
         * channel.basicNack(msg.getMessageProperties().getDeliveryTag(),false,false);
         * nack表示拒绝消息。multiple表示拒绝指定了delivery_tag的所有未确认的消息，requeue表示不是重回队列
         *
         */
    }
}
