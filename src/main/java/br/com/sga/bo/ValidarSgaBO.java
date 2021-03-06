/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.sga.bo;

import br.com.sga.transfer.CargoTransfer;
import br.com.sga.transfer.LocalTransfer;
import br.com.sga.transfer.RelatorioTransfer;
import br.com.sga.transfer.ServicoTransfer;
import br.com.sga.transfer.TriagemAlternativaTransfer;
import br.com.sga.transfer.TriagemAtendimentoClienteTransfer;
import br.com.sga.transfer.UnidadeTransfer;
import br.com.sga.transfer.UsuarioTransfer;
import br.com.sga.util.Util;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.component.UIInput;
import javax.faces.context.FacesContext;
import javax.faces.validator.ValidatorException;


/**
 *
 * @author Jandisson
 */
public class ValidarSgaBO {

    //variavel de mensagem
    private String msg = null;

    //variavel de Util
    private final Util util = new Util();

    //variveis da Unidade
    private final UnidadeTransfer unidadetransfer = new UnidadeTransfer();
    private final UnidadeBO unidadebo = new UnidadeBO();

    //variveis do Local
    private final LocalTransfer localtransfer = new LocalTransfer();
    private final LocalBO localbo = new LocalBO();

    //variaveis do Servico
    private final ServicoTransfer servicotransfer = new ServicoTransfer();
    private final ServicoBO servicobo = new ServicoBO();

    //variaveis da TriagemAlternativa
    private final TriagemAlternativaBO triagemalternativaBO = new TriagemAlternativaBO();
    private final TriagemAlternativaTransfer triagemalternativatransfer = new TriagemAlternativaTransfer();

    //variavel da TriagemAtendimentoClienteTransfer
    private final TriagemAtendimentoChamadaClienteBO triagematendimentochamadaclientebo = new TriagemAtendimentoChamadaClienteBO();
    private final TriagemAtendimentoClienteTransfer triagematendimentoclientetransfer = new TriagemAtendimentoClienteTransfer();

    //variavel de Cargo
    private final CargoTransfer cargotransfer = new CargoTransfer();
    private final CargoBO cargobo = new CargoBO();

    //variavel de Usuario
    private final UsuarioTransfer usuariotransfer = new UsuarioTransfer();
    private final UsuarioBO usuariobo = new UsuarioBO();

    //variavel do Relatorio
    private final RelatorioTransfer relatoriotransfer = new RelatorioTransfer();

    /**
     * M??TODOS DE VALIDACAO DE UNIDADE
     */
    //metodo para validacao da unidade
    public boolean validarNomeUnidade(FacesContext context, UIComponent component, Object value) throws ValidatorException, SQLException {
        Object id = ((UIInput) context.getViewRoot().findComponent("formulario:id")).getLocalValue();
        Object nome = ((UIInput) context.getViewRoot().findComponent("formulario:nomeunidade")).getSubmittedValue();
        unidadetransfer.setId(Short.parseShort((id.toString())));
        unidadetransfer.setNomeunidade(nome.toString().toUpperCase());

        msg = "INFORME NOME DA UNIDADE";
        if ((unidadetransfer.getNomeunidade() == null || "".equalsIgnoreCase(unidadetransfer.getNomeunidade()))) {
            ((UIInput) component).setValid(false);
            context.addMessage((component.getClientId(context)), new FacesMessage(FacesMessage.SEVERITY_ERROR, msg, msg));
            return false;
        }
        msg = "A UNIDADE S?? DEVE TER 5 E 200 DE D??GITOS";
        if (unidadetransfer.getNomeunidade().trim().length() <= 5 || unidadetransfer.getNomeunidade().trim().length() > 200) {
            ((UIInput) component).setValid(false);
            context.addMessage(component.getClientId(context), new FacesMessage(FacesMessage.SEVERITY_ERROR, msg, msg));
            return false;
        }
        if (unidadebo.idNomeUnidadeConfere(unidadetransfer.getId(), unidadetransfer.getNomeunidade()) || unidadebo.existeNomeUnidade(unidadetransfer.getNomeunidade()) == false) {
            return false;
        } else {
            msg = "UNIDADE J?? EXISTE";
            ((UIInput) component).setValid(false);
            context.addMessage(component.getClientId(context), new FacesMessage(FacesMessage.SEVERITY_ERROR, msg, msg));
            return true;
        }
    }

    //metodo para validacao da sigla unidade
    public boolean validarSiglaUnidade(FacesContext context, UIComponent component, Object value) throws ValidatorException, SQLException {
        Object id = ((UIInput) context.getViewRoot().findComponent("formulario:id")).getLocalValue();
        Object nome = ((UIInput) context.getViewRoot().findComponent("formulario:siglaunidade")).getSubmittedValue();
        unidadetransfer.setId(Short.parseShort((id.toString())));
        unidadetransfer.setSiglaunidade(nome.toString().toUpperCase());

        msg = "INFORME A SIGLA DA UNIDADE";
        if ((unidadetransfer.getSiglaunidade() == null || "".equalsIgnoreCase(unidadetransfer.getSiglaunidade()))) {
            ((UIInput) component).setValid(false);
            context.addMessage((component.getClientId(context)), new FacesMessage(FacesMessage.SEVERITY_ERROR, msg, msg));
            return false;
        }
        msg = "A SIGLA DA UNIDADE S?? DEVE TER 2 E 20 DE D??GITOS";
        if (unidadetransfer.getSiglaunidade().trim().length() <= 2 || unidadetransfer.getSiglaunidade().trim().length() > 20) {
            ((UIInput) component).setValid(false);
            context.addMessage(component.getClientId(context), new FacesMessage(FacesMessage.SEVERITY_ERROR, msg, msg));
            return false;
        }
        return true;
    }

