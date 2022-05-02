/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.sga.dao;

import br.com.sga.transfer.RelatorioTransfer;
import br.com.sga.transfer.ServicoTransfer;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 *
 * @author Jandisson
 */
public class RelatorioDAO extends DAO {

    public RelatorioDAO() {

    }

    /**
     * Metodo com a caracteristica de retornar uma lista de Dados dos Serviços
     * Ativos. Serviços Disponíveis
     *
     * @return List<ServicoTransfer>
     * @throws java.sql.SQLException
     */
    public List<RelatorioTransfer> listarTodoServicoDisponivel() throws SQLException {
        List<RelatorioTransfer> servicoTransfers = new ArrayList<RelatorioTransfer>();
        try {
            strBuffer = new StringBuffer().append("SELECT nomeservico"
                    + "                                  ,statusservico"
                    + "  FROM projetosga.servico"
                    + "  WHERE statusservico='A'"
                    + "  ORDER BY nomeservico");
            pstm = abrirconexao.getConexao().prepareStatement(strBuffer.toString());
            rs = pstm.executeQuery();
            while (rs.next()) {
                ServicoTransfer servicoTransfer = new ServicoTransfer();
                RelatorioTransfer relarotioTransfer = new RelatorioTransfer();
                servicoTransfer.setNomeservico(rs.getString("nomeservico"));
                servicoTransfer.setStatusservico(rs.getString("statusservico"));
                relarotioTransfer.setServicoTransfer(servicoTransfer);
                servicoTransfers.add(relarotioTransfer);
            }
        } catch (SQLException ex) {
            logPrincipal(RelatorioDAO.class).error(">>>>ERROR AO LISTAR SERVICO DISPONIVEL(listarTodosServicoAtivo)", ex);
        } finally {
            pstm.close();
            rs.close();
            abrirconexao.fecharConexao();
        }
        return servicoTransfers;
    }

    /**
     * Metodo com a caracteristica de retornar uma lista de Dados atraves da
     * busca de datas
     *
     * @return List<ServicoTransfer>
     * @throws java.sql.SQLException
     */
    public List<RelatorioTransfer> listarTodoServicoCodificado(Date datainicial, Date datafinal) throws SQLException, ParseException {
        List<RelatorioTransfer> servicoTransfers = new ArrayList<RelatorioTransfer>();
        PreparedStatement pstm = null;
        ResultSet rs = null;
        try {
            StringBuffer strBuffer = new StringBuffer().append("SELECT		 count(servicotrbk.idservico) as quantidadedeatendimentos\n"
                    + "					,servicotrbk.nomeservico\n"
                    + "                        FROM projetosga.triagemchamadaclientetrbk triagemchamadacliente\n"
                    + "                        INNER JOIN projetosga.servicotrbk on (triagemchamadacliente.idservico = servicotrbk.idservico)\n"
                    + "                        WHERE (emitirsenhatriagemchamadacliente ='ATENDIMENTO ENCERRADO E CODIFICADO') and date(datafimtriagemachamadacliente) \n"
                    + "                        BETWEEN ? AND ?\n"
                    + "                        GROUP BY servicotrbk .nomeservico");
            pstm = abrirconexao.getConexao().prepareStatement(strBuffer.toString());
            pstm.setObject(1, new java.sql.Timestamp(datainicial.getTime()));
            pstm.setObject(2, new java.sql.Timestamp(datafinal.getTime()));
            rs = pstm.executeQuery();
            while (rs.next()) {
                ServicoTransfer servicoTransfer = new ServicoTransfer();
                RelatorioTransfer relarotioTransfer = new RelatorioTransfer();
                relarotioTransfer.setQuantidadedeservicosatendidos(rs.getInt("quantidadedeatendimentos"));
                servicoTransfer.setNomeservico(rs.getString("nomeservico"));
                relarotioTransfer.setServicoTransfer(servicoTransfer);
                servicoTransfers.add(relarotioTransfer);
            }
        } catch (SQLException ex) {
            logPrincipal(RelatorioDAO.class).error(">>>>ERROR AO LISTAR SERVICO CODIFICADO POR DATA (listarTodoServicoCodificado)", ex);
        } finally {
            pstm.close();
            rs.close();
            abrirconexao.fecharConexao();
        }
        return servicoTransfers;
    }

