package pl.umk.mat.gobooks.authors;

import com.neovisionaries.i18n.CountryCode;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import pl.umk.mat.gobooks.authors.dto.AuthorResponse;
import pl.umk.mat.gobooks.authors.dto.NewAuthor;
import pl.umk.mat.gobooks.books.BookRepository;
import pl.umk.mat.gobooks.commons.exceptions.ResourceNotFound;

import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@SpringBootTest
class AuthorServiceTest {

    @Autowired
    private AuthorService service;

    @MockBean
    private AuthorRepository repository;

    @MockBean
    private BookRepository bookRepository;

    @Test
    void shouldReturnAuthorList() {

        when(repository.findAll(any(Pageable.class))).thenReturn(
                new PageImpl<>(List.of(
                        new Author(new NewAuthor("firstName1", "lastName1", "PL",
                                LocalDate.of(2000, 12, 12))),
                        new Author(new NewAuthor("firstName2", "lastName2", "DE",
                                LocalDate.of(1998, 4, 3))),
                        new Author(new NewAuthor("firstName3", "lastName3", "UA",
                                LocalDate.of(1998, 4, 3)))
                ))
        );

        List<AuthorResponse> response = service.findAll(Pageable.unpaged());

        assertEquals(3, response.size());

        verify(repository, times(1)).findAll(any(Pageable.class));
    }

    @Test
    void shouldReturnAuthorById() {

        var newAuthor = new NewAuthor(
                "firstName1", "lastName1", "PL",
                LocalDate.of(2000, 12, 12)
        );
        when(repository.findByIdOrThrow(anyLong())).thenReturn(new Author(newAuthor));

        AuthorResponse founded = service.findById(1L);

        assertThat(founded).isNotNull();
        assertEquals(founded.getNationality(), CountryCode.valueOf(newAuthor.getNationality()));
        assertEquals(founded.getFirstName(), newAuthor.getFirstName());

        verify(repository, times(1)).findByIdOrThrow(anyLong());
    }

    @Test
    void shouldThrowResourceNotFoundWhenPublisherNotExists() {

        when(repository.findByIdOrThrow(anyLong())).thenThrow(new ResourceNotFound());

        assertThrows(ResourceNotFound.class, () -> service.findById(1L));

        verify(repository, times(1)).findByIdOrThrow(anyLong());
    }

    @Test
    void shouldSearchAuthors() {

        when(repository.findDistinctByFirstNameContainsIgnoreCaseAndLastNameContainsIgnoreCase(
                anyString(), anyString(), any(Pageable.class)
        )).thenReturn(new PageImpl<>(List.of(
                new Author(new NewAuthor(
                        "firstName1", "lastName1", "PL",
                        LocalDate.of(2000, 12, 12))),
                new Author(new NewAuthor(
                        "firstName2", "lastName2", "DE",
                        LocalDate.of(1998, 4, 3))),
                new Author(new NewAuthor(
                        "firstName3", "lastName3", "UA",
                        LocalDate.of(1998, 4, 3)))
                ))
        );

        List<AuthorResponse> founded = service.search("name", "name", Pageable.unpaged());

        assertEquals(3, founded.size());
        founded.forEach(author ->
                assertTrue(author.getFirstName().toLowerCase().contains("name"))
        );
    }

    @Test
    void shouldCreateNewAuthor() {

        var newAuthor = new NewAuthor(
                "firstName1", "lastName1", "PL",
                LocalDate.of(2000, 12, 12)
        );
        when(repository.save(any())).thenReturn(new Author(newAuthor));

        AuthorResponse created = service.save(newAuthor);

        assertEquals(newAuthor.getFirstName(), created.getFirstName());

        verify(repository, times(1)).save(any());
    }

    @Test
    void shouldThrowResourceNotFoundWhenTryDeleteNotExistingAuthor() {

        when(repository.findByIdOrThrow(anyLong())).thenThrow(new ResourceNotFound());

        assertThrows(ResourceNotFound.class, () -> service.delete(1L));

        verify(repository, times(1)).findByIdOrThrow(anyLong());
    }

    @Test
    void shouldDeleteExistingAuthor() {

        when(repository.findByIdOrThrow(anyLong())).thenReturn(new Author(new NewAuthor(
                "firstName1", "lastName1", "PL",
                LocalDate.of(2000, 12, 12))
        ));
        when(bookRepository.existsByAuthor(any())).thenReturn(false);

        service.delete(1L);

        verify(repository, times(1)).delete(any());
    }
}