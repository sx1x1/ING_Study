package com.sxr.study.es.api;

import lombok.SneakyThrows;
import org.elasticsearch.action.admin.indices.delete.DeleteIndexRequest;
import org.elasticsearch.action.support.master.AcknowledgedResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.indices.CreateIndexRequest;
import org.elasticsearch.client.indices.CreateIndexResponse;
import org.elasticsearch.client.indices.GetIndexRequest;
import org.elasticsearch.client.indices.GetIndexResponse;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Arrays;

/**
 * @author sxr
 * @date 2021/7/18 2:36 下午
 */
@Service
public class IndexOperation {
    @Resource
    private RestHighLevelClient client;

    @SneakyThrows
    public void creatIndex() {
        // 创建索引请求
        CreateIndexRequest request = new CreateIndexRequest("users");

        // 客户端索引创建操作
        CreateIndexResponse response = client.indices().create(request, RequestOptions.DEFAULT);

        // 输出
        System.out.println(response.index());
    }

    @SneakyThrows
    public void getIndex() {
        // 获取索引请求
        GetIndexRequest request = new GetIndexRequest("movies");

        // 客户端索引获取操作
        GetIndexResponse response = client.indices().get(request, RequestOptions.DEFAULT);

        // 输出
        System.out.println(Arrays.stream(response.getIndices()).toString());
    }

    @SneakyThrows
    public void deleteIndex() {
        // 删除索引请求
        DeleteIndexRequest request = new DeleteIndexRequest("users");

        // 客户端索引删除操作
        AcknowledgedResponse response = client.indices().delete(request, RequestOptions.DEFAULT);

        System.out.println(response.isAcknowledged());
    }
}
