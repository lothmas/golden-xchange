//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.golden_xchange.controller.getbanknames;

import com.golden_xchange.domain.utilities.Enums.BankNameEnum;
import com.golden_xchange.domain.utilities.Enums.StatusCodeEnum;
import org.springframework.stereotype.Component;

@Component
public class GetBankNameListWebserviceEndpoint {


    public GetBankNameListResponse handleGetBankNameListRequest() throws Exception {
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

