/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.sga.mb;

import br.com.sga.bo.GraficosBO;
import br.com.sga.bo.ValidarSgaBO;
import br.com.sga.transfer.RelatorioTransfer;
import br.com.sga.util.Util;
import com.google.gson.Gson;
import java.io.IOException;
import java.io.Serializable;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.bean.ViewScoped;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.ValidatorException;
import javax.inject.Inject;
import org.omnifaces.cdi.Push;
import org.omnifaces.cdi.PushContext;

/**
 *
 * @author Jandisson
 */
@ManagedBean(eager = true)
@ApplicationScoped
public class GraficosMB implements Serializable {

    public GraficosMB() {
    }

    private RelatorioTransfer relatoriotransfer = new RelatorioTransfer();

    private ValidarSgaBO validarsgabo = new ValidarSgaBO();

    private List<RelatorioTransfer> relarotiotransfers = new ArrayList<RelatorioTransfer>();

    private GraficosBO graficosbo = new GraficosBO();

    private List listMapPie = new ArrayList<>();

    private Map<String, Object> mapPie;

    private String chartData;

    //variaveis de status
    private boolean recebevaloresstatus = false;
    private boolean recebevaloresstatusstop = false;
    private int quantidadestatus = 0;

    //variaveis atendimento por serviço
    private boolean recebevaloratendimentoservico = false;
    private boolean recebevaloratendimentoservicostop = false;
    private int quantidadeatendimentosporservico = 0;

    private String valordesignado;

    private boolean existevalordadataverdadeirooufalso;

    @PostConstruct
    public void init() {
        relatoriotransfer.setServicoeatendimento("");
        relatoriotransfer.setDatainicialrelatorio(new Util().convertUtilToSql(new Util().getPegaDataAtual()));
        relatoriotransfer.setDatafinallrelatorio(new Util().convertUtilToSql(new Util().getPegaDataAtual()));
    }

    /*Dados coletados*/
    public void listarValoresStatus() {

        //passa valor de quantidade de atendimento servico para 0
        quantidadeatendimentosporservico = (0);
        recebevaloratendimentoservico = (false);
        recebevaloratendimentoservicostop = (false);

//se condicao para status entao receba valor senao pause o poller 
        if (getValordesignado().equalsIgnoreCase("graficoporstatus") && quantidadestatus == 0) {
            setChartData(new Gson().toJson(listMapPie));
        } else {
            if (quantidadestatus > 0) {
                setRecebevaloresstatus(true);
                setRecebevaloresstatusstop(true);
            }
        }
        quantidadestatus++;
        //--encerra condicao status
    }

    public void listarValoresAtendimentoServico() {
        //passa valor de quantidade de status para e o painel de status
        quantidadestatus = (0);
        recebevaloresstatus = (false);
        recebevaloresstatusstop = (false);

        //se condicao para atendimento entao receba valor senao pause o poller 
        if (getValordesignado().equalsIgnoreCase("graficoporatendimentoservico") && quantidadeatendimentosporservico == 0) {
            setChartData(new Gson().toJson(listMapPie));
        } else {
            if (quantidadeatendimentosporservico > 0) {
                setRecebevaloratendimentoservico(true);
                setRecebevaloratendimentoservicostop(true);
            }
        }
        quantidadeatendimentosporservico++;
        //--encerra condicao servico
    }

