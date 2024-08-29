package net.einself.articles;

import org.springframework.cloud.consul.discovery.ConsulDiscoveryClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.graphql.client.HttpSyncGraphQlClient;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.RestTemplate;

@Configuration
public class ConsulConfiguration {

    @Bean
    public ConsulClientHttpRequestFactory clientHttpRequestFactory(ConsulDiscoveryClient consulDiscoveryClient) {
        return new ConsulClientHttpRequestFactory(consulDiscoveryClient);
    }

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

    @Bean
    public RestClient restClient(RestTemplate restTemplate, ConsulClientHttpRequestFactory clientHttpRequestFactory) {
        return RestClient.builder(restTemplate)
                .requestFactory(clientHttpRequestFactory)
                .build();
    }

    @Bean
    public HttpSyncGraphQlClient authorsGraphQlClient(RestClient restClient) {
        return HttpSyncGraphQlClient.builder(restClient)
                .url("http://AUTHORS/graphql")
                .build();
    }

}
