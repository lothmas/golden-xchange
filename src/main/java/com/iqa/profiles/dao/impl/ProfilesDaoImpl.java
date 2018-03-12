//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.iqa.profiles.dao.impl;


import com.iqa.profiles.dao.ProfilesDao;
import com.iqa.profiles.exception.ProfilesNotFoundException;
import com.iqa.profiles.model.ProfileEntity;
import com.iqa.profiles.service.ProfilesService;
import com.iqa.utilities.AbstractDaoImpl;
import com.iqa.utilities.Enums.UserStatusEnum;
import com.iqa.utilities.GeneralDomainFunctions;
import org.apache.log4j.Logger;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.web.multipart.MultipartException;

import java.security.NoSuchAlgorithmException;
import java.util.List;

@Repository
public class ProfilesDaoImpl extends AbstractDaoImpl<ProfileEntity, Integer> implements ProfilesDao {
//    @Autowired
//    BankAccountService bankAccountService;

    @Autowired
    ProfilesService profileService;

    private final Logger log = Logger.getLogger(ProfilesDaoImpl.class);
    Logger ProfilesDaoImplLogger = Logger.getLogger(this.getClass().getName());

    protected ProfilesDaoImpl() {
        super(ProfileEntity.class);
    }

    public void save(ProfileEntity ProfilesEntity) throws MultipartException,Exception {
        this.saveOrUpdate(ProfilesEntity);
    }

    public ProfileEntity findUserByUserIdEnhenced(int userId) throws ProfilesNotFoundException {
        List<ProfileEntity> result = this.getCurrentSession().createCriteria(ProfileEntity.class).add(Restrictions.eq("id", Integer.valueOf(userId))).list();
        if(result != null && !result.isEmpty()) {
            if(result.size() != 1) {
                throw new ProfilesNotFoundException("There are severalProfilesEntity found associated with user id :" + userId);
            } else {
                return (ProfileEntity)result.get(0);
            }
        } else {
            throw new ProfilesNotFoundException("No user found associated with user id :" + userId);
        }
    }

    public ProfileEntity findByUsernameAndPassword(String username, String password) throws ProfilesNotFoundException, NoSuchAlgorithmException {
        long end, end1, start = System.currentTimeMillis();

        List<ProfileEntity> result = getCurrentSession().createCriteria(ProfileEntity.class)
                .add(Restrictions.eq("username", username))
                .add(Restrictions.eq("enabled", 1))
                .list();

        end1 = System.currentTimeMillis();
        ProfilesDaoImplLogger.info("\"hibernate call to the Users.class searching by username\" took :[" + (end1 - start) + "ms]");

        if (result.isEmpty()) {
            throw new ProfilesNotFoundException("No User found associated with username:" + username + " and password :" + password);
        }

        if (result.size() != 1) {
            throw new ProfilesNotFoundException("There are several Users found associated with username:" + username + " and password :" + password);
        }

        int profileStatus = 0;
        String memberId = result.get(0).getUsername();
        ProfileEntity profile;
        try {

            long hi1, hi = System.currentTimeMillis();
             profile = profileService.findUserByUserId(result.get(0).getId());
            hi1 = System.currentTimeMillis();
            ProfilesDaoImplLogger.info("\"profileService.findProfileByUserId(result.get(0).getId())\" took :[" + (hi1 - hi) + "ms]");

            profileStatus = profile.getStatus();

        } catch (ProfilesNotFoundException ex) {

            throw new ProfilesNotFoundException("This user with memberId: " + memberId + " is cancelled!!");


//            java.util.logging.Logger.getLogger(UsersDaoImpl.class.getName()).log(Level.SEVERE, null, ex);
        }

        if (profileStatus == UserStatusEnum.CANCELLED.getValue()) {

            throw new ProfilesNotFoundException("This user with username: " + profile.getUsername() + " is cancelled!!");

        }

        String databasePassword[] = result.get(0).getPassword().split(":");
        String correctHashedPassword = databasePassword[0];
        String salt;

        if (databasePassword.length > 1) {
            salt = databasePassword[1];
        } else {
            salt = "";
        }

        String suppliedHashedPassword;

        try {

            long hi1, hi = System.currentTimeMillis();
            suppliedHashedPassword = GeneralDomainFunctions.getCryptedPassword(password, salt);
            hi1 = System.currentTimeMillis();
            ProfilesDaoImplLogger.info("\"GeneralDomainFunctions.getCryptedPassword(password, salt)\" took :[" + (hi1 - hi) + "ms]");

            if (suppliedHashedPassword.equals(correctHashedPassword)) {

                end = System.currentTimeMillis();
                ProfilesDaoImplLogger.info("\"UsersDaoImpl.findUsersByUsernameAndPassword\" has finished successfully and it took :[" + (end - start) + "ms]");

                return result.get(0);

            } else {
                throw new ProfilesNotFoundException("Wrong Log in credentials!!!");
            }

        } catch (NoSuchAlgorithmException v) {
            throw new NoSuchAlgorithmException(v);
        }
    }

