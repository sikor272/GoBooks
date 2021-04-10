package pl.umk.mat.gobooks.users;

import org.springframework.stereotype.Repository;
import pl.umk.mat.gobooks.utils.BaseRepository;

@Repository
public interface UserRepository extends BaseRepository<User, Long> {
    User findUserByEmail(String Email);

    Boolean existsUserByEmail(String Email);
}
