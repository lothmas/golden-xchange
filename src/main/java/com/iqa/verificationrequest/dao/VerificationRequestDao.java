//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.iqa.verificationrequest.dao;



import com.iqa.verificationrequest.exception.VerificationRequestNotFoundException;
import com.iqa.verificationrequest.model.VerificationRequestEntity;
import com.iqa.utilities.AbstractDao;

import java.util.List;

public interface VerificationRequestDao extends AbstractDao<VerificationRequestEntity, Integer> {

    void saveVerificationRequest(VerificationRequestEntity var1) throws VerificationRequestNotFoundException;

    VerificationRequestEntity findVerificationRequestByUsername(String var1) throws VerificationRequestNotFoundException;
    VerificationRequestEntity VerificationRequestCandidateIdAndInstituteIdAndUserId(String candidateId, int instituteId, int requesterId) throws VerificationRequestNotFoundException;
    List<VerificationRequestEntity> getAllVerificationRequest() throws VerificationRequestNotFoundException;
    public List<VerificationRequestEntity> getVerificationRequestByCountry(int countryId) throws VerificationRequestNotFoundException;;
    List<VerificationRequestEntity> getVerificationRequestByUserId(int userId) throws VerificationRequestNotFoundException;

}

