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
    public String handleCreateGoldenRichesRequest(Model model) {
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
            goldenRichesUsers.setReferenceUser("admin");
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
        return "/9j/4AAQSkZJRgABAQAAAQABAAD/2wCEAAkGBwgHBgkIBwgKCgkLDRYPDQwMDRsUFRAWIB0iIiAd\n" +
                "Hx8kKDQsJCYxJx8fLT0tMTU3Ojo6Iys/RD84QzQ5OjcBCgoKDQwNGg8PGjclHyU3Nzc3Nzc3Nzc3\n" +
                "Nzc3Nzc3Nzc3Nzc3Nzc3Nzc3Nzc3Nzc3Nzc3Nzc3Nzc3Nzc3Nzc3N//AABEIAUwBuwMBIgACEQED\n" +
                "EQH/xAAbAAEBAAMBAQEAAAAAAAAAAAAAAQIEBQYDB//EADkQAAIBAwIEAwQJAwUBAQAAAAABAgME\n" +
                "EQUhEjFBUQYTYSIycYEUI0JSkaGxwdEVYnIkM0NT8SUH/8QAGgEBAQADAQEAAAAAAAAAAAAAAAED\n" +
                "BAUCBv/EACkRAQACAgICAQMEAgMAAAAAAAABAgMRBBIhMUETIlEyQlJhBaEUcZH/2gAMAwEAAhED\n" +
                "EQA/AP3EAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAat7Q8yPHFe1H80c07bOZeUfKqZivZl+poc\n" +
                "rF++GbFb4a5SA0mdzL6j5dXiXuy3+DNY7Nen5tJw69DjtYbT5o53Ix9bbhsUtuGJDIhge0IUEWGI\n" +
                "KRh6RkaMiEGJCggxIZEYVCFAGLIzIhFYkMiBWIKQKEKAMSFAV+lAA+4fPgAAAAAAAAAAAAAAAAAA\n" +
                "AAAAAAAAAAAAAB861JVabi/k+x9ASYiY1I4souMmnzT3MTdv6WGqi67M0jkZaTS2m3W3aNhzr+lw\n" +
                "1FNcpfqdE+dzT8yjKPXmjWzU71ZKTqXIIUM5rZYkMiEEIUEemIKRhUI0ZEIMSGTRCDEhkRhUIUEV\n" +
                "iQyIBiQyIwrEFIFCYKAP0gAH3DgAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAABGUAYVIKcXF8mcicX\n" +
                "CTi+aZ2TQ1CniaqLk9manLx7r2j4ZcVvOmmADnNhy7un5dZro90fA6OoQzBTxyeGaGDmZq9bzDYp\n" +
                "O4YAyIYntiQyIBCFBHqGIKRhUMWZkZBiQyaIQYkMiMKhCgDEhkRkViQyZArEFAV+jgA+4cAAAAAA\n" +
                "ACAUEAFAAAAAAAAAAAAAAAAAAAAAAAAPlc0/Moyj1xsfUjJaNxpYnTikPrcw4K0l0e6PkcW1es6b\n" +
                "cTuNsK0eOlOPdHJOycqvHhrTXrsafKr6lmxy+bRCho02VgChkViCkAhCgj0xYKRhUMTMjIMSGTRC\n" +
                "DEhkQKhCgKxJgyIQYkMmTAV+jAA+4cEAAAAAAAABCgQFPnWqRpwcpvCRJmIFnOMMcUks8ixmpLMW\n" +
                "mvQ0G5SfmVPea2WfdRpVq1SEuOnNpnMv/k60vrXhmrhm3p3QcW11lyfDWj7Xx5nTpXVKpFPix8Tb\n" +
                "xcvFl9S82x2r7hsAxTT5FNljUEAFAAAAAAAAAAAAAaGow3hJfA0jqX0eKg/Tc5ZzOVXWTf5bOKd1\n" +
                "Dn30cV890joGnqC3g/kc/kRujNT20yGRDQbCNEKGQYAyIyKxIZEAhDIhFhiQyIw9IYmZGQYkMmiE\n" +
                "GLIZECoQoIMSGQCv0MAH3DhAAAAEYAAACkI+QCclGLlJpJLds51WspNVqmVBf7ccb/HHcyq1PpL2\n" +
                "3ox5f3vv8DQvLmKg43kJ0l0qweYr5rl80cfncz9lJZaUfWtc8VJ+U1CTePrFjHyOPXv5wj9dB5bw\n" +
                "tt/mYXVGMaalbRo1KWFw8W7+EXySffmcy4u24xhKT8xvLdWPXsl/HQ48RtvUrpsSrvzsxeDZr627\n" +
                "K2eOF1mnwqXL1b9EcZ1/LTnLeTlhKPOT6JH2sLSVeq69xiST5LdSafJekfzZdeXqfTZ0rXr7Q2rn\n" +
                "Wak6un3NRKq5e9aN4UZP+19UuX4nv4SU4RnCSlGSymns0eLuKFG5pSpXFKFWnLnCccp/I3vC04aZ\n" +
                "T/pzr/6fif0aNSW9NfcT6rt25Hb4XL39lmnmxa+6HqSBMp1GsgKQACkAFBAKCFAAADCsuKlJd0zj\n" +
                "HbZxZLEmuzNDmR5iWbDPtDVv17MH6m0a19/tx+Jzc36JbNfbRIZEOczoQoCo0QoZBgDIhFYkMiAQ\n" +
                "hQR62xYKQKhiZkIMSGTRCDFkMiBUIUAfoQAPt3DAAAIykAAAAaFzW86bo039XHapJdf7V+5neXD4\n" +
                "vIoyxPGZyX2F/OxpTqxpRUIJJLszlc/m/Tj6dPbLjx9pfWpUUVhYWOSPMXV/qdpUm60I1YNt7cl2\n" +
                "Sa5JeqOpcXOOpybu6znc4UWmZ3Lerj1DUuK9vUq1XbylSqRftyp7KTfdcn8cHKrXU5Tfmzjwx5yW\n" +
                "Vkxv7qME4UopcTy+FY37nxt5ULe0lqV+0rWnLFKEv+af8L8+RniPD1Murp9pUrTVSpmMmtl/1wfX\n" +
                "/KXTsjuRjGnBJKMYxWF0SR4Wn41rUuN8NpKDbk8uSk/j/wCHnde8T3Ooyl5tep5be1GHswX7v5mS\n" +
                "uC9peZtp7nXfF1tY1Y2enQV9fVGoRhTlmKk9km1zeeiN+nY3U7enU1GrGpcuC8yNPaEH2iv3PzLw\n" +
                "lc1rXxDZ140bZTqQl5UbjLUvRfdk+SfqfrmkXtvrFtOrbRlTq0nw1qM+dOX7r1Nia1xaiHjzPmV0\n" +
                "7XL3TWqdfiubZdG/bivR9fmet07UrXUaXmWtVSx70XtKPxR5G5oqMXKeElzb6HK86FKrG4s7lxqJ\n" +
                "4jOll/obeHlzHifLBfFE+n6cU8rpXirDjR1aKpy5KvFey/iuh6eE4zipwkpRkspp7M6NL1vG4a9q\n" +
                "zX2zIUHtAAAQoAAAARnHq7VZ/wCTOwzj1v8Aen/kzT5nqGbD7lga197sF6myal894L0ycrN+iWzX\n" +
                "21AUhz2ZAUhFQhQFTBiZBkGLIZGJFQhkQCEKCPTFgpAqEaMiEGJDJohBiwUBX6AAD7dwwAAACZAp\n" +
                "p3t0qEVGGHVn7qfT1fofS8uoWtF1J79IxXOT7HnLi7bnKpUadWXNrp2S9DQ5vLjBXVf1SzYcU5J/\n" +
                "pszr+XFpSzJvMpPm33NGtc+pz7nUadOpwzk02sptbGvWuVjmfOTE2ncuhWsV9Pvc3PPc4t/eqEW+\n" +
                "It1c4TZxYwuNVv6dparinN9eUV1b9EZqUj3KTLZ0mynq13Ljco28N61Tss7RXqzgeK9U/qV/5dJc\n" +
                "FpbfV0aa5RSP06xsqNjawtrfenT3c2t6kus3+3ofkuqW83q13bUKcpzVae0V6mfBaLXmfw8S5dSW\n" +
                "Fg+umabVvnK5nB/RKUlGUujl2JVnZ2blGvm5uUs+TT3jH/J9fkei8NeIbN2P9OulTVpLKjVpw4eB\n" +
                "8/aXx6m7abRXcQ8ajflwfEkpUKdrdU9pUqq/BnsY3dxRdDVtOreVd8C4n9mrHHKS6nH8Q6VP6PVt\n" +
                "pcMlOOaVRcpdmfXR7lV9BoJZ4oR8uWeacdjWzTulbR8Sz44idxL9O0TWLLxDaKVJxpXcI/W27e8X\n" +
                "3Xdepr3mnR4nxup8FN4Pyp3da0rxr21SdKrB5jODw0e50bWtc8R2tWnSnbU6lvGnmq4vM+LOz6Lb\n" +
                "c8+YjfwxTXU6dCdrTSSUeSwt8mVjqV3o8s29ROi3mVGo/Zfw7GjLw5rNw39J1pxXanksPBNrJ5ur\n" +
                "+6qvrvg9V5EUncSlse3vdG8Q2GrfVUKsY3KWZUJSXEl3XdHXPC+GvDml6Vq9vc21GXnR4oqpKbbW\n" +
                "Vg90jscbPGanZpZKdLaAAbLwoAAAACM403mcn6nYqPhi32RxTR5k+oZsMew0bx5rY7I3jnVZcVSU\n" +
                "u7OTyJ+3Tap7fMFZDTZEBSEVAUgVCFBFRohQ0QYkMjEioCkAhCgi7YsFIw9IyNGRCDEhcAmh78AH\n" +
                "27iAAAHyr1qdClKrVkowists+h5Px1O/cLenZOm4LM505PHH8H/OxizZPp0mz1SvadPjqOqfSazq\n" +
                "t4S2hH7q/k5NxX8xNPqcJalKVWVKop0qsedOaxJH1V1lbs+byRa9+1vbpViKxqGzUrVI7bTT5J8j\n" +
                "VzGnOcoTk1LdpybWRKr6nPvbpRi90kl3JWqvne3MpyUIJynJ8MYrm2+SPYaDpEdItHGph3lZZuJr\n" +
                "7K6QX7nP8J6S6EI6vew+tqL/AElOS92P3369j0PEoxcpPCW7bPOS37Yeax28sbiap0KkuOFPEW+K\n" +
                "b2j8T8h8T3vlydpp1xN0v+WVOPCpt/n82bnibxXcahc1IUcRtYyapxbftLu1yOh4a8NU61p9M1Hj\n" +
                "lUqwxTi3tHP2sd+X5mzhx/S+65a2/EPJS0+WiT8i5oqMqqUuNPijJPlh8muzRzrq3lTm7ixfDJby\n" +
                "hnmenq0puUNMq05XFpUqONPDXHQm+sc9H1jyfozh3VvUs6sqVSW8Pdn3/f8AdG9S253+WPXw2tB8\n" +
                "SqNL6Fexc7Z84fapv70f45M61nGFnd1OCop2N68xqrZRn+2e3c8nW0yrcUHc0KFWKj71TG34/sfb\n" +
                "Rr6FKc7S8qzo8f2ZR46c/wB0/gTJiras9f8AxaWmtvLs6jF06soS2aeGe/8AAKlZeHoXKpyqTvbq\n" +
                "UnGKy+BJJHhqlKWpUcW0/Or0Yvia3copZT+O2PwP1nw/bxstA06hjE4W8eLbq93+pz+RbWOKvet2\n" +
                "b+4MXUSPnKqkuePiasRt722bepGlcUpzkoxjLMm3hJHpotSWU8p8mjwd/fUfotaNOaq1HBqNOHtS\n" +
                "k8csI1/BN94jp6hRtrqzjS02eU41amZQ2yuH+PU7HAyxjjrPy1M9e07h+igiKdhqKAAAAA+N5Lho\n" +
                "S9Vg5Jv6lLEYQ7vJoHM5Vt31+GxijUMK0uCnJ+hzzavJe7BfE1jk57btps0jwhCgwPbEFZAICkIq\n" +
                "ApCKhCgKjRiZBogxZDIxIqEMiAQhQR6YsFIFRkwUEHvQAfbOIAACHn/FkcQtqnrKOfzPQHG8U0/O\n" +
                "01xjLhnF8UXjOHh/yY8te1Jh6rOp28XqNtaXlNQu6anj3XnEov0fNHAudPu7ROdrN3tBc0lirBeq\n" +
                "6/I+ta8uKVRwuNnn5MtO634oyal3Ryb4Zj226325ivYzi3TlnGzXVPszo+HNLWqV5Xl7H/59tLdP\n" +
                "/mn91ei6kvbW11J5rqVG4eyuKO0vmuTPTQdKFpQtrWn5VtRhwwgvzb9TTy/ZGoZY+5suvKrUc3t2\n" +
                "XZdjgeMdUla6TOhQy69z9VBR578/y/U6c6nBF5PLXv0vVr/goU/Lpx28ySw1HqzHgxza8ah6tMVj\n" +
                "y4ek6NRVaNW9SquLy4J7R9PVnubS7hPCytuTOLd6eraCVBPhivx+JpxrVIyzTeGjoZ+Nav6pYaZI\n" +
                "n01NQpuld1oVPZam/wD00NQlR1CSlcPE081Jcln7/wDP4m7rt07ilGo6bVaPsya6rucrSU613FNp\n" +
                "b7t8kedar2/D1vzp1LzTL6Eo0K1WnQppbPzE447rDOVd+Gp3040qS8+X/bGLil6n6p4UsrJ6HQq0\n" +
                "7GlThVlOdNOOXwuWzy+6WfmdyFOFNYpwjH/FJGp/y5paYr8MnXtHl+Y+DPDusaXefSrmjK4lw8Gf\n" +
                "c2z1b5vZHqfFGr6npGh3epws6D8iKflzqtt7pdFjqemaOP4tt1deGNVoP7VpU/KLf7Erl+rmi14L\n" +
                "Rqvh+c+FvGmv+JteoWTlRoUJzipKlDfGcvd+iZ+qf0+0by6Kn1+sbl+p+T//AIlZceozu2v9qnOe\n" +
                "fV+wv1Z+wucV2MnOmK5utI1EMeHc13KRhGEeGEYxXZLB9bR4vKL/AL0a0q8F9pfiZW1eMrqko7vj\n" +
                "jy+JiwxPeHu+tPXFIEfUuYoAAEKfOrPy6cpPoiTOo2OdeT4677R2PgVvLbfN8z4XM+GHD1Zxct/d\n" +
                "pblY+GrUk5zcjEowcyZ3O2dCFBFQhQQYgpAICkIqApAqEKCCMhQ0RWLIZGJFQFIBCFBFhiCgK92A\n" +
                "D7VxQAAQ1r+gq9Fo2iMD891nRcuT4NjytzYVbeTdNNrsz9jubSFZPKPOajoqeWo5R5tSLR5WJmPT\n" +
                "86p1mnh5yueTv6bdKpR4X7yF/oeW3w4fdHOhZ3lpUzGEpx7xW/4HM5XEmY3Vs4s0b8ujcTc5cEct\n" +
                "vbCNu1s1RpYfvy95/sZ6XYVW/Oqxam+SfRfyduhp7lzRm4XG+nXtb28ZsnadQ89Xs/MTWDgajpdS\n" +
                "lLjgj9LjpqxyR8LnSIzi045N21YtGpYYnXp+T1KKrQxJb8ji1badpVusJqPk5WPVqP7n6HrPh+tb\n" +
                "ydWhDbqjQsLO3uqzp3lPMduJcnlNNfocrk1nBuZ9NvFaL/8Ab2lnRjZ2NtbLZUqUYfgizrwjzkkc\n" +
                "a61SU5PEsvsuZqSV7X92nJes9jlYuLkyTvTYnJWsamXaq6hSh9rJzr/U4VbetSwmp05R39Vg+EdM\n" +
                "rSeatR/CKNuhpVOLz5eX3ludLF/jb73PhgtyI9Q854JorRNDpW9O3cbzhUa0knmWG3+rO9B31w8Y\n" +
                "4E/vP+Dq0tPk8ZR07TTsNbG5HAx9ptbzMsM57a1Di2ui1qz+sqzfotkeh0nRoWk1USXEur3Z07a3\n" +
                "jTS2NjBtUw46eoYptafciBQZHkAAA0dQqYSprruzck1FNvkjkVZupUlPuavKydadY+WTHXc7YPZb\n" +
                "mjWn5k3L8D73VTEeBc3zNU4We+/thuVj5AAa72MhRgCEKAqEKCDEFIBAUhFQhkQioQyIFRkKMEGL\n" +
                "IUMisQUgEIUEXb3QAPtXGAABCgAQxnBSWGZgDn19Op1OiNZaNTTzwo7AwBo0bCFPkjZjQiuh9SgY\n" +
                "cCReBNblKB8KtrTqxcZRTXqjiXXhm1rVPM8tZ7noiEmIn2bcCj4fpUliMYr4I2o6RTX2UdUFiNeh\n" +
                "y/6VBdEZw0+Meh0cFwBqwtYx6H1jSjE+gAFIABSFAAGFSahByfJEmdRsauoVcRVNc3zOfOShFtmd\n" +
                "SbqTcpdTSr1OOWFyicXk59zNm5jpqNPnKTnJuXUgBzGdAUhAAABkKGgIQoCoRlBBiCkAgKQioQyI\n" +
                "FQhkQioyFDRBgDIhFYgpAPcgA+0ccAAAAAAAAAAAAAAABAUAACAUAgFAAAgKBCgARnPvq3FPy1yX\n" +
                "P1Zs3dbyoez775HIrVPLXPLfQ0eXnisdWbFTc7YXNTC4FzfM1SttvL5kOFe83nbbiNQAA8KAAKgK\n" +
                "QgAAAyFDAhCgKhGUEGIKQCApCKhDIgVCGRCKjIUNEGAKCK9uAD7RxwAAAAAAAAAAAAAAAAAAAAAA\n" +
                "AAAACFAAwqTVODlJ7Iyckk29kjlXdx5ks5xCPIw580Y6/wBvdK9pfO5r8UnUn8kc+cnJ8T6mVWfH\n" +
                "LPRckYHz2bLN7N2tdICgwPSAAAAAAACoCkIAAAYIUMCEKAqEKCDEFIBAUhFQhkQioQyIBGQoIr2o\n" +
                "APs3IAAAAAAAAAAAAAAAAAAAAAAAAAAAI3hZDNC8uM/Vw5dWjHkyRjruXqtZtLC7ueNuMXiC5vuc\n" +
                "yvUc3hP2f1Mq1XPsx5dT4nA5Geclm5SkRCAA1WQAAEBSEAAAAAAAAVAUhAAADBChgQhQFQhQQYgr\n" +
                "IBAUhFQhkQKhCgg9oAD7JyQAAAAAAAAAAAAAAAAAAAAAAAAmSmreXHlrhh77/I8XvFI3KxG50wvL\n" +
                "jhzTg9+r7HKr1fsx+Za9VrMU/afNmscLlcib2bmOnUABpMoQoAgAAAACAoIIAAAAAAAKgKQgAABg\n" +
                "hQwIQoCoQoIMQVkAgKQioCkIr2YAPsnJAAAAAAAAAAAAAAAAAAAAAAA+VaqqUOJ/JdyTMRG5GNzc\n" +
                "KjHvJ8kcivWcW295syuKzbc5vd8jTbcnl9Ti8vkzbxDbx49RtH6sAHOZkBSEUAAAhQBAAAAAAhSE\n" +
                "AAAAAAAAVAUhAAABohQwIQoCoQoIMQUgEBQRXsQAfYuUAAAAAAAAAAAAAAAAAAAAAMZSUYuT2SRy\n" +
                "bq4dSTm3iK90+17X45OEX7Meb7nMqz43/b0OVzOTr7YbGKnyxnJyeTEA5O9tkABAABBAUgUAAAhQ\n" +
                "BAAAAAAhWQgAAAAAAACoCkIAAANEKGgIQoCoQoIMQUAewAB9g5YAAAAAAAAAAAAAAAAAABq3tby4\n" +
                "8EX7UvyPvUmqcHKXJHGuKzbdSXNvZGrys3066+WTHXtL5Vp/ZXzPgHu8g+fvbtO25EBCg8qgAAAA\n" +
                "AAAICkIoAABCgCAAAAAICkIAAAAAAAAqApCAAADIUYAhCgKgAIPXgA+wcsAAAAAAAAAAAAAAAAAP\n" +
                "nXqeVTlLsSZ1GyPLSv6vFLy09o8/icurPjn6Lkfa4m8PvI1j5/lZe9m7jrqAAGoyAAAEKAIAAAAA\n" +
                "AACApCKAAAQoAgAAAACAoIIAAAAAAAKgKQgAAAyFGAIAAr1wAPr3LAAAAAAAAAAAAAAAADn6hUzN\n" +
                "QXJbs3+hxruT46suuWavLv1pr8smKN2alSXFN+hgEU+fmdztuIACKAAgAAAQoYEAAAAAAAQQFZAo\n" +
                "AAgQoYVAAAABBAUgAAACTfDCUsZwm8FAHAo61qVbGNJcM0qc26lRpLjlhLZdtz5R8T1EpKpptdTU\n" +
                "5xzFNwXDybljZN5XLoel6hsyd6fxTU/l59+IZRqOM7RqCqKHmLicZp53j7O/ZeufTPzh4jryxxab\n" +
                "Ui93wty4msN5S4eSxv8A5Lmejba69xl5ayy9qfx/2dbfl56t4iqwnwrT6izL2ak21Tcdt8pPHvf+\n" +
                "7mxS1uU7WrVdlW82nVUPo8d5NNxy+2Vx7r0wdhPC2KSbU/iurflw7DWbm9qwUbJU4urGEuObUopw\n" +
                "4s44fRrc7WMiTayDxaYmfthY/t//2Q==";
    }
}

