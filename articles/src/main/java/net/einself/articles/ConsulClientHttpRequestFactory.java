package net.einself.articles;

import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.consul.discovery.ConsulDiscoveryClient;
import org.springframework.http.HttpMethod;
import org.springframework.http.client.ClientHttpRequest;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

@Component
public class ConsulClientHttpRequestFactory extends SimpleClientHttpRequestFactory {

    private final ConsulDiscoveryClient consulDiscoveryClient;

    public ConsulClientHttpRequestFactory(ConsulDiscoveryClient consulDiscoveryClient) {
        this.consulDiscoveryClient = consulDiscoveryClient;
    }

    @Override
    public ClientHttpRequest createRequest(URI uri,HttpMethod httpMethod) throws IOException {
        return super.createRequest(rewriteUri(uri), httpMethod);
    }

    private URI rewriteUri(URI requestUri) {
         return consulDiscoveryClient.getInstances(requestUri.getHost()).stream()
                 .findFirst()
                 .map(ServiceInstance::getUri)
                 .map(serviceUri -> mergeURIs(requestUri, serviceUri))
                 .orElse(requestUri);
    }

    private static URI mergeURIs(URI requestUri, URI serviceUri) {
        try {
            return new URI(serviceUri.getScheme(), serviceUri.getUserInfo(), serviceUri.getHost(), serviceUri.getPort(), requestUri.getPath(), requestUri.getQuery(), requestUri.getFragment());
        } catch (URISyntaxException e) {
            return requestUri;
        }
    }

}
