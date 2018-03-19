//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.goldenriches.domain.mainlist.service;

import com.goldenriches.domain.mainlist.exception.MainListNotFoundException;
import com.goldenriches.domain.mainlist.model.MainListEntity;
import java.security.NoSuchAlgorithmException;
import java.util.List;

public interface MainListService {
    List<MainListEntity> findDonorsByDonationReference(String var1);

    List<MainListEntity> findMainListEntityByUsername(String var1) throws MainListNotFoundException, NoSuchAlgorithmException;

    MainListEntity findDonationByMainListReference(String var1) throws MainListNotFoundException, NoSuchAlgorithmException;

    List<MainListEntity> findMainListsEntityByUsername(String var1) throws MainListNotFoundException, NoSuchAlgorithmException;

    MainListEntity findMainListsByDepositReference(String var1) throws MainListNotFoundException, NoSuchAlgorithmException;

    void saveUser(MainListEntity var1);

    void updateDonor(MainListEntity var1);

    boolean memberIdIsUnique(String var1);

    boolean suppliedEmailExists(String var1) throws MainListNotFoundException;

    List<MainListEntity> returnMainList(String var1) throws MainListNotFoundException;

    MainListEntity findBankAccByAccNumber(String var1) throws MainListNotFoundException;

    MainListEntity findMainRefByAccNumber(String var1) throws MainListNotFoundException;

    List<MainListEntity> returnPendingPayerList(String var1) throws MainListNotFoundException;

    List<MainListEntity> returnPendingApprovalReceiverList(String var1) throws MainListNotFoundException;

    List<MainListEntity> returnAdminUsers();

    boolean checkIfMainListAvailable() throws MainListNotFoundException;

    List<MainListEntity> UpdateNewMainList() throws MainListNotFoundException;

    List<MainListEntity> updateUsingTimeLapsed() throws MainListNotFoundException, NoSuchAlgorithmException;

    List<MainListEntity> getCompletedDonations() throws MainListNotFoundException;

    List<MainListEntity> returnCompletedDonation(String var1) throws MainListNotFoundException;
}

