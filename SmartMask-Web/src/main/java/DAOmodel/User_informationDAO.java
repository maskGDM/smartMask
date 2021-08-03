/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAOmodel;

import Config.Conection;
import Interfaces.User_informationCrud;
import Model.User_information;

/**
 *
 * @author Michelle
 */
public class User_informationDAO implements User_informationCrud {

    Conection conex = new Conection();
    User_information user_information = new User_information();

    public String jsonUser_information(User_information user_information) {
        return "";
    }

    @Override
    public boolean addUser_information(User_information user_information) {
        return conex.modifyBD("select adduser('" + user_information.getJson2Xml() + "')");
    }

    public boolean addUser_informationSocial(User_information user_information) {
        System.out.println("Datos= " + user_information.getJson2Xml());
        return conex.modifyBD("select addusersocial('" + user_information.getJson2Xml() + "')");
    }

    @Override
    public boolean editUser_information(User_information user_information) {
        System.out.println("select edituser ('" + user_information.getJson2Xml() + "');");
        return conex.modifyBD("select edituser ('" + user_information.getJson2Xml() + "');");
    }

    @Override
    public boolean deleteorresetUser_information(User_information user_information) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public String selectUser_information(User_information user_information) {
        return conex.getRecordsInJson("select * from user_information where user_informationid='" + user_information.getUser_informationid() + "';");
    }

    public String selectAdmin() {
        return conex.getRecordsInJson("select ui.user_informationid, names, lastnames, email, registrationdate, birthdaydate, \"user\", type from user_information as ui inner join \"user\" as u ON u.user_informationid = ui.user_informationid where type ='A'");
    }

    public String selectUser() {
        return conex.getRecordsInJson("select ui.user_informationid, names, lastnames, email, registrationdate, birthdaydate, \"user\", type from user_information as ui inner join \"user\" as u ON u.user_informationid = ui.user_informationid where type ='U'");
    }

    public String selectAllUser_information(User_information user_information) {
        System.out.println("select * from user_information as ui inner join \"user\" as u on u.user_informationid = ui.user_informationid where ui.user_informationid='" + user_information.getUser_informationid() + "';");

        return conex.getRecordsInJson("select * from user_information as ui inner join \"user\" as u on u.user_informationid = ui.user_informationid where ui.user_informationid='" + user_information.getUser_informationid() + "';");
    }

}
