/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.com.projeto.clinica.validacao;

import br.com.projeto.clinica.bo.ClinicaBO;
import br.com.projeto.clinica.bo.LoginBO;
import br.com.projeto.clinica.model.Clinica;
import br.com.projeto.clinica.model.Pessoa;
import br.com.projeto.clinica.util.EmailUtil;

import java.sql.SQLException;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.component.UIInput;
import javax.faces.context.FacesContext;
import javax.faces.validator.ValidatorException;

/**
 *
 * @author matheus
 */
public class ValidarLogin {

    //variavel de mensagem
    private String msg = null;

    //variavel de pessoa
    private Pessoa pessoa = new Pessoa();

    //variavel de LoginBo 
    private LoginBO loginBO = new LoginBO();

    //private Util Email
    private EmailUtil emailUtil = new EmailUtil();

    //variavel de Clinica
    private Clinica clinica = new Clinica();
    private ClinicaBO clinicaBO = new ClinicaBO();

    /**
     * Métodos de Login
     */
    //metodo validar Email, Código da Clínica e Senha do Login
    public boolean VALIDAREMAILCODIGOESENHADOLOGIN(FacesContext context, UIComponent component, Object value) throws ValidatorException, SQLException {
        Object emailPessoa = ((UIInput) context.getViewRoot().findComponent("formLogin:emailPessoa")).getSubmittedValue();
        Object senhaPessoa = ((UIInput) context.getViewRoot().findComponent("formLogin:senhaPessoa")).getSubmittedValue();
        Object codigoClinica = ((UIInput) context.getViewRoot().findComponent("formLogin:codigoClinica")).getSubmittedValue();

        pessoa.setEmailPessoa(emailPessoa.toString());
        clinica.setCodigoClinica(codigoClinica.toString());
        pessoa.setSenhaPessoa(senhaPessoa.toString());

        msg = "TODOS OS CAMPOS SÃO DE PREENCHIMENTO OBRIGATÓRIO";
        if ((pessoa.getEmailPessoa() == null || "".equalsIgnoreCase(pessoa.getEmailPessoa())) && (clinica.getCodigoClinica() == null || "".equalsIgnoreCase(clinica.getCodigoClinica())) && (pessoa.getSenhaPessoa() == null || "".equalsIgnoreCase(pessoa.getSenhaPessoa()))) {
            ((UIInput) component).setValid(false);
            context.addMessage(component.getClientId(context), new FacesMessage(FacesMessage.SEVERITY_ERROR, msg, msg));
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
        /**
         * msg = "A SENHA SÓ DEVE TER 8 E 20 DÍGITOS"; if
         * (usuariotransfer.getSenhausuario().trim().length() <= 7 || usuariotransfer.getSenhausuario().trim().length()
         * > 20) { ((UIInput) component).setValid(false);
         * context.addMessage(component.getClientId(context), new
         * FacesMessage(FacesMessage.SEVERITY_ERROR, msg, msg)); return false;
         * }**
         */
        msg = "EMAIL INFORMADO OU CÓDIGO DA CLÍNICA INFORMADO OU SENHA INCORRETOS";
        if (loginBO.EXISTEUSUARIOCLINICA(pessoa.getEmailPessoa(), clinica.getCodigoClinica(), pessoa.getSenhaPessoa()) == false) {
            ((UIInput) component).setValid(false);
            context.addMessage(component.getClientId(context), new FacesMessage(FacesMessage.SEVERITY_ERROR, msg, msg));
            return false;
        }
        return true;
    }

    //metodo de validar a senha de alteracao do usuario
    public boolean VALIDARSENHADOUSUARIOALTERAR(FacesContext context, UIComponent component, Object value) throws ValidatorException {
        Object senha = ((UIInput) context.getViewRoot().findComponent("formulariosenhadousuarioalterar:password")).getSubmittedValue();
        Object senhaconfirmar = ((UIInput) context.getViewRoot().findComponent("formulariosenhadousuarioalterar:senha")).getSubmittedValue();
        pessoa.setSenhaPessoa(senha.toString());
        pessoa.setSenhaConfirmacaoPessoa(senhaconfirmar.toString());

        msg = "INFORME SENHA";
        if ((pessoa.getSenhaPessoa() == null || "".equalsIgnoreCase(pessoa.getSenhaPessoa()))) {
            ((UIInput) component).setValid(false);
            context.addMessage(component.getClientId(context), new FacesMessage(FacesMessage.SEVERITY_ERROR, msg, msg));
            return false;
        }

        msg = "A SENHA SÓ DEVE TER 8 E 20 DÍGITOS";
        if (pessoa.getSenhaPessoa().trim().length() <= 7 || pessoa.getSenhaPessoa().trim().length() > 20) {
            ((UIInput) component).setValid(false);
            context.addMessage(component.getClientId(context), new FacesMessage(FacesMessage.SEVERITY_ERROR, msg, msg));
            return false;
        }

        msg = "INFORME SENHA DE CONFIRMAÇÃO";
        if ((pessoa.getSenhaConfirmacaoPessoa() == null || "".equalsIgnoreCase(pessoa.getSenhaConfirmacaoPessoa()))) {
            ((UIInput) component).setValid(false);
            context.addMessage(component.getClientId(context), new FacesMessage(FacesMessage.SEVERITY_ERROR, msg, msg));
            return false;
        }
        msg = "SENHA E A CONFIRMAÇÃO DE SENHA SÃO DIFERENTES";
        if (!pessoa.getSenhaPessoa().equalsIgnoreCase(pessoa.getSenhaConfirmacaoPessoa())) {
            ((UIInput) component).setValid(false);
            context.addMessage(component.getClientId(context), new FacesMessage(FacesMessage.SEVERITY_ERROR, msg, msg));
            return false;
        }
        return true;
    }

}
