package pl.umk.mat.gobooks.users;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Where;
import pl.umk.mat.gobooks.auth.enums.Role;
import pl.umk.mat.gobooks.common.BaseEntity;

import javax.persistence.*;
import java.util.Objects;

@Entity
@RequiredArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Table(name = "users")
@Where(clause = "deleted = false")
public class User extends BaseEntity {

    @Column(nullable = false, unique = true, updatable = false, length = 32)
    private String username;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false, unique = true, updatable = false)
    private String email;

    @Column(length = 1000)
    private String avatar;

    @Enumerated(EnumType.STRING)
    private Role role = Role.USER;

    public User(String username, String password, String email) {
        this.username = username;
        this.password = password;
        this.email = email;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof User)) return false;
        User user = (User) o;
        return username.equals(user.getUsername()) && email.equals(user.getEmail());
    }

    @Override
    public int hashCode() {
        return Objects.hash(username, email);
    }
}
