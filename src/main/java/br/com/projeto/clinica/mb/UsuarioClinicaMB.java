/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.projeto.clinica.mb;

import br.com.projeto.clinica.bo.CargoBO;
import br.com.projeto.clinica.bo.ClinicaBO;
import br.com.projeto.clinica.bo.UsuarioClinicaBO;
import br.com.projeto.clinica.email.Email;
import br.com.projeto.clinica.enumciado.IdentificacaoPessoa;
import br.com.projeto.clinica.model.Cargo;
import br.com.projeto.clinica.model.Clinica;
import br.com.projeto.clinica.model.Endereco;
import br.com.projeto.clinica.model.Pessoa;
import br.com.projeto.clinica.model.Profissional;
import br.com.projeto.clinica.util.EnvioSMSUtil;
import br.com.projeto.clinica.validacao.ValidarUsuarioClinica;
import java.io.IOException;
import java.io.Serializable;
import java.net.http.HttpResponse;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.application.ViewHandler;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.bean.ViewScoped;
import javax.faces.component.UIComponent;
import javax.faces.component.UIInput;
import javax.faces.component.UIViewRoot;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.validator.ValidatorException;
import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import kong.unirest.Unirest;

import org.omnifaces.cdi.Push;
import org.omnifaces.cdi.PushContext;
import org.primefaces.PrimeFaces;

import org.primefaces.context.PrimeFacesContext;
import org.springframework.beans.factory.parsing.Problem;

@ManagedBean
@RequestScoped
//@ViewScoped
public class UsuarioClinicaMB implements Serializable {

    private Cargo cargo = new Cargo();

    private Clinica clinica = new Clinica();

    private Endereco endereco = new Endereco();

    private Pessoa pessoa = new Pessoa();

    private Email email = new Email();

    private ClinicaBO clinicaBO = new ClinicaBO();

    private CargoBO cargoBO = new CargoBO();

    private UsuarioClinicaBO usuarioClinicaBO = new UsuarioClinicaBO();

    private ValidarUsuarioClinica validarClinica = new ValidarUsuarioClinica();

    private EnvioSMSUtil envioSMSUtil = new EnvioSMSUtil();

    private String codigoAleatorio;

    private boolean ativacao = false;

    private int contador = 0;
    //cria canal e injeta para atualizar o formulario de Cadastro
    @Inject
    @Push(channel = "registrarClinicapushmb")
    private PushContext registrarClinicapushmb;

    public Cargo getCargo() {
        return cargo;
    }

    public void setCargo(Cargo cargo) {
        this.cargo = cargo;
    }

    public Clinica getClinica() {
        return clinica;
    }

    public void setClinica(Clinica clinica) {
        this.clinica = clinica;
    }

    public Pessoa getPessoa() {
        return pessoa;
    }

    public void setPessoa(Pessoa pessoa) {
        this.pessoa = pessoa;
    }

    public Endereco getEndereco() {
        return endereco;
    }

    public void setEndereco(Endereco endereco) {
        this.endereco = endereco;
    }

    public Email getEmail() {
        return email;
    }

    public void setEmail(Email email) {
        this.email = email;
    }

    public String getCodigoAleatorio() {
        return codigoAleatorio;
    }

    public void setCodigoAleatorio(String codigoAleatorio) {
        this.codigoAleatorio = codigoAleatorio;
    }

    public boolean isAtivacao() {
        return ativacao;
    }

    public void setAtivacao(boolean ativacao) {
        this.ativacao = ativacao;
    }

    public int getContador() {
        return contador;
    }

    public void setContador(int contador) {
        this.contador = contador;
    }

    public UsuarioClinicaMB() {
        // EXISTECODIGO();
    }

    @PostConstruct
    public void init() {
        EXISTECODIGO();
    }
//
//    public boolean getHasMessage() {
//        Iterator<FacesMessage> iterator = FacesContext.getCurrentInstance().getMessages();
//        return (iterator != null) && (iterator.hasNext()) || (iterator == null);
//    }
//
//    public String getErrorMessage() {
//        Iterator<FacesMessage> iterator = FacesContext.getCurrentInstance().getMessages();
//        return iterator.hasNext() ? iterator.next().getSummary() : "";
//    }

