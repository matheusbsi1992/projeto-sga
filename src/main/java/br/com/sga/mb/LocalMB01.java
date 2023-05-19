/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.sga.mb;

import br.com.sga.bo.LocalBO;
import br.com.sga.bo.ValidarSgaBO;

import br.com.sga.transfer.LocalTransfer;
import br.com.sga.util.Util;

import java.io.Serializable;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.enterprise.context.Conversation;
import javax.enterprise.context.ConversationScoped;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.ValidatorException;

import javax.inject.Inject;
import javax.inject.Named;
import org.omnifaces.cdi.Push;
import org.omnifaces.cdi.PushContext;
import org.omnifaces.cdi.ViewScoped;

import javax.faces.application.Application;
import javax.faces.application.ViewHandler;
import javax.faces.component.UIViewRoot;

/**
 *
 * @author Jandisson
 */
@ManagedBean()
@RequestScoped
public class LocalMB01 implements Serializable {

    public LocalMB01() throws SQLException {

    }

    @Inject
    @Push(channel = "localpushmb")
    private PushContext localpushmb;

    private LocalTransfer localtransfer = new LocalTransfer();

    private ValidarSgaBO validarsgabolocal = new ValidarSgaBO();
    private LocalBO localbo = new LocalBO();
    private Util util = new Util();

    /**
     * METODOS PARA CHAMAR PAGINAS
     */
    public String editarLocal() {
        try {
            return ("editarlocal.xhtml");
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }

    /**
     * METODOS CORRESPONDENTES AO CRUD
     */
    public void inserirLocal() throws SQLException {

        try {
            localbo.inserirLocal(localtransfer);
            FacesContext fc = FacesContext.getCurrentInstance();
            fc.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "LOCAL INSERIDO COM SUCESSO!!!", ""));
            limpar();

        } catch (Exception ex) {
            FacesContext fc = FacesContext.getCurrentInstance();
            fc.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "ERROR AO INSERIR LOCAL!!!", ""));
        }
        localpushmb.send("localpushmb");
    }

    public void alterarLocal() throws SQLException {
        try {
            localbo.alterarLocal(localtransfer); //To change body of generated methods, choose Tools | Templates.
            FacesContext fc = FacesContext.getCurrentInstance();
            fc.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "LOCAL ALTERADO COM SUCESSO!!!", ""));
        } catch (Exception ex) {
            FacesContext fc = FacesContext.getCurrentInstance();
            fc.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "ERROR AO ALTERAR LOCAL!!!", ""));
        }
        localpushmb.send("localpushmb");
    }

    public void deletarLocal(Short idlocal) throws SQLException {
        try {
            localbo.deletarLocal(idlocal); //To change body of generated methods, choose Tools | Templates.
            FacesContext fc = FacesContext.getCurrentInstance();
            fc.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "LOCAL DELETADO COM SUCESSO!!!", ""));
        } catch (Exception ex) {
            FacesContext fc = FacesContext.getCurrentInstance();
            fc.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "ERROR AO DELETAR LOCAL!!!", ""));
        }
        localpushmb.send("localpushmb");
    }

    public void limpar() {
        localtransfer = new LocalTransfer();
    }

    //metodo para validacao do local
    public boolean validarNomeLocal(FacesContext context, UIComponent component, Object value) throws ValidatorException, SQLException {
        return validarsgabolocal.validarNomeLocal(context, component, this);
    }

    /**
     * MÉTODOS GETTERS E SETTERS
     */
    public LocalTransfer getLocaltransfer() {
        return localtransfer;
    }

    public void setLocaltransfer(LocalTransfer localtransfer) {
        this.localtransfer = localtransfer;
    }

}
