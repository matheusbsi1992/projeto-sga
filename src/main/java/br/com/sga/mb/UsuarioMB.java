/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.sga.mb;

import br.com.sga.bo.UsuarioBO;
import br.com.sga.bo.ValidarSgaBO;
import br.com.sga.transfer.CargoTransfer;
import br.com.sga.transfer.ServicoTransfer;
import br.com.sga.transfer.UnidadeTransfer;
import br.com.sga.transfer.UsuarioTransfer;
import br.com.sga.util.Util;

import java.io.Serializable;
import java.sql.SQLException;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.faces.application.FacesMessage;

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

/**
 *
 * @author Jandisson
 */
@ManagedBean
@RequestScoped
public class UsuarioMB implements Serializable {

    public UsuarioMB() throws SQLException {

    }

    @Inject
    @Push(channel = "usuariopushmb")
    private PushContext usuariopushmb;

    @Inject
    @Push(channel = "usuarioinativopushmb")
    private PushContext usuarioinativopushmb;

    private UsuarioTransfer usuariotransfer = new UsuarioTransfer();

    private List<ServicoTransfer> servicos = new ArrayList<ServicoTransfer>();
    private List<CargoTransfer> cargos = new ArrayList<CargoTransfer>();
    private List<UnidadeTransfer> unidades = new ArrayList<UnidadeTransfer>();
    private List<String> listarservicos = new ArrayList<String>();

    private UsuarioBO usuariobo = new UsuarioBO();
    private ValidarSgaBO validarusuario = new ValidarSgaBO();
    private Util util = new Util();

    private boolean ativargrowl = false;

