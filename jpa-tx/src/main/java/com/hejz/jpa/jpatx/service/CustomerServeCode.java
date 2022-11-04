package com.hejz.jpa.jpatx.service;

import com.hejz.jpa.jpatx.dao.CustomerRepositocy;
import com.hejz.jpa.jpatx.domain.Customer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import java.util.List;

/**
 * @author:hejz 75412985@qq.com
 * @create: 2022-11-04 13:26
 * @Description: 标签实现事务
 */
@Service
public class CustomerServeCode {

    @Autowired
    private CustomerRepositocy customerRepositocy;
    @Autowired
    private PlatformTransactionManager transactionManager;

    public Customer save(Customer customer){
        DefaultTransactionDefinition dtf=new DefaultTransactionDefinition();
        dtf.setIsolationLevel(TransactionDefinition.ISOLATION_DEFAULT);
        dtf.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);
        TransactionStatus ts = transactionManager.getTransaction(dtf);
        try{
            Customer customer1 = customerRepositocy.save(customer);
            error();
            transactionManager.commit(ts);
            return customer1;
        }catch (Exception e){
            transactionManager.rollback(ts);
            throw e;
        }

    }

    private void error() {
        throw new RuntimeException("some error");
    }

}
