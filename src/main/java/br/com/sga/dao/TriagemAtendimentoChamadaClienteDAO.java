/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.sga.dao;

import br.com.sga.transfer.CargoTransfer;
import br.com.sga.transfer.LocalTransfer;
import br.com.sga.transfer.ServicoTransfer;
import br.com.sga.transfer.TriagemAtendimentoClienteTransfer;
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
public class TriagemAtendimentoChamadaClienteDAO extends DAO {

    /**
     * Metodo com a caracteristica de retornar uma lista de Dados contendo os
     * dados para a obtencao de atendimento ao cliente
     *
     *
     * @param String local
     * @param String servico
     * @return List<TriagemAtendimentoClienteTransfer>
     * @throws java.sql.SQLException
     */
    public List<TriagemAtendimentoClienteTransfer> buscarTriagemAtendimentoChamadaClienteLocaleServico(String nomelocal, String nomeservico) throws SQLException {

        List<TriagemAtendimentoClienteTransfer> triagematendimentochamadaclientetransfers = new ArrayList<TriagemAtendimentoClienteTransfer>();
        PreparedStatement pstmbuscarlocaleservico = null;
        ResultSet rsbuscarlocaleservico = null;
        try {
            StringBuffer triagematendimentochamadaclientelocaleservico = new StringBuffer().append("SELECT	distinct(triagemchamadacliente.nometriagemachamadacliente)"
                    + "                                                                                                                         ,triagemchamadacliente.idservico\n"
                    + "                                                                                                                         ,servico.nomeservico\n"
                    + "																,triagemchamadacliente.idtriagemachamadacliente\n"
                    + "																,local.idlocal\n"
                    + "																,local.nomelocal\n"
                    + "																,triagemchamadacliente.prioridadetriagemachamadacliente\n"
                    + "																,triagemchamadacliente.normaltriagemachamadacliente\n"
                    + "																,triagemalternativa.siglatriagemalternativa\n"
                    + "																,triagemchamadacliente.datainiciotriagemachamadacliente\n"
                    + "                                                                                                                      FROM       projetosga.triagemalternativa triagemalternativa\n"
                    + "                                                                                                                      INNER JOIN projetosga.servico servico on (servico.idservico = triagemalternativa.idservico)\n"
                    + "                                                                                                                      INNER JOIN projetosga.triagemchamadacliente triagemchamadacliente on(triagemchamadacliente.idtriagemalternativa = triagemalternativa.idtriagemalternativa)\n"
                    + "                                                                                                                      INNER JOIN projetosga.local local on (local.idlocal = triagemalternativa.idlocal)\n"
                    + "                     											             INNER JOIN projetosga.usuarioservico usuarioservico on (usuarioservico.idservico = servico.idservico)\n"
                    + "                     											             INNER JOIN projetosga.usuario usuario on (usuarioservico.idusuario = usuario.idusuario)\n"
                    + "                                                                                                                      WHERE ((local.nomelocal = ? and servico.nomeservico = ?) \n"
                    + "															and (triagemchamadacliente.statustriagemchamadacliente ='A')\n"
                    + "															)\n"
                    + "                                                                                                         		ORDER BY      servico.nomeservico\n"
                    + "                                                                                                                                   ,triagemchamadacliente.datainiciotriagemachamadacliente");
            pstmbuscarlocaleservico = abrirconexao.getConexao().prepareStatement(triagematendimentochamadaclientelocaleservico.toString());
            pstmbuscarlocaleservico.setObject(1, nomelocal);
            pstmbuscarlocaleservico.setObject(2, nomeservico);

            rsbuscarlocaleservico = pstmbuscarlocaleservico.executeQuery();
            while (rsbuscarlocaleservico.next()) {

                //cria instacnia/variavel local atendimentochamadaclientetransfer
                TriagemAtendimentoClienteTransfer atendimentochamadaclientetransfer = new TriagemAtendimentoClienteTransfer();

                //idservico
                atendimentochamadaclientetransfer.setId(rsbuscarlocaleservico.getShort("idservico"));
                //nomeservico
                atendimentochamadaclientetransfer.getServicotransfer().setNomeservico(rsbuscarlocaleservico.getString("nomeservico"));
                //idtriagemachamadacliente                
                atendimentochamadaclientetransfer.setId(rsbuscarlocaleservico.getShort("idtriagemachamadacliente"));
                //idlocal
                atendimentochamadaclientetransfer.getLocaltransfer().setId(rsbuscarlocaleservico.getShort("idlocal"));
                //nomelocal
                atendimentochamadaclientetransfer.getLocaltransfer().setNomelocal(rsbuscarlocaleservico.getString("nomelocal"));

                //nometriagemachamadacliente
                atendimentochamadaclientetransfer.setNometriagemachamadacliente(rsbuscarlocaleservico.getString("nometriagemachamadacliente").toUpperCase());
                //cor do nome da senha, com a primeira posicao
                String cornomesenha = atendimentochamadaclientetransfer.getNometriagemachamadacliente().substring(0, 1);
                if (cornomesenha.equalsIgnoreCase("P")) {
                    atendimentochamadaclientetransfer.setCormonitorsenha("Red");
                    atendimentochamadaclientetransfer.setPrioridadetriagemachamadacliente(rsbuscarlocaleservico.getString("prioridadetriagemachamadacliente"));
                } else {
                    //definir prioridades
                    atendimentochamadaclientetransfer.setPrioridadetriagemachamadacliente(rsbuscarlocaleservico.getString("normaltriagemachamadacliente"));
                    atendimentochamadaclientetransfer.setCormonitorsenha("Blue");
                }
                //sigla
                atendimentochamadaclientetransfer.setSiglatriagemalternativa(rsbuscarlocaleservico.getString("siglatriagemalternativa"));
                //data chegada
                atendimentochamadaclientetransfer.setDatainiciotriagemchamadacliente(rsbuscarlocaleservico.getTimestamp("datainiciotriagemachamadacliente"));

                //adicionar na lista
                triagematendimentochamadaclientetransfers.add(atendimentochamadaclientetransfer);
            }
        } catch (SQLException ex) {
            logPrincipal(TriagemAtendimentoChamadaClienteDAO.class).error(">>>>ERROR AO BUSCAR POR TRIAGEMATENDIMENOCHAMADACLIENTE(buscarTriagemAtendimentoChamadaClienteLocaleServico)", ex);
        } finally {
            pstmbuscarlocaleservico.close();
            rsbuscarlocaleservico.close();
            abrirconexao.fecharConexao();
        }
        return triagematendimentochamadaclientetransfers;
    }

