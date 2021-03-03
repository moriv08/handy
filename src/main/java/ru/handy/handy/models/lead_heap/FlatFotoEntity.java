package ru.handy.handy.models.lead_heap;

import javax.persistence.*;

@Entity
@Table(name = "tb_flat_fotos")
public class FlatFotoEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private byte[] foto;

    @ManyToOne
    @JoinColumn(name = "lead_id")
    private LeadEntity lead;

    public FlatFotoEntity() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public byte[] getFoto() {
        return foto;
    }

    public void setFoto(byte[] foto) {
        this.foto = foto;
    }

    public LeadEntity getLead() {
        return lead;
    }

    public void setLead(LeadEntity lead) {
        this.lead = lead;
    }
}
