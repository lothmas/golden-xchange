//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.golden_xchange.controller.getuserprofile;

import com.golden_xchange.domain.bankaccounts.exception.BankAccountsNotFoundException;
import com.golden_xchange.domain.bankaccounts.model.BankAccountsEntity;
import com.golden_xchange.domain.bankaccounts.service.BankAccountService;
import com.golden_xchange.domain.users.exception.GoldenRichesUsersNotFoundException;
import com.golden_xchange.domain.users.model.GoldenRichesUsers;
import com.golden_xchange.domain.users.service.GoldenRichesUsersService;
import com.golden_xchange.domain.utilities.Enums.StatusCodeEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;

import java.util.Iterator;
import java.util.List;

@Controller
public class GetUserProfileWebserviceEndpoint {
    @Autowired
    GoldenRichesUsersService goldenRichesUsersService;
    @Autowired
    BankAccountService bankAccountService;
    private static final String NAMESPACE_URI = "getUserProfile.webservice.golden_xchange.com";

    public GetUserProfileWebserviceEndpoint() {
    }

    @PayloadRoot(
        namespace = "getUserProfile.webservice.golden_xchange.com",
        localPart = "GetUserProfileDetailsRequest"
    )
    @ResponsePayload
    public GetUserProfileDetailsResponse handleGetBankNameListRequest(@RequestPayload GetUserProfileDetailsRequest request) throws Exception {
        GetUserProfileDetailsResponse response = new GetUserProfileDetailsResponse();
        if(null != request.getUsername() && !request.getUsername().isEmpty()) {
            try {
                GoldenRichesUsers goldenRichesUsers = this.goldenRichesUsersService.findUserByMemberId(request.getUsername());
                PersonalDetails responsePersonalDetails = new PersonalDetails();
                responsePersonalDetails.setSurname(goldenRichesUsers.getSurname());
                responsePersonalDetails.setUsername(goldenRichesUsers.getUserName());
                responsePersonalDetails.setFirstName(goldenRichesUsers.getFirstName());
                responsePersonalDetails.setMobileNumber(goldenRichesUsers.getTelephoneNumber());
                responsePersonalDetails.setGender("place holder");
                responsePersonalDetails.setEmailAddress(goldenRichesUsers.getEmailAddress());
                response.setReturnData(responsePersonalDetails);
                BankDetails responseBankDetails = new BankDetails();
                responseBankDetails.setAccountEnabled(true);
                responseBankDetails.setAccountHolderName(goldenRichesUsers.getSurname());
                responseBankDetails.setAccountNumber(goldenRichesUsers.getAccountNumber());
                responseBankDetails.setBankName(goldenRichesUsers.getBankName());
                responseBankDetails.setAccountHolderName(goldenRichesUsers.getAccountHoldername());
                responseBankDetails.setBranchNumber(goldenRichesUsers.getBranchNumber());
                response.getReturnData2().add(responseBankDetails);

                try {
                    List<BankAccountsEntity> bankAccount = this.bankAccountService.findBankAccountsEntityByUsername(request.getUsername());
                    Iterator var7 = bankAccount.iterator();

                    while(var7.hasNext()) {
                        BankAccountsEntity returnedBankAccounts = (BankAccountsEntity)var7.next();
                        BankDetails responseBankDetails2 = new BankDetails();
                        responseBankDetails2.setAccountEnabled(returnedBankAccounts.getEnabled()!=0);
                        responseBankDetails2.setAccountHolderName(returnedBankAccounts.getAccountHolderName());
                        responseBankDetails2.setAccountNumber(returnedBankAccounts.getAccountNumber());
                        responseBankDetails2.setBankName(returnedBankAccounts.getBankName());
                        responseBankDetails2.setAccountHolderName(returnedBankAccounts.getAccountHolderName());
                        responseBankDetails2.setBranchNumber(returnedBankAccounts.getBranchNumber());
                        response.getReturnData2().add(responseBankDetails2);
                    }
                } catch (BankAccountsNotFoundException var10) {
                    ;
                }

                response.setMessage(StatusCodeEnum.OK.getStatusMessage());
                response.setStatusCode(StatusCodeEnum.OK.getStatusCode());
                return response;
            } catch (GoldenRichesUsersNotFoundException var11) {
                response.setMessage(var11.getMessage());
                response.setStatusCode(StatusCodeEnum.NOTFOUND.getStatusCode());
                return response;
            }
        } else {
            response.setMessage(StatusCodeEnum.EMPTYVALUE.getStatusMessage());
            response.setStatusCode(StatusCodeEnum.EMPTYVALUE.getStatusCode());
            return response;
        }
    }
}

