/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.sga.mb;

import br.com.sga.bo.ServicoBO;
import br.com.sga.bo.ValidarSgaBO;
import br.com.sga.transfer.ServicoTransfer;
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
import org.omnifaces.cdi.Push;
import org.omnifaces.cdi.PushContext;

/**
 *
 * @author Jandisson
 */
@ManagedBean
@RequestScoped
public class ServicoMB implements Serializable {

    public ServicoMB() throws SQLException {

    }

    private boolean ativargrowlsenhasservico = false;

    private ServicoTransfer servicotransfer = new ServicoTransfer();

    private ValidarSgaBO validarsgaboservico = new ValidarSgaBO();
    private ServicoBO servicobo = new ServicoBO();

    @Inject
    @Push(channel = "servicopushmb")
    private PushContext servicopushmb;

    @Inject
    @Push(channel = "servicopushmbatendimento")
    private PushContext servicopushmbatendimento;

    @Inject
    @Push(channel = "servicopushmbatendimentoatualizar")
    private PushContext servicopushmbatendimentoatualizar;

    //variavel com as caracteristicas de atualizar o painel de atendimento e monitor
    @Inject
    @Push(channel = "usuarioservicosenhaspushmb")
    private PushContext usuarioservicosenhaspushmb;

    //cria servico para listar os dados
    private List<ServicoTransfer> servicobuscarusuario = new ArrayList<ServicoTransfer>();

    //listar dados das senhas
    private List<ServicoTransfer> usuarioServicos = new ArrayList<ServicoTransfer>();

    /**
     * METODOS PARA CHAMAR PAGINAS
     */
    public String editarServico() {
        try {
            return ("editarservico.xhtml");
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }

    /**
     * Metodo com a caracteristica de retornar os dados der servico do usuario
     * logado
     */
    public void consultarServicoUsuario() throws SQLException {
        MBBase mBBase = new MBBase();
        String nomeusuario = mBBase.valorParam("nomeusuario");
        servicobuscarusuario = servicobo.buscarServicoUsuario(nomeusuario.toUpperCase());
    }

    /**
     * METODOS CORRESPONDENTES AO CRUD
     */
    public void inserirServico() throws SQLException {
        try {
            servicobo.inserirServico(servicotransfer); //To change body of generated methods, choose Tools | Templates.
            FacesContext fc = FacesContext.getCurrentInstance();
            fc.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "SERVIÇO INSERIDO COM SUCESSO!!!", ""));
            limpar();
        } catch (Exception ex) {
            FacesContext fc = FacesContext.getCurrentInstance();
            fc.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "ERROR AO INSERIR SERVIÇO!!!", ""));
        }
        servicopushmb.send("servicopushmb");
        servicopushmbatendimento.send("servicopushmbatendimento");
    }

    public void alterarServico() throws SQLException {
        try {
            servicobo.alterarServico(servicotransfer); //To change body of generated methods, choose Tools | Templates.
            FacesContext fc = FacesContext.getCurrentInstance();
            fc.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "SERVIÇO ALTERADO COM SUCESSO!!!", ""));
        } catch (Exception ex) {
            FacesContext fc = FacesContext.getCurrentInstance();
            fc.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "ERROR AO ATUALIZAR SERVIÇO!!!", ""));
        }
        if (servicotransfer.getStatusservico().equalsIgnoreCase("I")) {
            servicopushmbatendimentoatualizar.send("servicopushmbatendimentoatualizar");
        }
        servicopushmb.send("servicopushmb");
        servicopushmbatendimento.send("servicopushmbatendimento");
    }

    public void deletarServico(Short idservico) throws SQLException {
        try {
            servicobo.deletarServico(idservico); //To change body of generated methods, choose Tools | Templates.
            FacesContext fc = FacesContext.getCurrentInstance();
            fc.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "SERVIÇO DELETADO COM SUCESSO!!!", ""));
        } catch (Exception ex) {
            FacesContext fc = FacesContext.getCurrentInstance();
            fc.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "ERROR AO DELETAR SERVIÇO!!!", ""));
        }
        //desabilitar servico em atendimento;
        servicopushmbatendimentoatualizar.send("servicopushmbatendimentoatualizar");
        //nao visualizar dialogo em atendimento;
        servicopushmb.send("servicopushmb");
        servicopushmbatendimento.send("servicopushmbatendimento");
    }

    /**
     * Método com as caracteríticas de deleção das senhas de Monitor e
     * Atendimento
     */
    public void deletarSenhaServicoUsuario() throws SQLException {
        //ativar a growl
        setAtivargrowlsenhasservico(true);
        try {
            if (servicobo.existeSenhaServico(servicotransfer.getId()) == true && servicobo.deletarSenhasServico(servicotransfer.getId()) == true) {
                usuarioservicosenhaspushmb.send("usuarioservicosenhaspushmb");
                FacesContext fc = FacesContext.getCurrentInstance();
                fc.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "SENHA DE SERVIÇO DE USUÁRIO DELETADA COM SUCESSO!!!", ""));
            } else {
                usuarioservicosenhaspushmb.send("usuarioservicosenhaspushmb");
                FacesContext fc = FacesContext.getCurrentInstance();
                fc.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "NÃO A SENHA PARA SER DELETADA!!!", ""));
            }
        } catch (Exception ex) {
            FacesContext fc = FacesContext.getCurrentInstance();
            fc.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "ERROR AO DELETAR SENHA DO SERVIÇO DE USUÁRIO!!!", ""));
        }
    }

    public void limpar() {
        servicotransfer = new ServicoTransfer();
    }

    /**
     * METODOS PARA VALIDACAO DO SERVICO
     */
    //metodo para validacao do nome do servico
    public boolean validarNomeServico(FacesContext context, UIComponent component, Object value) throws ValidatorException, SQLException {
        return validarsgaboservico.validarNomeServico(context, component, this);
    }

    //metodo para validacao da descricao do servico
    public boolean validarDescricaoServico(FacesContext context, UIComponent component, Object value) throws ValidatorException, SQLException {
        return validarsgaboservico.validarDescricaoServico(context, component, this);
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

    public List<ServicoTransfer> getServicobuscarusuario() {
        return servicobuscarusuario;
    }

    public void setServicobuscarusuario(List<ServicoTransfer> servicobuscarusuario) {
        this.servicobuscarusuario = servicobuscarusuario;
    }

    public boolean isAtivargrowlsenhasservico() {
        return ativargrowlsenhasservico;
    }

    public void setAtivargrowlsenhasservico(boolean ativargrowlsenhasservico) {
        this.ativargrowlsenhasservico = ativargrowlsenhasservico;
    }

    public List<ServicoTransfer> getUsuarioServicos(Short id) throws SQLException {
        if (id == 1) {
            usuarioServicos = servicobo.listarTodosServico();
        } else {
            usuarioServicos = servicobo.listareBuscarServicoUsuario(id);
        }
        return usuarioServicos;
    }

    public void setUsuarioServicos(List<ServicoTransfer> usuarioServicos) {
        this.usuarioServicos = usuarioServicos;
    }

}
