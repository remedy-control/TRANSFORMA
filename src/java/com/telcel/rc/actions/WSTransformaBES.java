/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
* and open the template in the editor.
*/

package com.telcel.rc.actions;

import com.telcel.rc.utilerias.DBConsultas;
import org.apache.log4j.Logger;
import java.util.ArrayList;
import java.util.logging.Level;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;
import javax.xml.datatype.DatatypeConfigurationException;
/**
 *
 * @author GCaballero
 */
@WebService(serviceName = "WSTransformaBES",
        portName = "TroubleTicketService",
        targetNamespace = "http://www.amx.com.mx/mexico/telcel/di/sds/bes/tt/troubleticketservice"
)
public class WSTransformaBES {
    DBConsultas voConsulta = new DBConsultas();
    ConsumirTT voConsumirTT = new ConsumirTT();
    String estatus = "";
    String psStatus = "";
    public static final org.apache.log4j.Logger log = org.apache.log4j.Logger.getLogger(WSTransformaBES.class);
    /**
     * Web service operation
     * @param folioInc
     * @param ci
     * @param comentario
     * @param estatus
     * @return
     */
    @WebMethod(operationName = "updateTT")
    public String updateTT(
            @WebParam(name = "folioInc") String folioInc,
            @WebParam(name = "ci") String ci,
            @WebParam(name = "comentario") String comentario,
            @WebParam(name = "estatus") String estatus) {
        String vsRes="";
        ArrayList<String> bdRes;
        int vimax;
        int virel;
        String folioBES;
        try{
            vimax = voConsulta.getMaxValue();
            
            log.info(vimax);
            log.info("Folio del INC: " + folioInc);
            log.info("Comentario del INC: " + comentario);
            log.info("status del INC: " + estatus);
            
            bdRes = voConsulta.getValues(folioInc);
            if (bdRes.isEmpty()){
                log.error("No existe un folio de BES relacionado al Incidente en Remedy");
                vsRes="No existe un folio de BES relacionado al Incidente en Remedy";
            }else{
                virel = Integer.parseInt(bdRes.get(0));
                folioBES = bdRes.get(1);
                
                log.info("Ultimo registro en la base de datos "+vimax);
                log.info("Folio de BES TT: "+ folioBES);
                log.info("Folio de Remedy: "+ folioInc);
                if (comentario.replaceAll(" ", "").toLowerCase().trim().equals("cancelado")){
                    comentario = "EL INCIDENTE HA SIDO CANCELADO SIN DESCRIPCIÓN ESCRITA";
                }
                log.info("Comentario: "+ comentario);
                log.info("Estatus: "+ estatus);
                
                switch(estatus){
                    case "Resolved":
                        this.estatus = "Resuelto";
                        this.psStatus = "1";
                        break;
                    case "Cancelled":
                        this.estatus = "Cancelado";
                        this.psStatus = "5";
                        break;
                    default:
                        this.estatus = "ERROR";
                        this.psStatus = "NA";
                        break;
                }
                
                vsRes=voConsumirTT.enviarTT(vimax, folioBES, folioInc, ci, comentario, this.psStatus);
                
                //Crear registro en la base de datos de REMEDY-CONTROL
                log.info("Resultado de la operación: " +vsRes);
                log.error("Longitud de la cadena: "+comentario.length());
                if (comentario.length() > 4000) {
                    comentario = comentario.substring(0, 3599);
                    log.error("Longitud de la cadena modificada: "+comentario.length());
                }
                voConsulta.InsertFolioDB(vimax, virel, folioBES, folioInc, comentario, this.estatus);
            }
        }catch(ClassNotFoundException | DatatypeConfigurationException voEx){
            log.error(voEx);
        }
        return vsRes;
    }
}