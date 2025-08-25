package com.backend.fitgym.services;

import com.backend.fitgym.dto.PaymentDTO;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.support.WriteRequest;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
public class PaymentSearchService {
    private static final String INDEX = "payments";

    @Autowired
    private RestHighLevelClient client;

    @Autowired
    private ObjectMapper objectMapper;

    public void indexPayment(PaymentDTO member) throws IOException {
        IndexRequest request = new IndexRequest(INDEX)
                .id(member.getId().toString())
                .source(objectMapper.writeValueAsString(member), XContentType.JSON)
                .setRefreshPolicy(WriteRequest.RefreshPolicy.IMMEDIATE);

        client.index(request, RequestOptions.DEFAULT);
    }

    public List<PaymentDTO> searchByStatus(String status) throws IOException {
        SearchRequest request = new SearchRequest(INDEX);
        SearchSourceBuilder builder = new SearchSourceBuilder();
        builder.query(QueryBuilders.matchQuery("status", status));
        request.source(builder);

        SearchResponse response = client.search(request, RequestOptions.DEFAULT);

        List<PaymentDTO> results = new ArrayList<>();
        response.getHits().forEach(hit -> {
            try {
                results.add(objectMapper.readValue(hit.getSourceAsString(), PaymentDTO.class));
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        return results;
    }
}
