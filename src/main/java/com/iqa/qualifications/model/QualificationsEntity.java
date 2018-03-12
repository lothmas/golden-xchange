package com.iqa.qualifications.model;

import javax.persistence.*;

/**
 * Created by louis on 2018/05/22.
 */
@Entity
@Table(name = "qualifications", schema = "iqa-16-05-2018", catalog = "")
public class QualificationsEntity {
    private Integer id;
    private String username;
    private String qualifications;
    private String grade;

    @Id
    @Column(name = "id", nullable = false)
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
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
    @Column(name = "qualifications", nullable = false, length = -1)
    public String getQualifications() {
        return qualifications;
    }

    public void setQualifications(String qualifications) {
        this.qualifications = qualifications;
    }

    @Basic
    @Column(name = "grade", nullable = true, length = -1)
    public String getGrade() {
        return grade;
    }

    public void setGrade(String grade) {
        this.grade = grade;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        QualificationsEntity that = (QualificationsEntity) o;

        if (id != null ? !id.equals(that.id) : that.id != null) return false;
        if (username != null ? !username.equals(that.username) : that.username != null) return false;
        if (qualifications != null ? !qualifications.equals(that.qualifications) : that.qualifications != null)
            return false;
        if (grade != null ? !grade.equals(that.grade) : that.grade != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (username != null ? username.hashCode() : 0);
        result = 31 * result + (qualifications != null ? qualifications.hashCode() : 0);
        result = 31 * result + (grade != null ? grade.hashCode() : 0);
        return result;
    }
}
