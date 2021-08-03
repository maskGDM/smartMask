/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

/**
 * This model class contains the attributes and methods of City
 * @author geova
 */
public class City {
    int cityid;
    String city;
    int provinceid;
/**
 * Default construction method
 */
    public City() {
    }
/**
 * Parameterized construction method
 * @param cityid Identification of city
 * @param city name city
 * @param provinceid Identification of province
 */
    public City(int cityid, String city, int provinceid) {
        this.cityid = cityid;
        this.city = city;
        this.provinceid = provinceid;
    }
/**
 * Parameterized construction method
 * @param city name city
 * @param provinceid Identification of province
 */
    public City(String city, int provinceid) {
        this.city = city;
        this.provinceid = provinceid;
    }
/**
 * Method for obtaining the Identification of city
 * @return return the Identification of city
 */
    public int getCityid() {
        return cityid;
    }
/**
 * Set the identifier of city 
 * @param cityid 
 */
    public void setCityid(int cityid) {
        this.cityid = cityid;
    }
/**
 * Method for obtaining the name of city
 * @return the name of city
 */
    public String getCity() {
        return city;
    }
/**
 * set the name of city
 * @param city 
 */
    public void setCity(String city) {
        this.city = city;
    }
/**
 * Method for obtaining the Identification of province
 * @return Identification of province
 */
    public int getProvinceid() {
        return provinceid;
    }
/**
 * Set the identifier province
 * @param provinceid Identification of province
 */
    public void setProvinceid(int provinceid) {
        this.provinceid = provinceid;
    }
    
    
}
