/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.sga.bo;

import br.com.sga.dao.RelatorioDAO;
import br.com.sga.transfer.RelatorioTransfer;
import br.com.sga.transfer.ServicoTransfer;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.Date;
import java.util.List;

/**
 *
 * @author Jandisson
 */
public class RelatorioBO extends RelatorioDAO {

    public RelatorioBO() {

    }

    @Override
    public List<RelatorioTransfer> listarTodoServicoDisponivel() throws SQLException {
        return super.listarTodoServicoDisponivel(); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<RelatorioTransfer> listarTodoServicoCodificado(Date datainicial, Date datafinal) throws SQLException, ParseException {
        return super.listarTodoServicoCodificado(datainicial, datafinal); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<RelatorioTransfer> listarTodoServicoouAtendimentoConcluido(Date datainicial, Date datafinal) throws SQLException {
        return super.listarTodoServicoouAtendimentoConcluido(datainicial, datafinal); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<RelatorioTransfer> listarTodoServicoAtendimentoEmTodosOsStatus(Date datainicial, Date datafinal) throws SQLException {
        return super.listarTodoServicoAtendimentoEmTodosOsStatus(datainicial, datafinal); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<RelatorioTransfer> listarTempoMediodeServicoAtendimento(Date datainicial, Date datafinal) throws SQLException {
        return super.listarTempoMediodeServicoAtendimento(datainicial, datafinal); //To change body of generated methods, choose Tools | Templates.
    }

}