    public String INSERIRUSUARIOCLINICA() {

        // Object numeroCelularClinica = ((UIInput)  FacesContext.getCurrentInstance().getViewRoot().findComponent("formRegistro:dialogodeStatus")).getSubmittedValue();
//        if (contador >= 3) {
//            System.out.println("CONTADOR" + contador);
//            ativacao = true;
//            //mostra dialogo de carregamento
//        } else {
//            ativacao = false;
//            //lascou
//        }
        setAtivacao(true);
        //System.out.println("dialogo der status" + numeroCelularClinica);
        //aparecer();
        Random randomSenha = new Random();
        int senhaGeradora = (randomSenha.nextInt(100000000));
        try {

            //---INSERIR CLINICA
            clinica.setNomeDoResponsavelClinica(pessoa.getNomePessoa());
            clinica.setEmailClinica(pessoa.getEmailPessoa());
            clinica.setCodigoClinica(codigoAleatorio);
            clinica.setEndereco(endereco);
            clinica.setStatusClinica("A");
            clinicaBO.INSERIRCLINICA(clinica);
            //---FINALIZA INSERIR CLINICA

            //---INSERIR CARGO
            int idClinica = clinicaBO.ULTIMOIDCLINICA().getIdClinica();
            cargo.setNomeCargo("Administrador");
            cargo.setDescricaoCargo("Administrador do Sistema");
            cargo.setStatusCargo("A");
            cargo.setClinica(clinica);
            cargo.getClinica().setIdClinica(idClinica);

            if (cargoBO.LISTARPERMISSOES().isEmpty()) {
                cargoBO.INSERIRPERMISSAO();
            }
            cargoBO.INSERIRCARGOPERMISSAO(cargo);
            //--ENCERRA INSERIR CARGO

            //---INSERIR PROFISSIONAL/USUARIO CLINICA
            if (pessoa.getTipoPessoa().equalsIgnoreCase(IdentificacaoPessoa.PROFISSIONAL.name())) {
                
                pessoa.setClinica(clinica);
                pessoa.setCargo(cargo);
                pessoa.setSenhaPessoa(String.valueOf(senhaGeradora));
                pessoa.setStatusPessoa("A");
                pessoa.setTipoPessoa(IdentificacaoPessoa.PROFISSIONAL.name());

                usuarioClinicaBO.INSERIRUSUAROCLINICAPROFISSIONAL(pessoa);
                //---informa o email
                email.cadastrarConectadoEmail(pessoa);
                //---FINALIZA INSERIR PESSOA/USUARIO CLINICA
                //--INFORMA DADOS PARA O ENVIO DO SMS
                envioSMSUtil.ENVIODESMSCRIACAODEUSUARIOCLINICA(clinica, pessoa, senhaGeradora);
            }else{
                pessoa.setClinica(clinica);
                pessoa.setCargo(cargo);
                pessoa.setSenhaPessoa(String.valueOf(senhaGeradora));
                pessoa.setStatusPessoa("A");
                pessoa.setTipoPessoa(IdentificacaoPessoa.COLABORADOR.name());

                usuarioClinicaBO.INSERIRUSUAROCLINICACOLABORADOR(pessoa);
                //---informa o email
                email.cadastrarConectadoEmail(pessoa);
                //---FINALIZA INSERIR PESSOA/USUARIO CLINICA
                //--INFORMA DADOS PARA O ENVIO DO SMS
                envioSMSUtil.ENVIODESMSCRIACAODEUSUARIOCLINICA(clinica, pessoa, senhaGeradora);
            }
            //--FINALIZA O ENVIO DO SMS
            //if (getHasMessage()) {
            FacesContext fc = FacesContext.getCurrentInstance();
            fc.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "USUÁRIO CLÍNICA INSERIDO COM SUCESSO!!!", ""));
            //}

        } catch (Exception ex) {
            FacesContext fc = FacesContext.getCurrentInstance();
            fc.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "ERROR AO INSERIR USUÁRIO CLÍNICA!!!", ""));
        }

        //(expressions); .update(Arrays.asList("admin-frm:table-clientes", "admin-frm:toolbar"));
        //PrimeFaces.current().resetInputs("formRegistro:codigoClinica");
        // PrimeFaces.current().executeScript("PF('statusDialogo').show()");
        PrimeFaces.current()
                .executeScript("PF('dialogoDeCadastro').show()");

        //PrimeFaces.current().ajax().update("formRegistro");
        //FacesContext context = FacesContext.getCurrentInstance();
        //return context.getViewRoot().getViewId() + "?faces-redirect=true";
        //RELOADDADOS();
        //  ativacao = false;
        //setContador(0);
        //setAtivacao(false);
        registrarClinicapushmb.send(
                "registrarClinicapushmb");

        return "cadastro?faces-blackirect=true";

        //return "cadastro?faces-redirect=true";
    }

