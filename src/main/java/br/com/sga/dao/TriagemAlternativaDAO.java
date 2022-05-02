/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.sga.dao;

import br.com.sga.transfer.LocalTransfer;
import br.com.sga.transfer.ServicoTransfer;
import br.com.sga.transfer.TriagemAlternativaTransfer;
import br.com.sga.transfer.TriagemChamadaClienteTransfer;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Jandisson
 */
public class TriagemAlternativaDAO extends DAO {

    private final ServicoDAO servicodao = new ServicoDAO();
    private final LocalDAO localdao = new LocalDAO();

    
    public TriagemAlternativaDAO() {
    }

    /**
     * Metodo responsavel por inserir triagemalternativa
     *
     * @param triagemalternativaTransfer
     * @return boolean
     * @throws java.sql.SQLException
     */
    public boolean inserirTriagemAlternativa(TriagemAlternativaTransfer triagemalternativaTransfer) throws SQLException {
        try {
            strBuffer = new StringBuffer().append("INSERT INTO projetosga.triagemalternativa(\n"
                    + "            siglatriagemalternativa\n"
                    + "            ,statustriagemalternativa\n"
                    + "            ,idlocal\n"
                    + "            ,idservico)\n"
                    + "    VALUES (?\n"
                    + "    ,?\n"
                    + "    ,?\n"
                    + "    ,?);"
            );
            pstm = abrirconexao.getConexao().prepareStatement(strBuffer.toString());
            int tamtriagemalternativa = 1;
            pstm.setObject(tamtriagemalternativa++, triagemalternativaTransfer.getSiglatriagemalternativa().toUpperCase().trim().replace(" ", ""));
            pstm.setObject(tamtriagemalternativa++, triagemalternativaTransfer.getStatustriagemalternativa().toUpperCase());
            pstm.setObject(tamtriagemalternativa++, triagemalternativaTransfer.getLocaltransfer().getId());
            pstm.setObject(tamtriagemalternativa++, triagemalternativaTransfer.getServicotransfer().getId());

            pstm.executeUpdate();
        } catch (SQLException ex) {
            logPrincipal(TriagemAlternativaDAO.class).error(">>>>ERROR AO INSERIR TRIAGEMALTERNATIVA(inserirTriagemalternativa)", ex);
        } finally {
            pstm.close();
            abrirconexao.fecharConexao();
        }
        return true || false;
    }

    /**
     * Metodo com a caracteristica de retornar uma lista de Dados
     *
     * @return List<TriagemAlternativaTransfer>
     * @throws java.sql.SQLException
     */
    public List<TriagemAlternativaTransfer> listarTodosTriagemAlternativa() throws SQLException {
        List<TriagemAlternativaTransfer> triagemalternativaTransfers = new ArrayList<TriagemAlternativaTransfer>();
        try {
            strBuffer = new StringBuffer().append(" SELECT\n"
                    + "                                                                             triagemalternativa.idtriagemalternativa\n"
                    + "                                                                            ,triagemalternativa.siglatriagemalternativa\n"
                    + "									           ,triagemalternativa.statustriagemalternativa\n"
                    + "									           ,servico.nomeservico\n"
                    + "										   ,local.nomelocal\n"
                    + "                                                                                   from projetosga.triagemalternativa triagemalternativa\n"
                    + "                                                                                   inner join\n"
                    + "                                                                                   projetosga.servico servico on (servico.idservico = triagemalternativa.idservico)\n"
                    + "                                                                                   inner join\n"
                    + "                                                                                   projetosga.local local on (local.idlocal = triagemalternativa.idlocal)"
                    + "                                                                             WHERE servico.statusservico='A'    "
                    + "                                                                             ORDER BY servico.nomeservico");
            pstm = abrirconexao.getConexao().prepareStatement(strBuffer.toString());
            rs = pstm.executeQuery();
            while (rs.next()) {
                TriagemAlternativaTransfer triagemalternativaTransfer = new TriagemAlternativaTransfer();
                triagemalternativaTransfer.setId(rs.getShort("idtriagemalternativa"));
                triagemalternativaTransfer.setSiglatriagemalternativa(rs.getString("siglatriagemalternativa"));
                triagemalternativaTransfer.setStatustriagemalternativa(rs.getString("statustriagemalternativa"));

                ServicoTransfer servicotransfer = new ServicoTransfer();
                servicotransfer.setNomeservico(rs.getString("nomeservico"));
                triagemalternativaTransfer.setServicotransfer(servicotransfer);

                LocalTransfer localTransfer = new LocalTransfer();
                localTransfer.setNomelocal(rs.getString("nomelocal"));
                triagemalternativaTransfer.setLocaltransfer(localTransfer);

                triagemalternativaTransfers.add(triagemalternativaTransfer);
            }
        } catch (SQLException ex) {
            logPrincipal(TriagemAlternativaDAO.class).error(">>>>ERROR AO LISTAR TRIAGEMALETERNATIVA(listarTodosTriagemalternativa)", ex);
        } finally {
            rs.close();
            abrirconexao.fecharConexao();
        }
        return triagemalternativaTransfers;
    }

