/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.sga.dao;

import br.com.sga.transfer.Category;
import br.com.sga.transfer.ServicoTransfer;
import br.com.sga.transfer.TriagemChamadaClienteTransfer;
import br.com.sga.util.Util;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import javax.faces.model.SelectItem;
import javax.faces.model.SelectItemGroup;

/**
 *
 * @author Jandisson
 */
public class MonitorChamadaClienteDAO extends DAO {

    /**
     * Metodo com a caracteristica de retornar uma lista de Dados
     *
     * @return List<TriagemChamadaClienteTransfer>
     * @throws java.sql.SQLException
     */
    public List<TriagemChamadaClienteTransfer> listarTodasSenhasMonitorTriagemChamadaCliente() throws SQLException {

        List<TriagemChamadaClienteTransfer> triagemchamadaclientetransfers = new ArrayList();
        PreparedStatement pstmquantidadenormal = null;
        ResultSet rssenha = null;
        try {
            //quantidade de senhas
            strBuffer = new StringBuffer().append("SELECT                                                        triagemalternativa.siglatriagemalternativa\n"
                    + "                                                               				,triagemchamadacliente.prioridadetriagemachamadacliente\n"
                    + "                                                               				,triagemchamadacliente.nometriagemachamadacliente\n"
                    + "                                                               				,servico.nomeservico\n"
                    + "                                                                                         ,servico.statusservico\n"
                    + "                                                                                         ,triagemchamadacliente.normaltriagemachamadacliente"
                    + "                                                                                         ,triagemchamadacliente.datainiciotriagemachamadacliente"
                    + "                                                                                         ,(now()-datainiciotriagemachamadacliente) AS horasesperadas"
                    + "                                                                                         ,triagemchamadacliente.emitirsenhatriagemchamadacliente"
                    + "                                                                                    		FROM projetosga.triagemalternativa triagemalternativa\n"
                    + "                                                                                             INNER JOIN\n"
                    + "                                                                                                 projetosga.servico servico on (servico.idservico = triagemalternativa.idservico)\n"
                    + "                                                                                             INNER JOIN\n"
                    + "                                                                                                 projetosga.triagemchamadacliente triagemchamadacliente on(triagemchamadacliente.idtriagemalternativa = triagemalternativa.idtriagemalternativa)"
                    + "                                                        WHERE triagemchamadacliente.statustriagemchamadacliente ='A'\n"
                    + "                                                               				ORDER BY      servico.nomeservico\n"
                    + "                                                                                                      ,triagemchamadacliente.datainiciotriagemachamadacliente");
            pstmquantidadenormal = abrirconexao.getConexao().prepareStatement(strBuffer.toString());
            rssenha = pstmquantidadenormal.executeQuery();
            while (rssenha.next()) {
                TriagemChamadaClienteTransfer triagemchamadaclientetransfer = new TriagemChamadaClienteTransfer();

                //sigla
                triagemchamadaclientetransfer.setSiglatriagemalternativa(rssenha.getString("siglatriagemalternativa"));
                //servico
                triagemchamadaclientetransfer.getServicotransfer().setNomeservico(rssenha.getString("nomeservico"));
                //nometriagemachamadacliente
                triagemchamadaclientetransfer.setNometriagemachamadacliente(rssenha.getString("nometriagemachamadacliente").toUpperCase());
                //cor do nome da senha, com a primeira posicao
                String cornomesenha = triagemchamadaclientetransfer.getNometriagemachamadacliente().substring(0, 1);
                if (cornomesenha.equalsIgnoreCase("P")) {
                    triagemchamadaclientetransfer.setCormonitorsenha("Red");
                    triagemchamadaclientetransfer.setPrioridadetriagemachamadacliente(rssenha.getString("prioridadetriagemachamadacliente"));
                } else {
                    //definir prioridades
                    triagemchamadaclientetransfer.setPrioridadetriagemachamadacliente(rssenha.getString("normaltriagemachamadacliente"));
                    triagemchamadaclientetransfer.setCormonitorsenha("Blue");
                }
                //data chegada
                triagemchamadaclientetransfer.setDatainiciotriagemchamadacliente(rssenha.getTimestamp("datainiciotriagemachamadacliente"));
                //tempo de espera
                triagemchamadaclientetransfer.setTempodeesperatriagemchamadacliente(new Util().retorneDataHoraseMin(triagemchamadaclientetransfer.getDatainiciotriagemchamadacliente()));
                //normal
                triagemchamadaclientetransfer.setNormalchamadatriagemchamadacliente(rssenha.getString("normaltriagemachamadacliente"));
                //alteracao de senha
                triagemchamadaclientetransfer.setEmitirsenhatriagemchamadacliente(rssenha.getString("emitirsenhatriagemchamadacliente"));
                //adicionar na lista
                triagemchamadaclientetransfers.add(triagemchamadaclientetransfer);
            }
        } catch (SQLException ex) {
            logPrincipal(MonitorChamadaClienteDAO.class).error(">>>>ERROR AO LISTAR TRIAGEMCHAMADACLIENTEMONITOR(listarTodasSenhasMonitorTriagemChamadaCliente)", ex);
        } finally {
            pstmquantidadenormal.close();
            rssenha.close();
            abrirconexao.fecharConexao();
        }
        return triagemchamadaclientetransfers;
    }

    /**
     * Metodo com a caracteristica de retornar uma lista de Dados relacionadas
     * as senhas de um respectivo usuário
     *
     * @return List<TriagemChamadaClienteTransfer>
     * @throws java.sql.SQLException
     */
    public List<TriagemChamadaClienteTransfer> listarTodasSenhasPorServicoUsuarioMonitorTriagemChamadaCliente(Short id, String ativoInativo) throws SQLException {

        List<TriagemChamadaClienteTransfer> triagemchamadaclientetransfers = new ArrayList();
        PreparedStatement pstmSenhasUsuarioServico = null;
        ResultSet rssenha = null;
        try {
            if (ativoInativo.equalsIgnoreCase(" ") || ativoInativo.equalsIgnoreCase("")) {
                //quantidade de senhas
                strBuffer = new StringBuffer().append("SELECT                                                                                triagemalternativa.siglatriagemalternativa\n"
                        + "															,triagemchamadacliente.prioridadetriagemachamadacliente\n"
                        + "															,triagemchamadacliente.nometriagemachamadacliente\n"
                        + "															,servico.nomeservico\n"
                        + "															,servico.statusservico\n"
                        + "															,triagemchamadacliente.normaltriagemachamadacliente\n"
                        + "															,triagemchamadacliente.datainiciotriagemachamadacliente\n"
                        + "															,(now()-datainiciotriagemachamadacliente) AS horasesperadas\n"
                        + "															,triagemchamadacliente.emitirsenhatriagemchamadacliente\n"
                        + "														FROM  projetosga.usuario usuario                                                                                                         	                                                                                                                \n"
                        + "                                                                                                         INNER JOIN \n"
                        + "														      projetosga.usuarioservico usuarioservico on (usuarioservico.idusuario = usuario.idusuario)\n"
                        + "														INNER JOIN\n"
                        + "														      projetosga.servico servico on (usuarioservico.idservico = servico.idservico)\n"
                        + "														INNER JOIN\n"
                        + "														      projetosga.triagemalternativa triagemalternativa on (triagemalternativa.idservico = servico.idservico)	\n"
                        + "                                                                                                         INNER JOIN\n"
                        + "                                                                                                                      projetosga.triagemchamadacliente triagemchamadacliente on(triagemchamadacliente.idtriagemalternativa = triagemalternativa.idtriagemalternativa)\n"
                        + "														WHERE usuario.idusuario=? and triagemchamadacliente.statustriagemchamadacliente ='A'\n"
                        + "                                                                                    			ORDER BY    servico.nomeservico\n"
                        + "                                                                                                                           ,triagemchamadacliente.datainiciotriagemachamadacliente");
                pstmSenhasUsuarioServico = abrirconexao.getConexao().prepareStatement(strBuffer.toString());
                pstmSenhasUsuarioServico.setObject(1, id);                
                rssenha = pstmSenhasUsuarioServico.executeQuery();

                while (rssenha.next()) {
                    TriagemChamadaClienteTransfer triagemchamadaclientetransfer = new TriagemChamadaClienteTransfer();

                    //sigla
                    triagemchamadaclientetransfer.setSiglatriagemalternativa(rssenha.getString("siglatriagemalternativa"));
                    //servico
                    triagemchamadaclientetransfer.getServicotransfer().setNomeservico(rssenha.getString("nomeservico"));
                    //nometriagemachamadacliente
                    triagemchamadaclientetransfer.setNometriagemachamadacliente(rssenha.getString("nometriagemachamadacliente").toUpperCase());
                    //cor do nome da senha, com a primeira posicao
                    String cornomesenha = triagemchamadaclientetransfer.getNometriagemachamadacliente().substring(0, 1);
                    if (cornomesenha.equalsIgnoreCase("P")) {
                        triagemchamadaclientetransfer.setCormonitorsenha("Red");
                        triagemchamadaclientetransfer.setPrioridadetriagemachamadacliente(rssenha.getString("prioridadetriagemachamadacliente"));
                    } else {
                        //definir prioridades
                        triagemchamadaclientetransfer.setPrioridadetriagemachamadacliente(rssenha.getString("normaltriagemachamadacliente"));
                        triagemchamadaclientetransfer.setCormonitorsenha("Blue");
                    }
                    //data chegada
                    triagemchamadaclientetransfer.setDatainiciotriagemchamadacliente(rssenha.getTimestamp("datainiciotriagemachamadacliente"));
                    //tempo de espera
                    triagemchamadaclientetransfer.setTempodeesperatriagemchamadacliente(new Util().retorneDataHoraseMin(triagemchamadaclientetransfer.getDatainiciotriagemchamadacliente()));
                    //normal
                    triagemchamadaclientetransfer.setNormalchamadatriagemchamadacliente(rssenha.getString("normaltriagemachamadacliente"));
                    //alteracao de senha
                    triagemchamadaclientetransfer.setEmitirsenhatriagemchamadacliente(rssenha.getString("emitirsenhatriagemchamadacliente"));
                    //adicionar na lista
                    triagemchamadaclientetransfers.add(triagemchamadaclientetransfer);
                }
            } else {
                //quantidade de senhas
                strBuffer = new StringBuffer().append("SELECT                                                                                triagemalternativa.siglatriagemalternativa\n"
                        + "															,triagemchamadacliente.prioridadetriagemachamadacliente\n"
                        + "															,triagemchamadacliente.nometriagemachamadacliente\n"
                        + "															,servico.nomeservico\n"
                        + "															,servico.statusservico\n"
                        + "															,triagemchamadacliente.normaltriagemachamadacliente\n"
                        + "															,triagemchamadacliente.datainiciotriagemachamadacliente\n"
                        + "															,(now()-datainiciotriagemachamadacliente) AS horasesperadas\n"
                        + "															,triagemchamadacliente.emitirsenhatriagemchamadacliente\n"
                        + "														FROM  projetosga.usuario usuario                                                                                                         	                                                                                                                \n"
                        + "                                                                                                         INNER JOIN \n"
                        + "														      projetosga.usuarioservico usuarioservico on (usuarioservico.idusuario = usuario.idusuario)\n"
                        + "														INNER JOIN\n"
                        + "														      projetosga.servico servico on (usuarioservico.idservico = servico.idservico)\n"
                        + "														INNER JOIN\n"
                        + "														      projetosga.triagemalternativa triagemalternativa on (triagemalternativa.idservico = servico.idservico)	\n"
                        + "                                                                                                         INNER JOIN\n"
                        + "                                                                                                                      projetosga.triagemchamadacliente triagemchamadacliente on(triagemchamadacliente.idtriagemalternativa = triagemalternativa.idtriagemalternativa)\n"
                        + "														WHERE usuario.idusuario=? and triagemchamadacliente.statustriagemchamadacliente =?\n"
                        + "                                                                                    			ORDER BY    servico.nomeservico\n"
                        + "                                                                                                                           ,triagemchamadacliente.datainiciotriagemachamadacliente");
                pstmSenhasUsuarioServico = abrirconexao.getConexao().prepareStatement(strBuffer.toString());
                pstmSenhasUsuarioServico.setObject(1, id);
                pstmSenhasUsuarioServico.setObject(2, ativoInativo);
                rssenha = pstmSenhasUsuarioServico.executeQuery();

                while (rssenha.next()) {
                    TriagemChamadaClienteTransfer triagemchamadaclientetransfer = new TriagemChamadaClienteTransfer();

                    //sigla
                    triagemchamadaclientetransfer.setSiglatriagemalternativa(rssenha.getString("siglatriagemalternativa"));
                    //servico
                    triagemchamadaclientetransfer.getServicotransfer().setNomeservico(rssenha.getString("nomeservico"));
                    //nometriagemachamadacliente
                    triagemchamadaclientetransfer.setNometriagemachamadacliente(rssenha.getString("nometriagemachamadacliente").toUpperCase());
                    //cor do nome da senha, com a primeira posicao
                    String cornomesenha = triagemchamadaclientetransfer.getNometriagemachamadacliente().substring(0, 1);
                    if (cornomesenha.equalsIgnoreCase("P")) {
                        triagemchamadaclientetransfer.setCormonitorsenha("Red");
                        triagemchamadaclientetransfer.setPrioridadetriagemachamadacliente(rssenha.getString("prioridadetriagemachamadacliente"));
                    } else {
                        //definir prioridades
                        triagemchamadaclientetransfer.setPrioridadetriagemachamadacliente(rssenha.getString("normaltriagemachamadacliente"));
                        triagemchamadaclientetransfer.setCormonitorsenha("Blue");
                    }
                    //data chegada
                    triagemchamadaclientetransfer.setDatainiciotriagemchamadacliente(rssenha.getTimestamp("datainiciotriagemachamadacliente"));
                    //tempo de espera
                    triagemchamadaclientetransfer.setTempodeesperatriagemchamadacliente(new Util().retorneDataHoraseMin(triagemchamadaclientetransfer.getDatainiciotriagemchamadacliente()));
                    //normal
                    triagemchamadaclientetransfer.setNormalchamadatriagemchamadacliente(rssenha.getString("normaltriagemachamadacliente"));
                    //alteracao de senha
                    triagemchamadaclientetransfer.setEmitirsenhatriagemchamadacliente(rssenha.getString("emitirsenhatriagemchamadacliente"));
                    //adicionar na lista
                    triagemchamadaclientetransfers.add(triagemchamadaclientetransfer);
                }
            }
        } catch (SQLException ex) {
            logPrincipal(MonitorChamadaClienteDAO.class).error(">>>>ERROR AO LISTAR TRIAGEMCHAMADACLIENTEMONITOR(listarTodasSenhasMonitorTriagemChamadaCliente)", ex);
        } finally {
            pstmSenhasUsuarioServico.close();
            rssenha.close();
            abrirconexao.fecharConexao();
        }
        return triagemchamadaclientetransfers;
    }

