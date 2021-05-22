package pl.umk.mat.gobooks.books;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import pl.umk.mat.gobooks.authors.AuthorRepository;
import pl.umk.mat.gobooks.books.dto.BookResponse;
import pl.umk.mat.gobooks.books.dto.NewBook;
import pl.umk.mat.gobooks.books.dto.UpdatedBook;
import pl.umk.mat.gobooks.books.enums.Category;
import pl.umk.mat.gobooks.commons.exceptions.BadRequest;
import pl.umk.mat.gobooks.commons.exceptions.ResourceAlreadyExist;
import pl.umk.mat.gobooks.commons.exceptions.ResourceNotFound;
import pl.umk.mat.gobooks.publisher.PublishingHouseRepository;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BookService {

    private final BookRepository bookRepository;
    private final AuthorRepository authorRepository;
    private final PublishingHouseRepository publishingHouseRepository;

    public List<BookResponse> findAll(Pageable pageable) {
        return bookRepository.findAll(pageable).stream()
                .map(BookResponse::new)
                .collect(Collectors.toList());
    }

    public BookResponse findById(Long id) {
        return new BookResponse(bookRepository.findByIdOrThrow(id));
    }

    public List<BookResponse> searchByAuthor(Long authorId, Pageable pageable) throws ResourceNotFound {
        var author = authorRepository.findByIdOrThrow(authorId);
        return bookRepository.findAllByAuthor(author, pageable).stream()
                .map(BookResponse::new)
                .collect(Collectors.toList());
    }

    public List<BookResponse> searchByCategories(List<String> categories, Pageable pageable) {
        List<Category> categoriesList = categories.stream()
                .map(Category::fromName)
                .filter(Optional::isPresent)
                .map(Optional::get)
                .collect(Collectors.toList());
        return bookRepository.findAllByCategoryIn(categoriesList, pageable).stream()
                .map(BookResponse::new)
                .collect(Collectors.toList());
    }

    public List<BookResponse> searchByPublisher(Long publisherId, Pageable pageable) throws ResourceNotFound {
        var publisher = publishingHouseRepository.findByIdOrThrow(publisherId);
        return bookRepository.findAllByPublishingHouse(publisher, pageable).stream()
                .map(BookResponse::new)
                .collect(Collectors.toList());
    }

    @Transactional
    public BookResponse save(NewBook newBook) throws ResourceAlreadyExist, BadRequest {
        if (bookExists(newBook)) {
            throw new ResourceAlreadyExist("Cannot create book with duplicated ISBN or pair (author, title)");
        }

        var category = Category.fromName(newBook.getCategory())
                .orElseThrow(() -> new BadRequest("Selected category not exist"));
        var author = authorRepository.findByIdOrThrow(newBook.getAuthorId());
        var publisher = publishingHouseRepository.findByIdOrThrow(newBook.getPublisherId());

        return new BookResponse(bookRepository.save(new Book(newBook, author, publisher, category)));
    }

    @Transactional
    public BookResponse update(Long id, UpdatedBook updatedBook) throws ResourceNotFound, BadRequest {
        var book = bookRepository.findByIdOrThrow(id);

        if (updatedBook.getTitle() != null && !updatedBook.getTitle().equals("")) {
            book.setTitle(updatedBook.getTitle());
        }

        if (updatedBook.getCategory() != null) {
            var category = Category.fromName(updatedBook.getCategory())
                    .orElseThrow(() -> new BadRequest("Selected category not exist"));
            book.setCategory(category);
        }

        if (updatedBook.getPublicationDate() != null) {
            book.setPublicationDate(updatedBook.getPublicationDate());
        }

        return new BookResponse(book);
    }

    /**
     * TODO: MD
     * add adding the appropriate logic after creating the {@link pl.umk.mat.gobooks.reviews.Review} logic
     * either blocking deletion of books if there are reviews, or cascading deletion of reviews
     */
    @Transactional
    public void delete(Long id) {
        bookRepository.delete(bookRepository.findByIdOrThrow(id));
    }

    private boolean bookExists(NewBook newBook) {
        return bookRepository.existsByIsbn(newBook.getIsbn()) ||
                bookRepository.existsByAuthor_IdAndTitle(newBook.getAuthorId(), newBook.getTitle());
    }
}
