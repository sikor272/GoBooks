package pl.umk.mat.gobooks.books;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
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
@Tag(name = "Books Controller", description = "This controller provide logic for books")
public interface BookController {

    @GetMapping
    @Operation(summary = ".", tags = {"Books Controller"})
    IterableResponse<BookResponse> getAll(Pageable pageable);

    @GetMapping("/{id}")
    @Operation(summary = ".", tags = {"Books Controller"})
    BookResponse getById(@PathVariable Long id);

    @GetMapping("/search")
    @Operation(summary = ".", tags = {"Books Controller"})
    IterableResponse<BookResponse> search(
            @RequestParam(defaultValue = "") String author,
            @RequestParam(defaultValue = "") String category,
            @RequestParam(defaultValue = "") String publisher,
            Pageable pageable
    );

    @GetMapping("/bestBooks")
    @Operation(summary = ".", tags = {"Books Controller"})
    IterableResponse<BookResponse> getBestBooks(
            @RequestParam(defaultValue = "") String author,
            @RequestParam(defaultValue = "") String category,
            @RequestParam(defaultValue = "") String publisher,
            Pageable pageable
    );

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = ".", tags = {"Books Controller"})
    BookResponse save(
            @RequestBody @Valid NewBook newBook
    ) throws BadRequest;

    @PatchMapping("/{id}")
    @Operation(summary = ".", tags = {"Books Controller"})
    BookResponse update(
            @PathVariable Long id,
            @RequestBody @Valid UpdatedBook updatedBook
    ) throws ResourceAlreadyExist, BadRequest;

    @DeleteMapping("/{id}")
    @Operation(summary = ".", tags = {"Books Controller"})
    void delete(@PathVariable Long id) throws BadRequest;

}
