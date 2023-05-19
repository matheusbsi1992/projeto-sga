/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.com.projeto.clinica.bo;

import br.com.projeto.clinica.dao.LoginDAO;
import br.com.projeto.clinica.model.Pessoa;
import java.util.List;

/**
 *
 * @author matheus
 */
public class LoginBO extends LoginDAO {

    public LoginBO() {
    }

    @Override
    public boolean ALTERARSENHADOUSUARIOLOGADO(Pessoa pessoa) {
        return super.ALTERARSENHADOUSUARIOLOGADO(pessoa); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/OverriddenMethodBody
    }

    @Override
    public List<Pessoa> EXISTELOGINDEPAGINASDEPERMISSAO(String emailPessoa, String codigoClinica, String senhaPessoa) {
        return super.EXISTELOGINDEPAGINASDEPERMISSAO(emailPessoa, codigoClinica, senhaPessoa); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/OverriddenMethodBody
    }

    @Override
    public boolean EXISTEUSUARIOCLINICA(String emailPessoa, String codigoClinica, String senhaPessoa) {
        return super.EXISTEUSUARIOCLINICA(emailPessoa, codigoClinica, senhaPessoa); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/OverriddenMethodBody
    }

    @Override
    public boolean LOGIN(String emailPessoa, String senhaPessoa, String codigoClinica) {
        return super.LOGIN(emailPessoa, senhaPessoa, codigoClinica); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/OverriddenMethodBody
    }

    @Override
    public Pessoa PESQUISARLOGIN(String email, String codigoClinica, String senhaPessoa) {
        return super.PESQUISARLOGIN(email, codigoClinica, senhaPessoa); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/OverriddenMethodBody
    }

}
