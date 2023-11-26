/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
* and open the template in the editor.
*/

package com.telcel.rc.actions;

import com.infomedia.utils.PropertyLoader;
import com.telcel.rc.TransformaBES.ControlDataRequestHeaderType;
import com.telcel.rc.TransformaBES.ControlDataResponseHeaderType;
import com.telcel.rc.TransformaBES.DetailFailType;
import com.telcel.rc.TransformaBES.DetailResponseType;
import com.telcel.rc.TransformaBES.ErrorType;
import com.telcel.rc.TransformaBES.GeneralException;
import com.telcel.rc.TransformaBES.TroubleTicketService;
import com.telcel.rc.TransformaBES.TroubleTicketServiceException;
import com.telcel.rc.TransformaBES.TroubleTicketServiceHttpService;
import com.telcel.rc.TransformaBES.UpdateTTPetType;
import com.telcel.rc.TransformaBES.UpdateTTRespType;
import java.util.Arrays;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;
import javax.xml.ws.BindingProvider;
import javax.xml.ws.Holder;
import javax.xml.ws.handler.MessageContext;
import org.apache.log4j.Logger;

/**
 *
 * @author GCaballero
 */
public class ConsumirTT {
    public static final Logger log = Logger.getLogger(ConsumirTT.class);
    public static final Properties prop = PropertyLoader.load("rctransformabes.properties");
    
    public static final String usuario = prop.getProperty("usuarioBES");
    public static final String contraseña = prop.getProperty("claveBES");
    
    public static final Response result = new Response();
    
