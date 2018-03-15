//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.golden_xchange.controller.getmainlistwithdonation;

import com.golden_xchange.domain.bankaccounts.model.BankAccountsEntity;
import com.golden_xchange.domain.bankaccounts.service.BankAccountService;
import com.golden_xchange.domain.mainlist.exception.MainListNotFoundException;
import com.golden_xchange.domain.mainlist.model.MainListEntity;
import com.golden_xchange.domain.mainlist.service.MainListService;
import com.golden_xchange.domain.users.model.GoldenRichesUsers;
import com.golden_xchange.domain.users.service.GoldenRichesUsersService;
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
public class GetMainListWithDonationsWebserviceEndpoint {
    private static final String NAMESPACE_URI = "getMainListWithDonations.webservice.golden_xchange.com";
    int decider = 0;
    @Autowired
    MainListService mainListService;
    @Autowired
    BankAccountService bankAccountService;
    @Autowired
    GoldenRichesUsersService goldenRichesUsersService;
    BankAccountsEntity createBankAccount = new BankAccountsEntity();

    public GetMainListWithDonationsWebserviceEndpoint() {
    }

    @PayloadRoot(
        namespace = "getMainListWithDonations.webservice.golden_xchange.com",
        localPart = "GetMainListWithDonationsRequest"
    )
    @ResponsePayload
    public GetMainListWithDonationResponse handleCreateGoldenRichesRequest(@RequestPayload GetMainListWithDonationRequest request) throws Exception {
        GetMainListWithDonationResponse response = new GetMainListWithDonationResponse();
        if(null != request.getUsername() && !request.getUsername().isEmpty()) {
            List returnedMainList = null;

            try {
                returnedMainList = this.mainListService.returnMainList(request.getUsername());
            } catch (MainListNotFoundException var16) {
                response.setMessage("No user found with username: " + request.getUsername());
                response.setStatusCode(StatusCodeEnum.NOTFOUND.getStatusCode());
                return response;
            }

            Iterator var4 = returnedMainList.iterator();

            while(var4.hasNext()) {
                MainListEntity retunedList = (MainListEntity)var4.next();
                MainList mainLists = new MainList();
                GoldenRichesUsers checkUser = this.goldenRichesUsersService.getUserByBankDetails(retunedList.getBankAccountNumber());
                mainLists.setUsername(checkUser.getUserName());
                mainLists.setEmailAddress(checkUser.getEmailAddress());
                mainLists.setMobileNumber(checkUser.getTelephoneNumber());
                mainLists.setBranchNumber(checkUser.getBranchNumber());
                mainLists.setBankName(checkUser.getBankName());
                mainLists.setAccountHolderName(checkUser.getAccountHoldername());
                mainLists.setAccountNumber(checkUser.getAccountNumber());
                mainLists.setAmount(retunedList.getAdjustedAmount());
                mainLists.setMainListReference(retunedList.getMainListReference());
                response.getReturnMainListData().add(mainLists);

                try {
                    List<MainListEntity> mainList = this.mainListService.findDonorsByDonationReference(mainLists.getMainListReference());
                    Iterator var9 = mainList.iterator();

                    while(var9.hasNext()) {
                        MainListEntity donorList = (MainListEntity)var9.next();
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
                        response.getReturnDonationData().add(donor);
                    }
                } catch (Exception var17) {
                    ;
                }
            }

            if(returnedMainList.size() == 0) {
                response.setMessage("No List to return");
                response.setStatusCode(StatusCodeEnum.NOTFOUND.getStatusCode());
                return response;
            } else {
                response.setMessage("MainList Returned Successfully");
                response.setStatusCode(StatusCodeEnum.OK.getStatusCode());
                return response;
            }
        } else {
            response.setMessage("Username Can't be Left Empty");
            response.setStatusCode(StatusCodeEnum.EMPTYVALUE.getStatusCode());
            return response;
        }
    }
}

