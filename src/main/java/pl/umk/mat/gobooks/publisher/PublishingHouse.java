package pl.umk.mat.gobooks.publisher;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;
import pl.umk.mat.gobooks.commons.BaseEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Getter
@Setter
@Table(name = "publishing_houses")
@Where(clause = "deleted = false")
@SQLDelete(sql = "UPDATE publishing_houses SET deleted = TRUE WHERE id = ?")
public class PublishingHouse extends BaseEntity {

    @Column(nullable = false)
    private String name;

    @Override
    public boolean equals(Object o) {
        return super.equals(o);
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }
}
