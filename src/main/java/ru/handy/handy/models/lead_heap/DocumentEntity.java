package ru.handy.handy.models.lead_heap;

import javax.persistence.*;

@Entity
@Table(name = "tb_documents")
public class DocumentEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "jurist_info_id")
    private JuristInfoEntity juristInfo;

    private String doc;
    private Boolean docExisting;

    public DocumentEntity() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public JuristInfoEntity getJuristInfo() {
        return juristInfo;
    }

    public void setJuristInfo(JuristInfoEntity juristInfo) {
        this.juristInfo = juristInfo;
    }

    public String getDoc() {
        return doc;
    }

    public void setDoc(String doc) {
        this.doc = doc;
    }

    public Boolean getDocExisting() {
        return docExisting;
    }

    public void setDocExisting(Boolean docExisting) {
        this.docExisting = docExisting;
    }
}
