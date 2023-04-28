package com.szw.rabbitmq;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * @Author Szw 2001
 * @Date 2023/4/20 22:43
 * @Slogn 致未来的你！
 */
public class Producer {
    //队列名称
    public static final String QUEUE_NAME="hello";
    //发消息
    public static void main(String[] args) throws Exception {
        //创建一个连接工程
        ConnectionFactory factory = new ConnectionFactory();
        //工厂IP 连接RabbitMQ的队列
        factory.setHost("127.0.0.1");
        factory.setUsername("guest");
        factory.setPassword("guest");
        Connection connection = factory.newConnection();
        //获取信道
        Channel channel = connection.createChannel();
        /*
         * 创建队列
         * 1.队列名
         * 2.队列里面的消息是否持久化（磁盘） 默认消息存储在内存中
         * 3.该队列是否只供一个消费者消费 是否进行消息的共享 true:多个消费者消费 false:不允许多个消费者(一个消费者)
         * 4.是否自动删除 最后一个消费者断开连接 该队列是否自动删除 true:自动删除
         * 5.其他参数
         **/
        channel.queueDeclare(QUEUE_NAME,false,false,false,null);
        //发消息
        String message = "hello,world!";
        /*
         * 1.发送到哪个交换机
         * 2.路由的key值是哪个，本次是队列的名称
         * 3.其他参数信息
         * 4.发送消息的消息体
         **/
        channel.basicPublish("",QUEUE_NAME,null,message.getBytes());
        System.out.println("消息发送完毕！");

    }
}
