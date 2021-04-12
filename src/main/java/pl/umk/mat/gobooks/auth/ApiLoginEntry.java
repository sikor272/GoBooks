package pl.umk.mat.gobooks.auth;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Where;
import pl.umk.mat.gobooks.common.BaseEntity;
import pl.umk.mat.gobooks.users.User;

import javax.persistence.*;
import java.time.Instant;

@Entity
@NoArgsConstructor
@Getter
@Setter
@Table(name = "api_login_entries")
@Where(clause = "deleted = false")
public class ApiLoginEntry extends BaseEntity {

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
}