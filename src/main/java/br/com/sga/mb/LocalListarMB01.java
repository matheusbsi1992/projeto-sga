/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.sga.mb;

import br.com.sga.bo.LocalBO;
import br.com.sga.transfer.LocalTransfer;

import java.io.Serializable;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.faces.application.FacesMessage;


import javax.faces.bean.ManagedBean;


import javax.faces.bean.ViewScoped;


import javax.faces.context.FacesContext;
import javax.inject.Named;
//import org.omnifaces.cdi.ViewScoped;

/**
 *
 * @author Jandisson
 */
@ManagedBean
@ViewScoped
public class LocalListarMB01 implements Serializable {

    public LocalListarMB01() throws SQLException {
        listarTodosLocal();
    }

    private String nomelocal = null;

    private List<LocalTransfer> listartransferlocal = new ArrayList<LocalTransfer>();

    private LocalBO localbo = new LocalBO();

    public void listarTodosLocal() throws SQLException {
        listartransferlocal = localbo.listarTodosLocal(); //To change body of generated methods, choose Tools | Templates.
    }

    //metodo responsavel por consultar pelo nome do local
    public List<LocalTransfer> consultarAction() throws SQLException {
        if (nomelocal.trim().length() == 0 || nomelocal.trim().equalsIgnoreCase("")) {
            listarTodosLocal();
        } else {
            listartransferlocal = localbo.buscarLocal(nomelocal);
            if (listartransferlocal.size() > 0) {
                FacesContext fc = FacesContext.getCurrentInstance();
                fc.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "CONSULTADO COM SUCESSO!!!", ""));
                setNomelocal(null);
            }
        }
        return listartransferlocal;
    }

    public String getNomelocal() {
        return nomelocal;
    }

    public void setNomelocal(String nomelocal) {
        this.nomelocal = nomelocal;
    }

    public List<LocalTransfer> getListarTransferLocal() {
        return listartransferlocal;
    }

    public void setListarTransferLocal(List<LocalTransfer> listartransferlocal) {
        this.listartransferlocal = listartransferlocal;
    }

}