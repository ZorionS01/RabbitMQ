package com.szw.rabbitmq.Demo2;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.DeliverCallback;
import com.szw.rabbitmq.Utils.RabbitmqUtil;
import com.szw.rabbitmq.Utils.SleepUtil;

/**
 * @Author Szw 2001
 * @Date 2023/4/23 18:11
 * @Slogn 致未来的你！
 */
public class Worker3 {
    //队列名称
    public static final String TASK_QUEUE_NAME="ack_queue";

    //消费消息
    public static void main(String[] args) throws Exception {
        Channel channel = RabbitmqUtil.getChannel();
        System.out.println("C2等待接受消息处理时间较长");

        DeliverCallback deliverCallback = (consumerTag,message) -> {
            //沉睡一秒
            SleepUtil.sleep(30);
            System.out.println("接收到的消息:"+new String(message.getBody(),"UTF-8"));
            //手动应答代码
            /*
            1.消息的标记 tag
            2.是否批量应答 false:不批量 true:批量
             **/

            channel.basicAck(message.getEnvelope().getDeliveryTag(),false);
        };
        //设置不公平分发
        //int prefetchCount = 1;
        //设置预取值
        int prefetchCount = 5;
        channel.basicQos(prefetchCount);
        //手动应答
        boolean autoAck = false;
        channel.basicConsume(TASK_QUEUE_NAME,autoAck,deliverCallback,(consumerTag->{
            System.out.println(consumerTag+"消费者取消取消接口回调逻辑");
        }));

    }

}
