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
import br.com.sga.transfer.UnidadeTransfer;
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
public class TriagemChamadaClienteDAO extends DAO {

    private final ServicoDAO servicodao = new ServicoDAO();
    private final LocalDAO localdao = new LocalDAO();

    public TriagemChamadaClienteDAO() {
    }

    /**
     * Metodo responsavel por inserir triagemchamadacliente
     *
     * @param triagemchamadaclientetransfer
     * @return boolean
     * @throws java.sql.SQLException
     */
    public boolean inserirTriagemChamadaCliente(TriagemChamadaClienteTransfer triagemchamadaclientetransfer) throws SQLException {
        try {
            String nomeprincipal;
            strBuffer = new StringBuffer().append("INSERT INTO projetosga.triagemchamadacliente("
                    + "             datainiciotriagemachamadacliente"
                    + "            ,idservico"
                    + "            ,idtriagemalternativa"
                    + "            ,normaltriagemachamadacliente"
                    + "            ,prioridadetriagemachamadacliente"
                    + "            ,nometriagemachamadacliente"
                    + "            ,emitirsenhatriagemchamadacliente"
                    + "            ,statustriagemchamadacliente)"
                    + "    VALUES (?"
                    + "           ,?"
                    + "           ,?"
                    + "           ,?"
                    + "           ,?"
                    + "           ,?"
                    + "           ,?"
                    + "           ,?);"
            );
            pstm = abrirconexao.getConexao().prepareStatement(strBuffer.toString());
            int tamtriagemchamadacliente = 1;
            //data inicio cliente
            pstm.setObject(tamtriagemchamadacliente++, new Util().getSqlTimeStamp(new Util().getPegaDataAtual()));
            //obter id Servico
            pstm.setObject(tamtriagemchamadacliente++, triagemAlternativaServicobuscarServico(triagemchamadaclientetransfer.getServicotransfer().getNomeservico()).iterator().next().getId());
            //obter idTriagemAlternativa
            pstm.setObject(tamtriagemchamadacliente++, triagemchamadaclientetransfer.getId());
            //pedido de senha normal/convencional
            pstm.setObject(tamtriagemchamadacliente++, triagemchamadaclientetransfer.getNormalchamadatriagemchamadacliente());
            //pedido de senha prioridade 
            pstm.setObject(tamtriagemchamadacliente++, triagemchamadaclientetransfer.getPrioridadechamadatriagemchamadacliente());
            ////obter a quantidade com o id da Triagem Alternativa e o tipo de chamada da Triagem
            int quantidade = 0;
            if (triagemchamadaclientetransfer.getNormalchamadatriagemchamadacliente() != null) {
                quantidade = quantidadeTriagemChamadaCliente(triagemchamadaclientetransfer.getId(), triagemchamadaclientetransfer.getNormalchamadatriagemchamadacliente());
                //senha normal/convencional 
                strBuffer = new StringBuffer();
                //pega os dados de convencional(c) + sigla + quantidade e concatena em um nome principal gerando uma senha unica de convencional
                strBuffer.append(triagemchamadaclientetransfer.getNormalchamadatriagemchamadacliente().substring(0, 1))
                        .append(triagemchamadaclientetransfer.getSiglatriagemalternativa())
                        .append(quantidade);
                nomeprincipal = strBuffer.toString().replace(" ", "");
                pstm.setObject(tamtriagemchamadacliente++, nomeprincipal);
            } else {
                quantidade = quantidadeTriagemChamadaCliente(triagemchamadaclientetransfer.getId(), triagemchamadaclientetransfer.getPrioridadechamadatriagemchamadacliente());
                //senha prioridade
                strBuffer = new StringBuffer();
                //pega os dados de prioridade(p) + sigla + quantidade e concatena em um nome principal gerando uma senha unica de prioridade
                strBuffer.append(triagemchamadaclientetransfer.getPrioridadechamadatriagemchamadacliente().substring(0, 1))
                        .append(triagemchamadaclientetransfer.getSiglatriagemalternativa())
                        .append(quantidade);
                nomeprincipal = strBuffer.toString().replace(" ", "");
                pstm.setObject(tamtriagemchamadacliente++, nomeprincipal);
            }
            //emitir senha
            pstm.setObject(tamtriagemchamadacliente++, triagemchamadaclientetransfer.getEmitirsenhatriagemchamadacliente());
            //status ativo
            pstm.setObject(tamtriagemchamadacliente++, triagemchamadaclientetransfer.getStatustriagemchamadacliente());
            //resultado inserido na base de dados
            pstm.executeUpdate();
        } catch (SQLException ex) {
            logPrincipal(TriagemChamadaClienteDAO.class).error(">>>>ERROR AO INSERIR TRIAGEMCHAMADACLIENTE(inserirTriagemChamadaCliente)", ex);
        } finally {
            strBuffer = null;
            pstm.close();
            abrirconexao.fecharConexao();
        }
        return true || false;
    }

