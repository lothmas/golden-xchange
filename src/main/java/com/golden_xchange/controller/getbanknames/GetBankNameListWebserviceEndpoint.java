//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.golden_xchange.controller.getbanknames;

import com.golden_xchange.domain.utilities.Enums.BankNameEnum;
import com.golden_xchange.domain.utilities.Enums.StatusCodeEnum;
import org.springframework.stereotype.Controller;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;

@Controller
public class GetBankNameListWebserviceEndpoint {
    private static final String NAMESPACE_URI = "getBankNames.webservice.golden_xchange.com";

    public GetBankNameListWebserviceEndpoint() {
    }

    @PayloadRoot(
        namespace = "getBankNames.webservice.golden_xchange.com",
        localPart = "GetBankNameListRequest"
    )
    @ResponsePayload
    public GetBankNameListResponse handleGetBankNameListRequest(@RequestPayload GetBankNameListRequest request) throws Exception {
        GetBankNameListResponse response = new GetBankNameListResponse();
        BankNameEnum[] var3 = BankNameEnum.values();
        int var4 = var3.length;

        for(int var5 = 0; var5 < var4; ++var5) {
            BankNameEnum bankNameList = var3[var5];
            StatusCodeEnum status = StatusCodeEnum.OK;
            response.setStatusCode(status.getStatusCode());
            response.setMessage(status.getStatusMessage());
            ReturnData BankNames = new ReturnData();
            BankNames.setBankName(bankNameList.getName());
            if(bankNameList.getValue() != 0) {
                BankNames.setBranchOrSortCode(Integer.valueOf(bankNameList.getValue()));
            }

            if(!bankNameList.getDefaultBranchName().isEmpty()) {
                BankNames.setBranchName(bankNameList.getDefaultBranchName());
            }

            response.getReturnData().add(BankNames);
        }

        return response;
    }
}

