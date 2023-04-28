package com.szw.rabbitmq.Utils;

/**
 * @Author Szw 2001
 * @Date 2023/4/23 18:20
 * @Slogn 致未来的你！
 */
//睡眠工具类
public class SleepUtil {
    public static void sleep(int seconds){
        try {
            Thread.sleep(1000*seconds);
        } catch (InterruptedException e) {
           Thread.currentThread().interrupt();
        }
    }
}
