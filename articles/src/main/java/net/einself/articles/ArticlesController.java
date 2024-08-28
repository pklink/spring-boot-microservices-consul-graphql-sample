package net.einself.articles;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/articles")
public class ArticlesController {

    public static final List<Article> ARTICLES = List.of(
            new Article(1L, 1L, "Mastering Microservices with Spring Boot: A Comprehensive Guide for Java Developers"),
            new Article(2L, 1L, "Spring Boot Performance Tuning: Best Practices to Optimize Your Applications"),
            new Article(3L, 2L, "Getting Started with Spring Boot and Docker: Containerizing Your Java Apps"),
            new Article(4L, 3L, "Spring Boot Security: Implementing OAuth2 and JWT for Secure APIs"),
            new Article(5L, 2L, "Exploring Reactive Programming in Spring Boot with WebFlux")
    );

    private final AuthorsService authorsService;

    public ArticlesController(AuthorsService authorsService) {
        this.authorsService = authorsService;
    }

    @GetMapping
    public List<Article> getArticles() {
        return ARTICLES;
    }

    @GetMapping("/{articleId}/author")
    public Author findAuthorByArticleId(@PathVariable Long articleId) {
        return ARTICLES.stream()
                .filter(article -> articleId.equals(article.id()))
                .findFirst()
                .map(Article::authorId)
                .map(authorsService::retrieveOneById)
                .orElseThrow(() -> new RuntimeException("no"));
    }

}
