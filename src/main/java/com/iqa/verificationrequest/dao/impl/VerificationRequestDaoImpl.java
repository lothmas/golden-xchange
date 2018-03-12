//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.iqa.verificationrequest.dao.impl;


import com.iqa.verificationrequest.dao.VerificationRequestDao;
import com.iqa.verificationrequest.exception.VerificationRequestNotFoundException;
import com.iqa.verificationrequest.model.VerificationRequestEntity;
import com.iqa.utilities.AbstractDaoImpl;
import org.apache.log4j.Logger;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.ZonedDateTime;
import java.util.Date;
import java.util.List;

@Repository
public class VerificationRequestDaoImpl extends AbstractDaoImpl<VerificationRequestEntity, Integer> implements VerificationRequestDao {

    private final Logger log = Logger.getLogger(VerificationRequestDaoImpl.class);
    Logger VerificationRequestDaoImplLogger = Logger.getLogger(this.getClass().getName());

    protected VerificationRequestDaoImpl() {
        super(VerificationRequestEntity.class);
    }

    @Override
    public void saveVerificationRequest(VerificationRequestEntity VerificationRequestEntity) throws VerificationRequestNotFoundException{

        saveOrUpdate(VerificationRequestEntity);
    }

    @Override
    public VerificationRequestEntity findVerificationRequestByUsername(String username) throws VerificationRequestNotFoundException {
        List<VerificationRequestEntity> results = this.getCurrentSession().createCriteria(VerificationRequestEntity.class)
                .add(Restrictions.eq("username", username))
                .list();

        if (results.isEmpty()) {
            throw new VerificationRequestNotFoundException("No VerificationRequest found associated with username:" + username);
        } else if (results.size() != 1) {
            throw new VerificationRequestNotFoundException("There are several VerificationRequestEntity found associated with :" + username);
        } else {
            return (VerificationRequestEntity) results.get(0);
        }
    }

    @Override
    public VerificationRequestEntity VerificationRequestCandidateIdAndInstituteIdAndUserId(String candidateNumber,int instituteId,int requesterId) throws VerificationRequestNotFoundException {
        List<VerificationRequestEntity> results = this.getCurrentSession().createCriteria(VerificationRequestEntity.class)
                .add(Restrictions.eq("candidateNumber", candidateNumber))
                .add(Restrictions.eq("institute", instituteId))
                .add(Restrictions.eq("requesterId", requesterId))
                .add(Restrictions.eq("enabled", 1))
                .list();

        if (results.isEmpty()) {
            throw new VerificationRequestNotFoundException("No VerificationRequest found associated with candidateNumber:" + candidateNumber);
        } else if (results.size() != 1) {
            throw new VerificationRequestNotFoundException("There are several VerificationRequestEntity found associated with instituteId: " + instituteId);
        }

        return results.get(0);
    }

    @Override
    public List<VerificationRequestEntity> getAllVerificationRequest() throws VerificationRequestNotFoundException {
        List<VerificationRequestEntity> results = this.getCurrentSession().createCriteria(VerificationRequestEntity.class)
                .list();

        if (results.isEmpty()) {
            throw new VerificationRequestNotFoundException("No VerificationRequest found ");
        }

        return results;
    }


    @Override
    public List<VerificationRequestEntity> getVerificationRequestByCountry(int countryId) throws VerificationRequestNotFoundException {
        List<VerificationRequestEntity> results = this.getCurrentSession().createCriteria(VerificationRequestEntity.class)
                .add(Restrictions.eq("country", countryId))
                .add(Restrictions.eq("enabled", 1))
                .list();

        if (results.isEmpty()) {
            throw new VerificationRequestNotFoundException("No VerificationRequest found ");
        }

        return results;
    }

    @Override
    public List<VerificationRequestEntity> getVerificationRequestByUserId(int userId) throws VerificationRequestNotFoundException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

        try {
            Date beginDate = sdf.parse(ZonedDateTime.now().withDayOfMonth(1).toString().substring(0, 10));


        long time = System.currentTimeMillis();
        List<VerificationRequestEntity> results = this.getCurrentSession().createCriteria(VerificationRequestEntity.class)
                .add(Restrictions.eq("requesterId", userId))
                .add(Restrictions.eq("enabled", 1))
                .add(Restrictions.between("requestDate", beginDate, new Date()))
                .list();


        if (results.isEmpty()) {
            throw new VerificationRequestNotFoundException("No VerificationRequest found ");
        }

        return results;
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return null;
    }
}