    /**
     * Metodo com a caracteristica de retornar uma lista de Dados relacionadas
     * as senhas de um respectivo usuário
     *
     * @return List<TriagemChamadaClienteTransfer>
     * @throws java.sql.SQLException
     */
    public List<TriagemChamadaClienteTransfer> buscarSenhasPorServicoUsuarioMonitorTriagemChamadaCliente(Short id, String ativoInativo, String nomeSenha) throws SQLException {

        List<TriagemChamadaClienteTransfer> triagemchamadaclientetransfers = new ArrayList();
        PreparedStatement pstmSenhasUsuarioServico = null;
        ResultSet rssenha = null;
        try {
            //quantidade de senhas
            strBuffer = new StringBuffer().append("SELECT                                                                                triagemalternativa.siglatriagemalternativa\n"
                    + "															,triagemchamadacliente.prioridadetriagemachamadacliente\n"
                    + "															,triagemchamadacliente.nometriagemachamadacliente\n"
                    + "															,servico.nomeservico\n"
                    + "															,servico.statusservico\n"
                    + "															,triagemchamadacliente.normaltriagemachamadacliente\n"
                    + "															,triagemchamadacliente.datainiciotriagemachamadacliente\n"
                    + "															,(now()-datainiciotriagemachamadacliente) AS horasesperadas\n"
                    + "															,triagemchamadacliente.emitirsenhatriagemchamadacliente\n"
                    + "														FROM  projetosga.usuario usuario                                                                                                         	                                                                                                                \n"
                    + "                                                                                                         INNER JOIN \n"
                    + "														      projetosga.usuarioservico usuarioservico on (usuarioservico.idusuario = usuario.idusuario)\n"
                    + "														INNER JOIN\n"
                    + "														      projetosga.servico servico on (usuarioservico.idservico = servico.idservico)\n"
                    + "														INNER JOIN\n"
                    + "														      projetosga.triagemalternativa triagemalternativa on (triagemalternativa.idservico = servico.idservico)	\n"
                    + "                                                                                                         INNER JOIN\n"
                    + "                                                                                                                      projetosga.triagemchamadacliente triagemchamadacliente on(triagemchamadacliente.idtriagemalternativa = triagemalternativa.idtriagemalternativa)\n"
                    + "														WHERE (usuario.idusuario=? AND triagemchamadacliente.statustriagemchamadacliente =?) and UPPER(triagemchamadacliente.nometriagemachamadacliente) \n"
                    + "                                                                                                         LIKE UPPER (?)"
                    + "                                                                                                         ORDER BY    servico.nomeservico\n"
                    + "                                                                                                                    ,triagemchamadacliente.datainiciotriagemachamadacliente"
            );
            pstmSenhasUsuarioServico = abrirconexao.getConexao().prepareStatement(strBuffer.toString());
            //int valor=1;
            pstmSenhasUsuarioServico.setObject(1, id);
            pstmSenhasUsuarioServico.setObject(2, ativoInativo);
            pstmSenhasUsuarioServico.setObject(3, "%" + nomeSenha + "%");

            rssenha = pstmSenhasUsuarioServico.executeQuery();

            while (rssenha.next()) {
                TriagemChamadaClienteTransfer triagemchamadaclientetransfer = new TriagemChamadaClienteTransfer();

                //sigla
                triagemchamadaclientetransfer.setSiglatriagemalternativa(rssenha.getString("siglatriagemalternativa"));
                //servico
                triagemchamadaclientetransfer.getServicotransfer().setNomeservico(rssenha.getString("nomeservico"));
                //nometriagemachamadacliente
                triagemchamadaclientetransfer.setNometriagemachamadacliente(rssenha.getString("nometriagemachamadacliente").toUpperCase());
                //cor do nome da senha, com a primeira posicao
                String cornomesenha = triagemchamadaclientetransfer.getNometriagemachamadacliente().substring(0, 1);
                if (cornomesenha.equalsIgnoreCase("P")) {
                    triagemchamadaclientetransfer.setCormonitorsenha("Red");
                    triagemchamadaclientetransfer.setPrioridadetriagemachamadacliente(rssenha.getString("prioridadetriagemachamadacliente"));
                } else {
                    //definir prioridades
                    triagemchamadaclientetransfer.setPrioridadetriagemachamadacliente(rssenha.getString("normaltriagemachamadacliente"));
                    triagemchamadaclientetransfer.setCormonitorsenha("Blue");
                }
                //data chegada
                triagemchamadaclientetransfer.setDatainiciotriagemchamadacliente(rssenha.getTimestamp("datainiciotriagemachamadacliente"));
                //tempo de espera
                triagemchamadaclientetransfer.setTempodeesperatriagemchamadacliente(new Util().retorneDataHoraseMin(triagemchamadaclientetransfer.getDatainiciotriagemchamadacliente()));
                //normal
                triagemchamadaclientetransfer.setNormalchamadatriagemchamadacliente(rssenha.getString("normaltriagemachamadacliente"));
                //alteracao de senha
                triagemchamadaclientetransfer.setEmitirsenhatriagemchamadacliente(rssenha.getString("emitirsenhatriagemchamadacliente"));
                //adicionar na lista
                triagemchamadaclientetransfers.add(triagemchamadaclientetransfer);
            }
        } catch (SQLException ex) {
            logPrincipal(MonitorChamadaClienteDAO.class).error(">>>>ERROR AO LISTAR TRIAGEMCHAMADACLIENTEMONITOR(buscarSenhasPorServicoUsuarioMonitorTriagemChamadaCliente)", ex);
        } finally {
            pstmSenhasUsuarioServico.close();
            rssenha.close();
            abrirconexao.fecharConexao();
        }
        return triagemchamadaclientetransfers;
    }

