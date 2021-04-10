package pl.umk.mat.gobooks.auth;

import org.springframework.stereotype.Repository;
import pl.umk.mat.gobooks.utils.BaseRepository;

@Repository
public interface ApiLoginEntryRepository extends BaseRepository<ApiLoginEntry, Long> {
    ApiLoginEntry findByToken(String token);
}
