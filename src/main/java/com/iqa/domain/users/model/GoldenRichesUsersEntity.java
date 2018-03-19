//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.goldenriches.domain.users.model;

import java.util.Arrays;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;

@Entity
@Table(
    name = "golden_riches_users",
    schema = "golden-riches-13-08-2016"
)
@IdClass(GoldenRichesUsersEntityPK.class)
public class GoldenRichesUsersEntity {
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
    private byte enabled;
    private String accountHoldername;
    private String gender;
    private byte[] profilePic;

    public GoldenRichesUsersEntity() {
    }

    @Id
    @Column(
        name = "id",
        nullable = false
    )
    public int getId() {
        return this.id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Id
    @Column(
        name = "userName",
        nullable = false,
        length = 45
    )
    public String getUserName() {
        return this.userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    @Basic
    @Column(
        name = "firstName",
        nullable = false,
        length = 45
    )
    public String getFirstName() {
        return this.firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    @Basic
    @Column(
        name = "surname",
        nullable = false,
        length = 45
    )
    public String getSurname() {
        return this.surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    @Basic
    @Column(
        name = "password",
        nullable = false,
        length = 150
    )
    public String getPassword() {
        return this.password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Basic
    @Column(
        name = "bankName",
        nullable = false,
        length = 45
    )
    public String getBankName() {
        return this.bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    @Basic
    @Column(
        name = "branchNumber",
        nullable = false,
        length = 45
    )
    public String getBranchNumber() {
        return this.branchNumber;
    }

    public void setBranchNumber(String branchNumber) {
        this.branchNumber = branchNumber;
    }

    @Basic
    @Column(
        name = "accountNumber",
        nullable = false,
        length = 45
    )
    public String getAccountNumber() {
        return this.accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    @Basic
    @Column(
        name = "emailAddress",
        nullable = false,
        length = 45
    )
    public String getEmailAddress() {
        return this.emailAddress;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    @Basic
    @Column(
        name = "telephoneNumber",
        nullable = false,
        length = 45
    )
    public String getTelephoneNumber() {
        return this.telephoneNumber;
    }

    public void setTelephoneNumber(String telephoneNumber) {
        this.telephoneNumber = telephoneNumber;
    }

    @Basic
    @Column(
        name = "enabled",
        nullable = false
    )
    public byte getEnabled() {
        return this.enabled;
    }

    public void setEnabled(byte enabled) {
        this.enabled = enabled;
    }

    @Basic
    @Column(
        name = "accountHoldername",
        nullable = false,
        length = 45
    )
    public String getAccountHoldername() {
        return this.accountHoldername;
    }

    public void setAccountHoldername(String accountHoldername) {
        this.accountHoldername = accountHoldername;
    }

    @Basic
    @Column(
        name = "gender",
        nullable = false,
        length = 1
    )
    public String getGender() {
        return this.gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    @Basic
    @Column(
        name = "profilePic",
        nullable = true
    )
    public byte[] getProfilePic() {
        return this.profilePic;
    }

    public void setProfilePic(byte[] profilePic) {
        this.profilePic = profilePic;
    }

    public boolean equals(Object o) {
        if(this == o) {
            return true;
        } else if(o != null && this.getClass() == o.getClass()) {
            GoldenRichesUsersEntity that = (GoldenRichesUsersEntity)o;
            if(this.id != that.id) {
                return false;
            } else if(this.enabled != that.enabled) {
                return false;
            } else {
                label150: {
                    if(this.userName != null) {
                        if(this.userName.equals(that.userName)) {
                            break label150;
                        }
                    } else if(that.userName == null) {
                        break label150;
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

                label136: {
                    if(this.surname != null) {
                        if(this.surname.equals(that.surname)) {
                            break label136;
                        }
                    } else if(that.surname == null) {
                        break label136;
                    }

                    return false;
                }

                label129: {
                    if(this.password != null) {
                        if(this.password.equals(that.password)) {
                            break label129;
                        }
                    } else if(that.password == null) {
                        break label129;
                    }

                    return false;
                }

                if(this.bankName != null) {
                    if(!this.bankName.equals(that.bankName)) {
                        return false;
                    }
                } else if(that.bankName != null) {
                    return false;
                }

                if(this.branchNumber != null) {
                    if(!this.branchNumber.equals(that.branchNumber)) {
                        return false;
                    }
                } else if(that.branchNumber != null) {
                    return false;
                }

                label108: {
                    if(this.accountNumber != null) {
                        if(this.accountNumber.equals(that.accountNumber)) {
                            break label108;
                        }
                    } else if(that.accountNumber == null) {
                        break label108;
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

                label87: {
                    if(this.accountHoldername != null) {
                        if(this.accountHoldername.equals(that.accountHoldername)) {
                            break label87;
                        }
                    } else if(that.accountHoldername == null) {
                        break label87;
                    }

                    return false;
                }

                if(this.gender != null) {
                    if(!this.gender.equals(that.gender)) {
                        return false;
                    }
                } else if(that.gender != null) {
                    return false;
                }

                if(!Arrays.equals(this.profilePic, that.profilePic)) {
                    return false;
                } else {
                    return true;
                }
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
        result = 31 * result + (this.accountHoldername != null?this.accountHoldername.hashCode():0);
        result = 31 * result + (this.gender != null?this.gender.hashCode():0);
        result = 31 * result + Arrays.hashCode(this.profilePic);
        return result;
    }
}

