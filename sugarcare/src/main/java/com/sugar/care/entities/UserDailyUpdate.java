package com.sugar.care.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.sugar.care.enums.AlertColour;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.Date;

@Entity
@Table(name = "userDailyUpdates")
@EntityListeners(AuditingEntityListener.class) //to enable jpa auditing
@JsonIgnoreProperties(value = {"creationDate", "updateDate" ,"colourStatus"}, allowGetters = true)
public class UserDailyUpdate implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "update_id")
    private long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonIgnore
    private User user;

    private boolean isReadingTakenAfterMeal;

    private int sugarReadingValue;

    @Enumerated(EnumType.STRING)
    private AlertColour colourStatus;

    @Column(name = "HB1AC_value")
    private int hb1ACvalue;

    @Column(name = "record_date", nullable = false)
    private LocalDate recordDate;

    @Column(nullable = false, updatable = false)
    @Temporal(TemporalType.TIMESTAMP)
    @CreatedDate
    private Date creationDate;

    @Column(nullable = false, updatable = true)
    @Temporal(TemporalType.TIMESTAMP)
    @LastModifiedDate
    private Date updateDate;

    public UserDailyUpdate() {

    }

    public long getId() {
        return id;
    }

    /*public void setId(long id) {
        this.id = id;
    }*/

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public boolean getIsReadingTakenAfterMeal() {
        return isReadingTakenAfterMeal;
    }

    public void setReadingTakenAfterMeal(boolean readingTakenAfterMeal) {
        isReadingTakenAfterMeal = readingTakenAfterMeal;
    }

    public int getSugarReadingValue() {
        return sugarReadingValue;
    }

    public void setSugarReadingValue(int sugarReadingValue) {
        this.sugarReadingValue = sugarReadingValue;
    }

    public int getHB1ACvalue() {
        return hb1ACvalue;
    }

    public void setHB1ACvalue(int HB1ACvalue) {
        this.hb1ACvalue = HB1ACvalue;
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

    public LocalDate getRecordDate() {
        return recordDate;
    }

    public void setRecordDate(LocalDate recordDate) {
        this.recordDate = recordDate;
    }


    public AlertColour getColourStatus() {
        return colourStatus;
    }

    public void setColourStatus(AlertColour colourStatus) {
        this.colourStatus = colourStatus;
    }

}
