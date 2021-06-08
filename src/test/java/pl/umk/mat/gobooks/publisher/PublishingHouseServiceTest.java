package pl.umk.mat.gobooks.publisher;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import pl.umk.mat.gobooks.books.BookRepository;
import pl.umk.mat.gobooks.commons.exceptions.BadRequest;
import pl.umk.mat.gobooks.commons.exceptions.ResourceNotFound;
import pl.umk.mat.gobooks.publisher.dto.NewPublishingHouse;
import pl.umk.mat.gobooks.publisher.dto.PublishingHouseResponse;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@SpringBootTest
class PublishingHouseServiceTest {

    @Autowired
    private PublishingHouseService service;

    @MockBean
    private PublishingHouseRepository repository;

    @MockBean
    private BookRepository bookRepository;

    @Test
    void shouldReturnPublisherList() {

        when(repository.findAll(any(Pageable.class))).thenReturn(
                new PageImpl<>(List.of(
                        new PublishingHouse(new NewPublishingHouse("publisher1")),
                        new PublishingHouse(new NewPublishingHouse("publisher2")),
                        new PublishingHouse(new NewPublishingHouse("publisher3"))
                ))
        );

        List<PublishingHouseResponse> response = service.findAll(Pageable.unpaged());

        assertEquals(3, response.size());

        verify(repository, times(1)).findAll(any(Pageable.class));
    }

    @Test
    void shouldReturnPublisherById() {

        var newPublisher = new NewPublishingHouse("publisher");
        when(repository.findByIdOrThrow(anyLong())).thenReturn(new PublishingHouse(newPublisher));

        PublishingHouseResponse founded = service.findById(1L);

        assertThat(founded).isNotNull();
        assertEquals(founded.getName(), newPublisher.getName());

        verify(repository, times(1)).findByIdOrThrow(anyLong());
    }

    @Test
    void shouldThrowResourceNotFoundWhenPublisherNotExists() {

        when(repository.findByIdOrThrow(anyLong())).thenThrow(new ResourceNotFound());

        assertThrows(ResourceNotFound.class, () -> service.findById(1L));

        verify(repository, times(1)).findByIdOrThrow(anyLong());
    }

    @Test
    void shouldSearchPublishersById() {

        when(repository.findDistinctByNameContainsIgnoreCase(anyString(), any())).thenReturn(
                new PageImpl<>(List.of(
                        new PublishingHouse(new NewPublishingHouse("publisher1")),
                        new PublishingHouse(new NewPublishingHouse("publisher2")),
                        new PublishingHouse(new NewPublishingHouse("publisher3"))
                ))
        );

        List<PublishingHouseResponse> founded = service.search("publisher", Pageable.unpaged());

        assertEquals(3, founded.size());
        founded.forEach(publisher ->
                assertTrue(publisher.getName().contains("publisher"))
        );

        verify(repository, times(1)).findDistinctByNameContainsIgnoreCase(anyString(), any());
    }

    @Test
    void shouldCreateNewPublisher() {

        var newPublisher = new NewPublishingHouse("publisher");
        when(repository.save(any())).thenReturn(new PublishingHouse(newPublisher));

        PublishingHouseResponse created = service.save(newPublisher);

        assertEquals(newPublisher.getName(), created.getName());

        verify(repository, times(1)).save(any());
    }

    @Test
    void shouldThrowResourceNotFoundWhenTryDeleteNotExistingPublisher() {

        when(repository.findByIdOrThrow(anyLong())).thenThrow(new ResourceNotFound());

        assertThrows(ResourceNotFound.class, () -> service.delete(1L));

        verify(repository, times(1)).findByIdOrThrow(anyLong());
    }

    @Test
    void shouldThrowBadRequestWhenTryDeletePublisherWithAtLeastOneBook() {

        when(repository.findByIdOrThrow(anyLong())).thenReturn(new PublishingHouse(new NewPublishingHouse("publisher")));
        when(bookRepository.existsByPublishingHouse(any())).thenReturn(true);

        assertThrows(BadRequest.class, () -> service.delete(1L));

        verify(repository, times(1)).findByIdOrThrow(anyLong());
        verify(bookRepository, times(1)).existsByPublishingHouse(any());
    }

    @Test
    void shouldDeleteExistingPublisher() {

        when(repository.findByIdOrThrow(anyLong())).thenReturn(new PublishingHouse(new NewPublishingHouse("publisher")));
        when(bookRepository.existsByPublishingHouse(any())).thenReturn(false);
        service.delete(1L);

        verify(repository, times(1)).delete(any());
    }
}