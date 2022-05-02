/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.sga.dao;

import br.com.sga.bo.CargoBO;
import br.com.sga.transfer.CargoTransfer;
import br.com.sga.transfer.LocalTransfer;
import br.com.sga.transfer.ServicoTransfer;
import br.com.sga.transfer.TriagemAlternativaTransfer;
import br.com.sga.transfer.TriagemChamadaClienteTransfer;
import br.com.sga.transfer.UnidadeTransfer;
import br.com.sga.transfer.UsuarioTransfer;
import br.com.sga.util.Util;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Jandisson
 */
public class UsuarioDAO extends DAO {

    private final CargoDAO cargodao = new CargoDAO();
    private final ServicoDAO servicodao = new ServicoDAO();
    private final UnidadeDAO unidadedao = new UnidadeDAO();

    public UsuarioDAO() {
    }

    /**
     * Metodo responsavel por inserir usuario
     *
     * @param usuariotransfer
     * @return boolean
     * @throws java.sql.SQLException
     */
    public boolean inserirUsuario(UsuarioTransfer usuariotransfer) throws SQLException {
        try {
            strBuffer = new StringBuffer().append("INSERT INTO projetosga.usuario(\n"
                    + "             nomeusuario"
                    + "            ,sobrenomeusuario"
                    + "            ,senhausuario"
                    + "            ,statususuario"
                    + "            ,idcargo"
                    + "            ,idunidade)\n"
                    + "    VALUES (?"
                    + "            ,?"
                    + "            ,?"
                    + "            ,?"
                    + "            ,?"
                    + "            ,?);"
            );
            pstm = abrirconexao.getConexao().prepareStatement(strBuffer.toString());
            int tamusuario = 1;
            pstm.setObject(tamusuario++, usuariotransfer.getNomeusuario().toUpperCase());
            pstm.setObject(tamusuario++, usuariotransfer.getSobrenomeusuario().toUpperCase());
            pstm.setObject(tamusuario++, new Util().hashCode(usuariotransfer.getSenhausuario()).toHex());
            pstm.setObject(tamusuario++, usuariotransfer.getStatususuario());
            pstm.setObject(tamusuario++, usuariotransfer.getCargo().getId());
            pstm.setObject(tamusuario++, usuariotransfer.getUnidade().getId());

            //insira usuario
            pstm.executeUpdate();

            //obter o ultimo usuario na qual foi informado acima
            StringBuffer strBufferultimoidusuario = new StringBuffer().append("SELECT idusuario\n"
                    + "                            FROM projetosga.usuario"
                    + "                            ORDER BY idusuario"
                    + "                            DESC LIMIT 1;");
            PreparedStatement pstmultimoidusuario = abrirconexao.getConexao().prepareStatement(strBufferultimoidusuario.toString());
            ResultSet rsidusuario = pstmultimoidusuario.executeQuery();
            rsidusuario.next();
            //seta o ultimo valor do id de usuario
            usuariotransfer.setId(rsidusuario.getShort("idusuario"));
            pstmultimoidusuario.close();
            rsidusuario.close();
            //encerra o pstm e result do ultimo id do usuario

            //percorra os servicos
            for (Object iddescricaoservico : usuariotransfer.getDescricaousuarioservico()) {

                //abri uma nova string para a insercao de usuario e servico
                StringBuffer strBufferusuarioeservico = new StringBuffer().append("INSERT INTO projetosga.usuarioservico(\n"
                        + "            idusuario"
                        + "          , idservico)\n"
                        + "    VALUES (?"
                        + "          , ?);");
                PreparedStatement pstmusuarioeservico = abrirconexao.getConexao().prepareStatement(strBufferusuarioeservico.toString());

                pstmusuarioeservico.setObject(1, usuariotransfer.getId());
                pstmusuarioeservico.setObject(2, Short.parseShort(iddescricaoservico.toString()));

                //inseri usuario e servico
                pstmusuarioeservico.executeUpdate();

            }

        } catch (SQLException ex) {
            logPrincipal(UsuarioDAO.class).error(">>>>ERROR AO INSERIR USUARIO(inserirUsuario)", ex);
        } finally {
            pstm.close();
            abrirconexao.fecharConexao();
        }
        return true || false;
    }

