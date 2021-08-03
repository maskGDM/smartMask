/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

import java.util.Date;

/**
 * This model class contains the attributes and methods of login
 * @author Michelle
 */
public class Login {

    int start_id;
    int user_id;
    String ip;
    Date logindate;
    String json;
    String xml;
/**
 * Default construction method
 */
    public Login() {
    }
/**
 * Parameterized construction method
 * 
 * @param start_id
 * @param user_id
 * @param ip
 * @param logindate
 * @param json
 * @param xml 
 */
    public Login(int start_id, int user_id, String ip, Date logindate, String json, String xml) {
        this.start_id = start_id;
        this.user_id = user_id;
        this.ip = ip;
        this.logindate = logindate;
        this.json = json;
        this.xml = xml;
    }
/**
 * Parameterized construction method
 * 
 * @param user_id
 * @param ip
 * @param logindate
 * @param json
 * @param xml 
 */
    public Login(int user_id, String ip, Date logindate, String json, String xml) {
        this.user_id = user_id;
        this.ip = ip;
        this.logindate = logindate;
        this.json = json;
        this.xml = xml;
    }

    public int getStart_id() {
        return start_id;
    }
/**
 * Set the identifier of start
 * @param start_id 
 */
    public void setStart_id(int start_id) {
        this.start_id = start_id;
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
/**
 * Method to obtain ip 
 * @return 
 */
    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }
/**
 * Method to obtain date login of user
 * @return 
 */
    public Date getLogindate() {
        return logindate;
    }
/**
 * Set the login date
 * @param logindate 
 */
    public void setLogindate(Date logindate) {
        this.logindate = logindate;
    }
/**
 * Method to obtain a json with the information of login
 * @return 
 */
    public String getJson() {
        return json;
    }
/**
 * Set up a json with the information of login
 * @param json 
 */
    public void setJson(String json) {
        this.json = json;
    }
/**
 * Method to obtain a xml with the information of login
 * @return 
 */
    public String getXml() {
        return xml;
    }
/**
 * Set up a xml with the information of login
 * @param xml 
 */
    public void setXml(String xml) {
        this.xml = xml;
    }

}
