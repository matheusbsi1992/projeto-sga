package br.com.projeto.clinica.dao;

import br.com.projeto.clinica.model.Clinica;
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

public class ClinicaDAO extends UtilDAO {

    public ClinicaDAO() {
    }

    /**
     * Metodo por inserir a Clinica
     *
     * @param clinica
     * @return true||false
     */
    public boolean INSERIRCLINICA(Clinica clinica) {

        StringBuffer strEndereco = new StringBuffer().append("INSERT INTO endereco(\n"
                + "            bairroendereco, cependereco, cidadeendereco, complementoendereco, \n"
                + "            estadoendereco, nomeendereco, numeroendereco, ruaendereco)\n"
                + "    VALUES (?, ?, ?, ?, ?, \n"
                + "            ?, ?, ?);");

        StringBuffer strClinica = new StringBuffer().append("INSERT INTO clinica(\n"
                + "            celularclinica, cnpjclinica, codigoclinica, comercialclinica, \n"
                + "            cpfclinica, datacadastroclinica, emailclinica, imagemclinica, \n"
                + "            nomeclinica, nomedoresponsavelclinica, idendereco)\n"
                + "    VALUES (?, ?, ?, ?, \n"
                + "            ?, ?, ?, ?, \n"
                + "            ?, ?, ?);");

        try (PreparedStatement pstmEndereco = Conexao.GETCONEXAO().prepareStatement(strEndereco.toString());
                PreparedStatement pstmClinica = Conexao.GETCONEXAO().prepareStatement(strClinica.toString());) {

            int tamEndereco = 1;

            pstmEndereco.setObject(tamEndereco++, clinica.getEndereco().getBairroEndereco());
            pstmEndereco.setObject(tamEndereco++, clinica.getEndereco().getCepEndereco());
            pstmEndereco.setObject(tamEndereco++, clinica.getEndereco().getCidadeEndereco());
            pstmEndereco.setObject(tamEndereco++, clinica.getEndereco().getComplementoEndereco());
            pstmEndereco.setObject(tamEndereco++, clinica.getEndereco().getEstadoEndereco());
            pstmEndereco.setObject(tamEndereco++, clinica.getEndereco().getNomeEndereco());
            pstmEndereco.setObject(tamEndereco++, clinica.getEndereco().getNumeroEndereco());
            pstmEndereco.setObject(tamEndereco++, clinica.getEndereco().getRuaEndereco());
            pstmEndereco.executeUpdate();

            ULTIMOENDERECO(clinica);

            int tamClinica = 1;

            pstmClinica.setObject(tamClinica++, clinica.getCelularClinica());
            pstmClinica.setObject(tamClinica++, clinica.getCnpjClinica());
            pstmClinica.setObject(tamClinica++, clinica.getCodigoClinica());
            pstmClinica.setObject(tamClinica++, clinica.getComercialClinica());
            pstmClinica.setObject(tamClinica++, clinica.getCpfClinica());
            pstmClinica.setObject(tamClinica++, new DataUtil().GETSQLTIMESTAMP(new DataUtil().DATAHORASRECIFE()));
            pstmClinica.setObject(tamClinica++, clinica.getEmailClinica());
            pstmClinica.setObject(tamClinica++, clinica.getImagemClinica());
            pstmClinica.setObject(tamClinica++, clinica.getNomeClinica());
            pstmClinica.setObject(tamClinica++, clinica.getNomeDoResponsavelClinica());
            pstmClinica.setObject(tamClinica++, clinica.getEndereco().getIdEndereco());
            pstmClinica.executeUpdate();

        } catch (SQLException ex) {
            logPrincipal(ClinicaDAO.class).error(">>>>ERROR AO INSERIR CLINICA(INSERIRCLINICA)", ex);
            return false;
        }
        return true || false;
    }