    /**
     * Metodo responsavel por alterar usuario e os seus servicos
     *
     *
     * @param usuartiotransfer
     * @return boolean
     * @throws java.sql.SQLException
     */
    public boolean alterarUsuario(UsuarioTransfer usuariotransfer, List<String> listarservicos) throws SQLException {
        try {
            //editar o cargo 
            StringBuffer strBuffer = new StringBuffer().append("UPDATE projetosga.usuario\n"
                    + "   SET nomeusuario=?\n"
                    + "     , sobrenomeusuario=?\n"
                    + "     , idcargo=?\n"
                    + "     , idunidade=?"
                    + "     , statususuario=?\n"
                    + " WHERE idusuario=?;"
            );
            PreparedStatement pstmalterarusuario = abrirconexao.getConexao().prepareStatement(strBuffer.toString());
            int tamusuario = 1;
            pstmalterarusuario.setObject(tamusuario++, usuariotransfer.getNomeusuario().toUpperCase());
            pstmalterarusuario.setObject(tamusuario++, usuariotransfer.getSobrenomeusuario().toUpperCase());
            pstmalterarusuario.setObject(tamusuario++, buscarCargo(usuariotransfer.getCargo().getNomecargo()).iterator().next().getId());
            pstmalterarusuario.setObject(tamusuario++, buscarUnidade(usuariotransfer.getUnidade().getNomeunidade()).iterator().next().getId());
            pstmalterarusuario.setObject(tamusuario++, usuariotransfer.getStatususuario().toUpperCase());
            pstmalterarusuario.setObject(tamusuario++, usuariotransfer.getId());
            //atualizar na tabela usuario
            pstmalterarusuario.executeUpdate();
            //fechar pstm
            pstmalterarusuario.close();

            //deletar o usuario junto com os servicos
            StringBuffer strBufferdeletarusuarioservico = new StringBuffer().append("DELETE FROM"
                    + " projetosga.usuarioservico\n"
                    + " WHERE idusuario=?;");
            PreparedStatement pstmusuarioeservico = abrirconexao.getConexao().prepareStatement(strBufferdeletarusuarioservico.toString());
            pstmusuarioeservico.setObject(1, usuariotransfer.getId());
            //remova dados 
            pstmusuarioeservico.executeUpdate();
            pstmusuarioeservico.close();

            //listar os servicos
            for (Object idservicos : listarservicos) {
                //cria o servico
                ServicoTransfer servicoTransfer = new ServicoTransfer();
                //recebe o nome do servico
                servicoTransfer.setNomeservico(idservicos.toString());
                //recebe o servico
                usuariotransfer.setServico(servicoTransfer);

                //abri uma nova string para a insercao de usuario e servico
                StringBuffer strBufferusuarioeservico = new StringBuffer().append("INSERT INTO projetosga.usuarioservico(\n"
                        + "            idusuario"
                        + "          , idservico)\n"
                        + "    VALUES (?"
                        + "          , ?);");
                PreparedStatement pstmusuarioeservicos = abrirconexao.getConexao().prepareStatement(strBufferusuarioeservico.toString());

                pstmusuarioeservicos.setObject(1, usuariotransfer.getId());
                pstmusuarioeservicos.setObject(2, buscarServico(usuariotransfer.getServico().getNomeservico()).iterator().next().getId());

                //inseri usuario e servico
                pstmusuarioeservicos.executeUpdate();
            }

        } catch (SQLException ex) {
            logPrincipal(CargoDAO.class).error(">>>>ERROR AO ALTERAR O USUARIO(alterarCargo)", ex);
        } finally {
            abrirconexao.fecharConexao();
        }
        return true || false;
    }

