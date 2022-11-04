package com.hejz.springtxjta.service;

import com.hejz.springtxjta.dao.CustomerRepositocy;
import com.hejz.springtxjta.domain.Customer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author:hejz 75412985@qq.com
 * @create: 2022-11-04 13:26
 * @Description: 标签实现事务
 */
@Service
public class CustomerServeAnnotation {

    @Autowired
    private CustomerRepositocy customerRepositocy;

    @Transactional
    public Customer save(Customer customer){
        error();
        return customerRepositocy.save(customer);
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
