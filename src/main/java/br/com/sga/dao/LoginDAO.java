/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.sga.dao;

import br.com.sga.transfer.CargoTransfer;
import br.com.sga.transfer.UsuarioTransfer;
import br.com.sga.util.Util;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Jandisson
 */
public class LoginDAO extends DAO {

    public LoginDAO() {
    }

    /**
     * Metodo que retornar boleano do login atributo login
     *
     * @param String login
     * @return List<PessoaT>
     * @exception SQLException
     */
    public boolean login(String login, String senha) throws SQLException {

        try {
            strBuffer = new StringBuffer().append(" SELECT\n"
                    + "                            usuario.nomeusuario"
                    + "                            ,usuario.senhausuario"
                    + "                       FROM projetosga.usuario usuario\n"
                    + "                       INNER JOIN projetosga.cargo cargo on (cargo.idcargo = usuario.idcargo)\n"
                    + "                       WHERE (usuario.nomeusuario = ? and usuario.senhausuario= ?)");
            pstm = abrirconexao.getConexao().prepareStatement(strBuffer.toString());
            pstm.setObject(1, login);
            pstm.setObject(2, new Util().hashCode(senha).toHex());

            rs = pstm.executeQuery();

            if (rs.next()) {
                return true;
            } else {
                return false;
            }

        } catch (SQLException ex) {
            logPrincipal(LoginDAO.class).error(">>>>ERROR AO LISTAR LOGIN(login)", ex);
        } finally {
            rs.close();
            pstm.close();
            abrirconexao.fecharConexao();
        }
        return false;
    }

    /**
     * Metodo que retornar boleano do login atributo login
     *
     * @param String login
     * @return List<PessoaT>
     * @exception SQLException
     */
    public UsuarioTransfer pesquisarLogin(String login) throws SQLException {
        List<UsuarioTransfer> listadelogin = new ArrayList<UsuarioTransfer>();
        try {
            strBuffer = new StringBuffer().append(" SELECT\n"
                    + "                             usuario.idusuario\n"
                    + "                            ,usuario.nomeusuario"
                    + "                            ,usuario.sobrenomeusuario\n"
                    + "                            ,usuario.statususuario"
                    + "                            ,cargo.nomecargo\n"
                    + "                       FROM projetosga.usuario usuario\n"
                    + "                       INNER JOIN projetosga.cargo cargo on (cargo.idcargo = usuario.idcargo)\n"
                    + "                       WHERE (usuario.nomeusuario = ?)");
            pstm = abrirconexao.getConexao().prepareStatement(strBuffer.toString());
            pstm.setObject(1, login);

            rs = pstm.executeQuery();

            while (rs.next()) {
                //usuario
                UsuarioTransfer usuarioTransfer = new UsuarioTransfer();
                usuarioTransfer.setId(rs.getShort("idusuario"));
                usuarioTransfer.setNomeusuario(rs.getString("nomeusuario"));
                usuarioTransfer.setSobrenomeusuario(rs.getString("sobrenomeusuario"));
                usuarioTransfer.setStatususuario(rs.getString("statususuario"));
                //cargo
                usuarioTransfer.getCargo().setNomecargo(rs.getString("nomecargo"));
                listadelogin.add(usuarioTransfer);
            }

        } catch (SQLException ex) {
            logPrincipal(LoginDAO.class).error(">>>>ERROR AO PESQUISAR LOGIN(pesquisarLogin)", ex);
        } finally {
            rs.close();
            pstm.close();
            abrirconexao.fecharConexao();
        }
        return listadelogin.size() > 0 ? listadelogin.get(0) : null;
    }

