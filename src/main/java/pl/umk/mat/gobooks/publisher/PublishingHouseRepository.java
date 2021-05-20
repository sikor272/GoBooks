package pl.umk.mat.gobooks.publisher;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import pl.umk.mat.gobooks.commons.BaseRepository;

public interface PublishingHouseRepository extends BaseRepository<PublishingHouse, Long> {
    Page<PublishingHouse> findDistinctByNameContainsIgnoreCase(String name, Pageable pageable);

    boolean existsByNameEquals(String name);
}
