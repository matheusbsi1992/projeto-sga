/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.sga.transfer;

import java.util.Objects;

/**
 *
 * @author Jandisson
 *
 */
public class ServicoTransfer extends GenericDomain {

    private String statusservico;
    private String nomeservico;
    private String descricaoservico;

    public String getStatusservico() {
        return statusservico;
    }

    public void setStatusservico(String statusservico) {
        this.statusservico = statusservico;
    }

    public String getNomeservico() {
        return nomeservico;
    }

    public void setNomeservico(String nomeservico) {
        this.nomeservico = nomeservico;
    }

    public String getDescricaoservico() {
        return descricaoservico;
    }

    public void setDescricaoservico(String descricaoservico) {
        this.descricaoservico = descricaoservico;
    }

    @Override
    public String toString() {
        return super.toString(); //To change body of generated methods, choose Tools | Templates.
    }

    
    
    
}
