package com.sxr.study.kafka.api;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;

import java.util.Collections;
import java.util.Properties;

/**
 * @author sxr
 * @date 2021/8/20 4:06 下午
 */
public class Consumer {
    public static void main(String[] args) {
        Properties props = new Properties();
        props.put("bootstrap.servers", "localhost:9092");
        // 设置消费者组名
        props.put("group.id", "test1");
        // 重新消费
        props.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");
        // 开启自动提交
        props.put("enable.auto.commit", "true");
        // 自动提交 offset 的时间间隔
        props.put("auto.commit.interval.ms", "1000");
        props.put("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        props.put("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");

        // 创建消费者
        KafkaConsumer<String, String> consumer = new KafkaConsumer<>(props);
        // 订阅主题
        consumer.subscribe(Collections.singletonList("sxr"));
        while (true) {
            // 拉取数据
            ConsumerRecords<String, String> records = consumer.poll(100);
            for (ConsumerRecord<String, String> record : records) {
                System.out.println("------>" + record.offset() + record.key() + record.value());
            }
        }
    }
}
