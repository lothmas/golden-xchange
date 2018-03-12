//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.iqa.profiles.service;



import com.iqa.profiles.exception.ProfilesNotFoundException;
import com.iqa.profiles.model.ProfileEntity;
import org.springframework.web.multipart.MultipartException;

import java.security.NoSuchAlgorithmException;
import java.util.List;

public interface ProfilesService {
    ProfileEntity findUserByUserId(int var1) throws ProfilesNotFoundException;

    ProfileEntity findProfileByUsernameAndPassword(String var1, String var2) throws ProfilesNotFoundException, NoSuchAlgorithmException;

    ProfileEntity findProfileByEmailAndPassword(String var1, String var2) throws ProfilesNotFoundException, NoSuchAlgorithmException;

    void saveUser(ProfileEntity var1) throws MultipartException,Exception;

    ProfileEntity getUserByBankDetails(String var1) throws ProfilesNotFoundException;

    boolean suppliedEmailExists(String var1) throws ProfilesNotFoundException;

    ProfileEntity findUserByEmail(String var1) throws ProfilesNotFoundException;

    ProfileEntity findUserByMemberId(String var1) throws ProfilesNotFoundException;

    String findUserEmail(String username) throws ProfilesNotFoundException;

    public List<ProfileEntity> searchForRegisteredAccounts(String username) throws ProfilesNotFoundException;

    public List<ProfileEntity> getEntityByCandidateIdOrIdNumber(String candidateNumberOrIdNumber) throws ProfilesNotFoundException;
}

