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
 * @date 2021/7/16 7:58 ??????
 */
@Service
public class DocOperation {
    // ???????????????
    @Resource
    private RestHighLevelClient client;

    @SneakyThrows
    public void indexDoc() {
        // ????????????????????????
        IndexRequest request = new IndexRequest("movies", "movie", "12");

        // ????????????????????????????????????????????????
        String[] genres = {"sxr", "dyt"};
        Movie movie = new Movie("happy and lucky", "sxr", 1972, genres);

        // ????????????map
        /*
        Map<String, Object> map = new HashMap<>();
        map.put("user","sxr");
        map.put("created",new Date());
        request.source(map);
        */

        // ????????????XContentBuilder
        /*
        XContentBuilder builder = XContentFactory.jsonBuilder()
                .startObject()
                    .field("user", "sxr")
                    .field("created", new Date())
                .endObject();
        request.source(builder);
        */

        // ????????????
        // request.id("7");
        request.source(JSON.toJSONString(movie), XContentType.JSON);
        request.routing("id");
        request.timeout(TimeValue.timeValueSeconds(1));
        // request.version(1);
        request.opType(DocWriteRequest.OpType.CREATE);

        // ??????????????????????????????????????????
        IndexResponse response = client.index(request, RequestOptions.DEFAULT);

        // ??????????????????????????????????????????
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
        // ??????????????????
        GetRequest request = new GetRequest("movies", id);

        // ??????
        /*
        request.routing();
        request.version();
        */

        // ????????????????????????????????????
        /*
        String[] includes = {"field1", "filed2"};
        String[] excludes = {"field3", "filed4"};
        FetchSourceContext fetchSourceContext = new FetchSourceContext(true, includes, excludes);
        request.fetchSourceContext(fetchSourceContext);
        */

        // ??????????????????????????????????????????
        GetResponse response = client.get(request, RequestOptions.DEFAULT);

        // ??????????????????????????????????????????
        ActionListener<GetResponse> listener = new ActionListener<GetResponse>() {
            @Override
            public void onResponse(GetResponse response) {
                // ???????????????
                System.out.println("response.getSourceAsMap() = " + response.getSourceAsMap());
                System.out.println("response.getSourceAsString() = " + response.getSourceAsString());
                System.out.println("response.getSourceAsBytes() = " + response.getSourceAsBytes());

                // ??????
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
        // ????????????????????????
        UpdateRequest request = new UpdateRequest("movies", "7");

        // ??????????????????
        String[] str = {"aaa", "bbb"};
        Movie movie = new Movie("yue guang", "sxr", 1962, str);

        // ????????????
        // request.index("movies");
        // request.id("7");
        request.doc(JSON.toJSONString(movie), XContentType.JSON);

        // ???????????????????????????
        UpdateResponse response = client.update(request, RequestOptions.DEFAULT);

        // ??????
        System.out.println(response.status());
        getDoc("7");
    }

    @SneakyThrows
    public void deleteDoc() {
        // ??????????????????
        DeleteRequest request = new DeleteRequest("movies", "7");

        // ??????????????????
        // request.index("movies");
        // request.id("7");

        // ???????????????????????????
        DeleteResponse response = client.delete(request, RequestOptions.DEFAULT);

        // ??????
        System.out.println(response);
    }

    @SneakyThrows
    public void bulkDoc() {
        // ??????????????????
        BulkRequest request = new BulkRequest();

        // ????????????
        List<Movie> movies = new ArrayList<>();
        movies.add(new Movie("a", "b", 1962, new String[]{"a", "b"}));
        movies.add(new Movie("b", "b", 1962, new String[]{"a", "b"}));
        movies.add(new Movie("c", "b", 1962, new String[]{"a", "b"}));

        // ??????????????????
        for (int i = 0; i < movies.size(); i++) {
            request.add(new IndexRequest("movies")
                    .id(String.valueOf(i + 8))
                    .source(JSON.toJSONString(movies.get(i)), XContentType.JSON));
        }

        // ???????????????????????????
        BulkResponse response = client.bulk(request, RequestOptions.DEFAULT);

        // ??????
        System.out.println(response.hasFailures());
    }

    // ??????
    @SneakyThrows
    public void searchTerm() {
        // ?????????????????????????????????????????????
        SearchRequest request = new SearchRequest("movies");

        // ???????????????
        SearchSourceBuilder builder = new SearchSourceBuilder();

        // ???????????????
        QueryBuilder queryBuilder = QueryBuilders.termQuery("year", 1972);
        builder.query(queryBuilder);

        // ??????????????????????????????
        builder.from(0);
        builder.size(10);
        builder.timeout(TimeValue.timeValueSeconds(1));
        builder.fetchSource(false);
        builder.sort(new ScoreSortBuilder().order(SortOrder.DESC));

        // ????????????????????????????????????
        request.source(builder);

        // ???????????????
        SearchResponse response = client.search(request, RequestOptions.DEFAULT);

        // ??????????????????
        SearchHits hits = response.getHits();

        // ??????
        hits.forEach(System.out::println);
    }

    @SneakyThrows
    public void searchMatch() {
        // ????????????????????????
        SearchRequest request = new SearchRequest("movies");

        // ???????????????
        SearchSourceBuilder builder = new SearchSourceBuilder();

        // ???????????????
        QueryBuilder queryBuilder = QueryBuilders.matchQuery("year", 1962);

        // ???????????????
        HighlightBuilder highlightBuilder = new HighlightBuilder();
        // HighlightBuilder.Field field = new HighlightBuilder.Field("year");
        // HighlightBuilder highlight = highlightBuilder.field(field);
        HighlightBuilder highlight = highlightBuilder.field("year");

        builder.query(queryBuilder);
        builder.highlighter(highlight);

        request.source(builder);

        // ???????????????????????????
        SearchResponse response = client.search(request, RequestOptions.DEFAULT);

        // ??????
        SearchHits hits = response.getHits();
        hits.forEach(System.out::println);
    }

    @SneakyThrows
    public void searchTermSuggest() {
        // ??????????????????
        SearchRequest request = new SearchRequest("movies");

        // ???????????????
        SearchSourceBuilder builder = new SearchSourceBuilder();

        // ???????????????
        QueryBuilder query = QueryBuilders.matchQuery("title", "kill");
        builder.query(query);

        // ???????????????
        SuggestBuilder sugBuilder = new SuggestBuilder();
        TermSuggestionBuilder suggestionBuilder = SuggestBuilders.termSuggestion("title").text("killer bil");
        sugBuilder.addSuggestion("suggest_title", suggestionBuilder);
        builder.suggest(sugBuilder);

        request.source(builder);

        // ???????????????????????????
        SearchResponse response = client.search(request, RequestOptions.DEFAULT);

        // ??????
        Suggest suggest = response.getSuggest();
        final SearchHits hits = response.getHits();
        hits.forEach(System.out::println);
        System.out.println("suggest = " + suggest);
    }

    @SneakyThrows
    public void searchPhraseSuggest() {
        // ??????????????????
        SearchRequest request = new SearchRequest("movies");

        // ???????????????
        SearchSourceBuilder builder = new SearchSourceBuilder();

        // ???????????????
        QueryBuilder query = QueryBuilders.matchQuery("title", "kill");
        builder.query(query);

        // ???????????????
        SuggestBuilder sugBuilder = new SuggestBuilder();
        PhraseSuggestionBuilder suggestionBuilder = SuggestBuilders.phraseSuggestion("title").text("killer sxr bill");
        sugBuilder.addSuggestion("suggest_dir", suggestionBuilder);
        builder.suggest(sugBuilder);

        request.source(builder);

        // ???????????????????????????
        SearchResponse response = client.search(request, RequestOptions.DEFAULT);

        // ??????
        Suggest suggest = response.getSuggest();
        final SearchHits hits = response.getHits();
        hits.forEach(System.out::println);
        System.out.println("suggest = " + suggest);
    }

    @SneakyThrows
    public void searchCompletionSuggester() {
        // ??????????????????
        SearchRequest request = new SearchRequest("users");

        // ???????????????
        SearchSourceBuilder builder = new SearchSourceBuilder();

        // ???????????????
        SuggestBuilder sugBuilder = new SuggestBuilder();
        // ????????????????????????????????????
        CompletionSuggestionBuilder suggestionBuilder = SuggestBuilders
                .completionSuggestion("name_suggest")
                .prefix("sun")
                .skipDuplicates(true);
        sugBuilder.addSuggestion("name_sug",suggestionBuilder);

        builder.suggest(sugBuilder);
        request.source(builder);

        // ???????????????????????????
        SearchResponse response = client.search(request, RequestOptions.DEFAULT);

        // ??????
        Suggest suggest = response.getSuggest();
        final SearchHits hits = response.getHits();
        hits.forEach(System.out::println);
        System.out.println("suggest = " + suggest);
    }

}
