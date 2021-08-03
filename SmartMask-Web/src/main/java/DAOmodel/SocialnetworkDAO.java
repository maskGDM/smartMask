/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAOmodel;

import Config.Conection;
import Interfaces.SocialnetworkCrud;
import Model.Socialnetwork;

/**
 * Method to obtain a json-chain with the information of a social network
 * @author Brito,Solano,Velez
 * @version 1.0
 */
public class SocialnetworkDAO implements SocialnetworkCrud {

    Conection conex = new Conection();
    Socialnetwork socialnetwork = new Socialnetwork();

    public String jsonSocialnetwork(Socialnetwork socialnetwork) {
        return "";
    }

    @Override
    public boolean addSocialnetwork(Socialnetwork socialnetwork) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean editSocialnetwork(Socialnetwork socialnetwork) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean deleteorresetSocialnetwork(Socialnetwork socialnetwork) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

     

}
