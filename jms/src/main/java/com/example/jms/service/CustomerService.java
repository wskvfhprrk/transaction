package com.example.jms.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class CustomerService {

    @Autowired
    JmsTemplate jmsTemplate;

    @JmsListener(destination = "customer:msg1:new")
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
