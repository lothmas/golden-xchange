package com.iqa.utilities;

import com.iqa.profile.individual.model.IndividualProfileEntity;

/**
 * Created by kwk on 2018/05/17.
 */
public  class GenericResponse {

    String message;
    int statusCode;
    IndividualProfileEntity individualProfileEntity;

    public GenericResponse() {
    }

    public GenericResponse(String message, int statusCode) {
        this.message = message;
        this.statusCode = statusCode;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(int statusCode) {
        this.statusCode = statusCode;
    }

    public IndividualProfileEntity getIndividualProfileEntity() {
        return individualProfileEntity;
    }

    public void setIndividualProfileEntity(IndividualProfileEntity individualProfileEntity) {
        this.individualProfileEntity = individualProfileEntity;
    }
}
