package com.szw.rabbitmq.Demo5;

import com.rabbitmq.client.Channel;
import com.szw.rabbitmq.Utils.RabbitmqUtil;

import java.util.Scanner;

/**
 * @Author Szw 2001
 * @Date 2023/4/24 21:15
 * @Slogn 致未来的你！
 */
public class DirectLogs {

    //交换机名称
    public static final String EXCHANGE_NAME="direct_log";

    public static void main(String[] args) throws Exception{
        Channel channel = RabbitmqUtil.getChannel();


        Scanner scanner = new Scanner(System.in);

        while (scanner.hasNext()){
            String message = scanner.next();
            channel.basicPublish(EXCHANGE_NAME,"error",null,message.getBytes("UTF-8"));
            System.out.println("生产者发送消息:"+message);
        }

    }
}