    /**
     * Metodo com a caracteristica de retornar uma lista de Dados contendo a
     * triagemalternativa
     *
     * @param String nometriagemalternativa
     * @return List<TriagemAlternativaTransfer>
     * @throws java.sql.SQLException
     */
    public List<TriagemAlternativaTransfer> buscarTriagemAlternativa(String siglatriagemalternativa) throws SQLException {

        List<TriagemAlternativaTransfer> triagemalternativaTransfers = new ArrayList<TriagemAlternativaTransfer>();
        try {
            strBuffer = new StringBuffer().append(" SELECT\n"
                    + "									           triagemalternativa.idtriagemalternativa\n"
                    + "									           ,triagemalternativa.siglatriagemalternativa\n"
                    + "									           ,triagemalternativa.statustriagemalternativa\n"
                    + "									           ,servico.nomeservico\n"
                    + "										   ,local.nomelocal\n"
                    + "                                                                                   from projetosga.triagemalternativa triagemalternativa\n"
                    + "                                                                                   inner join\n"
                    + "                                                                                   projetosga.servico servico on (servico.idservico = triagemalternativa.idservico)\n"
                    + "                                                                                   inner join\n"
                    + "                                                                                   projetosga.local local on (local.idlocal = triagemalternativa.idlocal)"
                    + "                                                                             WHERE UPPER(siglatriagemalternativa) \n"
                    + "                                                                             LIKE UPPER(?) ORDER BY servico.nomeservico");
            pstm = abrirconexao.getConexao().prepareStatement(strBuffer.toString());
            pstm.setObject(1, "%" + siglatriagemalternativa + "%");
            rs = pstm.executeQuery();
            while (rs.next()) {
                TriagemAlternativaTransfer triagemalternativaTransfer = new TriagemAlternativaTransfer();
                triagemalternativaTransfer.setId(rs.getShort("idtriagemalternativa"));
                triagemalternativaTransfer.setSiglatriagemalternativa(rs.getString("siglatriagemalternativa"));
                triagemalternativaTransfer.setStatustriagemalternativa(rs.getString("statustriagemalternativa"));

                ServicoTransfer servicotransfer = new ServicoTransfer();
                servicotransfer.setNomeservico(rs.getString("nomeservico"));
                triagemalternativaTransfer.setServicotransfer(servicotransfer);

                LocalTransfer localTransfer = new LocalTransfer();
                localTransfer.setNomelocal(rs.getString("nomelocal"));
                triagemalternativaTransfer.setLocaltransfer(localTransfer);

                triagemalternativaTransfers.add(triagemalternativaTransfer);
            }
        } catch (SQLException ex) {
            logPrincipal(TriagemAlternativaDAO.class).error(">>>>ERROR AO BUSCAR POR TRIAGEMALTERNATIVA(buscarTriagemalternativa)", ex);
        } finally {
            rs.close();
            abrirconexao.fecharConexao();
        }
        return triagemalternativaTransfers;
    }

