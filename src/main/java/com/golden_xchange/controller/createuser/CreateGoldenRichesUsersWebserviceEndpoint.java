//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.golden_xchange.controller.createuser;

import com.golden_xchange.domain.users.exception.GoldenRichesUsersNotFoundException;
import com.golden_xchange.domain.users.model.GoldenRichesUsers;
import com.golden_xchange.domain.users.service.GoldenRichesUsersService;
import com.golden_xchange.domain.utilities.Enums.StatusCodeEnum;
import com.golden_xchange.domain.utilities.GeneralDomainFunctions;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.binary.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;

import javax.servlet.http.HttpSession;
import javax.transaction.Transactional;
import javax.validation.Valid;

@ControllerAdvice
@Controller
public class CreateGoldenRichesUsersWebserviceEndpoint {
    Logger GoldenRichesUsersLogger = Logger.getLogger(this.getClass().getName());
    @Autowired
    GoldenRichesUsersService goldenRichesUsersService;

    public CreateGoldenRichesUsersWebserviceEndpoint() {
    }

    @RequestMapping(value = {"/register"})
    public String handleCreateGoldenRichesRequest(Model model){
        CreateGoldenRichesUserResponse response = new CreateGoldenRichesUserResponse();
        model.addAttribute("response",response);
        return "register";
    }

    @RequestMapping(value = {"/register"}, method = RequestMethod.POST)
    @Transactional
    public String handleCreateGoldenRichesRequest(@Valid CreateGoldenRichesUserRequest request, Model model, HttpSession session, @RequestParam(value = "diallingCode", required = false) String diallingCode
            , @RequestParam(value = "pic", required = false) MultipartFile profilePic) throws Exception {
        CreateGoldenRichesUserResponse response = new CreateGoldenRichesUserResponse();
        GoldenRichesUsers goldenRichesUsers = new GoldenRichesUsers();

        try {
            GoldenRichesUsers goldenUsers = this.goldenRichesUsersService.getUserByBankDetails(request.getAccountNumber());
            if(null != goldenUsers) {
                response.setMessage("AccountNumber Already Registered");
                response.setStatusCode(StatusCodeEnum.FORBIDDEN.getStatusCode());
                return registerResponse(model, response);
            }
        } catch (Exception var5) {
            //do nothing
        }

        try {
            if(null != request.getUserName() && !request.getUserName().equals("")) {
                this.goldenRichesUsersService.findUserByMemberId(request.getUserName());
                response.setMessage("Username: " + request.getUserName() + " Already Exists");
                response.setStatusCode(StatusCodeEnum.FORBIDDEN.getStatusCode());
                return registerResponse(model, response);
            } else {
                response.setMessage("Username Can't be Left Empty");
                response.setStatusCode(StatusCodeEnum.EMPTYVALUE.getStatusCode());
                return registerResponse(model, response);
            }
        } catch (GoldenRichesUsersNotFoundException var6) {
            if(this.inputValidation(request, response)) {
                return registerResponse(model, response);
            } else {
                goldenRichesUsers.setAccountNumber(request.getAccountNumber());
                goldenRichesUsers.setBankName(request.getBankName());
                goldenRichesUsers.setBranchNumber(request.getBranchNumber());
                goldenRichesUsers.setEmailAddress(request.getEmailAddress());
                goldenRichesUsers.setFirstName(request.getFirstName());
                goldenRichesUsers.setPassword(GeneralDomainFunctions.getCryptedPasswordAndSalt(request.getPassword()));
                goldenRichesUsers.setSurname(request.getSurname());
                goldenRichesUsers.setTelephoneNumber("+"+diallingCode+request.getTelephone());
                goldenRichesUsers.setUserName(request.getUserName());
                goldenRichesUsers.setEnabled((byte) 1);
                goldenRichesUsers.setAccountHoldername(request.getAccountHolderName());
                goldenRichesUsers.setGender(request.getGender());
                goldenRichesUsers.setAccountType(request.getAccountType());
                goldenRichesUsers.setProfilePic(StringUtils.newStringUtf8(Base64.encodeBase64(profilePic.getBytes(), false)));
                goldenRichesUsers.setReferenceUser(request.getReferenceUser());
                this.goldenRichesUsersService.saveUser(goldenRichesUsers);
                response.setMessage("User " + request.getUserName() + " Was Successfully Created");
                response.setStatusCode(StatusCodeEnum.CREATED.getStatusCode());
                model.addAttribute("profile",goldenRichesUsers);
                session.setAttribute("profile", goldenRichesUsers);
                return "profile";
            }
        }
    }

