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
 * This model class contains the attributes and methods of Mask produced
 * @author geova
 */
public class Mask_produced {

    int mask_code;
    Date admissiondate;
    boolean active;
    String json;
    ;
String xml;
/**
 * Default construction method
 */
    public Mask_produced() {
    }
/**
 * Parameterized construction method
 * 
 * @param mask_code 
 * @param admissiondate
 * @param active 
 */
    public Mask_produced(int mask_code, Date admissiondate, boolean active) {
        this.mask_code = mask_code;
        this.admissiondate = admissiondate;
        this.active = active;
    }
/**
 * Method to obtain mask code
 * @return 
 */
    public int getMask_code() {
        return mask_code;
    }
/**
 * Set the code mask
 * @param mask_code 
 */
    public void setMask_code(int mask_code) {
        this.mask_code = mask_code;
    }

    public Date getAdmissiondate() {
        return admissiondate;
    }
/**
 * Set the  admission date
 * @param admissiondate 
 */
    public void setAdmissiondate(Date admissiondate) {
        this.admissiondate = admissiondate;
    }

    public boolean isActive() {
        return active;
    }
/**
 * Set the active
 * @param active 
 */
    public void setActive(boolean active) {
        this.active = active;
    }
/**
 * Method to obtain a json with the information of mask produced
 * @return 
 */
    public String getJson() {
        return json;
    }
/**
 * Set up a json with the information of mask produced
 * @param json 
 */
    public void setJson(String json) {
        this.json = json;
    }
/**
 *  Method to obtain a xml with the information of mask produced
 * @return 
 */
    public String getXml() {
        return xml;
    }
/**
 * Set up a xml with the information of mask produced
 * @param xml 
 */
    public void setXml(String xml) {
        this.xml = xml;
    }

    public String getJson2Xml() {
        JSONObject jsonU = new JSONObject(this.json);
        return this.xml = XML.toString(jsonU);
    }
}
