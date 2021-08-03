/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

import java.util.Date;

/**
 * This model class contains the attributes and methods of Social network
 * @author Michelle
 */
public class Socialnetwork {

    int socialnetworkid;
    String socialnetwork;
    String json;
    String xml;
/**
 * Default construction method
 */
    public Socialnetwork() {
    }
/**
 * Parameterized construction method
 * 
 * @param socialnetworkid Identification of social network
 * @param socialnetwork name social network
 * @param json
 * @param xml 
 */
    public Socialnetwork(int socialnetworkid, String socialnetwork, String json, String xml) {
        this.socialnetworkid = socialnetworkid;
        this.socialnetwork = socialnetwork;
        this.json = json;
        this.xml = xml;
    }
/**
 * Parameterized construction method
 * 
 * @param socialnetwork
 * @param json
 * @param xml 
 */
    public Socialnetwork(String socialnetwork, String json, String xml) {
        this.socialnetwork = socialnetwork;
        this.json = json;
        this.xml = xml;
    }
/**
 * Method to obtain identifier of social network
 * @return 
 */
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

    public String getSocialnetwork() {
        return socialnetwork;
    }
/**
 * Set the social network
 * @param socialnetwork 
 */
    public void setSocialnetwork(String socialnetwork) {
        this.socialnetwork = socialnetwork;
    }
/**
 * Method to obtain a json with the information of social network
 * @return 
 */
    public String getJson() {
        return json;
    }
/**
 * Set up a json with the information of social network
 * @param json 
 */
    public void setJson(String json) {
        this.json = json;
    }
/**
 * Method to obtain a xml with the information of social network
 * @return 
 */
    public String getXml() {
        return xml;
    }
/**
 * Set up a xml with the information of social network
 * @param xml 
 */
    public void setXml(String xml) {
        this.xml = xml;
    }

   
}
