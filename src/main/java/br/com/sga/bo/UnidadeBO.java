/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.sga.bo;

import br.com.sga.dao.UnidadeDAO;
import br.com.sga.transfer.UnidadeTransfer;
import java.sql.SQLException;

import java.util.List;

/**
 *
 * @author Jandisson
 */
public class UnidadeBO extends UnidadeDAO{


    @Override
    public boolean inserirUnidade(UnidadeTransfer unidadeTransfer) throws SQLException  {
        return super.inserirUnidade(unidadeTransfer); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean alterarUnidade(UnidadeTransfer unidadeTransfer) throws SQLException{
        return super.alterarUnidade(unidadeTransfer); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean deletarUnidade(Short idunidade) throws SQLException {
        return super.deletarUnidade(idunidade); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<UnidadeTransfer> listarTodosUnidade() throws SQLException {
        return super.listarTodosUnidade(); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<UnidadeTransfer> buscarUnidade(String nomeunidade) throws SQLException {
        return super.buscarUnidade(nomeunidade); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean idNomeUnidadeConfere(Short id, String nomeunidade) throws SQLException {
        return super.idNomeUnidadeConfere(id, nomeunidade); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean existeNomeUnidade(String nomeunidade) throws SQLException {
        return super.existeNomeUnidade(nomeunidade); //To change body of generated methods, choose Tools | Templates.
    }
    
    
    
}