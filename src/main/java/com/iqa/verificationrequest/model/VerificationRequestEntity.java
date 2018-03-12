package com.iqa.verificationrequest.model;

import javax.persistence.*;
import java.sql.Date;

/**
 * Created by louis on 2018/02/12.
 */
@Entity
@Table(name = "verification_request", schema = "", catalog = "")
public class VerificationRequestEntity {
    private int id;
    private String candidateNumber;
    private int institute;
    private Date requestDate;
    private int processStatus;
    private int enabled;
    private int requesterId;
    private int paymentStatus;
    private double amountPaid;
    private int notificationStatus;

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
    @Column(name = "institute", nullable = false)
    public int getInstitute() {
        return institute;
    }

    public void setInstitute(int institute) {
        this.institute = institute;
    }

    @Basic
    @Column(name = "request_date", nullable = false)
    public Date getRequestDate() {
        return requestDate;
    }

    public void setRequestDate(Date requestDate) {
        this.requestDate = requestDate;
    }

    @Basic
    @Column(name = "process_status", nullable = false)
    public int getProcessStatus() {
        return processStatus;
    }

    public void setProcessStatus(int processStatus) {
        this.processStatus = processStatus;
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
    @Column(name = "requester_id", nullable = false, length = 45)
    public int getRequesterId() {
        return requesterId;
    }

    public void setRequesterId(int requesterId) {
        this.requesterId = requesterId;
    }

    @Basic
    @Column(name = "payment_status", nullable = false, length = 45)
    public int getPaymentStatus() {
        return paymentStatus;
    }

    public void setPaymentStatus(int paymentStatus) {
        this.paymentStatus = paymentStatus;
    }

    @Basic
    @Column(name = "amount_paid", nullable = false, length = 45)
    public double getAmountPaid() {
        return amountPaid;
    }

    public void setAmountPaid(double amountPaid) {
        this.amountPaid = amountPaid;
    }

    @Basic
    @Column(name = "notification_status", nullable = false)
    public int getNotificationStatus() {
        return notificationStatus;
    }

    public void setNotificationStatus(int notificationStatus) {
        this.notificationStatus = notificationStatus;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        VerificationRequestEntity that = (VerificationRequestEntity) o;

        if (id != that.id) return false;
        if (institute != that.institute) return false;
        if (processStatus != that.processStatus) return false;
        if (enabled != that.enabled) return false;
        if (notificationStatus != that.notificationStatus) return false;
        if (candidateNumber != null ? !candidateNumber.equals(that.candidateNumber) : that.candidateNumber != null)
            return false;
        if (requestDate != null ? !requestDate.equals(that.requestDate) : that.requestDate != null) return false;
        if (requesterId != that.requesterId ) return false;
        if (paymentStatus != that.paymentStatus) return false;
        if (amountPaid != that.amountPaid ) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (candidateNumber != null ? candidateNumber.hashCode() : 0);
        result = 31 * result + institute;
        result = 31 * result + (requestDate != null ? requestDate.hashCode() : 0);
        result = 31 * result + processStatus;
        result = 31 * result + enabled;
        result = 31 * result + requesterId;
        result = 31 * result + paymentStatus;
        result = 31 * result + notificationStatus;
        return result;
    }
}