    /**
     * Metodo com a caracteristica de retornar uma lista de Dados atraves da
     * busca de datas, sobre os servicos/atendimentos concluidos
     *
     * @param datainicial
     * @param datafinal
     * @return List<RelatorioTransfer>
     * @throws java.sql.SQLException
     */
    public List<RelatorioTransfer> listarTodoServicoouAtendimentoConcluido(Date datainicial, Date datafinal) throws SQLException {
        List<RelatorioTransfer> servicoTransfers = new ArrayList<RelatorioTransfer>();
        PreparedStatement pstm = null;
        ResultSet rs = null;
        try {
            StringBuffer strBuffer = new StringBuffer().append("SELECT  nometriagemachamadacliente\n "
                    + "        ,date(datainiciotriagemachamadacliente) as datainiciotiradatriagemchamadacliente\n"
                    + "       ,datainiciotriagemachamadacliente\n"
                    + "       ,datafimtriagemachamadacliente \n"
                    + "       ,normaltriagemachamadacliente\n"
                    + "       ,prioridadetriagemachamadacliente \n"
                    + "       ,usuariotriagemchamadacliente\n"
                    + "       ,datachamadatriagemachamadacliente\n"
                    + "       ,servicotrbk.nomeservico\n"
                    + "       ,(triagemchamadaclientetrbk.datafimtriagemachamadacliente) - (triagemchamadaclientetrbk.datachamadatriagemachamadacliente) as duracao\n"
                    + "       ,(triagemchamadaclientetrbk.datachamadatriagemachamadacliente) - (triagemchamadaclientetrbk.datainiciotriagemachamadacliente) as tempodeespera\n"
                    + "FROM 		projetosga.triagemchamadaclientetrbk triagemchamadaclientetrbk\n"
                    + "INNER JOIN 	projetosga.servicotrbk servicotrbk\n"
                    + "ON 		(servicotrbk.idservico=triagemchamadaclientetrbk.idservico)\n"
                    + "WHERE 		(emitirsenhatriagemchamadacliente ='ATENDIMENTO ENCERRADO E CODIFICADO') AND DATE(triagemchamadaclientetrbk.datafimtriagemachamadacliente) BETWEEN ? AND ?\n"
                    + "ORDER BY servicotrbk.nomeservico");
            pstm = abrirconexao.getConexao().prepareStatement(strBuffer.toString());
            pstm.setObject(1, new java.sql.Timestamp(datainicial.getTime()));
            pstm.setObject(2, new java.sql.Timestamp(datafinal.getTime()));
            rs = pstm.executeQuery();
            while (rs.next()) {
                ServicoTransfer servicoTransfer = new ServicoTransfer();
                RelatorioTransfer relarotioTransfer = new RelatorioTransfer();
                //data da tirada da senha
                relarotioTransfer.setDatainiciotiradatriagemchamadacliente(rs.getDate("datainiciotiradatriagemchamadacliente"));
                //data retira de senha/pedido de senha
                relarotioTransfer.setDatainiciotriagemchamadacliente(rs.getTimestamp("datainiciotriagemachamadacliente"));
                //data finalizada da senha
                relarotioTransfer.setDatafimtriagemchamadacliente(rs.getTimestamp("datafimtriagemachamadacliente"));
                //senha da triagem
                relarotioTransfer.setNometriagemachamadacliente(rs.getString("nometriagemachamadacliente").toUpperCase());
                //cor do nome da senha, com a primeira posicao
                String cornomesenha = relarotioTransfer.getNometriagemachamadacliente().substring(0, 1);
                //codicao para definir se e prioridade ou convencional
                if (cornomesenha.equalsIgnoreCase("P")) {
                    relarotioTransfer.setCormonitorsenha("Red");
                    relarotioTransfer.setPrioridadetriagemachamadacliente(rs.getString("prioridadetriagemachamadacliente"));
                } else {
                    //definir prioridades
                    relarotioTransfer.setPrioridadetriagemachamadacliente(rs.getString("normaltriagemachamadacliente"));
                    relarotioTransfer.setCormonitorsenha("Blue");
                }
                //usuario que concluiu o atendimento
                relarotioTransfer.setUsuario(rs.getString("usuariotriagemchamadacliente"));
                //data da chamada do atendimento
                relarotioTransfer.setDatachamadatriagemchamadacliente(rs.getTimestamp("datachamadatriagemachamadacliente"));
                //servico realizado
                servicoTransfer.setNomeservico(rs.getString("nomeservico"));
                relarotioTransfer.setServicoTransfer(servicoTransfer);
                //tempo de duracao entre a data de finalizado do atendimento menos a data de pedido

                //seta o tempo de duracao
                String duracao = String.valueOf(rs.getObject("duracao"))
                        .replaceAll("years", "anos")
                        .replaceAll("mons", "meses")
                        .replaceAll("days", "dias")
                        .replaceAll("hours", "horas")
                        .replaceAll("mins", "mins")
                        .replaceAll("secs", "segs");
                if (duracao.contains("dias")) {
                    relarotioTransfer.setDuracao(duracao);
                } else {
                    duracao = String.valueOf(rs.getObject("duracao"))
                            .replaceAll("years", "ano")
                            .replaceAll("mons", "mes")
                            .replaceAll("days", "dia")
                            .replaceAll("hours", "hora")
                            .replaceAll("mins", "min")
                            .replaceAll("secs", "seg");
                    relarotioTransfer.setDuracao(duracao);
                }

                //seta o tempo de espera
                String tempodeespera = String.valueOf(rs.getObject("tempodeespera"))
                        .replaceAll("years", "anos")
                        .replaceAll("mons", "meses")
                        .replaceAll("days", "dias")
                        .replaceAll("hours", "horas")
                        .replaceAll("mins", "mins")
                        .replaceAll("secs", "segs");
                if (tempodeespera.contains("dias")) {
                    relarotioTransfer.setTempodeesperatriagemchamadacliente(tempodeespera);
                } else {
                    tempodeespera = String.valueOf(rs.getObject("tempodeespera"))
                            .replaceAll("years", "ano")
                            .replaceAll("mons", "mes")
                            .replaceAll("days", "dia")
                            .replaceAll("hours", "hora")
                            .replaceAll("mins", "min")
                            .replaceAll("secs", "seg");
                    relarotioTransfer.setTempodeesperatriagemchamadacliente(tempodeespera);
                }

                servicoTransfers.add(relarotioTransfer);
            }
        } catch (SQLException ex) {
            logPrincipal(RelatorioDAO.class).error(">>>>ERROR AO LISTAR SERVICO/ATENDIMENTO CONCLUIDO POR DATA (listarTodoServicoouAtendimentoConcluido)", ex);
        } finally {
            pstm.close();
            rs.close();
            abrirconexao.fecharConexao();
        }
        return servicoTransfers;
    }

