//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.golden_xchange.domain.mainlist.model;

import org.hibernate.envers.Audited;

import javax.persistence.*;
import java.sql.Timestamp;


@Entity
@Table(name = "main_list", schema = "", catalog = "")
public class MainListEntity {
    private String userName;
    private int id;
    private String mainListReference;
    private String bankAccountNumber;
    private double donatedAmount;
    private double amountToReceive;
    private double adjustedAmount;
    private int status;
    private int enabled;
    private Timestamp date;
    private String payerUsername;
    private String donationReference;
    private String depositReference;
    private Timestamp updatedDate;
    private int donationType;

    public MainListEntity() {
    }

    public Timestamp getUpdatedDate() {
        return this.updatedDate;
    }

    public void setUpdatedDate(Timestamp updatedDate) {
        this.updatedDate = updatedDate;
    }

    @Id
    @Column(name = "id", nullable = false)
    public int getId() {
        return this.id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Basic
    @Column(name = "main_list_reference", nullable = false, length = 45)
    public String getMainListReference() {
        return this.mainListReference;
    }

    public void setMainListReference(String mainListReference) {
        this.mainListReference = mainListReference;
    }

    @Basic
    @Column(name = "bank_account_number", nullable = false, length = 45)
    public String getBankAccountNumber() {
        return this.bankAccountNumber;
    }

    public void setBankAccountNumber(String bankAccountNumber) {
        this.bankAccountNumber = bankAccountNumber;
    }

    @Basic
    @Column(name = "donated_amount", nullable = false, length = 45)
    public double getDonatedAmount() {
        return this.donatedAmount;
    }

    public void setDonatedAmount(double donatedAmount) {
        this.donatedAmount = donatedAmount;
    }

    @Basic
    @Column(name = "amount_to_receive", nullable = false)
    public double getAmountToReceive() {
        return this.amountToReceive;
    }

    public void setAmountToReceive(double amountToReceive) {
        this.amountToReceive = amountToReceive;
    }

    @Basic
    @Column(name = "adjusted_amount", nullable = false)
    public double getAdjustedAmount() {
        return this.adjustedAmount;
    }

    public void setAdjustedAmount(double adjustedAmount) {
        this.adjustedAmount = adjustedAmount;
    }

    @Basic
    @Column(name = "status", nullable = false)
    public int getStatus() {
        return this.status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    @Basic
    @Column(name = "enabled", nullable = false, length = 1)
    public int getEnabled() {
        return this.enabled;
    }

    public void setEnabled(int enabled) {
        this.enabled = enabled;
    }

    @Basic
    @Column(name = "date", nullable = false, length = 45)
    public Timestamp getDate() {
        return this.date;
    }

    public void setDate(Timestamp date) {
        this.date = date;
    }

    @Basic
    @Column(name = "payer_user_name", nullable = false, length = 45)
    public String getPayerUsername() {
        return this.payerUsername;
    }

    public void setPayerUsername(String payerUsername) {
        this.payerUsername = payerUsername;
    }

    @Basic
    @Column(name = "donation_reference", nullable = false, length = 45)
    public String getDonationReference() {
        return this.donationReference;
    }

    public void setDonationReference(String donationReference) {
        this.donationReference = donationReference;
    }

    @Basic
    @Column(name = "deposit_reference", nullable = false, length = 45)
    public String getDepositReference() {
        return this.depositReference;
    }

    public void setDepositReference(String depositReference) {
        this.depositReference = depositReference;
    }


    @Basic
    @Column(name = "donation_type", nullable = false,columnDefinition = "0")
    public int getDonationType() {
        return donationType;
    }

    public void setDonationType(int donationType) {
        this.donationType = donationType;
    }







    public boolean equals(Object o) {
        if(this == o) {
            return true;
        } else if(o != null && this.getClass() == o.getClass()) {
            MainListEntity that = (MainListEntity)o;
            if(this.id != that.id) {
                return false;
            } else if(Double.compare(that.donatedAmount, this.donatedAmount) != 0) {
                return false;
            } else if(Double.compare(that.amountToReceive, this.amountToReceive) != 0) {
                return false;
            } else if(Double.compare(that.adjustedAmount, this.adjustedAmount) != 0) {
                return false;
            } else if(this.status != that.status) {
                return false;
            } else if(this.enabled != that.enabled) {
                return false;
            } else {
                if(this.mainListReference != null) {
                    if(!this.mainListReference.equals(that.mainListReference)) {
                        return false;
                    }
                } else if(that.mainListReference != null) {
                    return false;
                }

                label89: {
                    if(this.bankAccountNumber != null) {
                        if(this.bankAccountNumber.equals(that.bankAccountNumber)) {
                            break label89;
                        }
                    } else if(that.bankAccountNumber == null) {
                        break label89;
                    }

                    return false;
                }

                if(this.date != null) {
                    if(!this.date.equals(that.date)) {
                        return false;
                    }
                } else if(that.date != null) {
                    return false;
                }

                label75: {
                    if(this.payerUsername != null) {
                        if(this.payerUsername.equals(that.payerUsername)) {
                            break label75;
                        }
                    } else if(that.payerUsername == null) {
                        break label75;
                    }

                    return false;
                }

                if(this.donationReference != null) {
                    if(!this.donationReference.equals(that.donationReference)) {
                        return false;
                    }
                } else if(that.donationReference != null) {
                    return false;
                }

                if(this.depositReference != null) {
                    if(this.depositReference.equals(that.depositReference)) {
                        return true;
                    }
                } else if(that.depositReference == null) {
                    return true;
                }

                return false;
            }
        } else {
            return false;
        }
    }

    public int hashCode() {
        int result = this.id;
        result = 31 * result + (this.mainListReference != null?this.mainListReference.hashCode():0);
        result = 31 * result + (this.bankAccountNumber != null?this.bankAccountNumber.hashCode():0);
        long temp = Double.doubleToLongBits(this.donatedAmount);
        result = 31 * result + (int)(temp ^ temp >>> 32);
        temp = Double.doubleToLongBits(this.amountToReceive);
        result = 31 * result + (int)(temp ^ temp >>> 32);
        temp = Double.doubleToLongBits(this.adjustedAmount);
        result = 31 * result + (int)(temp ^ temp >>> 32);
        result = 31 * result + this.status;
        result = 31 * result + this.enabled;
        result = 31 * result + (this.date != null?this.date.hashCode():0);
        result = 31 * result + (this.payerUsername != null?this.payerUsername.hashCode():0);
        result = 31 * result + (this.donationReference != null?this.donationReference.hashCode():0);
        result = 31 * result + (this.depositReference != null?this.depositReference.hashCode():0);
        return result;
    }

    public String getUserName() {
        return this.userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
}

