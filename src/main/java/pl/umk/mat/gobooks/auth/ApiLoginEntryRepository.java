package pl.umk.mat.gobooks.auth;

import org.springframework.stereotype.Repository;
import pl.umk.mat.gobooks.common.BaseRepository;

@Repository
public interface ApiLoginEntryRepository extends BaseRepository<ApiLoginEntry, Long> {
    ApiLoginEntry findByToken(String token);
}
