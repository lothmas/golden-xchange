package com.iqa.controller;


import com.iqa.domain.countries.service.CountriesService;
import com.iqa.domain.derived.country.model.Country;
import com.iqa.institutes.exception.InstitutesNotFoundException;
import com.iqa.institutes.model.InstitutesEntity;
import com.iqa.institutes.service.InstitutesService;
import com.iqa.profile.individual.model.LoginRequest;
import com.iqa.profiles.exception.ProfilesNotFoundException;
import com.iqa.profiles.model.ProfileEntity;
import com.iqa.profiles.service.ProfilesService;
import com.iqa.utilities.Enums;
import com.iqa.utilities.GeneralDomainFunctions;
import com.iqa.verificationrequest.exception.VerificationRequestNotFoundException;
import com.iqa.verificationrequest.model.VerificationRequestEntity;
import com.iqa.verificationrequest.service.VerificationRequestService;
import com.iqa.verifiedcandidates.exception.VerifiedCandidatesNotFoundException;
import com.iqa.verifiedcandidates.model.CandidatesVerifiedEntity;
import com.iqa.verifiedcandidates.service.VerifiedCandidatesService;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.binary.StringUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.validator.routines.EmailValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartException;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.ConstraintViolationException;
import javax.validation.Valid;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringWriter;
import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static org.apache.commons.codec.binary.Base64.encodeBase64;


@ControllerAdvice
@Controller
public class RegistrationController {


    @Autowired
    ProfilesService profilesService;

    @Autowired
    CountriesService countriesService;

    @Autowired
    VerificationRequestService verificationRequestService;

    @Autowired
    InstitutesService institutesService;

    @Autowired
    VerifiedCandidatesService verifiedCandidatesService;

    @Autowired
    VerificationController verificationController;

    private static final Pattern EMAIL_PATTERN = Pattern.compile("^\\s*?(.+)@(.+?)\\s*$");
    private static final Pattern USER_PATTERN = Pattern.compile("^\\s*(((\\\\.)|[^\\s\\p{Cntrl}\\(\\)<>@,;:'\\\\\\\"\\.\\[\\]]|')+|(\"(\\\\\"|[^\"])*\"))(\\.(((\\\\.)|[^\\s\\p{Cntrl}\\(\\)<>@,;:'\\\\\\\"\\.\\[\\]]|')+|(\"(\\\\\"|[^\"])*\")))*$");

    public static String byteToString(byte[] _bytes) {
        String file_string = "";

        for (int i = 0; i < _bytes.length; i++) {
            file_string += (char) _bytes[i];
        }

        return file_string;
    }


