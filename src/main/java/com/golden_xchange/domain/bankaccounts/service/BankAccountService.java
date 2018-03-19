//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.golden_xchange.domain.bankaccounts.service;

import com.golden_xchange.domain.bankaccounts.exception.BankAccountsNotFoundException;
import com.golden_xchange.domain.bankaccounts.model.BankAccountsEntity;

import java.security.NoSuchAlgorithmException;
import java.util.List;

public interface BankAccountService {
    BankAccountsEntity findUserByUserId(int var1) throws BankAccountsNotFoundException;

    List<BankAccountsEntity> findBankAccountsEntityByUsername(String var1) throws BankAccountsNotFoundException, NoSuchAlgorithmException;

    BankAccountsEntity findBankAccountsEntityByEmailAndPassword(String var1, String var2) throws BankAccountsNotFoundException, NoSuchAlgorithmException;

    void saveUser(BankAccountsEntity var1);

    boolean memberIdIsUnique(String var1);

    boolean suppliedEmailExists(String var1) throws BankAccountsNotFoundException;

    BankAccountsEntity findUserByEmail(String var1) throws BankAccountsNotFoundException;

    BankAccountsEntity findBankAccByAccNumber(String var1) throws BankAccountsNotFoundException;

    boolean findBankAccByAccNumberAndUserName(String var1, String var2);
}

