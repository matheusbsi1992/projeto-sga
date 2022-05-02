/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.sga.dao;

import br.com.sga.transfer.ServicoTransfer;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Jandisson
 */
public class ServicoDAO extends DAO {

    public ServicoDAO() {
    }

    /**
     * Metodo responsavel por inserir servico
     *
     * @param servicoTransfer
     * @return boolean
     * @throws java.sql.SQLException
     */
    public boolean inserirServico(ServicoTransfer servicoTransfer) throws SQLException {
        try {
            strBuffer = new StringBuffer().append("INSERT INTO projetosga.servico(\n"
                    + "           descricaoservico"
                    + "         , statusservico"
                    + "         , nomeservico)\n"
                    + "    VALUES (?"
                    + "          ,?"
                    + "          ,?);"
            );
            pstm = abrirconexao.getConexao().prepareStatement(strBuffer.toString());
            int tamservico = 1;
            pstm.setObject(tamservico++, servicoTransfer.getDescricaoservico().toUpperCase());
            pstm.setObject(tamservico++, servicoTransfer.getStatusservico().toUpperCase());
            pstm.setObject(tamservico++, servicoTransfer.getNomeservico().toUpperCase());
            pstm.executeUpdate();
        } catch (SQLException ex) {
            logPrincipal(ServicoDAO.class).error(">>>>ERROR AO INSERIR SERVICO(inserirServico)", ex);
        } finally {
            pstm.close();
            abrirconexao.fecharConexao();
        }
        return true || false;
    }