    /**
     * Metodo com a caracteristica de retornar uma lista de Dados contendo os
     * dados para a obtencao de atendimento ao cliente
     *
     *
     * @param String local
     * @param String servico
     * @return List<TriagemAtendimentoClienteTransfer>
     * @throws java.sql.SQLException
     */
    public List<TriagemAtendimentoClienteTransfer> buscarTriagemAtendimentoChamadaClienteLocaleServicoeCliente(String nomelocal, String nomeservico, String nomedousuario) throws SQLException {

        List<TriagemAtendimentoClienteTransfer> triagematendimentochamadaclientetransfers = new ArrayList<TriagemAtendimentoClienteTransfer>();
        PreparedStatement pstmbuscarlocaleservico = null;
        ResultSet rsbuscarlocaleservico = null;
        try {
            StringBuffer triagematendimentochamadaclientelocaleservico = new StringBuffer().append("															SELECT	triagemchamadacliente.idservico\n"
                    + "																,servico.nomeservico\n"
                    + "																,triagemchamadacliente.idtriagemachamadacliente\n"
                    + "																,local.idlocal\n"
                    + "																,local.nomelocal\n"
                    + "																,triagemchamadacliente.prioridadetriagemachamadacliente\n"
                    + "																,triagemchamadacliente.nometriagemachamadacliente\n"
                    + "																,triagemchamadacliente.normaltriagemachamadacliente\n"
                    + "																,triagemalternativa.siglatriagemalternativa\n"
                    + "																,triagemchamadacliente.datainiciotriagemachamadacliente\n"
                    + "                                                                                                                      FROM 	 projetosga.triagemalternativa triagemalternativa\n"
                    + "                                                                                                                      INNER JOIN projetosga.servico servico on (servico.idservico = triagemalternativa.idservico)\n"
                    + "                                                                                                                      INNER JOIN projetosga.triagemchamadacliente triagemchamadacliente on(triagemchamadacliente.idtriagemalternativa = triagemalternativa.idtriagemalternativa)\n"
                    + "                                                                                                                      INNER JOIN projetosga.local local on (local.idlocal = triagemalternativa.idlocal)\n"
                    + "                     											              INNER JOIN projetosga.usuarioservico usuarioservico on (usuarioservico.idservico = servico.idservico)\n"
                    + "                     											              INNER JOIN projetosga.usuario usuario on (usuarioservico.idusuario = usuario.idusuario)\n"
                    + "                                                                                                                      WHERE ((local.nomelocal = ? and servico.nomeservico = ?) \n"
                    + "															and (triagemchamadacliente.statustriagemchamadacliente ='A')\n"
                    + "															and (usuario.nomeusuario= ?) and (usuario.statususuario='A') and (servico.statusservico='A'))\n"
                    + "                                                                                                         		ORDER BY      servico.nomeservico\n"
                    + "                                                                                                                                   ,triagemchamadacliente.datainiciotriagemachamadacliente");
            pstmbuscarlocaleservico = abrirconexao.getConexao().prepareStatement(triagematendimentochamadaclientelocaleservico.toString());
            pstmbuscarlocaleservico.setObject(1, nomelocal);
            pstmbuscarlocaleservico.setObject(2, nomeservico);
            pstmbuscarlocaleservico.setObject(3, nomedousuario);
            rsbuscarlocaleservico = pstmbuscarlocaleservico.executeQuery();
            while (rsbuscarlocaleservico.next()) {

                //cria instacnia/variavel local atendimentochamadaclientetransfer
                TriagemAtendimentoClienteTransfer atendimentochamadaclientetransfer = new TriagemAtendimentoClienteTransfer();

                //idservico
                atendimentochamadaclientetransfer.setId(rsbuscarlocaleservico.getShort("idservico"));
                //nomeservico
                atendimentochamadaclientetransfer.getServicotransfer().setNomeservico(rsbuscarlocaleservico.getString("nomeservico"));
                //idtriagemachamadacliente                
                atendimentochamadaclientetransfer.setId(rsbuscarlocaleservico.getShort("idtriagemachamadacliente"));
                //idlocal
                atendimentochamadaclientetransfer.getLocaltransfer().setId(rsbuscarlocaleservico.getShort("idlocal"));
                //nomelocal
                atendimentochamadaclientetransfer.getLocaltransfer().setNomelocal(rsbuscarlocaleservico.getString("nomelocal"));

                //nometriagemachamadacliente
                atendimentochamadaclientetransfer.setNometriagemachamadacliente(rsbuscarlocaleservico.getString("nometriagemachamadacliente").toUpperCase());
                //cor do nome da senha, com a primeira posicao
                String cornomesenha = atendimentochamadaclientetransfer.getNometriagemachamadacliente().substring(0, 1);
                if (cornomesenha.equalsIgnoreCase("P")) {
                    atendimentochamadaclientetransfer.setCormonitorsenha("Red");
                    atendimentochamadaclientetransfer.setPrioridadetriagemachamadacliente(rsbuscarlocaleservico.getString("prioridadetriagemachamadacliente"));
                } else {
                    //definir prioridades
                    atendimentochamadaclientetransfer.setPrioridadetriagemachamadacliente(rsbuscarlocaleservico.getString("normaltriagemachamadacliente"));
                    atendimentochamadaclientetransfer.setCormonitorsenha("Blue");
                }
                //sigla
                atendimentochamadaclientetransfer.setSiglatriagemalternativa(rsbuscarlocaleservico.getString("siglatriagemalternativa"));
                //data chegada
                atendimentochamadaclientetransfer.setDatainiciotriagemchamadacliente(rsbuscarlocaleservico.getTimestamp("datainiciotriagemachamadacliente"));

                //adicionar na lista
                triagematendimentochamadaclientetransfers.add(atendimentochamadaclientetransfer);
            }
        } catch (SQLException ex) {
            logPrincipal(TriagemAtendimentoChamadaClienteDAO.class).error(">>>>ERROR AO BUSCAR POR TRIAGEMATENDIMENOCHAMADACLIENTE(buscarTriagemAtendimentoChamadaClienteLocaleServico)", ex);
        } finally {
            pstmbuscarlocaleservico.close();
            rsbuscarlocaleservico.close();
            abrirconexao.fecharConexao();
        }
        return triagematendimentochamadaclientetransfers;
    }

