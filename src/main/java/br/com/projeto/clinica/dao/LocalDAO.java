package br.com.projeto.clinica.dao;

import br.com.projeto.clinica.model.Clinica;
import br.com.projeto.clinica.model.Local;
import br.com.projeto.clinica.persistencia.Conexao;
import br.com.projeto.clinica.util.DataUtil;
import br.com.projeto.clinica.util.JpaUtil;
import br.com.projeto.clinica.util.UtilDAO;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.persistence.EntityManager;

public class LocalDAO extends UtilDAO {

    public LocalDAO() {
    }

    /**
     * Metodo responsavel por inserir local
     *
     * @param local
     * @return boolean
     * @throws java.sql.SQLException
     */
    public boolean INSERIRLOCAL(Local local) {

        StringBuffer strLocal = new StringBuffer().append("INSERT INTO public.local(\n"
                + "	datacadastrolocal, nomelocal, idclinica)\n"
                + "	VALUES (?, ?, ?);");

        try (PreparedStatement pstmLocal = Conexao.GETCONEXAO().prepareStatement(strLocal.toString());) {

            int tamLocal = 1;

            pstmLocal.setObject(tamLocal++, new DataUtil().GETSQLTIMESTAMP(new DataUtil().DATAHORASRECIFE()));
            pstmLocal.setObject(tamLocal++, local.getNomeLocal());
            pstmLocal.setObject(tamLocal++, local.getClinica().getIdClinica());
            pstmLocal.executeUpdate();

        } catch (SQLException ex) {
            logPrincipal(LocalDAO.class).error(">>>>ERROR AO INSERIR LOCAL(INSERIRLOCAL)", ex);
            return false;
        }
        return true || false;

    }

    /**
     * Metodo com a caracteristica de retornar uma lista de Dados
     *
     * @param idCLinica
     * @return List<Clinica>
     * @throws java.sql.SQLException
     */
    public List<Local> LISTARTODOSLOCAL(int idCLinica) {
        List<Local> locals = new ArrayList<Local>();

        StringBuffer strLocal = new StringBuffer().append("SELECT *"
                + "  FROM clinica clinica"
                + "  INNER JOIN local local on (local.idclinica = clinica.idclinica)\n"
                + "  WHERE local.idclinica = ?"
                + "  ORDER BY local.nomelocal");
        try (PreparedStatement pstm = Conexao.GETCONEXAO().prepareStatement(strLocal.toString());) {
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

                Local local = new Local();
                local.setIdLocal(rs.getInt("idlocal"));
                local.setDataCadastroLocal(rs.getDate("dataCadastroLocal"));
                local.setNomeLocal(rs.getString("nomelocal"));

                local.setClinica(clinica);

                locals.add(local);
            }
        } catch (SQLException | NullPointerException | NoSuchElementException ex) {
            logPrincipal(LocalDAO.class).error(">>>>ERROR AO LISTAR LOCALDAO(LISTARTODOSLOCAL)", ex);
        }
        return locals;
    }

    /**
     * Metodo com a caracteristica de retornar uma lista de Dados contendo o
     * nome do Local
     *
     * @param idClinica
     * @param nomeLocal
     * @return List<LocalTransfer>
     */
    public List<Local> BUSCARLISTANOMELOCAL(int idClinica, String nomeLocal) {

        return LISTARTODOSLOCAL(idClinica).stream().filter(local
                -> local.getNomeLocal().contains(nomeLocal)).collect(Collectors.toList());

    }

    /**
     * Metodo responsavel por buscar por nome do Local na tentativa de retornar
     * os dados previstos informado.
     *
     * @param idClinica
     * @param nomeLocal
     * @return
     */
    public Optional<Local> BUSCARNOMELOCALCONFERE(int idClinica, String nomeLocal) {

        return LISTARTODOSLOCAL(idClinica).stream().filter(c
                -> c.getNomeLocal().equalsIgnoreCase(nomeLocal)).findFirst();

    }

    /**
     * Metodo responsavel por buscar por id da clinica , id do local e nome do Local
     *
     * @param idClinica
     * @param idLocal
     * @param nomeLocal
     * @return boolean
     *
     */
    public boolean IDNOMELOCALCONFERE(int idClinica, int idLocal, String nomeLocal) {

        return LISTARTODOSLOCAL(idClinica).stream().anyMatch(local
                -> local.getIdLocal() == (idLocal) && local.getNomeLocal().equalsIgnoreCase(nomeLocal));
    }

    /**
     * Metodo responsavel por buscar por id do Local na tentativa de retornar os
     * dados previstos informado.
     *
     *
     * @param idClinica
     * @param idLocal
     * @return
     */
    public boolean IDLOCALCONFERE(int idClinica, int idLocal) {

        return LISTARTODOSLOCAL(idClinica
        ).stream().anyMatch(local
                -> local.getIdLocal() == idLocal);
    }
    
    
    /**
     * Metodo responsavel por buscar por id do Local na tentativa de retornar os
     * dados previstos informado.
     *
     *
     * @param idClinica
     * @param idLocal
     * @return
     */
    public Optional<Local> IDLOCAL(int idClinica, int idLocal) {

         return LISTARTODOSLOCAL(idClinica
        ).stream().filter(c
                -> c.getIdLocal() == idLocal).findAny();
    }
    
    

    /**
     * Metodo responsavel por verificar se existi nome do Local
     *
     * @param idClinica
     * @param nomeLocal
     * @return boolean
     */
    public boolean EXISTINOMELOCAL(int idClinica, String nomeLocal) {

        return LISTARTODOSLOCAL(idClinica).stream().anyMatch(local
                -> local.getNomeLocal().equalsIgnoreCase(nomeLocal));
    }

    /**
     * Metodo responsavel por alterar o Local
     *
     * @param local
     * @return boolean
     *
     */
    public boolean ALTERARLOCAL(Local local) {
        EntityManager em = JpaUtil.getEntityManager();
        try {
            em.getTransaction().begin();
            em.merge(local);
            em.getTransaction().commit();
        } catch (Exception ex) {
            logPrincipal(LocalDAO.class).error(">>>>ERROR AO ALTERAR LOCALDAO(ALTERARLOCAL)", ex);
            em.getTransaction().rollback();
        } finally {
            em.close();
        }
        return true || false;
    }

    /**
     * Metodo responsavel por deletar um determinado Local
     *
     * @param idLocal
     * @return boolean
     */
    public boolean DELETARLOCAL(int idLocal) {
        EntityManager em = JpaUtil.getEntityManager();
        Local l = em.find(Local.class, idLocal);
        //Clinica c = IDCLINICACONFERE(idClinica).get();
        try {
            em.getTransaction().begin();
            em.remove(l);
            em.getTransaction().commit();
        } catch (Exception ex) {
            logPrincipal(LocalDAO.class).error(">>>>ERROR AO REMOVER LOCALDAO(DELETARLOCAL)", ex);
            em.getTransaction().rollback();
        } finally {
            em.close();
        }
        return true || false;
    }
}
