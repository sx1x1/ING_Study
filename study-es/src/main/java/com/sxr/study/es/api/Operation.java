package com.sxr.study.es.api;

import lombok.SneakyThrows;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.builder.SearchSourceBuilder;

/**
 * @author sxr
 * @date 2021/7/16 7:58 下午
 */
public class Operation {
    // 获取客户端
    private static final RestHighLevelClient client = ConnectEsUtil.getClient();

    // 查询
    @SneakyThrows
    public void query() {
        // 搜索请求构建，设置请求路径
        SearchRequest request = new SearchRequest("movies");

        // 请求体构建
        SearchSourceBuilder builder = new SearchSourceBuilder();

        // 查询体构建
        QueryBuilder queryBuilder = QueryBuilders.termQuery("year", 1962);
        builder.query(queryBuilder);

        // 搜索请求构建，设置请求体
        request.source(builder);

        // 客户端查询
        SearchResponse response = client.search(request, RequestOptions.DEFAULT);

        // 获取返回结果
        SearchHits hits = response.getHits();

        // 输出
        hits.forEach(System.out::println);
    }

    @SneakyThrows
    public static void main(String[] args) {
        Operation operation = new Operation();
        operation.query();
    }
}
