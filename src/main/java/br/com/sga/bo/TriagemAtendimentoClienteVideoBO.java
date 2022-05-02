/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.sga.bo;

import br.com.sga.dao.TriagemAtendimentoClienteVideoDAO;
import java.sql.SQLException;

/**
 *
 * @author D-LAG-AHS-TI-02
 */
public class TriagemAtendimentoClienteVideoBO extends TriagemAtendimentoClienteVideoDAO {

    @Override
    public boolean alterarVideo(String nomepainelvideolink) throws SQLException {
        return super.alterarVideo(nomepainelvideolink); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public String consultarLink() throws SQLException {
        return super.consultarLink(); //To change body of generated methods, choose Tools | Templates.
    }

}
