package ru.handy.handy.models.lead_heap;

import javax.persistence.*;

@Entity
@Table(name = "tb_foto_docs")
public class FotoDocsEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "foto_doc")
    private byte[] fotoDoc;

    @ManyToOne
    @JoinColumn(name = "jurist_info_id")
    private JuristInfoEntity juristInfo;

    public FotoDocsEntity() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public byte[] getFotoDoc() {
        return fotoDoc;
    }

    public void setFotoDoc(byte[] fotoDoc) {
        this.fotoDoc = fotoDoc;
    }

    public JuristInfoEntity getJuristInfo() {
        return juristInfo;
    }

    public void setJuristInfo(JuristInfoEntity juristInfo) {
        this.juristInfo = juristInfo;
    }
}
