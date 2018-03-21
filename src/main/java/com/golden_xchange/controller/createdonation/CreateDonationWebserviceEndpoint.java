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
import com.golden_xchange.domain.utilities.Enums;
import com.golden_xchange.domain.utilities.Enums.StatusCodeEnum;
import com.golden_xchange.domain.utilities.SendSms;
import org.apache.commons.lang.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;

@ControllerAdvice
@Controller
public class CreateDonationWebserviceEndpoint {
    @Autowired
    MainListService donationService;
    @Autowired
    GoldenRichesUsersService goldenRichesUsersService;
    @Autowired
    BankAccountService bankAccountService;
    MainListEntity mainListReference;

    public CreateDonationWebserviceEndpoint() {
    }

    @RequestMapping({"/createDonation"})
    public CreateDonationResponse handleCreateGoldenRichesRequest(HttpServletRequest httpServletRequest, Model model, HttpSession session, CreateDonationRequest request, @RequestParam(value = "action", required = false) String action) throws Exception {
        MainListEntity createDonation = new MainListEntity();
        CreateDonationResponse response = new CreateDonationResponse();
        Date utilDate = new Date();
        new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Timestamp sqlDate = new Timestamp(utilDate.getTime());

        if(action.equals("1")){
            if (commonValidator(request, response)) return response;
            if(null==request.getPayerUsername()) {
                response.setMessage("Please relogin and try again required values have not been supplied");
                response.setStatusCode(StatusCodeEnum.FORBIDDEN.getStatusCode());
                return response;
            }
            GoldenRichesUsers goldenRichesUsers=goldenRichesUsersService.findUserByMemberId(request.getPayerUsername());
            MainListEntity mainListEntity=new MainListEntity();
            mainListEntity.setStatus(2);
            mainListEntity.setUpdatedDate(sqlDate);
            mainListEntity.setAdjustedAmount(request.getAmount() + 0.4D * request.getAmount());
            mainListEntity.setDonatedAmount(request.getAmount());
            mainListEntity.setEnabled(1);
            mainListEntity.setBankAccountNumber(goldenRichesUsers.getAccountNumber());
            mainListEntity.setAmountToReceive(request.getAmount() + 0.4D * request.getAmount());
            mainListEntity.setDate(sqlDate);
            String mainRef=RandomStringUtils.randomAlphanumeric(10).toUpperCase();
            mainListEntity.setMainListReference(mainRef);
            mainListEntity.setDonationReference(RandomStringUtils.randomAlphanumeric(10).toUpperCase());
            mainListEntity.setDepositReference(RandomStringUtils.randomAlphanumeric(10).toUpperCase());
            mainListEntity.setUserName(request.getPayerUsername());
            mainListEntity.setPayerUsername(request.getPayerUsername());
            donationService.saveUser(mainListEntity);
            createForSponsor(goldenRichesUsers,request,mainRef);
        }
        else{

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

                    if (commonValidator(request, response)) return response;

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
                            createDonation.setAdjustedAmount(request.getAmount() + 0.4D * request.getAmount());
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
//            SendSms send = new SendSms();
//            send.send("sendSms.sh", gold.getTelephoneNumber(), "Golden Riches Donation Created [" + createDonation.getUpdatedDate() + "]." + " DepositReference: " + createDonation.getDepositReference() + " AmountToPay: R" + request.getAmount() + ". Confirm Before Payment [expires in 5hrs]");
        } catch (Exception var11) {
            ;
        }

        return response;
    }
        return response;
    }

    private boolean commonValidator(CreateDonationRequest request, CreateDonationResponse response) {
        if(request.getAmount() < 500.0D) {
            response.setMessage("Minimum Donation Amount is R500");
            response.setStatusCode(StatusCodeEnum.FORBIDDEN.getStatusCode());
            return true;
        }
        if(request.getAmount() > 100000.0D) {
            response.setMessage("Maximum Donation Amount is R100000");
            response.setStatusCode(StatusCodeEnum.FORBIDDEN.getStatusCode());
            return true;
        }


        return false;
    }


    public void createForSponsor(GoldenRichesUsers goldenRichesUsers,CreateDonationRequest request,String ref){
        Date utilDate = new Date();
        new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Timestamp sqlDate = new Timestamp(utilDate.getTime());
        try {
            GoldenRichesUsers sponsorProfile=goldenRichesUsersService.findUserByMemberId(goldenRichesUsers.getReferenceUser());
            MainListEntity mainListEntity=new MainListEntity();
            mainListEntity.setStatus(0);
            mainListEntity.setUpdatedDate(sqlDate);
            mainListEntity.setAdjustedAmount(0.2D * request.getAmount());
            mainListEntity.setDonatedAmount(0.2D * request.getAmount());
            mainListEntity.setEnabled(0);
            mainListEntity.setBankAccountNumber(sponsorProfile.getAccountNumber());
            mainListEntity.setAmountToReceive(0.2D * request.getAmount());
            mainListEntity.setDate(sqlDate);
            mainListEntity.setMainListReference(RandomStringUtils.randomAlphanumeric(10).toUpperCase());
            mainListEntity.setDonationReference(ref);
            mainListEntity.setDepositReference(RandomStringUtils.randomAlphanumeric(10).toUpperCase());
            mainListEntity.setUserName(request.getPayerUsername());
            mainListEntity.setPayerUsername(sponsorProfile.getUserName());
            donationService.saveUser(mainListEntity);
        } catch (GoldenRichesUsersNotFoundException e) {
            e.printStackTrace();
        }


    }
}