    /**
     * Metodo responsavel por deletar uma determinada triagemalternativa
     *
     * @param short idtriagemalternativa
     */
    public boolean deletarTriagemAlternativa(Short idtriagemalternativa) throws SQLException {
        try {
            strBuffer = new StringBuffer().append("DELETE FROM projetosga.triagemalternativa\n"
                    + " WHERE idtriagemalternativa = ?");
            //pstm = abrirconexao.getConexao().prepareStatement(tbPessoa, PreparedStatement.RETURN_GENERATED_KEYS);
            pstm = abrirconexao.getConexao().prepareStatement(strBuffer.toString());
            pstm.setObject(1, idtriagemalternativa);
            //remova id da galeria tabela Galeria
            pstm.executeUpdate();
        } catch (SQLException ex) {
            logPrincipal(TriagemAlternativaDAO.class).error(">>>>ERROR AO DELETAR POR UNIDADE(deletarTriagemalternativa)", ex);
        } finally {
            pstm.close();
            abrirconexao.fecharConexao();
        }
        return true || false;
    }

    /**
     * Metodo responsavel por alterar a triagemalternativa
     *
     * @param TriagemAlternativaTransfer triagemalternativaTransfer
     * @return boolean
     *
     */
    public boolean alterarTriagemAlternativa(TriagemAlternativaTransfer triagemalternativaTransfer) throws SQLException {
        try {
            strBuffer = new StringBuffer().append("UPDATE projetosga.triagemalternativa\n"
                    + "   SET siglatriagemalternativa=?, statustriagemalternativa=?, \n"
                    + "       idlocal=?, idservico=?\n"
                    + " WHERE idtriagemalternativa=?");
            //pstm = abrirconexao.getConexao().prepareStatement(tbPessoa, PreparedStatement.RETURN_GENERATED_KEYS);
            pstm = abrirconexao.getConexao().prepareStatement(strBuffer.toString());
            int tamtriagemalternativa = 1;
            pstm.setObject(tamtriagemalternativa++, triagemalternativaTransfer.getSiglatriagemalternativa().toUpperCase().trim().replace(" ", ""));
            pstm.setObject(tamtriagemalternativa++, triagemalternativaTransfer.getStatustriagemalternativa().toUpperCase());
            pstm.setObject(tamtriagemalternativa++, triagemAlternativabuscarLocal(triagemalternativaTransfer.getLocaltransfer().getNomelocal()).iterator().next().getId());
            pstm.setObject(tamtriagemalternativa++, triagemAlternativaServicobuscarServico(triagemalternativaTransfer.getServicotransfer().getNomeservico()).iterator().next().getId());
            pstm.setObject(tamtriagemalternativa++, triagemalternativaTransfer.getId());
            pstm.executeUpdate();
        } catch (SQLException ex) {
            logPrincipal(TriagemAlternativaDAO.class).error(">>>>ERROR AO ATUALIZAR POR TRIAGEM ALTERNATIVA(alterarTriagemalternativa)", ex);
        } finally {
            pstm.close();
            abrirconexao.fecharConexao();
        }
        return true || false;
    }

    /**
     * Metodo responsavel por buscar por id e nome da triagemalternativa
     *
     * @param id
     * @param siglatriagemalternativa
     * @return boolean
     * @throws java.sql.SQLException
     *
     */
    public boolean idSiglaTriagemAlternativaConfere(Short id, String siglatriagemalternativa) throws SQLException {
        try {
            strBuffer = new StringBuffer().append("select idtriagemalternativa, \n"
                    + "	siglatriagemalternativa\n"
                    + " from 	projetosga.triagemalternativa\n"
                    + " where idtriagemalternativa 	= ?\n"
                    + " and siglatriagemalternativa	= ?;");
            pstm = abrirconexao.getConexao().prepareStatement(strBuffer.toString());

            pstm.setObject(1, id);
            pstm.setObject(2, siglatriagemalternativa);
            rs = pstm.executeQuery();
            if (rs.next()) {
                return true;
            }
        } catch (SQLException ex) {
            logPrincipal(TriagemAlternativaDAO.class).error(">>>>ERROR AO BUSCAR ID E NOME DA TRIAGEMALTERNATIVA(idSiglaTriagemAlternativaConfere)", ex);
        } finally {
            rs.close();
            abrirconexao.fecharConexao();
        }
        return false;
    }

