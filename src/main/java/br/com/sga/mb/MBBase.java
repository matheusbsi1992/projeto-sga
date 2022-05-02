/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.sga.mb;


import java.util.Map;

import javax.faces.context.FacesContext;

/**
 *
 * @author Jandisson
 */
public class MBBase {

    private Map parameter;

    /**
     * @return the parameter
     */
    public Map getParameter() {
        parameter = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();
        return parameter;
    }

    
    /**
     * @param param
     * @return the parameter
     */
    public String valorParam(String param) {

        FacesContext fc = FacesContext.getCurrentInstance();
        Map<String, String> params = fc.getExternalContext().getRequestParameterMap();
        String id = params.get(param);
        return id ;
    }

    /**
     * @param parameter the parameter to set
     */
    public void setParameter(Map parameter) {
        this.parameter = parameter;
    }
}
