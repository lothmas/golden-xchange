//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.golden_xchange.controller.getcompleteddonations;

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
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;

import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;
import java.sql.Date;
import java.util.GregorianCalendar;
import java.util.Iterator;
import java.util.List;

@Controller
public class GetCompletedDonationsWebserviceEndpoint {
    private static final String NAMESPACE_URI = "getCompletedDonations.webservice.golden_xchange.com";
    DateConversion dateConversion = new DateConversion();
    @Autowired
    MainListService mainListService;
    @Autowired
    GoldenRichesUsersService goldenRichesUsersService;

    public GetCompletedDonationsWebserviceEndpoint() {
    }

    @PayloadRoot(
        namespace = "getCompletedDonations.webservice.golden_xchange.com",
        localPart = "GetCompletedDonationsRequest"
    )
    @ResponsePayload
    public GetCompletedDonationsResponse handleGetCompletedDonationsRequest(@RequestPayload GetCompletedDonationsRequest request) throws Exception {
        GetCompletedDonationsResponse response = new GetCompletedDonationsResponse();
        DateConversion dateConversion = new DateConversion();

        try {
            List<MainListEntity> payerPendingList = this.mainListService.getCompletedDonations();
            Iterator var5 = payerPendingList.iterator();

            while(true) {
                MainListEntity pendingList;
                GoldenRichesUsers goldenRichesUsers;
                do {
                    if(!var5.hasNext()) {
                        response.setMessage("Donations Pending Approval List Successfully Returned");
                        response.setStatusCode(StatusCodeEnum.OK.getStatusCode());
                        return response;
                    }

                    pendingList = (MainListEntity)var5.next();
                    goldenRichesUsers = this.goldenRichesUsersService.getUserByBankDetails(pendingList.getBankAccountNumber());
                } while(!goldenRichesUsers.getUserName().equals(request.getUsername()) && !request.getUsername().equals(pendingList.getPayerUsername()));

                CompletedDonationsList completedDonationsList = new CompletedDonationsList();
                completedDonationsList.setMainListReference(pendingList.getMainListReference());
                completedDonationsList.setDepositReference(pendingList.getDepositReference());
                completedDonationsList.setAmountPaid(pendingList.getDonatedAmount());
                completedDonationsList.setStatus(pendingList.getStatus());
                completedDonationsList.setPayerUserNamer(pendingList.getPayerUsername());
                if(null != pendingList.getUpdatedDate()) {
                    completedDonationsList.setDateCreated(dateConversion.timeStampToGregorian(pendingList.getUpdatedDate()));
                } else {
                    Date date = new Date(pendingList.getDate().getTime());
                    GregorianCalendar gregory = new GregorianCalendar();
                    gregory.setTime(date);
                    XMLGregorianCalendar calendar = DatatypeFactory.newInstance().newXMLGregorianCalendar(gregory);
                    completedDonationsList.setDateCreated(calendar);
                }

                response.getReturnData().add(completedDonationsList);
            }
        } catch (GoldenRichesUsersNotFoundException | MainListNotFoundException var12) {
            response.setMessage(var12.getMessage());
            response.setStatusCode(StatusCodeEnum.NOTFOUND.getStatusCode());
            return response;
        }
    }
}

