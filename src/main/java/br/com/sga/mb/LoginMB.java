/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.sga.mb;

import br.com.sga.bo.LoginBO;
import br.com.sga.bo.UsuarioBO;
import br.com.sga.bo.ValidarSgaBO;
import br.com.sga.transfer.CargoTransfer;
import br.com.sga.transfer.ServicoTransfer;
import br.com.sga.transfer.UnidadeTransfer;
import br.com.sga.transfer.UsuarioTransfer;
import br.com.sga.util.Util;

import java.io.Serializable;
import java.sql.SQLException;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import javax.annotation.PostConstruct;
import javax.enterprise.context.Destroyed;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ApplicationScoped;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;
import javax.faces.bean.SessionScoped;
import javax.faces.bean.ViewScoped;

import javax.faces.component.UIComponent;

import javax.faces.context.FacesContext;
import javax.faces.validator.ValidatorException;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpSession;
import org.omnifaces.cdi.Push;
import org.omnifaces.cdi.PushContext;

/**
 *
 * @author Jandisson
 */
@SessionScoped
@ManagedBean
public class LoginMB implements Serializable {

    public LoginMB() throws SQLException {

    }
    private UsuarioTransfer usuariotransfer = new UsuarioTransfer();

    private static LoginMB me;

    public static LoginMB getInstance() throws SQLException {
        if (me == null) {
            me = new LoginMB();
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

    private String nomeusuario = "";
    private String senhausuario = "";

    private HttpSession session = null;

    private LoginBO loginbo = new LoginBO();

    private ValidarSgaBO validarusuario = new ValidarSgaBO();

    private boolean ativargrowltopmenu = false;

    @Inject
    @Push(channel = "loginpushmb")
    private PushContext loginpushmb;

    /**
     *
     * @return Usuario do login
     * @throws java.sql.SQLException
     */
    public String loginUsuario() throws SQLException {

        ativo = loginbo.login(nomeusuario.toUpperCase(), senhausuario);

        if (ativo == false) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "USUÁRIO OU SENHA INVÁLIDOS", "LOGIN INEXISTENTE"));
            //nao seta como o valor de nulo na variavel usuariotransfer. Pois quebra o erro de java.el.propertyexceptiom
            usuariotransfer = new UsuarioTransfer();
            return null;
        } else {
            //verifica usuario logado
            usuariotransfer = usuarioLogado();

            if (!usuariotransfer.getStatususuario().equals("A") || ativo == false) {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "USUÁRIO INATIVO. FAVOR ENTRAR EM CONTATO COM O ADMINISTRADOR", "LOGIN INEXISTENTE"));
                //nao seta como o valor de nulo na variavel usuariotransfer. Pois quebra o erro de java.el.propertyexceptiom
                usuariotransfer = new UsuarioTransfer();
                return null;
            }
            //abrir sessao
            setSession((HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(true));
            if (usuariotransfer != null) {
                LoginMB.getInstance().setUsuariotransfer(usuariotransfer);
                //verificar a visualizacao das paginas
                if (ativo == true) {
                    paginas(loginbo.existeLogindePermissao(usuariotransfer.getNomeusuario()));
                    //obtem a sessao
                    getSession().setAttribute("usuario", usuariotransfer);
                    //System.out.println(getSession().getId());
                } else {
                    //b3902548b782152ce314f91937ee
                    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "USUÁRIO INATIVO. FAVOR ENTRAR EM CONTATO COM O ADMINISTRADOR", "LOGIN INEXISTENTE"));
                    //nao seta como o valor de nulo na variavel usuariotransfer. Pois quebra o erro de java.el.propertyexceptiom
                    //  LoginMB.getInstance().setUsuarioTransfer(null);
                    usuariotransfer = new UsuarioTransfer();
                    return null;
                }
            } else {
                return null;
            }
        }
        loginpushmb.send("loginpushmb");
        System.out.println("ENTROU NO LOGIN!!!");
        return "home.xhtml?faces-redirect=true";
    }

    //retornar usuario logado no sistema
    public UsuarioTransfer usuarioLogado() throws SQLException {
        return loginbo.pesquisarLogin(nomeusuario.toUpperCase());
    }

    //remove o usuario que esta logado e mata a sessao
    public String logOff() throws SQLException {
        //LoginMB.getInstance().setUsuarioTransfer(null);
        setNomeusuario(null);
        setSenhausuario(null);

        FacesContext fc = FacesContext.getCurrentInstance();
        setSession((HttpSession) fc.getExternalContext().getSession(false));
        getSession().invalidate();
        return "/index.xhtml?faces-redirect=true";
    }

    //altera a senha do usuario e faz a ativação e desativação do growl
    public String alterarSenhadoUsuario() throws SQLException {
        try {
            loginbo.alterarSenhadoUsuario(usuariotransfer);
            setAtivargrowltopmenu(true);
            FacesContext fc = FacesContext.getCurrentInstance();
            fc.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "SENHA ALTERADA COM SUCESSO!!!", ""));
        } catch (Exception ex) {
            FacesContext fc = FacesContext.getCurrentInstance();
            fc.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "ERROR AO ATUALIZAR SENHA DO USUARIO!!!", ""));
        }
        setAtivargrowltopmenu(false);
        return logOff();
    }

    public void paginas(List<UsuarioTransfer> usuarioTransfers) {

        if (usuarioTransfers.isEmpty()) {
            return;
        }

        usuarioTransfers.stream().map((usuarioTransfer) -> {
            if (usuarioTransfer.getCargo().getNomepermissao().equalsIgnoreCase("ATENDIMENTO")) {
                setAtivaratendimento(true);
            }
            return usuarioTransfer;
        }).map((usuarioTransfer) -> {
            if (usuarioTransfer.getCargo().getNomepermissao().equalsIgnoreCase("CARGO")) {
                setAtivarcargo(true);
            }
            return usuarioTransfer;
        }).map((usuarioTransfer) -> {
            if (usuarioTransfer.getCargo().getNomepermissao().equalsIgnoreCase("LOCAL")) {
                setAtivarlocal(true);
            }
            return usuarioTransfer;
        }).map((usuarioTransfer) -> {
            if (usuarioTransfer.getCargo().getNomepermissao().equalsIgnoreCase("ESTATÍSTICA")) {
                setAtivarestatistica(true);
            }
            return usuarioTransfer;
        }).map((usuarioTransfer) -> {
            if (usuarioTransfer.getCargo().getNomepermissao().equalsIgnoreCase("MONITOR")) {
                setAtivarmonitor(true);
            }
            return usuarioTransfer;
        }).map((usuarioTransfer) -> {
            if (usuarioTransfer.getCargo().getNomepermissao().equalsIgnoreCase("SERVIÇO")) {
                setAtivarservico(true);
            }
            return usuarioTransfer;

        }).map((usuarioTransfer) -> {
            if (usuarioTransfer.getCargo().getNomepermissao().equalsIgnoreCase("PAINEL DE ATENDIMENTO")) {
                setAtivarpainelatendimento(true);
            }
            return usuarioTransfer;

        }).map((usuarioTransfer) -> {
            if (usuarioTransfer.getCargo().getNomepermissao().equalsIgnoreCase("TRIAGEM ALTERNATIVA")) {
                setAtivartriagemalternativa(true);
            }
            return usuarioTransfer;
        }).map((usuarioTransfer) -> {
            if (usuarioTransfer.getCargo().getNomepermissao().equalsIgnoreCase("TRIAGEM CHAMADACLIENTE")) {
                setAtivartriagemchamadacliente(true);
            }
            return usuarioTransfer;
        }).map((usuarioTransfer) -> {
            if (usuarioTransfer.getCargo().getNomepermissao().equalsIgnoreCase("UNIDADE")) {
                setAtivarunidade(true);
            }
            return usuarioTransfer;
        }).map((usuarioTransfer) -> {
            if (usuarioTransfer.getCargo().getNomepermissao().equalsIgnoreCase("USUÁRIO")) {
                setAtivarusuario(true);
            }
            return usuarioTransfer;

        }).map((usuarioTransfer) -> {
            if (usuarioTransfer.getCargo().getNomepermissao().equalsIgnoreCase("REINICIAR SENHA")) {
                setAtivarreiniciarsenha(true);
            }
            return usuarioTransfer;
        }).filter((usuarioTransfer) -> (usuarioTransfer.getCargo().getNomepermissao().equalsIgnoreCase("REINICIAR SENHA POR SERVIÇO"))).forEach((_item) -> {
            setAtivarreiniciarservicosenha(true);
        });

    }

    /**
     * Validações
     */
    public boolean validarUsuarioSenhaLogin(FacesContext context, UIComponent component, Object value) throws ValidatorException, SQLException {
        return validarusuario.validarUsuarioSenhaLogin(context, component, this);
    }

    public boolean validarSenhadoUsuarioAlterar(FacesContext context, UIComponent component, Object value) throws ValidatorException, SQLException {
        return validarusuario.validarSenhadoUsuarioAlterar(context, component, this);
    }

    /**
     * MÉTODOS GETTERS E SETTERS
     */
    public HttpSession getSession() {
        return session;
    }

    public void setSession(HttpSession session) {
        this.session = session;
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

    public boolean isAtivo() {
        return ativo;
    }

    public void setAtivo(boolean ativo) {
        this.ativo = ativo;
    }

    public boolean isAtivargrowltopmenu() {
        return ativargrowltopmenu;
    }

    public void setAtivargrowltopmenu(boolean ativargrowltopmenu) {
        this.ativargrowltopmenu = ativargrowltopmenu;
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

    public String getNomeusuario() {
        return nomeusuario;
    }

    public void setNomeusuario(String nomeusuario) {
        this.nomeusuario = nomeusuario;
    }

    public String getSenhausuario() {
        return senhausuario;
    }

    public void setSenhausuario(String senhausuario) {
        this.senhausuario = senhausuario;
    }

    public UsuarioTransfer getUsuariotransfer() {
        return usuariotransfer;
    }

    public void setUsuariotransfer(UsuarioTransfer usuariotransfer) {
        this.usuariotransfer = usuariotransfer;
    }

}
