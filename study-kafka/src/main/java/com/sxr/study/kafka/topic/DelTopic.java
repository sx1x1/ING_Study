package com.sxr.study.kafka.topic;

import org.apache.kafka.clients.admin.AdminClient;
import org.apache.kafka.clients.admin.DeleteTopicsResult;
import org.apache.kafka.clients.admin.KafkaAdminClient;

import java.util.Collections;
import java.util.Properties;

/**
 * @author sxr
 * @date 2021/8/20 4:45 下午
 */
public class DelTopic {
    public static void main(String[] args) {
        Properties properties = new Properties();
        properties.put("bootstrap.servers", "localhost:9092");
        AdminClient client = KafkaAdminClient.create(properties);
        DeleteTopicsResult result = client.deleteTopics(Collections.singletonList("sxr"));
        System.out.println(result.values());
        System.out.println("-----------------");
    }
}
