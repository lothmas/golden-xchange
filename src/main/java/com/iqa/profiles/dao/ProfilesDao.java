//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.iqa.profiles.dao;



import com.iqa.profiles.exception.ProfilesNotFoundException;
import com.iqa.profiles.model.ProfileEntity;
import com.iqa.utilities.AbstractDao;
import org.springframework.web.multipart.MultipartException;

import java.security.NoSuchAlgorithmException;
import java.util.List;

public interface ProfilesDao extends AbstractDao<ProfileEntity, Integer> {
    ProfileEntity findUserByUserIdEnhenced(int var1) throws ProfilesNotFoundException;

    ProfileEntity findByUsernameAndPassword(String var1, String var2) throws ProfilesNotFoundException, NoSuchAlgorithmException;

    void save(ProfileEntity var1) throws MultipartException,Exception;

    ProfileEntity getUserByBankDetails(String var1) throws ProfilesNotFoundException;

    boolean suppliedEmailExists(String var1) throws ProfilesNotFoundException;

    ProfileEntity findUserByEmail(String var1) throws ProfilesNotFoundException;

    ProfileEntity findUserByMemberId(String var1) throws ProfilesNotFoundException;

    ProfileEntity findProfileByEmailAndPassword(String var1, String var2) throws ProfilesNotFoundException, NoSuchAlgorithmException;

    String findUserEmail(String username) throws ProfilesNotFoundException;

    public List<ProfileEntity> searchForRegisteredAccounts(String username) throws ProfilesNotFoundException;

    public List<ProfileEntity> getEntityByCandidateIdOrIdNumber(String candidateNumberOrIdNumber) throws ProfilesNotFoundException;

}