    public String enviarTT(int piFolio, String psFolioBES, String psIncidente, String psCI, String psComentario, String psEstatus) throws DatatypeConfigurationException{
        log.info("ENVIANDO TT ************");
        log.info("Folio: "+piFolio);
        log.info("BES TT: "+psFolioBES);
        log.info("Incidente: "+psIncidente);
        log.info("CI asociado: "+psCI);
        switch (Integer.parseInt(psEstatus)) {
            case 1: log.info("Estatus: Resuelto = " + psEstatus);
                break;
            case 5: log.info("Estatus: Cancelado = " + psEstatus);
                break;
            default:
                break;
        }
        log.info("Comentario: "+ psComentario);
        log.info("Usuario: " +usuario);
        log.info("Password: "+contraseña);
       
        Date date = new Date();
        GregorianCalendar calendar = new GregorianCalendar();
        calendar.setTime(date);
        XMLGregorianCalendar requestDate = DatatypeFactory.newInstance().newXMLGregorianCalendar(calendar);
        log.info("RequestDate: " +requestDate);
        
        String vsRes="";
        //Objeto tipo ControlDataRequestHeaderType
        ControlDataRequestHeaderType cd = new ControlDataRequestHeaderType();
        cd.setVersion("1.0.0");
        cd.setMessageUUID(piFolio +"-"+ psIncidente);
        cd.setRequestDate(requestDate);
        cd.setSendBy("prodTelcelBES01");
        //cd.setSendBy("preprodTelcelBES01");
        //Objeto tipo UpdateTTPetType
        UpdateTTPetType upt = new UpdateTTPetType();
        upt.setServReqSerialNo(psFolioBES);
        upt.setExternalSystem("1");
        upt.setCI(psCI);
        upt.setTtDescription(psComentario);
        upt.setExternalSystemCaseID(psIncidente);
        upt.setOperation(psEstatus);
    //    upt.setHandlingrole("20190723045556");
        upt.setHandlingrole("");
        //Llamada del servicio web TroubleTicketTT y de la operación UpdateTT
        try{
            updateTT(cd, upt);
            vsRes="Operación exitosa. Detalle en los logs del sistema";
            //ControlData - Detalle de la respuesta del BUS de TELCEL ESB
            ControlDataResponseHeaderType srpd = result.getResponseControlData().value;
            //DetailResponse - Detalle de la respuesta del WS de Huawei BES TT
            DetailResponseType drt = result.getDetailResponse().value;
            //UpdateTTRespType - No funcional
            UpdateTTRespType uttr = result.getUpdateTTResponse().value;
            
            //Recuperar respuesta exitosa de Telcel ESB
            result.setCdResponseDate(srpd.getResponseDate().toString());
            result.setCdResultCode(srpd.getResultCode());
            result.setCdResultDesc(srpd.getResultDesc());
            log.info("Operacion exitosa ESB:\n"
                    + "Fecha de respuesta: " + result.getCdResponseDate() + "\n"
                    + "Detalle: " + result.getCdResultCode() + ". " 
                    + result.getCdResultDesc());
            /*System.out.println("Operacion exitosa ESB:\n"
                    + "Fecha de respuesta: " + result.getCdResponseDate() + "\n"
                    + "Detalle: " + result.getCdResultCode() + ". " 
                    + result.getCdResultDesc());*/
            
            //Recuperar respuesta de Huawei BES TT
            result.setDrCode(drt.getCode());
            result.setDrDescription(drt.getDescription());
            result.setDrActor(drt.getActor());
            log.info("Operacion exitosa BES TT, actor:" + result.getDrActor() + "\n"
                    + "Codigo: " + result.getDrCode() + "\n"
                    + "Detalle: " + result.getDrDescription());
            /*System.out.println("Operacion exitosa BES TT, actor: " + result.getDrActor() + "\n"
                    + "Codigo: " + result.getDrCode() + "\n"
                    + "Detalle: " + result.getDrDescription());*/
        }catch(GeneralException gEx){
            //Clase padre de Excepcion del BUS ESB
            TroubleTicketServiceException tts = gEx.getFaultInfo();
            //ControlData - Detalle del error del BUS de Telcel ESB
            ControlDataResponseHeaderType rpd = tts.getControlData();
            //DetailFail - Detalle del error o errores del WS de Huawei BES TT
            DetailFailType dft = tts.getDetailFail();
            
            //Recuperar errores de Telcel ESB
            result.setExcdResponseDate(rpd.getResponseDate().toString());
            result.setExcdResultCode(rpd.getResultCode());
            result.setExcdResultDesc(rpd.getResultDesc());
            log.error("Error de consumo ESB:\n"
                    + "Fecha de respuesta: " + result.getExcdResponseDate() + "\n"
                    + "Detalle: " + result.getExcdResultCode() + ". " 
                    + result.getExcdResultDesc());
            /*System.out.println("Error de consumo ESB:\n"
                    + "Fecha de respuesta: " + result.getExcdResponseDate() + "\n"
                    + "Detalle: " + result.getExcdResultCode() + ". " 
                    + result.getExcdResultDesc());*/
            //Recuperar errores de Huawei BES TT
            result.setDfErrorList(dft.getErrors());
            log.error("Error de consumo BES TT:");
            //System.out.println("Error de consumo BES TT:");
            if (result.getDfErrorList().size() > 1){
                int cont=1;
                log.error("Multiples errores de BES TT encontrados: ");
                //System.out.println("Multiples errores de BES TT encontrados: ");
                for(ErrorType et:result.getDfErrorList()){
                    log.error("Error #" + cont + ":\n"
                            + "Codigo: " + et.getCode() + "\n"
                            + "Nivel de impacto: " + et.getSeverityLevel() + "\n"
                            + "Detalle: " + et.getDescription());
                    /*System.out.println("Error #" + cont + ":\n"
                            + "Codigo: " + et.getCode() + "\n"
                            + "Nivel de impacto: " + et.getSeverityLevel() + "\n"
                            + "Detalle: " + et.getDescription());*/
                    cont++;
                }
            }else{
                result.setEtCode(result.getDfErrorList().get(0).getCode());
                result.setEtSeverityLevel(Integer.toString(result.getDfErrorList().get(0).getSeverityLevel()));
                result.setEtDescription(result.getDfErrorList().get(0).getDescription());
                log.error("Codigo: " + result.getEtCode() + "\n"
                        + "Nivel de impacto: " + result.getEtSeverityLevel() + "\n"
                        + "Detalle: " + result.getEtDescription());
                /*System.out.println("Codigo: " + result.getEtCode() + "\n"
                        + "Nivel de impacto: " + result.getEtSeverityLevel() + "\n"
                        + "Detalle: " + result.getEtDescription());*/
            }
            vsRes="Error. Durante la ejecución, detalle en los logs del sistema";
        }
        return vsRes;
    }

    private static void updateTT(ControlDataRequestHeaderType controlData, UpdateTTPetType updateTT) throws GeneralException {
        
        TroubleTicketServiceHttpService service = new TroubleTicketServiceHttpService();
        TroubleTicketService port = service.getTroubleTicketServiceHttpPort();
        BindingProvider bp = (BindingProvider) port;
        Map<String, List<String>> requestHeaders = new HashMap<>();
        requestHeaders.put("Bes-usuario", Arrays.asList("remedy"));
        requestHeaders.put("Bes-password", Arrays.asList("remedy"));
        bp.getRequestContext().put(MessageContext.HTTP_REQUEST_HEADERS, requestHeaders);
        
        final Holder<ControlDataResponseHeaderType> responseControlData = new Holder<ControlDataResponseHeaderType>();
        final Holder<DetailResponseType> detailResponse = new Holder<DetailResponseType>();
        final Holder<UpdateTTRespType> updateTTResponse = new Holder<UpdateTTRespType>();
        port.updateTT(controlData, updateTT, responseControlData, detailResponse, updateTTResponse);
        //Recuperar contenedores de respuesta
        result.setResponseControlData(responseControlData);
        result.setDetailResponse(detailResponse);
        result.setUpdateTTResponse(updateTTResponse);
    }
    
}
