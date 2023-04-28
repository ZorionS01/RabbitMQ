package com.szw.rabbitmq.Demo2;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.MessageProperties;
import com.szw.rabbitmq.Utils.RabbitmqUtil;

import java.util.Scanner;

/**
 * @Author Szw 2001
 * @Date 2023/4/23 18:03
 * @Slogn 致未来的你！
 */
//消息在手动应答时 不丢失
public class Task2 {

    //队列名称
    public static final String TASK_QUEUE_NAME="ack_queue";

    public static void main(String[] args) throws Exception {
        Channel channel = RabbitmqUtil.getChannel();
        //开启发布确认
        channel.confirmSelect();
        //声明队列
        boolean durable=true;//需要队列持久化
        channel.queueDeclare(TASK_QUEUE_NAME,durable,false,false,null);
        //从控制台中输入信息
        Scanner scanner = new Scanner(System.in);
        while (scanner.hasNext()){
            String msg = scanner.next();
            //设置生产者发送消息为持久化消息
            channel.basicPublish("",TASK_QUEUE_NAME, MessageProperties.PERSISTENT_TEXT_PLAIN,msg.getBytes("UTF-8"));
            System.out.println("生产者发出消息:"+msg);
        }
    }
}
