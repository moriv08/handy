package ru.handy.handy.models.lead_heap;

import javax.persistence.*;

@Entity
@Table(name = "tb_weak_sides")
public class WeakSideEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String firstLast;
    private String badView;
    private String roof;
    private String cargo;
    private String gazStation;
    private String other;

    @OneToOne
    @JoinColumn(name = "lead_id")
    private LeadEntity lead;

    public WeakSideEntity() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstLast() {
        return firstLast;
    }

    public void setFirstLast(String firstLast) {
        this.firstLast = firstLast;
    }

    public String getBadView() {
        return badView;
    }

    public void setBadView(String badView) {
        this.badView = badView;
    }

    public String getRoof() {
        return roof;
    }

    public void setRoof(String roof) {
        this.roof = roof;
    }

    public String getCargo() {
        return cargo;
    }

    public void setCargo(String cargo) {
        this.cargo = cargo;
    }

    public String getGazStation() {
        return gazStation;
    }

    public void setGazStation(String gazStation) {
        this.gazStation = gazStation;
    }

    public String getOther() {
        return other;
    }

    public void setOther(String other) {
        this.other = other;
    }

    public LeadEntity getLead() {
        return lead;
    }

    public void setLead(LeadEntity lead) {
        this.lead = lead;
    }


}
