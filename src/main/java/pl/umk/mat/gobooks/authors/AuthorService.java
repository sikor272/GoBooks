package pl.umk.mat.gobooks.authors;

import com.neovisionaries.i18n.CountryCode;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import pl.umk.mat.gobooks.authors.dto.AuthorBiography;
import pl.umk.mat.gobooks.authors.dto.AuthorResponse;
import pl.umk.mat.gobooks.authors.dto.NewAuthor;
import pl.umk.mat.gobooks.books.BookRepository;
import pl.umk.mat.gobooks.commons.exceptions.BadRequest;
import pl.umk.mat.gobooks.commons.exceptions.ResourceAlreadyExist;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AuthorService {

    private final AuthorRepository authorRepository;
    private final BookRepository bookRepository;

    public List<AuthorResponse> findAll(Pageable pageable) {
        return authorRepository.findAll(pageable).stream()
                .map(AuthorResponse::new)
                .collect(Collectors.toList());
    }

    public AuthorResponse findById(Long id) {
        return new AuthorResponse(authorRepository.findByIdOrThrow(id));
    }

    public List<AuthorResponse> search(String firstName, String lastName, Pageable pageable) {
        return authorRepository
                .findDistinctByFirstNameContainsIgnoreCaseAndLastNameContainsIgnoreCase(
                        firstName, lastName, pageable)
                .stream()
                .map(AuthorResponse::new)
                .collect(Collectors.toList());
    }

    @Transactional
    public AuthorResponse save(NewAuthor newAuthor) {
        CountryCode nationality;
        try {
            nationality = CountryCode.valueOf(newAuthor.getNationality());
        } catch (IllegalArgumentException ex) {
            throw new BadRequest(String.format("Cannot find %s nationality value", newAuthor.getNationality()));
        }
        if (authorRepository.existsByFirstNameEqualsAndLastNameEqualsAndNationalityEquals(
                newAuthor.getFirstName(), newAuthor.getLastName(), nationality)) {
            throw new ResourceAlreadyExist();
        }
        return new AuthorResponse(authorRepository.save(new Author(newAuthor)));
    }

    @Transactional
    public AuthorResponse updateBiography(Long id, AuthorBiography biography) {
        var author = authorRepository.findByIdOrThrow(id);
        author.setBiography(biography.getBiography());
        return new AuthorResponse(authorRepository.save(author));
    }

    @Transactional
    public void delete(Long id) {
        var author = authorRepository.findByIdOrThrow(id);
        if (bookRepository.existsByAuthor(author)) {
            throw new BadRequest("Cannot remove author with books");
        }
        authorRepository.delete(author);
    }
}
