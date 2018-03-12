/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.iqa.domain.countries.dao;

import com.iqa.domain.derived.country.model.Country;
import com.iqa.domain.countries.exception.CountryNotFoundException;
import com.iqa.domain.countries.model.Countries;


import java.util.List;

/**
 *TODO Deal with the Countries table and replace ExchangeRateFeed table below.
 * @author kwk
 */
public interface CountriesDao  {

    
    List<Country> getAllCountries();
    /**
     * To get a list of countries in
     *
     * @return a string list of countries that are available
     */
    
    List<String> getCountries();

    /**
     *
     * @param name
     * @return
     * @throws CountryNotFoundException
     */
    String findByCountryName(String name)throws CountryNotFoundException;
    int getCountryIdByCountryCode(String countryCode)throws CountryNotFoundException;
    
    /**
     *
     * @param countryCode
     * @return
     * @throws CountryNotFoundException
     */
    int getCountryIdByCountryCode2(String countryCode)throws CountryNotFoundException;
    /**
  */

    public Countries findByCountryCode(String countryCode)throws CountryNotFoundException;
    
    public String findISO2CountryCodeByCountryCode(String countryCode)throws CountryNotFoundException;
    public String convertISO2CountryCodeToISO3CountryCode(String iso2CountryCode) throws CountryNotFoundException;
    Boolean isValidISO3CountryCode(String countrtyCode);
    

    String getCountryNameFromCountryCode(String countryCode)throws CountryNotFoundException;

    /**
     *
     * @param countryCode
     * @return
     */
    public Boolean isValidISO2CountryCode(String countryCode);
    
    public List<Countries> getCountryList(int id, String countryCode) throws CountryNotFoundException;
    
    void saveOrUpdateCountry(Countries country) throws CountryNotFoundException;
    
    String getCountryCodeFromCountryId(int countryId) throws CountryNotFoundException;

    public Countries findByCountryId(int countryCode) throws CountryNotFoundException;

}
