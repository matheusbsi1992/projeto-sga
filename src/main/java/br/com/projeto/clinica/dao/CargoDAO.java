/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.projeto.clinica.dao;

import br.com.projeto.clinica.model.Cargo;
import br.com.projeto.clinica.model.Permissao;

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

import javax.persistence.EntityManager;

public class CargoDAO extends UtilDAO {

    /**
     * Metodo responsavel por inserir cargo e as suas permissoes de acesso a
     * cada um dos links
     *
     * @param cargo
     * @return boolean
     */
    public boolean INSERIRCARGO(Cargo cargo) {
        try {

            //---Inicia insercao do Cargo
            StringBuffer strInsercaoCargo = new StringBuffer().append("INSERT INTO cargo(\n"
                    + "	datacadastrocargo, descricaocargo, nomecargo, statuscargo, idclinica)\n"
                    + "	VALUES (?, ?, ?, ?, ?);"
            );
            PreparedStatement pstmInsercaoCargo = Conexao.GETCONEXAO().prepareStatement(strInsercaoCargo.toString());
            int tamCargo = 1;
            pstmInsercaoCargo.setObject(tamCargo++, new DataUtil().GETSQLTIMESTAMP(new DataUtil().DATAHORASRECIFE()));
            pstmInsercaoCargo.setObject(tamCargo++, cargo.getDescricaoCargo().toUpperCase());
            pstmInsercaoCargo.setObject(tamCargo++, cargo.getNomeCargo().toUpperCase());
            pstmInsercaoCargo.setObject(tamCargo++, cargo.getStatusCargo().toUpperCase());
            pstmInsercaoCargo.setObject(tamCargo++, cargo.getClinica().getIdClinica());

            pstmInsercaoCargo.executeUpdate();

            pstmInsercaoCargo.close();
            ///---Encerra insercao do Cargo
            //---RETORNA O ULTIMO CARGO
            ULTIMOCARGO(cargo);
            //---listar as paginas informadas como Permissao
            for (Object idPermissoes : cargo.getPermissao().getDescricaoPermissoes()) {

                //---Obtencao do id sobre o Nome da Permissao
                StringBuffer strIdPermissao = new StringBuffer().append("SELECT idpermissao\n"
                        + "     FROM permissao"
                        + "     WHERE nomepermissao = ?\n"
                );

                PreparedStatement pstmIdPermissao = Conexao.GETCONEXAO().prepareStatement(strIdPermissao.toString());
                pstmIdPermissao.setObject(1, idPermissoes.toString());
                ResultSet resultIdPermissao = pstmIdPermissao.executeQuery();
                //----Encerra o resultado das Permissoes

                //abri uma nova string para a insercao de cargo e permissao
                StringBuffer strCargoePermissao = new StringBuffer().append("INSERT INTO cargo_permissao("
                        + "           idcargo      "
                        + "          ,idpermissao )"
                        + "    VALUES (?"
                        + "          ,?);");
                PreparedStatement pstmcCargoePermissao = Conexao.GETCONEXAO().prepareStatement(strCargoePermissao.toString());

                //validar cargo e permissao para a insercao
                INSERIROUALTERARCARGOEPERMISSAO(resultIdPermissao, cargo, pstmcCargoePermissao);

            }

        } catch (SQLException ex) {
            logPrincipal(CargoDAO.class).error(">>>>ERROR AO INSERIR CARGO(INSERIRCARGO)", ex);
        } finally {
            Conexao.FECHARCONEXAO();
        }
        return true || false;
    }

