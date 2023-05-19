/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.sga.dao;

import br.com.sga.transfer.LocalTransfer;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Jandisson
 */
public class LocalDAO extends DAO {
    
    public LocalDAO() {
    }

    /**
     * Metodo responsavel por inserir local
     *
     * @param localTransfer
     * @return boolean
     * @throws java.sql.SQLException
     */
    public boolean inserirLocal(LocalTransfer localTransfer) throws SQLException {
        try {
            strBuffer = new StringBuffer().append("INSERT INTO projetosga.local(\n"
                    + "            nomelocal"
                    + "            )\n"
                    + "    VALUES (  ?"
                    + "             );"
            );
            pstm = abrirconexao.getConexao().prepareStatement(strBuffer.toString());
            int tamlocal = 1;
            pstm.setObject(tamlocal++, localTransfer.getNomelocal().toUpperCase());
            pstm.executeUpdate();
        } catch (SQLException ex) {
            logPrincipal(LocalDAO.class).error(">>>>ERROR AO INSERIR LOCAL(inserirLocal)", ex);
        } finally {
            pstm.close();
            abrirconexao.fecharConexao();
        }
        return true || false;
    }

    /**
     * Metodo com a caracteristica de retornar uma lista de Dados
     *
     * @return List<LocalTransfer>
     * @throws java.sql.SQLException
     */
    public List<LocalTransfer> listarTodosLocal() throws SQLException {
        List<LocalTransfer> localTransfers = new ArrayList<LocalTransfer>();
        try {
            strBuffer = new StringBuffer().append("SELECT *"
                    + "  FROM projetosga.local"
                    + "  ORDER BY nomelocal");
            pstm = abrirconexao.getConexao().prepareStatement(strBuffer.toString());
            rs = pstm.executeQuery();
            while (rs.next()) {
                LocalTransfer localTransfer = new LocalTransfer();
                localTransfer.setId(rs.getShort("idlocal"));
                localTransfer.setNomelocal(rs.getString("nomelocal"));
                localTransfers.add(localTransfer);
            }
        } catch (SQLException ex) {
            logPrincipal(LocalDAO.class).error(">>>>ERROR AO LISTAR LOCAL(listarTodosLocal)", ex);
        } finally {
            rs.close();
            abrirconexao.fecharConexao();
        }
        return localTransfers;
    }

    /**
     * Metodo com a caracteristica de retornar uma lista de Dados contendo a
     * local
     *
     * @param String nomelocal
     * @return List<LocalTransfer>
     * @throws java.sql.SQLException
     */
    public List<LocalTransfer> buscarLocal(String nomelocal) throws SQLException {
        LocalTransfer localTransfer = new LocalTransfer();
        List<LocalTransfer> localTransfers = new ArrayList<LocalTransfer>();
        try {
            strBuffer = new StringBuffer().append("SELECT *"
                    + "                    FROM projetosga.local"
                    + "                    WHERE UPPER(nomelocal) \n"
                    + "                    LIKE UPPER(?) ORDER BY nomelocal;");
            pstm = abrirconexao.getConexao().prepareStatement(strBuffer.toString());
            pstm.setObject(1, "%" + nomelocal + "%");
            rs = pstm.executeQuery();
            while (rs.next()) {
                localTransfer.setId(rs.getShort("idlocal"));
                localTransfer.setNomelocal(rs.getString("nomelocal"));
                localTransfers.add(localTransfer);
            }
        } catch (SQLException ex) {
            logPrincipal(LocalDAO.class).error(">>>>ERROR AO BUSCAR POR LOCAL(buscarLocal)", ex);
        } finally {
            rs.close();
            abrirconexao.fecharConexao();
        }
        return localTransfers;
    }

    /**
     * Metodo com a caracteristica de retornar uma lista de Dados contendo a
     * local
     *
     * @param String nomelocal
     * @return List<LocalTransfer>
     * @throws java.sql.SQLException
     */
    public LocalTransfer buscarLocalExistente(String nomelocal) throws SQLException {
        LocalTransfer localTransfer = new LocalTransfer();
        List<LocalTransfer> localTransfers = new ArrayList<LocalTransfer>();
        try {
            strBuffer = new StringBuffer().append("SELECT *"
                    + "                    FROM projetosga.local"
                    + "                    WHERE UPPER(nomelocal) \n"
                    + "                    LIKE UPPER(?) ORDER BY nomelocal;");
            pstm = abrirconexao.getConexao().prepareStatement(strBuffer.toString());
            pstm.setObject(1, "%" + nomelocal + "%");
            rs = pstm.executeQuery();
            while (rs.next()) {
                localTransfer.setId(rs.getShort("idlocal"));
                localTransfer.setNomelocal(rs.getString("nomelocal"));
                localTransfers.add(localTransfer);
            }
        } catch (SQLException ex) {
            logPrincipal(LocalDAO.class).error(">>>>ERROR AO BUSCAR POR LOCAL(buscarLocal)", ex);
        } finally {
            rs.close();
            abrirconexao.fecharConexao();
        }
        return localTransfers.size() > 0 ? localTransfers.get(0) : null;
    }

