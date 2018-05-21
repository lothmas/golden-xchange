/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.golden_xchange.controller.userlogin;


import com.golden_xchange.domain.notifications.model.NotificationsEntity;
import com.golden_xchange.domain.notifications.service.NotificationsService;
import com.golden_xchange.domain.users.exception.GoldenRichesUsersNotFoundException;
import com.golden_xchange.domain.users.model.GoldenRichesUsers;
import com.golden_xchange.domain.users.service.GoldenRichesUsersService;
import com.golden_xchange.domain.utilities.Enums;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.security.NoSuchAlgorithmException;
import java.util.List;

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

    @Autowired
    NotificationsService notificationsService;


    @RequestMapping({"/login"})
    public String loadPage(Model model) {
        model.addAttribute("login", new LoginRequest());
        UserLoginResponse response = new UserLoginResponse();
        model.addAttribute("response", response);
        return "login";
    }

    @RequestMapping(value = {"/login"}, method = RequestMethod.POST)
    public String generateReport(@Valid LoginRequest loginRequest, Model model, HttpSession session,
                                 @RequestParam(value = "action", required = true) String action, @RequestParam(value = "username", required = false) String username, @RequestParam(value = "password", required = false) String password) throws Exception {

        UserLoginResponse response = new UserLoginResponse();
        GoldenRichesUsers goldenRichesUsers = new GoldenRichesUsers();


        model.addAttribute("login", new LoginRequest());

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

            goldenRichesUsers = goldenRichesUsersService.findGoldenRichesUsersByEmailAndPassword(username, password);
            if(goldenRichesUsers.getEnabled()==0){
                response.setMessage("Your Account Has Been De-Activated, Suspicious Activities Identified.");
                response.setStatusCode(Enums.StatusCodeEnum.FORBIDDEN.getStatusCode());
                return errorResponse(model, response);
            }
            model.addAttribute("profile", goldenRichesUsers);
            session.setAttribute("profile", goldenRichesUsers);

        } catch (GoldenRichesUsersNotFoundException | NoSuchAlgorithmException grunf) {

            try {

                goldenRichesUsers = goldenRichesUsersService.findGoldenRichesUsersByUsernameAndPassword(username, password);
                if(goldenRichesUsers.getEnabled()==0){
                    response.setMessage("Your Account Has Been De-Activated, Suspicious Activities Identified.");
                    response.setStatusCode(Enums.StatusCodeEnum.FORBIDDEN.getStatusCode());
                    return errorResponse(model, response);
                }
                response.setMessage("User: " + goldenRichesUsers.getUserName() + " Successfully LoggedIn");
                response.setStatusCode(Enums.StatusCodeEnum.OK.getStatusCode());
                model.addAttribute("profile", goldenRichesUsers);
                session.setAttribute("profile", goldenRichesUsers);
                getNotifications(model, goldenRichesUsers, session);

                return "profile";
            } catch (GoldenRichesUsersNotFoundException | NoSuchAlgorithmException unf) {
                response.setMessage(unf.getMessage());
                response.setStatusCode(Enums.StatusCodeEnum.NOTAUTHORISED.getStatusCode());
                return errorResponse(model, response);
            }


        }
        response.setMessage("User: " + goldenRichesUsers.getUserName() + " Successfully LoggedIn");
        response.setStatusCode(Enums.StatusCodeEnum.OK.getStatusCode());

        getNotifications(model, goldenRichesUsers, session);
        return "profile";
    }

    public void getNotifications(Model model, GoldenRichesUsers goldenRichesUsers, HttpSession session) {
        try {
            List<NotificationsEntity> notificationsEntityList = notificationsService.getUserNotifications(goldenRichesUsers.getUserName());
            int count = 0;
            for (NotificationsEntity notificationsEntity : notificationsEntityList) {
                if (notificationsEntity.getStatus() == 0) {
                    count++;
                }
            }
            model.addAttribute("notificationCount", count);
            session.setAttribute("notificationCount", count);
            model.addAttribute("notifications", notificationsEntityList);
            session.setAttribute("notifications", notificationsEntityList);
        } catch (Exception exp) {
            model.addAttribute("notifications", new NotificationsEntity());
            session.setAttribute("notifications", new NotificationsEntity());
            model.addAttribute("notificationCount", 0);
            session.setAttribute("notificationCount", 0);
        }
    }

    private String errorResponse(Model model, UserLoginResponse response) {
        model.addAttribute("response", response);
        return "login";
    }


}

//Hibernate: create table bank_account (id integer not null, account_holder_name varchar(45) not null, account_number varchar(45) not null, bank_name varchar(45) not null, branch_number varchar(45) not null, email_address varchar(45) not null, enabled integer not null, first_name varchar(45) not null, gender varchar(45) not null, password varchar(45) not null, surname varchar(45) not null, telephone varchar(45) not null, username varchar(45) not null, primary key (id)) ENGINE=InnoDB
//        Hibernate: create table bank_account_aud (id integer not null, rev integer not null, revtype tinyint, account_holder_name varchar(45), account_number varchar(45), bank_name varchar(45), branch_number varchar(45), email_address varchar(45), enabled integer, first_name varchar(45), gender varchar(45), password varchar(45), surname varchar(45), telephone varchar(45), username varchar(45), primary key (id, rev)) ENGINE=InnoDB
//        Hibernate: create table main_list (id integer not null, adjusted_amount double precision not null, amount_to_receive double precision not null, bank_account_number varchar(45) not null, date datetime not null, deposit_reference varchar(45) not null, donated_amount double precision not null, donation_reference varchar(45) not null, enabled integer not null, main_list_reference varchar(45) not null, payer_user_name varchar(45) not null, status integer not null, updated_date datetime, user_name varchar(255), primary key (id)) ENGINE=InnoDB
//        Hibernate: create table main_list_aud (id integer not null, rev integer not null, revtype tinyint, adjusted_amount double precision, amount_to_receive double precision, bank_account_number varchar(45), date datetime, deposit_reference varchar(45), donated_amount double precision, donation_reference varchar(45), enabled integer, main_list_reference varchar(45), payer_user_name varchar(45), status integer, updated_date datetime, user_name varchar(255), primary key (id, rev)) ENGINE=InnoDB
//        Hibernate: create table revinfo (rev integer not null auto_increment, revtstmp bigint, primary key (rev)) ENGINE=InnoDB
//        Hibernate: create table users (id integer not null, account_holder_name varchar(45) not null, account_number varchar(45) not null, bank_name varchar(45) not null, branch_number varchar(45) not null, email_address varchar(45) not null, enabled integer not null, first_name varchar(45) not null, gender varchar(45) not null, password varchar(45) not null, profile_pic varchar(45) not null, surname varchar(45) not null, telephone varchar(45) not null, username varchar(45) not null, primary key (id)) ENGINE=InnoDB
//        Hibernate: create table users_aud (id integer not null, rev integer not null, revtype tinyint, account_holder_name varchar(45), account_number varchar(45), bank_name varchar(45), branch_number varchar(45), email_address varchar(45), enabled integer, first_name varchar(45), gender varchar(45), password varchar(45), profile_pic varchar(45), surname varchar(45), telephone varchar(45), username varchar(45), primary key (id, rev)) ENGINE=InnoDB

