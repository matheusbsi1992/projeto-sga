/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.sga.messages;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

/**
 *
 * @author Jandisson
 */
public class Mensagens {

    public Mensagens() {
    }

    public static String retorneMensagem(boolean ok, String mensagem, String pagina) {
        if (ok != true) {
            FacesContext
                    .getCurrentInstance()
                    .addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, mensagem, ""));
        } else {
            FacesContext
                    .getCurrentInstance()
                    .addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, mensagem, ""));
        }
        FacesContext
                .getCurrentInstance()
                .getExternalContext()
                .getFlash()
                .setKeepMessages(true);

        return pagina;
    }
}
