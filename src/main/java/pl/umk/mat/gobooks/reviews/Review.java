package pl.umk.mat.gobooks.reviews;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.Where;
import pl.umk.mat.gobooks.common.BaseEntity;
import pl.umk.mat.gobooks.users.User;

import javax.persistence.*;

@Entity
@Table(name = "reviews")
@Getter
@Setter
@Where(clause = "deleted = false")
@SQLDelete(sql = "UPDATE reviews SET deleted = TRUE WHERE id = ?")
class Review extends BaseEntity {

    @Column(nullable = false)
    private String title;

    @Lob
    @Type(type = "org.hibernate.type.TextType")
    @Column(nullable = false)
    private String content;

    @Column(nullable = false)
    private Short score;

    @ManyToOne
    @JoinColumn(name = "reviewer_id")
    private User reviewer;

    @Override
    public boolean equals(Object o) {
        return super.equals(o);
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }
}