    /**
     * Metodo com a caracteristica de retornar uma lista de Dados ativos
     * contendo a monitor cliente de senhas
     *
     * @param nometriagemachamadacliente
     * @return List<TriagemChamadaClienteTransfer>
     * buscarSenhasMonitorTriagemChamadaCliente
     * @throws java.sql.SQLException
     */
    public List<TriagemChamadaClienteTransfer> buscarSenhasAtivasMonitorTriagemChamadaCliente(String siglatriagem, String senhasAtivasInativas) throws SQLException {

        List<TriagemChamadaClienteTransfer> triagemChamadaClienteTransfers = new ArrayList<TriagemChamadaClienteTransfer>();
        ResultSet rsbuscarsenhasativas = null;
        PreparedStatement pstmbuscarsenhasativas = null;
        StringBuffer strBufferSenhasAtivas = null;
        try {
            if (("".equalsIgnoreCase(siglatriagem) || " ".equalsIgnoreCase(siglatriagem)) && (senhasAtivasInativas.equalsIgnoreCase("I") || senhasAtivasInativas.equalsIgnoreCase("A"))) {
                strBufferSenhasAtivas = new StringBuffer().append("SELECT  triagemalternativa.siglatriagemalternativa\n"
                        + "                                                            ,triagemchamadacliente.prioridadetriagemachamadacliente\n"
                        + "                                                            ,triagemchamadacliente.nometriagemachamadacliente\n"
                        + "                                                            ,servico.nomeservico\n"
                        + "                                                            ,servico.statusservico\n"
                        + "                                                            ,triagemchamadacliente.normaltriagemachamadacliente\n"
                        + "                                                            ,triagemchamadacliente.datainiciotriagemachamadacliente"
                        + "                                                            ,triagemchamadacliente.emitirsenhatriagemchamadacliente"
                        + "                                                            ,triagemchamadacliente.idservico\n"
                        + "                                                            ,triagemchamadacliente.idtriagemalternativa\n"
                        + "                                                     FROM projetosga.triagemalternativa triagemalternativa\n"
                        + "                                                     INNER JOIN\n"
                        + "                                                     projetosga.servico servico on (servico.idservico = triagemalternativa.idservico)\n"
                        + "                                                     INNER JOIN\n"
                        + "                                                     projetosga.triagemchamadacliente triagemchamadacliente on(triagemchamadacliente.idtriagemalternativa = triagemalternativa.idtriagemalternativa)"
                        + "                                                     WHERE triagemchamadacliente.statustriagemchamadacliente =? \n"
                        + "                                                     ORDER BY      servico.nomeservico\n"
                        + "                                                     ,triagemchamadacliente.datainiciotriagemachamadacliente");
                pstmbuscarsenhasativas = abrirconexao.getConexao().prepareStatement(strBufferSenhasAtivas.toString());
                pstmbuscarsenhasativas.setObject(1, senhasAtivasInativas);
                rsbuscarsenhasativas = pstmbuscarsenhasativas.executeQuery();
                while (rsbuscarsenhasativas.next()) {
                    TriagemChamadaClienteTransfer triagemchamadaclientetransfer = new TriagemChamadaClienteTransfer();

                    //sigla
                    triagemchamadaclientetransfer.setSiglatriagemalternativa(rsbuscarsenhasativas.getString("siglatriagemalternativa"));
                    //servico
                    triagemchamadaclientetransfer.getServicotransfer().setNomeservico(rsbuscarsenhasativas.getString("nomeservico"));
                    //nometriagemachamadacliente
                    triagemchamadaclientetransfer.setNometriagemachamadacliente(rsbuscarsenhasativas.getString("nometriagemachamadacliente").toUpperCase());
                    //cor do nome da senha, com a primeira posicao
                    String cornomesenha = triagemchamadaclientetransfer.getNometriagemachamadacliente().substring(0, 1);
                    if (cornomesenha.equalsIgnoreCase("P")) {
                        triagemchamadaclientetransfer.setCormonitorsenha("Red");
                        triagemchamadaclientetransfer.setPrioridadetriagemachamadacliente(rsbuscarsenhasativas.getString("prioridadetriagemachamadacliente"));
                    } else {
                        //definir prioridades
                        triagemchamadaclientetransfer.setPrioridadetriagemachamadacliente(rsbuscarsenhasativas.getString("normaltriagemachamadacliente"));
                        triagemchamadaclientetransfer.setCormonitorsenha("Blue");
                    }
                    //data chegada
                    triagemchamadaclientetransfer.setDatainiciotriagemchamadacliente(rsbuscarsenhasativas.getTimestamp("datainiciotriagemachamadacliente"));
                    //tempo de espera
                    triagemchamadaclientetransfer.setTempodeesperatriagemchamadacliente(new Util().retorneDataHoraseMin(triagemchamadaclientetransfer.getDatainiciotriagemchamadacliente()));
                    //prioridade
                    triagemchamadaclientetransfer.setPrioridadechamadatriagemchamadacliente(rsbuscarsenhasativas.getString("prioridadetriagemachamadacliente"));
                    //normal
                    triagemchamadaclientetransfer.setNormalchamadatriagemchamadacliente(rsbuscarsenhasativas.getString("normaltriagemachamadacliente"));
                    //tipo de emissao de senha
                    triagemchamadaclientetransfer.setEmitirsenhatriagemchamadacliente(rsbuscarsenhasativas.getString("emitirsenhatriagemchamadacliente"));
                    //idservico
                    triagemchamadaclientetransfer.getServicotransfer().setId(rsbuscarsenhasativas.getShort("idservico"));
                    //idtriagemalternativa
                    triagemchamadaclientetransfer.setId(rsbuscarsenhasativas.getShort("idtriagemalternativa"));

                    //adicionar na lista
                    triagemChamadaClienteTransfers.add(triagemchamadaclientetransfer);
                }
            } else {
                strBufferSenhasAtivas = new StringBuffer().append("SELECT  triagemalternativa.siglatriagemalternativa\n"
                        + "                                                            ,triagemchamadacliente.prioridadetriagemachamadacliente\n"
                        + "                                                            ,triagemchamadacliente.nometriagemachamadacliente\n"
                        + "                                                            ,servico.nomeservico\n"
                        + "                                                            ,servico.statusservico\n"
                        + "                                                            ,triagemchamadacliente.normaltriagemachamadacliente\n"
                        + "                                                            ,triagemchamadacliente.datainiciotriagemachamadacliente\n"
                        + "                                                             ,triagemchamadacliente.emitirsenhatriagemchamadacliente\n"
                        + "                                                            ,triagemchamadacliente.idservico\n"
                        + "                                                            ,triagemchamadacliente.idtriagemalternativa\n"
                        + "                                                     FROM projetosga.triagemalternativa triagemalternativa\n"
                        + "                                                     INNER JOIN\n"
                        + "                                                     projetosga.servico servico on (servico.idservico = triagemalternativa.idservico)\n"
                        + "                                                     INNER JOIN\n"
                        + "                                                     projetosga.triagemchamadacliente triagemchamadacliente on(triagemchamadacliente.idtriagemalternativa = triagemalternativa.idtriagemalternativa)"
                        + "                                                     WHERE triagemchamadacliente.statustriagemchamadacliente =? and UPPER(triagemchamadacliente.nometriagemachamadacliente) \n"
                        + "                                                     LIKE UPPER(?)\n"
                        + "                                                     ORDER BY      servico.nomeservico\n"
                        + "                                                     ,triagemchamadacliente.datainiciotriagemachamadacliente");
                pstmbuscarsenhasativas = abrirconexao.getConexao().prepareStatement(strBufferSenhasAtivas.toString());
                pstmbuscarsenhasativas.setObject(1, senhasAtivasInativas);
                pstmbuscarsenhasativas.setObject(2, "%" + siglatriagem + "%");
                rsbuscarsenhasativas = pstmbuscarsenhasativas.executeQuery();
                while (rsbuscarsenhasativas.next()) {
                    TriagemChamadaClienteTransfer triagemchamadaclientetransfer = new TriagemChamadaClienteTransfer();

                    //sigla
                    triagemchamadaclientetransfer.setSiglatriagemalternativa(rsbuscarsenhasativas.getString("siglatriagemalternativa"));
                    //servico
                    triagemchamadaclientetransfer.getServicotransfer().setNomeservico(rsbuscarsenhasativas.getString("nomeservico"));
                    //nometriagemachamadacliente
                    triagemchamadaclientetransfer.setNometriagemachamadacliente(rsbuscarsenhasativas.getString("nometriagemachamadacliente").toUpperCase());
                    //cor do nome da senha, com a primeira posicao
                    String cornomesenha = triagemchamadaclientetransfer.getNometriagemachamadacliente().substring(0, 1);
                    if (cornomesenha.equalsIgnoreCase("P")) {
                        triagemchamadaclientetransfer.setCormonitorsenha("Red");
                        triagemchamadaclientetransfer.setPrioridadetriagemachamadacliente(rsbuscarsenhasativas.getString("prioridadetriagemachamadacliente"));
                    } else {
                        //definir prioridades
                        triagemchamadaclientetransfer.setPrioridadetriagemachamadacliente(rsbuscarsenhasativas.getString("normaltriagemachamadacliente"));
                        triagemchamadaclientetransfer.setCormonitorsenha("Blue");
                    }
                    //data chegada
                    triagemchamadaclientetransfer.setDatainiciotriagemchamadacliente(rsbuscarsenhasativas.getTimestamp("datainiciotriagemachamadacliente"));
                    //tempo de espera
                    triagemchamadaclientetransfer.setTempodeesperatriagemchamadacliente(new Util().retorneDataHoraseMin(triagemchamadaclientetransfer.getDatainiciotriagemchamadacliente()));
                    //prioridade
                    triagemchamadaclientetransfer.setPrioridadechamadatriagemchamadacliente(rsbuscarsenhasativas.getString("prioridadetriagemachamadacliente"));
                    //normal
                    triagemchamadaclientetransfer.setNormalchamadatriagemchamadacliente(rsbuscarsenhasativas.getString("normaltriagemachamadacliente"));
                    //tipo de senha com o resultado de emissao
                    triagemchamadaclientetransfer.setEmitirsenhatriagemchamadacliente(rsbuscarsenhasativas.getString("emitirsenhatriagemchamadacliente"));
                    //idservico
                    triagemchamadaclientetransfer.getServicotransfer().setId(rsbuscarsenhasativas.getShort("idservico"));
                    //idtriagemalternativa
                    triagemchamadaclientetransfer.setId(rsbuscarsenhasativas.getShort("idtriagemalternativa"));

                    //adicionar na lista
                    triagemChamadaClienteTransfers.add(triagemchamadaclientetransfer);
                }
            }
        } catch (SQLException ex) {
            logPrincipal(MonitorChamadaClienteDAO.class).error(">>>>ERROR AO BUSCAR TRIAGEMCHAMADACLIENTEMONITOR(buscarSenhasMonitorTriagemChamadaCliente)", ex);
        } finally {
            pstmbuscarsenhasativas.close();
            rsbuscarsenhasativas.close();
            abrirconexao.fecharConexao();
        }
        return triagemChamadaClienteTransfers;
    }

    /**
     * Metodo com a caracteristica de retornar uma lista de Dados contendo o id
     * da triagemalternativa e o id do servico
     *
     * @param nomeservico
     * @return List<TriagemChamadaClienteTransfer>
     * @throws java.sql.SQLException
     */
    public List<TriagemChamadaClienteTransfer> buscarTriagemAlternativaeServico(String siglatriagem) throws SQLException {

        List<TriagemChamadaClienteTransfer> triagemchamadaclientetransfers = new ArrayList<TriagemChamadaClienteTransfer>();
        PreparedStatement pstmbuscarTriagemAlternativaeServico = null;
        ResultSet rsbuscarTriagemAlternativaeServico = null;
        try {
            StringBuffer buscartriagemalternativaeservico = new StringBuffer().append(" SELECT\n"
                    + "									           triagemalternativa.idtriagemalternativa\n"
                    + "									           ,servico.idservico"
                    + "                                                                            ,triagemalternativa.siglatriagemalternativa\n"
                    + "                                                                                   from projetosga.triagemalternativa triagemalternativa\n"
                    + "                                                                                   inner join\n"
                    + "                                                                                   projetosga.servico servico on (servico.idservico = triagemalternativa.idservico)\n"
                    + "                                                                                   inner join\n"
                    + "                                                                                   projetosga.local local on (local.idlocal = triagemalternativa.idlocal)"
                    + "                                                                             WHERE triagemalternativa.siglatriagemalternativa=? \n"
                    + "                                                                             ORDER BY servico.nomeservico");
            pstmbuscarTriagemAlternativaeServico = abrirconexao.getConexao().prepareStatement(buscartriagemalternativaeservico.toString());
            pstmbuscarTriagemAlternativaeServico.setObject(1, siglatriagem);
            rsbuscarTriagemAlternativaeServico = pstmbuscarTriagemAlternativaeServico.executeQuery();
            if (rsbuscarTriagemAlternativaeServico.next()) {
                TriagemChamadaClienteTransfer triagemchamadaclientetransfer = new TriagemChamadaClienteTransfer();
                //pega o id de triagem alternativa
                triagemchamadaclientetransfer.setId(rsbuscarTriagemAlternativaeServico.getShort("idtriagemalternativa"));
                //pega o id de servico
                triagemchamadaclientetransfer.getServicotransfer().setId(rsbuscarTriagemAlternativaeServico.getShort("idservico"));
                //pega o a senha 
                triagemchamadaclientetransfer.setSiglatriagemalternativa(rsbuscarTriagemAlternativaeServico.getString("siglatriagemalternativa"));
                //adicionar na lista
                triagemchamadaclientetransfers.add(triagemchamadaclientetransfer);
            }
        } catch (SQLException ex) {
            logPrincipal(MonitorChamadaClienteDAO.class).error(">>>>ERROR AO BUSCAR POR TRIAGEM E SERVICO(buscarTriagemAlternativaeServico)", ex);
        } finally {
            pstmbuscarTriagemAlternativaeServico.close();
            rsbuscarTriagemAlternativaeServico.close();
            abrirconexao.fecharConexao();
        }
        return triagemchamadaclientetransfers;
    }

