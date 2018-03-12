/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.iqa.domain.countries.exception;

/**
 *
 * @author kwk
 */
public class CountryNotFoundException  extends Exception
{

//    private final Logger log = Logger.getLogger(CountryNotFoundException.class);

    /**
     *
     * @param message
     */
    public CountryNotFoundException(String message)
    {
        super(message);
//        log.debug("country not found error somewhere"); // Send the error message to the logger..
    }
}