    @RequestMapping(value = {"/register"})
    public String registerUser(HttpServletRequest request, Model model, HttpSession session,
                               @RequestParam(value = "userType", required = false) String userType
            , @RequestParam(value = "name", required = false) String name
            , @RequestParam(value = "username", required = false) String username
            , @RequestParam(value = "password", required = false) String password
            , @RequestParam(value = "firstName", required = false) String firstName
            , @RequestParam(value = "surname", required = false) String surname
            , @RequestParam(value = "phoneNumber", required = false) String phoneNumber
            , @RequestParam(value = "emailAddress", required = false) String emailAddress
            , @RequestParam(value = "emailNotification", required = false) String emailNotification
            , @RequestParam(value = "smsNotification", required = false) String smsNotification
            , @RequestParam(value = "idNumber", required = false) String idNumber
            , @RequestParam(value = "companyRegistration", required = false) String companyRegistration
            , @RequestParam(value = "countryId", required = false) String countryId
            , @RequestParam(value = "actions", required = false) String actions
            , @RequestParam(value = "pic", required = false) MultipartFile pic
            , @RequestParam(value = "diallingCode", required = false) String diallingCode) {


        model.addAttribute("userType", null);
        model.addAttribute("alertMessage", null);

        if(actions!=null){
            model.addAttribute("userType", session.getAttribute("userType"));

            if(null==session.getAttribute("userType") || session.getAttribute("userType").equals("1")){
                if(null==firstName){
                    userType=null;
                }
                else{
                    userType= String.valueOf(session.getAttribute("userType"));
                }
            }
            if(null==session.getAttribute("userType") || session.getAttribute("userType").equals("2")
                    ||session.getAttribute("userType").equals("3")
                    ||session.getAttribute("userType").equals("4")
                   ){
                if(null==name){
                    userType=null;
                }
                else{
                    userType= String.valueOf(session.getAttribute("userType"));
                }
            }

        }



        try {
            if(userType!=null) {
                if(countryId!=null) {




                    boolean correctEmail = EmailValidator.getInstance().isValid(emailAddress);
                    if(!correctEmail){
                        model.addAttribute("alertMessage", "........Please Enter Valid Email-Address");
                        model.addAttribute("countries", session.getAttribute("countries"));
                        return"register";
                    }
                    try{
                        if(phoneNumber.length()>14 ){
                            model.addAttribute("alertMessage", "........Please Enter Numbers Only for Phone-Number");
                            model.addAttribute("countries", session.getAttribute("countries"));
                            return"register";
                        }

                    }
                    catch (Exception exp){
                        model.addAttribute("alertMessage", "........Please Enter Numbers Only Maximum 10 for Phone-Number");
                        model.addAttribute("countries", session.getAttribute("countries"));
                        return"register";
                    }



                    if (profilesService.suppliedEmailExists(emailAddress)) {
                        model.addAttribute("alertMessage", "Provided Email-Address Already Exists ");
                        model.addAttribute("userType", userType);
                        session.setAttribute("userType",userType);
                        List<Country> countries = countriesService.getAllCountries();
                        model.addAttribute("countries", countries);
                        session.setAttribute("countries", countries);
                        return"register";
                    }
                    ProfileEntity usernameProfile = profilesService.findUserByMemberId(username);
                    if (usernameProfile != null) {
                        model.addAttribute("alertMessage", "Provided Username Already Exists ");
                    }
                }
                model.addAttribute("userType", userType);
                session.setAttribute("userType",userType);
                List<Country> countries = countriesService.getAllCountries();
                model.addAttribute("countries", countries);
                session.setAttribute("countries", countries);
            }
            else{
                List<Country> countries = countriesService.getAllCountries();
                model.addAttribute("countries", countries);
                model.addAttribute("alertMessage", "...... Select UserType ");
                return "register";
            }
            model.addAttribute("countries", session.getAttribute("countries"));
            return "register";
        } catch (ProfilesNotFoundException e) {
            if(null!=username && password!=null &&phoneNumber!=null && emailAddress!=null && emailNotification!=null) {

                ProfileEntity profileEntity = new ProfileEntity();
                profileEntity.setUsername(username);
                long time = System.currentTimeMillis();
                java.sql.Date date = new java.sql.Date(time);
                profileEntity.setCreationDate(date);
                profileEntity.setCountryId(Integer.parseInt(countryId));
                profileEntity.setStatus(1);
                profileEntity.setEnabled(1);
                profileEntity.setKyc(1);
                profileEntity.setBalanceAmount(0.00);
                profileEntity.setEmailAddress(emailAddress);
                if(diallingCode.isEmpty()){
                    diallingCode="263";
                }
                profileEntity.setPhoneNumber("+"+diallingCode+phoneNumber.replaceFirst ("^0*", ""));
                profileEntity.setUserType(userType);
                try {
                    profileEntity.setPicture(StringUtils.newStringUtf8(Base64.encodeBase64(pic.getBytes(), false)));

                } catch (IOException e1) {
                    model.addAttribute("alertMessage", "loaded profile picture not properly formed load a good picture");
                    return "register";
                }

                profileEntity.setEmailNotification(1);
                profileEntity.setMobileNotification(0);

                try {
                    profileEntity.setPassword(GeneralDomainFunctions.getCryptedPasswordAndSalt(password));
                } catch (Exception e1) {
                    model.addAttribute("alertMessage", "Something Nusty went Wrong with your Password");
                }

                if (userType != null) {
                    model.addAttribute("userType", userType);
                    session.setAttribute("userType", userType);
                    if (userType.equalsIgnoreCase("1")) {
                        profileEntity.setName(firstName + surname);
                    }
                    else {
                        if(null==name){
                            model.addAttribute("alertMessage", "........Please Reselect UserType");
                            model.addAttribute("countries", session.getAttribute("countries"));
                            return"register";
                        }
                        profileEntity.setName(name);
                    }
                }

                try {
                    profilesService.saveUser(profileEntity);
                } catch (MultipartException e1) {
                    model.addAttribute("countries", session.getAttribute("countries"));
                    model.addAttribute("alertMessage", "your image exceedes more than 1mb ");
                    return "register";
                }
                catch (ConstraintViolationException con){
                    model.addAttribute("countries", session.getAttribute("countries"));
                    model.addAttribute("alertMessage", "please limit you profile image to 16MB");
                    return "register";
                }
                catch (Exception e1) {
                    model.addAttribute("countries", session.getAttribute("countries"));
                    model.addAttribute("alertMessage", "something went wrong please contact E-Verify with error: ");
                    return "register";
                }
                model.addAttribute("alertMessage", "your account was successfullyy created you can login");

                RedirectAttributes redirectAttributes = null;
                return verificationController.login( model, session, username,  password, redirectAttributes);
                }else{
                model.addAttribute("alertMessage", "please provide all required details");               
            }
            List<Country> countries = countriesService.getAllCountries();
            model.addAttribute("countries", countries);
            session.setAttribute("countries", countries);
            return "register";
        }
    }





}