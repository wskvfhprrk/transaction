package com.hejz.springtxjta.service;

import com.hejz.springtxjta.dao.CustomerRepositocy;
import com.hejz.springtxjta.domain.Customer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

/**
 * @author:hejz 75412985@qq.com
 * @create: 2022-11-04 13:26
 * @Description: 代码实现事务
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
        //传播机制中PROPAGATION_SUPPORTS是原来如果有事务就有，没有就算了
//        dtf.setPropagationBehavior(TransactionDefinition.PROPAGATION_SUPPORTS);
        //修改默认传播机制——没有事务添加事务
        dtf.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);
        TransactionStatus ts = transactionManager.getTransaction(dtf);
        try{
            Customer customer1 = customerRepositocy.save(customer);
            transactionManager.commit(ts);
            error();
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
