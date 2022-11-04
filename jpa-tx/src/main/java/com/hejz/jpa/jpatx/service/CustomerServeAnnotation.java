package com.hejz.jpa.jpatx.service;

import com.hejz.jpa.jpatx.dao.CustomerRepositocy;
import com.hejz.jpa.jpatx.domain.Customer;
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
        return customerRepositocy.save(customer);
    }

    public Customer findByUsername(String username){
       return customerRepositocy.findAllByUsername(username);
    }

    public List<Customer> getAll() {
        return customerRepositocy.findAll();
    }
}
