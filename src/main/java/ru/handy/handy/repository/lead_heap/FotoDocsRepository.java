package ru.handy.handy.repository.lead_heap;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.handy.handy.models.lead_heap.FotoDocsEntity;
import ru.handy.handy.models.lead_heap.JuristInfoEntity;

import java.util.List;

@Repository
public interface FotoDocsRepository extends CrudRepository<FotoDocsEntity, Long> {
    List<FotoDocsEntity> findAllByJuristInfoId(Long id);

    @Modifying
    @Query("update FotoDocsEntity fotodoc set fotodoc.juristInfo = ?2 where fotodoc.id = ?1")
    void setJuristInfoToDocFotoById(Long docId, JuristInfoEntity juristInfo);
}