    /**
     * Metodo com a caracteristica de retornar uma lista de Dados contendo os
     * dados para a obtencao de atendimento ao cliente
     *
     *
     * @return List<TriagemAtendimentoClienteTransfer>
     * @throws java.sql.SQLException
     */
    public List<TriagemAtendimentoClienteTransfer> listarTodosTriagemAtendimentoChamadaClienteLocaleServico() throws SQLException {

        List<TriagemAtendimentoClienteTransfer> triagematendimentochamadaclientetransfers = new ArrayList<TriagemAtendimentoClienteTransfer>();
        PreparedStatement pstmbuscarlocaleservico = null;
        ResultSet rsbuscarlocaleservico = null;
        try {
            StringBuffer triagematendimentochamadaclientelocaleservico = new StringBuffer().append("SELECT	distinct(triagemchamadacliente.nometriagemachamadacliente)\n"
                    + "																		, triagemchamadacliente.idservico\n"
                    + "                     																,servico.nomeservico\n"
                    + "                     																,triagemchamadacliente.idtriagemachamadacliente\n"
                    + "                     																,local.idlocal\n"
                    + "                     																,local.nomelocal\n"
                    + "                     																,triagemchamadacliente.prioridadetriagemachamadacliente\n"
                    + "                     																\n"
                    + "                     																,triagemchamadacliente.normaltriagemachamadacliente\n"
                    + "                     																,triagemalternativa.siglatriagemalternativa\n"
                    + "                     																,triagemchamadacliente.datainiciotriagemachamadacliente\n"
                    + "                                                                                                                                           FROM 	 projetosga.triagemalternativa triagemalternativa\n"
                    + "                                                                                                                                           INNER JOIN projetosga.servico servico on (servico.idservico = triagemalternativa.idservico)\n"
                    + "                                                                                                                                           INNER JOIN projetosga.triagemchamadacliente triagemchamadacliente on(triagemchamadacliente.idtriagemalternativa = triagemalternativa.idtriagemalternativa)\n"
                    + "                                                                                                                                           INNER JOIN projetosga.local local on (local.idlocal = triagemalternativa.idlocal)\n"
                    + "                                          											              INNER JOIN projetosga.usuarioservico usuarioservico on (usuarioservico.idservico = servico.idservico)\n"
                    + "                                          											              INNER JOIN projetosga.usuario usuario on (usuarioservico.idusuario = usuario.idusuario)\n"
                    + "                                                                                                                                           WHERE (triagemchamadacliente.statustriagemchamadacliente ='A')\n"
                    + "                                                                                                                              		ORDER BY      servico.nomeservico\n"
                    + "                                                                                                                                                        ,triagemchamadacliente.datainiciotriagemachamadacliente");
            pstmbuscarlocaleservico = abrirconexao.getConexao().prepareStatement(triagematendimentochamadaclientelocaleservico.toString());
            rsbuscarlocaleservico = pstmbuscarlocaleservico.executeQuery();
            while (rsbuscarlocaleservico.next()) {

                //cria instacnia/variavel local atendimentochamadaclientetransfer
                TriagemAtendimentoClienteTransfer atendimentochamadaclientetransfer = new TriagemAtendimentoClienteTransfer();

                //idservico
                atendimentochamadaclientetransfer.setId(rsbuscarlocaleservico.getShort("idservico"));
                //nomeservico
                atendimentochamadaclientetransfer.getServicotransfer().setNomeservico(rsbuscarlocaleservico.getString("nomeservico"));
                //idtriagemachamadacliente                
                atendimentochamadaclientetransfer.setId(rsbuscarlocaleservico.getShort("idtriagemachamadacliente"));
                //idlocal
                atendimentochamadaclientetransfer.getLocaltransfer().setId(rsbuscarlocaleservico.getShort("idlocal"));
                //nomelocal
                atendimentochamadaclientetransfer.getLocaltransfer().setNomelocal(rsbuscarlocaleservico.getString("nomelocal"));

                //nometriagemachamadacliente
                atendimentochamadaclientetransfer.setNometriagemachamadacliente(rsbuscarlocaleservico.getString("nometriagemachamadacliente").toUpperCase());
                //cor do nome da senha, com a primeira posicao
                String cornomesenha = atendimentochamadaclientetransfer.getNometriagemachamadacliente().substring(0, 1);
                if (cornomesenha.equalsIgnoreCase("P")) {
                    atendimentochamadaclientetransfer.setCormonitorsenha("Red");
                    atendimentochamadaclientetransfer.setPrioridadetriagemachamadacliente(rsbuscarlocaleservico.getString("prioridadetriagemachamadacliente"));
                } else {
                    //definir prioridades
                    atendimentochamadaclientetransfer.setPrioridadetriagemachamadacliente(rsbuscarlocaleservico.getString("normaltriagemachamadacliente"));
                    atendimentochamadaclientetransfer.setCormonitorsenha("Blue");
                }
                //sigla
                atendimentochamadaclientetransfer.setSiglatriagemalternativa(rsbuscarlocaleservico.getString("siglatriagemalternativa"));
                //data chegada
                atendimentochamadaclientetransfer.setDatainiciotriagemchamadacliente(rsbuscarlocaleservico.getTimestamp("datainiciotriagemachamadacliente"));

                //adicionar na lista
                triagematendimentochamadaclientetransfers.add(atendimentochamadaclientetransfer);
            }
        } catch (SQLException ex) {
            logPrincipal(TriagemAtendimentoChamadaClienteDAO.class).error(">>>>ERROR AO BUSCAR POR TRIAGEMATENDIMENOCHAMADACLIENTE(listarTodosTriagemAtendimentoChamadaClienteLocaleServico)", ex);
        } finally {
            pstmbuscarlocaleservico.close();
            rsbuscarlocaleservico.close();
            abrirconexao.fecharConexao();
        }
        return triagematendimentochamadaclientetransfers;
    }

