//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.golden_xchange.controller.updateuserprofile;

import com.golden_xchange.domain.users.exception.GoldenRichesUsersNotFoundException;
import com.golden_xchange.domain.users.model.GoldenRichesUsers;
import com.golden_xchange.domain.users.service.GoldenRichesUsersService;
import com.golden_xchange.domain.utilities.Enums.StatusCodeEnum;
import com.golden_xchange.domain.utilities.GeneralDomainFunctions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;

@Controller
public class UpdateUserProfileWebserviceEndpoint {
    private static final String NAMESPACE_URI = "updateUserProfile.webservice.golden_xchange.com";
    @Autowired
    GoldenRichesUsersService goldenRichesUsersService;
    GoldenRichesUsers goldenRichesUsers = new GoldenRichesUsers();

    public UpdateUserProfileWebserviceEndpoint() {
    }

    @PayloadRoot(
        namespace = "updateUserProfile.webservice.golden_xchange.com",
        localPart = "UpdateUserProfileDetailsRequest"
    )
    @ResponsePayload
    public UpdateUserProfileDetailsResponse handleCreateGoldenRichesRequest(@RequestPayload UpdateUserProfileDetailsRequest request) throws Exception {
        UpdateUserProfileDetailsResponse response = new UpdateUserProfileDetailsResponse();

        try {
            if(null == request.getUsername() || request.getUsername().equals("")) {
                response.setMessage("Username Can't be Left Empty");
                response.setStatusCode(StatusCodeEnum.EMPTYVALUE.getStatusCode());
                return response;
            }

            this.goldenRichesUsers = this.goldenRichesUsersService.findUserByMemberId(request.getUsername());
            if(!this.inputValidation(request, response)) {
                return response;
            }

            this.goldenRichesUsersService.saveUser(this.goldenRichesUsers);
        } catch (GoldenRichesUsersNotFoundException var4) {
            response.setMessage("Username: " + request.getUsername() + " Not Found");
            response.setStatusCode(StatusCodeEnum.FORBIDDEN.getStatusCode());
            return response;
        }

        response.setMessage("User " + request.getUsername() + " Was Successfully Updated");
        response.setStatusCode(StatusCodeEnum.OK.getStatusCode());
        return response;
    }

    public boolean inputValidation(@RequestPayload UpdateUserProfileDetailsRequest request, UpdateUserProfileDetailsResponse response) throws Exception {
        if(null != request.getMobileNumber() && !request.getMobileNumber().isEmpty()) {
            this.goldenRichesUsers.setTelephoneNumber(request.getMobileNumber());
        }

        if(null != request.getPassword1() && !request.getPassword1().isEmpty()) {
            if(!request.getPassword1().equals(request.getPassword2())) {
                response.setStatusCode(StatusCodeEnum.INVALIDSYNTAX.getStatusCode());
                response.setMessage("Your passwords do not match!!");
                return false;
            }

            this.goldenRichesUsers.setPassword(GeneralDomainFunctions.getCryptedPasswordAndSalt(request.getPassword1()));
        }

        if(null != request.getEmailAddress() && !request.getEmailAddress().isEmpty()) {
            if(!GeneralDomainFunctions.isEmailValid(request.getEmailAddress())) {
                response.setStatusCode(StatusCodeEnum.INVALIDSYNTAX.getStatusCode());
                response.setMessage("Invalid emailAddress!!");
                return false;
            }

            this.goldenRichesUsers.setEmailAddress(request.getEmailAddress());
        }

        return true;
    }
}

