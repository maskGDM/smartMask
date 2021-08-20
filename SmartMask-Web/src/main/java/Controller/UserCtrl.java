/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import Config.WeEncoder;
import DAOmodel.RecordsDAO;
import DAOmodel.UserDAO;
import Model.User;
import Model.User_information;
import java.io.File;

/**
 *
 * @author geova
 */
public class UserCtrl {

    UserDAO userDAO;
    WeEncoder weEncoder;

    public UserCtrl() {
        userDAO = new UserDAO();
        weEncoder = new WeEncoder();
    }

    public String[] logIn(
            String user,
            String pass
    ) {
        User userModel = new User();
        userModel.setUser(user);
        pass = weEncoder.encriptPassword(pass);

        userModel.setPassword(pass);
        String status = "4", message = "Error al retornar datos", data = "[]";

        String[] resp = userDAO.logInAPI("1", userModel);

        if (resp[0].equals("2")) {
            status = resp[0];
            data = resp[1];
            message = "Los datos se han retornado correctamente";
        }
        return new String[]{status, message, data};
    }

    public String[] sigIn(
            String name,
            String lastname,
            String email,
            String user,
            String pass,
            String confirmpass
    ) {
        User userModel = new User();
        userModel.setUser(user);
        userModel.setNames(name);
        userModel.setLastnames(lastname);
        userModel.setEmail(email);
        userModel.setType("U");
        userModel.setPassword(pass);
        pass = weEncoder.encriptPassword(pass);
        confirmpass = weEncoder.encriptPassword(confirmpass);
        userModel.setPassword(pass);

        String status = "4", message = "No se puede agregar este usuario", data = "[]";

        if (pass.equals(confirmpass)) {
            String[] resp = userDAO.saveUser(userModel);

            if (resp[0].equals("2")) {
                status = resp[0];
                data = resp[1];
                message = "El usuario ha sido ingresado correctamente";
            }else if (resp[0].equals("3")){
                status = resp[0];
                data = resp[1];
                message = "El usuario ya se encuentra registrado";
            }
        }
        return new String[]{status, message, data};
    }

    public String[] updateUser(
            String names,
            String lastnames,
            String user,
            String userinformation_id
    ) {
        User userModel = new User();
//        User_information user_information = new User_information();
        userModel.setNames(names);
        userModel.setLastnames(lastnames);
        userModel.setUser(user);
        userModel.setUser_informationid(userinformation_id);

        String status = "4", message = "Error al retornar datos", data = "[]";

        String[] resp = userDAO.updateUser(userModel);

        if (resp[0].equals("2")) {
            status = resp[0];
            data = resp[1];
            message = "Los datos se han retornado correctamente";
        }
        return new String[]{status, message, data};
    }
}
