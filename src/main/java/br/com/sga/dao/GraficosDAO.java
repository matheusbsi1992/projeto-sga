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
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 *
 * @author Jandisson
 */
public class GraficosDAO extends DAO {

    /**
     * Metodo com a caracteristica de retornar uma lista de Dados atraves da
     * busca de datas, para a obtencao de um total do status
     *
     * @param datainicial
     * @param datafinal
     * @return List<ServicoTransfer>
     * @throws java.sql.SQLException
     */
    public List<RelatorioTransfer> listarTodoGraficoServicoPorStatus(Date datainicial, Date datafinal) throws SQLException {
        List<RelatorioTransfer> servicoTransfers = new ArrayList<RelatorioTransfer>();
        PreparedStatement pstm = null;
        ResultSet rs = null;
        try {
            StringBuffer strBuffer = new StringBuffer().append("SELECT   emitirsenhatriagemchamadacliente"
                    + "                                                 ,COUNT    (emitirsenhatriagemchamadacliente) as quantidadedeatendimentos"
                    + "  FROM     projetosga.triagemchamadaclientetrbk"
                    + "  WHERE    DATE(triagemchamadaclientetrbk.datainiciotriagemachamadacliente) BETWEEN ? AND ?"
                    + "  GROUP BY emitirsenhatriagemchamadacliente"
                    + "  HAVING COUNT(emitirsenhatriagemchamadacliente) > 0");
            pstm = abrirconexao.getConexao().prepareStatement(strBuffer.toString());
            pstm.setObject(1, new java.sql.Timestamp(datainicial.getTime()));
            pstm.setObject(2, new java.sql.Timestamp(datafinal.getTime()));
            rs = pstm.executeQuery();
            while (rs.next()) {
                RelatorioTransfer relarotioTransfer = new RelatorioTransfer();
                relarotioTransfer.setEmitirsenhatriagemchamadacliente(rs.getString("emitirsenhatriagemchamadacliente"));
                relarotioTransfer.setQuantidadedestatus(rs.getInt("quantidadedeatendimentos"));

                servicoTransfers.add(relarotioTransfer);
            }
        } catch (SQLException ex) {
            logPrincipal(GraficosDAO.class).error(">>>>ERROR AO LISTAR GRAFICO DE STATUS (listarTodoGraficoServicoPorStatus)", ex);
        } finally {
            pstm.close();
            rs.close();
            abrirconexao.fecharConexao();
        }
        return servicoTransfers;
    }

    /**
     * Metodo com a caracteristica de retornar uma lista de Dados atraves da
     * busca de datas, para a obtencao de atendimento por serviço
     *
     * @param datainicial
     * @param datafinal
     * @return List<ServicoTransfer>
     * @throws java.sql.SQLException
     */
    public List<RelatorioTransfer> listarTodoGraficoAtendimentoPorServico(Date datainicial, Date datafinal) throws SQLException {
        List<RelatorioTransfer> servicoTransfers = new ArrayList<RelatorioTransfer>();
        PreparedStatement pstm = null;
        ResultSet rs = null;
        try {
            StringBuffer strBuffer = new StringBuffer().append("SELECT servicotrbk.nomeservico\n"
                    + "  ,COUNT (servicotrbk.nomeservico) as quantidadedeservicosatendidos\n"
                    + "  FROM projetosga.triagemchamadaclientetrbk triagemchamadaclientetrbk\n"
                    + "  INNER JOIN projetosga.servicotrbk servicotrbk on (servicotrbk.idservico=triagemchamadaclientetrbk.idservico)\n"
                    + "  WHERE (triagemchamadaclientetrbk.emitirsenhatriagemchamadacliente='ATENDIMENTO ENCERRADO E CODIFICADO') AND  DATE(triagemchamadaclientetrbk.datainiciotriagemachamadacliente) BETWEEN ? AND ?\n"
                    + "  GROUP BY servicotrbk.nomeservico");
            pstm = abrirconexao.getConexao().prepareStatement(strBuffer.toString());
            pstm.setObject(1, new java.sql.Timestamp(datainicial.getTime()));
            pstm.setObject(2, new java.sql.Timestamp(datafinal.getTime()));
            rs = pstm.executeQuery();
            while (rs.next()) {
                RelatorioTransfer relarotioTransfer = new RelatorioTransfer();
                ServicoTransfer servicoTransfer = new ServicoTransfer();
                servicoTransfer.setNomeservico(rs.getString("nomeservico"));
                relarotioTransfer.setServicoTransfer(servicoTransfer);
                relarotioTransfer.setQuantidadedeservicosatendidos(rs.getInt("quantidadedeservicosatendidos"));
                servicoTransfers.add(relarotioTransfer);
            }
        } catch (SQLException ex) {
            logPrincipal(GraficosDAO.class).error(">>>>ERROR AO LISTAR GRAFICO DE SERVIÇOS ATENDIDOS (listarTodoGraficoServicoPorStatus)", ex);
        } finally {
            pstm.close();
            rs.close();
            abrirconexao.fecharConexao();
        }
        return servicoTransfers;
    }

}
