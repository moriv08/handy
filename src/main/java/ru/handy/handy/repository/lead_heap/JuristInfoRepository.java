package ru.handy.handy.repository.lead_heap;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.handy.handy.models.lead_heap.DocumentEntity;
import ru.handy.handy.models.lead_heap.JuristInfoEntity;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface JuristInfoRepository extends CrudRepository<JuristInfoEntity, Long> {


    //******* set lead */
    @Modifying
    @Query("update JuristInfoEntity juristInfo set juristInfo.requiredDocuments = ?2 where juristInfo.id = ?1")
    void setNewRequiredDocumentsToJuridicalInto(Long juryId, List<DocumentEntity> requaredDocuments);

//    /********** set appl */
//
//    @Modifying
//    @Query("update FotoDocsEntityOld fotodoc set fotodoc.juristicOpinion = ?2 where fotodoc.id = ?1")
//    void setJuristicOpinionByDocsFotoId(Long docsId, JuristicOpinionEntity juristicOpinionEntity);
    /************************************ basis flat info *********************************/
    @Modifying
    @Query("update JuristInfoEntity juristOpinion set juristOpinion.propertyBasis = ?2 where juristOpinion.id = ?1")
    void setJuristOpinionPropertyBasis(Long juristOpinionId, String propertyBasis);
    @Modifying
    @Query("update JuristInfoEntity juristOpinion set juristOpinion.owners = ?2 where juristOpinion.id = ?1")
    void setJuristOpinionOwners(Long juristOpinionId, String owner);
    @Modifying
    @Query("update JuristInfoEntity juristOpinion set juristOpinion.bankCargo = ?2 where juristOpinion.id = ?1")
    void setJuristOpinionBankCargo(Long juristOpinionId, String bankCargo);

    /*************** main contract type */
    @Modifying
    @Query("update JuristInfoEntity juristOpinion set juristOpinion.contractType = ?2 where juristOpinion.id = ?1")
    void setJuristOpinionContractType(Long juristOpinionId, String contractType);
    @Modifying
    @Query("update JuristInfoEntity juristOpinion set juristOpinion.contractStart = ?2 where juristOpinion.id = ?1")
    void setJuristOpinionContractStartDate(Long juristOpinionId, LocalDate contractDate);
    @Modifying
    @Query("update JuristInfoEntity juristOpinion set juristOpinion.contractEnd = ?2 where juristOpinion.id = ?1")
    void setJuristOpinionContractEndDate(Long juristicOpinionId, LocalDate contractEnd);

    /***************    prepayment type */
    @Modifying
    @Query("update JuristInfoEntity juristOpinion set juristOpinion.prepaymentType = ?2 where juristOpinion.id = ?1")
    void setJuristOpinionPrepaymentType(Long juristOpinionId, String prepaymentType);
    @Modifying
    @Query("update JuristInfoEntity juristOpinion set juristOpinion.prepaymentStart = ?2 where juristOpinion.id = ?1")
    void setJuristOpinionPrepaymentStart(Long juristOpinionId, LocalDate prepaymentDate);
    @Modifying
    @Query("update JuristInfoEntity juristOpinion set juristOpinion.prepaymentEnd = ?2 where juristOpinion.id = ?1")
    void setJuristOpinionPrepaymentEnd(Long juristOpinionId, LocalDate prepaymentEnd);
}
