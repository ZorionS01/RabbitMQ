package com.szw.rabbitmq.Demo1;

import com.rabbitmq.client.CancelCallback;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.DeliverCallback;
import com.szw.rabbitmq.Utils.RabbitmqUtil;

/**
 * @Author Szw 2001
 * @Date 2023/4/21 12:56
 * @Slogn 致未来的你！
 */
//工作线程1 <--> 消费者
public class Worker1 {
    //队列的名称
    public static final String QUEUE_NAME="hello";

    public static void main(String[] args) throws Exception {
        Channel channel = RabbitmqUtil.getChannel();

        DeliverCallback deliverCallback = (var1,var2)->{
            System.out.println("worker1接收到的消息:"+new String(var2.getBody()));
        };
        //消费接受被取消 执行下面内容
        CancelCallback cancelCallback = var1 -> {
            System.out.println(var1+"消息被消费者取消消费接口回调逻辑");
        };
        System.out.println("C2等待接收消息......");
        //消息的接受
        channel.basicConsume(QUEUE_NAME,true,deliverCallback,cancelCallback);
    }
}
