package com.hejz.txjmsrabbit.controller;

import com.hejz.txjmsrabbit.service.CostomerService;
import com.rabbitmq.client.Channel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

/**
 * @author:hejz 75412985@qq.com
 * @create: 2022-11-04 18:18
 * @Description: 标签实现事物
 */
@RestController
@RequestMapping("api/msg")
@Slf4j
public class CostomerControllerInAnno {
    @Autowired
    private CostomerService costomerService;

    /**
     * 直接通过
     * @param msg
     */
    @GetMapping("sendAnno")
    public void sendAnno(@RequestParam String msg){
        costomerService.send(msg);
    }

    private void error() {
        throw new RuntimeException("some error");
    }
}
