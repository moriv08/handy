package ru.handy.handy.repository.lead_heap;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.handy.handy.models.lead_heap.DocumentEntity;

@Repository
public interface DocumentRepository extends CrudRepository<DocumentEntity, Long> {

    @Modifying
    @Query("update DocumentEntity doc set doc.docExisting = ?2 where doc.id = ?1")
    void setNewDocumentExistingInfo(Long docId, Boolean existing);
}
