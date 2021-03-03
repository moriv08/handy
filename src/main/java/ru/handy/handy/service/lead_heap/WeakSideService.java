package ru.handy.handy.service.lead_heap;

import org.springframework.data.annotation.ReadOnlyProperty;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.handy.handy.models.lead_heap.LeadEntity;
import ru.handy.handy.models.lead_heap.WeakSideEntity;
import ru.handy.handy.repository.lead_heap.WeakSideRepository;

@Service
public class WeakSideService {
    private final WeakSideRepository weakSideRepository;

    public WeakSideService(WeakSideRepository weakSideRepository) {
        this.weakSideRepository = weakSideRepository;
    }

    @Transactional
    @ReadOnlyProperty
    public WeakSideEntity findWeakSideByLead(Long id){
        return weakSideRepository.findByLeadId(id);
    }

    @Transactional
    public void createWeakSideEntity(WeakSideEntity weakSide){
        weakSideRepository.save(weakSide);
    }

    @Transactional
    public void setFirstLastDisadv(LeadEntity lead, String firstLast){
        weakSideRepository.setFirstLastDisadv(lead, firstLast);
    }
    @Transactional
    public void setBadView(LeadEntity lead, String badView){
        weakSideRepository.setBadView(lead, badView);
    }
    @Transactional
    public void setCargo(LeadEntity lead, String cargo){
        weakSideRepository.setCargo(lead, cargo);
    }
    @Transactional
    public void setGazStation(LeadEntity lead, String gazStation){
        weakSideRepository.setGazStation(lead, gazStation);
    }
    @Transactional
    public void setOther(LeadEntity lead, String other){
        weakSideRepository.setOther(lead, other);
    }

}
