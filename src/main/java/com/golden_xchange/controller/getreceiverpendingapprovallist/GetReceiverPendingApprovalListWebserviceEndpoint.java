//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.golden_xchange.controller.getreceiverpendingapprovallist;

import com.golden_xchange.controller.getmainlist.GetMainListResponse;
import com.golden_xchange.controller.getmainlist.MainList;
import com.golden_xchange.domain.mainlist.exception.MainListNotFoundException;
import com.golden_xchange.domain.mainlist.model.MainListEntity;
import com.golden_xchange.domain.mainlist.service.MainListService;
import com.golden_xchange.domain.users.exception.GoldenRichesUsersNotFoundException;
import com.golden_xchange.domain.users.model.GoldenRichesUsers;
import com.golden_xchange.domain.users.service.GoldenRichesUsersService;
import com.golden_xchange.domain.utilities.DateConversion;
import com.golden_xchange.domain.utilities.Enums.StatusCodeEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;

import javax.servlet.http.HttpSession;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.logging.Logger;

@Controller
public class GetReceiverPendingApprovalListWebserviceEndpoint {
    Logger aprrover = Logger.getLogger(this.getClass().getName());

    @Autowired
    MainListService mainListService;
    @Autowired
    GoldenRichesUsersService goldenRichesUsersService;

    public GetReceiverPendingApprovalListWebserviceEndpoint() {
    }

    @RequestMapping({"/approvals"})
    public String handleGetReceiverPendingApprovalListRequest(Model model, HttpSession session) throws Exception {
        GetReceiverPendingApprovalListResponse response = new GetReceiverPendingApprovalListResponse();
        GetMainListResponse responses=new GetMainListResponse();
        model.addAttribute("notifications",session.getAttribute("notifications")) ;
        model.addAttribute("notificationCount",session.getAttribute("notificationCount"));
        GoldenRichesUsers goldenRichesUsers = (GoldenRichesUsers) session.getAttribute("profile");
        model.addAttribute("profile", goldenRichesUsers);

        try {
            List<MainListEntity> payerPendingList = this.mainListService.returnPendingApprovalReceiverList(goldenRichesUsers.getUserName().trim());

            for(MainListEntity mainListEntity:payerPendingList){
                 prepareMainListResponse(responses,mainListEntity);
            }

            response.setMessage("Donations Pending Approval List Successfully Returned");
            response.setStatusCode(StatusCodeEnum.OK.getStatusCode());
            return response(model,session,responses);
        } catch (MainListNotFoundException var7) {
            aprrover.warning(var7.getMessage());
            response.setMessage(var7.getMessage());
            response.setStatusCode(StatusCodeEnum.NOTFOUND.getStatusCode());
            return response(model,session,responses);
        }
        catch (Exception exp){
            aprrover.warning(exp.getMessage());
            response.setMessage(exp.getMessage());
            response.setStatusCode(StatusCodeEnum.NOTFOUND.getStatusCode());
            return response(model,session,responses);
        }
    }


    private String response(Model model, HttpSession session, GetMainListResponse response) {
        model.addAttribute("response", response);
        session.setAttribute("mainList", response);
        model.addAttribute("profile", (GoldenRichesUsers) session.getAttribute("profile"));
        return "approvals";
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

}

