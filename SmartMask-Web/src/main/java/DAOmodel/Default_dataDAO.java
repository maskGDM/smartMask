/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAOmodel;

import Config.Conection;
import Interfaces.Default_dataCrud;
import Model.Default_data;

/**
 * 
 * This CAD model class contains the default 
 * Data attributes and methods
 * @author Brito,Solano,Velez
 * @version 1.0
 */

public class Default_dataDAO implements Default_dataCrud {

    Conection conex = new Conection();
    Default_data default_data = new Default_data();
    
    /**
     * Method to obtain a json-type string with the default data
     * @param default_data Receive the model of the Default_data
     * @return  Return a Boolean and the query was correct
     */

    public String jsonDefault_data(Default_data default_data) {
        return conex.getRecordsInJson("select * from default_data where user_informationid =" + default_data.getUser_informationid()+ " order by defaultdataid desc limit 1;");
    }
    
    /**
     * Default saving method
     * @param default_data Receive the model of the Default_data
     * @return Return a Boolean and the query was correct
     */

    @Override
    public boolean addDefault_data(Default_data default_data) {
        return conex.modifyBD("select adddefauktdata('" + default_data.getJson2Xml() + "')");
    }
    
    /**
     * Method for editing default data
     * @param default_data Receive the model of the Default_data
     * @return Return a boolean and the query was correct
     */

    @Override
    public boolean editDefault_data(Default_data default_data) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    /**
     * Method to delete data by default
     * @param default_data Receive the model of the Default_data
     * @return Return a boolean and the query was correct
     */

    @Override
    public boolean deleteorresetDefault_data(Default_data default_data) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
