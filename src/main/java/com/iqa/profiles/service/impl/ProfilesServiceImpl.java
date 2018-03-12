//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.iqa.profiles.service.impl;


import com.iqa.profiles.exception.ProfilesNotFoundException;
import com.iqa.profiles.model.ProfileEntity;
import com.iqa.profiles.service.ProfilesService;
import com.iqa.profiles.dao.ProfilesDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartException;

import java.security.NoSuchAlgorithmException;
import java.util.List;

@Service("ProfileService")
@Transactional(
    readOnly = true
)
public class ProfilesServiceImpl implements ProfilesService {

    @Autowired
    private ProfilesDao ProfileDao;

    public ProfilesServiceImpl() {
    }

    public ProfileEntity findUserByUserId(int userId) throws ProfilesNotFoundException {
        return this.ProfileDao.findUserByUserIdEnhenced(userId);
    }

    public ProfileEntity findProfileByUsernameAndPassword(String username, String password) throws ProfilesNotFoundException, NoSuchAlgorithmException {
        return this.ProfileDao.findByUsernameAndPassword(username, password);
    }

    @Transactional(
        readOnly = false
    )
    public void saveUser(ProfileEntity ProfileEntity) throws MultipartException,Exception {
        this.ProfileDao.save(ProfileEntity);
    }

    public ProfileEntity getUserByBankDetails(String username) throws ProfilesNotFoundException {
        return this.ProfileDao.getUserByBankDetails(username);
    }

    public boolean suppliedEmailExists(String email) throws ProfilesNotFoundException {
        return this.ProfileDao.suppliedEmailExists(email);
    }

    public ProfileEntity findUserByEmail(String email) throws ProfilesNotFoundException {
        return this.ProfileDao.findUserByEmail(email);
    }

    public ProfileEntity findUserByMemberId(String memberId) throws ProfilesNotFoundException {
        return this.ProfileDao.findUserByMemberId(memberId);
    }

    @Override
    public String findUserEmail(String username) throws ProfilesNotFoundException {
        return this.ProfileDao.findUserEmail(username);
    }

    @Override
    public List<ProfileEntity> searchForRegisteredAccounts(String username) throws ProfilesNotFoundException {
        return this.ProfileDao.searchForRegisteredAccounts(username);
    }

    @Override
    public List<ProfileEntity> getEntityByCandidateIdOrIdNumber(String candidateNumberOrIdNumber) throws ProfilesNotFoundException {
        return this.ProfileDao.getEntityByCandidateIdOrIdNumber(candidateNumberOrIdNumber);
    }

    public ProfileEntity findProfileByEmailAndPassword(String email, String password) throws ProfilesNotFoundException, NoSuchAlgorithmException {
        return this.ProfileDao.findProfileByEmailAndPassword(email, password);
    }



}

