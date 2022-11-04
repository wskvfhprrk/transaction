package com.hejz.springtxjta.controller;

import com.hejz.springtxjta.domain.Customer;
import com.hejz.springtxjta.service.CustomerServeAnnotation;
import com.hejz.springtxjta.service.CustomerServeCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author:hejz 75412985@qq.com
 * @create: 2022-11-04 13:29
 * @Description: 控制器
 */
@RestController
@RequestMapping("api")
public class CustomerController {
    @Autowired
    private CustomerServeAnnotation customerServeAnnotation;
    @Autowired
    private CustomerServeCode customerServeCode;
    @PostMapping("saveByAnnotation")
    public Customer saveByAnnotation(@RequestBody Customer customer){
        return customerServeAnnotation.save(customer);
    }
    @PostMapping("saveByCode")
    public Customer saveByCode(@RequestBody Customer customer){
        return customerServeCode.save(customer);
    }
    @GetMapping("all")
    public List<Customer> getAll(){
        return customerServeAnnotation.getAll();
    }
}
