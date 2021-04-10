package pl.umk.mat.gobooks.auth;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import pl.umk.mat.gobooks.common.Audit;
import pl.umk.mat.gobooks.users.User;

import javax.persistence.*;
import java.io.Serializable;
import java.time.Instant;

@Entity
@RequiredArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Table(name = "api_login_entries")
public class ApiLoginEntry implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String token;

    @Column(nullable = false)
    private Instant expiredAt;

    @Embedded
    private Audit audit = new Audit();

    @ManyToOne
    @JoinColumn (name = "user_id")
    private User user;

}