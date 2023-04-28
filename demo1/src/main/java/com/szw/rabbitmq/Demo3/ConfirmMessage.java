package com.szw.rabbitmq.Demo3;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.ConfirmCallback;
import com.sun.org.glassfish.gmbal.Description;
import com.szw.rabbitmq.Utils.RabbitmqUtil;

import java.util.UUID;
import java.util.concurrent.ConcurrentNavigableMap;
import java.util.concurrent.ConcurrentSkipListMap;

/**
 * @Author Szw 2001
 * @Date 2023/4/23 19:59
 * @Slogn 致未来的你！
 */
//发布确认模式
    /*
    1.单个确认
    2.批量确认
    3.异步批量确认
     **/

public class ConfirmMessage {

    //批量发消息的个数
    public static final int MESSAGE_COUNT=1000;

    public static void main(String[] args) throws Exception {
        //1.单个确认
//        ConfirmMessage.publishMessageIndividually();
        //2.批量确认
//        ConfirmMessage.publishMessageBatch();
        //3.异步批量确认
        ConfirmMessage.publishMessageAsync();
    }

    //单个确认
    public static void publishMessageIndividually() throws Exception{
        Channel channel = RabbitmqUtil.getChannel();
        //队列的声明
        String queueName = UUID.randomUUID().toString();
        channel.queueDeclare(queueName,true,false,false,null);
        //开始发布确认
        channel.confirmSelect();
        //开启时间
        long begin = System.currentTimeMillis();

        //批量发消息
        for (int i = 0; i < MESSAGE_COUNT ; i++) {
            String message = i+"";
            channel.basicPublish("",queueName,null,message.getBytes());
            //单个消息马上发布确认
            boolean flag = channel.waitForConfirms();
            if (flag){
                System.out.println("消息发送成功！");
            }
        }
        //结束时间
        long end = System.currentTimeMillis();
        System.out.println("发布"+MESSAGE_COUNT+"条单独确认消息,耗时"+(end - begin)+"ms");

    }

    //批量发送
    public static void publishMessageBatch() throws Exception{
        Channel channel = RabbitmqUtil.getChannel();
        String queueName = UUID.randomUUID().toString();
        channel.queueDeclare(queueName,true,false,false,null);
        channel.confirmSelect();
        long begin = System.currentTimeMillis();
        //批量确认消息大小
        int batchSize = 100;
        //未确认消息个数

        //批量发送消息
        for (int i = 0; i < MESSAGE_COUNT; i++) {
            String message = i + "";
            channel.basicPublish("",queueName,null,message.getBytes());
            //判断达到100条消息的时候 批量确认一次
            if (i%batchSize==0){
                //发布确认
                channel.waitForConfirms();
            }
        }

        long end = System.currentTimeMillis();
        System.out.println("发布"+MESSAGE_COUNT+"条批量确认消息,耗时"+(end-begin)+"ms");
    }

    //异步发布确认
    public static void publishMessageAsync() throws Exception{
        Channel channel = RabbitmqUtil.getChannel();
        String queueName = UUID.randomUUID().toString();
        channel.queueDeclare(queueName,true,false,false,null);
        //开始发布确认
        channel.confirmSelect();
        /*
        线程安全有序的一个哈希表 适用于高并发的情况下
        1.轻松的将序号与消息关联 Map
        2.轻松批量删除 只要给到序号
        3.支持高并发（多线程）
         **/
        ConcurrentSkipListMap<Long,String> skipListMap = new ConcurrentSkipListMap<>();

        long begin = System.currentTimeMillis();

        //消息监听器 监听那些消息成功 哪些消息失败了
        //消息确认成功 回调函数
        ConfirmCallback confirmCallback = (deliveryTag,multiple) ->{
            if (multiple){
                ConcurrentNavigableMap<Long, String> confirmed = skipListMap.headMap(deliveryTag);
                confirmed.clear();
            }else {
                skipListMap.remove(deliveryTag);
            }
            //删除已经确认的消息
            System.out.println("确认的消息:"+deliveryTag);
        };

        //消息确认失败 回调函数
        /*
         1.消息的标记
         2.是否为批量处理
         **/

        ConfirmCallback nackCallback = (deliveryTag,multiple) ->{
            //打印一下未确认的消息
            String s = skipListMap.get(deliveryTag);
            System.out.println("未确认的消息是:"+s+"-------未确认的消息tag:"+deliveryTag);
            System.out.println("未确认的消息"+deliveryTag);
        };
        channel.addConfirmListener(confirmCallback,nackCallback);//异步通知
        for (int i = 0; i < MESSAGE_COUNT; i++) {
            String message = "消息" + i;
            channel.basicPublish("",queueName,null,message.getBytes());
            //记录下所有发送的消息
            skipListMap.put(channel.getNextPublishSeqNo(),message);
        }


        long end = System.currentTimeMillis();
        System.out.println("发布"+MESSAGE_COUNT+"条异步发布确认消息,耗时"+(end-begin)+"ms");

    }
}
