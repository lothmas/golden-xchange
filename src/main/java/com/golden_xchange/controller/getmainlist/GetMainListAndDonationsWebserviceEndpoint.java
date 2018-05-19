/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.golden_xchange.controller.getmainlist;


import com.golden_xchange.controller.createdonation.CreateDonationRequest;
import com.golden_xchange.controller.createdonation.CreateDonationWebserviceEndpoint;
import com.golden_xchange.domain.bankaccounts.model.BankAccountsEntity;
import com.golden_xchange.domain.bankaccounts.service.BankAccountService;
import com.golden_xchange.domain.mainlist.exception.MainListNotFoundException;
import com.golden_xchange.domain.mainlist.model.MainListEntity;
import com.golden_xchange.domain.mainlist.service.MainListService;
import com.golden_xchange.domain.users.exception.GoldenRichesUsersNotFoundException;
import com.golden_xchange.domain.users.model.GoldenRichesUsers;
import com.golden_xchange.domain.users.service.GoldenRichesUsersService;
import com.golden_xchange.domain.utilities.Enums;
import org.apache.commons.lang.RandomStringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;

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
import java.util.Collections;
import java.util.Date;
import java.util.List;

/**
 * @author louis
 */
@ControllerAdvice
@Controller
public class GetMainListAndDonationsWebserviceEndpoint {

    @Autowired
    MainListService donationService;
    @Autowired
    MainListService mainListService;

    @Autowired
    BankAccountService bankAccountService;

    @Autowired
    GoldenRichesUsersService goldenRichesUsersService;

    @Autowired
    CreateDonationWebserviceEndpoint createDonationWebserviceEndpoint;


    BankAccountsEntity createBankAccount = new BankAccountsEntity();
    Logger mainListLogger = Logger.getLogger(this.getClass().getName());
    static double amountToSponsors = 0;
    static boolean addNew = true;
    static String returnUrl = null;

    @RequestMapping({"/current_donations", "/donation_state", "/maturity"})
    public String handleCreateGoldenRichesRequest(HttpServletRequest requests, Model model, HttpSession session, String automatic)
            throws Exception {
        addNew = true;
        returnUrl = null;
        GetMainListResponse response = new GetMainListResponse();
        GoldenRichesUsers goldenRichesUsers = null;
        try {
            goldenRichesUsers = (GoldenRichesUsers) session.getAttribute("profile");
            model.addAttribute("profile", goldenRichesUsers);

            model.addAttribute("notifications", session.getAttribute("notifications"));
            model.addAttribute("notificationCount", session.getAttribute("notificationCount"));
        } catch (NullPointerException nulls) {
            //do nothing
        }
        try {
            String url;
            try {
                url = requests.getRequestURI();
            } catch (NullPointerException npe) {
                url = "auto";
            }

            if (null == goldenRichesUsers && null == automatic) {
                return "index";
            }
            if (url.contains("donation_state")) {
                addNew = false;
                try {
                    List<MainListEntity> payerPendingList = this.mainListService.returnPendingPayerList(goldenRichesUsers.getUserName());
                    setLists(response, payerPendingList, model, session, goldenRichesUsers, addNew);
                } catch (Exception exp) {
                    response.setMessage("No Previous Donations Found. Create a New Donation");
                    response.setStatusCode(500);
                    model.addAttribute("response", response);
                }

                returnUrl = "donation_state";
            }
//            else  if (url.contains("maturity")) {
//                addNew=false;
//
//                try {
//                    List<MainListEntity> keeperDetails = mainListService.returnKeeperList(goldenRichesUsers.getUserName());
//                    setLists(response, keeperDetails, model, session,goldenRichesUsers,addNew);
//                }catch (Exception exp){
//                    response.setMessage("No Keeper Donations Found");
//                    response.setStatusCode(500);
//                    model.addAttribute("response", response);
//                }
//                returnUrl= "maturity";
//
//            }


            List<MainListEntity> returnedSponsor = null;
            try {
                returnedSponsor = mainListService.returnMainList(goldenRichesUsers.getUserName());
                if (returnedSponsor.size() != 0) {
                    model.addAttribute("sponsorResponse", 1);
                } else {
                    model.addAttribute("sponsorResponse", null);
                }

            } catch (Exception ff) {
                response.setMessage("No user found with username: " + goldenRichesUsers.getUserName());
                response.setStatusCode(Enums.StatusCodeEnum.NOTFOUND.getStatusCode());
                if (null == automatic) {
                    return response(model, session, response);
                }
            }

            boolean getMainList = true;
            double totalAmountToPay;
            amountToSponsors = 0;
            MainListLoop:
            for (MainListEntity retunedList : returnedSponsor) {
                amountToSponsors = retunedList.getDonatedAmount() + amountToSponsors;
                if (retunedList.getStatus() == 1 || retunedList.getStatus() == 0) {
                    getMainList = false;
                }

                if (retunedList.getDonationType() == 2) {
                    model.addAttribute("sponsorResponse", 1);
                }

                try {
                    if (addNew) {
                        prepareMainListResponse(response, retunedList, addNew);
                    }
                } catch (Exception expt) {
                    break MainListLoop;
                }

            }
            MainListEntity mainListEntity1 = mainListService.findDonationByMainListReference(returnedSponsor.get(0).getDonationReference());
            totalAmountToPay = mainListEntity1.getDonatedAmount() - amountToSponsors;

            if (totalAmountToPay != 0 && automatic.equals("autoRun")) {
            //    getMainListAfterPayingSponsor(response, getMainList, totalAmountToPay, mainListEntity1.getUserName());
            }

            if (returnedSponsor.size() == 0) {
                response.setMessage("No Donations to show, Please make a new Donation");
                response.setStatusCode(Enums.StatusCodeEnum.NOTFOUND.getStatusCode());
                return response(model, session, response);
            } else {
                response.setMessage("MainList Returned Successfully");
                response.setStatusCode(Enums.StatusCodeEnum.OK.getStatusCode());
                return response(model, session, response);
            }

        } catch (Exception exp) {
            mainListLogger.error(exp.getMessage() + exp.getStackTrace());
        }
        response.setStatusCode(Enums.StatusCodeEnum.OK.getStatusCode());
        return response(model, session, response);
    }

