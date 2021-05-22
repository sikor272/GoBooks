package pl.umk.mat.gobooks.publisher;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import pl.umk.mat.gobooks.books.BookRepository;
import pl.umk.mat.gobooks.commons.exceptions.BadRequest;
import pl.umk.mat.gobooks.commons.exceptions.ResourceAlreadyExist;
import pl.umk.mat.gobooks.commons.exceptions.ResourceNotFound;
import pl.umk.mat.gobooks.publisher.dto.NewPublishingHouse;
import pl.umk.mat.gobooks.publisher.dto.PublishingHouseResponse;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PublishingHouseService {
    private final PublishingHouseRepository publishingHouseRepository;
    private final BookRepository bookRepository;

    public List<PublishingHouseResponse> findAll(Pageable pageable) {
        return publishingHouseRepository.findAll(pageable).stream()
                .map(PublishingHouseResponse::new)
                .collect(Collectors.toList());
    }

    public PublishingHouseResponse findById(Long id) {
        return new PublishingHouseResponse(publishingHouseRepository.findByIdOrThrow(id));
    }

    public List<PublishingHouseResponse> search(String name, Pageable pageable) {
        return publishingHouseRepository
                .findDistinctByNameContainsIgnoreCase(name, pageable)
                .stream()
                .map(PublishingHouseResponse::new)
                .collect(Collectors.toList());
    }

    @Transactional
    public PublishingHouseResponse save(NewPublishingHouse newPublishingHouse) throws ResourceAlreadyExist {
        if (publishingHouseRepository.existsByNameEquals(newPublishingHouse.getName())) {
            throw new ResourceAlreadyExist();
        }
        return new PublishingHouseResponse(publishingHouseRepository.save(new PublishingHouse(newPublishingHouse)));
    }

    @Transactional
    public PublishingHouseResponse updateName(Long id, NewPublishingHouse newPublishingHouse)
            throws ResourceAlreadyExist, ResourceNotFound {
        var publishingHouse = publishingHouseRepository.findByIdOrThrow(id);
        // TODO: MD co jeśli wysyłam update bez zmiany nazwy?
        if (publishingHouseRepository.existsByNameEquals(newPublishingHouse.getName())) {
            throw new ResourceAlreadyExist("Cannot create second publisher with the same name");
        }
        publishingHouse.setName(newPublishingHouse.getName());
        return new PublishingHouseResponse(publishingHouseRepository.save(publishingHouse));
    }

    @Transactional
    public void delete(Long id) throws ResourceNotFound, BadRequest {
        var publishingHouse = publishingHouseRepository.findByIdOrThrow(id);
        if (bookRepository.existsByPublishingHouse(publishingHouse)) {
            throw new BadRequest("Cannot remove publishing house with books");
        }
        publishingHouseRepository.delete(publishingHouse);
    }

}
