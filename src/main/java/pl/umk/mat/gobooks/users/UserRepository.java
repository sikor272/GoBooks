package pl.umk.mat.gobooks.users;

import org.springframework.stereotype.Repository;
import pl.umk.mat.gobooks.common.BaseRepository;

import java.util.Optional;

@Repository
public interface UserRepository extends BaseRepository<User, Long> {
    Optional<User> findUserByEmail(String email);

    Boolean existsUserByEmail(String email);

    Boolean existsUserByUsername(String username);
}