    /**
     * Metodo responsavel por alterar a senha do usuario
     *
     * @param usuariotransfer
     * @return boolean
     * @throws java.sql.SQLException
     */
    public boolean alterarSenhadoUsuario(UsuarioTransfer usuariotransfer) throws SQLException {
        try {
            strBuffer = new StringBuffer().append("UPDATE projetosga.usuario\n"
                    + "    SET senhausuario = ?\n"
                    + "    WHERE idusuario=?\n"
            );
            pstm = abrirconexao.getConexao().prepareStatement(strBuffer.toString());
            int tamusuario = 1;
            pstm.setObject(tamusuario++, new Util().hashCode(usuariotransfer.getSenhausuario()).toHex());
            pstm.setObject(tamusuario++, usuariotransfer.getId());

            //alterar senha
            pstm.executeUpdate();

        } catch (SQLException ex) {
            logPrincipal(UsuarioDAO.class).error(">>>>ERROR AO ALTERAR A SENHA DO USUARIO(alterarSenhaUsuario)", ex);
        } finally {
            pstm.close();
            abrirconexao.fecharConexao();
        }
        return true || false;
    }

    /**
     * Metodo responsavel por buscar por id e nome do usuario
     *
     * @param id
     * @param usuario
     * @return boolean
     *
     *
     */
    public boolean IdUsuarioConfere(Short id, String usuario) {
        try {

            strBuffer = new StringBuffer().append("select idusuario, \n"
                    + "	nomeusuario\n"
                    + " from 	projetosga.usuario\n"
                    + " where idusuario	= ?\n"
                    + " and nomeusuario	= ?;");

            //pstm = conexao.prepareStatement(tbPessoa, PreparedStatement.RETURN_GENERATED_KEYS);
            pstm = abrirconexao.getConexao().prepareStatement(strBuffer.toString());

            pstm.setObject(1, id);
            pstm.setObject(2, usuario);
            rs = pstm.executeQuery();
            if (rs.next()) {
                return true;
            }

        } catch (SQLException ex) {
            logPrincipal(UsuarioDAO.class).error(">>>>ERROR AO BUSCAR USUARIO ATRAVES DO ID E NOME DO USUARIO (IdUsuarioConfere)!!!", ex);
        }
        return false;
    }

    /**
     * Metodo com a caracteristica de retornar uma lista de Dados
     *
     * @return List<UsuarioTransfer>
     * @throws java.sql.SQLException
     */
    public List<UsuarioTransfer> listarTodosUsuario() throws SQLException {
        List<UsuarioTransfer> usuarioTransfers = new ArrayList<UsuarioTransfer>();
        try {
            strBuffer = new StringBuffer().append("SELECT                                            usuario.idusuario\n"
                    + "                                                 ,usuario.nomeusuario\n"
                    + "                                                 ,usuario.sobrenomeusuario\n"
                    + "                                                 ,usuario.statususuario\n"
                    + "                                                 ,cargo.nomecargo\n"
                    + "                                                 ,unidade.nomeunidade\n"
                    + "                                                 ,servico.nomeservico\n"
                    + "                                                  ,servico.statusservico\n"
                    + "                                            FROM projetosga.usuario usuario\n"
                    + "                                            INNER JOIN projetosga.usuarioservico usuarioservico on (usuarioservico.idusuario = usuario.idusuario)\n"
                    + "                                            INNER JOIN projetosga.servico servico on (usuarioservico.idservico = servico.idservico)\n"
                    + "                                            INNER JOIN projetosga.cargo cargo on (cargo.idcargo = usuario.idcargo)\n"
                    + "                                            INNER JOIN projetosga.unidade unidade on (unidade.idunidade = usuario.idunidade)\n"
                    + "                                            ORDER BY usuario.nomeusuario;");
            pstm = abrirconexao.getConexao().prepareStatement(strBuffer.toString());
            rs = pstm.executeQuery();
            while (rs.next()) {
                //usuario
                UsuarioTransfer usuarioTransfer = new UsuarioTransfer();
                usuarioTransfer.setNomeusuario(rs.getString("nomeusuario"));
                usuarioTransfer.setId(rs.getShort("idusuario"));
                usuarioTransfer.setSobrenomeusuario(rs.getString("sobrenomeusuario"));
                usuarioTransfer.setStatususuario(rs.getString("statususuario"));
                //Cargo
                CargoTransfer cargoTransfer = new CargoTransfer();
                cargoTransfer.setNomecargo(rs.getString("nomecargo"));
                usuarioTransfer.setCargo(cargoTransfer);
                //Unidade
                UnidadeTransfer unidadeTransfer = new UnidadeTransfer();
                unidadeTransfer.setNomeunidade(rs.getString("nomeunidade"));
                usuarioTransfer.setUnidade(unidadeTransfer);
                //servico
                ServicoTransfer servicoTransfer = new ServicoTransfer();
                if (rs.getString("statusservico").equalsIgnoreCase("A")) {
                    servicoTransfer.setNomeservico(rs.getString("nomeservico"));
                }
                usuarioTransfer.setServico(servicoTransfer);

                usuarioTransfers.add(usuarioTransfer);
            }
        } catch (SQLException ex) {
            logPrincipal(UsuarioDAO.class).error(">>>>ERROR AO LISTAR USUARIO(listarTodosUsuario)", ex);
        } finally {
            rs.close();
            abrirconexao.fecharConexao();
        }
        return usuarioTransfers;
    }

