package com.sugar.care.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.sugar.care.enums.UserStatuses;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import javax.transaction.Transactional;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "users")
@EntityListeners(AuditingEntityListener.class) //to enable jpa auditing
@Transactional
@JsonIgnoreProperties(value = {"creationDate", "updateDate"},
        allowGetters = true)
public class User implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private long id;

    @Column(nullable = false)
    @NotEmpty(message = "Name should not be empty")
    private String firstName;

    private String lastName;


    @OneToOne(mappedBy = "user", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @PrimaryKeyJoinColumn
    @JsonManagedReference  //to save from infinite reference loop
    private UserAccount account;

    @Column(unique = true, updatable = false)
    @Size(min = 10, max = 10)
    private String phoneNumber;

    @Size(min = 4, max = 32)
    private String password;

    @Enumerated(EnumType.STRING)
    private UserStatuses status;

    private boolean firstTimeLogin;

    @Column(nullable = false, updatable = false)
    @Temporal(TemporalType.TIMESTAMP)
    @CreatedDate
    private Date creationDate;

    @Column(nullable = false, updatable = false)
    @Temporal(TemporalType.TIMESTAMP)
    @LastModifiedDate
    private Date updateDate;

    public User() {
    }

    public long getId() {
        return id;
    }

    /*public void setId(long id) {
        this.id = id;
    }*/

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public UserStatuses getStatus() {
        return status;
    }

    public void setStatus(UserStatuses status) {
        this.status = status;
    }

    public boolean isFirstTimeLogin() {
        return firstTimeLogin;
    }

    public void setFirstTimeLogin(boolean firstTimeLogin) {
        this.firstTimeLogin = firstTimeLogin;
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

    public UserAccount getAccount() {
        return account;
    }

    public void setAccount(UserAccount account) {
        this.account = account;
    }
}
