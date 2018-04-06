//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.golden_xchange.controller.approvedonation;

import com.golden_xchange.controller.getmainlist.GetMainListResponse;
import com.golden_xchange.domain.mainlist.exception.MainListNotFoundException;
import com.golden_xchange.domain.mainlist.model.MainListEntity;
import com.golden_xchange.domain.mainlist.service.MainListService;
import com.golden_xchange.domain.users.exception.GoldenRichesUsersNotFoundException;
import com.golden_xchange.domain.users.model.GoldenRichesUsers;
import com.golden_xchange.domain.users.service.GoldenRichesUsersService;
import com.golden_xchange.domain.utilities.Enums.StatusCodeEnum;
import com.golden_xchange.domain.utilities.SendSms;
import org.apache.log4j.Logger;
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
import java.util.Iterator;
import java.util.List;

@ControllerAdvice
@Controller
public class ApproveDonationWebserviceEndpoint {
    @Autowired
    MainListService mainListService;
    @Autowired
    GoldenRichesUsersService goldenRichesUsersService;

    public ApproveDonationWebserviceEndpoint() {
    }

    @RequestMapping({"/approveDonation"})
    public String handleApproveDonationRequest(ApproveDonationRequest request, HttpServletRequest requests, Model model, HttpSession session,
                                               @RequestParam(value = "approvers", required = false) Integer approvers
    ,@RequestParam(value = "requester", required = false) String requester
            ,@RequestParam(value = "selectedRow", required = false) String Integer) throws Exception {
        model.addAttribute("notifications",session.getAttribute("notifications")) ;

        Logger LOG = Logger.getLogger(this.getClass().getName());
        String url = requests.getRequestURI();
        if (null != approvers && approvers == 2) {
            request.setApprover(2);
        }
        ApproveDonationResponse response = new ApproveDonationResponse();
        new MainListEntity();
        new GoldenRichesUsers();
        GoldenRichesUsers goldenRichesUsers = (GoldenRichesUsers) session.getAttribute("profile");
        request.setUsername(goldenRichesUsers.getUserName());
        try {
            Date utilDate = new Date();
            new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Timestamp sqlDate = new Timestamp(utilDate.getTime());

            MainListEntity mainListEntity = this.mainListService.findMainListsByDepositReference(request.getDepositReference());
//            if(!mainListEntity.getUserName().equals(request.getUsername())) {
//                response.setMessage("FORBIDDEN Not Allowed To Update Donation: Please Contact System Administrator " + request.getDepositReference());
//                response.setStatusCode(StatusCodeEnum.FORBIDDEN.getStatusCode());
//                LOG.error("FORBIDDEN Not Allowed To Approve Donation:" + request.getDepositReference() + "between ["+mainListEntity.getUserName()+ "] and ["+request.getUsername() +"]");
//                return errorResponse(model,response,session);
//            }

            if (request.getApprover() == 1) {
                if (mainListEntity.getPayerUsername().equals(request.getUsername())) {
                    mainListEntity.setStatus(1);
                    mainListEntity.setUpdatedDate(sqlDate);
                    this.mainListService.saveUser(mainListEntity);
                }

                try {
//                    GoldenRichesUsers goldenRichesUsers = this.goldenRichesUsersService.findUserByMemberId(mainListEntity.getUserName());
                    SendSms send = new SendSms();
                    send.send("sendSms.sh", goldenRichesUsers.getTelephoneNumber(), "Golden Riches: Deposit Confirmed [" + mainListEntity.getUpdatedDate() + "]." + " DepositReference: " + mainListEntity.getDepositReference() + ". AmountPayed: " + mainListEntity.getDonatedAmount() + ". Confirm in Your Account and Update The System");
                    LOG.info("MSG SENT: Golden Riches: Deposit Confirmed [" + mainListEntity.getUpdatedDate() + "]." + " DepositReference: " + mainListEntity.getDepositReference() + ". AmountPayed: " + mainListEntity.getDonatedAmount() + ". Confirm in Your Account and Update The System");
                } catch (Exception var12) {
                    //do nothing skip
                }


                response.setMessage("Payer Has Approved Payment For Deposit Reference: " + request.getDepositReference());
                response.setStatusCode(StatusCodeEnum.OK.getStatusCode());
                return "redirect:/"+requester;

            } else if (request.getApprover() != 2) {
                response.setMessage("Please set Approver: [1 = PayerApprover] [2= Receiver Approver]");
                response.setStatusCode(StatusCodeEnum.FORBIDDEN.getStatusCode());
                return errorResponse(model, response, session,url);
            } else {
                mainListEntity.setStatus(2);
                mainListEntity.setUpdatedDate(sqlDate);
                this.mainListService.saveUser(mainListEntity);
                response.setMessage("Donation Receiver has Approved Deposit Reference: " + request.getDepositReference());
                response.setStatusCode(StatusCodeEnum.OK.getStatusCode());
                List<MainListEntity> donations = this.mainListService.findDonorsByDonationReference(mainListEntity.getDonationReference());
                List<MainListEntity> paidDonations = this.mainListService.findPaidDonationsPayerName(mainListEntity.getPayerUsername());

                boolean paidAll = true;
                double amountToPay = 0;
                for (MainListEntity mainListEntity2 : paidDonations) {
                    if (mainListEntity2.getStatus() == 0 || mainListEntity2.getStatus() == 1) {
                        paidAll = false;
                        break;
                    } else {
                        amountToPay = amountToPay + mainListEntity2.getDonatedAmount();
                    }
                }

                if (paidAll) {
                    //update donation to 1 so it can be added to
                    MainListEntity mainListEntity3 = mainListService.findDonationToStartMaturityProcess(mainListEntity.getPayerUsername(),amountToPay);
                    if (amountToPay - mainListEntity3.getDonatedAmount() == 0.0) {
                        mainListEntity3.setStatus(1);
                        mainListService.saveUser(mainListEntity3);
                    }


                    for (MainListEntity mainListEntity2 : paidDonations) {
                        if (mainListEntity2.getStatus() == 2) {
                            mainListEntity2.setStatus(3);
                            mainListService.saveUser(mainListEntity2);
                        }
                    }
                }

                boolean setToCompleted = true;

                for (MainListEntity donated : donations) {
                    if (donated.getStatus() == 2 && (donated.getEnabled() != 1 || donated.getEnabled() != 0)) {
                        //do nothing
                    } else {
                        setToCompleted = false;
                    }

                }

                if (setToCompleted) {
                    MainListEntity completeDonation = this.mainListService.findDonationByMainListReference(mainListEntity.getDonationReference());
                    if (completeDonation.getAdjustedAmount() == 0.0D) {
                        completeDonation.setStatus(3);
                        completeDonation.setEnabled(0);
                        this.mainListService.saveUser(completeDonation);
                        LOG.info("DONATION COMPLETED: " + completeDonation.getMainListReference());
                        return "redirect:/"+requester;
                    }
                }
            }
        } catch (MainListNotFoundException var13) {
            response.setMessage(var13.getMessage());
            response.setStatusCode(StatusCodeEnum.NOTFOUND.getStatusCode());
            LOG.error(var13.getCause() + "   " + var13.getMessage());
            return errorResponse(model, response, session,requester);
        }


        return "redirect:/"+requester;
    }

    private String errorResponse(Model model, ApproveDonationResponse response, HttpSession session, String requester) {
        model.addAttribute("profile", session.getAttribute("profile"));
        GetMainListResponse responses = (GetMainListResponse) session.getAttribute("mainList");
        responses.setStatusCode(response.getStatusCode());
        responses.setMessage(response.getMessage());
        model.addAttribute("response", responses);
        return "redirect:/"+requester;

    }
}

