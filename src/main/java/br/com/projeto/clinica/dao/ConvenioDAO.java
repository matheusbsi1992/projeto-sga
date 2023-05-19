/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.com.projeto.clinica.dao;

import br.com.projeto.clinica.model.Clinica;
import br.com.projeto.clinica.model.Convenio;
import br.com.projeto.clinica.model.Endereco;
import br.com.projeto.clinica.persistencia.Conexao;
import br.com.projeto.clinica.util.DataUtil;
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

public class ConvenioDAO extends UtilDAO {

    public ConvenioDAO() {
    }

    /**
     * Metodo responsavel por inserir convenio
     *
     * @param convenio
     * @return boolean
     * @throws java.sql.SQLException
     */
    public boolean INSERIRCONVENIO(Convenio convenio) {

        StringBuffer strConvenio = new StringBuffer().append("INSERT INTO convenio(\n"
                + "	celularconvenio, cnpjconvenio, cpfconvenio, datacadastroconvenio, emailconvenio, nomeconvenio, numeroconvenio, periododecarenciadoconvenio, registroansconvenio, statusconvenio, telefoneconvenio, tipoconvenio, idclinica, idendereco)\n"
                + "	VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?);");

        try (PreparedStatement pstmConvenio = Conexao.GETCONEXAO().prepareStatement(strConvenio.toString());) {

            int tamConvenio = 1;

            pstmConvenio.setObject(tamConvenio++, convenio.getCelularConvenio());
            pstmConvenio.setObject(tamConvenio++, convenio.getCnpjConvenio());
            pstmConvenio.setObject(tamConvenio++, convenio.getCpfConvenio());
            pstmConvenio.setObject(tamConvenio++, new DataUtil().GETSQLTIMESTAMP(new DataUtil().DATAHORASRECIFE()));
            pstmConvenio.setObject(tamConvenio++, convenio.getEmailConvenio());
            pstmConvenio.setObject(tamConvenio++, convenio.getNomeConvenio());
            pstmConvenio.setObject(tamConvenio++, convenio.getNumeroConvenio());
            pstmConvenio.setObject(tamConvenio++, convenio.getPeriodoDeCarenciaDoConvenio());
            pstmConvenio.setObject(tamConvenio++, convenio.getRegistroAnsConvenio());
            pstmConvenio.setObject(tamConvenio++, convenio.getStatusConvenio());
            pstmConvenio.setObject(tamConvenio++, convenio.getTelefoneConvenio());
            pstmConvenio.setObject(tamConvenio++, convenio.getTipoConvenio());
            pstmConvenio.setObject(tamConvenio++, convenio.getClinica().getIdClinica());
            pstmConvenio.setObject(tamConvenio++, convenio.getEndereco().getIdEndereco());
            pstmConvenio.executeUpdate();

        } catch (SQLException ex) {
            logPrincipal(ConvenioDAO.class).error(">>>>ERROR AO INSERIR CONVENIO(INSERIRCONVENIO)", ex);
            return false;
        }
        return true || false;

    }

    /**
     * Metodo com a caracteristica de retornar uma lista de Dados
     *
     * @param idCLinica
     * @return List<Clinica>
     */
    public List<Convenio> LISTARTODOSCONVENIO(int idCLinica) {
        List<Convenio> convenios = new ArrayList<Convenio>();

        StringBuffer strConvenio = new StringBuffer().append("SELECT *"
                + "  FROM clinica clinica"
                + "  INNER JOIN convenio convenio on (convenio.idclinica = clinica.idclinica)\n"
                + "  WHERE convenio.idclinica = ?"
                + "  ORDER BY nomeconvenio");
        try (PreparedStatement pstm = Conexao.GETCONEXAO().prepareStatement(strConvenio.toString());) {
            ResultSet rs = null;
            pstm.setObject(1, idCLinica);
            rs = pstm.executeQuery();
            while (rs.next()) {
                Clinica clinica = new Clinica();

                clinica.setIdClinica(rs.getInt("idclinica"));
                clinica.setCelularClinica(rs.getString("celularclinica"));
                clinica.setCnpjClinica(rs.getString("cnpjclinica"));
                clinica.setCodigoClinica(rs.getString("codigoclinica"));
                clinica.setComercialClinica(rs.getString("comercialclinica"));
                clinica.setCpfClinica(rs.getString("cpfclinica"));
                clinica.setDataCadastroClinica(rs.getTimestamp("datacadastroclinica"));
                clinica.setEmailClinica(rs.getString("emailclinica"));
                clinica.setImagemClinica(rs.getBytes("imagemclinica"));
                clinica.setNomeClinica(rs.getString("nomeclinica"));
                clinica.setNomeDoResponsavelClinica(rs.getString("nomedoresponsavelclinica"));

                Endereco endereco = new Endereco();
                endereco.setIdEndereco(rs.getInt("idendereco"));
                endereco.setBairroEndereco(rs.getString("bairroendereco"));
                endereco.setNomeEndereco(rs.getString("nomeendereco"));
                endereco.setCepEndereco(rs.getString("cependereco"));
                endereco.setCidadeEndereco(rs.getString("cidadeendereco"));
                endereco.setComplementoEndereco(rs.getString("complementoendereco"));
                endereco.setEstadoEndereco(rs.getString("estadoendereco"));
                endereco.setNumeroEndereco(rs.getString("numeroendereco"));
                endereco.setRuaEndereco(rs.getString("ruaendereco"));

                Convenio convenio = new Convenio();
                convenio.setIdConvenio(rs.getInt("idconvenio"));
                convenio.setDataCadastroConvenio(rs.getDate("dataCadastroConvenio"));
                convenio.setNomeConvenio(rs.getString("nomeconvenio"));
                convenio.setCelularConvenio(rs.getString("celularconvenio"));
                convenio.setCnpjConvenio(rs.getString("cnpjconvenio"));
                convenio.setCpfConvenio(rs.getString("cpfconvenio"));
                convenio.setEmailConvenio(rs.getString("emailconvenio"));
                convenio.setNumeroConvenio(rs.getString("numeroconvenio"));
                convenio.setPeriodoDeCarenciaDoConvenio(rs.getInt("periododecarenciadoconvenio"));
                convenio.setRegistroAnsConvenio(rs.getInt("registroansconvenio"));
                convenio.setStatusConvenio(rs.getString("statusconvenio"));
                convenio.setTelefoneConvenio(rs.getString("telefoneconvenio"));
                convenio.setTipoConvenio(rs.getString("tipoconvenio"));

                convenio.setClinica(clinica);
                convenio.setEndereco(endereco);

                convenios.add(convenio);
            }
        } catch (SQLException | NullPointerException ex) {
            logPrincipal(ConvenioDAO.class).error(">>>>ERROR AO LISTAR CONVENIODAO(LISTARTODOSCONVENIO)", ex);
        }
        return convenios;
    }

