package com.golden_xchange;


import com.golden_xchange.controller.File;
import com.golden_xchange.controller.JsonResponse;
import com.golden_xchange.controller.createdonation.CreateDonationResponse;
import com.golden_xchange.controller.createuser.CreateGoldenRichesUserResponse;
import com.golden_xchange.controller.getbanknames.GetBankNameListResponse;
import com.golden_xchange.controller.getbanknames.GetBankNameListWebserviceEndpoint;
import com.golden_xchange.controller.userlogin.LoginRequest;
import com.golden_xchange.controller.userlogin.UserLoginResponse;
import com.golden_xchange.domain.mainlist.model.MainListEntity;
import com.golden_xchange.domain.mainlist.service.MainListService;
import com.golden_xchange.domain.upload.PaymentProofEntity;
import com.golden_xchange.domain.upload.service.UploadService;
import com.golden_xchange.domain.users.exception.GoldenRichesUsersNotFoundException;
import com.golden_xchange.domain.users.model.GoldenRichesUsers;
import com.golden_xchange.domain.users.service.GoldenRichesUsersService;
import com.golden_xchange.domain.utilities.Enums;
import com.golden_xchange.domain.utilities.JsonObjectConversionUtility;
import com.golden_xchange.domain.utilities.SendEmailMessages;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.binary.StringUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.RandomStringUtils;
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
import javax.validation.Valid;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringWriter;
import java.security.NoSuchAlgorithmException;
import java.sql.Date;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;


@ControllerAdvice
@org.springframework.stereotype.Controller
public class Controller {


    @Autowired
    GetBankNameListWebserviceEndpoint getBankNameListWebserviceEndpoint;

    @Autowired
    MainListService mainListService;

    @Autowired
    UploadService uploadService;


    @Autowired
    GoldenRichesUsersService goldenRichesUsersService;


    public static String byteToString(byte[] _bytes) {
        String file_string = "";

        for (int i = 0; i < _bytes.length; i++) {
            file_string += (char) _bytes[i];
        }

        return file_string;
    }

    @RequestMapping({"/"})
    public String getIndex() {
        return "index";
    }


