package com.iqa.profiles.model;

import javax.persistence.*;
import java.sql.Date;

/**
 * Created by louis on 2018/02/16.
 */
@Entity
@Table(name = "profile", schema = "", catalog = "")
public class ProfileEntity {
    private int id;
    private String username;
    private String password;
    private Date creationDate;
    private String emailAddress;
    private String phoneNumber;
    private int countryId;
    private String userType;
    private int emailNotification;
    private int mobileNotification;
    private String idNumber;
    private String registrationNumber;
    private int enabled;
    private String picture;
    private int kyc;
    private Double balanceAmount;
    private Date topUpDate;
    private Integer status;
    private String extras;
    private String name;

    @Id
    @Column(name = "id", nullable = false)
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Basic
    @Column(name = "username", nullable = false, length = 45)
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Basic
    @Column(name = "password", nullable = false, length = 2000)
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Basic
    @Column(name = "creation_date", nullable = false)
    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    @Basic
    @Column(name = "email_address", nullable = false, length = 45)
    public String getEmailAddress() {
        return emailAddress;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    @Basic
    @Column(name = "phone_number", nullable = false, length = 45)
    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    @Basic
    @Column(name = "country_id", nullable = false)
    public int getCountryId() {
        return countryId;
    }

    public void setCountryId(int countryId) {
        this.countryId = countryId;
    }

    @Basic
    @Column(name = "user_type", nullable = false, length = 45)
    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }

    @Basic
    @Column(name = "email_notification", nullable = false)
    public int getEmailNotification() {
        return emailNotification;
    }

    public void setEmailNotification(int emailNotification) {
        this.emailNotification = emailNotification;
    }

    @Basic
    @Column(name = "mobile_notification", nullable = false)
    public int getMobileNotification() {
        return mobileNotification;
    }

    public void setMobileNotification(int mobileNotification) {
        this.mobileNotification = mobileNotification;
    }

    @Basic
    @Column(name = "id_number", nullable = true, length = 100)
    public String getIdNumber() {
        return idNumber;
    }

    public void setIdNumber(String idNumber) {
        this.idNumber = idNumber;
    }

    @Basic
    @Column(name = "registration_number", nullable = true, length = 100)
    public String getRegistrationNumber() {
        return registrationNumber;
    }

    public void setRegistrationNumber(String registrationNumber) {
        this.registrationNumber = registrationNumber;
    }

    @Basic
    @Column(name = "enabled", nullable = false)
    public int getEnabled() {
        return enabled;
    }

    public void setEnabled(int enabled) {
        this.enabled = enabled;
    }

    @Basic
    @Column(name = "picture", nullable = true, length = 17000000)
    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    @Basic
    @Column(name = "kyc", nullable = false)
    public int getKyc() {
        return kyc;
    }

    public void setKyc(int kyc) {
        this.kyc = kyc;
    }

    @Basic
    @Column(name = "balance_amount", nullable = true, precision = 0)
    public Double getBalanceAmount() {
        return balanceAmount;
    }

    public void setBalanceAmount(Double balanceAmount) {
        this.balanceAmount = balanceAmount;
    }

    @Basic
    @Column(name = "top_up_date", nullable = true)
    public Date getTopUpDate() {
        return topUpDate;
    }

    public void setTopUpDate(Date topUpDate) {
        this.topUpDate = topUpDate;
    }

    @Basic
    @Column(name = "status", nullable = true)
    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    @Basic
    @Column(name = "extras", nullable = true, length = 1000)
    public String getExtras() {
        return extras;
    }

    public void setExtras(String extras) {
        this.extras = extras;
    }

    @Basic
    @Column(name = "name", nullable = false, length = 100)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ProfileEntity that = (ProfileEntity) o;

        if (id != that.id) return false;
        if (countryId != that.countryId) return false;
        if (emailNotification != that.emailNotification) return false;
        if (mobileNotification != that.mobileNotification) return false;
        if (enabled != that.enabled) return false;
        if (kyc != that.kyc) return false;
        if (username != null ? !username.equals(that.username) : that.username != null) return false;
        if (password != null ? !password.equals(that.password) : that.password != null) return false;
        if (creationDate != null ? !creationDate.equals(that.creationDate) : that.creationDate != null) return false;
        if (emailAddress != null ? !emailAddress.equals(that.emailAddress) : that.emailAddress != null) return false;
        if (phoneNumber != null ? !phoneNumber.equals(that.phoneNumber) : that.phoneNumber != null) return false;
        if (userType != null ? !userType.equals(that.userType) : that.userType != null) return false;
        if (idNumber != null ? !idNumber.equals(that.idNumber) : that.idNumber != null) return false;
        if (registrationNumber != null ? !registrationNumber.equals(that.registrationNumber) : that.registrationNumber != null)
            return false;
        if (picture != null ? !picture.equals(that.picture) : that.picture != null) return false;
        if (balanceAmount != null ? !balanceAmount.equals(that.balanceAmount) : that.balanceAmount != null)
            return false;
        if (topUpDate != null ? !topUpDate.equals(that.topUpDate) : that.topUpDate != null) return false;
        if (status != null ? !status.equals(that.status) : that.status != null) return false;
        if (extras != null ? !extras.equals(that.extras) : that.extras != null) return false;
        if (name != null ? !name.equals(that.name) : that.name != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (username != null ? username.hashCode() : 0);
        result = 31 * result + (password != null ? password.hashCode() : 0);
        result = 31 * result + (creationDate != null ? creationDate.hashCode() : 0);
        result = 31 * result + (emailAddress != null ? emailAddress.hashCode() : 0);
        result = 31 * result + (phoneNumber != null ? phoneNumber.hashCode() : 0);
        result = 31 * result + countryId;
        result = 31 * result + (userType != null ? userType.hashCode() : 0);
        result = 31 * result + emailNotification;
        result = 31 * result + mobileNotification;
        result = 31 * result + (idNumber != null ? idNumber.hashCode() : 0);
        result = 31 * result + (registrationNumber != null ? registrationNumber.hashCode() : 0);
        result = 31 * result + enabled;
        result = 31 * result + (picture != null ? picture.hashCode() : 0);
        result = 31 * result + kyc;
        result = 31 * result + (balanceAmount != null ? balanceAmount.hashCode() : 0);
        result = 31 * result + (topUpDate != null ? topUpDate.hashCode() : 0);
        result = 31 * result + (status != null ? status.hashCode() : 0);
        result = 31 * result + (extras != null ? extras.hashCode() : 0);
        result = 31 * result + (name != null ? name.hashCode() : 0);
        return result;
    }
}
