package pl.umk.mat.gobooks.authors;

import com.neovisionaries.i18n.CountryCode;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import pl.umk.mat.gobooks.commons.BaseRepository;

@Repository
public interface AuthorRepository extends BaseRepository<Author, Long> {

    Page<Author> findDistinctByFirstNameContainsIgnoreCaseAndLastNameContainsIgnoreCase(
            String firstName, String lastName, Pageable pageable);

    boolean existsByFirstNameEqualsAndLastNameEqualsAndNationalityEquals(
            String firstName, String lastName, CountryCode nationality);
}
