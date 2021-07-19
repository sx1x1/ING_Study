package com.sxr.study.es.api;

import lombok.SneakyThrows;
import org.elasticsearch.action.ActionListener;
import org.elasticsearch.action.admin.indices.alias.Alias;
import org.elasticsearch.action.admin.indices.delete.DeleteIndexRequest;
import org.elasticsearch.action.support.master.AcknowledgedResponse;
import org.elasticsearch.client.Cancellable;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.indices.CreateIndexRequest;
import org.elasticsearch.client.indices.CreateIndexResponse;
import org.elasticsearch.client.indices.GetIndexRequest;
import org.elasticsearch.client.indices.GetIndexResponse;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.elasticsearch.common.xcontent.XContentFactory;
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

        // 设置分片数
        request.settings(Settings.builder()
                .put("index.number_of_shards", 3)
                .put("index.number_of_replicas", 2)
                //.put("analysis.analyzer.default.tokenizer", "ik_smart")
        );

        // 设置映射规则
        XContentBuilder mapping = XContentFactory.jsonBuilder()
                .startObject()
                    .startObject("properties")
                        .startObject("id")
                            .field("type", "long")
                            .field("store", true)
                        .endObject()
                        .startObject("name")
                            .field("type", "text")
                            .field("store", true)
                            //.field("analyser", "ik_smart")
                        .endObject()
                        .startObject("birth")
                            .field("type", "long")
                            .field("store", true)
                            //.field("analyser", "ik_smart")
                        .endObject()
                    .endObject()
                .endObject();
        request.mapping(mapping);

        // 设置索引别名
        request.alias(new Alias("sunxiran"));

        System.out.println("--------请二选一");
        // 客户端索引创建操作，同步执行
        CreateIndexResponse response = client.indices().create(request, RequestOptions.DEFAULT);

        // 客户端索引创建操作，异步执行
        ActionListener<CreateIndexResponse> listener = new ActionListener<CreateIndexResponse>() {

            @Override
            public void onResponse(CreateIndexResponse response) {
                boolean acknowledged = response.isAcknowledged();
                boolean shardsAcknowledged = response.isShardsAcknowledged();
                System.out.println("acknowledged = " + acknowledged);
                System.out.println("shardsAcknowledged = " + shardsAcknowledged);
            }

            @Override
            public void onFailure(Exception e) {
                System.out.println("e = " + e);
            }
        };

        Cancellable async = client.indices().createAsync(request, RequestOptions.DEFAULT, listener);

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
