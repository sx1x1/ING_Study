package com.sxr.study.es.api;

import org.apache.http.HttpHost;
import org.elasticsearch.client.NodeSelector;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestClientBuilder;
import org.elasticsearch.client.RestHighLevelClient;

/**
 * @author sxr
 * @date 2021/7/16 8:24 下午
 */
public class ConnectEsUtil {
    private static RestHighLevelClient client;

    static {
        try {
            HttpHost httpHost = new HttpHost("localhost", 9200, "http");
            RestClientBuilder restClientBuilder = RestClient.builder(httpHost);
            restClientBuilder.setNodeSelector(NodeSelector.SKIP_DEDICATED_MASTERS);
            client = new RestHighLevelClient(restClientBuilder);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static RestHighLevelClient getClient() {
        return client;
    }

    private ConnectEsUtil(){

    }
}
