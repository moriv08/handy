package ru.handy.handy.service.lead_heap;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.handy.handy.models.lead_heap.JuristInfoEntity;
import ru.handy.handy.models.lead_heap.LeadEntity;
import ru.handy.handy.repository.lead_heap.JuristInfoRepository;

import java.time.LocalDate;

@Service
public class JuristInfoService {
    private final JuristInfoRepository juristInfoRepository;

    public JuristInfoService(JuristInfoRepository juristInfoRepository) {
        this.juristInfoRepository = juristInfoRepository;
    }

    @Transactional
    public void createNewJuristInfo(JuristInfoEntity juristInfo){
        juristInfoRepository.save(juristInfo);
    }

    //**********************    main juridical info    *****/
    @Transactional
    public void setJuristOpinionPropertyBasis(LeadEntity lead, String propertyBasis){
        JuristInfoEntity juristInfo = lead.getJuristInfo();
        juristInfoRepository.setJuristOpinionPropertyBasis(juristInfo.getId(), propertyBasis);
    }
    @Transactional
    public void setJuristOpinionOwners(LeadEntity lead, String owners){
        JuristInfoEntity juristInfo = lead.getJuristInfo();
        juristInfoRepository.setJuristOpinionOwners(juristInfo.getId(), owners);
    }
    @Transactional
    public void setJuristOpinionBankCargo(LeadEntity lead, String bankCargo){
        JuristInfoEntity juristInfo = lead.getJuristInfo();
        juristInfoRepository.setJuristOpinionBankCargo(juristInfo.getId(), bankCargo);
    }
    //**********************    main contract info    *****/
    @Transactional
    public void setJuristOpinionContractType(LeadEntity lead, String contractType){
        JuristInfoEntity juristInfo = lead.getJuristInfo();
        juristInfoRepository.setJuristOpinionContractType(juristInfo.getId(), contractType);
    }
    @Transactional
    public void setJuristOpinionContractStartDate(LeadEntity lead, LocalDate contractDate){
        JuristInfoEntity juristInfo = lead.getJuristInfo();
        juristInfoRepository.setJuristOpinionContractStartDate(juristInfo.getId(), contractDate);
    }
    @Transactional
    public void setJuristicOpinionContractEndDate(LeadEntity lead, LocalDate contractEnd){
        JuristInfoEntity juristInfo = lead.getJuristInfo();
        juristInfoRepository.setJuristOpinionContractEndDate(juristInfo.getId(), contractEnd);
    }

    /************************************ prepayment type */
    @Transactional
    public void setJuristOpinionPrepaymentType(LeadEntity lead, String prepeymentType){
        JuristInfoEntity juristInfo = lead.getJuristInfo();
        juristInfoRepository.setJuristOpinionPrepaymentType(juristInfo.getId(), prepeymentType);
    }
    @Transactional
    public void setJuristOpinionPrepaymentStart(LeadEntity lead, LocalDate prepaymentStart){
        JuristInfoEntity juristInfo = lead.getJuristInfo();
        juristInfoRepository.setJuristOpinionPrepaymentStart(juristInfo.getId(), prepaymentStart);
    }
    @Transactional
    public void setJuristOpinionPrepaymentEnd(LeadEntity lead, LocalDate prepaymentEnd){
        JuristInfoEntity juristInfo = lead.getJuristInfo();
        juristInfoRepository.setJuristOpinionPrepaymentEnd(juristInfo.getId(), prepaymentEnd);
    }
}
