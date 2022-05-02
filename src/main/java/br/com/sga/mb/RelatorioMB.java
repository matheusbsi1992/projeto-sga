/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.sga.mb;

import br.com.sga.bo.RelatorioBO;
import br.com.sga.bo.ValidarSgaBO;
import br.com.sga.relatorio.RelatorioSenhas;
import br.com.sga.transfer.RelatorioTransfer;
import br.com.sga.util.Util;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.Serializable;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.ValidatorException;
import org.primefaces.component.export.ExcelOptions;
import org.primefaces.component.export.PDFOptions;

/**
 *
 * @author Jandisson
 */
@SessionScoped
@ManagedBean
public class RelatorioMB implements Serializable {

    public RelatorioMB() {

    }

    private RelatorioBO relatoriobo = new RelatorioBO();

    private ValidarSgaBO validarsgabo = new ValidarSgaBO();

    private List<RelatorioTransfer> relarotiotransfers = new ArrayList<RelatorioTransfer>();

    private RelatorioTransfer relatoriotransfer = new RelatorioTransfer();

    @PostConstruct
    public void init() {
        relatoriotransfer.setServicoeatendimento("");
        relatoriotransfer.setDatainicialrelatorio(new Util().convertUtilToSql(new Util().getPegaDataAtual()));
        relatoriotransfer.setDatafinallrelatorio(new Util().convertUtilToSql(new Util().getPegaDataAtual()));
        //setRelarotiotransfers(null);
    }

    /**
     * atributos com a caracteristica de ativacao
     */
    private boolean ativardatatable = false;
    private boolean ativarcolunaservico = false;
    private boolean ativarcolunastatus = false;
    private boolean ativarcolunaquantidadedeatendimentos = false;
    private boolean ativarsenha = false;
    private boolean ativardatachegada = false;
    private boolean ativardatachamada = false;
    private boolean ativardatafim = false;
    private boolean ativarduracao = false;
    private boolean ativartempodeespera = false;
    private boolean ativaratendente = false;
    private boolean ativarpaineldeatendimentoconcluido = false;
    private boolean ativardatainiciotiradatriagemchamadacliente = false;
    private boolean ativaremitirsenhastatus = false;
    private boolean ativartempomediodeatendimento = false;
    private boolean ativartempomediodedeslocamento = false;
    private boolean ativartempomediodeespera = false;
    private boolean ativartempototal = false;
    private boolean ativarquantidadedeatendimentos = false;

