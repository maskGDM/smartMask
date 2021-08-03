/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAOmodel;

import Config.Conection;
import Interfaces.ProvinceCrud;
import Model.Province;

/**
 * This DAO model class contains the attributes and methods of a ProvinceDAO
 * @author Brito,Solano,Velez
 * @version 1.0
 */
public class ProvinceDAO implements ProvinceCrud {

    Conection conex = new Conection();
    Province province = new Province();

    /**
     * Method to obtain a json-chain with the information of a
     * province
     * @param province Receive the model Mask
     * @return Return one json string
     */
    public String jsonProvince(Province province) {
        return conex.getRecordsInJson("SELECT * FROM province WHERE countryid = " + province.getCountryid()+ " order by province asc;");
    }

    @Override
    public boolean addProvince(Province province) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean editProvince(Province province) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean deleteorresetProvince(Province province) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

  

}
