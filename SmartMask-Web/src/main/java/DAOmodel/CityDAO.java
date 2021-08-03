/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAOmodel;

import Config.Conection;
import Interfaces.CityCrud;
import Model.City;

/**
 *
 * This DAO model class contains the attributes and methods of city
 *
 * @authors Brito, Solano, Velez
 * @version 1.0
 */
public class CityDAO implements CityCrud {

    Conection conex = new Conection();
    City city = new City();

   /** 
    *Method to obtain the json chain with the information of a city
    * 
    * @return Returns a json format string containing the information
    * of a city
    */
    
    public String jsonCity(City city) {
        return conex.getRecordsInJson("SELECT * FROM city WHERE provinceid = " + city.getProvinceid()+ " order by city asc;");
    }
    
    /**
     * Method for saving the information of a city
     * 
     * @param city Receive the model of the city
     * @return Returns a Boolean if you inserted the city correctly
     */
    @Override
    public boolean addCity(City city) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    /**
     * Method to edit the information of a city
     * @param city Receive the model of the city
     * @return 
     */

    @Override
    public boolean editCity(City city) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    /**
     * Method to eliminate a city
     * @param city Receive the model of the city
     * @return 
     */

    @Override
    public boolean deleteorresetCity(City city) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
