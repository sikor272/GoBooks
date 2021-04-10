package pl.umk.mat.gobooks.users;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import pl.umk.mat.gobooks.common.Audit;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

@Entity
@RequiredArgsConstructor
@Getter @Setter
@Table(name = "users")
public class User implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true, updatable = false, length = 32)
    private String username;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false, unique = true, updatable = false)
    private String email;

    @Column(length = 1000)
    private String avatar;

    @Embedded
    private Audit audit = new Audit();

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
