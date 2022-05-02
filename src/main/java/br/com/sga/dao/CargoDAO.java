/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.sga.dao;

import br.com.sga.transfer.CargoTransfer;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.faces.model.SelectItem;

/**
 *
 * @author Jandisson
 */
public class CargoDAO extends DAO {

    /**
     * Metodo responsavel por inserir cargo e as suas permissoes de acesso a
     * cada um dos links
     *
     *
     * @param cargotransfer
     * @return boolean
     * @throws java.sql.SQLException
     */
    public boolean inserirCargo(CargoTransfer cargotransfer) throws SQLException {
        try {

            StringBuffer strBuffer = new StringBuffer().append("INSERT INTO projetosga.cargo(\n"
                    + "            nomecargo,"
                    + "            descricaocargo,"
                    + "            statuscargo"
                    + "            )\n"
                    + "    VALUES (  ?"
                    + "             ,?"
                    + "             ,?);"
            );
            PreparedStatement pstminsercaocargo = abrirconexao.getConexao().prepareStatement(strBuffer.toString());
            int tamcargo = 1;
            pstminsercaocargo.setObject(tamcargo++, cargotransfer.getNomecargo().toUpperCase());
            pstminsercaocargo.setObject(tamcargo++, cargotransfer.getDescricaocargo().toUpperCase());
            pstminsercaocargo.setObject(tamcargo++, cargotransfer.getStatuscargo().toUpperCase());
            //insira na tabela cargo
            pstminsercaocargo.executeUpdate();
            //fechar pstm
            pstminsercaocargo.close();

            //listar as paginas informadas como permissao
            for (Object idpermissoes : cargotransfer.getDescricaopermissoes()) {

                //obter o ultimo cargo na qual foi informado acima
                StringBuffer strBufferultimoidcargo = new StringBuffer().append("SELECT idcargo\n"
                        + "                            FROM projetosga.cargo"
                        + "                            ORDER BY idcargo"
                        + "                            DESC LIMIT 1;");
                PreparedStatement pstmultimoidcargo = abrirconexao.getConexao().prepareStatement(strBufferultimoidcargo.toString());
                ResultSet rsidcargo = pstmultimoidcargo.executeQuery();
                rsidcargo.next();
                //seta o ultimo valor do cargo
                cargotransfer.setId(rsidcargo.getShort("idcargo"));
                pstmultimoidcargo.close();
                rsidcargo.close();
                //encerra o pstm e result do ultimo id do cargo

                //obtencao do id sobre o nome da permissao
                StringBuffer strBufferidpermissao = new StringBuffer().append("SELECT "
                        + "     idpermissao\n"
                        + "     FROM projetosga.permissao"
                        + "     WHERE nomepermissao = ?\n"
                );

                PreparedStatement pstmidpermissao = abrirconexao.getConexao().prepareStatement(strBufferidpermissao.toString());
                pstmidpermissao.setObject(1, idpermissoes.toString());
                ResultSet resultidpermissao = pstmidpermissao.executeQuery();
                //encerra o result das permissoes

                //abri uma nova string para a insercao de cargo e permissao
                StringBuffer strBuffercargoepermissao = new StringBuffer().append("INSERT INTO projetosga.cargospermissoes(\n"
                        + "            idpermissao"
                        + "          , idcargo)\n"
                        + "    VALUES (?"
                        + "          ,?);");
                PreparedStatement pstmcargoepermissao = abrirconexao.getConexao().prepareStatement(strBuffercargoepermissao.toString());

                //validar cargo e permissao para a insercao
                inseriroualterarCargoePermissao(resultidpermissao, cargotransfer, pstmcargoepermissao);

            }

        } catch (SQLException ex) {
            logPrincipal(CargoDAO.class).error(">>>>ERROR AO INSERIR CARGO(inserirCargo)", ex);
        } finally {
            abrirconexao.fecharConexao();
        }
        return true || false;
    }