    /**
     * Metodo responsavel por verificar se o status esta ativo ou inativo
     *
     * @param statustriagemalternativa
     * @return boolean
     * @throws java.sql.SQLException
     */
    public boolean verificarStatusTriagemalternativa(String statustriagemalternativa) throws SQLException {
        try {
            strBuffer = new StringBuffer().append("SELECT  statustriagemalternativa\n"
                    + "  FROM projetosga.triagemalternativa\n"
                    + "  WHERE statustriagemalternativa	= ?;");
            pstm = abrirconexao.getConexao().prepareStatement(strBuffer.toString());
            pstm.setObject(1, statustriagemalternativa);
            rs = pstm.executeQuery();
            if (rs.next()) {
                return true;
            }
        } catch (SQLException ex) {
            logPrincipal(TriagemAlternativaDAO.class).error(">>>>ERROR AO EXISTIR NOME DA UNIDADE(existeNomeTriagemalternativa)", ex);
        } finally {
            rs.close();
            abrirconexao.fecharConexao();
        }
        return false;
    }

    /**
     * Metodo responsavel por verificar se existi sigla
     *
     * @param siglatriagemalternativa
     * @return boolean
     * @throws java.sql.SQLException
     */
    public boolean existeSiglaTriagemalternativa(String siglatriagemalternativa) throws SQLException {
        try {
            strBuffer = new StringBuffer().append("SELECT  siglatriagemalternativa\n"
                    + "  FROM projetosga.triagemalternativa\n"
                    + "  WHERE siglatriagemalternativa	= ?;");
            pstm = abrirconexao.getConexao().prepareStatement(strBuffer.toString());
            pstm.setObject(1, siglatriagemalternativa);
            rs = pstm.executeQuery();
            if (rs.next()) {
                return true;
            }
        } catch (SQLException ex) {
            logPrincipal(ServicoDAO.class).error(">>>>ERROR AO EXISTIR SIGLA DA TRIAGEMALTERNATIVA(existeSiglaTriagemalternativa)", ex);
        } finally {
            rs.close();
            abrirconexao.fecharConexao();
        }
        return false;
    }

    /**
     * Metodo responsavel por buscar por id e nome do servico vinculado a
     * triagemalternativa
     *
     * @param id
     * @param nomeservico
     * @return boolean
     * @throws java.sql.SQLException
     *
     */
    public boolean idNomeServicoTriagemAlternativaConfere(Short id, String nomeservico) throws SQLException {
        try {
            strBuffer = new StringBuffer().append(
                    "SELECT                                                                              triagemalternativa.idtriagemalternativa\n"
                    + "                                                                                  ,servico.nomeservico\n"
                    + "                                                                                   from projetosga.triagemalternativa triagemalternativa\n"
                    + "                                                                                   inner join\n"
                    + "                                                                                   projetosga.servico servico on (servico.idservico = triagemalternativa.idservico)\n"
                    + " where  triagemalternativa.idtriagemalternativa 	= ?\n"
                    + " and servico.nomeservico	= ?;");
            pstm = abrirconexao.getConexao().prepareStatement(strBuffer.toString());

            pstm.setObject(1, id);
            pstm.setObject(2, nomeservico);
            rs = pstm.executeQuery();
            if (rs.next()) {
                return true;
            }
        } catch (SQLException ex) {
            logPrincipal(TriagemAlternativaDAO.class).error(">>>>ERROR AO BUSCAR ID E NOME DO SERVICO DA TRIAGEMALTERNATIVA(idNomeServicoTriagemAlternativaConfere)", ex);
        } finally {
            rs.close();
            abrirconexao.fecharConexao();
        }
        return false;
    }

    /**
     * Metodo responsavel por buscar nome do servico vinculado a
     * triagemalternativa
     *
     * @param nomeservico
     * @return boolean
     * @throws java.sql.SQLException
     *
     */
    public boolean existeNomeServicoTriagemAlternativaConfere(String nomeservico) throws SQLException {
        try {
            strBuffer = new StringBuffer().append(
                    "SELECT servico.nomeservico\n"
                    + "                                                                                   from projetosga.triagemalternativa triagemalternativa\n"
                    + "                                                                                   inner join\n"
                    + "                                                                                   projetosga.servico servico on (servico.idservico = triagemalternativa.idservico)\n"
                    + " where servico.nomeservico	= ?;");
            pstm = abrirconexao.getConexao().prepareStatement(strBuffer.toString());
            pstm.setObject(1, nomeservico);
            rs = pstm.executeQuery();
            if (rs.next()) {
                return true;
            }
        } catch (SQLException ex) {
            logPrincipal(TriagemAlternativaDAO.class).error(">>>>ERROR AO BUSCAR ID E NOME DO SERVICO NA TRIAGEMALTERNATIVA(existeNomeServicoTriagemAlternativaConfere)", ex);
        } finally {
            rs.close();
            abrirconexao.fecharConexao();
        }
        return false;
    }

