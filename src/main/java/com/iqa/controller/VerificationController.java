package com.iqa.controller;


import com.iqa.domain.countries.exception.CountryNotFoundException;
import com.iqa.domain.countries.service.CountriesService;
import com.iqa.domain.derived.country.model.Country;
import com.iqa.institutes.exception.InstitutesNotFoundException;
import com.iqa.institutes.model.InstitutesEntity;
import com.iqa.institutes.service.InstitutesService;
import com.iqa.profile.individual.model.LoginRequest;
import com.iqa.profiles.exception.ProfilesNotFoundException;
import com.iqa.profiles.model.ProfileEntity;
import com.iqa.profiles.service.ProfilesService;
import com.iqa.utilities.JsonObjectConversionUtility;
import com.iqa.verificationrequest.exception.VerificationRequestNotFoundException;
import com.iqa.verificationrequest.model.VerificationRequestEntity;
import com.iqa.verificationrequest.service.VerificationRequestService;
import com.iqa.verifiedcandidates.exception.VerifiedCandidatesNotFoundException;
import com.iqa.verifiedcandidates.model.CandidatesVerifiedEntity;
import com.iqa.verifiedcandidates.service.VerifiedCandidatesService;
import com.opencsv.CSVReader;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.*;
import java.security.NoSuchAlgorithmException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.ZonedDateTime;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.TimeUnit;


@ControllerAdvice
@Controller
public class VerificationController {


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


    @RequestMapping({"/verification_login", "/profile"})
    public String loginVerification(HttpServletRequest request, LoginRequest loginRequest, Model model, HttpSession session,
                                    @RequestParam(value = "username", required = false) String username, @RequestParam(value = "searchText", required = false) String searchText
            , @RequestParam(value = "password", required = false) String password, final RedirectAttributes redirectAttributes) {
        ProfileEntity profileEntity = null;
        String url = request.getRequestURI();
        int index = url.lastIndexOf("/");
        if (index != -1) {
            if (url.contains("profile")) {
                ProfileEntity existing = new ProfileEntity();
                try {
                    existing = (ProfileEntity) session.getAttribute("profile");
                    profileEntity = profilesService.findUserByUserId(existing.getId());
                } catch (ProfilesNotFoundException e) {
                    e.printStackTrace();
                }
                model.addAttribute("profile", profileEntity);
                model.addAttribute("alertMessage", null);
                ProfileEntity profileEntity1 = (ProfileEntity) session.getAttribute("profile");
                try {
                    model.addAttribute("countryName", countriesService.findByCountryId(profileEntity1.getCountryId()).getName());
                } catch (CountryNotFoundException e) {
                    e.printStackTrace();
                }
                return "profile";
            } else if (url.contains("verification_login")) {
                String x = login(model, session, username, password, redirectAttributes);
                if (x != null) return x;
            }


        }

        return "profile";
    }

