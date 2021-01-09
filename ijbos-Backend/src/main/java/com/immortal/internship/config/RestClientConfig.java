package com.immortal.internship.config;

import org.apache.http.HttpHost;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RestClientConfig {

    @Value("${elasticsearch.url}")
    private String url;

    @Bean
    public RestHighLevelClient client(){
        RestHighLevelClient client = new RestHighLevelClient(
                RestClient.builder(
                        HttpHost.create(url)
                )
        );
        return client;
    }
}