    /**
     * Metodo responsavel por inserir cargo e as suas permissoes de acesso a
     * cada um dos links do Usuario Principal da Clinica
     *
     * @param cargo
     * @return boolean
     */
    public boolean INSERIRCARGOPERMISSAO(Cargo cargo) {
        try {

            //---Inicia insercao do Cargo
            StringBuffer strInsercaoCargo = new StringBuffer().append("INSERT INTO cargo(\n"
                    + "	datacadastrocargo, descricaocargo, nomecargo, statuscargo, idclinica)\n"
                    + "	VALUES (?, ?, ?, ?, ?);"
            );
            PreparedStatement pstmInsercaoCargo = Conexao.GETCONEXAO().prepareStatement(strInsercaoCargo.toString());
            int tamCargo = 1;
            pstmInsercaoCargo.setObject(tamCargo++, new DataUtil().GETSQLTIMESTAMP(new DataUtil().DATAHORASRECIFE()));
            pstmInsercaoCargo.setObject(tamCargo++, cargo.getDescricaoCargo().toUpperCase());
            pstmInsercaoCargo.setObject(tamCargo++, cargo.getNomeCargo().toUpperCase());
            pstmInsercaoCargo.setObject(tamCargo++, cargo.getStatusCargo().toUpperCase());
            pstmInsercaoCargo.setObject(tamCargo++, cargo.getClinica().getIdClinica());

            pstmInsercaoCargo.executeUpdate();

            pstmInsercaoCargo.close();
            ///---Encerra insercao do Cargo
            //---RETORNA O ULTIMO CARGO
            ULTIMOCARGO(cargo);
            //---listar as paginas informadas como Permissao
            for (Cargo cargoPermissao : LISTARPERMISSOES()) {

                //---Obtencao do id sobre o Nome da Permissao
                StringBuffer strIdPermissao = new StringBuffer().append("SELECT idpermissao\n"
                        + "     FROM permissao"
                        + "     WHERE nomepermissao = ?\n"
                );

                PreparedStatement pstmIdPermissao = Conexao.GETCONEXAO().prepareStatement(strIdPermissao.toString());
                pstmIdPermissao.setObject(1, cargoPermissao.getPermissao().getNomePermissao());
                ResultSet resultIdPermissao = pstmIdPermissao.executeQuery();
                //----Encerra o resultado das Permissoes

                //abri uma nova string para a insercao de cargo e permissao
                StringBuffer strCargoePermissao = new StringBuffer().append("INSERT INTO cargo_permissao("
                        + "           idcargo      "
                        + "          ,idpermissao )"
                        + "    VALUES (?"
                        + "          ,?);");
                PreparedStatement pstmcCargoePermissao = Conexao.GETCONEXAO().prepareStatement(strCargoePermissao.toString());

                //validar cargo e permissao para a insercao
                INSERIROUALTERARCARGOEPERMISSAO(resultIdPermissao, cargo, pstmcCargoePermissao);

            }

        } catch (SQLException ex) {
            logPrincipal(CargoDAO.class).error(">>>>ERROR AO INSERIR CARGO(INSERIRCARGO)", ex);
        }
        return true || false;
    }

    /**
     * Metodo responsavel por inserir todas as Permissoes de acesso a cada um
     * dos links
     */
    public void INSERIRPERMISSAO() {

        //---Inicia insercao da Permissao
        StringBuffer strInsercaoPermissao = new StringBuffer().append("INSERT INTO permissao(\n"
                + "	descricaopermissao, nomepermissao)"
                + "VALUES ('atendimentochamadacliente.xhtml','ATENDIMENTO')\n"
                + ",('atendimentocliente.xhtml','ATENDIMENTO')\n"
                + ",('consultarlocal.xhtml','LOCAL')\n"
                + ",('editarlocal.xhtml','LOCAL')\n"
                + ",('inserirlocal.xhtml','LOCAL')\n"
                + ",('monitortriagemachamadacliente.xhtml','MONITOR')\n"
                + ",('consultarservico.xhtml','SERVIÇO')\n"
                + ",('editarservico.xhtml','SERVIÇO')\n"
                + ",('inserirservico.xhtml','SERVIÇO')\n"
                + ",('consultartriagemalternativa.xhtml','TRIAGEM ALTERNATIVA')\n"
                + ",('editartriagemalternativa.xhtml','TRIAGEM ALTERNATIVA')\n"
                + ",('inserirtriagemalternativa.xhtml','TRIAGEM ALTERNATIVA')\n"
                + ",('consultartriagemachamadacliente.xhtml','TRIAGEM CHAMADACLIENTE')\n"
                + ",('consultartriagemcliente.xhtml','TRIAGEM CHAMADACLIENTE')\n"
                + ",('consultarunidade.xhtml','UNIDADE')\n"
                + ",('editarunidade.xhtml','UNIDADE')\n"
                + ",('inserirunidade.xhtml','UNIDADE')\n"
                + ",('inserircargo.xhtml','CARGO')\n"
                + ",('editarcargo.xhtml','CARGO')\n"
                + ",('consultarcargo.xhtml','CARGO')\n"
                + ",('consultarusuario.xhtml','USUÁRIO')\n"
                + ",('editarusuario.xhtml','USUÁRIO')\n"
                + ",('inserirusuario.xhtml','USUÁRIO')\n"
                + ",('consultarestatisticas.xhtml','ESTATÍSTICA')\n"
                + ",('atendimentochamadacliente.xhtml','PAINEL DE ATENDIMENTO')\n"
                + ",('atendimentovideo.xhtml','PAINEL DE ATENDIMENTO')\n"
                + ",('reiniciarsenhas.xhtml','REINICIAR SENHA'),\n"
                + "('reiniciarsenhasporservico.xhtml','REINICIAR SENHA POR SERVIÇO');");

        try (PreparedStatement pstmInsercaoPermissao = Conexao.GETCONEXAO().prepareStatement(strInsercaoPermissao.toString())) {
            pstmInsercaoPermissao.executeUpdate();
            pstmInsercaoPermissao.close();
        } catch (SQLException ex) {
            logPrincipal(CargoDAO.class).error(">>>>ERROR AO INSERIR PERMISSAO(INSERIRPERMISSAO)", ex);
        }
    }

