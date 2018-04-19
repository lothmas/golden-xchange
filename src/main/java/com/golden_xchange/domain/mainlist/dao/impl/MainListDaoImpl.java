//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.golden_xchange.domain.mainlist.dao.impl;

import com.golden_xchange.domain.mainlist.dao.MainListDao;
import com.golden_xchange.domain.mainlist.exception.MainListNotFoundException;
import com.golden_xchange.domain.mainlist.model.MainListEntity;
import com.golden_xchange.domain.mainlist.service.MainListService;
import com.golden_xchange.domain.users.model.GoldenRichesUsers;
import com.golden_xchange.domain.utilities.AbstractDaoImpl;
import org.apache.log4j.Logger;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.transform.Transformers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.security.NoSuchAlgorithmException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;

@Repository
public class MainListDaoImpl extends AbstractDaoImpl<MainListEntity, Integer> implements MainListDao {
    Logger schedulerLog = Logger.getLogger(this.getClass().getName());
    @Autowired
    MainListService donationService;
    LocalDateTime endDate = LocalDateTime.now();
    MainListEntity reverseDonation;
    Date toDate = new Date();
    SimpleDateFormat f = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    Timestamp sqlDate;
    private final Logger log;
    Logger MainListEntityDaoImplLogger;

    protected MainListDaoImpl() {
        super(MainListEntity.class);
        this.sqlDate = new Timestamp(this.toDate.getTime());
        this.log = Logger.getLogger(MainListDaoImpl.class);
        this.MainListEntityDaoImplLogger = Logger.getLogger(this.getClass().getName());
    }

    public void save(MainListEntity MainListEntity) {
        this.saveOrUpdate(MainListEntity);

    }

    public void updateDonor(MainListEntity MainList) {
    }

    public void updateDonor(MainListEntity MainListEntity, String depositReference, String mainListReference, String donorUsername) throws NoSuchAlgorithmException, MainListNotFoundException {
        MainListEntity mainList = this.findDonationByMainListReference(mainListReference);
        if (!mainList.getDepositReference().equals(depositReference)) {
            throw new MainListNotFoundException("Forbidden operation Can't update transaction status");
        } else if (!mainList.getMainListReference().equals(mainListReference)) {
            throw new MainListNotFoundException("Forbidden operation Can't update transaction status");
        } else if (!mainList.getPayerUsername().equals(donorUsername)) {
            throw new MainListNotFoundException("Forbidden operation Can't update transaction status");
        } else {
            mainList.setStatus(2);
            this.saveOrUpdate(mainList);
        }
    }

    public List<MainListEntity> findDonorsByDonationReference(String donorRef) {
        List<MainListEntity> result = this.getCurrentSession().createCriteria(MainListEntity.class)
                .add(Restrictions.eq("donationReference", donorRef)).list();
        return result;
    }

    public List<MainListEntity> findKeeperDonorsByDonationReference(String donorRef) {
        List<MainListEntity> result = this.getCurrentSession().createCriteria(MainListEntity.class)
                .add(Restrictions.eq("donationReference", donorRef))
                .add(Restrictions.eq("keeper", 1))
                .list();
        return result;
    }

    public MainListEntity findMainListsByDepositReference(String depositReference) throws MainListNotFoundException, NoSuchAlgorithmException {
        List<MainListEntity> result = this.getCurrentSession().createCriteria(MainListEntity.class)
                .add(Restrictions.eq("depositReference", depositReference)).list();
        if (result != null && !result.isEmpty()) {
            return (MainListEntity) result.get(0);
        } else {
            throw new MainListNotFoundException("No donation found associated with depositReference: " + depositReference);
        }
    }

    public boolean getIfSuppliedUsernameIsUnique(String username) {
        boolean result = false;
        username = username.replaceAll(" ", "");
        List<MainListEntity> results = this.getCurrentSession().createCriteria(MainListEntity.class).add(Restrictions.eq("username", username)).list();
        if (results.isEmpty()) {
            result = true;
        }

        if (!results.isEmpty()) {
            result = false;
        }

        return result;
    }

    public boolean suppliedEmailExists(String email) throws MainListNotFoundException {
        boolean result = true;
        email = email.replaceAll(" ", "");
        List<MainListEntity> results = this.getCurrentSession().createCriteria(MainListEntity.class).add(Restrictions.eq("emailAddress", email)).list();
        if (results.isEmpty()) {
            result = false;
        }

        if (!results.isEmpty()) {
            result = true;
        }

        return result;
    }

