package net.einself.authors;

import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

import java.util.ArrayList;
import java.util.List;

@Controller
public class AuthorsController {

    public static final List<Author> AUTHORS = new ArrayList<>(List.of(
            new Author(1L, "Aron", "Kaufman"),
            new Author(2L, "Juanita", "Kaiser"),
            new Author(3L, "Kirby", "Matthews")
    ));

    @QueryMapping
    public List<Author> authors() {
        return AUTHORS;
    }

    @QueryMapping
    public Author authorById(@Argument Long id) {
        return AUTHORS.stream()
                .filter(author -> id.equals(author.id()))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Author not found"));
    }

    @MutationMapping
    public Author create(@Argument AuthorInput input) {
        Author author = new Author(AUTHORS.size() + 1L, input.firstName(), input.lastName());
        AUTHORS.add(author);
        return author;
    }

}
