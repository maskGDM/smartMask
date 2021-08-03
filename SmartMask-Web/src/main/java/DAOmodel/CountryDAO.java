/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAOmodel;

import Config.Conection;
import Interfaces.CountryCrud;
import Model.Country;

/**
 *
 * This DAO model class contains the country attributes and methods
 *
 * @author Brito, Solano, Velez
 * @version 1.0
 */
public class CountryDAO implements CountryCrud {

    Conection conex = new Conection();
    Country country = new Country();

    /**
     * Method for obtaining information about a country
     * @return Returns a json format string containing the information
     * of a country
     */
    public String jsonCountry() {
         return conex.getRecordsInJson("select * from country order by country asc");
    }
    
    /**
     * Method for saving the information of a country
     * @param country Receive the model of the country
     * @return Returns a Boolean if you inserted the city correctly
     */

    @Override
    public boolean addCountry(Country country) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    /**
     * Method to edit the information of a country
     * @param country Receive the model of the country
     * @return 
     */

    @Override
    public boolean editCountry(Country country) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    /**
     * Method to delete a country information
     * @param country Receive the model of the country
     * @return 
     */

    @Override
    public boolean deleteorresetCountry(Country country) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