    /**
     * M??TODOS DE VALIDACAO DE LOCAL
     */
    //metodo para validacao do local
    public boolean validarNomeLocal(FacesContext context, UIComponent component, Object value) throws ValidatorException, SQLException {
        Object id = ((UIInput) context.getViewRoot().findComponent("formulario:id")).getLocalValue();
        Object nome = ((UIInput) context.getViewRoot().findComponent("formulario:nomelocal")).getSubmittedValue();
        localtransfer.setId(Short.parseShort((id.toString())));
        localtransfer.setNomelocal(nome.toString().toUpperCase());

        msg = "INFORME NOME DO LOCAL";
        if ((localtransfer.getNomelocal() == null || "".equalsIgnoreCase(localtransfer.getNomelocal()))) {
            ((UIInput) component).setValid(false);
            context.addMessage((component.getClientId(context)), new FacesMessage(FacesMessage.SEVERITY_ERROR, msg, msg));
            return false;
        }
        msg = "O NOME DO LOCAL S?? DEVE TER 4 E 30 DE D??GITOS";
        if (localtransfer.getNomelocal().trim().length() <= 3 || localtransfer.getNomelocal().trim().length() > 30) {
            ((UIInput) component).setValid(false);
            context.addMessage(component.getClientId(context), new FacesMessage(FacesMessage.SEVERITY_ERROR, msg, msg));
            return false;
        }
        if (localbo.idNomeLocalConfere(localtransfer.getId(), localtransfer.getNomelocal()) || localbo.existeNomeLocal(localtransfer.getNomelocal()) == false) {
            return false;
        } else {
            msg = "O LOCAL J?? EXISTE";
            ((UIInput) component).setValid(false);
            context.addMessage(component.getClientId(context), new FacesMessage(FacesMessage.SEVERITY_ERROR, msg, msg));
            return true;
        }
    }

    /**
     * M??TODOS DE VALIDACAO DE SERVICO
     */
    //metodo para validacao do servico
    public boolean validarNomeServico(FacesContext context, UIComponent component, Object value) throws ValidatorException, SQLException {
        Object id = ((UIInput) context.getViewRoot().findComponent("formulario:id")).getLocalValue();
        Object nome = ((UIInput) context.getViewRoot().findComponent("formulario:nomeservico")).getSubmittedValue();
        servicotransfer.setId(Short.parseShort((id.toString())));
        servicotransfer.setNomeservico(nome.toString().toUpperCase());

        msg = "INFORME O NOME DO SERVI??O";
        if ((servicotransfer.getNomeservico() == null || "".equalsIgnoreCase(servicotransfer.getNomeservico()))) {
            ((UIInput) component).setValid(false);
            context.addMessage((component.getClientId(context)), new FacesMessage(FacesMessage.SEVERITY_ERROR, msg, msg));
            return false;
        }
        msg = "O NOME DO SERVI??O S?? DEVE TER 3 E 30 DE D??GITOS";
        if (servicotransfer.getNomeservico().trim().length() <= 2 || servicotransfer.getNomeservico().trim().length() > 30) {
            ((UIInput) component).setValid(false);
            context.addMessage(component.getClientId(context), new FacesMessage(FacesMessage.SEVERITY_ERROR, msg, msg));
            return false;
        }
        if (servicobo.idNomeServicoConfere(servicotransfer.getId(), servicotransfer.getNomeservico()) || servicobo.existeNomeServico(servicotransfer.getNomeservico()) == false) {
            return false;
        } else {
            msg = "O SERVI??O J?? EXISTE";
            ((UIInput) component).setValid(false);
            context.addMessage(component.getClientId(context), new FacesMessage(FacesMessage.SEVERITY_ERROR, msg, msg));
            return true;
        }
    }

    //metodo para validacao do servico
    public boolean validarDescricaoServico(FacesContext context, UIComponent component, Object value) throws ValidatorException, SQLException {
        Object id = ((UIInput) context.getViewRoot().findComponent("formulario:id")).getLocalValue();
        Object descricao = ((UIInput) context.getViewRoot().findComponent("formulario:descricaoservico")).getSubmittedValue();
        servicotransfer.setId(Short.parseShort((id.toString())));
        servicotransfer.setDescricaoservico(descricao.toString().toUpperCase());

        msg = "INFORME A DESCRI????O DO SERVI??O";
        if ((servicotransfer.getDescricaoservico() == null || "".equalsIgnoreCase(servicotransfer.getDescricaoservico()))) {
            ((UIInput) component).setValid(false);
            context.addMessage((component.getClientId(context)), new FacesMessage(FacesMessage.SEVERITY_ERROR, msg, msg));
            return false;
        }
        msg = "A DESCRI????O DO SERVI??O S?? DEVE TER 5 E 200 DE D??GITOS";
        if (servicotransfer.getDescricaoservico().trim().length() <= 4 || servicotransfer.getDescricaoservico().trim().length() > 200) {
            ((UIInput) component).setValid(false);
            context.addMessage(component.getClientId(context), new FacesMessage(FacesMessage.SEVERITY_ERROR, msg, msg));
            return false;
        }
        return true;
    }

