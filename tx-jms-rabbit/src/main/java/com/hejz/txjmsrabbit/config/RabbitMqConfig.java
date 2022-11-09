//package com.hejz.txjmsrabbit.config;
//
//import org.springframework.amqp.core.Binding;
//import org.springframework.amqp.core.BindingBuilder;
//import org.springframework.amqp.core.Queue;
//import org.springframework.amqp.core.TopicExchange;
//import org.springframework.beans.factory.annotation.Qualifier;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//
//@Configuration
//public class RabbitMqConfig {
//
//    /**
//     * rabbitmq中，消息发送者只需要知道交换机的名称就行(还需携带路由键)，不需要知道队列的名称
//     * rabbitmq中，消息消费者只需要知道队列名称就行，不需要知道交换器和路由键的名称
//     */
//
//    /**
//     * @author ZhengTianLiang
//     * @date 2020/9/12  22:42
//     * @desc 往rabbitmq的broker里面创建一个队列
//     */
//    @Bean(name = "message")
//    public Queue getQueue() {
//        return new Queue("zheng.alipay.message");
//    }
//
//    /**
//     * @author ZhengTianLiang
//     * @date 2020/9/12  22:46
//     * @desc 创建交换器
//     */
//    @Bean
//    public TopicExchange getExchange(){
//        return new TopicExchange("zheng.alipay.exchange");
//    }
//
//    @Bean
//    Binding bindingExchangeMessage(@Qualifier("message") Queue getQueue,TopicExchange getExchange){
//        return BindingBuilder.bind(getQueue).to(getExchange()).with("zheng.alipay.routkey");
//    }
//
//
//}