    /**
     * Metodo com a caracteristica de retornar uma lista de Dados contendo os
     * dados para a obtencao de atendimento ao cliente
     *
     *
     * @param String local
     * @param String servico
     * @return List<TriagemAtendimentoClienteTransfer>
     * @throws java.sql.SQLException
     */
    public boolean existeTriagemAtendimentoChamadaClienteLocaleServico(String nomelocal, String nomeservico, String nomedeusuario) throws SQLException {

        PreparedStatement pstmbuscarlocaleservico = null;
        ResultSet rsbuscarlocaleservico = null;
        try {
            StringBuffer triagematendimentochamadaclientelocaleservico = new StringBuffer().append("															SELECT	\n"
                    + "																servico.nomeservico\n"
                    + "																,local.nomelocal\n"
                    + "														            FROM 	 projetosga.triagemalternativa triagemalternativa\n"
                    + "                                                                                                                      INNER JOIN projetosga.servico servico on (servico.idservico = triagemalternativa.idservico)\n"
                    + "                                                                                                                      INNER JOIN projetosga.triagemchamadacliente triagemchamadacliente on(triagemchamadacliente.idtriagemalternativa = triagemalternativa.idtriagemalternativa)\n"
                    + "                                                                                                                      INNER JOIN projetosga.local local on (local.idlocal = triagemalternativa.idlocal)\n"
                    + "                     											              INNER JOIN projetosga.usuarioservico usuarioservico on (usuarioservico.idservico = servico.idservico)\n"
                    + "                     											              INNER JOIN projetosga.usuario usuario on (usuarioservico.idusuario = usuario.idusuario)\n"
                    + "                                                                                                                      WHERE ((local.nomelocal = ? and servico.nomeservico = ?) \n"
                    + "															and (triagemchamadacliente.statustriagemchamadacliente ='A')\n"
                    + "															and usuario.nomeusuario= ?)\n"
                    + "                                                                                                         		ORDER BY      servico.nomeservico\n"
                    + "                                                                                                                                   ,triagemchamadacliente.datainiciotriagemachamadacliente");
            pstmbuscarlocaleservico = abrirconexao.getConexao().prepareStatement(triagematendimentochamadaclientelocaleservico.toString());
            pstmbuscarlocaleservico.setObject(1, nomelocal);
            pstmbuscarlocaleservico.setObject(2, nomeservico);
            pstmbuscarlocaleservico.setObject(3, nomedeusuario);
            rsbuscarlocaleservico = pstmbuscarlocaleservico.executeQuery();
            if (rsbuscarlocaleservico.next()) {
                return true;
            }
        } catch (SQLException ex) {
            logPrincipal(TriagemAtendimentoChamadaClienteDAO.class).error(">>>>ERROR AO BUSCAR POR EXISTENCIA DE LOCAL E SERVICO TRIAGEMATENDIMENOCHAMADACLIENTE(existeTriagemAtendimentoChamadaClienteLocaleServico)", ex);
        } finally {
            pstmbuscarlocaleservico.close();
            rsbuscarlocaleservico.close();
            abrirconexao.fecharConexao();
        }
        return false;
    }

