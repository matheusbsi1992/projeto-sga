/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.sga.transfer;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

/**
 *
 * @author Jandisson
 */
public class PermissaoTransfer extends GenericDomain {

    //set de permissoes a serem tratadas pelo cargo do usuarioa
    private List<String> descricaopermissoes = new ArrayList<String>();

    private String nomepermissao;

    public List<String> getDescricaopermissoes() {
        return descricaopermissoes;
    }

    public void setDescricaopermissoes(List<String> descricaopermissoes) {
        this.descricaopermissoes = descricaopermissoes;
    }

    public String getNomepermissao() {
        return nomepermissao;
    }

    public void setNomepermissao(String nomepermissao) {
        this.nomepermissao = nomepermissao;
    }

}
