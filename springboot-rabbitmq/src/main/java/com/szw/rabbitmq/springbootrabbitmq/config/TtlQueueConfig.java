package com.szw.rabbitmq.springbootrabbitmq.config;

import org.springframework.amqp.core.*;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

/**
 * @Author Szw 2001
 * @Date 2023/4/28 14:22
 * @Slogn 致未来的你！
 */
@Configuration
public class TtlQueueConfig {
    //普通交换机
    public static final String X_EXCHANGE="X";
    //普通队列A
    public static final String QUEUE_A="QA";
    //普通队列B
    public static final String QUEUE_B="QB";
    //死信交换机
    public static final String Y_DEAD_LETTER_EXCHANGE="Y";
    //死信队列
    public static final String DEAD_LETTER_QUEUE="QD";
    //普通队列
    public static final String QUEUE_C="QC";

    //声明QC
    @Bean("queueC")
    public Queue queueC(){
        Map<String,Object> map = new HashMap<>();
        //设置死信交换机
        map.put("x-dead-letter-exchange", Y_DEAD_LETTER_EXCHANGE);
        //设置死信路由
        map.put("x-dead-letter-routing-key","YD");
        //设置TTL
        return QueueBuilder.durable(QUEUE_C).withArguments(map).build();
    }

    @Bean
    public Binding queueCBinding(@Qualifier("queueC") Queue queueC,
                                 @Qualifier("xExchange") DirectExchange xExchange){
        return BindingBuilder.bind(queueC).to(xExchange).with("XC");
    }


    //声明xExchange
    @Bean("xExchange")//别名
    public DirectExchange xExchange(){
        return new DirectExchange(X_EXCHANGE);
    }

    //声明yExchange
    @Bean("yExchange")//别名
    public DirectExchange yExchange(){
        return new DirectExchange(Y_DEAD_LETTER_EXCHANGE);
    }

    //声明队列 ttl 10s
    @Bean("queueA")
    public Queue queueA(){
        Map<String,Object> map = new HashMap<>();
        //设置死信交换机
        map.put("x-dead-letter-exchange", Y_DEAD_LETTER_EXCHANGE);
        //设置死信路由
        map.put("x-dead-letter-routing-key","YD");
        //设置TTL
        map.put("x-message-ttl", 10000);
        return QueueBuilder.durable(QUEUE_A).withArguments(map).build();
    }

    //声明队列 ttl 10s
    @Bean("queueB")
    public Queue queueB(){
        Map<String,Object> map = new HashMap<>();
        //设置死信交换机
        map.put("x-dead-letter-exchange", Y_DEAD_LETTER_EXCHANGE);
        //设置死信路由
        map.put("x-dead-letter-routing-key","YD");
        //设置TTL
        map.put("x-message-ttl", 40000);
        return QueueBuilder.durable(QUEUE_B).withArguments(map).build();
    }

    //死信队列
    @Bean("queueD")
    public Queue queueD(){
         return QueueBuilder.durable(DEAD_LETTER_QUEUE).build();
    }

    //绑定
    @Bean
    public Binding queueABindingX(@Qualifier("queueA") Queue queueA,
                                  @Qualifier("xExchange") DirectExchange xExchange){
        return BindingBuilder.bind(queueA).to(xExchange).with("XA");
    }

    //绑定
    @Bean
    public Binding queuebBindingX(@Qualifier("queueB") Queue queueB,
                                  @Qualifier("xExchange") DirectExchange xExchange){
        return BindingBuilder.bind(queueB).to(xExchange).with("XB");
    }

    //绑定
    @Bean
    public Binding queueDBindingX(@Qualifier("queueD") Queue queueD,
                                  @Qualifier("yExchange") DirectExchange yExchange){
        return BindingBuilder.bind(queueD).to(yExchange).with("YD");
    }

}
