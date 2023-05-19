/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.projeto.clinica.util;

import java.sql.Timestamp;
import java.util.Calendar;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.GregorianCalendar;
import java.util.TimeZone;

/**
 *
 * @author DHNSTI02
 */
public class DataUtil {

    
    public java.util.Date DATAHORASRECIFE() {
        Calendar calendar = Calendar.getInstance();
        calendar.setLenient(false);
        TimeZone timeZone = TimeZone.getTimeZone("America/Recife");
        calendar.setTimeZone(timeZone);
        return calendar.getTime();
    }

    private java.sql.Date CONVERTUTILTOSQL(java.util.Date uDate) {
        if (uDate == null) {
            return null;
        }
        java.sql.Date sDate = new java.sql.Date(uDate.getTime());
        return sDate;
    }

    public java.sql.Timestamp GETSQLTIMESTAMP(java.util.Date data) {
        if (data == null) {
            return null;
        }
        return java.sql.Timestamp.valueOf(DATETOSTR(data, "yyyy-MM-dd HH:mm:ss"));
    }

    private String DATETOSTR(java.util.Date date, String format) {
        String retorno = null;
        if ((null != date) && (null != format)) {
            SimpleDateFormat formater = new SimpleDateFormat(format);
            formater.setLenient(false);
            retorno = formater.format(date);
        }
        return retorno;
    }
    
    

}