    public String login(Model model, HttpSession session, @RequestParam(value = "username", required = false) String username, @RequestParam(value = "password", required = false) String password, RedirectAttributes redirectAttributes) {
        ProfileEntity profileEntity;
        try {
            profileEntity = profilesService.findProfileByUsernameAndPassword(username, password);
            model.addAttribute("countryName", countriesService.findByCountryId(profileEntity.getCountryId()).getName());
            model.addAttribute("profile", profileEntity);
            session.setAttribute("profile", profileEntity);
            model.addAttribute("alertMessage", null);

            return "profile";
        } catch (ProfilesNotFoundException | NoSuchAlgorithmException e) {
            redirectAttributes.addFlashAttribute("alertMessage", "Wrong Login Credentials Please Provide Correct Credentials");
            model.addAttribute("alertMessage", "Wrong Login Credentials Please Provide Correct Credentials");
            return "redirect:login";
        } catch (CountryNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    @RequestMapping(value = {"/verification"}, method = RequestMethod.GET)
    public String verification(HttpServletRequest request, Model model, HttpSession session, @RequestParam(value = "countryId", required = false) String countryId, @RequestParam(value = "checkOutValue", required = false) Double checkOutValue) {
        String url = request.getRequestURI();
        int index = url.lastIndexOf("/");
        model.addAttribute("option",1);
        if (index != -1) {
            if (url.contains("verification")) {
                try {

                    List<Country> countries = countriesService.getAllCountries();
                    model.addAttribute("countries", countries);
                    session.setAttribute("countries", countries);
                    model.addAttribute("institutes", null);
                    model.addAttribute("profile", session.getAttribute("profile"));
                    model.addAttribute("searchButton", true);
                    model.addAttribute("selectedCountryName", null);
                    model.addAttribute("errorMessage", null);

                    return "verification";
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }

        return "profile";
    }

    @RequestMapping(value = {"/getInstitutions"}, method = RequestMethod.POST)
    public String getInstuteByCountryId(HttpServletRequest request, Model model, HttpSession session, @RequestParam(value = "countryId", required = false) String countryId
            , @RequestParam(value = "instituteId", required = false) String instituteId, @RequestParam(value = "candidateId", required = false) String candidateId
            , @RequestParam(value = "action", required = false) String action, @RequestParam(value = "option", required = false) String option) {

        model.addAttribute("option",Integer.valueOf(option));
        String url = request.getRequestURI();
        int index = url.lastIndexOf("/");
        model.addAttribute("errorMessage", null);

        if (index != -1) {
            model.addAttribute("profile", session.getAttribute("profile"));
            model.addAttribute("countries", session.getAttribute("countries"));
            model.addAttribute("searchButton", false);
            if (url.contains("getInstitutions") && candidateId == null && instituteId == null) {

                if(option.equals("2")){
                    model.addAttribute("searchButton", false);
                    try {
                        List<InstitutesEntity> institutesEntities = institutesService.findInstitutesByUserType(2);
                        model.addAttribute("institutes", institutesEntities);
                        session.setAttribute("institute",institutesEntities);
                    } catch (InstitutesNotFoundException e) {
                        model.addAttribute("institutes", null);
                        model.addAttribute("searchButton", true);
                        model.addAttribute("errorMessage", "No International Professional Institution Found");
                    }

                }else if(null!=countryId){

                String[] spiltRequest = countryId.split(",");
                try {
                    List<InstitutesEntity> institutesEntities = institutesService.getInstitutesByCountry(Integer.parseInt(spiltRequest[0]));
                    model.addAttribute("institutes", institutesEntities);
                } catch (Exception e) {
                    model.addAttribute("institutes", null);
                    model.addAttribute("searchButton", true);
                    model.addAttribute("errorMessage", "No Institute Available for Selected Country");
                }


                model.addAttribute("selectedCountryName", spiltRequest[1]);
                session.setAttribute("selectedCountryName", spiltRequest[1]);
                }

                return "verification";
            } else if (url.contains("getInstitutions") && candidateId != null && instituteId != null && !candidateId.isEmpty()) {
                verificationRequest(instituteId, candidateId, model, session);
              if(option.equals("2")){
                  try {
                      model.addAttribute("countryName", countriesService.findByCountryId(55555).getName());
                      model.addAttribute("searchButton", false);
                      model.addAttribute("institutes", session.getAttribute("institutes"));
                  } catch (CountryNotFoundException e) {
                      e.printStackTrace();
                  }

              }
                model.addAttribute("selectedCountryName", session.getAttribute("selectedCountryName"));
                return "verification";
            } else {
                if(option.equals("2")){
                    model.addAttribute("searchButton", false);
                    model.addAttribute("errorMessage", "Please provide both institute and candidate number");

                }
                else{
                    model.addAttribute("searchButton", true);
                    model.addAttribute("errorMessage", "Please reselect your desired country or institute and provide candidate number");

                }
                model.addAttribute("institutes", null);
            }
        }
        return "verification";
    }


    public void verificationRequest(String instituteId, String candidateId, Model model, HttpSession session) {
        ProfileEntity profiles = (ProfileEntity) session.getAttribute("profile");

        try {
            InstitutesEntity institutesEntity = institutesService.findInstitutesById(Integer.parseInt(instituteId));
            model.addAttribute("selectedInstitute", institutesEntity);

            List<Country> selectedCountry = (List<Country>) session.getAttribute("countries");

            for (Country countries : selectedCountry) {
                if (countries.getCountryId() == institutesEntity.getCountry()) {
                    model.addAttribute("selectedCountry", countries);
                    break;
                } else {
                    model.addAttribute("selectedCountry", null);
                }
            }


            List<CandidatesVerifiedEntity> verifiedCandidate = verifiedCandidatesService.getVerifiedCandidatesByCandidateIdAndInstituteId(Integer.parseInt(instituteId), candidateId);


            long diffInMillies = Math.abs(new Date().getTime() - verifiedCandidate.get(0).getUpdateDate().getTime());
            long diff = TimeUnit.DAYS.convert(diffInMillies, TimeUnit.MILLISECONDS);

            if (diff > 31) {
                throw new VerifiedCandidatesNotFoundException("");
            }

            model.addAttribute("verifiedCandidate", verifiedCandidate);


            model.addAttribute("authenticationStatus", "1");

        } catch (VerifiedCandidatesNotFoundException e) {
            try {

                CandidatesVerifiedEntity candidatesVerifiedEntity = new CandidatesVerifiedEntity();
                VerificationRequestEntity verificationRequestEntity = verificationRequestService.VerificationRequestCandidateIdAndInstituteIdAndUserId(candidateId, Integer.parseInt(instituteId), profiles.getId());
                candidatesVerifiedEntity.setCandidateNumber(verificationRequestEntity.getCandidateNumber());
                candidatesVerifiedEntity.setProgram("");
                candidatesVerifiedEntity.setFirstName("");
                candidatesVerifiedEntity.setSurname("");
                candidatesVerifiedEntity.setCertificateNumber("");
                candidatesVerifiedEntity.setDateAwarded(null);

                model.addAttribute("initialRequestDate", verificationRequestEntity.getRequestDate());
                model.addAttribute("verifiedCandidate", candidatesVerifiedEntity);
                model.addAttribute("authenticationStatus", 1);
            } catch (VerificationRequestNotFoundException e1) {

                ProfileEntity profileEntity = (ProfileEntity) session.getAttribute("profile");
                ProfileEntity profileEntity1 = null;
                try {
                    profileEntity1 = profilesService.findUserByUserId(profileEntity.getId());
                } catch (ProfilesNotFoundException e2) {
                    e2.printStackTrace();
                }
                if (null == profileEntity1.getBalanceAmount() || (profileEntity1.getUserType().equals("1") && profileEntity1.getBalanceAmount() < 5)
                        || (profileEntity1.getUserType().equals("2") && profileEntity1.getBalanceAmount() < 10)
                        || (profileEntity1.getUserType().equals("3") && profileEntity1.getBalanceAmount() < 15)
                        || (profileEntity1.getUserType().equals("4") && profileEntity1.getBalanceAmount() < 20)) {
                    model.addAttribute("errorMessage", "You Can't Make An Authentication Request: Please Top-Up Your Account First");
                } else {

                    VerificationRequestEntity verificationRequestEntity = new VerificationRequestEntity();
                    verificationRequestEntity.setCandidateNumber(candidateId);
                    verificationRequestEntity.setEnabled(1);
                    verificationRequestEntity.setInstitute(Integer.parseInt(instituteId));
                    verificationRequestEntity.setProcessStatus(1);
                    long time = System.currentTimeMillis();
                    java.sql.Date date = new java.sql.Date(time);
                    verificationRequestEntity.setRequestDate(date);
                    verificationRequestEntity.setRequesterId(profiles.getId());
                    try {

                        if (profileEntity1.getUserType().equals("1")) {
                            profileEntity1.setBalanceAmount(profileEntity1.getBalanceAmount() - 5);
                            verificationRequestEntity.setAmountPaid(5.00);
                        }
                        if (profileEntity1.getUserType().equals("2")) {
                            profileEntity1.setBalanceAmount(profileEntity1.getBalanceAmount() - 10);
                            verificationRequestEntity.setAmountPaid(10.00);
                        }
                        if (profileEntity1.getUserType().equals("3")) {
                            profileEntity1.setBalanceAmount(profileEntity1.getBalanceAmount() - 15);
                            verificationRequestEntity.setAmountPaid(15.00);
                        }
                        if (profileEntity1.getUserType().equals("4")) {
                            profileEntity1.setBalanceAmount(profileEntity1.getBalanceAmount() - 20);
                            verificationRequestEntity.setAmountPaid(20.00);
                        }

                        verificationRequestService.saveVerificationRequest(verificationRequestEntity);
                        profilesService.saveUser(profileEntity1);
                    } catch (Exception e2) {
                        model.addAttribute("errorMessage", "Request Not Successfully Sent");

                    }


                    CandidatesVerifiedEntity candidatesVerifiedEntity = new CandidatesVerifiedEntity();
                    candidatesVerifiedEntity.setCandidateNumber(verificationRequestEntity.getCandidateNumber());
                    candidatesVerifiedEntity.setProgram("");
                    candidatesVerifiedEntity.setFirstName("");
                    candidatesVerifiedEntity.setSurname("");
                    candidatesVerifiedEntity.setCertificateNumber("");

                    candidatesVerifiedEntity.setDateAwarded(null);


                    model.addAttribute("verifiedCandidate", candidatesVerifiedEntity);
                    model.addAttribute("authenticationStatus", "2");
                    model.addAttribute("initialRequestDate", verificationRequestEntity.getRequestDate());
                }
                //@TODO call webservice
            }

        } catch (InstitutesNotFoundException e) {
            e.printStackTrace();
        }
        model.addAttribute("searchButton", true);

    }

    @RequestMapping(value = {"/payment", "/cart", "/complete", "/invoice"})
    public String payment(HttpServletRequest request, Model model, HttpSession session, @RequestParam(value = "countryId", required = false) String countryId, @RequestParam(value = "userTypeCharge", required = false) String userTypeCharge
            , @RequestParam(value = "qty", required = false) String qty) {
        String url = request.getRequestURI();
        int index = url.lastIndexOf("/");
        if (index != -1) {
            ProfileEntity profileEntity = (ProfileEntity) session.getAttribute("profile");

            if (url.contains("cart")) {
                model.addAttribute("profile", session.getAttribute("profile"));
                return "cart";

            } else if (url.contains("payment")) {
                model.addAttribute("profile", session.getAttribute("profile"));
                if (profileEntity.getUserType().equalsIgnoreCase("1") && null != qty) {
                    model.addAttribute("amountCredited", 5 * Double.valueOf(qty));
                    session.setAttribute("amountCredited", 5 * Double.valueOf(qty));
                } else if (profileEntity.getUserType().equalsIgnoreCase("2") && null != qty) {
                    model.addAttribute("amountCredited", 10 * Double.valueOf(qty));
                    session.setAttribute("amountCredited", 10 * Double.valueOf(qty));

                } else if (profileEntity.getUserType().equalsIgnoreCase("3") && null != qty) {
                    model.addAttribute("amountCredited", 15 * Double.valueOf(qty));
                    session.setAttribute("amountCredited", 15 * Double.valueOf(qty));

                } else if (profileEntity.getUserType().equalsIgnoreCase("4") && null != qty) {
                    model.addAttribute("amountCredited", 20 * Double.valueOf(qty));
                    session.setAttribute("amountCredited", 20 * Double.valueOf(qty));

                }

                return "payment";
            } else if (url.contains("complete")) {
                model.addAttribute("profile", session.getAttribute("profile"));
                try {
                    ProfileEntity profileEntity1 = profilesService.findUserByUserId(profileEntity.getId());
                    profileEntity1.setBalanceAmount(profileEntity1.getBalanceAmount() + (Double) session.getAttribute("amountCredited"));
                    long time = System.currentTimeMillis();
                    java.sql.Date date = new java.sql.Date(time);
                    profileEntity1.setTopUpDate(date);
                    profilesService.saveUser(profileEntity1);
                } catch (ProfilesNotFoundException e) {
                    e.printStackTrace();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                model.addAttribute("amountCredited", session.getAttribute("amountCredited"));

                return "complete";
            } else if (url.contains("invoice")) {
                try {
                    ProfileEntity profileEntity1 = profilesService.findUserByUserId(profileEntity.getId());
                    model.addAttribute("profile", profileEntity1);
                    List<VerificationRequestEntity> verificationRequestEntities = verificationRequestService.getVerificationRequestByUserId(profileEntity.getId());
                    model.addAttribute("verificationRequests", verificationRequestEntities);

                    model.addAttribute("institutes", institutesService.getAllInstitutes());
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                    Date beginDate = sdf.parse(ZonedDateTime.now().withDayOfMonth(1).toString().substring(0, 10));
                    model.addAttribute("beginDate", beginDate);
                    model.addAttribute("nowDate", new Date());

                    double total = 0;
                    for (VerificationRequestEntity verificationRequestEntity1 : verificationRequestEntities) {
                        total = total + verificationRequestEntity1.getAmountPaid();
                    }

                    model.addAttribute("total", total);
                    return "invoice";
                } catch (ProfilesNotFoundException e) {
                    e.printStackTrace();
                } catch (VerificationRequestNotFoundException e) {
                    model.addAttribute("errorMessage", "No Previous Requests Made this Month");

                } catch (ParseException e) {
                    e.printStackTrace();
                } catch (InstitutesNotFoundException e) {
                    e.printStackTrace();
                }
            }

            return "profile";
        }
        return url;
    }


    @RequestMapping(value = {"/upload"})
    public String upload(HttpServletRequest request, HttpServletResponse response, Model model, HttpSession session, @RequestParam(value = "file", required = false) MultipartFile file) {
        String url = request.getRequestURI();
        model.addAttribute("profile", session.getAttribute("profile"));
        int index = url.lastIndexOf("/");
        if (index != -1) {
            if (null != file) {
                try {
                    InputStream stream = file.getInputStream();
                    XSSFWorkbook myExcelBook = new XSSFWorkbook(stream);
                    XSSFSheet myExcelSheet = myExcelBook.getSheet("Report");
                    XSSFRow column = myExcelSheet.getRow(0);
                    int columns;
                    try {
                        for (columns = 0; columns <= 100; columns++) {
                            if (column.getCell(columns).getCellType() == HSSFCell.CELL_TYPE_STRING) {
                                String name = column.getCell(columns).getStringCellValue();
                                if(name.equals("candidate_number")||name.equals("date_awarded")||name.equals("certificate_number")
                                        ||name.equals("first_name")||name.equals("surname")||name.equals("date_of_birth")
                                        ||name.equals("id_number")||name.equals("status")||name.equals("program")){
                                    System.out.println("Column Name : " + name);
                                }else{
                                    model.addAttribute("errorMessage","column name: "+name+" not valid accepted names are: " +
                                            "candidate_number\tdate_awarded\tcertificate_number\tfirst_name\tsurname\tdate_of_birth\tid_number\tstatus\tprogram\n");
                                    return "profile";
                                }
                            } else {
                                break;
                            }
                        }
                    } catch (NullPointerException npx) {
                        int roww;
                        for (roww = 1; roww <= 20000; roww++) {
                            XSSFRow row = myExcelSheet.getRow(roww);

                            int rows;
                            for (columns = 0; columns <= 100; columns++) {

                                try {
                                    String name = row.getCell(columns).getStringCellValue();
                                    System.out.println("row : " + name);
                                } catch (IllegalStateException ill) {
                                    Double name = row.getCell(columns).getNumericCellValue();
                                    System.out.println("row : " + name);
                                } catch (NullPointerException gl) {
                                    break;
                                }
                            }
                        }
                    }

                } catch (IOException e) {
                    e.printStackTrace();
                }


            }
            return "upload";

        }
        return "upload";
    }


    @RequestMapping(value = {"/uploading"})
    @ResponseBody

    public String uploading(HttpServletRequest request, HttpServletResponse response, Model model, HttpSession session, @RequestParam(value = "file", required = false) MultipartFile file) {
        String url = request.getRequestURI();
     //   response.setStatus(200,"File Uploaded");
        model.addAttribute("profile", session.getAttribute("profile"));
        int index = url.lastIndexOf("/");
        if (index != -1) {
            if (null != file) {
                try {
                    InputStream stream = file.getInputStream();
                    XSSFWorkbook myExcelBook = new XSSFWorkbook(stream);
                    XSSFSheet myExcelSheet = myExcelBook.getSheet("Report");
                    XSSFRow column = myExcelSheet.getRow(0);
                    int columns;
                    try {
                        for (columns = 0; columns <= 100; columns++) {
                            if (column.getCell(columns).getCellType() == HSSFCell.CELL_TYPE_STRING) {
                                String name = column.getCell(columns).getStringCellValue();
                                if(name.equals("candidate_number")||name.equals("date_awarded")||name.equals("certificate_number")
                                        ||name.equals("first_name")||name.equals("surname")||name.equals("date_of_birth")
                                        ||name.equals("id_number")||name.equals("status")||name.equals("program")){
                                    System.out.println("Column Name : " + name);
                                }else{
                                    model.addAttribute("errorMessage","column name: "+name+" not valid accepted names are: " +
                                            "candidate_number\tdate_awarded\tcertificate_number\tfirst_name\tsurname\tdate_of_birth\tid_number\tstatus\tprogram\n");
                                    return "profile";
                                }
                            } else {
                                break;
                            }
                        }
                    } catch (NullPointerException npx) {
                        int roww;
                        for (roww = 1; roww <= 20000; roww++) {
                            XSSFRow row = myExcelSheet.getRow(roww);

                            int rows;
                            for (columns = 0; columns <= 100; columns++) {

                                try {
                                    String name = row.getCell(columns).getStringCellValue();
                                    System.out.println("row : " + name);
                                } catch (IllegalStateException ill) {
                                    Double name = row.getCell(columns).getNumericCellValue();
                                    System.out.println("row : " + name);
                                } catch (NullPointerException gl) {
                                    break;
                                }
                            }
                        }
                    }

                } catch (IOException e) {
                    e.printStackTrace();
                }


            }
            return "upload";

        }
        return "upload";
    }

//    UploadResponse uploadResponse=new UploadResponse();
//            uploadResponse.setStatus(200);
//            uploadResponse.setMessage(file.getName()+" Successfully Uploaded");
//    JsonObjectConversionUtility jsonObjectConversionUtility=new JsonObjectConversionUtility();
//           return jsonObjectConversionUtility.objectToJson(uploadResponse);
}