    /**
     * M??TODOS DE TRIAGEM ALTERNATIVA
     */
    //metodo para validacao da sigla triagemalternativa
    public boolean validarSiglaTriagemAlternativa(FacesContext context, UIComponent component, Object value) throws ValidatorException, SQLException {
        Object id = ((UIInput) context.getViewRoot().findComponent("formulario:id")).getLocalValue();
        Object nome = ((UIInput) context.getViewRoot().findComponent("formulario:siglatriagemalternativa")).getSubmittedValue();
        triagemalternativatransfer.setId(Short.parseShort((id.toString())));
        triagemalternativatransfer.setSiglatriagemalternativa(nome.toString().toUpperCase());

        msg = "INFORME A SIGLA ALTERNATIVA";
        if ((triagemalternativatransfer.getSiglatriagemalternativa() == null || "".equalsIgnoreCase(triagemalternativatransfer.getSiglatriagemalternativa()))) {
            ((UIInput) component).setValid(false);
            context.addMessage((component.getClientId(context)), new FacesMessage(FacesMessage.SEVERITY_ERROR, msg, msg));
            return false;
        }
        msg = "A SIGLA ALTERNATIVA S?? DEVE TER 2 E 10 DE D??GITOS";
        if (triagemalternativatransfer.getSiglatriagemalternativa().trim().length() <= 1 || triagemalternativatransfer.getSiglatriagemalternativa().trim().length() > 10) {
            ((UIInput) component).setValid(false);
            context.addMessage(component.getClientId(context), new FacesMessage(FacesMessage.SEVERITY_ERROR, msg, msg));
            return false;
        }
        if (triagemalternativaBO.idSiglaTriagemAlternativaConfere(triagemalternativatransfer.getId(), triagemalternativatransfer.getSiglatriagemalternativa()) || triagemalternativaBO.existeSiglaTriagemalternativa(triagemalternativatransfer.getSiglatriagemalternativa()) == false) {
            return false;
        } else {
            msg = "A SIGLA ALTERNATIVA J?? EXISTE";
            ((UIInput) component).setValid(false);
            context.addMessage(component.getClientId(context), new FacesMessage(FacesMessage.SEVERITY_ERROR, msg, msg));
            return true;
        }
    }

    //metodo para validacao do local triagemalternativa
    public boolean validarLocalTriagemAlternativa(FacesContext context, UIComponent component, Object value) throws ValidatorException, SQLException {
        Object id = ((UIInput) context.getViewRoot().findComponent("formulario:id")).getLocalValue();
        Object nome = ((UIInput) context.getViewRoot().findComponent("formulario:local")).getSubmittedValue();
        triagemalternativatransfer.setId(Short.parseShort((id.toString())));
        localtransfer.setNomelocal(nome.toString().toUpperCase());
        triagemalternativatransfer.setLocaltransfer(localtransfer);

        msg = "INFORME O LOCAL PARA A TRIAGEM ALTERNATIVA";
        if ((triagemalternativatransfer.getLocaltransfer().getNomelocal() == null || "".equalsIgnoreCase(triagemalternativatransfer.getLocaltransfer().getNomelocal()))) {
            ((UIInput) component).setValid(false);
            context.addMessage((component.getClientId(context)), new FacesMessage(FacesMessage.SEVERITY_ERROR, msg, msg));
            return false;
        }
//        if (triagemalternativaBO.idNomeLocalTriagemAlternativaConfere(triagemalternativatransfer.getId(), triagemalternativatransfer.getLocaltransfer().getNomelocal()) || triagemalternativaBO.existeNomeLocalTriagemAlternativaConfere(triagemalternativatransfer.getLocaltransfer().getNomelocal()) == false) {
//            return false;
//        } else {
//            msg = "SIGLA EXISTE";
//            ((UIInput) component).setValid(false);
//            context.addMessage(component.getClientId(context), new FacesMessage(FacesMessage.SEVERITY_ERROR, msg, msg));
//            return true;
//        }
        return true;

    }

    //metodo para validacao do servico triagemalternativa
    public boolean validarServicoTriagemAlternativa(FacesContext context, UIComponent component, Object value) throws ValidatorException, SQLException {
        Object id = ((UIInput) context.getViewRoot().findComponent("formulario:id")).getLocalValue();
        Object nome = ((UIInput) context.getViewRoot().findComponent("formulario:servico")).getSubmittedValue();
        triagemalternativatransfer.setId(Short.parseShort((id.toString())));
        servicotransfer.setNomeservico(nome.toString().toUpperCase());
        triagemalternativatransfer.setServicotransfer(servicotransfer);

        msg = "INFORME O SERVI??O PARA A TRIAGEM ALTERNATIVA";
        if ((triagemalternativatransfer.getServicotransfer().getNomeservico() == null || "".equalsIgnoreCase(triagemalternativatransfer.getServicotransfer().getNomeservico()))) {
            ((UIInput) component).setValid(false);
            context.addMessage((component.getClientId(context)), new FacesMessage(FacesMessage.SEVERITY_ERROR, msg, msg));
            return false;
        }
//        if (triagemalternativaBO.idNomeServicoTriagemAlternativaConfere(triagemalternativatransfer.getId(), triagemalternativatransfer.getServicotransfer().getNomeservico()) || triagemalternativaBO.existeNomeServicoTriagemAlternativaConfere(triagemalternativatransfer.getServicotransfer().getNomeservico()) == false) {
//            return false;
//        } else {
//            msg = "SERVICO EXISTE";
//            ((UIInput) component).setValid(false);
//            context.addMessage(component.getClientId(context), new FacesMessage(FacesMessage.SEVERITY_ERROR, msg, msg));
//            return true;
//        }
        return true;

    }