    /**
     * Metodo com a caracteristica de retornar uma lista de Dados contendo os
     * dados para a obtencao de atendimento ao cliente com o nome do local,
     * prioridade, nome da triagem/senha/numero de senha,local,numero do local
     *
     *
     * @return List<TriagemAtendimentoClienteTransfer>
     * @throws java.sql.SQLException
     */
    public List<TriagemAtendimentoClienteTransfer> listarTriagemAtendimentoChamadaClienteAtendido() throws SQLException {

        List<TriagemAtendimentoClienteTransfer> triagematendimentochamadaclientetransfers = new ArrayList<TriagemAtendimentoClienteTransfer>();
        PreparedStatement pstmbuscarclienteatendido = null;
        ResultSet rsbuscarclienteatendido = null;
        try {
            StringBuffer triagematendimentochamadaclientelocaleservico = new StringBuffer().append("SELECT      local.nomelocal\n"
                    + "                                                                                         ,triagemchamadacliente.prioridadetriagemachamadacliente\n"
                    + "                                                                                         ,triagemchamadacliente.nometriagemachamadacliente\n"
                    + "                                                                                         ,triagemchamadacliente.datafimtriagemachamadacliente"
                    + "                                                                                         ,triagemchamadacliente.numerodolocaltriagemachamadacliente\n"
                    + "                                                                                         ,triagemchamadacliente.normaltriagemachamadacliente\n"
                    + "                                                                                                 FROM 	      projetosga.triagemalternativa triagemalternativa\n"
                    + "                                                                                                 INNER JOIN\n"
                    + "                                                                                                               projetosga.servico servico on (servico.idservico = triagemalternativa.idservico)\n"
                    + "                                                                                                 INNER JOIN\n"
                    + "                                                                                                               projetosga.triagemchamadacliente triagemchamadacliente on(triagemchamadacliente.idtriagemalternativa = triagemalternativa.idtriagemalternativa)\n"
                    + "                                                                                                 INNER JOIN\n"
                    + "														      projetosga.local local on (local.idlocal = triagemalternativa.idlocal)\n"
                    + "                                                                                                 WHERE  ((triagemchamadacliente.statustriagemchamadacliente ='D') \n"
                    + "                                                                                                              OR     triagemchamadacliente.statustriagemchamadacliente  ='N')\n"
                    + "                                                                                    		ORDER BY      triagemchamadacliente.datafimtriagemachamadacliente"
                    + "                                                                                          DESC"
                    + "                                                                                          LIMIT 6");
            pstmbuscarclienteatendido = abrirconexao.getConexao().prepareStatement(triagematendimentochamadaclientelocaleservico.toString());
            rsbuscarclienteatendido = pstmbuscarclienteatendido.executeQuery();
            while (rsbuscarclienteatendido.next()) {
                //cria instacnia/variavel local atendimentochamadaclientetransfer
                TriagemAtendimentoClienteTransfer atendimentochamadaclientetransfer = new TriagemAtendimentoClienteTransfer();
                //nomelocal
                atendimentochamadaclientetransfer.getLocaltransfer().setNomelocal(rsbuscarclienteatendido.getString("nomelocal"));
                //nometriagemachamadacliente
                atendimentochamadaclientetransfer.setNometriagemachamadacliente(rsbuscarclienteatendido.getString("nometriagemachamadacliente").toUpperCase());
                //definir prioridades
                String prioridadeounormal = atendimentochamadaclientetransfer.getNometriagemachamadacliente().substring(0, 1);
                if (prioridadeounormal.equalsIgnoreCase("P")) {
                    atendimentochamadaclientetransfer.setCormonitorsenha("Red");
                    atendimentochamadaclientetransfer.setPrioridadetriagemachamadacliente(rsbuscarclienteatendido.getString("prioridadetriagemachamadacliente"));
                } else {
                    atendimentochamadaclientetransfer.setCormonitorsenha("Blue");
                    atendimentochamadaclientetransfer.setPrioridadetriagemachamadacliente(rsbuscarclienteatendido.getString("normaltriagemachamadacliente"));
                }
                //numero do local
                atendimentochamadaclientetransfer.setNumerodolocal(rsbuscarclienteatendido.getInt("numerodolocaltriagemachamadacliente"));
                //data fim
                atendimentochamadaclientetransfer.setDatafimtriagemchamadacliente(rsbuscarclienteatendido.getTimestamp("datafimtriagemachamadacliente"));
                //adicionar na lista
                triagematendimentochamadaclientetransfers.add(atendimentochamadaclientetransfer);
            }
        } catch (SQLException ex) {
            logPrincipal(TriagemAtendimentoChamadaClienteDAO.class).error(">>>>ERROR AO BUSCAR POR TRIAGEMATENDIMENOCHAMADACLIENTE(buscarTriagemAtendimentoChamadaClienteLocaleServico)", ex);
        } finally {
            pstmbuscarclienteatendido.close();
            rsbuscarclienteatendido.close();
            abrirconexao.fecharConexao();
        }
        return triagematendimentochamadaclientetransfers;
    }

