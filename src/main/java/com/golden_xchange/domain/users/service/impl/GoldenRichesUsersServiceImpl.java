//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.golden_xchange.domain.users.service.impl;

import com.golden_xchange.domain.users.dao.GoldenRichesUsersDao;
import com.golden_xchange.domain.users.exception.GoldenRichesUsersNotFoundException;
import com.golden_xchange.domain.users.model.GoldenRichesUsers;
import com.golden_xchange.domain.users.service.GoldenRichesUsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.NoSuchAlgorithmException;

@Service("GoldenRichesUsersService")
@Transactional(
    readOnly = true
)
public class GoldenRichesUsersServiceImpl implements GoldenRichesUsersService {
    @Autowired
    private GoldenRichesUsersDao GoldenRichesUsersDao;

    public GoldenRichesUsersServiceImpl() {
    }

    public GoldenRichesUsers findUserByUserId(int userId) throws GoldenRichesUsersNotFoundException {
        return this.GoldenRichesUsersDao.findUserByUserIdEnhenced(userId);
    }

    public GoldenRichesUsers findGoldenRichesUsersByUsernameAndPassword(String username, String password) throws GoldenRichesUsersNotFoundException, NoSuchAlgorithmException {
        return this.GoldenRichesUsersDao.findByUsernameAndPassword(username, password);
    }

    @Transactional(
        readOnly = false
    )
    public void saveUser(GoldenRichesUsers GoldenRichesUsers) {
        this.GoldenRichesUsersDao.save(GoldenRichesUsers);
    }

    public GoldenRichesUsers getUserByBankDetails(String username) throws GoldenRichesUsersNotFoundException {
        return this.GoldenRichesUsersDao.getUserByBankDetails(username);
    }

    public boolean suppliedEmailExists(String email) throws GoldenRichesUsersNotFoundException {
        return this.GoldenRichesUsersDao.suppliedEmailExists(email);
    }

    public GoldenRichesUsers findUserByEmail(String email) throws GoldenRichesUsersNotFoundException {
        return this.GoldenRichesUsersDao.findUserByEmail(email);
    }

    public GoldenRichesUsers findUserByMemberId(String memberId) throws GoldenRichesUsersNotFoundException {
        return this.GoldenRichesUsersDao.findUserByMemberId(memberId);
    }

    public GoldenRichesUsers findGoldenRichesUsersByEmailAndPassword(String email, String password) throws GoldenRichesUsersNotFoundException, NoSuchAlgorithmException {
        return this.GoldenRichesUsersDao.findGoldenRichesUsersByEmailAndPassword(email, password);
    }
}

