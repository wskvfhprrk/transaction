//package com.hejz.txjmsrabbit.config;
//
//import org.springframework.amqp.core.*;
//import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
//import org.springframework.amqp.rabbit.connection.ConnectionFactory;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//
//
///**
// * @author:hejz 75412985@qq.com
// * @create: 2022-11-09 16:49
// * @Description: rabbitmq启动配置类
// */
//@Configuration
//public class RabbitMqConfig {
//    /**
//     * 创建队列rabbitmqMsg
//     * @return
//     */
//    @Bean
//    Queue queue(){
//        return new Queue("rabbitmqMsg");
//    }
//
//    /**
//     * 队列交换机配置
//     * @return
//     */
//    @Bean
//    Exchange exchange(){
//        return new TopicExchange("rabbitmqMsg.topic");
//    }
//
//    /**
//     * 生成bindKey，把队列用bindKey绑定到路由上
//     * @param queue
//     * @param exchange
//     * @return
//     */
//    @Bean
//    Binding binding(Queue queue,TopicExchange exchange){
//        return BindingBuilder.bind(queue).to(exchange).with("rabbitmqMsg.bindKey");
//    }
//
//    /**
//     * 连接工厂
//     * @return
//     */
//    @Bean
//    public ConnectionFactory rabbitConnectionFactory() {
//        CachingConnectionFactory connectionFactory = new CachingConnectionFactory();
//        connectionFactory.setHost("localhost");
//        connectionFactory.setPort(5672);
//        connectionFactory.setUsername("root");
//        connectionFactory.setPassword("123456");
//        return connectionFactory;
//    }
//}