    /**
     * Metodo com a caracteristica de retornar uma lista de Dados
     *
     * @return List<ServicoTransfer>
     * @throws java.sql.SQLException
     */
    public List<ServicoTransfer> listarTodosServico() throws SQLException {
        List<ServicoTransfer> servicoTransfers = new ArrayList<ServicoTransfer>();
        try {
            strBuffer = new StringBuffer().append("SELECT *"
                    + "  FROM projetosga.servico"
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
     * Metodo com a caracteristica de retornar uma lista de Dados contendo a
     * servico
     *
     * @param String nomeservico
     * @return List<ServicoTransfer>
     * @throws java.sql.SQLException
     */
    public List<ServicoTransfer> buscarServico(String nomeservico) throws SQLException {

        List<ServicoTransfer> servicoTransfers = new ArrayList<ServicoTransfer>();
        try {
            strBuffer = new StringBuffer().append("SELECT *"
                    + "                    FROM projetosga.servico"
                    + "                    WHERE UPPER(nomeservico) \n"
                    + "                    LIKE UPPER(?) ORDER BY nomeservico;");
            pstm = abrirconexao.getConexao().prepareStatement(strBuffer.toString());
            pstm.setObject(1, "%" + nomeservico + "%");
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
            logPrincipal(ServicoDAO.class).error(">>>>ERROR AO BUSCAR POR SERVICO(buscarServico)", ex);
        } finally {
            rs.close();
            pstm.close();
            //abrirconexao.fecharConexao();
        }
        return servicoTransfers;
    }

    /**
     * Método responsável por buscar os Serviços de um determinado usuário
     *
     * @param id
     * @return List<UsuarioTransfer>
     * @throws java.sql.SQLException
     */
    public List<ServicoTransfer> listareBuscarServicoUsuario(Short id) throws SQLException {
        List<ServicoTransfer> listarbuscarusuarioservico = new ArrayList<ServicoTransfer>();
        try {

            strBuffer = new StringBuffer().append("SELECT \n"
                    + "       servico.idservico"
                    + "      ,servico.nomeservico\n"
                    + "  FROM  projetosga.usuario usuario\n"
                    + "  INNER JOIN projetosga.usuarioservico usuarioservico on (usuarioservico.idusuario = usuario.idusuario)\n"
                    + "  INNER JOIN projetosga.servico servico on (usuarioservico.idservico = servico.idservico)\n"
                    + "  WHERE usuario.idusuario=? and statusservico='A'"
                    + "  ORDER BY servico.nomeservico");

            pstm = abrirconexao.getConexao().prepareStatement(strBuffer.toString());
            pstm.setObject(1, id);

            rs = pstm.executeQuery();

            while (rs.next()) {
                ServicoTransfer servicoTransfer = new ServicoTransfer();
                servicoTransfer.setId(rs.getShort("idservico"));
                servicoTransfer.setNomeservico(rs.getString("nomeservico"));
                listarbuscarusuarioservico.add(servicoTransfer);
            }

            return listarbuscarusuarioservico;

        } catch (SQLException ex) {
            logPrincipal(UsuarioDAO.class).error(">>>>ERROR AO LISTAR BUSCAR OS SERVIÇOS DO USUARIOSERVICODAO(listarPermissoes)!!!", ex);
        } finally {
            pstm.close();
            rs.close();
        }
        return null;
    }

    /**
     * Metodo com a caracteristica de retornar uma lista de Dados atraves da
     * busca do usuario
     *
     * @return List<UsuarioTransfer>
     * @throws java.sql.SQLException
     */
    public List<ServicoTransfer> buscarServicoUsuario(String nomedeusuario) throws SQLException {
        List<ServicoTransfer> servicoTransfers = new ArrayList<ServicoTransfer>();
        try {
            strBuffer = new StringBuffer().append("SELECT servico.nomeservico"
                    + "                                   ,servico.statusservico"
                    + "                                   ,servico.descricaoservico"
                    + "  FROM projetosga.usuario usuario\n"
                    + "  INNER JOIN projetosga.usuarioservico usuarioservico on (usuarioservico.idusuario = usuario.idusuario)\n"
                    + "  INNER JOIN projetosga.servico servico on (usuarioservico.idservico = servico.idservico)\n"
                    + "  WHERE usuario.nomeusuario= ?");
            pstm = abrirconexao.getConexao().prepareStatement(strBuffer.toString());
            pstm.setObject(1, nomedeusuario);
            rs = pstm.executeQuery();
            while (rs.next()) {
                //servico
                ServicoTransfer servicoTransfer = new ServicoTransfer();
                if (rs.getString("statusservico").equalsIgnoreCase("A")) {
                    servicoTransfer.setNomeservico(rs.getString("nomeservico"));
                    servicoTransfer.setDescricaoservico(rs.getString("descricaoservico"));
                }

                servicoTransfers.add(servicoTransfer);
            }
        } catch (SQLException ex) {
            logPrincipal(TriagemAtendimentoChamadaClienteDAO.class).error(">>>>ERROR AO BUSCAR SERVICOUSUARIO(buscarServicoUsuario)", ex);
        } finally {
            rs.close();
            abrirconexao.fecharConexao();
        }
        return servicoTransfers;
    }

    /**
     * Metodo responsavel por deletar uma determinada servico
     *
     * @param short idservico
     */
    public boolean deletarServico(Short idservico) throws SQLException {
        try {
            strBuffer = new StringBuffer().append("DELETE FROM projetosga.servico\n"
                    + " WHERE idservico = ?");
            //pstm = abrirconexao.getConexao().prepareStatement(tbPessoa, PreparedStatement.RETURN_GENERATED_KEYS);
            pstm = abrirconexao.getConexao().prepareStatement(strBuffer.toString());
            pstm.setObject(1, idservico);
            //remova id da galeria tabela Galeria
            pstm.executeUpdate();
        } catch (SQLException ex) {
            logPrincipal(ServicoDAO.class).error(">>>>ERROR AO DELETAR POR SERVICO(deletarServico)", ex);
        } finally {
            pstm.close();
            abrirconexao.fecharConexao();
        }
        return true || false;
    }

    /**
     * Metodo responsavel por deletar senhas dos Serviços
     *
     * @param short idservico
     */
    public boolean deletarSenhasServico(Short idservico) throws SQLException {
        try {
            strBuffer = new StringBuffer().append("DELETE FROM projetosga.triagemchamadacliente\n"
                    + "  WHERE idservico = ?;");
            //pstm = abrirconexao.getConexao().prepareStatement(tbPessoa, PreparedStatement.RETURN_GENERATED_KEYS);
            pstm = abrirconexao.getConexao().prepareStatement(strBuffer.toString());
            pstm.setObject(1, idservico);
            //remova id da galeria tabela Galeria
            pstm.executeUpdate();
        } catch (SQLException ex) {
            logPrincipal(ServicoDAO.class).error(">>>>ERROR AO DELETAR POR SERVICO(deletarSenhasServico)", ex);
        } finally {
            pstm.close();
            abrirconexao.fecharConexao();
        }
        return true || false;
    }

    /**
     * Metodo responsavel por alterar a servico
     *
     * @param servicoTransfer
     * @return boolean
     * @throws java.sql.SQLException
     *
     */
    public boolean alterarServico(ServicoTransfer servicoTransfer) throws SQLException {
        try {
            strBuffer = new StringBuffer().append("UPDATE projetosga.servico\n"
                    + "   SET nomeservico=?"
                    + "     ,statusservico=?\n"
                    + "     ,descricaoservico=?"
                    + " WHERE idservico=?;");
            //pstm = abrirconexao.getConexao().prepareStatement(tbPessoa, PreparedStatement.RETURN_GENERATED_KEYS);
            pstm = abrirconexao.getConexao().prepareStatement(strBuffer.toString());
            int tamservico = 1;
            pstm.setObject(tamservico++, servicoTransfer.getNomeservico().toUpperCase());
            pstm.setObject(tamservico++, servicoTransfer.getStatusservico().toUpperCase());
            pstm.setObject(tamservico++, servicoTransfer.getDescricaoservico().toUpperCase());
            pstm.setObject(tamservico++, servicoTransfer.getId());
            pstm.executeUpdate();
        } catch (SQLException ex) {
            logPrincipal(ServicoDAO.class).error(">>>>ERROR AO ATUALIZAR POR SERVICO(alterarServico)", ex);
        } finally {
            pstm.close();
            abrirconexao.fecharConexao();
        }
        return true || false;
    }

    /**
     * Metodo responsavel por buscar por id e nome da servico
     *
     * @param id
     * @param nomeservico
     * @return boolean
     * @throws java.sql.SQLException
     *
     */
    public boolean idNomeServicoConfere(Short id, String nomeservico) throws SQLException {
        try {
            strBuffer = new StringBuffer().append("select idservico, \n"
                    + "	nomeservico\n"
                    + " from 	projetosga.servico\n"
                    + " where idservico 	= ?\n"
                    + " and nomeservico	= ?;");
            pstm = abrirconexao.getConexao().prepareStatement(strBuffer.toString());

            pstm.setObject(1, id);
            pstm.setObject(2, nomeservico);
            rs = pstm.executeQuery();
            if (rs.next()) {
                return true;
            }
        } catch (SQLException ex) {
            logPrincipal(ServicoDAO.class).error(">>>>ERROR AO BUSCAR ID E NOME DO SERVICO(idNomeServicoConfere)", ex);
        } finally {
            rs.close();
            abrirconexao.fecharConexao();
        }
        return false;
    }

    /**
     * Metodo responsavel por verificar se existi servico
     *
     * @param nomeservico
     * @return boolean
     * @throws java.sql.SQLException
     */
    public boolean existeNomeServico(String nomeservico) throws SQLException {
        try {
            strBuffer = new StringBuffer().append("SELECT  nomeservico\n"
                    + "  FROM projetosga.servico\n"
                    + "  WHERE nomeservico	= ?;");
            pstm = abrirconexao.getConexao().prepareStatement(strBuffer.toString());
            pstm.setObject(1, nomeservico);
            rs = pstm.executeQuery();
            if (rs.next()) {
                return true;
            }
        } catch (SQLException ex) {
            logPrincipal(ServicoDAO.class).error(">>>>ERROR AO EXISTIR NOME DO SERVICO(existeNomeServico)", ex);
        } finally {
            rs.close();
            abrirconexao.fecharConexao();
        }
        return false;
    }

    /**
     * Metodo responsavel por verificar se existi o id de servico pela ligação
     * entre a Triagem de senhas e o Serviço
     *
     * @param id
     * @return boolean
     * @throws java.sql.SQLException
     */
    public boolean existeSenhaServico(Short id) throws SQLException {
        try {
            strBuffer = new StringBuffer().append("  SELECT distinct(servico.idservico)\n"
                    + "  FROM projetosga.triagemchamadacliente triagemchamadacliente\n"
                    + "  INNER JOIN \n"
                    + "  projetosga.servico servico on (servico.idservico = triagemchamadacliente.idservico)\n"
                    + "  WHERE servico.idservico= ?");
            pstm = abrirconexao.getConexao().prepareStatement(strBuffer.toString());
            pstm.setObject(1, id);
            rs = pstm.executeQuery();
            if (rs.next()) {
                return true;
            }
        } catch (SQLException ex) {
            logPrincipal(ServicoDAO.class).error(">>>>ERROR AO EXISTIR SERVICO DE SENHAS(existeSenhaServico)", ex);
        } finally {
            rs.close();
            abrirconexao.fecharConexao();
        }
        return false;
    }
}