    public ProfileEntity getUserByBankDetails(String accountNumber) throws ProfilesNotFoundException {
       ProfileEntity setBankAcc = new ProfileEntity();
        List<ProfileEntity> results = null;
//        List<BankAccountsEntity> bankResults = this.getCurrentSession().createCriteria(BankAccountsEntity.class)
//                .add(Restrictions.eq("accountNumber", accountNumber))
//                .list();
//        if(bankResults.size() != 0) {
//            results = this.getCurrentSession().createCriteria(ProfileEntity.class)
//                    .add(Restrictions.eq("userName", ((BankAccountsEntity)bankResults.get(0)).getUserName()))
//                    .list();
//        } else {
//            results = this.getCurrentSession().createCriteria(ProfileEntity.class).add(Restrictions.eq("accountNumber", accountNumber)).list();
//        }
//
//        try {
//            if(null != ((ProfileEntity)results.get(0)).getAccountNumber()) {
//                setBankAcc.setAccountHoldername(((ProfileEntity)results.get(0)).getAccountHoldername());
//                setBankAcc.setBranchNumber(((ProfileEntity)results.get(0)).getBranchNumber());
//                setBankAcc.setBankName(((ProfileEntity)results.get(0)).getBankName());
//            } else {
//                setBankAcc.setAccountHoldername(((BankAccountsEntity)bankResults.get(0)).getAccountHolderName());
//                setBankAcc.setBranchNumber(((BankAccountsEntity)bankResults.get(0)).getBranchNumber());
//                setBankAcc.setBankName(((BankAccountsEntity)bankResults.get(0)).getBankName());
//            }
//        } catch (Exception var6) {
//            return null;
//        }
//
//        setBankAcc.setTelephoneNumber(((ProfileEntity)results.get(0)).getTelephoneNumber());
//        setBankAcc.setEmailAddress(((ProfileEntity)results.get(0)).getEmailAddress());
//        setBankAcc.setUserName(((ProfileEntity)results.get(0)).getUserName());
//        setBankAcc.setAccountNumber(accountNumber);
        return setBankAcc;
    }

    public boolean suppliedEmailExists(String email) throws ProfilesNotFoundException {
        boolean result = true;
        email = email.replaceAll(" ", "");
        List<ProfileEntity> results = this.getCurrentSession().createCriteria(ProfileEntity.class)
                .add(Restrictions.eq("emailAddress", email))
                .list();
        if(results.isEmpty()) {
            result = false;
        }

        if(!results.isEmpty()) {
            result = true;
        }

        return result;
    }

    public ProfileEntity findUserByEmail(String email) throws ProfilesNotFoundException {
        List<ProfileEntity> results = this.getCurrentSession().createCriteria(ProfileEntity.class)
                .add(Restrictions.eq("emailAddress", email)).list();
        if(results.isEmpty()) {
            throw new ProfilesNotFoundException("No User found associated with email:" + email);
        } else if(results.size() != 1) {
            throw new ProfilesNotFoundException("There are severalProfilesEntity found associated with :" + email);
        } else {
            return (ProfileEntity)results.get(0);
        }
    }

