//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.iqa.profile.individual.dao;



import com.iqa.profile.individual.exception.IndividualProfileNotFoundException;
import com.iqa.profile.individual.model.IndividualProfileEntity;
import com.iqa.utilities.AbstractDao;

import java.security.NoSuchAlgorithmException;
import java.util.List;

public interface IndividualProfileDao extends AbstractDao<IndividualProfileEntity, Integer> {
    IndividualProfileEntity findUserByUserIdEnhenced(int var1) throws IndividualProfileNotFoundException;

    IndividualProfileEntity findByUsernameAndPassword(String var1, String var2) throws IndividualProfileNotFoundException, NoSuchAlgorithmException;

    void save(IndividualProfileEntity var1);

    IndividualProfileEntity getUserByBankDetails(String var1) throws IndividualProfileNotFoundException;

    boolean suppliedEmailExists(String var1) throws IndividualProfileNotFoundException;

    IndividualProfileEntity findUserByEmail(String var1) throws IndividualProfileNotFoundException;

    IndividualProfileEntity findUserByMemberId(String var1) throws IndividualProfileNotFoundException;

    IndividualProfileEntity findIndividualProfileByEmailAndPassword(String var1, String var2) throws IndividualProfileNotFoundException, NoSuchAlgorithmException;

    String findUserEmail(String username) throws IndividualProfileNotFoundException;

    public List<IndividualProfileEntity> searchForRegisteredAccounts(String username) throws IndividualProfileNotFoundException;

    public List<IndividualProfileEntity> getEntityByCandidateIdOrIdNumber(String candidateNumberOrIdNumber) throws IndividualProfileNotFoundException;

}