    /**
     * M??TODOS DE VALIDACAO DE ATENDIMENTO
     */
    //metodo para validacao do local
    public boolean validarNumeroLocal(FacesContext context, UIComponent component, Object value) throws ValidatorException, SQLException {
        Object numerolocal = ((UIInput) context.getViewRoot().findComponent("consultaratendimento:numeroatendimentocliente")).getSubmittedValue();

        msg = "INFORME O N??MERO DO LOCAL DE ATENDIMENTO";
        if (numerolocal.toString().equals("") || numerolocal == null) {
            ((UIInput) component).setValid(false);
            context.addMessage(component.getClientId(context), new FacesMessage(FacesMessage.SEVERITY_ERROR, msg, msg));
            return false;
        }

        triagematendimentoclientetransfer.setNumerodolocal(Integer.parseInt(numerolocal.toString()));

        msg = "O N??MERO DO LOCAL DEVE EST?? ENTRE 1 E 99";
        if (triagematendimentoclientetransfer.getNumerodolocal() < 1 || triagematendimentoclientetransfer.getNumerodolocal() > 100) {
            ((UIInput) component).setValid(false);
            context.addMessage(component.getClientId(context), new FacesMessage(FacesMessage.SEVERITY_ERROR, msg, msg));
            return false;
        }
        return true;
    }

    /**
     * M??TODO DE VALIDACAO DE CARGO
     */
    //metodo para validacao de cargo
    public boolean validarNomeCargo(FacesContext context, UIComponent component, Object value) throws ValidatorException, SQLException {
        Object id = ((UIInput) context.getViewRoot().findComponent("formulariocargo:tabview:id")).getLocalValue();
        Object nome = ((UIInput) context.getViewRoot().findComponent("formulariocargo:tabview:nomecargo")).getSubmittedValue();
        cargotransfer.setId(Short.parseShort((id.toString())));
        cargotransfer.setNomecargo(nome.toString().toUpperCase());

        msg = "INFORME O NOME DO CARGO";
        if ((cargotransfer.getNomecargo() == null || "".equalsIgnoreCase(cargotransfer.getNomecargo()))) {
            ((UIInput) component).setValid(false);
            context.addMessage((component.getClientId(context)), new FacesMessage(FacesMessage.SEVERITY_ERROR, msg, msg));
            return false;
        }
        msg = "O NOME DO CARGO S?? DEVE TER 4 E 30 DE D??GITOS";
        if (cargotransfer.getNomecargo().trim().length() <= 3 || cargotransfer.getNomecargo().trim().length() > 30) {
            ((UIInput) component).setValid(false);
            context.addMessage(component.getClientId(context), new FacesMessage(FacesMessage.SEVERITY_ERROR, msg, msg));
            return false;
        }
        if (cargobo.idNomedoCargoConfere(cargotransfer.getId(), cargotransfer.getNomecargo()) || cargobo.existeNomeCargo(cargotransfer.getNomecargo()) == false) {
            return false;
        } else {
            msg = "O CARGO J?? EXISTE";
            ((UIInput) component).setValid(false);
            context.addMessage(component.getClientId(context), new FacesMessage(FacesMessage.SEVERITY_ERROR, msg, msg));
            return true;
        }
    }

    //metodo para validacao da descricao do cargo
    public boolean validarDescricaoCargo(FacesContext context, UIComponent component, Object value) throws ValidatorException, SQLException {
        Object id = ((UIInput) context.getViewRoot().findComponent("formulariocargo:tabview:id")).getLocalValue();
        Object descricao = ((UIInput) context.getViewRoot().findComponent("formulariocargo:tabview:descricaocargo")).getSubmittedValue();
        cargotransfer.setId(Short.parseShort((id.toString())));
        cargotransfer.setDescricaocargo(descricao.toString().toUpperCase());

        msg = "INFORME DESCRI????O DO CARGO";
        if ((cargotransfer.getDescricaocargo() == null || "".equalsIgnoreCase(cargotransfer.getDescricaocargo()))) {
            ((UIInput) component).setValid(false);
            context.addMessage((component.getClientId(context)), new FacesMessage(FacesMessage.SEVERITY_ERROR, msg, msg));
            return false;
        }
        msg = "A DESCRI????O DO CARGO S?? DEVE TER 4 E 200 DE D??GITOS";
        if (cargotransfer.getDescricaocargo().trim().length() <= 3 || cargotransfer.getDescricaocargo().trim().length() > 200) {
            ((UIInput) component).setValid(false);
            context.addMessage(component.getClientId(context), new FacesMessage(FacesMessage.SEVERITY_ERROR, msg, msg));
            return false;
        }
        return true;
    }

    /**
     * M??todos de valida????o do usu??rio
     */
    //metodo validar Usuario
    public boolean validarUsuario(FacesContext context, UIComponent component, Object value) throws ValidatorException {
        Object id = ((UIInput) context.getViewRoot().findComponent("formulariousuario:tabview:id")).getLocalValue();
        Object usuario = ((UIInput) context.getViewRoot().findComponent("formulariousuario:tabview:usuario")).getSubmittedValue();
        usuariotransfer.setId(Short.valueOf(id.toString()));
        usuariotransfer.setNomeusuario(usuario.toString().toUpperCase());

        msg = "INFORME USU??RIO";
        if ((usuariotransfer.getNomeusuario() == null || "".equalsIgnoreCase(usuariotransfer.getNomeusuario()))) {
            ((UIInput) component).setValid(false);
            context.addMessage(component.getClientId(context), new FacesMessage(FacesMessage.SEVERITY_ERROR, msg, msg));
            return false;
        }
        msg = "O USU??RIO S?? DEVE TER 5 E 200 DE D??GITOS";
        if (usuariotransfer.getNomeusuario().trim().length() <= 4 || usuariotransfer.getNomeusuario().trim().length() > 200) {
            ((UIInput) component).setValid(false);
            context.addMessage(component.getClientId(context), new FacesMessage(FacesMessage.SEVERITY_ERROR, msg, msg));
            return false;
        }
        if (usuariobo.IdUsuarioConfere(usuariotransfer.getId(), usuariotransfer.getNomeusuario()) || usuariobo.existeUsuario(usuariotransfer.getNomeusuario()) == false) {
            return false;
        } else {
            msg = "USU??RIO EXISTE";
            ((UIInput) component).setValid(false);
            context.addMessage(component.getClientId(context), new FacesMessage(FacesMessage.SEVERITY_ERROR, msg, msg));
            return true;
        }
    }

