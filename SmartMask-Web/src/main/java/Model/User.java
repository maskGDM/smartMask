/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

import org.json.JSONObject;
import org.json.XML;

/**
 * This model class contains the attributes and methods of User
 *
 * @author User
 */
public class User {

    String user_id;
    String user;
    String password;
    String user_informationid;
    String socialnetworkid;
    String type;
    String socialnetworkcode;
    String json;
    String xml;

    String names;
    String lastnames;
    String email;
    String imguser;

    /**
     * Default construction method
     */
    public User() {
    }

    /**
     * Parameterized construction method
     *
     * @param user_id Identification of user
     * @param user
     * @param password
     * @param user_informationid Identification of user_information
     * @param socialnetworkid Identification of social network
     * @param type
     * @param socialnetworkcode
     * @param json
     * @param xml
     */


    /**
     * Parameterized construction method
     *
     * @param user
     * @param password
     * @param user_informationid
     * @param socialnetworkid
     * @param type
     * @param socialnetworkcode
     * @param json
     * @param xml
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


    public String getUser() {
        return user;
    }

    /**
     * Set the name of user
     *
     * @param user
     */
    public void setUser(String user) {
        this.user = user;
    }

    public String getPassword() {
        return password;
    }

    /**
     * Set the password of user
     *
     * @param password
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * Method to obtain identifier of user
     *
     * @return
     */
 

    /**
     * Set the identifier of user
     *
     * @param user_informationid
     */
    

    /**
     * Set the identifier of social network
     *
     * @param socialnetworkid
     */


    /**
     * Set type
     *
     * @param type
     */
  

    public String getSocialnetworkcode() {
        return socialnetworkcode;
    }

    /**
     * Set the code of social network
     *
     * @param socialnetworkcode
     */
    public void setSocialnetworkcode(String socialnetworkcode) {
        this.socialnetworkcode = socialnetworkcode;
    }

    /**
     * Method to obtain a json with the information of user
     *
     * @return
     */
    public String getJson() {
        return json;
    }

    /**
     * Set up a json with the information of user
     *
     * @param json
     */
    public void setJson(String json) {
        this.json = json;
    }

    /**
     * Method to obtain a xml with the information of user
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
     * Set up a xml with the information of user
     *
     * @param xml
     */
    public void setXml(String xml) {
        this.xml = xml;
    }

    public String getNames() {
        return names;
    }

    public void setNames(String names) {
        this.names = names;
    }

    public String getLastnames() {
        return lastnames;
    }

    public void setLastnames(String lastnames) {
        this.lastnames = lastnames;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getImguser() {
        return imguser;
    }

    public void setImguser(String imguser) {
        this.imguser = imguser;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getUser_informationid() {
        return user_informationid;
    }

    public void setUser_informationid(String user_informationid) {
        this.user_informationid = user_informationid;
    }

    public String getSocialnetworkid() {
        return socialnetworkid;
    }

    public void setSocialnetworkid(String socialnetworkid) {
        this.socialnetworkid = socialnetworkid;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String returnXml() {
        JSONObject jsonU = new JSONObject(this);
        return "<users>" + XML.toString(jsonU) + "</users>";
    }
}