    /**
     * Metodo com a caracteristica de retornar uma lista de Dados atraves da
     * busca de datas, sobre os servicos/atendimentos em todos os status
     *
     * @param datainicial
     * @param datafinal
     * @return List<RelatorioTransfer>
     * @throws java.sql.SQLException
     */
    public List<RelatorioTransfer> listarTodoServicoAtendimentoEmTodosOsStatus(Date datainicial, Date datafinal) throws SQLException {
        List<RelatorioTransfer> servicoTransfers = new ArrayList<RelatorioTransfer>();
        PreparedStatement pstm = null;
        ResultSet rs = null;
        try {
            StringBuffer strBuffer = new StringBuffer().append("SELECT date(datainiciotriagemachamadacliente) as datainiciotiradatriagemchamadacliente\n"
                    + "       ,datainiciotriagemachamadacliente\n"
                    + "       ,datafimtriagemachamadacliente \n"
                    + "       ,normaltriagemachamadacliente"
                    + "       ,nometriagemachamadacliente\n"
                    + "       ,prioridadetriagemachamadacliente \n"
                    + "       ,usuariotriagemchamadacliente\n"
                    + "       ,datachamadatriagemachamadacliente\n"
                    + "       ,servicotrbk.nomeservico\n"
                    + "       ,emitirsenhatriagemchamadacliente\n"
                    + "       ,(triagemchamadaclientetrbk.datafimtriagemachamadacliente) - (triagemchamadaclientetrbk.datachamadatriagemachamadacliente) as duracao\n"
                    + "       ,(triagemchamadaclientetrbk.datachamadatriagemachamadacliente) - (triagemchamadaclientetrbk.datainiciotriagemachamadacliente) as tempodeespera\n"
                    + "FROM 		projetosga.triagemchamadaclientetrbk triagemchamadaclientetrbk\n"
                    + "INNER JOIN 	projetosga.servicotrbk servicotrbk\n"
                    + "ON 		(servicotrbk.idservico=triagemchamadaclientetrbk.idservico)\n"
                    + "WHERE 		DATE(triagemchamadaclientetrbk.datainiciotriagemachamadacliente) BETWEEN ? AND ?\n"
                    + "ORDER BY triagemchamadaclientetrbk.emitirsenhatriagemchamadacliente");
            pstm = abrirconexao.getConexao().prepareStatement(strBuffer.toString());
            pstm.setObject(1, new java.sql.Timestamp(datainicial.getTime()));
            pstm.setObject(2, new java.sql.Timestamp(datafinal.getTime()));
            rs = pstm.executeQuery();
            while (rs.next()) {
                ServicoTransfer servicoTransfer = new ServicoTransfer();
                RelatorioTransfer relarotioTransfer = new RelatorioTransfer();

                //data da tirada da senha
                relarotioTransfer.setDatainiciotiradatriagemchamadacliente(rs.getDate("datainiciotiradatriagemchamadacliente"));
                //data retira de senha/pedido de senha
                relarotioTransfer.setDatainiciotriagemchamadacliente(rs.getTimestamp("datainiciotriagemachamadacliente"));
                //data finalizada da senha
                relarotioTransfer.setDatafimtriagemchamadacliente(rs.getTimestamp("datafimtriagemachamadacliente"));
                //senha da triagem
                relarotioTransfer.setNometriagemachamadacliente(rs.getString("nometriagemachamadacliente").toUpperCase());
                //status
                relarotioTransfer.setEmitirsenhatriagemchamadacliente(rs.getString("emitirsenhatriagemchamadacliente"));
                //cor do nome da senha, com a primeira posicao
                String cornomesenha = relarotioTransfer.getNometriagemachamadacliente().substring(0, 1);
                //codicao para definir se e prioridade ou convencional
                if (cornomesenha.equalsIgnoreCase("P")) {
                    relarotioTransfer.setCormonitorsenha("Red");
                    relarotioTransfer.setPrioridadetriagemachamadacliente(rs.getString("prioridadetriagemachamadacliente"));
                } else {
                    //definir prioridades
                    relarotioTransfer.setPrioridadetriagemachamadacliente(rs.getString("normaltriagemachamadacliente"));
                    relarotioTransfer.setCormonitorsenha("Blue");
                }
                //usuario que concluiu o atendimento
                relarotioTransfer.setUsuario(rs.getString("usuariotriagemchamadacliente"));
                //data da chamada do atendimento
                relarotioTransfer.setDatachamadatriagemchamadacliente(rs.getTimestamp("datachamadatriagemachamadacliente"));
                //servico realizado
                servicoTransfer.setNomeservico(rs.getString("nomeservico"));
                relarotioTransfer.setServicoTransfer(servicoTransfer);
                //tempo de duracao entre a data de finalizado do atendimento menos a data de pedido

                //seta o tempo de duracao
                String duracao = String.valueOf(rs.getObject("duracao"))
                        .replaceAll("years", "anos")
                        .replaceAll("mons", "meses")
                        .replaceAll("days", "dias")
                        .replaceAll("hours", "horas")
                        .replaceAll("mins", "mins")
                        .replaceAll("secs", "segs");
                if (duracao == "null" || duracao.equals(" ")) {
                    relarotioTransfer.setTempodeesperatriagemchamadacliente("");
                } else if (duracao.contains("dias")) {
                    relarotioTransfer.setDuracao(duracao);
                } else {
                    duracao = String.valueOf(rs.getObject("duracao"))
                            .replaceAll("years", "ano")
                            .replaceAll("mons", "mes")
                            .replaceAll("days", "dia")
                            .replaceAll("hours", "hora")
                            .replaceAll("mins", "min")
                            .replaceAll("secs", "seg");
                    relarotioTransfer.setDuracao(duracao);
                }

                //seta o tempo de espera
                String tempodeespera = String.valueOf(rs.getObject("tempodeespera"))
                        .replaceAll("years", "anos")
                        .replaceAll("mons", "meses")
                        .replaceAll("days", "dias")
                        .replaceAll("hours", "horas")
                        .replaceAll("mins", "mins")
                        .replaceAll("secs", "segs");
                if (tempodeespera == "null" || tempodeespera.equals(" ")) {
                    relarotioTransfer.setTempodeesperatriagemchamadacliente("");
                } else if (tempodeespera.contains("dias")) {
                    relarotioTransfer.setTempodeesperatriagemchamadacliente(tempodeespera);
                } else {
                    tempodeespera = String.valueOf(rs.getObject("tempodeespera"))
                            .replaceAll("years", "ano")
                            .replaceAll("mons", "mes")
                            .replaceAll("days", "dia")
                            .replaceAll("hours", "hora")
                            .replaceAll("mins", "min")
                            .replaceAll("secs", "seg");
                    relarotioTransfer.setTempodeesperatriagemchamadacliente(tempodeespera);
                }
                servicoTransfers.add(relarotioTransfer);
            }
        } catch (SQLException ex) {
            logPrincipal(RelatorioDAO.class).error(">>>>ERROR AO LISTAR SERVICO/ATENDIMENTO STATUS POR DATA (listarTodoServicoAtendimentoEmTodosOsStatus)", ex);
        } finally {
            pstm.close();
            rs.close();
            abrirconexao.fecharConexao();
        }
        return servicoTransfers;
    }

