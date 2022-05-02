/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.sga.mb;

import br.com.sga.bo.UnidadeBO;
import br.com.sga.transfer.UnidadeTransfer;
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
public class UnidadeListarMB implements Serializable {

    public UnidadeListarMB() throws SQLException {
        listarTodosUnidade();
    }

    private UnidadeBO unidadebo = new UnidadeBO();

    private String nomeunidade = null;

    private UnidadeTransfer unidadetransfer = new UnidadeTransfer();

    private List<UnidadeTransfer> listartransferunidade = new ArrayList<UnidadeTransfer>();

    public void listarTodosUnidade() throws SQLException {
        listartransferunidade = unidadebo.listarTodosUnidade(); //To change body of generated methods, choose Tools | Templates.
    }

    //metodo responsavel por consultar pelo nome da unidade
    public void consultarAction() throws SQLException {
        if (nomeunidade.trim().length() == 0 || nomeunidade.trim().equalsIgnoreCase("")) {
            listarTodosUnidade();
        } else {
            listartransferunidade = unidadebo.buscarUnidade(nomeunidade);
            if (listartransferunidade.size() > 0) {
                FacesContext fc = FacesContext.getCurrentInstance();
                fc.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "CONSULTADO COM SUCESSO!!!", ""));
                setNomeunidade(null);
            }
        }
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

    public String getNomeunidade() {
        return nomeunidade;
    }

    public void setNomeunidade(String nomeunidade) {
        this.nomeunidade = nomeunidade;
    }

    public List<UnidadeTransfer> getListarTransferunidade() {
        return listartransferunidade;
    }

    public void setTransferunidade(List<UnidadeTransfer> listartransferunidade) {
        this.listartransferunidade = listartransferunidade;
    }

}
