/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.sga.transfer;

import java.util.List;

/**
 *
 * @author Jandisson
 */
public class CargoTransfer extends PermissaoTransfer {

    private String nomecargo;
    private String descricaocargo;
    private String statuscargo;

    public String getNomecargo() {
        return nomecargo;
    }

    public void setNomecargo(String nomecargo) {
        this.nomecargo = nomecargo;
    }

    public String getDescricaocargo() {
        return descricaocargo;
    }

    public void setDescricaocargo(String descricaocargo) {
        this.descricaocargo = descricaocargo;
    }

    public String getStatuscargo() {
        return statuscargo;
    }

    public void setStatuscargo(String statuscargo) {
        this.statuscargo = statuscargo;
    }
    

}
