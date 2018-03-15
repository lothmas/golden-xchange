//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.golden_xchange.controller.createbankaccount;

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

@Controller
public class CreateBankAccountWebserviceEndpoint {
    private static final String NAMESPACE_URI = "createBankAccount.webservice.golden_xchange.com";
    @Autowired
    BankAccountService bankAccountService;
    @Autowired
    GoldenRichesUsersService goldenRichesUsersService;
    BankAccountsEntity createBankAccount = new BankAccountsEntity();

    public CreateBankAccountWebserviceEndpoint() {
    }

    @PayloadRoot(
        namespace = "createBankAccount.webservice.golden_xchange.com",
        localPart = "CreateBankAccountRequest"
    )
    @ResponsePayload
    public CreateBankAccountResponse handleCreateGoldenRichesRequest(@RequestPayload CreateBankAccountRequest request) throws Exception {
        CreateBankAccountResponse response = new CreateBankAccountResponse();

        try {
            if(!this.inputValidation(request, response)) {
                return response;
            }

            if(null == request.getUsername() || request.getUsername().isEmpty()) {
                response.setMessage("Username Can't be Left Empty");
                response.setStatusCode(StatusCodeEnum.EMPTYVALUE.getStatusCode());
                return response;
            }

            GoldenRichesUsers goldenUsers = this.goldenRichesUsersService.getUserByBankDetails(request.getAccountNumber());
            if(null != goldenUsers) {
                response.setMessage("AccountNumber Already Registered");
                response.setStatusCode(StatusCodeEnum.FORBIDDEN.getStatusCode());
                return response;
            }

            this.goldenRichesUsersService.findUserByMemberId(request.getUsername());
            this.createBankAccount.setUserName(request.username);
            this.createBankAccount.setEnabled(1);
        } catch (GoldenRichesUsersNotFoundException var4) {
            response.setMessage(var4.getMessage());
            response.setStatusCode(StatusCodeEnum.NOTFOUND.getStatusCode());
            return response;
        }

        this.bankAccountService.saveUser(this.createBankAccount);
        response.setMessage("BankAccount no." + request.getAccountNumber() + " Was Successfully Added");
        response.setStatusCode(StatusCodeEnum.OK.getStatusCode());
        return response;
    }

    public boolean inputValidation(@RequestPayload CreateBankAccountRequest request, CreateBankAccountResponse response) throws Exception {
        if(null != request.getAccountHolderName() && !request.getAccountHolderName().isEmpty()) {
            this.createBankAccount.setAccountHolderName(request.getAccountHolderName());
            if(null != request.getAccountNumber() && !request.getAccountNumber().isEmpty()) {
                this.createBankAccount.setAccountNumber(request.getAccountNumber());

                try {
                    new BankAccountsEntity();
                    BankAccountsEntity bankAccount = this.bankAccountService.findBankAccByAccNumber(request.getAccountNumber());
                    if(bankAccount != null) {
                        response.setStatusCode(StatusCodeEnum.FORBIDDEN.getStatusCode());
                        response.setMessage("Bank Account already Exists ");
                        return false;
                    }
                } catch (BankAccountsNotFoundException var4) {
                    ;
                }

                if(null != request.getBankName() && !request.getBankName().isEmpty()) {
                    this.createBankAccount.setBankName(request.getBankName());
                    if(null != request.getBranchNumber() && !request.getBranchNumber().isEmpty()) {
                        this.createBankAccount.setBranchNumber(request.getBranchNumber());
                        return true;
                    } else {
                        response.setStatusCode(StatusCodeEnum.EMPTYVALUE.getStatusCode());
                        response.setMessage("branchNumber cant be empty!!");
                        return false;
                    }
                } else {
                    response.setStatusCode(StatusCodeEnum.EMPTYVALUE.getStatusCode());
                    response.setMessage("bankName cant be empty!!");
                    return false;
                }
            } else {
                response.setStatusCode(StatusCodeEnum.EMPTYVALUE.getStatusCode());
                response.setMessage("AccountNumber cant be empty!!");
                return false;
            }
        } else {
            response.setStatusCode(StatusCodeEnum.EMPTYVALUE.getStatusCode());
            response.setMessage("accountholdername cant be empty!!");
            return false;
        }
    }
}

