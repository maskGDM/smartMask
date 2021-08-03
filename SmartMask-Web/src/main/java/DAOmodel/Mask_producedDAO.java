/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAOmodel;

import Config.Conection;
import Interfaces.Mask_producedCrud;
import Model.Mask_produced;

/**
 * This DAO model class contains the attributes and methods of a mask produce
 * @author Brito,Solano,Velez
 * @version 1.0
 */
public class Mask_producedDAO implements Mask_producedCrud {

    Conection conex = new Conection();
    Mask_produced mask = new Mask_produced();
/**
 * Method to obtain a json-chain with the information of a
     * mask produced
 * @return Return one json string
 */
    
    public String jsonMaskMask_produced() {
        return conex.getRecordsInJson("SELECT * FROM mask_produced");
    }
    /**
     * Method to deactivate a mask
     * @param mask Receive of the model Mask_produced
     * @return Returns a Boolean if the query was successful 
     */

    public boolean deactivateMaskMask_produced(Mask_produced mask) {
        return conex.modifyBD("select deletemask(" + mask.getMask_code() + ");");
    }
    /**
     * Method to add a mask
     * @param mask Receive of the model Mask_produced
     * @return Returns a Boolean if the query was succesful
     */

    @Override
    public boolean addMask_produced(Mask_produced mask) {
        return conex.modifyBD("select addmaskproduced (" + mask.getMask_code() + ");");
    }

    @Override
    public boolean editMask_produced(Mask_produced mask) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean deleteorresetMask_produced(Mask_produced mask) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