    //metodo validar nome Usuario
    public boolean validarNomedeUsuario(FacesContext context, UIComponent component, Object value) throws ValidatorException {
        Object id = ((UIInput) context.getViewRoot().findComponent("formulariousuario:tabview:id")).getLocalValue();
        Object usuario = ((UIInput) context.getViewRoot().findComponent("formulariousuario:tabview:sobrenomeusuario")).getSubmittedValue();
        usuariotransfer.setId(Short.valueOf(id.toString()));
        usuariotransfer.setSobrenomeusuario(usuario.toString().toUpperCase());

        msg = "INFORME O NOME DO USU??RIO";
        if ((usuariotransfer.getSobrenomeusuario() == null || "".equalsIgnoreCase(usuariotransfer.getSobrenomeusuario()))) {
            ((UIInput) component).setValid(false);
            context.addMessage(component.getClientId(context), new FacesMessage(FacesMessage.SEVERITY_ERROR, msg, msg));
            return false;
        }
        msg = "O NOME DO USU??RIO S?? DEVE TER 5 E 200 DE D??GITOS";
        if (usuariotransfer.getSobrenomeusuario().trim().length() <= 4 || usuariotransfer.getSobrenomeusuario().trim().length() > 200) {
            ((UIInput) component).setValid(false);
            context.addMessage(component.getClientId(context), new FacesMessage(FacesMessage.SEVERITY_ERROR, msg, msg));
            return false;
        }
        return true;
    }

    //metodo de validar a senha de cadastro do usuario
    public boolean validarUsuarioSenhaCadastrar(FacesContext context, UIComponent component, Object value) throws ValidatorException {
        Object senha = ((UIInput) context.getViewRoot().findComponent("formulariousuario:tabview:password")).getSubmittedValue();
        Object senhaconfirmar = ((UIInput) context.getViewRoot().findComponent("formulariousuario:tabview:senha")).getSubmittedValue();
        usuariotransfer.setSenhausuario(senha.toString());
        usuariotransfer.setSenhaconfirmacao(senhaconfirmar.toString());

        msg = "INFORME SENHA";
        if ((usuariotransfer.getSenhausuario() == null || "".equalsIgnoreCase(usuariotransfer.getSenhausuario()))) {
            ((UIInput) component).setValid(false);
            context.addMessage(component.getClientId(context), new FacesMessage(FacesMessage.SEVERITY_ERROR, msg, msg));
            return false;
        }

        msg = "A SENHA S?? DEVE TER 8 E 20 D??GITOS";
        if (usuariotransfer.getSenhausuario().trim().length() <= 7 || usuariotransfer.getSenhausuario().trim().length() > 20) {
            ((UIInput) component).setValid(false);
            context.addMessage(component.getClientId(context), new FacesMessage(FacesMessage.SEVERITY_ERROR, msg, msg));
            return false;
        }

        msg = "INFORME SENHA DE CONFIRMA????O";
        if ((usuariotransfer.getSenhaconfirmacao() == null || "".equalsIgnoreCase(usuariotransfer.getSenhaconfirmacao()))) {
            ((UIInput) component).setValid(false);
            context.addMessage(component.getClientId(context), new FacesMessage(FacesMessage.SEVERITY_ERROR, msg, msg));
            return false;
        }
        msg = "SENHA E A CONFIRMA????O DE SENHA S??O DIFERENTES";
        if (!usuariotransfer.getSenhausuario().equalsIgnoreCase(usuariotransfer.getSenhaconfirmacao())) {
            ((UIInput) component).setValid(false);
            context.addMessage(component.getClientId(context), new FacesMessage(FacesMessage.SEVERITY_ERROR, msg, msg));
            return false;
        }
        return true;
    }

    //metodo de validar a senha de alteracao do usuario
    public boolean validarUsuarioSenhaAlterar(FacesContext context, UIComponent component, Object value) throws ValidatorException {
        Object senha = ((UIInput) context.getViewRoot().findComponent("formulariosenhaeditarusuario:password")).getSubmittedValue();
        Object senhaconfirmar = ((UIInput) context.getViewRoot().findComponent("formulariosenhaeditarusuario:senha")).getSubmittedValue();
        usuariotransfer.setSenhausuario(senha.toString());
        usuariotransfer.setSenhaconfirmacao(senhaconfirmar.toString());

        msg = "INFORME SENHA";
        if ((usuariotransfer.getSenhausuario() == null || "".equalsIgnoreCase(usuariotransfer.getSenhausuario()))) {
            ((UIInput) component).setValid(false);
            context.addMessage(component.getClientId(context), new FacesMessage(FacesMessage.SEVERITY_ERROR, msg, msg));
            return false;
        }

        msg = "A SENHA S?? DEVE TER 8 E 20 D??GITOS";
        if (usuariotransfer.getSenhausuario().trim().length() <= 7 || usuariotransfer.getSenhausuario().trim().length() > 20) {
            ((UIInput) component).setValid(false);
            context.addMessage(component.getClientId(context), new FacesMessage(FacesMessage.SEVERITY_ERROR, msg, msg));
            return false;
        }

        msg = "INFORME SENHA DE CONFIRMA????O";
        if ((usuariotransfer.getSenhaconfirmacao() == null || "".equalsIgnoreCase(usuariotransfer.getSenhaconfirmacao()))) {
            ((UIInput) component).setValid(false);
            context.addMessage(component.getClientId(context), new FacesMessage(FacesMessage.SEVERITY_ERROR, msg, msg));
            return false;
        }
        msg = "SENHA E A CONFIRMA????O DE SENHA S??O DIFERENTES";
        if (!usuariotransfer.getSenhausuario().equalsIgnoreCase(usuariotransfer.getSenhaconfirmacao())) {
            ((UIInput) component).setValid(false);
            context.addMessage(component.getClientId(context), new FacesMessage(FacesMessage.SEVERITY_ERROR, msg, msg));
            return false;
        }
        return true;
    }

