package net.einself.articles;

import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.graphql.data.method.annotation.SchemaMapping;
import org.springframework.stereotype.Controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Controller
public class ArticlesController {

    public static final List<Article> ARTICLES = new ArrayList<>(List.of(
            new Article(1L, 1L, "Mastering Microservices with Spring Boot: A Comprehensive Guide for Java Developers"),
            new Article(2L, 1L, "Spring Boot Performance Tuning: Best Practices to Optimize Your Applications"),
            new Article(3L, 2L, "Getting Started with Spring Boot and Docker: Containerizing Your Java Apps"),
            new Article(4L, 3L, "Spring Boot Security: Implementing OAuth2 and JWT for Secure APIs"),
            new Article(5L, 2L, "Exploring Reactive Programming in Spring Boot with WebFlux")
    ));

    private final AuthorsService authorsService;

    public ArticlesController(AuthorsService authorsService) {
        this.authorsService = authorsService;
    }

    @QueryMapping
    public List<Article> articles() {
        return ARTICLES;
    }

    @QueryMapping
    public Article articleById(@Argument Long id) {
        return ARTICLES.stream()
                .filter(article -> Objects.equals(article.authorId(), id))
                .findFirst()
                .orElse(null);
    }

    @MutationMapping
    public Article create(@Argument ArticleInput input) {
        Article article = new Article(ARTICLES.size() + 1L, input.authorId(), input.title());
        ARTICLES.add(article);
        return article;
    }

    @SchemaMapping
    public Author author(Article article) {
        return authorsService.retrieveOneById(article.authorId());
    }

}
