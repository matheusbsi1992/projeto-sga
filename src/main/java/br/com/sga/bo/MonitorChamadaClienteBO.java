/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.sga.bo;

import br.com.sga.dao.MonitorChamadaClienteDAO;
import br.com.sga.dao.TriagemChamadaClienteDAO;
import br.com.sga.transfer.Category;
import br.com.sga.transfer.ServicoTransfer;
import br.com.sga.transfer.TriagemChamadaClienteTransfer;
import java.sql.Connection;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.faces.model.SelectItem;

/**
 *
 * @author Jandisson
 */
public class MonitorChamadaClienteBO extends MonitorChamadaClienteDAO {

    @Override
    public boolean atualizarCancelarSenhaMonitorTriagemChamadaCliente(String nometriagemachamadacliente, String nomeusuario) throws SQLException {
        return super.atualizarCancelarSenhaMonitorTriagemChamadaCliente(nometriagemachamadacliente, nomeusuario); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<TriagemChamadaClienteTransfer> listarTodasSenhasMonitorTriagemChamadaCliente() throws SQLException {
        return super.listarTodasSenhasMonitorTriagemChamadaCliente(); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<TriagemChamadaClienteTransfer> listarTodasSenhasPorServicoUsuarioMonitorTriagemChamadaCliente(Short id, String ativoInativo) throws SQLException {        
    
        return super.listarTodasSenhasPorServicoUsuarioMonitorTriagemChamadaCliente(id, ativoInativo); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<TriagemChamadaClienteTransfer> buscarSenhasPorServicoUsuarioMonitorTriagemChamadaCliente(Short id, String ativoInativo, String nomeSenha) throws SQLException {
        return super.buscarSenhasPorServicoUsuarioMonitorTriagemChamadaCliente(id, ativoInativo, nomeSenha); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<TriagemChamadaClienteTransfer> buscarSenhasAtivasMonitorTriagemChamadaCliente(String siglatriagem, String senhasAtivasInativas) throws SQLException {
        return super.buscarSenhasAtivasMonitorTriagemChamadaCliente(siglatriagem, senhasAtivasInativas); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<TriagemChamadaClienteTransfer> buscarTriagemAlternativaeServico(String siglatriagem) throws SQLException {
        return super.buscarTriagemAlternativaeServico(siglatriagem); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean nomeDaTriagemChamadaClienteAtivaouInativa(String nometriagemchamadacliente) throws SQLException {
        return super.nomeDaTriagemChamadaClienteAtivaouInativa(nometriagemchamadacliente); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<TriagemChamadaClienteTransfer> listarTodasSiglas() throws SQLException {
        return super.listarTodasSiglas(); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<SelectItem> listarTodasSiglasAgrupadas() throws SQLException {
        return super.listarTodasSiglasAgrupadas(); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<SelectItem> listarTodasSiglasPorServicodoUsuarioAgrupadas(Short id) throws SQLException {
        return super.listarTodasSiglasPorServicodoUsuarioAgrupadas(id); //To change body of generated methods, choose Tools | Templates.
    }

}