    //metodo para validacao da unidade do usuario
    public boolean validarUsuarioUnidade(FacesContext context, UIComponent component, Object value) throws ValidatorException, SQLException {
        Object id = ((UIInput) context.getViewRoot().findComponent("formulariousuario:tabview:id")).getLocalValue();
        Object nome = ((UIInput) context.getViewRoot().findComponent("formulariousuario:tabview:unidade")).getSubmittedValue();
        usuariotransfer.setId(Short.parseShort((id.toString())));
        unidadetransfer.setNomeunidade(nome.toString().toUpperCase());
        usuariotransfer.setUnidade(unidadetransfer);

        msg = "INFORME A UNIDADE";
        if ((usuariotransfer.getUnidade().getNomeunidade() == null || "".equalsIgnoreCase(usuariotransfer.getUnidade().getNomeunidade()))) {
            ((UIInput) component).setValid(false);
            context.addMessage((component.getClientId(context)), new FacesMessage(FacesMessage.SEVERITY_ERROR, msg, msg));
            return false;
        }
        return true;
    }

    //metodo para validacao do cargo do usuario
    public boolean validarUsuarioCargo(FacesContext context, UIComponent component, Object value) throws ValidatorException, SQLException {
        Object id = ((UIInput) context.getViewRoot().findComponent("formulariousuario:tabview:id")).getLocalValue();
        Object nome = ((UIInput) context.getViewRoot().findComponent("formulariousuario:tabview:cargo")).getSubmittedValue();
        usuariotransfer.setId(Short.parseShort((id.toString())));
        cargotransfer.setNomecargo(nome.toString().toUpperCase());
        usuariotransfer.setCargo(cargotransfer);

        msg = "INFORME O CARGO";
        if ((usuariotransfer.getCargo().getNomecargo() == null || "".equalsIgnoreCase(usuariotransfer.getCargo().getNomecargo()))) {
            ((UIInput) component).setValid(false);
            context.addMessage((component.getClientId(context)), new FacesMessage(FacesMessage.SEVERITY_ERROR, msg, msg));
            return false;
        }
        return true;
    }

    /**
     * M??todos de Login
     */
    //metodo validar Usuario e Senha do Login
    public boolean validarUsuarioSenhaLogin(FacesContext context, UIComponent component, Object value) throws ValidatorException, SQLException {
        Object usuario = ((UIInput) context.getViewRoot().findComponent("formlogin:usuario")).getSubmittedValue();
        Object senha = ((UIInput) context.getViewRoot().findComponent("formlogin:senha")).getSubmittedValue();

        usuariotransfer.setNomeusuario(usuario.toString().toUpperCase());
        usuariotransfer.setSenhausuario(senha.toString());

        msg = "INFORME USU??RIO E SENHA";
        if ((usuariotransfer.getSenhausuario().equals("") || "".equalsIgnoreCase(usuariotransfer.getSenhausuario())) && (usuariotransfer.getNomeusuario() == null || "".equalsIgnoreCase(usuariotransfer.getNomeusuario()))) {
            ((UIInput) component).setValid(false);
            context.addMessage(component.getClientId(context), new FacesMessage(FacesMessage.SEVERITY_ERROR, msg, msg));
            return false;
        }
        msg = "O USU??RIO S?? DEVE TER 5 E 200 DE D??GITOS";
        if (usuariotransfer.getNomeusuario().trim().length() <= 4 || usuariotransfer.getNomeusuario().trim().length() > 200) {
            ((UIInput) component).setValid(false);
            context.addMessage(component.getClientId(context), new FacesMessage(FacesMessage.SEVERITY_ERROR, msg, msg));
            return false;
        }
       /** msg = "A SENHA S?? DEVE TER 8 E 20 D??GITOS";
        if (usuariotransfer.getSenhausuario().trim().length() <= 7 || usuariotransfer.getSenhausuario().trim().length() > 20) {
            ((UIInput) component).setValid(false);
            context.addMessage(component.getClientId(context), new FacesMessage(FacesMessage.SEVERITY_ERROR, msg, msg));
            return false;
        }***/
        msg = "USU??RIO INEXISTENTE";
        if (usuariobo.existeUsuario(usuariotransfer.getNomeusuario()) == false) {
            ((UIInput) component).setValid(false);
            context.addMessage(component.getClientId(context), new FacesMessage(FacesMessage.SEVERITY_ERROR, msg, msg));
        }
        return true;
    }

