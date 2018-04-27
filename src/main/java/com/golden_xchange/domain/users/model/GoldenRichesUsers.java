//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.golden_xchange.domain.users.model;

import org.hibernate.envers.Audited;

import javax.persistence.*;

@Entity
//
@Table(name = "users", schema = "", catalog = "")
public class GoldenRichesUsers {
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
    private String accountHoldername;
    private String profilePic;
    private String gender;
    private String accountType;
    private String referenceUser;
    private Integer userType;

    public GoldenRichesUsers() {
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
    @Column(name = "username", nullable = false, length = 45)
    public String getUserName() {
        return this.userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    @Basic
    @Column(name = "first_name", nullable = false, length = 45)
    public String getFirstName() {
        return this.firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    @Basic
    @Column(name = "surname", nullable = false, length = 45)
    public String getSurname() {
        return this.surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    @Basic
    @Column(name = "password", nullable = false, length = 500)
    public String getPassword() {
        return this.password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Basic
    @Column(name = "bank_name", nullable = false, length = 45)
    public String getBankName() {
        return this.bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    @Basic
    @Column(name = "branch_number", nullable = false, length = 45)
    public String getBranchNumber() {
        return this.branchNumber;
    }

    public void setBranchNumber(String branchNumber) {
        this.branchNumber = branchNumber;
    }

    @Basic
    @Column(name = "account_number", nullable = false, length = 45)
    public String getAccountNumber() {
        return this.accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    @Basic
    @Column(name = "email_address", nullable = false, length = 45)
    public String getEmailAddress() {
        return this.emailAddress;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    @Basic
    @Column(name = "telephone", nullable = false, length = 45)
    public String getTelephoneNumber() {
        return this.telephoneNumber;
    }

    public void setTelephoneNumber(String telephoneNumber) {
        this.telephoneNumber = telephoneNumber;
    }

    @Basic
    @Column(name = "enabled", nullable = false)
    public int getEnabled() {
        return this.enabled;
    }

    public void setEnabled(int enabled) {
        this.enabled = enabled;
    }

    @Basic
    @Column(name = "profile_pic",  length = 17000000)
    public String getProfilePic() {
        return this.profilePic;
    }

    public void setProfilePic(String profilePic) {
        this.profilePic = profilePic;
    }

    @Basic
    @Column(name = "gender", nullable = false, length = 45)
    public String getGender() {
        return this.gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    @Basic
    @Column(name = "account_holder_name", nullable = false, length = 45)
    public String getAccountHoldername() {
        return this.accountHoldername;
    }

    @Basic
    @Column(name = "account_type",  length = 45)
    public String getAccountType() {
        return accountType;
    }

    public void setAccountType(String accountType) {
        this.accountType = accountType;
    }

    @Basic
    @Column(name = "reference",  length = 45)
    public String getReferenceUser() {
        return referenceUser;
    }

    public void setReferenceUser(String referenceUser) {
        this.referenceUser = referenceUser;
    }

    public void setAccountHoldername(String accountHoldername) {
        this.accountHoldername = accountHoldername;
    }

    @Basic
    @Column(name = "user_type")
    public Integer getUserType() {
        return userType;
    }

    public void setUserType(Integer userType) {
        this.userType = userType;
    }

    public boolean equals(Object o) {
        if(this == o) {
            return true;
        } else if(o != null && this.getClass() == o.getClass()) {
            GoldenRichesUsers that = (GoldenRichesUsers)o;
            if(this.id != that.id) {
                return false;
            } else if(this.enabled != that.enabled) {
                return false;
            } else {
                label122: {
                    if(this.userName != null) {
                        if(this.userName.equals(that.userName)) {
                            break label122;
                        }
                    } else if(that.userName == null) {
                        break label122;
                    }

                    return false;
                }

                if(this.firstName != null) {
                    if(!this.firstName.equals(that.firstName)) {
                        return false;
                    }
                } else if(that.firstName != null) {
                    return false;
                }

                label108: {
                    if(this.surname != null) {
                        if(this.surname.equals(that.surname)) {
                            break label108;
                        }
                    } else if(that.surname == null) {
                        break label108;
                    }

                    return false;
                }

                if(this.password != null) {
                    if(!this.password.equals(that.password)) {
                        return false;
                    }
                } else if(that.password != null) {
                    return false;
                }

                if(this.bankName != null) {
                    if(!this.bankName.equals(that.bankName)) {
                        return false;
                    }
                } else if(that.bankName != null) {
                    return false;
                }

                label87: {
                    if(this.branchNumber != null) {
                        if(this.branchNumber.equals(that.branchNumber)) {
                            break label87;
                        }
                    } else if(that.branchNumber == null) {
                        break label87;
                    }

                    return false;
                }

                label80: {
                    if(this.accountNumber != null) {
                        if(this.accountNumber.equals(that.accountNumber)) {
                            break label80;
                        }
                    } else if(that.accountNumber == null) {
                        break label80;
                    }

                    return false;
                }

                if(this.emailAddress != null) {
                    if(!this.emailAddress.equals(that.emailAddress)) {
                        return false;
                    }
                } else if(that.emailAddress != null) {
                    return false;
                }

                if(this.telephoneNumber != null) {
                    if(!this.telephoneNumber.equals(that.telephoneNumber)) {
                        return false;
                    }
                } else if(that.telephoneNumber != null) {
                    return false;
                }

                return true;
            }
        } else {
            return false;
        }
    }

    public int hashCode() {
        int result = this.id;
        result = 31 * result + (this.userName != null?this.userName.hashCode():0);
        result = 31 * result + (this.firstName != null?this.firstName.hashCode():0);
        result = 31 * result + (this.surname != null?this.surname.hashCode():0);
        result = 31 * result + (this.password != null?this.password.hashCode():0);
        result = 31 * result + (this.bankName != null?this.bankName.hashCode():0);
        result = 31 * result + (this.branchNumber != null?this.branchNumber.hashCode():0);
        result = 31 * result + (this.accountNumber != null?this.accountNumber.hashCode():0);
        result = 31 * result + (this.emailAddress != null?this.emailAddress.hashCode():0);
        result = 31 * result + (this.telephoneNumber != null?this.telephoneNumber.hashCode():0);
        result = 31 * result + this.enabled;
        return result;
    }
}

