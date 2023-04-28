package com.szw.rabbitmq.Demo7;

import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.DeliverCallback;
import com.szw.rabbitmq.Utils.RabbitmqUtil;

import java.util.HashMap;
import java.util.Map;

/**
 * @Author Szw 2001
 * @Date 2023/4/26 21:32
 * @Slogn 致未来的你！
 */
//死信队列
public class Consumer02 {


    //死信队列名称
    public static final String DEAD_QUEUE="dead_queue";

    public static void main(String[] args) throws Exception {
        Channel channel = RabbitmqUtil.getChannel();

        System.out.println("等待接收消息........");
        DeliverCallback deliverCallback = (consumerTag, message) -> {
            System.out.println("Consumer02接收的消息是:"+new String(message.getBody(),"UTF-8"));
             };

        channel.basicConsume(DEAD_QUEUE,true,deliverCallback,consumerTag->{});
    }
}
