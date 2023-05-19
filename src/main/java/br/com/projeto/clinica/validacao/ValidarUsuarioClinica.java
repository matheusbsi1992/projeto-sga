/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.com.projeto.clinica.validacao;

import br.com.projeto.clinica.bo.ClinicaBO;
import br.com.projeto.clinica.bo.UsuarioClinicaBO;
import br.com.projeto.clinica.model.Clinica;
import br.com.projeto.clinica.model.Pessoa;
import br.com.projeto.clinica.model.Profissional;
import br.com.projeto.clinica.util.CelularUtil;
import br.com.projeto.clinica.util.EmailUtil;

import java.sql.SQLException;
import java.util.Arrays;
//import java.util.Random;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.component.UIInput;
import javax.faces.context.FacesContext;
import javax.faces.validator.ValidatorException;

public class ValidarUsuarioClinica {

    //variavel de mensagem
    private String msg = null;

    //variaveis da Clinica
    private Clinica clinica = new Clinica();
    private ClinicaBO clinicaBO = new ClinicaBO();
    //private Random randomCodigoClinica = new Random();
    private UsuarioClinicaBO usuarioClinicaBO = new UsuarioClinicaBO();

    //variaveis da Pessoa
    private Pessoa pessoa = new Pessoa();
    
    //variavel de Util Email
    private EmailUtil emailUtil = new EmailUtil();
    
    //variavel de Util Celular
    private CelularUtil celularUtil = new CelularUtil();

    /**
     * METODOS DE VALIDACAO DA CLINICA
     */
    //metodo para validacao do nome da Pessoa da Clinica
    public boolean VALIDARNOMEPESSOACLINICA(FacesContext context, UIComponent component, Object value) throws ValidatorException, SQLException {
        Object nomePessoa = ((UIInput) context.getViewRoot().findComponent("formRegistro:nomePessoa")).getSubmittedValue();

        pessoa.setNomePessoa(nomePessoa.toString());

        msg = "INFORME O SEU NOME E SOBRENOME";
        if ((pessoa.getNomePessoa() == null || "".equalsIgnoreCase(pessoa.getNomePessoa()))) {
            ((UIInput) component).setValid(false);
            context.addMessage((component.getClientId(context)), new FacesMessage(FacesMessage.SEVERITY_ERROR, msg, msg));
            return false;
        }
        msg = "O SEU NOME E SOBRENOME DEVE TER 02 DE 255 DÍGITOS";
        if (pessoa.getNomePessoa().trim().length() <= 1 || pessoa.getNomePessoa().trim().length() > 255) {
            ((UIInput) component).setValid(false);
            context.addMessage(component.getClientId(context), new FacesMessage(FacesMessage.SEVERITY_ERROR, msg, msg));
            return false;
        }
        return true;
    }

    //metodo para validacao da Clinica
    public boolean VALIDARCODIGOUSUARIOCLINICA(FacesContext context, UIComponent component, Object value) throws ValidatorException, SQLException {
        //Object idClinica = ((UIInput) context.getViewRoot().findComponent("formRegistro:idClinica")).getLocalValue();
        Object codigoClinica = ((UIInput) context.getViewRoot().findComponent("formRegistro:codigoClinica")).getSubmittedValue();

        //clinica.setIdClinica(Integer.parseInt(idClinica.toString()));
        clinica.setCodigoClinica(codigoClinica.toString());

        msg = "INFORME O CÓDIGO DA CLÍNICA";
        if ((clinica.getCodigoClinica() == null || "".equalsIgnoreCase(clinica.getCodigoClinica()))) {
            ((UIInput) component).setValid(false);
            context.addMessage((component.getClientId(context)), new FacesMessage(FacesMessage.SEVERITY_ERROR, msg, msg));
            return false;
        }
//        msg = "O CÓDIGO DA CLÍNICA SÓ DEVE TER 06 DE DÍGITOS";
//        if (clinica.getCodigoClinica().trim().length() < 5 || clinica.getCodigoClinica().trim().length() > 6) {
//            ((UIInput) component).setValid(false);
//            context.addMessage(component.getClientId(context), new FacesMessage(FacesMessage.SEVERITY_ERROR, msg, msg));
//            return false;
//        }
        //if (clinicaBO.EXISTIIDECODIGOCLINICA(clinica.getIdClinica(), clinica.getCodigoClinica()) == false || clinicaBO.EXISTICODIGOCLINICA(clinica.getCodigoClinica()) == false) {
        //  return false;
        if (clinicaBO.EXISTICODIGOCLINICA(clinica.getCodigoClinica()) == false) {
            return true;
        } else {
            msg = "O CÓDIGO DA CLÍNICA JÁ EXISTE";
            ((UIInput) component).setValid(false);
            context.addMessage(component.getClientId(context), new FacesMessage(FacesMessage.SEVERITY_ERROR, msg, msg));
            return false;
        }
    }

