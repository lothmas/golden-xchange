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


    @RequestMapping({"/current_donations", "/donation_state"})
    public String handleCreateGoldenRichesRequest(HttpServletRequest requests, Model model, HttpSession session)
            throws Exception {
        GetMainListResponse response = new GetMainListResponse();
        model.addAttribute("notifications",session.getAttribute("notifications")) ;

        try {
            String url = requests.getRequestURI();

            GoldenRichesUsers goldenRichesUsers = (GoldenRichesUsers) session.getAttribute("profile");

            if (url.contains("donation_state")) {
                model.addAttribute("profile", goldenRichesUsers);
                try {
                    List<MainListEntity> payerPendingList = this.mainListService.returnPendingPayerList(goldenRichesUsers.getUserName());
                    Collections.reverse(payerPendingList);
                    for (MainListEntity mainListEntity : payerPendingList) {
                        prepareMainListResponse(response, mainListEntity);
                    }

                    response.setMessage("sucessfull");
                    response.setStatusCode(200);
                } catch (Exception exp) {
                    response.setMessage("No Previous Donations Found. Create a New Donation");
                    response.setStatusCode(500);
                }
                model.addAttribute("response", response);
                session.setAttribute("mainList", response);
                return "donation_state";
            }


            List<MainListEntity> returnedSponsor = null;
            try {
                returnedSponsor = mainListService.returnMainList(goldenRichesUsers.getUserName());
                if (returnedSponsor.size() != 0) {
                    model.addAttribute("sponsorResponse", 1);
                } else {
                    model.addAttribute("sponsorResponse", null);
                }

            } catch (MainListNotFoundException ff) {
                response.setMessage("No user found with username: " + goldenRichesUsers.getUserName());
                response.setStatusCode(Enums.StatusCodeEnum.NOTFOUND.getStatusCode());
                return response(model, session, response);
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
                    prepareMainListResponse(response, retunedList);
                } catch (Exception expt) {
                    break MainListLoop;
                }

            }
            MainListEntity mainListEntity1 = mainListService.findDonationByMainListReference(returnedSponsor.get(0).getDonationReference());
            totalAmountToPay = mainListEntity1.getDonatedAmount() - amountToSponsors;

            if (totalAmountToPay != 0) {
                getMainListAfterPayingSponsor(response, getMainList, totalAmountToPay, mainListEntity1.getUserName());
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

    private void prepareMainListResponse(GetMainListResponse response, MainListEntity retunedList) throws GoldenRichesUsersNotFoundException {
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
        double percentage=(double)numberOfDays/30*retunedList.getAmountToReceive();
        if(numberOfDays<=30){
            mainLists.setMaturityAmount(Math.round((percentage) * 100.0) / 100.0);
        }
        else{
            mainLists.setMaturityAmount(retunedList.getAmountToReceive());
        }

        response.getReturnData().add(mainLists);

    }

    private boolean getMainListAfterPayingSponsor(GetMainListResponse response, boolean getMainList, double amountToPay, String username) throws MainListNotFoundException, GoldenRichesUsersNotFoundException {
        Date utilDate = new Date();
        new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Timestamp sqlDate = new Timestamp(utilDate.getTime());
        double checker = amountToPay;

        if (getMainList) {
            List<MainListEntity> mainList = mainListService.getMainList(username);
            amountToPay = createDonationProcess(response, amountToPay, username, sqlDate, true, mainList);

            if (amountToPay > 0) {
                //keeper add keeper if not
                amountToPay = createDonationProcess(response, amountToPay, username, sqlDate, false, mainList);
            }

        }
        {
            return false;
        }
    }

    private double createDonationProcess(GetMainListResponse response, double amountToPay, String username, Timestamp sqlDate, boolean keeper, List<MainListEntity> mainList) throws GoldenRichesUsersNotFoundException {
        while (amountToPay > 0) {
            for (MainListEntity mainListEntity : mainList) {

                if (checkDateLimit(mainListEntity.getUpdatedDate()) && keeper) {
                    amountToPay = createDonation(response, amountToPay, username, sqlDate, mainListEntity);
                }
                if (!keeper) {
                    amountToPay = createDonation(response, amountToPay, username, sqlDate, mainListEntity);
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

        try {
            MainListEntity mainListEntity11 = createDonationWebserviceEndpoint.createDonationFromExisting(createDonationRequest, new MainListEntity(), null, sqlDate);
            if (null != mainListEntity11) {
                amountToPay = amountToPay - mainListEntity.getAdjustedAmount();
                prepareMainListResponse(response, mainListEntity11);
            }
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }


//                        }
        return amountToPay;
    }


    private String response(Model model, HttpSession session, GetMainListResponse response) {
        model.addAttribute("response", response);
        session.setAttribute("mainList", response);
        model.addAttribute("profile", (GoldenRichesUsers) session.getAttribute("profile"));
        return "current_donations";
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
