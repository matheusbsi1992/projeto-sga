/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.com.projeto.clinica.mb;

import br.com.projeto.clinica.model.Pessoa;
import br.com.projeto.clinica.bo.LoginBO;
import br.com.projeto.clinica.validacao.ValidarLogin;

import java.io.Serializable;
import java.sql.SQLException;

import java.util.List;

import javax.faces.application.FacesMessage;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import javax.faces.component.UIComponent;

import javax.faces.context.FacesContext;
import javax.faces.validator.ValidatorException;

import javax.inject.Inject;

import javax.servlet.http.HttpSession;

import org.omnifaces.cdi.Push;
import org.omnifaces.cdi.PushContext;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;

@SessionScoped
@ManagedBean
public class LoginClinicaMB implements Serializable {

    public LoginClinicaMB() {

    }

    private Pessoa pessoa = new Pessoa();

    private static LoginClinicaMB me;

    public static LoginClinicaMB getInstance() throws SQLException {
        if (me == null) {
            me = new LoginClinicaMB();
        }
        return me;
    }

    private boolean ativaratendimento = false;
    private boolean ativarcargo = false;
    private boolean ativarestatistica = false;
    private boolean ativarlocal = false;
    private boolean ativarmonitor = false;
    private boolean ativarservico = false;
    private boolean ativartriagemalternativa = false;
    private boolean ativartriagemchamadacliente = false;
    private boolean ativarunidade = false;
    private boolean ativarusuario = false;
    private boolean ativarpainelatendimento = false;
    private boolean ativarreiniciarsenha = false;
    private boolean ativarreiniciarservicosenha = false;

    private boolean ativo = false;

    private String emailPessoa = "";
    private String codigoClinica = "";
    private String senhaPessoa = "";

    private HttpSession session = null;

    private LoginBO loginBO = new LoginBO();

    private ValidarLogin validarLogin = new ValidarLogin();

    private boolean ativargrowltopmenu = false;

    @Inject
    @Push(channel = "loginpushmb")
    private PushContext loginpushmb;

    /**
     * Métodos getters e setters
     *
     * @return
     */
    public Pessoa getPessoa() {
        return pessoa;
    }

    public void setPessoa(Pessoa pessoa) {
        this.pessoa = pessoa;
    }

    public boolean isAtivaratendimento() {
        return ativaratendimento;
    }

    public void setAtivaratendimento(boolean ativaratendimento) {
        this.ativaratendimento = ativaratendimento;
    }

    public boolean isAtivarcargo() {
        return ativarcargo;
    }

    public void setAtivarcargo(boolean ativarcargo) {
        this.ativarcargo = ativarcargo;
    }

    public boolean isAtivarestatistica() {
        return ativarestatistica;
    }

    public void setAtivarestatistica(boolean ativarestatistica) {
        this.ativarestatistica = ativarestatistica;
    }

    public boolean isAtivarlocal() {
        return ativarlocal;
    }

    public void setAtivarlocal(boolean ativarlocal) {
        this.ativarlocal = ativarlocal;
    }

    public boolean isAtivarmonitor() {
        return ativarmonitor;
    }

    public void setAtivarmonitor(boolean ativarmonitor) {
        this.ativarmonitor = ativarmonitor;
    }

    public boolean isAtivarservico() {
        return ativarservico;
    }

    public void setAtivarservico(boolean ativarservico) {
        this.ativarservico = ativarservico;
    }

    public boolean isAtivartriagemalternativa() {
        return ativartriagemalternativa;
    }

    public void setAtivartriagemalternativa(boolean ativartriagemalternativa) {
        this.ativartriagemalternativa = ativartriagemalternativa;
    }

    public boolean isAtivartriagemchamadacliente() {
        return ativartriagemchamadacliente;
    }

    public void setAtivartriagemchamadacliente(boolean ativartriagemchamadacliente) {
        this.ativartriagemchamadacliente = ativartriagemchamadacliente;
    }

    public boolean isAtivarunidade() {
        return ativarunidade;
    }

    public void setAtivarunidade(boolean ativarunidade) {
        this.ativarunidade = ativarunidade;
    }

    public boolean isAtivarusuario() {
        return ativarusuario;
    }

    public void setAtivarusuario(boolean ativarusuario) {
        this.ativarusuario = ativarusuario;
    }

    public boolean isAtivarpainelatendimento() {
        return ativarpainelatendimento;
    }

    public void setAtivarpainelatendimento(boolean ativarpainelatendimento) {
        this.ativarpainelatendimento = ativarpainelatendimento;
    }

    public boolean isAtivarreiniciarsenha() {
        return ativarreiniciarsenha;
    }

    public void setAtivarreiniciarsenha(boolean ativarreiniciarsenha) {
        this.ativarreiniciarsenha = ativarreiniciarsenha;
    }

    public boolean isAtivarreiniciarservicosenha() {
        return ativarreiniciarservicosenha;
    }

