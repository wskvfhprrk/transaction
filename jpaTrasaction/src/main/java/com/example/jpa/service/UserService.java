package com.example.jpa.service;

import com.example.jpa.entity.User;
import com.example.jpa.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.DefaultTransactionDefinition;

/**
 * service
 * <p>
 * 何哥 {@link  PlatformTransactionManager}
 * 2018/11/3 17:53
 **/
@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PlatformTransactionManager platformTransactionManager;

    //注解事务
    @Transactional
    public User save(User user) {
        User user1 = userRepository.save(user);
        error();
        return user1;
    }

    //使用代码事务
    public User saveTransactional(User user) {
        DefaultTransactionDefinition transactionDefinition = new DefaultTransactionDefinition();
        transactionDefinition.setIsolationLevel(TransactionDefinition.ISOLATION_REPEATABLE_READ);
        transactionDefinition.setPropagationBehavior(TransactionDefinition.PROPAGATION_SUPPORTS);
        TransactionStatus ts = platformTransactionManager.getTransaction(transactionDefinition);
        User user1 = userRepository.save(user);
        error();
        platformTransactionManager.commit(ts);
        return user1;
    }

    private void error() {
        throw new RuntimeException("data error");
    }
}
