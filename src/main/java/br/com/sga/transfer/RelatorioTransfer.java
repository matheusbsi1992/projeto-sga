/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.sga.transfer;

import java.util.Date;

/**
 *
 * @author Jandisson
 */
public class RelatorioTransfer extends TriagemChamadaClienteTransfer {

    public RelatorioTransfer() {
    }

    private ServicoTransfer servicoTransfer = new ServicoTransfer();

    private String servicoeatendimento;

    private String tipodeservico;

    private String usuario;

    private String duracao;

    private int quantidadedeservicosatendidos;
    
    private int quantidadedestatus;

    private String tempomediodeespera;

    private String tempomediodedeslocamento;

    private String tempomediodeatendimento;

    private String tempototal;

    private Date datainicialrelatorio;

    private Date datafinallrelatorio;

    public ServicoTransfer getServicoTransfer() {
        return servicoTransfer;
    }

    public void setServicoTransfer(ServicoTransfer servicoTransfer) {
        this.servicoTransfer = servicoTransfer;
    }

    public String getServicoeatendimento() {
        return servicoeatendimento;
    }

    public void setServicoeatendimento(String servicoeatendimento) {
        this.servicoeatendimento = servicoeatendimento;
    }

    public String getTempomediodeespera() {
        return tempomediodeespera;
    }

    public void setTempomediodeespera(String tempomediodeespera) {
        this.tempomediodeespera = tempomediodeespera;
    }

    public String getTempomediodedeslocamento() {
        return tempomediodedeslocamento;
    }

    public void setTempomediodedeslocamento(String tempomediodedeslocamento) {
        this.tempomediodedeslocamento = tempomediodedeslocamento;
    }

    public String getTempomediodeatendimento() {
        return tempomediodeatendimento;
    }

    public void setTempomediodeatendimento(String tempomediodeatendimento) {
        this.tempomediodeatendimento = tempomediodeatendimento;
    }

    public String getTempototal() {
        return tempototal;
    }

    public void setTempototal(String tempototal) {
        this.tempototal = tempototal;
    }

    public String getTipodeservico() {
        return tipodeservico;
    }

    public void setTipodeservico(String tipodeservico) {
        this.tipodeservico = tipodeservico;
    }

    public Date getDatainicialrelatorio() {
        return datainicialrelatorio;
    }

    public void setDatainicialrelatorio(Date datainicialrelatorio) {
        this.datainicialrelatorio = datainicialrelatorio;
    }

    public Date getDatafinallrelatorio() {
        return datafinallrelatorio;
    }

    public void setDatafinallrelatorio(Date datafinallrelatorio) {
        this.datafinallrelatorio = datafinallrelatorio;
    }

    public int getQuantidadedeservicosatendidos() {
        return quantidadedeservicosatendidos;
    }

    public void setQuantidadedeservicosatendidos(int quantidadedeservicosatendidos) {
        this.quantidadedeservicosatendidos = quantidadedeservicosatendidos;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getDuracao() {
        return duracao;
    }

    public void setDuracao(String duracao) {
        this.duracao = duracao;
    }

    public int getQuantidadedestatus() {
        return quantidadedestatus;
    }

    public void setQuantidadedestatus(int quantidadedestatus) {
        this.quantidadedestatus = quantidadedestatus;
    }
    
    

}
