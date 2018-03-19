//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.golden_xchange.domain.users.dao.impl;

import com.golden_xchange.domain.bankaccounts.model.BankAccountsEntity;
import com.golden_xchange.domain.bankaccounts.service.BankAccountService;
import com.golden_xchange.domain.users.dao.GoldenRichesUsersDao;
import com.golden_xchange.domain.users.exception.GoldenRichesUsersNotFoundException;
import com.golden_xchange.domain.users.model.GoldenRichesUsers;
import com.golden_xchange.domain.utilities.AbstractDaoImpl;
import com.golden_xchange.domain.utilities.GeneralDomainFunctions;
import org.apache.log4j.Logger;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.security.NoSuchAlgorithmException;
import java.util.List;

@Repository
public class GoldenRichesUsersDaoImpl extends AbstractDaoImpl<GoldenRichesUsers, Integer> implements GoldenRichesUsersDao {
    @Autowired
    BankAccountService bankAccountService;
    private final Logger log = Logger.getLogger(GoldenRichesUsersDaoImpl.class);
    Logger GoldenRichesUsersDaoImplLogger = Logger.getLogger(this.getClass().getName());

    protected GoldenRichesUsersDaoImpl() {
        super(GoldenRichesUsers.class);
    }

    public void save(GoldenRichesUsers GoldenRichesUsers) {
        this.saveOrUpdate(GoldenRichesUsers);
    }

    public GoldenRichesUsers findUserByUserIdEnhenced(int userId) throws GoldenRichesUsersNotFoundException {
        List<GoldenRichesUsers> result = this.getCurrentSession().createCriteria(GoldenRichesUsers.class).add(Restrictions.eq("id", Integer.valueOf(userId))).list();
        if(result != null && !result.isEmpty()) {
            if(result.size() != 1) {
                throw new GoldenRichesUsersNotFoundException("There are several GoldenRichesUsers found associated with user id :" + userId);
            } else {
                return (GoldenRichesUsers)result.get(0);
            }
        } else {
            throw new GoldenRichesUsersNotFoundException("No user found associated with user id :" + userId);
        }
    }

