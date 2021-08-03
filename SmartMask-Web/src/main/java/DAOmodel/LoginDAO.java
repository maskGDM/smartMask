/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAOmodel;

import Config.Conection;
import Interfaces.LoginCrud;
import Model.Login;

/**
 * This DAO model class contains the attributes and methods for logging
 * @author Brito,Solano,Velez
 * @version 1.0
 */
public class LoginDAO implements LoginCrud {

    Conection conex = new Conection();
    Login login = new Login();

    /**
     * Method for logging in
     * @param login Receive the model of Login
     * @return 
     */
    public String jsonLogin(Login login) {
        return "";
    }

    @Override
    public boolean addLogin(Login login) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean editLogin(Login login) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean deleteorresetLogin(Login login) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