    private String registerResponse(Model model, CreateGoldenRichesUserResponse response) {
        model.addAttribute("response",response);
        return "register";
    }

    public boolean inputValidation(@RequestPayload CreateGoldenRichesUserRequest request, CreateGoldenRichesUserResponse response) throws GoldenRichesUsersNotFoundException {
        if(null != request.getFirstName() && !request.getFirstName().equals("")) {
            if(null != request.getSurname() && !request.getSurname().equals("")) {
                if(null != request.getAccountHolderName() && !request.getAccountHolderName().equals("")) {
                    if(null != request.getBankName() && !request.getBankName().equals("")) {
                        if(null != request.getBranchNumber() && !request.getBranchNumber().equals("")) {
                            if(null != request.getAccountNumber() && !request.getAccountNumber().equals("")) {
                                if(null != request.getTelephone() && !request.getTelephone().equals("")) {
                                    if(null != request.getPassword() && !request.getPassword().equals("")) {
                                        if(!request.getPassword().equals(request.getPassword2())) {
                                            response.setStatusCode(StatusCodeEnum.INVALIDSYNTAX.getStatusCode());
                                            response.setMessage("Your passwords do not match!!");
                                            return true;
                                        } else {
                                            return false;
                                        }
                                    } else {
                                        response.setMessage("Password Can't be Left Empty");
                                        response.setStatusCode(StatusCodeEnum.EMPTYVALUE.getStatusCode());
                                        return true;
                                    }
                                } else {
                                    response.setMessage("Telephone Can't be Left Empty");
                                    response.setStatusCode(StatusCodeEnum.EMPTYVALUE.getStatusCode());
                                    return true;
                                }
                            } else {
                                response.setMessage("AccountNumber Can't be Left Empty");
                                response.setStatusCode(StatusCodeEnum.EMPTYVALUE.getStatusCode());
                                return true;
                            }
                        } else {
                            response.setMessage("BranchNumber Can't be Left Empty");
                            response.setStatusCode(StatusCodeEnum.EMPTYVALUE.getStatusCode());
                            return true;
                        }
                    } else {
                        response.setMessage("BankName Can't be Left Empty");
                        response.setStatusCode(StatusCodeEnum.EMPTYVALUE.getStatusCode());
                        return true;
                    }
                } else {
                    response.setMessage("AccountHolderName Can't be Left Empty");
                    response.setStatusCode(StatusCodeEnum.EMPTYVALUE.getStatusCode());
                    return true;
                }
            } else {
                response.setMessage("Surname Can't be Left Empty");
                response.setStatusCode(StatusCodeEnum.EMPTYVALUE.getStatusCode());
                return true;
            }
        } else {
            response.setMessage("FirstName Can't be Left Empty");
            response.setStatusCode(StatusCodeEnum.EMPTYVALUE.getStatusCode());
            return true;
        }
    }

    public boolean emailAddressValidation(@RequestPayload CreateGoldenRichesUserRequest request, CreateGoldenRichesUserResponse response) throws GoldenRichesUsersNotFoundException, GoldenRichesUsersNotFoundException {
        if(null != request.getEmailAddress() && !request.getEmailAddress().equals("")) {
            if(!GeneralDomainFunctions.isEmailValid(request.getEmailAddress())) {
                response.setMessage("Provided EmailAddress is Invalid");
                response.setStatusCode(StatusCodeEnum.INVALIDSYNTAX.getStatusCode());
                return true;
            } else if(this.goldenRichesUsersService.suppliedEmailExists(request.getEmailAddress())) {
                response.setMessage("EmailAddress: " + request.getEmailAddress() + " Already Exists");
                response.setStatusCode(StatusCodeEnum.FORBIDDEN.getStatusCode());
                return true;
            } else {
                return false;
            }
        } else {
            response.setMessage("EmailAddress Can't be Left Empty");
            response.setStatusCode(StatusCodeEnum.EMPTYVALUE.getStatusCode());
            return true;
        }
    }
}

