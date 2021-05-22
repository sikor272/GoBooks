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

import javax.validation.Valid;

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

    @GetMapping("/search")
    @Operation(summary = ".", tags = {"Books Controller"})
    public IterableResponse<BookResponse> search(
            @RequestParam(defaultValue = "") String author,
            @RequestParam(defaultValue = "") String category,
            @RequestParam(defaultValue = "") String publisher,
            Pageable pageable
    ) {
        return new IterableResponse<>(
                bookService.search(author, category, publisher, pageable)
        );
    }

    @GetMapping("/bestBooks")
    @Operation(summary = ".", tags = {"Books Controller"})
    public IterableResponse<BookResponse> getBestBooks(
            @RequestParam(defaultValue = "") String author,
            @RequestParam(defaultValue = "") String category,
            @RequestParam(defaultValue = "") String publisher,
            Pageable pageable
    ) {
        return new IterableResponse<>(
                bookService.search(author, category, publisher, pageable)
        );
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
