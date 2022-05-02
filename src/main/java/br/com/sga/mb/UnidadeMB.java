/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.sga.mb;

import br.com.sga.bo.UnidadeBO;
import br.com.sga.bo.ValidarSgaBO;
import br.com.sga.transfer.UnidadeTransfer;
import br.com.sga.util.Util;
import java.io.Serializable;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.bean.SessionScoped;
import javax.faces.bean.ViewScoped;
import javax.faces.component.UIComponent;
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
public final class UnidadeMB implements Serializable {

    public UnidadeMB() throws SQLException {
       
    }

    @Inject
    @Push(channel = "unidadepushmb")
    private PushContext unidadepushmb;
    
    private UnidadeTransfer unidadetransfer = new UnidadeTransfer();
    private ValidarSgaBO validarsgabounidade = new ValidarSgaBO();
    private UnidadeBO unidadebo = new UnidadeBO();
    private Util util = new Util();
    

    /**
     * METODOS PARA CHAMAR PAGINAS
     */
    public String editarUnidade() {
        try {
            return ("editarunidade.xhtml");
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }

    /**
     * METODOS CORRESPONDENTES AO CRUD
     */
    public void inserirUnidade() throws SQLException {
        try {
            unidadebo.inserirUnidade(unidadetransfer); //To change body of generated methods, choose Tools | Templates.
            FacesContext fc = FacesContext.getCurrentInstance();
            fc.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "INSERIDO COM SUCESSO!!!", ""));
            limpar();
        } catch (Exception ex) {
            FacesContext fc = FacesContext.getCurrentInstance();
            fc.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "ERROR AO INSERIR UNIDADE!!!", ""));
        }
        unidadepushmb.send("unidadepushmb");
    }

    public void alterarUnidade() throws SQLException {
        try {
            unidadebo.alterarUnidade(unidadetransfer); //To change body of generated methods, choose Tools | Templates.
            FacesContext fc = FacesContext.getCurrentInstance();
            fc.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "UNIDADE ALTERADO COM SUCESSO!!!", ""));
        } catch (Exception ex) {
            FacesContext fc = FacesContext.getCurrentInstance();
            fc.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "ERROR AO ATUALIZAR UNIDADE!!!", ""));
        }
        unidadepushmb.send("unidadepushmb");
    }

    public void deletarUnidade(Short idunidade) throws SQLException {
        try {
            unidadebo.deletarUnidade(idunidade); //To change body of generated methods, choose Tools | Templates.
            FacesContext fc = FacesContext.getCurrentInstance();
            fc.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "DELETADO COM SUCESSO!!!", ""));
        } catch (Exception ex) {
            FacesContext fc = FacesContext.getCurrentInstance();
            fc.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "ERROR AO DELETAR UNIDADE!!!", ""));
        }
        unidadepushmb.send("unidadepushmb");
    }

   

    public void limpar() {
        unidadetransfer = new UnidadeTransfer();
    }

    //metodo para validacao da unidade
    public boolean validarNomeUnidade(FacesContext context, UIComponent component, Object value) throws ValidatorException, SQLException {
        return validarsgabounidade.validarNomeUnidade(context, component, this);
    }
    
    //metodo para validacao da sigla
    public boolean validarSiglaUnidade(FacesContext context, UIComponent component, Object value) throws ValidatorException, SQLException {
        return validarsgabounidade.validarSiglaUnidade(context, component, this);
    }
    

    /**
     * MÉTODOS GETTERS E SETTERS
     */
    public UnidadeTransfer getUnidadetransfer() {
        return unidadetransfer;
    }

    public void setUnidadetransfer(UnidadeTransfer unidadetransfer) {
        this.unidadetransfer = unidadetransfer;
    }

  
}
