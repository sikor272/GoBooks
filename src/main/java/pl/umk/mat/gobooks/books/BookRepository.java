package pl.umk.mat.gobooks.books;

import org.springframework.stereotype.Repository;
import pl.umk.mat.gobooks.authors.Author;
import pl.umk.mat.gobooks.commons.BaseRepository;

@Repository
public interface BookRepository extends BaseRepository<Book, Long> {
    boolean existsByAuthor(Author author);
}
