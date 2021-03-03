package ru.handy.handy.repository.lead_heap;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.handy.handy.models.lead_heap.ExpertCommentEntity;

@Repository
public interface ExpertCommentRepository extends CrudRepository<ExpertCommentEntity, Long> {
}
