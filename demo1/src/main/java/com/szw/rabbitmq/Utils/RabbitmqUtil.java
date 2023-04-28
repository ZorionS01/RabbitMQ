package com.szw.rabbitmq.Utils;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;



/**
 * @Author Szw 2001
 * @Date 2023/4/21 12:54
 * @Slogn 致未来的你！
 */
public class RabbitmqUtil {
    //得到一个连接工厂
    public static Channel getChannel() throws Exception {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("127.0.0.1");
        factory.setUsername("guest");
        factory.setPassword("guest");
        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();
        return channel;
    }
}
