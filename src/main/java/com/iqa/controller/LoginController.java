package com.iqa.controller;


import com.iqa.generated.qualifications.Qualifications;
import com.iqa.generated.qualifications.Shares;
import com.iqa.generated.qualifications.Skills;
import com.iqa.institutes.exception.InstitutesNotFoundException;
import com.iqa.institutes.model.InstitutesEntity;
import com.iqa.institutes.service.InstitutesService;
import com.iqa.profile.individual.endpoint.IndividualLoginEndPoint;
import com.iqa.profile.individual.exception.IndividualProfileNotFoundException;
import com.iqa.profile.individual.model.IndividualProfileEntity;
import com.iqa.profile.individual.model.LoginRequest;
import com.iqa.profile.individual.service.IndividualProfileService;
import com.iqa.qualifications.exception.QualificationsNotFoundException;
import com.iqa.qualifications.model.QualificationsEntity;
import com.iqa.qualifications.service.QualificationsService;
import com.iqa.utilities.Enums;
import com.iqa.utilities.GenericResponse;
import com.iqa.utilities.MarshallerUnMarshaller;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.lang.reflect.Array;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;
import org.jdom2.output.Format;
import org.jdom2.output.XMLOutputter;


@ControllerAdvice
@Controller
public class LoginController {

    MarshallerUnMarshaller marshallerUnMarshaller = new MarshallerUnMarshaller();

    @Autowired
    IndividualLoginEndPoint individualLoginEndPoint;

    @Autowired
    IndividualLoginEndPoint accountHolderLoginEndPoint;

    @Autowired
    IndividualProfileService individualProfileService;

    @Autowired
    InstitutesService institutesService;

    @Autowired
    IndividualProfileService adminProfileService;

    @Autowired
    GenericResponse genericResponse;

    @Autowired
    GenericResponse accountHolderGenericResponse;

    @Autowired
    QualificationsService qualificationsService;

    @Autowired
    List<Qualifications.Qualification> qualificationsList;

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


    @RequestMapping({"/dashboard", "/index", "/contact_us", "/blank", "/extra_profile", "/about_us",  "/authorization", "/qualifications_national", "/switch_to_master", "/edit_profile", "/search_add", "/edit_qualifications", "/selected_qualification","verify"})
    public String getPage(HttpServletRequest request, Model model, HttpSession session,
                          @RequestParam(value = "username", required = false) String username, @RequestParam(value = "searchText", required = false) String searchText
    ) throws InstitutesNotFoundException {
        LoginRequest loginRequest = new LoginRequest();
        String url = request.getRequestURI();
        int index = url.lastIndexOf("/");
        if (index != -1) {
            if (url.contains("authorization") || url.contains("/qualifications_national") || url.contains("extra_profile") || url.contains("/switch_to_master")
                    || url.contains("/edit_profile") || url.contains("/edit_qualifications") || url.contains("/verify")) {
                loginRequest.setUsername(username);
                if (url.contains("/switch_to_master")) {
                    loginRequest.setUsername(session.getAttribute("accountHolderUsername").toString());
                }

                if (url.contains("/verify")) {
                    model.addAttribute("AllInstitutes", institutesService.getAllInstitutes());
                }

                if (!session.getAttribute("accountHolderUsername").equals(username)) {
                    try {
                        loginProcessor(loginRequest, model);
                    } catch (IndividualProfileNotFoundException e) {
                        e.printStackTrace();
                    } catch (NoSuchAlgorithmException e) {
                        e.printStackTrace();
                    }
                }
                model.addAttribute("individualProfile", genericResponse.getIndividualProfileEntity());
                model.addAttribute("picture", (genericResponse.getIndividualProfileEntity().getPicture()));
                model.addAttribute("qualificationsAttained", qualificationsList);
                model.addAttribute("numberOfQualifications", qualificationsList.size());
                processProfileAuthorization(model);
                processSkills(model);
                setAccountHolder(model, session);

                if (url.contains("/edit_qualifications")) {
                    model.addAttribute("selectedQualification", new Qualifications.Qualification());
                    model.addAttribute("selectedQualificationSelected", "Select Existing Qualification to Add / Edit On Existing Results");
                    model.addAttribute("selectedModuleSelected", "Select Module");
                    try {
                        model.addAttribute("institutes", institutesService.findInstitutesById(accountHolderGenericResponse.getIndividualProfileEntity().getInstitute()));
                    } catch (InstitutesNotFoundException e) {
                        e.printStackTrace();
                    }
                }


            }

            if (url.contains("/switch_to_master")) {
                return "extra_profile";
            } else if (url.contains("/search_add")) {
                if (null != searchText) {

                    if (null != username) {
                        addViewer(genericResponse.getIndividualProfileEntity(), username);
                        loginRequest.setUsername(session.getAttribute("accountHolderUsername").toString());
                        try {
                            loginProcessor(loginRequest, model);
                        } catch (IndividualProfileNotFoundException e) {
                            e.printStackTrace();
                        } catch (NoSuchAlgorithmException e) {
                            e.printStackTrace();
                        }
                        setAccountHolder(model, session);
                        return "authorization";

                    } else {
                        try {
                            model.addAttribute("foundAccounts", individualProfileService.searchForRegisteredAccounts(searchText));
                        } catch (IndividualProfileNotFoundException e) {
                            e.printStackTrace();
                        }
                    }
                }
                reLoadAuthorities(model, session, loginRequest);
                model.addAttribute("search_add", "");

                return "search_add";
            } else {
                return url.substring(index + 1, url.length());

            }


        }
        return url;
    }

