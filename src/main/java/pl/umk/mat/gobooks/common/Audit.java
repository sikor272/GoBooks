package pl.umk.mat.gobooks.common;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import javax.persistence.Embeddable;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import java.time.Instant;

@Embeddable
@RequiredArgsConstructor
@Getter @Setter
public class Audit {

    private Instant createdOn;

    private Instant updatedOn;

    @PrePersist
    private void prePersist() {
        this.createdOn = Instant.now();
    }

    @PreUpdate
    private void preMerge() {
        this.updatedOn = Instant.now();
    }
}
