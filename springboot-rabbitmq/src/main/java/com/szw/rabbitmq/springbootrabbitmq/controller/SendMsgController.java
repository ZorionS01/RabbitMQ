package com.szw.rabbitmq.springbootrabbitmq.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

/**
 * @Author Szw 2001
 * @Date 2023/4/28 14:42
 * @Slogn 致未来的你！
 */
@Slf4j
@RestController
@RequestMapping("ttl")
public class SendMsgController {

    @Autowired
    private RabbitTemplate rabbitTemplate;


    //开始发消息
    @GetMapping("/sendMsg/{message}")
    public void sendMsg(@PathVariable String message){
      log.info("当前时间:{},发送一条信息给两个TTL队列：{}",new Date().toString(),message);
      rabbitTemplate.convertAndSend("X","XA","消息来自TTL为10s的队列"+message);
      rabbitTemplate.convertAndSend("X", "XB", "消息来自 ttl 为 40S 的队列: "+message);
    }
}