    /**
     * Metodo responsavel por verificar se senha esta ativa ou nao
     *
     * @param nometriagemchamadacliente
     * @return boolean
     * @throws java.sql.SQLException
     */
    public boolean nomeDaTriagemChamadaClienteAtivaouInativa(String nometriagemchamadacliente) throws SQLException {
        try {
            strBuffer = new StringBuffer().append("SELECT "
                    + "       statustriagemchamadacliente\n"
                    + "  FROM projetosga.triagemchamadacliente\n"
                    + "  WHERE nometriagemachamadacliente=?;");
            pstm = abrirconexao.getConexao().prepareStatement(strBuffer.toString());
            pstm.setObject(1, nometriagemchamadacliente);
            rs = pstm.executeQuery();

            if (rs.next()) {
                String validarsenha = rs.getString("statustriagemchamadacliente");
                return true == validarsenha.equalsIgnoreCase("A");
            }
        } catch (SQLException ex) {
            logPrincipal(MonitorChamadaClienteDAO.class).error(">>>>ERROR AO VALIDAR SENHA DA TRIAGEM ATIVA OU INATIVA EM MONITORCHAMADACLIENTE(nomeDaTriagemChamadaClienteAtivaouInativ)", ex);
        } finally {
            pstm.close();
            rs.close();
            abrirconexao.fecharConexao();
        }
        return true || false;
    }

