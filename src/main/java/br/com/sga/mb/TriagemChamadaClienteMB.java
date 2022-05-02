/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.sga.mb;

import br.com.sga.bo.TriagemAlternativaBO;
import br.com.sga.bo.TriagemChamadaClienteBO;
import br.com.sga.bo.ValidarSgaBO;
import br.com.sga.relatorio.RelatorioSenhas;
import br.com.sga.transfer.LocalTransfer;
import br.com.sga.transfer.ServicoTransfer;
import br.com.sga.transfer.TriagemChamadaClienteTransfer;
import br.com.sga.transfer.TriagemChamadaClienteTransfer;
import br.com.sga.util.Util;
import java.applet.Applet;
import java.io.IOException;

import java.io.Serializable;
import java.sql.SQLException;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.faces.application.Application;

import javax.faces.application.FacesMessage;
import javax.faces.application.ViewHandler;
import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.bean.SessionScoped;
import javax.faces.bean.ViewScoped;
import javax.faces.component.UIColumn;
import javax.faces.component.UIComponent;
import javax.faces.component.UIInput;
import javax.faces.component.UIViewRoot;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.faces.validator.ValidatorException;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpServletRequest;
import net.sf.jasperreports.engine.JRException;
import org.apache.commons.lang3.StringEscapeUtils;

import org.omnifaces.cdi.Push;
import org.omnifaces.cdi.PushContext;
import org.primefaces.context.PrimeFacesContext;

/**
 *
 * @author Jandisson
 */
@ManagedBean
@ViewScoped
public class TriagemChamadaClienteMB implements Serializable {

    private boolean ativargrowl = false;

    public TriagemChamadaClienteMB() throws SQLException {
        listarTodosTriagemChamadaCliente();
        listarSenhasTodosTriagemChamadaCliente();
    }

    @Inject
    @Push(channel = "triagemchamadaclientepushmbmb")
    private PushContext triagemchamadaclientepushmbmb;

    @Inject
    @Push(channel = "triagemchamadaprincipalclientepushmbmb")
    private PushContext triagemchamadaprincipalclientepushmbmb;

    @Inject
    @Push(channel = "triagemchamadaclienteauxpushmbmb")
    private PushContext triagemchamadaclienteauxpushmbmb;

    @Inject
    @Push(channel = "triagemchamadaclientepushmbaux")
    private PushContext triagemchamadaclientepushmbaux;

    private TriagemChamadaClienteTransfer triagemchamadaclientetransfer = new TriagemChamadaClienteTransfer();
    private TriagemChamadaClienteBO triagemchamadaclienteBO = new TriagemChamadaClienteBO();
    private RelatorioSenhas relatorio = new RelatorioSenhas();

    private List<TriagemChamadaClienteTransfer> listartransferchamadacliente = new ArrayList<TriagemChamadaClienteTransfer>();
    private List<TriagemChamadaClienteTransfer> listarsenhatransferchamadacliente = new ArrayList<TriagemChamadaClienteTransfer>();

