/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.sga.transfer;

/**
 *
 * @author Jandisson
 */
public class TriagemAtendimentoClienteTransfer extends TriagemChamadaClienteTransfer {

    public TriagemAtendimentoClienteTransfer(){
        
    }
    
    //identificar numero do local como guiche,sala e consultorio
    private int numerodolocal = 1;

    //status do atendimento para a(ativo)/i(inativo)
    private String statustriagemAtendimentochamadacliente = "A";

   
    public String getStatustriagemAtendimentochamadacliente() {
        return statustriagemAtendimentochamadacliente;
    }

    public void setStatustriagemAtendimentochamadacliente(String statustriagemAtendimentochamadacliente) {
        this.statustriagemAtendimentochamadacliente = statustriagemAtendimentochamadacliente;
    }

    public int getNumerodolocal() {
        return numerodolocal;
    }

    public void setNumerodolocal(int numerodolocal) {
        this.numerodolocal = numerodolocal;
    }
}
