//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.goldenriches.domain.mainlist.dao;

import com.goldenriches.domain.mainlist.exception.MainListNotFoundException;
import com.goldenriches.domain.mainlist.model.MainListEntity;
import com.goldenriches.domain.utilities.AbstractDao;
import java.security.NoSuchAlgorithmException;
import java.util.List;

public interface MainListDao extends AbstractDao<MainListEntity, Integer> {
    List<MainListEntity> findDonorsByDonationReference(String var1);

    MainListEntity findMainListsByDepositReference(String var1) throws MainListNotFoundException, NoSuchAlgorithmException;

    void save(MainListEntity var1);

    void updateDonor(MainListEntity var1);

    boolean getIfSuppliedUsernameIsUnique(String var1);

    boolean suppliedEmailExists(String var1) throws MainListNotFoundException;

    List<MainListEntity> returnMainList(String var1) throws MainListNotFoundException;

    MainListEntity findBankAccByAccNumber(String var1) throws MainListNotFoundException;

    MainListEntity findMainRefByAccNumber(String var1) throws MainListNotFoundException;

    List<MainListEntity> returnAdminUsers();

    MainListEntity findDonationByMainListReference(String var1) throws MainListNotFoundException, NoSuchAlgorithmException;

    List<MainListEntity> findMainListEntityByUsername(String var1) throws MainListNotFoundException, NoSuchAlgorithmException;

    List<MainListEntity> returnPendingPayerList(String var1) throws MainListNotFoundException;

    List<MainListEntity> returnPendingApprovalReceiverList(String var1) throws MainListNotFoundException;

    boolean checkIfMainListAvailable() throws MainListNotFoundException;

    List<MainListEntity> UpdateNewMainList() throws MainListNotFoundException;

    List<MainListEntity> updateUsingTimeLapsed() throws MainListNotFoundException, NoSuchAlgorithmException;

    List<MainListEntity> getCompletedDonations() throws MainListNotFoundException;

    List<MainListEntity> returnCompletedDonation(String var1) throws MainListNotFoundException;
}

