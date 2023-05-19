package br.com.projeto.clinica.dao;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import br.com.projeto.clinica.model.Clinica;
import br.com.projeto.clinica.model.Servico;
import br.com.projeto.clinica.persistencia.Conexao;
import br.com.projeto.clinica.util.JpaUtil;
import br.com.projeto.clinica.util.UtilDAO;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.persistence.EntityManager;



public class ServicoDAO extends UtilDAO {

    public ServicoDAO() {
    }

    /**
     * Metodo por inserir a Servico
     *
     * @param servico
     * @return true||false
     */
    public boolean INSERIRSERVICO(Servico servico) {

        StringBuffer strServico = new StringBuffer().append("INSERT INTO servico(\n"
                + "            datacadastroservico, nomeservico, observacaoservico, \n"
                + "            statusservico, valorservico, idclinica)\n"
                + "    VALUES (?, ?, ?, \n"
                + "            ?, ?, ?);");

        try (PreparedStatement pstmServico = Conexao.GETCONEXAO().prepareStatement(strServico.toString());) {

            int tamServico = 1;

            pstmServico.setObject(tamServico++, servico.getDataCadastroServico());
            pstmServico.setObject(tamServico++, servico.getNomeServico());
            pstmServico.setObject(tamServico++, servico.getObservacaoServico());
            pstmServico.setObject(tamServico++, servico.getStatusServico());
            pstmServico.setObject(tamServico++, servico.getValorServico());
            pstmServico.setObject(tamServico++, servico.getClinica().getIdClinica());

            pstmServico.executeUpdate();

        } catch (SQLException ex) {
            logPrincipal(ServicoDAO.class).error(">>>>ERROR AO INSERIR SERVICO(INSERIRSERVICO)", ex);
            return false;
        }
        return true || false;
    }

    /**
     * Metodo com a caracteristica de retornar uma lista de Dados
     *
     * @return List<Servico>
     * @throws java.sql.SQLException
     */
    public List<Servico> LISTARTODOSSERVICO(int idCLinica) {
        List<Servico> servicos = new ArrayList<Servico>();

        StringBuffer strServico = new StringBuffer().append("SELECT *"
                + "  FROM servico servico"
                + "  INNER JOIN clinica clinica on (clinica.idclinica = servico.idclinica)\n"
                + "  WHERE servico.idclinica= ?"
                + "  ORDER BY nomeservico");
        try (PreparedStatement pstm = Conexao.GETCONEXAO().prepareStatement(strServico.toString());) {
            ResultSet rs = null;
            pstm.setObject(1, idCLinica);
            pstm.executeQuery();
            while (rs.next()) {
                Servico servico = new Servico();

                /* 
             valorservico, idclinica
                 */
                servico.setIdServico(rs.getInt("idservico"));
                servico.setDataCadastroServico(rs.getDate("datacadastroservico"));
                servico.setNomeServico(rs.getString("nomeservico"));
                servico.setObservacaoServico(rs.getString("observacaoservico"));
                servico.setStatusServico(rs.getString("statusservico"));
                servico.setValorServico(rs.getBigDecimal("valorservico"));

                Clinica clinica = new Clinica();
                clinica.setIdClinica(rs.getInt("idclinica"));

                servico.setClinica(clinica);

                servicos.add(servico);
            }
        } catch (SQLException ex) {
            logPrincipal(ServicoDAO.class).error(">>>>ERROR AO LISTAR SERVICODAO(LISTARTODOSSERVICO)", ex);
        }
        return servicos;
    }

    /**
     * Metodo com a caracteristica de retornar uma lista de Dados contendo o
     * nome da Servico
     *
     * @param idClinica
     * @param nomeServico
     * @return List<LocalTransfer>
     */
    public List<Servico> BUSCARLISTANOMESERVICO(int idClinica, String nomeServico) {

        return LISTARTODOSSERVICO(idClinica).stream().filter(servico
                -> servico.getNomeServico().contains(nomeServico)).collect(Collectors.toList());

    }

    /**
     * Metodo responsavel por buscar por informar o nome da Servico e retornar
     * os dados previstos do objeto da classe.
     *
     *
     * @param idClinica
     * @param nomeServico
     * @return
     */
    public Optional<Servico> BUSCARNOMESERVICOCONFERE(int idClinica, String nomeServico) {
        Optional<Servico> cliOptional = LISTARTODOSSERVICO(idClinica).stream().filter(c
                -> c.getNomeServico().equalsIgnoreCase(nomeServico)).findAny();

        return cliOptional;
    }

    /**
     * Metodo responsavel por buscar por id e nome da Servico
     *
     * @param idClinica
     * @param idServico
     * @param nomeServico
     * @return boolean
     *
     */
    public boolean IDNOMESERVICOCONFERE(int idClinica, int idServico, String nomeServico) {

        return LISTARTODOSSERVICO(idClinica).stream().anyMatch(servico
                -> servico.getIdServico() == (idServico) && servico.getNomeServico().equalsIgnoreCase(nomeServico));
    }

    /**
     * Metodo responsavel por buscar por id da Servico na tentativa de retornar
     * os dados previstos informado.
     *
     *
     * @param idClinica
     * @param idServico
     * @return
     */
    public Optional<Servico> IDSERVICOCONFERE(int idClinica, int idServico) {

        Optional<Servico> opTionalServico = LISTARTODOSSERVICO(idClinica).stream().filter(c
                -> c.getIdServico() == idServico).findAny();
        return opTionalServico;

    }

    /**
     * Metodo responsavel por verificar se existi nome da Servico
     *
     * @param idClinica
     * @param nomeServico
     * @return boolean
     */
    public boolean EXISTINOMESERVICO(int idClinica, String nomeServico) {

        return LISTARTODOSSERVICO(idClinica).stream().anyMatch(servico
                -> servico.getNomeServico().equalsIgnoreCase(nomeServico));
    }

    /**
     * Metodo responsavel por alterar a Servico
     *
     * @param servico
     * @return boolean
     *
     */
    public boolean ALTERARSERVICO(Servico servico) {
        EntityManager em = JpaUtil.getEntityManager();
        try {
            em.getTransaction().begin();
            em.merge(servico);
            em.getTransaction().commit();
        } catch (Exception ex) {
            logPrincipal(ServicoDAO.class).error(">>>>ERROR AO ALTERAR SERVICO(ALTERARSERVICO)", ex);
            em.getTransaction().rollback();
        } finally {
            em.close();
        }
        return true || false;
    }

    /**
     * Metodo responsavel por deletar uma determinada Servico
     *
     * @param idServico
     */
    public boolean DELETARSERVICO(int idServico) {
        EntityManager em = JpaUtil.getEntityManager();
        Servico c = em.find(Servico.class, idServico);
        //Servico c = IDSERVICOCONFERE(idServico).get();
        try {
            em.getTransaction().begin();
            em.remove(c);
            em.getTransaction().commit();
        } catch (Exception ex) {
            logPrincipal(ServicoDAO.class).error(">>>>ERROR AO REMOVER SERVICO(DELETARSERVICO)", ex);
            em.getTransaction().rollback();
        } finally {
            em.close();
        }
        return true || false;
    }
}
