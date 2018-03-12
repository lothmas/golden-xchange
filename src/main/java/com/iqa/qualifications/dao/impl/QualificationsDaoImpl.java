//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.iqa.qualifications.dao.impl;


import com.iqa.qualifications.model.QualificationsEntity;
import com.iqa.qualifications.dao.QualificationsDao;
import com.iqa.qualifications.exception.QualificationsNotFoundException;
import com.iqa.utilities.AbstractDaoImpl;
import com.iqa.utilities.GeneralDomainFunctions;
import org.apache.log4j.Logger;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import java.security.NoSuchAlgorithmException;
import java.util.List;

@Repository
public class QualificationsDaoImpl extends AbstractDaoImpl<QualificationsEntity, Integer> implements QualificationsDao {

    private final Logger log = Logger.getLogger(QualificationsDaoImpl.class);
    Logger QualificationsDaoImplLogger = Logger.getLogger(this.getClass().getName());

    protected QualificationsDaoImpl() {
        super(QualificationsEntity.class);
    }

    @Override
    public void saveQualifications(QualificationsEntity QualificationsEntity) {
        saveOrUpdate(QualificationsEntity);
    }

    @Override
    public QualificationsEntity findQualificationsByUsername(String username) throws QualificationsNotFoundException {
        List<QualificationsEntity> results = this.getCurrentSession().createCriteria(QualificationsEntity.class)
                .add(Restrictions.eq("username", username))
                .list();

        if (results.isEmpty()) {
            throw new QualificationsNotFoundException("No Qualifications found associated with username:" + username);
        } else if (results.size() != 1) {
            throw new QualificationsNotFoundException("There are several QualificationsEntity found associated with :" + username);
        } else {
            return (QualificationsEntity) results.get(0);
        }
    }
}