    private void setLists(GetMainListResponse response, List<MainListEntity> payerPendingList, Model model, HttpSession session, GoldenRichesUsers goldenRichesUsers, boolean addNew1) throws GoldenRichesUsersNotFoundException {
        model.addAttribute("profile", goldenRichesUsers);

        Collections.reverse(payerPendingList);
        for (MainListEntity mainListEntity : payerPendingList) {

            prepareMainListResponse(response, mainListEntity, addNew1);

        }
        model.addAttribute("response", response);
        session.setAttribute("mainList", response);
        response.setMessage("sucessfull");
        response.setStatusCode(200);
    }

    private void prepareMainListResponse(GetMainListResponse response, MainListEntity retunedList, boolean addNew) throws GoldenRichesUsersNotFoundException {
        if ((addNew && retunedList.getStatus() == 0) || !addNew) {
            MainList mainLists = new MainList();
            GoldenRichesUsers checkUser = goldenRichesUsersService.getUserByBankDetails(retunedList.getBankAccountNumber().trim());
            mainLists.setUsername(checkUser.getUserName());
            mainLists.setEmailAddress(checkUser.getEmailAddress());
            mainLists.setMobileNumber(checkUser.getTelephoneNumber());
            mainLists.setBranchNumber(checkUser.getBranchNumber());
            mainLists.setBankName(checkUser.getBankName());
            mainLists.setAccountHolderName(checkUser.getAccountHoldername());
            mainLists.setAccountNumber(checkUser.getAccountNumber());
            mainLists.setAmount(retunedList.getAdjustedAmount());
            mainLists.setMainListReference(retunedList.getMainListReference());
            mainLists.setAccountType(checkUser.getAccountType());
            mainLists.setDepositReference(retunedList.getDepositReference());
            mainLists.setDonationType(retunedList.getDonationType());
            mainLists.setStatus(retunedList.getStatus());
            mainLists.setPayerUsername(retunedList.getPayerUsername());
            mainLists.setAmountToReceive(retunedList.getAmountToReceive());
            LocalDateTime endDate = LocalDateTime.now();
            long numberOfDays = Duration.between(retunedList.getUpdatedDate().toLocalDateTime(), endDate).toDays();
            mainLists.setDaysRemaining(30 - (int) numberOfDays - 2);

            double percentage = (double) numberOfDays / 30 * retunedList.getAmountToReceive();
            if (numberOfDays <= 30) {
                mainLists.setMaturityAmount(Math.round((percentage) * 100.0) / 100.0);
            } else {
                mainLists.setMaturityAmount(retunedList.getAmountToReceive());
            }


            response.getReturnData().add(mainLists);
        }

    }

