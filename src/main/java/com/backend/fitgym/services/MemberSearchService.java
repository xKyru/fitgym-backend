package com.backend.fitgym.services;

import com.backend.fitgym.dto.MemberDTO;
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
import java.util.*;

@Service
public class MemberSearchService {
    private static final String INDEX = "members";

    @Autowired
    private RestHighLevelClient client;

    @Autowired
    private ObjectMapper objectMapper;

    public void indexMember(MemberDTO member) throws IOException {
        IndexRequest request = new IndexRequest(INDEX)
                .id(member.getId().toString())
                .source(objectMapper.writeValueAsString(member), XContentType.JSON)
                .setRefreshPolicy(WriteRequest.RefreshPolicy.IMMEDIATE);

        client.index(request, RequestOptions.DEFAULT);
    }

    /**
     * 🔍 Búsqueda combinada por nombre o email
     */
    public List<MemberDTO> search(String value) throws IOException {
        SearchRequest request = new SearchRequest(INDEX);
        SearchSourceBuilder builder = new SearchSourceBuilder();

        // multi_match para buscar en nombre y email
        builder.query(QueryBuilders.multiMatchQuery(value, "nombre", "email"));
        request.source(builder);

        SearchResponse response = client.search(request, RequestOptions.DEFAULT);

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
}
