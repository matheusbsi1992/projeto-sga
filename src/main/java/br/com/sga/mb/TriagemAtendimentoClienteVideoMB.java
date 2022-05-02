/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.sga.mb;

import br.com.sga.bo.TriagemAtendimentoClienteVideoBO;
import br.com.sga.bo.ValidarSgaBO;
import java.io.Serializable;
import java.sql.SQLException;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.bean.SessionScoped;
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
@ManagedBean
@SessionScoped
public class TriagemAtendimentoClienteVideoMB implements Serializable {

    public TriagemAtendimentoClienteVideoMB() {
    }

    private TriagemAtendimentoClienteVideoBO triagematendimentoclientevideobo = new TriagemAtendimentoClienteVideoBO();

    //cria variavel da classe ValidarSgaBO
    private ValidarSgaBO validartriagematendimentocliente = new ValidarSgaBO();
    //link do youtube
    private String link = "https://www.youtube.com/embed/{0}";
    //variavel auxiliar para o link do youtube
    private String idlink;

    @Inject
    @Push(channel = "atendimentovideopushmb")
    private PushContext atendimentovideopushmb;

    /**
     * MÉTODO RESPONSÁVEL POR INSERIR O ID DO VÍDEO
     */
    public void inserirIdVideo() {
        try {
            if (link.contains("{0}")) {
                setLink(link.replace("{0}", getIdlink()));
            } else {
                String auxiliarlink = link.substring(30, 41);
                setLink(link.replace(auxiliarlink, getIdlink()));
            }
            triagematendimentoclientevideobo.alterarVideo(getLink());
            FacesContext fc = FacesContext.getCurrentInstance();
            fc.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "LINK INSERIDO COM SUCESSO!!!", ""));
        } catch (Exception ex) {
            FacesContext fc = FacesContext.getCurrentInstance();
            fc.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "ERROR NO SERVIÇO DO LINK!!!", ""));
        }
        atendimentovideopushmb.send("atendimentovideopushmb");
    }

    public void retornarLink() throws SQLException {
        link = triagematendimentoclientevideobo.consultarLink();
        System.out.println("ENTROU AQUI!!!");
    }

    /**
     * Método responsável por validar o id do Vídeo
     */
    public boolean validarIdVideo(FacesContext context, UIComponent component, Object value) throws ValidatorException, SQLException {
        return validartriagematendimentocliente.validarIdVideo(context, component, this);
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getIdlink() {
        return idlink;
    }

    public void setIdlink(String idlink) {
        this.idlink = idlink;
    }
}
