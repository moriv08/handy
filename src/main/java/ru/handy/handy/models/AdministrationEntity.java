package ru.handy.handy.models;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "tb_administrations")
public class AdministrationEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String announcement;

    @OneToMany(mappedBy = "administration")
    private List<PrincipalEntity> principals;

    public AdministrationEntity() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAnnouncement() {
        return announcement;
    }

    public void setAnnouncement(String announcement) {
        this.announcement = announcement;
    }

    public List<PrincipalEntity> getPrincipals() {
        return principals;
    }

    public void setPrincipals(List<PrincipalEntity> principals) {
        this.principals = principals;
    }
}
