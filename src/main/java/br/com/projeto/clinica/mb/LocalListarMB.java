/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.projeto.clinica.mb;

import br.com.projeto.clinica.model.Clinica;
import br.com.projeto.clinica.model.Local;
import br.com.projeto.clinica.bo.LocalBO;


import java.io.Serializable;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.faces.application.FacesMessage;

import javax.faces.bean.ManagedBean;

import javax.faces.bean.ViewScoped;

import javax.faces.context.FacesContext;
import javax.inject.Named;

/**
 *
 * @author Jandisson
 */
@ManagedBean
@ViewScoped
public class LocalListarMB implements Serializable {

    public LocalListarMB() throws SQLException,java.lang.IndexOutOfBoundsException {
        LISTARLOCALCLINICA();
    }

    private List<Local> listarLocal = new ArrayList<Local>();

    private final LocalBO localBO = new LocalBO();

    private Clinica clinica = new Clinica();

    private String nomeLocal = null;

    public String getNomeLocal() {
        return nomeLocal;
    }

    public void setNomeLocal(String nomeLocal) {
        this.nomeLocal = nomeLocal;
    }

    public List<Local> getListarLocal() {
        return listarLocal;
    }

    public void setListarLocal(List<Local> listarLocal) {
        this.listarLocal = listarLocal;
    }

    public Clinica getClinica() {
        return clinica;
    }

    public void setClinica(Clinica clinica) {
        this.clinica = clinica;
    }

    public void INICIALIZAR() throws SQLException,java.lang.IndexOutOfBoundsException {
        clinica = LoginClinicaMB.getInstance().getPessoa().getClinica();        
    }

    public void LISTARLOCALCLINICA() throws SQLException,java.lang.IndexOutOfBoundsException {
        INICIALIZAR();
        listarLocal = localBO.LISTARTODOSLOCAL(clinica.getIdClinica());
    }

    //metodo responsavel por consultar pelo nome do local
    public List<Local> CONSULTARACTION() throws SQLException,java.lang.IndexOutOfBoundsException {
        if (nomeLocal.trim().length() == 0 || nomeLocal.trim().equalsIgnoreCase("")) {
            LISTARLOCALCLINICA();
        } else {
            listarLocal = localBO.BUSCARLISTANOMELOCAL(clinica.getIdClinica(), nomeLocal);
            if (!listarLocal.isEmpty()) {
                FacesContext fc = FacesContext.getCurrentInstance();
                fc.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "CONSULTADO COM SUCESSO!!!", ""));
                
            }
        }
        setNomeLocal(null);
        return listarLocal;
    }

  

}