    /**
     * Metodo com a caracteristica de retornar uma lista de Dados
     *
     * @return List<TriagemChamadaClienteTransfer>
     * @throws java.sql.SQLException
     */
    public List<TriagemChamadaClienteTransfer> listarTodosTriagemChamadaCliente() throws SQLException {
        List<TriagemChamadaClienteTransfer> triagemchamadaclientetransfers = new ArrayList<TriagemChamadaClienteTransfer>();
        PreparedStatement pstmchamadacliente = null;
        try {
            strBuffer = new StringBuffer().append("select triagemalternativa.siglatriagemalternativa\n"
                    + "                                  ,servico.nomeservico\n"
                    + "                                  ,triagemalternativa.idtriagemalternativa	\n"
                    + "                     	  from projetosga.triagemalternativa triagemalternativa\n"
                    + "                           inner join\n"
                    + "                           projetosga.servico servico on (servico.idservico = triagemalternativa.idservico)"
                    + "                           where triagemalternativa.statustriagemalternativa='A'          \n"
                    + "                           order by servico.nomeservico");
            pstmchamadacliente = abrirconexao.getConexao().prepareStatement(strBuffer.toString());
            rs = pstmchamadacliente.executeQuery();
            while (rs.next()) {

                TriagemChamadaClienteTransfer triagemchamadaclientetransfer = new TriagemChamadaClienteTransfer();
                //heranca da classe TriagemAlternativa contendo a sigla e o servico
                triagemchamadaclientetransfer.setId(rs.getShort("idtriagemalternativa"));
                triagemchamadaclientetransfer.setSiglatriagemalternativa(rs.getString("siglatriagemalternativa"));
                triagemchamadaclientetransfer.getServicotransfer().setNomeservico(rs.getString("nomeservico"));
                triagemchamadaclientetransfers.add(triagemchamadaclientetransfer);

            }

        } catch (SQLException ex) {
            logPrincipal(TriagemChamadaClienteDAO.class).error(">>>>ERROR AO LISTAR TRIAGEMCHAMADACLIENTE(listarTodosTriagemChamadaCliente)", ex);
        } finally {

            rs.close();
            abrirconexao.fecharConexao();
        }
        return triagemchamadaclientetransfers;
    }

    /**
     * Metodo com a caracteristica de retornar uma lista de Dados
     *
     * @return List<TriagemChamadaClienteTransfer>
     * @throws java.sql.SQLException
     */
    public List<TriagemChamadaClienteTransfer> listarServicoPorIdUsuarioTriagemChamadaCliente(Short id) throws SQLException {
        List<TriagemChamadaClienteTransfer> triagemchamadaclientetransfers = new ArrayList<TriagemChamadaClienteTransfer>();
        PreparedStatement pstmchamadacliente = null;
        try {
            strBuffer = new StringBuffer().append("SELECT triagemalternativa.siglatriagemalternativa\n"
                    + "                                                       ,servico.nomeservico\n"
                    + "                                                       ,triagemalternativa.idtriagemalternativa	\n"
                    + "                                                FROM  projetosga.usuario usuario                                                                                                         	                                                                                                                \n"
                    + "                                                INNER JOIN \n"
                    + "                     				      projetosga.usuarioservico usuarioservico on (usuarioservico.idusuario = usuario.idusuario)\n"
                    + "                     				INNER JOIN\n"
                    + "                     				      projetosga.servico servico on (usuarioservico.idservico = servico.idservico)\n"
                    + "                     				INNER JOIN\n"
                    + "                     				      projetosga.triagemalternativa triagemalternativa on (triagemalternativa.idservico = servico.idservico)	                                                \n"
                    + "                                                WHERE usuario.idusuario=? AND triagemalternativa.statustriagemalternativa='A'          \n"
                    + "                                                ORDER BY servico.nomeservico");
            pstmchamadacliente = abrirconexao.getConexao().prepareStatement(strBuffer.toString());
            pstmchamadacliente.setObject(1, id);
            rs = pstmchamadacliente.executeQuery();
            while (rs.next()) {

                TriagemChamadaClienteTransfer triagemchamadaclientetransfer = new TriagemChamadaClienteTransfer();
                //heranca da classe TriagemAlternativa contendo a sigla e o servico
                triagemchamadaclientetransfer.setId(rs.getShort("idtriagemalternativa"));
                triagemchamadaclientetransfer.setSiglatriagemalternativa(rs.getString("siglatriagemalternativa"));
                triagemchamadaclientetransfer.getServicotransfer().setNomeservico(rs.getString("nomeservico"));
                triagemchamadaclientetransfers.add(triagemchamadaclientetransfer);

            }

        } catch (SQLException ex) {
            logPrincipal(TriagemChamadaClienteDAO.class).error(">>>>ERROR AO LISTAR TRIAGEMCHAMADACLIENTE(listarServicoPorUsuarioTriagemChamadaCliente)", ex);
        } finally {

            rs.close();
            abrirconexao.fecharConexao();
        }
        return triagemchamadaclientetransfers;
    }