    /**
     * Metodo com a caracteristica de retornar ultimo endereco
     *
     * @return
     *
     */
    private int ULTIMOENDERECO(Clinica clinica) {

        StringBuffer strUltimoidEndereco = new StringBuffer().append("SELECT idendereco\n"
                + "                            FROM endereco"
                + "                            ORDER BY idendereco"
                + "                            DESC LIMIT 1;");
        try (PreparedStatement pstmIdEndereco = Conexao.GETCONEXAO().prepareStatement(strUltimoidEndereco.toString()); ResultSet rsIdEndereco = pstmIdEndereco.executeQuery();) {

            rsIdEndereco.next();
            clinica.getEndereco().setIdEndereco(rsIdEndereco.getInt("idendereco"));
            pstmIdEndereco.close();

        } catch (SQLException ex) {
            logPrincipal(ClinicaDAO.class
            ).error(">>>>ERROR AO LISTAR CLINICADAO(ULTIMOENDERECO)", ex);
        }
        return clinica.getEndereco().getIdEndereco();
    }

    /**
     * Metodo com a caracteristica de retornar ultimo id da Clinica
     *
     * @return
     *
     */
    public Clinica ULTIMOIDCLINICA() {
        Clinica clinica = new Clinica();

        StringBuffer strUltimoidClinica = new StringBuffer().append("SELECT idclinica\n"
                + "                            FROM clinica"
                + "                            ORDER BY idclinica"
                + "                            DESC LIMIT 1;");
        try (PreparedStatement pstmIdEndereco = Conexao.GETCONEXAO().prepareStatement(strUltimoidClinica.toString()); ResultSet rsIdClinica = pstmIdEndereco.executeQuery();) {

            rsIdClinica.next();

            clinica.setIdClinica(rsIdClinica.getInt("idclinica"));

        } catch (SQLException ex) {
            logPrincipal(ClinicaDAO.class).error(">>>>ERROR AO LISTAR UsuarioClinicaDAO(ULTIMOIDCLINICA)", ex);
        }
        return clinica;
    }

