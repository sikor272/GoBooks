package pl.umk.mat.gobooks.utils;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.NoRepositoryBean;
import pl.umk.mat.gobooks.utils.exceptions.ResourceNotFound;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@NoRepositoryBean
public interface BaseRepository<T, ID> extends CrudRepository<T, ID> {
    default T findByIdOrThrow(ID id) {
        Optional<T> entity = this.findById(id);
        if (entity.isPresent()) {
            return entity.get();
        } else {
            throw new ResourceNotFound();
        }
    }

    default List<T> findAllList() {
        List<T> list = new ArrayList<>();
        this.findAll().forEach(list::add);
        return list;
    }
}