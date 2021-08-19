/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

import org.json.JSONObject;
import org.json.XML;

/**
 * This model class contains the attributes and methods of Mask
 *
 * @author Michelle
 */
public class Mask {

    String id_mask;
    String user_informationid;
    String mask_register;
    String json;
    String xml;
    String mask_code;

    /**
     * Default construction method
     */
    public Mask() {
    }

    /**
     * Parameterized construction method
     *
     * @param id_mask Identification of mask
     * @param user_id Identification of user
     * @param mask_register
     * @param json
     * @param xml
     * @param mask_code
     */
    /**
     * Parameterized construction method
     *
     * @param user_id Identification of user
     * @param mask_register
     * @param json
     * @param xml
     * @param mask_code
     */
    /**
     * Set the identifier of mask
     *
     * @param id_mask
     */
    /**
     * Method to obtain identifier of user
     *
     * @return
     */
    /**
     * Set the identifier of user
     *
     * @param user_id
     */
    public String getMask_register() {
        return mask_register;
    }

    public void setMask_register(String mask_register) {
        this.mask_register = mask_register;
    }

    public String getId_mask() {
        return id_mask;
    }

    public void setId_mask(String id_mask) {
        this.id_mask = id_mask;
    }

    public String getUser_informationid() {
        return user_informationid;
    }

    public void setUser_informationid(String user_informationid) {
        this.user_informationid = user_informationid;
    }

    public String getMask_code() {
        return mask_code;
    }

    public void setMask_code(String mask_code) {
        this.mask_code = mask_code;
    }

    /**
     * Method to obtain code mask
     *
     * @return
     */
    /**
     * Method to obtain a json with the information of mask
     *
     * @return
     */
    public String getJson() {
        return json;
    }

    /**
     * Set up a json with the information of mask
     *
     * @param json
     */
    public void setJson(String json) {
        this.json = json;
    }

    /**
     * Method to obtain a xml with the information of mask
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
     * Set up a xml with the information of mask
     *
     * @param xml
     */
    public void setXml(String xml) {
        this.xml = xml;
    }

    public String returnXml() {
        JSONObject jsonU = new JSONObject(this);
        return "<mask>" + XML.toString(jsonU) + "</mask>";
    }
}
