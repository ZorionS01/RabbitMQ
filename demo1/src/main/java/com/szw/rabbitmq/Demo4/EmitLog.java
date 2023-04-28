package com.szw.rabbitmq.Demo4;

import com.rabbitmq.client.Channel;
import com.szw.rabbitmq.Utils.RabbitmqUtil;

import java.util.Scanner;

/**
 * @Author Szw 2001
 * @Date 2023/4/24 20:14
 * @Slogn 致未来的你！
 */
//发消息 交换机
public class EmitLog {
    //交换机名称
    public static final String EXCHANGE_NAME="logs";

    public static void main(String[] args) throws Exception{
        Channel channel = RabbitmqUtil.getChannel();
        channel.exchangeDeclare(EXCHANGE_NAME,"fanout");

        Scanner scanner = new Scanner(System.in);

        while (scanner.hasNext()){
            String message = scanner.next();
            channel.basicPublish(EXCHANGE_NAME,"",null,message.getBytes("UTF-8"));
            System.out.println("生产者发送消息:"+message);
        }

    }
}
