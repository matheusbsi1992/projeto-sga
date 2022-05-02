/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.sga.mb;

import br.com.sga.bo.ServicoBO;
import br.com.sga.bo.TriagemAtendimentoChamadaClienteBO;
import br.com.sga.bo.ValidarSgaBO;
import br.com.sga.transfer.LocalTransfer;
import br.com.sga.transfer.ServicoTransfer;
import br.com.sga.transfer.TriagemAtendimentoClienteTransfer;

import java.io.Serializable;
import java.sql.SQLException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.bean.SessionScoped;
import javax.faces.component.UIComponent;
import javax.faces.component.UIInput;
import javax.faces.context.FacesContext;
import javax.faces.validator.ValidatorException;
import javax.faces.bean.ViewScoped;

import javax.inject.Inject;
import javax.inject.Named;

import org.omnifaces.cdi.*;

/**
 *
 * @author Jandisson
 */
@ManagedBean
@SessionScoped
public class TriagemAtendimentoClienteMB implements Serializable {

    public TriagemAtendimentoClienteMB() throws SQLException {
        carregarServico();
    }

    //cria canal e injeta para listar os dados na visao de atendimentochamadacliente
    @Inject
    @Push(channel = "counterAdvWeb")
    private PushContext counterAdvWeb;

    //cria canal e injeta para listar o dado de atendido
    @Inject
    @Push(channel = "counterAtendiWeb")
    private PushContext counterAtendiWeb;

    //cria canal e injeta para atualizar o grid 
    @Inject
    @Push(channel = "atualizarlistagrid")
    private PushContext atualizarlistagrid;

    //cria canal e injeta para atualizar o grid de atendimento
    @Inject
    @Push(channel = "atualizarlistagridatendimento")
    private PushContext atualizarlistagridatendimento;

    //criar variavel da classe servico
    private ServicoBO servicobo = new ServicoBO();

    //cria variavel da classe ValidarSgaBO
    private ValidarSgaBO validartriagematendimentocliente = new ValidarSgaBO();

    //cria variavel local triagematendimentochamadaclientetransfer
    private TriagemAtendimentoClienteTransfer triagematendimentochamadaclientetransfer = new TriagemAtendimentoClienteTransfer();

    //cria variavel local triagematendimentochamadaclientebo
    private TriagemAtendimentoChamadaClienteBO triagematendimentochamadaclientebo = new TriagemAtendimentoChamadaClienteBO();

    //cria lista para a obtencao dos dados
    private List<TriagemAtendimentoClienteTransfer> listartriagematendimentotransferchamadacliente = new ArrayList<TriagemAtendimentoClienteTransfer>();

    //cria servico para listar os dados
    private List<ServicoTransfer> servico = new ArrayList<ServicoTransfer>();

    //cria servico para listar os dados
    private List<LocalTransfer> local = new ArrayList<LocalTransfer>();

    //variaveis de obtencao de local e servico
    private Object localservico = null;
    private Object servicoaux = null;

    //cria variavel para ativar e desativar o painel de serviço e senha
    private boolean ativarpanelatendimentoservicoesenha = false;

    //cria variavel para ativar e desativar o botao de chamar o proximo
    private boolean ativarbotaochamarproximo = false;

    //cria variavel para ativar e desativar o botao de chamar o proximo
    private boolean ativarbotaochamarcliente = false;

    //cria variavel paa ativar cronometro
    private boolean ativarcronometro = false;

    //cria variavel para ativar dialogo de atendimento de verificacao
    private boolean ativaratendimento = false;

    //cria variavel para obter os dados da chamada do Paciente
    private Map<Object, Object> message = null;

    //cria variavel para receber o valor do numerolocal definido por atendimento
    private int numerolocal;

    //cria variavel para indicar o numero de senhas chamadas
    private int numerodesenhas = -1;

    //criar variavel countauxacessos
    private int countauxacessos = 0;