    public List<MainListEntity> returnMainList(String username) throws MainListNotFoundException {
        List<MainListEntity> returnMainList = this.getCurrentSession().createCriteria(MainListEntity.class)
                .add(Restrictions.eq("enabled", 1))
                .add(Restrictions.eq("payerUsername", username))
                .add(Restrictions.or(Restrictions.eq("donationType", 1), Restrictions.or(Restrictions.eq("donationType",2))))
                .add(Restrictions.ne("status", 3))
                .add(Restrictions.gt("adjustedAmount", 0.0))
                .list();

        if (null == returnMainList) {
            throw new MainListNotFoundException("No Donations to Display on the List");
        } else {
            return returnMainList;
        }

    }

    public MainListEntity findBankAccByAccNumber(String accountNumber) throws MainListNotFoundException {
        List<MainListEntity> results = this.getCurrentSession().createCriteria(MainListEntity.class).add(Restrictions.eq("bankAccountNumber", accountNumber)).list();
        if (results.isEmpty()) {
            throw new MainListNotFoundException("No MainList found associated with accountNumber:" + accountNumber);
        } else if (results.size() != 1) {
            throw new MainListNotFoundException("There are several MainListEntity found associated with accountNumber:" + accountNumber);
        } else {
            return (MainListEntity) results.get(0);
        }
    }

    public MainListEntity findMainRefByAccNumber(String accountNumber) throws MainListNotFoundException {
        List<MainListEntity> results = this.getCurrentSession().createCriteria(MainListEntity.class).add(Restrictions.eq("bankAccountNumber", accountNumber)).list();
        if (results.isEmpty()) {
            throw new MainListNotFoundException("No MainList found associated with accountNumber:" + accountNumber);
        } else {
            return (MainListEntity) results.get(0);
        }
    }

    public MainListEntity findDonationByMainListReference(String mainListRefernce) throws MainListNotFoundException, NoSuchAlgorithmException {
        List<MainListEntity> results = this.getCurrentSession().createCriteria(MainListEntity.class)
                .add(Restrictions.eq("mainListReference", mainListRefernce)).list();
        if (results.isEmpty()) {
            throw new MainListNotFoundException("No Donation found for MainList with ref:" + mainListRefernce);
        } else {
            return (MainListEntity) results.get(0);
        }
    }

    public List<MainListEntity> returnAdminUsers() {
        List<MainListEntity> results = this.getCurrentSession().createCriteria(MainListEntity.class).add(Restrictions.eq("status", Integer.valueOf(5))).add(Restrictions.eq("enabled", Integer.valueOf(1))).list();
        if (results.isEmpty()) {
            ;
        }

        return results;
    }

    public List<MainListEntity> findMainListEntityByUsername(String username) throws MainListNotFoundException {
        List<MainListEntity> results = this.getCurrentSession().createCriteria(MainListEntity.class)
                .add(Restrictions.eq("userName", username)).list();
        if (results.isEmpty()) {
            throw new MainListNotFoundException("No MainList found associated with username:" + username);
        } else {
            return results;
        }
    }

    public List<MainListEntity> returnPendingPayerList(String payerUsername) throws MainListNotFoundException {
        List<MainListEntity> results = this.getCurrentSession().createCriteria(MainListEntity.class)
                .add(Restrictions.or(Restrictions.eq("payerUsername", payerUsername), Restrictions
                .or(Restrictions.eq("userName",payerUsername))))
                .list();
        if (results.isEmpty()) {
            throw new MainListNotFoundException("No PendigList found for username:" + payerUsername);
        } else {
            return results;
        }
    }

    public List<MainListEntity> returnPendingApprovalReceiverList(String username) throws MainListNotFoundException {
        List<MainListEntity> results = this.getCurrentSession().createCriteria(MainListEntity.class)
                .add(Restrictions.eq("status", Integer.valueOf(1)))
                .add(Restrictions.eq("enabled", Integer.valueOf(0)))
                .add(Restrictions.eq("userName", username)).list();
        if (results.isEmpty()) {
            throw new MainListNotFoundException("No Donations found Pending Approval  found");
        } else {
            return results;
        }
    }

    public boolean checkIfMainListAvailable() throws MainListNotFoundException {
        List<MainListEntity> returnMainList = this.getCurrentSession().createCriteria(MainListEntity.class)
                .add(Restrictions.eq("enabled", Integer.valueOf(1)))
                .add(Restrictions.eq("status", Integer.valueOf(2)))
                .add(Restrictions.gt("adjustedAmount", 0.0))
                .list();
        if (!returnMainList.isEmpty() && returnMainList != null) {
            this.schedulerLog.info(returnMainList.size() + " Donations Still Available on the MainList");
            return false;
        } else {
            this.schedulerLog.info("set to update new list");
            return true;
        }
    }

    public List<MainListEntity> UpdateNewMainList() throws MainListNotFoundException {
        List<MainListEntity> returnMainList = this.getCurrentSession().createCriteria(MainListEntity.class).add(Restrictions.eq("enabled", Integer.valueOf(0))).add(Restrictions.eq("status", Integer.valueOf(2))).list();
        return returnMainList;
    }

