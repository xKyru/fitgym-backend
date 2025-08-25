package com.backend.fitgym.config;

import com.backend.fitgym.dto.PaymentDTO;
import com.backend.fitgym.entities.Payment;
import com.backend.fitgym.mappers.PaymentMapper;
import com.backend.fitgym.repositories.PaymentRepository;
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
public class ElasticPaymentDataMigrator {

    private static final String INDEX = "payments";

    @Autowired
    private PaymentRepository paymentRepository;

    @Autowired
    private PaymentMapper paymentMapper;

    @Autowired
    private RestHighLevelClient client;

    @Autowired
    private ObjectMapper objectMapper;

    public String migratePaymentsToElasticsearch() throws IOException {
        List<Payment> payments = paymentRepository.findAll();

        BulkRequest bulkRequest = new BulkRequest();

        for (Payment member : payments) {
            PaymentDTO dto = paymentMapper.toDTO(member);

            IndexRequest request = new IndexRequest(INDEX)
                    .id(dto.getId().toString())
                    .source(objectMapper.writeValueAsString(dto), XContentType.JSON);

            bulkRequest.add(request);
        }

        BulkResponse response = client.bulk(bulkRequest, RequestOptions.DEFAULT);

        if (response.hasFailures()) {
            return "Errores en la migración: " + response.buildFailureMessage();
        } else {
            return "Migración completada: " + payments.size() + " pagos indexados en Elasticsearch.";
        }
    }
}
