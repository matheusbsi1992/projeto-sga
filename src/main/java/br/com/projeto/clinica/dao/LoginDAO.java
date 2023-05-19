/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.com.projeto.clinica.dao;

import br.com.projeto.clinica.model.Cargo;
import br.com.projeto.clinica.model.Clinica;
import br.com.projeto.clinica.model.Permissao;
import br.com.projeto.clinica.model.Pessoa;

import br.com.projeto.clinica.persistencia.Conexao;
import br.com.projeto.clinica.util.JpaUtil;

import br.com.sga.dao.DAO;
import static br.com.sga.dao.DAO.logPrincipal;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;

/**
 *
 * @author matheus
 */
public class LoginDAO extends DAO {

    public LoginDAO() {
    }

    /**
     * Metodo que retornar um boolean de confirmacao se o Usário deste a Clínica
     * realmente existe, para a efetuação do Login ao sistema.
     *
     * @param emailPessoa
     * @param senhaPessoa
     * @param codigoClinica
     * @return
     */
    public boolean LOGIN(String emailPessoa, String senhaPessoa, String codigoClinica) {

        StringBuffer strLogin = new StringBuffer().append("SELECT   pessoa.emailpessoa\n"
                + "                                                ,pessoa.senhapessoa\n"
                + "                                                ,clinica.codigoclinica\n"
                + "                                          FROM pessoa pessoa\n"
                + "                                          INNER JOIN cargo cargo     on (pessoa.idcargo = cargo.idcargo)\n"
                + "                                          INNER JOIN clinica clinica on (clinica.idclinica = pessoa.idclinica)\n"
                + "                                          WHERE (pessoa.emailpessoa     = ?\n"
                + "                                                 and \n"
                + "                                                 pessoa.senhapessoa     = ?\n"
                + "                                                 and \n"
                + "                                                 clinica.codigoclinica  = ?)");
        try (PreparedStatement pstmLogin = Conexao.GETCONEXAO().prepareStatement(strLogin.toString())) {
            pstmLogin.setObject(1, emailPessoa);
            pstmLogin.setObject(2, senhaPessoa);
            pstmLogin.setObject(3, codigoClinica);

            ResultSet rsLogin = pstmLogin.executeQuery();

            if (rsLogin.next()) {
                return true;
            }

        } catch (SQLException ex) {
            logPrincipal(LoginDAO.class).error(">>>>ERROR AO LISTAR LOGIN(login)", ex);
        }
        return false;
    }

    /**
     * Metodo que retornar os dados da Pessoa relacionando o cargo, a pessoa e
     * junto a elas, a devida clínica
     *
     * @param email
     * @param codigoClinica
     * @return List<Pessoa>
     */
    public Pessoa PESQUISARLOGIN(String email, String codigoClinica, String senhaPessoa) {
        List<Pessoa> listaPesquisadeLogin = new ArrayList<Pessoa>();

        StringBuffer strPesquisarLogin = new StringBuffer().append("SELECT   pessoa.idpessoa\n"
                + "                                                ,pessoa.nomepessoa\n"
                + "                                                ,pessoa.emailpessoa\n"
                + "                                                ,pessoa.statuspessoa\n"
                + "                                                ,clinica.idclinica\n"
                + "                                                ,clinica.codigoclinica\n"
                + "                                                 FROM pessoa pessoa\n"
                + "                                                 INNER JOIN cargo cargo     on (pessoa.idcargo = cargo.idcargo)\n"
                + "                                                 INNER JOIN clinica clinica on (clinica.idclinica = pessoa.idclinica)\n"
                + "                                                 WHERE (pessoa.emailpessoa     = ?\n"
                + "                                                        and "
                + "                                                        clinica.codigoclinica  = ?"
                + "                                                        and"
                + "                                                        pessoa.senhapessoa  = ?)");
        try (PreparedStatement pstmLogin = Conexao.GETCONEXAO().prepareStatement(strPesquisarLogin.toString())) {
            pstmLogin.setObject(1, email);
            pstmLogin.setObject(2, codigoClinica);
            pstmLogin.setObject(3, senhaPessoa);

            ResultSet rsPesquisarLogin = pstmLogin.executeQuery();

            while (rsPesquisarLogin.next()) {

                Pessoa pessoa = new Pessoa();

                pessoa.setIdPessoa(rsPesquisarLogin.getInt("idpessoa"));
                pessoa.setNomePessoa(rsPesquisarLogin.getString("nomepessoa"));
                pessoa.setEmailPessoa(rsPesquisarLogin.getString("emailpessoa"));
                pessoa.setStatusPessoa(rsPesquisarLogin.getString("statuspessoa"));

                Clinica clinica = new Clinica();

                clinica.setIdClinica(rsPesquisarLogin.getInt("idclinica"));
                clinica.setCodigoClinica(rsPesquisarLogin.getString("codigoclinica"));

                pessoa.setClinica(clinica);

                listaPesquisadeLogin.add(pessoa);
            }

        } catch (SQLException ex) {
            logPrincipal(LoginDAO.class).error(">>>>ERROR AO PESQUISAR LOGIN(PESQUISARLOGIN)", ex);
        }
        return !listaPesquisadeLogin.isEmpty() ? listaPesquisadeLogin.get(0) : null;
    }