    public void setAtivarreiniciarservicosenha(boolean ativarreiniciarservicosenha) {
        this.ativarreiniciarservicosenha = ativarreiniciarservicosenha;
    }

    /**
     * Encerra métodos getters e setters das páginas de visualização
     */
    //------------------
    /**
     * Indica se o usuário está ativo
     */
    public boolean isAtivo() {
        return ativo;
    }

    public void setAtivo(boolean ativo) {
        this.ativo = ativo;
    }

    /**
     * Encerra métodos getters e setters de usuário ativo
     */
    //------------------
    /**
     *
     */
    public HttpSession getSession() {
        return session;
    }

    public void setSession(HttpSession session) {
        this.session = session;
    }

    /**
     * Método de Getter e Setter para ativar a Growl de senha do usuário Logado
     */
    public boolean isAtivargrowltopmenu() {
        return ativargrowltopmenu;
    }

    public void setAtivargrowltopmenu(boolean ativargrowltopmenu) {
        this.ativargrowltopmenu = ativargrowltopmenu;
    }
    //-----Encerra métodos da Growl para o usuário ao qual necessitade de uma nova senha

//    /**
//     * Métodos de Getter e Setter para coletar os dados do usuário a qual está
//     * Logado no Sistema
//     */
//    public static LoginClinicaMB getMe() {
//        return me;
//    }
//
//    public static void setMe(LoginClinicaMB me) {
//        LoginClinicaMB.me = me;
//    }
//    //-----Encerra métodos para coletar usuário logado
    /**
     * Métodos correspondentes ao usuário para a contenção de Email, Senha e
     * Código da Clínica
     */
    public String getEmailPessoa() {
        return emailPessoa;
    }

    public void setEmailPessoa(String emailPessoa) {
        this.emailPessoa = emailPessoa;
    }

    public String getCodigoClinica() {
        return codigoClinica;
    }

    public void setCodigoClinica(String codigoClinica) {
        this.codigoClinica = codigoClinica;
    }

    public String getSenhaPessoa() {
        return senhaPessoa;
    }

    public void setSenhaPessoa(String senhaPessoa) {
        this.senhaPessoa = senhaPessoa;
    }
    //----Encerra os getters e setters da pessoa, para a contenção de Email, Senha e Código da Clínica

