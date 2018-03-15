/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.golden_xchange.controller.userlogin;


import com.golden_xchange.domain.users.exception.GoldenRichesUsersNotFoundException;
import com.golden_xchange.domain.users.model.GoldenRichesUsers;
import com.golden_xchange.domain.users.service.GoldenRichesUsersService;
import com.golden_xchange.domain.utilities.Enums;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.security.NoSuchAlgorithmException;

/**
 * @author louis
 */
@ControllerAdvice
@Controller
public class UserLoginWebserviceEndpoint {

    private static final String NAMESPACE_URI = "userLogin.webservice.golden_xchange.com";


    Logger GoldenRichesUsersLogger = Logger.getLogger(this.getClass().getName());

    @Autowired
    GoldenRichesUsersService goldenRichesUsersService;

    @RequestMapping({"/login"})
    public String handleGetUserGoldenRichesUsersListRequest(HttpServletRequest request, Model model, HttpSession session,
                                                            @RequestParam(value = "username", required = false) String username, @RequestParam(value = "searchText", required = false) String searchText
            , @RequestParam(value = "password", required = false) String password, final RedirectAttributes redirectAttributes) {

        UserLoginResponse response = new UserLoginResponse();
        GoldenRichesUsers goldenRichesUsers = new GoldenRichesUsers();

        if (null == username && null == password) {
            return "login";
        }

        try {
            if (null == password || password.equals("")) {
                response.setMessage("Password Can't be Left Empty");
                response.setStatusCode(Enums.StatusCodeEnum.EMPTYVALUE.getStatusCode());
                return errorResponse(model, response);
            }
            if (null == username || username.equals("")) {
                response.setMessage("Username / EmailAddress Can't be Left Empty");
                response.setStatusCode(Enums.StatusCodeEnum.EMPTYVALUE.getStatusCode());
                return errorResponse(model, response);
            }


            model.addAttribute("profile", goldenRichesUsersService.findGoldenRichesUsersByUsernameAndPassword(username, password));


        } catch (GoldenRichesUsersNotFoundException | NoSuchAlgorithmException grunf) {

            try {

                goldenRichesUsers = goldenRichesUsersService.findGoldenRichesUsersByEmailAndPassword(username, password);
                response.setMessage("User: " + goldenRichesUsers.getUserName() + " Successfully LoggedIn");
                response.setStatusCode(Enums.StatusCodeEnum.OK.getStatusCode());
                model.addAttribute("profile", goldenRichesUsers);
                return "profile";
            } catch (GoldenRichesUsersNotFoundException | NoSuchAlgorithmException unf) {
                response.setMessage(unf.getMessage());
                response.setStatusCode(Enums.StatusCodeEnum.NOTAUTHORISED.getStatusCode());
                return errorResponse(model, response);
            }


        }
        response.setMessage("User: " + goldenRichesUsers.getUserName() + " Successfully LoggedIn");
        response.setStatusCode(Enums.StatusCodeEnum.OK.getStatusCode());
        return "profile";
    }

    private String errorResponse(Model model, UserLoginResponse response) {
        model.addAttribute("response", response);
        return "login";
    }


}