    /**
     * Métodos com a característica de obter o relatorio
     */
    public List<RelatorioTransfer> listarRelatorios() throws SQLException {

        setAtivardatatable(true);
        try {
            if (relatoriotransfer.getServicoeatendimento().equalsIgnoreCase("relatorioservicoglobal")) {
                setAtivarcolunaservico(true);
                setAtivarcolunastatus(true);
                setAtivarcolunaquantidadedeatendimentos(false);
                ativaremitirsenhastatus = ativardatainiciotiradatriagemchamadacliente = ativarsenha = ativardatachegada = ativardatachamada = ativardatafim = ativarduracao = ativartempodeespera = ativaratendente = false;
                ativartempomediodeatendimento = ativartempomediodedeslocamento = ativartempomediodeespera = ativartempototal = ativarquantidadedeatendimentos = false;
                relatoriotransfer.setTipodeservico("Servicos Disponíveis Global");
                relarotiotransfers = relatoriobo.listarTodoServicoDisponivel();
                if (relarotiotransfers.size() > 0) {
                    setAtivarpaineldeatendimentoconcluido(true);
                    return relarotiotransfers;
                } else {
                    setAtivarpaineldeatendimentoconcluido(false);
                    return null;
                }
            }
            if (relatoriotransfer.getServicoeatendimento().equalsIgnoreCase("relatorioservicodificado")) {
                setAtivarcolunaservico(true);
                setAtivarcolunaquantidadedeatendimentos(true);
                ativaremitirsenhastatus = ativardatainiciotiradatriagemchamadacliente = ativarsenha = ativardatachegada = ativardatachamada = ativardatafim = ativarduracao = ativartempodeespera = ativaratendente = ativarcolunastatus = false;
                ativartempomediodeatendimento = ativartempomediodedeslocamento = ativartempomediodeespera = ativartempototal = ativarquantidadedeatendimentos = false;
                relatoriotransfer.setTipodeservico("Serviços Codificados");
                relarotiotransfers = relatoriobo.listarTodoServicoCodificado(relatoriotransfer.getDatainicialrelatorio(), relatoriotransfer.getDatafinallrelatorio());
                if (relarotiotransfers.size() > 0) {
                    setAtivarpaineldeatendimentoconcluido(true);
                    return relarotiotransfers;
                } else {
                    setAtivarpaineldeatendimentoconcluido(false);
                    return null;
                }
            }
            if (relatoriotransfer.getServicoeatendimento().equalsIgnoreCase("relatorioservicoconcluido")) {
                setAtivarcolunastatus(false);
                setAtivarcolunaquantidadedeatendimentos(false);
                setAtivarcolunaservico(true);
                setAtivaremitirsenhastatus(false);
                ativardatainiciotiradatriagemchamadacliente = ativarsenha = ativardatachegada = ativardatachamada = ativardatafim = ativarduracao = ativartempodeespera = ativaratendente = true;
                ativartempomediodeatendimento = ativartempomediodedeslocamento = ativartempomediodeespera = ativartempototal = ativarquantidadedeatendimentos = false;
                relatoriotransfer.setTipodeservico("Serviços Concluídos");
                relarotiotransfers = relatoriobo.listarTodoServicoouAtendimentoConcluido(relatoriotransfer.getDatainicialrelatorio(), relatoriotransfer.getDatafinallrelatorio());
                if (relarotiotransfers.size() > 0) {
                    setAtivarpaineldeatendimentoconcluido(true);
                    return relarotiotransfers;
                } else {
                    setAtivarpaineldeatendimentoconcluido(false);
                    return null;
                }
            }
            if (relatoriotransfer.getServicoeatendimento().equalsIgnoreCase("relatorioatendimentostatus")) {
                setAtivarcolunastatus(false);
                setAtivarcolunaquantidadedeatendimentos(false);
                setAtivarcolunaservico(true);
                ativaremitirsenhastatus = ativardatainiciotiradatriagemchamadacliente = ativarsenha = ativardatachegada = ativardatachamada = ativardatafim = ativarduracao = ativartempodeespera = ativaratendente = true;
                ativartempomediodeatendimento = ativartempomediodedeslocamento = ativartempomediodeespera = ativartempototal = ativarquantidadedeatendimentos = false;
                relatoriotransfer.setTipodeservico("Atendimentos em Todos os Status");
                relarotiotransfers = relatoriobo.listarTodoServicoAtendimentoEmTodosOsStatus(relatoriotransfer.getDatainicialrelatorio(), relatoriotransfer.getDatafinallrelatorio());
                if (relarotiotransfers.size() > 0) {
                    setAtivarpaineldeatendimentoconcluido(true);
                    return relarotiotransfers;
                } else {
                    setAtivarpaineldeatendimentoconcluido(false);
                    return null;
                }
            }
            if (relatoriotransfer.getServicoeatendimento().equalsIgnoreCase("relatorioatempomedioporatendente")) {
                setAtivarcolunastatus(false);
                setAtivarcolunaquantidadedeatendimentos(false);
                ativarcolunaservico = ativaremitirsenhastatus = ativardatainiciotiradatriagemchamadacliente = ativarsenha = ativardatachegada = ativardatachamada = ativardatafim = ativarduracao = ativartempodeespera = false;
                ativartempomediodeatendimento = ativartempomediodedeslocamento = ativartempomediodeespera = ativartempototal = ativarquantidadedeatendimentos = ativaratendente = true;
                relatoriotransfer.setTipodeservico("Tempos médios por Atendente");
                relarotiotransfers = relatoriobo.listarTempoMediodeServicoAtendimento(relatoriotransfer.getDatainicialrelatorio(), relatoriotransfer.getDatafinallrelatorio());
                if (relarotiotransfers.size() > 0) {
                    setAtivarpaineldeatendimentoconcluido(true);
                    return relarotiotransfers;
                } else {
                    setAtivarpaineldeatendimentoconcluido(false);
                    return null;
                }
            }
            FacesContext fc = FacesContext.getCurrentInstance();
            fc.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "CONSULTADO COM SUCESSO!!!", ""));
        } catch (Exception ex) {
            FacesContext fc = FacesContext.getCurrentInstance();
            fc.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "ERROR AO CONSULTAR!!!", ""));
        }
        return null;
    }

    public void customizationOptions() {
        ExcelOptions excelOpt = new ExcelOptions();
        excelOpt.setFacetBgColor("#F88017");
        excelOpt.setFacetFontSize("10");
        excelOpt.setFacetFontColor("#0000ff");
        excelOpt.setFacetFontStyle("BOLD");
        excelOpt.setCellFontColor("#00ff00");
        excelOpt.setCellFontSize("25");

        PDFOptions pdfOpt = new PDFOptions();
        pdfOpt.setFacetBgColor("#F88017");
        pdfOpt.setFacetFontColor("#0000ff");
        pdfOpt.setFacetFontStyle("SERIF");
        pdfOpt.setCellFontSize("22");

    }

    /**
     * Validacoes
     */
    //metodo para validar data inicial
    public boolean validarDataInicial(FacesContext context, UIComponent component, Object value) throws ValidatorException, SQLException {
        return validarsgabo.validarDataInicialRelatorio(context, component, this);
    }

    //metodo para validar data final
