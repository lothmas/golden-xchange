//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.iqa.verifiedcandidates.dao;



import com.iqa.verifiedcandidates.exception.VerifiedCandidatesNotFoundException;
import com.iqa.verifiedcandidates.model.CandidatesVerifiedEntity;
import com.iqa.utilities.AbstractDao;

import java.util.List;

public interface VerifiedCandidatesDao extends AbstractDao<CandidatesVerifiedEntity, Integer> {

    void saveVerifiedCandidates(CandidatesVerifiedEntity var1);

   CandidatesVerifiedEntity findVerifiedCandidatesByUsername(String var1) throws VerifiedCandidatesNotFoundException;
   CandidatesVerifiedEntity findVerifiedCandidatesById(int id) throws VerifiedCandidatesNotFoundException;
    List<CandidatesVerifiedEntity> getAllVerifiedCandidates() throws VerifiedCandidatesNotFoundException;
    public List<CandidatesVerifiedEntity> getVerifiedCandidatesByCandidateIdAndInstituteId(int instituteId, String candidateNumber) throws VerifiedCandidatesNotFoundException;;
}

