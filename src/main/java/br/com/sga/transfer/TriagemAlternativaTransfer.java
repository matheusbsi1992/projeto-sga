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
public class TriagemAlternativaTransfer extends GenericDomain {

    private String statustriagemalternativa;
    private String siglatriagemalternativa;
    private LocalTransfer localtransfer = new LocalTransfer();
    private ServicoTransfer servicotransfer = new ServicoTransfer();
    

    public String getSiglatriagemalternativa() {
        return siglatriagemalternativa;
    }

    public void setSiglatriagemalternativa(String siglatriagemalternativa) {
        this.siglatriagemalternativa = siglatriagemalternativa;
    }

    public String getStatustriagemalternativa() {
        return statustriagemalternativa;
    }

    public void setStatustriagemalternativa(String statustriagemalternativa) {
        this.statustriagemalternativa = statustriagemalternativa;
    }

    public LocalTransfer getLocaltransfer() {
        return localtransfer;
    }

    public void setLocaltransfer(LocalTransfer localtransfer) {
        this.localtransfer = localtransfer;
    }

    public ServicoTransfer getServicotransfer() {
        return servicotransfer;
    }

    public void setServicotransfer(ServicoTransfer servicotransfer) {
        this.servicotransfer = servicotransfer;
    }

    
}