    @RequestMapping(value = {"/selected_qualification"}, method = RequestMethod.POST)
    public String getQualifications(@Valid Qualifications.Qualification qualificationRequest, Model model, HttpSession session,
                                    @RequestParam(value = "selectedQualification", required = false) Integer selectedQualification, @RequestParam(value = "selectedModule", required = false) Integer selectedModule, @RequestParam(value = "username", required = false) String username) throws Exception {
        if(null==selectedModule){
            session.setAttribute("usernameForEditedQualification", username);
        }

        LoginRequest loginRequest = new LoginRequest();
        StringBuffer gradedPrint = new StringBuffer();
        String gradesForModules = null;


        if (null != selectedQualification) {
            session.setAttribute("selectedQualification", selectedQualification);
            model.addAttribute("selectedQualificationSelected", "Select Existing Qualification to Add / Edit On Existing Results");
            model.addAttribute("selectedModuleSelected", "Select Module");
        } else {
            selectedQualification = (Integer) session.getAttribute("selectedQualification");
        }
        loginRequest.setUsername(username);
        loginProcessor(loginRequest, model);
        setAccountHolder(model, session);

        if (null != selectedQualification) {
            Qualifications.Qualification qualificationsLists = qualificationsList.get(selectedQualification);
            session.setAttribute("selectedQualification",selectedQualification);
            model.addAttribute("selectedQualification", qualificationsLists);
            model.addAttribute("selectedQualificationSelected", qualificationsLists.getProgramme());


            InstitutesEntity institutesEntity = institutesService.findInstitutesById(accountHolderGenericResponse.getIndividualProfileEntity().getInstitute());
            model.addAttribute("institutes", institutesEntity);

            List<Qualifications.Qualification.Module> modules = qualificationsList.get(selectedQualification).getModule();


            model.addAttribute("modules", modules);
            int count = 0;
            if (null != modules) {
//                    gradedPrint.append("[");

                if (null != selectedModule) {
                    //   int getSelectedModule = (selectedModule - 1);
                    session.setAttribute("selectedModule",selectedModule);
                    for (Qualifications.Qualification.Module ass : modules) {

                        List<Qualifications.Qualification.Module.Grades> grades = ass.getGrades();
                        if (count == selectedModule - 1) {
                            for (Qualifications.Qualification.Module.Grades grade : grades) {

                                model.addAttribute("selectedModuleSelected", ass.getLevel() + " " + ass.getPeriod());

                                gradedPrint.append("[\"" + grade.getModuleCode() + "\",\"" + grade.getSubject() + "\",\"" + grade.getGrade() + "\"],");
                            }

                        }
                        count++;
                    }
                    StringUtils.removeEnd(gradedPrint.toString(), ",");
                    gradesForModules = StringUtils.removeEnd(gradedPrint.toString(), ",") + "";
                }
            }

        } else {
            model.addAttribute("selectedQualification", new Qualifications.Qualification());
            model.addAttribute("institutes", new InstitutesEntity());

        }
        model.addAttribute("gradesForModules", gradesForModules);
        session.setAttribute("gradesForModules",gradesForModules);

        return "edit_qualifications";
    }


