//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.7 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2017.05.23 at 03:17:44 PM SAST 
//


package com.golden_xchange.controller.getuserprofile;

import javax.xml.bind.annotation.XmlRegistry;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the com.goldenriches.webservice.getuserprofile package. 
 * <p>An ObjectFactory allows you to programatically 
 * construct new instances of the Java representation 
 * for XML content. The Java representation of XML 
 * content can consist of schema derived interfaces 
 * and classes representing the binding of schema 
 * type definitions, element declarations and model 
 * groups.  Factory methods for each of these are 
 * provided in this class.
 * 
 */
@XmlRegistry
public class ObjectFactory {


    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: com.goldenriches.webservice.getuserprofile
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link GetUserProfileDetailsResponse }
     * 
     */
    public GetUserProfileDetailsResponse createGetUserProfileDetailsResponse() {
        return new GetUserProfileDetailsResponse();
    }

    /**
     * Create an instance of {@link PersonalDetails }
     * 
     */
    public PersonalDetails createPersonalDetails() {
        return new PersonalDetails();
    }

    /**
     * Create an instance of {@link BankDetails }
     * 
     */
    public BankDetails createBankDetails() {
        return new BankDetails();
    }

    /**
     * Create an instance of {@link GetUserProfileDetailsRequest }
     * 
     */
    public GetUserProfileDetailsRequest createGetUserProfileDetailsRequest() {
        return new GetUserProfileDetailsRequest();
    }

}
