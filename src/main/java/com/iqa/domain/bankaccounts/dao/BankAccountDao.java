package com.goldenriches.domain.bankaccounts.dao;

import com.goldenriches.domain.bankaccounts.exception.BankAccountsNotFoundException;
import com.goldenriches.domain.bankaccounts.model.BankAccountsEntity;
import com.goldenriches.domain.utilities.AbstractDao;
import java.security.NoSuchAlgorithmException;
import java.util.List;

public abstract interface BankAccountDao
        extends AbstractDao<BankAccountsEntity, Integer>
{
    public abstract BankAccountsEntity findUserByUserIdEnhenced(int paramInt)
            throws BankAccountsNotFoundException;

    public abstract BankAccountsEntity findByUsernameAndPassword(String paramString1, String paramString2)
            throws BankAccountsNotFoundException, NoSuchAlgorithmException;

    public abstract void save(BankAccountsEntity paramBankAccountsEntity);

    public abstract boolean getIfSuppliedUsernameIsUnique(String paramString);

    public abstract boolean suppliedEmailExists(String paramString)
            throws BankAccountsNotFoundException;

    public abstract BankAccountsEntity findUserByEmail(String paramString)
            throws BankAccountsNotFoundException;

    public abstract BankAccountsEntity findBankAccByAccNumber(String paramString)
            throws BankAccountsNotFoundException;

    public abstract BankAccountsEntity findBankAccountsByEmailAndPassword(String paramString1, String paramString2)
            throws BankAccountsNotFoundException, NoSuchAlgorithmException;

    public abstract List<BankAccountsEntity> findBankAccountsEntityByUsername(String paramString)
            throws BankAccountsNotFoundException, NoSuchAlgorithmException;

    public abstract boolean findBankAccByAccNumberAndUserName(String paramString1, String paramString2);
}
