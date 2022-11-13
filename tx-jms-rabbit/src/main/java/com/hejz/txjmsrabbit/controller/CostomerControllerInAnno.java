package com.hejz.txjmsrabbit.controller;

import com.hejz.txjmsrabbit.domain.Customer;
import com.hejz.txjmsrabbit.service.CostomerService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


/**
 * @author:hejz 75412985@qq.com
 * @create: 2022-11-04 18:18
 * @Description: 标签实现事物
 */
@RestController
@RequestMapping("api/anno")
@Slf4j
public class CostomerControllerInAnno {
    @Autowired
    private CostomerService costomerService;

    /**
     * 直接通过
     * @param msg
     */
    @GetMapping("send")
    public void sendAnno(@RequestParam String msg){
        costomerService.send(msg);
    }
    @GetMapping("all")
    public List<Customer> getAll(){
        return costomerService.all();
    }

}
