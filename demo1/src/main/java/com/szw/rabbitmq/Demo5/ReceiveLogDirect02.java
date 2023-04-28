package com.szw.rabbitmq.Demo5;

import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.DeliverCallback;
import com.szw.rabbitmq.Utils.RabbitmqUtil;

/**
 * @Author Szw 2001
 * @Date 2023/4/24 21:09
 * @Slogn 致未来的你！
 */
public class ReceiveLogDirect02 {
    public static final String EXCHANGE_NAME="direct_log";

    public static void main(String[] args) throws Exception {
        Channel channel = RabbitmqUtil.getChannel();

        //声明一个交换机
        channel.exchangeDeclare(EXCHANGE_NAME, BuiltinExchangeType.DIRECT);
        //声明一个队列
        String queue = channel.queueDeclare("disk",false,false,false,null).getQueue();

        channel.queueBind("disk",EXCHANGE_NAME,"error");

        DeliverCallback deliverCallback = (consumerTag, message) -> {
            System.out.println("ReceiveLogDirect02控制台打印接收到的消息:"+new String(message.getBody(),"UTF-8"));

        };

        channel.basicConsume("disk",true,deliverCallback,consumeTag->{});
    }
}
