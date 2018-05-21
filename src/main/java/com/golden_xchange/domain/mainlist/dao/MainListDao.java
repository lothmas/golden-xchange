//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.golden_xchange.domain.mainlist.dao;

import com.golden_xchange.domain.mainlist.exception.MainListNotFoundException;
import com.golden_xchange.domain.mainlist.model.MainListEntity;
import com.golden_xchange.domain.utilities.AbstractDao;

import java.security.NoSuchAlgorithmException;
import java.util.List;

public interface MainListDao extends AbstractDao<MainListEntity, Integer> {
    List<MainListEntity> findDonorsByDonationReference(String var1);
    List<MainListEntity> findKeeperDonorsByDonationReference(String var1);

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

    List<MainListEntity> getMainList(String username) throws MainListNotFoundException;

    List<MainListEntity> findPaidDonationsPayerName(String payerUsername) throws MainListNotFoundException;

    public MainListEntity findDonationToStartMaturityProcess(String userName,double donatedAmount) throws  MainListNotFoundException;

    List<MainListEntity> outStandingPayment(String payerUsername) throws MainListNotFoundException;

    List<MainListEntity> returnKeeperList(String var1) throws MainListNotFoundException;

    List<MainListEntity> donationsToReverse() throws MainListNotFoundException;

    List<MainListEntity> getAllDonations() throws MainListNotFoundException;

    List<MainListEntity> getUserDonors(String ownerUser) throws MainListNotFoundException;


}

