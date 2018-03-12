package com.iqa.profile.individual.model;

import javax.persistence.*;
import java.sql.Date;
import java.sql.Timestamp;

/**
 * Created by louis on 2018/07/25.
 */
@Entity
@Table(name = "individual_profile", schema = "iqa-16-05-2018", catalog = "")
public class IndividualProfileEntity {
    private Integer id;
    private String about;
    private String countryOfResidence;
    private Timestamp creationDate;
    private Date dateOfBirth;
    private String emailAddress;
    private Byte emailAddressNotification;
    private Byte enabled;
    private String firstName;
    private String gender;
    private String idIssuingAuthority;
    private String idNumber;
    private String mobileNumber;
    private Byte mobileNumberNotification;
    private String nationality;
    private String notificationId;
    private String password;
    private String picture;
    private String surname;
    private Integer userType;
    private String username;
    private String shares;
    private String skills;
    private String occupation;
    private Integer institute;
    private Integer roles;

    @Id
    @Column(name = "id", nullable = false)
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @Basic
    @Column(name = "about", nullable = true, length = 1000)
    public String getAbout() {
        return about;
    }

    public void setAbout(String about) {
        this.about = about;
    }

    @Basic
    @Column(name = "country_of_residence", nullable = true, length = 1000)
    public String getCountryOfResidence() {
        return countryOfResidence;
    }

    public void setCountryOfResidence(String countryOfResidence) {
        this.countryOfResidence = countryOfResidence;
    }

