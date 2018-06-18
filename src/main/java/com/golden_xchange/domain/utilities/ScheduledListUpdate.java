//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.golden_xchange.domain.utilities;

import com.golden_xchange.controller.createdonation.CreateDonationRequest;
import com.golden_xchange.controller.createdonation.CreateDonationWebserviceEndpoint;
import com.golden_xchange.controller.getmainlist.GetMainListAndDonationsWebserviceEndpoint;
import com.golden_xchange.domain.mainlist.exception.MainListNotFoundException;
import com.golden_xchange.domain.mainlist.model.MainListEntity;
import com.golden_xchange.domain.mainlist.service.MainListService;
import com.golden_xchange.domain.notifications.model.NotificationsEntity;
import com.golden_xchange.domain.notifications.service.NotificationsService;
import com.golden_xchange.domain.users.exception.GoldenRichesUsersNotFoundException;
import com.golden_xchange.domain.users.model.GoldenRichesUsers;
import com.golden_xchange.domain.users.service.GoldenRichesUsersService;
import org.apache.commons.lang.RandomStringUtils;
import org.apache.log4j.Logger;
import org.apache.struts.mock.MockHttpServletRequest;
import org.apache.struts.mock.MockHttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.security.NoSuchAlgorithmException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

@Configuration
@EnableAsync
@EnableScheduling

public class ScheduledListUpdate {
    Logger schedulerLog = Logger.getLogger(this.getClass().getName());
    @Autowired
    MainListService mainListService;

    @Autowired
    GoldenRichesUsersService goldenRichesUsersService;

    @Autowired
    NotificationsService notificationsService;

    @Autowired
    GetMainListAndDonationsWebserviceEndpoint getMainListAndDonationsWebserviceEndpoint;

    public ScheduledListUpdate() {
    }