    private boolean getMainListAfterPayingSponsor(GetMainListResponse response, boolean getMainList, double amountToPay, String username) throws MainListNotFoundException, GoldenRichesUsersNotFoundException {
        Date utilDate = new Date();
        new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Timestamp sqlDate = new Timestamp(utilDate.getTime());
        double checker = amountToPay;

        List<MainListEntity> mainList = mainListService.getMainList(username);
        amountToPay = createDonationProcess(response, amountToPay, username, sqlDate, true, mainList);

        if (amountToPay > 0) {
            //keeper add keeper if no mature donation has been selected

            amountToPay = createDonationProcess(response, amountToPay, username, sqlDate, false, mainList);
        }

        return true;

    }

    private double createDonationProcess(GetMainListResponse response, double amountToPay, String username, Timestamp sqlDate, boolean keeper, List<MainListEntity> mainList) throws GoldenRichesUsersNotFoundException {
        int count;
        while (amountToPay > 0) {

            for (MainListEntity mainListEntity : mainList) {

                if (checkDateLimit(mainListEntity.getUpdatedDate()) && keeper) {
                    mainListEntity.setKeeper(0);
                    amountToPay = createDonation(response, amountToPay, username, sqlDate, mainListEntity);
                }
                if (!keeper) {
                    boolean continueAsigningKeeper = true;
                    double expenses = 0;
                    List<MainListEntity> mainlistss = donationService.findKeeperDonorsByDonationReference(mainListEntity.getMainListReference());
                    if (mainlistss.size() != 0) {
                        for (MainListEntity mainListEntity1 : mainlistss) {
                            expenses = expenses + mainListEntity1.getDonatedAmount();
                        }
                        if (expenses / mainListEntity.getAmountToReceive() * 100 > 0.75) {
                            continueAsigningKeeper = false;
                        }
                    }

                    if (continueAsigningKeeper) {
                        mainListEntity.setKeeper(1);
                        amountToPay = createDonation(response, amountToPay, username, sqlDate, mainListEntity);
                    }
                }
            }

            break;
        }
        return amountToPay;
    }

    private double createDonation(GetMainListResponse response, double amountToPay, String username, Timestamp sqlDate, MainListEntity mainListEntity) throws GoldenRichesUsersNotFoundException {
        // double amountToBeDistributed = mainListEntity.getDonatedAmount() - amountToPay;
//                        checker=checker-mainListEntity.getAdjustedAmount();
//                        if (amountToBeDistributed == 0 || amountToBeDistributed > 100) {
        CreateDonationRequest createDonationRequest = new CreateDonationRequest();
        createDonationRequest.setMainListReference(mainListEntity.getMainListReference());
        createDonationRequest.setAmount(amountToPay);
        createDonationRequest.setPayerUsername(username);
        createDonationRequest.setBankAccountNumber(mainListEntity.getBankAccountNumber());
        createDonationRequest.setKeeper(mainListEntity.getKeeper());

        try {
            MainListEntity mainListEntity11 = createDonationWebserviceEndpoint.createDonationFromExisting(createDonationRequest, new MainListEntity(), null, sqlDate);
            if (null != mainListEntity11) {
                amountToPay = amountToPay - mainListEntity.getAdjustedAmount();
                if (addNew) {
                    prepareMainListResponse(response, mainListEntity11, addNew);
                }
            }
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }


//                        }
        return amountToPay;
    }


    private String response(Model model, HttpSession session, GetMainListResponse response) {
        try {
            model.addAttribute("response", response);
            session.setAttribute("mainList", response);
            model.addAttribute("profile", (GoldenRichesUsers) session.getAttribute("profile"));
            if (null == returnUrl) {
                return "current_donations";
            } else {
                return returnUrl;
            }
        } catch (Exception ecp) {
            //
        }
        return null;
    }


    boolean checkDateLimit(Date date) {
        // convert Date into Java 8 LocalDate
        LocalDate localDate = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        LocalDate today = LocalDate.now();
        // count number of days between the given date and today
        long days = ChronoUnit.DAYS.between(localDate, today);
        return days > 30;
    }

}