    /**
     * Metodo com a caracteristica de retornar uma lista de Dados contendo os
     * dados do servico ativos para a obtencao do local de atendimento.
     *
     *
     * @return List<TriagemAtendimentoClienteTransfer>
     * @throws java.sql.SQLException
     */
    public List<ServicoTransfer> listarTriagemAtendimentoChamadaClienteAtendidoServico() throws SQLException {

        List<ServicoTransfer> triagematendimentochamadaclientetransfers = new ArrayList<ServicoTransfer>();
        PreparedStatement pstmbuscarclienteatendidoservico = null;
        ResultSet rsbuscarclienteatendidoservico = null;
        try {
            StringBuffer triagematendimentochamadaclienteservicolocal = new StringBuffer().append(" SELECT distinct(servico.nomeservico)\n"
                    + "                                          FROM projetosga.usuario usuario\n"
                    + "                                          INNER JOIN\n"
                    + "                                          projetosga.usuarioservico usuarioservico on (usuarioservico.idusuario = usuario.idusuario)\n"
                    + "                                          INNER JOIN\n"
                    + "                                          projetosga.servico servico on(servico.idservico = usuarioservico.idservico)\n"
                    + "                                          INNER JOIN\n"
                    + "                                          projetosga.triagemalternativa triagemalternativa on (triagemalternativa.idservico = servico.idservico)\n"
                    + "                                          WHERE ((servico.statusservico='A'))\n"
                    + "                                          ORDER BY servico.nomeservico");
            pstmbuscarclienteatendidoservico = abrirconexao.getConexao().prepareStatement(triagematendimentochamadaclienteservicolocal.toString());
            //pstmbuscarclienteatendido.setObject(1, usuario);
            rsbuscarclienteatendidoservico = pstmbuscarclienteatendidoservico.executeQuery();
            while (rsbuscarclienteatendidoservico.next()) {
                ServicoTransfer servicotransfer = new ServicoTransfer();
                servicotransfer.setNomeservico(rsbuscarclienteatendidoservico.getString("nomeservico"));
                triagematendimentochamadaclientetransfers.add(servicotransfer);
            }
        } catch (SQLException ex) {
            logPrincipal(TriagemAtendimentoChamadaClienteDAO.class).error(">>>>ERROR AO BUSCAR POR TRIAGEMATENDIMENOCHAMADACLIENTE(listarTriagemAtendimentoChamadaClienteAtendidoServico)", ex);
        } finally {
            pstmbuscarclienteatendidoservico.close();
            rsbuscarclienteatendidoservico.close();
            abrirconexao.fecharConexao();
        }
        return triagematendimentochamadaclientetransfers;
    }

    /**
     * Metodo com a caracteristica de retornar uma lista de Dados contendo os
     * dados do servico para a obtencao do local de atendimento atraves do
     * usuario que estiver logado e vinculado ao seu servico ativo
     *
     *
     * @return List<TriagemAtendimentoClienteTransfer>
     * @throws java.sql.SQLException
     */
    public List<ServicoTransfer> listarTriagemAtendimentoChamadaClienteAtendidoLocalcomServicoparaUsuario(String usuario) throws SQLException {

        List<ServicoTransfer> triagematendimentochamadaclientetransfers = new ArrayList<ServicoTransfer>();
        PreparedStatement pstmbuscarclienteatendidolocaleservico = null;
        ResultSet rsbuscarclienteatendidolocaleservico = null;
        try {
            StringBuffer triagematendimentochamadaclienteservico = new StringBuffer().append(" SELECT distinct(servico.nomeservico)\n"
                    + "                     FROM projetosga.usuario usuario\n"
                    + "                     INNER JOIN\n"
                    + "                     projetosga.usuarioservico usuarioservico on (usuarioservico.idusuario = usuario.idusuario)\n"
                    + "                     INNER JOIN\n"
                    + "                     projetosga.servico servico on(servico.idservico = usuarioservico.idservico)\n"
                    + "                     INNER JOIN\n"
                    + "                     projetosga.triagemalternativa triagemalternativa on (triagemalternativa.idservico = servico.idservico)\n"
                    + "                     WHERE ((usuario.nomeusuario=?) and (servico.statusservico='A'))\n"
                    + "                     ORDER BY servico.nomeservico");
            pstmbuscarclienteatendidolocaleservico = abrirconexao.getConexao().prepareStatement(triagematendimentochamadaclienteservico.toString());
            pstmbuscarclienteatendidolocaleservico.setObject(1, usuario);
            rsbuscarclienteatendidolocaleservico = pstmbuscarclienteatendidolocaleservico.executeQuery();
            while (rsbuscarclienteatendidolocaleservico.next()) {
                ServicoTransfer servicotransfer = new ServicoTransfer();
                servicotransfer.setNomeservico(rsbuscarclienteatendidolocaleservico.getString("nomeservico"));
                triagematendimentochamadaclientetransfers.add(servicotransfer);
            }
        } catch (SQLException ex) {
            logPrincipal(TriagemAtendimentoChamadaClienteDAO.class).error(">>>>ERROR AO BUSCAR POR TRIAGEMATENDIMENOCHAMADACLIENTE(listarTriagemAtendimentoChamadaClienteAtendidoServicoeLocalAtravesdoUsuario)", ex);
        } finally {
            pstmbuscarclienteatendidolocaleservico.close();
            rsbuscarclienteatendidolocaleservico.close();
            abrirconexao.fecharConexao();
        }
        return triagematendimentochamadaclientetransfers;
    }

