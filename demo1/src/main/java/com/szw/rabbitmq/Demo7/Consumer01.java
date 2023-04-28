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
public class Consumer01 {

    //普通交换机
    public static final String NORMAL_EXCHANGE="normal_exchange";
    //死信交换机
    public static final String DEAD_EXCHANGE="dead_exchange";
    //普通队列的名称
    public static final String NORMAL_QUEUE="normal_queue";
    //死信队列名称
    public static final String DEAD_QUEUE="dead_queue";

    public static void main(String[] args) throws Exception {
        Channel channel = RabbitmqUtil.getChannel();
        //声明交换机
        channel.exchangeDeclare(NORMAL_EXCHANGE, BuiltinExchangeType.DIRECT);
        channel.exchangeDeclare(DEAD_EXCHANGE,BuiltinExchangeType.DIRECT);
        //声明死信和普通队列
        //-------------------------------------------------------------------
        Map<String,Object> map = new HashMap<>();
        //过期时间
//        map.put("x-message-ttl",10000);
        //正常队列设置死信交换机
        map.put("x-dead-letter-exchange",DEAD_EXCHANGE);
        //设置死信Routingkey
        map.put("x-dead-letter-routing-key","lisi");
       /* //设置正常队列长度的限制
        map.put("x-max-length",6);*/
        channel.queueDeclare(NORMAL_QUEUE,false,false,false,map);
        //绑定普通交换机与队列
        channel.queueBind(NORMAL_QUEUE,NORMAL_EXCHANGE,"zhangsan");
        channel.queueDeclare(DEAD_QUEUE,false,false,false,null);
        //绑定死信交换机与队列
        channel.queueBind(DEAD_QUEUE,DEAD_EXCHANGE,"lisi");
        System.out.println("等待接收消息........");
        DeliverCallback deliverCallback = (consumerTag, message) -> {
            String s = new String(message.getBody(), "UTF-8");
            if (s.equals("info5")){
                System.out.println("Consumer01接收的消息是:"+s+"：此消息是被c1拒绝的");
                channel.basicReject(message.getEnvelope().getDeliveryTag(),false);
            }else {
                System.out.println("Consumer01接收的消息是:"+s);
                channel.basicAck(message.getEnvelope().getDeliveryTag(),false);
            }

             };
        //开启手动应答
        channel.basicConsume(NORMAL_QUEUE,false,deliverCallback,consumerTag->{});
    }
}
