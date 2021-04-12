package pl.umk.mat.gobooks.auth;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.NaturalId;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;
import pl.umk.mat.gobooks.common.BaseEntity;
import pl.umk.mat.gobooks.users.User;

import javax.persistence.*;
import java.time.Instant;
import java.util.Objects;

@Entity
@NoArgsConstructor
@Getter
@Setter
@Table(name = "api_login_entries")
@Where(clause = "deleted = false")
@SQLDelete(sql = "UPDATE users SET deleted = true WHERE id = ?")
public class ApiLoginEntry extends BaseEntity {

    @NaturalId
    @Column(unique = true, nullable = false)
    private String token;

    @Column(nullable = false)
    private Instant expiredAt;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    public ApiLoginEntry(User user, String token, Instant expiredAt) {
        this.user = user;
        this.token = token;
        this.expiredAt = expiredAt;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ApiLoginEntry)) return false;
        if (!super.equals(o)) return false;
        ApiLoginEntry that = (ApiLoginEntry) o;
        return getToken().equals(that.getToken());
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), getToken());
    }
}