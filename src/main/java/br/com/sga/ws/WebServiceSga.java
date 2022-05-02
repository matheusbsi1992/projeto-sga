/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.sga.ws;

import br.com.sga.bo.TriagemChamadaClienteBO;
import br.com.sga.dao.DAO;
import br.com.sga.dao.LinkServidorIPDAO;
import br.com.sga.dao.MonitorChamadaClienteDAO;
import br.com.sga.dao.TriagemChamadaClienteDAO;
import br.com.sga.mb.TriagemChamadaClienteMB;
import br.com.sga.transfer.TriagemAtendimentoClienteTransfer;

import br.com.sga.transfer.TriagemChamadaClienteTransfer;
import br.com.sga.util.Util;
import com.google.gson.Gson;
import java.net.InetAddress;
import java.sql.SQLException;
import java.util.ArrayList;

import java.util.List;
import java.util.Set;
import java.util.concurrent.Future;
import javax.ws.rs.*;
import javax.ws.rs.core.*;
import javax.enterprise.context.RequestScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import static javax.ws.rs.HttpMethod.POST;
import javax.ws.rs.core.MediaType;
import org.omnifaces.cdi.Push;
import org.omnifaces.cdi.PushContext;

/**
 * REST Web Service
 *
 * @author Jandisson
 */
@Path("/projeto")
public class WebServiceSga extends DAO {

    @Context
    private UriInfo context;

    @Inject
    @Push(channel = "triagemchamadaclienteprioriconvpushmbmb")
    private PushContext triagemchamadaclienteprioriconvpushmbmb;

    //classe que retornar dados sobre administracao das senhas
    private TriagemChamadaClienteDAO triagemchamadaclientedao;
    //classe que retorna dados do IP do Servidor
    private LinkServidorIPDAO linkservidoripdao;
    //classe que retorna dados sobre o monitor
    private MonitorChamadaClienteDAO monitorchamadaclientedao;

    /**
     * Creates a new instance of WebServiceSga
     */
    public WebServiceSga() throws SQLException {
        triagemchamadaclientedao = new TriagemChamadaClienteDAO();
        linkservidoripdao = new LinkServidorIPDAO();
        monitorchamadaclientedao = new MonitorChamadaClienteDAO();
    }

    /**
     * Metodo responsavel por inserir triagemchamadacliente prioridade
     *
     * @param ipservidor
     * @param tipo
     * @return
     */
    @GET
    @Path("/linkipservidor/inseriralterariplinkservidor/{ipservidor}/{tipo}")
    public String inserirAlterarIpServidor(@PathParam("ipservidor") String ipservidor, @PathParam("tipo") String tipo) {
        String result;
        try {
            linkservidoripdao.inserirAlterarIPLinkServidor(ipservidor, tipo);
            result = "inserirAlterarIPLinkServidor";
        } catch (Exception ex) {
            logPrincipal(WebServiceSga.class).error(">>>>ERROR AO INSERIR/ALTERAR LINKSERVIDORIP - WS(inserirAlterarIPLinkServidor)", ex);
            result = ">>>>ERROR AO INSERIR/ALTERAR LINKSERVIDORIP - WS(inserirAlterarIPLinkServidor)";
        }
        return result;
    }

    /**
     * metodo que lista ip do servidor
     *
     * @return Gson
     */
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/linkipservidor/listaripservidor")
    public String listarIpServidor() {
        try {
            Gson gson = new Gson();
            return gson.toJson(linkservidoripdao.listarIPServidorIPLinkServidor());
        } catch (Exception ex) {
            logPrincipal(WebServiceSga.class).error(">>>>ERROR AO LISTAR IP DO SERVIDOR - WS(listarIpServidor())", ex);
        }
        return "";
    }

    /**
     * Metodo responsavel por inserir triagemchamadacliente convencional
     *
     * @param nomeservico
     * @param id
     * @param senhatriagemalternativa
     * @return
     */
    @GET
    @Path("/triagemchamadacliente/inserirtriagemchamadaclienteconvencional/{nomeservico}/{id}/{senhatriagemalternativa}")
    public String inserirTriagemChamadaClienteConvencional(@PathParam("nomeservico") String nomeservico, @PathParam("id") short id, @PathParam("senhatriagemalternativa") String senhatriagemalternativa) {
        String result = null;
        try {
            TriagemChamadaClienteTransfer triagemchamadaclientetransfer = new TriagemAtendimentoClienteTransfer();
            //--nome do servico
            triagemchamadaclientetransfer.getServicotransfer().setNomeservico(nomeservico.toUpperCase());
            //--id triagemalternativa
            triagemchamadaclientetransfer.setId(id);
            //pedido de senha normal/convencional
            triagemchamadaclientetransfer.setNormalchamadatriagemchamadacliente("CONVENCIONAL");
            //pedido de senha prioridade recebe nulo 
            triagemchamadaclientetransfer.setPrioridadechamadatriagemchamadacliente(null);
            //senha emitida
            triagemchamadaclientetransfer.setEmitirsenhatriagemchamadacliente("SENHA EMITIDA");
            //senha gerada
            triagemchamadaclientetransfer.setSiglatriagemalternativa(senhatriagemalternativa.toUpperCase());
            //status ativo
            triagemchamadaclientetransfer.setStatustriagemchamadacliente("A");
            //recebe os dados que vem atraves do ws
            triagemchamadaclientedao.inserirTriagemChamadaCliente(triagemchamadaclientetransfer);
            //faz o send de comunicacao para a pagina monitochamadacliente para uma devida atuzalicacao da grid
            triagemchamadaclienteprioriconvpushmbmb.send("triagemchamadaclienteprioriconvpushmbmb");
            result = "inserirTriagemChamadaClienteConvencional";
        } catch (Exception ex) {
            logPrincipal(WebServiceSga.class).error(">>>>ERROR AO INSERIR TRIAGEMCHAMADACLIENTE CONVENCIONAL - WS(inserirTriagemChamadaClienteConvencionalWS)", ex);
            result = ">>>>ERROR AO INSERIR TRIAGEMCHAMADACLIENTE (CONVENCIONAL) - WS(inserirTriagemChamadaClienteConvencionalWS)";
        }
        return result;
    }

