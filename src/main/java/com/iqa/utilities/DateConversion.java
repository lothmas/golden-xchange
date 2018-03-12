//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.iqa.utilities;

import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.GregorianCalendar;

public class DateConversion {
    public DateConversion() {
    }

    public XMLGregorianCalendar dateToXMLGregorianCalendar(Date date) throws DatatypeConfigurationException {
        DatatypeFactory df = DatatypeFactory.newInstance();
        if(date == null) {
            return null;
        } else {
            GregorianCalendar gc = new GregorianCalendar();
            gc.setTimeInMillis(date.getTime());
            return df.newXMLGregorianCalendar(gc);
        }
    }

    public XMLGregorianCalendar timeStampToGregorian(Timestamp ts) throws DatatypeConfigurationException {
        ts.setNanos(123456789);
        LocalDateTime ldt = ts.toLocalDateTime();
        XMLGregorianCalendar cal = DatatypeFactory.newInstance().newXMLGregorianCalendar();
        cal.setYear(ldt.getYear());
        cal.setMonth(ldt.getMonthValue());
        cal.setDay(ldt.getDayOfMonth());
        cal.setHour(ldt.getHour());
        cal.setMinute(ldt.getMinute());
        cal.setSecond(ldt.getSecond());
        return cal;
    }
}

