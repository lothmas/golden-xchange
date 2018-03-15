//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.7 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2017.05.23 at 03:17:44 PM SAST 
//


package com.golden_xchange.controller.getcompleteddonations;

import javax.xml.bind.annotation.*;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * <p>Java class for CompletedDonationsList complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="CompletedDonationsList">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;all>
 *         &lt;element name="payerUserNamer" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="mainListReference" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="depositReference" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="dateCreated" type="{http://www.w3.org/2001/XMLSchema}dateTime"/>
 *         &lt;element name="status" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="amountPaid" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *       &lt;/all>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "CompletedDonationsList", propOrder = {

})
public class CompletedDonationsList {

    @XmlElement(required = true)
    protected String payerUserNamer;
    @XmlElement(required = true)
    protected String mainListReference;
    @XmlElement(required = true)
    protected String depositReference;
    @XmlElement(required = true)
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar dateCreated;
    protected int status;
    protected double amountPaid;

    /**
     * Gets the value of the payerUserNamer property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPayerUserNamer() {
        return payerUserNamer;
    }

    /**
     * Sets the value of the payerUserNamer property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPayerUserNamer(String value) {
        this.payerUserNamer = value;
    }

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
     * Gets the value of the depositReference property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDepositReference() {
        return depositReference;
    }

    /**
     * Sets the value of the depositReference property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDepositReference(String value) {
        this.depositReference = value;
    }

    /**
     * Gets the value of the dateCreated property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getDateCreated() {
        return dateCreated;
    }

    /**
     * Sets the value of the dateCreated property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setDateCreated(XMLGregorianCalendar value) {
        this.dateCreated = value;
    }

    /**
     * Gets the value of the status property.
     * 
     */
    public int getStatus() {
        return status;
    }

    /**
     * Sets the value of the status property.
     * 
     */
    public void setStatus(int value) {
        this.status = value;
    }

    /**
     * Gets the value of the amountPaid property.
     * 
     */
    public double getAmountPaid() {
        return amountPaid;
    }

    /**
     * Sets the value of the amountPaid property.
     * 
     */
    public void setAmountPaid(double value) {
        this.amountPaid = value;
    }

}