    private int ULTIMOCARGO(Cargo cargo) {
        //Obtem o Cargo respectivo a clinica
        StringBuffer strObterIdCargo = new StringBuffer().append("SELECT cargo.idcargo\n"
                + "                            FROM cargo cargo"
                + "                            INNER JOIN clinica clinica on (clinica.idclinica = cargo.idclinica)"
                + "                            WHERE cargo.idclinica = ?"
                + "                            ORDER BY idcargo"
                + "                            DESC LIMIT 1;");
        try (PreparedStatement pstmUltimoIdCargo = Conexao.GETCONEXAO().prepareStatement(strObterIdCargo.toString())) {
            ResultSet rsIdCargo = null;
            pstmUltimoIdCargo.setObject(1, cargo.getClinica().getIdClinica());
            rsIdCargo = pstmUltimoIdCargo.executeQuery();
            rsIdCargo.next();
            //seta o valor do Cargo
            cargo.setIdCargo(rsIdCargo.getInt("idcargo"));
            //encerra o pstm e result do  id do cargo
            pstmUltimoIdCargo.close();
            //encerra o resultado 
            rsIdCargo.close();
            ///---Encerra comunicacao ao cargo
        } catch (SQLException ex) {
            logPrincipal(CargoDAO.class).error(">>>>ERROR AO LISTAR CARGODAO(ULTIMOCARGO)", ex);
        }
        return cargo.getIdCargo();
    }