    /**
     * Método com a característica de retornar uma listagem agrupada de cada um
     * das siglas do serviço; Obtendo a palavragerada e o agrupando os valores
     * por posições, bem como a informação sobre o serviço a ser informado
     *
     * @return
     * @throws java.sql.SQLException
     */
    public List<SelectItem> listarTodasSiglasAgrupadas() throws SQLException {
        List<SelectItem> listaagrupada = new ArrayList<>();

        try {
            strBuffer = new StringBuffer().append("SELECT array_agg (distinct triagemalternativa.siglatriagemalternativa) as sigla\n"
                    + "                                                                                            ,servico.nomeservico\n"
                    + "                                                                                      from projetosga.triagemalternativa triagemalternativa\n"
                    + "                                                                                      inner join\n"
                    + "                                                                                      projetosga.servico servico on (servico.idservico = triagemalternativa.idservico)\n"
                    + "                                                                                      WHERE triagemalternativa.statustriagemalternativa='A'\n"
                    + "                     								 group by servico.nomeservico"
                    + "                                            ");
            pstm = abrirconexao.getConexao().prepareStatement(strBuffer.toString());
            rs = pstm.executeQuery();

            while (rs.next()) {
                TriagemChamadaClienteTransfer triagemChamadaClienteTransfer = new TriagemChamadaClienteTransfer();

                String palavrageradaaux = String.valueOf(rs.getObject("sigla"))
                        .replaceAll("\\{|\\}", "")
                        .replaceAll("\"", "");
                String palavragerada[] = palavrageradaaux.split(",");
                //triagemChamadaClienteTransfer.setSiglatriagemalternativa(rs.getString("sigla"));
                triagemChamadaClienteTransfer.getServicotransfer().setNomeservico(rs.getString("nomeservico"));
                SelectItemGroup selectItemGroup = new SelectItemGroup(triagemChamadaClienteTransfer.getServicotransfer().getNomeservico());
                if (palavragerada.length == 1) {
                    selectItemGroup.setSelectItems(new SelectItem[]{new SelectItem(palavragerada[0], palavragerada[0])});
                }
                if (palavragerada.length == 2) {
                    selectItemGroup.setSelectItems(new SelectItem[]{new SelectItem(palavragerada[0], palavragerada[0]), new SelectItem(palavragerada[1], palavragerada[1])});
                }
                if (palavragerada.length == 3) {
                    selectItemGroup.setSelectItems(new SelectItem[]{new SelectItem(palavragerada[0], palavragerada[0]), new SelectItem(palavragerada[1], palavragerada[1]), new SelectItem(palavragerada[2], palavragerada[2])});
                }
                if (palavragerada.length == 4) {
                    selectItemGroup.setSelectItems(new SelectItem[]{new SelectItem(palavragerada[0], palavragerada[0]), new SelectItem(palavragerada[1], palavragerada[1]), new SelectItem(palavragerada[2], palavragerada[2]), new SelectItem(palavragerada[3], palavragerada[3])});
                }
                if (palavragerada.length == 5) {
                    selectItemGroup.setSelectItems(new SelectItem[]{new SelectItem(palavragerada[0], palavragerada[0]), new SelectItem(palavragerada[1], palavragerada[1]), new SelectItem(palavragerada[2], palavragerada[2]), new SelectItem(palavragerada[3], palavragerada[3]), new SelectItem(palavragerada[4], palavragerada[4])});
                }
                if (palavragerada.length == 6) {
                    selectItemGroup.setSelectItems(new SelectItem[]{new SelectItem(palavragerada[0], palavragerada[0]), new SelectItem(palavragerada[1], palavragerada[1]), new SelectItem(palavragerada[2], palavragerada[2]), new SelectItem(palavragerada[3], palavragerada[3]), new SelectItem(palavragerada[4], palavragerada[4]), new SelectItem(palavragerada[5], palavragerada[5])});
                }
                if (palavragerada.length == 7) {
                    selectItemGroup.setSelectItems(new SelectItem[]{new SelectItem(palavragerada[0], palavragerada[0]), new SelectItem(palavragerada[1], palavragerada[1]), new SelectItem(palavragerada[2], palavragerada[2]), new SelectItem(palavragerada[3], palavragerada[3]), new SelectItem(palavragerada[4], palavragerada[4]), new SelectItem(palavragerada[5], palavragerada[5]), new SelectItem(palavragerada[6], palavragerada[6])});
                }
                if (palavragerada.length == 8) {
                    selectItemGroup.setSelectItems(new SelectItem[]{new SelectItem(palavragerada[0], palavragerada[0]), new SelectItem(palavragerada[1], palavragerada[1]), new SelectItem(palavragerada[2], palavragerada[2]), new SelectItem(palavragerada[3], palavragerada[3]), new SelectItem(palavragerada[4], palavragerada[4]), new SelectItem(palavragerada[5], palavragerada[5]), new SelectItem(palavragerada[6], palavragerada[6]), new SelectItem(palavragerada[7], palavragerada[7])});
                }
                if (palavragerada.length == 9) {
                    selectItemGroup.setSelectItems(new SelectItem[]{new SelectItem(palavragerada[0], palavragerada[0]), new SelectItem(palavragerada[1], palavragerada[1]), new SelectItem(palavragerada[2], palavragerada[2]), new SelectItem(palavragerada[3], palavragerada[3]), new SelectItem(palavragerada[4], palavragerada[4]), new SelectItem(palavragerada[5], palavragerada[5]), new SelectItem(palavragerada[6], palavragerada[6]), new SelectItem(palavragerada[7], palavragerada[7]), new SelectItem(palavragerada[8], palavragerada[8])});
                }
                if (palavragerada.length == 10) {
                    selectItemGroup.setSelectItems(new SelectItem[]{new SelectItem(palavragerada[0], palavragerada[0]), new SelectItem(palavragerada[1], palavragerada[1]), new SelectItem(palavragerada[2], palavragerada[2]), new SelectItem(palavragerada[3], palavragerada[3]), new SelectItem(palavragerada[4], palavragerada[4]), new SelectItem(palavragerada[5], palavragerada[5]), new SelectItem(palavragerada[6], palavragerada[6]), new SelectItem(palavragerada[7], palavragerada[7]), new SelectItem(palavragerada[8], palavragerada[8]), new SelectItem(palavragerada[9], palavragerada[9])});
                }
                if (palavragerada.length == 11) {
                    selectItemGroup.setSelectItems(new SelectItem[]{new SelectItem(palavragerada[0], palavragerada[0]), new SelectItem(palavragerada[1], palavragerada[1]), new SelectItem(palavragerada[2], palavragerada[2]), new SelectItem(palavragerada[3], palavragerada[3]), new SelectItem(palavragerada[4], palavragerada[4]), new SelectItem(palavragerada[5], palavragerada[5]), new SelectItem(palavragerada[6], palavragerada[6]), new SelectItem(palavragerada[7], palavragerada[7]), new SelectItem(palavragerada[8], palavragerada[8]), new SelectItem(palavragerada[9], palavragerada[9]), new SelectItem(palavragerada[10], palavragerada[10])});
                }
                if (palavragerada.length == 12) {
                    selectItemGroup.setSelectItems(new SelectItem[]{new SelectItem(palavragerada[0], palavragerada[0]), new SelectItem(palavragerada[1], palavragerada[1]), new SelectItem(palavragerada[2], palavragerada[2]), new SelectItem(palavragerada[3], palavragerada[3]), new SelectItem(palavragerada[4], palavragerada[4]), new SelectItem(palavragerada[5], palavragerada[5]), new SelectItem(palavragerada[6], palavragerada[6]), new SelectItem(palavragerada[7], palavragerada[7]), new SelectItem(palavragerada[8], palavragerada[8]), new SelectItem(palavragerada[9], palavragerada[9]), new SelectItem(palavragerada[10], palavragerada[10]), new SelectItem(palavragerada[11], palavragerada[11])});
                }
                if (palavragerada.length == 13) {
                    selectItemGroup.setSelectItems(new SelectItem[]{new SelectItem(palavragerada[0], palavragerada[0]), new SelectItem(palavragerada[1], palavragerada[1]), new SelectItem(palavragerada[2], palavragerada[2]), new SelectItem(palavragerada[3], palavragerada[3]), new SelectItem(palavragerada[4], palavragerada[4]), new SelectItem(palavragerada[5], palavragerada[5]), new SelectItem(palavragerada[6], palavragerada[6]), new SelectItem(palavragerada[7], palavragerada[7]), new SelectItem(palavragerada[8], palavragerada[8]), new SelectItem(palavragerada[9], palavragerada[9]), new SelectItem(palavragerada[10], palavragerada[10]), new SelectItem(palavragerada[11], palavragerada[11]), new SelectItem(palavragerada[12], palavragerada[12])});
                }
                if (palavragerada.length == 14) {
                    selectItemGroup.setSelectItems(new SelectItem[]{new SelectItem(palavragerada[0], palavragerada[0]), new SelectItem(palavragerada[1], palavragerada[1]), new SelectItem(palavragerada[2], palavragerada[2]), new SelectItem(palavragerada[3], palavragerada[3]), new SelectItem(palavragerada[4], palavragerada[4]), new SelectItem(palavragerada[5], palavragerada[5]), new SelectItem(palavragerada[6], palavragerada[6]), new SelectItem(palavragerada[7], palavragerada[7]), new SelectItem(palavragerada[8], palavragerada[8]), new SelectItem(palavragerada[9], palavragerada[9]), new SelectItem(palavragerada[10], palavragerada[10]), new SelectItem(palavragerada[11], palavragerada[11]), new SelectItem(palavragerada[12], palavragerada[12]), new SelectItem(palavragerada[13], palavragerada[13])});
                }
                if (palavragerada.length == 15) {
                    selectItemGroup.setSelectItems(new SelectItem[]{new SelectItem(palavragerada[0], palavragerada[0]), new SelectItem(palavragerada[1], palavragerada[1]), new SelectItem(palavragerada[2], palavragerada[2]), new SelectItem(palavragerada[3], palavragerada[3]), new SelectItem(palavragerada[4], palavragerada[4]), new SelectItem(palavragerada[5], palavragerada[5]), new SelectItem(palavragerada[6], palavragerada[6]), new SelectItem(palavragerada[7], palavragerada[7]), new SelectItem(palavragerada[8], palavragerada[8]), new SelectItem(palavragerada[9], palavragerada[9]), new SelectItem(palavragerada[10], palavragerada[10]), new SelectItem(palavragerada[11], palavragerada[11]), new SelectItem(palavragerada[12], palavragerada[12]), new SelectItem(palavragerada[13], palavragerada[13]), new SelectItem(palavragerada[14], palavragerada[14])});
                }
                if (palavragerada.length == 16) {
                    selectItemGroup.setSelectItems(new SelectItem[]{new SelectItem(palavragerada[0], palavragerada[0]), new SelectItem(palavragerada[1], palavragerada[1]), new SelectItem(palavragerada[2], palavragerada[2]), new SelectItem(palavragerada[3], palavragerada[3]), new SelectItem(palavragerada[4], palavragerada[4]), new SelectItem(palavragerada[5], palavragerada[5]), new SelectItem(palavragerada[6], palavragerada[6]), new SelectItem(palavragerada[7], palavragerada[7]), new SelectItem(palavragerada[8], palavragerada[8]), new SelectItem(palavragerada[9], palavragerada[9]), new SelectItem(palavragerada[10], palavragerada[10]), new SelectItem(palavragerada[11], palavragerada[11]), new SelectItem(palavragerada[12], palavragerada[12]), new SelectItem(palavragerada[13], palavragerada[13]), new SelectItem(palavragerada[14], palavragerada[14]), new SelectItem(palavragerada[15], palavragerada[15])});
                }
                if (palavragerada.length == 17) {
                    selectItemGroup.setSelectItems(new SelectItem[]{new SelectItem(palavragerada[0], palavragerada[0]), new SelectItem(palavragerada[1], palavragerada[1]), new SelectItem(palavragerada[2], palavragerada[2]), new SelectItem(palavragerada[3], palavragerada[3]), new SelectItem(palavragerada[4], palavragerada[4]), new SelectItem(palavragerada[5], palavragerada[5]), new SelectItem(palavragerada[6], palavragerada[6]), new SelectItem(palavragerada[7], palavragerada[7]), new SelectItem(palavragerada[8], palavragerada[8]), new SelectItem(palavragerada[9], palavragerada[9]), new SelectItem(palavragerada[10], palavragerada[10]), new SelectItem(palavragerada[11], palavragerada[11]), new SelectItem(palavragerada[12], palavragerada[12]), new SelectItem(palavragerada[13], palavragerada[13]), new SelectItem(palavragerada[14], palavragerada[14]), new SelectItem(palavragerada[15], palavragerada[15]), new SelectItem(palavragerada[16], palavragerada[16])});
                }
                if (palavragerada.length == 18) {
                    selectItemGroup.setSelectItems(new SelectItem[]{new SelectItem(palavragerada[0], palavragerada[0]), new SelectItem(palavragerada[1], palavragerada[1]), new SelectItem(palavragerada[2], palavragerada[2]), new SelectItem(palavragerada[3], palavragerada[3]), new SelectItem(palavragerada[4], palavragerada[4]), new SelectItem(palavragerada[5], palavragerada[5]), new SelectItem(palavragerada[6], palavragerada[6]), new SelectItem(palavragerada[7], palavragerada[7]), new SelectItem(palavragerada[8], palavragerada[8]), new SelectItem(palavragerada[9], palavragerada[9]), new SelectItem(palavragerada[10], palavragerada[10]), new SelectItem(palavragerada[11], palavragerada[11]), new SelectItem(palavragerada[12], palavragerada[12]), new SelectItem(palavragerada[13], palavragerada[13]), new SelectItem(palavragerada[14], palavragerada[14]), new SelectItem(palavragerada[15], palavragerada[15]), new SelectItem(palavragerada[16], palavragerada[16]), new SelectItem(palavragerada[17], palavragerada[17])});
                }
                if (palavragerada.length == 19) {
                    selectItemGroup.setSelectItems(new SelectItem[]{new SelectItem(palavragerada[0], palavragerada[0]), new SelectItem(palavragerada[1], palavragerada[1]), new SelectItem(palavragerada[2], palavragerada[2]), new SelectItem(palavragerada[3], palavragerada[3]), new SelectItem(palavragerada[4], palavragerada[4]), new SelectItem(palavragerada[5], palavragerada[5]), new SelectItem(palavragerada[6], palavragerada[6]), new SelectItem(palavragerada[7], palavragerada[7]), new SelectItem(palavragerada[8], palavragerada[8]), new SelectItem(palavragerada[9], palavragerada[9]), new SelectItem(palavragerada[10], palavragerada[10]), new SelectItem(palavragerada[11], palavragerada[11]), new SelectItem(palavragerada[12], palavragerada[12]), new SelectItem(palavragerada[13], palavragerada[13]), new SelectItem(palavragerada[14], palavragerada[14]), new SelectItem(palavragerada[15], palavragerada[15]), new SelectItem(palavragerada[16], palavragerada[16]), new SelectItem(palavragerada[17], palavragerada[17]), new SelectItem(palavragerada[18], palavragerada[18])});
                }
                if (palavragerada.length == 20) {
                    selectItemGroup.setSelectItems(new SelectItem[]{new SelectItem(palavragerada[0], palavragerada[0]), new SelectItem(palavragerada[1], palavragerada[1]), new SelectItem(palavragerada[2], palavragerada[2]), new SelectItem(palavragerada[3], palavragerada[3]), new SelectItem(palavragerada[4], palavragerada[4]), new SelectItem(palavragerada[5], palavragerada[5]), new SelectItem(palavragerada[6], palavragerada[6]), new SelectItem(palavragerada[7], palavragerada[7]), new SelectItem(palavragerada[8], palavragerada[8]), new SelectItem(palavragerada[9], palavragerada[9]), new SelectItem(palavragerada[10], palavragerada[10]), new SelectItem(palavragerada[11], palavragerada[11]), new SelectItem(palavragerada[12], palavragerada[12]), new SelectItem(palavragerada[13], palavragerada[13]), new SelectItem(palavragerada[14], palavragerada[14]), new SelectItem(palavragerada[15], palavragerada[15]), new SelectItem(palavragerada[16], palavragerada[16]), new SelectItem(palavragerada[17], palavragerada[17]), new SelectItem(palavragerada[18], palavragerada[18]), new SelectItem(palavragerada[19], palavragerada[19])});
                }
                if (palavragerada.length == 21) {
                    selectItemGroup.setSelectItems(new SelectItem[]{new SelectItem(palavragerada[0], palavragerada[0]), new SelectItem(palavragerada[1], palavragerada[1]), new SelectItem(palavragerada[2], palavragerada[2]), new SelectItem(palavragerada[3], palavragerada[3]), new SelectItem(palavragerada[4], palavragerada[4]), new SelectItem(palavragerada[5], palavragerada[5]), new SelectItem(palavragerada[6], palavragerada[6]), new SelectItem(palavragerada[7], palavragerada[7]), new SelectItem(palavragerada[8], palavragerada[8]), new SelectItem(palavragerada[9], palavragerada[9]), new SelectItem(palavragerada[10], palavragerada[10]), new SelectItem(palavragerada[11], palavragerada[11]), new SelectItem(palavragerada[12], palavragerada[12]), new SelectItem(palavragerada[13], palavragerada[13]), new SelectItem(palavragerada[14], palavragerada[14]), new SelectItem(palavragerada[15], palavragerada[15]), new SelectItem(palavragerada[16], palavragerada[16]), new SelectItem(palavragerada[17], palavragerada[17]), new SelectItem(palavragerada[18], palavragerada[18]), new SelectItem(palavragerada[19], palavragerada[19]), new SelectItem(palavragerada[20], palavragerada[20])});
                }
                if (palavragerada.length == 22) {
                    selectItemGroup.setSelectItems(new SelectItem[]{new SelectItem(palavragerada[0], palavragerada[0]), new SelectItem(palavragerada[1], palavragerada[1]), new SelectItem(palavragerada[2], palavragerada[2]), new SelectItem(palavragerada[3], palavragerada[3]), new SelectItem(palavragerada[4], palavragerada[4]), new SelectItem(palavragerada[5], palavragerada[5]), new SelectItem(palavragerada[6], palavragerada[6]), new SelectItem(palavragerada[7], palavragerada[7]), new SelectItem(palavragerada[8], palavragerada[8]), new SelectItem(palavragerada[9], palavragerada[9]), new SelectItem(palavragerada[10], palavragerada[10]), new SelectItem(palavragerada[11], palavragerada[11]), new SelectItem(palavragerada[12], palavragerada[12]), new SelectItem(palavragerada[13], palavragerada[13]), new SelectItem(palavragerada[14], palavragerada[14]), new SelectItem(palavragerada[15], palavragerada[15]), new SelectItem(palavragerada[16], palavragerada[16]), new SelectItem(palavragerada[17], palavragerada[17]), new SelectItem(palavragerada[18], palavragerada[18]), new SelectItem(palavragerada[19], palavragerada[19]), new SelectItem(palavragerada[20], palavragerada[20]), new SelectItem(palavragerada[21], palavragerada[21])});
                }
                if (palavragerada.length == 23) {
                    selectItemGroup.setSelectItems(new SelectItem[]{new SelectItem(palavragerada[0], palavragerada[0]), new SelectItem(palavragerada[1], palavragerada[1]), new SelectItem(palavragerada[2], palavragerada[2]), new SelectItem(palavragerada[3], palavragerada[3]), new SelectItem(palavragerada[4], palavragerada[4]), new SelectItem(palavragerada[5], palavragerada[5]), new SelectItem(palavragerada[6], palavragerada[6]), new SelectItem(palavragerada[7], palavragerada[7]), new SelectItem(palavragerada[8], palavragerada[8]), new SelectItem(palavragerada[9], palavragerada[9]), new SelectItem(palavragerada[10], palavragerada[10]), new SelectItem(palavragerada[11], palavragerada[11]), new SelectItem(palavragerada[12], palavragerada[12]), new SelectItem(palavragerada[13], palavragerada[13]), new SelectItem(palavragerada[14], palavragerada[14]), new SelectItem(palavragerada[15], palavragerada[15]), new SelectItem(palavragerada[16], palavragerada[16]), new SelectItem(palavragerada[17], palavragerada[17]), new SelectItem(palavragerada[18], palavragerada[18]), new SelectItem(palavragerada[19], palavragerada[19]), new SelectItem(palavragerada[20], palavragerada[20]), new SelectItem(palavragerada[21], palavragerada[21]), new SelectItem(palavragerada[22], palavragerada[22])});
                }
                if (palavragerada.length == 24) {
                    selectItemGroup.setSelectItems(new SelectItem[]{new SelectItem(palavragerada[0], palavragerada[0]), new SelectItem(palavragerada[1], palavragerada[1]), new SelectItem(palavragerada[2], palavragerada[2]), new SelectItem(palavragerada[3], palavragerada[3]), new SelectItem(palavragerada[4], palavragerada[4]), new SelectItem(palavragerada[5], palavragerada[5]), new SelectItem(palavragerada[6], palavragerada[6]), new SelectItem(palavragerada[7], palavragerada[7]), new SelectItem(palavragerada[8], palavragerada[8]), new SelectItem(palavragerada[9], palavragerada[9]), new SelectItem(palavragerada[10], palavragerada[10]), new SelectItem(palavragerada[11], palavragerada[11]), new SelectItem(palavragerada[12], palavragerada[12]), new SelectItem(palavragerada[13], palavragerada[13]), new SelectItem(palavragerada[14], palavragerada[14]), new SelectItem(palavragerada[15], palavragerada[15]), new SelectItem(palavragerada[16], palavragerada[16]), new SelectItem(palavragerada[17], palavragerada[17]), new SelectItem(palavragerada[18], palavragerada[18]), new SelectItem(palavragerada[19], palavragerada[19]), new SelectItem(palavragerada[20], palavragerada[20]), new SelectItem(palavragerada[21], palavragerada[21]), new SelectItem(palavragerada[22], palavragerada[22]), new SelectItem(palavragerada[23], palavragerada[23])});
                }
                if (palavragerada.length == 25) {
                    selectItemGroup.setSelectItems(new SelectItem[]{new SelectItem(palavragerada[0], palavragerada[0]), new SelectItem(palavragerada[1], palavragerada[1]), new SelectItem(palavragerada[2], palavragerada[2]), new SelectItem(palavragerada[3], palavragerada[3]), new SelectItem(palavragerada[4], palavragerada[4]), new SelectItem(palavragerada[5], palavragerada[5]), new SelectItem(palavragerada[6], palavragerada[6]), new SelectItem(palavragerada[7], palavragerada[7]), new SelectItem(palavragerada[8], palavragerada[8]), new SelectItem(palavragerada[9], palavragerada[9]), new SelectItem(palavragerada[10], palavragerada[10]), new SelectItem(palavragerada[11], palavragerada[11]), new SelectItem(palavragerada[12], palavragerada[12]), new SelectItem(palavragerada[13], palavragerada[13]), new SelectItem(palavragerada[14], palavragerada[14]), new SelectItem(palavragerada[15], palavragerada[15]), new SelectItem(palavragerada[16], palavragerada[16]), new SelectItem(palavragerada[17], palavragerada[17]), new SelectItem(palavragerada[18], palavragerada[18]), new SelectItem(palavragerada[19], palavragerada[19]), new SelectItem(palavragerada[20], palavragerada[20]), new SelectItem(palavragerada[21], palavragerada[21]), new SelectItem(palavragerada[22], palavragerada[22]), new SelectItem(palavragerada[23], palavragerada[23]), new SelectItem(palavragerada[24], palavragerada[24])});
                }
                listaagrupada.add(selectItemGroup);
            }

        } catch (SQLException ex) {
            logPrincipal(MonitorChamadaClienteDAO.class).error(">>>>ERROR AO LISTAR SERVICO EM MONITORCHAMADACLIENTE(listarTodasSiglasAgrupadas)", ex);
        } finally {
            rs.close();
            abrirconexao.fecharConexao();
        }
        return listaagrupada;
    }

