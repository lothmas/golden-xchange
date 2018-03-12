package com.iqa.verification;

import javax.persistence.*;
import java.sql.Date;

/**
 * Created by louis on 2018/02/09.
 */
@Entity
@Table(name = "verification", schema = "", catalog = "")
public class VerificationEntity {
    private int id;
    private String candidateNumber;
    private int institution;
    private String enabled;
    private Date dateAwarded;
    private Integer country;
    private String idNumber;
    private Integer userId;

    @Id
    @Column(name = "id", nullable = false)
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Basic
    @Column(name = "candidate_number", nullable = false, length = 45)
    public String getCandidateNumber() {
        return candidateNumber;
    }

    public void setCandidateNumber(String candidateNumber) {
        this.candidateNumber = candidateNumber;
    }

    @Basic
    @Column(name = "institution", nullable = false)
    public int getInstitution() {
        return institution;
    }

    public void setInstitution(int institution) {
        this.institution = institution;
    }

    @Basic
    @Column(name = "enabled", nullable = true, length = 45)
    public String getEnabled() {
        return enabled;
    }

    public void setEnabled(String enabled) {
        this.enabled = enabled;
    }

    @Basic
    @Column(name = "date_awarded", nullable = true)
    public Date getDateAwarded() {
        return dateAwarded;
    }

    public void setDateAwarded(Date dateAwarded) {
        this.dateAwarded = dateAwarded;
    }

    @Basic
    @Column(name = "country", nullable = true)
    public Integer getCountry() {
        return country;
    }

    public void setCountry(Integer country) {
        this.country = country;
    }

    @Basic
    @Column(name = "id_number", nullable = true, length = 45)
    public String getIdNumber() {
        return idNumber;
    }

    public void setIdNumber(String idNumber) {
        this.idNumber = idNumber;
    }

    @Basic
    @Column(name = "user_id", nullable = true)
    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        VerificationEntity that = (VerificationEntity) o;

        if (id != that.id) return false;
        if (institution != that.institution) return false;
        if (candidateNumber != null ? !candidateNumber.equals(that.candidateNumber) : that.candidateNumber != null)
            return false;
        if (enabled != null ? !enabled.equals(that.enabled) : that.enabled != null) return false;
        if (dateAwarded != null ? !dateAwarded.equals(that.dateAwarded) : that.dateAwarded != null) return false;
        if (country != null ? !country.equals(that.country) : that.country != null) return false;
        if (idNumber != null ? !idNumber.equals(that.idNumber) : that.idNumber != null) return false;
        if (userId != null ? !userId.equals(that.userId) : that.userId != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (candidateNumber != null ? candidateNumber.hashCode() : 0);
        result = 31 * result + institution;
        result = 31 * result + (enabled != null ? enabled.hashCode() : 0);
        result = 31 * result + (dateAwarded != null ? dateAwarded.hashCode() : 0);
        result = 31 * result + (country != null ? country.hashCode() : 0);
        result = 31 * result + (idNumber != null ? idNumber.hashCode() : 0);
        result = 31 * result + (userId != null ? userId.hashCode() : 0);
        return result;
    }
}
