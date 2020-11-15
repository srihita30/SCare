package com.sugar.care.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
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
@JsonIgnoreProperties(value = {"creationDate", "updateDate"},
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

    private int height;

    private boolean hasHyperTension;

    private boolean isCardiacPatient;

    private boolean isCardiacSurgeryDone;

    private int sugarLevel;

    @Temporal(TemporalType.DATE)
    private Date yearDetected;

    @Column(nullable = false, updatable = false)
    @Temporal(TemporalType.TIMESTAMP)
    @CreatedDate
    private Date creationDate;

    @Column(nullable = false, updatable = false)
    @Temporal(TemporalType.TIMESTAMP)
    @LastModifiedDate
    private Date updateDate;

    public UserAccount() {

    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
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

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
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

    public int getSugarLevel() {
        return sugarLevel;
    }

    public void setSugarLevel(int sugarLevel) {
        this.sugarLevel = sugarLevel;
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
}
