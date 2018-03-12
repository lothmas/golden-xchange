//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.iqa.profile.individual.dao.impl;


import com.iqa.profile.individual.dao.IndividualProfileDao;
import com.iqa.profile.individual.exception.IndividualProfileNotFoundException;
import com.iqa.profile.individual.model.IndividualProfileEntity;
import com.iqa.utilities.AbstractDaoImpl;
import com.iqa.utilities.GeneralDomainFunctions;
import org.apache.log4j.Logger;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import java.security.NoSuchAlgorithmException;
import java.util.List;

@Repository
public class IndividualProfileDaoImpl extends AbstractDaoImpl<IndividualProfileEntity, Integer> implements IndividualProfileDao {
//    @Autowired
//    BankAccountService bankAccountService;
    private final Logger log = Logger.getLogger(IndividualProfileDaoImpl.class);
    Logger IndividualProfileDaoImplLogger = Logger.getLogger(this.getClass().getName());

    protected IndividualProfileDaoImpl() {
        super(IndividualProfileEntity.class);
    }

    public void save(IndividualProfileEntity IndividualProfileEntity) {
        this.saveOrUpdate(IndividualProfileEntity);
    }

    public IndividualProfileEntity findUserByUserIdEnhenced(int userId) throws IndividualProfileNotFoundException {
        List<IndividualProfileEntity> result = this.getCurrentSession().createCriteria(IndividualProfileEntity.class).add(Restrictions.eq("id", Integer.valueOf(userId))).list();
        if(result != null && !result.isEmpty()) {
            if(result.size() != 1) {
                throw new IndividualProfileNotFoundException("There are several IndividualProfileEntity found associated with user id :" + userId);
            } else {
                return (IndividualProfileEntity)result.get(0);
            }
        } else {
            throw new IndividualProfileNotFoundException("No user found associated with user id :" + userId);
        }
    }

    public IndividualProfileEntity findByUsernameAndPassword(String username, String password) throws IndividualProfileNotFoundException, NoSuchAlgorithmException {
        List<IndividualProfileEntity> result = this.getCurrentSession().createCriteria(IndividualProfileEntity.class)
                .add(Restrictions.eq("username", username))
                .list();
        if(result.isEmpty()) {
            throw new IndividualProfileNotFoundException("No User found associated with username:" + username);
        } else if(result.size() != 1) {
            throw new IndividualProfileNotFoundException("There are several Users found associated with :" + username);
        } else {
            return result.get(0);
//            String[] databasePassword = ((IndividualProfileEntity)result.get(0)).getPassword().split(":");
//            String correctHashedPassword = databasePassword[0];
//            String salt;
//            if(databasePassword.length > 1) {
//                salt = databasePassword[1];
//            } else {
//                salt = "";
//            }
//
//            try {
//                String suppliedHashedPassword = GeneralDomainFunctions.getCryptedPassword(password, salt);
//                if(suppliedHashedPassword.equals(correctHashedPassword)) {
//                    return (IndividualProfileEntity)result.get(0);
//                } else {
//                    throw new IndividualProfileNotFoundException("Wrong Log in credentials!!!");
//                }
//            } catch (NoSuchAlgorithmException var9) {
//                throw new NoSuchAlgorithmException(var9);
//            }
        }
    }

    public IndividualProfileEntity getUserByBankDetails(String accountNumber) throws IndividualProfileNotFoundException {
        IndividualProfileEntity setBankAcc = new IndividualProfileEntity();
        List<IndividualProfileEntity> results = null;
//        List<BankAccountsEntity> bankResults = this.getCurrentSession().createCriteria(BankAccountsEntity.class)
//                .add(Restrictions.eq("accountNumber", accountNumber))
//                .list();
//        if(bankResults.size() != 0) {
//            results = this.getCurrentSession().createCriteria(IndividualProfileEntity.class)
//                    .add(Restrictions.eq("userName", ((BankAccountsEntity)bankResults.get(0)).getUserName()))
//                    .list();
//        } else {
//            results = this.getCurrentSession().createCriteria(IndividualProfileEntity.class).add(Restrictions.eq("accountNumber", accountNumber)).list();
//        }
//
//        try {
//            if(null != ((IndividualProfileEntity)results.get(0)).getAccountNumber()) {
//                setBankAcc.setAccountHoldername(((IndividualProfileEntity)results.get(0)).getAccountHoldername());
//                setBankAcc.setBranchNumber(((IndividualProfileEntity)results.get(0)).getBranchNumber());
//                setBankAcc.setBankName(((IndividualProfileEntity)results.get(0)).getBankName());
//            } else {
//                setBankAcc.setAccountHoldername(((BankAccountsEntity)bankResults.get(0)).getAccountHolderName());
//                setBankAcc.setBranchNumber(((BankAccountsEntity)bankResults.get(0)).getBranchNumber());
//                setBankAcc.setBankName(((BankAccountsEntity)bankResults.get(0)).getBankName());
//            }
//        } catch (Exception var6) {
//            return null;
//        }
//
//        setBankAcc.setTelephoneNumber(((IndividualProfileEntity)results.get(0)).getTelephoneNumber());
//        setBankAcc.setEmailAddress(((IndividualProfileEntity)results.get(0)).getEmailAddress());
//        setBankAcc.setUserName(((IndividualProfileEntity)results.get(0)).getUserName());
//        setBankAcc.setAccountNumber(accountNumber);
        return setBankAcc;
    }

