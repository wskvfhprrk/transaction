package com.hejz.txjms.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.DefaultTransactionDefinition;

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
    @Autowired
    private PlatformTransactionManager transactionManager;
    //containerFactory设置自己配置的msgFactory
    @Transactional
//    @JmsListener(destination = "customer:msg1:new")
    @JmsListener(destination = "customer:msg1:new",containerFactory = "msgFactory")
    public void handle(String msg){
        log.info("get msg1:{}",msg);
        String reply="reply1-"+msg;
        jmsTemplate.convertAndSend("customer:msg:reply",reply);
        if(msg.contains("error")){
            error();
        }
    }
//代码方式提交事务
    @JmsListener(destination = "customer:msg2:new")
    public void handleByCode(String msg){
        TransactionDefinition def=new DefaultTransactionDefinition();
        TransactionStatus ts=transactionManager.getTransaction(def);
        try{
            log.info("get msg2:{}",msg);
            String reply="reply2-"+msg;
            jmsTemplate.convertAndSend("customer:msg:reply",reply);
            if(msg.contains("error")){
                transactionManager.rollback(ts);
                error();
            }else {
                transactionManager.commit(ts);
            }
        }catch (Exception e){
            transactionManager.rollback(ts);
            throw e;
        }
    }

    private void error() {
        throw new RuntimeException("some error");
    }
}
