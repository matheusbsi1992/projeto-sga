/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.projeto.clinica.mb;

import br.com.projeto.clinica.bo.LocalBO;
import br.com.projeto.clinica.model.Clinica;
import br.com.projeto.clinica.model.Local;
import br.com.projeto.clinica.model.Pessoa;
import br.com.projeto.clinica.validacao.ValidarLocal;
import br.com.sga.bo.UsuarioBO;

import java.io.Serializable;

import java.sql.SQLException;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ApplicationScoped;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.bean.ViewScoped;
//import javax.faces.bean.ViewScoped;
import javax.faces.component.UIComponent;

import javax.faces.context.FacesContext;
import javax.faces.validator.ValidatorException;
import javax.inject.Inject;
import javax.inject.Named;
import org.omnifaces.cdi.Push;
import org.omnifaces.cdi.PushContext;
import org.springframework.security.core.context.SecurityContextHolder;

import org.primefaces.PrimeFaces;

/**
 *
 * @author Jandisson
 */
@ManagedBean
@RequestScoped
public class LocalMB implements Serializable {

    public LocalMB() throws SQLException, java.lang.IndexOutOfBoundsException {
        INICIALIZAR();
    }

    @Inject
    @Push(channel = "localPushMB")
    private PushContext localPushMB;
    
    private Map<String,Object> sessionMap = FacesContext.getCurrentInstance().getExternalContext().getSessionMap();

    private final LocalBO localBO = new LocalBO();

    private Local local = new Local();

    private final ValidarLocal validarLocal = new ValidarLocal();

    private Clinica clinica = new Clinica();

    
    public PushContext getLocalPushMB() {
        return localPushMB;
    }

    public void setLocalPushMB(PushContext localPushMB) {
        this.localPushMB = localPushMB;
    }

    public Local getLocal() {
        return local;
    }

    public void setLocal(Local local) {
        this.local = local;
    }

    public Clinica getClinica() {
        return clinica;
    }

    public void setClinica(Clinica clinica) {
        this.clinica = clinica;
    }

    /**
     * METODOS PARA CHAMAR PAGINAS
     */
    public String EDITARLOCAL(int idLocal) throws SQLException, IndexOutOfBoundsException {
        local=localBO.IDLOCALEDITAR(clinica.getIdClinica(), idLocal);
        //setReceberAtivacaoEdicao(true);
        //PrimeFaces.current().executeScript(":formularioLocal:nomeLocal");
        try {
            sessionMap.put("localClinica", local);  
            return ("editarlocal.xhtml?faces-redirect=true");
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        //PrimeFaces.current().executeScript(":formularioLocal");
        //localPushMB.send("localPushMB"); 
        //setReceberAtivacaoEdicao(false);
        //setReceberDesativacaoEdicao(true);
        return null;
    }

    private void INICIALIZAR() throws SQLException {
        clinica = LoginClinicaMB.getInstance().getPessoa().getClinica();
        local.setClinica(clinica);
    }

    public void INSERIRLOCAL() throws SQLException {
        try {
            localBO.INSERIRLOCAL(local);
            FacesContext fc = FacesContext.getCurrentInstance();
            fc.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "LOCAL INSERIDO COM SUCESSO!!!", ""));

        } catch (Exception ex) {
            FacesContext fc = FacesContext.getCurrentInstance();
            fc.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "ERROR AO INSERIR LOCAL!!!", ""));
        }
        // local = new Local();
        localPushMB.send("localPushMB");
    }

    public void ALTERARLOCAL(Local local) throws SQLException, IndexOutOfBoundsException {        
        try {
           
            localBO.ALTERARLOCAL(local); //To change body of generated methods, choose Tools | Templates.
            FacesContext fc = FacesContext.getCurrentInstance();
            fc.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "LOCAL ALTERADO COM SUCESSO!!!", ""));
        } catch (java.lang.IndexOutOfBoundsException ex) {
            FacesContext fc = FacesContext.getCurrentInstance();
            fc.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "ERROR AO ALTERAR LOCAL!!!", ""));
        } finally {
            Exception ex;
        }
        // local = new Local();
        localPushMB.send("localPushMB");
    }

    public void DELETARLOCAL(int idLocal) throws SQLException {
        
        try {
            localBO.DELETARLOCAL(idLocal); //To change body of generated methods, choose Tools | Templates.
            FacesContext fc = FacesContext.getCurrentInstance();
            fc.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "LOCAL DELETADO COM SUCESSO!!!", ""));
        } catch (Exception ex) {
            FacesContext fc = FacesContext.getCurrentInstance();
            fc.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "ERROR AO DELETAR LOCAL!!!", ""));
        }
        // local = new Local();
        localPushMB.send("localPushMB");
    }

    //metodo para validacao do local
    public boolean VALIDARNOMEDOLOCALINSERCAO(FacesContext context, UIComponent component, Object value) throws ValidatorException, SQLException {
        return validarLocal.VALIDARNOMEDOLOCALINSERCAO(context, component, this, clinica);
    }

    //metodo para validacao do local
    public boolean VALIDARNOMEDOLOCALATUALIZACAO(FacesContext context, UIComponent component, Object value) throws ValidatorException, SQLException, IndexOutOfBoundsException {
        return validarLocal.VALIDARNOMEDOLOCALATUALIZACAO(context, component, this, clinica);
    }

}
