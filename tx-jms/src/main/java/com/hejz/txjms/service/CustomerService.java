package com.hejz.txjms.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author:hejz 75412985@qq.com
 * @create: 2022-11-04 18:11
 * @Description: jms事务测试
 */
@Service
@Slf4j
public class CustomerService {
    @Autowired
    private JmsTemplate jmsTemplate;
    //containerFactory设置自己配置的msgFactory
    @Transactional
    @JmsListener(destination = "customer:msg1:new")
//    @JmsListener(destination = "customer:msg1:new",containerFactory = "msgFactory")
    public void handle(String msg){
        log.info("get msg:{}",msg);
        String reply="reply-"+msg;
        jmsTemplate.convertAndSend("customer:msg:reply",reply);
        if(msg.contains("error")){
            error();
        }
    }

    private void error() {
        throw new RuntimeException("some error");
    }
}
