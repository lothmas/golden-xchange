package com.goldenriches.domain.bankaccounts.model;

import org.hibernate.envers.Audited;

@Audited
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
    private String enabled;
    private String accountHoldername;
    private byte[] profilePic;
    private String gender;
    private String accountHolderName;

    public String getAccountHolderName()
    {
        return this.accountHolderName;
    }

    public int getId()
    {
        return this.id;
    }

    public void setId(int id)
    {
        this.id = id;
    }

    public String getUserName()
    {
        return this.userName;
    }

    public void setUserName(String userName)
    {
        this.userName = userName;
    }

    public String getFirstName()
    {
        return this.firstName;
    }

    public void setFirstName(String firstName)
    {
        this.firstName = firstName;
    }

    public String getSurname()
    {
        return this.surname;
    }

    public void setSurname(String surname)
    {
        this.surname = surname;
    }

    public String getPassword()
    {
        return this.password;
    }

    public void setPassword(String password)
    {
        this.password = password;
    }

    public String getBankName()
    {
        return this.bankName;
    }

    public void setBankName(String bankName)
    {
        this.bankName = bankName;
    }

    public String getBranchNumber()
    {
        return this.branchNumber;
    }

    public void setBranchNumber(String branchNumber)
    {
        this.branchNumber = branchNumber;
    }

    public String getAccountNumber()
    {
        return this.accountNumber;
    }

    public void setAccountNumber(String accountNumber)
    {
        this.accountNumber = accountNumber;
    }

    public String getEmailAddress()
    {
        return this.emailAddress;
    }

    public void setEmailAddress(String emailAddress)
    {
        this.emailAddress = emailAddress;
    }

    public String getTelephoneNumber()
    {
        return this.telephoneNumber;
    }

    public void setTelephoneNumber(String telephoneNumber)
    {
        this.telephoneNumber = telephoneNumber;
    }

    public String getEnabled()
    {
        return this.enabled;
    }

    public void setEnabled(String enabled)
    {
        this.enabled = enabled;
    }

    public byte[] getProfilePic()
    {
        return this.profilePic;
    }

    public void setProfilePic(byte[] profilePic)
    {
        this.profilePic = profilePic;
    }

    public String getGender()
    {
        return this.gender;
    }

    public void setGender(String gender)
    {
        this.gender = gender;
    }

    public String getAccountHoldername()
    {
        return this.accountHoldername;
    }

    public void setAccountHoldername(String accountHoldername)
    {
        this.accountHoldername = accountHoldername;
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

    public void setAccountHolderName(String accountHolderName)
    {
        this.accountHolderName = accountHolderName;
    }
}
