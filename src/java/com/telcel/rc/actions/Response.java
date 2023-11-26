/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.telcel.rc.actions;

import com.telcel.rc.TransformaBES.ControlDataResponseHeaderType;
import com.telcel.rc.TransformaBES.DetailResponseType;
import com.telcel.rc.TransformaBES.ErrorType;
import com.telcel.rc.TransformaBES.UpdateTTRespType;
import java.util.List;
import javax.xml.ws.Holder;

/**
 *
 * @author GCaballero
 */
public class Response {
    
    //CONTENEDORES DEL ERROR
    private Holder<ControlDataResponseHeaderType> responseControlData;
    private Holder<DetailResponseType> detailResponse;
    private Holder<UpdateTTRespType> updateTTResponse;
    //VARIABLES DE RESPUESTA EXITOSA DEL BUS ESB
    private String cdResultCode;
    private String cdResultDesc;
    private String cdResponseDate;
    //VARIABLES DE RESPUESTA EXITOSA DE HUAWEI BESTT
    private String drCode;
    private String drDescription;
    private String drActor;
    //VARIABLES DE RESPUESTA ERRONEA DEL BUS ESB
    private String excdResultCode;
    private String excdResultDesc;
    private String excdResponseDate;
    //VARIABLES DE RESPUESTA ERRONEA DE HUAWEI BESTT
    private List<ErrorType> dfErrorList;
    private String etCode;
    private String etSeverityLevel;
    private String etDescription;
    //MENSAJE 
    private String mensaje;
    

    /**
     * @return the cdResultCode
     */
    public String getCdResultCode() {
        return cdResultCode;
    }

    /**
     * @param cdResultCode the cdResultCode to set
     */
    public void setCdResultCode(String cdResultCode) {
        this.cdResultCode = cdResultCode;
    }

    /**
     * @return the cdResultDesc
     */
    public String getCdResultDesc() {
        return cdResultDesc;
    }

    /**
     * @param cdResultDesc the cdResultDesc to set
     */
    public void setCdResultDesc(String cdResultDesc) {
        this.cdResultDesc = cdResultDesc;
    }

    /**
     * @return the cdResponseDate
     */
    public String getCdResponseDate() {
        return cdResponseDate;
    }

    /**
     * @param cdResponseDate the cdResponseDate to set
     */
    public void setCdResponseDate(String cdResponseDate) {
        this.cdResponseDate = cdResponseDate;
    }

    /**
     * @return the drCode
     */
    public String getDrCode() {
        return drCode;
    }

    /**
     * @param drCode the drCode to set
     */
    public void setDrCode(String drCode) {
        this.drCode = drCode;
    }

    /**
     * @return the drDescription
     */
    public String getDrDescription() {
        return drDescription;
    }

    /**
     * @param drDescription the drDescription to set
     */
    public void setDrDescription(String drDescription) {
        this.drDescription = drDescription;
    }

    /**
     * @return the drActor
     */
    public String getDrActor() {
        return drActor;
    }

    /**
     * @param drActor the drActor to set
     */
    public void setDrActor(String drActor) {
        this.drActor = drActor;
    }

    /**
     * @return the excdResultCode
     */
    public String getExcdResultCode() {
        return excdResultCode;
    }

    /**
     * @param excdResultCode the excdResultCode to set
     */
    public void setExcdResultCode(String excdResultCode) {
        this.excdResultCode = excdResultCode;
    }

    /**
     * @return the excdResultDesc
     */
    public String getExcdResultDesc() {
        return excdResultDesc;
    }

    /**
     * @param excdResultDesc the excdResultDesc to set
     */
    public void setExcdResultDesc(String excdResultDesc) {
        this.excdResultDesc = excdResultDesc;
    }

    /**
     * @return the excdResponseDate
     */
    public String getExcdResponseDate() {
        return excdResponseDate;
    }

    /**
     * @param excdResponseDate the excdResponseDate to set
     */
    public void setExcdResponseDate(String excdResponseDate) {
        this.excdResponseDate = excdResponseDate;
    }    

    /**
     * @return the dfErrorList
     */
    public List<ErrorType> getDfErrorList() {
        return dfErrorList;
    }

    /**
     * @param dfErrorList the dfErrorList to set
     */
    public void setDfErrorList(List<ErrorType> dfErrorList) {
        this.dfErrorList = dfErrorList;
    }

    /**
     * @return the etCode
     */
    public String getEtCode() {
        return etCode;
    }

    /**
     * @param etCode the etCode to set
     */
    public void setEtCode(String etCode) {
        this.etCode = etCode;
    }

    /**
     * @return the etSeverityLevel
     */
    public String getEtSeverityLevel() {
        return etSeverityLevel;
    }

    /**
     * @param etSeverityLevel the etSeverityLevel to set
     */
    public void setEtSeverityLevel(String etSeverityLevel) {
        this.etSeverityLevel = etSeverityLevel;
    }

    /**
     * @return the etDescription
     */
    public String getEtDescription() {
        return etDescription;
    }

    /**
     * @param etDescription the etDescription to set
     */
    public void setEtDescription(String etDescription) {
        this.etDescription = etDescription;
    }

    /**
     * @return the responseControlData
     */
    public Holder<ControlDataResponseHeaderType> getResponseControlData() {
        return responseControlData;
    }

    /**
     * @param responseControlData the responseControlData to set
     */
    public void setResponseControlData(Holder<ControlDataResponseHeaderType> responseControlData) {
        this.responseControlData = responseControlData;
    }

    /**
     * @return the detailResponse
     */
    public Holder<DetailResponseType> getDetailResponse() {
        return detailResponse;
    }

    /**
     * @param detailResponse the detailResponse to set
     */
    public void setDetailResponse(Holder<DetailResponseType> detailResponse) {
        this.detailResponse = detailResponse;
    }

    /**
     * @return the updateTTResponse
     */
    public Holder<UpdateTTRespType> getUpdateTTResponse() {
        return updateTTResponse;
    }

    /**
     * @param updateTTResponse the updateTTResponse to set
     */
    public void setUpdateTTResponse(Holder<UpdateTTRespType> updateTTResponse) {
        this.updateTTResponse = updateTTResponse;
    }

    /**
     * @return the mensaje
     */
    public String getMensaje() {
        return mensaje;
    }

    /**
     * @param mensaje the mensaje to set
     */
    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }
}
