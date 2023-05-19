/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.sga.conexaofactory;

import br.com.sga.dao.DAO;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import javax.servlet.ServletContext;
//import org.apache.logging.log4j.LogManager;
//import org.apache.logging.log4j.Logger;

/**
 *
 * @author Jandisson
 */
public final class Conexao{

    private String driver = "org.postgresql.Driver";
    private String url = "jdbc:postgresql://localhost:5432/sga?currentSchema=projetosga";
    private String user = "postgres";
    private String password = "hnsc!=2019";
    private Connection conexao = null;
    private DAO daoLogger = null;
    public Connection getConexao() {

        try {
            Class.forName(driver);
        } catch (ClassNotFoundException ex) {
            daoLogger.logPrincipal(Conexao.class.getClass());
        }
        
        try {
            conexao = DriverManager.getConnection(url, user, password);
        } catch (SQLException ex) {
            daoLogger.logPrincipal(Conexao.class.getClass());
        }
        return conexao;

    }
    
    public void fecharConexao() throws SQLException{
        conexao.close();
    }

//    //cria variavel tipo DataSource
//    private static HikariDataSource hkdatasource = new HikariDataSource();
//
// 
//
//    static {
//        HikariConfig hkconfig = new HikariConfig();
// 
//        hkconfig.setJdbcUrl("jdbc:postgresql://localhost:5432/sga?currentSchema=projetosga");
//        hkconfig.setUsername("postgres");
//        hkconfig.setPassword("hnsc!=2019");
//        hkconfig.setDriverClassName("org.postgresql.Driver");
//        hkconfig.setMaxLifetime(30000);
//        hkconfig.setMinimumIdle(1);
//        hkconfig.setMaximumPoolSize(100);
//        hkconfig.addDataSourceProperty("cachePrepStmts", "true");
//        hkconfig.addDataSourceProperty("prepStmtCacheSize", "250");
//        hkconfig.addDataSourceProperty("prepStmtCacheSqlLimit", "2048");
//        hkconfig.setConnectionTestQuery("SELECT 1");
//
//        hkdatasource = new HikariDataSource(hkconfig);
//
//    }
//
//    /**
//     * Obtem uma conexao do datasource
//     *
//     * @return A conexao para acesso a base de dados
//     * @throws SQLException Lancada caso nao seja possivel obter a conexao
//     */
//    public static Connection getConexao() {
//        try {
//            return hkdatasource.getConnection();
//        } catch (SQLException ex) {
//            logPrincipal(Conexao.class).error(">>>>ERROR AO SE CONECTAR", ex);
//        }
//        return null;
//    }
//
//    /**
//     * Fecha todas as conexoes abertas no pool
//     *
//     */
//    public static void fecharConexao() {
//        if (hkdatasource != null && hkdatasource instanceof HikariDataSource) {
//            ((HikariDataSource) hkdatasource).close();
//        }
//        hkdatasource.close();
//    }
//
//    /**
//     * Não commita os dados
//     *
//     * @param trueboolean
//     * @throws java.sql.SQLException
//     */
//    public static void conectionCommitOrRoolback(boolean trueboolean) throws SQLException {
//        if (trueboolean == false) {
//            hkdatasource.setAutoCommit(false);
//        }
//    }

}