//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.golden_xchange.controller.getdonationslist;

import com.golden_xchange.domain.mainlist.model.MainListEntity;
import com.golden_xchange.domain.mainlist.service.MainListService;
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
import java.sql.Timestamp;
import java.util.GregorianCalendar;
import java.util.Iterator;
import java.util.List;

@Controller
public class GetDonationsListWebserviceEndpoint {
    private static final String NAMESPACE_URI = "getDonationsList.webservice.golden_xchange.com";
    @Autowired
    MainListService mainListService;

    public GetDonationsListWebserviceEndpoint() {
    }

    @PayloadRoot(
        namespace = "getDonationsList.webservice.golden_xchange.com",
        localPart = "GetDonationsListRequest"
    )
    @ResponsePayload
    public GetDonationsListResponse handleDonationsListRequest(@RequestPayload GetDonationsListRequest request) throws Exception {
        GetDonationsListResponse response = new GetDonationsListResponse();

        try {
            List<MainListEntity> mainList = this.mainListService.findDonorsByDonationReference(request.getMainListReference());
            Iterator var4 = mainList.iterator();

            while(var4.hasNext()) {
                MainListEntity donorList = (MainListEntity)var4.next();
                DonorList donor = new DonorList();
                donor.setPayerUserNamer(donorList.getPayerUsername());
                new Timestamp(System.currentTimeMillis());
                Date date = new Date(donorList.getDate().getTime());
                GregorianCalendar gregory = new GregorianCalendar();
                gregory.setTime(date);
                XMLGregorianCalendar calendar = DatatypeFactory.newInstance().newXMLGregorianCalendar(gregory);
                donor.setDateCreated(calendar);
                donor.setDepositReference(donorList.getDepositReference());
                donor.setMainListReference(donorList.getMainListReference());
                donor.setStatus(donorList.getStatus());
                donor.setAmountPaid(donorList.getDonatedAmount());
                response.getReturnData().add(donor);
            }

            response.setMessage("Donor List Successfully Returned");
            response.setStatusCode(StatusCodeEnum.OK.getStatusCode());
            return response;
        } catch (Exception var11) {
            response.setMessage(var11.getMessage());
            response.setStatusCode(StatusCodeEnum.NOTFOUND.getStatusCode());
            return response;
        }
    }
}

