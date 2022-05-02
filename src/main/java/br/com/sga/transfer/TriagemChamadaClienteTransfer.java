/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.sga.transfer;

import java.sql.Date;
import java.sql.Timestamp;
import javax.el.PropertyNotFoundException;

/**
 *
 * @author Jandisson
 *
 */
public class TriagemChamadaClienteTransfer extends TriagemAlternativaTransfer {

    //atributos para identificação de botoões nas paginas contidas em triagemchamadacliente
    private String normalchamadatriagemchamadacliente = "CONVENCIONAL";
    private String prioridadechamadatriagemchamadacliente = "PRIORIDADE";

    //quantidade de senhas normal e prioridade
    private int quantidadetriagemchamadaclientenormal;
    private int quantidadetriagemchamadaclienteprioridade;
    //quantidade de senhas
    private int quantidadetriagemachamadacliente;

    //nome triagem achamadacliente
    private String nometriagemachamadacliente = "Senha";

    //cor da senha no monitor
    private String cormonitorsenha;

    //situacao de emitir senha
    private String emitirsenhatriagemchamadacliente = "SENHA EMITIDA";

    //definir prioridades
    private String prioridadetriagemachamadacliente;

    //status da senha
    private String statustriagemchamadacliente = "A";

    //tempo determinado
    private Timestamp datainiciotriagemchamadacliente = null;
    private Timestamp datafimtriagemchamadacliente = null;
    private Timestamp datachamadatriagemchamadacliente = null;
    private Date datainiciotiradatriagemchamadacliente = null;

    //tempo de espera
    private String tempodeesperatriagemchamadacliente;

    public TriagemChamadaClienteTransfer() {
    }

    //unidade
    private UnidadeTransfer unidadeTransfer = new UnidadeTransfer();

    public String getNormalchamadatriagemchamadacliente() {
        return normalchamadatriagemchamadacliente;
    }

    public void setNormalchamadatriagemchamadacliente(String normalchamadatriagemchamadacliente) {
        this.normalchamadatriagemchamadacliente = normalchamadatriagemchamadacliente;
    }

    public String getPrioridadechamadatriagemchamadacliente() {
        return prioridadechamadatriagemchamadacliente;
    }

    public void setPrioridadechamadatriagemchamadacliente(String prioridadechamadatriagemchamadacliente) {
        this.prioridadechamadatriagemchamadacliente = prioridadechamadatriagemchamadacliente;
    }

    public String getNometriagemachamadacliente() {
        return nometriagemachamadacliente;
    }

    public void setNometriagemachamadacliente(String nometriagemachamadacliente) {
        this.nometriagemachamadacliente = nometriagemachamadacliente;
    }

    public String getCormonitorsenha() {
        return cormonitorsenha;
    }

    public void setCormonitorsenha(String cormonitorsenha) {
        this.cormonitorsenha = cormonitorsenha;
    }

    public String getEmitirsenhatriagemchamadacliente() {
        return emitirsenhatriagemchamadacliente;
    }

    public void setEmitirsenhatriagemchamadacliente(String emitirsenhatriagemchamadacliente) {
        this.emitirsenhatriagemchamadacliente = emitirsenhatriagemchamadacliente;
    }

    public String getPrioridadetriagemachamadacliente() {
        return prioridadetriagemachamadacliente;
    }

    public void setPrioridadetriagemachamadacliente(String prioridadetriagemachamadacliente) {
        this.prioridadetriagemachamadacliente = prioridadetriagemachamadacliente;
    }

    public int getQuantidadetriagemchamadaclientenormal() {
        return quantidadetriagemchamadaclientenormal;
    }

    public void setQuantidadetriagemchamadaclientenormal(int quantidadetriagemchamadaclientenormal) {
        this.quantidadetriagemchamadaclientenormal = quantidadetriagemchamadaclientenormal;
    }

    public int getQuantidadetriagemchamadaclienteprioridade() {
        return quantidadetriagemchamadaclienteprioridade;
    }

    public void setQuantidadetriagemchamadaclienteprioridade(int quantidadetriagemchamadaclienteprioridade) {
        this.quantidadetriagemchamadaclienteprioridade = quantidadetriagemchamadaclienteprioridade;
    }

    public int getQuantidadetriagemachamadacliente() {
        return quantidadetriagemachamadacliente;
    }

    public void setQuantidadetriagemachamadacliente(int quantidadetriagemachamadacliente) {
        this.quantidadetriagemachamadacliente = quantidadetriagemachamadacliente;
    }

    public String getStatustriagemchamadacliente() {
        return statustriagemchamadacliente;
    }

    public void setStatustriagemchamadacliente(String statustriagemchamadacliente) {
        this.statustriagemchamadacliente = statustriagemchamadacliente;
    }

    public Timestamp getDatainiciotriagemchamadacliente() {
        return datainiciotriagemchamadacliente;
    }

    public void setDatainiciotriagemchamadacliente(Timestamp datainiciotriagemchamadacliente) {
        this.datainiciotriagemchamadacliente = datainiciotriagemchamadacliente;
    }

    public Timestamp getDatafimtriagemchamadacliente() {
        return datafimtriagemchamadacliente;
    }

    public void setDatafimtriagemchamadacliente(Timestamp datafimtriagemchamadacliente) {
        this.datafimtriagemchamadacliente = datafimtriagemchamadacliente;
    }

    public Timestamp getDatachamadatriagemchamadacliente() {
        return datachamadatriagemchamadacliente;
    }

    public Date getDatainiciotiradatriagemchamadacliente() {
        return datainiciotiradatriagemchamadacliente;
    }

    public void setDatainiciotiradatriagemchamadacliente(Date datainiciotiradatriagemchamadacliente) {
        this.datainiciotiradatriagemchamadacliente = datainiciotiradatriagemchamadacliente;
    }

    public void setDatachamadatriagemchamadacliente(Timestamp datachamadatriagemchamadacliente) {
        this.datachamadatriagemchamadacliente = datachamadatriagemchamadacliente;
    }

    public String getTempodeesperatriagemchamadacliente() {
        return tempodeesperatriagemchamadacliente;
    }

    public void setTempodeesperatriagemchamadacliente(String tempodeesperatriagemchamadacliente) {
        this.tempodeesperatriagemchamadacliente = tempodeesperatriagemchamadacliente;
    }

    public UnidadeTransfer getUnidadeTransfer() {
        return unidadeTransfer;
    }

    public void setUnidadeTransfer(UnidadeTransfer unidadeTransfer) {
        this.unidadeTransfer = unidadeTransfer;
    }

}