    /**
     * Metodo que retornar boleano do login atributo login
     *
     * @param String login
     * @return List<PessoaT>
     * @exception SQLException
     */
    public List<UsuarioTransfer> existeLogindePermissao(String login) throws SQLException {
        List<UsuarioTransfer> listadelogin = new ArrayList<UsuarioTransfer>();
        try {
            strBuffer = new StringBuffer().append("SELECT\n"
                    + "                                                 distinct(permissao.nomepermissao)\n"
                    + "                                            FROM projetosga.usuario usuario\n"
                    + "                                            INNER JOIN projetosga.cargo cargo on (cargo.idcargo = usuario.idcargo)\n"
                    + "                                            INNER JOIN projetosga.cargospermissoes cargospermissoes on (cargospermissoes.idcargo = cargo.idcargo)\n"
                    + "                                            INNER JOIN projetosga.permissao permissao on (cargospermissoes.idpermissao = permissao.idpermissao)\n"
                    + "                                            WHERE (usuario.nomeusuario = ?)");
            pstm = abrirconexao.getConexao().prepareStatement(strBuffer.toString());
            pstm.setObject(1, login);

            rs = pstm.executeQuery();

            while (rs.next()) {
                //usuario

                UsuarioTransfer usuarioTransfer = new UsuarioTransfer();
                //Cargo
                CargoTransfer cargoTransfer = new CargoTransfer();
                //informar a descricao da permissao, para o usuario de administradtor

                cargoTransfer.setNomepermissao(rs.getString("nomepermissao"));
                if (login.toUpperCase().equalsIgnoreCase("ADMINISTRADOR") && cargoTransfer.getNomepermissao().toUpperCase().equalsIgnoreCase("ATENDIMENTO")) {
                    cargoTransfer.setNomepermissao("");
                }
                usuarioTransfer.setCargo(cargoTransfer);

                listadelogin.add(usuarioTransfer);
            }

        } catch (SQLException ex) {
            logPrincipal(LoginDAO.class).error(">>>>ERROR AO BUSCAR EXISTE LOGIN DE PERMISSAO (existeLogindePermissao)", ex);
        } finally {
            rs.close();
            abrirconexao.fecharConexao();
        }
        return listadelogin;
    }

    /**
     * Metodo responsavel por buscar um usuario no sistema perguntando se ele
     * existe ou nao
     *
     * @return boolean
     * @param String usuario
     */
    public boolean existeUsuario(String usuario) {
        try {
            strBuffer = new StringBuffer().append("SELECT  nomeusuario\n"
                    + "  FROM projetosga.usuario\n"
                    + "  WHERE nomeusuario =?;");
            pstm = abrirconexao.getConexao().prepareStatement(strBuffer.toString());
            pstm.setObject(1, usuario);

            rs = pstm.executeQuery();

            if (rs.next()) {
                return true;
            }

        } catch (SQLException ex) {
            logPrincipal(UsuarioDAO.class).error(">>>>ERROR AO TENTAR BUSCAR O USUARIO (existeUsuario)!!!", ex);
        }
        return false;
    }

    /**
     * Metodo responsavel por alterar a senha do Usuario
     *
     * @param usuariotransfer
     * @return verdadeiro para a alteracao da senha senao retorne falso para a
     * nao alteracao da senha do usuario
     *
     */
    public boolean alterarSenhadoUsuario(UsuarioTransfer usuariotransfer) {
        try {
            strBuffer = new StringBuffer().append("UPDATE projetosga.usuario \n"
                    + "  SET senhausuario=?\n"
                    + "  WHERE idusuario =?;");
            pstm = abrirconexao.getConexao().prepareStatement(strBuffer.toString());

            pstm.setObject(1, new Util().hashCode(usuariotransfer.getSenhausuario()).toHex());
            pstm.setObject(2, usuariotransfer.getId());

            pstm.executeUpdate();

            return true;

        } catch (SQLException ex) {
            logPrincipal(LoginDAO.class).error(">>>>ERROR AO TENTAR ALTERAR A SENHA DO USUARIO(alterarSenhadoUsuario)!!!", ex);
        }
        return false;
    }

}
