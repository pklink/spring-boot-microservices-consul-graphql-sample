package net.einself.articles;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.cloud.consul.discovery.ConsulDiscoveryClient;
import org.springframework.cloud.consul.discovery.ConsulServiceInstance;
import org.springframework.http.HttpMethod;
import org.springframework.http.client.ClientHttpRequest;

import java.io.IOException;
import java.net.URI;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ConsulClientHttpRequestFactoryTest {

    @Mock
    ConsulDiscoveryClient consulDiscoveryClient;

    @InjectMocks
    ConsulClientHttpRequestFactory underTest;

    @Test
    void createRequest_doNotRewriteRequestHost() throws IOException {
        // arrange
        URI uri = URI.create("http://localhost:1111/foo?bar=1");

        // act
        ClientHttpRequest result = underTest.createRequest(uri, HttpMethod.POST);

        // assert
        assertEquals("http://localhost:1111/foo?bar=1", result.getURI().toString());
    }

    @Test
    void createRequest_rewriteRequestHost() throws IOException {
        // arrange
        ConsulServiceInstance serviceInstance = new ConsulServiceInstance("FOO-SERVICE-1", "FOO-SERVICE", "localhost", 1111, false);
        when(consulDiscoveryClient.getInstances("FOO-SERVICE")).thenReturn(List.of(serviceInstance));

        URI uri = URI.create("http://FOO-SERVICE/foo?bar=1");

        // act
        ClientHttpRequest result = underTest.createRequest(uri, HttpMethod.POST);

        // arrange
        assertEquals("http://localhost:1111/foo?bar=1", result.getURI().toString());
    }

}