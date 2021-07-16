package com.sxr.study.es.api;

import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.builder.SearchSourceBuilder;

import java.io.IOException;

/**
 * @author sxr
 * @date 2021/7/16 7:58 下午
 */
public class Operation {
    private static RestHighLevelClient client = ConnectEsUtil.getClient();

    public void query(){
        SearchRequest request = new SearchRequest("movies");

        SearchSourceBuilder builder = new SearchSourceBuilder();

        builder.query(QueryBuilders.termQuery("year",1962));

        request.source(builder);

        try {
            SearchResponse response = client.search(request, RequestOptions.DEFAULT);
            SearchHits hits = response.getHits();
            hits.forEach(System.out::println);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        final Operation operation = new Operation();
        operation.query();
    }
}
