/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

import java.util.Date;
import org.json.JSONObject;
import org.json.XML;

/**
 * This model class contains the attributes and methods of Records
 *
 * @author User
 */
public class Records {

    int records_id;
    int mask_code;
    Date datetimeregistration;
    Boolean alert;
    int ppminternal;
    int ppmexternal;
    String json;
    String xml;

    /**
     * Default construction method
     */
    public Records() {
    }

    /**
     * Parameterized construction method
     *
     * @param records_id
     * @param mask_code
     * @param datetimeregistration
     * @param alert
     * @param ppminternal
     * @param ppmexternal
     */
    public Records(int records_id, int mask_code, Date datetimeregistration, Boolean alert, int ppminternal, int ppmexternal) {
        this.records_id = records_id;
        this.mask_code = mask_code;
        this.datetimeregistration = datetimeregistration;
        this.alert = alert;
        this.ppminternal = ppminternal;
        this.ppmexternal = ppmexternal;
    }

    /**
     * Parameterized construction method
     *
     * @param mask_code
     * @param datetimeregistration
     * @param alert
     * @param ppminternal
     * @param ppmexternal
     */
    public Records(int mask_code, Date datetimeregistration, Boolean alert, int ppminternal, int ppmexternal) {
        this.mask_code = mask_code;
        this.datetimeregistration = datetimeregistration;
        this.alert = alert;
        this.ppminternal = ppminternal;
        this.ppmexternal = ppmexternal;
    }

    public int getRecords_id() {
        return records_id;
    }

    /**
     * Set the identifier of records
     *
     * @param records_id
     */
    public void setRecords_id(int records_id) {
        this.records_id = records_id;
    }

    public int getMask_code() {
        return mask_code;
    }

    /**
     * Set the code mask
     *
     * @param mask_code
     */
    public void setMask_code(int mask_code) {
        this.mask_code = mask_code;
    }

    public Date getDatetimeregistration() {
        return datetimeregistration;
    }

    /**
     * Set the date registration
     *
     * @param datetimeregistration
     */
    public void setDatetimeregistration(Date datetimeregistration) {
        this.datetimeregistration = datetimeregistration;
    }

    public Boolean getAlert() {
        return alert;
    }

    /**
     * Set the alert
     *
     * @param alert
     */
    public void setAlert(Boolean alert) {
        this.alert = alert;
    }

    public int getPpminternal() {
        return ppminternal;
    }

    /**
     * Set the ppp internal mask
     *
     * @param ppminternal
     */
    public void setPpminternal(int ppminternal) {
        this.ppminternal = ppminternal;
    }

    public int getPpmexternal() {
        return ppmexternal;
    }

    /**
     * Set the ppp external mask
     *
     * @param ppmexternal
     */
    public void setPpmexternal(int ppmexternal) {
        this.ppmexternal = ppmexternal;
    }

    /**
     * Method to obtain a json with the information of records
     *
     * @return
     */
    public String getJson() {
        return json;
    }

    /**
     * Set up a json with the information of records
     *
     * @param json
     */
    public void setJson(String json) {
        this.json = json;
    }

    /**
     * Method to obtain a xml with the information of records
     *
     * @return
     */
    public String getXml() {
        return xml;
    }

    public String getJson2Xml() {
        JSONObject jsonU = new JSONObject(this.json);
        return this.xml = XML.toString(jsonU);
    }

    /**
     * Set up a xml with the information of records
     *
     * @param xml
     */
    public void setXml(String xml) {
        this.xml = xml;
    }

}
