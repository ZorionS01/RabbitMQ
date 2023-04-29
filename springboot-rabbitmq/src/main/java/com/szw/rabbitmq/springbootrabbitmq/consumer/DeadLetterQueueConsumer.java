package com.szw.rabbitmq.springbootrabbitmq.consumer;

import com.rabbitmq.client.Channel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;


import java.util.Date;

/**
 * @Author Szw 2001
 * @Date 2023/4/28 14:53
 * @Slogn 致未来的你！
 */
@Slf4j
@Component
public class DeadLetterQueueConsumer {

    //接收消息
    @RabbitListener(queues = "QD")
    public void receiverD(Message message, Channel channel) throws Exception{
        String msg = new String(message.getBody());
        log.info("当前的时间：{}，收到死信队列消息",new Date().toString(),msg);
    }

}
