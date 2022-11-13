package com.hejz.txjmsrabbit.dao;

import com.hejz.txjmsrabbit.domain.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author:hejz 75412985@qq.com
 * @create: 2022-11-04 13:24
 * @Description: dao
 */
public interface CustomerRepositocy extends JpaRepository<Customer,Long> {

    Customer findAllByUsername(String username);

}
