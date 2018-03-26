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


    @RequestMapping({"/donation_status"})
    public String handleCreateGoldenRichesRequest(HttpServletRequest requests, Model model, HttpSession session)
            throws Exception {
        GetMainListResponse response = new GetMainListResponse();
        try {

            GoldenRichesUsers goldenRichesUsers= (GoldenRichesUsers) session.getAttribute("profile");




           List<MainListEntity> returnedSponsor = null;
           try {
               returnedSponsor = mainListService.returnMainList(goldenRichesUsers.getUserName());
              if(returnedSponsor.size()!=0)
              { model.addAttribute("sponsorResponse",1);}
              else{
                  model.addAttribute("sponsorResponse",null);
              }

           } catch (MainListNotFoundException ff) {
               response.setMessage("No user found with username: " + goldenRichesUsers.getUserName());
               response.setStatusCode(Enums.StatusCodeEnum.NOTFOUND.getStatusCode());
               return response(model, session, response);
           }

           boolean getMainList=true;
           double amountToSponsors = 0;
           double totalAmountToPay;
           MainListLoop:
           for (MainListEntity retunedList : returnedSponsor) {
               amountToSponsors=+retunedList.getAmountToReceive();
                    if(retunedList.getStatus()==1||retunedList.getStatus()==2){
                        getMainList=false;
                    }
               try {
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
               catch (Exception expt){
                   break MainListLoop;
               }

           }
            totalAmountToPay =mainListService.findDonationByMainListReference(returnedSponsor.get(0).getDonationReference()).getDonatedAmount()-amountToSponsors;

            getMainListAfterPayingSponsor(response, getMainList,totalAmountToPay);

            if (returnedSponsor.size() == 0) {
               response.setMessage("No Donations to show, Please make a new Donation");
               response.setStatusCode(Enums.StatusCodeEnum.NOTFOUND.getStatusCode());
               return response(model, session, response);
           } else {
               response.setMessage("MainList Returned Successfully");
               response.setStatusCode(Enums.StatusCodeEnum.OK.getStatusCode());
               return response(model, session, response);
           }

       }
       catch (Exception exp){
           mainListLogger.error(exp.getMessage() + exp.getStackTrace() );
       }
        response.setStatusCode(Enums.StatusCodeEnum.OK.getStatusCode());
        return response(model, session, response);
    }

    private void getMainListAfterPayingSponsor(GetMainListResponse response, boolean getMainList,double amountToPay) throws MainListNotFoundException, GoldenRichesUsersNotFoundException {
        if(getMainList) {
            List<MainListEntity> mainList = mainListService.getMainList();
            for (MainListEntity mainListEntity : mainList) {
                if(mainListEntity.getAmountToReceive()>=amountToPay && mainListEntity.getAmountToReceive()-amountToPay>=500) {
                    MainList mainLists = new MainList();

                    GoldenRichesUsers checkUser = goldenRichesUsersService.getUserByBankDetails(mainListEntity.getBankAccountNumber().trim());
                    //check amounts
                    // if(mainListEntity)
//               mainListService.findDonationByMainListReference()
                    mainLists.setUsername(checkUser.getUserName());
                    mainLists.setEmailAddress(checkUser.getEmailAddress());
                    mainLists.setMobileNumber(checkUser.getTelephoneNumber());
                    mainLists.setBranchNumber(checkUser.getBranchNumber());
                    mainLists.setBankName(checkUser.getBankName());
                    mainLists.setAccountHolderName(checkUser.getAccountHoldername());
                    mainLists.setAccountNumber(checkUser.getAccountNumber());
                    mainLists.setAmount(mainListEntity.getAdjustedAmount());
                    mainLists.setMainListReference(mainListEntity.getMainListReference());
                    mainLists.setAccountType(checkUser.getAccountType());
                    mainLists.setDepositReference(mainListEntity.getDepositReference());
                    mainLists.setDonationType(mainListEntity.getDonationType());
                    mainLists.setStatus(mainListEntity.getStatus());
                    response.getReturnData().add(mainLists);
                }
            }
        }
    }

    private String response(Model model, HttpSession session, GetMainListResponse response) {
        model.addAttribute("response",response);
        session.setAttribute("mainList",response);
        model.addAttribute("profile",(GoldenRichesUsers)session.getAttribute("profile"));
        return "donation_status";
    }


}
