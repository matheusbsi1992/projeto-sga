/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.sga.dao;

import br.com.sga.transfer.UnidadeTransfer;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Jandisson
 */
public class UnidadeDAO extends DAO {

    public UnidadeDAO() {
    }

    /**
     * Metodo responsavel por inserir unidade
     *
     * @param unidadeTransfer
     * @return boolean
     * @throws java.sql.SQLException
     */
    public boolean inserirUnidade(UnidadeTransfer unidadeTransfer) throws SQLException {
        try {
            strBuffer = new StringBuffer().append("INSERT INTO projetosga.unidade(\n"
                    + "            nomeunidade"
                    + "            ,siglaunidade"
                    + "            ,statusunidade)\n"
                    + "    VALUES (  ?"
                    + "             ,?"
                    + "             ,?);"
            );
            pstm = abrirconexao.getConexao().prepareStatement(strBuffer.toString());
            int tamunidade = 1;
            pstm.setObject(tamunidade++, unidadeTransfer.getNomeunidade().toUpperCase());
            pstm.setObject(tamunidade++, unidadeTransfer.getSiglaunidade().toUpperCase());
            pstm.setObject(tamunidade++, unidadeTransfer.getStatusunidade().toUpperCase());
            pstm.executeUpdate();
        } catch (SQLException ex) {
            logPrincipal(UnidadeDAO.class).error(">>>>ERROR AO INSERIR UNIDADE(inserirUnidade)", ex);
        } finally {
            pstm.close();
            abrirconexao.fecharConexao();
        }
        return true || false;
    }

    /**
     * Metodo com a caracteristica de retornar uma lista de Dados
     *
     * @return List<UnidadeTransfer>
     * @throws java.sql.SQLException
     */
    public List<UnidadeTransfer> listarTodosUnidade() throws SQLException {
        List<UnidadeTransfer> unidadeTransfers = new ArrayList<UnidadeTransfer>();
        try {
            strBuffer = new StringBuffer().append("SELECT *"
                    + "  FROM projetosga.unidade"
                    + "  ORDER BY nomeunidade");
            pstm = abrirconexao.getConexao().prepareStatement(strBuffer.toString());
            rs = pstm.executeQuery();
            while (rs.next()) {
                UnidadeTransfer unidadeTransfer = new UnidadeTransfer();
                unidadeTransfer.setId(rs.getShort("idunidade"));
                unidadeTransfer.setNomeunidade(rs.getString("nomeunidade"));
                unidadeTransfer.setSiglaunidade(rs.getString("siglaunidade"));
                unidadeTransfer.setStatusunidade(rs.getString("statusunidade"));
                unidadeTransfers.add(unidadeTransfer);
            }
        } catch (SQLException ex) {
            logPrincipal(UnidadeDAO.class).error(">>>>ERROR AO LISTAR UNIDADE(listarTodosUnidade)", ex);
        } finally {
            rs.close();
            abrirconexao.fecharConexao();
        }
        return unidadeTransfers;
    }

    /**
     * Metodo com a caracteristica de retornar uma lista de Dados contendo a
     * unidade
     *
     * @param String nomeunidade
     * @return List<UnidadeTransfer>
     * @throws java.sql.SQLException
     */
    public List<UnidadeTransfer> buscarUnidade(String nomeunidade) throws SQLException {
        UnidadeTransfer unidadeTransfer = new UnidadeTransfer();
        List<UnidadeTransfer> unidadeTransfers = new ArrayList<UnidadeTransfer>();
        try {
            strBuffer = new StringBuffer().append("SELECT *"
                    + "                    FROM projetosga.unidade"
                    + "                    WHERE UPPER(nomeunidade) \n"
                    + "                    LIKE UPPER(?) ORDER BY nomeunidade;");
            pstm = abrirconexao.getConexao().prepareStatement(strBuffer.toString());
            pstm.setObject(1, "%" + nomeunidade + "%");
            rs = pstm.executeQuery();
            while (rs.next()) {
                unidadeTransfer.setId(rs.getShort("idunidade"));
                unidadeTransfer.setNomeunidade(rs.getString("nomeunidade"));
                unidadeTransfer.setSiglaunidade(rs.getString("siglaunidade"));
                unidadeTransfer.setStatusunidade(rs.getString("statusunidade"));
                unidadeTransfers.add(unidadeTransfer);
            }
        } catch (SQLException ex) {
            logPrincipal(UnidadeDAO.class).error(">>>>ERROR AO BUSCAR POR UNIDADE(buscarUnidade)", ex);
        } finally {
            rs.close();
            abrirconexao.fecharConexao();
        }
        return unidadeTransfers;
    }

