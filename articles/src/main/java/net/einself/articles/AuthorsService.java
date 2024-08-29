package net.einself.articles;

import org.springframework.graphql.client.HttpSyncGraphQlClient;
import org.springframework.stereotype.Service;

@Service
public class AuthorsService {

    private final HttpSyncGraphQlClient httpSyncGraphQlClient;

    public AuthorsService(HttpSyncGraphQlClient httpSyncGraphQlClient) {
        this.httpSyncGraphQlClient = httpSyncGraphQlClient;
    }

    public Author retrieveOneById(Long id) {
        return httpSyncGraphQlClient
                .documentName("authorById")
                .variable("id", id)
                .retrieveSync("authorById")
                .toEntity(Author.class);
    }

}
