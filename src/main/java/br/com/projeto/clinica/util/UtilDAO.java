/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.projeto.clinica.util;

import br.com.projeto.clinica.persistencia.Conexao;
import org.apache.log4j.Logger;

public class UtilDAO {

    public UtilDAO() {
    }

    protected Conexao abrirconexao = new Conexao();
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
