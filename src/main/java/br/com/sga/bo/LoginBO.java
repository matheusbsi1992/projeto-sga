/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.sga.bo;

import br.com.sga.dao.LoginDAO;
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
public class LoginBO extends LoginDAO {

    public LoginBO() {
    }

    @Override
    public boolean login(String login, String senha) throws SQLException {
        return super.login(login, senha); //To change body of generated methods, choose Tools | Templates.
    }

    

    @Override
    public boolean alterarSenhadoUsuario(UsuarioTransfer usuariotransfer) {
        return super.alterarSenhadoUsuario(usuariotransfer); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public UsuarioTransfer pesquisarLogin(String login) throws SQLException {
        return super.pesquisarLogin(login); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<UsuarioTransfer> existeLogindePermissao(String login) throws SQLException {
        return super.existeLogindePermissao(login); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean existeUsuario(String usuario) {
        return super.existeUsuario(usuario); //To change body of generated methods, choose Tools | Templates.
    }

}