    public GoldenRichesUsers findByUsernameAndPassword(String username, String password) throws GoldenRichesUsersNotFoundException, NoSuchAlgorithmException {
        List<GoldenRichesUsers> result = this.getCurrentSession().createCriteria(GoldenRichesUsers.class).add(Restrictions.eq("userName", username)).list();
        if(result.isEmpty()) {
            throw new GoldenRichesUsersNotFoundException("No User found associated with username:" + username);
        } else if(result.size() != 1) {
            throw new GoldenRichesUsersNotFoundException("There are several Users found associated with username:" + username);
        } else {
            String[] databasePassword = ((GoldenRichesUsers)result.get(0)).getPassword().split(":");
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
                    return (GoldenRichesUsers)result.get(0);
                } else {
                    throw new GoldenRichesUsersNotFoundException("Wrong Log in credentials!!!");
                }
            } catch (NoSuchAlgorithmException var9) {
                throw new NoSuchAlgorithmException(var9);
            }
        }
    }

    public GoldenRichesUsers getUserByBankDetails(String accountNumber) throws GoldenRichesUsersNotFoundException {
        GoldenRichesUsers setBankAcc = new GoldenRichesUsers();
        List<GoldenRichesUsers> results = null;
        List<BankAccountsEntity> bankResults = this.getCurrentSession().createCriteria(BankAccountsEntity.class)
                .add(Restrictions.eq("accountNumber", accountNumber))
                .list();

        if(bankResults.size() != 0) {
            results = this.getCurrentSession().createCriteria(GoldenRichesUsers.class)
                    .add(Restrictions.eq("userName", bankResults.get(0).getUserName().trim()))
                    .list();

        } else {
            results = this.getCurrentSession().createCriteria(GoldenRichesUsers.class)
                    .add(Restrictions.eq("accountNumber", accountNumber))
                    .list();

        }

        try {
            if(null != (results.get(0)).getAccountNumber()) {
                setBankAcc.setAccountHoldername(results.get(0).getAccountHoldername());
                setBankAcc.setBranchNumber(results.get(0).getBranchNumber());
                setBankAcc.setBankName(results.get(0).getBankName());
            } else {
                setBankAcc.setAccountHoldername(bankResults.get(0).getAccountHolderName());
                setBankAcc.setBranchNumber(bankResults.get(0).getBranchNumber());
                setBankAcc.setBankName(bankResults.get(0).getBankName());
            }
        } catch (Exception var6) {
            log.error("Error on retrieving mainList: "+var6.getCause() +var6.getMessage() +" accountNumber: "+accountNumber);
            return null;
        }

        setBankAcc.setTelephoneNumber((results.get(0)).getTelephoneNumber());
        setBankAcc.setEmailAddress((results.get(0)).getEmailAddress());
        setBankAcc.setUserName((results.get(0)).getUserName());
        setBankAcc.setAccountNumber(accountNumber);
        return setBankAcc;
    }

    public boolean suppliedEmailExists(String email) throws GoldenRichesUsersNotFoundException {
        boolean result = true;
        email = email.replaceAll(" ", "");
        List<GoldenRichesUsers> results = this.getCurrentSession().createCriteria(GoldenRichesUsers.class).add(Restrictions.eq("emailAddress", email)).list();
        if(results.isEmpty()) {
            result = false;
        }

        if(!results.isEmpty()) {
            result = true;
        }

        return result;
    }

    public GoldenRichesUsers findUserByEmail(String email) throws GoldenRichesUsersNotFoundException {
        List<GoldenRichesUsers> results = this.getCurrentSession().createCriteria(GoldenRichesUsers.class).add(Restrictions.eq("emailAddress", email)).list();
        if(results.isEmpty()) {
            throw new GoldenRichesUsersNotFoundException("No User found associated with email:" + email);
        } else if(results.size() != 1) {
            throw new GoldenRichesUsersNotFoundException("There are several GoldenRichesUsers found associated with email:" + email);
        } else {
            return results.get(0);
        }
    }

    public GoldenRichesUsers findUserByMemberId(String memberId) throws GoldenRichesUsersNotFoundException {
        List<GoldenRichesUsers> results = this.getCurrentSession().createCriteria(GoldenRichesUsers.class).add(Restrictions.eq("userName", memberId)).list();
        if(results.isEmpty()) {
            throw new GoldenRichesUsersNotFoundException("No User found associated with username:" + memberId);
        } else if(results.size() != 1) {
            throw new GoldenRichesUsersNotFoundException("There are several GoldenRichesUsers found associated with username:" + memberId);
        } else {
            return (GoldenRichesUsers)results.get(0);
        }
    }

    public GoldenRichesUsers findGoldenRichesUsersByEmailAndPassword(String email, String password) throws GoldenRichesUsersNotFoundException, NoSuchAlgorithmException {
        List<GoldenRichesUsers> result = this.getCurrentSession().createCriteria(GoldenRichesUsers.class).add(Restrictions.eq("emailAddress", email)).list();
        if(result.isEmpty()) {
            throw new GoldenRichesUsersNotFoundException("No User found associated with email: " + email);
        } else if(result.size() != 1) {
            throw new GoldenRichesUsersNotFoundException("There are several Users found associated with email: " + email);
        } else {
            String[] databasePassword = ((GoldenRichesUsers)result.get(0)).getPassword().split(":");
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
                    return (GoldenRichesUsers)result.get(0);
                } else {
                    throw new GoldenRichesUsersNotFoundException("Wrong Log in credentials!!!");
                }
            } catch (NoSuchAlgorithmException var9) {
                throw new NoSuchAlgorithmException(var9);
            }
        }
    }
}