    //metodo para validacao da Clinica
    public boolean VALIDARNOMECLINICA(FacesContext context, UIComponent component, Object value) throws ValidatorException, SQLException {
        //Object idClinica = ((UIInput) context.getViewRoot().findComponent("formRegistro:idClinica")).getLocalValue();
        Object nomeClinica = ((UIInput) context.getViewRoot().findComponent("formRegistro:nomeClinica")).getSubmittedValue();

        //clinica.setIdClinica(Integer.parseInt(idClinica.toString()));
        clinica.setNomeClinica(nomeClinica.toString());

        msg = "INFORME O NOME DA CLÍNICA";
        if (null == nomeClinica || (clinica.getNomeClinica() == null || "".equalsIgnoreCase(clinica.getNomeClinica()))) {
            ((UIInput) component).setValid(false);
            context.addMessage((component.getClientId(context)), new FacesMessage(FacesMessage.SEVERITY_ERROR, msg, msg));
            return false;
        }

        msg = "O NOME DA CLÍNICA SÓ DEVE TER 02 E 20 DE DÍGITOS";
        if (clinica.getNomeClinica().trim().length() <= 1 || clinica.getNomeClinica().trim().length() > 20) {
            ((UIInput) component).setValid(false);
            context.addMessage(component.getClientId(context), new FacesMessage(FacesMessage.SEVERITY_ERROR, msg, msg));
            return false;
        }
//        if (clinicaBO.EXISTINOMECLINICA(clinica.getNomeClinica()) == false || clinicaBO.EXISTEIDNOMECLINICA(clinica.getIdClinica(), clinica.getNomeClinica()) == false) {
//            return false;
//        } else {
//            msg = "O NOME DA CLÍNICA JÁ EXISTE";
//            ((UIInput) component).setValid(false);
//            context.addMessage(component.getClientId(context), new FacesMessage(FacesMessage.SEVERITY_ERROR, msg, msg));
//            return true;
//        }
        return true;
    }

    //metodo para validacao do Email da Pessoa Responsavel pela Clinica
    public boolean VALIDAREMAILUSUARIOCLINICA(FacesContext context, UIComponent component, Object value) throws ValidatorException, SQLException {
        //Object idClinica = ((UIInput) context.getViewRoot().findComponent("formRegistro:idClinica")).getLocalValue();
        Object nomeClinica = ((UIInput) context.getViewRoot().findComponent("formRegistro:nomeClinica")).getLocalValue();
        Object emailPessoa = ((UIInput) context.getViewRoot().findComponent("formRegistro:emailPessoa")).getSubmittedValue();
        //Object codigoClinica = ((UIInput) context.getViewRoot().findComponent("formRegistro:codigoClinica")).getLocalValue();

        //clinica.setIdClinica(Integer.parseInt(idClinica.toString()));
        //clinica.setNomeClinica(nomeClinica.toString());
        //clinica.setCodigoClinica(codigoClinica.toString());
        pessoa.setEmailPessoa(emailPessoa.toString());

        msg = "INFORME SEU MELHOR EMAIL";
        if ((pessoa.getEmailPessoa() == null || "".equalsIgnoreCase(pessoa.getEmailPessoa()))) {
            ((UIInput) component).setValid(false);
            context.addMessage((component.getClientId(context)), new FacesMessage(FacesMessage.SEVERITY_ERROR, msg, msg));
            return false;
        }

        msg = "O EMAIL SÓ DEVE TER 5 E 255 DE DÍGITOS";
        if (pessoa.getEmailPessoa().trim().length() <= 4 || pessoa.getEmailPessoa().trim().length() > 255) {
            ((UIInput) component).setValid(false);
            context.addMessage(component.getClientId(context), new FacesMessage(FacesMessage.SEVERITY_ERROR, msg, msg));
            return false;
        }

        msg = "O EMAIL NÃO É CORRESPONDENTE";
        if (emailUtil.VALIDAREMAIL(pessoa.getEmailPessoa()) == false) {
            ((UIInput) component).setValid(false);
            context.addMessage(component.getClientId(context), new FacesMessage(FacesMessage.SEVERITY_ERROR, msg, msg));
            return false;
        }

        msg = "IMPORTANTE INFORMAR O NOME DA CLÍNICA CORRESPODENTE AO DEVIDO EMAIL";
        if (null == nomeClinica) {
            ((UIInput) component).setValid(false);
            context.addMessage((component.getClientId(context)), new FacesMessage(FacesMessage.SEVERITY_ERROR, msg, msg));
            return false;
        }

        if (usuarioClinicaBO.EXISTECLINICAPESSOA(nomeClinica.toString(), pessoa.getEmailPessoa()) == false) {
            return true;
        } else {
            msg = "A CLÍNICA " + nomeClinica.toString() + " CORRESPONDENTE A PESSOA " + pessoa.getEmailPessoa() + " JÁ EXISTE";
            ((UIInput) component).setValid(false);
            context.addMessage(component.getClientId(context), new FacesMessage(FacesMessage.SEVERITY_ERROR, msg, msg));
            return false;
        }
    }

