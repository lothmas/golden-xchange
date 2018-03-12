/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.iqa.domain.derived.country.model;

import java.util.Comparator;
import java.util.Objects;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author kwk
 */
@XmlRootElement
public class Country {

    private String countryName;
    private String countryCode;
    private int countryId;
    private String continent;
    private String dialingCode;

    /**
     * @return the countryName
     */
    public String getCountryName() {
        return countryName;
    }

    /**
     * @param countryName the countryName to set
     */
    public void setCountryName(String countryName) {
        this.countryName = countryName;
    }

    /**
     * @return the countryCode
     */
    public String getCountryCode() {
        return countryCode;
    }

    /**
     * @param countryCode the countryCode to set
     */
    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode;
    }

    /**
     * @return the countryId
     */
    public int getCountryId() {
        return countryId;
    }

    /**
     * @param countryId the countryId to set
     */
    public void setCountryId(int countryId) {
        this.countryId = countryId;
    }

    /**
     * @return the continent
     */
    public String getContinent() {
        return continent;
    }

    /**
     * @param continent the continent to set
     */
    public void setContinent(String continent) {
        this.continent = continent;
    }
    /*Comparator for sorting the list by Continent Name*/
    public static Comparator<Country> CountryContinentComparator = (Country s1, Country s2) -> {
        String continent1 = s1.getContinent().toUpperCase();
        String continent2 = s2.getContinent().toUpperCase();
        
        //ascending order
        return continent1.compareTo(continent2);
        
        //descending order
        //return StudentName2.compareTo(StudentName1);
    };

    /*Comparator for sorting the list by Continent Name*/
    /**
     *
     */
    public static Comparator<Country> CountryNameComparator = (Country s1, Country s2) -> {
        String countryName1 = s1.getCountryName().toUpperCase();
        String countryName2 = s2.getCountryName().toUpperCase();
        
        //ascending order
        return countryName1.compareTo(countryName2);
        
        //descending order
        //return StudentName2.compareTo(StudentName1);
    };

    @Override
    public boolean equals(Object obj) {

        if (obj == null || !(obj instanceof Country)||null==((Country) obj).getCountryCode()) {

            return false;

        }

        return (this.getCountryCode().equalsIgnoreCase(((Country) obj).getCountryCode()));

    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 13 * hash + Objects.hashCode(this.countryCode);
        return hash;
    }

    /**
     * @return the dialingCode
     */
    public String getDialingCode() {
        return dialingCode;
    }

    /**
     * @param dialingCode the dialingCode to set
     */
    public void setDialingCode(String dialingCode) {
        this.dialingCode = dialingCode;
    }

}