    /**
     * Metodo responsavel por alterar cargo e as suas permissoes de acesso a
     * cada um dos links especificos ao cargo do Usuario
     *
     *
     * @param cargo
     * @param listarpermissoes
     * @return boolean
     *
     */
    public boolean ALTERARCARGO(Cargo cargo, List<String> listarpermissoes) {
        try {
            //editar o cargo 
            StringBuffer strAtualizarCargo = new StringBuffer().append("UPDATE cargo\n"
                    + "   SET  nomecargo      = ?"
                    + "       ,descricaocargo = ?"
                    + "       ,datacadastrocargo = ?"
                    + "       ,statuscargo       = ?"
                    + " WHERE idcargo = ? and idclinica = ?;"
            );
            PreparedStatement pstmInsercaoCargo = Conexao.GETCONEXAO().prepareStatement(strAtualizarCargo.toString());
            int tamCargo = 1;
            pstmInsercaoCargo.setObject(tamCargo++, cargo.getNomeCargo().toUpperCase());
            pstmInsercaoCargo.setObject(tamCargo++, cargo.getDescricaoCargo().toUpperCase());
            pstmInsercaoCargo.setObject(tamCargo++, new DataUtil().GETSQLTIMESTAMP(new DataUtil().DATAHORASRECIFE()));
            pstmInsercaoCargo.setObject(tamCargo++, cargo.getStatusCargo().toUpperCase());
            pstmInsercaoCargo.setObject(tamCargo++, cargo.getIdCargo());
            pstmInsercaoCargo.setObject(tamCargo++, cargo.getClinica().getIdClinica());
            //atualizar na tabela cargo
            pstmInsercaoCargo.executeUpdate();
            //fechar pstmInsercaoCargo
            pstmInsercaoCargo.close();

            //deletar o Cargo junto com as permissaoes
            StringBuffer strDeletarCargoePermissao = new StringBuffer().append("DELETE FROM "
                    + "                                                              cargo_permissao"
                    + "                                                              WHERE idcargo =  ?;");
            PreparedStatement pstmCargoePermissao = Conexao.GETCONEXAO().prepareStatement(strDeletarCargoePermissao.toString());
            pstmCargoePermissao.setObject(1, cargo.getIdCargo());
            //Remova dados 
            pstmCargoePermissao.executeUpdate();
            //Fecha a comunicacao de dados do Cargo junto a Permissao
            pstmCargoePermissao.close();

            //Listar as paginas informadas como permissao
            for (Object idPermissoes : listarpermissoes) {

                //Obtencao do id sobre o nome da permissao
                StringBuffer strIdpermissao = new StringBuffer().append("SELECT "
                        + "     idpermissao"
                        + "     FROM permissao"
                        + "     WHERE nomepermissao = ?"
                );

                PreparedStatement pstmIdpermissao = Conexao.GETCONEXAO().prepareStatement(strIdpermissao.toString());
                pstmIdpermissao.setObject(1, idPermissoes.toString());
                ResultSet resultIdPermissao = pstmIdpermissao.executeQuery();

                //abri uma nova string para uma nova insercao de cargo(s) e permissao/permissoes
                StringBuffer strCargoePermissao = new StringBuffer().append("INSERT INTO cargo_permissao(\n"
                        + "            idpermissao"
                        + "          , idcargo)\n"
                        + "    VALUES (?"
                        + "          ,?);");
                PreparedStatement pstmCargoPermissao = Conexao.GETCONEXAO().prepareStatement(strCargoePermissao.toString());

                //validar cargo e permissao para a atualizacao
                INSERIROUALTERARCARGOEPERMISSAO(resultIdPermissao, cargo, pstmCargoPermissao);

            }

        } catch (SQLException ex) {
            logPrincipal(CargoDAO.class
            ).error(">>>>ERROR AO ALTERAR O CARGO(ALTERARCARGO)", ex);
        } finally {
            Conexao.FECHARCONEXAO();
        }
        return true || false;
    }

    /**
     * Metodo auxiliar, de insercao de cargo e de permissao
     *
     * @param set
     * @param cargo
     * @param pstm
     * @exception SQLException
     */
    private boolean INSERIROUALTERARCARGOEPERMISSAO(ResultSet Rset, Cargo cargo, PreparedStatement pstm) {
        try {
            while (Rset.next()) {
                int idPermissao = (Rset.getInt("idpermissao"));
                //set do update
                int pos = 1;
                pstm.setObject(pos++, cargo.getIdCargo());
                pstm.setObject(pos++, idPermissao);
                //insira na tabela cargopermissoes
                pstm.executeUpdate();
            }
        } catch (SQLException ex) {
            logPrincipal(CargoDAO.class
            ).error(">>>>ERROR AO INSERIR CARGO E PERMISSAO(INSERIROUALTERARCARGOEPERMISSAO(ResultSet set, Cargo cargo, PreparedStatement pstm)", ex);
        }
        return false || true;
    }

    /**
     * Metodo responsavel por listar as permissoes sem deixar de existir
     * repeticoes
     *
     * @return boolean
     */
    public List<Cargo> LISTARPERMISSOES() {
        List<Cargo> permissaos = new ArrayList<Cargo>();

        StringBuffer strPermissao = new StringBuffer().append("SELECT distinct(nomepermissao)\n"
                + "  FROM permissao\n"
                + "  ORDER BY nomepermissao");

        try (PreparedStatement pstmPermissao = Conexao.GETCONEXAO().prepareStatement(strPermissao.toString());) {

            ResultSet rs = pstmPermissao.executeQuery();

            while (rs.next()) {
                Cargo cargo = new Cargo();
                Permissao permissao = new Permissao();
                permissao.setNomePermissao(rs.getString("nomepermissao"));
                cargo.setPermissao(permissao);
                permissaos.add(cargo);
            }

            return permissaos;

        } catch (SQLException ex) {
            logPrincipal(CargoDAO.class
            ).error(">>>>ERROR AO LISTAR NOMES DAS PERMISSOES(listarPermissoes)!!!", ex);
        }
        return null;
    }

