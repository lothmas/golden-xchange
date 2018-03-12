package com.iqa.verifiedcandidates.model;

import javax.persistence.*;
import java.sql.Date;

/**
 * Created by louis on 2018/02/16.
 */
@Entity
@Table(name = "candidates_verified", schema = "", catalog = "")
public class CandidatesVerifiedEntity {
    private int id;
    private String candidateNumber;
    private int institution;
    private String enabled;
    private Date dateAwarded;
    private String certificateNumber;
    private String firstName;
    private String surname;
    private Date dob;
    private String idNumber;
    private String program;
    private Integer outcome;
    private Integer progressStatus;
    private Date updateDate;
    private int requesterId;

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
    @Column(name = "requester_id", nullable = false)
    public int getRequesterId() {
        return requesterId;
    }

    public void setRequesterId(int requesterId) {
        this.requesterId = requesterId;
    }


    @Basic
    @Column(name = "enabled", nullable = false, length = 45)
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
    @Column(name = "certificate_number", nullable = true, length = 45)
    public String getCertificateNumber() {
        return certificateNumber;
    }

    public void setCertificateNumber(String certificateNumber) {
        this.certificateNumber = certificateNumber;
    }

    @Basic
    @Column(name = "first_name", nullable = true, length = 45)
    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    @Basic
    @Column(name = "surname", nullable = true, length = 45)
    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    @Basic
    @Column(name = "dob", nullable = true)
    public Date getDob() {
        return dob;
    }

    public void setDob(Date dob) {
        this.dob = dob;
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
    @Column(name = "program", nullable = false, length = 200)
    public String getProgram() {
        return program;
    }

    public void setProgram(String program) {
        this.program = program;
    }

    @Basic
    @Column(name = "outcome", nullable = true)
    public Integer getOutcome() {
        return outcome;
    }

    public void setOutcome(Integer outcome) {
        this.outcome = outcome;
    }

    @Basic
    @Column(name = "progress_status", nullable = true)
    public Integer getProgressStatus() {
        return progressStatus;
    }

    public void setProgressStatus(Integer progressStatus) {
        this.progressStatus = progressStatus;
    }

    @Basic
    @Column(name = "update_date", nullable = true)
    public Date getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(Date updateDate) {
        this.updateDate = updateDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        CandidatesVerifiedEntity that = (CandidatesVerifiedEntity) o;

        if (id != that.id) return false;
        if (institution != that.institution) return false;
        if (candidateNumber != null ? !candidateNumber.equals(that.candidateNumber) : that.candidateNumber != null)
            return false;
        if (enabled != null ? !enabled.equals(that.enabled) : that.enabled != null) return false;
        if (dateAwarded != null ? !dateAwarded.equals(that.dateAwarded) : that.dateAwarded != null) return false;
        if (certificateNumber != null ? !certificateNumber.equals(that.certificateNumber) : that.certificateNumber != null)
            return false;
        if (firstName != null ? !firstName.equals(that.firstName) : that.firstName != null) return false;
        if (surname != null ? !surname.equals(that.surname) : that.surname != null) return false;
        if (dob != null ? !dob.equals(that.dob) : that.dob != null) return false;
        if (idNumber != null ? !idNumber.equals(that.idNumber) : that.idNumber != null) return false;
        if (program != null ? !program.equals(that.program) : that.program != null) return false;
        if (outcome != null ? !outcome.equals(that.outcome) : that.outcome != null) return false;
        if (progressStatus != null ? !progressStatus.equals(that.progressStatus) : that.progressStatus != null)
            return false;
        if (updateDate != null ? !updateDate.equals(that.updateDate) : that.updateDate != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (candidateNumber != null ? candidateNumber.hashCode() : 0);
        result = 31 * result + institution;
        result = 31 * result + (enabled != null ? enabled.hashCode() : 0);
        result = 31 * result + (dateAwarded != null ? dateAwarded.hashCode() : 0);
        result = 31 * result + (certificateNumber != null ? certificateNumber.hashCode() : 0);
        result = 31 * result + (firstName != null ? firstName.hashCode() : 0);
        result = 31 * result + (surname != null ? surname.hashCode() : 0);
        result = 31 * result + (dob != null ? dob.hashCode() : 0);
        result = 31 * result + (idNumber != null ? idNumber.hashCode() : 0);
        result = 31 * result + (program != null ? program.hashCode() : 0);
        result = 31 * result + (outcome != null ? outcome.hashCode() : 0);
        result = 31 * result + (progressStatus != null ? progressStatus.hashCode() : 0);
        result = 31 * result + (updateDate != null ? updateDate.hashCode() : 0);
        return result;
    }
}
