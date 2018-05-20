//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.golden_xchange.controller.createdonation;

import com.golden_xchange.controller.userlogin.UserLoginWebserviceEndpoint;
import com.golden_xchange.domain.bankaccounts.service.BankAccountService;
import com.golden_xchange.domain.mainlist.exception.MainListNotFoundException;
import com.golden_xchange.domain.mainlist.model.MainListEntity;
import com.golden_xchange.domain.mainlist.service.MainListService;
import com.golden_xchange.domain.notifications.model.NotificationsEntity;
import com.golden_xchange.domain.notifications.service.NotificationsService;
import com.golden_xchange.domain.users.exception.GoldenRichesUsersNotFoundException;
import com.golden_xchange.domain.users.model.GoldenRichesUsers;
import com.golden_xchange.domain.users.service.GoldenRichesUsersService;
import com.golden_xchange.domain.utilities.Enums;
import com.golden_xchange.domain.utilities.Enums.StatusCodeEnum;
import com.golden_xchange.domain.utilities.SendSms;
import org.apache.commons.lang.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.security.NoSuchAlgorithmException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@ControllerAdvice
@Controller
public class CreateDonationWebserviceEndpoint {
    @Autowired
    MainListService donationService;

    @Autowired
    GoldenRichesUsersService goldenRichesUsersService;
    @Autowired
    BankAccountService bankAccountService;

    @Autowired
    NotificationsService notificationsService;


    public CreateDonationWebserviceEndpoint() {
    }

