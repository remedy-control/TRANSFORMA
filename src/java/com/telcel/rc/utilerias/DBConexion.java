/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.telcel.rc.utilerias;

import com.infomedia.utils.PropertyLoader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import org.apache.log4j.Logger;

/**
 *
 * @author INFOTELCEL
 */
public class DBConexion {
    
    public static final Logger log = Logger.getLogger(DBConexion.class);
    public static final Properties prop = PropertyLoader.load("rctransformabes.properties");
    
    public static final String user = prop.getProperty("rmrcontrol.db.user");
    public static final String pass = prop.getProperty("rmrcontrol.db.pass");
    public static final String url = prop.getProperty("rmrcontrol.db.url");
    
    public Context context;
    public DataSource dataSource;
    public Connection conecta;
    
    public Connection getConecta() {
        return conecta;
    }
    
    public void setConecta(Connection conecta) {
        this.conecta = conecta;
    }

    /**
     * @return @throws SQLException
     * @throws ClassNotFoundException
     * @throws NamingException
     */
    public Connection getConnectDBWeb() throws SQLException {
        //log.info("********************************* getConnectDBWeb");
        conecta = null;
        try {
            context = new InitialContext();
            dataSource = (DataSource) context.lookup("java:/comp/env/rcServiceDeskPool");
            conecta = dataSource.getConnection();
        } catch (Exception e) {
            log.error(e);
        }
        
        return conecta;
    }

    /**
     * @return @throws SQLException
     * @throws ClassNotFoundException
     */
    public Connection getConnectDB() throws SQLException, ClassNotFoundException {     
        //log.info("********************************* getConnectDB");
        Class.forName("oracle.jdbc.driver.OracleDriver");
        conecta = DriverManager.getConnection(url, user, pass);
        if (conecta == null) {
            log.error("No se pudo realizar la conexion plana a Oracle");
        }
        return conecta;
    }
    
}