    //metodo de validar a senha de alteracao do usuario
    public boolean validarSenhadoUsuarioAlterar(FacesContext context, UIComponent component, Object value) throws ValidatorException {
        Object senha = ((UIInput) context.getViewRoot().findComponent("formulariosenhadousuarioalterar:password")).getSubmittedValue();
        Object senhaconfirmar = ((UIInput) context.getViewRoot().findComponent("formulariosenhadousuarioalterar:senha")).getSubmittedValue();
        usuariotransfer.setSenhausuario(senha.toString());
        usuariotransfer.setSenhaconfirmacao(senhaconfirmar.toString());

        msg = "INFORME SENHA";
        if ((usuariotransfer.getSenhausuario() == null || "".equalsIgnoreCase(usuariotransfer.getSenhausuario()))) {
            ((UIInput) component).setValid(false);
            context.addMessage(component.getClientId(context), new FacesMessage(FacesMessage.SEVERITY_ERROR, msg, msg));
            return false;
        }

        msg = "A SENHA S?? DEVE TER 8 E 20 D??GITOS";
        if (usuariotransfer.getSenhausuario().trim().length() <= 7 || usuariotransfer.getSenhausuario().trim().length() > 20) {
            ((UIInput) component).setValid(false);
            context.addMessage(component.getClientId(context), new FacesMessage(FacesMessage.SEVERITY_ERROR, msg, msg));
            return false;
        }

        msg = "INFORME SENHA DE CONFIRMA????O";
        if ((usuariotransfer.getSenhaconfirmacao() == null || "".equalsIgnoreCase(usuariotransfer.getSenhaconfirmacao()))) {
            ((UIInput) component).setValid(false);
            context.addMessage(component.getClientId(context), new FacesMessage(FacesMessage.SEVERITY_ERROR, msg, msg));
            return false;
        }
        msg = "SENHA E A CONFIRMA????O DE SENHA SAO DIFERENTES";
        if (!usuariotransfer.getSenhausuario().equalsIgnoreCase(usuariotransfer.getSenhaconfirmacao())) {
            ((UIInput) component).setValid(false);
            context.addMessage(component.getClientId(context), new FacesMessage(FacesMessage.SEVERITY_ERROR, msg, msg));
            return false;
        }
        return true;
    }

    /**
     * M??todos de Relatorio
     */
    //metodo validar Data inicial do relatorio
    public boolean validarDataInicialRelatorio(FacesContext context, UIComponent component, Object value) throws ValidatorException {
        Object datainicial = ((UIInput) context.getViewRoot().findComponent("formulariorelatorioestatisticas:datainiciorelatorio")).getSubmittedValue();
        Object datafinal = ((UIInput) context.getViewRoot().findComponent("formulariorelatorioestatisticas:datafinalrelatorio")).getSubmittedValue();

        int anoatual = Calendar.getInstance().getWeekYear();

        msg = "INFORME DATA INICIAL";
        if ("".equalsIgnoreCase(datainicial.toString())) {
            ((UIInput) component).setValid(false);
            context.addMessage(component.getClientId(context), new FacesMessage(FacesMessage.SEVERITY_ERROR, msg, msg));
            return false;
        }

        msg = "INFORME DATA FINAL";
        if ("".equalsIgnoreCase(datafinal.toString())) {
            ((UIInput) component).setValid(false);
            context.addMessage(component.getClientId(context), new FacesMessage(FacesMessage.SEVERITY_ERROR, msg, msg));
            return false;
        }

        SimpleDateFormat formatadordatainicial = new SimpleDateFormat("dd/MM/yyyy");
        SimpleDateFormat formatadoranodatainicial = new SimpleDateFormat("yyyy");
        Date datainicialFormatada = null;
        SimpleDateFormat formatadordatafinal = new SimpleDateFormat("dd/MM/yyyy");
        SimpleDateFormat formatadoranodatafinal = new SimpleDateFormat("yyyy");
        Date datafinalFormatada = null;

        try {
            datainicialFormatada = formatadordatainicial.parse(datainicial.toString());
            datafinalFormatada = formatadordatafinal.parse(datafinal.toString());
        } catch (ParseException ex) {
            java.util.logging.Logger.getLogger(ValidarSgaBO.class.getName()).log(Level.SEVERE, null, ex);
        }

        relatoriotransfer.setDatainicialrelatorio(datainicialFormatada);
        relatoriotransfer.setDatafinallrelatorio(datafinalFormatada);

        msg = "A DATA INICIAL DEVE SER MENOR OU IGUAL A HOJE";
        if (relatoriotransfer.getDatainicialrelatorio().after(util.getPegaDataAtual()) == true) {
            ((UIInput) component).setValid(false);
            context.addMessage(component.getClientId(context), new FacesMessage(FacesMessage.SEVERITY_ERROR, msg, msg));
            return false;
        }

        msg = "A DATA INICIAL DEVE SER MENOR OU IGUAL A DATA FINAL";
        if (relatoriotransfer.getDatainicialrelatorio().after(relatoriotransfer.getDatafinallrelatorio())) {
            ((UIInput) component).setValid(false);
            context.addMessage(component.getClientId(context), new FacesMessage(FacesMessage.SEVERITY_ERROR, msg, msg));
            return false;
        }

        int valorinicialano = Integer.parseInt(formatadoranodatainicial.format(relatoriotransfer.getDatainicialrelatorio()));
        msg = "A DATA INICIAL DEVE EST?? ENTRE 2009 E " + anoatual;
        if (valorinicialano < 2019 || valorinicialano > anoatual) {
            ((UIInput) component).setValid(false);
            context.addMessage(component.getClientId(context), new FacesMessage(FacesMessage.SEVERITY_ERROR, msg, msg));
            return false;
        }

        //inicia procedimento data final
        msg = "A DATA FINAL DEVE SER MENOR OU IGUAL A HOJE";
        if (relatoriotransfer.getDatafinallrelatorio().after(util.getPegaDataAtual()) == true) {
            ((UIInput) component).setValid(false);
            context.addMessage(component.getClientId(context), new FacesMessage(FacesMessage.SEVERITY_ERROR, msg, msg));
            return false;
        }

        int valorfinalano = Integer.parseInt(formatadoranodatafinal.format(relatoriotransfer.getDatafinallrelatorio()));
        msg = "A DATA FINAL DEVE ESTA ENTRE 2019 E " + anoatual;
        if (valorfinalano < 2019 || valorfinalano > anoatual) {
            ((UIInput) component).setValid(false);
            context.addMessage(component.getClientId(context), new FacesMessage(FacesMessage.SEVERITY_ERROR, msg, msg));
            return false;
        }

        return true;
    }