    @RequestMapping({"/createDonation"})
    public String handleCreateGoldenRichesRequest(HttpServletRequest httpServletRequest, Model model, HttpSession session, CreateDonationRequest request, @RequestParam(value = "action", required = false) String action) throws Exception {
        MainListEntity createDonation = new MainListEntity();
        CreateDonationResponse response = new CreateDonationResponse();
        model.addAttribute("response",response);
        Date utilDate = new Date();
        new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Timestamp sqlDate = new Timestamp(utilDate.getTime());
        model.addAttribute("notificationCount",session.getAttribute("notificationCount"));
        model.addAttribute("notifications",session.getAttribute("notifications")) ;

        if(action.equals("1")){
            if (commonValidator(request, response))return errorResponse(model, response,session);
            try{
                List<MainListEntity> paidDonations = this.donationService.outStandingPayment(request.getPayerUsername());
                if(paidDonations.size()>2){
                response.setMessage("You Can Only Have a Maximum of 3 Investments at a time. Either Cancel Some, Or Make Payments");
                response.setStatusCode(StatusCodeEnum.FORBIDDEN.getStatusCode());
                return errorResponse(model, response,session);
                }
            }
            catch(Exception ex){
               //do nothing
            }

            if(request.getAmount()%10!=0){
                response.setMessage("Please Make Your Donation Divisible By 10 e.g 300 , 8560, 12000");
                response.setStatusCode(StatusCodeEnum.FORBIDDEN.getStatusCode());
                return errorResponse(model, response,session);
            }

            if((request.getAmount() + 0.8D * request.getAmount())%10!=0){
                response.setMessage("Please Make Your Donation-Interest to be Divisible By 10 e.g 300 , 8560, 12000");
                response.setStatusCode(StatusCodeEnum.FORBIDDEN.getStatusCode());
                return errorResponse(model, response,session);
            }

            if(null==request.getPayerUsername()) {
                response.setMessage("Please relogin and try again required values have not been supplied");
                response.setStatusCode(StatusCodeEnum.FORBIDDEN.getStatusCode());
                return errorResponse(model, response,session);
            }


            GoldenRichesUsers goldenRichesUsers=goldenRichesUsersService.findUserByMemberId(request.getPayerUsername());
            MainListEntity mainListEntity=new MainListEntity();
            mainListEntity.setStatus(0);
            mainListEntity.setUpdatedDate(sqlDate);
            mainListEntity.setAdjustedAmount(request.getAmount() + 0.8D * request.getAmount());
            mainListEntity.setDonatedAmount(request.getAmount());
            mainListEntity.setEnabled(1);
            mainListEntity.setBankAccountNumber(goldenRichesUsers.getAccountNumber());
            mainListEntity.setAmountToReceive(request.getAmount() + 0.8D * request.getAmount());
            mainListEntity.setDate(sqlDate);
            String mainRef=RandomStringUtils.randomAlphanumeric(10).toUpperCase();
            mainListEntity.setMainListReference(mainRef);
            mainListEntity.setDonationReference(RandomStringUtils.randomAlphanumeric(10).toUpperCase());
            mainListEntity.setDepositReference(RandomStringUtils.randomAlphanumeric(10).toUpperCase());
            mainListEntity.setUserName(request.getPayerUsername());
            mainListEntity.setPayerUsername(request.getPayerUsername());
            mainListEntity.setDonationType(0);
            donationService.saveUser(mainListEntity);
           // createForSponsor(goldenRichesUsers,request,mainRef);

        }


        try {
            List<NotificationsEntity> notificationsEntityList = notificationsService.getUserNotifications(request.getPayerUsername());
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


        return "redirect:/donation_state";
    }

    private void createNotificationMessage(CreateDonationRequest request, MainListEntity mainListEntity) {
        NotificationsEntity notificationsEntity=new NotificationsEntity();
        notificationsEntity.setMessage("DepositReference: "+mainListEntity.getDepositReference()+", AmountPaid: "+mainListEntity.getDonatedAmount()+", Status: Pending Payment Confirmation");
        notificationsEntity.setUserName(request.getPayerUsername());
        notificationsEntity.setCreationDate(new Date());
        notificationsEntity.setStatus(0);
        notificationsEntity.setMainListRef(mainListEntity.getMainListReference());
        notificationsService.save(notificationsEntity);

    }

    public MainListEntity createDonationFromExisting(CreateDonationRequest request, MainListEntity createDonation, CreateDonationResponse response, Timestamp sqlDate) throws NoSuchAlgorithmException {
        MainListEntity mainListReference=new MainListEntity();

        try {
            label94: {
                if(null != request.getMainListReference() && !request.getMainListReference().isEmpty()) {
                    mainListReference = donationService.findDonationByMainListReference(request.getMainListReference());
                    if(mainListReference.getPayerUsername().equals(request.getPayerUsername())) {
                        response.setMessage("You can't Donate to Yourself");
                        response.setStatusCode(StatusCodeEnum.FORBIDDEN.getStatusCode());
                        return mainListReference;
                    }

                    if(mainListReference.getAdjustedAmount() < request.getAmount()) {
                    request.setAmount(mainListReference.getAdjustedAmount());
                    }

                    if (commonValidator(request, response)) return mainListReference;


                    Double extra = Double.valueOf(mainListReference.getAdjustedAmount() - request.getAmount());
                    if(extra.doubleValue() < 300.0D && extra.doubleValue() != 0.0D) {
                        response.setMessage("You can't leave less than R300 on a Donation after Payment.");
                        response.setStatusCode(StatusCodeEnum.FORBIDDEN.getStatusCode());
                        return mainListReference;
                    }

                    if(null != request.getMainListReference() && !request.getMainListReference().isEmpty()) {
      //                  try {
//                            gold = this.goldenRichesUsersService.findUserByMemberId(request.getPayerUsername());
                            createDonation.setPayerUsername(request.getPayerUsername());
//                        } catch (GoldenRichesUsersNotFoundException var12) {
//                            response.setStatusCode(StatusCodeEnum.NOTFOUND.getStatusCode());
//                            response.setMessage(var12.getMessage());
//                            return true;
//                        }

                        if(0.0D != request.getAmount()) {
//                            if(request.getKeeper()==1){
//                                request.setAmount(0.75*request.getAmount());
//                            }
                            createDonation.setKeeper(request.getKeeper());

                            createDonation.setDonatedAmount(request.getAmount());
                            createDonation.setAdjustedAmount(request.getAmount());
                            createDonation.setAmountToReceive(0.0);
                            if(null != request.getBankAccountNumber() && !request.getBankAccountNumber().isEmpty()) {
//                                if(!this.bankAccountService.findBankAccByAccNumberAndUserName(request.getBankAccountNumber(), request.getPayerUsername())) {
//                                    response.setStatusCode(StatusCodeEnum.NOTFOUND.getStatusCode());
//                                    response.setMessage("Provided AccountNumber: " + request.getBankAccountNumber() + " doesn't match user: " + request.getPayerUsername());
//                                    return true;
//                                }

                                GoldenRichesUsers goldenRichesUsers = this.goldenRichesUsersService.getUserByBankDetails(request.getBankAccountNumber());
                                createDonation.setUserName(mainListReference.getUserName());
                                createDonation.setBankAccountNumber(request.getBankAccountNumber());
                                createDonation.setMainListReference(RandomStringUtils.randomAlphanumeric(10).toUpperCase());
                                createDonation.setDonationReference(request.getMainListReference());
                                createDonation.setDepositReference(RandomStringUtils.randomAlphanumeric(10).toUpperCase());
                                break label94;
                            }

                            response.setStatusCode(StatusCodeEnum.FORBIDDEN.getStatusCode());
                            response.setMessage("donationReference can't be left empty ");
                            return mainListReference;
                        }

                        response.setStatusCode(StatusCodeEnum.EMPTYVALUE.getStatusCode());
                        response.setMessage("amount cant be empty!!");
                        return mainListReference;
                    }

                    response.setMessage("MainListReference` Can't be Left Empty");
                    response.setStatusCode(StatusCodeEnum.EMPTYVALUE.getStatusCode());
                    return mainListReference;
                }

                response.setMessage("MainListReference` Can't be Left Empty");
                response.setStatusCode(StatusCodeEnum.EMPTYVALUE.getStatusCode());
                return mainListReference;
            }
        } catch (MainListNotFoundException | GoldenRichesUsersNotFoundException var13) {
            response.setMessage(var13.getMessage());
            response.setStatusCode(StatusCodeEnum.NOTFOUND.getStatusCode());
            return mainListReference;
        }

        createDonation.setEnabled(1);
        createDonation.setStatus(0);
        createDonation.setDonationType(2);
        createDonation.setUpdatedDate(sqlDate);
        createDonation.setDate(sqlDate);
        String user = mainListReference.getPayerUsername();
        createDonation.setUserName(user);
        donationService.saveUser(createDonation);

        if(mainListReference.getAdjustedAmount() > 0.0D) {
            double reduceDonatedAmount = mainListReference.getAdjustedAmount() - request.getAmount();
            mainListReference.setAdjustedAmount(reduceDonatedAmount);
            donationService.saveUser(mainListReference);
        }

        sendMessage(request, createDonation);
        return createDonation;
    }

    private void sendMessage(CreateDonationRequest request, MainListEntity createDonation) {
        try {
            SendSms send = new SendSms();
            GoldenRichesUsers goldenRichesUsers=goldenRichesUsersService.findUserByMemberId(createDonation.getUserName());
            send.send("sendSms.sh", goldenRichesUsers.getTelephoneNumber(), "Golden-Xchange Donation Created [" + createDonation.getUpdatedDate() + "]." + " DepositReference: " + createDonation.getDepositReference() + " AmountToPay: R" + createDonation.getDonatedAmount() + ". Confirm Before Payment [expires in 12hrs]");
        } catch (Exception var11) {
            //do nothing
        }
    }

    private String errorResponse(Model model, CreateDonationResponse response,HttpSession session ) {
        model.addAttribute("response",response);
        model.addAttribute("profile",session.getAttribute("profile"));
        return "new_donation";
    }

    private boolean commonValidator(CreateDonationRequest request, CreateDonationResponse response) {
        if(request.getAmount() < 350.0D) {
            response.setMessage("Minimum Donation Amount is R350");
            response.setStatusCode(StatusCodeEnum.FORBIDDEN.getStatusCode());
            return true;
        }
        if(request.getAmount() > 50000.0D) {
            response.setMessage("Maximum Donation Amount is R50000");
            response.setStatusCode(StatusCodeEnum.FORBIDDEN.getStatusCode());
            return true;
        }


        return false;
    }


    public void createForSponsor(GoldenRichesUsers goldenRichesUsers,CreateDonationRequest request,String ref){
        Date utilDate = new Date();
        new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Timestamp sqlDate = new Timestamp(utilDate.getTime());
        double sponsorPercentage=0.2D;
        try {
            GoldenRichesUsers sponsorProfile=goldenRichesUsersService.findUserByMemberId(goldenRichesUsers.getReferenceUser());
            addSponsors(request, ref, sqlDate, 0.1D, sponsorProfile);
//            GoldenRichesUsers sponsorProfile1=goldenRichesUsersService.findUserByMemberId(sponsorProfile.getReferenceUser());
//            addSponsors(request, ref, sqlDate, 0.1D, sponsorProfile1);

        } catch (GoldenRichesUsersNotFoundException e) {
            e.printStackTrace();
        }


    }

    private void addSponsors(CreateDonationRequest request, String ref, Timestamp sqlDate, double sponsorPercentage, GoldenRichesUsers sponsorProfile) {
        MainListEntity mainListEntity=new MainListEntity();
        mainListEntity.setStatus(0);
        mainListEntity.setUpdatedDate(sqlDate);
        mainListEntity.setAdjustedAmount(sponsorPercentage * request.getAmount());
        mainListEntity.setDonatedAmount(sponsorPercentage * request.getAmount());
        mainListEntity.setEnabled(1);
        mainListEntity.setBankAccountNumber(sponsorProfile.getAccountNumber());
        mainListEntity.setAmountToReceive(sponsorPercentage * request.getAmount());
        mainListEntity.setDate(sqlDate);
        mainListEntity.setMainListReference(RandomStringUtils.randomAlphanumeric(10).toUpperCase());
        mainListEntity.setDonationReference(ref);
        mainListEntity.setDepositReference(RandomStringUtils.randomAlphanumeric(10).toUpperCase());
        mainListEntity.setUserName(sponsorProfile.getUserName());
        mainListEntity.setPayerUsername(request.getPayerUsername());
        mainListEntity.setDonationType(1);
        donationService.saveUser(mainListEntity);
        createNotificationMessage(request, mainListEntity);
        sendMessage(request, mainListEntity);

    }



}

