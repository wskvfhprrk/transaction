package com.hejz.txjmsrabbit.controller;

import com.hejz.txjmsrabbit.service.BusinessMessageSender;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author:hejz 75412985@qq.com
 * @create: 2022-11-04 18:18
 * @Description: 死信测试控制器
 */
@RequestMapping("rabbitmq")
@RestController
public class RabbitMQMsgController {
 
    @Autowired
    private BusinessMessageSender sender;

    /**
     * 请求中含有deadletter词才能进入死信队列
     * @param msg
     */
    @RequestMapping("sendmsg")
    public void sendMsg(String msg){
        sender.sendMsg(msg);
    }
}