    /**
     * Metodo responsavel por deletar uma determinada local
     *
     * @param short idlocal
     */
    public boolean deletarLocal(Short idlocal) throws SQLException {
        try {
            strBuffer = new StringBuffer().append("DELETE FROM projetosga.local\n"
                    + " WHERE idlocal = ?");
            //pstm = abrirconexao.getConexao().prepareStatement(tbPessoa, PreparedStatement.RETURN_GENERATED_KEYS);
            pstm = abrirconexao.getConexao().prepareStatement(strBuffer.toString());
            pstm.setObject(1, idlocal);
            //remova id da galeria tabela Galeria
            pstm.executeUpdate();
        } catch (SQLException ex) {
            logPrincipal(LocalDAO.class).error(">>>>ERROR AO DELETAR POR LOCAL(deletarLocal)", ex);
        } finally {
            pstm.close();
            abrirconexao.fecharConexao();
        }
        return true || false;
    }

    /**
     * Metodo responsavel por alterar a local
     *
     * @param LocalTransfer localTransfer
     * @return boolean
     *
     */
    public boolean alterarLocal(LocalTransfer localTransfer) throws SQLException {
        try {
            strBuffer = new StringBuffer().append("UPDATE projetosga.local\n"
                    + "   SET nomelocal=?"
                    + " WHERE idlocal=?;");
            //pstm = abrirconexao.getConexao().prepareStatement(tbPessoa, PreparedStatement.RETURN_GENERATED_KEYS);
            pstm = abrirconexao.getConexao().prepareStatement(strBuffer.toString());
            int tamlocal = 1;
            pstm.setObject(tamlocal++, localTransfer.getNomelocal().toUpperCase());
            pstm.setObject(tamlocal++, localTransfer.getId());
            pstm.executeUpdate();
        } catch (SQLException ex) {
            logPrincipal(LocalDAO.class).error(">>>>ERROR AO ATUALIZAR POR LOCAL(alterarLocal)", ex);
        } finally {
            pstm.close();
            abrirconexao.fecharConexao();
        }
        return true || false;
    }

    /**
     * Metodo responsavel por buscar por id e nome da local
     *
     * @param id
     * @param nomelocal
     * @return boolean
     * @throws java.sql.SQLException
     *
     */
    public boolean idNomeLocalConfere(Short id, String nomelocal) throws SQLException {
        try {
            strBuffer = new StringBuffer().append("select idlocal, \n"
                    + "	nomelocal\n"
                    + " from 	projetosga.local\n"
                    + " where idlocal 	= ?\n"
                    + " and nomelocal	= ?;");
            pstm = abrirconexao.getConexao().prepareStatement(strBuffer.toString());
            
            pstm.setObject(1, id);
            pstm.setObject(2, nomelocal);
            rs = pstm.executeQuery();
            if (rs.next()) {
                return true;
            }
        } catch (SQLException ex) {
            logPrincipal(LocalDAO.class).error(">>>>ERROR AO BUSCAR ID E NOME DO LOCAL(idNomeLocalConfere)", ex);
        } finally {
            rs.close();
            abrirconexao.fecharConexao();
        }
        return false;
    }

    /**
     * Metodo responsavel por verificar se existi local
     *
     * @param nomelocal
     * @return boolean
     * @throws java.sql.SQLException
     */
    public boolean existeNomeLocal(String nomelocal) throws SQLException {
        try {
            strBuffer = new StringBuffer().append("SELECT  nomelocal\n"
                    + "  FROM projetosga.local\n"
                    + "  WHERE nomelocal	= ?;");
            pstm = abrirconexao.getConexao().prepareStatement(strBuffer.toString());
            pstm.setObject(1, nomelocal);
            rs = pstm.executeQuery();
            if (rs.next()) {
                return true;
            }
        } catch (SQLException ex) {
            logPrincipal(LocalDAO.class).error(">>>>ERROR AO EXISTIR NOME DO LOCAL(existeNomeLocal)", ex);
        } finally {
            rs.close();
            abrirconexao.fecharConexao();
        }
        return false;
    }
}
