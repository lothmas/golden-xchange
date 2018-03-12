//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.iqa.qualifications.service.impl;


import com.iqa.qualifications.model.QualificationsEntity;
import com.iqa.qualifications.exception.QualificationsNotFoundException;
import com.iqa.qualifications.service.QualificationsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.NoSuchAlgorithmException;

@Service("QualificationsService")
@Transactional(
    readOnly = true
)
public class QualificationsServiceImpl implements QualificationsService {

    @Autowired
    private com.iqa.qualifications.dao.QualificationsDao QualificationsDao;


    @Override
    @Transactional(
            readOnly = false
    )
    public void saveQualifications(QualificationsEntity var1) {
         this.QualificationsDao.saveQualifications(var1);
    }

    @Override
    public QualificationsEntity findQualificationsByUsername(String username) throws QualificationsNotFoundException {
        return this.QualificationsDao.findQualificationsByUsername(username);
    }
}

