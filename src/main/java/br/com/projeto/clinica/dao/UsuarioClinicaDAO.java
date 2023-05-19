/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.projeto.clinica.dao;

import br.com.projeto.clinica.enumciado.IdentificacaoPessoa;
import br.com.projeto.clinica.model.Cargo;
import br.com.projeto.clinica.model.Clinica;
import br.com.projeto.clinica.model.Colaborador;
import br.com.projeto.clinica.model.Endereco;
import br.com.projeto.clinica.model.Pessoa;
import br.com.projeto.clinica.model.Profissional;
import br.com.projeto.clinica.persistencia.Conexao;
import br.com.projeto.clinica.util.DataUtil;
import br.com.projeto.clinica.util.UtilDAO;
import static br.com.projeto.clinica.util.UtilDAO.logPrincipal;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UsuarioClinicaDAO extends UtilDAO {

    public UsuarioClinicaDAO() {
    }

    /**
     * Metodo responsavel por inserir clinica, cargo e as suas permissoes de
     * acesso a cada um dos links, e inserir o dono da clinica
     *
     * @return boolean
     */
    public boolean INSERIRUSUAROCLINICAPROFISSIONAL(Pessoa pessoa) {

        //---Inicia insercao da Pessoa
        StringBuffer strInsercaoPessoa = new StringBuffer().append("INSERT INTO pessoa(\n"
                + "	celularpessoa, datacadastropessoa, emailpessoa, fonepessoa, imagempessoa, nomepessoa, senhapessoa, tipopessoa, idcargo, idclinica, statuspessoa)\n"
                + "	VALUES ( ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?);"
        );

        //---Inicia insercao da Pessoa
        StringBuffer strInsercaoProfissional = new StringBuffer().append("INSERT INTO profissional(\n"
                + "                    idprofissional)\n"
                + "            VALUES( ?);\n"
        );

        try (PreparedStatement pstmInsercaoPessoa = Conexao.GETCONEXAO().prepareStatement(strInsercaoPessoa.toString());
                PreparedStatement pstmInsercaoProfissional = Conexao.GETCONEXAO().prepareStatement(strInsercaoProfissional.toString())) {
            int tamPessoa = 1;

            pstmInsercaoPessoa.setObject(tamPessoa++, pessoa.getCelularPessoa());
            pstmInsercaoPessoa.setObject(tamPessoa++, new DataUtil().GETSQLTIMESTAMP(new DataUtil().DATAHORASRECIFE()));
            pstmInsercaoPessoa.setObject(tamPessoa++, pessoa.getEmailPessoa());
            pstmInsercaoPessoa.setObject(tamPessoa++, pessoa.getFonePessoa());
            pstmInsercaoPessoa.setObject(tamPessoa++, pessoa.getImagemPessoa());
            pstmInsercaoPessoa.setObject(tamPessoa++, pessoa.getNomePessoa());
            pstmInsercaoPessoa.setObject(tamPessoa++, pessoa.getSenhaPessoa());
            pstmInsercaoPessoa.setObject(tamPessoa++, pessoa.getTipoPessoa());            
            pstmInsercaoPessoa.setObject(tamPessoa++, pessoa.getCargo().getIdCargo());
            pstmInsercaoPessoa.setObject(tamPessoa++, pessoa.getClinica().getIdClinica());
            pstmInsercaoPessoa.setObject(tamPessoa++, pessoa.getStatusPessoa());

            pstmInsercaoPessoa.executeUpdate();

            pstmInsercaoPessoa.close();

            //--pega o ultimo ID de pessoa
            ULTIMAPESSOA(pessoa);

            //pstmInsercaoProfissional.setObject(1, null);
            pstmInsercaoProfissional.setObject(1, pessoa.getIdPessoa());

            pstmInsercaoProfissional.executeUpdate();

            pstmInsercaoProfissional.close();

        } catch (SQLException ex) {
            logPrincipal(CargoDAO.class).error(">>>>ERROR AO INSERIR USUARIOCLINICADAO(INSERIRUSUAROCLINICAPROFISSIONAL)", ex);
        }
        return true || false;
    }

    /**
     * Metodo responsavel por inserir clinica, cargo e as suas permissoes de
     * acesso a cada um dos links, e inserir o dono da clinica
     *
     * @return boolean
     */
    public boolean INSERIRUSUAROCLINICACOLABORADOR(Pessoa pessoa) {

        //---Inicia insercao da Pessoa
        StringBuffer strInsercaoPessoa = new StringBuffer().append("INSERT INTO pessoa(\n"
                + "    celularpessoa, datacadastropessoa, emailpessoa, fonepessoa, imagempessoa, nomepessoa, senhapessoa, tipopessoa, idcargo, idclinica, statuspessoa)\n"
                + "	VALUES ( ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?);"
        );

        //---Inicia insercao da Pessoa
        StringBuffer strInsercaoColaborador = new StringBuffer().append("INSERT INTO colaborador(\n"
                + "	idcolaborador)\n"
                + "	VALUES (?);"
        );

        try (PreparedStatement pstmInsercaoPessoa = Conexao.GETCONEXAO().prepareStatement(strInsercaoPessoa.toString());
                PreparedStatement pstmInsercaoColaborador = Conexao.GETCONEXAO().prepareStatement(strInsercaoColaborador.toString())) {
            int tamPessoa = 1;

            pstmInsercaoPessoa.setObject(tamPessoa++, pessoa.getCelularPessoa());
            pstmInsercaoPessoa.setObject(tamPessoa++, new DataUtil().GETSQLTIMESTAMP(new DataUtil().DATAHORASRECIFE()));
            pstmInsercaoPessoa.setObject(tamPessoa++, pessoa.getEmailPessoa());
            pstmInsercaoPessoa.setObject(tamPessoa++, pessoa.getFonePessoa());
            pstmInsercaoPessoa.setObject(tamPessoa++, pessoa.getImagemPessoa());
            pstmInsercaoPessoa.setObject(tamPessoa++, pessoa.getNomePessoa());
            pstmInsercaoPessoa.setObject(tamPessoa++, pessoa.getSenhaPessoa());
            pstmInsercaoPessoa.setObject(tamPessoa++, pessoa.getTipoPessoa());            
            pstmInsercaoPessoa.setObject(tamPessoa++, pessoa.getCargo().getIdCargo());
            pstmInsercaoPessoa.setObject(tamPessoa++, pessoa.getClinica().getIdClinica());
            pstmInsercaoPessoa.setObject(tamPessoa++, pessoa.getStatusPessoa());

            pstmInsercaoPessoa.executeUpdate();

            pstmInsercaoPessoa.close();

            //--pega o ultimo ID de pessoa
            ULTIMAPESSOA(pessoa);

            pstmInsercaoColaborador.setObject(1, pessoa.getIdPessoa());

            pstmInsercaoColaborador.executeUpdate();

            pstmInsercaoColaborador.close();

        } catch (SQLException ex) {
            logPrincipal(CargoDAO.class).error(">>>>ERROR AO INSERIR USUARIOCLINICADAO(INSERIRUSUAROCLINICACOLABORADOR)", ex);
        }
        return true || false;
    }

    private int ULTIMAPESSOA(Pessoa pessoa) {
        //Obtem o ID respectivo
        StringBuffer strObterIdPessoa = new StringBuffer().append("SELECT pessoa.idpessoa\n"
                + "                            FROM pessoa pessoa"
                + "                            ORDER BY idpessoa"
                + "                            DESC LIMIT 1;");
        try (PreparedStatement pstmUltimoIdPessoa = Conexao.GETCONEXAO().prepareStatement(strObterIdPessoa.toString())) {
            ResultSet rsIdPessoa = null;

            rsIdPessoa = pstmUltimoIdPessoa.executeQuery();

            rsIdPessoa.next();
            //seta o valor da pessoa
            pessoa.setIdPessoa(rsIdPessoa.getInt("idpessoa"));

        } catch (SQLException ex) {
            logPrincipal(CargoDAO.class
            ).error(">>>>ERROR AO LISTAR USUARIOCLINICADAO(ULTIMAPESSOA)", ex);
        }
        return pessoa.getIdPessoa();
    }

    /**
     * Metodo com a caracteristica de retornar uma lista de Dados
     *
     * @param nomeClinica
     * @param nomeEmail
     */
    public boolean EXISTECLINICAPESSOA(String nomeClinica, String nomeEmail) {

        StringBuffer strClinicaPessoa = new StringBuffer().append("SELECT pessoa.emailpessoa,"
                + "  clinica.nomeclinica"
                + "  FROM clinica clinica"
                + "  INNER JOIN pessoa pessoa on (pessoa.idclinica = clinica.idclinica)\n"
                + "  WHERE (pessoa.emailpessoa = ? and clinica.nomeclinica = ?)"
                + "  ORDER BY nomeclinica");
        try (PreparedStatement pstm = Conexao.GETCONEXAO().prepareStatement(strClinicaPessoa.toString());) {
            ResultSet rs = null;

            pstm.setObject(1, nomeEmail);
            pstm.setObject(2, nomeClinica);

            rs = pstm.executeQuery();

            if (rs.next()) {
                return true;
            }
        } catch (SQLException ex) {
            logPrincipal(ClinicaDAO.class).error(">>>>ERROR AO LISTAR UsuarioClinicaDAO(EXISTECLINICAPESSOA)", ex);
        }
        return false;
    }

}
