//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.golden_xchange.domain.utilities;

import com.golden_xchange.domain.mainlist.exception.MainListNotFoundException;
import com.golden_xchange.domain.mainlist.model.MainListEntity;
import com.golden_xchange.domain.mainlist.service.MainListService;
import com.golden_xchange.domain.users.exception.GoldenRichesUsersNotFoundException;
import com.golden_xchange.domain.users.service.GoldenRichesUsersService;
import org.apache.commons.lang.RandomStringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import java.security.NoSuchAlgorithmException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

@Configuration
@EnableScheduling
public class ScheduledListUpdate {
    Logger schedulerLog = Logger.getLogger(this.getClass().getName());
    @Autowired
    MainListService mainListService;

    @Autowired
    GoldenRichesUsersService goldenRichesUsersService;

    public ScheduledListUpdate() {
    }

    @Scheduled(
            fixedDelay = 600000L
    )
    public void updateMainList() {
        this.schedulerLog.info("started SchedulerListUpdate");

        try {

            this.schedulerLog.info("started checking reserved donations");
            List<MainListEntity> returnMainList = this.mainListService.donationsToReverse();
            for (MainListEntity upDate : returnMainList) {
                try {
                    MainListEntity mainDonation=  mainListService.findDonationByMainListReference(upDate.getDonationReference());

                    if (returnMainList.size() != 0 && null!=mainDonation) {
                        LocalDateTime endDate = LocalDateTime.now();
                        Date toDate = new Date();
                        new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                        Timestamp sqlDate = new Timestamp(toDate.getTime());
                        Iterator var7 = returnMainList.iterator();

                        long numberOfHours = Duration.between(upDate.getUpdatedDate().toLocalDateTime(), endDate).toHours();
                        this.schedulerLog.info("mainRef: " + upDate.getMainListReference() + " lapsed by: " + numberOfHours + " hours");
                        if (numberOfHours >= 12L) {
                            new MainListEntity();
                            upDate.setStatus(4);
                            upDate.setUpdatedDate(sqlDate);
                            this.mainListService.saveUser(upDate);
                            if (upDate.getDonationType() == 0) {
                                MainListEntity reverseDonation = this.mainListService.findDonationByMainListReference(upDate.getDonationReference());
                                reverseDonation.setAdjustedAmount(reverseDonation.getAdjustedAmount() + upDate.getDonatedAmount());
                                reverseDonation.setUpdatedDate(sqlDate);
                                this.mainListService.saveUser(reverseDonation);
                            }
                            this.schedulerLog.info("Rolled Back Donation MainRef: " + upDate.getMainListReference() + " DonationRef: " + upDate.getDonationReference() + " amount rolled back: " + upDate.getDonatedAmount());
                        }

                    }
                }catch(Exception exp){
                // do nothing
                }
            }
        } catch ( MainListNotFoundException var22) {
            this.schedulerLog.info("Error running Scheduler: " + var22.getMessage());
        }

    }

    public void updateAdmin(String s) {
        schedulerLog.info("In Progress Updating AdminUsers");

        Date toDate = new Date();
        SimpleDateFormat f = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Timestamp sqlDate = new Timestamp(toDate.getTime());
        String[] adminUsers = s.split(",");

        try {
            goldenRichesUsersService.findUserByMemberId(adminUsers[2].trim());
            if (adminUsers[2].trim().equals(goldenRichesUsersService.getUserByBankDetails(adminUsers[0].trim()).getUserName())) {
                MainListEntity newAdminTransaction = new MainListEntity();
                newAdminTransaction.setBankAccountNumber(adminUsers[0].trim());
                newAdminTransaction.setEnabled(1);
                newAdminTransaction.setUpdatedDate(sqlDate);
                newAdminTransaction.setStatus(2);
                newAdminTransaction.setDonatedAmount(Double.valueOf(adminUsers[1].trim()));
                newAdminTransaction.setAdjustedAmount(Double.valueOf(adminUsers[1].trim()));
                newAdminTransaction.setAmountToReceive(newAdminTransaction.getAdjustedAmount());
                newAdminTransaction.setUserName("Admin");
                String mainRef = RandomStringUtils.randomAlphanumeric(10).toUpperCase();
                String donationRef = RandomStringUtils.randomAlphanumeric(10).toUpperCase();
                newAdminTransaction.setMainListReference(mainRef);
                newAdminTransaction.setDonationReference(donationRef);
                newAdminTransaction.setDepositReference(RandomStringUtils.randomAlphanumeric(10).toUpperCase());
                newAdminTransaction.setDate(sqlDate);
                newAdminTransaction.setPayerUsername(adminUsers[2].trim());
                mainListService.saveUser(newAdminTransaction);
                schedulerLog.info("admin user rolledin accountNumber: " + adminUsers[0].trim() + "amount: " + adminUsers[1].trim() + " mainRef: " + mainRef);
            } else {
                schedulerLog.error("Admin user: with accNo." + adminUsers[0].trim() + " username: " + adminUsers[2].trim() + " not Added because of a mismatch");

            }
        } catch (GoldenRichesUsersNotFoundException ntfnd) {
            schedulerLog.warn("No Admin User Added: " + ntfnd.getMessage());

        } catch (Exception exp) {
            schedulerLog.error(exp.getMessage());

        }


    }
}