    /**
     * Metodo responsavel por deletar uma determinada unidade
     *
     * @param short idunidade
     */
    public boolean deletarUnidade(Short idunidade) throws SQLException {
        try {
            strBuffer = new StringBuffer().append("DELETE FROM projetosga.unidade\n"
                    + " WHERE idunidade = ?");
            //pstm = abrirconexao.getConexao().prepareStatement(tbPessoa, PreparedStatement.RETURN_GENERATED_KEYS);
            pstm = abrirconexao.getConexao().prepareStatement(strBuffer.toString());
            pstm.setObject(1, idunidade);
            //remova id da galeria tabela Galeria
            pstm.executeUpdate();
        } catch (SQLException ex) {
            logPrincipal(UnidadeDAO.class).error(">>>>ERROR AO DELETAR POR UNIDADE(deletarUnidade)", ex);
        } finally {
            pstm.close();
            abrirconexao.fecharConexao();
        }
        return true || false;
    }

    /**
     * Metodo responsavel por alterar a unidade
     *
     * @param UnidadeTransfer unidadeTransfer
     * @return boolean
     *
     */
    public boolean alterarUnidade(UnidadeTransfer unidadeTransfer) throws SQLException {
        try {
            strBuffer = new StringBuffer().append("UPDATE projetosga.unidade\n"
                    + "   SET nomeunidade=?"
                    + "      , siglaunidade=?"
                    + "     ,statusunidade=?\n"
                    + " WHERE idunidade=?;");
            //pstm = abrirconexao.getConexao().prepareStatement(tbPessoa, PreparedStatement.RETURN_GENERATED_KEYS);
            pstm = abrirconexao.getConexao().prepareStatement(strBuffer.toString());
            int tamunidade = 1;
            pstm.setObject(tamunidade++, unidadeTransfer.getNomeunidade().toUpperCase());
            pstm.setObject(tamunidade++, unidadeTransfer.getSiglaunidade().toUpperCase());
            pstm.setObject(tamunidade++, "A");
            pstm.setObject(tamunidade++, unidadeTransfer.getId());
            pstm.executeUpdate();
        } catch (SQLException ex) {
            logPrincipal(UnidadeDAO.class).error(">>>>ERROR AO ATUALIZAR POR UNIDADE(alterarUnidade)", ex);
        } finally {
            pstm.close();
            abrirconexao.fecharConexao();
        }
        return true || false;
    }

    /**
     * Metodo responsavel por buscar por id e nome da unidade
     *
     * @param id
     * @param nomeunidade
     * @return boolean
     * @throws java.sql.SQLException
     *
     */
    public boolean idNomeUnidadeConfere(Short id, String nomeunidade) throws SQLException {
        try {
            strBuffer = new StringBuffer().append("select idunidade, \n"
                    + "	nomeunidade\n"
                    + " from 	projetosga.unidade\n"
                    + " where idunidade 	= ?\n"
                    + " and nomeunidade	= ?;");
            pstm = abrirconexao.getConexao().prepareStatement(strBuffer.toString());

            pstm.setObject(1, id);
            pstm.setObject(2, nomeunidade);
            rs = pstm.executeQuery();
            if (rs.next()) {
                return true;
            }
        } catch (SQLException ex) {
            logPrincipal(UnidadeDAO.class).error(">>>>ERROR AO BUSCAR ID E NOME DA UNIDADE(idNomeUnidadeConfere)", ex);
        } finally {
            rs.close();
            abrirconexao.fecharConexao();
        }
        return false;
    }

    /**
     * Metodo responsavel por verificar se existi unidade
     *
     * @param nomeunidade
     * @return boolean
     * @throws java.sql.SQLException
     */
    public boolean existeNomeUnidade(String nomeunidade) throws SQLException {
        try {
            strBuffer = new StringBuffer().append("SELECT  nomeunidade\n"
                    + "  FROM projetosga.unidade\n"
                    + "  WHERE nomeunidade	= ?;");
            pstm = abrirconexao.getConexao().prepareStatement(strBuffer.toString());
            pstm.setObject(1, nomeunidade);
            rs = pstm.executeQuery();
            if (rs.next()) {
                return true;
            }
        } catch (SQLException ex) {
            logPrincipal(UnidadeDAO.class).error(">>>>ERROR AO EXISTIR NOME DA UNIDADE(existeNomeUnidade)", ex);
        } finally {
            rs.close();
            abrirconexao.fecharConexao();
        }
        return false;
    }
}
