package com.hejz.txjmsrabbit.service;

import com.rabbitmq.client.Channel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.io.IOException;

import static com.hejz.txjmsrabbit.config.DeadRabbitMQConfig.BUSINESS_QUEUEA_NAME;
import static com.hejz.txjmsrabbit.config.DeadRabbitMQConfig.BUSINESS_QUEUEB_NAME;


/**
 * @author:hejz 75412985@qq.com
 * @create: 2022-11-04 18:18
 * @Description:  测试消息被拒绝进入死信交换机
 */
@Slf4j
@Component
public class BusinessMessageReceiver {

    @RabbitListener(queues = BUSINESS_QUEUEA_NAME)
    public void receiveA(Message message, Channel channel) throws IOException {
        String msg = new String(message.getBody());
        log.info("收到业务消息A：{}", msg);
        boolean ack = true;
        Exception exception = null;
        try {
            if (msg.contains("deadletter")){
                throw new RuntimeException("dead letter exception");
            }
        } catch (Exception e){
            ack = false;
            exception = e;
        }
        if (!ack){ //如果是死信消息就被道拒绝进入死信队列
            log.error("消息消费发生异常，error msg:{}，消息进入死信队列", exception.getMessage());
//            log.error("消息消费发生异常，error msg:{}", exception.getMessage(), exception);
            channel.basicNack(message.getMessageProperties().getDeliveryTag(), false, false);
        } else { //如果不含有拒绝的就被确认
            log.info("如果不含有\"deadletter\"拒绝的就被确认");
            channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
        }
    }
 
    @RabbitListener(queues = BUSINESS_QUEUEB_NAME)
    public void receiveB(Message message, Channel channel) throws IOException {
        System.out.println("收到业务消息B：" + new String(message.getBody()));
        channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
    }
}