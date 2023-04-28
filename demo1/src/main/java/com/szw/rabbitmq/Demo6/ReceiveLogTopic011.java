package com.szw.rabbitmq.Demo6;

import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.DeliverCallback;
import com.szw.rabbitmq.Utils.RabbitmqUtil;

/**
 * @Author Szw 2001
 * @Date 2023/4/24 21:39
 * @Slogn 致未来的你！
 */
//声明主题交换机及相关队列 消费者一号
public class ReceiveLogTopic011 {
    //交换机名称
    public static final String EXCHANGE_NAME="topic+log";

    //接收消息
    public static void main(String[] args) throws Exception {
        Channel channel = RabbitmqUtil.getChannel();

        channel.exchangeDeclare(EXCHANGE_NAME, BuiltinExchangeType.TOPIC);

        //生命队列
        String queueName="Q1";

        channel.queueDeclare(queueName,false,false,false,null);

        channel.queueBind(queueName,EXCHANGE_NAME,"*.orange.*");

        System.out.println("等待接收消息。。。。");

        DeliverCallback deliverCallback = (consumerTag, message) -> {
            System.out.println("ReceiveLogTopic011控制台打印接收到的消息:"+new String(message.getBody(),"UTF-8"));
            System.out.println("接收队列:"+queueName+"绑定键:"+message.getEnvelope().getRoutingKey());
        };

        channel.basicConsume(queueName,true,deliverCallback,consumerTag->{});

    }
}