    /**
     * Metodo com a caracteristica de retornar uma lista de Dados
     *
     * @return List<TriagemChamadaClienteTransfer>
     * @throws java.sql.SQLException
     */
    public List<TriagemChamadaClienteTransfer> listarServicoPorUsuarioTriagemChamadaCliente(Short id, String nomeServico) throws SQLException {
        List<TriagemChamadaClienteTransfer> triagemchamadaclientetransfers = new ArrayList<TriagemChamadaClienteTransfer>();
        PreparedStatement pstmchamadacliente = null;
        try {
            strBuffer = new StringBuffer().append("SELECT triagemalternativa.siglatriagemalternativa\n"
                    + "                                                       ,servico.nomeservico\n"
                    + "                                                       ,triagemalternativa.idtriagemalternativa	\n"
                    + "                                                FROM  projetosga.usuario usuario                                                                                                         	                                                                                                                \n"
                    + "                                                INNER JOIN \n"
                    + "                     				      projetosga.usuarioservico usuarioservico on (usuarioservico.idusuario = usuario.idusuario)\n"
                    + "                     				INNER JOIN\n"
                    + "                     				      projetosga.servico servico on (usuarioservico.idservico = servico.idservico)\n"
                    + "                     				INNER JOIN\n"
                    + "                     				      projetosga.triagemalternativa triagemalternativa on (triagemalternativa.idservico = servico.idservico)	                                                \n"
                    + "                                                WHERE usuario.idusuario=? AND triagemalternativa.statustriagemalternativa='A' AND UPPER(servico.nomeservico)         \n"
                    + "                                                LIKE ('%" + nomeServico + "%')"
                    + "                                                ORDER BY servico.nomeservico");
            pstmchamadacliente = abrirconexao.getConexao().prepareStatement(strBuffer.toString());
            pstmchamadacliente.setObject(1, id);
            //pstmchamadacliente.setObject(2, nomeservico);
            rs = pstmchamadacliente.executeQuery();
            while (rs.next()) {

                TriagemChamadaClienteTransfer triagemchamadaclientetransfer = new TriagemChamadaClienteTransfer();
                //heranca da classe TriagemAlternativa contendo a sigla e o servico
                triagemchamadaclientetransfer.setId(rs.getShort("idtriagemalternativa"));
                triagemchamadaclientetransfer.setSiglatriagemalternativa(rs.getString("siglatriagemalternativa"));
                triagemchamadaclientetransfer.getServicotransfer().setNomeservico(rs.getString("nomeservico"));
                triagemchamadaclientetransfers.add(triagemchamadaclientetransfer);

            }

        } catch (SQLException ex) {
            logPrincipal(TriagemChamadaClienteDAO.class).error(">>>>ERROR AO LISTAR TRIAGEMCHAMADACLIENTE(listarServicoPorUsuarioTriagemChamadaCliente)", ex);
        } finally {

            rs.close();
            abrirconexao.fecharConexao();
        }
        return triagemchamadaclientetransfers;
    }

    /**
     * Metodo com a caracteristica de retornar uma lista de Dados sobre o
     * serviço a ser buscado
     *
     * @return List<TriagemChamadaClienteTransfer>
     * @throws java.sql.SQLException
     */
    public List<TriagemChamadaClienteTransfer> buscarServicoTriagemChamadaCliente(String nomeservico) throws SQLException {
        List<TriagemChamadaClienteTransfer> triagemchamadaclientetransfers = new ArrayList<TriagemChamadaClienteTransfer>();
        PreparedStatement pstmchamadacliente = null;
        try {
            strBuffer = new StringBuffer().append("select triagemalternativa.siglatriagemalternativa\n"
                    + "                                  ,servico.nomeservico\n"
                    + "                                  ,triagemalternativa.idtriagemalternativa	\n"
                    + "                     	  from projetosga.triagemalternativa triagemalternativa\n"
                    + "                           inner join\n"
                    + "                           projetosga.servico servico on (servico.idservico = triagemalternativa.idservico)"
                    + "                           WHERE UPPER(servico.nomeservico) \n"
                    + "                           LIKE UPPER(?)\n"
                    + "                           order by servico.nomeservico");
            pstmchamadacliente = abrirconexao.getConexao().prepareStatement(strBuffer.toString());
            pstmchamadacliente.setObject(1, "%" + nomeservico + "%");
            rs = pstmchamadacliente.executeQuery();
            while (rs.next()) {

                TriagemChamadaClienteTransfer triagemchamadaclientetransfer = new TriagemChamadaClienteTransfer();
                //heranca da classe TriagemAlternativa contendo a sigla e o servico
                triagemchamadaclientetransfer.setId(rs.getShort("idtriagemalternativa"));
                triagemchamadaclientetransfer.setSiglatriagemalternativa(rs.getString("siglatriagemalternativa"));
                triagemchamadaclientetransfer.getServicotransfer().setNomeservico(rs.getString("nomeservico"));
                triagemchamadaclientetransfers.add(triagemchamadaclientetransfer);

            }

        } catch (SQLException ex) {
            logPrincipal(TriagemChamadaClienteDAO.class).error(">>>>ERROR AO LISTAR TRIAGEMCHAMADACLIENTE(buscarServicoTriagemChamadaCliente)", ex);
        } finally {

            rs.close();
            abrirconexao.fecharConexao();
        }
        return triagemchamadaclientetransfers;
    }

