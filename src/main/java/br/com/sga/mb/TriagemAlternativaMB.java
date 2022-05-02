/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.sga.mb;

import br.com.sga.bo.TriagemAlternativaBO;
import br.com.sga.bo.ValidarSgaBO;
import br.com.sga.transfer.LocalTransfer;
import br.com.sga.transfer.ServicoTransfer;
import br.com.sga.transfer.TriagemAlternativaTransfer;
import br.com.sga.util.Util;

import java.io.Serializable;
import java.sql.SQLException;

import java.util.ArrayList;
import java.util.List;
import javax.faces.application.Application;

import javax.faces.application.FacesMessage;
import javax.faces.application.ViewHandler;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.bean.SessionScoped;
import javax.faces.component.UIComponent;
import javax.faces.component.UIViewRoot;
import javax.faces.context.FacesContext;
import javax.faces.validator.ValidatorException;
import javax.inject.Inject;
import javax.inject.Named;
import org.omnifaces.cdi.Push;
import org.omnifaces.cdi.PushContext;

/**
 *
 * @author Jandisson
 */
@ManagedBean
@RequestScoped
public final class TriagemAlternativaMB implements Serializable {

    public TriagemAlternativaMB() throws SQLException {

    }

    private TriagemAlternativaTransfer triagemalternativatransfer = new TriagemAlternativaTransfer();

    private ValidarSgaBO validarsgabounidade = new ValidarSgaBO();
    private TriagemAlternativaBO triagemalternativabo = new TriagemAlternativaBO();

    private List<ServicoTransfer> servico = new ArrayList<ServicoTransfer>();
    private List<LocalTransfer> local = new ArrayList<LocalTransfer>();

    private Util util = new Util();

    @Inject
    @Push(channel = "triagemalternativapushmb")
    private PushContext triagemalternativapushmb;
    

    @Inject
    @Push(channel = "triagemalternativaauxpushmb")
    private PushContext triagemalternativaauxpushmb;
    
    

    /**
     * METODOS PARA CHAMAR PAGINAS
     */
    public String editarTriagemAlternativa() {
        try {
            return ("editartriagemalternativa.xhtml");
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }

    /**
     * METODOS CORRESPONDENTES AO CRUD
     *
     * @return String
     * @throws java.sql.SQLException
     */
    public void inserirTriagemAlternativa() throws SQLException {
        try {
            triagemalternativabo.inserirTriagemAlternativa(triagemalternativatransfer); //To change body of generated methods, choose Tools | Templates.
            FacesContext fc = FacesContext.getCurrentInstance();
            fc.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "TRIAGEM ALTERNATIVA INSERIDO COM SUCESSO!!!", ""));
        } catch (Exception ex) {
            FacesContext fc = FacesContext.getCurrentInstance();
            fc.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "ERROR AO INSERIR TRIAGEM ALTERNATIVA!!!", ""));
        }
        limpar();
        triagemalternativapushmb.send("triagemalternativapushmb");
    }

    public void alterarTriagemAlternativa() throws SQLException {
        try {
            triagemalternativabo.alterarTriagemAlternativa(triagemalternativatransfer); //To change body of generated methods, choose Tools | Templates.
            FacesContext fc = FacesContext.getCurrentInstance();
            fc.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "TRIAGEM ALTERNATIVA ALTERADO COM SUCESSO!!!", ""));
        } catch (Exception ex) {
            FacesContext fc = FacesContext.getCurrentInstance();
            fc.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "ERROR AO ATUALIZAR TRIAGEM ALTERNATIVA!!!", ""));
        }
        triagemalternativapushmb.send("triagemalternativapushmb");
        if (triagemalternativatransfer.getStatustriagemalternativa().equalsIgnoreCase("I")) {
            triagemalternativaauxpushmb.send("triagemalternativaauxpushmb");
        }
    }

    public void deletarTriagemAlternativa(Short idtriagemalternativa) throws SQLException {
        try {
            triagemalternativabo.deletarTriagemAlternativa(idtriagemalternativa); //To change body of generated methods, choose Tools | Templates.            
            FacesContext fc = FacesContext.getCurrentInstance();
            fc.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "TRIAGEM ALTERNATIVA DELETADO COM SUCESSO!!!", ""));
        } catch (Exception ex) {
            FacesContext fc = FacesContext.getCurrentInstance();
            fc.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "ERROR AO DELETAR TRIAGEM ALTERNATIVA!!!", ""));
        }
        triagemalternativapushmb.send("triagemalternativapushmb");
    }

    //metodo para validacao sigla
    public boolean validarSiglaTriagemAlternativa(FacesContext context, UIComponent component, Object value) throws ValidatorException, SQLException {
        return validarsgabounidade.validarSiglaTriagemAlternativa(context, component, this);
    }

    public boolean validarLocalTriagemAlternativa(FacesContext context, UIComponent component, Object value) throws ValidatorException, SQLException {
        return validarsgabounidade.validarLocalTriagemAlternativa(context, component, this);
    }

    public boolean validarServicoTriagemAlternativa(FacesContext context, UIComponent component, Object value) throws ValidatorException, SQLException {
        return validarsgabounidade.validarServicoTriagemAlternativa(context, component, this);
    }

    public void limpar() {
        triagemalternativatransfer = new TriagemAlternativaTransfer();
    }

    public void listarLocal() throws SQLException {
        local = triagemalternativabo.triagemAlternativaLocal();
    }

    public void listarServico() throws SQLException {
        servico = triagemalternativabo.listarServico();
    }

    /**
     * MÉTODOS GETTERS E SETTERS
     */
    public TriagemAlternativaTransfer getTriagemAlternativatransfer() {
        return triagemalternativatransfer;
    }

    public void setTriagemAlternativatransfer(TriagemAlternativaTransfer triagemalternativatransfer) {
        this.triagemalternativatransfer = triagemalternativatransfer;
    }

    public List<ServicoTransfer> getServico() throws SQLException {
        //if (triagemalternativatransfer.getServicotransfer().getNomeservico() == null) {
        return servico = triagemalternativabo.listarServico();
        //}
        //return servico = triagemalternativabo.triagemAlternativaServicobuscarServico(triagemalternativatransfer.getServicotransfer().getNomeservico());
    }

    public void setServico(List<ServicoTransfer> servico) {
        this.servico = servico;
    }

    public List<LocalTransfer> getLocal() throws SQLException {
        //if (triagemalternativatransfer.getLocaltransfer().getNomelocal() == null) {
        return local = triagemalternativabo.triagemAlternativaLocal();
        //}
        //return local = triagemalternativabo.triagemAlternativabuscarLocal(triagemalternativatransfer.getLocaltransfer().getNomelocal());
    }

    public void setLocal(List<LocalTransfer> local) {
        this.local = local;
    }
}
