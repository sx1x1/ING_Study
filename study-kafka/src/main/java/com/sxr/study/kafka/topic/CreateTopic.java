package com.sxr.study.kafka.topic;

import org.apache.kafka.clients.admin.AdminClient;
import org.apache.kafka.clients.admin.CreateTopicsResult;
import org.apache.kafka.clients.admin.KafkaAdminClient;
import org.apache.kafka.clients.admin.NewTopic;

import java.util.Collections;
import java.util.Properties;

/**
 * @author sxr
 * @date 2021/8/20 3:38 下午
 */
public class CreateTopic {
    public static void main(String[] args) {
        NewTopic topic = new NewTopic("sxr", 1, (short) 1);
        Properties properties = new Properties();
        properties.put("bootstrap.servers", "localhost:9092");
        AdminClient client = KafkaAdminClient.create(properties);
        CreateTopicsResult result = client.createTopics(Collections.singletonList(topic));
        System.out.println(result);
    }
}
