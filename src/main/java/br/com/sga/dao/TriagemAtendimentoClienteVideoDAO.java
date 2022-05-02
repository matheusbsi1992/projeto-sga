/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.sga.dao;

import java.sql.SQLException;

/**
 *
 * @author Jandisson
 */
public class TriagemAtendimentoClienteVideoDAO extends DAO {

    public TriagemAtendimentoClienteVideoDAO() {
    }

    /**
     * Metodo responsavel por alterar o id do video servico
     *
     * @param nomepainelvideolink
     * @return boolean
     * @throws java.sql.SQLException
     *
     */
    public boolean alterarVideo(String nomepainelvideolink) throws SQLException {
        try {
            //atualizar o dado do link
            strBuffer = new StringBuffer().append("UPDATE projetosga.triagempainelvideolink\n"
                    + "   SET nomepainelvideolink=?\n"
                    + " WHERE idpainelvideolink=1;");
            //pstm = abrirconexao.getConexao().prepareStatement(tbPessoa, PreparedStatement.RETURN_GENERATED_KEYS);
            pstm = abrirconexao.getConexao().prepareStatement(strBuffer.toString());
            pstm.setObject(1, nomepainelvideolink);
            pstm.executeUpdate();

        } catch (SQLException ex) {
            logPrincipal(TriagemAtendimentoClienteVideoDAO.class).error(">>>>ERROR AO ATUALIZAR POR TRIAGEMATENDIMENTOCLIENTEVIDEODAO(nomepainelvideolink)", ex);
        } finally {
            pstm.close();
            abrirconexao.fecharConexao();
        }
        return true||false;
    }

    public String consultarLink() throws SQLException {
        try {            
            strBuffer = new StringBuffer().append("SELECT nomepainelvideolink\n"
                    + "  FROM projetosga.triagempainelvideolink\n"
                    + "  ");
            pstm = abrirconexao.getConexao().prepareStatement(strBuffer.toString());
            rs = pstm.executeQuery();

            if (rs.next()) {
                return rs.getString("nomepainelvideolink");
            }

        } catch (SQLException ex) {
            logPrincipal(TriagemAtendimentoClienteVideoDAO.class).error(">>>>ERROR AO CONSULTAR POR TRIAGEMATENDIMENTOCLIENTEVIDEODAO(consultarLink)", ex);
        } finally {
            pstm.close();
            abrirconexao.fecharConexao();
        }
        return "";
    }
}
