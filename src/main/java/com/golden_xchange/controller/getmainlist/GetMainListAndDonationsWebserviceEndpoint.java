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
import com.golden_xchange.domain.users.model.GoldenRichesUsers;
import com.golden_xchange.domain.users.service.GoldenRichesUsersService;
import com.golden_xchange.domain.utilities.Enums;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;

import java.util.List;

/**
 * @author louis
 */

public class GetMainListAndDonationsWebserviceEndpoint {

    private static final String NAMESPACE_URI = "getMainList.webservice.golden_xchange.com";


    @Autowired
    MainListService mainListService;

    @Autowired
    BankAccountService bankAccountService;

    @Autowired
    GoldenRichesUsersService goldenRichesUsersService;


    BankAccountsEntity createBankAccount = new BankAccountsEntity();
    Logger mainListLogger = Logger.getLogger(this.getClass().getName());



    public GetMainListResponse handleCreateGoldenRichesRequest(@RequestPayload GetMainListRequest request)
            throws Exception {
        GetMainListResponse response = new GetMainListResponse();
        try {


           if (null == request.getUsername() || request.getUsername().isEmpty()) {
               response.setMessage("Username Can't be Left Empty");
               response.setStatusCode(Enums.StatusCodeEnum.EMPTYVALUE.getStatusCode());
               return response;
           }


           List<MainListEntity> returnedMainList = null;
           try {
               returnedMainList = mainListService.returnMainList(request.getUsername());
           } catch (MainListNotFoundException ff) {
               response.setMessage("No user found with username: " + request.getUsername());
               response.setStatusCode(Enums.StatusCodeEnum.NOTFOUND.getStatusCode());
               return response;
           }

           MainListLoop:
           for (MainListEntity retunedList : returnedMainList) {
               MainList mainLists = new MainList();
               try {
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

                   response.getReturnData().add(mainLists);
               }
               catch (Exception expt){
                   break MainListLoop;
               }

           }

           if (returnedMainList.size() == 0) {
               response.setMessage("No List to return");
               response.setStatusCode(Enums.StatusCodeEnum.NOTFOUND.getStatusCode());
               return response;
           } else {
               response.setMessage("MainList Returned Successfully");
               response.setStatusCode(Enums.StatusCodeEnum.OK.getStatusCode());
               return response;
           }

       }
       catch (Exception exp){
           mainListLogger.error(exp.getMessage() + exp.getStackTrace() +request.getUsername() );
       }
       return response;
    }


}
