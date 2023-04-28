package com.szw.rabbitmq;

import com.rabbitmq.client.*;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * @Author Szw 2001
 * @Date 2023/4/21 12:23
 * @Slogn 致未来的你！
 */
public class Consumer {
    //队列的名称
    public static final String QUEUE_NAME="hello";

    //接收消息
    public static void main(String[] args) throws Exception {
        //创建工厂
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("127.0.0.1");
        factory.setUsername("guest");
        factory.setPassword("guest");
        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();
        //声明 接收消息
        DeliverCallback deliverCallback = (var1,var2) -> {
            System.out.println(new String(var2.getBody()));
        };
        //声明 取消消息时的回调
        CancelCallback cancelCallback = var1 -> {
            System.out.println("消息消费被中断");
        };

        /*
          消费者消费
          1.消费哪个队列
          2.消费成功之后是否要自动应答 true:自动应答 false:手动应答
          3.消费者若未成功消费的回调
          4.消费者取消消费的回调
         **/
        channel.basicConsume(QUEUE_NAME,true,deliverCallback,cancelCallback);

    }
}
