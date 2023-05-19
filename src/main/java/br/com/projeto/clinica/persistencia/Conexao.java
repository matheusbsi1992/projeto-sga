/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.projeto.clinica.persistencia;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import static br.com.projeto.clinica.util.UtilDAO.logPrincipal;
import com.zaxxer.hikari.HikariDataSource;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Conexao {

    private static HikariDataSource hkdatasource = new HikariDataSource();

    static {
        HikariConfig hkconfig = new HikariConfig();

        hkconfig.setJdbcUrl("jdbc:postgresql://localhost:5432/bancoteste");
        hkconfig.setUsername("postgres");
        hkconfig.setPassword("postgres");
        hkconfig.setDriverClassName("org.postgresql.Driver");
        hkconfig.setMaxLifetime(30000*2);
        hkconfig.setMinimumIdle(1);
        hkconfig.setMaximumPoolSize(1000);
        //hkconfig.addDataSourceProperty("cachePrepStmts", "true");
        //hkconfig.addDataSourceProperty("prepStmtCacheSize", "250");
        //hkconfig.addDataSourceProperty("prepStmtCacheSqlLimit", "2048");
        //hkconfig.setConnectionTestQuery("SELECT 1");

        hkdatasource = new HikariDataSource(hkconfig);

    }

    /**
     * Obtem uma conexao do datasource
     *
     * @return A conexao para acesso a base de dados
     * @throws SQLException Lancada caso nao seja possivel obter a conexao
     */
    public static Connection GETCONEXAO() {
        try {
            return hkdatasource.getConnection();
        } catch (SQLException ex) {
            logPrincipal(Conexao.class).error(">>>>ERROR DE DADOS (Conexao)", ex);
        }
        return null;
    }

    public static void ROLLBACK() {
        try {
            GETCONEXAO().rollback();
        } catch (SQLException exRollback) {
            logPrincipal(Conexao.class).error(">>>>ERROR DE DADOS (Conexao)", exRollback);
        }
    }

    /**
     * Fecha todas as conexoes abertas no pool
     *
     */
    public static void FECHARCONEXAO() {
        if (hkdatasource != null && hkdatasource instanceof HikariDataSource) {
            ((HikariDataSource) hkdatasource).close();
        }
        hkdatasource.close();
    }

}