    /**
     * Método com a característica de retornar uma listagem agrupada de cada um
     * das siglas do serviço; Obtendo a palavragerada e o agrupando os valores
     * por posições, bem como a informação sobre o serviço a ser informado, isto
     * contido através do usuário
     *
     * @param id
     * @return id
     * @throws java.sql.SQLException
     */
    public List<SelectItem> listarTodasSiglasPorServicodoUsuarioAgrupadas(Short id) throws SQLException {
        List<SelectItem> listaagrupada = new ArrayList<>();

        try {
            strBuffer = new StringBuffer().append("SELECT array_agg (distinct triagemalternativa.siglatriagemalternativa) as sigla\n"
                    + "           ,servico.nomeservico\n"
                    + "     FROM  projetosga.usuario usuario\n"
                    + "     INNER JOIN projetosga.usuarioservico usuarioservico on (usuarioservico.idusuario = usuario.idusuario)\n"
                    + "	    INNER JOIN projetosga.servico servico on (usuarioservico.idservico = servico.idservico)\n"
                    + "     INNER JOIN projetosga.triagemalternativa triagemalternativa on (servico.idservico = triagemalternativa.idservico)\n"
                    + "     WHERE  usuario.idusuario=? AND triagemalternativa.statustriagemalternativa='A'\n"
                    + "     GROUP BY servico.nomeservico");
            pstm = abrirconexao.getConexao().prepareStatement(strBuffer.toString());
            pstm.setObject(1, id);
            rs = pstm.executeQuery();

            while (rs.next()) {
                TriagemChamadaClienteTransfer triagemChamadaClienteTransfer = new TriagemChamadaClienteTransfer();

                String palavrageradaaux = String.valueOf(rs.getObject("sigla"))
                        .replaceAll("\\{|\\}", "")
                        .replaceAll("\"", "");
                String palavragerada[] = palavrageradaaux.split(",");
                //triagemChamadaClienteTransfer.setSiglatriagemalternativa(rs.getString("sigla"));
                triagemChamadaClienteTransfer.getServicotransfer().setNomeservico(rs.getString("nomeservico"));
                SelectItemGroup selectItemGroup = new SelectItemGroup(triagemChamadaClienteTransfer.getServicotransfer().getNomeservico());
                if (palavragerada.length == 1) {
                    selectItemGroup.setSelectItems(new SelectItem[]{new SelectItem(palavragerada[0], palavragerada[0])});
                }
                if (palavragerada.length == 2) {
                    selectItemGroup.setSelectItems(new SelectItem[]{new SelectItem(palavragerada[0], palavragerada[0]), new SelectItem(palavragerada[1], palavragerada[1])});
                }
                if (palavragerada.length == 3) {
                    selectItemGroup.setSelectItems(new SelectItem[]{new SelectItem(palavragerada[0], palavragerada[0]), new SelectItem(palavragerada[1], palavragerada[1]), new SelectItem(palavragerada[2], palavragerada[2])});
                }
                if (palavragerada.length == 4) {
                    selectItemGroup.setSelectItems(new SelectItem[]{new SelectItem(palavragerada[0], palavragerada[0]), new SelectItem(palavragerada[1], palavragerada[1]), new SelectItem(palavragerada[2], palavragerada[2]), new SelectItem(palavragerada[3], palavragerada[3])});
                }
                if (palavragerada.length == 5) {
                    selectItemGroup.setSelectItems(new SelectItem[]{new SelectItem(palavragerada[0], palavragerada[0]), new SelectItem(palavragerada[1], palavragerada[1]), new SelectItem(palavragerada[2], palavragerada[2]), new SelectItem(palavragerada[3], palavragerada[3]), new SelectItem(palavragerada[4], palavragerada[4])});
                }
                if (palavragerada.length == 6) {
                    selectItemGroup.setSelectItems(new SelectItem[]{new SelectItem(palavragerada[0], palavragerada[0]), new SelectItem(palavragerada[1], palavragerada[1]), new SelectItem(palavragerada[2], palavragerada[2]), new SelectItem(palavragerada[3], palavragerada[3]), new SelectItem(palavragerada[4], palavragerada[4]), new SelectItem(palavragerada[5], palavragerada[5])});
                }
                if (palavragerada.length == 7) {
                    selectItemGroup.setSelectItems(new SelectItem[]{new SelectItem(palavragerada[0], palavragerada[0]), new SelectItem(palavragerada[1], palavragerada[1]), new SelectItem(palavragerada[2], palavragerada[2]), new SelectItem(palavragerada[3], palavragerada[3]), new SelectItem(palavragerada[4], palavragerada[4]), new SelectItem(palavragerada[5], palavragerada[5]), new SelectItem(palavragerada[6], palavragerada[6])});
                }
                if (palavragerada.length == 8) {
                    selectItemGroup.setSelectItems(new SelectItem[]{new SelectItem(palavragerada[0], palavragerada[0]), new SelectItem(palavragerada[1], palavragerada[1]), new SelectItem(palavragerada[2], palavragerada[2]), new SelectItem(palavragerada[3], palavragerada[3]), new SelectItem(palavragerada[4], palavragerada[4]), new SelectItem(palavragerada[5], palavragerada[5]), new SelectItem(palavragerada[6], palavragerada[6]), new SelectItem(palavragerada[7], palavragerada[7])});
                }
                if (palavragerada.length == 9) {
                    selectItemGroup.setSelectItems(new SelectItem[]{new SelectItem(palavragerada[0], palavragerada[0]), new SelectItem(palavragerada[1], palavragerada[1]), new SelectItem(palavragerada[2], palavragerada[2]), new SelectItem(palavragerada[3], palavragerada[3]), new SelectItem(palavragerada[4], palavragerada[4]), new SelectItem(palavragerada[5], palavragerada[5]), new SelectItem(palavragerada[6], palavragerada[6]), new SelectItem(palavragerada[7], palavragerada[7]), new SelectItem(palavragerada[8], palavragerada[8])});
                }
                if (palavragerada.length == 10) {
                    selectItemGroup.setSelectItems(new SelectItem[]{new SelectItem(palavragerada[0], palavragerada[0]), new SelectItem(palavragerada[1], palavragerada[1]), new SelectItem(palavragerada[2], palavragerada[2]), new SelectItem(palavragerada[3], palavragerada[3]), new SelectItem(palavragerada[4], palavragerada[4]), new SelectItem(palavragerada[5], palavragerada[5]), new SelectItem(palavragerada[6], palavragerada[6]), new SelectItem(palavragerada[7], palavragerada[7]), new SelectItem(palavragerada[8], palavragerada[8]), new SelectItem(palavragerada[9], palavragerada[9])});
                }
                if (palavragerada.length == 11) {
                    selectItemGroup.setSelectItems(new SelectItem[]{new SelectItem(palavragerada[0], palavragerada[0]), new SelectItem(palavragerada[1], palavragerada[1]), new SelectItem(palavragerada[2], palavragerada[2]), new SelectItem(palavragerada[3], palavragerada[3]), new SelectItem(palavragerada[4], palavragerada[4]), new SelectItem(palavragerada[5], palavragerada[5]), new SelectItem(palavragerada[6], palavragerada[6]), new SelectItem(palavragerada[7], palavragerada[7]), new SelectItem(palavragerada[8], palavragerada[8]), new SelectItem(palavragerada[9], palavragerada[9]), new SelectItem(palavragerada[10], palavragerada[10])});
                }
                if (palavragerada.length == 12) {
                    selectItemGroup.setSelectItems(new SelectItem[]{new SelectItem(palavragerada[0], palavragerada[0]), new SelectItem(palavragerada[1], palavragerada[1]), new SelectItem(palavragerada[2], palavragerada[2]), new SelectItem(palavragerada[3], palavragerada[3]), new SelectItem(palavragerada[4], palavragerada[4]), new SelectItem(palavragerada[5], palavragerada[5]), new SelectItem(palavragerada[6], palavragerada[6]), new SelectItem(palavragerada[7], palavragerada[7]), new SelectItem(palavragerada[8], palavragerada[8]), new SelectItem(palavragerada[9], palavragerada[9]), new SelectItem(palavragerada[10], palavragerada[10]), new SelectItem(palavragerada[11], palavragerada[11])});
                }
                if (palavragerada.length == 13) {
                    selectItemGroup.setSelectItems(new SelectItem[]{new SelectItem(palavragerada[0], palavragerada[0]), new SelectItem(palavragerada[1], palavragerada[1]), new SelectItem(palavragerada[2], palavragerada[2]), new SelectItem(palavragerada[3], palavragerada[3]), new SelectItem(palavragerada[4], palavragerada[4]), new SelectItem(palavragerada[5], palavragerada[5]), new SelectItem(palavragerada[6], palavragerada[6]), new SelectItem(palavragerada[7], palavragerada[7]), new SelectItem(palavragerada[8], palavragerada[8]), new SelectItem(palavragerada[9], palavragerada[9]), new SelectItem(palavragerada[10], palavragerada[10]), new SelectItem(palavragerada[11], palavragerada[11]), new SelectItem(palavragerada[12], palavragerada[12])});
                }
                if (palavragerada.length == 14) {
                    selectItemGroup.setSelectItems(new SelectItem[]{new SelectItem(palavragerada[0], palavragerada[0]), new SelectItem(palavragerada[1], palavragerada[1]), new SelectItem(palavragerada[2], palavragerada[2]), new SelectItem(palavragerada[3], palavragerada[3]), new SelectItem(palavragerada[4], palavragerada[4]), new SelectItem(palavragerada[5], palavragerada[5]), new SelectItem(palavragerada[6], palavragerada[6]), new SelectItem(palavragerada[7], palavragerada[7]), new SelectItem(palavragerada[8], palavragerada[8]), new SelectItem(palavragerada[9], palavragerada[9]), new SelectItem(palavragerada[10], palavragerada[10]), new SelectItem(palavragerada[11], palavragerada[11]), new SelectItem(palavragerada[12], palavragerada[12]), new SelectItem(palavragerada[13], palavragerada[13])});
                }
                if (palavragerada.length == 15) {
                    selectItemGroup.setSelectItems(new SelectItem[]{new SelectItem(palavragerada[0], palavragerada[0]), new SelectItem(palavragerada[1], palavragerada[1]), new SelectItem(palavragerada[2], palavragerada[2]), new SelectItem(palavragerada[3], palavragerada[3]), new SelectItem(palavragerada[4], palavragerada[4]), new SelectItem(palavragerada[5], palavragerada[5]), new SelectItem(palavragerada[6], palavragerada[6]), new SelectItem(palavragerada[7], palavragerada[7]), new SelectItem(palavragerada[8], palavragerada[8]), new SelectItem(palavragerada[9], palavragerada[9]), new SelectItem(palavragerada[10], palavragerada[10]), new SelectItem(palavragerada[11], palavragerada[11]), new SelectItem(palavragerada[12], palavragerada[12]), new SelectItem(palavragerada[13], palavragerada[13]), new SelectItem(palavragerada[14], palavragerada[14])});
                }
                if (palavragerada.length == 16) {
                    selectItemGroup.setSelectItems(new SelectItem[]{new SelectItem(palavragerada[0], palavragerada[0]), new SelectItem(palavragerada[1], palavragerada[1]), new SelectItem(palavragerada[2], palavragerada[2]), new SelectItem(palavragerada[3], palavragerada[3]), new SelectItem(palavragerada[4], palavragerada[4]), new SelectItem(palavragerada[5], palavragerada[5]), new SelectItem(palavragerada[6], palavragerada[6]), new SelectItem(palavragerada[7], palavragerada[7]), new SelectItem(palavragerada[8], palavragerada[8]), new SelectItem(palavragerada[9], palavragerada[9]), new SelectItem(palavragerada[10], palavragerada[10]), new SelectItem(palavragerada[11], palavragerada[11]), new SelectItem(palavragerada[12], palavragerada[12]), new SelectItem(palavragerada[13], palavragerada[13]), new SelectItem(palavragerada[14], palavragerada[14]), new SelectItem(palavragerada[15], palavragerada[15])});
                }
                if (palavragerada.length == 17) {
                    selectItemGroup.setSelectItems(new SelectItem[]{new SelectItem(palavragerada[0], palavragerada[0]), new SelectItem(palavragerada[1], palavragerada[1]), new SelectItem(palavragerada[2], palavragerada[2]), new SelectItem(palavragerada[3], palavragerada[3]), new SelectItem(palavragerada[4], palavragerada[4]), new SelectItem(palavragerada[5], palavragerada[5]), new SelectItem(palavragerada[6], palavragerada[6]), new SelectItem(palavragerada[7], palavragerada[7]), new SelectItem(palavragerada[8], palavragerada[8]), new SelectItem(palavragerada[9], palavragerada[9]), new SelectItem(palavragerada[10], palavragerada[10]), new SelectItem(palavragerada[11], palavragerada[11]), new SelectItem(palavragerada[12], palavragerada[12]), new SelectItem(palavragerada[13], palavragerada[13]), new SelectItem(palavragerada[14], palavragerada[14]), new SelectItem(palavragerada[15], palavragerada[15]), new SelectItem(palavragerada[16], palavragerada[16])});
                }
                if (palavragerada.length == 18) {
                    selectItemGroup.setSelectItems(new SelectItem[]{new SelectItem(palavragerada[0], palavragerada[0]), new SelectItem(palavragerada[1], palavragerada[1]), new SelectItem(palavragerada[2], palavragerada[2]), new SelectItem(palavragerada[3], palavragerada[3]), new SelectItem(palavragerada[4], palavragerada[4]), new SelectItem(palavragerada[5], palavragerada[5]), new SelectItem(palavragerada[6], palavragerada[6]), new SelectItem(palavragerada[7], palavragerada[7]), new SelectItem(palavragerada[8], palavragerada[8]), new SelectItem(palavragerada[9], palavragerada[9]), new SelectItem(palavragerada[10], palavragerada[10]), new SelectItem(palavragerada[11], palavragerada[11]), new SelectItem(palavragerada[12], palavragerada[12]), new SelectItem(palavragerada[13], palavragerada[13]), new SelectItem(palavragerada[14], palavragerada[14]), new SelectItem(palavragerada[15], palavragerada[15]), new SelectItem(palavragerada[16], palavragerada[16]), new SelectItem(palavragerada[17], palavragerada[17])});
                }
                if (palavragerada.length == 19) {
                    selectItemGroup.setSelectItems(new SelectItem[]{new SelectItem(palavragerada[0], palavragerada[0]), new SelectItem(palavragerada[1], palavragerada[1]), new SelectItem(palavragerada[2], palavragerada[2]), new SelectItem(palavragerada[3], palavragerada[3]), new SelectItem(palavragerada[4], palavragerada[4]), new SelectItem(palavragerada[5], palavragerada[5]), new SelectItem(palavragerada[6], palavragerada[6]), new SelectItem(palavragerada[7], palavragerada[7]), new SelectItem(palavragerada[8], palavragerada[8]), new SelectItem(palavragerada[9], palavragerada[9]), new SelectItem(palavragerada[10], palavragerada[10]), new SelectItem(palavragerada[11], palavragerada[11]), new SelectItem(palavragerada[12], palavragerada[12]), new SelectItem(palavragerada[13], palavragerada[13]), new SelectItem(palavragerada[14], palavragerada[14]), new SelectItem(palavragerada[15], palavragerada[15]), new SelectItem(palavragerada[16], palavragerada[16]), new SelectItem(palavragerada[17], palavragerada[17]), new SelectItem(palavragerada[18], palavragerada[18])});
                }
                if (palavragerada.length == 20) {
                    selectItemGroup.setSelectItems(new SelectItem[]{new SelectItem(palavragerada[0], palavragerada[0]), new SelectItem(palavragerada[1], palavragerada[1]), new SelectItem(palavragerada[2], palavragerada[2]), new SelectItem(palavragerada[3], palavragerada[3]), new SelectItem(palavragerada[4], palavragerada[4]), new SelectItem(palavragerada[5], palavragerada[5]), new SelectItem(palavragerada[6], palavragerada[6]), new SelectItem(palavragerada[7], palavragerada[7]), new SelectItem(palavragerada[8], palavragerada[8]), new SelectItem(palavragerada[9], palavragerada[9]), new SelectItem(palavragerada[10], palavragerada[10]), new SelectItem(palavragerada[11], palavragerada[11]), new SelectItem(palavragerada[12], palavragerada[12]), new SelectItem(palavragerada[13], palavragerada[13]), new SelectItem(palavragerada[14], palavragerada[14]), new SelectItem(palavragerada[15], palavragerada[15]), new SelectItem(palavragerada[16], palavragerada[16]), new SelectItem(palavragerada[17], palavragerada[17]), new SelectItem(palavragerada[18], palavragerada[18]), new SelectItem(palavragerada[19], palavragerada[19])});
                }
                if (palavragerada.length == 21) {
                    selectItemGroup.setSelectItems(new SelectItem[]{new SelectItem(palavragerada[0], palavragerada[0]), new SelectItem(palavragerada[1], palavragerada[1]), new SelectItem(palavragerada[2], palavragerada[2]), new SelectItem(palavragerada[3], palavragerada[3]), new SelectItem(palavragerada[4], palavragerada[4]), new SelectItem(palavragerada[5], palavragerada[5]), new SelectItem(palavragerada[6], palavragerada[6]), new SelectItem(palavragerada[7], palavragerada[7]), new SelectItem(palavragerada[8], palavragerada[8]), new SelectItem(palavragerada[9], palavragerada[9]), new SelectItem(palavragerada[10], palavragerada[10]), new SelectItem(palavragerada[11], palavragerada[11]), new SelectItem(palavragerada[12], palavragerada[12]), new SelectItem(palavragerada[13], palavragerada[13]), new SelectItem(palavragerada[14], palavragerada[14]), new SelectItem(palavragerada[15], palavragerada[15]), new SelectItem(palavragerada[16], palavragerada[16]), new SelectItem(palavragerada[17], palavragerada[17]), new SelectItem(palavragerada[18], palavragerada[18]), new SelectItem(palavragerada[19], palavragerada[19]), new SelectItem(palavragerada[20], palavragerada[20])});
                }
                if (palavragerada.length == 22) {
                    selectItemGroup.setSelectItems(new SelectItem[]{new SelectItem(palavragerada[0], palavragerada[0]), new SelectItem(palavragerada[1], palavragerada[1]), new SelectItem(palavragerada[2], palavragerada[2]), new SelectItem(palavragerada[3], palavragerada[3]), new SelectItem(palavragerada[4], palavragerada[4]), new SelectItem(palavragerada[5], palavragerada[5]), new SelectItem(palavragerada[6], palavragerada[6]), new SelectItem(palavragerada[7], palavragerada[7]), new SelectItem(palavragerada[8], palavragerada[8]), new SelectItem(palavragerada[9], palavragerada[9]), new SelectItem(palavragerada[10], palavragerada[10]), new SelectItem(palavragerada[11], palavragerada[11]), new SelectItem(palavragerada[12], palavragerada[12]), new SelectItem(palavragerada[13], palavragerada[13]), new SelectItem(palavragerada[14], palavragerada[14]), new SelectItem(palavragerada[15], palavragerada[15]), new SelectItem(palavragerada[16], palavragerada[16]), new SelectItem(palavragerada[17], palavragerada[17]), new SelectItem(palavragerada[18], palavragerada[18]), new SelectItem(palavragerada[19], palavragerada[19]), new SelectItem(palavragerada[20], palavragerada[20]), new SelectItem(palavragerada[21], palavragerada[21])});
                }
                if (palavragerada.length == 23) {
                    selectItemGroup.setSelectItems(new SelectItem[]{new SelectItem(palavragerada[0], palavragerada[0]), new SelectItem(palavragerada[1], palavragerada[1]), new SelectItem(palavragerada[2], palavragerada[2]), new SelectItem(palavragerada[3], palavragerada[3]), new SelectItem(palavragerada[4], palavragerada[4]), new SelectItem(palavragerada[5], palavragerada[5]), new SelectItem(palavragerada[6], palavragerada[6]), new SelectItem(palavragerada[7], palavragerada[7]), new SelectItem(palavragerada[8], palavragerada[8]), new SelectItem(palavragerada[9], palavragerada[9]), new SelectItem(palavragerada[10], palavragerada[10]), new SelectItem(palavragerada[11], palavragerada[11]), new SelectItem(palavragerada[12], palavragerada[12]), new SelectItem(palavragerada[13], palavragerada[13]), new SelectItem(palavragerada[14], palavragerada[14]), new SelectItem(palavragerada[15], palavragerada[15]), new SelectItem(palavragerada[16], palavragerada[16]), new SelectItem(palavragerada[17], palavragerada[17]), new SelectItem(palavragerada[18], palavragerada[18]), new SelectItem(palavragerada[19], palavragerada[19]), new SelectItem(palavragerada[20], palavragerada[20]), new SelectItem(palavragerada[21], palavragerada[21]), new SelectItem(palavragerada[22], palavragerada[22])});
                }
                if (palavragerada.length == 24) {
                    selectItemGroup.setSelectItems(new SelectItem[]{new SelectItem(palavragerada[0], palavragerada[0]), new SelectItem(palavragerada[1], palavragerada[1]), new SelectItem(palavragerada[2], palavragerada[2]), new SelectItem(palavragerada[3], palavragerada[3]), new SelectItem(palavragerada[4], palavragerada[4]), new SelectItem(palavragerada[5], palavragerada[5]), new SelectItem(palavragerada[6], palavragerada[6]), new SelectItem(palavragerada[7], palavragerada[7]), new SelectItem(palavragerada[8], palavragerada[8]), new SelectItem(palavragerada[9], palavragerada[9]), new SelectItem(palavragerada[10], palavragerada[10]), new SelectItem(palavragerada[11], palavragerada[11]), new SelectItem(palavragerada[12], palavragerada[12]), new SelectItem(palavragerada[13], palavragerada[13]), new SelectItem(palavragerada[14], palavragerada[14]), new SelectItem(palavragerada[15], palavragerada[15]), new SelectItem(palavragerada[16], palavragerada[16]), new SelectItem(palavragerada[17], palavragerada[17]), new SelectItem(palavragerada[18], palavragerada[18]), new SelectItem(palavragerada[19], palavragerada[19]), new SelectItem(palavragerada[20], palavragerada[20]), new SelectItem(palavragerada[21], palavragerada[21]), new SelectItem(palavragerada[22], palavragerada[22]), new SelectItem(palavragerada[23], palavragerada[23])});
                }
                if (palavragerada.length == 25) {
                    selectItemGroup.setSelectItems(new SelectItem[]{new SelectItem(palavragerada[0], palavragerada[0]), new SelectItem(palavragerada[1], palavragerada[1]), new SelectItem(palavragerada[2], palavragerada[2]), new SelectItem(palavragerada[3], palavragerada[3]), new SelectItem(palavragerada[4], palavragerada[4]), new SelectItem(palavragerada[5], palavragerada[5]), new SelectItem(palavragerada[6], palavragerada[6]), new SelectItem(palavragerada[7], palavragerada[7]), new SelectItem(palavragerada[8], palavragerada[8]), new SelectItem(palavragerada[9], palavragerada[9]), new SelectItem(palavragerada[10], palavragerada[10]), new SelectItem(palavragerada[11], palavragerada[11]), new SelectItem(palavragerada[12], palavragerada[12]), new SelectItem(palavragerada[13], palavragerada[13]), new SelectItem(palavragerada[14], palavragerada[14]), new SelectItem(palavragerada[15], palavragerada[15]), new SelectItem(palavragerada[16], palavragerada[16]), new SelectItem(palavragerada[17], palavragerada[17]), new SelectItem(palavragerada[18], palavragerada[18]), new SelectItem(palavragerada[19], palavragerada[19]), new SelectItem(palavragerada[20], palavragerada[20]), new SelectItem(palavragerada[21], palavragerada[21]), new SelectItem(palavragerada[22], palavragerada[22]), new SelectItem(palavragerada[23], palavragerada[23]), new SelectItem(palavragerada[24], palavragerada[24])});
                }
                listaagrupada.add(selectItemGroup);
            }

        } catch (SQLException ex) {
            logPrincipal(MonitorChamadaClienteDAO.class).error(">>>>ERROR AO LISTAR SERVICO EM MONITORCHAMADACLIENTE(listarTodasSiglasAgrupadas)", ex);
        } finally {
            rs.close();
            abrirconexao.fecharConexao();
        }
        return listaagrupada;
    }

