//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.golden_xchange.domain.mainlist.service.impl;

import com.golden_xchange.domain.mainlist.dao.MainListDao;
import com.golden_xchange.domain.mainlist.exception.MainListNotFoundException;
import com.golden_xchange.domain.mainlist.model.MainListEntity;
import com.golden_xchange.domain.mainlist.service.MainListService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.NoSuchAlgorithmException;
import java.util.List;

@Service("MainListService")
@Transactional(
    readOnly = true
)
public class MainListServiceImpl implements MainListService {
    @Autowired
    private MainListDao mainListDao;

    public MainListServiceImpl() {
    }

    public List<MainListEntity> findDonorsByDonationReference(String mainListRef) {
        return this.mainListDao.findDonorsByDonationReference(mainListRef);
    }
    public List<MainListEntity> findKeeperDonorsByDonationReference(String mainListRef) {
        return this.mainListDao.findKeeperDonorsByDonationReference(mainListRef);
    }

    public List<MainListEntity> findMainListEntityByUsername(String username) throws MainListNotFoundException, NoSuchAlgorithmException {
        return null;
    }

    public MainListEntity findDonationByMainListReference(String mainListref) throws MainListNotFoundException, NoSuchAlgorithmException {
        return this.mainListDao.findDonationByMainListReference(mainListref);
    }

    public List<MainListEntity> findMainListsEntityByUsername(String username) throws MainListNotFoundException, NoSuchAlgorithmException {
        return this.mainListDao.findMainListEntityByUsername(username);
    }

    public MainListEntity findMainListsByDepositReference(String depositReference) throws MainListNotFoundException, NoSuchAlgorithmException {
        return this.mainListDao.findMainListsByDepositReference(depositReference);
    }

    @Transactional(
        readOnly = false
    )
    public void saveUser(MainListEntity MainList) {
        this.mainListDao.save(MainList);
    }

    @Transactional(
        readOnly = false
    )
    public void updateDonor(MainListEntity MainListEntity) {
        this.mainListDao.updateDonor(MainListEntity);
    }

    public boolean memberIdIsUnique(String username) {
        return this.mainListDao.getIfSuppliedUsernameIsUnique(username);
    }

    public boolean suppliedEmailExists(String email) throws MainListNotFoundException {
        return this.mainListDao.suppliedEmailExists(email);
    }

    public List<MainListEntity> returnMainList(String username) throws MainListNotFoundException {
        return this.mainListDao.returnMainList(username);
    }

    @Override
    public List<MainListEntity> returnKeeperList(String var1) throws MainListNotFoundException {
        return this.mainListDao.returnKeeperList(var1);
    }

    public MainListEntity findBankAccByAccNumber(String accountNumber) throws MainListNotFoundException {
        return this.mainListDao.findBankAccByAccNumber(accountNumber);
    }

    public MainListEntity findMainRefByAccNumber(String accountNumber) throws MainListNotFoundException {
        return this.mainListDao.findMainRefByAccNumber(accountNumber);
    }

    public List<MainListEntity> returnPendingPayerList(String payerUsername) throws MainListNotFoundException {
        return this.mainListDao.returnPendingPayerList(payerUsername);
    }

    public List<MainListEntity> returnPendingApprovalReceiverList(String username) throws MainListNotFoundException {
        return this.mainListDao.returnPendingApprovalReceiverList(username);
    }

    public boolean checkIfMainListAvailable() throws MainListNotFoundException {
        return this.mainListDao.checkIfMainListAvailable();
    }

    public List<MainListEntity> UpdateNewMainList() throws MainListNotFoundException {
        return this.mainListDao.UpdateNewMainList();
    }

    public List<MainListEntity> returnAdminUsers() {
        return this.mainListDao.returnAdminUsers();
    }

    public List<MainListEntity> updateUsingTimeLapsed() throws MainListNotFoundException, NoSuchAlgorithmException {
        return this.mainListDao.updateUsingTimeLapsed();
    }

    public List<MainListEntity> getCompletedDonations() throws MainListNotFoundException {
        return this.mainListDao.getCompletedDonations();
    }

    public List<MainListEntity> returnCompletedDonation(String mainreference) throws MainListNotFoundException {
        return this.mainListDao.returnCompletedDonation(mainreference);
    }

    @Override
    public List<MainListEntity> getMainList(String username) throws MainListNotFoundException {
        return this.mainListDao.getMainList(username);
    }

    @Override
    public List<MainListEntity> findPaidDonationsPayerName(String payerUsername) throws MainListNotFoundException {
        return this.mainListDao.findPaidDonationsPayerName(payerUsername);
    }

    @Override
    public MainListEntity findDonationToStartMaturityProcess(String userName, double donatedAmount) throws MainListNotFoundException {
        return this.mainListDao.findDonationToStartMaturityProcess(userName,donatedAmount);
    }

    @Override
    public List<MainListEntity> outStandingPayment(String payerUsername) throws MainListNotFoundException {
        return this.mainListDao.outStandingPayment(payerUsername);
    }


}