    /**
     * Metodo responsavel por buscar as permissoes existentes sem repeticoes
     *
     * @param nomeCargo
     * @param idClinica
     * @param nomecargo
     */
    public List<Cargo> LISTARPERMISSOES(String nomeCargo, int idClinica) {
        List<Cargo> permissaos = new ArrayList<Cargo>();
        try {

            StringBuffer strPermissao = new StringBuffer().append("SELECT distinct(permissao.nomepermissao)\n"
                    + "  FROM  cargo cargo\n"
                    + "  INNER JOIN cargo_permissao cargo_permissao on (cargo_permissao.idcargo = cargo.idcargo)\n"
                    + "  INNER JOIN permissao permissao on (cargo_permissao.idpermissao = permissao.idpermissao)"
                    + "  INNER JOIN clinica clinica on (clinica.idclinica = cargo.idclinica)"
                    + "  WHERE (cargo.nomecargo = ? and cargo.idclinica = ?)\n"
                    + "  ORDER BY permissao.nomepermissao");

            PreparedStatement pstmCargoePermissao = Conexao.GETCONEXAO().prepareStatement(strPermissao.toString());
            pstmCargoePermissao.setObject(1, nomeCargo);
            pstmCargoePermissao.setObject(2, idClinica);

            ResultSet rs = pstmCargoePermissao.executeQuery();

            while (rs.next()) {
                Cargo cargo = new Cargo();
                Permissao permissao = new Permissao();
                permissao.setNomePermissao(rs.getString("nomepermissao"));
                cargo.setPermissao(permissao);
                permissaos.add(cargo);
            }

            return permissaos;

        } catch (SQLException ex) {
            logPrincipal(CargoDAO.class
            ).error(">>>>ERROR AO LISTAR NOMES DAS PERMISSOES(LISTARPERMISSOES)!!!", ex);
        }
        return null;
    }

    /**
     * Metodo responsavel por listar os dados do cargo e permissao
     *
     * @param idClinica
     * @return boolean
     *
     */
    public List<Cargo> LISTARCARGO(int idClinica) {
        List<Cargo> cargoPermissao = new ArrayList<Cargo>();

        try {

            StringBuffer strCargoPermissao = new StringBuffer().append("SELECT  distinct(cargo.nomecargo)\n"
                    + "                                            ,cargo.descricaocargo\n"
                    + "                                            ,cargo.idcargo"
                    + "                                            ,cargo.statuscargo\n"
                    + "                                            ,permissao.nomepermissao\n"
                    + "                                            FROM   cargo cargo\n"
                    + "                                            INNER JOIN cargo_permissao cargo_permissao on (cargo_permissao.idcargo = cargo.idcargo)\n"
                    + "                                            INNER JOIN permissao permissao on (permissao.idpermissao = cargo_permissao.idpermissao)\n"
                    + "                                            INNER JOIN clinica clinica on (clinica.idclinica = cargo.idclinica)\n"
                    + "                                            WHERE cargo.idclinica = ?"
                    + "                                            ORDER BY cargo.nomecargo");

            PreparedStatement pstmCargoePermissao = Conexao.GETCONEXAO().prepareStatement(strCargoPermissao.toString());
            pstmCargoePermissao.setInt(1, idClinica);
            ResultSet rs = pstmCargoePermissao.executeQuery();

            while (rs.next()) {
                Cargo cargo = new Cargo();
                Permissao permissao = new Permissao();
                permissao.setNomePermissao(rs.getString("nomepermissao"));
                cargo.setPermissao(permissao);
                cargo.setIdCargo(rs.getInt("idcargo"));
                cargo.setNomeCargo(rs.getString("nomecargo"));
                cargo.setDescricaoCargo(rs.getString("descricaocargo"));
                cargo.setStatusCargo(rs.getString("statuscargo"));

                cargoPermissao.add(cargo);
            }

            return cargoPermissao;

        } catch (SQLException ex) {
            logPrincipal(CargoDAO.class
            ).error(">>>>ERROR AO LISTAR TODOS OS DADOS DE CARGO E PERMISSOES(LISTARCARGO)!!!", ex);
        }
        return null;
    }

