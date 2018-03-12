/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.iqa.domain.countries.service.impl;

import com.iqa.domain.derived.country.model.Country;

import com.iqa.domain.countries.dao.CountriesDao;
import com.iqa.domain.countries.exception.CountryNotFoundException;
import com.iqa.domain.countries.model.Countries;
import com.iqa.domain.countries.service.CountriesService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

/**
 *
 * @author kwk
 */
@Service("countriesService")
@Transactional(readOnly = true)
public class CountriesServiceImpl implements CountriesService {

    private final Logger log = Logger.getLogger(CountriesServiceImpl.class);
    @Autowired
    private CountriesDao countriesDao;


    /**
     * Gets all enabled currencies from the database
     *
     * @return a list of enabled currencies
     */
    @Override
    public List<String> getEnabledCountries(int userID, boolean quotedExchangeRate) {
        log.info(new Date() + "||Started a " + this.getClass().getName() + "  \"getEnabledCurrencies\" method");
        return countriesDao.getCountries();
    }

    /**
     *
     * @param name
     * @return
     * @throws CountryNotFoundException
     */
    @Override
    public String findCountryCodeByCountryName(String name) throws CountryNotFoundException {

        return countriesDao.findByCountryName(name);
    }


    @Override
    // @PreAuthorize("hasAnyRole('ROLE_Individual','ROLE_Corporate','ROLE_MTA')")
    public int getCountryIdFromCountryCode(String countryCode) throws CountryNotFoundException {
        return countriesDao.getCountryIdByCountryCode(countryCode);
    }

    @Override
    public List<Country> getAllCountries() {
        return countriesDao.getAllCountries();
    }

    @Override
    public Boolean iso3CountryCodeExists(String countryCode) {
        return countriesDao.isValidISO3CountryCode(countryCode);
    }

    @Override
    public String convertCountryCodeToISO2CountryCode(String countryCode) throws CountryNotFoundException {
        return countriesDao.findISO2CountryCodeByCountryCode(countryCode);
    }

    @Override
    public String getCountryNameFromCountryCode(String countryCode) throws CountryNotFoundException {
        return countriesDao.getCountryNameFromCountryCode(countryCode);
    }

    @Override
    public Boolean iso2CountryCodeExists(String iso2CountryCode) {
        return countriesDao.isValidISO2CountryCode(iso2CountryCode);
    }

    /**
     *
     * @param countryCode
     * @return
     * @throws CountryNotFoundException
     */
    @Override
    public Countries findByCountryCode(String countryCode) throws CountryNotFoundException {
        return countriesDao.findByCountryCode(countryCode);
    }

    @Override
    public Countries findByCountryId(int countryCode) throws CountryNotFoundException {
       return countriesDao.findByCountryId(countryCode);
    }


    @Override
    public int getCountryIdByCountryCode2(String countryCode) throws CountryNotFoundException {
        return countriesDao.getCountryIdByCountryCode2(countryCode);
    }

    @Override
    public String convertISO2CountryCodeToISO3CountryCode(String iso2CountryCode) throws CountryNotFoundException {
        return countriesDao.convertISO2CountryCodeToISO3CountryCode(iso2CountryCode);
    }
    
    @Override
    public List<Countries> getCountryList(int id, String countryCode) throws CountryNotFoundException{
        return countriesDao.getCountryList(id, countryCode);
    }
    
    @Override
    @Transactional(readOnly = false)
    public void saveOrUpdateCountry(Countries country) throws CountryNotFoundException{
         countriesDao.saveOrUpdateCountry(country);
    }
    
    @Override
    public String getCountryCodeFromCountryId(int countryId) throws CountryNotFoundException{
        return countriesDao.getCountryCodeFromCountryId(countryId);
    }

}