    //metodo para validacao do celular da Clinica
    public boolean VALIDARCELULARUSUARIOCLINICA(FacesContext context, UIComponent component, Object value) throws ValidatorException, SQLException {
        Object numeroCelularClinica = ((UIInput) context.getViewRoot().findComponent("formRegistro:celularClinica")).getSubmittedValue();

        clinica.setCelularClinica(numeroCelularClinica.toString());

        msg = "INFORME O MELHOR NUḾERO DE SEU CELULAR";
        if ((clinica.getCelularClinica() == null || "".equalsIgnoreCase(clinica.getCelularClinica()))) {
            ((UIInput) component).setValid(false);
            context.addMessage((component.getClientId(context)), new FacesMessage(FacesMessage.SEVERITY_ERROR, msg, msg));
            return false;
        }

        msg = "O NÚMERO DA CLÍNICA SÓ DEVE TER 15 DE DÍGITOS";
        if (clinica.getCelularClinica().trim().length() < 15 || clinica.getCelularClinica().trim().length() > 15) {
            ((UIInput) component).setValid(false);
            context.addMessage(component.getClientId(context), new FacesMessage(FacesMessage.SEVERITY_ERROR, msg, msg));
            return false;
        }

        msg = "O NÚMERO DA CLÍNICA NÃO É CORRESPONDENTE";
        if (celularUtil.VALIDARCELULAR(clinica.getCelularClinica()) == false) {           
            ((UIInput) component).setValid(false);
            context.addMessage(component.getClientId(context), new FacesMessage(FacesMessage.SEVERITY_ERROR, msg, msg));
            return false;
        }

        return true;
    }
    
    //metodo para validacao do celular da Clinica
    public boolean VALIDARTIPOUSUARIOCLINICA(FacesContext context, UIComponent component, Object value) throws ValidatorException, SQLException {
        Object tipoPessoa = ((UIInput) context.getViewRoot().findComponent("formRegistro:tipoPessoa")).getSubmittedValue();

        pessoa.setTipoPessoa(tipoPessoa.toString());

        msg = "INFORME O TIPO DE PESSOA DA SUA CLÍNICA";
        if (pessoa.getTipoPessoa() == null || "".equalsIgnoreCase(pessoa.getTipoPessoa()) || " ".equalsIgnoreCase(pessoa.getTipoPessoa())) {
            ((UIInput) component).setValid(false);
            context.addMessage((component.getClientId(context)), new FacesMessage(FacesMessage.SEVERITY_ERROR, msg, msg));
            return false;
        }


        return true;
    }

//    public String VALIDARCELULAR(String numeroCelular) {
//        //5579999165475
//        //(79) 99916-5475
//        Pattern pattern = Pattern.compile("^\\([0-9]{2}\\)((3[0-9]{3}-[0-9]{4})|(9[0-9]{3}-[0-9]{5}))$");
//        Matcher matcher = pattern.matcher(numeroCelular);
//        System.out.println(numeroCelular);
//        boolean celular = matcher.find();
//        if (celular == true) {
//            numeroCelular = numeroCelular.replaceAll("\\(|\\)", "").replaceAll("-", "").replaceAll(" ", "");
//            return "55" + numeroCelular;
//        }
//
//        return null;
//
//    }
 

  

//    public String VALIDARCODIGOCLINICAALEATORIA(String codigoClinica) {
//        if (codigoClinica.trim().length() == 0 || "".equalsIgnoreCase(codigoClinica) || codigoClinica.equals(null)) {
//            clinica.setCodigoClinica(String.valueOf(randomCodigoClinica.nextInt(1000000)));
//        }
//        return clinica.getCodigoClinica();
//    }
}