    //metodo responsavel por consultar pelo nome do servico
    public void consultarAction(Short id, String nomeservico) throws SQLException {
        listartransferchamadacliente = null;
        if (nomeservico.trim().length() == 0 || nomeservico.trim().equalsIgnoreCase("")) {
            triagemchamadaclientepushmbaux.send("triagemchamadaclientepushmbaux");
        } else {
            if (id == 1) {
                listartransferchamadacliente = triagemchamadaclienteBO.buscarServicoTriagemChamadaCliente(nomeservico);
            } else {
                listartransferchamadacliente = triagemchamadaclienteBO.listarServicoPorUsuarioTriagemChamadaCliente(id, nomeservico);
            }

            if (listartransferchamadacliente.size() > 0) {
                FacesContext fc = FacesContext.getCurrentInstance();
                fc.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "CONSULTADO COM SUCESSO!!!", ""));
            } else {
                FacesContext fc = FacesContext.getCurrentInstance();
                fc.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "CONSULTA NÃO REALIZADA!!!", ""));
                //setMonitorcliente(null);
            }

        }
    }

    public void inserirConvencionalTriagemChamadaCliente() throws SQLException, IOException, JRException {
        triagemchamadaclientetransfer.setNormalchamadatriagemchamadacliente("CONVENCIONAL");
        triagemchamadaclientetransfer.setPrioridadechamadatriagemchamadacliente(null);
        triagemchamadaclientetransfer.setEmitirsenhatriagemchamadacliente("SENHA EMITIDA");

        try {
            triagemchamadaclienteBO.inserirTriagemChamadaCliente(triagemchamadaclientetransfer); //To change body of generated methods, choose Tools | Templates.

            triagemchamadaclientepushmbmb.send("triagemchamadaclientepushmbmb");
            triagemchamadaprincipalclientepushmbmb.send("triagemchamadaprincipalclientepushmbmb");
            //retorna a última senha gerada!
            triagemchamadaclientetransfer.setNometriagemachamadacliente(triagemchamadaclienteBO.retornarUltimaSenha());
            // FacesContext fc = FacesContext.getCurrentInstance();
            // fc.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "SERVIÇO, " + triagemchamadaclientetransfer.getServicotransfer().getNomeservico() + " GERADO COM SUCESSO!!!", ""));
        } catch (Exception ex) {
            FacesContext fc = FacesContext.getCurrentInstance();
            fc.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "ERROR AO FAZER O PEDIDO DO SERVIÇO!!!", ""));
        }

        //relatorio.imprimirSenha(triagemchamadaclienteBO.retornarUltimaSenha(), "triagemchamadacliente");
    }

    public void inserirPrioridadeTriagemChamadaCliente() throws SQLException, IOException, JRException {
        triagemchamadaclientetransfer.setPrioridadechamadatriagemchamadacliente("PRIORIDADE");
        triagemchamadaclientetransfer.setNormalchamadatriagemchamadacliente(null);
        triagemchamadaclientetransfer.setEmitirsenhatriagemchamadacliente("SENHA EMITIDA");

        try {
            triagemchamadaclienteBO.inserirTriagemChamadaCliente(triagemchamadaclientetransfer); //To change body of generated methods, choose Tools | Templates.

            triagemchamadaclientepushmbmb.send("triagemchamadaclientepushmbmb");
            triagemchamadaprincipalclientepushmbmb.send("triagemchamadaprincipalclientepushmbmb");

            //retorna a última senha gerada!
            triagemchamadaclientetransfer.setNometriagemachamadacliente(triagemchamadaclienteBO.retornarUltimaSenha());

            //FacesContext fc = FacesContext.getCurrentInstance();
            //fc.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "SERVIÇO, " + triagemchamadaclientetransfer.getServicotransfer().getNomeservico() + " GERADO COM SUCESSO!!!", ""));
        } catch (Exception ex) {
            FacesContext fc = FacesContext.getCurrentInstance();
            fc.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "ERROR AO FAZER O PEDIDO DO SERVIÇO!!!", ""));
        }
        //relatorio.imprimirSenha(triagemchamadaclienteBO.retornarUltimaSenha(), "triagemchamadacliente");

    }

    public void deletarTriagemChamadaCliente() throws SQLException {

        setAtivargrowl(true);
        try {
            if (listarsenhatransferchamadacliente.size() > 0) {

                triagemchamadaclienteBO.deletarTriagemChamadaCliente(); //To change body of generated methods, choose Tools | Templates.
                triagemchamadaclientepushmbmb.send("triagemchamadaclientepushmbmb");
                //atualizar a grid de atendimentochamadacliente
                triagemchamadaclienteauxpushmbmb.send("triagemchamadaclienteauxpushmbmb");
                triagemchamadaprincipalclientepushmbmb.send("triagemchamadaprincipalclientepushmbmb");

                FacesContext fc = FacesContext.getCurrentInstance();
                fc.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "SENHA(S) DELETADA(S) COM SUCESSO!!!", ""));
            } else {

                //atualizar a grid de atendimentocliente
                triagemchamadaclienteauxpushmbmb.send("triagemchamadaclienteauxpushmbmb");
                triagemchamadaprincipalclientepushmbmb.send("triagemchamadaprincipalclientepushmbmb");

                FacesContext fc = FacesContext.getCurrentInstance();
                fc.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "NÃO A SENHA PARA SER DELETADA!!!", ""));
            }
        } catch (Exception ex) {
            FacesContext fc = FacesContext.getCurrentInstance();
            fc.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "ERROR AO DELETAR QUAISQUER SENHA!!!", ""));
        }
    }

    /**
     * Métodos com a característica de imprimir a senha do paciente em uma nova
     * pagina
     *
     * @throws java.io.IOException
     * @throws net.sf.jasperreports.engine.JRException
     */
    public void imprimirSenha() throws IOException, JRException, SQLException {
        //FacesContext context = FacesContext.getCurrentInstance();
        //Map<String, String> paramsm = context.getExternalContext().getRequestParameterMap();
        System.out.println(triagemchamadaclientetransfer.getNometriagemachamadacliente());
        //triagemchamadaclientetransfer.setNometriagemachamadacliente(paramsm.get("senhanometriagemachamadacliente"));
        relatorio.imprimirSenha(triagemchamadaclientetransfer.getNometriagemachamadacliente(), "monitortriagemchamadacliente");
        //context = null;
        //triagemchamadaclientetransfer.setNometriagemachamadacliente(null);
    }

    public void listarQuantidadeSenhasTodosTriagemChamadaCliente() throws SQLException {
        listarsenhatransferchamadacliente = triagemchamadaclienteBO.listarTodasSenhasTriagemChamadaCliente();
    }

    public void listarTodosTriagemChamadaCliente() throws SQLException {
        listartransferchamadacliente = triagemchamadaclienteBO.listarTodosTriagemChamadaCliente(); //To change body of generated methods, choose Tools | Templates.
    }

    /**
     *
     * @return @throws SQLException
     */
    public void listarSenhasTodosTriagemChamadaCliente() throws SQLException {
        listarsenhatransferchamadacliente = triagemchamadaclienteBO.listarTodasSenhasTriagemChamadaCliente();
    }

    /**
     * MÉTODOS GETTERS E SETTERS
     */
    public TriagemChamadaClienteTransfer getTriagemchamadaclientetransfer() {
        return triagemchamadaclientetransfer;
    }

    public void setTriagemchamadaclientetransfer(TriagemChamadaClienteTransfer triagemchamadaclientetransfer) {
        this.triagemchamadaclientetransfer = triagemchamadaclientetransfer;
    }

    public List<TriagemChamadaClienteTransfer> getListartransferchamadacliente(Short id, String nomeservico) throws SQLException {
        if (id == 1 && "".equalsIgnoreCase(nomeservico) || " ".equalsIgnoreCase(nomeservico)) {
            listartransferchamadacliente = triagemchamadaclienteBO.listarTodosTriagemChamadaCliente();
        } else if (id == 1 && !nomeservico.equalsIgnoreCase("")) {
            listartransferchamadacliente = triagemchamadaclienteBO.buscarServicoTriagemChamadaCliente(nomeservico);
        } else if (id != 1 && "".equalsIgnoreCase(nomeservico) || " ".equalsIgnoreCase(nomeservico)) {
            listartransferchamadacliente = triagemchamadaclienteBO.listarServicoPorIdUsuarioTriagemChamadaCliente(id);
        } else if (id != 1 && !nomeservico.equalsIgnoreCase("")) {
            listartransferchamadacliente = triagemchamadaclienteBO.listarServicoPorUsuarioTriagemChamadaCliente(id, nomeservico);
        }
        return listartransferchamadacliente;
    }

    public void setListartransferchamadacliente(List<TriagemChamadaClienteTransfer> listartransferchamadacliente) {
        this.listartransferchamadacliente = listartransferchamadacliente;
    }

    public List<TriagemChamadaClienteTransfer> getListarsenhatransferchamadacliente(Short id) throws SQLException {
        if(id==1){
            listarsenhatransferchamadacliente = triagemchamadaclienteBO.listarTodasSenhasTriagemChamadaCliente();
        }else{
            listarsenhatransferchamadacliente = triagemchamadaclienteBO.listarQuantidadedeSenhasPorUsuarioTriagemChamadaCliente(id);
        }
        return listarsenhatransferchamadacliente;
    }

    public void setListarsenhatransferchamadacliente(List<TriagemChamadaClienteTransfer> listarsenhatransferchamadacliente) {
        this.listarsenhatransferchamadacliente = listarsenhatransferchamadacliente;
    }

    public boolean isAtivargrowl() {
        return ativargrowl;
    }

    public void setAtivargrowl(boolean ativargrowl) {
        this.ativargrowl = ativargrowl;
    }

}