    public List<TriagemChamadaClienteTransfer> listarTodasSiglas() throws SQLException {
        List<TriagemChamadaClienteTransfer> siglaTransfers = new ArrayList<TriagemChamadaClienteTransfer>();
        try {
            strBuffer = new StringBuffer().append("SELECT triagemalternativa.idtriagemalternativa\n"
                    + "                                                       ,triagemalternativa.siglatriagemalternativa"
                    + "                                                       ,triagemalternativa.statustriagemalternativa"
                    + "                                                       ,servico.nomeservico"
                    + "                       from projetosga.triagemalternativa triagemalternativa\n"
                    + "                       inner join\n"
                    + "                       projetosga.servico servico on (servico.idservico = triagemalternativa.idservico)\n"
                    + "                       WHERE triagemalternativa.statustriagemalternativa='A'\n"
                    + "                       ORDER BY triagemalternativa.siglatriagemalternativa");
            pstm = abrirconexao.getConexao().prepareStatement(strBuffer.toString());
            rs = pstm.executeQuery();
            while (rs.next()) {
                TriagemChamadaClienteTransfer triagemChamadaClienteTransfer = new TriagemChamadaClienteTransfer();
                triagemChamadaClienteTransfer.setStatustriagemalternativa(rs.getString("statustriagemalternativa"));
                if (triagemChamadaClienteTransfer.getStatustriagemalternativa().equalsIgnoreCase("A")) {
                    triagemChamadaClienteTransfer.setId(rs.getShort("idtriagemalternativa"));
                    triagemChamadaClienteTransfer.setSiglatriagemalternativa(rs.getString("siglatriagemalternativa"));
                    triagemChamadaClienteTransfer.getServicotransfer().setNomeservico(rs.getString("nomeservico"));
                }
                siglaTransfers.add(triagemChamadaClienteTransfer);
            }
        } catch (SQLException ex) {
            logPrincipal(MonitorChamadaClienteDAO.class).error(">>>>ERROR AO LISTAR SERVICO EM MONITORCHAMADACLIENTE(listarTodosServico)", ex);
        } finally {
            rs.close();
            abrirconexao.fecharConexao();
        }
        return siglaTransfers;
    }

