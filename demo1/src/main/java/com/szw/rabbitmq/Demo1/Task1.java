package com.szw.rabbitmq.Demo1;


import com.rabbitmq.client.Channel;
import com.szw.rabbitmq.Utils.RabbitmqUtil;

import java.util.Scanner;

/**
 * @Author Szw 2001
 * @Date 2023/4/21 13:43
 * @Slogn 致未来的你！
 */
//生产者
public class Task1 {
 //队列名称
    public static final String QUEUE_NAME="hello";
    //发送大量消息
    public static void main(String[] args) throws Exception {
        Channel channel = RabbitmqUtil.getChannel();
        //队列的声明
        channel.queueDeclare(QUEUE_NAME,false,false,false,null);
        Scanner scanner = new Scanner(System.in);
        while (scanner.hasNext()){
            String msg = scanner.next();
            channel.basicPublish("",QUEUE_NAME,null,msg.getBytes());
            System.out.println("消息发送完毕:"+msg);
        }
    }
}

