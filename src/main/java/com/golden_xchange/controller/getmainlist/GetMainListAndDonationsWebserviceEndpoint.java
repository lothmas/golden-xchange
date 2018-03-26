/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.golden_xchange.controller.getmainlist;


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
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.List;

/**
 * @author louis
 */
@ControllerAdvice
@Controller
public class GetMainListAndDonationsWebserviceEndpoint {


    @Autowired
    MainListService mainListService;

    @Autowired
    BankAccountService bankAccountService;

    @Autowired
    GoldenRichesUsersService goldenRichesUsersService;


    BankAccountsEntity createBankAccount = new BankAccountsEntity();
    Logger mainListLogger = Logger.getLogger(this.getClass().getName());
    static double amountToSponsors = 0;




    @RequestMapping({"/donation_status"})
    public String handleCreateGoldenRichesRequest(HttpServletRequest requests, Model model, HttpSession session)
            throws Exception {
        GetMainListResponse response = new GetMainListResponse();
        try {

            GoldenRichesUsers goldenRichesUsers = (GoldenRichesUsers) session.getAttribute("profile");


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
                amountToSponsors = retunedList.getAmountToReceive() + amountToSponsors;
                if (retunedList.getStatus() == 1 || retunedList.getStatus() == 0) {
                    getMainList = false;
                }
                try {
                    prepareMainListResponse(response, retunedList);
                } catch (Exception expt) {
                    break MainListLoop;
                }

            }
            MainListEntity mainListEntity1 = mainListService.findDonationByMainListReference(returnedSponsor.get(0).getDonationReference());
            totalAmountToPay = mainListEntity1.getDonatedAmount() - amountToSponsors;

            getMainListAfterPayingSponsor(response, getMainList, totalAmountToPay, mainListEntity1.getUserName());

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
        response.getReturnData().add(mainLists);
    }

    private void getMainListAfterPayingSponsor(GetMainListResponse response, boolean getMainList, double amountToPay, String username) throws MainListNotFoundException, GoldenRichesUsersNotFoundException {
        Date utilDate = new Date();
        new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Timestamp sqlDate = new Timestamp(utilDate.getTime());

        if (getMainList) {
            List<MainListEntity> mainList = mainListService.getMainList(username);
            for (MainListEntity mainListEntity : mainList) {

                if (checkDateLimit(mainListEntity.getUpdatedDate())) {
                    double amountToBeDistributed = mainListEntity.getAmountToReceive() - amountToPay;

                    if (amountToBeDistributed > 100) {
                        prepareMainListResponse(response, mainListEntity);
                        MainListEntity newMain=new MainListEntity();
                        newMain.setStatus(0);
                        newMain.setUpdatedDate(sqlDate);
                        newMain.setAdjustedAmount(request.getAmount() + 0.4D * request.getAmount());
                        newMain.setDonatedAmount(request.getAmount());
                        newMain.setEnabled(1);
                        newMain.setBankAccountNumber(goldenRichesUsers.getAccountNumber());
                        newMain.setAmountToReceive(request.getAmount() + 0.4D * request.getAmount());
                        newMain.setDate(sqlDate);
                        String mainRef= RandomStringUtils.randomAlphanumeric(10).toUpperCase();
                        newMain.setMainListReference(mainRef);
                        newMain.setDonationReference(RandomStringUtils.randomAlphanumeric(10).toUpperCase());
                        newMain.setDepositReference(RandomStringUtils.randomAlphanumeric(10).toUpperCase());
                        newMain.setUserName(request.getPayerUsername());
                        newMain.setPayerUsername(request.getPayerUsername());
                        newMain.setDonationType(0);
                        donationService.saveUser(newMain);
                    }
                }
            }


        }
    }


    private String response(Model model, HttpSession session, GetMainListResponse response) {
        model.addAttribute("response", response);
        session.setAttribute("mainList", response);
        model.addAttribute("profile", (GoldenRichesUsers) session.getAttribute("profile"));
        return "donation_status";
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
