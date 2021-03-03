package ru.handy.handy.models.lead_heap;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "tb_jurist_info")
public class JuristInfoEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "lead_id")
    private LeadEntity lead;

    private String propertyBasis;
    private String owners;
    private String bankCargo;

    private String contractType;
    private LocalDate contractStart;
    private LocalDate contractEnd;

    private String prepaymentType;
    private LocalDate prepaymentStart;
    private LocalDate prepaymentEnd;

    @OneToMany(mappedBy = "juristInfo")
    private List<DocumentEntity> requiredDocuments;

    @OneToMany(mappedBy = "juristInfo")
    private List<FotoDocsEntity> fotoDocs;

    public JuristInfoEntity() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LeadEntity getLead() {
        return lead;
    }

    public void setLead(LeadEntity lead) {
        this.lead = lead;
    }

    public String getPropertyBasis() {
        return propertyBasis;
    }

    public void setPropertyBasis(String propertyBasis) {
        this.propertyBasis = propertyBasis;
    }

    public String getOwners() {
        return owners;
    }

    public void setOwners(String owners) {
        this.owners = owners;
    }

    public String getBankCargo() {
        return bankCargo;
    }

    public void setBankCargo(String bankCargo) {
        this.bankCargo = bankCargo;
    }

    public String getContractType() {
        return contractType;
    }

    public void setContractType(String contractType) {
        this.contractType = contractType;
    }

    public LocalDate getContractStart() {
        return contractStart;
    }

    public void setContractStart(LocalDate contractStart) {
        this.contractStart = contractStart;
    }

    public LocalDate getContractEnd() {
        return contractEnd;
    }

    public void setContractEnd(LocalDate contractEnd) {
        this.contractEnd = contractEnd;
    }

    public String getPrepaymentType() {
        return prepaymentType;
    }

    public void setPrepaymentType(String prepaymentType) {
        this.prepaymentType = prepaymentType;
    }

    public LocalDate getPrepaymentStart() {
        return prepaymentStart;
    }

    public void setPrepaymentStart(LocalDate prepaymentStart) {
        this.prepaymentStart = prepaymentStart;
    }

    public LocalDate getPrepaymentEnd() {
        return prepaymentEnd;
    }

    public void setPrepaymentEnd(LocalDate prepaymentEnd) {
        this.prepaymentEnd = prepaymentEnd;
    }

    public List<DocumentEntity> getRequiredDocuments() {
        return requiredDocuments;
    }

    public void setRequiredDocuments(List<DocumentEntity> requiredDocuments) {
        this.requiredDocuments = requiredDocuments;
    }

    public List<FotoDocsEntity> getFotoDocs() {
        return fotoDocs;
    }

    public void setFotoDocs(List<FotoDocsEntity> fotoDocs) {
        this.fotoDocs = fotoDocs;
    }


}
