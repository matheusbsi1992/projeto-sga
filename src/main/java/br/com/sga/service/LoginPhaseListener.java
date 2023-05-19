/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.sga.service;

import javax.faces.application.NavigationHandler;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.event.PhaseEvent;
import javax.faces.event.PhaseId;
import javax.faces.event.PhaseListener;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import br.com.sga.transfer.UsuarioTransfer;
import javax.servlet.http.HttpServletRequest;

/**
 *
 * @author Jandisson
 */
public class LoginPhaseListener implements PhaseListener {

    @Override
    public void afterPhase(PhaseEvent event) {
        FacesContext facesContext = event.getFacesContext();
        String viewId = facesContext.getViewRoot().getViewId();
        ExternalContext ec = facesContext.getExternalContext();
        HttpServletResponse response = (HttpServletResponse) ec.getResponse();
        NavigationHandler nh = facesContext.getApplication().getNavigationHandler();
        boolean paginaLogin = viewId.lastIndexOf("index") > -1;
        boolean paginaRegistro = viewId.lastIndexOf("registrar") > -1;
        boolean paginaCadastro = viewId.lastIndexOf("cadastro") > -1;

        if (existeUsuarioLogado() && paginaLogin) {
            nh.handleNavigation(facesContext, null, "/home?faces-redirect=true");
        } else if (!existeUsuarioLogado() && !paginaLogin && !paginaRegistro && !paginaCadastro) {
            nh.handleNavigation(facesContext, null, "/index?faces-redirect=true");
        } else if (!existeUsuarioLogado() && !paginaLogin && !paginaRegistro && !paginaCadastro) {
            nh.handleNavigation(facesContext, null, "/registrar?faces-redirect=true");
        } else if (!existeUsuarioLogado() && !paginaLogin && !paginaRegistro && !paginaCadastro) {
            nh.handleNavigation(facesContext, null, "/cadastro?faces-redirect=true");
        } 
        response.setHeader("Expires", "-1");
        response.setHeader("Cache-Control", "no-store, no-cache, must-revalidade, proxy-revalidade, private, post-check=0, pre-check=0");
        response.setHeader("Pragma", "no-cache");
    }

    public boolean existeUsuarioLogado() {
        return ((UsuarioTransfer) getAtributoSessao("usuario") != null);
    }

    public Object getAtributoSessao(String attributeName) {

        HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
        if (session != null) {
            return session.getAttribute(attributeName);
        }
        return null;
    }

    @Override
    public void beforePhase(PhaseEvent event) {

    }

    @Override
    public PhaseId getPhaseId() {
        return PhaseId.RESTORE_VIEW;
    }
}
