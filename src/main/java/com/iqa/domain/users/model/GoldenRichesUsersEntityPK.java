//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.goldenriches.domain.users.model;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Id;

public class GoldenRichesUsersEntityPK implements Serializable {
    private int id;
    private String userName;

    public GoldenRichesUsersEntityPK() {
    }

    @Column(
        name = "id",
        nullable = false
    )
    @Id
    public int getId() {
        return this.id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Column(
        name = "userName",
        nullable = false,
        length = 45
    )
    @Id
    public String getUserName() {
        return this.userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public boolean equals(Object o) {
        if(this == o) {
            return true;
        } else if(o != null && this.getClass() == o.getClass()) {
            GoldenRichesUsersEntityPK that = (GoldenRichesUsersEntityPK)o;
            if(this.id != that.id) {
                return false;
            } else {
                if(this.userName != null) {
                    if(!this.userName.equals(that.userName)) {
                        return false;
                    }
                } else if(that.userName != null) {
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
        return result;
    }
}

