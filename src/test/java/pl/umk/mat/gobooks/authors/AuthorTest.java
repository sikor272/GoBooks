package pl.umk.mat.gobooks.authors;

import org.junit.jupiter.api.Test;
import pl.umk.mat.gobooks.authors.dto.NewAuthor;
import pl.umk.mat.gobooks.commons.exceptions.BadRequest;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class AuthorTest {

    @Test
    void shouldCreateAuthorFromDto() {
        
        var newAuthorDto = new NewAuthor(
                "name", "name", "PL", LocalDate.now()
        );
        var author = new Author(newAuthorDto);

        assertAll(
                () -> assertNotNull(author),
                () -> assertEquals(newAuthorDto.getFirstName(), author.getFirstName()),
                () -> assertEquals(newAuthorDto.getLastName(), author.getLastName()),
                () -> assertEquals(newAuthorDto.getNationality(), author.getNationality().toString()),
                () -> assertEquals(newAuthorDto.getBirthDate(), author.getBirthDate())
        );
    }

    @Test
    void shouldThrowBadRequestWhenNationalityNotExists() {

        var newAuthorDto = new NewAuthor(
                "name", "name", "POL", LocalDate.now()
        );

        assertThrows(BadRequest.class, () -> new Author(newAuthorDto));
    }
}