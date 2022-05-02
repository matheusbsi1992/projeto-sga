/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.sga.mb;

import br.com.sga.bo.ServicoBO;
import br.com.sga.transfer.ServicoTransfer;
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
public class ServicoListarMB implements Serializable {

    public ServicoListarMB() throws SQLException {
        listarTodosServico();
    }

    private ServicoBO servicobo = new ServicoBO();

    private List<ServicoTransfer> listartransferservico = new ArrayList<ServicoTransfer>();
    
    private ServicoTransfer servicotransfer = new ServicoTransfer();
    
    private String nomeservico = null;

    public void listarTodosServico() throws SQLException {
        listartransferservico = servicobo.listarTodosServico(); //To change body of generated methods, choose Tools | Templates.
    }

    //metodo responsavel por consultar pelo nome da servico
    public void consultarAction() throws SQLException {
        if (nomeservico == null || nomeservico.trim().length() == 0 || nomeservico.trim().equalsIgnoreCase("")) {
            listarTodosServico();
        } else {
            listartransferservico = servicobo.buscarServico(nomeservico);
            if (listartransferservico.size() > 0) {
                FacesContext fc = FacesContext.getCurrentInstance();
                fc.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "CONSULTADO COM SUCESSO!!!", ""));
                setNomeservico(null);
            }
        }
    }

    /**
     * MÉTODOS GETTERS E SETTERS
     */
    public ServicoTransfer getServicotransfer() {
        return servicotransfer;
    }

    public void setServicotransfer(ServicoTransfer servicotransfer) {
        this.servicotransfer = servicotransfer;
    }

    public String getNomeservico() {
        return nomeservico;
    }

    public void setNomeservico(String nomeservico) {
        this.nomeservico = nomeservico;
    }

    public List<ServicoTransfer> getListarTransferservico() {
        return listartransferservico;
    }

    public void setTransferservico(List<ServicoTransfer> listartransferservico) {
        this.listartransferservico = listartransferservico;
    }

}