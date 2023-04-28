package com.szw.rabbitmq.Demo4;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.DeliverCallback;
import com.szw.rabbitmq.Utils.RabbitmqUtil;

/**
 * @Author Szw 2001
 * @Date 2023/4/24 20:02
 * @Slogn 致未来的你！
 */
//接收消息 消息1
public class ReceiveLog1 {

    //交换机名称
    public static final String EXCHANGE_NAME="logs";

    public static void main(String[] args) throws Exception {
        Channel channel = RabbitmqUtil.getChannel();
        //声明一个交换机
        channel.exchangeDeclare(EXCHANGE_NAME,"fanout");
        //声明一个队列 临时队列
        /*
         * 生成一个临时队列 队列名称是随机的
         * 1当消费者断开与队列的连接时候 队列就自动删除
         **/
        String queueName = channel.queueDeclare().getQueue();
        /*
        绑定交换机与队列
         **/
        channel.queueBind(queueName,EXCHANGE_NAME,"");
        System.out.println("等待接受消息，把接收到的消息打印在屏幕上...");
        //接收消息
        DeliverCallback deliverCallback = (consumerTag, message) -> {
            System.out.println("ReceiveLog1控制台打印接收到的消息:"+new String(message.getBody(),"UTF-8"));

        };
        //消费者取消回调接口
        channel.basicConsume(queueName,true,deliverCallback,consumerTag->{});
    }
}
