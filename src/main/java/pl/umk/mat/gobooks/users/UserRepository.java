package pl.umk.mat.gobooks.users;

import org.springframework.stereotype.Repository;
import pl.umk.mat.gobooks.commons.BaseRepository;

import java.util.Optional;

@Repository
public interface UserRepository extends BaseRepository<User, Long> {
    Optional<User> findUserByEmail(String email);

    boolean existsUserByEmail(String email);

    boolean existsUserByUsername(String username);
}
