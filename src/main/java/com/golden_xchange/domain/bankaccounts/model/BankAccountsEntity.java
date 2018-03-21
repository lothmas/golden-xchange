package com.golden_xchange.domain.bankaccounts.model;

import org.hibernate.envers.Audited;

import javax.persistence.*;
import java.sql.Date;


@Entity
@Table(name = "bank_account", schema = "", catalog = "")
public class BankAccountsEntity
{
    private int id;
    private String userName;
    private String firstName;
    private String surname;
    private String password;
    private String bankName;
    private String branchNumber;
    private String accountNumber;
    private String emailAddress;
    private String telephoneNumber;
    private int enabled;
    private String gender;
    private String accountHolderName;



    @Id
    @Column(name = "id", nullable = false)
    public int getId()
    {
        return this.id;
    }

    public void setId(int id)
    {
        this.id = id;
    }

    @Basic
    @Column(name = "username", nullable = false, length = 45)
    public String getUserName()
    {
        return this.userName;
    }

    public void setUserName(String userName)
    {
        this.userName = userName;
    }

    @Basic
    @Column(name = "first_name", nullable = false, length = 45)
    public String getFirstName()
    {
        return this.firstName;
    }

    public void setFirstName(String firstName)
    {
        this.firstName = firstName;
    }

    @Basic
    @Column(name = "surname", nullable = false, length = 45)
    public String getSurname()
    {
        return this.surname;
    }

    public void setSurname(String surname)
    {
        this.surname = surname;
    }

    @Basic
    @Column(name = "password", nullable = false, length = 45)
    public String getPassword()
    {
        return this.password;
    }

    public void setPassword(String password)
    {
        this.password = password;
    }

    @Basic
    @Column(name = "bank_name", nullable = false, length = 45)
    public String getBankName()
    {
        return this.bankName;
    }

    public void setBankName(String bankName)
    {
        this.bankName = bankName;
    }

    @Basic
    @Column(name = "branch_number", nullable = false, length = 45)
    public String getBranchNumber()
    {
        return this.branchNumber;
    }

    public void setBranchNumber(String branchNumber)
    {
        this.branchNumber = branchNumber;
    }

    @Basic
    @Column(name = "account_number", nullable = false, length = 45)
    public String getAccountNumber()
    {
        return this.accountNumber;
    }

    public void setAccountNumber(String accountNumber)
    {
        this.accountNumber = accountNumber;
    }

    @Basic
    @Column(name = "email_address", nullable = false, length = 45)
    public String getEmailAddress()
    {
        return this.emailAddress;
    }

    public void setEmailAddress(String emailAddress)
    {
        this.emailAddress = emailAddress;
    }

    @Basic
    @Column(name = "telephone", nullable = false, length = 45)
    public String getTelephoneNumber()
    {
        return this.telephoneNumber;
    }

    public void setTelephoneNumber(String telephoneNumber)
    {
        this.telephoneNumber = telephoneNumber;
    }

    @Basic
    @Column(name = "enabled", nullable = false, length = 1)
    public int getEnabled() {
        return enabled;
    }


    public void setEnabled(int enabled)
    {
        this.enabled = enabled;
    }

    @Basic
    @Column(name = "gender", nullable = false, length = 45)
    public String getGender()
    {
        return this.gender;
    }

    public void setGender(String gender)
    {
        this.gender = gender;
    }

    @Basic
    @Column(name = "account_holder_name", nullable = false, length = 45)
    public String getAccountHolderName()
    {
        return this.accountHolderName;
    }

    public void setAccountHolderName(String accountHoldername)
    {
        this.accountHolderName = accountHoldername;
    }

    public boolean equals(Object o)
    {
        if (this == o) {
            return true;
        }
        if ((o == null) || (getClass() != o.getClass())) {
            return false;
        }
        BankAccountsEntity that = (BankAccountsEntity)o;
        if (this.id != that.id) {
            return false;
        }
        if (this.enabled != that.enabled) {
            return false;
        }
        if (this.userName != null ? !this.userName.equals(that.userName) : that.userName != null) {
            return false;
        }
        if (this.firstName != null ? !this.firstName.equals(that.firstName) : that.firstName != null) {
            return false;
        }
        if (this.surname != null ? !this.surname.equals(that.surname) : that.surname != null) {
            return false;
        }
        if (this.password != null ? !this.password.equals(that.password) : that.password != null) {
            return false;
        }
        if (this.bankName != null ? !this.bankName.equals(that.bankName) : that.bankName != null) {
            return false;
        }
        if (this.branchNumber != null ? !this.branchNumber.equals(that.branchNumber) : that.branchNumber != null) {
            return false;
        }
        if (this.accountNumber != null ? !this.accountNumber.equals(that.accountNumber) : that.accountNumber != null) {
            return false;
        }
        if (this.emailAddress != null ? !this.emailAddress.equals(that.emailAddress) : that.emailAddress != null) {
            return false;
        }
        if (this.telephoneNumber != null ? !this.telephoneNumber.equals(that.telephoneNumber) : that.telephoneNumber != null) {
            return false;
        }
        return true;
    }

    public int hashCode()
    {
        int result = this.id;
        result = 31 * result + (this.userName != null ? this.userName.hashCode() : 0);
        result = 31 * result + (this.firstName != null ? this.firstName.hashCode() : 0);
        result = 31 * result + (this.surname != null ? this.surname.hashCode() : 0);
        result = 31 * result + (this.password != null ? this.password.hashCode() : 0);
        result = 31 * result + (this.bankName != null ? this.bankName.hashCode() : 0);
        result = 31 * result + (this.branchNumber != null ? this.branchNumber.hashCode() : 0);
        result = 31 * result + (this.accountNumber != null ? this.accountNumber.hashCode() : 0);
        result = 31 * result + (this.emailAddress != null ? this.emailAddress.hashCode() : 0);
        result = 31 * result + (this.telephoneNumber != null ? this.telephoneNumber.hashCode() : 0);

        return result;
    }


}
