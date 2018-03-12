//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.iqa.profile.individual.service;



import com.iqa.profile.individual.exception.IndividualProfileNotFoundException;
import com.iqa.profile.individual.model.IndividualProfileEntity;

import java.security.NoSuchAlgorithmException;
import java.util.List;

public interface IndividualProfileService {
    IndividualProfileEntity findUserByUserId(int var1) throws IndividualProfileNotFoundException;

    IndividualProfileEntity findIndividualProfileByUsernameAndPassword(String var1, String var2) throws IndividualProfileNotFoundException, NoSuchAlgorithmException;

    IndividualProfileEntity findIndividualProfileByEmailAndPassword(String var1, String var2) throws IndividualProfileNotFoundException, NoSuchAlgorithmException;

    void saveUser(IndividualProfileEntity var1);

    IndividualProfileEntity getUserByBankDetails(String var1) throws IndividualProfileNotFoundException;

    boolean suppliedEmailExists(String var1) throws IndividualProfileNotFoundException;

    IndividualProfileEntity findUserByEmail(String var1) throws IndividualProfileNotFoundException;

    IndividualProfileEntity findUserByMemberId(String var1) throws IndividualProfileNotFoundException;

    String findUserEmail(String username) throws IndividualProfileNotFoundException;

    public List<IndividualProfileEntity> searchForRegisteredAccounts(String username) throws IndividualProfileNotFoundException;

    public List<IndividualProfileEntity> getEntityByCandidateIdOrIdNumber(String candidateNumberOrIdNumber) throws IndividualProfileNotFoundException;
}