    //ativar e desativar o servico de cada usuario
    private boolean servicoAux = false;

    /**
     * Carregar servicos que estiverem ativos de um determinado usuario
     */
    public void carregarServico() throws SQLException {
        //System.out.println(LoginMB.getInstance().getUsuariotransfer().getNomeusuario().toUpperCase());
        //servico = triagematendimentochamadaclientebo.listarTriagemAtendimentoChamadaClienteAtendidoLocalcomServicoparaUsuario(LoginMB.getInstance().getUsuariotransfer().getNomeusuario().toUpperCase());
        servico = triagematendimentochamadaclientebo.listarTriagemAtendimentoChamadaClienteAtendidoServico();
        if (servico == null || servico.isEmpty()) {
            return;
        } else if (servico != null) {
            triagematendimentochamadaclientetransfer.getServicotransfer().setNomeservico(servico.iterator().next().getNomeservico());
        }
        // atualizarservicodousuario.send("atualizarservicodousuario");
    }

    /**
     * Metodo com as caracteristicas de desabilitar lista de senhas referente ao
     * tipo de usuario que venha a fazer as chamados. O servico diretamente com
     * o local, a chamada da senha, bem como o painel de atendimento. Fazendo a
     * aceitacao de limpar os dados referentes ao painel Atendimento.
     */
    public void listarGridRefreshServico() throws SQLException {
        //servico = null;
        //getTriagematendimentochamadaclientetransfer().getServicotransfer().setNomeservico(null);
        //listartriagematendimentotransferchamadacliente = null;
        setAtivarbotaochamarcliente(false);
        setAtivarpanelatendimentoservicoesenha(false);
        //setTriagematendimentochamadaclientetransfer(null);
        setAtivarbotaochamarproximo(false);
        //getTriagematendimentochamadaclientetransfer().getLocaltransfer().setNomelocal(null);
        getTriagematendimentochamadaclientetransfer().setNumerodolocal(0);
        getTriagematendimentochamadaclientetransfer().setDatainiciotriagemchamadacliente(null);
        getTriagematendimentochamadaclientetransfer().getServicotransfer().setNomeservico(null);
    }

    /**
     * Metodo com as caracteristicas de desativar todos os dados de painel de
     * atendimento
     */
    public void atendimentoDesativado() {
        getTriagematendimentochamadaclientetransfer().setNometriagemachamadacliente(null);
        getTriagematendimentochamadaclientetransfer().setDatainiciotriagemchamadacliente(null);
        //getTriagematendimentochamadaclientetransfer().getServicotransfer().setNomeservico(null);
        getTriagematendimentochamadaclientetransfer().setPrioridadetriagemachamadacliente(null);
    }