    /**
     * Metodo responsavel por buscar por id e nome do Local vinculado a
     * triagemalternativa
     *
     * @param id
     * @param nomelocal
     * @return boolean
     * @throws java.sql.SQLException
     *
     */
    public boolean idNomeLocalTriagemAlternativaConfere(Short id, String nomelocal) throws SQLException {
        try {
            strBuffer = new StringBuffer().append(
                    "SELECT                                                                              triagemalternativa.idtriagemalternativa\n"
                    + "                                                                                  ,local.nomelocal\n"
                    + "                                                                                   from projetosga.triagemalternativa triagemalternativa\n"
                    + "                                                                                   inner join\n"
                    + "                                                                                   projetosga.local local on (local.idlocal = triagemalternativa.idlocal)\n"
                    + " where  triagemalternativa.idtriagemalternativa 	= ?\n"
                    + " and local.nomelocal	= ?;");
            pstm = abrirconexao.getConexao().prepareStatement(strBuffer.toString());

            pstm.setObject(1, id);
            pstm.setObject(2, nomelocal);
            rs = pstm.executeQuery();
            if (rs.next()) {
                return true;
            }
        } catch (SQLException ex) {
            logPrincipal(TriagemAlternativaDAO.class).error(">>>>ERROR AO BUSCAR ID E NOME DO LOCAL(idNomeLocalTriagemAlternativaConfere)", ex);
        } finally {
            rs.close();
            abrirconexao.fecharConexao();
        }
        return false;
    }

    /**
     * Metodo responsavel por buscar nome do LOCAL vinculado a
     * triagemalternativa
     *
     * @param nomelocal
     * @return boolean
     * @throws java.sql.SQLException
     *
     */
    public boolean existeNomeLocalTriagemAlternativaConfere(String nomelocal) throws SQLException {
        try {
            strBuffer = new StringBuffer().append(
                    "SELECT local.nomelocal\n"
                    + "                                                                                   from projetosga.triagemalternativa triagemalternativa\n"
                    + "                                                                                   inner join\n"
                    + "                                                                                   projetosga.local local on (local.idlocal = triagemalternativa.idlocal)\n"
                    + " where local.nomelocal	= ?;");
            pstm = abrirconexao.getConexao().prepareStatement(strBuffer.toString());
            pstm.setObject(1, nomelocal);
            rs = pstm.executeQuery();
            if (rs.next()) {
                return true;
            }
        } catch (SQLException ex) {
            logPrincipal(TriagemAlternativaDAO.class).error(">>>>ERROR AO BUSCAR ID E NOME DO LOCAL NA TRIAGEMALTERNATIVA(existeNomeLocalTriagemAlternativaConfere)", ex);
        } finally {
            rs.close();
            abrirconexao.fecharConexao();
        }
        return false;
    }

    /**
     * Metodo responsavel por retornar os dados do Local
     *
     * @return List<LocalTransfer>
     * @throws java.sql.SQLException
     */
    public List<LocalTransfer> triagemAlternativaLocal() throws SQLException {
        return localdao.listarTodosLocal();
    }

    /**
     * Metodo responsavel por retornar os dados do Local atraves de uma busca do
     * Local
     *
     * @return List<LocalTransfer>
     * @throws java.sql.SQLException
     */
    public List<LocalTransfer> triagemAlternativabuscarLocal(String nomelocal) throws SQLException {
        return localdao.buscarLocal(nomelocal);
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
    public List<ServicoTransfer> triagemAlternativaServicobuscarServico(String nomeservico) throws SQLException {
        return servicodao.buscarServico(nomeservico);
    }

}