    /**
     * Métodos com a característica de obter gráficos
     */
    public void listarGraficos() throws SQLException {
        try {
            //inicia relacao sobre o grafico de satus
            if (relatoriotransfer.getServicoeatendimento().equalsIgnoreCase("graficoporstatus")) {
                listMapPie = new ArrayList();
                mapPie = new HashMap<>();
                graficosbo.listarTodoGraficoServicoPorStatus(relatoriotransfer.getDatainicialrelatorio(), relatoriotransfer.getDatafinallrelatorio())
                        .stream()
                        .map((relarotiotransfer) -> {
                            mapPie = new HashMap<>();
                            return relarotiotransfer;
                        })
                        .map((relarotiotransfer) -> {
                            mapPie.put("name", relarotiotransfer.getEmitirsenhatriagemchamadacliente());
                            return relarotiotransfer;
                        })
                        .map((relarotiotransfer) -> {
                            mapPie.put("y", relarotiotransfer.getQuantidadedestatus());
                            return relarotiotransfer;
                        })
                        .forEach((_item) -> {
                            if (_item.getEmitirsenhatriagemchamadacliente() != null || !_item.getEmitirsenhatriagemchamadacliente().equals("")) {
                                listMapPie.add(mapPie);
                                setRecebevaloresstatus(true);
                                setRecebevaloratendimentoservico(false);
                                setValordesignado("graficoporstatus");
                                relatoriotransfer.setTipodeservico("Atendimentos Por Status");
                            }
                        });
                if (!listMapPie.isEmpty() && relatoriotransfer.getServicoeatendimento().equalsIgnoreCase("graficoporstatus")) {
                    FacesContext fc = FacesContext.getCurrentInstance();
                    fc.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "GRÁFICO DE STATUS CONSULTADO COM SUCESSO!!!", ""));
                }
            }
            if (listMapPie.isEmpty() && relatoriotransfer.getServicoeatendimento().equalsIgnoreCase("graficoporstatus")) {
                FacesContext fc = FacesContext.getCurrentInstance();
                fc.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "GRÁFICO DE STATUS NÃO PODE SER EXIBIDO!!! POR FAVOR, VERIFIQUE A DATA INICIAL E FINAL!!!", ""));
                relatoriotransfer.setServicoeatendimento("");
                return;
            }
            //finaliza relacao sobre o grafico de satus
            
            //inicia relacao sobre o grafico de servico
            if (relatoriotransfer.getServicoeatendimento().equalsIgnoreCase("graficoporatendimentoservico")) {
                listMapPie = new ArrayList();
                mapPie = new HashMap<>();
                graficosbo.listarTodoGraficoAtendimentoPorServico(relatoriotransfer.getDatainicialrelatorio(), relatoriotransfer.getDatafinallrelatorio()).stream().map((relarotiotransfer) -> {
                    mapPie = new HashMap<>();
                    return relarotiotransfer;
                }).map((relarotiotransfer) -> {
                    mapPie.put("name", relarotiotransfer.getServicoTransfer().getNomeservico());
                    return relarotiotransfer;
                }).map((relarotiotransfer) -> {
                    mapPie.put("y", relarotiotransfer.getQuantidadedeservicosatendidos());
                    return relarotiotransfer;
                }).forEach((_item) -> {
                    if (_item.getServicoTransfer().getNomeservico() != null || !_item.getServicoTransfer().getNomeservico().equals("")) {
                        listMapPie.add(mapPie);
                        setRecebevaloratendimentoservico(true);
                        setRecebevaloresstatus(false);
                        setValordesignado("graficoporatendimentoservico");
                        relatoriotransfer.setTipodeservico("Atendimentos Por Serviço");
                    }
                });
                if (!listMapPie.isEmpty() && relatoriotransfer.getServicoeatendimento().equalsIgnoreCase("graficoporatendimentoservico")) {
                    FacesContext fc = FacesContext.getCurrentInstance();
                    fc.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "GRÁFICO DE SERVIÇO CONSULTADO COM SUCESSO!!!", ""));
                }
            }
            if (relatoriotransfer.getServicoeatendimento().equalsIgnoreCase("graficoporatendimentoservico") && listMapPie.isEmpty()) {
                FacesContext fc = FacesContext.getCurrentInstance();
                fc.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "GRÁFICO DE SERVIÇO NÃO PODE SER EXIBIDO!!! POR FAVOR, VERIFIQUE A DATA INICIAL E FINAL!!!", ""));
                relatoriotransfer.setServicoeatendimento("");
                return;
            }
            //finaliza relacao sobre o grafico de servico
        } catch (Exception ex) {
            FacesContext fc = FacesContext.getCurrentInstance();
            fc.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "ERROR AO CONSULTAR!!!", ""));
        }
    }

    /**
     * Validacoes
     */
    //metodo para validar data inicial
    public boolean validarDataInicial(FacesContext context, UIComponent component, Object value) throws ValidatorException, SQLException {
        if (validarsgabo.validarDataInicialGrafico(context, component, this) == false) {
            recebevaloratendimentoservico = (false);
            recebevaloratendimentoservicostop = (false);
            recebevaloresstatus = (false);
            recebevaloresstatusstop = (false);
            return false;
        }
        return true;
    }

    /**
     * Métodos getters e setters
     */
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

    public String getChartData() {
        return chartData;
    }

    public void setChartData(String chartData) {
        this.chartData = chartData;
    }

    public boolean isRecebevaloresstatus() {
        return recebevaloresstatus;
    }

    public void setRecebevaloresstatus(boolean recebevaloresstatus) {
        this.recebevaloresstatus = recebevaloresstatus;
    }

    public boolean isRecebevaloresstatusstop() {
        return recebevaloresstatusstop;
    }

    public void setRecebevaloresstatusstop(boolean recebevaloresstatusstop) {
        this.recebevaloresstatusstop = recebevaloresstatusstop;
    }

    public boolean isRecebevaloratendimentoservico() {
        return recebevaloratendimentoservico;
    }

    public void setRecebevaloratendimentoservico(boolean recebevaloratendimentoservico) {
        this.recebevaloratendimentoservico = recebevaloratendimentoservico;
    }

    public boolean isRecebevaloratendimentoservicostop() {
        return recebevaloratendimentoservicostop;
    }

    public void setRecebevaloratendimentoservicostop(boolean recebevaloratendimentoservicostop) {
        this.recebevaloratendimentoservicostop = recebevaloratendimentoservicostop;
    }

    public String getValordesignado() {
        return valordesignado;
    }

    public void setValordesignado(String valordesignado) {
        this.valordesignado = valordesignado;
    }

    public int getQuantidadestatus() {
        return quantidadestatus;
    }

    public void setQuantidadestatus(int quantidadestatus) {
        this.quantidadestatus = quantidadestatus;
    }

    public int getQuantidadeatendimentosporservico() {
        return quantidadeatendimentosporservico;
    }

    public void setQuantidadeatendimentosporservico(int quantidadeatendimentosporservico) {
        this.quantidadeatendimentosporservico = quantidadeatendimentosporservico;
    }

    public boolean isExistevalordadataverdadeirooufalso() {
        return existevalordadataverdadeirooufalso;
    }

    public void setExistevalordadataverdadeirooufalso(boolean existevalordadataverdadeirooufalso) {
        this.existevalordadataverdadeirooufalso = existevalordadataverdadeirooufalso;
    }

}
