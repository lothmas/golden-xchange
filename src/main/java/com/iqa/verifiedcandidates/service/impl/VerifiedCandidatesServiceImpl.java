//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.iqa.verifiedcandidates.service.impl;


import com.iqa.verifiedcandidates.exception.VerifiedCandidatesNotFoundException;
import com.iqa.verifiedcandidates.service.VerifiedCandidatesService;
import com.iqa.verifiedcandidates.model.CandidatesVerifiedEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service("VerifiedCandidatesService")
@Transactional(
    readOnly = true
)
public class VerifiedCandidatesServiceImpl implements VerifiedCandidatesService {

    @Autowired
    private com.iqa.verifiedcandidates.dao.VerifiedCandidatesDao VerifiedCandidatesDao;


    @Override
    public void saveVerifiedCandidates(CandidatesVerifiedEntity var1) {

    }

    @Override
    public CandidatesVerifiedEntity findVerifiedCandidatesByUsername(String username) throws VerifiedCandidatesNotFoundException {
        return this.VerifiedCandidatesDao.findVerifiedCandidatesByUsername(username);
    }

    @Override
    public CandidatesVerifiedEntity findVerifiedCandidatesById(int id) throws VerifiedCandidatesNotFoundException {
        return this.VerifiedCandidatesDao.findVerifiedCandidatesById(id);
    }

    @Override
    public List<CandidatesVerifiedEntity> getAllVerifiedCandidates() throws VerifiedCandidatesNotFoundException {
        return this.VerifiedCandidatesDao.getAllVerifiedCandidates();
    }

    @Override
    public List<CandidatesVerifiedEntity> getVerifiedCandidatesByCandidateIdAndInstituteId(int instituteId,String candidateNumber) throws VerifiedCandidatesNotFoundException {
        return this.VerifiedCandidatesDao.getVerifiedCandidatesByCandidateIdAndInstituteId(instituteId,candidateNumber);
    }


}