    /**
     * Metodo com a caracteristica de retornar uma lista de Dados ativos
     * contendo uma unica senha ativa
     *
     * @param nometriagemachamadacliente
     * @return List<TriagemChamadaClienteTransfer>
     * buscarSenhasMonitorTriagemChamadaCliente
     * @throws java.sql.SQLException
     */
    public List<TriagemChamadaClienteTransfer> buscarSenhaAtivaTriagemChamadaCliente(String nometriagemachamadacliente) throws SQLException {

        List<TriagemChamadaClienteTransfer> triagemChamadaClienteTransfers = new ArrayList<TriagemChamadaClienteTransfer>();
        ResultSet rsbuscarsenhasativa = null;
        PreparedStatement pstmbuscarsenhasativa = null;
        try {
            StringBuffer strBufferSenhasAtiva = new StringBuffer().append("SELECT projetosga.servicotrbk.nomeservico,\n"
                    + "			projetosga.triagemchamadaclientetrbk.nometriagemachamadacliente,\n"
                    + "			projetosga.triagemchamadaclientetrbk.datainiciotriagemachamadacliente,\n"
                    + "			projetosga.triagemchamadaclientetrbk.prioridadetriagemachamadacliente,\n"
                    + "			projetosga.triagemchamadaclientetrbk.normaltriagemachamadacliente,\n"
                    + "			projetosga.triagemchamadaclientetrbk.siglaunidade\n"
                    + "	FROM 		projetosga.triagemchamadaclientetrbk\n"
                    + "	INNER JOIN 	projetosga.servicotrbk ON \n"
                    + "			projetosga.triagemchamadaclientetrbk.idservico = projetosga.servicotrbk.idservico \n"
                    + "	WHERE  		((projetosga.triagemchamadaclientetrbk.nometriagemachamadacliente = ?)\n"
                    + "	AND 		(triagemchamadaclientetrbk.statustriagemchamadacliente ='A'))\n"
                    + "	ORDER BY 	projetosga.triagemchamadaclientetrbk.datainiciotriagemachamadacliente \n"
                    + "	DESC LIMIT 1;");
            pstmbuscarsenhasativa = abrirconexao.getConexao().prepareStatement(strBufferSenhasAtiva.toString());
            pstmbuscarsenhasativa.setObject(1, nometriagemachamadacliente);
            rsbuscarsenhasativa = pstmbuscarsenhasativa.executeQuery();
            while (rsbuscarsenhasativa.next()) {
                TriagemChamadaClienteTransfer triagemchamadaclientetransfer = new TriagemChamadaClienteTransfer();
                //servico
                triagemchamadaclientetransfer.getServicotransfer().setNomeservico(rsbuscarsenhasativa.getString("nomeservico"));
                //nometriagemachamadacliente
                triagemchamadaclientetransfer.setNometriagemachamadacliente(rsbuscarsenhasativa.getString("nometriagemachamadacliente").toUpperCase());
                //data chegada
                triagemchamadaclientetransfer.setDatainiciotriagemchamadacliente(rsbuscarsenhasativa.getTimestamp("datainiciotriagemachamadacliente"));
                //prioridade
                triagemchamadaclientetransfer.setPrioridadechamadatriagemchamadacliente(rsbuscarsenhasativa.getString("prioridadetriagemachamadacliente"));
                //normal
                triagemchamadaclientetransfer.setNormalchamadatriagemchamadacliente(rsbuscarsenhasativa.getString("normaltriagemachamadacliente"));
                //unidade
                triagemchamadaclientetransfer.getUnidadeTransfer().setSiglaunidade(rsbuscarsenhasativa.getString("siglaunidade"));
                //adicionar na lista
                triagemChamadaClienteTransfers.add(triagemchamadaclientetransfer);
            }
        } catch (SQLException ex) {
            logPrincipal(MonitorChamadaClienteDAO.class).error(">>>>ERROR AO BUSCAR TRIAGEMCHAMADACLIENTE(buscarSenhaAtivaTriagemChamadaCliente)", ex);
        } finally {
            pstmbuscarsenhasativa.close();
            rsbuscarsenhasativa.close();
            abrirconexao.fecharConexao();
        }
        return triagemChamadaClienteTransfers;
    }

