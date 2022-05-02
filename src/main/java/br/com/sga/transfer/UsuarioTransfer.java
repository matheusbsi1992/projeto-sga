/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.sga.transfer;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Jandisson
 */
public class UsuarioTransfer extends GenericDomain {

    private String nomeusuario;
    private String sobrenomeusuario;
    private String senhausuario;
    private String senhaconfirmacao;
    private String statususuario;
    //Lista de servico a serem tratados 
    private List<String> descricaousuarioservico = new ArrayList<String>();
    private CargoTransfer cargo = new CargoTransfer();
    private UnidadeTransfer unidade = new UnidadeTransfer();
    private ServicoTransfer servico = new ServicoTransfer();

    public String getNomeusuario() {
        return nomeusuario;
    }

    public void setNomeusuario(String nomeusuario) {
        this.nomeusuario = nomeusuario;
    }

    public String getSobrenomeusuario() {
        return sobrenomeusuario;
    }

    public void setSobrenomeusuario(String sobrenomeusuario) {
        this.sobrenomeusuario = sobrenomeusuario;
    }

    public String getSenhausuario() {
        return senhausuario;
    }

    public void setSenhausuario(String senhausuario) {
        this.senhausuario = senhausuario;
    }

    public String getSenhaconfirmacao() {
        return senhaconfirmacao;
    }

    public void setSenhaconfirmacao(String senhaconfirmacao) {
        this.senhaconfirmacao = senhaconfirmacao;
    }

    public String getStatususuario() {
        return statususuario;
    }

    public void setStatususuario(String statususuario) {
        this.statususuario = statususuario;
    }

    public List<String> getDescricaousuarioservico() {
        return descricaousuarioservico;
    }

    public void setDescricaousuarioservico(List<String> descricaousuarioservico) {
        this.descricaousuarioservico = descricaousuarioservico;
    }

    public CargoTransfer getCargo() {
        return cargo;
    }

    public void setCargo(CargoTransfer cargo) {
        this.cargo = cargo;
    }

    public UnidadeTransfer getUnidade() {
        return unidade;
    }

    public void setUnidade(UnidadeTransfer unidade) {
        this.unidade = unidade;
    }

    public ServicoTransfer getServico() {
        return servico;
    }

    public void setServico(ServicoTransfer servico) {
        this.servico = servico;
    }

}