    /**
     * Metodo responsavel por listar os dados do cargo vinculado com o usuario
     *
     * @param idClinica
     * @return boolean
     * @throws java.sql.SQLException
     */
    public List<Cargo> LISTARTODOSCARGOS(int idClinica) throws SQLException {
        List<Cargo> cargos = new ArrayList<Cargo>();

        try {

            StringBuffer strCargo = new StringBuffer().append("SELECT distinct(cargo.nomecargo),\n"
                    + "                           cargo.idcargo,\n"
                    + "                           cargo.statuscargo,\n"
                    + "                           cargo.descricaocargo\n"
                    + "                           FROM cargo cargo\n"
                    + "                           INNER JOIN clinica clinica (clinica.idclinica = cargo.idclinica)"
                    + "                           WHERE cargo.statuscargo = 'A' and cargo.idclinica = ?\n"
                    + "                           ORDER BY cargo.nomecargo;");

            PreparedStatement pstmCargo = Conexao.GETCONEXAO().prepareStatement(strCargo.toString());
            pstmCargo.setObject(1, idClinica);
            ResultSet rs = pstmCargo.executeQuery();

            while (rs.next()) {
                Cargo cargoTransfer = new Cargo();

                cargoTransfer.setStatusCargo(rs.getString("statuscargo"));
                cargoTransfer.setIdCargo(rs.getShort("idcargo"));
                cargoTransfer.setNomeCargo(rs.getString("nomecargo"));
                cargoTransfer.setDescricaoCargo(rs.getString("descricaocargo"));

                cargos.add(cargoTransfer);
            }

            return cargos;

        } catch (SQLException ex) {
            logPrincipal(CargoDAO.class
            ).error(">>>>ERROR AO LISTAR TODOS OS DADOS DE CARGO(LISTARTODOSCARGOS)!!!", ex);
        }
        return null;
    }

    /**
     * Metodo responsavel por obter o id e nome do cargo com a verificacao se
     * existi retornando verdadeiro ou falso
     *
     *
     * @param idCargo
     * @param idClinica
     * @param nomeCargo
     * @return boolean
     */
    public boolean IDNOMEDOCARGOCONFERE(int idCargo, int idClinica, String nomeCargo) {
        return BUSCARTODOSCARGOS(nomeCargo, idClinica).stream().anyMatch(c
                -> c.getNomeCargo().equalsIgnoreCase(nomeCargo) && c.getClinica().getIdClinica() == idClinica && c.getIdCargo() == idCargo);
    }

    /**
     * Metodo responsavel por verificar se existi cargo
     *
     * @param nomeCargo
     * @param idClinica
     * @return boolean
     */
    public boolean EXISTENOMECARGO(String nomeCargo, int idClinica) {
        return BUSCARTODOSCARGOS(nomeCargo, idClinica).stream().anyMatch(c
                -> c.getNomeCargo().equalsIgnoreCase(nomeCargo) && c.getClinica().getIdClinica() == idClinica);
    }

    /**
     * Metodo responsavel por buscar os dados do cargo vinculado com o usuario
     *
     * @param nomeCargo
     * @param idCLinica
     * @return boolean
     *
     */
    public List<Cargo> BUSCARTODOSCARGOS(String nomeCargo, int idCLinica) {
        List<Cargo> cargos = new ArrayList<Cargo>();

        try {

            StringBuffer strCargo = new StringBuffer().append("SELECT *\n"
                    + "                            FROM cargo cargo"
                    + "                            INNER JOIN clinica clinica (clinica.idclinica on cargo.idclinica) "
                    + "                            WHERE cargo.nomecargo = ? and cargo.idclinica = ?");

            PreparedStatement pstmCargo = Conexao.GETCONEXAO().prepareStatement(strCargo.toString());
            pstmCargo.setObject(1, nomeCargo);
            pstmCargo.setObject(2, idCLinica);
            ResultSet rs = pstmCargo.executeQuery();

            while (rs.next()) {
                Cargo cargoTransfer = new Cargo();
                cargoTransfer.setIdCargo(rs.getInt("idcargo"));
                cargoTransfer.setNomeCargo(rs.getString("nomecargo"));
                cargoTransfer.setDescricaoCargo(rs.getString("descricaocargo"));
                cargos.add(cargoTransfer);
            }

            return cargos;

        } catch (SQLException ex) {
            logPrincipal(CargoDAO.class
            ).error(">>>>ERROR AO BUSCAR TODOS OS DADOS DE CARGO(BUSCARTODOSCARGOS)!!!", ex);
        }
        return null;
    }

