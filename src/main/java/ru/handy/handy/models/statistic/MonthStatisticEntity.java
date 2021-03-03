package ru.handy.handy.models.statistic;

import ru.handy.handy.models.PrincipalEntity;

import javax.persistence.*;
import java.security.Principal;

@Entity
@Table(name = "tb_statistics")
public class MonthStatisticEntity implements Comparable<MonthStatisticEntity>{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String month;
    private Integer leads;
    private Integer contracts;
    private Integer deals;

    @ManyToOne
    @JoinColumn(name = "principal_id")
    private PrincipalEntity principal;

    public MonthStatisticEntity() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public Integer getLeads() {
        return leads;
    }

    public void setLeads(Integer leads) {
        this.leads = leads;
    }

    public Integer getContracts() {
        return contracts;
    }

    public void setContracts(Integer contracts) {
        this.contracts = contracts;
    }

    public Integer getDeals() {
        return deals;
    }

    public void setDeals(Integer deals) {
        this.deals = deals;
    }

    public PrincipalEntity getPrincipal() {
        return principal;
    }

    public void setPrincipal(PrincipalEntity principal) {
        this.principal = principal;
    }

    @Override
    public int compareTo(MonthStatisticEntity o) {
        return (int)(this.getId() - o.getId());
    }
}
