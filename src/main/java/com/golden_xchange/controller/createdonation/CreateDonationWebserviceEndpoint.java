//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.golden_xchange.controller.createdonation;

import com.golden_xchange.domain.bankaccounts.service.BankAccountService;
import com.golden_xchange.domain.mainlist.exception.MainListNotFoundException;
import com.golden_xchange.domain.mainlist.model.MainListEntity;
import com.golden_xchange.domain.mainlist.service.MainListService;
import com.golden_xchange.domain.users.exception.GoldenRichesUsersNotFoundException;
import com.golden_xchange.domain.users.model.GoldenRichesUsers;
import com.golden_xchange.domain.users.service.GoldenRichesUsersService;
import com.golden_xchange.domain.utilities.Enums.StatusCodeEnum;
import com.golden_xchange.domain.utilities.SendSms;
import org.apache.commons.lang.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;

@Controller
public class CreateDonationWebserviceEndpoint {
    private static final String NAMESPACE_URI = "createDonation.webservice.golden_xchange.com";
    @Autowired
    MainListService donationService;
    @Autowired
    GoldenRichesUsersService goldenRichesUsersService;
    @Autowired
    BankAccountService bankAccountService;
    MainListEntity mainListReference;

    public CreateDonationWebserviceEndpoint() {
    }

    @PayloadRoot(
        namespace = "createDonation.webservice.golden_xchange.com",
        localPart = "CreateDonationRequest"
    )
    @ResponsePayload
    public CreateDonationResponse handleCreateGoldenRichesRequest(@RequestPayload CreateDonationRequest request) throws Exception {
        MainListEntity createDonation = new MainListEntity();
        CreateDonationResponse response = new CreateDonationResponse();

        GoldenRichesUsers gold;
        try {
            label94: {
                if(null != request.getMainListReference() && !request.getMainListReference().isEmpty()) {
                    this.mainListReference = this.donationService.findDonationByMainListReference(request.getMainListReference());
                    if(this.mainListReference.getPayerUsername().equals(request.getPayerUsername())) {
                        response.setMessage("You can't Donate to Yourself");
                        response.setStatusCode(StatusCodeEnum.FORBIDDEN.getStatusCode());
                        return response;
                    }

                    if(this.mainListReference.getAdjustedAmount() < request.getAmount()) {
                        response.setMessage("Provided amount is greater than amount required to Donate");
                        response.setStatusCode(StatusCodeEnum.FORBIDDEN.getStatusCode());
                        return response;
                    }

                    if(request.getAmount() < 300.0D) {
                        response.setMessage("Minimum Donation Amount is R300");
                        response.setStatusCode(StatusCodeEnum.FORBIDDEN.getStatusCode());
                        return response;
                    }

                    Double extra = Double.valueOf(this.mainListReference.getAdjustedAmount() - request.getAmount());
                    if(extra.doubleValue() < 300.0D && extra.doubleValue() != 0.0D) {
                        response.setMessage("You can't leave less than R300 on a Donation after Payment.");
                        response.setStatusCode(StatusCodeEnum.FORBIDDEN.getStatusCode());
                        return response;
                    }

                    if(null != request.getMainListReference() && !request.getMainListReference().isEmpty()) {
                        try {
                            gold = this.goldenRichesUsersService.findUserByMemberId(request.getPayerUsername());
                            createDonation.setPayerUsername(request.getPayerUsername());
                        } catch (GoldenRichesUsersNotFoundException var12) {
                            response.setStatusCode(StatusCodeEnum.NOTFOUND.getStatusCode());
                            response.setMessage(var12.getMessage());
                            return response;
                        }

                        if(0.0D != request.getAmount()) {
                            createDonation.setDonatedAmount(request.getAmount());
                            createDonation.setAdjustedAmount(request.getAmount() + 0.3D * request.getAmount());
                            createDonation.setAmountToReceive(createDonation.getAdjustedAmount());
                            if(null != request.getBankAccountNumber() && !request.getBankAccountNumber().isEmpty()) {
                                if(!this.bankAccountService.findBankAccByAccNumberAndUserName(request.getBankAccountNumber(), request.getPayerUsername())) {
                                    response.setStatusCode(StatusCodeEnum.NOTFOUND.getStatusCode());
                                    response.setMessage("Provided AccountNumber: " + request.getBankAccountNumber() + " doesn't match user: " + request.getPayerUsername());
                                    return response;
                                }

                                GoldenRichesUsers goldenRichesUsers = this.goldenRichesUsersService.getUserByBankDetails(request.getBankAccountNumber());
                                createDonation.setUserName(this.mainListReference.getUserName());
                                createDonation.setBankAccountNumber(request.getBankAccountNumber());
                                createDonation.setMainListReference(RandomStringUtils.randomAlphanumeric(10).toUpperCase());
                                createDonation.setDonationReference(request.getMainListReference());
                                createDonation.setDepositReference(RandomStringUtils.randomAlphanumeric(10).toUpperCase());
                                break label94;
                            }

                            response.setStatusCode(StatusCodeEnum.FORBIDDEN.getStatusCode());
                            response.setMessage("donationReference can't be left empty ");
                            return response;
                        }

                        response.setStatusCode(StatusCodeEnum.EMPTYVALUE.getStatusCode());
                        response.setMessage("amount cant be empty!!");
                        return response;
                    }

                    response.setMessage("MainListReference` Can't be Left Empty");
                    response.setStatusCode(StatusCodeEnum.EMPTYVALUE.getStatusCode());
                    return response;
                }

                response.setMessage("MainListReference` Can't be Left Empty");
                response.setStatusCode(StatusCodeEnum.EMPTYVALUE.getStatusCode());
                return response;
            }
        } catch (MainListNotFoundException | GoldenRichesUsersNotFoundException var13) {
            response.setMessage(var13.getMessage());
            response.setStatusCode(StatusCodeEnum.NOTFOUND.getStatusCode());
            return response;
        }

        createDonation.setEnabled(0);
        createDonation.setStatus(0);
        Date utilDate = new Date();
        new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Timestamp sqlDate = new Timestamp(utilDate.getTime());
        createDonation.setUpdatedDate(sqlDate);
        createDonation.setDate(sqlDate);
        String user = this.mainListReference.getPayerUsername();
        createDonation.setUserName(user);
        this.donationService.saveUser(createDonation);
        response.setMessage("Donation  Was Successfully Created and Pending Payment");
        response.setStatusCode(StatusCodeEnum.OK.getStatusCode());
        response.setDepositReference(createDonation.getDepositReference());
        if(this.mainListReference.getAdjustedAmount() > 0.0D) {
            double reduceDonatedAmount = this.mainListReference.getAdjustedAmount() - request.getAmount();
            this.mainListReference.setAdjustedAmount(reduceDonatedAmount);
            this.donationService.saveUser(this.mainListReference);
        }

        try {
            SendSms send = new SendSms();
            send.send("sendSms.sh", gold.getTelephoneNumber(), "Golden Riches Donation Created [" + createDonation.getUpdatedDate() + "]." + " DepositReference: " + createDonation.getDepositReference() + " AmountToPay: R" + request.getAmount() + ". Confirm Before Payment [expires in 5hrs]");
        } catch (Exception var11) {
            ;
        }

        return response;
    }
}

