package com.sxr.study.es.api;

import org.apache.http.HttpHost;
import org.elasticsearch.client.NodeSelector;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestClientBuilder;
import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author sxr
 * @date 2021/7/17 12:32 下午
 */
@Configuration
public class EsConfig {

    @Bean
    public RestHighLevelClient getClient() {
        HttpHost httpHost = new HttpHost("localhost", 9200, "http");
        RestClientBuilder restClientBuilder = RestClient.builder(httpHost);
        restClientBuilder.setNodeSelector(NodeSelector.SKIP_DEDICATED_MASTERS);
        return new RestHighLevelClient(restClientBuilder);
    }
}
