package pl.umk.mat.gobooks.utils;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;
import pl.umk.mat.gobooks.utils.exceptions.ResourceNotFound;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@NoRepositoryBean
public interface BaseRepository<T, ID> extends JpaRepository<T, ID> {
    default T findByIdOrThrow(ID id) {
        Optional<T> entity = this.findById(id);
        if (entity.isPresent()) {
            return entity.get();
        } else {
            throw new ResourceNotFound();
        }
    }

    default List<T> findAllList() {
        return new ArrayList<>(this.findAll());
    }
}