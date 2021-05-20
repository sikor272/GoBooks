package pl.umk.mat.gobooks.authors;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import pl.umk.mat.gobooks.authors.dto.AuthorBiography;
import pl.umk.mat.gobooks.authors.dto.AuthorResponse;
import pl.umk.mat.gobooks.authors.dto.NewAuthor;
import pl.umk.mat.gobooks.commons.IterableResponse;
import pl.umk.mat.gobooks.commons.exceptions.BadRequest;

import javax.validation.Valid;

@RestController
@RequestMapping("api/authors")
@RequiredArgsConstructor
@Tag(name = "Author Controller", description = "This controller provide logic for authors.")
public class AuthorController {

    private final AuthorService authorService;

    @GetMapping
    @Operation(summary = ".", tags = {"Author Controller"})
    public IterableResponse<AuthorResponse> getAll(Pageable pageable) {
        return new IterableResponse<>(authorService.findAll(pageable));
    }

    @GetMapping("/{id}")
    @Operation(summary = ".", tags = {"Author Controller"})
    public AuthorResponse getById(@PathVariable Long id) {
        return authorService.findById(id);
    }

    @GetMapping("/search")
    @Operation(summary = ".", tags = {"Author Controller"})
    public IterableResponse<AuthorResponse> search(
            @RequestParam(defaultValue = "") String firstName,
            @RequestParam(defaultValue = "") String lastName,
            Pageable pageable
    ) {
        return new IterableResponse<>(authorService.search(firstName, lastName, pageable));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = ".", tags = {"Author Controller"})
    public AuthorResponse save(@RequestBody @Valid NewAuthor newAuthor)
            throws BadRequest {
        return authorService.save(newAuthor);
    }

    @PatchMapping("/{id}")
    @Operation(summary = ".", tags = {"Author Controller"})
    public AuthorResponse updateBiography(@PathVariable Long id, @RequestBody @Valid AuthorBiography biography) {
        return authorService.updateBiography(id, biography);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = ".", tags = {"Author Controller"})
    public void delete(@PathVariable Long id) throws BadRequest {
        authorService.delete(id);
    }
}
