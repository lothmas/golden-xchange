/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.golden_xchange.domain.utilities;

/**
 *
 * @author louis
 */
public class Context {

    /**
     *
     */
    public static ThreadLocal<String> ip = new ThreadLocal<String>();
}
