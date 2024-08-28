package net.einself.authors;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/authors")
public class AuthorsController {

    public static final List<Author> AUTHORS = List.of(
            new Author(1L, "Aron", "Kaufman"),
            new Author(2L, "Juanita", "Kaiser"),
            new Author(3L, "Kirby", "Matthews")
    );

    @GetMapping
    public List<Author> getAuthors() {
        return AUTHORS;
    }

    @GetMapping("/{id}")
    public Author getAuthor(@PathVariable Long id) {
        return AUTHORS.stream()
                .filter(author -> id.equals(author.id()))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Author not found"));
    }

}
