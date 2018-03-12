//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.iqa.verificationrequest.service.impl;


import com.iqa.verificationrequest.exception.VerificationRequestNotFoundException;
import com.iqa.verificationrequest.service.VerificationRequestService;
import com.iqa.verificationrequest.model.VerificationRequestEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service("VerificationRequestService")
@Transactional(
    readOnly = true
)
public class VerificationRequestServiceImpl implements VerificationRequestService {

    @Autowired
    private com.iqa.verificationrequest.dao.VerificationRequestDao VerificationRequestDao;


    @Override
    @Transactional(readOnly = false)
    public void saveVerificationRequest(VerificationRequestEntity var1) throws VerificationRequestNotFoundException{
            this.VerificationRequestDao.saveVerificationRequest(var1);
    }

    @Override
    public VerificationRequestEntity findVerificationRequestByUsername(String username) throws VerificationRequestNotFoundException {
        return this.VerificationRequestDao.findVerificationRequestByUsername(username);
    }

    @Override
    public VerificationRequestEntity VerificationRequestCandidateIdAndInstituteIdAndUserId(String candidateId,int instituteId,int userId) throws VerificationRequestNotFoundException {
        return this.VerificationRequestDao.VerificationRequestCandidateIdAndInstituteIdAndUserId(candidateId,instituteId,userId);
    }

    @Override
    public List<VerificationRequestEntity> getAllVerificationRequest() throws VerificationRequestNotFoundException {
        return this.VerificationRequestDao.getAllVerificationRequest();
    }

    @Override
    public List<VerificationRequestEntity> getVerificationRequestByCountry(int countryId) throws VerificationRequestNotFoundException {
        return this.VerificationRequestDao.getVerificationRequestByCountry(countryId);
    }

    @Override
    public List<VerificationRequestEntity> getVerificationRequestByUserId(int userId) throws VerificationRequestNotFoundException {
        return this.VerificationRequestDao.getVerificationRequestByUserId(userId);
    }


}

