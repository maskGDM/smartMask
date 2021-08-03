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
 * @author User
 */
public class User {

    int user_id;
    String user;
    String password;
    int user_informationid;
    int socialnetworkid;
    char type;
    String socialnetworkcode;
    String json;
    String xml;
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
    public User(int user_id, String user, String password, int user_informationid, int socialnetworkid, char type, String socialnetworkcode, String json, String xml) {
        this.user_id = user_id;
        this.user = user;
        this.password = password;
        this.user_informationid = user_informationid;
        this.socialnetworkid = socialnetworkid;
        this.type = type;
        this.socialnetworkcode = socialnetworkcode;
        this.json = json;
        this.xml = xml;
    }
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
    public User(String user, String password, int user_informationid, int socialnetworkid, char type, String socialnetworkcode, String json, String xml) {
        this.user = user;
        this.password = password;
        this.user_informationid = user_informationid;
        this.socialnetworkid = socialnetworkid;
        this.type = type;
        this.socialnetworkcode = socialnetworkcode;
        this.json = json;
        this.xml = xml;
    }
/**
 * Method to obtain identifier of user
 * @return 
 */
    public int getUser_id() {
        return user_id;
    }
/**
 * Set the identifier of user
 * @param user_id 
 */
    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public String getUser() {
        return user;
    }
/**
 * Set the name of user
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
 * @param password 
 */
    public void setPassword(String password) {
        this.password = password;
    }
/**
 * Method to obtain identifier of user
 * @return 
 */
    public int getUser_informationid() {
        return user_informationid;
    }
/**
 * Set the identifier of user
 * @param user_informationid 
 */
    public void setUser_informationid(int user_informationid) {
        this.user_informationid = user_informationid;
    }

    public int getSocialnetworkid() {
        return socialnetworkid;
    }
/**
 * Set the identifier of social network
 * @param socialnetworkid 
 */
    public void setSocialnetworkid(int socialnetworkid) {
        this.socialnetworkid = socialnetworkid;
    }

    public char getType() {
        return type;
    }
/**
 * Set type 
 * @param type 
 */
    public void setType(char type) {
        this.type = type;
    }

    public String getSocialnetworkcode() {
        return socialnetworkcode;
    }
/**
 * Set the code of social network
 * @param socialnetworkcode 
 */
    public void setSocialnetworkcode(String socialnetworkcode) {
        this.socialnetworkcode = socialnetworkcode;
    }
/**
 * Method to obtain a json with the information of user
 * @return 
 */
    public String getJson() {
        return json;
    }
/**
 * Set up a json with the information of user
 * @param json 
 */
    public void setJson(String json) {
        this.json = json;
    }
/**
 * Method to obtain a xml with the information of user
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
 * @param xml 
 */
    public void setXml(String xml) {
        this.xml = xml;
    }

}
