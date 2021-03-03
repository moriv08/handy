package ru.handy.handy.models.lead_heap;

import ru.handy.handy.models.PrincipalEntity;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "tb_leads")
public class LeadEntity implements Comparable<LeadEntity>{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String tel;

    private LocalDate leadDate;
    private LocalDate controlDate;

    private String name;
    private String address;
    private String metro;
    private String metroDistance;
    private String houseType;
    private String buildYear;
    private String flatFloor;
    private String flatRooms;
    private String flatSquare;
    private String flatKitchen;
    private String flatBalcony;
    private String flatCondition;

    private String expertPrice;
    private String precontractType;
    private String superPrice;
    private String superComment;

    private Boolean isCalled;
    private String clientStatus;
    private Integer clientDigitStatus;
    private String clientAnswer;
    private Integer clientDigitAnswer;
    private String leadStatus;
    private Integer leadDigitStatus;
    private String statusEmergency;
    private Integer emergencyStatusDigit;

    private Integer allCalls;
    private Integer allViews;
    private String briefComment;
    private String sellingText;

    @ManyToOne
    @JoinColumn(name = "expert_id")
    private PrincipalEntity expert;

    @OneToOne(mappedBy = "lead", orphanRemoval = true)
    private BookingEntity booking;

    @OneToMany(mappedBy = "lead", orphanRemoval = true)
    private List<FlatFotoEntity> fotos ;

    @OneToOne(mappedBy = "lead", orphanRemoval = true)
    private WeakSideEntity weakSide;

    @OneToMany(mappedBy = "lead", orphanRemoval = true)
    private List<ExpertCommentEntity> expertComments;

    @OneToOne(mappedBy = "lead", orphanRemoval = true)
    private JuristInfoEntity juristInfo;

    public LeadEntity() {
    }

    public void setId(Long id) {
        this.id = id;
    }