    /**
     * METODOS PARA CHAMAR PAGINAS
     */
    public String editarUsuario() {
        try {
            return ("editarusuario.xhtml");
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }

    /**
     * METODOS CORRESPONDENTES AO CRUD
     *
     * @throws java.sql.SQLException
     */
    public void inserirUsuario() throws SQLException {
        try {
            usuariobo.inserirUsuario(usuariotransfer); //To change body of generated methods, choose Tools | Templates.
            if (usuariotransfer.getStatususuario().equalsIgnoreCase("I")) {
                usuarioinativopushmb.send("usuarioinativopushmb");
            }
            usuariopushmb.send("usuariopushmb");

            FacesContext fc = FacesContext.getCurrentInstance();
            fc.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "USUÁRIO INSERIDO COM SUCESSO!!!", ""));
            limpar();
        } catch (Exception ex) {
            FacesContext fc = FacesContext.getCurrentInstance();
            fc.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "ERROR AO INSERIR USUÁRIO!!!", ""));
        }

    }

    public void alterarUsuario() throws SQLException {
        //desativar a growl
        setAtivargrowl(false);
        try {
            usuariobo.alterarUsuario(usuariotransfer, listarservicos);

            usuariopushmb.send("usuariopushmb");
            FacesContext fc = FacesContext.getCurrentInstance();
            fc.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "USUÁRIO ALTERADO COM SUCESSO!!!", ""));
        } catch (Exception ex) {
            FacesContext fc = FacesContext.getCurrentInstance();
            fc.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "ERROR AO ATUALIZAR USUÁRIO!!!", ""));
        }
        if (usuariotransfer.getStatususuario().equalsIgnoreCase("I")) {
            usuarioinativopushmb.send("usuarioinativopushmb");
        }

    }

    public void alterarSenhaUsuario() throws SQLException, IndexOutOfBoundsException {
        //ativar a growl
        setAtivargrowl(true);
        try {
            FacesContext context = FacesContext.getCurrentInstance();
            Map<String, String> params = context.getExternalContext().getRequestParameterMap();
            String id = params.get("id");
            usuariotransfer.setId(Short.parseShort(id));
            usuariobo.alterarSenhadoUsuario(usuariotransfer);
            usuariopushmb.send("usuariopushmb");
            FacesContext fc = FacesContext.getCurrentInstance();
            fc.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "SENHA DO USUÁRIO ALTERADA COM SUCESSO!!!", ""));
        } catch (java.lang.IndexOutOfBoundsException ex) {
            ex.printStackTrace();
            FacesContext fc = FacesContext.getCurrentInstance();
            fc.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "ERROR AO ATUALIZAR SENHA DO USUÁRIO!!!", ""));
        }

        //ativar a growl
        //setAtivargrowl(false);
    }

    public void deletarUsuario(Short idusuario) throws SQLException {
        try {
            usuariobo.deletarUsuario(idusuario);
            usuariopushmb.send("usuariopushmb");

            FacesContext fc = FacesContext.getCurrentInstance();
            fc.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "USUÁRIO DELETADO COM SUCESSO!!!", ""));
        } catch (Exception ex) {
            FacesContext fc = FacesContext.getCurrentInstance();
            fc.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "ERROR AO DELETAR USUÁRIO!!!", ""));
        }
        if (usuariobo.existeUsuarioId(idusuario) == false) {
            usuarioinativopushmb.send("usuarioinativopushmb");
        }
    }

    public void limpar() {
        usuariotransfer = new UsuarioTransfer();
    }

    /**
     * Método que busca a lista de dados existentes sobre o nome do usuario em
     * buscar o servico e adicionar na lista do tipo String de servicos.
     *
     * @return lista de servicos
     * @throws java.sql.SQLException
     */
    public List<String> getListarservicos() throws SQLException {
        usuariobo.listareBuscarServico(usuariotransfer.getNomeusuario())
                .stream()
                .forEach((listadeservicos) -> {
                    listarservicos
                            .add(listadeservicos.getServico().getNomeservico());
                    usuariotransfer.getDescricaousuarioservico()
                            .add(listadeservicos.getServico().getNomeservico());
                });
        return listarservicos;
    }

    public void setListarservicos(List<String> listarservicos) {
        this.listarservicos = listarservicos;
    }

    /**
     * Validações
     */
    public boolean validarUsuario(FacesContext context, UIComponent component, Object value) throws ValidatorException, SQLException {
        return validarusuario.validarUsuario(context, component, this);
    }

    public boolean validarNomedoUsuario(FacesContext context, UIComponent component, Object value) throws ValidatorException, SQLException {
        return validarusuario.validarNomedeUsuario(context, component, this);
    }

    public boolean validarSenhadoUsuario(FacesContext context, UIComponent component, Object value) throws ValidatorException, SQLException {
        return validarusuario.validarUsuarioSenhaCadastrar(context, component, this);
    }

    public boolean validarUsuarioSenhaAlterar(FacesContext context, UIComponent component, Object value) throws ValidatorException, SQLException {
        return validarusuario.validarUsuarioSenhaAlterar(context, component, this);
    }

    public boolean validarUnidadeUsuario(FacesContext context, UIComponent component, Object value) throws ValidatorException, SQLException {
        return validarusuario.validarUsuarioUnidade(context, component, this);
    }

    public boolean validarCargoUsuario(FacesContext context, UIComponent component, Object value) throws ValidatorException, SQLException {
        return validarusuario.validarUsuarioCargo(context, component, this);
    }

    public void listarCargos() throws SQLException {
        cargos = usuariobo.listarCargos();
    }

    public void listarUnidade() throws SQLException {
        unidades = usuariobo.listarUnidade();
    }

    public void listarServico() throws SQLException {
        servicos = usuariobo.listarServico();
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

    public List<CargoTransfer> getCargos() throws SQLException {
        return cargos = usuariobo.listarCargos();
    }

    public void setCargos(List<CargoTransfer> cargos) {
        this.cargos = cargos;
    }

    public List<UnidadeTransfer> getUnidades() throws SQLException {
        return unidades = usuariobo.listarUnidade();
    }

    public void setUnidades(List<UnidadeTransfer> unidades) {
        this.unidades = unidades;
    }

    public List<ServicoTransfer> getServicos() throws SQLException {
        return servicos = usuariobo.listarServico();
    }

    public void setServicos(List<ServicoTransfer> servicos) {
        this.servicos = servicos;
    }

    public boolean isAtivargrowl() {
        return ativargrowl;
    }

    public void setAtivargrowl(boolean ativargrowl) {
        this.ativargrowl = ativargrowl;
    }

}
