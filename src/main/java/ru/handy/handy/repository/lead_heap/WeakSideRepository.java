package ru.handy.handy.repository.lead_heap;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.handy.handy.models.lead_heap.LeadEntity;
import ru.handy.handy.models.lead_heap.WeakSideEntity;

@Repository
public interface WeakSideRepository extends CrudRepository<WeakSideEntity, Long> {

    WeakSideEntity findByLeadId(Long id);

    @Modifying
    @Query("update WeakSideEntity weakSideEntity set weakSideEntity.firstLast = ?2 where weakSideEntity.lead = ?1")
    void setFirstLastDisadv(LeadEntity lead, String firstLast);
    @Modifying
    @Query("update WeakSideEntity weakSideEntity set weakSideEntity.badView = ?2 where weakSideEntity.lead = ?1")
    void setBadView(LeadEntity lead, String badView);
    @Modifying
    @Query("update WeakSideEntity weakSideEntity set weakSideEntity.cargo = ?2 where weakSideEntity.lead = ?1")
    void setCargo(LeadEntity lead, String cargo);
    @Modifying
    @Query("update WeakSideEntity weakSideEntity set weakSideEntity.gazStation = ?2 where weakSideEntity.lead = ?1")
    void setGazStation(LeadEntity lead, String gazStation);
    @Modifying
    @Query("update WeakSideEntity weakSideEntity set weakSideEntity.other = ?2 where weakSideEntity.lead = ?1")
    void setOther(LeadEntity lead, String other);
}