//    public boolean validarDataFinal(FacesContext context, UIComponent component, Object value) throws ValidatorException, SQLException {
//        return validarsgabo.validarDataFinal(context, component, this);
//    }
    public List<RelatorioTransfer> getRelarotiotransfers() {
        return relarotiotransfers;
    }

    public void setRelarotiotransfers(List<RelatorioTransfer> relarotiotransfers) {
        this.relarotiotransfers = relarotiotransfers;
    }

    public RelatorioTransfer getRelatoriotransfer() {
        return relatoriotransfer;
    }

    public void setRelatoriotransfer(RelatorioTransfer relatoriotransfer) {
        this.relatoriotransfer = relatoriotransfer;
    }

    /**
     * getters e setters das ativacoes
     */
    public boolean isAtivardatatable() {
        return ativardatatable;
    }

    public void setAtivardatatable(boolean ativardatatable) {
        this.ativardatatable = ativardatatable;
    }

    public boolean isAtivarcolunaquantidadedeatendimentos() {
        return ativarcolunaquantidadedeatendimentos;
    }

    public void setAtivarcolunaquantidadedeatendimentos(boolean ativarcolunaquantidadedeatendimentos) {
        this.ativarcolunaquantidadedeatendimentos = ativarcolunaquantidadedeatendimentos;
    }

    public boolean isAtivarcolunaservico() {
        return ativarcolunaservico;
    }

    public void setAtivarcolunaservico(boolean ativarcolunaservico) {
        this.ativarcolunaservico = ativarcolunaservico;
    }

    public boolean isAtivarcolunastatus() {
        return ativarcolunastatus;
    }

    public void setAtivarcolunastatus(boolean ativarcolunastatus) {
        this.ativarcolunastatus = ativarcolunastatus;
    }

    public boolean isAtivarsenha() {
        return ativarsenha;
    }

    public void setAtivarsenha(boolean ativarsenha) {
        this.ativarsenha = ativarsenha;
    }

    public boolean isAtivardatachegada() {
        return ativardatachegada;
    }

    public void setAtivardatachegada(boolean ativardatachegada) {
        this.ativardatachegada = ativardatachegada;
    }

    public boolean isAtivardatachamada() {
        return ativardatachamada;
    }

    public void setAtivardatachamada(boolean ativardatachamada) {
        this.ativardatachamada = ativardatachamada;
    }

    public boolean isAtivardatafim() {
        return ativardatafim;
    }

    public void setAtivardatafim(boolean ativardatafim) {
        this.ativardatafim = ativardatafim;
    }

    public boolean isAtivarduracao() {
        return ativarduracao;
    }

    public void setAtivarduracao(boolean ativarduracao) {
        this.ativarduracao = ativarduracao;
    }

    public boolean isAtivartempodeespera() {
        return ativartempodeespera;
    }

    public void setAtivartempodeespera(boolean ativartempodeespera) {
        this.ativartempodeespera = ativartempodeespera;
    }

    public boolean isAtivaratendente() {
        return ativaratendente;
    }

    public void setAtivaratendente(boolean ativaratendente) {
        this.ativaratendente = ativaratendente;
    }

    public boolean isAtivarpaineldeatendimentoconcluido() {
        return ativarpaineldeatendimentoconcluido;
    }

    public void setAtivarpaineldeatendimentoconcluido(boolean ativarpaineldeatendimentoconcluido) {
        this.ativarpaineldeatendimentoconcluido = ativarpaineldeatendimentoconcluido;
    }

    public boolean isAtivardatainiciotiradatriagemchamadacliente() {
        return ativardatainiciotiradatriagemchamadacliente;
    }

    public void setAtivardatainiciotiradatriagemchamadacliente(boolean ativardatainiciotiradatriagemchamadacliente) {
        this.ativardatainiciotiradatriagemchamadacliente = ativardatainiciotiradatriagemchamadacliente;
    }

    public boolean isAtivaremitirsenhastatus() {
        return ativaremitirsenhastatus;
    }

    public void setAtivaremitirsenhastatus(boolean ativaremitirsenhastatus) {
        this.ativaremitirsenhastatus = ativaremitirsenhastatus;
    }

    public boolean isAtivartempomediodeatendimento() {
        return ativartempomediodeatendimento;
    }

    public void setAtivartempomediodeatendimento(boolean ativartempomediodeatendimento) {
        this.ativartempomediodeatendimento = ativartempomediodeatendimento;
    }

    public boolean isAtivartempomediodedeslocamento() {
        return ativartempomediodedeslocamento;
    }

    public void setAtivartempomediodedeslocamento(boolean ativartempomediodedeslocamento) {
        this.ativartempomediodedeslocamento = ativartempomediodedeslocamento;
    }

    public boolean isAtivartempomediodeespera() {
        return ativartempomediodeespera;
    }

    public void setAtivartempomediodeespera(boolean ativartempomediodeespera) {
        this.ativartempomediodeespera = ativartempomediodeespera;
    }

    public boolean isAtivartempototal() {
        return ativartempototal;
    }

    public void setAtivartempototal(boolean ativartempototal) {
        this.ativartempototal = ativartempototal;
    }

    public boolean isAtivarquantidadedeatendimentos() {
        return ativarquantidadedeatendimentos;
    }

    public void setAtivarquantidadedeatendimentos(boolean ativarquantidadedeatendimentos) {
        this.ativarquantidadedeatendimentos = ativarquantidadedeatendimentos;
    }

}