    /**
     * Metodo que retornar os dados da pessoa em uma Lista de Dados,
     * relacionando o cargo, a pessoa respectiva ao cargo e as permissões, para
     * o acesso ao trabalho da função de páginas.
     *
     * @param emailPessoa
     * @param codigoClinica
     * @return List<Pessoa>
     */
    public List<Pessoa> EXISTELOGINDEPAGINASDEPERMISSAO(String emailPessoa, String codigoClinica, String senhaPessoa) {
        List<Pessoa> listaPermissaodePaginasdeLogin = new ArrayList<Pessoa>();

        StringBuffer strLogindePermissaodePagina = new StringBuffer().append("SELECT DISTINCT(permissao.nomepermissao)\n"
                + "                                                        FROM pessoa pessoa\n"
                + "                                                        INNER JOIN cargo cargo                       on (pessoa.idcargo = cargo.idcargo)\n"
                + "                                                        INNER JOIN clinica clinica                   on (clinica.idclinica = pessoa.idclinica)\n"
                + "                                                        INNER JOIN cargo_permissao cargospermissoes  on (cargospermissoes.idcargo = cargo.idcargo)\n"
                + "                                                        INNER JOIN permissao permissao               on (cargospermissoes.idpermissao = permissao.idpermissao)\n"
                + "                                                        WHERE (pessoa.emailpessoa     = ?\n"
                + "                                                        and  "
                + "                                                        clinica.codigoclinica         = ?"
                + "                                                        and"
                + "                                                        pessoa.senhapessoa            = ?)");
        try (PreparedStatement pstmLoginPermissaodePagina = Conexao.GETCONEXAO().prepareStatement(strLogindePermissaodePagina.toString())) {

            pstmLoginPermissaodePagina.setObject(1, emailPessoa);
            pstmLoginPermissaodePagina.setObject(2, codigoClinica);
            pstmLoginPermissaodePagina.setObject(3, senhaPessoa);

            ResultSet rsLoginPermissaodePagina = pstmLoginPermissaodePagina.executeQuery();

            while (rsLoginPermissaodePagina.next()) {

                //Pessoa
                Pessoa pessoa = new Pessoa();

                //Cargo
                Cargo cargo = new Cargo();

                //Permissao
                Permissao permissao = new Permissao();
                permissao.setNomePermissao(rsLoginPermissaodePagina.getString("nomepermissao"));
                //---Permissao

                cargo.setPermissao(permissao);
                //---Cargo e Permissao

                pessoa.setCargo(cargo);
                //---Pessoa e Cargo

                listaPermissaodePaginasdeLogin.add(pessoa);
            }

        } catch (SQLException ex) {
            logPrincipal(LoginDAO.class).error(">>>>ERROR AO BUSCAR EXISTE LOGIN DE PERMISSAO (EXISTELOGINDEPAGINASDEPERMISSAO)", ex);
        }
        return listaPermissaodePaginasdeLogin;
    }

    /**
     * Metodo responsavel por buscar um usuario(idpessoa) no sistema perguntando
     * se ele existe ou nao
     *
     * @return boolean
     * @param String emailPessoa
     * @param String codigoClinica
     * @param String senhaClinica
     */
    public boolean EXISTEUSUARIOCLINICA(String emailPessoa, String codigoClinica, String senhaPessoa) {

        StringBuffer strLogindePermissaodePagina = new StringBuffer().append("SELECT idpessoa\n"
                + "                                                        FROM pessoa pessoa\n"
                + "                                                        INNER JOIN clinica clinica on (clinica.idclinica = pessoa.idclinica)\n"
                + "                                                        WHERE (pessoa.emailpessoa     = ?\n"
                + "                                                        and  "
                + "                                                        clinica.codigoclinica         = ?"
                + "                                                        and"
                + "                                                        pessoa.senhapessoa            = ?)");
        try (PreparedStatement pstmLoginPermissaodePagina = Conexao.GETCONEXAO().prepareStatement(strLogindePermissaodePagina.toString())) {

            pstmLoginPermissaodePagina.setObject(1, emailPessoa);
            pstmLoginPermissaodePagina.setObject(2, codigoClinica);
            pstmLoginPermissaodePagina.setObject(3, senhaPessoa);

            ResultSet rsLoginPermissaodePagina = pstmLoginPermissaodePagina.executeQuery();

            if (rsLoginPermissaodePagina.next()) {
                return true;
            }

        } catch (SQLException ex) {
            logPrincipal(LoginDAO.class).error(">>>>ERROR AO TENTAR BUSCAR O USUARIO (EXISTEUSUARIOCLINICA)!!!", ex);
        }
        return false;
    }

    /* Metodo responsavel por alterar a senha do Usuario
     *
     * @param pessoa
     * @return verdadeiro para a alteracao da senha senao retorne falso para a
     * nao alteracao da senha do usuario ao qual esta logado
     */
    public boolean ALTERARSENHADOUSUARIOLOGADO(Pessoa pessoa) {
        EntityManager em = JpaUtil.getEntityManager();
        try {
            em.getTransaction().begin();
            em.merge(pessoa);
            em.getTransaction().commit();
        } catch (Exception ex) {
            logPrincipal(LoginDAO.class).error(">>>>ERROR AO TENTAR ALTERAR A SENHA DO USUARIO(alterarSenhadoUsuario)!!!", ex);
            em.getTransaction().rollback();
        } finally {
            em.close();
        }
        return true || false;
    }

}
