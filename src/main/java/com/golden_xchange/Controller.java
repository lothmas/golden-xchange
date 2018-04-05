package com.golden_xchange;


import com.golden_xchange.controller.createdonation.CreateDonationResponse;
import com.golden_xchange.controller.getbanknames.GetBankNameListResponse;
import com.golden_xchange.controller.getbanknames.GetBankNameListWebserviceEndpoint;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.security.NoSuchAlgorithmException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;


@ControllerAdvice
@org.springframework.stereotype.Controller
public class Controller {


@Autowired
    GetBankNameListWebserviceEndpoint getBankNameListWebserviceEndpoint;


    public static String byteToString(byte[] _bytes) {
        String file_string = "";

        for (int i = 0; i < _bytes.length; i++) {
            file_string += (char) _bytes[i];
        }

        return file_string;
    }

//    @RequestMapping({"/"})
//    public String getIndex() {
//        return "index";
//    }


    @RequestMapping({"/profile","/dashboard","/new_donation"})
    public String loginVerification(HttpServletRequest request, Model model, HttpSession session,
                                    @RequestParam(value = "username", required = false) String username, @RequestParam(value = "searchText", required = false) String searchText
            , @RequestParam(value = "password", required = false) String password, final RedirectAttributes redirectAttributes) {
        model.addAttribute("bankDetails",new GetBankNameListResponse());
        String url = request.getRequestURI();
        int index = url.lastIndexOf("/");
        if (index != -1) {
            if (url.contains("profile")) {
                model.addAttribute("profile",session.getAttribute("profile"));
                model.addAttribute("response",new CreateDonationResponse());
                return "profile";
            }
            if (url.contains("dashboard")) {
                return "dashboard";
            }
            if (url.contains("new_donation")) {
                try {
                    model.addAttribute("profile",session.getAttribute("profile"));
                    model.addAttribute("response",new CreateDonationResponse());
                } catch (Exception e) {
                    e.printStackTrace();
                }
                return "new_donation";
            }
//            if (url.contains("current_donations")) {
//
//
//                return "current_donations";
//            }

        }

        return url;
    }


    @RequestMapping(value = {"/logout"}, method = RequestMethod.GET)
    public String logout(HttpServletRequest request, HttpServletResponse response) throws Exception {

        request.getSession().invalidate();
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null) {
            new SecurityContextLogoutHandler().logout(request, response, auth);
        }
        return "redirect:/";
    }


}