    /**
     * Metodo com a caracteristica de retornar uma lista de Dados contendo os
     * dados do cliente sobre a senha
     *
     * @return List<TriagemChamadaClienteTransfer>
     * @throws java.sql.SQLException
     */
    public List<TriagemChamadaClienteTransfer> listarTodasSenhasTriagemChamadaCliente() throws SQLException {
        List<TriagemChamadaClienteTransfer> triagemchamadaclientetransfers = new ArrayList<TriagemChamadaClienteTransfer>();
        PreparedStatement pstmquantidadenormal = null;
        try {
            //quantidade de senhas
            strBuffer = new StringBuffer().append("SELECT                       count(triagemchamadacliente.normaltriagemachamadacliente) as quantidadenormal\n"
                    + "                     					,count (triagemchamadacliente.prioridadetriagemachamadacliente) as quantidadeprioridade\n"
                    + "                     					,triagemalternativa.siglatriagemalternativa\n"
                    + "                     					,servico.nomeservico\n"
                    + "                                          		FROM projetosga.triagemalternativa triagemalternativa\n"
                    + "                                                         INNER JOIN\n"
                    + "                                                         projetosga.servico servico on (servico.idservico = triagemalternativa.idservico)\n"
                    + "                                                         INNER JOIN\n"
                    + "                                                         projetosga.triagemchamadacliente triagemchamadacliente on(triagemchamadacliente.idtriagemalternativa = triagemalternativa.idtriagemalternativa)\n"
                    + "                     					WHERE ((triagemchamadacliente.prioridadetriagemachamadacliente='PRIORIDADE') OR (triagemchamadacliente.normaltriagemachamadacliente='CONVENCIONAL'))"                    
                    + "                     					GROUP BY triagemalternativa.idtriagemalternativa\n"
                    + "                     						 ,servico.nomeservico\n"
                    + "                     					ORDER BY servico.nomeservico");

            pstmquantidadenormal = abrirconexao.getConexao().prepareStatement(strBuffer.toString());
            rs = pstmquantidadenormal.executeQuery();
            while (rs.next()) {
                TriagemChamadaClienteTransfer triagemchamadaclientetransfer = new TriagemChamadaClienteTransfer();
                //quantidade normal
                triagemchamadaclientetransfer.setQuantidadetriagemchamadaclientenormal(rs.getInt("quantidadenormal"));
                //quantidade prioridade
                triagemchamadaclientetransfer.setQuantidadetriagemchamadaclienteprioridade(rs.getInt("quantidadeprioridade"));
                //sigla
                triagemchamadaclientetransfer.setSiglatriagemalternativa(rs.getString("siglatriagemalternativa"));
                //nome servico
                triagemchamadaclientetransfer.getServicotransfer().setNomeservico(rs.getString("nomeservico"));
                //adicionar na lista
                triagemchamadaclientetransfers.add(triagemchamadaclientetransfer);
            }
        } catch (SQLException ex) {
            logPrincipal(TriagemChamadaClienteDAO.class).error(">>>>ERROR AO LISTAR TRIAGEMCHAMADACLIENTE(listarTodasSenhasTriagemChamadaCliente)", ex);
        } finally {
            pstmquantidadenormal.close();
            rs.close();
            abrirconexao.fecharConexao();
        }
        return triagemchamadaclientetransfers;
    }

    /**
     * Metodo com a caracteristica de retornar uma lista de Dados contendo os
     * dados do cliente sobre a senha
     *
     * @return List<TriagemChamadaClienteTransfer>
     * @throws java.sql.SQLException
     */
    public List<TriagemChamadaClienteTransfer> listarQuantidadedeSenhasPorUsuarioTriagemChamadaCliente(Short id) throws SQLException {
        List<TriagemChamadaClienteTransfer> triagemchamadaclientetransfers = new ArrayList<TriagemChamadaClienteTransfer>();
        PreparedStatement pstmquantidadenormal = null;
        try {
            //quantidade de senhas
            strBuffer = new StringBuffer().append("SELECT   count(triagemchamadacliente.normaltriagemachamadacliente) as quantidadenormal\n"
                    + "																	,count (triagemchamadacliente.prioridadetriagemachamadacliente) as quantidadeprioridade\n"
                    + "																	,triagemalternativa.siglatriagemalternativa\n"
                    + "																	,servico.nomeservico\n"
                    + "                     														FROM  projetosga.usuario usuario                                                                                                         	                                                                                                                \n"
                    + "                                                                                                                                INNER JOIN \n"
                    + "                     														      projetosga.usuarioservico usuarioservico on (usuarioservico.idusuario = usuario.idusuario)\n"
                    + "                     														INNER JOIN\n"
                    + "                     														      projetosga.servico servico on (usuarioservico.idservico = servico.idservico)\n"
                    + "                     														INNER JOIN\n"
                    + "                     														      projetosga.triagemalternativa triagemalternativa on (triagemalternativa.idservico = servico.idservico)	\n"
                    + "                                                                                                                                INNER JOIN\n"
                    + "                                                                                                                                           projetosga.triagemchamadacliente triagemchamadacliente on(triagemchamadacliente.idtriagemalternativa = triagemalternativa.idtriagemalternativa)\n"
                    + "                     														WHERE ((triagemchamadacliente.prioridadetriagemachamadacliente='PRIORIDADE') OR (triagemchamadacliente.normaltriagemachamadacliente='CONVENCIONAL'))\n"
                    + "																      and statustriagemchamadacliente ='A' and usuario.idusuario=?\n"
                    + "                                                                                                         			GROUP BY triagemalternativa.idtriagemalternativa\n"
                    + "																	,servico.nomeservico\n"
                    + "																ORDER BY servico.nomeservico");

            pstmquantidadenormal = abrirconexao.getConexao().prepareStatement(strBuffer.toString());
            pstmquantidadenormal.setObject(1, id);
            rs = pstmquantidadenormal.executeQuery();
            while (rs.next()) {
                TriagemChamadaClienteTransfer triagemchamadaclientetransfer = new TriagemChamadaClienteTransfer();
                //quantidade normal
                triagemchamadaclientetransfer.setQuantidadetriagemchamadaclientenormal(rs.getInt("quantidadenormal"));
                //quantidade prioridade
                triagemchamadaclientetransfer.setQuantidadetriagemchamadaclienteprioridade(rs.getInt("quantidadeprioridade"));
                //sigla
                triagemchamadaclientetransfer.setSiglatriagemalternativa(rs.getString("siglatriagemalternativa"));
                //nome servico
                triagemchamadaclientetransfer.getServicotransfer().setNomeservico(rs.getString("nomeservico"));
                //adicionar na lista
                triagemchamadaclientetransfers.add(triagemchamadaclientetransfer);
            }
        } catch (SQLException ex) {
            logPrincipal(TriagemChamadaClienteDAO.class).error(">>>>ERROR AO LISTAR TRIAGEMCHAMADACLIENTE(listarQuantidadedeSenhasPorUsuarioTriagemChamadaCliente)", ex);
        } finally {
            pstmquantidadenormal.close();
            rs.close();
            abrirconexao.fecharConexao();
        }
        return triagemchamadaclientetransfers;
    }

