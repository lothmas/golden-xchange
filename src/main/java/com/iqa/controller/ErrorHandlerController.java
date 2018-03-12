package com.iqa.controller;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.NoHandlerFoundException;

import javax.servlet.http.HttpServletRequest;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ErrorHandlerController {


    @ControllerAdvice
    public class ExceptionController {
        @ExceptionHandler(Exception.class)
        public String handleError(HttpServletRequest request, Exception e)   {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, "Request: " + request.getRequestURL() + " raised " + e);
            return "index";
        }

        @ExceptionHandler(NoHandlerFoundException.class)
        public String handleError404(HttpServletRequest request, Exception e)   {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, "Request: " + request.getRequestURL() + " raised " + e);
            return "index";
        }
    }
}
