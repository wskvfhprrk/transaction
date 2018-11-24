package com.example.jms.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
public class CustomerService {

    @Autowired
    JmsTemplate jmsTemplate;

    //如果不加@Transactional注解，handle就不能关联到消息事物，引发jmsTemplate.convertAndSend回滚
    @Transactional
    @JmsListener(destination = "customer:msg1:new",containerFactory = "msgFactory")
    public void handle(String msg){
        log.info("listener接收到msg===={}",msg);
        String reply="reply_"+msg;
        jmsTemplate.convertAndSend("customer:msg:reply",reply);
        if(msg.contains("error")){
            error();
        }
    }

    private void error() {
        throw new RuntimeException("data error");
    }
}