    /**
     * Metodo responsavel por alterar cargo e as suas permissoes de acesso a
     * cada um dos links
     *
     *
     * @param cargotransfer
     * @return boolean
     * @throws java.sql.SQLException
     */
    public boolean alterarCargo(CargoTransfer cargotransfer, List<String> listarpermissoes) throws SQLException {
        try {
            //editar o cargo 
            StringBuffer strBuffer = new StringBuffer().append("UPDATE projetosga.cargo\n"
                    + "   SET nomecargo=?"
                    + "       ,descricaocargo=?"
                    + "       ,statuscargo=?\n"
                    + " WHERE idcargo=?;"
            );
            PreparedStatement pstminsercaocargo = abrirconexao.getConexao().prepareStatement(strBuffer.toString());
            int tamcargo = 1;
            pstminsercaocargo.setObject(tamcargo++, cargotransfer.getNomecargo().toUpperCase());
            pstminsercaocargo.setObject(tamcargo++, cargotransfer.getDescricaocargo().toUpperCase());
            pstminsercaocargo.setObject(tamcargo++, cargotransfer.getStatuscargo().toUpperCase());
            pstminsercaocargo.setObject(tamcargo++, cargotransfer.getId());
            //atualizar na tabela cargo
            pstminsercaocargo.executeUpdate();
            //fechar pstm
            pstminsercaocargo.close();

            //deletar as permissoes junto com o seu cargo
            StringBuffer strBufferdeletarpermissaoecargo = new StringBuffer().append("DELETE FROM "
                    + "                                                              projetosga.cargospermissoes"
                    + "                                                              WHERE idcargo =  ?;");
            PreparedStatement pstmpermissaoecargo = abrirconexao.getConexao().prepareStatement(strBufferdeletarpermissaoecargo.toString());
            pstmpermissaoecargo.setObject(1, cargotransfer.getId());
            //remova dados 
            pstmpermissaoecargo.executeUpdate();
            pstmpermissaoecargo.close();;

            //listar as paginas informadas como permissao
            for (Object idpermissoes : listarpermissoes) {

                //obtencao do id sobre o nome da permissao
                StringBuffer strBufferidpermissao = new StringBuffer().append("SELECT "
                        + "     idpermissao\n"
                        + "     FROM projetosga.permissao"
                        + "     WHERE nomepermissao = ?\n"
                );

                PreparedStatement pstmidpermissao = abrirconexao.getConexao().prepareStatement(strBufferidpermissao.toString());
                pstmidpermissao.setObject(1, idpermissoes.toString());
                ResultSet resultidpermissao = pstmidpermissao.executeQuery();

                //abri uma nova string para uma nova insercao de cargo(s) e permissao/permissoes
                StringBuffer strBuffercargoepermissao = new StringBuffer().append("INSERT INTO projetosga.cargospermissoes(\n"
                        + "            idpermissao"
                        + "          , idcargo)\n"
                        + "    VALUES (?"
                        + "          ,?);");
                PreparedStatement pstmcargoepermissao = abrirconexao.getConexao().prepareStatement(strBuffercargoepermissao.toString());

                //validar cargo e permissao para a atualizacao
                inseriroualterarCargoePermissao(resultidpermissao, cargotransfer, pstmcargoepermissao);

            }

        } catch (SQLException ex) {
            logPrincipal(CargoDAO.class).error(">>>>ERROR AO ALTERAR O CARGO(alterarCargo)", ex);
        } finally {
            abrirconexao.fecharConexao();
        }
        return true || false;
    }

    /**
     * Metodo auxiliar, de insercao de cargo e de permissao
     *
     * @param set
     * @param cargotransfer
     * @param pstm
     * @exception SQLException
     */
    private boolean inseriroualterarCargoePermissao(ResultSet set, CargoTransfer cargotransfer, PreparedStatement pstm) throws SQLException {
        try {
            while (set.next()) {
                Short idpermissao = (set.getShort("idpermissao"));
                //set do update
                pstm.setObject(1, idpermissao);
                pstm.setObject(2, cargotransfer.getId());
                //insira na tabela cargopermissoes
                pstm.executeUpdate();
            }
        } catch (SQLException ex) {
            logPrincipal(CargoDAO.class).error(">>>>ERROR AO INSERIR CARGO E PERMISSAO(inserirCargoePermissao(ResultSet set, CargoTransfer cargotransfer, PreparedStatement pstm)", ex);
        }
        return false || true;
    }

