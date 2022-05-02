/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.sga.mb;

import br.com.sga.bo.MonitorChamadaClienteBO;
import br.com.sga.bo.TriagemChamadaClienteBO;
import br.com.sga.relatorio.RelatorioSenhas;

import br.com.sga.transfer.ServicoTransfer;
import br.com.sga.transfer.TriagemChamadaClienteTransfer;
import br.com.sga.util.Util;

import java.io.IOException;
import java.io.Serializable;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;

import java.util.Map;
import java.util.List;
import static java.util.stream.Collectors.toList;
import javax.annotation.PostConstruct;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;

import javax.faces.component.UIInput;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import net.sf.jasperreports.engine.JRException;
import javax.faces.bean.ViewScoped;
import javax.faces.model.SelectItem;
import javax.faces.model.SelectItemGroup;
import org.omnifaces.cdi.*;

/**
 *
 * @author Jandisson
 */
@ManagedBean
@ViewScoped
public class MonitorChamadaClienteMB implements Serializable {

    private TriagemChamadaClienteTransfer triagemchamadaclientetransfer = new TriagemChamadaClienteTransfer();

    private TriagemChamadaClienteBO triagemchamadaclienteBO = new TriagemChamadaClienteBO();
    private MonitorChamadaClienteBO monitorchamadaclienteBO = new MonitorChamadaClienteBO();

    private List<SelectItem> listarsiglas = new ArrayList<>();

    private RelatorioSenhas relatorio = new RelatorioSenhas();

    @Inject
    @Push(channel = "monitorchamadaclientepushmbmb")
    private PushContext monitorchamadaclientepushmbmb;

    @Inject
    @Push(channel = "monitorchamadaclientepushauxmbmb")
    private PushContext monitorchamadaclientepushauxmbmb;

    @Inject
    @Push(channel = "monitorchamadaclienteatendimento")
    private PushContext monitorchamadaclienteatendimento;

    @Inject
    @Push(channel = "monitorchamadaclientesenhaspushmbmb")
    private PushContext monitorchamadaclientesenhaspushmbmb;

    

    /**
     * Atualizar dialogo da senha selecionada;
     */
//    public void atualizarDialogoSenhasMonitoramento() {
//        monitorchamadaclientepushauxmbmb.send("monitorchamadaclientepushauxmbmb");
//    }

