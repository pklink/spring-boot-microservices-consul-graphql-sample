package net.einself.articles;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class AuthorsService {

    private final RestTemplate restTemplate;

    public AuthorsService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public Author retrieveOneById(Long id) {
        return restTemplate.getForObject("http://AUTHORS/authors/" + id, Author.class);
    }

}
