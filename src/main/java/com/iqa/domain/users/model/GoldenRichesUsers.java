//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.goldenriches.domain.users.model;

import org.hibernate.envers.Audited;

@Audited
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
    private byte enabled;
    private String accountHoldername;
    private byte[] profilePic;
    private String gender;

    public GoldenRichesUsers() {
    }

    public int getId() {
        return this.id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUserName() {
        return this.userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getFirstName() {
        return this.firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getSurname() {
        return this.surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getPassword() {
        return this.password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getBankName() {
        return this.bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    public String getBranchNumber() {
        return this.branchNumber;
    }

    public void setBranchNumber(String branchNumber) {
        this.branchNumber = branchNumber;
    }

    public String getAccountNumber() {
        return this.accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public String getEmailAddress() {
        return this.emailAddress;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    public String getTelephoneNumber() {
        return this.telephoneNumber;
    }

    public void setTelephoneNumber(String telephoneNumber) {
        this.telephoneNumber = telephoneNumber;
    }

    public byte getEnabled() {
        return this.enabled;
    }

    public void setEnabled(byte enabled) {
        this.enabled = enabled;
    }

    public byte[] getProfilePic() {
        return this.profilePic;
    }

    public void setProfilePic(byte[] profilePic) {
        this.profilePic = profilePic;
    }

    public String getGender() {
        return this.gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getAccountHoldername() {
        return this.accountHoldername;
    }

    public void setAccountHoldername(String accountHoldername) {
        this.accountHoldername = accountHoldername;
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

