package com.sxr.study.es.api;

import com.alibaba.fastjson.JSON;
import lombok.SneakyThrows;
import org.elasticsearch.action.delete.DeleteRequest;
import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.action.get.GetRequest;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.action.update.UpdateResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author sxr
 * @date 2021/7/16 7:58 下午
 */
@Service
public class DocOperation {
    // 获取客户端
    @Resource
    private RestHighLevelClient client;

    @SneakyThrows
    public void indexDoc() {
        // 创建文档请求构建
        IndexRequest request = new IndexRequest("movies", "movie", "7");

        // 准备对象实体
        String[] genres = {"sxr", "dyt"};
        Movie movie = new Movie("happy and lucky", "sxr", 1972, genres);

        // 设置请求
        // request.id("7");
        request.source(JSON.toJSONString(movie), XContentType.JSON);

        // 客户端文档创建操作
        IndexResponse response = client.index(request, RequestOptions.DEFAULT);

        // 查询
        getDoc("7");
    }

    @SneakyThrows
    public void getDoc(String id) {
        // 创建查询请求
        GetRequest request = new GetRequest("movies", id);

        // 客户端文档查询操作
        GetResponse response = client.get(request, RequestOptions.DEFAULT);

        // 输出
        System.out.println(response.getSource());
    }

    @SneakyThrows
    public void updateDoc() {
        // 创建文档更新请求
        UpdateRequest request = new UpdateRequest("movies", "7");

        // 创建对象实体
        String[] str = {"aaa", "bbb"};
        Movie movie = new Movie("yue guang", "sxr", 1962, str);

        // 设置请求
        // request.index("movies");
        // request.id("7");
        request.doc(JSON.toJSONString(movie), XContentType.JSON);

        // 客户端文档更新操作
        UpdateResponse response = client.update(request, RequestOptions.DEFAULT);

        // 输出
        System.out.println(response.status());
        getDoc("7");
    }

    @SneakyThrows
    public void deleteDoc() {
        // 创建删除请求
        DeleteRequest request = new DeleteRequest("movies", "7");

        // 设置删除请求
        // request.index("movies");
        // request.id("7");

        // 客户端文档删除操作
        DeleteResponse response = client.delete(request, RequestOptions.DEFAULT);

        // 输出
        System.out.println(response);
    }

    // 查询
    @SneakyThrows
    public void query() {
        // 搜索请求构建，设置请求路径
        SearchRequest request = new SearchRequest("movies");

        // 请求体构建
        SearchSourceBuilder builder = new SearchSourceBuilder();

        // 查询体构建
        QueryBuilder queryBuilder = QueryBuilders.termQuery("year", 1972);
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
}