    /**
     * Metodo com a caracteristica de retornar uma lista de Dados atraves da
     * busca do usuario
     *
     * @return List<UsuarioTransfer>
     * @throws java.sql.SQLException
     */
    public List<UsuarioTransfer> buscarUsuario(String nomedeusuario) throws SQLException {
        List<UsuarioTransfer> usuarioTransfers = new ArrayList<UsuarioTransfer>();
        try {
            strBuffer = new StringBuffer().append("SELECT \n"
                    + "        usuario.idusuario\n"
                    + "       ,usuario.nomeusuario\n"
                    + "       ,usuario.sobrenomeusuario\n"
                    + "       ,usuario.statususuario\n"
                    + "       ,cargo.nomecargo\n"
                    + "       ,unidade.nomeunidade\n"
                    + "       ,servico.nomeservico\n"
                    + "  FROM projetosga.usuario usuario\n"
                    + "  INNER JOIN projetosga.usuarioservico usuarioservico on (usuarioservico.idusuario = usuario.idusuario)\n"
                    + "  INNER JOIN projetosga.servico servico on (usuarioservico.idservico = servico.idservico)\n"
                    + "  INNER JOIN projetosga.cargo cargo on (cargo.idcargo = usuario.idcargo)\n"
                    + "  INNER JOIN projetosga.unidade unidade on (unidade.idunidade = usuario.idunidade)\n"
                    + "  WHERE UPPER(usuario.nomeusuario)\n"
                    + "  LIKE UPPER(?)\n");
            pstm = abrirconexao.getConexao().prepareStatement(strBuffer.toString());
            pstm.setObject(1, "%" + nomedeusuario + "%");
            rs = pstm.executeQuery();
            while (rs.next()) {
                //usuario
                UsuarioTransfer usuarioTransfer = new UsuarioTransfer();
                usuarioTransfer.setNomeusuario(rs.getString("nomeusuario"));
                usuarioTransfer.setId(rs.getShort("idusuario"));
                usuarioTransfer.setSobrenomeusuario(rs.getString("sobrenomeusuario"));
                usuarioTransfer.setStatususuario(rs.getString("statususuario"));
                //Cargo
                CargoTransfer cargoTransfer = new CargoTransfer();
                cargoTransfer.setNomecargo(rs.getString("nomecargo"));
                usuarioTransfer.setCargo(cargoTransfer);
                //Unidade
                UnidadeTransfer unidadeTransfer = new UnidadeTransfer();
                unidadeTransfer.setNomeunidade(rs.getString("nomeunidade"));
                usuarioTransfer.setUnidade(unidadeTransfer);
                //servico
                ServicoTransfer servicoTransfer = new ServicoTransfer();
                servicoTransfer.setNomeservico(rs.getString("nomeservico"));
                usuarioTransfer.setServico(servicoTransfer);

                usuarioTransfers.add(usuarioTransfer);
            }
        } catch (SQLException ex) {
            logPrincipal(UsuarioDAO.class).error(">>>>ERROR AO BUSCAR USUARIO(buscarUsuario)", ex);
        } finally {
            rs.close();
            abrirconexao.fecharConexao();
        }
        return usuarioTransfers;
    }

