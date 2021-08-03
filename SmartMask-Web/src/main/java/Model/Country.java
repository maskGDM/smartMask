/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

/**
 * This model class contains the attributes and methods of Country
 * @author geova
 */
public class Country {

    int countryid;
    String country;
    String xml;
    String json;
/**
 * Default construction method
 */
    public Country() {
    }
/**
 * Parameterized construction method
 * @param countryid identifier country
 * @param country name country
 * @param xml
 * @param json 
 */
    public Country(int countryid, String country, String xml, String json) {
        this.countryid = countryid;
        this.country = country;
        this.xml = xml;
        this.json = json;
    }
/**
 * Parameterized construction method
 * @param country name country
 * @param xml
 * @param json 
 */
    public Country(String country, String xml, String json) {
        this.country = country;
        this.xml = xml;
        this.json = json;
    }
/**
 * Method for obtaining the Identification of country
 * @return return the identification of country
 */
    public int getCountryid() {
        return countryid;
    }
/**
 * Set the identifier country
 * @param countryid 
 */
    public void setCountryid(int countryid) {
        this.countryid = countryid;
    }
/**
 * Method for obtaining the name country
 * @return name country
 */
    public String getCountry() {
        return country;
    }
/**
 * Set the name country
 * @param country 
 */
    public void setCountry(String country) {
        this.country = country;
    }
/**
 * Method to obtain a xml with the information of country
 * @return 
 */
    public String getXml() {
        return xml;
    }
/**
 *  Set up a xml with the information of country
 * @param xml 
 */
    public void setXml(String xml) {
        this.xml = xml;
    }
/**
 * Method to obtain a json with the information of country
 * @return 
 */
    public String getJson() {
        return json;
    }
/**
 * Set up a json with the information of country
 * @param json 
 */
    public void setJson(String json) {
        this.json = json;
    }

  
}
