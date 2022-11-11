package com.hejz.springtxjta.service;

import com.hejz.springtxjta.dao.CustomerRepositocy;
import com.hejz.springtxjta.domain.Customer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author:hejz 75412985@qq.com
 * @create: 2022-11-04 13:26
 * @Description: 注解实现事务
 */
@Service
@Slf4j
public class CustomerServeAnnotation {

    @Autowired
    private CustomerRepositocy customerRepositocy;
    @Autowired
    private JmsTemplate jmsTemplate;

    @Transactional
    public Customer save(Customer customer){
        error();
        log.info("save:{}",customer);
        jmsTemplate.convertAndSend("create:new",customer.getUsername());
        return customerRepositocy.save(customer);
    }

    @JmsListener(destination = "create:new")
    public void listener(String name){
        log.info("接收到消息：{}",name);
    }

    private void error() {
        throw new RuntimeException("some error");
    }

    public Customer findByUsername(String username){
       return customerRepositocy.findAllByUsername(username);
    }

    public List<Customer> getAll() {
        return customerRepositocy.findAll();
    }
}
