package pl.umk.mat.gobooks.books;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.NaturalId;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;
import pl.umk.mat.gobooks.authors.Author;
import pl.umk.mat.gobooks.common.BaseEntity;
import pl.umk.mat.gobooks.publisher.PublishingHouse;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Objects;

@Entity
@Table(name = "books")
@Getter
@Setter
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Book)) return false;
        if (!super.equals(o)) return false;
        Book book = (Book) o;
        return getIsbn().equals(book.getIsbn());
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), getIsbn());
    }
}