    /**
     * Metodo responsavel por buscar os dados do cargo vinculado com o usuario
     *
     * @param nomeCargo
     * @param idClinica
     * @return boolean
     */
    public Optional<Cargo> BUSCADECARGO(String nomeCargo, int idClinica) {
        return BUSCARTODOSCARGOS(nomeCargo, idClinica).stream().filter(c
                -> c.getNomeCargo().equalsIgnoreCase(nomeCargo) && c.getClinica().getIdClinica() == idClinica).findFirst();
    }

    /**
     * Metodo responsavel por buscar pelo nome do cargo
     *
     * @param nomeCargo
     * @param idClinica
     * @return boolean
     */
    public List<Cargo> BUSCARCARGO(String nomeCargo, int idClinica) {
        List<Cargo> cargoPermissao = new ArrayList<Cargo>();

        try {

            StringBuffer strCargoePermissao = new StringBuffer().append("SELECT  distinct(permissao.nomepermissao)\n"
                    + "	, cargo.nomecargo\n"
                    + "	, cargo.descricaocargo"
                    + " , cargo.idcargo"
                    + " , cargo.statuscargo\n"
                    + "  FROM  cargo cargo\n"
                    + "  INNER JOIN cargo_permissao cargo_permissao on (cargo_permissao.idcargo = cargo.idcargo)\n"
                    + "  INNER JOIN permissao permissao on (cargo_permissao.idpermissao = permissao.idpermissao)"
                    + "  INNER JOIN clinica clinica     on (clinica.idclinica = cargo.idclinica )   \n"
                    + "  WHERE UPPER(cargo.nomecargo) \n"
                    + "        LIKE UPPER(?) AND cargo.idclinica = ? \n"
                    + "  ORDER BY permissao.nomepermissao");

            PreparedStatement pstmCargoePermissao = Conexao.GETCONEXAO().prepareStatement(strCargoePermissao.toString());
            pstmCargoePermissao.setObject(1, "%" + nomeCargo + "%");
            pstmCargoePermissao.setObject(2, idClinica);
            ResultSet rs = pstmCargoePermissao.executeQuery();

            while (rs.next()) {
                Cargo cargo = new Cargo();
                cargo.setIdCargo(rs.getInt("idcargo"));
                Permissao permissao = new Permissao();
                permissao.setNomePermissao(rs.getString("nomepermissao"));
                cargo.setPermissao(permissao);
                cargo.setNomeCargo(rs.getString("nomecargo"));
                cargo.setDescricaoCargo(rs.getString("descricaocargo"));
                cargo.setStatusCargo(rs.getString("statuscargo"));
                cargoPermissao.add(cargo);
            }

            return cargoPermissao;

        } catch (SQLException ex) {
            logPrincipal(CargoDAO.class
            ).error(">>>>ERROR AO BUSCAR O CARGO E PERMISSOES(BUSCARCARGO)!!!", ex);
        }
        return null;
    }

    /**
     * Metodo responsavel por deletar um determinado cargo
     *
     * @param idCargo
     * @return
     */
    public boolean DELETARCARGO(int idCargo) {
        EntityManager em = JpaUtil.getEntityManager();
        Cargo c = em.find(Cargo.class,
                idCargo);
        //Clinica c = IDCLINICACONFERE(idClinica).get();
        try {
            em.getTransaction().begin();
            em.remove(c);
            em.getTransaction().commit();
        } catch (Exception ex) {
            logPrincipal(CargoDAO.class
            ).error(">>>>ERROR AO REMOVER (DELETARCARGO)", ex);
            em.getTransaction().rollback();
        } finally {
            em.close();
        }
        return true || false;
    }

}
