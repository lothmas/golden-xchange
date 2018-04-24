//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.golden_xchange.controller.getpayerpendingdonationslist;

import com.golden_xchange.domain.mainlist.exception.MainListNotFoundException;
import com.golden_xchange.domain.mainlist.model.MainListEntity;
import com.golden_xchange.domain.mainlist.service.MainListService;
import com.golden_xchange.domain.users.service.GoldenRichesUsersService;
import com.golden_xchange.domain.utilities.DateConversion;
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
public class GetPayerPendingDonationsListWebserviceEndpoint {
    DateConversion dateConversion = new DateConversion();
    @Autowired
    MainListService mainListService;
    @Autowired
    GoldenRichesUsersService goldenRichesUsersService;

    public GetPayerPendingDonationsListWebserviceEndpoint() {
    }


    public GetPayerPendingDonationsListResponse handleDonationsListRequest(@RequestPayload GetPayerPendingDonationsListRequest request) throws Exception {
        GetPayerPendingDonationsListResponse response = new GetPayerPendingDonationsListResponse();

        try {
            List<MainListEntity> payerPendingList = this.mainListService.returnPendingPayerList(request.getUsername());
            Iterator var4 = payerPendingList.iterator();

            while(var4.hasNext()) {
                MainListEntity pendingList = (MainListEntity)var4.next();
                PayerPendingList pending = new PayerPendingList();
                pending.setBeneficiaryMainListReference(pendingList.getMainListReference());
                pending.setDepositReference(pendingList.getDepositReference());
                pending.setAmountPaid(pendingList.getDonatedAmount());
                pending.setStatus(pendingList.getStatus());
                pending.setBeneficiaryUsername(pendingList.getPayerUsername());
                pending.setDateCreated(this.dateConversion.timeStampToGregorian(pendingList.getUpdatedDate()));
                pending.setBeneficiaryUsername(pendingList.getUserName());
                response.getReturnData().add(pending);
            }

            if(response.getReturnData() != null) {
                response.setMessage("PayerPendingList Successfully Returned");
                response.setStatusCode(StatusCodeEnum.OK.getStatusCode());
                return response;
            } else {
                response.setMessage("No Pending List Found");
                response.setStatusCode(StatusCodeEnum.NOTFOUND.getStatusCode());
                return response;
            }
        } catch (MainListNotFoundException var7) {
            response.setMessage(var7.getMessage());
            response.setStatusCode(StatusCodeEnum.NOTFOUND.getStatusCode());
            return response;
        }
    }
}

