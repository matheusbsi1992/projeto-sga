package br.com.sga.dao;

import br.com.sga.conexaofactory.Conexao;


import java.sql.PreparedStatement;
import java.sql.ResultSet;

import org.apache.log4j.Logger;
//import org.apache.logging.log4j.Logger;
//import org.apache.logging.log4j.LogManager;

/**
 *
 * Classe de responsavel por conexao e log
 *
 * @author linuxserver
 *
 */
public class DAO {

    public DAO() {

    }

    protected Conexao abrirconexao = new Conexao();
    protected ResultSet rs;
    protected PreparedStatement pstm;
    protected StringBuffer strBuffer;

    public static Logger logPrincipal(Class classe) {
        if (classe != null) {
            Logger LOG = Logger.getLogger(classe);
                    
            return LOG;
        }
        return null;
    }

    public void fecharStringBuffer() {
        strBuffer = null;
    }

}
