package com.sxr.study.es.api;

import com.alibaba.fastjson.JSON;
import lombok.SneakyThrows;
import org.elasticsearch.action.ActionListener;
import org.elasticsearch.action.DocWriteRequest;
import org.elasticsearch.action.bulk.BulkRequest;
import org.elasticsearch.action.bulk.BulkResponse;
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
import org.elasticsearch.common.unit.TimeValue;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.elasticsearch.common.xcontent.XContentFactory;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.index.query.MatchAllQueryBuilder;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.fetch.subphase.FetchSourceContext;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightBuilder;
import org.elasticsearch.search.sort.ScoreSortBuilder;
import org.elasticsearch.search.sort.SortOrder;
import org.elasticsearch.search.suggest.Suggest;
import org.elasticsearch.search.suggest.SuggestBuilder;
import org.elasticsearch.search.suggest.SuggestBuilders;
import org.elasticsearch.search.suggest.SuggestionBuilder;
import org.elasticsearch.search.suggest.completion.CompletionSuggestionBuilder;
import org.elasticsearch.search.suggest.phrase.PhraseSuggestionBuilder;
import org.elasticsearch.search.suggest.term.TermSuggestionBuilder;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;

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
        IndexRequest request = new IndexRequest("movies", "movie", "12");

        // 方法一：准备对象实体（推荐使用）
        String[] genres = {"sxr", "dyt"};
        Movie movie = new Movie("happy and lucky", "sxr", 1972, genres);

        // 方法二：map
        /*
        Map<String, Object> map = new HashMap<>();
        map.put("user","sxr");
        map.put("created",new Date());
        request.source(map);
        */

        // 方法三：XContentBuilder
        /*
        XContentBuilder builder = XContentFactory.jsonBuilder()
                .startObject()
                    .field("user", "sxr")
                    .field("created", new Date())
                .endObject();
        request.source(builder);
        */

        // 设置请求
        // request.id("7");
        request.source(JSON.toJSONString(movie), XContentType.JSON);
        request.routing("id");
        request.timeout(TimeValue.timeValueSeconds(1));
        // request.version(1);
        request.opType(DocWriteRequest.OpType.CREATE);

        // 客户端文档创建操作，同步执行
        IndexResponse response = client.index(request, RequestOptions.DEFAULT);

        // 客户端文档创建操作，异步执行
        ActionListener<IndexResponse> listener = new ActionListener<IndexResponse>() {
            @Override
            public void onResponse(IndexResponse response) {
                System.out.println("response.getId() = " + response.getId());
                System.out.println("response.getIndex() = " + response.getIndex());
                System.out.println("response.getVersion() = " + response.getVersion());
                System.out.println("response.getResult() = " + response.getResult());
            }

            @Override
            public void onFailure(Exception e) {
                System.out.println("e = " + e);
            }
        };

        client.indexAsync(request, RequestOptions.DEFAULT, listener);
    }

    @SneakyThrows
    public void getDoc(String id) {
        // 创建查询请求
        GetRequest request = new GetRequest("movies", id);

        // 设置
        /*
        request.routing();
        request.version();
        */

        // 过滤请求结果不需要的属性
        /*
        String[] includes = {"field1", "filed2"};
        String[] excludes = {"field3", "filed4"};
        FetchSourceContext fetchSourceContext = new FetchSourceContext(true, includes, excludes);
        request.fetchSourceContext(fetchSourceContext);
        */

        // 客户端文档查询操作，同步执行
        GetResponse response = client.get(request, RequestOptions.DEFAULT);

        // 客户端文档查询操作，异步执行
        ActionListener<GetResponse> listener = new ActionListener<GetResponse>() {
            @Override
            public void onResponse(GetResponse response) {
                // 结果后处理
                System.out.println("response.getSourceAsMap() = " + response.getSourceAsMap());
                System.out.println("response.getSourceAsString() = " + response.getSourceAsString());
                System.out.println("response.getSourceAsBytes() = " + response.getSourceAsBytes());

                // 输出
                System.out.println(response.getVersion());
                System.out.println(response.getSource());
            }

            @Override
            public void onFailure(Exception e) {
                System.out.println("e = " + e);
            }
        };

        client.getAsync(request, RequestOptions.DEFAULT, listener);

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

    @SneakyThrows
    public void bulkDoc() {
        // 创建批量请求
        BulkRequest request = new BulkRequest();

        // 准备数据
        List<Movie> movies = new ArrayList<>();
        movies.add(new Movie("a", "b", 1962, new String[]{"a", "b"}));
        movies.add(new Movie("b", "b", 1962, new String[]{"a", "b"}));
        movies.add(new Movie("c", "b", 1962, new String[]{"a", "b"}));

        // 添加批量请求
        for (int i = 0; i < movies.size(); i++) {
            request.add(new IndexRequest("movies")
                    .id(String.valueOf(i + 8))
                    .source(JSON.toJSONString(movies.get(i)), XContentType.JSON));
        }

        // 客户端文档批量操作
        BulkResponse response = client.bulk(request, RequestOptions.DEFAULT);

        // 输出
        System.out.println(response.hasFailures());
    }

    // 查询
    @SneakyThrows
    public void searchTerm() {
        // 文档搜索请求构建，设置请求路径
        SearchRequest request = new SearchRequest("movies");

        // 请求体构建
        SearchSourceBuilder builder = new SearchSourceBuilder();

        // 查询体构建
        QueryBuilder queryBuilder = QueryBuilders.termQuery("year", 1972);
        builder.query(queryBuilder);

        // 设置请求体分页，超时
        builder.from(0);
        builder.size(10);
        builder.timeout(TimeValue.timeValueSeconds(1));
        builder.fetchSource(false);
        builder.sort(new ScoreSortBuilder().order(SortOrder.DESC));

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
    public void searchMatch() {
        // 文档搜索请求构建
        SearchRequest request = new SearchRequest("movies");

        // 请求体构建
        SearchSourceBuilder builder = new SearchSourceBuilder();

        // 查询体构建
        QueryBuilder queryBuilder = QueryBuilders.matchQuery("year", 1962);

        // 高亮体构建
        HighlightBuilder highlightBuilder = new HighlightBuilder();
        // HighlightBuilder.Field field = new HighlightBuilder.Field("year");
        // HighlightBuilder highlight = highlightBuilder.field(field);
        HighlightBuilder highlight = highlightBuilder.field("year");

        builder.query(queryBuilder);
        builder.highlighter(highlight);

        request.source(builder);

        // 客户端文档搜索操作
        SearchResponse response = client.search(request, RequestOptions.DEFAULT);

        // 输出
        SearchHits hits = response.getHits();
        hits.forEach(System.out::println);
    }

    @SneakyThrows
    public void searchTermSuggest() {
        // 创建搜索请求
        SearchRequest request = new SearchRequest("movies");

        // 请求体构建
        SearchSourceBuilder builder = new SearchSourceBuilder();

        // 查询体构建
        QueryBuilder query = QueryBuilders.matchQuery("title", "kill");
        builder.query(query);

        // 建议体构建
        SuggestBuilder sugBuilder = new SuggestBuilder();
        TermSuggestionBuilder suggestionBuilder = SuggestBuilders.termSuggestion("title").text("killer bil");
        sugBuilder.addSuggestion("suggest_title", suggestionBuilder);
        builder.suggest(sugBuilder);

        request.source(builder);

        // 客户端文档搜索操作
        SearchResponse response = client.search(request, RequestOptions.DEFAULT);

        // 输出
        Suggest suggest = response.getSuggest();
        final SearchHits hits = response.getHits();
        hits.forEach(System.out::println);
        System.out.println("suggest = " + suggest);
    }

    @SneakyThrows
    public void searchPhraseSuggest() {
        // 创建搜索请求
        SearchRequest request = new SearchRequest("movies");

        // 请求体构建
        SearchSourceBuilder builder = new SearchSourceBuilder();

        // 查询体构建
        QueryBuilder query = QueryBuilders.matchQuery("title", "kill");
        builder.query(query);

        // 建议体构建
        SuggestBuilder sugBuilder = new SuggestBuilder();
        PhraseSuggestionBuilder suggestionBuilder = SuggestBuilders.phraseSuggestion("title").text("killer sxr bill");
        sugBuilder.addSuggestion("suggest_dir", suggestionBuilder);
        builder.suggest(sugBuilder);

        request.source(builder);

        // 客户端文档搜索操作
        SearchResponse response = client.search(request, RequestOptions.DEFAULT);

        // 输出
        Suggest suggest = response.getSuggest();
        final SearchHits hits = response.getHits();
        hits.forEach(System.out::println);
        System.out.println("suggest = " + suggest);
    }

    @SneakyThrows
    public void searchCompletionSuggester() {
        // 搜索请求创建
        SearchRequest request = new SearchRequest("users");

        // 请求体创建
        SearchSourceBuilder builder = new SearchSourceBuilder();

        // 建议体创建
        SuggestBuilder sugBuilder = new SuggestBuilder();
        // 根据前缀自动补全至建议值
        CompletionSuggestionBuilder suggestionBuilder = SuggestBuilders
                .completionSuggestion("name_suggest")
                .prefix("sun")
                .skipDuplicates(true);
        sugBuilder.addSuggestion("name_sug",suggestionBuilder);

        builder.suggest(sugBuilder);
        request.source(builder);

        // 客户端文档搜索操作
        SearchResponse response = client.search(request, RequestOptions.DEFAULT);

        // 输出
        Suggest suggest = response.getSuggest();
        final SearchHits hits = response.getHits();
        hits.forEach(System.out::println);
        System.out.println("suggest = " + suggest);
    }

}