    /**
     * Metodo responsavel por buscar um usuario no sistema perguntando se ele
     * existe ou nao
     *
     * @return boolean
     * @param String usuario
     */
    public boolean existeUsuario(String usuario) {
        ResultSet rsd=null;
        try {
            strBuffer = new StringBuffer().append("SELECT  nomeusuario\n"
                    + "  FROM projetosga.usuario\n"
                    + "  WHERE nomeusuario =?;");
            pstm = abrirconexao.getConexao().prepareStatement(strBuffer.toString());
            pstm.setObject(1, usuario);

            rsd = pstm.executeQuery();

            if (rsd.next()) {
                return true;
            }

        } catch (SQLException ex) {
            logPrincipal(UsuarioDAO.class).error(">>>>ERROR AO TENTAR BUSCAR O USUARIO (existeUsuario)!!!", ex);
        }
        return false;
    }

    /**
     * Metodo responsavel por buscar um usuario no sistema perguntando se ele
     * existe ou nao, atraves do ID
     *
     * @return boolean
     * @param String usuario
     */
    public boolean existeUsuarioId(Short idusuario) {
        try {
            strBuffer = new StringBuffer().append("SELECT  nomeusuario\n"
                    + "  FROM projetosga.usuario\n"
                    + "  WHERE idusuario =?;");
            pstm = abrirconexao.getConexao().prepareStatement(strBuffer.toString());
            pstm.setObject(1, idusuario);

            rs = pstm.executeQuery();

            if (rs.next()) {
                return true;
            }

        } catch (SQLException ex) {
            logPrincipal(UsuarioDAO.class).error(">>>>ERROR AO TENTAR BUSCAR O USUARIO PELO ID (existeUsuarioId)!!!", ex);
        }
        return false;
    }

    /**
     * Metodo responsavel por deletar um determinado usuario
     *
     * @param short idusuario
     */
    public boolean deletarUsuario(Short idusuario) throws SQLException {
        try {
            strBuffer = new StringBuffer().append("DELETE FROM projetosga.usuario\n"
                    + " WHERE idusuario = ?");
            //pstm = abrirconexao.getConexao().prepareStatement(tbPessoa, PreparedStatement.RETURN_GENERATED_KEYS);
            pstm = abrirconexao.getConexao().prepareStatement(strBuffer.toString());
            pstm.setObject(1, idusuario);
            //remova id da galeria tabela Galeria
            pstm.executeUpdate();
        } catch (SQLException ex) {
            logPrincipal(UsuarioDAO.class).error(">>>>ERROR AO DELETAR POR USUARIO(deletarUsuario)", ex);
        } finally {
            pstm.close();
            abrirconexao.fecharConexao();
        }
        return true || false;
    }