    /**
     * Metodo acima faz o pre-carregamento dos dados do usuario. Ja este inidica
     * qual sera o de cada usuario a ser utilizado.
     */
//    public void carregarServicoUsuario() throws SQLException {
//        MBBase mBBase = new MBBase();
//        String nomeusuario = mBBase.valorParam("nomeusuario");
//        servico = new ArrayList<>();
//        servico = triagematendimentochamadaclientebo.listarTriagemAtendimentoChamadaClienteAtendidoLocalcomServicoparaUsuario(nomeusuario.toUpperCase());
//
//        if (servico == null) {
//            FacesContext fc = FacesContext.getCurrentInstance();
//            fc.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "" + nomeusuario.toUpperCase() + " ,O SEU SERVICO NAO ESTA RECONHECIDO. SENAO VERIFIQUE COM O SEU ADMINISTRADOR SE EXISTEM SENHAS ATIVAS FAVOR TENTE NOVAMENTE!!!", ""));
//            return;
//        } else if (servico != null) {
//            triagematendimentochamadaclientetransfer.getServicotransfer().setNomeservico(servico.iterator().next().getNomeservico());
//        }
//        atualizarservicodousuario.send("atualizarservicodousuario");
//    }
//    public void consultarLocaleServicosCliente() throws SQLException {
//
//        //obter lcal
//        Object local = ((UIInput) FacesContext.getCurrentInstance().getViewRoot().findComponent("consultaratendimento:local")).getValue();
//        //obter servico
//        Object servicoaux = ((UIInput) FacesContext.getCurrentInstance().getViewRoot().findComponent("consultaratendimento:servico")).getValue();
//        if (local == null || servicoaux == null) {
//            return;
//        }
//        //obter local
//        triagematendimentochamadaclientetransfer.getLocaltransfer().setNomelocal(local.toString().trim());
//        //obter servico
//        triagematendimentochamadaclientetransfer.getServicotransfer().setNomeservico(servicoaux.toString().trim());
//
//        MBBase mBBase = new MBBase();
//        String nomeusuario = mBBase.valorParam("nomeusuario");
//
//        servicoAux = triagematendimentochamadaclientebo.existeTriagemAtendimentoChamadaClienteLocaleServico(local.toString().toUpperCase(), servicoaux.toString().toUpperCase(), nomeusuario.toUpperCase());
//
//        if (servicoAux == false) {
//
//            //desativa o painel de senha
//            setAtivarpanelatendimentoservicoesenha(false);
//            //desativa o botao de proximo
//            //setAtivarbotaochamarproximo(false);
//            setAtivarbotaochamarcliente(false);
//            FacesContext fc = FacesContext.getCurrentInstance();
//            fc.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "" + nomeusuario.toUpperCase() + " ,O SEU SERVICO NAO ESTA RECONHECIDO. SENAO VERIFIQUE COM O SEU ADMINISTRADOR SE EXISTEM SENHAS ATIVAS. FAVOR TENTE NOVAMENTE!!!", ""));
//            return;
//        } else {
//            consultarLocaleServicoCliente(nomeusuario);
//        }
//    }
    /**
     * MÉTODO RESPONSÁVEL POR IDENTIFICAR O LOCAL DE ATENDIMENTO E O SERVICO
     * EXISTENTE. SOBRE O USUARIO. SO PERMITINDO O QUE FOR DIRECIONADO/DESIGNADO
     *
     *
     * @throws java.sql.SQLException
     */
    public void consultarLocaleServicoCliente() throws SQLException {
        try {
            MBBase mBBase = new MBBase();
            String nomeusuario = mBBase.valorParam("nomeusuario");
            //obter lcal
            localservico = ((UIInput) FacesContext.getCurrentInstance().getViewRoot().findComponent("consultaratendimento:local")).getValue();
            //obter servico
            servicoaux = ((UIInput) FacesContext.getCurrentInstance().getViewRoot().findComponent("consultaratendimento:servico")).getValue();
            if (localservico == null || servicoaux == null) {
                FacesContext fc = FacesContext.getCurrentInstance();
                fc.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "SERVIÇO INATIVADO! FAVOR INFORME AO RESPONSÁVEL!!!", ""));
                return;
            }

            //obter local
            triagematendimentochamadaclientetransfer.getLocaltransfer().setNomelocal(localservico.toString().trim());
            //obter servico
            triagematendimentochamadaclientetransfer.getServicotransfer().setNomeservico(servicoaux.toString().trim());

            setNumerolocal(triagematendimentochamadaclientetransfer.getNumerodolocal());

            listartriagematendimentotransferchamadacliente = triagematendimentochamadaclientebo.buscarTriagemAtendimentoChamadaClienteLocaleServicoeCliente(triagematendimentochamadaclientetransfer.getLocaltransfer().getNomelocal(), triagematendimentochamadaclientetransfer.getServicotransfer().getNomeservico(), nomeusuario.toUpperCase());