    /**
     * Método responsável por buscar os Serviços de um determinado usuário
     *
     * @param id
     * @return List<UsuarioTransfer>
     * @throws java.sql.SQLException
     */
    public List<ServicoTransfer> listareBuscarServicoUsuarioTriagemAtendimentoChamadaCliente(Short id) throws SQLException {
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
            logPrincipal(UsuarioDAO.class).error(">>>>ERROR AO LISTAR BUSCAR OS SERVIÇOS DO TRIAGEMATENDIMENTOCHAMADACLIENTE(listareBuscarServicoUsuarioTriagemAtendimentoChamadaCliente)!!!", ex);
        } finally {
            pstm.close();
            rs.close();
        }
        return null;
    }

    /**
     * Metodo com a caracteristica de retornar uma lista de Dados contendo os
     * dados do local atraves do servico pretendido usuario que estiver logado e
     * vinculado ao seu servico ativo
     *
     *
     * @param String servico
     * @return List<TriagemAtendimentoClienteTransfer>
     * @throws java.sql.SQLException
     */
    public List<LocalTransfer> listarTriagemAtendimentoChamadaClienteAtendidoLocal(String servico) throws SQLException {

        List<LocalTransfer> triagematendimentochamadaclientetransfers = new ArrayList<LocalTransfer>();
        PreparedStatement pstmbuscarclienteatendido = null;
        ResultSet rsbuscarclienteatendido = null;
        try {
            StringBuffer triagematendimentochamadaclienteservico = new StringBuffer().append("    SELECT local.nomelocal\n"
                    + "FROM projetosga.servico servico\n"
                    + "INNER JOIN\n"
                    + "projetosga.triagemalternativa triagemalternativa on (triagemalternativa.idservico= servico.idservico)\n"
                    + "INNER JOIN\n"
                    + "projetosga.local local on(local.idlocal= triagemalternativa.idlocal)\n"
                    + "WHERE ((servico.nomeservico=?) and (servico.statusservico='A'))");
            pstmbuscarclienteatendido = abrirconexao.getConexao().prepareStatement(triagematendimentochamadaclienteservico.toString());
            pstmbuscarclienteatendido.setObject(1, servico);
            rsbuscarclienteatendido = pstmbuscarclienteatendido.executeQuery();
            while (rsbuscarclienteatendido.next()) {
                LocalTransfer localtransfer = new LocalTransfer();
                localtransfer.setNomelocal(rsbuscarclienteatendido.getString("nomelocal"));
                triagematendimentochamadaclientetransfers.add(localtransfer);
            }
        } catch (SQLException ex) {
            logPrincipal(TriagemAtendimentoChamadaClienteDAO.class).error(">>>>ERROR AO BUSCAR POR TRIAGEMATENDIMENOCHAMADACLIENTE(listarTriagemAtendimentoChamadaClienteAtendidoLocal)", ex);
        } finally {
            pstmbuscarclienteatendido.close();
            rsbuscarclienteatendido.close();
            abrirconexao.fecharConexao();
        }
        return triagematendimentochamadaclientetransfers;
    }

    /**
     * Metodo responsavel por alterar a senha de atendimento para indicação de
     * atendido
     *
     * @param nometriagemachamadacliente
     * @param numerodolocaltriagemachamadacliente
     * @return boolean verdadeiro/falso
     * @throws java.sql.SQLException
     */
    public boolean atualizarAtendidoTriagemAtendimentoChamadaCliente(String nometriagemachamadacliente, int numerodolocaltriagemachamadacliente, String nomeusuario) throws SQLException {
        try {
            StringBuffer strBufferSenha = new StringBuffer().append("UPDATE projetosga.triagemchamadacliente"
                    + "   SET datafimtriagemachamadacliente=?"
                    + "      ,statustriagemchamadacliente='D'"
                    + "      ,numerodolocaltriagemachamadacliente=?"
                    + "      ,usuariotriagemchamadacliente=?"
                    + "      ,emitirsenhatriagemchamadacliente='ATENDIMENTO ENCERRADO E CODIFICADO'"
                    + " WHERE nometriagemachamadacliente=?;");
            pstm = abrirconexao.getConexao().prepareStatement(strBufferSenha.toString());
            //data fim paciente atendimento
            pstm.setObject(1, new Util().getSqlTimeStamp(new Util().getPegaDataAtual()));
            //numero do local
            pstm.setObject(2, numerodolocaltriagemachamadacliente);
            //nome do usuario
            pstm.setObject(3, nomeusuario);
            //dado da senha
            pstm.setObject(4, nometriagemachamadacliente);
            ///atualiza os dados
            pstm.executeUpdate();
        } catch (SQLException ex) {
            logPrincipal(TriagemAtendimentoChamadaClienteDAO.class).error(">>>>ERROR AO ATUALIZAR TRIAGEMATENDIMENOCHAMADACLIENTE(atualizarTriagemAtendimentoChamadaCliente)", ex);
        } finally {
            pstm.close();
            abrirconexao.fecharConexao();
        }
        return true || false;
    }

    /**
     * Metodo responsavel por alterar a senha de atendimento para indicação de
     * nao atendido
     *
     * @param nometriagemachamadacliente
     * @param numerodolocaltriagemachamadacliente
     * @return boolean verdadeiro/falso
     * @throws java.sql.SQLException
     */
    public boolean atualizarNaoAtendidoTriagemAtendimentoChamadaCliente(String nometriagemachamadacliente, int numerodolocaltriagemachamadacliente, String nomeusuario) throws SQLException {
        try {
            StringBuffer strBufferSenha = new StringBuffer().append("UPDATE projetosga.triagemchamadacliente\n"
                    + "   SET datafimtriagemachamadacliente=?"
                    + "      ,statustriagemchamadacliente='N'"
                    + "      ,numerodolocaltriagemachamadacliente=?"
                    + "      ,usuariotriagemchamadacliente=?"
                    + "      ,emitirsenhatriagemchamadacliente='ATENDIMENTO NÃO ENCERRADO E NÃO CODIFICADO'"
                    + " WHERE nometriagemachamadacliente=?;");
            pstm = abrirconexao.getConexao().prepareStatement(strBufferSenha.toString());
            //data fim paciente atendimento
            pstm.setObject(1, new Util().getSqlTimeStamp(new Util().getPegaDataAtual()));
            //numero do local
            pstm.setObject(2, numerodolocaltriagemachamadacliente);
            //nome de usuario
            pstm.setObject(3, nomeusuario);
            //dado da senha
            pstm.setObject(4, nometriagemachamadacliente);
            ///atualiza os dados
            pstm.executeUpdate();
        } catch (SQLException ex) {
            logPrincipal(TriagemAtendimentoChamadaClienteDAO.class).error(">>>>ERROR AO ATUALIZAR TRIAGEMATENDIMENOCHAMADACLIENTE(atualizarNaoAtendidoTriagemAtendimentoChamadaCliente)", ex);
        } finally {
            pstm.close();
            abrirconexao.fecharConexao();
        }
        return true || false;
    }

    /**
     * Metodo responsavel por alterar o horario em que foi chamada a senha de
     * atendimento
     *
     * @param nometriagemachamadacliente
     * @return boolean verdadeiro/falso
     * @throws java.sql.SQLException
     */
    public boolean atualizarChamadadeAtendidoTriagemAtendimentoChamadaCliente(String nometriagemachamadacliente, int numerodolocaltriagemachamadacliente, String nomeusuario) throws SQLException {
        try {
            StringBuffer strBufferSenha = new StringBuffer().append("UPDATE projetosga.triagemchamadacliente\n"
                    + "   SET datachamadatriagemachamadacliente=?"
                    + "      ,usuariotriagemchamadacliente=?"
                    + "      ,numerodolocaltriagemachamadacliente=?"
                    + "      ,emitirsenhatriagemchamadacliente='CHAMADO PELA MESA'"
                    + " WHERE nometriagemachamadacliente=?;");
            pstm = abrirconexao.getConexao().prepareStatement(strBufferSenha.toString());
            //data da chamada
            pstm.setObject(1, new Util().getSqlTimeStamp(new Util().getPegaDataAtual()));
            //usuario que chamou o paciente/senha
            pstm.setObject(2, nomeusuario);
            //numero do local
            pstm.setObject(3, numerodolocaltriagemachamadacliente);
            //dado da senha
            pstm.setObject(4, nometriagemachamadacliente);
            ///atualiza os dados
            pstm.executeUpdate();
        } catch (SQLException ex) {
            logPrincipal(TriagemAtendimentoChamadaClienteDAO.class).error(">>>>ERROR AO ATUALIZAR CHAMADA TRIAGEMATENDIMENOCHAMADACLIENTE(atualizarChamadadeAtendidoTriagemAtendimentoChamadaCliente)", ex);
        } finally {
            pstm.close();
            abrirconexao.fecharConexao();
        }
        return true || false;
    }

    /**
     * Metodo responsavel por alterar o horario em que foi iniciado a chamada
     * para deslocamento do paciente/cliente
     *
     * @param nometriagemachamadacliente
     * @param nomeusuario
     * @return boolean verdadeiro/falso
     * @throws java.sql.SQLException
     */
    public boolean atualizarChamadadeDeslocamentoClienteAtendimentoTriagemAtendimentoChamadaCliente(String nometriagemachamadacliente, int numerodolocaltriagemachamadacliente, String nomeusuario) throws SQLException {
        try {
            StringBuffer strBufferSenha = new StringBuffer().append("UPDATE projetosga.triagemchamadacliente\n"
                    + "   SET datainicioatendimentochamadacliente=?"
                    + "      ,usuariotriagemchamadacliente=?"
                    + "      ,numerodolocaltriagemachamadacliente=?"
                    + "      ,emitirsenhatriagemchamadacliente='INICIOU ATENDIMENTO'"
                    + " WHERE nometriagemachamadacliente=?;");
            pstm = abrirconexao.getConexao().prepareStatement(strBufferSenha.toString());
            //data da chamada
            pstm.setObject(1, new Util().getSqlTimeStamp(new Util().getPegaDataAtual()));
            //usuario que chamou o paciente/senha
            pstm.setObject(2, nomeusuario);
            //numero do local
            pstm.setObject(3, numerodolocaltriagemachamadacliente);
            //dado da senha
            pstm.setObject(4, nometriagemachamadacliente);
            ///atualiza os dados
            pstm.executeUpdate();
        } catch (SQLException ex) {
            logPrincipal(TriagemAtendimentoChamadaClienteDAO.class).error(">>>>ERROR AO ATUALIZAR CHAMADA TRIAGEMATENDIMENOCHAMADACLIENTE(atualizarChamadadeDeslocamentoClienteAtendimentoTriagemAtendimentoChamadaCliente)", ex);
        } finally {
            pstm.close();
            abrirconexao.fecharConexao();
        }
        return true || false;
    }

}
