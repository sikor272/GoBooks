package pl.umk.mat.gobooks.authors;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.umk.mat.gobooks.authors.dto.AuthorBiography;
import pl.umk.mat.gobooks.authors.dto.AuthorResponse;
import pl.umk.mat.gobooks.authors.dto.NewAuthor;
import pl.umk.mat.gobooks.commons.exceptions.BadRequest;

import javax.validation.Valid;
import java.io.Serializable;
import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("api/authors")
@RequiredArgsConstructor
public class AuthorController {

    private final AuthorService authorService;

    @GetMapping
    public ResponseEntity<List<AuthorResponse>> getAll(Pageable pageable) {
        return ResponseEntity.ok(authorService.findAll(pageable));
    }

    @GetMapping("/{id}")
    public ResponseEntity<AuthorResponse> getById(@PathVariable Long id) {
        return ResponseEntity.ok(authorService.findById(id));
    }

    @GetMapping("/search")
    public ResponseEntity<List<AuthorResponse>> search(
            @RequestParam(defaultValue = "") String firstName,
            @RequestParam(defaultValue = "") String lastName,
            Pageable pageable
    ) {
        return ResponseEntity.ok(authorService.search(firstName, lastName, pageable));
    }

    @PostMapping
    public ResponseEntity<Serializable> save(@RequestBody @Valid NewAuthor newAuthor)
            throws BadRequest {
        return ResponseEntity
                .created(URI.create("/authors/" + authorService.save(newAuthor)))
                .build();
    }

    @PatchMapping("/{id}")
    public ResponseEntity<AuthorResponse> updateBiography(@PathVariable Long id, @RequestBody @Valid AuthorBiography biography) {
        return ResponseEntity.ok(authorService.updateBiography(id, biography));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Serializable> delete(@PathVariable Long id) {
        authorService.delete(id);
        return ResponseEntity.ok().build();
    }
}