    /**
     * Metodo responsavel por inserir triagemchamadacliente prioridade
     *
     * @param nomeservico
     * @param id
     * @param senhatriagemalternativa
     */
    @GET
    @Path("/triagemchamadacliente/inserirtriagemchamadaclienteprioridade/{nomeservico}/{id}/{senhatriagemalternativa}")
    public String inserirTriagemChamadaClientePrioridade(@PathParam("nomeservico") String nomeservico, @PathParam("id") short id, @PathParam("senhatriagemalternativa") String senhatriagemalternativa) {
        String result;
        try {
            TriagemChamadaClienteTransfer triagemchamadaclientetransfer = new TriagemAtendimentoClienteTransfer();
            //--nome do servico
            triagemchamadaclientetransfer.getServicotransfer().setNomeservico(nomeservico.toUpperCase());
            //--id triagemalternativa
            triagemchamadaclientetransfer.setId(id);
            //pedido de senha normal/convencional
            triagemchamadaclientetransfer.setNormalchamadatriagemchamadacliente(null);
            //pedido de senha prioridade recebe nulo 
            triagemchamadaclientetransfer.setPrioridadechamadatriagemchamadacliente("PRIORIDADE");
            //senha emitida
            triagemchamadaclientetransfer.setEmitirsenhatriagemchamadacliente("SENHA EMITIDA");
            //senha gerada
            triagemchamadaclientetransfer.setSiglatriagemalternativa(senhatriagemalternativa.toUpperCase());
            //status ativo
            triagemchamadaclientetransfer.setStatustriagemchamadacliente("A");
            //recebe os dados que vem atraves do ws
            triagemchamadaclientedao.inserirTriagemChamadaCliente(triagemchamadaclientetransfer);
            //faz o send de comunicacao para a pagina monitochamadacliente para uma devida atuzalicacao da grid
            triagemchamadaclienteprioriconvpushmbmb.send("triagemchamadaclienteprioriconvpushmbmb");
            result = "inserirTriagemChamadaClientePrioridade";
        } catch (Exception ex) {
            logPrincipal(WebServiceSga.class).error(">>>>ERROR AO INSERIR TRIAGEMCHAMADACLIENTE PRIORIDADE - WS(inserirTriagemChamadaClientePrioridadeWS)", ex);
            result = ">>>>ERROR AO INSERIR TRIAGEMCHAMADACLIENTE (PRIORIDADE) - WS(inserirTriagemChamadaClientePrioridadeWS)";
        }
        return result;
    }

    /**
     * metodo que lista todos os meus dados sobre as senhas
     *
     * @return Gson
     */
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/triagemchamadacliente/listartodostriagemchamadacliente")
    public String listarTodosTriagemChamadaCliente() {
        try {
            List<TriagemChamadaClienteTransfer> listardadostraiagemchamadacliente = null;
            Gson gson = new Gson();
            listardadostraiagemchamadacliente = triagemchamadaclientedao.listarTodosTriagemChamadaCliente();
            return gson.toJson(listardadostraiagemchamadacliente);
        } catch (Exception ex) {
            logPrincipal(WebServiceSga.class).error(">>>>ERROR AO LISTAR TUDO DA MINHA TRIAGEM CHAMADA CLIENTE - WS(listarTodosTriagemChamadaCliente())", ex);
        }
        return "";
    }

    /**
     * metodo que lista uma unica senha para impressao
     *
     * @return Gson
     */
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/triagemchamadacliente/buscarsenhaativatriagemchamadacliente/{nometriagemchamadacliente}")
    public String buscarSenhaAtivaTriagemChamadaCliente(@PathParam("nometriagemchamadacliente") String nometriagemchamadacliente) {
        try {
            List<TriagemChamadaClienteTransfer> listarbuscarsenhaativatriagemchamadacliente = null;
            Gson gson = new Gson();
            listarbuscarsenhaativatriagemchamadacliente = triagemchamadaclientedao.buscarSenhaAtivaTriagemChamadaCliente(nometriagemchamadacliente);
            return gson.toJson(listarbuscarsenhaativatriagemchamadacliente);
        } catch (Exception ex) {
            logPrincipal(WebServiceSga.class).error(">>>>ERROR AO LISTAR SENHA DA MINHA TRIAGEM CHAMADA CLIENTE - WS(buscarSenhaAtivaTriagemChamadaCliente)", ex);
        }
        return "";
    }

}
