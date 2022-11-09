package com.hejz.txjmsrabbit.config;

import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


/**
 * @author:hejz 75412985@qq.com
 * @create: 2022-11-09 16:49
 * @Description: rabbitmq启动配置类
 */
@Configuration
public class RabbitMqConfig {
    /**
     * 创建队列rabbitmqMsg
     * @return
     */
    @Bean
    Queue queue(){
        return new Queue("rabbitmqMsg");
    }

    @Bean
    Exchange exchange(){
        return new TopicExchange("rabbitmqMsg.topic");
    }


    @Bean
    Binding binding(Queue queue,TopicExchange exchange){
        return BindingBuilder.bind(queue).to(exchange).with("rabbitmqMsg.bindKey");
    }
}