    /**
     * Metodo com a caracteristica de retornar sempre o ultimo
     * nometriagemachamadacliente, para obter o ultimo valor da senha
     *
     * @param idtriagemalternativa
     * @param chamadatriagemachamadacliente
     * @return int
     * @throws java.sql.SQLException
     */
    private int quantidadeTriagemChamadaCliente(Short idtriagemalternativa, String chamadatriagemachamadaclientenormalouprioridade) throws SQLException {
        int quantidadetriagemchamadacliente = 0;
        ResultSet resultqtd = null;
        PreparedStatement pstmquantidade = null;
        StringBuffer strBufferTriagem = null;
        try {
            //chamada tipo normal/convencional
            if (chamadatriagemachamadaclientenormalouprioridade.equalsIgnoreCase("CONVENCIONAL")) {
                strBufferTriagem = new StringBuffer().append("SELECT count(idtriagemalternativa)quantidadeidtriagemalternativa\n"
                        + "                          FROM projetosga.triagemchamadacliente\n"
                        + "                          WHERE ((idtriagemalternativa  = ?) AND (normaltriagemachamadacliente=?))\n");

            } else {
                //chamada tipo prioridade
                strBufferTriagem = new StringBuffer().append("SELECT count(idtriagemalternativa)quantidadeidtriagemalternativa\n"
                        + "                          FROM projetosga.triagemchamadacliente\n"
                        + "                          WHERE ((idtriagemalternativa  = ?) AND (prioridadetriagemachamadacliente=?))\n");
            }

            pstmquantidade = abrirconexao.getConexao().prepareStatement(strBufferTriagem.toString());
            int tamquantidade = 1;
            pstmquantidade.setObject(tamquantidade++, idtriagemalternativa);
            pstmquantidade.setObject(tamquantidade++, chamadatriagemachamadaclientenormalouprioridade);
            resultqtd = pstmquantidade.executeQuery();
            if (resultqtd.next()) {
                return resultqtd.getInt("quantidadeidtriagemalternativa") + 1;
            }

        } catch (SQLException ex) {
            logPrincipal(TriagemChamadaClienteDAO.class).error(">>>>ERROR AO LISTAR QUANTIDADE TRIAGEMCHAMADACLIENTE(quantidadeTriagemChamadaCliente)", ex);
        } finally {
            pstmquantidade.close();
            resultqtd.close();
            abrirconexao.fecharConexao();
        }
        return quantidadetriagemchamadacliente + 1;
    }

    /**
     * Metodo responsavel por deletar completamente a triagemchamadacliente
     * fazendo reinicar todas as senhas
     *
     * @return
     * @throws java.sql.SQLException
     */
    public boolean deletarTriagemChamadaCliente() throws SQLException {
        try {
            strBuffer = new StringBuffer().append("DELETE FROM projetosga.triagemchamadacliente\n"
                    + " WHERE idtriagemachamadacliente > 0");
            //pstm = abrirconexao.getConexao().prepareStatement(tbPessoa, PreparedStatement.RETURN_GENERATED_KEYS);
            pstm = abrirconexao.getConexao().prepareStatement(strBuffer.toString());

            pstm.executeUpdate();

        } catch (SQLException ex) {
            logPrincipal(TriagemChamadaClienteDAO.class).error(">>>>ERROR AO DELETAR TRIAGEMCHAMADACLIENTE(deletarTriagemChamadaCliente)", ex);
        } finally {

            pstm.close();
            abrirconexao.fecharConexao();
        }
        return true || false;
    }

    /**
     * Metodo com a caracteristica de retornar uma lista de Dados contendo a
     * local
     *
     * @param idtriagemchamadacliente
     * @return List<LocalTransfer>
     * @throws java.sql.SQLException
     */
    public List<TriagemChamadaClienteTransfer> buscaridTriagemAlternativaComIdTriagemChamadaCliente(Short idtriagemchamadacliente) throws SQLException {
        List<TriagemChamadaClienteTransfer> triagemchamadaclientetransfers = new ArrayList<TriagemChamadaClienteTransfer>();
        PreparedStatement pstmconexao = null;
        try {
            strBuffer = new StringBuffer().append("select triagemalternativa.idtriagemalternativa\n"
                    + "                     		         from projetosga.triagemalternativa triagemalternativa\n"
                    + "                                         inner join\n"
                    + "                                         projetosga.triagemchamadacliente triagemchamadacliente on(triagemchamadacliente.idtriagemalternativa = triagemalternativa.idtriagemalternativa)\n"
                    + "                                         where triagemchamadacliente.idtriagemachamadacliente	= ?;");

            pstmconexao = abrirconexao.getConexao().prepareStatement(strBuffer.toString());
            pstmconexao.setObject(1, idtriagemchamadacliente);
            rs = pstmconexao.executeQuery();
            while (rs.next()) {
                TriagemChamadaClienteTransfer triagemchamadaclientetransfer = new TriagemChamadaClienteTransfer();
                //pega id Triagem Alternativa
                triagemchamadaclientetransfer.setId(rs.getShort("idtriagemalternativa"));
                triagemchamadaclientetransfers.add(triagemchamadaclientetransfer);

            }
        } catch (SQLException ex) {
            logPrincipal(TriagemChamadaClienteDAO.class
            ).error(">>>>ERROR AO BUSCAR ID TRIAGEMCHAMADACLIENTE(buscaridTriagemAlternativaComIdTriagemChamadaCliente)", ex);
        } finally {
            rs.close();
            abrirconexao.fecharConexao();
        }
        return triagemchamadaclientetransfers;
    }

