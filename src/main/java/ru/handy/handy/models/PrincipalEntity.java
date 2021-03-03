package ru.handy.handy.models;

import ru.handy.handy.models.lead_heap.LeadEntity;
import ru.handy.handy.models.statistic.MonthStatisticEntity;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name="tb_principals")
public class PrincipalEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String username;
    private String name;
    private String password;
    private String email;
    private String phone;
    private String role;

    @OneToMany(mappedBy = "principal")
    private List<MonthStatisticEntity> statistics;

    @OneToMany(mappedBy = "superuser")
    List<PrincipalEntity> experts;

    @ManyToOne
    @JoinColumn(name = "principal_id")
    private PrincipalEntity superuser;

    @OneToMany(mappedBy = "expert")
    private List<LeadEntity> leads;

    @ManyToOne
    @JoinColumn(name = "administrations_id")
    private AdministrationEntity administration;

    public PrincipalEntity() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public List<PrincipalEntity> getExperts() {
        return experts;
    }

    public void setExperts(List<PrincipalEntity> experts) {
        this.experts = experts;
    }

    public PrincipalEntity getSuperuser() {
        return superuser;
    }

    public void setSuperuser(PrincipalEntity superuser) {
        this.superuser = superuser;
    }

    public List<LeadEntity> getLeads() {
        return leads;
    }

    public void setLeads(List<LeadEntity> leads) {
        this.leads = leads;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public AdministrationEntity getAdministration() {
        return administration;
    }

    public void setAdministration(AdministrationEntity administration) {
        this.administration = administration;
    }

    public List<MonthStatisticEntity> getStatistics() {
        return statistics;
    }

    public void setStatistics(List<MonthStatisticEntity> statistics) {
        this.statistics = statistics;
    }
}