    /**
     * Metodo responsavel por obter o id e nome do cargo com a verificacao se
     * existi retornando verdadeiro ou falso
     */
    public boolean idNomedoCargoConfere(Short id, String nomecargo) throws SQLException {
        try {
            strBuffer = new StringBuffer().append("select idcargo, \n"
                    + "	nomecargo\n"
                    + " from 	projetosga.cargo\n"
                    + " where idcargo 	= ?\n"
                    + " and nomecargo	= ?;");
            pstm = abrirconexao.getConexao().prepareStatement(strBuffer.toString());

            pstm.setObject(1, id);
            pstm.setObject(2, nomecargo);
            rs = pstm.executeQuery();
            if (rs.next()) {
                return true;
            }
        } catch (SQLException ex) {
            logPrincipal(CargoDAO.class).error(">>>>ERROR AO BUSCAR ID E NOME DO CARGO(idNomedoCargoConfere)", ex);
        } finally {
            rs.close();
            abrirconexao.fecharConexao();
        }
        return false;
    }

    /**
     * Metodo responsavel por verificar se existi cargo
     *
     * @param nomecargo
     * @return boolean
     * @throws java.sql.SQLException
     */
    public boolean existeNomeCargo(String nomecargo) throws SQLException {
        try {
            strBuffer = new StringBuffer().append("SELECT  nomecargo\n"
                    + "  FROM projetosga.cargo\n"
                    + "  WHERE nomecargo	= ?;");
            pstm = abrirconexao.getConexao().prepareStatement(strBuffer.toString());
            pstm.setObject(1, nomecargo);
            rs = pstm.executeQuery();
            if (rs.next()) {
                return true;
            }
        } catch (SQLException ex) {
            logPrincipal(CargoDAO.class).error(">>>>ERROR AO EXISTIR NOME DO CARGO(existeNomeCargo)", ex);
        } finally {
            rs.close();
            abrirconexao.fecharConexao();
        }
        return false;
    }

    /**
     * Metodo responsavel por listar as permissoes sem deixar de existir
     * repeticoes
     *
     * @return boolean
     * @throws java.sql.SQLException
     */
    public List<CargoTransfer> listarPermissoes() throws SQLException {
        List<CargoTransfer> categoriapermissao = new ArrayList<CargoTransfer>();

        try {

            strBuffer = new StringBuffer().append("SELECT distinct(nomepermissao)\n"
                    + "  FROM projetosga.permissao\n"
                    + "  ORDER BY nomepermissao");

            pstm = abrirconexao.getConexao().prepareStatement(strBuffer.toString());

            rs = pstm.executeQuery();

            while (rs.next()) {
                CargoTransfer cargoTransfer = new CargoTransfer();
                cargoTransfer.setNomepermissao(rs.getString("nomepermissao"));
                categoriapermissao.add(cargoTransfer);
            }

            return categoriapermissao;

        } catch (SQLException ex) {
            logPrincipal(CargoDAO.class).error(">>>>ERROR AO LISTAR NOMES DAS PERMISSOES(listarPermissoes)!!!", ex);
        } finally {
            pstm.close();
            rs.close();
        }
        return null;
    }

    /**
     * Metodo responsavel por buscar as permissoes existentes repeticoes
     *
     * @param nomecargo
     * @return boolean
     * @throws java.sql.SQLException
     */
    public List<CargoTransfer> listareBuscarPermissoes(String nomecargo) throws SQLException {
        List<CargoTransfer> categoriapermissao = new ArrayList<CargoTransfer>();
        try {

            strBuffer = new StringBuffer().append("SELECT  distinct(permissao.nomepermissao)\n"
                    + "  FROM   projetosga.cargo cargo\n"
                    + "  INNER JOIN projetosga.cargospermissoes cargospermissoes on (cargospermissoes.idcargo = cargo.idcargo)\n"
                    + "  INNER JOIN projetosga.permissao permissao on (cargospermissoes.idpermissao = permissao.idpermissao)"
                    + "  WHERE ((cargo.nomecargo = ?))\n"
                    + "  ORDER BY permissao.nomepermissao");

            pstm = abrirconexao.getConexao().prepareStatement(strBuffer.toString());
            pstm.setObject(1, nomecargo);

            rs = pstm.executeQuery();

            while (rs.next()) {
                CargoTransfer cargoTransfer = new CargoTransfer();
                cargoTransfer.setNomepermissao(rs.getString("nomepermissao"));
                categoriapermissao.add(cargoTransfer);
            }

            return categoriapermissao;

        } catch (SQLException ex) {
            logPrincipal(CargoDAO.class).error(">>>>ERROR AO LISTAR NOMES DAS PERMISSOES(listarPermissoes)!!!", ex);
        } finally {
            pstm.close();
            rs.close();
        }
        return null;
    }

