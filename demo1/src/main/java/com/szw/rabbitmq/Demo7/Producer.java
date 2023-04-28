package com.szw.rabbitmq.Demo7;
import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.szw.rabbitmq.Utils.RabbitmqUtil;

/**
 * @Author Szw 2001
 * @Date 2023/4/26 21:55
 * @Slogn 致未来的你！
 */
//死信队列——生产者代码
public class Producer {

    //普通交换机
    public static final String NORMAL_EXCHANGE="normal_exchange";

    public static void main(String[] args) throws Exception {
        Channel channel = RabbitmqUtil.getChannel();
      /*  //死信消息 设置ttl时间 单位是ms 10000ms==10s
        AMQP.BasicProperties properties = new AMQP.BasicProperties()
                .builder().expiration("10000").build();*/
        for (int i = 1; i < 11; i++) {
            String message = "info"+i;//info1.....info10
            channel.basicPublish(NORMAL_EXCHANGE,"zhangsan",null,message.getBytes());

        }
    }
}
