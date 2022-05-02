/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.sga.bo;

import br.com.sga.dao.LocalDAO;
import br.com.sga.dao.LocalDAO;
import br.com.sga.transfer.LocalTransfer;
import java.sql.SQLException;

import java.util.List;

/**
 *
 * @author Jandisson
 */
public class LocalBO extends LocalDAO {

    @Override
    public boolean inserirLocal(LocalTransfer localTransfer) throws SQLException {
        return super.inserirLocal(localTransfer); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean alterarLocal(LocalTransfer localTransfer) throws SQLException {
        return super.alterarLocal(localTransfer); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public LocalTransfer buscarLocalExistente(String nomelocal) throws SQLException {
        return super.buscarLocalExistente(nomelocal); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean deletarLocal(Short idlocal) throws SQLException {
        return super.deletarLocal(idlocal); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<LocalTransfer> listarTodosLocal() throws SQLException {
        return super.listarTodosLocal(); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<LocalTransfer> buscarLocal(String nomelocal) throws SQLException {
        return super.buscarLocal(nomelocal); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean idNomeLocalConfere(Short id, String nomelocal) throws SQLException {
        return super.idNomeLocalConfere(id, nomelocal); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean existeNomeLocal(String nomelocal) throws SQLException {
        return super.existeNomeLocal(nomelocal); //To change body of generated methods, choose Tools | Templates.
    }

}