    /**
     * Metodo responsavel por listar os dados do cargo e permissao
     *
     * @return boolean
     * @throws java.sql.SQLException
     */
    public List<CargoTransfer> listarCargo() throws SQLException {
        List<CargoTransfer> categoriapermissao = new ArrayList<CargoTransfer>();

        try {

            strBuffer = new StringBuffer().append("SELECT  distinct(cargo.nomecargo)\n"
                    + "                                            , cargo.descricaocargo\n"
                    + "                                            , cargo.idcargo"
                    + "                                            ,cargo.statuscargo\n"
                    + "                                            ,permissao.nomepermissao\n"
                    + "                                            FROM   projetosga.cargo cargo\n"
                    + "                                            INNER JOIN projetosga.cargospermissoes cargospermissoes on (cargospermissoes.idcargo = cargo.idcargo)\n"
                    + "                                            INNER JOIN projetosga.permissao permissao on (cargospermissoes.idpermissao = permissao.idpermissao)\n"
                    + "                                            WHERE cargo.idcargo<>1"
                    + "                                            ORDER BY cargo.nomecargo");

            pstm = abrirconexao.getConexao().prepareStatement(strBuffer.toString());

            rs = pstm.executeQuery();

            while (rs.next()) {
                CargoTransfer cargoTransfer = new CargoTransfer();
                cargoTransfer.setId(rs.getShort("idcargo"));
                cargoTransfer.setNomepermissao(rs.getString("nomepermissao"));
                cargoTransfer.setNomecargo(rs.getString("nomecargo"));
                cargoTransfer.setDescricaocargo(rs.getString("descricaocargo"));
                cargoTransfer.setStatuscargo(rs.getString("statuscargo"));

                categoriapermissao.add(cargoTransfer);
            }

            return categoriapermissao;

        } catch (SQLException ex) {
            logPrincipal(CargoDAO.class).error(">>>>ERROR AO LISTAR TODOS OS DADOS DE CARGO E PERMISSOES(listarTodos)!!!", ex);
        } finally {
            pstm.close();
            rs.close();
        }
        return null;
    }

    /**
     * Metodo responsavel por listar os dados do cargo vinculado com o usuario
     *
     * @return boolean
     * @throws java.sql.SQLException
     */
    public List<CargoTransfer> listarTodosCargos() throws SQLException {
        List<CargoTransfer> categoriapermissao = new ArrayList<CargoTransfer>();

        try {

            strBuffer = new StringBuffer().append("SELECT distinct(nomecargo),\n"
                    + "                           idcargo,\n"
                    + "                           statuscargo,\n"
                    + "                           descricaocargo\n"
                    + "                      FROM projetosga.cargo\n"
                    + "                      WHERE cargo.statuscargo = 'A' \n"
                    + "                      ORDER BY cargo.nomecargo;");

            pstm = abrirconexao.getConexao().prepareStatement(strBuffer.toString());

            rs = pstm.executeQuery();

            while (rs.next()) {
                CargoTransfer cargoTransfer = new CargoTransfer();

                cargoTransfer.setStatuscargo(rs.getString("statuscargo"));
                cargoTransfer.setId(rs.getShort("idcargo"));
                cargoTransfer.setNomecargo(rs.getString("nomecargo"));
                cargoTransfer.setDescricaocargo(rs.getString("descricaocargo"));

                categoriapermissao.add(cargoTransfer);
            }

            return categoriapermissao;

        } catch (SQLException ex) {
            logPrincipal(CargoDAO.class).error(">>>>ERROR AO LISTAR TODOS OS DADOS DE CARGO(listarTodosCargos)!!!", ex);
        } finally {
            pstm.close();
            rs.close();
        }
        return null;
    }