    @RequestMapping({"/profile", "/dashboard", "/new_donation", "/index", "upload", "administration", "admin_users", "donors"})
    public String loginVerification(HttpServletRequest request, Model model, HttpSession session,
                                    @RequestParam(value = "username", required = false) String username, @RequestParam(value = "searchText", required = false) String searchText
            , @RequestParam(value = "password", required = false) String password, final RedirectAttributes redirectAttributes) {
        model.addAttribute("bankDetails", new GetBankNameListResponse());
        String url = request.getRequestURI();

        if (!session.getAttributeNames().hasMoreElements()) {
            return "redirect:/";
        }
        int index = url.lastIndexOf("/");
        if (index != -1) {
            if (url.contains("profile")) {
                setModels(model, session);

                return "profile";
            }
            if (url.contains("dashboard")) {
                return "dashboard";
            }
            if (url.contains("new_donation")) {
                try {
                    setModels(model, session);

                } catch (Exception e) {
                    e.printStackTrace();
                }
                return "new_donation";
            }
            if (url.contains("upload")) {
                try {
                    setModels(model, session);

                } catch (Exception e) {
                    e.printStackTrace();
                }
                return "upload";
            }
            if (url.contains("donors")) {
                try {
                    setModels(model, session);
                    GoldenRichesUsers goldenRichesUsers = (GoldenRichesUsers) session.getAttribute("profile");
                    model.addAttribute("results", mainListService.getUserDonors(goldenRichesUsers.getUserName()));
                } catch (Exception e) {
                    e.printStackTrace();
                }
                return "donors";
            }
            if (url.contains("administration")) {
                try {
                    GoldenRichesUsers goldenRichesUsers = (GoldenRichesUsers) session.getAttribute("profile");
                    if (null != goldenRichesUsers.getUserType()) {
                        if (goldenRichesUsers.getUserType() == 99) {
                            List<MainListEntity> mainListEntityList = mainListService.getAllDonations();
                            model.addAttribute("mainList", mainListEntityList);
                            setModels(model, session);
                        } else {
                            return "redirect:/logout";
                        }
                    } else {
                        return "redirect:/logout";
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }
                return "administration";
            }


            if (url.contains("admin_users")) {
                try {
                    GoldenRichesUsers goldenRichesUsers = (GoldenRichesUsers) session.getAttribute("profile");
                    if (null != goldenRichesUsers.getUserType()) {
                        if (goldenRichesUsers.getUserType() == 99) {
                            List<GoldenRichesUsers> users = goldenRichesUsersService.getAllUsers();
                            model.addAttribute("users", users);
                            setModels(model, session);
                        } else {
                            return "redirect:/logout";
                        }
                    } else {
                        return "redirect:/logout";
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }
                return "admin_users";
            }


            if (url.contains("maturity")) {
                try {
                    setModels(model, session);

                } catch (Exception e) {
                    e.printStackTrace();
                }
                return "maturity";
            }
            if (url.contains("index")) {
                return "index";
            }
//            if (url.contains("current_donations")) {
//
//
//                return "current_donations";
//            }

        }

        return url;
    }

    private void setModels(Model model, HttpSession session) {
        model.addAttribute("profile", session.getAttribute("profile"));
        model.addAttribute("response", new CreateDonationResponse());
        model.addAttribute("notifications", session.getAttribute("notifications"));
        model.addAttribute("notificationCount", session.getAttribute("notificationCount"));
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


    @RequestMapping(value = {"/uploading"})
    @ResponseBody

    public String upload(HttpServletRequest request, HttpServletResponse response, Model model, HttpSession session, @RequestParam(value = "file", required = false) MultipartFile file, @RequestParam(value = "depositReference", required = false) String depositReference) {
        java.util.Date utilDate = new java.util.Date();
        new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Timestamp sqlDate = new Timestamp(utilDate.getTime());
        String url = request.getRequestURI();
        JsonObjectConversionUtility jsonObjectConversionUtility = new JsonObjectConversionUtility();
        JsonResponse jsonResponse = new JsonResponse();
        File file1 = new File();
        file1.setName(file.getOriginalFilename());
        file1.setSize((int) file.getSize());
        List<File> files = new ArrayList<>();
        setModels(model, session);
        int index = url.lastIndexOf("/");
        if (index != -1) {
            if (null != file) {
                try {
                    if (depositReference.equals("")) {
                        file1.setError("Provide Deposit Reference");
                        files.add(file1);
                        jsonResponse.setFiles(files);
                        return jsonObjectConversionUtility.objectToJson(jsonResponse);
                    }
                    String extension = FilenameUtils.getExtension(file.getOriginalFilename());
                    PaymentProofEntity paymentProofEntity = new PaymentProofEntity();
                    paymentProofEntity.setDate(sqlDate);
                    paymentProofEntity.setStatus(1);
                    paymentProofEntity.setDepositReference(depositReference);
                    paymentProofEntity.setExtension(extension);
                    GoldenRichesUsers goldenRichesUsers = (GoldenRichesUsers) session.getAttribute("profile");
                    paymentProofEntity.setUsername(goldenRichesUsers.getUserName());

                    if (!extension.equals("pdf")) {
                        paymentProofEntity.setFile(StringUtils.newStringUtf8(Base64.encodeBase64(file.getBytes(), false)));
                    } else {
                        StringWriter writer = new StringWriter();
                        IOUtils.copy(file.getInputStream(), writer);
                        String filess = writer.toString();
                        paymentProofEntity.setFile(filess);
                    }
                    uploadService.save(paymentProofEntity);

                    file1.setError("File Successfully Uploaded");
                    files.add(file1);
                    jsonResponse.setFiles(files);
                    return jsonObjectConversionUtility.objectToJson(jsonResponse);

                } catch (IOException e) {
                    file1.setError("File Failed To Successfully Upload");
                    files.add(file1);
                    jsonResponse.setFiles(files);
                    return jsonObjectConversionUtility.objectToJson(jsonResponse);
                }


            }

        }
        return "upload";
    }


    @RequestMapping(value = {"/reset"})
    public String resetLander(@RequestParam(value = "password", required = false) String password, Model model, HttpSession session) {
        UserLoginResponse response = new UserLoginResponse();

        if (null != password) {
            session.setAttribute("resetPassword", password);
        }
        model.addAttribute("response",response);
        return "reset";
    }

//    @RequestMapping(value = {"/reset"}, method = RequestMethod.POST)
//    public String generateReport(@Valid LoginRequest loginRequest, Model model, HttpSession session,
//                                 @RequestParam(value = "action", required = true) String action, @RequestParam(value = "username", required = false) String username, @RequestParam(value = "password", required = false) String password
//            , @RequestParam(value = "passwordConfirm", required = false) String passwordConfirm) throws Exception {
//
//        UserLoginResponse response = new UserLoginResponse();
//        GoldenRichesUsers goldenRichesUsers = new GoldenRichesUsers();
//
//
//        model.addAttribute("login", new LoginRequest());
//        String passwords = RandomStringUtils.randomAlphanumeric(10).toUpperCase();
//        try {
//            if (null == username || username.equals("")) {
//                response.setMessage("Username / EmailAddress Can't be Left Empty");
//                response.setStatusCode(Enums.StatusCodeEnum.EMPTYVALUE.getStatusCode());
//                return errorResponse(model, response);
//            }
//
//            goldenRichesUsers = goldenRichesUsersService.findUserByMemberId(username);
//            if (goldenRichesUsers.getEnabled() == 0) {
//                response.setMessage("Your Account Has Been De-Activated, Suspicious Activities Identified.");
//                response.setStatusCode(Enums.StatusCodeEnum.FORBIDDEN.getStatusCode());
//                return errorResponse(model, response);
//            }
//            model.addAttribute("profile", goldenRichesUsers);
//            session.setAttribute("profile", goldenRichesUsers);
//
//        } catch (GoldenRichesUsersNotFoundException exts) {
//
//            try {
//
//                goldenRichesUsers = goldenRichesUsersService.findUserByEmail(username);
//                if (goldenRichesUsers.getEnabled() == 0) {
//                    response.setMessage("You Can't Reset Your Password. Your Account Has Been De-Activated, Suspicious Activities Identified.");
//                    response.setStatusCode(Enums.StatusCodeEnum.FORBIDDEN.getStatusCode());
//                    return errorResponse(model, response);
//                }
//                goldenRichesUsers.setPassword(passwords);
//                goldenRichesUsersService.saveUser(goldenRichesUsers);
//                SendEmailMessages sendEmailMessages = new SendEmailMessages();
//                sendEmailMessages.sendMessage(goldenRichesUsers.getEmailAddress(), goldenRichesUsers.getPassword());
//                response.setMessage("A Link Was Successfully Sent to E-mail: " + goldenRichesUsers.getEmailAddress() + " to Reset Your Password ");
//                response.setStatusCode(Enums.StatusCodeEnum.OK.getStatusCode());
//                return "reset";
//            } catch (GoldenRichesUsersNotFoundException unf) {
//                response.setMessage("Provided Username / Email Does'nt Exist on the System");
//                response.setStatusCode(Enums.StatusCodeEnum.NOTAUTHORISED.getStatusCode());
//                return errorResponse(model, response);
//            }
//
//
//        }
//        goldenRichesUsers.setPassword(passwords);
//        goldenRichesUsersService.saveUser(goldenRichesUsers);
//        SendEmailMessages sendEmailMessages = new SendEmailMessages();
//        sendEmailMessages.sendMessage(goldenRichesUsers.getEmailAddress(), goldenRichesUsers.getPassword());
//        response.setMessage("A Link Was Successfully Sent to E-mail: " + goldenRichesUsers.getEmailAddress() + " to Reset Your Password ");
//        response.setStatusCode(Enums.StatusCodeEnum.OK.getStatusCode());
//
//        //getNotifications(model, goldenRichesUsers, session);
//        return "reset";
//    }

    private String errorResponse(Model model, UserLoginResponse response) {
        model.addAttribute("response", response);
        return "reset";
    }


}