    @Basic
    @Column(name = "creation_date", nullable = false)
    public Timestamp getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Timestamp creationDate) {
        this.creationDate = creationDate;
    }

    @Basic
    @Column(name = "date_of_birth", nullable = true)
    public Date getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(Date dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    @Basic
    @Column(name = "email_address", nullable = true, length = 45)
    public String getEmailAddress() {
        return emailAddress;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    @Basic
    @Column(name = "email_address_notification", nullable = false)
    public Byte getEmailAddressNotification() {
        return emailAddressNotification;
    }

    public void setEmailAddressNotification(Byte emailAddressNotification) {
        this.emailAddressNotification = emailAddressNotification;
    }

    @Basic
    @Column(name = "enabled", nullable = false)
    public Byte getEnabled() {
        return enabled;
    }

    public void setEnabled(Byte enabled) {
        this.enabled = enabled;
    }

    @Basic
    @Column(name = "first_name", nullable = false, length = 45)
    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    @Basic
    @Column(name = "gender", nullable = false, length = 1)
    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    @Basic
    @Column(name = "id_issuing_authority", nullable = false, length = 45)
    public String getIdIssuingAuthority() {
        return idIssuingAuthority;
    }

    public void setIdIssuingAuthority(String idIssuingAuthority) {
        this.idIssuingAuthority = idIssuingAuthority;
    }

    @Basic
    @Column(name = "id_number", nullable = false, length = 45)
    public String getIdNumber() {
        return idNumber;
    }

    public void setIdNumber(String idNumber) {
        this.idNumber = idNumber;
    }

    @Basic
    @Column(name = "mobile_number", nullable = true, length = 45)
    public String getMobileNumber() {
        return mobileNumber;
    }

    public void setMobileNumber(String mobileNumber) {
        this.mobileNumber = mobileNumber;
    }

    @Basic
    @Column(name = "mobile_number_notification", nullable = false)
    public Byte getMobileNumberNotification() {
        return mobileNumberNotification;
    }

    public void setMobileNumberNotification(Byte mobileNumberNotification) {
        this.mobileNumberNotification = mobileNumberNotification;
    }

    @Basic
    @Column(name = "nationality", nullable = true, length = 45)
    public String getNationality() {
        return nationality;
    }

    public void setNationality(String nationality) {
        this.nationality = nationality;
    }

    @Basic
    @Column(name = "notification_id", nullable = true, length = 45)
    public String getNotificationId() {
        return notificationId;
    }

    public void setNotificationId(String notificationId) {
        this.notificationId = notificationId;
    }

    @Basic
    @Column(name = "password", nullable = false, length = 45)
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Basic
    @Column(name = "picture", nullable = true, length = -1)
    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    @Basic
    @Column(name = "surname", nullable = false, length = 45)
    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    @Basic
    @Column(name = "user_type", nullable = false)
    public Integer getUserType() {
        return userType;
    }

    public void setUserType(Integer userType) {
        this.userType = userType;
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
    @Column(name = "shares", nullable = true, length = -1)
    public String getShares() {
        return shares;
    }

    public void setShares(String shares) {
        this.shares = shares;
    }

    @Basic
    @Column(name = "skills", nullable = true, length = 2000)
    public String getSkills() {
        return skills;
    }

    public void setSkills(String skills) {
        this.skills = skills;
    }

    @Basic
    @Column(name = "occupation", nullable = true, length = 45)
    public String getOccupation() {
        return occupation;
    }

    public void setOccupation(String occupation) {
        this.occupation = occupation;
    }

    @Basic
    @Column(name = "institute", nullable = true)
    public Integer getInstitute() {
        return institute;
    }

    public void setInstitute(Integer institute) {
        this.institute = institute;
    }

    @Basic
    @Column(name = "roles", nullable = true)
    public Integer getRoles() {
        return roles;
    }

    public void setRoles(Integer roles) {
        this.roles = roles;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        IndividualProfileEntity that = (IndividualProfileEntity) o;

        if (id != null ? !id.equals(that.id) : that.id != null) return false;
        if (about != null ? !about.equals(that.about) : that.about != null) return false;
        if (countryOfResidence != null ? !countryOfResidence.equals(that.countryOfResidence) : that.countryOfResidence != null)
            return false;
        if (creationDate != null ? !creationDate.equals(that.creationDate) : that.creationDate != null) return false;
        if (dateOfBirth != null ? !dateOfBirth.equals(that.dateOfBirth) : that.dateOfBirth != null) return false;
        if (emailAddress != null ? !emailAddress.equals(that.emailAddress) : that.emailAddress != null) return false;
        if (emailAddressNotification != null ? !emailAddressNotification.equals(that.emailAddressNotification) : that.emailAddressNotification != null)
            return false;
        if (enabled != null ? !enabled.equals(that.enabled) : that.enabled != null) return false;
        if (firstName != null ? !firstName.equals(that.firstName) : that.firstName != null) return false;
        if (gender != null ? !gender.equals(that.gender) : that.gender != null) return false;
        if (idIssuingAuthority != null ? !idIssuingAuthority.equals(that.idIssuingAuthority) : that.idIssuingAuthority != null)
            return false;
        if (idNumber != null ? !idNumber.equals(that.idNumber) : that.idNumber != null) return false;
        if (mobileNumber != null ? !mobileNumber.equals(that.mobileNumber) : that.mobileNumber != null) return false;
        if (mobileNumberNotification != null ? !mobileNumberNotification.equals(that.mobileNumberNotification) : that.mobileNumberNotification != null)
            return false;
        if (nationality != null ? !nationality.equals(that.nationality) : that.nationality != null) return false;
        if (notificationId != null ? !notificationId.equals(that.notificationId) : that.notificationId != null)
            return false;
        if (password != null ? !password.equals(that.password) : that.password != null) return false;
        if (picture != null ? !picture.equals(that.picture) : that.picture != null) return false;
        if (surname != null ? !surname.equals(that.surname) : that.surname != null) return false;
        if (userType != null ? !userType.equals(that.userType) : that.userType != null) return false;
        if (username != null ? !username.equals(that.username) : that.username != null) return false;
        if (shares != null ? !shares.equals(that.shares) : that.shares != null) return false;
        if (skills != null ? !skills.equals(that.skills) : that.skills != null) return false;
        if (occupation != null ? !occupation.equals(that.occupation) : that.occupation != null) return false;
        if (institute != null ? !institute.equals(that.institute) : that.institute != null) return false;
        if (roles != null ? !roles.equals(that.roles) : that.roles != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (about != null ? about.hashCode() : 0);
        result = 31 * result + (countryOfResidence != null ? countryOfResidence.hashCode() : 0);
        result = 31 * result + (creationDate != null ? creationDate.hashCode() : 0);
        result = 31 * result + (dateOfBirth != null ? dateOfBirth.hashCode() : 0);
        result = 31 * result + (emailAddress != null ? emailAddress.hashCode() : 0);
        result = 31 * result + (emailAddressNotification != null ? emailAddressNotification.hashCode() : 0);
        result = 31 * result + (enabled != null ? enabled.hashCode() : 0);
        result = 31 * result + (firstName != null ? firstName.hashCode() : 0);
        result = 31 * result + (gender != null ? gender.hashCode() : 0);
        result = 31 * result + (idIssuingAuthority != null ? idIssuingAuthority.hashCode() : 0);
        result = 31 * result + (idNumber != null ? idNumber.hashCode() : 0);
        result = 31 * result + (mobileNumber != null ? mobileNumber.hashCode() : 0);
        result = 31 * result + (mobileNumberNotification != null ? mobileNumberNotification.hashCode() : 0);
        result = 31 * result + (nationality != null ? nationality.hashCode() : 0);
        result = 31 * result + (notificationId != null ? notificationId.hashCode() : 0);
        result = 31 * result + (password != null ? password.hashCode() : 0);
        result = 31 * result + (picture != null ? picture.hashCode() : 0);
        result = 31 * result + (surname != null ? surname.hashCode() : 0);
        result = 31 * result + (userType != null ? userType.hashCode() : 0);
        result = 31 * result + (username != null ? username.hashCode() : 0);
        result = 31 * result + (shares != null ? shares.hashCode() : 0);
        result = 31 * result + (skills != null ? skills.hashCode() : 0);
        result = 31 * result + (occupation != null ? occupation.hashCode() : 0);
        result = 31 * result + (institute != null ? institute.hashCode() : 0);
        result = 31 * result + (roles != null ? roles.hashCode() : 0);
        return result;
    }
}
