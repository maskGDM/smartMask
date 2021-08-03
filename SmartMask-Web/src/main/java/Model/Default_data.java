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
 * This model class contains the attributes and methods of Default data
 *
 * @author User
 */
public class Default_data {

    int defaultdataid;
    Double maxvalueco2;
    Double minvalueco2;
    Double maxvlueco;
    Double minvalueco;
    Double minvaluec4h10;
    Double maxvaluec4h10;
    int user_informationid;
    Date editdate;
    int cityid;
    String json;
    String xml;

    /**
     * Default construction method
     */
    public Default_data() {
    }

    /**
     * Parameterized construction method
     *
     * @param defaultdataid
     * @param maxvalueco2 maximum carbon dioxide value
     * @param minvalueco2 minimum carbon dioxide value
     * @param maxvlueco maximum carbon monoxide value
     * @param minvalueco minimum carbon monoxide value
     * @param minvaluec4h10 minimum Butane value
     * @param maxvaluec4h10 maximum Butane value
     * @param user_informationid Identification of user
     * @param editdate date of edition
     * @param cityid Identification of city
     */
    public Default_data(int defaultdataid, Double maxvalueco2, Double minvalueco2, Double maxvlueco, Double minvalueco, Double minvaluec4h10, Double maxvaluec4h10, int user_informationid, Date editdate, int cityid) {
        this.defaultdataid = defaultdataid;
        this.maxvalueco2 = maxvalueco2;
        this.minvalueco2 = minvalueco2;
        this.maxvlueco = maxvlueco;
        this.minvalueco = minvalueco;
        this.minvaluec4h10 = minvaluec4h10;
        this.maxvaluec4h10 = maxvaluec4h10;
        this.user_informationid = user_informationid;
        this.editdate = editdate;
        this.cityid = cityid;
    }

    /**
     * Parameterized construction method
     *
     * @param maxvalueco2 maximum carbon dioxide value
     * @param minvalueco2 minimum carbon dioxide value
     * @param maxvlueco maximum carbon monoxide value
     * @param minvalueco minimum carbon monoxide value
     * @param minvaluec4h10 minimum Butane value
     * @param maxvaluec4h10 maximum Butane value
     * @param user_informationid Identification of user
     * @param editdate date of edition
     * @param cityid Identification of city
     */
    public Default_data(Double maxvalueco2, Double minvalueco2, Double maxvlueco, Double minvalueco, Double minvaluec4h10, Double maxvaluec4h10, int user_informationid, Date editdate, int cityid) {
        this.maxvalueco2 = maxvalueco2;
        this.minvalueco2 = minvalueco2;
        this.maxvlueco = maxvlueco;
        this.minvalueco = minvalueco;
        this.minvaluec4h10 = minvaluec4h10;
        this.maxvaluec4h10 = maxvaluec4h10;
        this.user_informationid = user_informationid;
        this.editdate = editdate;
        this.cityid = cityid;
    }
/**
 *  Method for obtaining the Identification of data 
 * @return 
 */
    public int getDefaultdataid() {
        return defaultdataid;
    }
/**
 * Set the identifier of data 
 * @param defaultdataid 
 */
    public void setDefaultdataid(int defaultdataid) {
        this.defaultdataid = defaultdataid;
    }

    /**
     * Method for obtaining the maximum carbon dioxide value
     *
     * @return
     */
    public Double getMaxvalueco2() {
        return maxvalueco2;
    }

    /**
     * Set the maximum carbon dioxide value
     *
     * @param maxvalueco2
     */
    public void setMaxvalueco2(Double maxvalueco2) {
        this.maxvalueco2 = maxvalueco2;
    }

    /**
     * Method for obtaining the minimum carbon dioxide value
     *
     * @return
     */
    public Double getMinvalueco2() {
        return minvalueco2;
    }

    /**
     * Set the minimum carbon dioxide value
     *
     * @param minvalueco2
     */
    public void setMinvalueco2(Double minvalueco2) {
        this.minvalueco2 = minvalueco2;
    }

    /**
     * Method for obtaining the maximum carbon monoxide value
     *
     * @return
     */
    public Double getMaxvlueco() {
        return maxvlueco;
    }

    /**
     * set the maximum carbon monoxide value
     *
     * @param maxvlueco
     */
    public void setMaxvlueco(Double maxvlueco) {
        this.maxvlueco = maxvlueco;
    }

    /**
     * Method for obtaining the minimum carbon monoxide value
     *
     * @return
     */
    public Double getMinvalueco() {
        return minvalueco;
    }

    /**
     * set the minimum carbon monoxide value
     *
     * @param minvalueco
     */
    public void setMinvalueco(Double minvalueco) {
        this.minvalueco = minvalueco;
    }

    /**
     * Method for obtaining the minimum Butane value
     *
     * @return
     */
    public Double getMinvaluec4h10() {
        return minvaluec4h10;
    }

    /**
     * set the minimum Butane value
     *
     * @param minvaluec4h10
     */
    public void setMinvaluec4h10(Double minvaluec4h10) {
        this.minvaluec4h10 = minvaluec4h10;
    }

    /**
     * Method for obtaining the maximum Butane value
     *
     * @return
     */
    public Double getMaxvaluec4h10() {
        return maxvaluec4h10;
    }

    /**
     * set the maximum Butane value
     *
     * @param maxvaluec4h10
     */
    public void setMaxvaluec4h10(Double maxvaluec4h10) {
        this.maxvaluec4h10 = maxvaluec4h10;
    }

    public int getUser_informationid() {
        return user_informationid;
    }

    public void setUser_informationid(int user_informationid) {
        this.user_informationid = user_informationid;
    }

    public Date getEditdate() {
        return editdate;
    }

    public void setEditdate(Date editdate) {
        this.editdate = editdate;
    }

    public int getCityid() {
        return cityid;
    }

    public void setCityid(int cityid) {
        this.cityid = cityid;
    }

    /**
     * Method to obtain a json with the information of default data
     *
     * @return
     */
    public String getJson() {
        return json;
    }

    /**
     * Set up a json with the information of default data
     *
     * @param json
     */
    public void setJson(String json) {
        this.json = json;
    }

    /**
     * Method to obtain a xml with the information of default data
     *
     * @return
     */
    public String getXml() {
        return xml;
    }

    /**
     * Set up a xml with the information of default data
     *
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
