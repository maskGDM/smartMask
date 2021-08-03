/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAOmodel;

import Config.Conection;
import Interfaces.UserCrud;
import Model.User;
import Model.User_information;
import javax.swing.table.DefaultTableModel;

/**
 * Method to obtain a json-chain with the information of a user
 *
 * @author Brito,Solano,Velez
 * @version 1.0
 */
public class UserDAO implements UserCrud {

    Conection conex = new Conection();
    User user = new User();
    User_informationDAO user_informationDAO = new User_informationDAO();
    User_information user_information = new User_information();

    public String jsonUser(User user) {
        return "";
    }

    /**
     *
     * @param user
     * @return
     */
    @Override
    public boolean addUser(User user) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean editUser(User user) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean deleteorresetUser(User user) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    /**
     * Method to login Administrator
     *
     * @param user Receive the login template
     * @return Returns a boolean if you enter the information correctly, to
     * start session
     */
    public boolean login(User user) {
        String[] informationUser = (conex.fillString("select (user_informationid|| '-' || \"user\" ||'-' ||password) from \"user\" where \"user\" ='" + user.getUser() + "' and \"password\" ='" + user.getPassword() + "' and type='A'; ").split("-"));
        if (!"".equals(informationUser[0])) {
            user.setUser_informationid(Integer.parseInt(informationUser[0]));
            return (informationUser[1].equals(user.getUser())) && informationUser[2].equals(user.getPassword());
        } else {
            return false;
        }
    }

    public String[] logInAPI(String type, User user) {
        String query = String.format("select * from selectloginapp(%s, '%s', '%s')", type, user.getUser(), user.getPassword());
        System.out.println(query);
        DefaultTableModel tab = conex.returnRecord(query);
        if (tab.getRowCount() > 0) {
            return new String[]{
                tab.getValueAt(0, 0).toString(),
                tab.getValueAt(0, 1).toString()};
        } else {
            return new String[]{"4", ""};
        }
    }

    /**
     *
     * Method for logging in User
     *
     * @param user Receive User model
     * @return Returns a boolean if you enter the information correctly, to
     * start session
     */
    public boolean loginU(User user) {
        String[] informationUser = (conex.fillString("select (user_informationid|| '-' || \"user\" ||'-' ||password) from \"user\" where \"user\" ='" + user.getUser() + "' and \"password\" ='" + user.getPassword() + "' and type='U'; ").split("-"));
        if (!"".equals(informationUser[0])) {
            user.setUser_informationid(Integer.parseInt(informationUser[0]));
            return (informationUser[1].equals(user.getUser())) && informationUser[2].equals(user.getPassword());
        } else {
            return false;
        }

    }

    /**
     *
     * Metodo para iniciar sesion Usuario de una red social
     *
     * @param user Recibe el modelo de User
     * @param user_information
     * @return Regresa una booleano si ingresa la informacion correctamente,
     * para inicar sesion
     */
    public boolean loginSocial(User user, User_information user_information) {
        try {
            String[] informationUser = (conex.fillString("select (user_informationid|| '-' || socialnetworkcode|| '-' || \"user\") as result from \"user\" where \"user\" ='" + user.getUser() + "' and \"socialnetworkcode\" ='" + user.getSocialnetworkcode() + "' and type='U'; ").split("-"));
            user.setUser_informationid(Integer.parseInt(informationUser[0]));
            return (informationUser[1].equals(user.getSocialnetworkcode())) && informationUser[2].equals(user.getUser());
        } catch (Exception e) {
            return false;
        }

    }
}
