/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.telcel.rc.utilerias;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import org.apache.log4j.Logger;

/**
 *
 * @author AMCruz
 */
public class DBConsultas {
    public static final Logger log = Logger.getLogger(DBConsultas.class);

    public StringBuilder resultaString = new StringBuilder();
    public StringBuilder resultaStringcasdm = new StringBuilder();
    public List<String> ListResult;
    public int maximo;
    public boolean datosOk;
    public Statement stDB;
    public ResultSet rtDB;

    DBConexion conDB = new DBConexion();
    Connection conn;

    public boolean isDatosOk() {
        return datosOk;
    }

    public void setDatosOk(boolean datosOk) {
        this.datosOk = datosOk;
    }

    public Statement getStDB() {
        return stDB;
    }

    public void setStDB(Statement stDB) {
        this.stDB = stDB;
    }

    public ResultSet getRtDB() {
        return rtDB;
    }

    public void setRtDB(ResultSet rtDB) {
        this.rtDB = rtDB;
    }

    public List<String> getListResult() {
        return ListResult;
    }

    public void setListResult(List<String> ListResult) {
        this.ListResult = ListResult;
    }

    public StringBuilder getResultaString() {
        return resultaString;
    }

    public void setResultaString(StringBuilder resultaString) {
        this.resultaString = resultaString;
    }

    public StringBuilder getResultaStringcasdm() {
        return resultaStringcasdm;
    }

    public void setResultaStringcasdm(StringBuilder resultaStringcasdm) {
        this.resultaStringcasdm = resultaStringcasdm;
    }

    public int getMaxValue() 
            throws ClassNotFoundException {
        try {
            maximo = 0;
            conn = conDB.getConnectDB();
            stDB = conn.createStatement();
            rtDB = stDB.executeQuery("SELECT MAX(CLAVE)+1 FROM FOLIO_RCBES");
            if (rtDB.next()) {
                maximo = rtDB.getInt("MAX(CLAVE)+1");
            }
            rtDB.close();
            stDB.close();
            conn.close();
        } catch (SQLException sqlEXC) {
            log.error("Error durante la consulta: " +sqlEXC);
            datosOk = false;
        }
        //log.info(maximo);
        return maximo;
    }
    
    public ArrayList<String> getValues(String incRMD)
            throws ClassNotFoundException{
        ArrayList<String> res = new ArrayList<>();
        int idClv = 0;
        try{
            conn = conDB.getConnectDB();
            stDB = conn.createStatement();
            rtDB = stDB.executeQuery("SELECT CLAVE, FOLIO_BES, FECHA_CREACION "
                    + "FROM INC_FOLIOS_BES "
                    + "WHERE FOLIO_REMEDY='" +incRMD+ "'");
            if (rtDB.next()) {
                idClv = rtDB.getInt("CLAVE");
                res.add(Integer.toString(idClv));
                res.add(rtDB.getString("FOLIO_BES"));
                res.add(rtDB.getString("FECHA_CREACION"));
            }
            rtDB.close();
            stDB.close();
            conn.close();
        }catch(SQLException ex){
            log.info(ex);
            datosOk = false;
        }
        
        return res;
    }
    
    
    public boolean InsertFolioDB(int piFolio, int fkFolio, String psBestt, String psIncidente, String psComentario, String psEstatus ) 
            throws ClassNotFoundException {
        int update = -1;
        try {
            conn = conDB.getConnectDB();
            PreparedStatement vpStatement = conn.prepareStatement("INSERT INTO FOLIO_RCBES (CLAVE, INC_CLAVE, FOLIO_BES, FOLIO_RMD, COM_INC_RMD, EST_INC_RMD) VALUES(?,?,?,?,?,?)");
            vpStatement.setInt(1, piFolio);
            vpStatement.setInt(2, fkFolio);
            vpStatement.setString(3, psBestt);
            vpStatement.setString(4, psIncidente);
            vpStatement.setString(5, psComentario);
            vpStatement.setString(6, psEstatus);
            update = vpStatement.executeUpdate();
            log.info("Resultado del Update: " +update);
        } catch (SQLException exc) {
            datosOk = false;
            log.error("OCURRIO UN ERROR INTERNO AL TRATAR DE GUARDAR EL FOLIO FAVOR DE REPORTARLO CON EL ADMINISTRADOR DEL SISTEMA");
            log.error(exc.getMessage());
        }
        return datosOk;
    }
    
    
    
}
