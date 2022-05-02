/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.sga.mb;

import br.com.sga.bo.UsuarioBO;
import br.com.sga.transfer.ServicoTransfer;
import br.com.sga.transfer.UsuarioTransfer;
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
public class UsuarioListarMB implements Serializable {

    public UsuarioListarMB() throws SQLException {
        listarTodosUsuario();
    }
    private UsuarioTransfer usuariotransfer = new UsuarioTransfer();
    private UsuarioBO usuariobo = new UsuarioBO();
    private String nomedeusuario = null;

    private List<UsuarioTransfer> listarusuarios = new ArrayList<UsuarioTransfer>();

   
    
    public void listarTodosUsuario() throws SQLException {
        listarusuarios = usuariobo.listarTodosUsuario();
    }

    

    //metodo responsavel por consultar pelo nome do usuario
    public void consultarAction() throws SQLException {
        if (nomedeusuario.trim().length() == 0 || nomedeusuario.trim().equalsIgnoreCase("")) {
            listarTodosUsuario();
        } else {
            listarusuarios = usuariobo.buscarUsuario(nomedeusuario);
            if (listarusuarios.size() > 0) {
                FacesContext fc = FacesContext.getCurrentInstance();
                fc.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "CONSULTADO COM SUCESSO!!!", ""));
                setNomedeusuario(null);
            }
        }
    }

    /**
     * MÉTODOS GETTERS E SETTERS
     */
    public UsuarioTransfer getUsuariotransfer() {
        return usuariotransfer;
    }

    public void setUsuariotransfer(UsuarioTransfer usuariotransfer) {
        this.usuariotransfer = usuariotransfer;
    }

    public String getNomedeusuario() {
        return nomedeusuario;
    }

    public void setNomedeusuario(String nomedeusuario) {
        this.nomedeusuario = nomedeusuario;
    }

    public List<UsuarioTransfer> getListarusuarios() {
        return listarusuarios;
    }

    public void setListarusuarios(List<UsuarioTransfer> listarusuarios) {
        this.listarusuarios = listarusuarios;
    }

    

}
