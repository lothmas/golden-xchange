//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.golden_xchange.controller.createuser;

import com.golden_xchange.domain.users.exception.GoldenRichesUsersNotFoundException;
import com.golden_xchange.domain.users.model.GoldenRichesUsers;
import com.golden_xchange.domain.users.service.GoldenRichesUsersService;
import com.golden_xchange.domain.utilities.Enums;
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
    public String handleCreateGoldenRichesRequest(@RequestParam(value="sponsor",required=false) String sponsor,Model model,HttpSession session) {
        if(null!=sponsor){
            model.addAttribute("sponsor",sponsor);
            model.addAttribute("disabled",true);
            session.setAttribute("sponsor",sponsor);
        }
        else {
            model.addAttribute("sponsor",null);
            model.addAttribute("disabled",false);

        }
        CreateGoldenRichesUserResponse response = new CreateGoldenRichesUserResponse();
        model.addAttribute("response", response);
        return "register";
    }

    @RequestMapping(value = {"/register"}, method = RequestMethod.POST)
    @Transactional
    public String handleCreateGoldenRichesRequest(@Valid CreateGoldenRichesUserRequest request, Model model, HttpSession session, @RequestParam(value = "diallingCode", required = false) String diallingCode
            , @RequestParam(value = "pic", required = false) MultipartFile profilePic) throws Exception {
        CreateGoldenRichesUserResponse response = new CreateGoldenRichesUserResponse();
        GoldenRichesUsers goldenRichesUsers = new GoldenRichesUsers();
        String referenceFromSession= (String) session.getAttribute("sponsor");
        if(null!=referenceFromSession && !referenceFromSession.equals("")){
            request.setReferenceUser(referenceFromSession);
        }
        if (!request.getReferenceUser().equals("")) {
            try {
                this.goldenRichesUsersService.findUserByMemberId(request.getReferenceUser());
                goldenRichesUsers.setReferenceUser(request.getReferenceUser());
            } catch (Exception exp) {
                response.setStatusCode(StatusCodeEnum.NOTFOUND.getStatusCode());
                response.setMessage("Supplied Sponsor-Username is not Registered on the System. Please confirm with your Sponsor Or Leave Blank To Proceed");
                return registerResponse(model, response);
            }
        } else {
            goldenRichesUsers.setReferenceUser("sanele");
        }


           if(this.goldenRichesUsersService.suppliedEmailExists(request.getEmailAddress())){
               response.setStatusCode(StatusCodeEnum.NOTFOUND.getStatusCode());
               response.setMessage("Supplied EmailAddress Already Exist on the System");
               return registerResponse(model, response);
           }


        try {
            GoldenRichesUsers goldenUsers = this.goldenRichesUsersService.getUserByBankDetails(request.getAccountNumber());
            if (null != goldenUsers) {
                response.setMessage("AccountNumber Already Registered");
                response.setStatusCode(StatusCodeEnum.FORBIDDEN.getStatusCode());
                return registerResponse(model, response);
            }
        } catch (Exception var5) {
            //do nothing
        }

        try {
            if (null != request.getUserName() && !request.getUserName().equals("")) {
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
            if (this.inputValidation(request, response)) {
                return registerResponse(model, response);
            } else {


                goldenRichesUsers.setAccountNumber(request.getAccountNumber());
                goldenRichesUsers.setBankName(request.getBankName());
                goldenRichesUsers.setBranchNumber(request.getBranchNumber());
                goldenRichesUsers.setEmailAddress(request.getEmailAddress());
                goldenRichesUsers.setFirstName(request.getFirstName());
                goldenRichesUsers.setPassword(GeneralDomainFunctions.getCryptedPasswordAndSalt(request.getPassword()));
                goldenRichesUsers.setSurname(request.getSurname());
                goldenRichesUsers.setTelephoneNumber("+" + diallingCode + request.getTelephone());
                goldenRichesUsers.setUserName(request.getUserName());
                goldenRichesUsers.setEnabled((byte) 1);
                goldenRichesUsers.setAccountHoldername(request.getAccountHolderName());
                goldenRichesUsers.setGender(request.getGender());
                goldenRichesUsers.setAccountType(request.getAccountType());
                if (!profilePic.getOriginalFilename().equals("")) {
                    goldenRichesUsers.setProfilePic(StringUtils.newStringUtf8(Base64.encodeBase64(profilePic.getBytes(), false)));
                } else {
                    goldenRichesUsers.setProfilePic(defaultImage());
                }


                this.goldenRichesUsersService.saveUser(goldenRichesUsers);
                response.setMessage("User " + request.getUserName() + " Was Successfully Created");
                response.setStatusCode(StatusCodeEnum.CREATED.getStatusCode());
                model.addAttribute("profile", goldenRichesUsers);
                session.setAttribute("profile", goldenRichesUsers);
                return "profile";
            }
        }
    }

    private String registerResponse(Model model, CreateGoldenRichesUserResponse response) {
        model.addAttribute("response", response);
        return "register";
    }

    public boolean inputValidation(@RequestPayload CreateGoldenRichesUserRequest request, CreateGoldenRichesUserResponse response) throws GoldenRichesUsersNotFoundException {
        if (null != request.getFirstName() && !request.getFirstName().equals("")) {
            if (null != request.getSurname() && !request.getSurname().equals("")) {
                if (null != request.getAccountHolderName() && !request.getAccountHolderName().equals("")) {
                    if (null != request.getBankName() && !request.getBankName().equals("")) {
                        if (null != request.getBranchNumber() && !request.getBranchNumber().equals("")) {
                            if (null != request.getAccountNumber() && !request.getAccountNumber().equals("")) {
                                if (null != request.getTelephone() && !request.getTelephone().equals("")) {
                                    if (null != request.getPassword() && !request.getPassword().equals("")) {
                                        if (!request.getPassword().equals(request.getPassword2())) {
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
        if (null != request.getEmailAddress() && !request.getEmailAddress().equals("")) {
            if (!GeneralDomainFunctions.isEmailValid(request.getEmailAddress())) {
                response.setMessage("Provided EmailAddress is Invalid");
                response.setStatusCode(StatusCodeEnum.INVALIDSYNTAX.getStatusCode());
                return true;
            } else if (this.goldenRichesUsersService.suppliedEmailExists(request.getEmailAddress())) {
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

    String defaultImage() {
        return "/9j/4AAQSkZJRgABAQAAAQABAAD//gA7Q1JFQVRPUjogZ2QtanBlZyB2MS4wICh1c2luZyBJSkcg\n" +
                "SlBFRyB2ODApLCBxdWFsaXR5ID0gOTAK/9sAQwADAgIDAgIDAwMDBAMDBAUIBQUEBAUKBwcGCAwK\n" +
                "DAwLCgsLDQ4SEA0OEQ4LCxAWEBETFBUVFQwPFxgWFBgSFBUU/9sAQwEDBAQFBAUJBQUJFA0LDRQU\n" +
                "FBQUFBQUFBQUFBQUFBQUFBQUFBQUFBQUFBQUFBQUFBQUFBQUFBQUFBQUFBQUFBQU/8AAEQgCcgJy\n" +
                "AwEiAAIRAQMRAf/EAB8AAAEFAQEBAQEBAAAAAAAAAAABAgMEBQYHCAkKC//EALUQAAIBAwMCBAMF\n" +
                "BQQEAAABfQECAwAEEQUSITFBBhNRYQcicRQygZGhCCNCscEVUtHwJDNicoIJChYXGBkaJSYnKCkq\n" +
                "NDU2Nzg5OkNERUZHSElKU1RVVldYWVpjZGVmZ2hpanN0dXZ3eHl6g4SFhoeIiYqSk5SVlpeYmZqi\n" +
                "o6Slpqeoqaqys7S1tre4ubrCw8TFxsfIycrS09TV1tfY2drh4uPk5ebn6Onq8fLz9PX29/j5+v/E\n" +
                "AB8BAAMBAQEBAQEBAQEAAAAAAAABAgMEBQYHCAkKC//EALURAAIBAgQEAwQHBQQEAAECdwABAgMR\n" +
                "BAUhMQYSQVEHYXETIjKBCBRCkaGxwQkjM1LwFWJy0QoWJDThJfEXGBkaJicoKSo1Njc4OTpDREVG\n" +
                "R0hJSlNUVVZXWFlaY2RlZmdoaWpzdHV2d3h5eoKDhIWGh4iJipKTlJWWl5iZmqKjpKWmp6ipqrKz\n" +
                "tLW2t7i5usLDxMXGx8jJytLT1NXW19jZ2uLj5OXm5+jp6vLz9PX29/j5+v/aAAwDAQACEQMRAD8A\n" +
                "/VKiiigAooooAKKKKACiiigAooooAKWiigBKKWkoAWikpaACikpaACkoooABS0lFABS0lFAC0lFF\n" +
                "ABS0lFABS0lFABRRRQAtJRWZr/ijR/CtkbzWtUs9KtB1nvZ1iT82IFAGnRXz94w/b5+Afgm4lgvv\n" +
                "iVot1PF9+PTpxdEHGcfu814h4o/4LG/BbSLmWLSbLXdcVCQJUtvIV/cbucfUUAfeFFfmL4k/4Lea\n" +
                "BCNmhfDO/uX5/e3uoqi+3yqhP61wd5/wW48WszfZfh5osa9hNNM5/RxQB+u9Ffjlc/8ABbP4lNn7\n" +
                "P4H8Kp/11juW/lOKit/+C2PxQB/f+CfCLj/pnDdL/O4NAH7I0V+QVt/wW28arj7R8P8AQH/65vOv\n" +
                "85DXX+HP+C30AkRde+GDsh+9Jp+pBSP+Auh/nQB+p1Ffnzon/BZ/4UXzqNS8N69pinqVCzY/LFez\n" +
                "+FP+ClX7PXiyKFk8fW2lNIASmqxPbFCex3DFAH0/R3rmPCPxR8H+Pl3eGvFGka+uN2dNvo5+PX5S\n" +
                "a6frQAUUUUALSUUd6ACiiigBaSiigAooooAKKKKAClpKKAClopKAClpKKACiiigAooooAWkoooAK\n" +
                "DRRQAtJS0lAC0lFFAC0UlFAC0Un4UUAFFFFAC0UUlABRRRQAtJRRQAUUUUAFLSUUAFLSUUALRSUU\n" +
                "ALSUUUALSUUUAFFFFAC0UUUAFJS0lABRRRQAUVzPxF+Jfhj4TeF7rxD4t1m10TSLYfPcXT7QT2UD\n" +
                "qSfQV+X/AO07/wAFi9TvZZ9E+Demx6fbAlX8Q6onmTP2/dRfdT6tuznoKAP03+JHxe8GfCHRZdV8\n" +
                "ZeJNP8P2SKX3XcuHcAfwRjLufZQTXw38Zv8Ags18PPDcU1p8O9C1LxdfDIW9vYjZ2fsRu/eH8UFf\n" +
                "kl49+J3iz4o61Pq3izxBf69qEzbnmvJi3OMcL0H0AFcxQB9Z/FT/AIKe/Hf4l3NyLXxO/hPT5RtS\n" +
                "10L9w0YxjiUYcnvnNfMfiXxfrnjO/N9r+sX2tXhzm4v7h5nP4sSayKKACiiigAooooAKKKKACiii\n" +
                "gAooooAnsb650y6jurSeS1uYzuSWFyrKfUEdK96+G37e3x0+FqxRaX8QNUvbWM5S11aY3kSj0CyE\n" +
                "gD2FfP1FAH6t/Br/AILV2ryxWnxP8HTQoTg6l4fIkxx1aJyvGfQ/ga+7vgt+1r8KP2gbVX8F+MLO\n" +
                "+uzw2n3Ia2ulPp5UgVm69VyPev5tqt6Xq99ol4l3p15PY3SHKzW0hjcfQg5oA/qdpK/EH9m7/grJ\n" +
                "8TfhXdWemeOSnj7wypCO11+7v4V9UlAw2PR1JOMZHWv1h/Z9/ap+Hf7S2hm+8G61HPdxIGutLnIS\n" +
                "6t8/3k9PcUAeu0dqKKACiiigBaKKSgApaSigAooooAKKKKAFpKKKACiiigAooooAKKKWgBKKKKAF\n" +
                "pKKKACiiigAooooAMUUUUAFFFFABRRRQAUUUUAFFLRQAlFFLQAlFFFAC0UlFAC0UlFAC0UUlAC0l\n" +
                "FFABS0lFAC0lFFABRRRQAV8tftkft8+C/wBlXS/7OSWPxB45uYy1totu27yR0DzsPuDPQE7j2GK8\n" +
                "2/4KFf8ABQ61+AGm3XgfwJNBf/EC6TZPdbt0elIerED70pHAHAGcnOMH8WPEfiTU/F2tXer6zeza\n" +
                "jqV05kmuZ2LM7H3oA7349/tH+Ov2j/Fs+ueM9auL0GRmtdPEhFrZqeiRR/dXA4zjJ6nkmvMKKKAC\n" +
                "iiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACtrwf40134f8AiG11zw3q15omr2rb\n" +
                "obyxmaKRf+BKQce1YtFAH7H/ALEv/BVDS/iXc6Z4J+K80Gi+JZQtva66QI7W8kxgCQ9I3Y9+FycV\n" +
                "+i0ciTRrJG6vGw3KynII9Qa/lbR2jYMpKspyCDgg1+kf/BPL/gpNe+CNQsvhz8Ub43fhuYrFpmuT\n" +
                "P+8sG6eXJ/ejPGDwVIPXPAB+wdFRWt1De20VxbypNBKoeOSM5VlIyCCOoIqWgBaSiigBaSiigApa\n" +
                "SigBaSiigBaKSigAooozQAtJRRQAtJRRQAtJRRQAtJRRQAtIKKKAFpO1FFAC0UmfaigAooooAKKK\n" +
                "WgBKKKWgAoopKAClpKWgBKKWigBKWiigBKWiigAooooAKSlpKACiijFABRRRQAV8cf8ABRH9t21/\n" +
                "Zl8Ff8I74cvoX+ImsQE20KkO9jCcr57L0GSCFz12n0r3z9oz466J+zn8I9c8b62yullHstbTeFa6\n" +
                "uGOI41+p5OOihj2r+dT4wfFTW/jV8R9c8Z+IJjLqWq3DTMuSViX+GNc/wqMAfSgDmNV1a913U7rU\n" +
                "dSu5r+/upWmnurmQySSuxyzMx5JJJJJqrRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFF\n" +
                "FFABRRRQAUUUUAFFFFABR0oooA/UD/glz+3k+k3enfB/4g6xmxmYW/h/Ub6T/VOThLUuf4SeEyeM\n" +
                "hRxgV+s45FfytwzSW00csTtHLGwdHU4KkHIINfu7/wAE2v2wo/2kfhgdA1yVE8b+GoYoboF8m8gx\n" +
                "tWcA85yMN6Er60AfY9FFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQA\n" +
                "UUUUALRSUUAFFFFABRRRQAUUtFABSUtFACUtFFACUtFFACUUtFACUUtFACUUtFACUUtJQAUUUUAF\n" +
                "FFfP37c37Qafs5fs96/4gt5CmuXcbWGlBeouJAQH69F6/lQB+Xn/AAVR/apX42/FtPBmg3xn8JeF\n" +
                "JXiPlkGO4vclZJAR94KAVU9MEnvXw5SySNK7O7FnY5LE8k0lABRRRQAUUUUAFFFFABRRRQAUUUUA\n" +
                "FFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAV6t+y/wDHXU/2c/jV4e8aadK6w20vk30C\n" +
                "4IuLVyBIhB9sEe6ivKaKAP6i/AvjTSviL4O0bxPodyt3pOq2qXdtKpzlGGcH0I6EdiCK3K/Nb/gj\n" +
                "d+0MPEHgTVfhNqczG80R3vtM3dDbSNudBz1DszdOjV+lNABQOlFFABRRRQAUUUUAFHeiigAooooA\n" +
                "KKMUUAFFFFABRRRQAUUUUAFAoooAKKKKACijFFABRS0UAJRS0UAJRS0UAJRS0UAFFJS0AFFFFABS\n" +
                "UtFACUtFFABSUtFABRRSGgAooooAK/G7/gs38ZZPEXxh0H4dWlwzWPh6xS7u4g3Aup8tgjviLyiM\n" +
                "/wB6v1/8T+ILTwn4c1TWr+VYbLT7aS6mkboERSx/lX80Xxw+I918Xfi94t8Y3cnmS6vqMtwpIxiP\n" +
                "OIx+CBR+FAHD0UUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQA\n" +
                "UUUUAFFFFABRRRQB7b+xj8ZJPgX+0h4K8TNcG3077dHaag27A+yysElJ7HCkn8K/o6ByAfWv5WK/\n" +
                "oi/YL+M6/HD9mPwjrU1wJ9Us7cabqBxg+fEApJ+owc+9AH0LRRRQAUUUUAFFFFAC0lFHagAooo7U\n" +
                "ABoooxQAUUUUALSUUUAFFFFABRQKKACijFFAC0U3FFADqKKKACkpaSgAopaSgAopaKAEoopaACik\n" +
                "paACiikoAWkpaKACiikoAKKWigBKKKKAPmD/AIKT/EZvhx+yF4ynhcR3mqeTpduSf4pHBb/yGklf\n" +
                "z71+t/8AwW4+IKW/gr4deCI5/wB7eahNq80QzwIYzEhP189/yNfkhQAUUUUAFFFFABRRRQAUUUUA\n" +
                "FFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAV+r3/AARK+IzT6f8A\n" +
                "ELwPO4Pk+RqlqmeQuWSX9Wjr8oa+xf8AglB8QU8DfthaLaTTmC38Q6fdaQxOcFiFmQH6tAo/GgD9\n" +
                "4qKKKADNFFFABRRRQAUUGigAooooAKKKKACiiigAooo7UAFFFFABRRRQAfzooooAOaKMUUALRRRQ\n" +
                "AlFLSUALSUtJQAUtFFACUtFFACUtFJQAUtFFACUtFFABRSUtACUUtFACUUtJQB+J3/BZTxWmsftN\n" +
                "aVo8bFhpGiRB89A8jMxx+AWvgmvpz/gpL4kTxJ+2N4+ZJRKtlcLY5U5AMa4I/Ak18x0AFFFFABRR\n" +
                "RQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFem/\n" +
                "sx+KV8FftDfDvWZCyxW2t2u8r1CtIEb9GNeZVoeHtQ/snX9Mvs7fs11FNn02uD/SgD+pZWDqGU5B\n" +
                "GQRS1heAtVTXPAvhzUkkWWO8062uFdTkMGiVs5/Gt2gAooooAKKKKACiiigAooooAM0UUUAFFFFA\n" +
                "BRRRQAUUUUAFFFFABmjNFFABkUUUUAFLRRQAUlLSUALRRSUALSUtFACUtJS0AFFFFABRRSUALSUU\n" +
                "tABRRRQAUUUUAJRRRQB/NX+1Tf8A9qftIfEm7zu87Xbp8/WQ15ZXYfGK/wD7U+Kviy73bvO1Kd8+\n" +
                "uXNcfQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUA\n" +
                "FFFFABRRRQAUUUUAf0o/spX51P8AZs+Gs+c50K1TP+6gX/2WvVq8G/YRv/7S/ZG+GU+d2dNKZ/3Z\n" +
                "pF/pXvIoAKKKKACiiigAooooAKKKKACig0UAFFFFABRRRQAUUUUAFFFFABRRRQAtFJRQAUtJRQAt\n" +
                "FJRQAtJRRQAtJRRQAtJRRQAtFFJQAUtJS0AFFJS0AFFFFABSUUtACUUtFAH8vHxDXZ4718el7L/6\n" +
                "Ea56um+J0Zi+IniRCCCt/MMH/fNczQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRR\n" +
                "RQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAf0Pf8E9V2/sa/DEf9OU3/pVNX0TXz5/wT/jM\n" +
                "f7HfwyUgg/YJTg+9xKa+g6ACiiigAooooAKKKWgBKKKKACiiigAooooAKKWkoAKKKKACiiigAooo\n" +
                "oAM0UtFACUUtFACUUUUAGKKWkoAKKKKACiiigAooooAKKKKACiiigAooooAKKKKAFooooA/me/aQ\n" +
                "0v8AsT49+PrDG37PrNzHj6Oa84r379vfQk8P/tf/ABRgjyI5dYluVB7eZ82PpkmvAaACiiigAooo\n" +
                "oAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiipL\n" +
                "WA3NzDCOsjhB+JxQB/SD+x3pf9jfsvfDW1xjGjQyY/38v/7NXsVch8HNJj0H4R+CdNiz5dpollCN\n" +
                "3U7YEGT7119ABRRRQAUUUUAFLSUtACUtFJQAUUUUAFFFFAC0lLSUAFLRSUAFFLSUAFFLSUALRRRQ\n" +
                "AUlFFAC0lLRQAlFLSUAFFFFABRRRQAUUtJQAtJS0lABRRRQAUtFJQAUUUtACUtJS0AfgN/wVC03+\n" +
                "zv2yPGHGPPSCf/vpP/rV8oV92f8ABY3w2mkftUWepR7sapodvI4PTejOhx+AWvhOgAooooAKKKKA\n" +
                "CiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigArY8G23\n" +
                "2zxfodvjPm38EePrIorHr0T9nTw6viz49fD3SHLCO612zRynXaJlJx+ANAH9KHhe2+x+GdJt+nlW\n" +
                "kMePogFadIiBFVVGFAwB6CloAKKKKAFpKKKACiiigAoopaAEoNFFABRRS0AJRRRQAUUtJQACiiig\n" +
                "AooooAMUUtFABRSUUAFFFFAC0lFFABRRRQAUUUUALRSUUAHSlpKKACiiloASlopKAFooooAKKK+b\n" +
                "f27v2rYf2V/g3dapYPbyeLtTDWuj282GAlI5mK/xKmc46HpQB+ff/BaTWtF1P41eDraw1CC61Sy0\n" +
                "yWK/t4jlrcl1ZA3bJBbj2r87a1PFHinVvGuv3ut67qE+q6teyGW4u7ly7yMepJrLoAKKKKACiiig\n" +
                "AooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAK9q/Yt\n" +
                "1XSNE/an+G9/rt7Fp2lW+qK81zPnYnyNtzj1baPxrxWlVijBlJVgcgg4INAH9UkUqTxJJG6yRuAy\n" +
                "upyGB6EGn1+a3/BKP9tO/wDH+nv8JfG2pC71jT4zJol9cP8AvZ7cdYGJ+8U5IPXaQO1fpRQAtFJR\n" +
                "QAUUUUAFLSUUAFFFFAC0lFFABRRRQAUUUUAFFFFABS0lFABRRRQAtFJmigAopaSgAooooAKKWkoA\n" +
                "KKKKADNFFFABRRRQAUUUUAFFFFABRRRQAUUUUABOK/Aj/gpn8c5PjN+1J4itra6M2heGW/sWyVWy\n" +
                "m6L/AFzAdOZTIM9wBX7n/FLxZH4E+HHibxDIhkXTdPmudgbbuKoSBntziv5jdc1efxBrWoapdHdd\n" +
                "XtxJcyn1d2LN+pNAFKiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooA\n" +
                "KKKKACiiigAooooAKKKKACiiigAooooA6X4afEDVPhZ490LxboszQ6npF3HdwlWIyUYHafY4wRX9\n" +
                "Mfw/8aaf8RvA+g+KdJkEum6zZQ31uwP8EiBgD784Ir+Xev3i/wCCU3xDk8cfsj6HZTjFxoNxNppb\n" +
                "dndGrEx/T5SB+FAH2LRS0lABRRRQAUUUUAFFFFABRRRQAUtJRQAtFJRQAtJS0lABRRRQAUUUUAFF\n" +
                "LRQAlFLSUAFFFLQAUlLSUAFFLSCgAooooAKKWkoAKKKWgBKKKWgBKKKKACiiloA8D/bz1z/hHf2Q\n" +
                "PijfB9jrpRjUj+88iIB+bV/OnX9DH/BRXS59W/Yx+J0UBG+OwjmIPdUnjYj8ga/nnoAKKKKACiii\n" +
                "gAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKA\n" +
                "CiiigAr9gv8AgiTrn2z4VfEPS2fJstVt5FT0WSJufzU1+Ptfrd/wRA0uePwZ8UNQYgW8t/ZwoO5Z\n" +
                "Y5CT/wCPCgD9OqSiigAooooAKKKWgBKKWkoAWkoooAKKKKACiiigAooooAKKKKAFpKKKAFooooAS\n" +
                "iiigAooooAKKKKACiiigAooooAKKKOaACiiigAooooAWkpaSgAoopaAPLv2ovDjeLf2d/iHpKLua\n" +
                "40a4wMf3V3f+y1/NR0r+qG/tFv7G4tn5SaNo2z6EY/rX8zXx78BT/C/41eN/CtwoV9K1e5t029DG\n" +
                "JCYyPYoVP40AcHRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFF\n" +
                "ABRRRQAUUUUAFFFFABRRRQAUUUUAFftb/wAEaPDb6Z+zZrGrFcLqesybTjr5Y2/1r8Uq/oz/AGHv\n" +
                "hxL8K/2V/h3odwix3h0yO8uEX+GWYeawPuC2PwoA90oopaACkopaAEpaSigAooooAKWkooAKKKKA\n" +
                "FpKWigBKKKKACiiigApaSigBaKKKAEoopaAEooooAKKKKACiiigAooooAKKKKAClpKWgBKKKWgBK\n" +
                "KWkoAKWkpaACvxW/4LD/AASXwR8d7Hx5ZQlLHxZaobhgDtF1CojYD6okZ+pNftRXgv7bf7PK/tKf\n" +
                "s/eIfDNtFG+vwRG90h3O3F1GMquewfG0/wC9QB/OlRVnUtNutH1C4sb2CS1vLdzHLDKpVkYHBBB6\n" +
                "Gq1ABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUU\n" +
                "UUAFFFFABRRRQB6z+yr8Gm+Pnx68I+DGV2sr68Rr1kBytspBlPH+zmv6Sre3itII4IUWKKNQiIow\n" +
                "FAGABX50f8Eff2Z7jwR4C1P4p69Z+RqfiE/ZtLSQEOlmhw0mD03vux7ID3r9HKAEooooAKWkooAK\n" +
                "KKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigBaKSigAooooAKKKKAFpKKKACiij8aACiiig\n" +
                "BaSiloASiiigAooooAWkoooAKWkooAWiiigD8tf+Cof7BE+qzX3xh+H9lJcXRO/XtIgTJK45uYwB\n" +
                "nr94e+eMGvyhZSrEEYI4INf1TkZBB5Br4z/aY/4JefDL486jea9o+7wV4oufnlubBM2875yWeLIG\n" +
                "T3YUAfhJRX1P+2T+wP4i/ZB0nRNY1DX7DXtJ1W6a0je1V1kjkCFwGDKByAehPSvligAooooAKKKK\n" +
                "ACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKK9Z/Zh/Z11r9qL\n" +
                "4q23gjQ761025e2lu5bu83eXHGmMn5QTnLKBx3oA8mr6/wD+Cfv7Dep/tOeNotc12G40/wCHulSC\n" +
                "S5uwuDeyAjEEZIxz1Y9gMdSK+0Pgf/wRs8GeEdUi1L4h69J4uaJ1dNNtVMNsSDnDnhmB7jAr9CdA\n" +
                "0DTvC2jWmk6RZQadptpGIoLW2QJHGg6AAdKAJdJ0q00PS7TTrC3S1sbSJYIIIxhURRhVA9gKtUUt\n" +
                "ACUUUtACUUUUAFGaKWgBKKKWgBKKWkoAKKKKACilpKACilpKACilooASilooASilooASiijNABRR\n" +
                "RQAtJRRQAUUUUAFFLSUALSUUUAFFFLQAlFFFABRRRQAUUUUAFLRRQAUlLRQB8Zf8FavA/wDwl37H\n" +
                "mr36xeZL4f1Oz1NcDkAubc/pOfyr8JK/py+NngK3+KHwk8XeFblPMj1TTZoFGM4faSh/Bgp/Cv5m\n" +
                "tc0qbQda1DTbgbZ7O4kt5AezIxU/qKAKVFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAU\n" +
                "UUUAFFFFABRRRQAUUUUAFFFFABX6Y/8ABEbwP9s+IXxG8XvFxp+mW+mRuRxmeUyNj/wGH51+Z1fu\n" +
                "r/wSa+Fq+Af2WLLWZYtt94munv3cjGYh8sQ/9CP40AfaVFFLQAlFFLQAlLSUUAFFFFABRRS0AJRR\n" +
                "S0AJRRRQAUUUUAFFLSUAFFLSUAFFFLQAlFFFABRS0UAFJS0lABRRS0AFJRRQAUUUUAFFFFAB+NFF\n" +
                "FABRRRQAUUUUAFFFBoAKKKKAFoopKAFopKKAFr+ff/gpL8Iz8Iv2tPFdvEm3TtbEet2eE2jZNkOP\n" +
                "wlSUV/QRX5v/APBZr4JP4m+G/hv4jWFo0l3oMrWV9JGmT9mkOULd9qtv56fPQB+O9FFFABRRRQAU\n" +
                "UUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFAGx4M8K3vjnxhofhzTl3\n" +
                "3+r30FhbrjOZJZFRf1YV/Tb8OvBlp8O/AegeGLAAWmk2UVnHhduQigZx74zX4sf8Elvgk/xI/aRg\n" +
                "8UXdo0ukeE4jel2TKG5IIhGf7ysQ4HX5a/cmgApaKKACkoooAWkoooAWkopaAEooooAKWkooAKKK\n" +
                "KAClpKKAFpKWkoAWkoooAKWkooAKKKKAFooooASilpKACilpKACjNLSGgAooooAKKKKAFpKKKACi\n" +
                "iigAooooAKKKKAClpKKAClpKKACiiigBa5L4r/DvTviz8N/EfhDVY1ksdYspbR9wztLKQGHuDgj6\n" +
                "V1tJQB/Lp468G6l8PPGWt+GdXiMGp6TeS2VwhGMPG5Vse2RWHX6Yf8Fi/wBmpfDvibTPi5otkUst\n" +
                "WcWWsGFDtS4x+7kbHA3AEZ4y3ua/M+gAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACi\n" +
                "iigAooooAKKKKACgAsQAMk9AKK+i/wBgv9nWX9pD9oPR9HuLd5fD2mY1HVpAp2iFCMIWHQs2APXB\n" +
                "9KAP1j/4JgfAv/hTf7L+i3t7biHXPFBOsXRK4YRuP3Cn/tmEP1Y19c1FaWsNhaw21tEkFvCixxxR\n" +
                "rhUUDAUDsABipaACiiigAooooAKKKKACilpKAClpKKACiiigApaSigAooooAKKWkoAKKO9FABRRR\n" +
                "QAtJRRQAtFJn3ooAKKKKACiiigAopaSgAooooAKKKKACiiigAooooAOtFFFABRRRQACiiigAopaS\n" +
                "gBaKSloAKSiigDz79oD4Qad8ePg94n8D6mq+VqtoUikYZ8qdSHik/wCAuqn6A1/Nz8QfBGp/Dbxt\n" +
                "rXhjWIGt9S0u6e2mjYc5U8H8Rg/jX9Q9flb/AMFfv2Tg7Wvxn8NWLbtotfEEcKZHH+quCB043Kx9\n" +
                "koA/KqiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAAMnA5Nfu5/wS\n" +
                "7/ZqPwL+A413VLfyvE3i0x3tyGXDRW6g+TGf++3b/gQ9K/NL/gnR+yrJ+0l8brK41W1kk8GeH5Fv\n" +
                "dTfadkzLzHBnp8zbcjrt3V++tvBHawRwwxrHDGoREUYCqBgACgCSkopaACkpaSgAooooAKKKKACi\n" +
                "looASloooASlopKACilpKACilpKACilpKACiiloAKSiigAopaKACiiigBKKKKACiiigAooooAKKM\n" +
                "UUAFFFFABRS0goAKKKKACiiigAooooAKKKKACiiigBaSiloASilpDQAVm+JvDmn+L/D2paJqtut3\n" +
                "puoW721xA44dGGCD+BrTooA/m+/a2/Zz1X9mT40a34TvUMmmec0+lXnUXFoxzGc/3gpAYdiD1614\n" +
                "xX9BP7fP7JFl+1J8ILuOxt408b6OjXWj3XRpGAJa3Y91cZAz0bBr8Ata0e88PaveaZqNu9pf2crQ\n" +
                "TwSDDI6nBBH1FAFOiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACtTwt4Z1Lxn4i\n" +
                "07Q9HtXvdTv5lt7eCMZLuxwBWXX62f8ABJr9i/8AsPTx8ZPGWn/6fdrs8PWs6n91F1a5x6sflX0A\n" +
                "J7igD7R/ZA/Z2s/2ZPgboPhCMRyat5YudWuY+RNduAZCD3UH5R7KK9qpaKAEooooAKKWkoAKKKWg\n" +
                "BKKKKACiiigApaKSgAooooAKKWkoAKWkooAKKWkoAKKWkoAKKKKACiiloATNFLRQAlFFFABRS0lA\n" +
                "C0lFFABRRRQAUUUUAFFLSUAFFFFABRRRQAUUUtACUUUUAFLSUUAFFFFABQaKWgBKKKKAFr8sf+Cr\n" +
                "/wCxPc6hOfjH4H0oSlYyviOztV+c45W6CjrxkPjnhTzzj9Taiu7SG+tpba4iSaCVSkkci5VlIwQR\n" +
                "3oA/ldor7V/4KMfsNXf7OPjO58XeGLeWf4eaxcF4+Nx06Z+TCx/u5ztPpgHJGT8VUAFFFFABRRRQ\n" +
                "AUUUUAFFFFABRRRQAUUUUAFFFFABRRXrf7Mn7Nvib9p/4l2nhXw9GYoRiW/1F1Jjs4c4Lt7+g70A\n" +
                "euf8E8/2NtR/aX+KNlq+r2DL8PNEnE+pXEoIS6ZeVt0/vFjjd2C7u+Af3psLC30uxt7O0hS3tbeN\n" +
                "YooY12qiKMAAdgAK5H4NfCHw98Cvhzo3gvwxbmDStMhEavJgyTN1aRyAMsxyTx34wK7WgBaKSigA\n" +
                "paSigBaSiigAooooAKKKKAFpKKKACiiigAooooAKKKKAFpKKKAFpKKKAClpKKAFpKWkoAKWkooAW\n" +
                "ikooAWkoooAWkoooAWkoooAKKKO9ABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFAC0lFFABRRRQAUUU\n" +
                "UAFFFFABRRRQBheO/A+i/Erwfq3hjxFYx6louqW7W11bSjIdGH6EcEHsQDX8/n7b/wCynd/sn/Fo\n" +
                "6GLpL7QtTja80u43DeYt2Crr1DKcD0Pav6IK/Gf/AILWakk/x98G2IbL2/h/zWHpvncD/wBANAH5\n" +
                "4UUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAdT8Lfh3qfxa+Ifh/wfowj/ALT1m7S1hMrhFBPU\n" +
                "knsACfwr+hX9lD9l/wAOfsr/AAyg8N6NGk+pXBFxqmpbcPdz4xnPXavRR25Pc1+E37GmpJpP7Vfw\n" +
                "ruJG2r/wkFrFk/7bhB+rV/SFQAUtJRQAtFJRQAUUUUAFLSUUAFLSUtABSUUtACUtFJQAtJRRQAUU\n" +
                "UUALSUUUAFFFFABRS0lABRRRQAtJRRQAtJRRQAtFJRQAUUUUAFFFFABRRRQAUUUUAFFFFABR1ooo\n" +
                "AKKKKACiiigAooooAKKKKAFpKWkoAKWkpaAEooooAKKKKAClpKKAFr8GP+Crmuf21+2P4gTdu+w2\n" +
                "NrafTClsf+P1+81fzd/tieNx8Rf2nPiLryS+bDc6tKsRznCJhFA9gFoA8cooooAKKKKACiiigAoo\n" +
                "ooAKKKKACiiigAooooA6L4can/YvxD8L6hu2/ZNVtZ93ptmVv6V/T3pF19u0qyuM586FJM/VQa/l\n" +
                "kjkaKRXQ7XUhgR2Nf0u/s3eNE+IXwF8B+IEk837ZpEG9853Oq7GP/fSmgD0mkpaSgBaSiigBaSil\n" +
                "oAKKKKAEopaSgApaSloASilpKAFpKWkoAKWkooAWkopaACkoooAKKWkoAWk7UUUALSUtJQAUUUUA\n" +
                "LRSYooAKKKKACiiigAoooxQAUUYooAKKKKACiiigAooxRQAUUUUAFFFFABRRRQAtJS0lABS0lFAB\n" +
                "RS0lABRS0UAFJS0UAeY/tM/FG2+DPwF8c+Lri4W2k0/Sp2tSWAL3LIVgQe5kKiv5qry4a8u552+9\n" +
                "K7OfqTmv03/4K8/ta2XiO6T4L+HZEuItPuI7nWrtHyDMBuWAAf3cqSfUYxxX5h0AFFFFABRRRQAU\n" +
                "UUUAFFFFABRRRQAUUUUAFFFFABX7bf8ABHr4sweM/wBm+88Iy3IOp+FtSkiW3LDcLWULIjAehkMw\n" +
                "/CvxJr6a/wCCf/7UqfsufG2PUtSjMvhnWY1sNUCnBiTdlZR6lMnjvk8igD+g6kqDT9QttWsLe9sr\n" +
                "iO6s7iNZYZ4mDJIjDIYEdQQc1PQAtJRS0AFFFJQAtFJRQAtJRRQAUUUUALRSUUALSUUUALSUtJQA\n" +
                "UUUtACUtFJQAUtFJQAUUUtACUUtJQAUtJRQAUUtFACUUUUALSUtJQAtJS0lABRRRQAUUUUAFGKKK\n" +
                "ACijrRQAUUUUAFFFFABRRRQAUUUtACUUUEgAknAHU0AFFeJ/GX9s34Q/AmCb/hJ/F9p9ujO0abYE\n" +
                "XFyzf3dqnAPH8RFfC/xg/wCC2BLy2nwy8CkKMhdS8RzDJ9P3ERP1/wBZ+FAH6pswRSzEADkk9K8d\n" +
                "+Jf7YnwY+EZkj8TfEXQrW6jBLWVtdLdXI+sUW5x+Ir8I/iz+2P8AF/41TXH/AAk3jS/ks5uDYWsh\n" +
                "htlHoEXtXjMkrzOWkdnY/wATHJoA/ZX4g/8ABaL4aaLG8fhPwzrXiOfJCzTqttDj1+Y7vwwK+WPj\n" +
                "H/wV++LXj+xn07wta2Hga0lRo2uLNPOuSD1+eQEKcd1AI9a+D6KAJ9Q1C61a/uL2+uZby8uJGlmu\n" +
                "J3LySOxyzMx5JJOSTUFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAfVH7On/AAUg\n" +
                "+Ln7O+k2ehWmoQ+JvDFrgQ6TrCeYIk/upKP3iqOy7sD0r7M+H/8AwWy8NXkqReMvAl/puRzcaVMs\n" +
                "yKf91iDivyLooA/oW+GH/BRH4B/FNbeOz8f6fo19Kdv2PXibBw3YbpQqsT7E19EabqlnrNlHeWF3\n" +
                "BfWkoyk9tIJI3HqGBINfywgkEEHBFd38Ovjt8QPhNdi48JeLdU0R9wcrbXDBWI9V6UAf020ma/HD\n" +
                "4N/8FnfHfhhbSy+IHhex8W2afK99Yym0uiP7xBDI5Hp8ufXvX3T8Fv8AgpT8EPjO0VrFr8vhbVXU\n" +
                "EWHiCNYGJ7hXVmQ9e7A+1AH1PSVDZX1tqVrHc2lxFdW8ihkmhcOjA9CCODU9ABSUtJQAUUUUAFLS\n" +
                "UtACUUUUALSUUUAFFLRQAlFLSUAFFFFAC0UlFAC0lLSUALRSUtABRRRQAlFFFABRRRQAUGiigAoz\n" +
                "RRQAUUYooAKKKKACiiigAooooAKKKKACiiuD+LPx18CfA7QZdX8a+JLPRLVASElbdNIcZwka5Zif\n" +
                "pQB3lZXifxZovgnRrjV/EGq2ei6XbqWlvL+dYYkA9WYgV+Xv7QH/AAWe89LjTPhJ4amhzlf7b10K\n" +
                "GHvHCpYevLN+FfnX8Wvjp45+OWvPq/jXxFea3dE/Is0h8uIYxtROij2FAH65/Hj/AIK9/DH4frNY\n" +
                "+A7eXx5qoO3z4w0Nmh553kAv2+769a/PH43/APBSD43/ABrmuoH8Uy+FtEmBUaX4fH2ZQp7GUfvW\n" +
                "yOuXx7V8vUUASXNzNeTvPcSvPM53PJIxZmPqSetR0UUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUA\n" +
                "FFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABR0oooA9k+Dn7YHxe+BEsK+EfG+pWthG4Y6bdOL\n" +
                "m1Ydx5UoZVyOMqAfev0H+BP/AAWf0rU5bfT/AIqeG/7JcqFbWNGVni3ZAJaIksO54PbpX5KUUAf0\n" +
                "3fCr44+A/jdo/wDaXgjxRp3iG2H+sW0mBli9nj+8h9mArua/lt8M+KtY8GaxBquhandaTqUDborq\n" +
                "0lMciH1BFfefwC/4LC+PvA0sFh8RtMTxtpI+VruBhDeoPUE/K59jj60Afs/RXhPwA/bY+E/7R9ov\n" +
                "/CMeIVtdVAzJo+qqLe6Tp2yVbr/Cxr3agApaSigBaKKSgAooooAKKKKACiiigAooooAKKKKACiii\n" +
                "gBaKSigBaKSigAooooAKKKKACilpKACiiigAooooAKKKKACiiigAoorifir8avBHwR8Oza5418RW\n" +
                "ehWEYyPPYtLIfRI1Bdz7KDQB21ecfGf9oj4ffADQ31Pxt4ks9IXYWitWkDXE+ATiOPOWJxX5q/tP\n" +
                "/wDBYjUfECz6H8HdOn0az5V9f1JVFxIP+mcQzsHX5ic+wr83vFPivWfG+u3eta/qd1q+q3bmSe8v\n" +
                "JWllkb1LE5NAH6JftH/8FjPE/iRrnSPhNpkXhzT2yh1q/j867cdjGp+RO/VWPTkV+e3jPx74j+Iu\n" +
                "tz6v4m1q91zUp3LvcXsxkOT1xngD2GBWDRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFF\n" +
                "FABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAT2N/c6ZdR3NncS2\n" +
                "lzGdyTQOUdT6gjkV9m/s6f8ABVP4r/BtrTTPEs0fjzw1GAhh1IYu4kAwPLmXBz0++Gr4rooA/of/\n" +
                "AGdv27/hR+0fDDbaLrkWk+IW4bRNTcRXBPH3M43jnqPyr6Ir+VqORoZFdGKOpyGU4INfbH7L/wDw\n" +
                "VM+IvwRey0jxa0/jvwpEBH5N1P8A6XAmMDy5WznHGFPGBjI60AfubRXh/wCz5+2b8K/2lbIHwn4g\n" +
                "WPVVUGbRtSX7Pdxn2U8P9ULCvcKACjtRR2oAKKKKACiiigAooooAKKKKACilpKACiiigAooooAKW\n" +
                "ikoAKKKKAFpKKDQAUUUUAFHWiigAoooJABJ4HrQAVn+IPEOmeFNFu9X1m+g0zTLRDLcXVzIEjjUd\n" +
                "yT0r5Q/ao/4KW/DT9nkXmi6Vcjxj40jQgafp53W8DdB5033RyOVUlvUCvx+/aI/a5+JH7TOrvP4t\n" +
                "1yc6Ushkt9FtpClnB6YjHBIHG4jPvQB+hP7Vn/BX6w8PSXPh/wCDVrBq16uUl8Ragha3Q/8ATGME\n" +
                "bj/tE49jX5d/Ej4seL/i7r82teMfEF9r+oytuMl3JkL7Ko+VR7ACuTooAKKKKACiiigAooooAKKK\n" +
                "KACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAoooo\n" +
                "AKKKKACiiigAooooAKKKKACiiigC5o+tah4e1GG/0u9uNPvYWDR3FtIY3U+xHNfoF+y1/wAFc/GP\n" +
                "w/lstC+KcP8Awl/h5cR/2rGu3UIB2LEfLKB6EA/7VfnjRQB/Tf8ACP41+DPjn4ZXXvBeu22tWGQs\n" +
                "nkt88LEfddeqn2NdxX8wvwy+LHi34OeJodf8Ha9e6DqcfBls5mQSL/dcA4ZfY5Ffqv8Asp/8FePD\n" +
                "vi2Ox8PfF+MeHtZYpDHr1vCWs52PGZQvMZJ6kDaM84FAH6SUlVNI1ix1/TbfUNMvINQsLhBJDc20\n" +
                "gkjkU9CrA4Iq3QAUUUUALSUUUALSUUtABSUtJQAtFJS0AFFFFACUUUUAFLSUUAFHalpDQAUUUUAF\n" +
                "FHNfHf7Zf/BRvwX+zdYXGh+H7m28VeP2Jj/s+3ffFY8fenccAg8bAd3qABQB9K/FL4ueEvgv4VuP\n" +
                "EXjHWrfRdLhH+smb5nP91V6sfYV+P37XX/BVbxd8YkuvDfw7ik8HeFGYrJehz9vvE6YZhgRqeu0Z\n" +
                "PT5q+SvjX8e/HH7QXi6fxB411261a5diYbd5CLe1XskUf3UH0Az1PJrz2gB888t1M800jyyudzO7\n" +
                "FmY+pJ60yiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiig\n" +
                "AooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKAP\n" +
                "pX9lb9vX4jfsvanFBZXX/CQ+FGOJ9B1GRjGAerRN1jb35HtX7P8A7Mn7Yvw//ak8PR3Xhy/FnraJ\n" +
                "uvNCu3AuLc5wcdN6+jDtjgV/OXWn4a8T6v4N1q11jQtTu9H1W1cPBeWM7QyxsO6spBFAH9StFfmN\n" +
                "+xZ/wVhtPEc9n4Q+NF3Dp18yCO18T+XthlcYAWcKMIT/AH8AZ69a/TGwv7bVLKC8sriK7tJ0EkU8\n" +
                "Dh0kUjIZWHBBHcUAT0UUUALRSUUALSUtJQAtJRRQAtFFFACUUUUAFFFBoAKKKKACqOua5p/hrSbr\n" +
                "VNVvIdP0+1jMk1zcOFRFHUkmud+LPxb8K/BHwPqHizxhq0GkaRZoWLzOA0r44jjXq7t0Cjk1+Gf7\n" +
                "aX7e/i39qbW5dMs7i50LwBbyE22jRPt+0ekk+Pvt6A8DsOtAH0j+2p/wVhvNZk1LwX8G3az04hoL\n" +
                "nxS/+tmBGGECY+Qcn5ySfQCvzJvLyfULmS4uZpLi4kYs8srFmYnuSahooAKKKKACiiigAooooAKK\n" +
                "KKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooo\n" +
                "oAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigABwa+wv2N/8Ago94\n" +
                "z/ZnkttA1lJPFngMyEtp80u2e0zjLQuQeO+w8H1Ga+PaKAP6b/g98a/B/wAd/CFv4k8GavFqunSj\n" +
                "5gOJIW7pIvVSK7iv5p/2fv2jPG37Nnje38R+D9WmtDkC7sS2be8j7pKh4b2PUHkEGv3a/ZH/AGx/\n" +
                "Bv7WHg/7ZpFzFYeJrNQNS0GaQCeA4H7xVPLRknhhxng80Ae/UUUUAFFFFABRRS0AFFJiigAooooA\n" +
                "KKKKACvIf2l/2oPBv7LfgZvEHiq5Z7iYMthpduQbi8kA+6oJ4AyMseBn8KZ+1D+054U/Zb+G134m\n" +
                "8Q3KSXrqY9N0pXHnX0/ZVHXaOrN0AB9q/AT9oP8AaC8XftJfEO78V+LtQe5mbMdpaLxDZw5yI41H\n" +
                "AHqepPJJoA3f2nf2rfGn7UnjW41nxHdNbaYsh+waNDITBaR/wqP7zY6tjk5PFeL0UUAFFFFABRRR\n" +
                "QAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFA\n" +
                "BRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAF\n" +
                "FFFABXSfD34i+IvhZ4ps/EXhfVJ9I1a0YNHPbuVPXOD6j2rm6KAP3j/YZ/4KEeH/ANqDTbXw5rwj\n" +
                "0L4iwQ/vbQsPJv8AauWkgPrgElD05wSK+xK/lq8N+JNU8H69Ya3ol/PperWEy3FreWzlJIpFOQwN\n" +
                "fuJ/wT8/b7079pjQV8LeKrm3sPiPYRAtGcRrqcYHMsY6bh/Eo6ZBAx0APtGiiigAooooAKKWigBK\n" +
                "KKWgBK8k/aY/aX8Kfsu/Dq48UeJpWmlbMdjpsBHn3kuOEUE8DkZY8DP0rsvij8StB+D/AIC1nxh4\n" +
                "lvFsdG0qDzppWPJJIVUX1ZmKqB3JFfz4ftc/tReIP2qPipeeItTleHRrctBpOm5wlrb7iRx3dupP\n" +
                "U8egoA5z9oT9oLxX+0l8Rb/xb4qui8szsLWyRiYbOHOViQHsBgZ79a8zoooAKKKKACiiigAooooA\n" +
                "KKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAo\n" +
                "oooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACii\n" +
                "igAooooAK0fDniPUvCOu2Os6PeS6fqdlKs1vcwNteNwcgg1nUUAfu7/wT+/b20z9pzw1b+GPEbLp\n" +
                "/wAR9OtwLhCQI9SVRzNF/tEDLJ2OcZFfZVfy7eA/HWt/DTxfpXifw5fyabrOmTrcW1xEeVZTnBHc\n" +
                "HoQeCCRX9A/7Fv7WWjftXfC6PVbdo7bxNpqxwazpw4MUpBw4H9x9rY+hHagD6DpKKWgBMUUZooAK\n" +
                "RmCKWY7VAySegpa+Jv8AgqB+1pL8AvhUvhTw/dLF4w8UQyRI45e1tTlXlHoxOQp9VNAHwz/wU4/b\n" +
                "Tf49eN5PAXhi7LeBtAu23Sxt8uoXSZUy+6LlgvrnNfC9BJJJJyT3NFABRRRQAUUUUAFFFFABRRRQ\n" +
                "AUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFAB\n" +
                "RRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFF\n" +
                "FFABRRRQAUUUUAFex/spftI65+y78W7DxbpDNLZuPs2pWO7C3VsSCyn3BAIPqK8cooA/qC+GnxE0\n" +
                "T4s+A9E8XeHbpbzR9WtluYJB1GRyrDsynKkdiDXTV+Pn/BI79rWXwj4qX4OeIbpf7F1eZpdGkk6w\n" +
                "XTdYgf7rkcD+8fev2CoAWiiigClrOr2nh/SL7U7+ZbeysoXuJ5XOAiKpZifwBr+cz9sH49XX7R/x\n" +
                "+8S+MJJHbTjJ9i0yJ/8AllaREiMAds5Zz7ua/Wr/AIKw/HGX4U/s0yaFp8xi1fxbdrpyMpwyW6gy\n" +
                "TMP++UT6SGvwtoAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAoo\n" +
                "ooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiii\n" +
                "gAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKALuh61e+Gtb0/V9Nu\n" +
                "HtNRsLiO6triM4aKVGDIwPqCAfwr+j/9lX472P7RvwQ8O+M7VlF1cQiG/gGP3N0oAkXA7Z5HsRX8\n" +
                "2dfpR/wRh+OMui/EXxL8ML6Ymx1m0/tGwDHhLiE4dQP9pHZj/wBcxQB+v9FLRQB+JP8AwWF+K7eN\n" +
                "P2irDwrBNI9h4XsfL2FvkE8pDSED1wqDPtXwbXrP7WHjp/iR+0b8QfELEeXd6vOYVH8MYYhB+QFe\n" +
                "TUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRR\n" +
                "QAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFA\n" +
                "BRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABXp/7MfxLn+EPx98D+LIJJIxYalG\n" +
                "JfKbBaJ/3cg+hV24rzCnQytBKkiHa6MGUjsR0oA/qfsbyLUbK3uoG3wTxrLG3qrDIP5GivjL4Vft\n" +
                "16TafC/wfBeWqyXcWjWaTOGI3OIEDH880UAfk9r/AOyN8dtX1u+vW+EfjUm4maUn+wbruc/886of\n" +
                "8Ma/HT/okXjT/wAEN1/8br+kOigD+bz/AIY1+On/AESLxp/4Ibr/AON0f8Ma/HT/AKJF40/8EN1/\n" +
                "8br+kOigD+bz/hjX46f9Ei8af+CG6/8AjdH/AAxr8dP+iReNP/BDdf8Axuv6Q6KAP5vP+GNfjp/0\n" +
                "SLxp/wCCG6/+N0f8Ma/HT/okXjT/AMEN1/8AG6/pDooA/m8/4Y1+On/RIvGn/ghuv/jdH/DGvx0/\n" +
                "6JF40/8ABDdf/G6/pDooA/m8/wCGNfjp/wBEi8af+CG6/wDjdH/DGvx0/wCiReNP/BDdf/G6/pDo\n" +
                "oA/m8/4Y1+On/RIvGn/ghuv/AI3R/wAMa/HT/okXjT/wQ3X/AMbr+kOigD+bz/hjX46f9Ei8af8A\n" +
                "ghuv/jdH/DGvx0/6JF40/wDBDdf/ABuv6Q6KAP5vP+GNfjp/0SLxp/4Ibr/43R/wxr8dP+iReNP/\n" +
                "AAQ3X/xuv6Q6KAP5vP8AhjX46f8ARIvGn/ghuv8A43R/wxr8dP8AokXjT/wQ3X/xuv6Q6KAP5vP+\n" +
                "GNfjp/0SLxp/4Ibr/wCN0f8ADGvx0/6JF40/8EN1/wDG6/pDooA/m8/4Y1+On/RIvGn/AIIbr/43\n" +
                "R/wxr8dP+iReNP8AwQ3X/wAbr+kOigD+bz/hjX46f9Ei8af+CG6/+N0f8Ma/HT/okXjT/wAEN1/8\n" +
                "br+kOigD+bz/AIY1+On/AESLxp/4Ibr/AON0f8Ma/HT/AKJF40/8EN1/8br+kOloA/m7/wCGNfjp\n" +
                "/wBEi8af+CG6/wDjdH/DGvx0/wCiReNP/BDdf/G6/pDooA/m8/4Y1+On/RIvGn/ghuv/AI3R/wAM\n" +
                "a/HT/okXjT/wQ3X/AMbr+kOigD+bz/hjX46f9Ei8af8Aghuv/jdH/DGvx0/6JF40/wDBDdf/ABuv\n" +
                "6Q6KAP5vP+GNfjp/0SLxp/4Ibr/43R/wxr8dP+iReNP/AAQ3X/xuv6Q6KAP5vP8AhjX46f8ARIvG\n" +
                "n/ghuv8A43R/wxr8dP8AokXjT/wQ3X/xuv6Q6KAP5vP+GNfjp/0SLxp/4Ibr/wCN0f8ADGvx0/6J\n" +
                "F40/8EN1/wDG6/pDpaAP5u/+GNfjp/0SLxp/4Ibr/wCN0f8ADGvx0/6JF40/8EN1/wDG6/pEpKAP\n" +
                "5vP+GNfjp/0SLxp/4Ibr/wCN0f8ADGvx0/6JF40/8EN1/wDG6/pEpKAP5vP+GNfjp/0SLxp/4Ibr\n" +
                "/wCN0f8ADGvx0/6JF40/8EN1/wDG6/pDpaAP5u/+GNfjp/0SLxp/4Ibr/wCN0f8ADGvx0/6JF40/\n" +
                "8EN1/wDG6/pDooA/m8/4Y1+On/RIvGn/AIIbr/43R/wxr8dP+iReNP8AwQ3X/wAbr+kSkoA/m8/4\n" +
                "Y1+On/RIvGn/AIIbr/43R/wxr8dP+iReNP8AwQ3X/wAbr+kOigD+bz/hjX46f9Ei8af+CG6/+N0f\n" +
                "8Ma/HT/okXjT/wAEN1/8br+kOigD+bz/AIY1+On/AESLxp/4Ibr/AON0f8Ma/HT/AKJF40/8EN1/\n" +
                "8br+kOloA/m7/wCGNfjp/wBEi8af+CG6/wDjdH/DGvx0/wCiReNP/BDdf/G6/pDooA/m8/4Y1+On\n" +
                "/RIvGn/ghuv/AI3R/wAMa/HT/okXjT/wQ3X/AMbr+kOigD+bz/hjX46f9Ei8af8Aghuv/jdH/DGv\n" +
                "x0/6JF40/wDBDdf/ABuv6RKKAP5u/wDhjX46f9Ei8af+CG6/+N0f8Ma/HT/okXjT/wAEN1/8br+k\n" +
                "OigD+bz/AIY1+On/AESLxp/4Ibr/AON0f8Ma/HT/AKJF40/8EN1/8br+kOloA/m7/wCGNfjp/wBE\n" +
                "i8af+CG6/wDjdH/DGvx0/wCiReNP/BDdf/G6/pEooA/m7/4Y1+On/RIvGn/ghuv/AI3R/wAMa/HT\n" +
                "/okXjT/wQ3X/AMbr+kOigD+bz/hjX46f9Ei8af8Aghuv/jdH/DGvx0/6JF40/wDBDdf/ABuv6RKK\n" +
                "AP5u/wDhjX46f9Ei8af+CG6/+N0f8Ma/HT/okXjT/wAEN1/8br+kOigD+bz/AIY1+On/AESLxp/4\n" +
                "Ibr/AON0f8Ma/HT/AKJF40/8EN1/8br+kSigD+bv/hjX46f9Ei8af+CG6/8AjdH/AAxr8dP+iReN\n" +
                "P/BDdf8Axuv6RKSgD8FtL+DXx70/TLO1Hwo8bAQQpFj+wrrsoH/PP2or96qKACk9KKKAAUUUUALS\n" +
                "GiigBaT1oooAO9AoooAWmiiigBe9FFFAAelHeiigBaT0oooADS0UUAFFFFACCloooAKQ9KKKADvS\n" +
                "0UUAFFFFABRRRQAUneiigANLRRQAlLRRQAUUUUAFJ60UUALRRRQAhpaKKACk70UUALSCiigBaQdK\n" +
                "KKACloooAKKKKACk70UUAFFFFAC0UUUAFFFFABSDpRRQAetLRRQAgoNFFAC0UUUAJS0UUAFJ60UU\n" +
                "ALRRRQAUUUUAf//Z";
    }
}

