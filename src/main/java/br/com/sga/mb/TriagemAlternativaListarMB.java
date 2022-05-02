/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.sga.mb;

import br.com.sga.bo.TriagemAlternativaBO;
import br.com.sga.transfer.TriagemAlternativaTransfer;
import java.io.Serializable;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

/**
 *
 * @author Jandisson
 */
@ManagedBean
@ViewScoped
public final class TriagemAlternativaListarMB implements Serializable {

    public TriagemAlternativaListarMB() throws SQLException {
        listarTodosTriagemAlternativa();
    }
    
    private TriagemAlternativaBO triagemalternativabo = new TriagemAlternativaBO();
    
    private TriagemAlternativaTransfer triagemalternativatransfer = new TriagemAlternativaTransfer();
    
    private List<TriagemAlternativaTransfer> listartriagemalternativa = new ArrayList<TriagemAlternativaTransfer>();
    
    private String nomesigla = null;

    public void listarTodosTriagemAlternativa() throws SQLException {
        listartriagemalternativa = triagemalternativabo.listarTodosTriagemAlternativa(); //To change body of generated methods, choose Tools | Templates.
    }

    //metodo responsavel por consultar pelo nome da unidade
    public void consultarAction() throws SQLException {
        if (nomesigla.trim().length() == 0 || nomesigla.trim().equalsIgnoreCase("")) {
            listarTodosTriagemAlternativa();
        } else {
            listartriagemalternativa = triagemalternativabo.buscarTriagemAlternativa(nomesigla);
            if (listartriagemalternativa.size() > 0) {
                FacesContext fc = FacesContext.getCurrentInstance();
                fc.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "CONSULTADO COM SUCESSO!!!", ""));
                setNomesigla(null);
            }
        }
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

    public String getNomesigla() {
        return nomesigla;
    }

    public void setNomesigla(String nomesigla) {
        this.nomesigla = nomesigla;
    }

    public List<TriagemAlternativaTransfer> getListartriagemalternativa() {
        return listartriagemalternativa;
    }

    public void setListartriagemalternativa(List<TriagemAlternativaTransfer> listartriagemalternativa) {
        this.listartriagemalternativa = listartriagemalternativa;
    }
}