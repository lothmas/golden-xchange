package com.golden_xchange.domain.notifications.model;

import javax.persistence.*;

import java.util.Date;
import java.util.Objects;

@Entity
@Table(name = "notifications", catalog = "")
public class NotificationsEntity {
    private int id;
    private String userName ;
    private String message;
    private Date creationDate;
    private int status;
    private String mainListRef;

    @Id
    @Column(name = "id", nullable = false)
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Basic
    @Column(name = "user_name", nullable = false, length = 45)
    public String getUserName() {
        return userName;
    }

    public void setUserName(String userId) {
        this.userName = userId;
    }

    @Basic
    @Column(name = "message", nullable = false, length = 5000)
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Basic
    @Column(name = "creation_date", nullable = false)
    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    @Basic
    @Column(name = "status", nullable = false)
    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    @Basic
    @Column(name = "main_list_reference", nullable = false, length = 25)
    public String getMainListRef() {
        return mainListRef;
    }

    public void setMainListRef(String mainListRef) {
        this.mainListRef = mainListRef;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        NotificationsEntity that = (NotificationsEntity) o;
        return id == that.id &&
                status == that.status &&
                Objects.equals(message, that.message) &&
                Objects.equals(creationDate, that.creationDate);
    }


}