    /**
     * Metodo com a caracteristica de retornar uma lista de Dados contendo o
     * nome do Convenio
     *
     * @param idClinica
     * @param nomeConvenio
     * @return List<ConvenioTransfer>
     */
    public List<Convenio> BUSCARLISTANOMECONVENIO(int idClinica, String nomeConvenio) {

        return LISTARTODOSCONVENIO(idClinica).stream().filter(convenio
                -> convenio.getNomeConvenio().contains(nomeConvenio)).collect(Collectors.toList());

    }

    /**
     * Metodo responsavel por buscar por nome do Convenio na tentativa de
     * retornar os dados previstos informado.
     *
     * @param idClinica
     * @param nomeConvenio
     * @return
     */
    public Optional<Convenio> BUSCARNOMECONVENIOCONFERE(int idClinica, String nomeConvenio) {

        return LISTARTODOSCONVENIO(idClinica).stream().filter(c
                -> c.getNomeConvenio().equalsIgnoreCase(nomeConvenio)).findFirst();

    }

    /**
     * Metodo responsavel por buscar por id e nome do Convenio
     *
     * @param idClinica
     * @param idConvenio
     * @param nomeConvenio
     * @return boolean
     *
     */
    public boolean IDNOMECONVENIOCONFERE(int idClinica, int idConvenio, String nomeConvenio) {

        return LISTARTODOSCONVENIO(idClinica).stream().anyMatch(convenio
                -> convenio.getIdConvenio() == (idConvenio) && convenio.getNomeConvenio().equalsIgnoreCase(nomeConvenio));
    }

    /**
     * Metodo responsavel por buscar por id do Convenio na tentativa de retornar
     * os dados previstos informado.
     *
     *
     * @param idClinica
     * @param idConvenio
     * @return
     */
    public Optional<Convenio> IDCONVENIOCONFERE(int idClinica, int idConvenio) {

        return LISTARTODOSCONVENIO(idClinica
        ).stream().filter(c
                -> c.getIdConvenio() == idConvenio).findFirst();
    }

    /**
     * Metodo responsavel por verificar se existi nome do Convenio
     *
     * @param idClinica
     * @param nomeConvenio
     * @return boolean
     */
    public boolean EXISTINOMECONVENIO(int idClinica, String nomeConvenio) {

        return LISTARTODOSCONVENIO(idClinica).stream().anyMatch(convenio
                -> convenio.getNomeConvenio().equalsIgnoreCase(nomeConvenio));
    }

    /**
     * Metodo responsavel por alterar o Convenio
     *
     * @param convenio
     * @return boolean
     *
     */
    public boolean ALTERARCONVENIO(Convenio convenio) {
        EntityManager em = JpaUtil.getEntityManager();
        try {
            em.getTransaction().begin();
            em.merge(convenio);
            em.getTransaction().commit();
        } catch (Exception ex) {
            logPrincipal(ConvenioDAO.class).error(">>>>ERROR AO ALTERAR CONVENIODAO(ALTERARCONVENIO)", ex);
            em.getTransaction().rollback();
        } finally {
            em.close();
        }
        return true || false;
    }

    /**
     * Metodo responsavel por deletar um determinado Convenio
     *
     * @param idConvenio
     * @return boolean
     */
    public boolean DELETARCONVENIO(int idConvenio) {
        EntityManager em = JpaUtil.getEntityManager();
        Convenio c = em.find(Convenio.class, idConvenio);
        //Clinica c = IDCLINICACONFERE(idClinica).get();
        try {
            em.getTransaction().begin();
            em.remove(c);
            em.getTransaction().commit();
        } catch (Exception ex) {
            logPrincipal(ConvenioDAO.class).error(">>>>ERROR AO REMOVER CONVENIODAO(DELETARCONVENIO)", ex);
            em.getTransaction().rollback();
        } finally {
            em.close();
        }
        return true || false;
    }
}
