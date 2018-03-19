//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.goldenriches.domain.bankaccounts.service.impl;

import com.goldenriches.domain.bankaccounts.dao.BankAccountDao;
import com.goldenriches.domain.bankaccounts.exception.BankAccountsNotFoundException;
import com.goldenriches.domain.bankaccounts.model.BankAccountsEntity;
import com.goldenriches.domain.bankaccounts.service.BankAccountService;
import java.security.NoSuchAlgorithmException;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service("BankAccountService")
@Transactional(
    readOnly = true
)
public class BankAccountServiceImpl implements BankAccountService {
    @Autowired
    private BankAccountDao BankAccountDao;

    public BankAccountServiceImpl() {
    }

    public BankAccountsEntity findUserByUserId(int userId) throws BankAccountsNotFoundException {
        return this.BankAccountDao.findUserByUserIdEnhenced(userId);
    }

    public List<BankAccountsEntity> findBankAccountsEntityByUsername(String username) throws BankAccountsNotFoundException, NoSuchAlgorithmException {
        return this.BankAccountDao.findBankAccountsEntityByUsername(username);
    }

    public BankAccountsEntity findBankAccountsEntityByEmailAndPassword(String username, String password) throws BankAccountsNotFoundException, NoSuchAlgorithmException {
        return null;
    }

    @Transactional(
        readOnly = false
    )
    public void saveUser(BankAccountsEntity BankAccount) {
        this.BankAccountDao.save(BankAccount);
    }

    public boolean memberIdIsUnique(String username) {
        return this.BankAccountDao.getIfSuppliedUsernameIsUnique(username);
    }

    public boolean suppliedEmailExists(String email) throws BankAccountsNotFoundException {
        return this.BankAccountDao.suppliedEmailExists(email);
    }

    public BankAccountsEntity findUserByEmail(String email) throws BankAccountsNotFoundException {
        return this.BankAccountDao.findUserByEmail(email);
    }

    public BankAccountsEntity findBankAccByAccNumber(String accountNumber) throws BankAccountsNotFoundException {
        return this.BankAccountDao.findBankAccByAccNumber(accountNumber);
    }

    public boolean findBankAccByAccNumberAndUserName(String accountNumber, String userName) {
        return this.BankAccountDao.findBankAccByAccNumberAndUserName(accountNumber, userName);
    }
}

