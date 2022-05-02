/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.sga.bo;

import br.com.sga.dao.TriagemAlternativaDAO;
import br.com.sga.dao.UsuarioDAO;
import br.com.sga.transfer.CargoTransfer;
import br.com.sga.transfer.LocalTransfer;
import br.com.sga.transfer.ServicoTransfer;
import br.com.sga.transfer.TriagemAlternativaTransfer;
import br.com.sga.transfer.UnidadeTransfer;
import br.com.sga.transfer.UsuarioTransfer;
import java.sql.SQLException;

import java.util.List;

/**
 *
 * @author Jandisson
 */
public class UsuarioBO extends UsuarioDAO {

    @Override
    public boolean inserirUsuario(UsuarioTransfer usuariotransfer) throws SQLException {
        return super.inserirUsuario(usuariotransfer); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean alterarUsuario(UsuarioTransfer usuariotransfer, List<String> listarservicos) throws SQLException {
        return super.alterarUsuario(usuariotransfer, listarservicos); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean alterarSenhadoUsuario(UsuarioTransfer usuariotransfer) throws SQLException {
        return super.alterarSenhadoUsuario(usuariotransfer); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean deletarUsuario(Short idusuario) throws SQLException {
        return super.deletarUsuario(idusuario); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<UsuarioTransfer> listarTodosUsuario() throws SQLException {
        return super.listarTodosUsuario(); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<ServicoTransfer> listarServico() throws SQLException {
        return super.listarServico(); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<CargoTransfer> listarCargos() throws SQLException {
        return super.listarCargos(); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<UnidadeTransfer> listarUnidade() throws SQLException {
        return super.listarUnidade(); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean IdUsuarioConfere(Short id, String usuario) {
        return super.IdUsuarioConfere(id, usuario); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean existeUsuario(String usuario) {
        return super.existeUsuario(usuario); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean existeUsuarioId(Short idusuario) {
        return super.existeUsuarioId(idusuario); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<UsuarioTransfer> buscarUsuario(String nomedeusuario) throws SQLException {
        return super.buscarUsuario(nomedeusuario); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<UsuarioTransfer> listareBuscarServico(String nomeusuario) throws SQLException {
        return super.listareBuscarServico(nomeusuario); //To change body of generated methods, choose Tools | Templates.
    }

}
