package pl.umk.mat.gobooks.authors;

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
public class AuthorController {

    private final AuthorService authorService;

    @GetMapping
    public IterableResponse<AuthorResponse> getAll(Pageable pageable) {
        return new IterableResponse<>(authorService.findAll(pageable));
    }

    @GetMapping("/{id}")
    public AuthorResponse getById(@PathVariable Long id) {
        return authorService.findById(id);
    }

    @GetMapping("/search")
    public IterableResponse<AuthorResponse> search(
            @RequestParam(defaultValue = "") String firstName,
            @RequestParam(defaultValue = "") String lastName,
            Pageable pageable
    ) {
        return new IterableResponse<>(authorService.search(firstName, lastName, pageable));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public AuthorResponse save(@RequestBody @Valid NewAuthor newAuthor)
            throws BadRequest {
        return authorService.save(newAuthor);
    }

    @PatchMapping("/{id}")
    public AuthorResponse updateBiography(@PathVariable Long id, @RequestBody @Valid AuthorBiography biography) {
        return authorService.updateBiography(id, biography);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        authorService.delete(id);
    }
}
