package pl.umk.mat.gobooks.books;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.NaturalId;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;
import pl.umk.mat.gobooks.authors.Author;
import pl.umk.mat.gobooks.books.dto.NewBook;
import pl.umk.mat.gobooks.books.enums.Category;
import pl.umk.mat.gobooks.commons.BaseEntity;
import pl.umk.mat.gobooks.publisher.PublishingHouse;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Objects;

@Entity
@Table(name = "books")
@Getter
@Setter
@NoArgsConstructor
@Where(clause = "deleted = false")
@SQLDelete(sql = "UPDATE books SET deleted = TRUE WHERE id = ?")
public class Book extends BaseEntity {

    @NaturalId
    @Column(nullable = false, unique = true, length = 20)
    private String isbn;

    @Column(nullable = false)
    private String title;

    @Enumerated(EnumType.STRING)
    private Category category;

    private LocalDate publicationDate;

    @ManyToOne
    @JoinColumn(name = "author_id")
    private Author author;

    @ManyToOne
    @JoinColumn(name = "publisher_id")
    private PublishingHouse publishingHouse;

    public Book(NewBook newBook, Author author, PublishingHouse publisher, Category category) {
        this.isbn = newBook.getIsbn();
        this.title = newBook.getTitle();
        this.category = category;
        this.publicationDate = newBook.getPublicationDate();
        this.author = author;
        this.publishingHouse = publisher;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Book)) return false;
        if (!super.equals(o)) return false;
        var book = (Book) o;
        return getIsbn().equals(book.getIsbn());
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), getIsbn());
    }
}
