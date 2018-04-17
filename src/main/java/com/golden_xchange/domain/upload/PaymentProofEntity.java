package com.golden_xchange.domain.upload;

import javax.persistence.*;

import java.sql.Timestamp;
import java.util.Arrays;
import java.util.Date;
import java.util.Objects;

@Entity
@Table(name = "payment_proof", catalog = "")
public class PaymentProofEntity {
    private int id;
    private String username;
    private String depositReference;
    private String file;
    private Date date;
    private String extension;
    private int status;

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
    @Column(name = "deposit_reference", nullable = false, length = 45)
    public String getDepositReference() {
        return depositReference;
    }

    public void setDepositReference(String depositReference) {
        this.depositReference = depositReference;
    }

    @Basic
    @Column(name = "file", nullable = false,length = 17000000)
    public String getFile() {
        return file;
    }

    public void setFile(String file) {
        this.file = file;
    }

    @Basic
    @Column(name = "date", nullable = false)
    public Date getDate() {
        return date;
    }

    public void setDate(Timestamp date) {
        this.date = date;
    }

    @Basic
    @Column(name = "extension", nullable = false, length = 45)
    public String getExtension() {
        return extension;
    }

    public void setExtension(String extension) {
        this.extension = extension;
    }

    @Basic
    @Column(name = "status", nullable = false)
    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PaymentProofEntity that = (PaymentProofEntity) o;
        return id == that.id &&
                status == that.status &&
                Objects.equals(username, that.username) &&
                Objects.equals(depositReference, that.depositReference) &&
                Objects.equals(date, that.date) &&
                Objects.equals(extension, that.extension);
    }

    @Override
    public int hashCode() {

        int result = Objects.hash(id, username, depositReference, date, extension, status);
        return result;
    }
}