    public boolean suppliedEmailExists(String email) throws IndividualProfileNotFoundException {
        boolean result = true;
        email = email.replaceAll(" ", "");
        List<IndividualProfileEntity> results = this.getCurrentSession().createCriteria(IndividualProfileEntity.class)
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

    public IndividualProfileEntity findUserByEmail(String email) throws IndividualProfileNotFoundException {
        List<IndividualProfileEntity> results = this.getCurrentSession().createCriteria(IndividualProfileEntity.class)
                .add(Restrictions.eq("emailAddress", email)).list();
        if(results.isEmpty()) {
            throw new IndividualProfileNotFoundException("No User found associated with email:" + email);
        } else if(results.size() != 1) {
            throw new IndividualProfileNotFoundException("There are several IndividualProfileEntity found associated with :" + email);
        } else {
            return (IndividualProfileEntity)results.get(0);
        }
    }

    public IndividualProfileEntity findUserByMemberId(String memberId) throws IndividualProfileNotFoundException {
        List<IndividualProfileEntity> results = this.getCurrentSession().createCriteria(IndividualProfileEntity.class)
                .add(Restrictions.eq("username", memberId))
                .list();
        if(results.isEmpty()) {
            throw new IndividualProfileNotFoundException("No User found associated with username:" + memberId);
        } else if(results.size() != 1) {
            throw new IndividualProfileNotFoundException("There are several IndividualProfileEntity found associated with :" + memberId);
        } else {
            return (IndividualProfileEntity)results.get(0);
        }
    }

    public IndividualProfileEntity findIndividualProfileByEmailAndPassword(String email, String password) throws IndividualProfileNotFoundException, NoSuchAlgorithmException {
        List<IndividualProfileEntity> result = this.getCurrentSession().createCriteria(IndividualProfileEntity.class).add(Restrictions.eq("emailAddress", email)).list();
        if(result.isEmpty()) {
            throw new IndividualProfileNotFoundException("No User found associated with email: " + email);
        } else if(result.size() != 1) {
            throw new IndividualProfileNotFoundException("There are several Users found associated with email: " + email);
        } else {
            String[] databasePassword = ((IndividualProfileEntity)result.get(0)).getPassword().split(":");
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
                    return (IndividualProfileEntity)result.get(0);
                } else {
                    throw new IndividualProfileNotFoundException("Wrong Log in credentials!!!");
                }
            } catch (NoSuchAlgorithmException var9) {
                throw new NoSuchAlgorithmException(var9);
            }
        }
    }

    @Override
    public String findUserEmail(String username) throws IndividualProfileNotFoundException {
        List<IndividualProfileEntity> results = this.getCurrentSession().createCriteria(IndividualProfileEntity.class)
                .add(Restrictions.eq("username", username))
                .list();

        if(results.size()>1){
            throw new IndividualProfileNotFoundException("Multiple Users found provided username");
        }
       return results.get(0).getEmailAddress();
    }

    @Override
    public List<IndividualProfileEntity> searchForRegisteredAccounts(String username) throws IndividualProfileNotFoundException {
        List<IndividualProfileEntity> results = this.getCurrentSession().createCriteria(IndividualProfileEntity.class)
                .add(Restrictions.like("username", username))
//                .add(Restrictions.ilike("firstName", username, MatchMode.ANYWHERE))
//                .add(Restrictions.ilike("surname", username, MatchMode.ANYWHERE))
                .list();

        if(results.size()==0){
            throw new IndividualProfileNotFoundException("No Profile Found with provided search text");

        }
        return results;
    }

    @Override
    public List<IndividualProfileEntity> getEntityByCandidateIdOrIdNumber(String candidateNumberOrIdNumber) throws IndividualProfileNotFoundException {
        List<IndividualProfileEntity> results = this.getCurrentSession().createCriteria(IndividualProfileEntity.class)
                .add(Restrictions.like("username", candidateNumberOrIdNumber))
//                .add(Restrictions.ilike("firstName", username, MatchMode.ANYWHERE))
//                .add(Restrictions.ilike("surname", username, MatchMode.ANYWHERE))
                .list();

        if(results.size()==0){
            throw new IndividualProfileNotFoundException("No Profile Found with provided search text");

        }
        return results;
    }


}

