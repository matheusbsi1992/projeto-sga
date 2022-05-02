/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.sga.dao;

import br.com.sga.transfer.LocalTransfer;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author Jandisson
 */
public class LinkServidorIPDAO extends DAO {

    public LinkServidorIPDAO() {
    }

    /**
     * Metodo responsavel por inserir Ip do Servidor senao alterar
     *
     *
     * @param ipservidor
     * @param ipservidorantigo
     * @param tipo
     * @return boolean
     * @throws java.sql.SQLException
     */
    public boolean inserirAlterarIPLinkServidor(String ipservidor, String tipo) throws SQLException {
        PreparedStatement pstminseririp = null;
        PreparedStatement pstmalterarip = null;
        try {
            if (tipo.equalsIgnoreCase("inserir")) {
                StringBuffer strBufferInserir = new StringBuffer().append("INSERT INTO projetosga.triagemlinkipservidor("
                        + "            iptriagemlink)"
                        + "    VALUES (?);"
                );
                pstminseririp = abrirconexao.getConexao().prepareStatement(strBufferInserir.toString());
                pstminseririp.setObject(1, ipservidor);
                pstminseririp.executeUpdate();
                pstminseririp.close();
            } else {
                //obter o ultimo ip do servidor na qual foi informado anteriormente
                StringBuffer strBufferultimoidcargo = new StringBuffer().append("SELECT idtriagemlink\n"
                        + "  FROM projetosga.triagemlinkipservidor\n"
                        + "  ORDER BY idtriagemlink\n"
                        + "  DESC LIMIT 1;");
                PreparedStatement pstmultimoidipservidor = abrirconexao.getConexao().prepareStatement(strBufferultimoidcargo.toString());
                ResultSet rsidiplinkservidor = pstmultimoidipservidor.executeQuery();
                rsidiplinkservidor.next();
                //seta o ultimo valor do ip do servidor
                short iplinkservidor = rsidiplinkservidor.getShort("idtriagemlink");
                //fecha PreparedStatement
                pstmultimoidipservidor.close();
                //fecha resultSet
                rsidiplinkservidor.close();
                
                //iniciar a atualizacao do ultimo ip do servidor
                StringBuffer strBufferAlterar = new StringBuffer().append("UPDATE projetosga.triagemlinkipservidor\n"
                        + "   SET iptriagemlink=?"
                        + " WHERE idtriagemlink=?;");
                pstmalterarip = abrirconexao.getConexao().prepareStatement(strBufferAlterar.toString());
                pstmalterarip.setObject(1, ipservidor);
                pstmalterarip.setObject(2, iplinkservidor);
                pstmalterarip.executeUpdate();
                pstmalterarip.close();
            }
        } catch (SQLException ex) {
            logPrincipal(LinkServidorIPDAO.class).error(">>>>ERROR AO INSERIR/ALTERAR IPSERVIDOR(inserirIPLinkServidor)", ex);
        } finally {
            abrirconexao.fecharConexao();
        }
        return true || false;
    }

    /**
     * Metodo com a caracteristica de retornar sempre o IP do servidor
     *
     * @return String
     * @throws java.sql.SQLException
     */
    public String listarIPServidorIPLinkServidor() throws SQLException {
        ResultSet resultipservidor = null;
        PreparedStatement pstmquantidade = null;
        StringBuffer strBufferTriagem = null;
        try {
            strBufferTriagem = new StringBuffer().append("SELECT iptriagemlink\n"
                    + "  FROM projetosga.triagemlinkipservidor;");
            pstmquantidade = abrirconexao.getConexao().prepareStatement(strBufferTriagem.toString());
            resultipservidor = pstmquantidade.executeQuery();
            if (resultipservidor.next()) {
                return resultipservidor.getString("iptriagemlink");
            }
        } catch (SQLException ex) {
            logPrincipal(TriagemChamadaClienteDAO.class).error(">>>>ERROR AO LISTAR LINKSERVIDORIPDAO (listarIPServidorIPLinkServidor)", ex);
        } finally {
            pstmquantidade.close();
            resultipservidor.close();
            abrirconexao.fecharConexao();
        }
        return "";
    }

}
