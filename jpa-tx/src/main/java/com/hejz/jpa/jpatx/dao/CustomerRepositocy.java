package com.hejz.jpa.jpatx.dao;

import com.hejz.jpa.jpatx.domain.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * @author:hejz 75412985@qq.com
 * @create: 2022-11-04 13:24
 * @Description: dao
 */
public interface CustomerRepositocy extends JpaRepository<Customer,Long> {

    Customer findAllByUsername(String username);

}
