/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.com.projeto.clinica.validacao;

import br.com.projeto.clinica.model.Clinica;
import br.com.projeto.clinica.model.Local;
import br.com.projeto.clinica.bo.LocalBO;

import java.sql.SQLException;

import javax.faces.application.FacesMessage;

import javax.faces.component.UIComponent;
import javax.faces.component.UIInput;

import javax.faces.context.FacesContext;

import javax.faces.validator.ValidatorException;

/**
 *
 * @author matheus
 */
public class ValidarLocal {

    //variavel de mensagem
    private String msg = null;

    //variavel de Local
    private Local local = new Local();
    private LocalBO localBO = new LocalBO();

    /**
     * MÉTODOS DE VALIDACAO DE LOCAL
     */
    //metodo para validacao do local
    public boolean VALIDARNOMEDOLOCALINSERCAO(FacesContext context, UIComponent component, Object value, Clinica clinica) throws ValidatorException, SQLException {
        Object idLocal = ((UIInput) context.getViewRoot().findComponent("formularioLocal:idLocal")).getLocalValue();
        Object nomeLocal = ((UIInput) context.getViewRoot().findComponent("formularioLocal:nomeLocal")).getSubmittedValue();

        local.setIdLocal(Integer.valueOf(idLocal.toString()));
        local.setNomeLocal(nomeLocal.toString());

        msg = "INFORME NOME DO LOCAL";
        if ((local.getNomeLocal() == null || "".equalsIgnoreCase(local.getNomeLocal()))) {
            ((UIInput) component).setValid(false);
            context.addMessage((component.getClientId(context)), new FacesMessage(FacesMessage.SEVERITY_ERROR, msg, msg));
            return false;
        }

        msg = "O NOME DO LOCAL SÓ DEVE TER 4 E 30 DE DÍGITOS";
        if (local.getNomeLocal().trim().length() <= 3 || local.getNomeLocal().trim().length() > 30) {
            ((UIInput) component).setValid(false);
            context.addMessage(component.getClientId(context), new FacesMessage(FacesMessage.SEVERITY_ERROR, msg, msg));
            return false;
        }

        if (localBO.IDNOMELOCALCONFERE(clinica.getIdClinica(), local.getIdLocal(), local.getNomeLocal()) || localBO.EXISTINOMELOCAL(clinica.getIdClinica(), local.getNomeLocal()) == false) {
            return false;
        } else {
            msg = "O LOCAL JÁ EXISTE";
            ((UIInput) component).setValid(false);
            context.addMessage(component.getClientId(context), new FacesMessage(FacesMessage.SEVERITY_ERROR, msg, msg));
            return true;
        }

    }

    //metodo para validacao do local
    public boolean VALIDARNOMEDOLOCALATUALIZACAO(FacesContext context, UIComponent component, Object value, Clinica clinica) throws ValidatorException, NullPointerException, SQLException, IndexOutOfBoundsException {
        Object idLocal = ((UIInput) context.getViewRoot().findComponent("formularioLocal:idLocal")).getLocalValue();
        Object nomeLocal = ((UIInput) context.getViewRoot().findComponent("formularioLocal:nomeLocal")).getSubmittedValue();

        local.setIdLocal(Integer.valueOf(idLocal.toString()));
        local.setNomeLocal(nomeLocal.toString());

        msg = "DESCULPE, MAS ESTE LOCAL PODE TER SIDO DELETADO!!!";
        if (localBO.IDLOCALCONFERE(clinica.getIdClinica(), local.getIdLocal()) == false) {
            ((UIInput) component).setValid(false);
            context.addMessage((component.getClientId(context)), new FacesMessage(FacesMessage.SEVERITY_ERROR, msg, msg));
            return false;
        }

        msg = "INFORME NOME DO LOCAL";
        if ((local.getNomeLocal() == null || "".equalsIgnoreCase(local.getNomeLocal()))) {
            ((UIInput) component).setValid(false);
            context.addMessage((component.getClientId(context)), new FacesMessage(FacesMessage.SEVERITY_ERROR, msg, msg));
            return false;
        }

        msg = "O NOME DO LOCAL SÓ DEVE TER 4 E 30 DE DÍGITOS";
        if (local.getNomeLocal().trim().length() <= 3 || local.getNomeLocal().trim().length() > 30) {
            ((UIInput) component).setValid(false);
            context.addMessage(component.getClientId(context), new FacesMessage(FacesMessage.SEVERITY_ERROR, msg, msg));
            return false;
        }

        if (localBO.IDNOMELOCALCONFERE(clinica.getIdClinica(), local.getIdLocal(), local.getNomeLocal()) || localBO.EXISTINOMELOCAL(clinica.getIdClinica(), local.getNomeLocal()) == false) {
            return false;
        } else {
            msg = "O LOCAL JÁ EXISTE";
            ((UIInput) component).setValid(false);
            context.addMessage(component.getClientId(context), new FacesMessage(FacesMessage.SEVERITY_ERROR, msg, msg));
            return true;
        }
    }

}
