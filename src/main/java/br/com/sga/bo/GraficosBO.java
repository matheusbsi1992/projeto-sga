/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.sga.bo;

import br.com.sga.dao.GraficosDAO;
import br.com.sga.transfer.RelatorioTransfer;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.Date;
import java.util.List;

/**
 *
 * @author Jandisson
 */
public class GraficosBO extends GraficosDAO {

    @Override
    public List<RelatorioTransfer> listarTodoGraficoServicoPorStatus(Date datainicial, Date datafinal) throws SQLException {
        return super.listarTodoGraficoServicoPorStatus(datainicial, datafinal); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<RelatorioTransfer> listarTodoGraficoAtendimentoPorServico(Date datainicial, Date datafinal) throws SQLException {
        return super.listarTodoGraficoAtendimentoPorServico(datainicial, datafinal); //To change body of generated methods, choose Tools | Templates.
    }

}