//    public void aparecer() {
//
//        if (contador >= 5) {
//            ativacao = true;
//            // PrimeFaces.current().executeScript("PF('formRegistro:statusDialogo').show();");
//        }
//        // contador = 0;
//        // ativacao = false;
//
//    }
//    private void RELOADDADOS() throws IOException {
//
//        FacesContext ctx = FacesContext.getCurrentInstance();
//        List<String> clientIds = Arrays.asList("formRegistro:codigoClinica", "formRegistro:nomeClinica");
//
//        UIViewRoot view = ctx.getViewRoot();
//        view.resetValues(ctx, clientIds); //
//
//        //System.out.println(view.getEventNames());
////        profissional = new Profissional();
////        clinica = new Clinica();
////        cargo = new Cargo();
////        email = new Email();
////        endereco = new Endereco();
////        codigoAleatorio = new Clinica().getCodigoClinica();
//        //PrimeFaces.current().executeScript("history.go(0)");
////        ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();
////        ec.redirect(((HttpServletRequest) ec.getRequest()).getRequestURI());
////        FacesContext fctx = FacesContext.getCurrentInstance();
////
////        String page = fctx.getViewRoot().getViewId();
////
////        ViewHandler ViewH = fctx.getApplication().getViewHandler();
////
////        UIViewRoot UIV = ViewH.createView(fctx, page);
////
////        UIV.setViewId(page);
////
////        fctx.setViewRoot(UIV);
//    }
    /**
     * METODOS PARA VALIDACAO DA CLINICA
     */
    //metodo para validacao do celular da Clinica
    public boolean VALIDARCELULARUSUARIOCLINICA(FacesContext context, UIComponent component, Object value) throws ValidatorException, SQLException {

        if (validarClinica.VALIDARCELULARUSUARIOCLINICA(context, component, value) == true) {
            //contador = contador + 1;
            //  System.out.println("VALIDARCELULARUSUARIOCLINICA" + contador);
            return true;
        }
        return false;
    }

    //metodo para validacao do codigo do usuario da Clinica
    public boolean VALIDARCODIGOUSUARIOCLINICA(FacesContext context, UIComponent component, Object value) throws ValidatorException, SQLException {

        if (validarClinica.VALIDARCODIGOUSUARIOCLINICA(context, component, value) == true) {
            //contador = contador + 1;
            //  System.out.println("ALIDARCODIGOUSUARIOCLINICA" + contador);
            return true;
        }
        return false;
    }

    //metodo para a validacao do nome do usuario da Clinica
    public boolean VALIDARNOMEPESSOACLINICA(FacesContext context, UIComponent component, Object value) throws ValidatorException, SQLException {

        if (validarClinica.VALIDARNOMEPESSOACLINICA(context, component, value) == true) {
            //contador = contador + 1;
            //System.out.println("VALIDARNOMEPESSOACLINICA" + contador);
            return true;
        }
        return false;
    }

    //metodo para a validacao do nome da Clinica
    public boolean VALIDARNOMECLINICA(FacesContext context, UIComponent component, Object value) throws ValidatorException, SQLException {

        if (validarClinica.VALIDARNOMECLINICA(context, component, value) == true) {
            //contador = contador + 1;

            return true;
        }
        return false;
    }

    //metodo para a validacao do Email do Usuario correspondente a Clinica
    public boolean VALIDAREMAILUSUARIOCLINICA(FacesContext context, UIComponent component, Object value) throws ValidatorException, SQLException {

        if (validarClinica.VALIDAREMAILUSUARIOCLINICA(context, component, value) == true) {
            // contador = contador + 1;
            //  System.out.println("VALIDAREMAILUSUARIOCLINICA" + contador);
            return true;
        }

        return false;
    }
    
    //metodo para a validacao do Email do Usuario correspondente a Clinica
    public boolean VALIDARTIPOUSUARIOCLINICA(FacesContext context, UIComponent component, Object value) throws ValidatorException, SQLException {

        if (validarClinica.VALIDARTIPOUSUARIOCLINICA(context, component, value) == true) {
            // contador = contador + 1;
            //  System.out.println("VALIDAREMAILUSUARIOCLINICA" + contador);
            return true;
        }

        return false;
    }

    //metodo para gerar outro codigo
    private void EXISTECODIGO() {

        codigoAleatorio = String.valueOf((new Random().nextInt(1000000)));

        boolean valorRetornado = clinicaBO.EXISTICODIGOCLINICA(String.valueOf(codigoAleatorio));

        while (valorRetornado == true) {

            codigoAleatorio = String.valueOf((new Random().nextInt(1000000)));

            String codigoClinica = clinicaBO.EXISTICODIGOCLINICACORRESPONDENTE(String.valueOf(codigoAleatorio)).getCodigoClinica();

            if (codigoClinica.equals(String.valueOf(codigoAleatorio))) {
                continue;
            } else {
                valorRetornado = false;
                break;
            }
        }
        //aparecer();
    }

}
