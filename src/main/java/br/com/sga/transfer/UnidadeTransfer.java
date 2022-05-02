/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.sga.transfer;

/**
 *
 * @author Jandisson
 *
 */
public class UnidadeTransfer extends GenericDomain {

    private String statusunidade;
    private String nomeunidade;
    private String siglaunidade;

    public String getStatusunidade() {
        return statusunidade;
    }

    public void setStatusunidade(String statusunidade) {
        this.statusunidade = statusunidade;
    }

    public String getNomeunidade() {
        return nomeunidade;
    }

    public void setNomeunidade(String nomeunidade) {
        this.nomeunidade = nomeunidade;
    }

    public String getSiglaunidade() {
        return siglaunidade;
    }

    public void setSiglaunidade(String siglaunidade) {
        this.siglaunidade = siglaunidade;
    }

}