    public Long getId() {
        return id;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public LocalDate getLeadDate() {
        return leadDate;
    }

    public void setLeadDate(LocalDate leadDate) {
        this.leadDate = leadDate;
    }

    public LocalDate getControlDate() {
        return controlDate;
    }

    public void setControlDate(LocalDate controlDate) {
        this.controlDate = controlDate;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getMetro() {
        return metro;
    }

    public void setMetro(String metro) {
        this.metro = metro;
    }

    public String getMetroDistance() {
        return metroDistance;
    }

    public void setMetroDistance(String metroDistance) {
        this.metroDistance = metroDistance;
    }

    public String getHouseType() {
        return houseType;
    }

    public void setHouseType(String houseType) {
        this.houseType = houseType;
    }

    public String getBuildYear() {
        return buildYear;
    }

    public void setBuildYear(String buildYear) {
        this.buildYear = buildYear;
    }

    public String getFlatFloor() {
        return flatFloor;
    }

    public void setFlatFloor(String flatFloor) {
        this.flatFloor = flatFloor;
    }

    public String getFlatRooms() {
        return flatRooms;
    }

    public void setFlatRooms(String flatRooms) {
        this.flatRooms = flatRooms;
    }

    public String getFlatSquare() {
        return flatSquare;
    }

    public void setFlatSquare(String flatSquare) {
        this.flatSquare = flatSquare;
    }

    public String getFlatKitchen() {
        return flatKitchen;
    }

    public void setFlatKitchen(String flatKitchen) {
        this.flatKitchen = flatKitchen;
    }

    public String getFlatBalcony() {
        return flatBalcony;
    }

    public void setFlatBalcony(String flatBalcony) {
        this.flatBalcony = flatBalcony;
    }

    public String getFlatCondition() {
        return flatCondition;
    }

    public void setFlatCondition(String flatCondition) {
        this.flatCondition = flatCondition;
    }

    public String getExpertPrice() {
        return expertPrice;
    }

    public void setExpertPrice(String expertPrice) {
        this.expertPrice = expertPrice;
    }

    public String getPrecontractType() {
        return precontractType;
    }

    public void setPrecontractType(String precontractType) {
        this.precontractType = precontractType;
    }

    public String getSuperPrice() {
        return superPrice;
    }

    public void setSuperPrice(String superPrice) {
        this.superPrice = superPrice;
    }

    public String getSuperComment() {
        return superComment;
    }

    public void setSuperComment(String superComment) {
        this.superComment = superComment;
    }

    public Boolean getCalled() {
        return isCalled;
    }

    public void setCalled(Boolean called) {
        isCalled = called;
    }

    public String getClientStatus() {
        return clientStatus;
    }

    public void setClientStatus(String clientStatus) {
        this.clientStatus = clientStatus;
    }

    public Integer getClientDigitStatus() {
        return clientDigitStatus;
    }

    public void setClientDigitStatus(Integer clientDigitStatus) {
        this.clientDigitStatus = clientDigitStatus;
    }

    public String getClientAnswer() {
        return clientAnswer;
    }

    public void setClientAnswer(String clientAnswer) {
        this.clientAnswer = clientAnswer;
    }

    public Integer getClientDigitAnswer() {
        return clientDigitAnswer;
    }

    public void setClientDigitAnswer(Integer clientDigitAnswer) {
        this.clientDigitAnswer = clientDigitAnswer;
    }

    public String getLeadStatus() {
        return leadStatus;
    }

    public void setLeadStatus(String leadStatus) {
        this.leadStatus = leadStatus;
    }

    public Integer getLeadDigitStatus() {
        return leadDigitStatus;
    }

    public void setLeadDigitStatus(Integer leadDigitStatus) {
        this.leadDigitStatus = leadDigitStatus;
    }

    public Integer getAllCalls() {
        return allCalls;
    }

    public void setAllCalls(Integer allCalls) {
        this.allCalls = allCalls;
    }

    public Integer getAllViews() {
        return allViews;
    }

    public void setAllViews(Integer allViews) {
        this.allViews = allViews;
    }

    public String getBriefComment() {
        return briefComment;
    }

    public void setBriefComment(String briefComment) {
        this.briefComment = briefComment;
    }

    public String getSellingText() {
        return sellingText;
    }

    public void setSellingText(String sellingText) {
        this.sellingText = sellingText;
    }

    public PrincipalEntity getExpert() {
        return expert;
    }

    public void setExpert(PrincipalEntity expert) {
        this.expert = expert;
    }

    public BookingEntity getBooking() {
        return booking;
    }

    public void setBooking(BookingEntity booking) {
        this.booking = booking;
    }

    public List<FlatFotoEntity> getFotos() {
        return fotos;
    }

    public void setFotos(List<FlatFotoEntity> fotos) {
        this.fotos = fotos;
    }

    public WeakSideEntity getWeakSide() {
        return weakSide;
    }

    public void setWeakSide(WeakSideEntity weakSide) {
        this.weakSide = weakSide;
    }

    public List<ExpertCommentEntity> getExpertComments() {
        return expertComments;
    }

    public void setExpertComments(List<ExpertCommentEntity> expertComments) {
        this.expertComments = expertComments;
    }

    public JuristInfoEntity getJuristInfo() {
        return juristInfo;
    }

    public void setJuristInfo(JuristInfoEntity juristInfo) {
        this.juristInfo = juristInfo;
    }

    public String getStatusEmergency() {
        return statusEmergency;
    }

    public void setStatusEmergency(String statusEmergency) {
        this.statusEmergency = statusEmergency;
    }

    public Integer getEmergencyStatusDigit() {
        return emergencyStatusDigit;
    }

    public void setEmergencyStatusDigit(Integer emergencyStatusDigit) {
        this.emergencyStatusDigit = emergencyStatusDigit;
    }

    @Override
    public int compareTo(LeadEntity o) {
        return this.getLeadDigitStatus() - o.getLeadDigitStatus();
    }
}

