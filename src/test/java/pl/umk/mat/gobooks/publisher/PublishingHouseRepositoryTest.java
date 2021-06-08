package pl.umk.mat.gobooks.publisher;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import pl.umk.mat.gobooks.commons.exceptions.ResourceNotFound;
import pl.umk.mat.gobooks.publisher.dto.NewPublishingHouse;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@DataJpaTest
class PublishingHouseRepositoryTest {

    @Autowired
    private PublishingHouseRepository repository;

    private PublishingHouse publishingHouse;
    private List<PublishingHouse> publishingHouses;

    @BeforeEach
    public void setUp() {
        publishingHouse = new PublishingHouse(new NewPublishingHouse("publisher"));
        publishingHouses = List.of(
                new PublishingHouse(new NewPublishingHouse("publisher1")),
                new PublishingHouse(new NewPublishingHouse("publisher2")),
                new PublishingHouse(new NewPublishingHouse("publisher3"))
        );
    }

    @AfterEach
    public void tearDown() {
        repository.deleteAll();
        publishingHouse = null;
        publishingHouses = null;
    }

    @Test
    void shouldThrowResourceNotFoundExceptionForEmptyRepository() {
        assertThrows(ResourceNotFound.class, () -> repository.findByIdOrThrow(1L));
    }

    @Test
    void shouldReturnExistingPublishersList() {

        repository.saveAll(publishingHouses);

        List<PublishingHouse> publishers = repository.findAllList();

        assertEquals(3, publishers.size());
    }

    @Test
    void shouldReturnExistingPublisherById() {

        repository.save(publishingHouse);
        assertNotNull(publishingHouse.getId());

        var fetchedPublisher = repository.findByIdOrThrow(publishingHouse.getId());

        assertEquals("publisher", fetchedPublisher.getName());
    }

    @Test
    void shouldReturnTrueForExistingPublisherByName() {

        repository.save(publishingHouse);

        assertTrue(repository.existsByNameEquals("publisher"));
    }

    @Test
    void shouldReturnPublisherForExistingName() {

        repository.saveAll(publishingHouses);

        List<PublishingHouse> publishers = repository.findDistinctByNameContainsIgnoreCase("publisher2", Pageable.unpaged()).toList();
        assertEquals(1, publishers.size());

        publishers = repository.findDistinctByNameContainsIgnoreCase("publisher", Pageable.unpaged()).toList();
        assertEquals(3, publishers.size());
    }

    @Test
    void shouldDeleteExistingPublisher() {
        repository.save(publishingHouse);
        assertNotNull(publishingHouse.getId());
        repository.deleteById(publishingHouse.getId());
        assertThrows(ResourceNotFound.class, () -> repository.findByIdOrThrow(publishingHouse.getId()));
    }
}