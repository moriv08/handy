package ru.handy.handy.repository.lead_heap;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.handy.handy.models.lead_heap.FlatFotoEntity;

import java.util.List;

@Repository
public interface FlatFotoRepository extends CrudRepository<FlatFotoEntity, Long> {
    List<FlatFotoEntity> findAllByLeadId(Long id);
}
