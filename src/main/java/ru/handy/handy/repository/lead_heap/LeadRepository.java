package ru.handy.handy.repository.lead_heap;

import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.handy.handy.models.lead_heap.LeadEntity;

import java.awt.print.Pageable;
import java.time.LocalDate;
import java.util.List;

@Repository
public interface LeadRepository extends CrudRepository<LeadEntity, Long> {
    LeadEntity findLeadEntityById(Long id);

    List<LeadEntity> findAllByControlDateEqualsAndExpertId(LocalDate localDate, Long id);
    List<LeadEntity> findAllByControlDateEquals(LocalDate today);
    List<LeadEntity> findAllByControlDateBeforeAndExpertId(LocalDate localDate, Long id);
    List<LeadEntity> findAllByControlDateBefore(LocalDate localDate);
    List<LeadEntity> findAllByExpertId(Long id);
    List<LeadEntity> findAllByExpertName(String name);
    LeadEntity findLeadEntityByTel(String name);

    List<LeadEntity> findAllByLeadStatus(String leadStatus);

    //***************   1.1
    @Modifying
    @Query("update LeadEntity lead set lead.name = ?2 where lead.id = ?1")
    void setLeadOwnerName(Long leadId, String leadOwnerName);
    @Modifying
    @Query("update LeadEntity lead set lead.tel = ?2 where lead.id = ?1")
    void setTel(Long id, String tel);
    @Modifying
    @Query("update LeadEntity lead set lead.leadStatus = ?2, lead.leadDigitStatus = ?3 where lead.id = ?1")
    void setNewLeadStatus(Long id, String leadStatus, Integer digit);
    @Modifying
    @Query("update LeadEntity lead set lead.precontractType = ?2 where lead.id = ?1")
    void setPrecontractType(Long leadId, String precontractType);
    @Modifying
    @Query("update LeadEntity lead set lead.controlDate = ?2 where lead.id = ?1")
    void setLeadControlDate(Long leadId, LocalDate cuntrolDate);
    //  *****************

    //***************   1.2
    @Modifying
    @Query("update LeadEntity lead set lead.address = ?2 where lead.id = ?1")
    void setAddress(Long leadId, String address);
    @Modifying
    @Query("update LeadEntity lead set lead.metro = ?2 where lead.id = ?1")
    void setMetro(Long leadId, String metro);
    @Modifying
    @Query("update LeadEntity lead set lead.buildYear = ?2 where lead.id = ?1")
    void setBuildYear(Long leadId, String buildYear);
    @Modifying
    @Query("update LeadEntity lead set lead.houseType = ?2 where lead.id = ?1")
    void setHouseType(Long leadId, String houseType);
    @Modifying
    @Query("update LeadEntity lead set lead.flatFloor = ?2 where lead.id = ?1")
    void setFloor(Long leadId, String floor);
    @Modifying
    @Query("update LeadEntity lead set lead.flatRooms = ?2 where lead.id = ?1")
    void setRooms(Long leadId, String rooms);
    @Modifying
    @Query("update LeadEntity lead set lead.flatSquare = ?2 where lead.id = ?1")
    void setFlatSquare(Long leadId, String square);
    //  *****************


    //  1.6 set expert price
    @Modifying
    @Query("update LeadEntity lead set lead.expertPrice = ?2 where lead.id = ?1")
    void setExpertPrice(Long id, String expertPrice);
    //  1.7 set super price
    @Modifying
    @Query("update LeadEntity lead set lead.superPrice = ?2 where lead.id = ?1")
    void setSuperPrice(Long id, String superPrice);
    //  1.7 set super comment
    @Modifying
    @Query("update LeadEntity lead set lead.superComment = ?2 where lead.id = ?1")
    void setSuperComment(Long id, String superComment);





    //*******   2.1
    @Modifying
    @Query("update LeadEntity lead set lead.isCalled = ?2 where lead.id = ?1")
    void setFirstCall(Long id, boolean call);
    //*******   2.2
    @Modifying
    @Query("update LeadEntity lead set lead.clientAnswer = ?2, lead.clientDigitAnswer = ?3 where lead.id = ?1")
    void setClientAnswer(Long id, String answer, Integer digitAnswer);
    //*******   2.3
    @Modifying
    @Query("update LeadEntity lead set lead.emergencyStatusDigit = ?3, lead.statusEmergency = ?2 where lead.id = ?1")
    void setStatusEmergency(Long id, String statusEmergency, Integer digitEmergencyStatus);
    @Modifying
    @Query("update LeadEntity lead set lead.clientStatus = ?2, lead.clientDigitStatus = ?3 where lead.id = ?1")
    void setClientStatus(Long id, String clientStatus, Integer clientDigitStatus);

    //*******   4.1
    @Modifying
    @Query("update LeadEntity lead set lead.allCalls = ?2 where lead.id = ?1")
    void setAllCalls(Long leadId, Integer allCalls);

    @Modifying
    @Query("update LeadEntity lead set lead.allViews = ?2 where lead.id = ?1")
    void setAllViews(Long leadId, Integer allViews);

    @Modifying
    @Query("update LeadEntity lead set lead.briefComment = ?2 where lead.id = ?1")
    void setBriefComment(Long leadId, String brief);


    @Modifying
    @Query("update LeadEntity lead set lead.sellingText = ?2 where lead.id = ?1")
    void setAdvertismentByApplId(Long leadId, String sellingText);


}