            if (listartriagematendimentotransferchamadacliente.size() > 0) {
                FacesContext fc = FacesContext.getCurrentInstance();
                fc.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "OBTIDO CONSULTA DAS SENHAS E SERVIÇO COM SUCESSO!!!", ""));

                setAtivarpanelatendimentoservicoesenha(true);
                //ativar botao de chamar o proximo
                setAtivarbotaochamarproximo(true);
                //apaga os dados do painel de atendimento
                triagematendimentochamadaclientetransfer.setNometriagemachamadacliente(null);
                triagematendimentochamadaclientetransfer.setPrioridadetriagemachamadacliente(null);
                triagematendimentochamadaclientetransfer.setDatainiciotriagemchamadacliente(null);
            } else {
                //desativar painel
                setAtivarpanelatendimentoservicoesenha(false);
                //desativar botao de chamar o proximo
                setAtivarbotaochamarproximo(false);
                //desativar botao de chamar cliente
                setAtivarbotaochamarcliente(false);

                FacesContext fc = FacesContext.getCurrentInstance();
                fc.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "" + nomeusuario.toUpperCase() + ", O SEU SERVIÇO NÃO ESTÁ RECONHECIDO OU O SEU USUÁRIO NÃO DEVE ESTA ATIVO. FAVOR, VERIFIQUE COM O SEU ADMINISTRADOR E TENTE NOVAMENTE!!!", ""));
                return;
            }
            nomeusuario = "";

            numerodesenhas = - 1;

        } catch (Exception ex) {
            FacesContext fc = FacesContext.getCurrentInstance();
            fc.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "ERROR AO CONSULTAR AS SENHAS!!!", ""));
        }
    }

    /**
     * Método com a caracteristica de obter os valores do atendimento e passar
     * estes para a página de atendimentochamadacliente.xhtml
     */
    public void chamarCliente() throws SQLException {

        MBBase mBBase = new MBBase();
        String nomeusuario = mBBase.valorParam("nomeusuario");

        if (triagematendimentochamadaclientetransfer.getNometriagemachamadacliente() == null || triagematendimentochamadaclientetransfer.getNometriagemachamadacliente().equals("")) {
            FacesContext fc = FacesContext.getCurrentInstance();
            fc.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "SENHA NÃO SELECIONADA PARA O ATENDIMENTO!!!", ""));
        }

        //obtencao da variavel nometriagemchamadacliente
        message = new HashMap<Object, Object>();

        //numero do local de atendimento para guiche/sala/consultorio ou o que for determinado pelo administrador        
        message.put("numerodolocal", triagematendimentochamadaclientetransfer.getNumerodolocal());
        //nome do local de atendimento guiche/sala/consultorio ou que for determinado pelo administrador
        message.put("nomelocal", triagematendimentochamadaclientetransfer.getLocaltransfer().getNomelocal());
        //nome da prioridade: convencional/prioridade de atendimento ao cliente
        message.put("prioridadetriagemachamadacliente", triagematendimentochamadaclientetransfer.getPrioridadetriagemachamadacliente());
        //nome da senha/numero de atendimento ao cliente
        message.put("nometriagemachamadacliente", triagematendimentochamadaclientetransfer.getNometriagemachamadacliente());
        //nome do servico de atendimento ao cliente
        message.put("nomeservico", triagematendimentochamadaclientetransfer.getServicotransfer().getNomeservico());
        //obtem os dados para comunicacao
        if (triagematendimentochamadaclientetransfer.getNometriagemachamadacliente() != null) {
            //atualizar chamada
            triagematendimentochamadaclientebo.atualizarChamadadeAtendidoTriagemAtendimentoChamadaCliente(triagematendimentochamadaclientetransfer.getNometriagemachamadacliente(), triagematendimentochamadaclientetransfer.getNumerodolocal(), nomeusuario.toUpperCase());
            //obtem os dados para comunicacao
            counterAdvWeb.send(message);
        }
    }

    /**
     * Método com a caracteristica de listar os valores do atendimento e passar
     * estes para o painel de atendimento e para a página de
     * atendimentochamadacliente.xhtml
     */
    public void chamarProximoCliente() throws SQLException, IndexOutOfBoundsException {

        try {

            //message = new HashMap<Object, Object>();
            //se nao existir mais chamadas
            if (listartriagematendimentotransferchamadacliente.isEmpty()) {
                //nao deixa o botao de chamar o paciente o cliente ficar renderizado
                setAtivarbotaochamarcliente(false);
                //retorna o valor anterior
                numerodesenhas = -1;
                //retorno do metodo
                return;
            }
            //verifica o tamanho da lista se for menos ou igual ao numero de senha
            if ((listartriagematendimentotransferchamadacliente.size() - 1) <= numerodesenhas) {
                //deixa o botao de chamar o paciente o cliente ficar renderizado
                setAtivarbotaochamarcliente(true);
                //faz retornar para o inicio
                numerodesenhas = 0;
            } else {
                //deixa o botao de chamar o paciente o cliente ficar renderizado
                setAtivarbotaochamarcliente(true);
                //contar quantidade de vezes que o metodo foi acionado
                numerodesenhas++;
            }

            setValorTriagemAtendimento(listartriagematendimentotransferchamadacliente.get(numerodesenhas));

            //recebe novos dados para serem visualizados no painel
//        //numero do local de atendimento para guiche/sala/consultorio ou o que for determinado pelo administrador        
//        message.put("numerodolocal", listartriagematendimentotransferchamadacliente.get(numerodesenhas).getNumerodolocal());
//        //nome do local de atendimento guiche/sala/consultorio ou que for determinado pelo administrador
//        message.put("nomelocal", listartriagematendimentotransferchamadacliente.get(numerodesenhas).getLocaltransfer().getNomelocal());
//        //nome da prioridade: convencional/prioridade de atendimento ao cliente
//        message.put("prioridadetriagemachamadacliente", listartriagematendimentotransferchamadacliente.get(numerodesenhas).getPrioridadetriagemachamadacliente());
//        //nome da senha/numero de atendimento ao cliente
//        message.put("nometriagemachamadacliente", listartriagematendimentotransferchamadacliente.get(numerodesenhas).getNometriagemachamadacliente());
//        //atualizar chamada
//        triagematendimentochamadaclientebo.atualizarChamadadeAtendidoTriagemAtendimentoChamadaCliente(listartriagematendimentotransferchamadacliente.get(numerodesenhas).getNometriagemachamadacliente());
//        //obtem os dados para comunicacao
//        counterAdvWeb.send(message);
        } catch (ArrayIndexOutOfBoundsException ex) {
            //try catch responsavel pelo estouro da pilha do array    
        }
    }

    //método com a caracteristica de zerar os dados informando que o paciente ja foi antendido anteriormente
    private void zerarAtendimentoChamadaCliente() throws SQLException {
        //obtencao da variavel nometriagemchamadacliente
        message = new HashMap<Object, Object>();
        //numero do local de atendimento para guiche/sala/consultorio ou o que for determinado pelo administrador        
        message.put("numerodolocal", " ");
        //nome do local de atendimento guiche/sala/consultorio ou que for determinado pelo administrador
        message.put("nomelocal", "ATENDIMENTO FINALIZADO!");
        //nome da prioridade: convencional/prioridade de atendimento ao cliente
        message.put("prioridadetriagemachamadacliente", " ");
        //nome da senha/numero de atendimento ao cliente
        message.put("nometriagemachamadacliente", " ");
        //nome do servico de atendimento ao cliente
        message.put("nomeservico", " ");
        //obtem os dados para comunicacao
        counterAtendiWeb.send(message);
        //atualizar a minnha grid
        atualizarlistagrid.send("listargrid");
    }

    /**
     * Método com a caracteristica de atualizar o deslocamento do paciente antes
     * de iniciar o servico. Obtendo o valor da senha
     *
     */
    public void chamadaAtualizarDeslocamento() throws SQLException {
        try {
            MBBase mBBase = new MBBase();
            String nomeusuario = mBBase.valorParam("nomeusuario");
            if (triagematendimentochamadaclientetransfer.getNometriagemachamadacliente() == null || triagematendimentochamadaclientetransfer.getNometriagemachamadacliente().equals("")) {
                FacesContext fc = FacesContext.getCurrentInstance();
                fc.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "SERVIÇO NÃO INICIADO! SENHA NÃO SELECIONADA PARA O ATENDIMENTO!!!", ""));
                return;
            }

            //passa a senha/nomedatriagem para que seja atualizado
            triagematendimentochamadaclientebo.atualizarChamadadeDeslocamentoClienteAtendimentoTriagemAtendimentoChamadaCliente(triagematendimentochamadaclientetransfer.getNometriagemachamadacliente(), triagematendimentochamadaclientetransfer.getNumerodolocal(), nomeusuario.toUpperCase());

            //ativar o cronometro
            setAtivarcronometro(true);

            //desativar o botao de chamar o proximo
            setAtivarbotaochamarproximo(false);

            FacesContext fc = FacesContext.getCurrentInstance();
            fc.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "INICIANDO SERVIÇO...", ""));
        } catch (Exception ex) {
            FacesContext fc = FacesContext.getCurrentInstance();
            fc.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "ERROR AO TENTAR INICIAR SERVIÇO!!!", ""));
        }
    }

    /**
     * Método com a caracteristica de obter os dados para atendimento ao
     * paciente. Obtendo o valor de dispensado indicando que o paciente foi
     * atendido
     */
    public void chamadaAtendidoCliente() throws SQLException {
        try {
            MBBase mBBase = new MBBase();
            String nomeusuario = mBBase.valorParam("nomeusuario");
            if (triagematendimentochamadaclientetransfer.getNometriagemachamadacliente() == null || triagematendimentochamadaclientetransfer.getNometriagemachamadacliente().equals("")) {
                FacesContext fc = FacesContext.getCurrentInstance();
                fc.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL, "PACIENTE E SEU SERVIÇO NÃO FORAM FINALIZADOS! SENHA NÃO SELECIONADA PARA O ATENDIMENTO!!!", ""));
                return;
            }

            //passa a senha/nomedatriagem para que seja atualizado
            triagematendimentochamadaclientebo.atualizarAtendidoTriagemAtendimentoChamadaCliente(triagematendimentochamadaclientetransfer.getNometriagemachamadacliente(),
                    triagematendimentochamadaclientetransfer.getNumerodolocal(), nomeusuario.toUpperCase());

            //contar quantidade de vezes que o metodo foi acionado
            numerodesenhas--;

            //nao deixa o botao de chamar o paciente o cliente ficar renderizado
            setAtivarbotaochamarcliente(false);

            //ativar o botao de chamar o proximo            
            setAtivarbotaochamarproximo(true);

            FacesContext fc = FacesContext.getCurrentInstance();
            fc.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "PACIENTE ATENDIDO COM SUCESSO!!!", ""));
        } catch (Exception ex) {
            FacesContext fc = FacesContext.getCurrentInstance();
            fc.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "ERROR AO ATENDER PACIENTE!!!", ""));
        }

        //seta nulo para os valores, para que limpe o painel
        triagematendimentochamadaclientetransfer.setNometriagemachamadacliente(null);
        triagematendimentochamadaclientetransfer.setPrioridadetriagemachamadacliente(null);
        triagematendimentochamadaclientetransfer.setNormalchamadatriagemchamadacliente(null);
        triagematendimentochamadaclientetransfer.setDatainiciotriagemchamadacliente(null);
        //atendimento realizado com sucesso e finalizado
        zerarAtendimentoChamadaCliente();
        //atualizar grid-atendido
        atualizarlistagridatendimento.send("atualizarlistagridatendimento");
    }

    /**
     * Método com a caracteristica de obter os dados para O nao atendimento ao
     * paciente. Obtendo o valor de dispensado indicando que o paciente nao foi
     * atendido
     */
    public void chamadaNaoAtendidoCliente() throws SQLException {
        try {
            MBBase mBBase = new MBBase();
            String nomeusuario = mBBase.valorParam("nomeusuario");
            if (triagematendimentochamadaclientetransfer.getNometriagemachamadacliente() == null || triagematendimentochamadaclientetransfer.getNometriagemachamadacliente().equals("")) {
                FacesContext fc = FacesContext.getCurrentInstance();
                fc.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL, "PACIENTE E SEU SERVIÇO NÃO FORAM FINALIZADOS! SENHA NÃO SELECIONADA PARA O ATENDIMENTO!!!", ""));
                return;
            }

            //passa a senha/nomedatriagem para que seja atualizado
            triagematendimentochamadaclientebo.atualizarNaoAtendidoTriagemAtendimentoChamadaCliente(triagematendimentochamadaclientetransfer.getNometriagemachamadacliente(),
                    triagematendimentochamadaclientetransfer.getNumerodolocal(), nomeusuario.toUpperCase());
            //nao deixa o botao de chamar o paciente o cliente ficar renderizado
            setAtivarbotaochamarcliente(false);
            //contar quantidade de vezes que o metodo foi acionado
            numerodesenhas--;
            FacesContext fc = FacesContext.getCurrentInstance();
            fc.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "PACIENTE NÃO ATENDIDO!!!", ""));
        } catch (Exception ex) {
            FacesContext fc = FacesContext.getCurrentInstance();
            fc.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "ERROR AO NÃO ATENDER PACIENTE!!!", ""));
        }

        //seta nulo para os valores, para que limpe o painel
        triagematendimentochamadaclientetransfer.setNometriagemachamadacliente(null);
        triagematendimentochamadaclientetransfer.setPrioridadetriagemachamadacliente(null);
        triagematendimentochamadaclientetransfer.setNormalchamadatriagemchamadacliente(null);
        triagematendimentochamadaclientetransfer.setDatainiciotriagemchamadacliente(null);
        //atendimento realizado com sucesso e finalizado
        zerarAtendimentoChamadaCliente();
        //atualizar grid de atendimento
        atualizarlistagridatendimento.send("atualizarlistagridatendimento");
    }

    public void listarTudodaGridSenhasporServico() throws SQLException {
        if (localservico != null && servicoaux != null) {
            listartriagematendimentotransferchamadacliente = triagematendimentochamadaclientebo.buscarTriagemAtendimentoChamadaClienteLocaleServico(triagematendimentochamadaclientetransfer.getLocaltransfer().getNomelocal(), triagematendimentochamadaclientetransfer.getServicotransfer().getNomeservico());
        }
        /// listartriagematendimentotransferchamadacliente = triagematendimentochamadaclientebo.listarTodosTriagemAtendimentoChamadaClienteLocaleServico();
    }

    public void popularServicoporLocal() throws SQLException {
        if (triagematendimentochamadaclientetransfer.getServicotransfer().getNomeservico() != null) {
            local = triagematendimentochamadaclientebo.listarTriagemAtendimentoChamadaClienteAtendidoLocal(triagematendimentochamadaclientetransfer.getServicotransfer().getNomeservico());
        } else {
            local = new ArrayList<LocalTransfer>();
        }
    }

    /**
     * Método responsável por validar o número do local/atendimento
     */
    public boolean validarNumeroLocal(FacesContext context, UIComponent component, Object value) throws ValidatorException, SQLException {
        return validartriagematendimentocliente.validarNumeroLocal(context, component, this);
    }

    /**
     * MÉTODOS GETTERS E SETTERS
     */
    //pega valores da triagem
    public void setValorTriagemAtendimento(TriagemAtendimentoClienteTransfer triagemAtendimentoClienteTransfer) {
        //obter numero do local
        triagemAtendimentoClienteTransfer.setNumerodolocal(getNumerolocal());
        //seta valores do atendimento
        setTriagematendimentochamadaclientetransfer(triagemAtendimentoClienteTransfer);
        //ativar botao de chamar o cliente
        setAtivarbotaochamarcliente(true);
    }

    public List<ServicoTransfer> getServico(Short id) throws SQLException {
        return servico = triagematendimentochamadaclientebo.listareBuscarServicoUsuarioTriagemAtendimentoChamadaCliente(id);
    }

    public void setServico(List<ServicoTransfer> servico) {
        this.servico = servico;
    }

    public List<LocalTransfer> getLocal() throws SQLException {
        return (triagematendimentochamadaclientebo.listarTriagemAtendimentoChamadaClienteAtendidoLocal(triagematendimentochamadaclientetransfer.getServicotransfer().getNomeservico()));
        //return local;
    }

    public void setLocal(List<LocalTransfer> local) {
        this.local = local;
    }

    //metodo com a caracteristica de retornar as senhas ativas para o atendimento
    public List<TriagemAtendimentoClienteTransfer> listarAtendimentoSenhasTodosTriagemChamadaCliente() throws SQLException {
        return listartriagematendimentotransferchamadacliente;
    }

    public TriagemAtendimentoClienteTransfer getTriagematendimentochamadaclientetransfer() {
        return triagematendimentochamadaclientetransfer;
    }

    public void setTriagematendimentochamadaclientetransfer(TriagemAtendimentoClienteTransfer triagematendimentochamadaclientetransfer) {
        this.triagematendimentochamadaclientetransfer = triagematendimentochamadaclientetransfer;
    }

    public List<TriagemAtendimentoClienteTransfer> getListartriagematendimentotransferchamadacliente() {
        return listartriagematendimentotransferchamadacliente;
    }

    public void setListartriagematendimentotransferchamadacliente(List<TriagemAtendimentoClienteTransfer> listartriagematendimentotransferchamadacliente) {
        this.listartriagematendimentotransferchamadacliente = listartriagematendimentotransferchamadacliente;
    }

    public boolean isAtivarbotaochamarcliente() {
        return ativarbotaochamarcliente;
    }

    public void setAtivarbotaochamarcliente(boolean ativarbotaochamarcliente) {
        this.ativarbotaochamarcliente = ativarbotaochamarcliente;
    }

    public boolean isAtivarpanelatendimentoservicoesenha() {
        return ativarpanelatendimentoservicoesenha;
    }

    public boolean isAtivarbotaochamarproximo() {
        return ativarbotaochamarproximo;
    }

    public void setAtivarbotaochamarproximo(boolean ativarbotaochamarproximo) {
        this.ativarbotaochamarproximo = ativarbotaochamarproximo;
    }

    public void setAtivarpanelatendimentoservicoesenha(boolean ativarpanelatendimentoservicoesenha) {
        this.ativarpanelatendimentoservicoesenha = ativarpanelatendimentoservicoesenha;
    }

    public int getNumerolocal() {
        return numerolocal;
    }

    public void setNumerolocal(int numerolocal) {
        this.numerolocal = numerolocal;
    }

    public void setNumerodesenhas(int numerodesenhas) {
        this.numerodesenhas = numerodesenhas;
    }

    public int getNumerodesenhas() {
        return numerodesenhas;
    }

    public boolean isAtivarcronometro() {
        return ativarcronometro;
    }

    public void setAtivarcronometro(boolean ativarcronometro) {
        this.ativarcronometro = ativarcronometro;
    }

    public boolean isServicoAux() {
        return servicoAux;
    }

    public void setServicoAux(boolean servicoAux) {
        this.servicoAux = servicoAux;
    }

}