    /**
     * Metodo responsavel por ALTERAR A SENHA DO CLIENTE PARA INATIVO
     *
     * @param nometriagemachamadacliente
     * @param nomeusuario
     * @return
     * @throws java.sql.SQLException
     */
    public boolean atualizarCancelarSenhaMonitorTriagemChamadaCliente(String nometriagemachamadacliente, String nomeusuario) throws SQLException {

        try {
            StringBuffer strBufferSenha = new StringBuffer().append("UPDATE projetosga.triagemchamadacliente\n"
                    + "   SET statustriagemchamadacliente='I'"
                    + "      ,emitirsenhatriagemchamadacliente ='SENHA CANCELADA'"
                    + "      ,usuariotriagemchamadacliente=?"
                    + "      ,datafimtriagemachamadacliente=?\n"
                    + "  WHERE nometriagemachamadacliente=?;");
            pstm = abrirconexao.getConexao().prepareStatement(strBufferSenha.toString());
            pstm.setObject(1, nomeusuario);
            //data cancelada da senha
            pstm.setObject(2, new Util().getSqlTimeStamp(new Util().getPegaDataAtual()));
            pstm.setObject(3, nometriagemachamadacliente);
            pstm.executeUpdate();
        } catch (SQLException ex) {
            logPrincipal(MonitorChamadaClienteDAO.class).error(">>>>ERROR AO CANCELAR TRIAGEMCHAMADACLIENTEMONITOR(deletarMonitorTriagemChamadaCliente)", ex);
        } finally {

            pstm.close();
            abrirconexao.fecharConexao();
        }
        return true || false;
    }

}
