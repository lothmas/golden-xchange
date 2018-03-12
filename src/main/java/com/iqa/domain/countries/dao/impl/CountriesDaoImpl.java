/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.iqa.domain.countries.dao.impl;

import com.iqa.domain.derived.country.model.Country;
import com.iqa.domain.countries.dao.CountriesDao;
import com.iqa.domain.countries.exception.CountryNotFoundException;
import com.iqa.domain.countries.model.Countries;
import com.iqa.utilities.AbstractDaoImpl;
import org.apache.log4j.Logger;
import org.apache.log4j.NDC;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.*;

/**
 *
 * @author kwk
 */
@Repository
public class CountriesDaoImpl extends AbstractDaoImpl<Countries, String> implements CountriesDao {

    private final Logger log = Logger.getLogger(CountriesDaoImpl.class);

       /**
     *
     */
    protected CountriesDaoImpl() {
        super(Countries.class);
    }

    /**
     * @param name
     * @throws
     * CountryNotFoundException
     * @notes This currently returns all currencies from the database, in future
     * replace it with the next function i.e. after the database has been
     * refactored
     * @return a string list of currencies that are available
     */
    @Override
    public String findByCountryName(String name) throws CountryNotFoundException {

        String result;
        List<Countries> country = getCurrentSession().createCriteria(Countries.class)
                .add(Restrictions.eq("name", name)).setCacheable(true)
                .list();

        if (country == null) {

            throw new CountryNotFoundException("No country found associated with country name :" + name);

        }
        result = country.get(0).getCode();
        return result;
    }

    @Override
    public List<String> getCountries() {

        log.info(new Date() + "||Started a " + this.getClass().getName() + "  \"getCountries\" method");

        List<String> resultList = new ArrayList<String>();

        try {
            List<Countries> myFeed = getCurrentSession().createCriteria(Countries.class)
                    .add(Restrictions.sqlRestriction("id LIKE '%'")).setCacheable(true).list();
            for (int i = 0; i < myFeed.size(); i++) {
                resultList.add(myFeed.get(i).getName());
            }
        } catch (Exception e) {
            log.error(new Date() + "||" + this.getClass().getName() + " \t :" + e.toString());
        }

        Collections.sort(resultList, new Comparator<String>() {

            @Override
            public int compare(final String c1, final String c2) {
                return c1.compareTo(c2);
            }
        });
        return resultList;
    }

    /**
     * To get a list of allowed payin countries
     *
     * @return a list of countries objects that are available
     */

    @Override
    public Countries findByCountryCode(String countryCode) throws CountryNotFoundException {

        List<Countries> country = getCurrentSession().createCriteria(Countries.class)
                .add(Restrictions.eq("code", countryCode)).setCacheable(true)
                .list();

        if (country.isEmpty()) {

            throw new CountryNotFoundException("No country found associated with country code :" + countryCode);

        }

        if (country.size() != 1) {
            throw new CountryNotFoundException("There are several countries found associated with country code :" + countryCode);
        }

        return country.get(0);
    }


    @Override
    public int getCountryIdByCountryCode(String countryCode) throws CountryNotFoundException {

        List<Countries> country = getCurrentSession().createCriteria(Countries.class)
                .add(Restrictions.eq("code", countryCode)).setCacheable(true)
                .list();

        if (country.isEmpty()) {

            throw new CountryNotFoundException("No country found associated with country code :" + countryCode);

        }

        if (country.size() != 1) {
            throw new CountryNotFoundException("There are several countries found associated with country code :" + countryCode);
        }

        return country.get(0).getId();
    }

    @Override
    public int getCountryIdByCountryCode2(String countryCode) throws CountryNotFoundException {

        List<Countries> country = getCurrentSession().createCriteria(Countries.class)
                .add(Restrictions.eq("code", countryCode)).setCacheable(true)
                .list();

        if (country.isEmpty()) {

            throw new CountryNotFoundException("No country found associated with country code :" + countryCode);

        }

        if (country.size() != 1) {
            throw new CountryNotFoundException("There are several countries found associated with country code :" + countryCode);
        }

        return country.get(0).getId();
    }

    @Override
    public List<Country> getAllCountries() {
        List<Country> result = new ArrayList<>();

        List<Countries> myFeed = getCurrentSession().createCriteria(Countries.class)
                .addOrder(Order.asc("continent"))
                .add(Restrictions.ne("name","International"))
                .addOrder(Order.asc("name")).setCacheable(true)
                .list();

        for (Countries a : myFeed) {
            Country e = new Country();
            e.setContinent(a.getContinent());
            e.setCountryCode(a.getCode());
            e.setCountryId(a.getId());
            e.setCountryName(a.getName());
            e.setDialingCode(a.getDialingCode());
            result.add(e);
        }
        return result;
    }

