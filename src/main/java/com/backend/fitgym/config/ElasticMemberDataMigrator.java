package com.backend.fitgym.config;

import com.backend.fitgym.dto.MemberDTO;
import com.backend.fitgym.entities.Member;
import com.backend.fitgym.mappers.MemberMapper;
import com.backend.fitgym.repositories.MemberRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.elasticsearch.action.bulk.BulkRequest;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.xcontent.XContentType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

@Service
public class ElasticMemberDataMigrator {

    private static final String INDEX = "members";

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private MemberMapper memberMapper;

    @Autowired
    private RestHighLevelClient client;

    @Autowired
    private ObjectMapper objectMapper;

    public String migrateMembersToElasticsearch() throws IOException {
        List<Member> members = memberRepository.findAll();

        BulkRequest bulkRequest = new BulkRequest();

        for (Member member : members) {
            MemberDTO dto = memberMapper.toDTO(member);

            IndexRequest request = new IndexRequest(INDEX)
                    .id(dto.getId().toString())
                    .source(objectMapper.writeValueAsString(dto), XContentType.JSON);

            bulkRequest.add(request);
        }

        BulkResponse response = client.bulk(bulkRequest, RequestOptions.DEFAULT);

        if (response.hasFailures()) {
            return "Errores en la migración: " + response.buildFailureMessage();
        } else {
            return "Migración completada: " + members.size() + " miembros indexados en Elasticsearch.";
        }
    }
}
