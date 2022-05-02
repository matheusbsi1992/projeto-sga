/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.sga.mb;

import br.com.sga.bo.TriagemAtendimentoChamadaClienteBO;
import br.com.sga.transfer.TriagemAtendimentoClienteTransfer;
import java.io.Serializable;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;

/**
 *
 * @author Jandisson
 */
@RequestScoped
@ManagedBean
public class TriagemListarAtendimentoChamadaCliente implements Serializable {

    //cria variavel local triagematendimentochamadaclientebo
    private TriagemAtendimentoChamadaClienteBO triagematendimentochamadaclientebo = new TriagemAtendimentoChamadaClienteBO();

    public TriagemListarAtendimentoChamadaCliente() throws SQLException {
        listarTriagemAtendimentoTransferChamadaCliente();
    }

    //cria a lista para visualizar todos os dados
    private List<TriagemAtendimentoClienteTransfer> listartodostriagematendimentotransferchamadacliente = new ArrayList<TriagemAtendimentoClienteTransfer>();

    /**
     * Método responsável por listar todos os dados
     */
    public void listarTriagemAtendimentoTransferChamadaCliente() throws SQLException {
        listartodostriagematendimentotransferchamadacliente = triagematendimentochamadaclientebo.listarTriagemAtendimentoChamadaClienteAtendido();
    }

    public List<TriagemAtendimentoClienteTransfer> getListartodostriagematendimentotransferchamadacliente() {
        return listartodostriagematendimentotransferchamadacliente;
    }

    public void setListartodostriagematendimentotransferchamadacliente(List<TriagemAtendimentoClienteTransfer> listartodostriagematendimentotransferchamadacliente) {
        this.listartodostriagematendimentotransferchamadacliente = listartodostriagematendimentotransferchamadacliente;
    }

}