    //1 hour
    @Scheduled(
            fixedDelay = 3600000L
    )
    public void updateMainList() {
        this.schedulerLog.info("started SchedulerListUpdate");

        try {

            this.schedulerLog.info("started checking reserved donations");
            List<MainListEntity> returnMainList = this.mainListService.donationsToReverse();
            for (MainListEntity upDate : returnMainList) {
                try {
                    MainListEntity mainDonation = mainListService.findDonationByMainListReference(upDate.getDonationReference());

                    if (returnMainList.size() != 0 && null != mainDonation) {
                        LocalDateTime endDate = LocalDateTime.now();
                        Date toDate = new Date();
                        new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                        Timestamp sqlDate = new Timestamp(toDate.getTime());
                        Iterator var7 = returnMainList.iterator();

                        long numberOfHours = Duration.between(upDate.getUpdatedDate().toLocalDateTime(), endDate).toHours();
                        this.schedulerLog.info("mainRef: " + upDate.getMainListReference() + " lapsed by: " + numberOfHours + " hours");
                        if (numberOfHours >= 12L) {
                            new MainListEntity();
                            upDate.setEnabled(0);
                            upDate.setStatus(4);
                            upDate.setUpdatedDate(sqlDate);
                            this.mainListService.saveUser(upDate);



                            if (upDate.getDonationType() == 1 || upDate.getDonationType() == 2) {
                                MainListEntity reverseDonation = this.mainListService.findDonationByMainListReference(upDate.getDonationReference());
                                reverseDonation.setAdjustedAmount(reverseDonation.getAdjustedAmount() + upDate.getDonatedAmount());
                                //reverseDonation.setUpdatedDate(sqlDate);
                                this.mainListService.saveUser(reverseDonation);
                            }
                            this.schedulerLog.info("Rolled Back Donation MainRef: " + upDate.getMainListReference() + " DonationRef: " + upDate.getDonationReference() + " amount rolled back: " + upDate.getDonatedAmount());

                            GoldenRichesUsers goldenRichesUsers =goldenRichesUsersService.findUserByMemberId(upDate.getPayerUsername());
                            goldenRichesUsers.setEnabled(0);
                            goldenRichesUsersService.saveUser(goldenRichesUsers);
                        }

                    }
                } catch (Exception exp) {
                    // do nothing
                }
            }
        } catch (MainListNotFoundException var22) {
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


    //20 minutes
    @Scheduled(
            fixedDelay = 1200000L
    )
    public void assignDonations() {

        try {
            List<MainListEntity> returnMainList = this.mainListService.pendingPayment();

            for (MainListEntity pending : returnMainList) {
                LocalDateTime endDate = LocalDateTime.now();
                long numberOfDays = Duration.between(pending.getUpdatedDate().toLocalDateTime(), endDate).toDays();
                List<MainListEntity> donationsToDonateTo;
                try {
                    donationsToDonateTo = mainListService.getMainList(pending.getUserName());
                } catch (Exception exp) {
                    continue;
                }
                double amountPaidToSponsor = 0;

                if (numberOfDays >= 28) {

                    Date utilDate = new Date();
                    new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                    Timestamp sqlDate = new Timestamp(utilDate.getTime());
                    GoldenRichesUsers sponsorProfile = goldenRichesUsersService.findUserByMemberId(pending.getUserName());
                    if (null != sponsorProfile.getReferenceUser() && !sponsorProfile.getReferenceUser().equals("")) {
                        List<MainListEntity> returnedSponsor = mainListService.findDonorsByDonationReference(pending.getMainListReference());

                        if (returnedSponsor.size() == 0 || returnedSponsor.get(0).getDonatedAmount() != (0.1D * pending.getDonatedAmount())) {
                            GoldenRichesUsers receiverProfile = goldenRichesUsersService.findUserByMemberId(sponsorProfile.getReferenceUser());
                            createInvester(pending, sqlDate, 0.1D, receiverProfile);
                           // amountPaidToSponsor = 0.1D * pending.getDonatedAmount();
                        }
                    }

                    for (MainListEntity donateTo : donationsToDonateTo) {
                        double alreadyReservedAmount = 0;

                        if (checkDateLimit(donateTo.getUpdatedDate())) {
                         //   List<MainListEntity> returnedSponsor = mainListService.findDonorsByDonationReference(donateTo.getMainListReference());
//                            for (MainListEntity alreadyReserved : returnedSponsor) {
//                                if (alreadyReserved.getDonationType() == 2) {
//                                    alreadyReservedAmount = alreadyReservedAmount + alreadyReserved.getDonatedAmount();
//                                }
//                            }
                            if (pending.getAdjustedAmount() > 0 ) {
                                createMainInvester(pending, sqlDate, donateTo.getAdjustedAmount(), sponsorProfile, donateTo);
                            }
                        }
                    }


                    //    getMainListAndDonationsWebserviceEndpoint.handleCreateGoldenRichesRequest(null,null,session,"autoRun");
                }
            }


        } catch (MainListNotFoundException e) {
            e.printStackTrace();
        } catch (GoldenRichesUsersNotFoundException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }


//    public void createForSponsor(GoldenRichesUsers goldenRichesUsers, CreateDonationRequest request, String ref){
//        Date utilDate = new Date();
//        new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//        Timestamp sqlDate = new Timestamp(utilDate.getTime());
//        try {
//            GoldenRichesUsers sponsorProfile=goldenRichesUsersService.findUserByMemberId(goldenRichesUsers.getReferenceUser());
//            addSponsors(request,  sqlDate, 0.1D, sponsorProfile);
//        } catch (GoldenRichesUsersNotFoundException e) {
//            e.printStackTrace();
//        }
//
//
//    }

    private void createInvester(MainListEntity request, Timestamp sqlDate, double sponsorPercentage, GoldenRichesUsers sponsorProfile) {
        try {
            mainListService.updateSponsorToInitiated(sponsorProfile.getUserName(),5,0);
        } catch (MainListNotFoundException e) {
            MainListEntity mainListEntity = new MainListEntity();
            mainListEntity.setStatus(5);
            mainListEntity.setUpdatedDate(sqlDate);
            mainListEntity.setAdjustedAmount(sponsorPercentage * request.getDonatedAmount());
            mainListEntity.setDonatedAmount(sponsorPercentage * request.getDonatedAmount());
            mainListEntity.setEnabled(0);
            mainListEntity.setBankAccountNumber(sponsorProfile.getAccountNumber());
            mainListEntity.setAmountToReceive(sponsorPercentage * request.getDonatedAmount());
            mainListEntity.setDate(sqlDate);
            mainListEntity.setMainListReference(RandomStringUtils.randomAlphanumeric(10).toUpperCase());
            mainListEntity.setDonationReference(request.getMainListReference());
            mainListEntity.setDepositReference(RandomStringUtils.randomAlphanumeric(10).toUpperCase());
            mainListEntity.setUserName(sponsorProfile.getUserName());
            mainListEntity.setPayerUsername(request.getUserName());
            mainListEntity.setDonationType(1);
            mainListService.saveUser(mainListEntity);
//            createNotificationMessage(request.getPayerUsername(), mainListEntity);
//            sendMessage(mainListEntity);
        }




//        double reduceDonatedAmount = request.getAdjustedAmount() - (sponsorPercentage * request.getDonatedAmount());
//        request.setAdjustedAmount(reduceDonatedAmount);
//        mainListService.saveUser(request);
//
//

    }


    private void createMainInvester(MainListEntity donateFrom, Timestamp sqlDate, double amountToDonate, GoldenRichesUsers sponsorProfile, MainListEntity donateTo) {
        double adjustAmount=donateFrom.getAdjustedAmount()-donateTo.getAdjustedAmount();
       if(((donateTo.getAdjustedAmount()-donateFrom.getAdjustedAmount())>=150 ||
               (donateTo.getAdjustedAmount()-donateFrom.getAdjustedAmount()==0) ||(donateFrom.getAdjustedAmount()-donateTo.getAdjustedAmount()>=150)) ) {


           if((donateFrom.getAdjustedAmount()-donateTo.getAdjustedAmount()>=150)){
               donateFrom.setAdjustedAmount(donateTo.getAdjustedAmount());
           }


           MainListEntity mainListEntity = new MainListEntity();
           mainListEntity.setStatus(0);
           mainListEntity.setUpdatedDate(sqlDate);
           mainListEntity.setAdjustedAmount(donateFrom.getAdjustedAmount());
           mainListEntity.setDonatedAmount(donateFrom.getAdjustedAmount());
           mainListEntity.setEnabled(1);
           mainListEntity.setBankAccountNumber(sponsorProfile.getAccountNumber());
           mainListEntity.setAmountToReceive(donateFrom.getAdjustedAmount());
           mainListEntity.setDate(sqlDate);
           mainListEntity.setMainListReference(RandomStringUtils.randomAlphanumeric(10).toUpperCase());
           mainListEntity.setDonationReference(donateTo.getMainListReference());
           mainListEntity.setDepositReference(RandomStringUtils.randomAlphanumeric(10).toUpperCase());
           mainListEntity.setUserName(donateTo.getUserName());
           mainListEntity.setPayerUsername(donateFrom.getUserName());
           mainListEntity.setDonationType(2);
           if(donateTo.getStatus()==1){
               mainListEntity.setKeeper(1);
           }
           mainListService.saveUser(mainListEntity);

           double reduceDonatedAmount = donateTo.getAdjustedAmount() - donateFrom.getAdjustedAmount();
           donateTo.setAdjustedAmount(reduceDonatedAmount);
           mainListService.saveUser(donateTo);

           if((donateFrom.getAdjustedAmount()-donateTo.getAdjustedAmount()>=150)){
               if(adjustAmount<-1){
                   donateFrom.setAdjustedAmount(0);
               }else{
                   donateFrom.setAdjustedAmount(adjustAmount);
               }
           }
           else{
               double reduceDonatedAmount1 = donateFrom.getAdjustedAmount() - donateFrom.getAdjustedAmount();
               donateFrom.setAdjustedAmount(reduceDonatedAmount1);
           }

           mainListService.saveUser(donateFrom);

           createNotificationMessage(donateFrom.getPayerUsername(), mainListEntity);
           sendMessage(mainListEntity);
       }


    }

    private void createNotificationMessage(String payerUsername, MainListEntity mainListEntity) {
        NotificationsEntity notificationsEntity = new NotificationsEntity();
        notificationsEntity.setMessage("DepositReference: " + mainListEntity.getDepositReference() + ", AmountPaid: " + mainListEntity.getDonatedAmount() + ", Status: Pending Payment Confirmation");
        notificationsEntity.setUserName(payerUsername);
        notificationsEntity.setCreationDate(new Date());
        notificationsEntity.setStatus(0);
        notificationsEntity.setMainListRef(mainListEntity.getMainListReference());
        notificationsService.save(notificationsEntity);
    }

    private void sendMessage(MainListEntity createDonation) {
        try {
            SendSms send = new SendSms();
            SendEmailMessages sendEmailMessages=new SendEmailMessages();
            GoldenRichesUsers goldenRichesUsers = goldenRichesUsersService.findUserByMemberId(createDonation.getUserName());
            send.send("sendSms.sh", goldenRichesUsers.getTelephoneNumber(), "MindSet24-7: Donation Created [" + createDonation.getUpdatedDate() + "]." + " DepositReference: " + createDonation.getDepositReference() + " AmountToPay: R" + createDonation.getDonatedAmount() + ". Confirm Before Payment [expires in 12hrs]");
            sendEmailMessages.sendMessage(goldenRichesUsers.getEmailAddress(),"","MindSet24-7: Donation Created [" + createDonation.getUpdatedDate() + "]." + " DepositReference: " + createDonation.getDepositReference() + " AmountToPay: R" + createDonation.getDonatedAmount() + ". Confirm Before Payment [expires in 12hrs]");
        } catch (Exception var11) {
            //do nothing
        }
    }


    public boolean checkDateLimit(Date date) {
        // convert Date into Java 8 LocalDate
        LocalDate localDate = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        LocalDate today = LocalDate.now();
        // count number of days between the given date and today
        long days = ChronoUnit.DAYS.between(localDate, today);
        return days > 30;
    }


    @Scheduled(
            fixedDelay = 1200000L
    )
    public void completePaidDonation() {
        try {
            List<MainListEntity> donationToPossiblyClose = this.mainListService.closeCompletedDonations();

            for(MainListEntity mainDonation:donationToPossiblyClose){
                List<MainListEntity> main=mainListService.findDonorsByDonationReference(mainDonation.getMainListReference());
                double totaldonated = 0;
                for(MainListEntity addMainListCompleted:main){
                    if((addMainListCompleted.getStatus()==2||addMainListCompleted.getStatus()==3) && addMainListCompleted.getKeeper()==1){
                        totaldonated=totaldonated+addMainListCompleted.getDonatedAmount();
                    }
                }
                if(mainDonation.getAmountToReceive()==totaldonated){
                    mainDonation.setStatus(3);
                    mainListService.saveUser(mainDonation);
                    List<MainListEntity> sponsorDonationPaid= mainListService.updateSponsorToInitiated(mainDonation.getUserName(),5,0);
                    sponsorDonationPaid.get(0).setStatus(6);
                    mainListService.saveUser(sponsorDonationPaid.get(0));
                }
//                else{
//                  MainListEntity mainListEntity=  mainListService.findSponsorDonation(mainDonation.getUserName(),mainDonation.getDonatedAmount());
//                  if( addMainListCompleted.getKeeper()==1)
//                    if(mainDonation.getAmountToReceive()==totaldonated+mainListEntity.getDonatedAmount() ){
//                        mainDonation.setStatus(3);
//                        mainListService.saveUser(mainDonation);
//                    }
//                }

            }
        } catch (MainListNotFoundException e) {
            e.printStackTrace();
        }

    }




}