    /**
     * Metodo responsavel por verificar se o status esta ativo ou inativo
     *
     * @param statustriagemchamadacliente
     * @return boolean
     * @throws java.sql.SQLException
     */
    public boolean verificarStatusTriagemalternativa(String statustriagemchamadacliente) throws SQLException {
        try {
            strBuffer = new StringBuffer().append("SELECT  statustriagemchamadacliente\n"
                    + "  FROM projetosga.triagemchamadacliente\n"
                    + "  WHERE statustriagemchamadacliente	= ?;");
            pstm = abrirconexao.getConexao().prepareStatement(strBuffer.toString());
            pstm.setObject(1, statustriagemchamadacliente);
            rs = pstm.executeQuery();
            if (rs.next()) {
                return true;

            }
        } catch (SQLException ex) {
            logPrincipal(TriagemChamadaClienteDAO.class
            ).error(">>>>ERROR AO EXISTIR NOME DA UNIDADE(existeNomeTriagemalternativa)", ex);
        } finally {
            rs.close();
            abrirconexao.fecharConexao();
        }
        return false;
    }

    /**
     * Metodo responsavel por verificar se existi sigla
     *
     * @param siglatriagemchamadacliente
     * @return boolean
     * @throws java.sql.SQLException
     */
    public boolean existeSiglaTriagemalternativa(String siglatriagemchamadacliente) throws SQLException {
        try {
            strBuffer = new StringBuffer().append("SELECT  siglatriagemchamadacliente\n"
                    + "  FROM projetosga.triagemchamadacliente\n"
                    + "  WHERE siglatriagemchamadacliente	= ?;");
            pstm = abrirconexao.getConexao().prepareStatement(strBuffer.toString());
            pstm.setObject(1, siglatriagemchamadacliente);
            rs = pstm.executeQuery();
            if (rs.next()) {
                return true;

            }
        } catch (SQLException ex) {
            logPrincipal(ServicoDAO.class
            ).error(">>>>ERROR AO EXISTIR SIGLA DA TRIAGEMALTERNATIVA(existeSiglaTriagemalternativa)", ex);
        } finally {
            rs.close();
            abrirconexao.fecharConexao();
        }
        return false;
    }

    /**
     * Metodo responsavel por buscar por id e nome do servico vinculado a
     * triagemchamadacliente
     *
     * @param id
     * @param nomeservico
     * @return boolean
     * @throws java.sql.SQLException
     *
     */
    public boolean idNomeServicoTriagemChamadaClienteConfere(Short id, String nomeservico) throws SQLException {
        try {
            strBuffer = new StringBuffer().append(
                    "SELECT                                                                              triagemchamadacliente.idtriagemchamadacliente\n"
                    + "                                                                                  ,servico.nomeservico\n"
                    + "                                                                                   from projetosga.triagemchamadacliente triagemchamadacliente\n"
                    + "                                                                                   inner join\n"
                    + "                                                                                   projetosga.servico servico on (servico.idservico = triagemchamadacliente.idservico)\n"
                    + " where  triagemchamadacliente.idtriagemchamadacliente 	= ?\n"
                    + " and servico.nomeservico	= ?;");
            pstm = abrirconexao.getConexao().prepareStatement(strBuffer.toString());

            pstm.setObject(1, id);
            pstm.setObject(2, nomeservico);
            rs = pstm.executeQuery();
            if (rs.next()) {
                return true;
            }
        } catch (SQLException ex) {
            logPrincipal(TriagemChamadaClienteDAO.class
            ).error(">>>>ERROR AO BUSCAR ID E NOME DA TRIAGEMALTERNATIVA(idNomeServicoTriagemChamadaClienteConfere)", ex);
        } finally {
            pstm.close();
            rs.close();
        }
        return false;
    }

