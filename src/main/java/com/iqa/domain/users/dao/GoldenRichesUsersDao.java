//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.goldenriches.domain.users.dao;

import com.goldenriches.domain.users.exception.GoldenRichesUsersNotFoundException;
import com.goldenriches.domain.users.model.GoldenRichesUsers;
import com.goldenriches.domain.utilities.AbstractDao;
import java.security.NoSuchAlgorithmException;

public interface GoldenRichesUsersDao extends AbstractDao<GoldenRichesUsers, Integer> {
    GoldenRichesUsers findUserByUserIdEnhenced(int var1) throws GoldenRichesUsersNotFoundException;

    GoldenRichesUsers findByUsernameAndPassword(String var1, String var2) throws GoldenRichesUsersNotFoundException, NoSuchAlgorithmException;

    void save(GoldenRichesUsers var1);

    GoldenRichesUsers getUserByBankDetails(String var1) throws GoldenRichesUsersNotFoundException;

    boolean suppliedEmailExists(String var1) throws GoldenRichesUsersNotFoundException;

    GoldenRichesUsers findUserByEmail(String var1) throws GoldenRichesUsersNotFoundException;

    GoldenRichesUsers findUserByMemberId(String var1) throws GoldenRichesUsersNotFoundException;

    GoldenRichesUsers findGoldenRichesUsersByEmailAndPassword(String var1, String var2) throws GoldenRichesUsersNotFoundException, NoSuchAlgorithmException;
}

