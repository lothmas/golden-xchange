//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.iqa.qualifications.service;



import com.iqa.qualifications.model.QualificationsEntity;
import com.iqa.qualifications.exception.QualificationsNotFoundException;

import java.security.NoSuchAlgorithmException;

public interface QualificationsService {


    void saveQualifications(QualificationsEntity var1) ;

    QualificationsEntity findQualificationsByUsername(String var1) throws QualificationsNotFoundException;


}

