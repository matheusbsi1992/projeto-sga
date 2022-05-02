/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.sga.mb;

import br.com.sga.bo.CargoBO;
import br.com.sga.bo.ValidarSgaBO;
import br.com.sga.messages.Mensagens;
import br.com.sga.transfer.CargoTransfer;
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
import javax.faces.bean.ViewScoped;

import javax.faces.component.UIComponent;
import javax.faces.component.UIViewRoot;
import javax.faces.context.FacesContext;

import javax.faces.validator.ValidatorException;
import javax.inject.Inject;
import org.omnifaces.cdi.Push;
import org.omnifaces.cdi.PushContext;

/**
 *
 * @author Jandisson
 */
@ManagedBean
@RequestScoped
public class CargoMB implements Serializable {

    public CargoMB() throws SQLException {

    }

    @Inject
    @Push(channel = "cargopushmb")
    private PushContext cargopushmb;

    private CargoTransfer cargotransfer = new CargoTransfer();

    private CargoBO cargobo = new CargoBO();
    private ValidarSgaBO validarsgabocargo = new ValidarSgaBO();
    private CargoListarMB cargolistarmb = new CargoListarMB();
    private Util util = new Util();

    
    private List<CargoTransfer> listarpermissoes = new ArrayList<CargoTransfer>();
    private List<String> listarpermissaoepermissoes = new ArrayList<String>();

    /**
     * METODOS PARA CHAMAR PAGINAS
     */
    public String editarCargo() {
        try {
            return ("editarcargo.xhtml");
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }

    /**
     * METODOS CORRESPONDENTES AO CRUD
     */
    public void inserirCargo() throws SQLException {
        try {
            cargobo.inserirCargo(cargotransfer); //To change body of generated methods, choose Tools | Templates.
            cargopushmb.send("cargopushmb");
            
            FacesContext fc = FacesContext.getCurrentInstance();
            fc.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "CARGO INSERIDO COM SUCESSO!!!", ""));
            limpar();
        } catch (Exception ex) {
            FacesContext fc = FacesContext.getCurrentInstance();
            fc.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "ERROR AO INSERIR CARGO!!!", ""));
        }
    }

    /**
     * METODOS CORRESPONDENTES AO CRUD
     */
    public void alterarCargo() throws SQLException {
        try {
            cargobo.alterarCargo(cargotransfer, listarpermissaoepermissoes); //To change body of generated methods, choose Tools | Templates.
            cargopushmb.send("cargopushmb");
            FacesContext fc = FacesContext.getCurrentInstance();
            fc.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "CARGO ALTERADO COM SUCESSO!!!", ""));
        } catch (Exception ex) {
            FacesContext fc = FacesContext.getCurrentInstance();
            fc.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "ERROR AO ALTERAR CARGO!!!", ""));
        }
    }

    public void deletarCargo(String nomecargo) throws SQLException {
        try {
            cargobo.deletarCargo(nomecargo);
            cargopushmb.send("cargopushmb");
            FacesContext fc = FacesContext.getCurrentInstance();
            fc.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "CARGO DELETADO COM SUCESSO!!!", ""));
        } catch (Exception ex) {
            FacesContext fc = FacesContext.getCurrentInstance();
            fc.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "ERROR AO DELETAR CARGO!!!", ""));
        }
    }

    public void limpar() {
        cargotransfer = new CargoTransfer();
    }

    /**
     * Métodos de validacao
     */
    public boolean validarNomeCargo(FacesContext context, UIComponent component, Object value) throws ValidatorException, SQLException {
        return validarsgabocargo.validarNomeCargo(context, component, value);
    }

    public boolean validarDescricaoCargo(FacesContext context, UIComponent component, Object value) throws ValidatorException, SQLException {
        return validarsgabocargo.validarDescricaoCargo(context, component, value);
    }

    /**
     * MÉTODOS GETTERS E SETTERS
     */
    public CargoTransfer getCargotransfer() {
        return cargotransfer;
    }

    public void setCargotransfer(CargoTransfer cargotransfer) {
        this.cargotransfer = cargotransfer;
    }

    public List<CargoTransfer> getListarpermissoes() throws SQLException {
        return listarpermissoes = cargobo.listarPermissoes();
    }

    public void setListarpermissoes(List<CargoTransfer> listarpermissoes) {
        this.listarpermissoes = listarpermissoes;
    }

    /**
     * Método que busca a lista de dados existentes sobre a permissao e
     * adicionar na lista do tipo String de permissoes.
     *
     * @return lista de permissoes
     * @throws java.sql.SQLException
     */
    public List<String> getListarpermissaoepermissoes() throws SQLException {
//                listarpermissoes.stream().filter(cargo -> cargo.getNomecargo().toUpperCase()
//                .contains(cargotransfer.getNomecargo().toUpperCase()))
//                .forEach((listadepermissaos) -> {
//                    listarpermissaoepermissoes.add(listadepermissaos.getNomepermissao());
//                    cargotransfer.getDescricaopermissoes().add(listadepermissaos.getNomepermissao());
//                });
        cargobo.listareBuscarPermissoes(cargotransfer.getNomecargo())
                .stream()
                .forEach((listadepermissaos) -> {
                    listarpermissaoepermissoes
                    .add(listadepermissaos.getNomepermissao());
                    cargotransfer.getDescricaopermissoes()
                    .add(listadepermissaos.getNomepermissao());
                });
        return listarpermissaoepermissoes;
    }

    public void setListarpermissaoepermissoes(List<String> listarpermissaoepermissoes) {
        this.listarpermissaoepermissoes = listarpermissaoepermissoes;
    }

}
