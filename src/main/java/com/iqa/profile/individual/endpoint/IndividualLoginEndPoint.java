package com.iqa.profile.individual.endpoint;

import com.iqa.profile.individual.exception.IndividualProfileNotFoundException;
import com.iqa.profile.individual.model.LoginRequest;
import com.iqa.profile.individual.service.IndividualProfileService;
import com.iqa.utilities.Enums;
import com.iqa.utilities.GenericResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.security.NoSuchAlgorithmException;

@Component
public class IndividualLoginEndPoint extends GenericResponse{

@Autowired
    IndividualProfileService individualProfileService;


    public boolean handleIndividualLogin(LoginRequest request) throws IndividualProfileNotFoundException, NoSuchAlgorithmException {


        try {

//            if (null == request.getPassword() || request.getPassword().equals("")) {
//                setMessage("Password Can't be Left Empty");
//                setStatusCode(Enums.StatusCodeEnum.EMPTYVALUE.getStatusCode());
//                return false;
//
//            }

            if (null == request.getUsername() || request.getUsername().equals("")) {
                setMessage("Username / EmailAddress Can't be Left Empty");
                setStatusCode(Enums.StatusCodeEnum.EMPTYVALUE.getStatusCode());
                return false;
            }

            setMessage("User: " + request.getUsername() + " Successfully LoggedIn");
            setStatusCode(Enums.StatusCodeEnum.OK.getStatusCode());
            setIndividualProfileEntity(individualProfileService.findIndividualProfileByUsernameAndPassword(request.getUsername(), request.getPassword()));
            return true;

        } catch (IndividualProfileNotFoundException grunf) {

            try {

                setIndividualProfileEntity(individualProfileService.findIndividualProfileByEmailAndPassword(request.getUsername(), request.getPassword()));
                setMessage("User: " + getIndividualProfileEntity().getUsername() + " Successfully LoggedIn");
                setStatusCode(Enums.StatusCodeEnum.OK.getStatusCode());
                return true;
            } catch (IndividualProfileNotFoundException unf) {
                setMessage("Please Enter Correct Credentials");
                setStatusCode(Enums.StatusCodeEnum.NOTAUTHORISED.getStatusCode());
                return false;
            }


        }
    }

}