    /**
     * Metodo responsavel por buscar as permissoes existentes repeticoes
     *
     * @param nomeusuario
     * @return boolean
     * @throws java.sql.SQLException
     */
    public List<UsuarioTransfer> listareBuscarServico(String nomeusuario) throws SQLException {
        List<UsuarioTransfer> listarbuscarusuarioservico = new ArrayList<UsuarioTransfer>();
        try {

            strBuffer = new StringBuffer().append("SELECT \n"
                    + "       servico.nomeservico\n"
                    + "  FROM projetosga.usuario usuario\n"
                    + "  INNER JOIN projetosga.usuarioservico usuarioservico on (usuarioservico.idusuario = usuario.idusuario)\n"
                    + "  INNER JOIN projetosga.servico servico on (usuarioservico.idservico = servico.idservico)\n"
                    + "  WHERE usuario.nomeusuario=?"
                    + "  ORDER BY servico.nomeservico");

            pstm = abrirconexao.getConexao().prepareStatement(strBuffer.toString());
            pstm.setObject(1, nomeusuario);

            rs = pstm.executeQuery();

            while (rs.next()) {
                UsuarioTransfer usuarioTransfer = new UsuarioTransfer();
                ServicoTransfer servicoTransfer = new ServicoTransfer();
                servicoTransfer.setNomeservico(rs.getString("nomeservico"));
                usuarioTransfer.setServico(servicoTransfer);
                listarbuscarusuarioservico.add(usuarioTransfer);
            }

            return listarbuscarusuarioservico;

        } catch (SQLException ex) {
            logPrincipal(UsuarioDAO.class).error(">>>>ERROR AO LISTAR BUSCAR SERVICO DAS PERMISSOES(listarPermissoes)!!!", ex);
        } finally {
            pstm.close();
            rs.close();
        }
        return null;
    }

    /**
     * Metodo responsavel por retornar os dados do cargo
     *
     * @return List<CargoTransfer>
     * @throws java.sql.SQLException
     */
    public List<CargoTransfer> listarCargos() throws SQLException {
        return cargodao.listarTodosCargos();
    }

    /**
     * Metodo responsavel por retornar os dados do Cargo atraves de uma busca do
     * Cargo
     *
     * @return List<CargoTransfer>
     * @throws java.sql.SQLException
     */
    public List<CargoTransfer> buscarCargo(String nomecargo) throws SQLException {
        return cargodao.buscarTodosCargos(nomecargo);
    }

    /**
     * Metodo responsavel por retornar os dados da Unidade
     *
     * @return List<UnidadeTransfer>
     * @throws java.sql.SQLException
     */
    public List<UnidadeTransfer> listarUnidade() throws SQLException {
        return unidadedao.listarTodosUnidade();
    }

    /**
     * Metodo responsavel por retornar os dados da Unidade
     *
     * @return List<UnidadeTransfer>
     * @throws java.sql.SQLException
     */
    public List<UnidadeTransfer> buscarUnidade(String nomeunidade) throws SQLException {
        return unidadedao.buscarUnidade(nomeunidade);
    }

    /**
     * Metodo responsavel por retornar os dados do Servico
     *
     * @return List<ServicoTransfer>
     * @throws java.sql.SQLException
     */
    public List<ServicoTransfer> listarServico() throws SQLException {
        List<ServicoTransfer> servicoTransfers = new ArrayList<ServicoTransfer>();
        try {
            strBuffer = new StringBuffer().append("SELECT *"
                    + "  FROM projetosga.servico"
                    + "  WHERE statusservico='A'   "
                    + "  ORDER BY nomeservico");
            pstm = abrirconexao.getConexao().prepareStatement(strBuffer.toString());
            rs = pstm.executeQuery();
            while (rs.next()) {
                ServicoTransfer servicoTransfer = new ServicoTransfer();
                servicoTransfer.setId(rs.getShort("idservico"));
                servicoTransfer.setNomeservico(rs.getString("nomeservico"));
                servicoTransfer.setStatusservico(rs.getString("statusservico"));
                servicoTransfer.setDescricaoservico(rs.getString("descricaoservico"));
                servicoTransfers.add(servicoTransfer);
            }
        } catch (SQLException ex) {
            logPrincipal(ServicoDAO.class).error(">>>>ERROR AO LISTAR SERVICO(listarTodosServico)", ex);
        } finally {
            rs.close();
            abrirconexao.fecharConexao();
        }
        return servicoTransfers;
    }

    /**
     * Metodo responsavel por retornar os dados do Servico
     *
     * @return List<ServicoTransfer>
     * @throws java.sql.SQLException
     */
    public List<ServicoTransfer> buscarServico(String nomeservico) throws SQLException {
        return servicodao.buscarServico(nomeservico);
    }

}
