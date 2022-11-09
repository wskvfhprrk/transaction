package com.hejz.txjms.config;

import org.springframework.boot.autoconfigure.jms.DefaultJmsListenerContainerFactoryConfigurer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jms.annotation.EnableJms;
import org.springframework.jms.annotation.JmsListenerAnnotationBeanPostProcessor;
import org.springframework.jms.config.DefaultJcaListenerContainerFactory;
import org.springframework.jms.config.DefaultJmsListenerContainerFactory;
import org.springframework.jms.config.JmsListenerContainerFactory;
import org.springframework.jms.connection.JmsTransactionManager;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.transaction.PlatformTransactionManager;

import javax.jms.ConnectionFactory;

/**
 * @author:hejz 75412985@qq.com
 * @create: 2022-11-04 18:46
 * @Description: jms配置，使用配置时在@JmsListener的containerFactory = "msgFactory"配置即可
 */
@EnableJms
@Configuration
public class JmsConfig {
    //得到一个JmsTransactionManager
    @Bean
    PlatformTransactionManager transactionManagec(ConnectionFactory cf) {
        return new JmsTransactionManager(cf);
    }
    //配置jmsTemplate
    @Bean
    JmsTemplate imsTemplate(ConnectionFactory cf) {
        JmsTemplate jmsTemplate = new JmsTemplate();
        jmsTemplate.setConnectionFactory(cf);
        return jmsTemplate;
    }

    @Bean
    JmsListenerContainerFactory<?> msgFactory(ConnectionFactory cf, PlatformTransactionManager transactionManager,DefaultJmsListenerContainerFactoryConfigurer configurer){
        DefaultJmsListenerContainerFactory factory=new DefaultJmsListenerContainerFactory();
        configurer.configure(factory,cf);
        //设置一个长的时间，防止日志过多
        factory.setReceiveTimeout(10000L);
        factory.setTransactionManager(transactionManager);
        return factory;
    }
}