    /**
     * Metodo responsavel por buscar os dados do cargo vinculado com o usuario
     *
     * @return boolean
     * @throws java.sql.SQLException
     */
    public List<CargoTransfer> buscarTodosCargos(String nomecargo) throws SQLException {
        List<CargoTransfer> categoriapermissao = new ArrayList<CargoTransfer>();

        try {

            strBuffer = new StringBuffer().append("SELECT *\n"
                    + "                            FROM projetosga.cargo"
                    + "                            WHERE nomecargo = ?");

            pstm = abrirconexao.getConexao().prepareStatement(strBuffer.toString());
            pstm.setObject(1, nomecargo);
            rs = pstm.executeQuery();

            while (rs.next()) {
                CargoTransfer cargoTransfer = new CargoTransfer();
                cargoTransfer.setId(rs.getShort("idcargo"));
                cargoTransfer.setNomecargo(rs.getString("nomecargo"));
                cargoTransfer.setDescricaocargo(rs.getString("descricaocargo"));
                categoriapermissao.add(cargoTransfer);
            }

            return categoriapermissao;

        } catch (SQLException ex) {
            logPrincipal(CargoDAO.class).error(">>>>ERROR AO BUSCAR TODOS OS DADOS DE CARGO(buscarTodosCargos)!!!", ex);
        } finally {
            pstm.close();
            rs.close();
        }
        return null;
    }

    /**
     * Metodo responsavel por buscar pelo nome do cargo
     *
     * @param nomecargo
     * @return boolean
     * @throws java.sql.SQLException
     */
    public List<CargoTransfer> buscarCargo(String nomecargo) throws SQLException {
        List<CargoTransfer> categoriapermissao = new ArrayList<CargoTransfer>();

        try {

            strBuffer = new StringBuffer().append("  SELECT  distinct(permissao.nomepermissao)\n"
                    + "	, cargo.nomecargo\n"
                    + "	, cargo.descricaocargo"
                    + " , cargo.idcargo"
                    + " , cargo.statuscargo\n"
                    + "  FROM   projetosga.cargo cargo\n"
                    + "  INNER JOIN projetosga.cargospermissoes cargospermissoes on (cargospermissoes.idcargo = cargo.idcargo)\n"
                    + "  INNER JOIN projetosga.permissao permissao on (cargospermissoes.idpermissao = permissao.idpermissao)\n"
                    + "  WHERE UPPER(cargo.nomecargo) \n"
                    + "                    LIKE UPPER(?)\n"
                    + "  ORDER BY permissao.nomepermissao");

            pstm = abrirconexao.getConexao().prepareStatement(strBuffer.toString());
            pstm.setObject(1, "%" + nomecargo + "%");
            rs = pstm.executeQuery();

            while (rs.next()) {
                CargoTransfer cargoTransfer = new CargoTransfer();
                cargoTransfer.setId(rs.getShort("idcargo"));
                cargoTransfer.setNomepermissao(rs.getString("nomepermissao"));
                cargoTransfer.setNomecargo(rs.getString("nomecargo"));
                cargoTransfer.setDescricaocargo(rs.getString("descricaocargo"));
                cargoTransfer.setStatuscargo(rs.getString("statuscargo"));
                categoriapermissao.add(cargoTransfer);
            }

            return categoriapermissao;

        } catch (SQLException ex) {
            logPrincipal(CargoDAO.class).error(">>>>ERROR AO BUSCAR O CARGO E PERMISSOES(buscarCargo)!!!", ex);
        } finally {
            pstm.close();
            rs.close();
        }
        return null;
    }

    /**
     * Metodo responsavel por deletar um determinado cargo
     *
     * @param short idcargo
     * @throws java.sql.SQLException
     */
    public boolean deletarCargo(String nomecargo) throws SQLException {
        try {
            strBuffer = new StringBuffer().append("DELETE FROM projetosga.cargo\n"
                    + " WHERE nomecargo=?;");
            pstm = abrirconexao.getConexao().prepareStatement(strBuffer.toString());
            pstm.setObject(1, nomecargo);
            //remova o cargo
            pstm.executeUpdate();
        } catch (SQLException ex) {
            logPrincipal(LocalDAO.class).error(">>>>ERROR AO DELETAR POR CARGO(deletarCargo)", ex);
        } finally {
            pstm.close();
            abrirconexao.fecharConexao();
        }
        return true || false;
    }

}
