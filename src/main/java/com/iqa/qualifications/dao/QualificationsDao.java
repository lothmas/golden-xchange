//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.iqa.qualifications.dao;



import com.iqa.qualifications.model.QualificationsEntity;
import com.iqa.qualifications.exception.QualificationsNotFoundException;
import com.iqa.utilities.AbstractDao;

public interface QualificationsDao extends AbstractDao<QualificationsEntity, Integer> {

    void saveQualifications(QualificationsEntity var1);

    QualificationsEntity findQualificationsByUsername(String var1) throws QualificationsNotFoundException;

}