    public List<MainListEntity> updateUsingTimeLapsed() throws MainListNotFoundException, NoSuchAlgorithmException {
        List<MainListEntity> returnMainList = this.getCurrentSession().createCriteria(MainListEntity.class).add(Restrictions.eq("status", Integer.valueOf(0))).add(Restrictions.eq("enabled", Integer.valueOf(0))).list();
        this.schedulerLog.info("number of reserved donations found: " + returnMainList.size());
        return returnMainList;
    }

    public List<MainListEntity> getCompletedDonations() throws MainListNotFoundException {
        List<MainListEntity> results = this.getCurrentSession().createCriteria(MainListEntity.class)
                .add(Restrictions.eq("status", Integer.valueOf(3)))
                .add(Restrictions.eq("enabled", Integer.valueOf(0)))
                .list();
        if (results.isEmpty()) {
            throw new MainListNotFoundException("No CompletedDonations found:");
        } else {
            return results;
        }
    }

    public List<MainListEntity> returnCompletedDonation(String mainreference) throws MainListNotFoundException {
        List<MainListEntity> results = this.getCurrentSession().createCriteria(MainListEntity.class).add(Restrictions.eq("donationReference", mainreference)).list();
        if (results.isEmpty()) {
            throw new MainListNotFoundException("No CompletedDonations found:");
        } else {
            return results;
        }
    }

    @Override
    public List<MainListEntity> getMainList(String username) throws MainListNotFoundException {
        List<MainListEntity> returnMainList = null;
        try {
            returnMainList = this.getCurrentSession().createCriteria(MainListEntity.class)
                    .add(Restrictions.ne("userName", username))
                    .add(Restrictions.eq("status", 1))
                    .add(Restrictions.gt("adjustedAmount", 0.0))
//                    .add(Restrictions.gt("updatedDate", cal.getTime()))
                    .add(Restrictions.eq("donationType", 0))
                    .add(Restrictions.eq("enabled", 1))
//                    .addOrder(Order.desc("updatedDate"))
                    .list();
        } catch (Exception exp) {
            throw new MainListNotFoundException("No MainListFound found:");
        }
        if (null == returnMainList || returnMainList.size() == 0) {
            throw new MainListNotFoundException("No MainListFound found:");
        } else {
            Collections.reverse(returnMainList);
            return returnMainList;
        }
    }

    @Override
    public List<MainListEntity> findPaidDonationsPayerName(String payerUsername) throws MainListNotFoundException {
        List<MainListEntity> returnMainList = this.getCurrentSession().createCriteria(MainListEntity.class)
                .add(Restrictions.eq("enabled", 1))
                .add(Restrictions.ne("donationType",0))
                .add(Restrictions.ne("status",0))
                .add(Restrictions.gt("adjustedAmount", 0.0))
                .add(Restrictions.eq("payerUsername",payerUsername))
                .list();
        if (returnMainList.size() == 0) {
            throw new MainListNotFoundException("No MainListFound found:");

        } else {
            return returnMainList;
        }
    }

    @Override
    public MainListEntity findDonationToStartMaturityProcess(String userName,double donatedAmount) throws MainListNotFoundException {
        List<MainListEntity> returnMainList = this.getCurrentSession().createCriteria(MainListEntity.class)
                .add(Restrictions.eq("enabled", 1))
                .add(Restrictions.eq("donationType",0))
                .add(Restrictions.eq("status",0))
                .add(Restrictions.gt("adjustedAmount", 0.0))
                .add(Restrictions.eq("userName",userName))
                .add(Restrictions.eq("donatedAmount",donatedAmount))
                .list();
        if (returnMainList.size() == 0 ) {
            throw new MainListNotFoundException("No MainListFound found:");
        }
        else  if (returnMainList.size() >1 ) {
            throw new MainListNotFoundException("More Than 1 transaction to update to start maturity for user: " +userName);
        }else {
            return returnMainList.get(0);
        }
    }

    @Override
    public List<MainListEntity> outStandingPayment(String payerUsername) throws MainListNotFoundException {
        List<MainListEntity> returnMainList = this.getCurrentSession().createCriteria(MainListEntity.class)
                .add(Restrictions.eq("enabled", 1))
                .add(Restrictions.or(Restrictions.eq("status", 0), Restrictions
                        .or(Restrictions.eq("status",1))))
                .add(Restrictions.gt("adjustedAmount", 0.0))
                .add(Restrictions.eq("payerUsername",payerUsername))
                .list();
        if (returnMainList.size() == 0 ) {
            throw new MainListNotFoundException("No MainListFound found:");
        }
       else {
            return returnMainList;
        }
    }


}

