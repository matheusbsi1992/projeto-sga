/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.sga.bo;

import br.com.sga.dao.ServicoDAO;
import br.com.sga.dao.ServicoDAO;
import br.com.sga.transfer.ServicoTransfer;
import java.sql.SQLException;

import java.util.List;

/**
 *
 * @author Jandisson
 */
public class ServicoBO extends ServicoDAO {

    @Override
    public boolean inserirServico(ServicoTransfer servicoTransfer) throws SQLException {
        return super.inserirServico(servicoTransfer); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean alterarServico(ServicoTransfer servicoTransfer) throws SQLException {
        return super.alterarServico(servicoTransfer); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean deletarServico(Short idservico) throws SQLException {
        return super.deletarServico(idservico); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean deletarSenhasServico(Short idservico) throws SQLException {
        return super.deletarSenhasServico(idservico); //To change body of generated methods, choose Tools | Templates.
    }


    @Override
    public List<ServicoTransfer> listarTodosServico() throws SQLException {
        return super.listarTodosServico(); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<ServicoTransfer> buscarServico(String nomeservico) throws SQLException {
        return super.buscarServico(nomeservico); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<ServicoTransfer> buscarServicoUsuario(String nomedeusuario) throws SQLException {
        return super.buscarServicoUsuario(nomedeusuario); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean idNomeServicoConfere(Short id, String nomeservico) throws SQLException {
        return super.idNomeServicoConfere(id, nomeservico); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean existeNomeServico(String nomeservico) throws SQLException {
        return super.existeNomeServico(nomeservico); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean existeSenhaServico(Short id) throws SQLException {
        return super.existeSenhaServico(id); //To change body of generated methods, choose Tools | Templates.
    }
    
    @Override
    public List<ServicoTransfer> listareBuscarServicoUsuario(Short id) throws SQLException {
        return super.listareBuscarServicoUsuario(id); //To change body of generated methods, choose Tools | Templates.
    }

}
