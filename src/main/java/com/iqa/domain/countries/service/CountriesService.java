/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.iqa.domain.countries.service;

import com.iqa.domain.derived.country.model.Country;
import com.iqa.domain.countries.exception.CountryNotFoundException;
import com.iqa.domain.countries.model.Countries;
import java.util.List;

/**
 *
 * @author kwk
 */
public interface CountriesService {

    List<Country> getAllCountries();

    List<String> getEnabledCountries(int userID, boolean quotedExchangeRate);

    int getCountryIdFromCountryCode(String countryCode) throws CountryNotFoundException;


    String findCountryCodeByCountryName(String name) throws CountryNotFoundException;

    /**
     *
     * @param countryCode
     * @return
     */
    Boolean iso3CountryCodeExists(String countryCode);


    public String convertCountryCodeToISO2CountryCode(String iso3CountryCode) throws CountryNotFoundException;

    public String convertISO2CountryCodeToISO3CountryCode(String iso2CountryCode) throws CountryNotFoundException;

    public Boolean iso2CountryCodeExists(String iso2CountryCode);

    public String getCountryNameFromCountryCode(String countryCode) throws CountryNotFoundException;

    public Countries findByCountryCode(String countryCode) throws CountryNotFoundException;

    public Countries findByCountryId(int countryCode) throws CountryNotFoundException;


    /**
     *
     * @param countryCode
     * @return
     * @throws CountryNotFoundException
     */
    int getCountryIdByCountryCode2(String countryCode) throws CountryNotFoundException;

    public List<Countries> getCountryList(int id, String countryCode) throws CountryNotFoundException;

    void saveOrUpdateCountry(Countries country) throws CountryNotFoundException;
    
    String getCountryCodeFromCountryId(int countryId) throws CountryNotFoundException;

}