    /**
     * Metodo responsavel por buscar nome do servico vinculado a
     * triagemchamadacliente
     *
     * @param nomeservico
     * @return boolean
     * @throws java.sql.SQLException
     *
     */
    public boolean existeNomeServicoTriagemChamadaClienteConfere(String nomeservico) throws SQLException {
        try {
            strBuffer = new StringBuffer().append(
                    "SELECT servico.nomeservico\n"
                    + "                                                                                   from projetosga.triagemchamadacliente triagemchamadacliente\n"
                    + "                                                                                   inner join\n"
                    + "                                                                                   projetosga.servico servico on (servico.idservico = triagemchamadacliente.idservico)\n"
                    + " where servico.nomeservico	= ?;");
            pstm = abrirconexao.getConexao().prepareStatement(strBuffer.toString());
            pstm.setObject(1, nomeservico);
            rs = pstm.executeQuery();
            if (rs.next()) {
                return true;

            }
        } catch (SQLException ex) {
            logPrincipal(TriagemChamadaClienteDAO.class
            ).error(">>>>ERROR AO BUSCAR ID E NOME DO SERVICO NA TRIAGEMALTERNATIVA(existeNomeServicoTriagemChamadaClienteConfere)", ex);
        } finally {
            pstm.close();
            rs.close();
        }
        return false;
    }

    /**
     * Metodo responsavel por buscar por id e nome do Local vinculado a
     * triagemchamadacliente
     *
     * @param id
     * @param nomelocal
     * @return boolean
     * @throws java.sql.SQLException
     *
     */
    public boolean idNomeLocalTriagemChamadaClienteConfere(Short id, String nomelocal) throws SQLException {
        try {
            strBuffer = new StringBuffer().append(
                    "SELECT                                                                              triagemchamadacliente.idtriagemchamadacliente\n"
                    + "                                                                                  ,local.nomelocal\n"
                    + "                                                                                   from projetosga.triagemchamadacliente triagemchamadacliente\n"
                    + "                                                                                   inner join\n"
                    + "                                                                                   projetosga.local local on (local.idlocal = triagemchamadacliente.idlocal)\n"
                    + " where  triagemchamadacliente.idtriagemchamadacliente 	= ?\n"
                    + " and local.nomelocal	= ?;");
            pstm = abrirconexao.getConexao().prepareStatement(strBuffer.toString());

            pstm.setObject(1, id);
            pstm.setObject(2, nomelocal);
            rs = pstm.executeQuery();
            if (rs.next()) {
                return true;

            }
        } catch (SQLException ex) {
            logPrincipal(TriagemChamadaClienteDAO.class
            ).error(">>>>ERROR AO BUSCAR ID E NOME DO LOCAL(idNomeLocalTriagemChamadaClienteConfere)", ex);
        } finally {
            pstm.close();
            rs.close();
        }
        return false;
    }

    /**
     * Metodo responsavel por buscar nome do LOCAL vinculado a
     * triagemchamadacliente
     *
     * @param nomelocal
     * @return boolean
     * @throws java.sql.SQLException
     *
     */
    public boolean existeNomeLocalTriagemChamadaClienteConfere(String nomelocal) throws SQLException {
        try {
            strBuffer = new StringBuffer().append(
                    "SELECT local.nomelocal\n"
                    + "                                                                                   from projetosga.triagemchamadacliente triagemchamadacliente\n"
                    + "                                                                                   inner join\n"
                    + "                                                                                   projetosga.local local on (local.idlocal = triagemchamadacliente.idlocal)\n"
                    + " where local.nomelocal	= ?;");
            pstm = abrirconexao.getConexao().prepareStatement(strBuffer.toString());
            pstm.setObject(1, nomelocal);
            rs = pstm.executeQuery();
            if (rs.next()) {
                return true;

            }
        } catch (SQLException ex) {
            logPrincipal(TriagemChamadaClienteDAO.class
            ).error(">>>>ERROR AO BUSCAR ID E NOME DO LOCAL NA TRIAGEMALTERNATIVA(existeNomeLocalTriagemChamadaClienteConfere)", ex);
        } finally {
            pstm.close();
            rs.close();
        }
        return false;
    }

    /**
     * Metodo responsavel por retornar a ultimo nome da senha
     *
     * @return
     * @throws java.sql.SQLException
     */
    public String retornarUltimaSenha() throws SQLException {

        try {
            strBuffer = new StringBuffer().append(
                    " SELECT                                                      triagemchamadacliente.nometriagemachamadacliente\n"
                    + "                                                     FROM  projetosga.triagemchamadacliente triagemchamadacliente \n"
                    + "                                                     ORDER BY triagemchamadacliente.idtriagemachamadacliente \n"
                    + "                                                     DESC LIMIT 1;");
            pstm = abrirconexao.getConexao().prepareStatement(strBuffer.toString());
            rs = pstm.executeQuery();
            if (rs.next()) {
                return rs.getString("nometriagemachamadacliente").toUpperCase();
            }
        } catch (SQLException ex) {
            logPrincipal(TriagemChamadaClienteDAO.class).error(">>>>ERROR AO RETORNAR ULTIMA SENHA DA TRIAGEMCHAMADACLIENTEDAO(retornarUltimaSenha)", ex);
        } finally {
            pstm.close();
            rs.close();
            abrirconexao.fecharConexao();
            strBuffer = null;
        }
        return "";
    }

    /**
     * Metodo responsavel por buscar nome do LOCAL vinculado a
     * triagemchamadacliente
     *
     * @param nomelocal
     * @return boolean
     * @throws java.sql.SQLException
     *
     */
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
    public List<ServicoTransfer> triagemAlternativaServico() throws SQLException {
        return servicodao.listarTodosServico();
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