    //metodo responsavel por consultar pelo nome da senha
    public void deletarTriagemChamadaCliente() throws SQLException {
        String nometriagemachamadacliente = null;
        try {
            FacesContext context = FacesContext.getCurrentInstance();
            Map<String, String> params = context.getExternalContext().getRequestParameterMap();
            nometriagemachamadacliente = params.get("nometriagemachamadacliente");
            monitorchamadaclienteBO.atualizarCancelarSenhaMonitorTriagemChamadaCliente(nometriagemachamadacliente, LoginMB.getInstance().getUsuariotransfer().getNomeusuario()); //To change body of generated methods, choose Tools | Templates.

            if (nometriagemachamadacliente != null || nometriagemachamadacliente.trim().length() > 0) {

                FacesContext fc = FacesContext.getCurrentInstance();
                fc.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "SENHA(" + nometriagemachamadacliente + ") CANCELADA COM SUCESSO!!!", ""));
                nometriagemachamadacliente = "";
            } else {
                if (nometriagemachamadacliente.trim().length() == 0) {
                    FacesContext fc = FacesContext.getCurrentInstance();
                    fc.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "SENHA CANCELADA ANTERIORMENTE!!!", ""));
                }
            }
        } catch (Exception ex) {
            FacesContext fc = FacesContext.getCurrentInstance();
            fc.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "ERROR AO DELETAR A SENHA!!!", ""));
        }
        //atrualizar grid de monitoramento
        monitorchamadaclientepushmbmb.send("monitorchamadaclientepushmbmb");
        //atualizar grid de atendimento
        monitorchamadaclienteatendimento.send("monitorchamadaclienteatendimento");
        //atualizar grid de monitoramento
        monitorchamadaclientesenhaspushmbmb.send("monitorchamadaclientesenhaspushmbmb");

    }

    /**
     * MÉTODO RESPONSÁVEL POR ALTERAR OU TRANSFERIR A SENHA DO CLIENTE ISTO
     * IDENTIFICA, O NOME DA SENHA/NÚMERO/TRIAGEM FAZENDO A ALTERAÇÃO DE
     * INATIVO/I E CRIANDO UM NOVO SERVIÇO, COM A OBTENÇÃO DESTA
     * TRIAGEM(SENHA/NÚMERO), PARA O ID DA TRIAGEM ALTERNATIVA E SERVIÇO.
     *
     * @param sigla
     * @param prioridade
     * @param nometriagemachamadacliente
     * @throws java.sql.SQLException
     */
    public void alterarOuTransferirSenha() throws SQLException {
        try {
            FacesContext context = FacesContext.getCurrentInstance();
            Map<String, String> params = context.getExternalContext().getRequestParameterMap();
            String siglatriagemalternativa = params.get("nometriagemachamadaclienteprincipal");

            MBBase mBBase = new MBBase();
            String nomeusuario = mBBase.valorParam("nomeusuario");

            //pergunta se a senha esta ativa ou nao
            if (monitorchamadaclienteBO.nomeDaTriagemChamadaClienteAtivaouInativa(siglatriagemalternativa) == false) {
                FacesContext fc = FacesContext.getCurrentInstance();
                fc.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "SENHA JÁ FOI ALTERADA ANTERIORMENTE!!!", ""));
                return;
            }

            //cancelar a senha anterior atraves do numero informado no dialogo
            monitorchamadaclienteBO.atualizarCancelarSenhaMonitorTriagemChamadaCliente(siglatriagemalternativa, nomeusuario.toUpperCase()); //To change body of generated methods, choose Tools | Templates.

            Object prioridadeounormal = ((UIInput) FacesContext.getCurrentInstance().getViewRoot().findComponent("consultarmonitor:prioridade")).getValue();

            if (prioridadeounormal != null && prioridadeounormal.toString().equalsIgnoreCase("CONVENCIONAL")) {
                triagemchamadaclientetransfer.setNormalchamadatriagemchamadacliente("CONVENCIONAL");
                triagemchamadaclientetransfer.setPrioridadechamadatriagemchamadacliente(null);
            } else {
                triagemchamadaclientetransfer.setPrioridadechamadatriagemchamadacliente("PRIORIDADE");
                triagemchamadaclientetransfer.setNormalchamadatriagemchamadacliente(null);
            }

            //pegar nome do servico
            Object sigla = ((UIInput) FacesContext.getCurrentInstance().getViewRoot().findComponent("consultarmonitor:sigla")).getValue();
            triagemchamadaclientetransfer.setSiglatriagemalternativa(sigla.toString());

            //buscar o id de servico e de triagem alternativa atraves da sigla
            List<TriagemChamadaClienteTransfer> listamonitoramentoidservicoeidtriagemalternativa = monitorchamadaclienteBO.buscarTriagemAlternativaeServico(triagemchamadaclientetransfer.getSiglatriagemalternativa());

            listamonitoramentoidservicoeidtriagemalternativa.stream().map((next) -> {
                //id servico
                triagemchamadaclientetransfer.getServicotransfer().setId(next.getServicotransfer().getId());
                return next;
            }).forEach((next) -> {
                //id triagem alternativa
                triagemchamadaclientetransfer.setId(next.getId());
                //nome ou sigla da senha
                triagemchamadaclientetransfer.setSiglatriagemalternativa(next.getSiglatriagemalternativa());

            });

            //obter uma nova senha em outro servico ou no mesmo servico
            triagemchamadaclienteBO.inserirTriagemChamadaCliente(triagemchamadaclientetransfer);
            // ultima senha para imprimir direto desabilitado retornar a ultima senha e faz com que imprima a nova
            //relatorio.imprimirSenha(triagemchamadaclienteBO.retornarUltimaSenha(), "alertarsenha");

            FacesContext fc = FacesContext.getCurrentInstance();
            fc.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "SENHA (" + triagemchamadaclienteBO.retornarUltimaSenha() + ") CRIADA/ALTERADA COM SUCESSO!!!", ""));
        } catch (Exception ex) {
            FacesContext fc = FacesContext.getCurrentInstance();
            fc.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "ERROR AO CRIAR A SENHA!!!", ""));
        }
        //atrualizar grid de monitoramento
        monitorchamadaclientepushmbmb.send("monitorchamadaclientepushmbmb");
        //atualizar grid de atendimento
        monitorchamadaclienteatendimento.send("monitorchamadaclienteatendimento");

        //atualizar grid de monitoramento
        monitorchamadaclientesenhaspushmbmb.send("monitorchamadaclientesenhaspushmbmb");
    }

    /**
     * Métodos com a característica de imprimir a senha do paciente em uma nova
     * pagina
     *
     * @throws java.io.IOException
     * @throws net.sf.jasperreports.engine.JRException
     */
    public void imprimirSenha() throws IOException, JRException, SQLException {
        FacesContext context = FacesContext.getCurrentInstance();
        Map<String, String> paramsm = context.getExternalContext().getRequestParameterMap();
        String senhanometriagemachamadacliente = paramsm.get("senhanometriagemachamadacliente");
        relatorio.imprimirSenha(senhanometriagemachamadacliente, "monitortriagemchamadacliente");
        context = null;
    }

    public void listarSiglas() throws SQLException {
        listarsiglas = monitorchamadaclienteBO.listarTodasSiglasAgrupadas();

    }

    /**
     * MÉTODOS GETTERS E SETTERS
     */
    public TriagemChamadaClienteTransfer getTriagemchamadaclientetransfer() {
        return triagemchamadaclientetransfer;
    }

    public void setTriagemchamadaclientetransfer(TriagemChamadaClienteTransfer triagemchamadaclientetransfer) {
        this.triagemchamadaclientetransfer = triagemchamadaclientetransfer;
    }

    public List<SelectItem> getListarsiglas(Short id) throws SQLException {
        if (id == 1) {
            listarsiglas = monitorchamadaclienteBO.listarTodasSiglasAgrupadas();
        } else {
            listarsiglas = monitorchamadaclienteBO.listarTodasSiglasPorServicodoUsuarioAgrupadas(id);
        }
        return listarsiglas;
    }

    public void setListarsiglas(List<SelectItem> listarsiglas) {
        this.listarsiglas = listarsiglas;
    }

   

}
