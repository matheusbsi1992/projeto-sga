/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.sga.bo;

import br.com.sga.dao.TriagemAtendimentoChamadaClienteDAO;
import br.com.sga.transfer.LocalTransfer;
import br.com.sga.transfer.ServicoTransfer;
import br.com.sga.transfer.TriagemAtendimentoClienteTransfer;
import java.sql.SQLException;
import java.util.List;

/**
 *
 * @author Jandisson
 */
public class TriagemAtendimentoChamadaClienteBO extends TriagemAtendimentoChamadaClienteDAO {

    @Override
    public boolean atualizarAtendidoTriagemAtendimentoChamadaCliente(String nometriagemachamadacliente, int numerodolocaltriagemachamadacliente, String nomeusuario) throws SQLException {
        return super.atualizarAtendidoTriagemAtendimentoChamadaCliente(nometriagemachamadacliente, numerodolocaltriagemachamadacliente, nomeusuario); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean atualizarNaoAtendidoTriagemAtendimentoChamadaCliente(String nometriagemachamadacliente, int numerodolocaltriagemachamadacliente, String nomeusuario) throws SQLException {
        return super.atualizarNaoAtendidoTriagemAtendimentoChamadaCliente(nometriagemachamadacliente, numerodolocaltriagemachamadacliente, nomeusuario); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean atualizarChamadadeAtendidoTriagemAtendimentoChamadaCliente(String nometriagemachamadacliente, int numerodolocaltriagemachamadacliente, String nomeusuario) throws SQLException {
        return super.atualizarChamadadeAtendidoTriagemAtendimentoChamadaCliente(nometriagemachamadacliente, numerodolocaltriagemachamadacliente, nomeusuario); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean atualizarChamadadeDeslocamentoClienteAtendimentoTriagemAtendimentoChamadaCliente(String nometriagemachamadacliente, int numerodolocaltriagemachamadacliente, String nomeusuario) throws SQLException {
        return super.atualizarChamadadeDeslocamentoClienteAtendimentoTriagemAtendimentoChamadaCliente(nometriagemachamadacliente, numerodolocaltriagemachamadacliente, nomeusuario); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean existeTriagemAtendimentoChamadaClienteLocaleServico(String nomelocal, String nomeservico, String nomedeusuario) throws SQLException {
        return super.existeTriagemAtendimentoChamadaClienteLocaleServico(nomelocal, nomeservico, nomedeusuario); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<TriagemAtendimentoClienteTransfer> buscarTriagemAtendimentoChamadaClienteLocaleServico(String nomelocal, String nomeservico) throws SQLException {
        return super.buscarTriagemAtendimentoChamadaClienteLocaleServico(nomelocal, nomeservico); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<TriagemAtendimentoClienteTransfer> buscarTriagemAtendimentoChamadaClienteLocaleServicoeCliente(String nomelocal, String nomeservico, String nomedousuario) throws SQLException {
        return super.buscarTriagemAtendimentoChamadaClienteLocaleServicoeCliente(nomelocal, nomeservico, nomedousuario); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<TriagemAtendimentoClienteTransfer> listarTriagemAtendimentoChamadaClienteAtendido() throws SQLException {
        return super.listarTriagemAtendimentoChamadaClienteAtendido(); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<TriagemAtendimentoClienteTransfer> listarTodosTriagemAtendimentoChamadaClienteLocaleServico() throws SQLException {
        return super.listarTodosTriagemAtendimentoChamadaClienteLocaleServico(); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<ServicoTransfer> listarTriagemAtendimentoChamadaClienteAtendidoServico() throws SQLException {
        return super.listarTriagemAtendimentoChamadaClienteAtendidoServico(); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<ServicoTransfer> listarTriagemAtendimentoChamadaClienteAtendidoLocalcomServicoparaUsuario(String usuario) throws SQLException {
        return super.listarTriagemAtendimentoChamadaClienteAtendidoLocalcomServicoparaUsuario(usuario); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<ServicoTransfer> listareBuscarServicoUsuarioTriagemAtendimentoChamadaCliente(Short id) throws SQLException {
        return super.listareBuscarServicoUsuarioTriagemAtendimentoChamadaCliente(id); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<LocalTransfer> listarTriagemAtendimentoChamadaClienteAtendidoLocal(String servico) throws SQLException {
        return super.listarTriagemAtendimentoChamadaClienteAtendidoLocal(servico); //To change body of generated methods, choose Tools | Templates.
    }

}
