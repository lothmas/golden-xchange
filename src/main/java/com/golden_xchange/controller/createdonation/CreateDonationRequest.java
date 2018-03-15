//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.7 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2017.05.23 at 03:17:44 PM SAST 
//


package com.golden_xchange.controller.createdonation;

import javax.xml.bind.annotation.*;


/**
 * <p>Java class for anonymous complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType>
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;all>
 *         &lt;element name="mainListReference" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="amount" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="bankAccountNumber" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="payerUsername" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *       &lt;/all>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {

})
@XmlRootElement(name = "CreateDonationRequest")
public class CreateDonationRequest {

    @XmlElement(required = true)
    protected String mainListReference;
    protected double amount;
    @XmlElement(required = true)
    protected String bankAccountNumber;
    @XmlElement(required = true)
    protected String payerUsername;

    /**
     * Gets the value of the mainListReference property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getMainListReference() {
        return mainListReference;
    }

    /**
     * Sets the value of the mainListReference property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setMainListReference(String value) {
        this.mainListReference = value;
    }

    /**
     * Gets the value of the amount property.
     * 
     */
    public double getAmount() {
        return amount;
    }

    /**
     * Sets the value of the amount property.
     * 
     */
    public void setAmount(double value) {
        this.amount = value;
    }

    /**
     * Gets the value of the bankAccountNumber property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getBankAccountNumber() {
        return bankAccountNumber;
    }

    /**
     * Sets the value of the bankAccountNumber property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setBankAccountNumber(String value) {
        this.bankAccountNumber = value;
    }

    /**
     * Gets the value of the payerUsername property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPayerUsername() {
        return payerUsername;
    }

    /**
     * Sets the value of the payerUsername property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPayerUsername(String value) {
        this.payerUsername = value;
    }

}
