package pl.umk.mat.gobooks.books;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import pl.umk.mat.gobooks.authors.Author;
import pl.umk.mat.gobooks.books.enums.Category;
import pl.umk.mat.gobooks.commons.BaseRepository;
import pl.umk.mat.gobooks.publisher.PublishingHouse;

import java.util.List;

@Repository
public interface BookRepository extends BaseRepository<Book, Long> {

    boolean existsByAuthor(Author author);

    boolean existsByPublishingHouse(PublishingHouse publishingHouse);

    boolean existsByIsbn(String isbn);

    boolean existsByAuthor_IdAndTitle(Long authorId, String title);

    Page<Book> findAllByAuthor(Author author, Pageable pageable);

    Page<Book> findAllByCategoryIn(List<Category> categories, Pageable pageable);

    Page<Book> findAllByPublishingHouse(PublishingHouse publisher, Pageable pageable);
}
