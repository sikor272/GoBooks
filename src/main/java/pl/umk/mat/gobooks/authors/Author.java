package pl.umk.mat.gobooks.authors;

import com.neovisionaries.i18n.CountryCode;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.Where;
import pl.umk.mat.gobooks.commons.BaseEntity;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "authors")
@Getter
@Setter
@Where(clause = "deleted = false")
@SQLDelete(sql = "UPDATE authors SET deleted = TRUE WHERE id = ?")
public class Author extends BaseEntity {

    @Column(nullable = false)
    private String firstName;

    @Column(nullable = false)
    private String lastName;

    @Enumerated(EnumType.STRING)
    private CountryCode nationality;

    private LocalDate birthDate;

    @Lob
    @Type(type = "org.hibernate.type.TextType")
    private String biography;

    @Override
    public boolean equals(Object o) {
        return super.equals(o);
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }
}
