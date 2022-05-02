/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.sga.bo;

import br.com.sga.dao.TriagemAlternativaDAO;
import br.com.sga.transfer.LocalTransfer;
import br.com.sga.transfer.ServicoTransfer;
import br.com.sga.transfer.TriagemAlternativaTransfer;
import java.sql.SQLException;

import java.util.List;

/**
 *
 * @author Jandisson
 */
public class TriagemAlternativaBO extends TriagemAlternativaDAO {

    @Override
    public boolean inserirTriagemAlternativa(TriagemAlternativaTransfer triagemTransfer) throws SQLException {
        return super.inserirTriagemAlternativa(triagemTransfer); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean alterarTriagemAlternativa(TriagemAlternativaTransfer triagemTransfer) throws SQLException {
        return super.alterarTriagemAlternativa(triagemTransfer); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean deletarTriagemAlternativa(Short idtriagem) throws SQLException {
        return super.deletarTriagemAlternativa(idtriagem); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<TriagemAlternativaTransfer> listarTodosTriagemAlternativa() throws SQLException {
        return super.listarTodosTriagemAlternativa(); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<TriagemAlternativaTransfer> buscarTriagemAlternativa(String siglatriagemalternativa) throws SQLException {
        return super.buscarTriagemAlternativa(siglatriagemalternativa); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean existeSiglaTriagemalternativa(String siglatriagemalternativa) throws SQLException {
        return super.existeSiglaTriagemalternativa(siglatriagemalternativa); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean idSiglaTriagemAlternativaConfere(Short id, String siglatriagemalternativa) throws SQLException {
        return super.idSiglaTriagemAlternativaConfere(id, siglatriagemalternativa); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<ServicoTransfer> triagemAlternativaServicobuscarServico(String nomeservico) throws SQLException {
        return super.triagemAlternativaServicobuscarServico(nomeservico); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<LocalTransfer> triagemAlternativabuscarLocal(String nomelocal) throws SQLException {
        return super.triagemAlternativabuscarLocal(nomelocal); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean idNomeServicoTriagemAlternativaConfere(Short id, String nomeservico) throws SQLException {
        return super.idNomeServicoTriagemAlternativaConfere(id, nomeservico); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean existeNomeServicoTriagemAlternativaConfere(String nomeservico) throws SQLException {
        return super.existeNomeServicoTriagemAlternativaConfere(nomeservico); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<ServicoTransfer> listarServico() throws SQLException {
        return super.listarServico(); //To change body of generated methods, choose Tools | Templates.
    }

   
    
    

}
