package com.sugar.care.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.sugar.care.enums.DiabeticState;
import com.sugar.care.enums.Gender;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;


@Entity
@Table(name = "userProfiles")
@EntityListeners(AuditingEntityListener.class) //to enable jpa auditing
@JsonIgnoreProperties(value = {"creationDate", "updateDate" , "shouldConsultDoctor"},
        allowGetters = true)
public class UserAccount implements Serializable {

    @Id
    @Column(name = "user_id")
    private long id;

    @JoinColumn(name = "user_id")
    @OneToOne(fetch = FetchType.LAZY)
    @MapsId
    @JsonBackReference  //to save from infinite reference loop
    private User user;

    private int age;

    @Enumerated(EnumType.STRING)
    private Gender gender;

    private int weight;

    private int heightInFeet;

    private int heightInInches;

    private boolean hasHyperTension;

    private boolean isCardiacPatient;

    private boolean isCardiacSurgeryDone;

    @Enumerated(EnumType.STRING)
    @Column(columnDefinition = "varchar(255) default 'NORMAL'")
    private DiabeticState diabeticStage;

    @Temporal(TemporalType.DATE)
    private Date yearDetected;

    private boolean shouldConsultDoctor;

    @Column(nullable = false, updatable = false)
    @Temporal(TemporalType.TIMESTAMP)
    @CreatedDate
    private Date creationDate;

    @Column(nullable = false, updatable = true)
    @Temporal(TemporalType.TIMESTAMP)
    @LastModifiedDate
    private Date updateDate;

    public UserAccount() {

    }

    public long getId() {
        return id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public Gender getGender() {
        return gender;
    }

    public int getHeightInFeet() {
        return heightInFeet;
    }

    public void setHeightInFeet(int heightInFeet) {
        this.heightInFeet = heightInFeet;
    }

    public int getHeightInInches() {
        return heightInInches;
    }

    public void setHeightInInches(int heightInInches) {
        this.heightInInches = heightInInches;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    public boolean isHasHyperTension() {
        return hasHyperTension;
    }

    public void setHasHyperTension(boolean hasHyperTension) {
        this.hasHyperTension = hasHyperTension;
    }

    public boolean isCardiacPatient() {
        return isCardiacPatient;
    }

    public void setCardiacPatient(boolean cardiacPatient) {
        isCardiacPatient = cardiacPatient;
    }

    public boolean isCardiacSurgeryDone() {
        return isCardiacSurgeryDone;
    }

    public void setCardiacSurgeryDone(boolean cardiacSurgeryDone) {
        isCardiacSurgeryDone = cardiacSurgeryDone;
    }

    public DiabeticState getDiabeticStage() {
        return diabeticStage;
    }

    public void setDiabeticStage(DiabeticState diabeticStage) {
        this.diabeticStage = diabeticStage;
    }

    public Date getYearDetected() {
        return yearDetected;
    }

    public void setYearDetected(Date yearDetected) {
        this.yearDetected = yearDetected;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    public Date getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(Date updateDate) {
        this.updateDate = updateDate;
    }

    public boolean isShouldConsultDoctor() {
        return shouldConsultDoctor;
    }

    public void setShouldConsultDoctor(boolean shouldConsultDoctor) {
        this.shouldConsultDoctor = shouldConsultDoctor;
    }
}
