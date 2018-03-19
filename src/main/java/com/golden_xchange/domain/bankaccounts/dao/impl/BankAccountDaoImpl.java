package com.golden_xchange.domain.bankaccounts.dao.impl;

import com.golden_xchange.domain.bankaccounts.dao.BankAccountDao;
import com.golden_xchange.domain.bankaccounts.exception.BankAccountsNotFoundException;
import com.golden_xchange.domain.bankaccounts.model.BankAccountsEntity;
import com.golden_xchange.domain.users.model.GoldenRichesUsers;
import com.golden_xchange.domain.utilities.AbstractDaoImpl;
import org.apache.log4j.Logger;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import java.security.NoSuchAlgorithmException;
import java.util.List;

@Repository
public class BankAccountDaoImpl
        extends AbstractDaoImpl<BankAccountsEntity, Integer>
        implements BankAccountDao
{
    private final Logger log = Logger.getLogger(BankAccountDaoImpl.class);
    Logger BankAccountsEntityDaoImplLogger = Logger.getLogger(getClass().getName());

    protected BankAccountDaoImpl()
    {
        super(BankAccountsEntity.class);
    }

    public void save(BankAccountsEntity BankAccountsEntity)
    {
        saveOrUpdate(BankAccountsEntity);
    }

    public BankAccountsEntity findUserByUserIdEnhenced(int userId)
            throws BankAccountsNotFoundException
    {
        List<BankAccountsEntity> result = getCurrentSession().createCriteria(BankAccountsEntity.class).add(Restrictions.eq("id", Integer.valueOf(userId))).list();
        if ((result == null) || (result.isEmpty())) {
            throw new BankAccountsNotFoundException("No user found associated with user id :" + userId);
        }
        if (result.size() != 1) {
            throw new BankAccountsNotFoundException("There are several BankAccountsEntity found associated with user id :" + userId);
        }
        return (BankAccountsEntity)result.get(0);
    }

    public BankAccountsEntity findByUsernameAndPassword(String username, String password)
            throws BankAccountsNotFoundException, NoSuchAlgorithmException
    {
        return null;
    }

    public boolean getIfSuppliedUsernameIsUnique(String username)
    {
        boolean result = false;
        username = username.replaceAll(" ", "");

        List<BankAccountsEntity> results = getCurrentSession().createCriteria(BankAccountsEntity.class).add(Restrictions.eq("username", username)).list();
        if (results.isEmpty()) {
            result = true;
        }
        if (!results.isEmpty()) {
            result = false;
        }
        return result;
    }

    public boolean suppliedEmailExists(String email)
            throws BankAccountsNotFoundException
    {
        boolean result = true;
        email = email.replaceAll(" ", "");

        List<BankAccountsEntity> results = getCurrentSession().createCriteria(BankAccountsEntity.class).add(Restrictions.eq("emailAddress", email)).list();
        if (results.isEmpty()) {
            result = false;
        }
        if (!results.isEmpty()) {
            result = true;
        }
        return result;
    }

    public BankAccountsEntity findUserByEmail(String email)
            throws BankAccountsNotFoundException
    {
        List<BankAccountsEntity> results = getCurrentSession().createCriteria(BankAccountsEntity.class).add(Restrictions.eq("emailAddress", email)).list();
        if (results.isEmpty()) {
            throw new BankAccountsNotFoundException("No User found associated with email:" + email);
        }
        if (results.size() != 1) {
            throw new BankAccountsNotFoundException("There are several BankAccountsEntity found associated with email:" + email);
        }
        return (BankAccountsEntity)results.get(0);
    }

    public BankAccountsEntity findBankAccByAccNumber(String accountNumber)
            throws BankAccountsNotFoundException
    {
        List<BankAccountsEntity> results = getCurrentSession().createCriteria(BankAccountsEntity.class).add(Restrictions.eq("accountNumber", accountNumber)).list();
        if (results.isEmpty()) {
            throw new BankAccountsNotFoundException("No BankAccount found associated with accountNumber:" + accountNumber);
        }
        if (results.size() != 1) {
            throw new BankAccountsNotFoundException("There are several BankAccountsEntity found associated with accountNumber:" + accountNumber);
        }
        return (BankAccountsEntity)results.get(0);
    }

    public boolean findBankAccByAccNumberAndUserName(String accountNumber, String userName)
    {
        List<BankAccountsEntity> bankAccResults = getCurrentSession().createCriteria(BankAccountsEntity.class).add(Restrictions.eq("accountNumber", accountNumber)).add(Restrictions.eq("userName", userName)).list();

        List<GoldenRichesUsers> userAccResults = getCurrentSession().createCriteria(GoldenRichesUsers.class).add(Restrictions.eq("accountNumber", accountNumber)).add(Restrictions.eq("userName", userName)).list();
        if ((bankAccResults.size() == 0) && (userAccResults.size() == 0)) {
            return false;
        }
        return true;
    }

    public BankAccountsEntity findBankAccountsByEmailAndPassword(String email, String password)
            throws BankAccountsNotFoundException, NoSuchAlgorithmException
    {
        return null;
    }

    public List<BankAccountsEntity> findBankAccountsEntityByUsername(String username)
            throws BankAccountsNotFoundException
    {
        List<BankAccountsEntity> results = getCurrentSession().createCriteria(BankAccountsEntity.class).add(Restrictions.eq("userName", username)).list();
        if (results.isEmpty()) {
            throw new BankAccountsNotFoundException("No BankAccount found associated with username:" + username);
        }
        return results;
    }
}