    @Override
    public Boolean isValidISO3CountryCode(String countryCode) {
        Boolean sendResult = true;

        List<Countries> country = getCurrentSession().createCriteria(Countries.class)
                .add(Restrictions.eq("code", countryCode))
                .list();

        if (country.isEmpty()) {
            sendResult = false;
            System.out.println("\n\nThere is no country with the countryCode :" + countryCode);
        }

        if (country.size() > 1) {
            sendResult = false;
            System.out.println("\n\nThere are several countries found associated with country code :" + countryCode);
        }

        return sendResult;
    }



    /**
     *
     * @param countryCode
     * @return
     * @throws CountryNotFoundException
     */
    @Override
    public String findISO2CountryCodeByCountryCode(String countryCode) throws CountryNotFoundException {

        List<Countries> country = getCurrentSession().createCriteria(Countries.class)
                .add(Restrictions.eq("code", countryCode)).setCacheable(true)
                .list();

        if (country.isEmpty()) {

            throw new CountryNotFoundException("No country found associated with country code :" + countryCode);

        }

        if (country.size() != 1) {
            throw new CountryNotFoundException("There are several countries found associated with country code :" + countryCode);
        }

        return country.get(0).getIso2Code();

    }

    /**
     *
     * @param iso2CountryCode
     * @return
     * @throws CountryNotFoundException
     */
    @Override
    public String convertISO2CountryCodeToISO3CountryCode(String iso2CountryCode) throws CountryNotFoundException {

        List<Countries> country = getCurrentSession().createCriteria(Countries.class)
                .add(Restrictions.eq("iso2Code", iso2CountryCode)).setCacheable(true)
                .list();

        if (country.isEmpty()) {

            throw new CountryNotFoundException("No country found associated with iso2CountryCode :" + iso2CountryCode);

        }

        if (country.size() != 1) {
            throw new CountryNotFoundException("There are several countries found associated with iso2CountryCode :" + iso2CountryCode);
        }

        return country.get(0).getCode();
    }

    @Override
    public String getCountryNameFromCountryCode(String countryCode) throws CountryNotFoundException {
        List<Countries> country = getCurrentSession().createCriteria(Countries.class)
                .add(Restrictions.eq("code", countryCode)).setCacheable(true)
                .list();
        if (country == null || country.isEmpty()) {

            throw new CountryNotFoundException("No country found associated with country name :" + country);

        }
        return country.get(0).getName();
    }

    @Override
    public Boolean isValidISO2CountryCode(String countryCode) {

        Boolean sendResult = true;

        List<Countries> country = getCurrentSession().createCriteria(Countries.class)
                .add(Restrictions.eq("iso2Code", countryCode)).setCacheable(true)
                .list();

        if (country.isEmpty()) {
            sendResult = false;
        }

        if (country.size() > 1) {
            sendResult = false;
        }

        return sendResult;
    }

    @Override
    public List<Countries> getCountryList(int id, String countryCode) throws CountryNotFoundException {

        List<Countries> countryList = new ArrayList<>();

        if (id != 0) {
            countryList = getCurrentSession().createCriteria(Countries.class)
                    .add(Restrictions.eq("id", id)).setCacheable(true)
                    .list();
        } else if (null != countryCode && !countryCode.isEmpty()) {
            countryList = getCurrentSession().createCriteria(Countries.class)
                    .add(Restrictions.eq("code", countryCode)).setCacheable(true)
                    .list();
        } else {
            countryList = getCurrentSession().createCriteria(Countries.class)
                    .list();
        }

        if (countryList.isEmpty()) {

            throw new CountryNotFoundException("No country found associated with id :" + id + ", or countryCode : " + countryCode);

        }

        return countryList;
    }

    @Override
    public void saveOrUpdateCountry(Countries country) throws CountryNotFoundException {
        saveOrUpdate(country);
    }
    
    @Override
    public String getCountryCodeFromCountryId(int countryId) throws CountryNotFoundException{
        List<Countries> country = getCurrentSession().createCriteria(Countries.class)
                .add(Restrictions.eq("id", countryId)).setCacheable(true)
                .list();

        if (country.isEmpty()) {

            throw new CountryNotFoundException("No country found associated with country id :" + countryId);

        }

        if (country.size() != 1) {
            throw new CountryNotFoundException("There are several countries found associated with country id :" + countryId);
        }

        return country.get(0).getCode();
    }

    @Override
    public Countries findByCountryId(int countryId) throws CountryNotFoundException {
        List<Countries> country = getCurrentSession().createCriteria(Countries.class)
                .add(Restrictions.eq("id", countryId)).setCacheable(true)
                .list();

        if (country.isEmpty()) {

            throw new CountryNotFoundException("No country found associated with country id :" + countryId);

        }

        if (country.size() != 1) {
            throw new CountryNotFoundException("There are several countries found associated with country id :" + countryId);
        }

        return country.get(0);

    }


}
