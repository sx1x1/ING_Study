package com.sxr.study.kafka.api;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;

import java.util.Properties;

/**
 * @author sxr
 * @date 2021/8/20 3:28 下午
 */
public class Producer {
    public static void main(String[] args) {
        Properties props = new Properties();
        // kafka 集群，broker-list
        props.put("bootstrap.servers", "localhost:9092");
        // ack
        props.put("acks", "all");
        // 重试次数
        props.put("retries", 1);
        // 批次大小
        props.put("batch.size", 16384);
        // 等待时间
        props.put("linger.ms", 1);
        // RecordAccumulator 缓冲区大小
        props.put("buffer.memory", 33554432);
        // 序列化器
        props.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        props.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");

        KafkaProducer<String, String> producer = new KafkaProducer<>(props);
        for (int i = 0; i < 100; i++) {
            producer.send(new ProducerRecord<>("sxr", Integer.valueOf(i).toString(), Integer.valueOf(i).toString()));
        }
        System.out.println("finish");
        // 将数据强制刷出
        producer.close();
    }
}
