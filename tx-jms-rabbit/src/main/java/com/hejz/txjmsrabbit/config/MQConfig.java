package com.hejz.txjmsrabbit.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListenerConfigurer;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.listener.RabbitListenerEndpointRegistrar;
import org.springframework.amqp.rabbit.transaction.RabbitTransactionManager;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.converter.MappingJackson2MessageConverter;
import org.springframework.messaging.handler.annotation.support.DefaultMessageHandlerMethodFactory;
import org.springframework.messaging.handler.annotation.support.MessageHandlerMethodFactory;

/**
 * @author:hejz 75412985@qq.com
 * @create: 2022-11-09 16:49
 * @Description: rabbitmq启动配置类：配置消息可靠性和事务同时生效（配置文件中有配置）
 */
@Slf4j
@Configuration
public class MQConfig implements RabbitListenerConfigurer {

    @Bean
    public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory) {
        RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        //消息序列化
        rabbitTemplate.setMessageConverter(new Jackson2JsonMessageConverter(new ObjectMapper()));
        //开启事务,只需要事务的话该函数剩下的代码可不要
        rabbitTemplate.setChannelTransacted(true);
        
        //交换器无法根据自身类型和路由键找到一个符合条件的队列时的处理方式
        //true:将消息返回给生产者   false:直接丢弃
        rabbitTemplate.setMandatory(true);

        //消息是否到交换机的回调
        rabbitTemplate.setConfirmCallback((correlationData, isAck, s) -> {
            if (isAck) {
                log.info("消息已发送到交换器 cause:{} - {}", s, correlationData.toString());
            } else {
                log.info("消息未发送到交换器 cause:{} - {}", s, correlationData.toString());
            }

        });
        //消息没有到队列则回调该函数
        rabbitTemplate.setReturnsCallback(returnedMessage -> log.info("消息被退回 {}", returnedMessage.toString()));
        return rabbitTemplate;
    }

    /**
     * 消息反序列化
     * @param registrar
     */
    @Override
    public void configureRabbitListeners(RabbitListenerEndpointRegistrar registrar) {
        registrar.setMessageHandlerMethodFactory(messageHandlerMethodFactory());
    }

    @Bean
    MessageHandlerMethodFactory messageHandlerMethodFactory() {
        DefaultMessageHandlerMethodFactory messageHandlerMethodFactory = new DefaultMessageHandlerMethodFactory();
        messageHandlerMethodFactory.setMessageConverter(mappingJackson2MessageConverter());
        return messageHandlerMethodFactory;
    }

    @Bean
    public MappingJackson2MessageConverter mappingJackson2MessageConverter() {
        return new MappingJackson2MessageConverter();
    }


    /**
     * rabbitmq事务管理器
     * @param connectionFactory
     * @return
     */
    @Bean("rabbitTransactionManager")
    RabbitTransactionManager rabbitTransactionManager(ConnectionFactory connectionFactory) {
        return new RabbitTransactionManager(connectionFactory);
    }
}