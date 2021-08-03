/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

import java.util.Date;

/**
 * This model class contains the attributes and methods of province
 * @author Michelle
 */
public class Province {

    int provinceid;
    String province;
    int countryid;
    String json;
    String xml;
/**
 * Default construction method
 */
    public Province() {
    }
/**
 * Parameterized construction method
 * 
 * @param provinceid Identification of province
 * @param province name province
 * @param countryid Identification of country
 * @param json
 * @param xml 
 */
    public Province(int provinceid, String province, int countryid, String json, String xml) {
        this.provinceid = provinceid;
        this.province = province;
        this.countryid = countryid;
        this.json = json;
        this.xml = xml;
    }
/**
 * Parameterized construction method
 * 
 * @param province name province
 * @param countryid Identification of country
 * @param json
 * @param xml 
 */
    public Province(String province, int countryid, String json, String xml) {
        this.province = province;
        this.countryid = countryid;
        this.json = json;
        this.xml = xml;
    }
/**
 * Method to obtain identifier of province
 * @return 
 */
    public int getProvinceid() {
        return provinceid;
    }
/**
 * Set the identifier of province
 * @param provinceid 
 */
    public void setProvinceid(int provinceid) {
        this.provinceid = provinceid;
    }
/**
 * Method to obtain name of province
 * @return 
 */
    public String getProvince() {
        return province;
    }
/**
 * Set the name province
 * @param province 
 */
    public void setProvince(String province) {
        this.province = province;
    }
/**
 * Method to obtain identifier of country
 * @return 
 */
    public int getCountryid() {
        return countryid;
    }
/**
 * Set the identifier of country
 * @param countryid 
 */
    public void setCountryid(int countryid) {
        this.countryid = countryid;
    }
/**
 * Method to obtain a json with the information of province
 * @return 
 */
    public String getJson() {
        return json;
    }
/**
 * Set up a json with the information of province
 * @param json 
 */
    public void setJson(String json) {
        this.json = json;
    }
/**
 * Method to obtain a xml with the information of province
 * @return 
 */
    public String getXml() {
        return xml;
    }
/**
 * Set up a xml with the information of province
 * @param xml 
 */
    public void setXml(String xml) {
        this.xml = xml;
    }

 
}