    //metodo validar Data inicial do relatorio
    public boolean validarDataInicialGrafico(FacesContext context, UIComponent component, Object value) throws ValidatorException {
        Object datainicial = ((UIInput) context.getViewRoot().findComponent("formulariograficosestatisticas:datainiciografico")).getSubmittedValue();
        Object datafinal = ((UIInput) context.getViewRoot().findComponent("formulariograficosestatisticas:datafinalgrafico")).getSubmittedValue();

        //ano atual
        int anoatual = Calendar.getInstance().getWeekYear();

        msg = "INFORME DATA INICIAL";
        if ("".equalsIgnoreCase(datainicial.toString())) {
            ((UIInput) component).setValid(false);
            context.addMessage(component.getClientId(context), new FacesMessage(FacesMessage.SEVERITY_ERROR, msg, msg));
            return false;
        }

        msg = "INFORME DATA FINAL";
        if ("".equalsIgnoreCase(datafinal.toString())) {
            ((UIInput) component).setValid(false);
            context.addMessage(component.getClientId(context), new FacesMessage(FacesMessage.SEVERITY_ERROR, msg, msg));
            return false;
        }

        SimpleDateFormat formatadordatainicial = new SimpleDateFormat("dd/MM/yyyy");
        SimpleDateFormat formatadoranodatainicial = new SimpleDateFormat("yyyy");
        Date datainicialFormatada = null;
        SimpleDateFormat formatadordatafinal = new SimpleDateFormat("dd/MM/yyyy");
        SimpleDateFormat formatadoranodatafinal = new SimpleDateFormat("yyyy");
        Date datafinalFormatada = null;

        try {
            datainicialFormatada = formatadordatainicial.parse(datainicial.toString());
            datafinalFormatada = formatadordatafinal.parse(datafinal.toString());
        } catch (ParseException ex) {
            java.util.logging.Logger.getLogger(ValidarSgaBO.class.getName()).log(Level.SEVERE, null, ex);
        }

        relatoriotransfer.setDatainicialrelatorio(datainicialFormatada);
        relatoriotransfer.setDatafinallrelatorio(datafinalFormatada);

        msg = "A DATA INICIAL DEVE SER MENOR OU IGUAL A HOJE";
        if (relatoriotransfer.getDatainicialrelatorio().after(util.getPegaDataAtual()) == true) {
            ((UIInput) component).setValid(false);
            context.addMessage(component.getClientId(context), new FacesMessage(FacesMessage.SEVERITY_ERROR, msg, msg));
            return false;
        }

        msg = "A DATA INICIAL DEVE SER MENOR OU IGUAL A DATA FINAL";
        if (relatoriotransfer.getDatainicialrelatorio().after(relatoriotransfer.getDatafinallrelatorio())) {
            ((UIInput) component).setValid(false);
            context.addMessage(component.getClientId(context), new FacesMessage(FacesMessage.SEVERITY_ERROR, msg, msg));
            return false;
        }

        int valorinicialano = Integer.parseInt(formatadoranodatainicial.format(relatoriotransfer.getDatainicialrelatorio()));

        msg = "A DATA INICIAL DEVE ESTA ENTRE 2019 E " + anoatual;
        if (valorinicialano < 2019 || valorinicialano > anoatual) {
            ((UIInput) component).setValid(false);
            context.addMessage(component.getClientId(context), new FacesMessage(FacesMessage.SEVERITY_ERROR, msg, msg));
            return false;
        }

        //inicia procedimento data final
        msg = "A DATA FINAL DEVE SER MENOR OU IGUAL A HOJE";
        if (relatoriotransfer.getDatafinallrelatorio().after(util.getPegaDataAtual()) == true) {
            ((UIInput) component).setValid(false);
            context.addMessage(component.getClientId(context), new FacesMessage(FacesMessage.SEVERITY_ERROR, msg, msg));
            return false;
        }

        int valorfinalano = Integer.parseInt(formatadoranodatafinal.format(relatoriotransfer.getDatafinallrelatorio()));
        msg = "A DATA FINAL DEVE ESTA ENTRE 2019 E " + anoatual;
        if (valorfinalano < 2019 || valorfinalano > anoatual) {
            ((UIInput) component).setValid(false);
            context.addMessage(component.getClientId(context), new FacesMessage(FacesMessage.SEVERITY_ERROR, msg, msg));
            return false;
        }

        return true;
    }

    /**
     * Metodos de validacao de video
     */
    public boolean ehIdValido(String s) {
        return s.matches("[0-9A-Za-z_-]{11}");
    }

    public boolean validarIdVideo(FacesContext context, UIComponent component, Object value) throws ValidatorException, SQLException {
        Object idvideo = ((UIInput) context.getViewRoot().findComponent("formulario:idvideo")).getSubmittedValue();

        msg = "INFORME ID DO V??DEO";
        if ((idvideo == null || "".equalsIgnoreCase(idvideo.toString()))) {
            ((UIInput) component).setValid(false);
            context.addMessage((component.getClientId(context)), new FacesMessage(FacesMessage.SEVERITY_ERROR, msg, msg));
            return false;
        }

        msg = "O ID DO V??DEO DEVE TER 11 D??GITOS";
        if (idvideo.toString().trim().length() <= 10) {
            ((UIInput) component).setValid(false);
            context.addMessage((component.getClientId(context)), new FacesMessage(FacesMessage.SEVERITY_ERROR, msg, msg));
            return false;
        }

        boolean v = ehIdValido(idvideo.toString());

        msg = "ID DO V??DEO ?? INV??LIDO";
        if (v == false) {
            ((UIInput) component).setValid(false);
            context.addMessage((component.getClientId(context)), new FacesMessage(FacesMessage.SEVERITY_ERROR, msg, msg));
            return false;
        }
        return true;
    }
}