    /**
     * Metodo com a caracteristica de retornar uma lista de Dados atraves da
     * busca de datas, sobre os servicos/atendimentos,chamadas e deslocamento em
     * todos os status
     *
     * @param datainicial
     * @param datafinal
     * @return List<RelatorioTransfer>
     * @throws java.sql.SQLException
     */
    public List<RelatorioTransfer> listarTempoMediodeServicoAtendimento(Date datainicial, Date datafinal) throws SQLException {
        List<RelatorioTransfer> servicoTransfers = new ArrayList<RelatorioTransfer>();
        PreparedStatement pstm = null;
        ResultSet rs = null;
        try {
            StringBuffer strBuffer = new StringBuffer().append("SELECT  usuariotriagemchamadacliente\n"
                    + "       ,COUNT(usuariotriagemchamadacliente)  as quantidaddeatendimentos\n"
                    + "      ,SUM((triagemchamadaclientetrbk.datachamadatriagemachamadacliente) - (triagemchamadaclientetrbk.datainiciotriagemachamadacliente))  / COUNT(usuariotriagemchamadacliente)  as tempomediodeespera\n"
                    + "      ,SUM((triagemchamadaclientetrbk.datainicioatendimentochamadacliente)-(triagemchamadaclientetrbk.datachamadatriagemachamadacliente)) / COUNT(usuariotriagemchamadacliente) as tempomediodedeslocamento\n"
                    + "      ,SUM((triagemchamadaclientetrbk.datafimtriagemachamadacliente) - (triagemchamadaclientetrbk.datainicioatendimentochamadacliente)) / COUNT(usuariotriagemchamadacliente) as tempomediodeatendimento\n"
                    + "     ,(SUM((triagemchamadaclientetrbk.datachamadatriagemachamadacliente  ) -(triagemchamadaclientetrbk.datainiciotriagemachamadacliente		))\n"
                    + "      +SUM((triagemchamadaclientetrbk.datainicioatendimentochamadacliente) -(triagemchamadaclientetrbk.datachamadatriagemachamadacliente	))\n"
                    + "      +SUM((triagemchamadaclientetrbk.datafimtriagemachamadacliente      ) -(triagemchamadaclientetrbk.datainicioatendimentochamadacliente	)))/ COUNT(usuariotriagemchamadacliente)as tempototal\n"
                    + "FROM 		projetosga.triagemchamadaclientetrbk triagemchamadaclientetrbk\n"
                    + "INNER JOIN 	projetosga.servicotrbk servicotrbk\n"
                    + "ON 		(servicotrbk.idservico=triagemchamadaclientetrbk.idservico)\n"
                    + "WHERE 		DATE(triagemchamadaclientetrbk.datafimtriagemachamadacliente) BETWEEN ? AND ?\n"
                    + "GROUP BY usuariotriagemchamadacliente");
            pstm = abrirconexao.getConexao().prepareStatement(strBuffer.toString());
            pstm.setObject(1, new java.sql.Timestamp(datainicial.getTime()));
            pstm.setObject(2, new java.sql.Timestamp(datafinal.getTime()));
            rs = pstm.executeQuery();
            while (rs.next()) {

                RelatorioTransfer relarotioTransfer = new RelatorioTransfer();

                //usuario de atendimento
                relarotioTransfer.setUsuario(rs.getString("usuariotriagemchamadacliente"));

                //quantidade de atendimentos realizado pelo atendente
                relarotioTransfer.setQuantidadedeservicosatendidos(rs.getInt("quantidaddeatendimentos"));

                //seta o tempo medio de espera
                String tempomediodeespera = String.valueOf(rs.getObject("tempomediodeespera"))
                        .replaceAll("years", "anos")
                        .replaceAll("mons", "meses")
                        .replaceAll("days", "dias")
                        .replaceAll("hours", "horas")
                        .replaceAll("mins", "mins")
                        .replaceAll("secs", "segs");
                if (tempomediodeespera == "null" || tempomediodeespera.equals(" ")) {
                    relarotioTransfer.setTempomediodeespera("");
                } else if (tempomediodeespera.contains("dias")) {
                    relarotioTransfer.setTempomediodeespera(tempomediodeespera);
                } else {
                    tempomediodeespera = String.valueOf(rs.getObject("tempomediodeespera"))
                            .replaceAll("years", "ano")
                            .replaceAll("mons", "mes")
                            .replaceAll("days", "dia")
                            .replaceAll("hours", "hora")
                            .replaceAll("mins", "min")
                            .replaceAll("secs", "seg");
                    relarotioTransfer.setTempomediodeespera(tempomediodeespera);
                }

                //seta o tempo medio de deslocamento
                String tempomediodedeslocamento = String.valueOf(rs.getObject("tempomediodedeslocamento"))
                        .replaceAll("years", "anos")
                        .replaceAll("mons", "meses")
                        .replaceAll("days", "dias")
                        .replaceAll("hours", "horas")
                        .replaceAll("mins", "mins")
                        .replaceAll("secs", "segs");
                if (tempomediodedeslocamento == "null" || tempomediodedeslocamento.equals(" ")) {
                    relarotioTransfer.setTempomediodedeslocamento("");
                } else if (tempomediodedeslocamento.contains("dias")) {
                    relarotioTransfer.setTempomediodedeslocamento(tempomediodedeslocamento);
                } else {
                    tempomediodedeslocamento = String.valueOf(rs.getObject("tempomediodedeslocamento"))
                            .replaceAll("years", "ano")
                            .replaceAll("mons", "mes")
                            .replaceAll("days", "dia")
                            .replaceAll("hours", "hora")
                            .replaceAll("mins", "min")
                            .replaceAll("secs", "seg");
                    relarotioTransfer.setTempomediodedeslocamento(tempomediodedeslocamento);
                }

                //seta o tempo medio de atendimento
                String tempomediodeatendimento = String.valueOf(rs.getObject("tempomediodeatendimento"))
                        .replaceAll("years", "anos")
                        .replaceAll("mons", "meses")
                        .replaceAll("days", "dias")
                        .replaceAll("hours", "horas")
                        .replaceAll("mins", "mins")
                        .replaceAll("secs", "segs");
                if (tempomediodeatendimento == "null" || tempomediodeatendimento.equals(" ")) {
                    relarotioTransfer.setTempomediodeatendimento("");
                } else if (tempomediodeatendimento.contains("dias")) {
                    relarotioTransfer.setTempomediodeatendimento(tempomediodeatendimento);
                } else {
                    tempomediodeatendimento = String.valueOf(rs.getObject("tempomediodeatendimento"))
                            .replaceAll("years", "ano")
                            .replaceAll("mons", "mes")
                            .replaceAll("days", "dia")
                            .replaceAll("hours", "hora")
                            .replaceAll("mins", "min")
                            .replaceAll("secs", "seg");
                    relarotioTransfer.setTempomediodeatendimento(tempomediodeatendimento);
                }

                //seta o tempo de total
                String tempototal = String.valueOf(rs.getObject("tempototal"))
                        .replaceAll("years", "anos")
                        .replaceAll("mons", "meses")
                        .replaceAll("days", "dias")
                        .replaceAll("hours", "horas")
                        .replaceAll("mins", "mins")
                        .replaceAll("secs", "segs");
                if (tempototal == "null" || tempototal.equals(" ")) {
                    relarotioTransfer.setTempototal("");
                } else if (tempototal.contains("dias")) {
                    relarotioTransfer.setTempototal(tempototal);
                } else {
                    tempototal = String.valueOf(rs.getObject("tempototal"))
                            .replaceAll("years", "ano")
                            .replaceAll("mons", "mes")
                            .replaceAll("days", "dia")
                            .replaceAll("hours", "hora")
                            .replaceAll("mins", "min")
                            .replaceAll("secs", "seg");
                    relarotioTransfer.setTempototal(tempototal);
                }

                servicoTransfers.add(relarotioTransfer);
            }
        } catch (SQLException ex) {
            logPrincipal(RelatorioDAO.class).error(">>>>ERROR AO LISTAR TEMPO MEDIO DE ATENDIMENTO DO SERVICO/ATENDIMENTO POR DATA (listarTempoMediodeServicoAtendimento)", ex);
        } finally {
            pstm.close();
            rs.close();
            abrirconexao.fecharConexao();
        }
        return servicoTransfers;
    }

}
