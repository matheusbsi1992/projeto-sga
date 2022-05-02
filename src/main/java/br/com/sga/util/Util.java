/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.sga.util;

import br.com.sga.dao.DAO;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.concurrent.TimeUnit;
import javax.faces.application.Application;
import javax.faces.application.ViewHandler;
import javax.faces.component.UIViewRoot;
import javax.faces.context.FacesContext;

import org.apache.shiro.crypto.hash.Sha512Hash;
import org.apache.shiro.crypto.hash.SimpleHash;

/**
 *
 * @author Jandisson
 */
public class Util {

    public String retorneDataHoraseMin(Timestamp valordatainicio) {
        StringBuffer retornodata = new StringBuffer();

        long valortotal = (getSqlTimeStamp(getPegaDataAtual()).getTime() - valordatainicio.getTime());
        long segundos = valortotal / 1000 % 60;
        long minutos = valortotal / (60 * 1000) % 60;
        long horas = valortotal / (60 * 60 * 1000) % 24;
        long dias = valortotal / (24 * 60 * 60 * 1000);

        retornodata.append(dias)
                .append("\tdias\t")
                .append(horas)
                .append(":")
                .append(minutos)
                .append(":")
                .append(segundos);

        return retornodata.toString();
    }

    //retorne 3 milisegundos
    public void getTempoMillisSegundos() {
        try {
            TimeUnit.SECONDS.sleep(3);
        } catch (InterruptedException ex) {
            
        }
    }
    
     //retorne 1 milisegundos
    public void getTempoRefreshMillisSegundos() {
        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException ex) {
            
        }
    }

    //Pegar Data com Horas atual
    public Date getPegaDataAtual() {
        Calendar calendar = new GregorianCalendar();
        Date date = new Date();
        calendar.setTime(date);
        return calendar.getTime();
    }

    public java.sql.Date convertUtilToSql(java.util.Date uDate) {
        if (uDate == null) {
            return null;
        }
        java.sql.Date sDate = new java.sql.Date(uDate.getTime());
        return sDate;
    }

    public java.sql.Timestamp getSqlTimeStamp(Date data) {
        if (data == null) {
            return null;
        }
        return java.sql.Timestamp.valueOf(dateToStr(data, "yyyy-MM-dd HH:mm:ss"));
    }

    public String dateToStr(Date date, String format) {
        String retorno = null;
        if ((null != date) && (null != format)) {
            SimpleDateFormat formater = new SimpleDateFormat(format);
            retorno = formater.format(date);
        }
        return retorno;
    }

    /**
     * Metodo hashCode
     *
     *
     * @param valorHash
     * @return SimpleHash
     */
    public SimpleHash hashCode(String valorHash) {
        SimpleHash simplehash = new Sha512Hash(valorHash);
        return simplehash;
    }

    public final void refresh() {
        FacesContext context = FacesContext.getCurrentInstance();
        Application application = context.getApplication();
        ViewHandler viewHandler = application.getViewHandler();
        UIViewRoot viewRoot = viewHandler.createView(context, context.getViewRoot().getViewId());
        context.setViewRoot(viewRoot);
        context.renderResponse();
    }

}