    @RequestMapping(value = "/selected", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody
    String Submit(@RequestParam("read") String name) {
        // your logic here
        String te = "";
        return null;
    }

    private void reLoadAuthorities(Model model, HttpSession session, LoginRequest loginRequest) {
        loginRequest.setUsername(session.getAttribute("accountHolderUsername").toString());
        processSkills(model);
        model.addAttribute("individualProfile", genericResponse.getIndividualProfileEntity());
        model.addAttribute("picture", (genericResponse.getIndividualProfileEntity().getPicture()));
        model.addAttribute("qualificationsAttained", qualificationsList);
        model.addAttribute("numberOfQualifications", qualificationsList.size());
        setAccountHolder(model, session);
    }

//    @RequestMapping({"/search"})
//    public String searchForUsers(HttpServletRequest request, Model model, HttpSession session, @RequestParam(value = "username", required = false) String username) {
//        LoginRequest loginRequest = new LoginRequest();
//        String url = request.getRequestURI();
//        return "search_add"
//    }


    @RequestMapping({"/email_compose"})
    public String composeEmail(HttpServletRequest request, Model model, HttpSession session, @RequestParam(value = "username", required = false) String username
    ) {
        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setUsername(session.getAttribute("accountHolderUsername").toString());
        String url = request.getRequestURI();
        int index = url.lastIndexOf("/");

        try {
            model.addAttribute("emailTo", individualProfileService.findUserEmail(username));
            loginProcessor(loginRequest, model);
            setAccountHolder(model, session);
            return "email_compose";
        } catch (IndividualProfileNotFoundException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return "login";
    }

    @RequestMapping({"/sendEmail"})
    public String sendMessage(HttpServletRequest request, Model model, HttpSession session, @RequestParam(value = "username", required = false) String username,
                              @RequestParam(value = "emailTo", required = false) String emailTo) {
        LoginRequest loginRequest = new LoginRequest();
        String url = request.getRequestURI();
        int index = url.lastIndexOf("/");

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

    @RequestMapping({"/login"})
    public String loadPage(Model model) {
        model.addAttribute("login", new LoginRequest());
        return "login";
    }

    @RequestMapping({"/switch_user"})
    public String switchPage(Model model) {
        model.addAttribute("username", new LoginRequest());
        return "login";
    }


    @RequestMapping(value = {"/login"}, method = RequestMethod.POST)
    public String generateReport(@Valid LoginRequest loginRequest, Model model, HttpSession session,
                                 @RequestParam(value = "action", required = true) String action, @RequestParam(value = "username", required = false) String username, @RequestParam(value = "nodePosition", required = false) Integer nodePosition) throws Exception {

        model.addAttribute("login", new LoginRequest());
        if (action.equals("login")) {
            String retunedPage = loginProcessor(loginRequest, model);
            session.setAttribute("accountHolderUsername", loginRequest.getUsername());
            session.setAttribute("accountHolderPassword", loginRequest.getPassword());
            session.setAttribute("accountHolderUserType", loginRequest.getUser_type());
            setAccountHolder(model, session);
            return retunedPage;
        } else if (action.equals("remove_viewer")) {
            loginRequest.setUsername(session.getAttribute("accountHolderUsername").toString());
            deleteViewer(genericResponse.getIndividualProfileEntity(), nodePosition);
            loginProcessor(loginRequest, model);
            setAccountHolder(model, session);
            return "authorization";


        } else if (action.equals("switch_user")) {
            String retunedPage = loginProcessor(loginRequest, model);
            setAccountHolder(model, session);
            return retunedPage;
        } else if (action.equals("forget_password")) {

        }
        return "login";
    }





    public String loginProcessor(LoginRequest loginRequest, Model model) throws IndividualProfileNotFoundException, NoSuchAlgorithmException {
        if (loginRequest.getUser_type() == 0) {
            individualLoginEndPoint.handleIndividualLogin(loginRequest);
            if (genericResponse.getStatusCode() == Enums.StatusCodeEnum.OK.getStatusCode()) {
                model.addAttribute("individualProfile", genericResponse.getIndividualProfileEntity());
                model.addAttribute("picture", (genericResponse.getIndividualProfileEntity().getPicture()));

                if (processQualifications(model)) return "verify";
                processProfileAuthorization(model);
                processSkills(model);
                return "verify";

            } else {
                model.addAttribute("errorMessage", genericResponse.getMessage());
                return "login";
            }

        }
        return null;
    }

    private void processSkills(Model model) {
        if (null != genericResponse.getIndividualProfileEntity().getSkills() && !genericResponse.getIndividualProfileEntity().getSkills().isEmpty()) {
            List<Skills.Skill> skillList = marshallerUnMarshaller.xmlToObjectSkills(genericResponse.getIndividualProfileEntity().getSkills());
            model.addAttribute("numberOfSkills", skillList.size());

            for (int i = 0; i < 6; i++) {
                try {
                    model.addAttribute("proficiency" + i, skillList.get(i).getProficiency());
                    model.addAttribute("areOfSpecialization" + i, skillList.get(i).getAreOfSpecialization());
                } catch (ArrayIndexOutOfBoundsException aeoub) {
                    model.addAttribute("proficiency" + i, "0");
                    model.addAttribute("areOfSpecialization" + i, null);
                }
            }

        }
    }

    private void processProfileAuthorization(Model model) {
        if (null != genericResponse.getIndividualProfileEntity().getShares() && !genericResponse.getIndividualProfileEntity().getShares().isEmpty()) {
            Shares shares = marshallerUnMarshaller.xmlToObjectShares(genericResponse.getIndividualProfileEntity().getShares());
            model.addAttribute("shares", setSharesProfilePic(shares, model));
        }
    }

    private boolean processQualifications(Model model) {
        String loggedInUser = genericResponse.getIndividualProfileEntity().getUsername();
        QualificationsEntity qualificationsEntity = new QualificationsEntity();

        try {
            qualificationsEntity = qualificationsService.findQualificationsByUsername(loggedInUser);
            qualificationsList = marshallerUnMarshaller.xmlToObjectQualifications(qualificationsEntity.getQualifications());
            model.addAttribute("qualificationsAttained", qualificationsList);
            model.addAttribute("numberOfQualifications", qualificationsList.size());

        } catch (QualificationsNotFoundException qnf) {
            return false;
        }
        return false;
    }

    public Shares setSharesProfilePic(Shares sharesWithoutProfilrPic, Model model) {
        Shares sharesWithPic = new Shares();
        List<Shares.ShareTo> sharesTo = sharesWithoutProfilrPic.getShareTo();
        List<Shares.ShareFrom> sharesFrom = sharesWithoutProfilrPic.getShareFrom();
        model.addAttribute("numberOfSharesTo", sharesTo.size());
        model.addAttribute("numberOfSharesFrom", sharesFrom.size());

        for (Shares.ShareTo shareTo : sharesTo) {
            try {
                IndividualProfileEntity individualProfileEntity = new IndividualProfileEntity();
                individualProfileEntity = individualProfileService.findUserByMemberId(shareTo.getUsername());
                shareTo.setProfilePic(individualProfileEntity.getPicture());
                shareTo.setCountry(individualProfileEntity.getCountryOfResidence());
                shareTo.setName(individualProfileEntity.getFirstName() + " . " + individualProfileEntity.getSurname());
                shareTo.setAbout(individualProfileEntity.getAbout());
            } catch (IndividualProfileNotFoundException e) {
                e.printStackTrace();
            }
        }

        for (Shares.ShareFrom shareFrom : sharesFrom) {
            try {
                IndividualProfileEntity individualProfileEntity = new IndividualProfileEntity();
                individualProfileEntity = individualProfileService.findUserByMemberId(shareFrom.getUsername());
                shareFrom.setProfilePic(individualProfileEntity.getPicture());
                shareFrom.setCountry(individualProfileEntity.getCountryOfResidence());
                shareFrom.setName(individualProfileEntity.getFirstName() + " . " + individualProfileEntity.getSurname());
                shareFrom.setAbout(individualProfileEntity.getAbout());
            } catch (IndividualProfileNotFoundException e) {
                e.printStackTrace();
            }
        }

        sharesWithPic.setShareTo(sharesTo);
        sharesWithPic.setShareFrom(sharesFrom);

        return sharesWithPic;

    }


    private void setAccountHolder(Model model, HttpSession session) {

        LoginRequest request = new LoginRequest();
        request.setUsername(session.getAttribute("accountHolderUsername").toString());
        request.setPassword(session.getAttribute("accountHolderPassword").toString());
        request.setUser_type(Integer.valueOf(session.getAttribute("accountHolderUserType").toString()));
        model.addAttribute("accountHolderUsername", session.getAttribute("accountHolderUsername"));
        model.addAttribute("accountHolderPassword", session.getAttribute("accountHolderPassword"));
        model.addAttribute("accountHolderUserType", session.getAttribute("accountHolderUserType"));

        try {
            accountHolderLoginEndPoint.handleIndividualLogin(request);
            if (accountHolderGenericResponse.getStatusCode() == Enums.StatusCodeEnum.OK.getStatusCode()) {
                model.addAttribute("adminProfile", accountHolderGenericResponse.getIndividualProfileEntity());
                model.addAttribute("adminPicture", (accountHolderGenericResponse.getIndividualProfileEntity().getPicture()));
            }

            if (null != accountHolderGenericResponse.getIndividualProfileEntity().getShares()) {
                Shares sharesWithoutProfilrPic = marshallerUnMarshaller.xmlToObjectShares(accountHolderGenericResponse.getIndividualProfileEntity().getShares());

                Shares sharesWithPic = new Shares();
                List<Shares.ShareFrom> sharesFrom = sharesWithoutProfilrPic.getShareFrom();


                for (Shares.ShareFrom shareFrom : sharesFrom) {
                    try {
                        IndividualProfileEntity adminProfileEntity = new IndividualProfileEntity();
                        adminProfileEntity = adminProfileService.findUserByMemberId(shareFrom.getUsername());
                        shareFrom.setProfilePic(adminProfileEntity.getPicture());
                        shareFrom.setCountry(adminProfileEntity.getCountryOfResidence());
                        shareFrom.setName(adminProfileEntity.getFirstName() + " . " + adminProfileEntity.getSurname());
                        shareFrom.setAbout(adminProfileEntity.getAbout());
                    } catch (IndividualProfileNotFoundException e) {
                        e.printStackTrace();
                    }
                }

                sharesWithPic.setShareFrom(sharesFrom);
                model.addAttribute("userAuthorisedAccounts", setSharesProfilePic(sharesWithPic, model));
            } else {
                model.addAttribute("userAuthorisedAccounts", "");
            }

        } catch (IndividualProfileNotFoundException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
    }


    public boolean deleteViewer(IndividualProfileEntity profileEntity, int nodeToDelete) {
        String OppositeAccountToDeleteFrom;
        SAXBuilder builder = new SAXBuilder();
        try {
            Document document = builder.build(
                    new ByteArrayInputStream(profileEntity.getShares().getBytes()));

            List<Element> children = document.getRootElement().getChildren("shareTo");

            if (children.get(nodeToDelete).getName().equals("shareTo")) {
                // Remove the fourth element.
                //  children.remove(nodeToDelete);
                OppositeAccountToDeleteFrom = children.get(nodeToDelete).getValue().trim();
                children.remove(nodeToDelete);
            } else {
                return false;
            }

            XMLOutputter outputter =
                    new XMLOutputter(Format.getPrettyFormat());
            profileEntity.setShares(outputter.outputString(document));
            individualProfileService.saveUser(profileEntity);
            return deleteOnAuthorisedViewer(profileEntity.getUsername(), OppositeAccountToDeleteFrom);
        } catch (JDOMException | IOException e) {
            e.printStackTrace();
        }

        return true;
    }

    public boolean addViewer(IndividualProfileEntity profileEntity, String usernameToApprove) {

        SAXBuilder builder = new SAXBuilder();
        try {
            Document document = builder.build(
                    new ByteArrayInputStream(profileEntity.getShares().getBytes()));

            List<Element> children = document.getRootElement().getChildren("shareTo");
            for (Element childElement : children) {
                String usernameApproved = childElement.getChildren().get(0).getValue();
                if (usernameApproved.equals(usernameToApprove)) {
                    return false;
                }
            }


            children.add(0, new Element("shareTo")
                    .setContent(new Element("username").setText(usernameToApprove)));
            //.setAttribute("username", usernameToApprove));

            XMLOutputter outputter =
                    new XMLOutputter(Format.getPrettyFormat());
            profileEntity.setShares(outputter.outputString(document));
            individualProfileService.saveUser(profileEntity);
            return addOnAuthorisedViewer(profileEntity.getUsername(), usernameToApprove);
        } catch (JDOMException | IOException e) {
            e.printStackTrace();
        }

        return false;
    }


    public boolean addOnAuthorisedViewer(String fromUsername, String username) {
        try {
            IndividualProfileEntity individualProfileEntity = individualProfileService.findUserByMemberId(username);
            SAXBuilder builder = new SAXBuilder();
            try {
                Document document = builder.build(
                        new ByteArrayInputStream(individualProfileEntity.getShares().getBytes()));

                List<Element> children = document.getRootElement().getChildren("shareFrom");
                for (Element childElement : children) {
                    String usernameApproved = childElement.getChildren().get(0).getValue();
                    if (usernameApproved.equals(individualProfileEntity)) {
                        return false;
                    }
                }


                children.add(0, new Element("shareFrom")
                        .setContent(new Element("username").setText(fromUsername)));
                //.setAttribute("username", usernameToApprove));

                XMLOutputter outputter =
                        new XMLOutputter(Format.getPrettyFormat());
                individualProfileEntity.setShares(outputter.outputString(document));
                individualProfileService.saveUser(individualProfileEntity);
                return true;
            } catch (JDOMException | IOException e) {
                e.printStackTrace();
            }
        } catch (IndividualProfileNotFoundException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean deleteOnAuthorisedViewer(String usernameToDelete, String usernameAccount) {
        int count = 0;
        boolean delete = false;
        SAXBuilder builder = new SAXBuilder();
        try {
            IndividualProfileEntity individualProfileEntity = individualProfileService.findUserByMemberId(usernameAccount);

            Document document = builder.build(
                    new ByteArrayInputStream(individualProfileEntity.getShares().getBytes()));

            List<Element> children = document.getRootElement().getChildren("shareFrom");
            for (Element childElement : children) {
                String usernameApproved = childElement.getChildren().get(0).getValue();
                if (usernameApproved.equals(usernameToDelete)) {
                    count = children.indexOf(childElement);
                    delete = true;

                }
            }

            // Remove the fourth element.
            if (delete) {
                children.remove(count);
            }


            XMLOutputter outputter =
                    new XMLOutputter(Format.getPrettyFormat());
            individualProfileEntity.setShares(outputter.outputString(document));
            individualProfileService.saveUser(individualProfileEntity);
            return true;
        } catch (JDOMException | IOException e) {
            e.printStackTrace();
        } catch (IndividualProfileNotFoundException e) {
            e.printStackTrace();
        }

        return delete;
    }

    @RequestMapping({"/verification"})
    public String verification(HttpServletRequest request, Model model, HttpSession session,
                          @RequestParam(value = "username", required = false) String username, @RequestParam(value = "searchText", required = false) String searchText
    ) {
        //individualProfileService.findIndividualProfileByEmailAndPassword();
        return "verify";
    }
}