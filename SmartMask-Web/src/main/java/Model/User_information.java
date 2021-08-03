/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

import org.json.JSONObject;
import org.json.XML;

/**
 * This model class contains the attributes and methods of User information
 * @author Michelle
 */
public class User_information {

    int user_informationid;
    String names;
    String lastnames;
    String email;
    String imguser;
    String json;
    String xml;
/**
 * 
 */
    public User_information() {
    }
/**
 * Parameterized construction method
 * 
 * @param user_informationid Identification of user_information
 * @param names
 * @param lastnames
 * @param email
 * @param imguser
 * @param json
 * @param xml 
 */
    public User_information(int user_informationid, String names, String lastnames, String email, String imguser, String json, String xml) {
        this.user_informationid = user_informationid;
        this.names = names;
        this.lastnames = lastnames;
        this.email = email;
        this.imguser = imguser;
        this.json = json;
        this.xml = xml;
    }
/**
 * Parameterized construction method
 * 
 * @param names name user
 * @param lastnames lastename user
 * @param email email user
 * @param imguser image user
 * @param json
 * @param xml 
 */
    public User_information(String names, String lastnames, String email, String imguser, String json, String xml) {
        this.names = names;
        this.lastnames = lastnames;
        this.email = email;
        this.imguser = imguser;
        this.json = json;
        this.xml = xml;
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

    public String getNames() {
        return names;
    }
/**
 * Set the name of user
 * @param names 
 */
    public void setNames(String names) {
        this.names = names;
    }
/**
 * Method to obtain lastname of user
 * @return 
 */
    public String getLastnames() {
        return lastnames;
    }
/**
 * Set the lastname of user
 * @param lastnames 
 */
    public void setLastnames(String lastnames) {
        this.lastnames = lastnames;
    }
/**
 * Method to obtain email of user
 * @return 
 */
    public String getEmail() {
        return email;
    }
/**
 * Set the email of user
 * @param email 
 */
    public void setEmail(String email) {
        this.email = email;
    }
/**
 * Method to obtain image of user
 * @return 
 */
    public String getImguser() {
        return imguser;
    }
/**
 * Set the image of user
 * @param imguser 
 */
    public void setImguser(String imguser) {
        this.imguser = imguser;
    }
/**
 * Method to obtain a json with the of user information
 * @return 
 */
    public String getJson() {
        return json;
    }
/**
 * Set up a json with the of user information
 * @param json 
 */
    public void setJson(String json) {
        this.json = json;
    }
/**
 * Method to obtain a xml with the of user information
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
     * Set up a xml with the of user information
     * @param xml 
     */
    public void setXml(String xml) {
        this.xml = xml;
    }

}
