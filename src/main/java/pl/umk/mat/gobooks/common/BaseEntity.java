package pl.umk.mat.gobooks.common;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.Hibernate;
import org.hibernate.annotations.Where;
import org.springframework.data.domain.Persistable;

import javax.persistence.*;
import java.io.Serializable;
import java.time.Instant;
import java.util.Objects;

@Getter
@Setter
@MappedSuperclass
public abstract class BaseEntity implements Serializable, Persistable<Long> {

    @Setter(value = AccessLevel.PRIVATE)
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected Long id;

    protected Instant createdOn;

    protected Instant updatedOn;

    protected Boolean deleted = Boolean.FALSE;

    @Override
    public boolean isNew() {
        return id == null;
    }

    @PrePersist
    private void prePersist() {
        this.createdOn = Instant.now();
        this.updatedOn = Instant.now();
    }

    @PreUpdate
    private void preMerge() {
        this.updatedOn = Instant.now();
    }

    @Override
    @SuppressWarnings("all")
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.unproxy(this).getClass() != Hibernate.unproxy(o).getClass())
            return false;
        BaseEntity that = (BaseEntity) o;
        return Objects.equals(getId(), that.getId());
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
