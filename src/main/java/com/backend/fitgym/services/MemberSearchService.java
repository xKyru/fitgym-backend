package com.backend.fitgym.services;

import com.backend.fitgym.dto.MemberDTO;
import org.elasticsearch.action.delete.DeleteRequest;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.index.query.MultiMatchQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.elasticsearch.search.aggregations.bucket.terms.Terms;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class MemberSearchService {

    @Autowired
    private RestHighLevelClient elasticClient;

    @Autowired
    private ObjectMapper objectMapper;

    private static final String INDEX = "members";

    public void indexMember(MemberDTO member) throws IOException {
        IndexRequest request = new IndexRequest(INDEX)
                .id(String.valueOf(member.getId()))
                .source(objectMapper.writeValueAsString(member), XContentType.JSON);

        elasticClient.index(request, RequestOptions.DEFAULT);
    }

    public void deleteMemberById(Long id) throws IOException {
        boolean exists = elasticClient.exists(
                new org.elasticsearch.action.get.GetRequest(INDEX, String.valueOf(id)),
                RequestOptions.DEFAULT
        );

        if(!exists){
            System.err.println("Documento con id " + id + " no existe en Elasticsearch");
            return;
        }

        DeleteRequest request = new DeleteRequest(INDEX, String.valueOf(id));
        elasticClient.delete(request, RequestOptions.DEFAULT);
    }

    public List<MemberDTO> search(String q) throws IOException {
        SearchRequest searchRequest = new SearchRequest(INDEX);
        SearchSourceBuilder sourceBuilder = new SearchSourceBuilder();

        sourceBuilder.query(QueryBuilders.boolQuery()
                .should(QueryBuilders.multiMatchQuery(q)
                        .field("email", 3.0f) // Mayor peso para email
                        .field("email._2gram")
                        .field("email._3gram")
                        .field("nombre", 2.0f)
                        .field("apellidos", 2.0f)
                        .type(MultiMatchQueryBuilder.Type.BOOL_PREFIX) // Importante para search-as-you-type
                )
        );

        searchRequest.source(sourceBuilder);

        SearchResponse response = elasticClient.search(searchRequest, RequestOptions.DEFAULT);

        List<MemberDTO> results = new ArrayList<>();
        response.getHits().forEach(hit -> {
            try {
                results.add(objectMapper.readValue(hit.getSourceAsString(), MemberDTO.class));
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        return results;
    }

    public List<MemberDTO> getAllMembers() throws IOException {
        SearchRequest searchRequest = new SearchRequest(INDEX);
        SearchSourceBuilder sourceBuilder = new SearchSourceBuilder();
        sourceBuilder.query(QueryBuilders.matchAllQuery());
        searchRequest.source(sourceBuilder);

        SearchResponse response = elasticClient.search(searchRequest, RequestOptions.DEFAULT);

        List<MemberDTO> results = new ArrayList<>();
        response.getHits().forEach(hit -> {
            try {
                results.add(objectMapper.readValue(hit.getSourceAsString(), MemberDTO.class));
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        return results;
    }

    public Map<String, Long> getMemberFacets() throws IOException {
        SearchRequest searchRequest = new SearchRequest(INDEX);
        SearchSourceBuilder sourceBuilder = new SearchSourceBuilder();

        sourceBuilder.size(0);

        sourceBuilder.aggregation(
                AggregationBuilders.terms("estado_facet")
                        .field("estado.keyword")
        );

        searchRequest.source(sourceBuilder);

        SearchResponse response = elasticClient.search(searchRequest, RequestOptions.DEFAULT);

        Map<String, Long> facets = new HashMap<>();

        // Total de registros en el índice
        facets.put("total", response.getHits().getTotalHits().value);

        // Totales por estado
        Terms estadoFacet = response.getAggregations().get("estado_facet");
        for (Terms.Bucket bucket : estadoFacet.getBuckets()) {
            String key = bucket.getKeyAsString().toLowerCase();
            facets.put(key, bucket.getDocCount());
        }

        return facets;
    }



}
