//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.iqa.verificationrequest.service;



import com.iqa.verificationrequest.exception.VerificationRequestNotFoundException;
import com.iqa.verificationrequest.model.VerificationRequestEntity;

import java.util.List;

public interface VerificationRequestService {


    void saveVerificationRequest(VerificationRequestEntity var1)throws VerificationRequestNotFoundException;

    VerificationRequestEntity findVerificationRequestByUsername(String var1) throws VerificationRequestNotFoundException;

    VerificationRequestEntity VerificationRequestCandidateIdAndInstituteIdAndUserId(String candidateNumber, int instituteId, int requesterId) throws VerificationRequestNotFoundException;

    List<VerificationRequestEntity> getAllVerificationRequest() throws VerificationRequestNotFoundException;
    public List<VerificationRequestEntity> getVerificationRequestByCountry(int countryId)  throws VerificationRequestNotFoundException;
    List<VerificationRequestEntity> getVerificationRequestByUserId(int userId) throws VerificationRequestNotFoundException;

}

