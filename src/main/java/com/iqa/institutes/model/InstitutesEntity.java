package com.iqa.institutes.model;

import javax.persistence.*;

/**
 * Created by louis on 2018/02/12.
 */
@Entity
@Table(name = "institutes", schema = "", catalog = "")
public class InstitutesEntity {
    private int id;
    private String name;
    private int country;
    private String picture;
    private int enabled;
    private String initials;
    private String about;
    private String phoneNumber;
    private String emailAddress;
    private String contactPerson;
    private String occupation;
    private String extras;
    private int type;

    @Id
    @Column(name = "id", nullable = false)
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Basic
    @Column(name = "name", nullable = false, length = 45)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Basic
    @Column(name = "country", nullable = false)
    public int getCountry() {
        return country;
    }

    public void setCountry(int country) {
        this.country = country;
    }

    @Basic
    @Column(name = "type", nullable = false)
    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
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
    @Column(name = "enabled", nullable = false)
    public int getEnabled() {
        return enabled;
    }

    public void setEnabled(int enabled) {
        this.enabled = enabled;
    }

    @Basic
    @Column(name = "initials", nullable = true, length = 45)
    public String getInitials() {
        return initials;
    }

    public void setInitials(String initials) {
        this.initials = initials;
    }

    @Basic
    @Column(name = "about", nullable = true, length = 2000)
    public String getAbout() {
        return about;
    }

    public void setAbout(String about) {
        this.about = about;
    }

    @Basic
    @Column(name = "phone_number", nullable = true, length = 45)
    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
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
    @Column(name = "contact_person", nullable = true, length = 45)
    public String getContactPerson() {
        return contactPerson;
    }

    public void setContactPerson(String contactPerson) {
        this.contactPerson = contactPerson;
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
    @Column(name = "extras", nullable = true, length = 2000)
    public String getExtras() {
        return extras;
    }

    public void setExtras(String extras) {
        this.extras = extras;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        InstitutesEntity that = (InstitutesEntity) o;

        if (id != that.id) return false;
        if (country != that.country) return false;
        if (enabled != that.enabled) return false;
        if (name != null ? !name.equals(that.name) : that.name != null) return false;
        if (picture != null ? !picture.equals(that.picture) : that.picture != null) return false;
        if (initials != null ? !initials.equals(that.initials) : that.initials != null) return false;
        if (about != null ? !about.equals(that.about) : that.about != null) return false;
        if (phoneNumber != null ? !phoneNumber.equals(that.phoneNumber) : that.phoneNumber != null) return false;
        if (emailAddress != null ? !emailAddress.equals(that.emailAddress) : that.emailAddress != null) return false;
        if (contactPerson != null ? !contactPerson.equals(that.contactPerson) : that.contactPerson != null)
            return false;
        if (occupation != null ? !occupation.equals(that.occupation) : that.occupation != null) return false;
        if (extras != null ? !extras.equals(that.extras) : that.extras != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + country;
        result = 31 * result + (picture != null ? picture.hashCode() : 0);
        result = 31 * result + enabled;
        result = 31 * result + (initials != null ? initials.hashCode() : 0);
        result = 31 * result + (about != null ? about.hashCode() : 0);
        result = 31 * result + (phoneNumber != null ? phoneNumber.hashCode() : 0);
        result = 31 * result + (emailAddress != null ? emailAddress.hashCode() : 0);
        result = 31 * result + (contactPerson != null ? contactPerson.hashCode() : 0);
        result = 31 * result + (occupation != null ? occupation.hashCode() : 0);
        result = 31 * result + (extras != null ? extras.hashCode() : 0);
        return result;
    }
}
