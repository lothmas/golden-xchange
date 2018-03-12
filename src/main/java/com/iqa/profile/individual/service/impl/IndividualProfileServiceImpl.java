//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.iqa.profile.individual.service.impl;


import com.iqa.profile.individual.exception.IndividualProfileNotFoundException;
import com.iqa.profile.individual.model.IndividualProfileEntity;
import com.iqa.profile.individual.service.IndividualProfileService;
import com.iqa.profile.individual.dao.IndividualProfileDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.NoSuchAlgorithmException;
import java.util.List;

@Service("IndividualProfileService")
@Transactional(
    readOnly = true
)
public class IndividualProfileServiceImpl implements IndividualProfileService {

    @Autowired
    private IndividualProfileDao IndividualProfileDao;

    public IndividualProfileServiceImpl() {
    }

    public IndividualProfileEntity findUserByUserId(int userId) throws IndividualProfileNotFoundException {
        return this.IndividualProfileDao.findUserByUserIdEnhenced(userId);
    }

    public IndividualProfileEntity findIndividualProfileByUsernameAndPassword(String username, String password) throws IndividualProfileNotFoundException, NoSuchAlgorithmException {
        return this.IndividualProfileDao.findByUsernameAndPassword(username, password);
    }

    @Transactional(
        readOnly = false
    )
    public void saveUser(IndividualProfileEntity IndividualProfileEntity) {
        this.IndividualProfileDao.save(IndividualProfileEntity);
    }

    public IndividualProfileEntity getUserByBankDetails(String username) throws IndividualProfileNotFoundException {
        return this.IndividualProfileDao.getUserByBankDetails(username);
    }

    public boolean suppliedEmailExists(String email) throws IndividualProfileNotFoundException {
        return this.IndividualProfileDao.suppliedEmailExists(email);
    }

    public IndividualProfileEntity findUserByEmail(String email) throws IndividualProfileNotFoundException {
        return this.IndividualProfileDao.findUserByEmail(email);
    }

    public IndividualProfileEntity findUserByMemberId(String memberId) throws IndividualProfileNotFoundException {
        return this.IndividualProfileDao.findUserByMemberId(memberId);
    }

    @Override
    public String findUserEmail(String username) throws IndividualProfileNotFoundException {
        return this.IndividualProfileDao.findUserEmail(username);
    }

    @Override
    public List<IndividualProfileEntity> searchForRegisteredAccounts(String username) throws IndividualProfileNotFoundException {
        return this.IndividualProfileDao.searchForRegisteredAccounts(username);
    }

    @Override
    public List<IndividualProfileEntity> getEntityByCandidateIdOrIdNumber(String candidateNumberOrIdNumber) throws IndividualProfileNotFoundException {
        return this.IndividualProfileDao.getEntityByCandidateIdOrIdNumber(candidateNumberOrIdNumber);
    }

    public IndividualProfileEntity findIndividualProfileByEmailAndPassword(String email, String password) throws IndividualProfileNotFoundException, NoSuchAlgorithmException {
        return this.IndividualProfileDao.findIndividualProfileByEmailAndPassword(email, password);
    }



}

