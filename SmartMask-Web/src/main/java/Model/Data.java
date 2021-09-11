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
 * This model class contains the attributes and methods of Data
 *
 * @author geova
 */
public class Data {

    String data_id;
    Date editdate;
    String user_informationid;
    Double maxvalueco2;
    Double minvalueco2;
    Double maxvalueco;
    Double minvalueco;
    Double maxvaluec4h10;
    Double minvaluec4h10;
    Double maxvaluenh3;
    Double minvaluenh3;
    String xml;
    String json;

    /**
     * Default construction method
     */
    public Data() {
    }

    /**
     * Parameterized construction method
     *
     * @param data_id Identification of data
     * @param editdate date of edition
     * @param user_informationid Identification of user
     * @param maxvalueco2 maximum carbon dioxide value
     * @param minvalueco2 minimum carbon dioxide value
     * @param maxvalueco maximum carbon monoxide value
     * @param minvalueco minimum carbon monoxide value
     * @param maxvaluec4h10 maximum Butane value
     * @param mainvaluec4h10 minimum Butane value
     */
    /**
     * Parameterized construction method
     *
     * @param editdate date of edition
     * @param user_informationid Identification of user
     * @param maxvalueco2 maximum carbon dioxide value
     * @param minvalueco2 minimum carbon dioxide value
     * @param maxvalueco maximum carbon monoxide value
     * @param minvalueco minimum carbon monoxide value
     * @param maxvaluec4h10 maximum Butane value
     * @param mainvaluec4h10 minimum Butane value
     */
    /**
     * Method for obtaining the Identification of data
     *
     * @return
     */
    /**
     * Set the identifier of data
     *
     * @param data_id
     */
    /**
     * Method for obtaining the edit date
     *
     * @return
     */
    public Date getEditdate() {
        return editdate;
    }

    /**
     * Set the edit date
     *
     * @param editdate
     */
    public void setEditdate(Date editdate) {
        this.editdate = editdate;
    }

    /**
     * Method for obtaining the Identification of user
     *
     * @return
     */
    /**
     *
     * Set the identifier of user
     *
     * @param user_informationid
     */
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
    public Double getMaxvalueco() {
        return maxvalueco;
    }

    /**
     * set the maximum carbon monoxide value
     *
     * @param maxvalueco
     */
    public void setMaxvalueco(Double maxvalueco) {
        this.maxvalueco = maxvalueco;
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

    /**
     * Method for obtaining the minimum Butane value
     *
     * @return
     */
    public Double getMainvaluec4h10() {
        return minvaluec4h10;
    }

    /**
     * set the minimum Butane value
     *
     * @param minvaluec4h10
     */
    public void setMainvaluec4h10(Double minvaluec4h10) {
        this.minvaluec4h10 = minvaluec4h10;
    }

    public String getData_id() {
        return data_id;
    }

    public void setData_id(String data_id) {
        this.data_id = data_id;
    }

    public String getUser_informationid() {
        return user_informationid;
    }

    public void setUser_informationid(String user_informationid) {
        this.user_informationid = user_informationid;
    }

    /**
     * Method to obtain a xml with the information of data
     *
     * @return
     */
    public String getXml() {
        return xml;
    }

    /**
     * Set up a xml with the information of data
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

    /**
     * Method to obtain a json with the information of data
     *
     * @return
     */
    public String getJson() {
        return json;
    }

    /**
     * Set up a json with the information of data
     *
     * @param json
     */
    public void setJson(String json) {
        this.json = json;
    }

    public Double getMinvaluec4h10() {
        return minvaluec4h10;
    }

    public void setMinvaluec4h10(Double minvaluec4h10) {
        this.minvaluec4h10 = minvaluec4h10;
    }

    public Double getMaxvaluenh3() {
        return maxvaluenh3;
    }

    public void setMaxvaluenh3(Double maxvaluenh3) {
        this.maxvaluenh3 = maxvaluenh3;
    }

    public Double getMinvaluenh3() {
        return minvaluenh3;
    }

    public void setMinvaluenh3(Double minvaluenh3) {
        this.minvaluenh3 = minvaluenh3;
    }

    public String returnXml() {
        JSONObject jsonU = new JSONObject(this);
        return "<data>" + XML.toString(jsonU) + "</data>";
    }
}
