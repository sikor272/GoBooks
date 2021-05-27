package pl.umk.mat.gobooks.books;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import pl.umk.mat.gobooks.books.dto.BookResponse;
import pl.umk.mat.gobooks.books.dto.NewBook;
import pl.umk.mat.gobooks.books.dto.UpdatedBook;
import pl.umk.mat.gobooks.commons.IterableResponse;
import pl.umk.mat.gobooks.commons.exceptions.BadRequest;
import pl.umk.mat.gobooks.commons.exceptions.ResourceAlreadyExist;
import pl.umk.mat.gobooks.commons.exceptions.ResourceNotFound;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("api/books")
@RequiredArgsConstructor
@Tag(name = "Books Controller", description = "This controller provide logic for books")
public class BookController {

    private final BookService bookService;

    @GetMapping
    @Operation(summary = ".", tags = {"Books Controller"})
    public IterableResponse<BookResponse> getAll(Pageable pageable) {
        return new IterableResponse<>(bookService.findAll(pageable));
    }

    @GetMapping("/{id}")
    @Operation(summary = ".", tags = {"Books Controller"})
    BookResponse getById(@PathVariable Long id) {
        return bookService.findById(id);
    }

    @GetMapping("/search/author/{authorId}")
    @Operation(summary = ".", tags = {"Books Controller"})
    public IterableResponse<BookResponse> searchByAuthor(@PathVariable Long authorId, Pageable pageable)
            throws ResourceNotFound {
        return new IterableResponse<>(
                bookService.searchByAuthor(authorId, pageable)
        );
    }

    @GetMapping("/search/categories")
    @Operation(summary = ".", tags = {"Books Controller"})
    public IterableResponse<BookResponse> searchByCategories(@RequestParam List<String> categories, Pageable pageable) {
        return new IterableResponse<>(
                bookService.searchByCategories(categories, pageable)
        );
    }

    @GetMapping("/search/publisher/{publisherId}")
    @Operation(summary = ".", tags = {"Books Controller"})
    public IterableResponse<BookResponse> searchByPublisher(@PathVariable Long publisherId, Pageable pageable)
            throws ResourceNotFound {
        return new IterableResponse<>(
                bookService.searchByPublisher(publisherId, pageable)
        );
    }

    @GetMapping("/bestBooks")
    @ResponseStatus(HttpStatus.NOT_IMPLEMENTED)
    @Operation(summary = ".", tags = {"Books Controller"})
    public IterableResponse<BookResponse> getBestBooks(Pageable pageable) {
        return new IterableResponse<>(null);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = ".", tags = {"Books Controller"})
    public BookResponse save(@RequestBody @Valid NewBook newBook) throws BadRequest {
        return bookService.save(newBook);
    }

    @PatchMapping("/{id}")
    @Operation(summary = ".", tags = {"Books Controller"})
    public BookResponse update(
            @PathVariable Long id,
            @RequestBody @Valid UpdatedBook updatedBook
    ) throws ResourceAlreadyExist, BadRequest {
        return bookService.update(id, updatedBook);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = ".", tags = {"Books Controller"})
    public void delete(@PathVariable Long id) throws BadRequest {
        bookService.delete(id);
    }

}
