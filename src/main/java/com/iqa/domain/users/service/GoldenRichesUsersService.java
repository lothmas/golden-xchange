//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.goldenriches.domain.users.service;

import com.goldenriches.domain.users.exception.GoldenRichesUsersNotFoundException;
import com.goldenriches.domain.users.model.GoldenRichesUsers;
import java.security.NoSuchAlgorithmException;

public interface GoldenRichesUsersService {
    GoldenRichesUsers findUserByUserId(int var1) throws GoldenRichesUsersNotFoundException;

    GoldenRichesUsers findGoldenRichesUsersByUsernameAndPassword(String var1, String var2) throws GoldenRichesUsersNotFoundException, NoSuchAlgorithmException;

    GoldenRichesUsers findGoldenRichesUsersByEmailAndPassword(String var1, String var2) throws GoldenRichesUsersNotFoundException, NoSuchAlgorithmException;

    void saveUser(GoldenRichesUsers var1);

    GoldenRichesUsers getUserByBankDetails(String var1) throws GoldenRichesUsersNotFoundException;

    boolean suppliedEmailExists(String var1) throws GoldenRichesUsersNotFoundException;

    GoldenRichesUsers findUserByEmail(String var1) throws GoldenRichesUsersNotFoundException;

    GoldenRichesUsers findUserByMemberId(String var1) throws GoldenRichesUsersNotFoundException;
}

