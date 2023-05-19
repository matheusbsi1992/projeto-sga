package br.com.projeto.clinica.dao;

///*
// * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
// * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
// */
//package comunicacao.banco.de.dados.dao;
//
//import comunicacao.banco.de.dados.model.Clinica;
//import comunicacao.banco.de.dados.model.Fornecedor;
//import comunicacao.banco.de.dados.model.Endereco;
//import comunicacao.banco.de.dados.persistencia.Conexao;
//import comunicacao.banco.de.dados.util.DataUtil;
//import comunicacao.banco.de.dados.util.JpaUtil;
//import java.sql.PreparedStatement;
//import java.sql.ResultSet;
//import java.sql.SQLException;
//import java.util.ArrayList;
//import java.util.List;
//import java.util.Optional;
//import java.util.stream.Collectors;
//import javax.persistence.EntityManager;
//
//
//public class FornecedorDAO extends DAO {
//
//    public FornecedorDAO() {
//    }
//
//    /**
//     * Metodo responsavel por inserir fornecedor
//     *
//     * @param fornecedor
//     * @return boolean
//     * @throws java.sql.SQLException
//     */
//    public boolean INSERIRFORNECEDOR(Fornecedor fornecedor) {
//
//        StringBuffer strFornecedor = new StringBuffer().append("INSERT INTO fornecedor(\n"
//                + "	celularfornecedor, cnpjfornecedor, cpffornecedor, datacadastrofornecedor, emailfornecedor, nomefornecedor, numerofornecedor, periododecarenciadofornecedor, registroansfornecedor, statusfornecedor, telefonefornecedor, tipofornecedor, idclinica, idendereco)\n"
//                + "	VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?);");
//
//        try (PreparedStatement pstmFornecedor = Conexao.GETCONEXAO().prepareStatement(strFornecedor.toString());) {
//
//            int tamFornecedor = 1;
//
//            pstmFornecedor.setObject(tamFornecedor++, fornecedor.getCelularFornecedor());
//            pstmFornecedor.setObject(tamFornecedor++, fornecedor.getCnpjFornecedor());
//            pstmFornecedor.setObject(tamFornecedor++, fornecedor.getCpfFornecedor());
//            pstmFornecedor.setObject(tamFornecedor++, new DataUtil().GETSQLTIMESTAMP(new DataUtil().DATAHORASRECIFE()));
//            pstmFornecedor.setObject(tamFornecedor++, fornecedor.getEmailFornecedor());
//            pstmFornecedor.setObject(tamFornecedor++, fornecedor.getNomeFornecedor());
//            pstmFornecedor.setObject(tamFornecedor++, fornecedor.getNumeroFornecedor());
//            pstmFornecedor.setObject(tamFornecedor++, fornecedor.getPeriodoDeCarenciaDoFornecedor());
//            pstmFornecedor.setObject(tamFornecedor++, fornecedor.getRegistroAnsFornecedor());
//            pstmFornecedor.setObject(tamFornecedor++, fornecedor.getStatusFornecedor());
//            pstmFornecedor.setObject(tamFornecedor++, fornecedor.getTelefoneFornecedor());
//            pstmFornecedor.setObject(tamFornecedor++, fornecedor.getTipoFornecedor());
//            pstmFornecedor.setObject(tamFornecedor++, fornecedor.getClinica().getIdClinica());
//            pstmFornecedor.setObject(tamFornecedor++, fornecedor.getEndereco().getIdEndereco());
//            pstmFornecedor.executeUpdate();
//
//        } catch (SQLException ex) {
//            logPrincipal(FornecedorDAO.class).error(">>>>ERROR AO INSERIR FORNECEDOR(INSERIRFORNECEDOR)", ex);
//            return false;
//        }
//        return true || false;
//
//    }
//
//    /**
//     * Metodo com a caracteristica de retornar uma lista de Dados
//     *
//     * @param idCLinica
//     * @return List<Clinica>
//     */
//    public List<Fornecedor> LISTARTODOSFORNECEDOR(int idCLinica) {
//        List<Fornecedor> fornecedors = new ArrayList<Fornecedor>();
//
//        StringBuffer strFornecedor = new StringBuffer().append("SELECT *"
//                + "  FROM clinica clinica"
//                + "  INNER JOIN fornecedor fornecedor on (fornecedor.idclinica = clinica.idclinica)\n"
//                + "  WHERE fornecedor.idclinica = ?"
//                + "  ORDER BY nomefornecedor");
//        try (PreparedStatement pstm = Conexao.GETCONEXAO().prepareStatement(strFornecedor.toString());) {
//            ResultSet rs = null;
//            pstm.setObject(1, idCLinica);
//            rs = pstm.executeQuery();
//            while (rs.next()) {
//                Clinica clinica = new Clinica();
//
//                clinica.setIdClinica(rs.getInt("idclinica"));
//                clinica.setCelularClinica(rs.getString("celularclinica"));
//                clinica.setCnpjClinica(rs.getString("cnpjclinica"));
//                clinica.setCodigoClinica(rs.getInt("codigoclinica"));
//                clinica.setComercialClinica(rs.getString("comercialclinica"));
//                clinica.setCpfClinica(rs.getString("cpfclinica"));
//                clinica.setDataCadastroClinica(rs.getTimestamp("datacadastroclinica"));
//                clinica.setEmailClinica(rs.getString("emailclinica"));
//                clinica.setImagemClinica(rs.getBytes("imagemclinica"));
//                clinica.setNomeClinica(rs.getString("nomeclinica"));
//                clinica.setNomeDoResponsavelClinica(rs.getString("nomedoresponsavelclinica"));
//
//                Endereco endereco = new Endereco();
//                endereco.setIdEndereco(rs.getInt("idendereco"));
//                endereco.setBairroEndereco(rs.getString("bairroendereco"));
//                endereco.setNomeEndereco(rs.getString("nomeendereco"));
//                endereco.setCepEndereco(rs.getString("cependereco"));
//                endereco.setCidadeEndereco(rs.getString("cidadeendereco"));
//                endereco.setComplementoEndereco(rs.getString("complementoendereco"));
//                endereco.setEstadoEndereco(rs.getString("estadoendereco"));
//                endereco.setNumeroEndereco(rs.getString("numeroendereco"));
//                endereco.setRuaEndereco(rs.getString("ruaendereco"));
//
//                Fornecedor fornecedor = new Fornecedor();
//                fornecedor.setIdFornecedor(rs.getInt("idfornecedor"));
//                fornecedor.setDataCadastroFornecedor(rs.getDate("dataCadastroFornecedor"));
//                fornecedor.setNomeFornecedor(rs.getString("nomefornecedor"));
//                fornecedor.setCelularFornecedor(rs.getString("celularfornecedor"));
//                fornecedor.setCnpjFornecedor(rs.getString("cnpjfornecedor"));
//                fornecedor.setCpfFornecedor(rs.getString("cpffornecedor"));
//                fornecedor.setEmailFornecedor(rs.getString("emailfornecedor"));
//                fornecedor.setNumeroFornecedor(rs.getString("numerofornecedor"));
//                fornecedor.setPeriodoDeCarenciaDoFornecedor(rs.getInt("periododecarenciadofornecedor"));
//                fornecedor.setRegistroAnsFornecedor(rs.getInt("registroansfornecedor"));
//                fornecedor.setStatusFornecedor(rs.getString("statusfornecedor"));
//                fornecedor.setTelefoneFornecedor(rs.getString("telefonefornecedor"));
//                fornecedor.setTipoFornecedor(rs.getString("tipofornecedor"));
//
//                fornecedor.setClinica(clinica);
//                fornecedor.setEndereco(endereco);
//
//                fornecedors.add(fornecedor);
//            }
//        } catch (SQLException | NullPointerException ex) {
//            logPrincipal(FornecedorDAO.class).error(">>>>ERROR AO LISTAR FORNECEDORDAO(LISTARTODOSFORNECEDOR)", ex);
//        }
//        return fornecedors;
//    }
//
//    /**
//     * Metodo com a caracteristica de retornar uma lista de Dados contendo o
//     * nome do Fornecedor
//     *
//     * @param idClinica
//     * @param nomeFornecedor
//     * @return List<FornecedorTransfer>
//     */
//    public List<Fornecedor> BUSCARLISTANOMEFORNECEDOR(int idClinica, String nomeFornecedor) {
//
//        return LISTARTODOSFORNECEDOR(idClinica).stream().filter(fornecedor
//                -> fornecedor.getNomeFornecedor().contains(nomeFornecedor)).collect(Collectors.toList());
//
//    }
//
//    /**
//     * Metodo responsavel por buscar por nome do Fornecedor na tentativa de
//     * retornar os dados previstos informado.
//     *
//     * @param idClinica
//     * @param nomeFornecedor
//     * @return
//     */
//    public Optional<Fornecedor> BUSCARNOMEFORNECEDORCONFERE(int idClinica, String nomeFornecedor) {
//
//        return LISTARTODOSFORNECEDOR(idClinica).stream().filter(c
//                -> c.getNomeFornecedor().equalsIgnoreCase(nomeFornecedor)).findFirst();
//
//    }
//
//    /**
//     * Metodo responsavel por buscar por id e nome do Fornecedor
//     *
//     * @param idClinica
//     * @param idFornecedor
//     * @param nomeFornecedor
//     * @return boolean
//     *
//     */
//    public boolean IDNOMEFORNECEDORCONFERE(int idClinica, int idFornecedor, String nomeFornecedor) {
//
//        return LISTARTODOSFORNECEDOR(idClinica).stream().anyMatch(fornecedor
//                -> fornecedor.getIdFornecedor() == (idFornecedor) && fornecedor.getNomeFornecedor().equalsIgnoreCase(nomeFornecedor));
//    }
//
//    /**
//     * Metodo responsavel por buscar por id do Fornecedor na tentativa de retornar
//     * os dados previstos informado.
//     *
//     *
//     * @param idClinica
//     * @param idFornecedor
//     * @return
//     */
//    public Optional<Fornecedor> IDFORNECEDORCONFERE(int idClinica, int idFornecedor) {
//
//        return LISTARTODOSFORNECEDOR(idClinica
//        ).stream().filter(c
//                -> c.getIdFornecedor() == idFornecedor).findFirst();
//    }
//
//    /**
//     * Metodo responsavel por verificar se existi nome do Fornecedor
//     *
//     * @param idClinica
//     * @param nomeFornecedor
//     * @return boolean
//     */
//    public boolean EXISTINOMEFORNECEDOR(int idClinica, String nomeFornecedor) {
//
//        return LISTARTODOSFORNECEDOR(idClinica).stream().anyMatch(fornecedor
//                -> fornecedor.getNomeFornecedor().equalsIgnoreCase(nomeFornecedor));
//    }
//
//    /**
//     * Metodo responsavel por alterar o Fornecedor
//     *
//     * @param fornecedor
//     * @return boolean
//     *
//     */
//    public boolean ALTERARFORNECEDOR(Fornecedor fornecedor) {
//        EntityManager em = JpaUtil.getEntityManager();
//        try {
//            em.getTransaction().begin();
//            em.merge(fornecedor);
//            em.getTransaction().commit();
//        } catch (Exception ex) {
//            logPrincipal(FornecedorDAO.class).error(">>>>ERROR AO ALTERAR FORNECEDORDAO(ALTERARFORNECEDOR)", ex);
//            em.getTransaction().rollback();
//        } finally {
//            em.close();
//        }
//        return true || false;
//    }
//
//    /**
//     * Metodo responsavel por deletar um determinado Fornecedor
//     *
//     * @param idFornecedor
//     * @return boolean
//     */
//    public boolean DELETARFORNECEDOR(int idFornecedor) {
//        EntityManager em = JpaUtil.getEntityManager();
//        Fornecedor c = em.find(Fornecedor.class, idFornecedor);
//        //Clinica c = IDCLINICACONFERE(idClinica).get();
//        try {
//            em.getTransaction().begin();
//            em.remove(c);
//            em.getTransaction().commit();
//        } catch (Exception ex) {
//            logPrincipal(FornecedorDAO.class).error(">>>>ERROR AO REMOVER FORNECEDORDAO(DELETARFORNECEDOR)", ex);
//            em.getTransaction().rollback();
//        } finally {
//            em.close();
//        }
//        return true || false;
//    }
//}
