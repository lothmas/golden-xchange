//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.iqa.verifiedcandidates.dao.impl;


import com.iqa.verifiedcandidates.dao.VerifiedCandidatesDao;
import com.iqa.verifiedcandidates.exception.VerifiedCandidatesNotFoundException;
import com.iqa.verifiedcandidates.model.CandidatesVerifiedEntity;
import com.iqa.utilities.AbstractDaoImpl;
import org.apache.log4j.Logger;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class VerifiedCandidatesDaoImpl extends AbstractDaoImpl<CandidatesVerifiedEntity, Integer> implements VerifiedCandidatesDao {

    private final Logger log = Logger.getLogger(VerifiedCandidatesDaoImpl.class);
    Logger VerifiedCandidatesDaoImplLogger = Logger.getLogger(this.getClass().getName());

    protected VerifiedCandidatesDaoImpl() {
        super(CandidatesVerifiedEntity.class);
    }

    @Override
    public void saveVerifiedCandidates(CandidatesVerifiedEntity CandidatesVerifiedEntity) {
        this.saveOrUpdate(CandidatesVerifiedEntity);
    }

    @Override
    public CandidatesVerifiedEntity findVerifiedCandidatesByUsername(String username) throws VerifiedCandidatesNotFoundException {
        List<CandidatesVerifiedEntity> results = this.getCurrentSession().createCriteria(CandidatesVerifiedEntity.class)
                .add(Restrictions.eq("username", username))
                .list();

        if (results.isEmpty()) {
            throw new VerifiedCandidatesNotFoundException("No VerifiedCandidates found associated with username:" + username);
        } else if (results.size() != 1) {
            throw new VerifiedCandidatesNotFoundException("There are several CandidatesVerifiedEntity found associated with :" + username);
        } else {
            return (CandidatesVerifiedEntity) results.get(0);
        }
    }

    @Override
    public CandidatesVerifiedEntity findVerifiedCandidatesById(int id) throws VerifiedCandidatesNotFoundException {
        List<CandidatesVerifiedEntity> results = this.getCurrentSession().createCriteria(CandidatesVerifiedEntity.class)
                .add(Restrictions.eq("id", id))
                .list();

        if (results.isEmpty()) {
            throw new VerifiedCandidatesNotFoundException("No VerifiedCandidates found associated with id:" + id);
        } else if (results.size() != 1) {
            throw new VerifiedCandidatesNotFoundException("There are several CandidatesVerifiedEntity found associated with instituteId: " + id);
        } else {
            return results.get(0);
        }
    }

    @Override
    public List<CandidatesVerifiedEntity> getAllVerifiedCandidates() throws VerifiedCandidatesNotFoundException {
        List<CandidatesVerifiedEntity> results = this.getCurrentSession().createCriteria(CandidatesVerifiedEntity.class)
                .list();

        if (results.isEmpty()) {
            throw new VerifiedCandidatesNotFoundException("No VerifiedCandidates found ");
        }

        return results;
    }


    @Override
    public List<CandidatesVerifiedEntity> getVerifiedCandidatesByCandidateIdAndInstituteId(int instituteId,String candidateNumber) throws VerifiedCandidatesNotFoundException {
        List<CandidatesVerifiedEntity> results = this.getCurrentSession().createCriteria(CandidatesVerifiedEntity.class)
                .add(Restrictions.eq("candidateNumber", candidateNumber))
                .add(Restrictions.eq("institution", instituteId))
                .add(Restrictions.eq("enabled", "1"))
                .addOrder(Order.desc("id"))
                .list();

        if (results.isEmpty()) {
            throw new VerifiedCandidatesNotFoundException("No VerifiedCandidates found ");
        }

        return results;
    }
}

