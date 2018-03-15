//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.golden_xchange.controller.getbankaccounttypelist;

import com.golden_xchange.domain.utilities.Enums.BankAccountTypeEnum;
import com.golden_xchange.domain.utilities.Enums.StatusCodeEnum;
import org.springframework.stereotype.Controller;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;

@Controller
public class GetBankAccountTypeListWebserviceEndpoint {
    private static final String NAMESPACE_URI = "getBankAccountTypeList.webservice.golden_xchange.com";

    public GetBankAccountTypeListWebserviceEndpoint() {
    }

    @PayloadRoot(
        namespace = "getBankAccountTypeList.webservice.golden_xchange.com",
        localPart = "GetBankAccountTypeListRequest"
    )
    @ResponsePayload
    public GetBankAccountTypeListResponse handleGetBankAccountTypeListRequest(@RequestPayload GetBankAccountTypeListRequest request) throws Exception {
        GetBankAccountTypeListResponse response = new GetBankAccountTypeListResponse();
        BankAccountTypeEnum[] var3 = BankAccountTypeEnum.values();
        int var4 = var3.length;

        for(int var5 = 0; var5 < var4; ++var5) {
            BankAccountTypeEnum bankAccountTypeList = var3[var5];
            StatusCodeEnum status = StatusCodeEnum.OK;
            response.setStatusCode(status.getStatusCode());
            response.setMessage(status.getStatusMessage());
            ReturnData BankAccountType = new ReturnData();
            BankAccountType.setBankAccountTypeName(bankAccountTypeList.getName());
            response.getReturnData().add(BankAccountType);
        }

        return response;
    }
}