    public ProfileEntity findUserByMemberId(String memberId) throws ProfilesNotFoundException {
        List<ProfileEntity> results = this.getCurrentSession().createCriteria(ProfileEntity.class)
                .add(Restrictions.eq("username", memberId))
                .list();
        if(results.isEmpty()) {
            throw new ProfilesNotFoundException("No User found associated with username:" + memberId);
        } else if(results.size() != 1) {
            throw new ProfilesNotFoundException("There are severalProfilesEntity found associated with :" + memberId);
        } else {
            return (ProfileEntity)results.get(0);
        }
    }

    @Override
    public ProfileEntity findProfileByEmailAndPassword(String var1, String var2) throws ProfilesNotFoundException, NoSuchAlgorithmException {
        return null;
    }

    public ProfileEntity findProfilesByEmailAndPassword(String email, String password) throws ProfilesNotFoundException, NoSuchAlgorithmException {
        List<ProfileEntity> result = this.getCurrentSession().createCriteria(ProfileEntity.class).add(Restrictions.eq("emailAddress", email)).list();
        if(result.isEmpty()) {
            throw new ProfilesNotFoundException("No User found associated with email: " + email);
        } else if(result.size() != 1) {
            throw new ProfilesNotFoundException("There are several Users found associated with email: " + email);
        } else {
            String[] databasePassword = ((ProfileEntity)result.get(0)).getPassword().split(":");
            String correctHashedPassword = databasePassword[0];
            String salt;
            if(databasePassword.length > 1) {
                salt = databasePassword[1];
            } else {
                salt = "";
            }

            try {
                String suppliedHashedPassword = GeneralDomainFunctions.getCryptedPassword(password, salt);
                if(suppliedHashedPassword.equals(correctHashedPassword)) {
                    return (ProfileEntity)result.get(0);
                } else {
                    throw new ProfilesNotFoundException("Wrong Log in credentials!!!");
                }
            } catch (NoSuchAlgorithmException var9) {
                throw new NoSuchAlgorithmException(var9);
            }
        }
    }

    @Override
    public String findUserEmail(String username) throws ProfilesNotFoundException {
        List<ProfileEntity> results = this.getCurrentSession().createCriteria(ProfileEntity.class)
                .add(Restrictions.eq("username", username))
                .list();

        if(results.size()>1){
            throw new ProfilesNotFoundException("Multiple Users found provided username");
        }
       return results.get(0).getEmailAddress();
    }

    @Override
    public List<ProfileEntity> searchForRegisteredAccounts(String username) throws ProfilesNotFoundException {
        List<ProfileEntity> results = this.getCurrentSession().createCriteria(ProfileEntity.class)
                .add(Restrictions.like("username", username))
//                .add(Restrictions.ilike("firstName", username, MatchMode.ANYWHERE))
//                .add(Restrictions.ilike("surname", username, MatchMode.ANYWHERE))
                .list();

        if(results.size()==0){
            throw new ProfilesNotFoundException("No Profiles Found with provided search text");

        }
        return results;
    }

    @Override
    public List<ProfileEntity> getEntityByCandidateIdOrIdNumber(String candidateNumberOrIdNumber) throws ProfilesNotFoundException {
        List<ProfileEntity> results = this.getCurrentSession().createCriteria(ProfileEntity.class)
                .add(Restrictions.like("username", candidateNumberOrIdNumber))
//                .add(Restrictions.ilike("firstName", username, MatchMode.ANYWHERE))
//                .add(Restrictions.ilike("surname", username, MatchMode.ANYWHERE))
                .list();

        if(results.size()==0){
            throw new ProfilesNotFoundException("No Profiles Found with provided search text");

        }
        return results;
    }
    
    


}

