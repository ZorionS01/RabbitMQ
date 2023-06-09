package com.szw.rabbitmq.Demo6;

import com.rabbitmq.client.Channel;
import com.szw.rabbitmq.Utils.RabbitmqUtil;

import java.util.HashMap;
import java.util.Map;

/**
 * @Author Szw 2001
 * @Date 2023/4/24 21:48
 * @Slogn 致未来的你！
 */
//生产者
public class EmitLogTopic {
    //交换机名称
    public static final String EXCHANGE_NAME="topic+log";
    public static void main(String[] args) throws Exception {
        Channel channel = RabbitmqUtil.getChannel();

        Map<String, String> bindingKeyMap = new HashMap<>();
        bindingKeyMap.put("quick.orange.rabbit","被队列 Q1Q2 接收到");
        bindingKeyMap.put("lazy.orange.elephant","被队列 Q1Q2 接收到");
        bindingKeyMap.put("quick.orange.fox","被队列 Q1 接收到");
        bindingKeyMap.put("lazy.brown.fox","被队列 Q2 接收到");
        bindingKeyMap.put("lazy.pink.rabbit","虽然满足两个绑定但只被队列 Q2 接收一次");
        bindingKeyMap.put("quick.brown.fox","不匹配任何绑定不会被任何队列接收到会被丢弃");
        bindingKeyMap.put("quick.orange.male.rabbit","是四个单词不匹配任何绑定会被丢弃");
        bindingKeyMap.put("lazy.orange.male.rabbit","是四个单词但匹配 Q2");

        for (Map.Entry<String, String> bindingKeyEntry: bindingKeyMap.entrySet()){
            String bindingKey = bindingKeyEntry.getKey();
            String message = bindingKeyEntry.getValue();
            channel.basicPublish(EXCHANGE_NAME,bindingKey, null,
                    message.getBytes("UTF-8"));
            System.out.println("生产者发出消息" + message);
        }
    }
}