    /**
     * Metodo com a caracteristica de retornar uma lista de Dados
     *
     * @return List<Clinica>
     * @throws java.sql.SQLException
     */
    public List<Clinica> LISTARTODOSCLINICA() {
        List<Clinica> clinicas = new ArrayList<Clinica>();

        StringBuffer strClinica = new StringBuffer().append("SELECT *"
                + "  FROM clinica clinica"
                + "  INNER JOIN endereco endereco on (endereco.idendereco = clinica.idendereco)\n"
                + "  ORDER BY nomeclinica");
        try (PreparedStatement pstm = Conexao.GETCONEXAO().prepareStatement(strClinica.toString()); ResultSet rs = pstm.executeQuery();) {

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

                clinica.setEndereco(endereco);

                clinicas.add(clinica);
            }
        } catch (SQLException ex) {
            logPrincipal(ClinicaDAO.class).error(">>>>ERROR AO LISTAR CLINICADAO(LISTARTODOSCLINICA)", ex);
        }
        return clinicas;
    }

    /**
     * Metodo com a caracteristica de retornar uma lista de Dados contendo o
     * nome da Clinica
     *
     * @param nomeClinica
     * @return List<LocalTransfer>
     */
    public List<Clinica> BUSCARLISTANOMECLINICA(String nomeClinica) {

        return LISTARTODOSCLINICA().stream().filter(clinica
                -> clinica.getNomeClinica().contains(nomeClinica)).collect(Collectors.toList());

    }

    /**
     * Metodo responsavel por buscar por informar o nome da Clinica e retornar
     * os dados previstos do objeto da classe.
     *
     *
     * @param nomeClinica
     * @return
     */
    public Optional<Clinica> BUSCARNOMECLINICACONFERE(String nomeClinica) {
        Optional<Clinica> cliOptional = LISTARTODOSCLINICA().stream().filter(c
                -> c.getNomeClinica().equalsIgnoreCase(nomeClinica)).findAny();

        return cliOptional;
    }

    /**
     * Metodo responsavel por buscar por id e nome da Clinica
     *
     * @param id
     * @param nomeClinica
     * @return boolean
     *
     */
    public boolean EXISTEIDNOMECLINICA(int id, String nomeClinica) {

        return LISTARTODOSCLINICA().stream().anyMatch(clinica
                -> clinica.getIdClinica() == (id) && clinica.getNomeClinica().equalsIgnoreCase(nomeClinica));
    }

    /**
     * Metodo responsavel por buscar por id da Clinica na tentativa de retornar
     * os dados previstos informado.
     *
     *
     * @param idClinica
     * @return
     */
    public Optional<Clinica> IDCLINICACONFERE(int idClinica) {

        Optional<Clinica> opTionalClinica = LISTARTODOSCLINICA().stream().filter(c
                -> c.getIdClinica() == idClinica).findAny();
        return opTionalClinica;

    }

    /**
     * Metodo responsavel por verificar se existi nome da Clinica
     *
     * @param nomeClinica
     * @return boolean
     */
    public boolean EXISTINOMECLINICA(String nomeClinica) {

        return LISTARTODOSCLINICA().stream().anyMatch(clinica
                -> clinica.getNomeClinica().equalsIgnoreCase(nomeClinica));
    }

    /**
     * Metodo responsavel por verificar se existi o codigo da Clinica
     *
     * @param codigoClinica
     * @return boolean
     */
    public boolean EXISTICODIGOCLINICA(String codigoClinica) {

        return LISTARTODOSCLINICA().stream().anyMatch(clinica
                -> clinica.getCodigoClinica().equalsIgnoreCase(codigoClinica));
    }
    
    
    /**
     * Metodo responsavel por verificar se existi o codigo da Clinica
     *
     * @param codigoClinica
     * @return boolean
     */
    public Optional<Clinica> EXISTICODIGOCLINICACONFERE(String codigoClinica) {

           Optional<Clinica> opTionalClinica = LISTARTODOSCLINICA().stream().filter(c
                -> c.getCodigoClinica().equalsIgnoreCase(codigoClinica)).findFirst();
        return opTionalClinica;
    }

    /**
     * Metodo responsavel por verificar se existi o codigo da Clinica
     *
     * @param idClinica
     * @param codigoClinica
     * @return boolean
     */
    public boolean EXISTIIDECODIGOCLINICA(int idClinica, String codigoClinica) {

        return LISTARTODOSCLINICA().stream().anyMatch(clinica
                -> clinica.getIdClinica() == idClinica && clinica.getCodigoClinica().equalsIgnoreCase(codigoClinica));
    }

    /**
     * Metodo responsavel por alterar a Clinica
     *
     * @param clinica
     * @return boolean
     *
     */
    public boolean ALTERARCLINICA(Clinica clinica) {
        EntityManager em = JpaUtil.getEntityManager();
        try {
            em.getTransaction().begin();
            em.merge(clinica);
            em.getTransaction().commit();
        } catch (Exception ex) {
            logPrincipal(ClinicaDAO.class).error(">>>>ERROR AO ALTERAR CLINICA(ALTERARCLINICA)", ex);
            em.getTransaction().rollback();
        } finally {
            em.close();
        }
        return true || false;
    }

    /**
     * Metodo responsavel por deletar uma determinada Clinica
     *
     * @param idClinica
     */
    public boolean DELETARCLINICA(int idClinica) {
        EntityManager em = JpaUtil.getEntityManager();
        Clinica c = em.find(Clinica.class, idClinica);
        //Clinica c = IDCLINICACONFERE(idClinica).get();
        try {
            em.getTransaction().begin();
            em.remove(c);
            em.getTransaction().commit();
        } catch (Exception ex) {
            logPrincipal(ClinicaDAO.class).error(">>>>ERROR AO REMOVER CLINICA(DELETARCLINICA)", ex);
            em.getTransaction().rollback();
        } finally {
            em.close();
        }
        return true || false;
    }
}
