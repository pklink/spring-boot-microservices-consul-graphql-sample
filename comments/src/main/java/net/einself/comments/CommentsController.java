package net.einself.comments;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/")
public class CommentsController {

    public static final List<Comment> COMMENTS = List.of(
            new Comment(1L, 1L, "Foo Text #1"),
            new Comment(2L, 1L, "Foo Text #2"),
            new Comment(3L, 2L, "Foo Text #3"),
            new Comment(4L, 1L, "Foo Text #4")
    );

    @GetMapping("/articles/{authorId}/comments")
    public List<Comment> getComments(@PathVariable Long authorId) {
        return COMMENTS.stream()
                .filter(comment -> authorId.equals(comment.articleId()))
                .toList();
    }

}