    /**
     *
     * @return Usuario do login
     */
    public String LOGINUSUARIO() throws SQLException {

        ativo = loginBO.LOGIN(emailPessoa, senhaPessoa, codigoClinica);

        if (ativo == false) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "EMAIL, CÓDIGO DA CLÍNICA OU SENHA INVÁLIDOS", "LOGIN INEXISTENTE"));
            //nao seta como o valor de nulo na variavel usuariotransfer. Pois quebra o erro de java.el.propertyexceptiom
            pessoa = new Pessoa();
            return null;
        }

        //verifica usuario logado
        pessoa = USUARIOLOGADO();

        if (!pessoa.getStatusPessoa().equals("A") || ativo == false) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "USUÁRIO INATIVO. FAVOR ENTRAR EM CONTATO COM O ADMINISTRADOR DA SUA CLÍNICA", "LOGIN INEXISTENTE"));
            //nao seta como o valor de nulo na variavel pessoa. Pois quebra o erro de java.el.propertyexceptiom
            pessoa = new Pessoa();
            return null;
        } else {

            //abrir sessao
            setSession((HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(true));

            if (pessoa != null) {

                //pega os dados do usuário a qual está totalmente Logado
                getInstance().setPessoa(pessoa);

                //verificar a visualizacao das paginas
                if (ativo == true) {
                    paginas(loginBO.EXISTELOGINDEPAGINASDEPERMISSAO(emailPessoa, codigoClinica, senhaPessoa));
                    //obtem a sessao
                    getSession().setAttribute("usuario", pessoa);
                    //System.out.println(getSession().getId());
                } else {
                    //b3902548b782152ce314f91937ee
                    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "USUÁRIO INATIVO. FAVOR ENTRAR EM CONTATO COM O ADMINISTRADOR DA SUA CLÍNICA", "LOGIN INEXISTENTE"));
                    //nao seta como o valor de nulo na variavel usuariotransfer. Pois quebra o erro de java.el.propertyexceptiom
                    //  LoginMB.getInstance().setUsuarioTransfer(null);
                    pessoa = new Pessoa();
                    return null;
                }
            } else {
                return null;
            }
        }
        //loginpushmb.send("loginpushmb");
        System.out.println("ENTROU NO LOGIN!!!");
        return "home.xhtml?faces-redirect=true";
    }

    //retornar usuario logado no sistema
    public Pessoa USUARIOLOGADO() {
        return loginBO.PESQUISARLOGIN(emailPessoa, codigoClinica, senhaPessoa);
    }

    //remove o usuario que esta logado e mata a sessao
    public String LOGOFF() throws SQLException {
        //LoginMB.getInstance().setUsuarioTransfer(null);
        setEmailPessoa(null);
        setCodigoClinica(null);
        setSenhaPessoa(null);

        FacesContext fc = FacesContext.getCurrentInstance();
        setSession((HttpSession) fc.getExternalContext().getSession(false));
        getSession().invalidate();
        return "/index.xhtml?faces-redirect=true";
    }

    //altera a senha do usuario e faz a ativação e desativação do growl
    public String ALTERARSENHADOUSUARIO() throws SQLException {
        try {
            loginBO.ALTERARSENHADOUSUARIOLOGADO(pessoa);
            setAtivargrowltopmenu(true);
            FacesContext fc = FacesContext.getCurrentInstance();
            fc.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "SENHA ALTERADA COM SUCESSO!!!", ""));
        } catch (Exception ex) {
            FacesContext fc = FacesContext.getCurrentInstance();
            fc.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "ERROR AO ATUALIZAR SENHA DO USUÁRIO!!!", ""));
        }
        setAtivargrowltopmenu(false);
        return LOGOFF();
    }

    public void paginas(List<Pessoa> pessoaLista) {

        if (pessoaLista.isEmpty()) {
            return;
        }

        pessoaLista.stream().map((pessoa) -> {
            if (pessoa.getCargo().getPermissao().getNomePermissao().equalsIgnoreCase("ATENDIMENTO")) {
                setAtivaratendimento(true);
            }
            return pessoa;
        }).map((pessoa) -> {
            if (pessoa.getCargo().getPermissao().getNomePermissao().equalsIgnoreCase("CARGO")) {
                setAtivarcargo(true);
            }
            return pessoa;
        }).map((pessoa) -> {
            if (pessoa.getCargo().getPermissao().getNomePermissao().equalsIgnoreCase("LOCAL")) {
                setAtivarlocal(true);
            }
            return pessoa;
        }).map((pessoa) -> {
            if (pessoa.getCargo().getPermissao().getNomePermissao().equalsIgnoreCase("ESTATÍSTICA")) {
                setAtivarestatistica(true);
            }
            return pessoa;
        }).map((pessoa) -> {
            if (pessoa.getCargo().getPermissao().getNomePermissao().equalsIgnoreCase("MONITOR")) {
                setAtivarmonitor(true);
            }
            return pessoa;
        }).map((pessoa) -> {
            if (pessoa.getCargo().getPermissao().getNomePermissao().equalsIgnoreCase("SERVIÇO")) {
                setAtivarservico(true);
            }
            return pessoa;

        }).map((pessoa) -> {
            if (pessoa.getCargo().getPermissao().getNomePermissao().equalsIgnoreCase("PAINEL DE ATENDIMENTO")) {
                setAtivarpainelatendimento(true);
            }
            return pessoa;

        }).map((pessoa) -> {
            if (pessoa.getCargo().getPermissao().getNomePermissao().equalsIgnoreCase("TRIAGEM ALTERNATIVA")) {
                setAtivartriagemalternativa(true);
            }
            return pessoa;
        }).map((pessoa) -> {
            if (pessoa.getCargo().getPermissao().getNomePermissao().equalsIgnoreCase("TRIAGEM CHAMADACLIENTE")) {
                setAtivartriagemchamadacliente(true);
            }
            return pessoa;
        }).map((pessoa) -> {
            if (pessoa.getCargo().getPermissao().getNomePermissao().equalsIgnoreCase("UNIDADE")) {
                setAtivarunidade(true);
            }
            return pessoa;
        }).map((pessoa) -> {
            if (pessoa.getCargo().getPermissao().getNomePermissao().equalsIgnoreCase("USUÁRIO")) {
                setAtivarusuario(true);
            }
            return pessoa;

        }).map((pessoa) -> {
            if (pessoa.getCargo().getPermissao().getNomePermissao().equalsIgnoreCase("REINICIAR SENHA")) {
                setAtivarreiniciarsenha(true);
            }
            return pessoa;
        }).filter((pessoa) -> (pessoa.getCargo().getPermissao().getNomePermissao().equalsIgnoreCase("REINICIAR SENHA POR SERVIÇO"))).forEach((_item) -> {
            setAtivarreiniciarservicosenha(true);
        });

    }

    /**
     * Validações
     */
    public boolean VALIDAREMAILECODIGOESENHADOLOGIN(FacesContext context, UIComponent component, Object value) throws ValidatorException, SQLException {
        return validarLogin.VALIDAREMAILCODIGOESENHADOLOGIN(context, component, this);
    }

    public boolean VALIDARSENHAUSUARIOLOGADO(FacesContext context, UIComponent component, Object value) throws ValidatorException, SQLException {
        return validarLogin.VALIDARSENHADOUSUARIOALTERAR(context, component, this);
    }

}
