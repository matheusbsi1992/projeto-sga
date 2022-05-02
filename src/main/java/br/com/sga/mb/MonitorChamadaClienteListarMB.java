/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.sga.mb;

import br.com.sga.bo.MonitorChamadaClienteBO;
import br.com.sga.transfer.ServicoTransfer;
import br.com.sga.transfer.TriagemChamadaClienteTransfer;
import java.io.Serializable;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.bean.SessionScoped;
import javax.faces.bean.ViewScoped;

import javax.faces.context.FacesContext;

/**
 *
 * @author Jandisson
 */
@ManagedBean
@ViewScoped
public final class MonitorChamadaClienteListarMB implements Serializable {

    public MonitorChamadaClienteListarMB() throws SQLException {
        listarMonitorSenhasTodosTriagemChamadaCliente();
    }

    private boolean ativoOhInativoBotao = true;
    //private String monitorcliente;
    private TriagemChamadaClienteTransfer triagemchamadaclientetransfer = new TriagemChamadaClienteTransfer();

    private List<TriagemChamadaClienteTransfer> listarmonitorsenhatransferchamadacliente = new ArrayList<TriagemChamadaClienteTransfer>();

    private MonitorChamadaClienteBO monitorchamadaclienteBO = new MonitorChamadaClienteBO();

    //metodo responsavel por consultar pelo nome da servico
    public void consultarAction(Short id, String monitor, String ativoohinativo) throws SQLException {
        listarmonitorsenhatransferchamadacliente = null;
        if (listarMonitorSenhaTransferchamadacliente(id, monitor, ativoohinativo).size() > 0) {
            FacesContext fc = FacesContext.getCurrentInstance();
            fc.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "CONSULTADO COM SUCESSO!!!", ""));
            //setMonitorcliente(null);
        } else {
            FacesContext fc = FacesContext.getCurrentInstance();
            fc.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "CONSULTA NÃO REALIZADA!!!", ""));
            //setMonitorcliente(null);
        }
        monitor = null;
    }

    /**
     *
     * @return @throws SQLException
     */
    public void listarMonitorSenhasTodosTriagemChamadaCliente() throws SQLException {
        listarmonitorsenhatransferchamadacliente = monitorchamadaclienteBO.listarTodasSenhasMonitorTriagemChamadaCliente();
    }

    public List<TriagemChamadaClienteTransfer> listarMonitorSenhaTransferchamadacliente(Short id, String monitor, String valorAtivoInativo) throws SQLException {
        if ((id != 1 || id == 1) && "I".equalsIgnoreCase(valorAtivoInativo)) {
            ativoOhInativoBotao = false;
        }
        if ((id != 1 || id == 1) && "A".equalsIgnoreCase(valorAtivoInativo)) {
            ativoOhInativoBotao = true;
        }
        if ((id == 1 && ("A").equalsIgnoreCase(valorAtivoInativo)) && (("".equalsIgnoreCase(monitor) || " ".equalsIgnoreCase(monitor)))) {
            listarmonitorsenhatransferchamadacliente = monitorchamadaclienteBO.listarTodasSenhasMonitorTriagemChamadaCliente();
        } else if ((id == 1 && ("I").equalsIgnoreCase(valorAtivoInativo)) && (("".equalsIgnoreCase(monitor) || " ".equalsIgnoreCase(monitor)))) {
            listarmonitorsenhatransferchamadacliente = monitorchamadaclienteBO.buscarSenhasAtivasMonitorTriagemChamadaCliente(monitor, valorAtivoInativo);
        } else if ((id == 1 && (("A").equalsIgnoreCase(valorAtivoInativo) || ("I").equalsIgnoreCase(valorAtivoInativo))) && ((!"".equalsIgnoreCase(monitor) || !" ".equalsIgnoreCase(monitor)))) {
            listarmonitorsenhatransferchamadacliente = monitorchamadaclienteBO.buscarSenhasAtivasMonitorTriagemChamadaCliente(monitor, valorAtivoInativo);
        } else if (id != 1 && (monitor.equalsIgnoreCase("") || monitor.equalsIgnoreCase(" ")) && (valorAtivoInativo.equalsIgnoreCase("") || valorAtivoInativo.equalsIgnoreCase(" ") || valorAtivoInativo.equalsIgnoreCase("A") || valorAtivoInativo.equalsIgnoreCase("I"))) {
            listarmonitorsenhatransferchamadacliente = monitorchamadaclienteBO.listarTodasSenhasPorServicoUsuarioMonitorTriagemChamadaCliente(id, valorAtivoInativo);
        } else if (id != 1 && (("A").equalsIgnoreCase(valorAtivoInativo) || ("I").equalsIgnoreCase(valorAtivoInativo)) && (!"".equalsIgnoreCase(monitor) || !" ".equalsIgnoreCase(monitor))) {
            listarmonitorsenhatransferchamadacliente = monitorchamadaclienteBO.buscarSenhasPorServicoUsuarioMonitorTriagemChamadaCliente(id, valorAtivoInativo, monitor.toUpperCase());
        }
        monitor = null;
        return listarmonitorsenhatransferchamadacliente;
    }

    public List<TriagemChamadaClienteTransfer> getListarmonitorsenhatransferchamadacliente(Short id, String monitor, String valorAtivoInativo) throws SQLException {
        return listarMonitorSenhaTransferchamadacliente(id, monitor, valorAtivoInativo);
    }

    public void setListarmonitorsenhatransferchamadacliente(List<TriagemChamadaClienteTransfer> listarmonitorsenhatransferchamadacliente) {
        this.listarmonitorsenhatransferchamadacliente = listarmonitorsenhatransferchamadacliente;
    }

    public TriagemChamadaClienteTransfer getTriagemchamadaclientetransfer() {
        return triagemchamadaclientetransfer;
    }

    public void setTriagemchamadaclientetransfer(TriagemChamadaClienteTransfer triagemchamadaclientetransfer) {
        this.triagemchamadaclientetransfer = triagemchamadaclientetransfer;
    }

    public boolean isAtivoOhInativoBotao() {
        return ativoOhInativoBotao;
    }

    public void setAtivoOhInativoBotao(boolean ativoOhInativoBotao) {
        this.ativoOhInativoBotao = ativoOhInativoBotao;
    }

}
