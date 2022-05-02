/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.sga.bo;

import br.com.sga.dao.TriagemChamadaClienteDAO;
import br.com.sga.transfer.TriagemChamadaClienteTransfer;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Jandisson
 */
public class TriagemChamadaClienteBO extends TriagemChamadaClienteDAO {

    @Override
    public List<TriagemChamadaClienteTransfer> listarTodosTriagemChamadaCliente() throws SQLException {
        return super.listarTodosTriagemChamadaCliente(); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<TriagemChamadaClienteTransfer> listarTodasSenhasTriagemChamadaCliente() throws SQLException {
        return super.listarTodasSenhasTriagemChamadaCliente(); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<TriagemChamadaClienteTransfer> listarServicoPorIdUsuarioTriagemChamadaCliente(Short id) throws SQLException {
        return super.listarServicoPorIdUsuarioTriagemChamadaCliente(id); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<TriagemChamadaClienteTransfer> listarServicoPorUsuarioTriagemChamadaCliente(Short id, String nomeservico) throws SQLException {
        return super.listarServicoPorUsuarioTriagemChamadaCliente(id, nomeservico); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<TriagemChamadaClienteTransfer> listarQuantidadedeSenhasPorUsuarioTriagemChamadaCliente(Short id) throws SQLException {
        return super.listarQuantidadedeSenhasPorUsuarioTriagemChamadaCliente(id); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<TriagemChamadaClienteTransfer> buscarServicoTriagemChamadaCliente(String nomeservico) throws SQLException {
        return super.buscarServicoTriagemChamadaCliente(nomeservico); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean inserirTriagemChamadaCliente(TriagemChamadaClienteTransfer triagemchamadaclientetransfer) throws SQLException {
        return super.inserirTriagemChamadaCliente(triagemchamadaclientetransfer); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean deletarTriagemChamadaCliente() throws SQLException {
        return super.deletarTriagemChamadaCliente(); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public String retornarUltimaSenha() throws SQLException {
        return super.retornarUltimaSenha(); //To change body of generated methods, choose Tools | Templates.
    }

}
