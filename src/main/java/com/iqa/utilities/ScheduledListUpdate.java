package com.iqa.utilities;////
//// Source code recreated from a .class file by IntelliJ IDEA
//// (powered by Fernflower decompiler)
////
//
//package com.iqa.utilities;
//
//import com.goldenriches.domain.mainlist.exception.MainListNotFoundException;
//import com.goldenriches.domain.mainlist.model.MainListEntity;
//import com.goldenriches.domain.mainlist.service.MainListService;
//import com.goldenriches.domain.users.exception.IndividualProfileNotFoundException;
//import com.goldenriches.domain.users.service.IndividualProfileService;
//import org.apache.commons.lang.RandomStringUtils;
//import org.apache.log4j.Logger;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.scheduling.annotation.Scheduled;
//
//import java.io.IOException;
//import java.nio.file.Files;
//import java.nio.file.Paths;
//import java.security.NoSuchAlgorithmException;
//import java.sql.Timestamp;
//import java.text.SimpleDateFormat;
//import java.time.Duration;
//import java.time.LocalDateTime;
//import java.util.Date;
//import java.util.Iterator;
//import java.util.List;
//import java.util.stream.Stream;
//
//public class ScheduledListUpdate {
//    Logger schedulerLog = Logger.getLogger(this.getClass().getName());
//    @Autowired
//    MainListService mainListService;
//
//    @Autowired
//    IndividualProfileService IndividualProfileService;
//
//    public ScheduledListUpdate() {
//    }
//
//    @Scheduled(
//        fixedDelay = 600000L
//    )
//    public void updateMainList() {
//        this.schedulerLog.info("started SchedulerListUpdate");
//
//        try {
//            List returnMainList;
//            MainListEntity reverseDonation;
//            if(this.mainListService.checkIfMainListAvailable()) {
//                returnMainList = this.mainListService.UpdateNewMainList();
//                if(null != returnMainList) {
//                    Iterator var2 = returnMainList.iterator();
//
//                    while(var2.hasNext()) {
//                        reverseDonation = (MainListEntity)var2.next();
//                        reverseDonation.setEnabled(1);
//                        this.mainListService.saveUser(reverseDonation);
//                        this.schedulerLog.info("Upadated MainRef: " + reverseDonation.getMainListReference() + " to MAINLIST");
//                    }
//                }
//
//                String fileName = "/home/GRusers.txt";
//
//                try {
//                    Stream<String> stream = Files.lines(Paths.get(fileName, new String[0]));
//                    Throwable var4 = null;
//
//                    try {
//                        stream.forEach(this::updateAdmin);
//                    } catch (Throwable var19) {
//                        var4 = var19;
//                        throw var19;
//                    } finally {
//                        if(stream != null) {
//                            if(var4 != null) {
//                                try {
//                                    stream.close();
//                                } catch (Throwable var18) {
//                                    var4.addSuppressed(var18);
//                                }
//                            } else {
//                                stream.close();
//                            }
//                        }
//
//                    }
//                } catch (IOException var21) {
//                    this.schedulerLog.info(var21.getMessage());
//                }
//            }
//
//            this.schedulerLog.info("started checking reserved donations");
//            returnMainList = this.mainListService.updateUsingTimeLapsed();
//            if(returnMainList.size() != 0) {
//                LocalDateTime endDate = LocalDateTime.now();
//                Date toDate = new Date();
//                new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//                Timestamp sqlDate = new Timestamp(toDate.getTime());
//                Iterator var7 = returnMainList.iterator();
//
//                while(var7.hasNext()) {
//                    MainListEntity upDate = (MainListEntity)var7.next();
//                    long numberOfHours = Duration.between(upDate.getUpdatedDate().toLocalDateTime(), endDate).toHours();
//                    this.schedulerLog.info("mainRef: " + upDate.getMainListReference() + " lapsed by: " + numberOfHours + " hours");
//                    if(numberOfHours >= 5L) {
//                        new MainListEntity();
//                        upDate.setStatus(4);
//                        upDate.setUpdatedDate(sqlDate);
//                        this.mainListService.saveUser(upDate);
//                        reverseDonation = this.mainListService.findDonationByMainListReference(upDate.getDonationReference());
//                        reverseDonation.setAdjustedAmount(reverseDonation.getAdjustedAmount() + upDate.getDonatedAmount());
//                        reverseDonation.setUpdatedDate(sqlDate);
//                        this.mainListService.saveUser(reverseDonation);
//                        this.schedulerLog.info("Rolled Back Donation MainRef: " + upDate.getMainListReference() + " DonationRef: " + upDate.getDonationReference() + " amount rolled back: " + upDate.getDonatedAmount());
//                    }
//                }
//            }
//        } catch (NoSuchAlgorithmException | MainListNotFoundException var22) {
//            this.schedulerLog.info("Error running Scheduler: " + var22.getMessage());
//        }
//
//    }
//
//    public void updateAdmin(String s) {
//        schedulerLog.info("In Progress Updating AdminUsers");
//
//        Date toDate = new Date();
//        SimpleDateFormat f = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//        Timestamp sqlDate = new Timestamp(toDate.getTime());
//        String[] adminUsers = s.split(",");
//
//        try {
//            IndividualProfileService.findUserByMemberId(adminUsers[2].trim());
//            MainListEntity newAdminTransaction = new MainListEntity();
//            newAdminTransaction.setBankAccountNumber(adminUsers[0].trim());
//            newAdminTransaction.setEnabled(1);
//            newAdminTransaction.setUpdatedDate(sqlDate);
//            newAdminTransaction.setStatus(2);
//            newAdminTransaction.setDonatedAmount(Double.valueOf(adminUsers[1].trim()));
//            newAdminTransaction.setAdjustedAmount(Double.valueOf(adminUsers[1].trim()) + (0.3 * Double.valueOf(adminUsers[1].trim())));
//            newAdminTransaction.setAmountToReceive(newAdminTransaction.getAdjustedAmount());
//
//            newAdminTransaction.setUserName("Admin");
//            String mainRef = RandomStringUtils.randomAlphanumeric(10).toUpperCase();
//            String donationRef = RandomStringUtils.randomAlphanumeric(10).toUpperCase();
//            newAdminTransaction.setMainListReference(mainRef);
//            newAdminTransaction.setDonationReference(donationRef);
//            newAdminTransaction.setDepositReference(RandomStringUtils.randomAlphanumeric(10).toUpperCase());
//            newAdminTransaction.setDate(sqlDate);
//            newAdminTransaction.setPayerUsername(adminUsers[2].trim());
//            mainListService.saveUser(newAdminTransaction);
//            schedulerLog.info("admin user rolledin accountNumber: "+ adminUsers[0].trim() +"amount: "+adminUsers[1].trim() +" mainRef: "+mainRef);
//
//        }
//        catch (IndividualProfileNotFoundException ntfnd){
//            schedulerLog.warn("No Admin User Added: "+ ntfnd.getMessage());
//
//        }
//        catch (Exception exp)
//        {
//            schedulerLog.error(exp.getMessage());
//
//        }
//
//
//    }
//}
//
